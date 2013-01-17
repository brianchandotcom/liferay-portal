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
 * The DDM Template local service is responsible for accessing, creating,
 * modifying and deleting templates.
 *
 * For more information on ddm structures, see
 * {@link
 * com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateLocalServiceImpl}.
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMTemplateServiceImpl extends DDMTemplateServiceBaseImpl {

	/**
	 * Adds a template
	 *
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
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
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
	 * Adds a template
	 *
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
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
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
	 * Copies a template: creates a new template extracting all the values from
	 * the original one. Supports defining the new name and description.
	 *
	 * @param  templateId the primary key of the template to be copied
	 * @param  nameMap the new template's locales and localized names
	 * @param  descriptionMap the new template's locales and localized
	 *         descriptions
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
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
	 * Copies a list of templates with matching class Name IDs, class PKs and
	 * type. It creates new templates extracting all the values from the
	 * original ones and updating their class PKs to new class PK.
	 *
	 * @param  classNameId the primary key of the entity's class the template is
	 *         related to
	 * @param  classPK the primary key of entity the original template is
	 *         related to
	 * @param  newClassPK the primary key of entity the new templates will be
	 *         related to
	 * @param  type the template's type. For more information see {@link
	 *         com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants}
	 * @param  serviceContext the service context to be applied. Must have the
	 *         ddmResource attribute to check permissions. Can set the UUID,
	 *         creation date, modification date, guest permissions and group
	 *         permissions for the template.
	 * @return the template
	 * @throws PortalException if a portal exception occurred
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
	 * Deletes a template and its resources.
	 *
	 * @param  templateId the primary key of the template that will be deleted
	 * @throws PortalException if a portal exception occurred
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.DELETE);

		ddmTemplateLocalService.deleteTemplate(templateId);
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
		throws PortalException, SystemException {

		DDMTemplate ddmTemplate = ddmTemplateLocalService.fetchTemplate(
			groupId, templateKey);

		if (ddmTemplate != null) {
			DDMTemplatePermission.check(
				getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);
		}

		return ddmTemplate;
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

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

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

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getTemplate(
			groupId, templateKey);

		DDMTemplatePermission.check(
			getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);

		return ddmTemplate;
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

		DDMTemplate ddmTemplate = ddmTemplateLocalService.getTemplate(
			groupId, templateKey, includeGlobalTemplates);

		DDMTemplatePermission.check(
			getPermissionChecker(), ddmTemplate, ActionKeys.VIEW);

		return ddmTemplate;
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

		return ddmTemplatePersistence.filterFindByG_C(groupId, classNameId);
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

		return ddmTemplatePersistence.filterFindByG_C_C(
			groupId, classNameId, classPK);
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

		return ddmTemplateFinder.filterFindByKeywords(
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

		return ddmTemplateFinder.filterFindByC_G_C_C_N_D_T_M_L(
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

		return ddmTemplateFinder.filterFindByKeywords(
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

		return ddmTemplateFinder.filterFindByC_G_C_C_N_D_T_M_L(
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

		return ddmTemplateFinder.filterCountByKeywords(
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

		return ddmTemplateFinder.filterCountByC_G_C_C_N_D_T_M_L(
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

		return ddmTemplateFinder.filterCountByKeywords(
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

		return ddmTemplateFinder.filterCountByC_G_C_C_N_D_T_M_L(
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

		if (Validator.isNull(ddmResourceActionId)) {
			if (ddmResource.equals(_DDL_CLASS_NAME)) {
				ddmResourceActionId = ActionKeys.ADD_TEMPLATE;
			}
			else {
				ddmResourceActionId = ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE;
			}
		}

		return ddmResourceActionId;
	}

	private static final String _DDL_CLASS_NAME =
		"com.liferay.portlet.dynamicdatalists";

}