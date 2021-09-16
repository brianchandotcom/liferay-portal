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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.base.SXPBlueprintServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=sxp",
		"json.web.service.context.path=SXPBlueprint",
		"jsonws.web.service.parameter.type.whitelist.class.names=com.liferay.search.experiences.util.comparator.SXPBlueprintModifiedDateComparator",
		"jsonws.web.service.parameter.type.whitelist.class.names=com.liferay.search.experiences.util.comparator.SXPBlueprintTitleComparator"
	},
	service = AopService.class
)
public class SXPBlueprintServiceImpl extends SXPBlueprintServiceBaseImpl {

	public SXPBlueprint addCompanySXPBlueprint(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configurationsJSON, String elementInstancesJSON,
			ServiceContext serviceContext)
		throws PortalException {

		long groupId = _getCompanyGroupId(serviceContext);

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, SXPActionKeys.ADD_SXP_BLUEPRINT);

		return sxpBlueprintLocalService.addSXPBlueprint(
			getUserId(), groupId, titleMap, descriptionMap, configurationsJSON,
			elementInstancesJSON, serviceContext);
	}

	public SXPBlueprint deleteSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprintId, ActionKeys.DELETE);

		return sxpBlueprintLocalService.deleteSXPBlueprint(sxpBlueprintId);
	}

	public SXPBlueprint getSXPBlueprint(long sxpBlueprintId)
		throws PortalException {

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.getSXPBlueprint(
			sxpBlueprintId);

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprint,
			SXPActionKeys.APPLY_SXP_BLUEPRINT);

		return sxpBlueprint;
	}

	public SXPBlueprint updateSXPBlueprint(
			long sxpBlueprintId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configurationsJSON,
			String elementInstancesJSON, ServiceContext serviceContext)
		throws PortalException {

		_sxpBlueprintModelResourcePermission.check(
			getPermissionChecker(), sxpBlueprintId, ActionKeys.UPDATE);

		return _sxpBlueprintLocalService.updateSXPBlueprint(
			getUserId(), sxpBlueprintId, titleMap, descriptionMap,
			configurationsJSON, elementInstancesJSON, serviceContext);
	}

	private long _getCompanyGroupId(ServiceContext serviceContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		return company.getGroupId();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.search.experiences.model.Blueprint)"
	)
	private volatile ModelResourcePermission<SXPBlueprint>
		_sxpBlueprintModelResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}