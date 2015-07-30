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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateVersionServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public class DDMTemplateVersionServiceImpl
	extends DDMTemplateVersionServiceBaseImpl {

	@Override
	public DDMTemplateVersion getLatestTemplateVersion(long templateId)
		throws PortalException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

		return ddmTemplateVersionLocalService.getLatestTemplateVersion(
			templateId);
	}

	@Override
	public DDMTemplateVersion getTemplateVersion(long templateVersionId)
		throws PortalException {

		DDMTemplateVersion templateVersion =
			ddmTemplateVersionLocalService.getTemplateVersion(
				templateVersionId);

		DDMTemplatePermission.check(
			getPermissionChecker(), templateVersion.getTemplateId(),
			ActionKeys.VIEW);

		return templateVersion;
	}

	@Override
	public List<DDMTemplateVersion> getTemplateVersions(
			long templateId, int start, int end,
			OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws PortalException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

		return ddmTemplateVersionLocalService.getTemplateVersions(
			templateId, start, end, orderByComparator);
	}

	@Override
	public int getTemplateVersionsCount(long templateId)
		throws PortalException {

		DDMTemplatePermission.check(
			getPermissionChecker(), templateId, ActionKeys.VIEW);

		return ddmTemplateVersionLocalService.getTemplateVersionsCount(
			templateId);
	}

}