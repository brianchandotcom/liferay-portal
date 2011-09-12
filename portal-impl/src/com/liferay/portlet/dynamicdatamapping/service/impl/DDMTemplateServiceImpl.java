/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 */
public class DDMTemplateServiceImpl extends DDMTemplateServiceBaseImpl {

	public DDMTemplate addTemplate(
			long groupId, long structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String language,
			String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_TEMPLATE);

		return ddmTemplateLocalService.addTemplate(
			getUserId(), groupId, structureId, nameMap, descriptionMap, type,
			language, script, serviceContext);
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

	public List<DDMTemplate> getTemplates(long structureId, String type)
		throws SystemException {

		return ddmTemplatePersistence.findByS_T(structureId, type);
	}

	public DDMTemplate updateTemplate(
			long templateId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String type, String language,
			String script, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.UPDATE);

		return ddmTemplateLocalService.updateTemplate(
			templateId, nameMap, descriptionMap, type, language, script,
			serviceContext);
	}

}