/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.configuration.DDMServiceConfigurationKeys;
import com.liferay.dynamic.data.mapping.configuration.DDMServiceConfigurationUtil;
import com.liferay.dynamic.data.mapping.exception.InvalidTemplateVersionException;
import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.exception.RequiredTemplateException;
import com.liferay.dynamic.data.mapping.exception.TemplateDuplicateTemplateKeyException;
import com.liferay.dynamic.data.mapping.exception.TemplateNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateScriptException;
import com.liferay.dynamic.data.mapping.exception.TemplateSmallImageNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateSmallImageSizeException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMTemplateLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.service.permission.DDMTemplatePermission;
import com.liferay.dynamic.data.mapping.util.DDMXMLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.permission.ModelPermissions;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.xml.XMLUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, copying, deleting, and
 * updating dynamic data mapping (DDM) templates.
 *
 * <p>
 * DDM templates (templates) are used in Liferay to render templated content
 * like applications, forms, dynamic data lists, or web contents.
 * </p>
 *
 * <p>
 * Templates support a variety of templating languages, like Velocity or
 * FreeMarker. They also support multi-language names and descriptions.
 * </p>
 *
 * <p>
 * Templates can be related to many models in Liferay, such as those for
 * structures, dynamic data lists, and applications. This relationship can be
 * established via the class name ID and class PK.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMTemplateLocalServiceImpl
	extends DDMTemplateLocalServiceBaseImpl {

	/**
	 * Adds a template.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  mode the template's mode. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  language the template's script language. For more information,
	 *         see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  script the template's script
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the template.
	 * @return the template
	 */
	@Override
	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, ServiceContext serviceContext)
		throws PortalException {

		return addTemplate(
			userId, groupId, classNameId, classPK, resourceClassNameId, null,
			nameMap, descriptionMap, type, mode, language, script, false, false,
			null, null, serviceContext);
	}

	/**
	 * Adds a template with additional parameters.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  templateKey the unique string identifying the template
	 *         (optionally <code>null</code>)
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  mode the template's mode. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  language the template's script language. For more information,
	 *         see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  smallImage whether the template has a small image
	 * @param  smallImageURL the template's small image URL (optionally
	 *         <code>null</code>)
	 * @param  smallImageFile the template's small image file (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the template.
	 * @return the template
	 */
	@Override
	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		// Template

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(templateKey)) {
			templateKey = String.valueOf(counterLocalService.increment());
		}
		else {
			templateKey = StringUtil.toUpperCase(templateKey.trim());
		}

		script = formatScript(type, language, script);

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
			groupId, classNameId, templateKey, nameMap, script, smallImage,
			smallImageURL, smallImageFile, smallImageBytes);

		long templateId = counterLocalService.increment();

		DDMTemplate template = ddmTemplatePersistence.create(templateId);

		template.setUuid(serviceContext.getUuid());
		template.setGroupId(groupId);
		template.setCompanyId(user.getCompanyId());
		template.setUserId(user.getUserId());
		template.setUserName(user.getFullName());
		template.setVersionUserId(user.getUserId());
		template.setVersionUserName(user.getFullName());
		template.setClassNameId(classNameId);
		template.setClassPK(classPK);
		template.setResourceClassNameId(resourceClassNameId);
		template.setTemplateKey(templateKey);
		template.setVersion(DDMTemplateConstants.VERSION_DEFAULT);
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
				template, serviceContext.getModelPermissions());
		}

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Template version

		addTemplateVersion(
			user, template, DDMTemplateConstants.VERSION_DEFAULT,
			serviceContext);

		return template;
	}

	/**
	 * Adds the resources to the template.
	 *
	 * @param template the template to add resources to
	 * @param addGroupPermissions whether to add group permissions
	 * @param addGuestPermissions whether to add guest permissions
	 */
	@Override
	public void addTemplateResources(
			DDMTemplate template, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		String resourceName =
			DDMTemplatePermission.getTemplateModelResourceName(
				template.getResourceClassNameId());

		resourceLocalService.addResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), resourceName, template.getTemplateId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the template.
	 *
	 * @param template the template to add resources to
	 * @param modelPermissions the model permissions to be added
	 */
	@Override
	public void addTemplateResources(
			DDMTemplate template, ModelPermissions modelPermissions)
		throws PortalException {

		String resourceName =
			DDMTemplatePermission.getTemplateModelResourceName(
				template.getResourceClassNameId());

		resourceLocalService.addModelResources(
			template.getCompanyId(), template.getGroupId(),
			template.getUserId(), resourceName, template.getTemplateId(),
			modelPermissions);
	}

	/**
	 * Copies the template, creating a new template with all the values
	 * extracted from the original one. This method supports defining a new name
	 * and description.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  templateId the primary key of the template to be copied
	 * @param  nameMap the new template's locales and localized names
	 * @param  descriptionMap the new template's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, guest permissions, and
	 *         group permissions for the template.
	 * @return the new template
	 */
	@Override
	public DDMTemplate copyTemplate(
			long userId, long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		return copyTemplate(
			userId, template, template.getClassPK(), nameMap, descriptionMap,
			serviceContext);
	}

	@Override
	public DDMTemplate copyTemplate(
			long userId, long templateId, ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		return copyTemplate(
			userId, template, template.getClassPK(), template.getNameMap(),
			template.getDescriptionMap(), serviceContext);
	}

	/**
	 * Copies all the templates matching the class name ID, class PK, and type.
	 * This method creates new templates, extracting all the values from the old
	 * ones and updating their class PKs.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  oldClassPK the primary key of the old template's related entity
	 * @param  newClassPK the primary key of the new template's related entity
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  serviceContext the service context to be applied. Can set the
	 *         creation date, modification date, guest permissions, and group
	 *         permissions for the new templates.
	 * @return the new templates
	 */
	@Override
	public List<DDMTemplate> copyTemplates(
			long userId, long classNameId, long oldClassPK, long newClassPK,
			String type, ServiceContext serviceContext)
		throws PortalException {

		List<DDMTemplate> newTemplates = new ArrayList<>();

		List<DDMTemplate> oldTemplates = ddmTemplatePersistence.findByC_C_T(
			classNameId, oldClassPK, type);

		for (DDMTemplate oldTemplate : oldTemplates) {
			DDMTemplate newTemplate = copyTemplate(
				userId, oldTemplate, newClassPK, oldTemplate.getNameMap(),
				oldTemplate.getDescriptionMap(), serviceContext);

			newTemplates.add(newTemplate);
		}

		return newTemplates;
	}

	/**
	 * Deletes the template and its resources.
	 *
	 * @param template the template to be deleted
	 */
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteTemplate(DDMTemplate template) throws PortalException {

		// Template

		if (ddmTemplateLinkPersistence.countByTemplateId(
				template.getTemplateId()) > 0) {

			throw new RequiredTemplateException.
				MustNotDeleteTemplateReferencedByTemplateLinks(
					template.getTemplateId());
		}

		ddmTemplatePersistence.remove(template);

		// Resources

		resourceLocalService.deleteResource(
			template.getCompanyId(), DDMTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, template.getTemplateId());
	}

	/**
	 * Deletes the template and its resources.
	 *
	 * @param templateId the primary key of the template to be deleted
	 */
	@Override
	public void deleteTemplate(long templateId) throws PortalException {
		DDMTemplate template = ddmTemplatePersistence.findByPrimaryKey(
			templateId);

		ddmTemplateLocalService.deleteTemplate(template);
	}

	/**
	 * Deletes all the templates of the group.
	 *
	 * @param groupId the primary key of the group
	 */
	@Override
	public void deleteTemplates(long groupId) throws PortalException {
		List<DDMTemplate> templates = ddmTemplatePersistence.findByGroupId(
			groupId);

		for (DDMTemplate template : templates) {
			ddmTemplateLocalService.deleteTemplate(template);
		}
	}

	@Override
	public void deleteTemplates(long groupId, long classNameId)
		throws PortalException {

		List<DDMTemplate> templates = ddmTemplatePersistence.findByG_C(
			groupId, classNameId);

		for (DDMTemplate template : templates) {
			ddmTemplateLocalService.deleteTemplate(template);
		}
	}

	/**
	 * Returns the template with the ID.
	 *
	 * @param  templateId the primary key of the template
	 * @return the matching template, or <code>null</code> if a matching
	 *         template could not be found
	 */
	@Override
	public DDMTemplate fetchTemplate(long templateId) {
		return ddmTemplatePersistence.fetchByPrimaryKey(templateId);
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @return the matching template, or <code>null</code> if a matching
	 *         template could not be found
	 */
	@Override
	public DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey) {

		templateKey = StringUtil.toUpperCase(templateKey.trim());

		return ddmTemplatePersistence.fetchByG_C_T(
			groupId, classNameId, templateKey);
	}

	/**
	 * Returns the template matching the group and template key, optionally
	 * searching ancestor sites (that have sharing enabled) and global scoped
	 * sites.
	 *
	 * <p>
	 * This method first searches in the given group. If the template is still
	 * not found and <code>includeAncestorTemplates</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @param  includeAncestorTemplates whether to include ancestor sites (that
	 *         have sharing enabled) and include global scoped sites in the
	 *         search in the search
	 * @return the matching template, or <code>null</code> if a matching
	 *         template could not be found
	 */
	@Override
	public DDMTemplate fetchTemplate(
			long groupId, long classNameId, String templateKey,
			boolean includeAncestorTemplates)
		throws PortalException {

		templateKey = StringUtil.toUpperCase(templateKey.trim());

		DDMTemplate template = ddmTemplatePersistence.fetchByG_C_T(
			groupId, classNameId, templateKey);

		if (template != null) {
			return template;
		}

		if (!includeAncestorTemplates) {
			return null;
		}

		for (long ancestorSiteGroupId :
				PortalUtil.getAncestorSiteGroupIds(groupId)) {

			template = ddmTemplatePersistence.fetchByG_C_T(
				ancestorSiteGroupId, classNameId, templateKey);

			if (template != null) {
				return template;
			}
		}

		return null;
	}

	/**
	 * Returns the template with the ID.
	 *
	 * @param  templateId the primary key of the template
	 * @return the template with the ID
	 */
	@Override
	public DDMTemplate getTemplate(long templateId) throws PortalException {
		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @return the matching template
	 */
	@Override
	public DDMTemplate getTemplate(
			long groupId, long classNameId, String templateKey)
		throws PortalException {

		templateKey = StringUtil.toUpperCase(templateKey.trim());

		return ddmTemplatePersistence.findByG_C_T(
			groupId, classNameId, templateKey);
	}

	/**
	 * Returns the template matching the group and template key, optionally
	 * searching ancestor sites (that have sharing enabled) and global scoped
	 * sites.
	 *
	 * <p>
	 * This method first searches in the group. If the template is still not
	 * found and <code>includeAncestorTemplates</code> is set to
	 * <code>true</code>, this method searches the group's ancestor sites (that
	 * have sharing enabled) and lastly searches global scoped sites.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @param  includeAncestorTemplates whether to include ancestor sites (that
	 *         have sharing enabled) and include global scoped sites in the
	 *         search in the search
	 * @return the matching template
	 */
	@Override
	public DDMTemplate getTemplate(
			long groupId, long classNameId, String templateKey,
			boolean includeAncestorTemplates)
		throws PortalException {

		templateKey = StringUtil.toUpperCase(templateKey.trim());

		DDMTemplate template = ddmTemplatePersistence.fetchByG_C_T(
			groupId, classNameId, templateKey);

		if (template != null) {
			return template;
		}

		if (!includeAncestorTemplates) {
			throw new NoSuchTemplateException(
				"No DDMTemplate exists with the template key " + templateKey);
		}

		for (long ancestorSiteGroupId :
				PortalUtil.getAncestorSiteGroupIds(groupId)) {

			template = ddmTemplatePersistence.fetchByG_C_T(
				ancestorSiteGroupId, classNameId, templateKey);

			if (template != null) {
				return template;
			}
		}

		throw new NoSuchTemplateException(
			"No DDMTemplate exists with the template key " + templateKey +
				" in the ancestor groups");
	}

	@Override
	public DDMTemplate getTemplateBySmallImageId(long smallImageId)
		throws PortalException {

		return ddmTemplatePersistence.findBySmallImageId(smallImageId);
	}

	/**
	 * Returns all the templates with the class PK.
	 *
	 * @param  classPK the primary key of the template's related entity
	 * @return the templates with the class PK
	 */
	@Override
	public List<DDMTemplate> getTemplates(long classPK) {
		return ddmTemplatePersistence.findByClassPK(classPK);
	}

	/**
	 * Returns all the templates matching the group and class name ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @return the matching templates
	 */
	@Override
	public List<DDMTemplate> getTemplates(long groupId, long classNameId) {
		return ddmTemplatePersistence.findByG_C(groupId, classNameId);
	}

	/**
	 * Returns all the templates matching the group, class name ID, and class
	 * PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @return the matching templates
	 */
	@Override
	public List<DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK) {

		return ddmTemplatePersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK,
			boolean includeAncestorTemplates)
		throws PortalException {

		List<DDMTemplate> ddmTemplates = new ArrayList<>();

		ddmTemplates.addAll(
			ddmTemplatePersistence.findByG_C_C(groupId, classNameId, classPK));

		if (!includeAncestorTemplates) {
			return ddmTemplates;
		}

		ddmTemplates.addAll(
			ddmTemplatePersistence.findByG_C_C(
				PortalUtil.getAncestorSiteGroupIds(groupId), classNameId,
				classPK));

		return ddmTemplates;
	}

	/**
	 * Returns all the templates matching the group, class name ID, class PK,
	 * and type.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @return the matching templates
	 */
	@Override
	public List<DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, String type) {

		return ddmTemplatePersistence.findByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

	/**
	 * Returns all the templates matching the group, class name ID, class PK,
	 * type, and mode.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  mode the template's mode. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @return the matching templates
	 */
	@Override
	public List<DDMTemplate> getTemplates(
		long groupId, long classNameId, long classPK, String type,
		String mode) {

		return ddmTemplatePersistence.findByG_C_C_T_M(
			groupId, classNameId, classPK, type, mode);
	}

	@Override
	public List<DDMTemplate> getTemplates(
		long[] groupIds, long classNameId, long classPK) {

		return ddmTemplatePersistence.findByG_C_C(
			groupIds, classNameId, classPK);
	}

	@Override
	public List<DDMTemplate> getTemplatesByClassPK(long groupId, long classPK) {
		return ddmTemplatePersistence.findByG_CPK(groupId, classPK);
	}

	@Override
	public List<DDMTemplate> getTemplatesByClassPK(
		long groupId, long classPK, int start, int end) {

		return ddmTemplatePersistence.findByG_CPK(groupId, classPK, start, end);
	}

	@Override
	public List<DDMTemplate> getTemplatesByClassPK(
		long[] groupIds, long classPK) {

		return ddmTemplatePersistence.findByG_CPK(groupIds, classPK);
	}

	/**
	 * Returns the number of templates matching the group and class PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classPK the primary key of the template's related entity
	 * @return the number of templates belonging to the group and class PK
	 */
	@Override
	public int getTemplatesByClassPKCount(long groupId, long classPK) {
		return ddmTemplatePersistence.countByG_CPK(groupId, classPK);
	}

	/**
	 * Returns an ordered range of all the templates matching the group and
	 * structure class name ID.
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
	 * @param  groupId the primary key of the group
	 * @param  structureClassNameId the primary key of the class name for the
	 *         template's related structure
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	@Override
	public List<DDMTemplate> getTemplatesByStructureClassNameId(
		long groupId, long structureClassNameId, int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		return ddmTemplateFinder.findByG_SC_S(
			groupId, structureClassNameId, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of templates matching the group and structure class
	 * name ID, including Generic Templates.
	 *
	 * @param  groupId the primary key of the group
	 * @param  structureClassNameId the primary key of the class name for the
	 *         template's related structure
	 * @return the number of matching templates
	 */
	@Override
	public int getTemplatesByStructureClassNameIdCount(
		long groupId, long structureClassNameId, int status) {

		return ddmTemplateFinder.countByG_SC_S(
			groupId, structureClassNameId, status);
	}

	/**
	 * Returns the number of templates belonging to the group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the number of templates belonging to the group
	 */
	@Override
	public int getTemplatesCount(long groupId) {
		return ddmTemplatePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of templates matching the group and class name ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @return the number of matching templates
	 */
	@Override
	public int getTemplatesCount(long groupId, long classNameId) {
		return ddmTemplatePersistence.countByG_C(groupId, classNameId);
	}

	/**
	 * Returns the number of templates matching the group, class name ID, and
	 * class PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @return the number of matching templates
	 */
	@Override
	public int getTemplatesCount(long groupId, long classNameId, long classPK) {
		return ddmTemplatePersistence.countByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public void revertTemplate(
			long userId, long templateId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		DDMTemplateVersion templateVersion =
			ddmTemplateVersionLocalService.getTemplateVersion(
				templateId, version);

		if (!templateVersion.isApproved()) {
			throw new InvalidTemplateVersionException(
				"Unable to revert from an unapproved template version");
		}

		DDMTemplate template = templateVersion.getTemplate();

		serviceContext.setAttribute("majorVersion", Boolean.TRUE);
		serviceContext.setAttribute(
			"status", WorkflowConstants.STATUS_APPROVED);
		serviceContext.setCommand(Constants.REVERT);

		ddmTemplateLocalService.updateTemplate(
			userId, templateId, templateVersion.getClassPK(),
			templateVersion.getNameMap(), templateVersion.getDescriptionMap(),
			template.getType(), template.getMode(),
			templateVersion.getLanguage(), templateVersion.getScript(),
			template.getCacheable(), serviceContext);
	}

	/**
	 * Returns an ordered range of all the templates matching the group, class
	 * name ID, class PK, type, and mode, and matching the keywords in the
	 * template names and descriptions.
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
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	@Override
	public List<DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			keywords, type, mode, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates matching the group, class
	 * name ID, class PK, name keyword, description keyword, type, mode, and
	 * language.
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
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	@Override
	public List<DDMTemplate> search(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		return ddmTemplateFinder.findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId, name,
			description, type, mode, language, status, andOperator, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates matching the group IDs,
	 * class Name IDs, class PK, type, and mode, and include the keywords on its
	 * names and descriptions.
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
	 * @param  companyId the primary key of the template's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instances the
	 *         templates are related to
	 * @param  classPKs the primary keys of the template's related entities
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	@Override
	public List<DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status, int start, int end,
		OrderByComparator<DDMTemplate> orderByComparator) {

		return ddmTemplateFinder.findByKeywords(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			keywords, type, mode, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates matching the group IDs,
	 * class name IDs, class PK, name keyword, description keyword, type, mode,
	 * and language.
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
	 * @param  companyId the primary key of the template's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instances the
	 *         templates are related to
	 * @param  classPKs the primary keys of the template's related entities
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the range of matching templates ordered by the comparator
	 */
	@Override
	public List<DDMTemplate> search(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator,
		int start, int end, OrderByComparator<DDMTemplate> orderByComparator) {

		return ddmTemplateFinder.findByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			name, description, type, mode, language, status, andOperator, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, type, and matching the keywords in the template names and
	 * descriptions.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @return the number of matching templates
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupId, classNameId, classPK, resourceClassNameId,
			keywords, type, mode, status);
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, name keyword, description keyword, type, mode, and language.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for the template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching templates
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, long classNameId, long classPK,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		return ddmTemplateFinder.countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupId, classNameId, classPK, resourceClassNameId, name,
			description, type, mode, language, status, andOperator);
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PK, type, and mode, and matching the keywords in the template names
	 * and descriptions.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instance the
	 *         templates are related to
	 * @param  classPKs the primary keys of the template's related entities
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @return the number of matching templates
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String keywords, String type, String mode,
		int status) {

		return ddmTemplateFinder.countByKeywords(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			keywords, type, mode, status);
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PKs, name keyword, description keyword, type, mode, and language.
	 *
	 * @param  companyId the primary key of the templates company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instance the
	 *         templates are related to
	 * @param  classPKs the primary keys of the template's related entities
	 * @param  resourceClassNameId the primary key of the class name for
	 *         template's resource model
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see DDMTemplateConstants in the
	 *         dynamic-data-mapping-api module.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @return the number of matching templates
	 */
	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, long[] classPKs,
		long resourceClassNameId, String name, String description, String type,
		String mode, String language, int status, boolean andOperator) {

		return ddmTemplateFinder.countByC_G_C_C_R_N_D_T_M_L_S(
			companyId, groupIds, classNameIds, classPKs, resourceClassNameId,
			name, description, type, mode, language, status, andOperator);
	}

	/**
	 * Updates the template matching the ID.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  templateId the primary key of the template
	 * @param  classPK the primary key of the template's related entity
	 * @param  nameMap the template's new locales and localized names
	 * @param  descriptionMap the template's new locales and localized
	 *         description
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  mode the template's mode. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  language the template's script language. For more information,
	 *         see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  smallImage whether the template has a small image
	 * @param  smallImageURL the template's small image URL (optionally
	 *         <code>null</code>)
	 * @param  smallImageFile the template's small image file (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date.
	 * @return the updated template
	 */
	@Override
	public DDMTemplate updateTemplate(
			long userId, long templateId, long classPK, Map<Locale,
			String> nameMap, Map<Locale, String> descriptionMap, String type,
			String mode, String language, String script, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		script = formatScript(type, language, script);

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

		if ((template.getClassPK() == 0) && (classPK > 0)) {

			// Allow users to set the structure if and only if it currently does
			// not have one. Otherwise, you can have bad data because there may
			// be an existing content that has chosen to use a structure and
			// template combination that no longer exists.

			template.setClassPK(classPK);
		}

		DDMTemplateVersion latestTemplateVersion =
			ddmTemplateVersionLocalService.getLatestTemplateVersion(templateId);

		boolean majorVersion = GetterUtil.getBoolean(
			serviceContext.getAttribute("majorVersion"));

		String version = getNextVersion(
			latestTemplateVersion.getVersion(), majorVersion);

		template.setVersion(version);
		template.setVersionUserId(user.getUserId());
		template.setVersionUserName(user.getFullName());
		template.setNameMap(nameMap);
		template.setDescriptionMap(descriptionMap);
		template.setType(type);
		template.setMode(mode);
		template.setLanguage(language);
		template.setScript(script);
		template.setCacheable(cacheable);
		template.setSmallImage(smallImage);
		template.setSmallImageURL(smallImageURL);

		// Small image

		saveImages(
			smallImage, template.getSmallImageId(), smallImageFile,
			smallImageBytes);

		// Template version

		DDMTemplateVersion ddmTemplateVersion = addTemplateVersion(
			user, template, version, serviceContext);

		if (ddmTemplateVersion.isApproved()) {
			ddmTemplatePersistence.update(template);
		}

		return template;
	}

	/**
	 * Updates the template matching the ID.
	 *
	 * @param  userId the primary key of the template's creator/owner
	 * @param  templateId the primary key of the template
	 * @param  classPK the primary key of the template's related entity
	 * @param  nameMap the template's new locales and localized names
	 * @param  descriptionMap the template's new locales and localized
	 *         description
	 * @param  type the template's type. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  mode the template's mode. For more information, see
	 *         DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  language the template's script language. For more information,
	 *         see DDMTemplateConstants in the dynamic-data-mapping-api module.
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date.
	 * @return the updated template
	 */
	@Override
	public DDMTemplate updateTemplate(
			long userId, long templateId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, ServiceContext serviceContext)
		throws PortalException {

		DDMTemplate template = ddmTemplateLocalService.getDDMTemplate(
			templateId);

		File smallImageFile = getSmallImageFile(template);

		return updateTemplate(
			userId, templateId, classPK, nameMap, descriptionMap, type, mode,
			language, script, cacheable, template.isSmallImage(),
			template.getSmallImageURL(), smallImageFile, serviceContext);
	}

	protected DDMTemplateVersion addTemplateVersion(
		User user, DDMTemplate template, String version,
		ServiceContext serviceContext) {

		long templateVersionId = counterLocalService.increment();

		DDMTemplateVersion templateVersion =
			ddmTemplateVersionPersistence.create(templateVersionId);

		templateVersion.setGroupId(template.getGroupId());
		templateVersion.setCompanyId(template.getCompanyId());
		templateVersion.setUserId(template.getUserId());
		templateVersion.setUserName(template.getUserName());
		templateVersion.setCreateDate(template.getModifiedDate());
		templateVersion.setClassNameId(template.getClassNameId());
		templateVersion.setClassPK(template.getClassPK());
		templateVersion.setTemplateId(template.getTemplateId());
		templateVersion.setVersion(version);
		templateVersion.setName(template.getName());
		templateVersion.setDescription(template.getDescription());
		templateVersion.setLanguage(template.getLanguage());
		templateVersion.setScript(template.getScript());

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		templateVersion.setStatus(status);

		templateVersion.setStatusByUserId(user.getUserId());
		templateVersion.setStatusByUserName(user.getFullName());
		templateVersion.setStatusDate(template.getModifiedDate());

		ddmTemplateVersionPersistence.update(templateVersion);

		return templateVersion;
	}

	protected DDMTemplate copyTemplate(
			long userId, DDMTemplate template, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		File smallImageFile = getSmallImageFile(template);

		return addTemplate(
			userId, template.getGroupId(), template.getClassNameId(), classPK,
			template.getResourceClassNameId(), null, nameMap, descriptionMap,
			template.getType(), template.getMode(), template.getLanguage(),
			template.getScript(), template.isCacheable(),
			template.isSmallImage(), template.getSmallImageURL(),
			smallImageFile, serviceContext);
	}

	protected String formatScript(String type, String language, String script)
		throws PortalException {

		if (language.equals(TemplateConstants.LANG_TYPE_XSL)) {
			try {
				script = DDMXMLUtil.validateXML(script);
			}
			catch (PortalException pe) {
				throw new TemplateScriptException(pe);
			}

			script = XMLUtil.formatXML(script);
		}

		return script;
	}

	protected String getNextVersion(String version, boolean majorVersion) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected File getSmallImageFile(DDMTemplate template) {
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
		throws PortalException {

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
			long groupId, long classNameId, String templateKey,
			Map<Locale, String> nameMap, String script, boolean smallImage,
			String smallImageURL, File smallImageFile, byte[] smallImageBytes)
		throws PortalException {

		templateKey = StringUtil.toUpperCase(templateKey.trim());

		DDMTemplate template = ddmTemplatePersistence.fetchByG_C_T(
			groupId, classNameId, templateKey);

		if (template != null) {
			throw new TemplateDuplicateTemplateKeyException(
				"Template already exists with template key " + templateKey);
		}

		validate(
			nameMap, script, smallImage, smallImageURL, smallImageFile,
			smallImageBytes);
	}

	protected void validate(Map<Locale, String> nameMap, String script)
		throws PortalException {

		validateName(nameMap);

		if (Validator.isNull(script)) {
			throw new TemplateScriptException("Script is null");
		}
	}

	protected void validate(
			Map<Locale, String> nameMap, String script, boolean smallImage,
			String smallImageURL, File smallImageFile, byte[] smallImageBytes)
		throws PortalException {

		validate(nameMap, script);

		String[] imageExtensions = DDMServiceConfigurationUtil.getArray(
			DDMServiceConfigurationKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS);

		if (!smallImage || Validator.isNotNull(smallImageURL) ||
			(smallImageFile == null) || (smallImageBytes == null)) {

			return;
		}

		String smallImageName = smallImageFile.getName();

		boolean validSmallImageExtension = false;

		for (String imageExtension : imageExtensions) {
			if (StringPool.STAR.equals(imageExtension) ||
				StringUtil.endsWith(
					smallImageName, imageExtension)) {

				validSmallImageExtension = true;

				break;
			}
		}

		if (!validSmallImageExtension) {
			throw new TemplateSmallImageNameException(smallImageName);
		}

		long smallImageMaxSize = GetterUtil.getLong(
			DDMServiceConfigurationUtil.get(
				DDMServiceConfigurationKeys.
					DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE));

		if ((smallImageMaxSize > 0) &&
			(smallImageBytes.length > smallImageMaxSize)) {

			throw new TemplateSmallImageSizeException(
				"Image " + smallImageName + " has " + smallImageBytes.length +
					" bytes and exceeds the maximum size of " +
						smallImageMaxSize);
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getSiteDefault());

		if (Validator.isNull(name)) {
			throw new TemplateNameException("Name is null");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMTemplateLocalServiceImpl.class);

}