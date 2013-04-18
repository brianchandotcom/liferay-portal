/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The Dynamic Data Mapping (DDM) Template service is responsible for
 * accessing, creating, modifying, and deleting templates.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 * @see    com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl
 */
public class DDMTemplateServiceImpl extends DDMTemplateServiceBaseImpl {

	/**
	 * Adds a template.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language. For more information,
	 *         see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  script the template's script
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the template.
	 * @return the template
	 * @throws PortalException if the user did not have permission to add the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate addTemplate(
			long groupId, long classNameId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		String ddmResourceActionId = getDDMResourceActionId(
			ddmResource, serviceContext);

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ddmResourceActionId);

		return ddmTemplateLocalService.addTemplate(
			getUserId(), groupId, classNameId, classPK, null, nameMap,
			descriptionMap, type, mode, language, script, false, false, null,
			null, serviceContext);
	}

	/**
	 * Adds a template with additional parameters.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  templateKey the unique string identifying the template
	 *         (optionally <code>null</code>)
	 * @param  nameMap the template's locales and localized names
	 * @param  descriptionMap the template's locales and localized descriptions
	 * @param  type the template's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language. For more information,
	 *         see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  script the template's script
	 * @param  cacheable whether the template is cacheable
	 * @param  smallImage whether the template has a small image
	 * @param  smallImageURL the template's small image URL (optionally
	 *         <code>null</code>)
	 * @param  smallImageFile the template's small image file (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the template.
	 * @return the template
	 * @throws PortalException if the user did not have permission to add the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate addTemplate(
			long groupId, long classNameId, long classPK, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		String ddmResourceActionId = getDDMResourceActionId(
			ddmResource, serviceContext);

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ddmResourceActionId);

		return ddmTemplateLocalService.addTemplate(
			getUserId(), groupId, classNameId, classPK, templateKey, nameMap,
			descriptionMap, type, mode, language, script, cacheable, smallImage,
			smallImageURL, smallImageFile, serviceContext);
	}

	/**
	 * Copies the template, creating a new template with all the values
	 * extracted from the original one. This method supports defining a new name
	 * and description.
	 *
	 * @param  templateId the primary key of the template to be copied
	 * @param  nameMap the new template's locales and localized names
	 * @param  descriptionMap the new template's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the template.
	 * @return the new template
	 * @throws PortalException if the user did not have permission to add the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate copyTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		String ddmResourceActionId = getDDMResourceActionId(
			ddmResource, serviceContext);

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ddmResourceActionId);

		return ddmTemplateLocalService.copyTemplate(
			getUserId(), templateId, nameMap, descriptionMap, serviceContext);
	}

	public DDMTemplate copyTemplate(
			long templateId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		String ddmResourceActionId = getDDMResourceActionId(
			ddmResource, serviceContext);

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ddmResourceActionId);

		return ddmTemplateLocalService.copyTemplate(
			getUserId(), templateId, serviceContext);
	}

	/**
	 * Copies all the templates matching the class name ID, class PK, and type.
	 * This method creates new templates, extracting all the values from the old
	 * ones and updating their class PKs.
	 *
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the original template's related entity
	 * @param  newClassPK the primary key of the new template's related entity
	 * @param  type the template's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  serviceContext the service context to be applied. Must have the
	 *         <code>ddmResource</code> attribute to check permissions. Can set
	 *         the UUID, creation date, modification date, guest permissions,
	 *         and group permissions for the template.
	 * @return the new template
	 * @throws PortalException if the user did not have permission to add the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> copyTemplates(
			long classNameId, long classPK, long newClassPK, String type,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = ParamUtil.getString(serviceContext, "ddmResource");

		String ddmResourceActionId = getDDMResourceActionId(
			ddmResource, serviceContext);

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ddmResourceActionId);

		return ddmTemplateLocalService.copyTemplates(
			getUserId(), classNameId, classPK, newClassPK, type,
			serviceContext);
	}

	/**
	 * Deletes the template and its resources.
	 *
	 * @param  templateId the primary key of the template to be deleted
	 * @throws PortalException if the user did not have permission to delete the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.DELETE);

		ddmTemplateLocalService.deleteTemplate(templateId);
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @return the matching template, or <code>null</code> if a matching
	 *         template could not be found
	 * @throws PortalException if the user did not have permission to view the
	 *         template
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate fetchTemplate(
			long groupId, long classNameId, String templateKey)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = ddmTemplateLocalService.fetchTemplate(
			groupId, classNameId, templateKey);

		if (ddmTemplate != null) {
			DDMTemplatePermission.check(
				getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);
		}

		return ddmTemplate;
	}

	/**
	 * Returns the template with the ID.
	 *
	 * @param  templateId the primary key of the template
	 * @return the template with the ID
	 * @throws PortalException if the user did not have permission to view the
	 *         template or if a matching template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

		return ddmTemplatePersistence.findByPrimaryKey(templateId);
	}

	/**
	 * Returns the template matching the group and template key.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @return the matching template
	 * @throws PortalException if a matching template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate getTemplate(
			long groupId, long classNameId, String templateKey)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getTemplate(
			groupId, classNameId, templateKey);

		DDMTemplatePermission.check(
			getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);

		return ddmTemplate;
	}

	/**
	 * Returns the template matching the group and template key, optionally in
	 * the global scope.
	 *
	 * <p>
	 * This method first searches in the group. If the template is still not
	 * found and <code>includeGlobalTemplates</code> is set to
	 * <code>true</code>, this method searches the global group.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  templateKey the unique string identifying the template
	 * @param  includeGlobalTemplates whether to include the global scope in the
	 *         search
	 * @return the matching template
	 * @throws PortalException if a matching template could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate getTemplate(
			long groupId, long classNameId, String templateKey,
			boolean includeGlobalTemplates)
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getTemplate(
			groupId, classNameId, templateKey, includeGlobalTemplates);

		DDMTemplatePermission.check(
			getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);

		return ddmTemplate;
	}

	/**
	 * Returns all the templates matching the group and class name ID.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @return the matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(long groupId, long classNameId)
		throws SystemException {

		return ddmTemplatePersistence.filterFindByG_C(groupId, classNameId);
	}

	/**
	 * Returns all the templates matching the group, class name ID, and class
	 * PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @return the matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK)
		throws SystemException {

		return ddmTemplatePersistence.filterFindByG_C_C(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns all the templates matching the class name ID, class PK, type, and
	 * mode.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  type the template's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @return the matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK, String type)
		throws SystemException {

		return ddmTemplatePersistence.filterFindByG_C_C_T(
			groupId, classNameId, classPK, type);
	}

	public List<DDMTemplate> getTemplates(
			long groupId, long classNameId, long classPK, String type,
			String mode)
		throws SystemException {

		return ddmTemplatePersistence.filterFindByG_C_C_T_M(
			groupId, classNameId, classPK, type, mode);
	}

	/**
	 * Returns all the templates matching the group and class PK.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classPK the primary key of the template's related entity
	 * @return the matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplatesByClassPK(long groupId, long classPK)
		throws SystemException {

		return ddmTemplatePersistence.filterFindByG_CPK(groupId, classPK);
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> getTemplatesByStructureClassNameId(
			long groupId, long structureClassNameId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.filterFindByG_SC(
			groupId, structureClassNameId, start, end, orderByComparator);
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
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>) For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.filterFindByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode,
			start, end, orderByComparator);
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
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long groupId, long classNameId, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.filterFindByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the templates matching the group IDs,
	 * class name IDs, class PK, type, and mode, and matching the keywords in
	 * the template names and descriptions.
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
	 * @param  classPK the primary key of the template's related entity
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.filterFindByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode,
			start, end, orderByComparator);
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
	 * @param  classPK the primary key of the template's related entity
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  andOperator whether every field must match its keywords, or just
	 *         one field.
	 * @param  start the lower bound of the range of templates to return
	 * @param  end the upper bound of the range of templates to return (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the templates
	 *         (optionally <code>null</code>)
	 * @return the matching templates ordered by the comparator
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMTemplate> search(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmTemplateFinder.filterFindByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPK, name, description, type,
			mode, language, andOperator, start, end, orderByComparator);
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, type, and mode, and matching the keywords in the template names and
	 * descriptions.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.filterCountByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode);
	}

	/**
	 * Returns the number of templates matching the group, class name ID, class
	 * PK, name keyword, description keyword, type, mode, and language.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupId the primary key of the group
	 * @param  classNameId the primary key of the class name for template's
	 *         related model
	 * @param  classPK the primary key of the template's related entity
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
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

		return ddmTemplateFinder.filterCountByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator);
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PK, type, and mode, and matching the keywords in the template names
	 * and descriptions.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instances the
	 *         templates are related to
	 * @param  classPK the primary key of the template's related entity
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         template's name or description (optionally <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @return the number of matching templates
	 * @throws SystemException if a system exception occurred
	 */
	public int searchCount(
			long companyId, long[] groupIds, long[] classNameIds, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.filterCountByKeywords(
			companyId, groupIds, classNameIds, classPK, keywords, type, mode);
	}

