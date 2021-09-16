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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.search.experiences.constants.SXPActionKeys;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;
import com.liferay.search.experiences.service.base.SXPElementServiceBaseImpl;

import java.util.List;
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
		"json.web.service.context.path=SXPElement"
	},
	service = AopService.class
)
public class SXPElementServiceImpl extends SXPElementServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.search.experiences.sxpElements.service.SXPElementServiceUtil</code> to access the sxpElement remote service.
	 */
	@Override
	public SXPElement addCompanySXPElement(
			Map<Locale, String> descriptionMap, String elementDefinitionJSON,
			Map<Locale, String> titleMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		long groupId = _getCompanyGroupId(serviceContext);

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, SXPActionKeys.ADD_SXP_ELEMENT);

		return sxpElementLocalService.addSXPElement(
			getUserId(), groupId, descriptionMap, elementDefinitionJSON,
			titleMap, type, serviceContext);
	}

	@Override
	public SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException {

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElementId, ActionKeys.DELETE);

		return sxpElementLocalService.deleteSXPElement(sxpElementId);
	}

	@Override
	public List<SXPElement> getGroupSXPElements(
		long companyId, int type, int start, int end) {

		return getGroupSXPElements(
			companyId, WorkflowConstants.STATUS_APPROVED, type, start, end);
	}

	@Override
	public List<SXPElement> getGroupSXPElements(
		long groupId, int status, int type, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return sxpElementPersistence.filterFindByG_T(
				groupId, type, start, end);
		}

		return sxpElementPersistence.filterFindByG_T_S(
			groupId, type, status, start, end);
	}

	@Override
	public int getGroupSXPElementsCount(long companyId, int status, int type) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return sxpElementPersistence.countByG_T(companyId, type);
		}

		return sxpElementPersistence.countByG_S_T(companyId, status, type);
	}

	@Override
	public SXPElement getSXPElement(long sxpElementId) throws PortalException {
		SXPElement sxpElement = _sxpElementLocalService.getSXPElement(
			sxpElementId);

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElement, ActionKeys.VIEW);

		return sxpElement;
	}

	@Override
	public SXPElement updateSXPElement(
			long sxpElementId, Map<Locale, String> descriptionMap,
			String elementDefinitionJSON, boolean hidden,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		_sxpElementModelResourcePermission.check(
			getPermissionChecker(), sxpElementId, ActionKeys.UPDATE);

		return _sxpElementLocalService.updateSXPElement(
			getUserId(), sxpElementId, descriptionMap, elementDefinitionJSON,
			hidden, titleMap, serviceContext);
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
	private SXPElementLocalService _sxpElementLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.search.experiences.model.SXPElement)"
	)
	private volatile ModelResourcePermission<SXPElement>
		_sxpElementModelResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}