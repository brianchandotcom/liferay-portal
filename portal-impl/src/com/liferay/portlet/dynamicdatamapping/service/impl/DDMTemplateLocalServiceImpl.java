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
 * The DDM Template local service is responsible for accessing, creating,
 * modifying and deleting templates.
 *
 * <p>
 * Templates are used in Liferay to render templated content like web contents,
 * dynamic data lists, applications or forms.
 * </p>
 *
 * <p>
 * Templates can be written in a variety of templating languages, like Velocity
 * or Freemarker. They can have multi-language name and description.
 * </p>
 *
 * <p>
 * Templates can be related to many models in the portal, for instance
 * Structures, Dynamic Data Lists or Applications. This relationship can be
 * defined using the classNameId - classPK pattern.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMTemplateLocalServiceImpl
	extends DDMTemplateLocalServiceBaseImpl {

	/**
	 * Adds a template
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @param  classPK the primary key of entity the template is related to
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language. For more information see
	 *         {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  script the template's script
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions and
	 *         group permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Adds a template
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @param  classPK the primary key of entity the template is related to
	 * @param  templateKey unique string identifying the template (optionally
	 *         <code>null</code>)
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language. For more information see
	 *         {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  smallImage whether the template has a small image
	 * @param  smallImageURL the template's small image URL (optionally
	 *         <code>null</code>)
	 * @param  smallImageFile the template's small image file (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions and
	 *         group permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Adds the resources of a template
	 *
	 * @param  template the template we are adding resources to
	 * @param  addGroupPermissions set to <code>true</code> to add group
	 *         permissions
	 * @param  addGuestPermissions set to <code>true</code> to add guest
	 *         permissions
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Adds the resources of a template
	 *
	 * @param  template the template we are adding resources to
	 * @param  groupPermissions group permissions to be added
	 * @param  guestPermissions guest permissions to be added
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void addTemplateResources(
			DDMTemplate template, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), DDMTemplate.class.getName(),
			template.getTemplateId(), groupPermissions, guestPermissions);
	}

	/**
	 * Copies a template: creates a new template extracting all the values from
	 * the original one. Supports defining the new name and description.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  templateId the primary key of the template to be copied
	 * @param  nameMap the new template's locales and localized names
	 * @param  descriptionMap the new template's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions and
	 *         group permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate copyTemplate(
			long userId, long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		File smallImageFile = copySmallImage(template);

		return addTemplate(
			userId, template.getGroupId(), template.getClassNameId(),
			template.getClassPK(), null, nameMap, descriptionMap,
			template.getType(), template.getMode(), template.getLanguage(),
			template.getScript(), template.isCacheable(),
			template.isSmallImage(), template.getSmallImageURL(),
			smallImageFile, serviceContext);
	}

	public DDMTemplate copyTemplate(
			long userId, long templateId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		File smallImageFile = copySmallImage(template);

		return addTemplate(
			userId, template.getGroupId(), template.getClassNameId(),
			template.getClassPK(), null, template.getNameMap(),
			template.getDescriptionMap(), template.getType(),
			template.getMode(), template.getLanguage(), template.getScript(),
			template.isCacheable(), template.isSmallImage(),
			template.getSmallImageURL(), smallImageFile, serviceContext);
	}

	/**
	 * Copies a list of templates with matching class Name IDs, class PKs and
	 * type. It creates new templates extracting all the values from the
	 * original ones and updating their class PKs to new class PK.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  classNameId the primary key of the entity's class the template is
	 *         related to
	 * @param  oldClassPK the primary key of entity the original template is
	 *         related to
	 * @param  newClassPK the primary key of entity the new templates will be
	 *         related to
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  serviceContext the service context to be applied. Can set the
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the new templates.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> copyTemplates(
			long userId, long classNameId, long oldClassPK, long newClassPK,
			String type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<DDMTemplate> newTemplates = new ArrayList<DDMTemplate>();

		List<DDMTemplate> oldTemplates = ddmTemplatePersistence.findByC_C_T(
			classNameId, oldClassPK, type);

		for (DDMTemplate oldTemplate : oldTemplates) {
			DDMTemplate newTemplate = copyTemplate(
				userId, oldTemplate.getTemplateId(), serviceContext);

			newTemplates.add(newTemplate);
		}

		return newTemplates;
	}

	/**
	 * Deletes a template and its resources.
	 *
	 * @param  template the template that will be deleted
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTemplate(DDMTemplate template)
		throws PortalException, SystemException {

		// Template

		ddmTemplatePersistence.remove(template);

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), DDMTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, template.getTemplateId());
	}

	/**
	 * Deletes a template and its resources.
	 *
	 * @param  templateId the primary key of the template that will be deleted
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		deleteTemplate(template);
	}

	/**
	 * Deletes all the templates of a group
	 *
	 * @param  groupId the primary key of the group
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTemplates(long groupId)
		throws PortalException, SystemException {

		List<DDMTemplate> templates = ddmTemplatePersistence.findByGroupId(
			groupId);

		for (DDMTemplate template : templates) {
			deleteTemplate(template);
		}
	}

	/**
	 * Returns the template with the matching templateKey that belongs to the
	 * group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  templateKey unique string identifying the template
	 * @return the template with the templateKey, or <code>null</code> if a
	 *         matching template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchTemplate(long groupId, String templateKey)
		throws SystemException {

		templateKey = templateKey.trim().toUpperCase();

		return ddmTemplatePersistence.fetchByG_T(groupId, templateKey);
	}

	/**
	 * Returns the template with the matching templateKey in a given group and
	 * optionally in the global scope.
	 *
	 * This method first searches in the given group and in case the template is
	 * not found and includeGlobalTemplates is set to <code>true</code>, then
	 * searches the
	 * global group.
	 *
	 * @param  groupId the primary key of the group
	 * @param  templateKey unique string identifying the template
	 * @param  includeGlobalTemplates whether to include the global scope in the
	 *         search
	 * @return the template or <code>null</code> if a matching template could
	 *         not be found
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchTemplate(
			long groupId, String templateKey, boolean includeGlobalTemplates)
		throws PortalException, SystemException {

		templateKey = templateKey.trim().toUpperCase();

		DDMTemplate template = ddmTemplatePersistence.fetchByG_T(
			groupId, templateKey);

		if ((template != null) || !includeGlobalTemplates) {
			return template;
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Group companyGroup = groupLocalService.getCompanyGroup(
			group.getCompanyId());

		return ddmTemplatePersistence.fetchByG_T(
			companyGroup.getGroupId(), templateKey);
	}

	/**
	 * Returns the template with the matching UUID that belongs to the group.
	 *
	 * @param  uuid unique string identifying the template
	 * @param  groupId the primary key of the group
	 * @return the template with the templateKey, or <code>null</code> if a
	 *         matching template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchTemplate(String uuid, long groupId)
		throws SystemException {

		return ddmTemplatePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a template that has a matching primary key
	 *
	 * @param  templateId the primary key of the template
	 * @return the matching template
	 * @throws PortalException if the template was not found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	/**
	 * Returns a template that has a matching template key in a given group
	 *
	 * @param  groupId the primary key of the template's group
	 * @param  templateKey unique string identifying the template
	 * @return the matching template
	 * @throws PortalException if the template was not found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate getTemplate(long groupId, String templateKey)
		throws PortalException, SystemException {

		templateKey = templateKey.trim().toUpperCase();

		return ddmTemplatePersistence.findByG_T(groupId, templateKey);
	}

	/**
	 * Returns a template that has a matching template key in a given group and
	 * optionally in the global scope.
	 *
	 * This method first searches in the give group and in case the template is
	 * not found and includeGlobalTemplates is set to <code>true</code>, then
	 * searches the global group.
	 *
	 * @param  groupId the primary key of the template's group
	 * @param  templateKey unique string identifying the template
	 * @param  includeGlobalTemplates whether to include the global scope in the
	 *         search
	 * @return the matching template
	 * @throws PortalException if the template was not found
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns a list with all the templates that matches the given classPK
	 *
	 * @param  classPK the primary key of entity the templates are related to
	 * @return the list of all matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(long classPK) throws SystemException {
		return ddmTemplatePersistence.findByClassPK(classPK);
	}

	/**
	 * Returns a list of templates that has a matching classNameId in a given
	 * group
	 *
	 * @param  groupId the primary key of the template's group
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @return a list of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(long groupId, long classNameId)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C(groupId, classNameId);
	}

	/**
	 * Returns a list of templates that has a matching classNameId and classPK
	 * in a given group
	 *
	 * @param  groupId the primary key of the template's group
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @return a list of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns a list of templates that has a matching classNameId, classPK and
	 * type
	 *
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @return a list of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK, String type)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

	/**
	 * Returns a list of templates that has a matching classNameId, classPK,
	 * type and mode
	 *
	 * @param  classNameId the primary key of the entity's class this template
	 *         is related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @return a list of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK, String type,
			String mode)
		throws SystemException {

		return ddmTemplatePersistence.findByG_C_C_T_M(
			groupId, classNameId, classPK, type, mode);
	}

	/**
	 * Returns the number of templates that belong to the group
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of templates that belong to the group
	 * @throws SystemException if a system exception occurred
	 */
	public int getTemplatesCount(long groupId) throws SystemException {
		return ddmTemplatePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of templates that belong to the group and matches the
	 * classNameId
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the entity's class the templates
	 *         are related to
	 * @return the number of templates that belong to the group and matches the
	 *         classNameId
	 * @throws SystemException if a system exception occurred
	 */
	public int getTemplatesCount(long groupId, long classNameId)
		throws SystemException {

		return ddmTemplatePersistence.countByG_C(groupId, classNameId);
	}

	/**
	 * Returns an ordered range of all the templates belonging to the company
	 * and group that matches the classNameId, classPk type, mode and include
	 * the keywords on its names or descriptions
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupId the primary keys of the group
	 * @param  classNameId the primary key of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name, or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  start the lower bound of the range of templates
	 * @param  end the upper bound of the range of templates (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by comparator
	 *         <code>orderByComparator</code>
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode,
			start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates belonging to the company
	 * and group that matches the classNameId, classPk, type, mode and language
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupId the primary keys of the group
	 * @param  classNameId the primary key of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  name the template's name (optionally <code>null</code>)
	 * @param  description the template's description (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language (optionally
	 *         <code>null</code>) For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of templates
	 * @param  end the upper bound of the range of templates (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by comparator
	 *         <code>orderByComparator</code>
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns an ordered range of all the templates belonging to the company
	 * that matches the groupIds, classNameIds, classPk, type, mode and include
	 * the keywords on its names or descriptions
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name, or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  start the lower bound of the range of templates
	 * @param  end the upper bound of the range of templates (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by comparator
	 *         <code>orderByComparator</code>
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode,
			start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates belonging to the company
	 * that matches the groupIds, classNameIds, classPk, name, description,
	 * type, mode and language
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  name the template's name (optionally <code>null</code>)
	 * @param  description the template's description (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language (optionally
	 *         <code>null</code>) For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of templates
	 * @param  end the upper bound of the range of templates (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by comparator
	 *         <code>orderByComparator</code>
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Returns the number of templates belonging to the company and group that
	 * matches the classNameId, classPk, type, mode and include the keywords on
	 * its names or descriptions
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary keys of the group
	 * @param  classNameId the primary key of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name, or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode);
	}

	/**
	 * Returns the number of templates belonging to the company and group that
	 * matches the classNameId, classPk, name, description, type, mode and
	 * language
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupId the primary keys of the group
	 * @param  classNameId the primary key of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  name the template's name (optionally <code>null</code>)
	 * @param  description the template's description (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language (optionally
	 *         <code>null</code>) For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator);
	}

	/**
	 * Returns the number of templates belonging to the company that matches the
	 * groupIds, classNameIds, classPk, type, mode and include the keywords on
	 * its names or descriptions
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name, or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode);
	}

	/**
	 * Returns the number of templates belonging to the company that matches the
	 * groupIds, classNameIds, classPks, name, description, type, mode and
	 * language
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's class the templates
	 *         are related to
	 * @param  classPK the primary key of entity the templates are related to
	 * @param  name the template's name (optionally <code>null</code>)
	 * @param  description the template's description (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language (optionally
	 *         <code>null</code>) For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.countByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPK, name, description, type,
			mode, language, andOperator);
	}

	/**
	 * Updates the template matching the templateId
	 *
	 * @param  templateId the primary key of the template
	 * @param  nameMap the template's new locales and localized names
	 * @param  descriptionMap the template's new locales and localized
	 *         description
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  mode the template's mode. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  language the template's script language. For more information see
	 *         {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  smallImage whether the template has a small image
	 * @param  smallImageURL the template's small image URL (optionally
	 *         <code>null</code>)
	 * @param  smallImageFile the template's small image file (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date
	 * @return the updated template
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
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

	protected File copySmallImage(DDMTemplate template) throws SystemException {
		File smallImageFile = null;

		if (template.isSmallImage() &&
			Validator.isNull(template.getSmallImageURL())) {

			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			if (smallImage != null) {
				smallImageFile = FileUtil.createTempFile(smallImage.getType());

				try {
					FileUtil.write(smallImageFile, smallImage.getTextObj());
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		return smallImageFile;
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