	/**
	 * Returns the number of templates matching the group IDs, class name IDs,
	 * class PK, name keyword, description keyword, type, mode, and language.
	 *
	 * @param  companyId the primary key of the template's company
	 * @param  groupIds the primary keys of the groups
	 * @param  classNameIds the primary keys of the entity's instances the
	 *         templates are related to
	 * @param  classPK the primary key of the template's related entity
	 * @param  name the name keywords (optionally <code>null</code>)
	 * @param  description the description keywords (optionally
	 *         <code>null</code>)
	 * @param  type the template's type (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode (optionally <code>null</code>). For more
	 *         information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language (optionally
	 *         <code>null</code>). For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
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

		return ddmTemplateFinder.filterCountByC_G_C_C_N_D_T_M_L(
			companyId, groupIds, classNameIds, classPK, name, description, type,
			mode, language, andOperator);
	}

	/**
	 * Updates the template matching the ID.
	 *
	 * @param  templateId the primary key of the template
	 * @param  nameMap the template's new locales and localized names
	 * @param  descriptionMap the template's new locales and localized
	 *         description
	 * @param  type the template's type. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  mode the template's mode. For more information, see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
	 * @param  language the template's script language. For more information,
	 *         see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}.
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
	 * @throws PortalException if the user did not have permission to update the
	 *         template or if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public DDMTemplate updateTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.UPDATE);

		return ddmTemplateLocalService.updateTemplate(
			templateId, nameMap, descriptionMap, type, mode, language, script,
			cacheable, smallImage, smallImageURL, smallImageFile,
			serviceContext);
	}

	protected String getDDMResourceActionId(
		String ddmResource, ServiceContext serviceContext) {

		String ddmResourceActionId = ParamUtil.getString(
			serviceContext, "ddmResourceActionId");

		if (Validator.isNull(ddmResourceActionId) &&
			ddmResource.equals(_DDL_CLASS_NAME)) {

			ddmResourceActionId = ActionKeys.ADD_TEMPLATE;
		}

		return ddmResourceActionId;
	}

	private static final String _DDL_CLASS_NAME =
		"com.liferay.portlet.dynamicdatalists";

}