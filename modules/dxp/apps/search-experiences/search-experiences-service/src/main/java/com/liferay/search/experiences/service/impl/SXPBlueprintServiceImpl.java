/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.base.SXPBlueprintServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=sxp",
		"json.web.service.context.path=SXPBlueprint",
		"jsonws.web.service.parameter.type.whitelist.class.names=com.liferay.search.experiences.util.comparator.SXPBlueprintModifiedDateComparator",
		"jsonws.web.service.parameter.type.whitelist.class.names=com.liferay.search.experiences.util.comparator.SXPBlueprintTitleComparator"
	},
	service = AopService.class
)
public class SXPBlueprintServiceImpl extends SXPBlueprintServiceBaseImpl {

	@Override
	public SXPBlueprint addSXPBlueprint(
			String externalReferenceCode, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, SXPActionKeys.ADD_SXP_BLUEPRINT);

		return sxpBlueprintLocalService.addSXPBlueprint(
			externalReferenceCode, getUserId(), configurationJSON,
			descriptionMap, elementInstancesJSON, schemaVersion, titleMap,
			serviceContext);
	}

	@Override
	public SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprintId, ActionKeys.DELETE);

		return sxpBlueprintLocalService.deleteSXPBlueprint(sxpBlueprintId);
	}

	@Override
	public SXPBlueprint getSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		SXPBlueprint sxpBlueprint = sxpBlueprintLocalService.getSXPBlueprint(
			sxpBlueprintId);

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprint,
			SXPActionKeys.APPLY_SXP_BLUEPRINT);

		return sxpBlueprint;
	}

	@Override
	public SXPBlueprint getSXPBlueprintByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		SXPBlueprint sxpBlueprint =
			sxpBlueprintLocalService.getSXPBlueprintByExternalReferenceCode(
				companyId, externalReferenceCode);

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprint,
			SXPActionKeys.APPLY_SXP_BLUEPRINT);

		return sxpBlueprint;
	}

	@Override
	public SXPBlueprint updateSXPBlueprint(
			long sxpBlueprintId, String configurationJSON,
			Map<Locale, String> descriptionMap, String elementInstancesJSON,
			String schemaVersion, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprintId, ActionKeys.UPDATE);

		return sxpBlueprintLocalService.updateSXPBlueprint(
			getUserId(), sxpBlueprintId, configurationJSON, descriptionMap,
			elementInstancesJSON, schemaVersion, titleMap, serviceContext);
	}

	@Reference(target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")")
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.search.experiences.model.SXPBlueprint)"
	)
	private volatile ModelResourcePermission<SXPBlueprint>
		_sxpBlueprintModelResourcePermission;

}