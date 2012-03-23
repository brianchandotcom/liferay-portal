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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMPermission;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMTemplateServiceImpl extends DDMTemplateServiceBaseImpl {

	public DDMTemplate addTemplate(
			long groupId, long classNameId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = GetterUtil.getString(
			serviceContext.getAttribute("ddmResource"));

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_TEMPLATE);

		return ddmTemplateLocalService.addTemplate(
			getUserId(), groupId, classNameId, classPK, nameMap, descriptionMap,
			type, mode, language, script, serviceContext);
	}

	public List<DDMTemplate> copyTemplates(
			long classNameId, long classPK, long newClassPK, String type,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String ddmResource = GetterUtil.getString(
			serviceContext.getAttribute("ddmResource"));

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ddmResource, ActionKeys.ADD_TEMPLATE);

		return ddmTemplateLocalService.copyTemplates(
			getUserId(), classNameId, classPK, newClassPK, type,
			serviceContext);
	}

	public void deleteTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.DELETE);

		ddmTemplateLocalService.deleteTemplate(templateId);
	}

	public DDMTemplate getTemplate(long templateId)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

		return ddmTemplateLocalService.getTemplate(templateId);
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

		return ddmTemplateFinder.filterFindByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode,
			start, end, orderByComparator);
	}

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

	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String keywords, String type, String mode)
		throws SystemException {

		return ddmTemplateFinder.filterCountByKeywords(
			companyId, groupId, classNameId, classPK, keywords, type, mode);
	}

	public int searchCount(
			long companyId, long groupId, long classNameId, long classPK,
			String name, String description, String type, String mode,
			String language, boolean andOperator)
		throws SystemException {

		return ddmTemplateFinder.filterCountByC_G_C_C_N_D_T_M_L(
			companyId, groupId, classNameId, classPK, name, description, type,
			mode, language, andOperator);
	}

	public DDMTemplate updateTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String mode,
			String language, String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.UPDATE);

		return ddmTemplateLocalService.updateTemplate(
			templateId, nameMap, descriptionMap, type, mode, language, script,
			serviceContext);
	}

}