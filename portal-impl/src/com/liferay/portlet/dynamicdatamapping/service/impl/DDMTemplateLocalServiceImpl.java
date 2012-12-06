/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.TemplateDuplicateTemplateKeyException;
import com.liferay.portlet.dynamicdatamapping.TemplateNameException;
import com.liferay.portlet.dynamicdatamapping.TemplateScriptException;
import com.liferay.portlet.dynamicdatamapping.TemplateSmallImageNameException;
import com.liferay.portlet.dynamicdatamapping.TemplateSmallImageSizeException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateLocalServiceBaseImpl;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMTemplateLocalServiceImpl
	extends DDMTemplateLocalServiceBaseImpl {

	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addTemplate(
			userId, groupId, classNameId, classPK, null, nameMap,
			descriptionMap, type, mode, language, script, false, false, null,
			null, serviceContext);
	}

	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			String templateKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Template

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		if (Validator.isNull(templateKey)) {
			templateKey = String.valueOf(counterLocalService.increment());
		}
		else {
			templateKey = templateKey.trim().toUpperCase();
		}

		byte[] smallImageBytes = null;

		if (smallImage) {
			try {
				smallImageBytes = FileUtil.getBytes(smallImageFile);
			}
			catch (IOException ioe) {
			}

			if ((smallImageBytes == null) || Validator.isUrl(smallImageURL)) {
				smallImage = false;
			}
		}

		validate(
			groupId, templateKey, nameMap, script, smallImage, smallImageURL,
			smallImageFile, smallImageBytes);

		long templateId = counterLocalService.increment();

		DDMTemplate template = ddmTemplatePersistence.create(templateId);

		template.setUuid(serviceContext.getUuid());
		template.setGroupId(groupId);
		template.setCompanyId(user.getCompanyId());
		template.setUserId(user.getUserId());
		template.setUserName(user.getFullName());
		template.setCreateDate(serviceContext.getCreateDate(now));
		template.setModifiedDate(serviceContext.getModifiedDate(now));
		template.setClassNameId(classNameId);
		template.setClassPK(classPK);
		template.setTemplateKey(templateKey);
		template.setNameMap(nameMap);
		template.setDescriptionMap(descriptionMap);
		template.setType(type);
		template.setMode(mode);
		template.setLanguage(language);
		template.setScript(script);
		template.setCacheable(cacheable);
		template.setSmallImage(smallImage);
		template.setSmallImageId(counterLocalService.increment());
		template.setSmallImageURL(smallImageURL);

		ddmTemplatePersistence.update(template);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addTemplateResources(
				template, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addTemplateResources(
				template, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallImageFile,
			smallImageBytes);

		return template;
	}

	public void addTemplateResources(
			DDMTemplate template, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	public void addTemplateResources(
			DDMTemplate template, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), groupPermissions, guestPermissions);
	}

	public List<DDMTemplate> copyTemplates(
			long userId, long classNameId, long oldClassPK, long newClassPK,
			String type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<DDMTemplate> newTemplates = new ArrayList<DDMTemplate>();

		List<DDMTemplate> oldTemplates = getTemplates(
			classNameId, oldClassPK, type);

		for (DDMTemplate oldTemplate : oldTemplates) {
			File smallImageFile = null;

			if (oldTemplate.isSmallImage() &&
					Validator.isNull(oldTemplate.getSmallImageURL())) {

				Image smallImage = ImageUtil.fetchByPrimaryKey(
					oldTemplate.getSmallImageId());

				if (smallImage != null) {
					smallImageFile = FileUtil.createTempFile(
						smallImage.getType());

					try {
						FileUtil.write(smallImageFile, smallImage.getTextObj());
					}
					catch (IOException ioe) {
						_log.error(ioe);
					}
				}
			}

			DDMTemplate newTemplate = addTemplate(
				userId, oldTemplate.getGroupId(), oldTemplate.getClassNameId(),
				newClassPK, null, oldTemplate.getNameMap(),
				oldTemplate.getDescriptionMap(), oldTemplate.getType(),
				oldTemplate.getMode(), oldTemplate.getLanguage(),
				oldTemplate.getScript(), oldTemplate.isCacheable(),
				oldTemplate.isSmallImage(), oldTemplate.getSmallImageURL(),
				smallImageFile, serviceContext);

			newTemplates.add(newTemplate);
		}

		return newTemplates;
	}

	public void deleteTemplate(DDMTemplate template)
		throws PortalException, SystemException {

		// Template

		ddmTemplatePersistence.remove(template);

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), DDMTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, template.getTemplateId());
	}

	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		deleteTemplate(template);
	}

	public void deleteTemplates(long groupId)
		throws PortalException, SystemException {

		List<DDMTemplate> templates = ddmTemplatePersistence.findByGroupId(
			groupId);

		for (DDMTemplate template : templates) {
			deleteTemplate(template);
		}
	}

	public DDMTemplate fetchTemplate(long groupId, String templateKey)
		throws SystemException {

		templateKey = templateKey.trim().toUpperCase();

		return ddmTemplatePersistence.fetchByG_T(groupId, templateKey);
	}

	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	public DDMTemplate getTemplate(long groupId, String templateKey)
		throws PortalException, SystemException {

		templateKey = templateKey.trim().toUpperCase();

		return ddmTemplatePersistence.findByG_T(groupId, templateKey);
	}

	public DDMTemplate getTemplate(
			long groupId, String templateKey, boolean includeGlobalTemplates)
		throws PortalException, SystemException {

		templateKey = templateKey.trim().toUpperCase();

		DDMTemplate template = ddmTemplatePersistence.fetchByG_T(
			groupId, templateKey);

		if (template != null) {
			return template;
		}

		if (!includeGlobalTemplates) {
			throw new NoSuchTemplateException(
				"No DDMTemplate exists with the template key " + templateKey);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return ddmTemplatePersistence.findByG_T(
			companyGroup.getGroupId(), templateKey);
	}

	public List<DDMTemplate> getTemplates(long classPK) throws SystemException {
		return ddmTemplatePersistence.findByClassPK(classPK);
	}

	public List<DDMTemplate> getTemplates(long groupId, long classNameId)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C(groupId, classNameId);
	}

	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	public List<DDMTemplate> getTemplates(
			long classNameId, long classPK, String type)
		throws SystemException {

		return ddmTemplatePersistence.findByC_C_T(classNameId, classPK, type);
	}

	public List<DDMTemplate> getTemplates(
			long classNameId, long classPK, String type, String mode)
		throws SystemException {

		return ddmTemplatePersistence.findByC_C_T_M(
			classNameId, classPK, type, mode);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode,
			start, end, orderByComparator);
	}

	public List<DDMTemplate> search(
			long companyId, long groupId, long classNameId, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator, start, end, orderByComparator);
	}

	public List<DDMTemplate> search(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode,
			start, end, orderByComparator);
	}

	public List<DDMTemplate> search(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPK, name, description, type,
			mode, language, andOperator, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode);
	}

	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator);
	}

	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode);
	}

	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPK, name, description, type,
			mode, language, andOperator);
	}

	public DDMTemplate updateTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioe) {
		}

		validate(
			nameMap, script, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);

		DDMTemplate template = ddmTemplateLocalService.getDDMTemplate(
			templateId);

		template.setModifiedDate(serviceContext.getModifiedDate(null));
		template.setNameMap(nameMap);
		template.setDescriptionMap(descriptionMap);
		template.setType(type);
		template.setMode(mode);
		template.setLanguage(language);
		template.setScript(script);
		template.setCacheable(cacheable);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);

		ddmTemplatePersistence.update(template);

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallImageFile,
			smallImageBytes);

		return template;
	}

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallImageFile,
			byte[] smallImageBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null)) {
				imageLocalService.updateImage(smallImageId, smallImageBytes);
			}
		}
		else {
			imageLocalService.deleteImage(smallImageId);
		}
	}

	protected void validate(
			long groupId, String templateKey, Map<Locale, String> nameMap,
			String script, boolean smallImage, String smallImageURL,
			File smallImageFile, byte[] smallImageBytes)
		throws PortalException, SystemException {

		templateKey = templateKey.trim().toUpperCase();

		DDMTemplate template = ddmTemplatePersistence.fetchByG_T(
			groupId, templateKey);

		if (template != null) {
			throw new TemplateDuplicateTemplateKeyException();
		}

		validate(
			nameMap, script, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);
	}

	protected void validate(
			Map<Locale, String> nameMap, String script, boolean smallImage,
			String smallImageURL, File smallImageFile, byte[] smallImageBytes)
		throws PortalException, SystemException {

		validateName(nameMap);

		if (Validator.isNull(script)) {
			throw new TemplateScriptException();
		}

		String[] imageExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS, StringPool.COMMA);

		if (smallImage && Validator.isNull(smallImageURL) &&
			(smallImageFile != null) && (smallImageBytes != null)) {

			String smallImageName = smallImageFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
						StringUtil.endsWith(
							smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new TemplateSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize = PrefsPropsUtil.getLong(
				PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE);

			if ((smallImageMaxSize > 0) &&
				((smallImageBytes == null) ||
				 (smallImageBytes.length > smallImageMaxSize))) {

				throw new TemplateSmallImageSizeException();
			}
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(name)) {
			throw new TemplateNameException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMTemplateLocalServiceImpl.class);

}