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

package com.liferay.portal.search.tuning.blueprints.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsActionKeys;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsConstants;
import com.liferay.portal.search.tuning.blueprints.model.Element;
import com.liferay.portal.search.tuning.blueprints.service.ElementLocalService;
import com.liferay.portal.search.tuning.blueprints.service.base.ElementServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * The implementation of the element remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.portal.search.tuning.elements.service.ElementService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ElementServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=search",
		"json.web.service.context.path=Element"
	},
	service = AopService.class
)
public class ElementServiceImpl extends ElementServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use <code>com.liferay.portal.search.tuning.elements.service.ElementServiceUtil</code> to access the element remote service.
	 */
	public Element addCompanyElement(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, int type, ServiceContext serviceContext)
		throws PortalException {

		long groupId = _getCompanyGroupId(serviceContext);

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, BlueprintsActionKeys.ADD_ELEMENT);

		return elementLocalService.addElement(
			getUserId(), groupId, titleMap, descriptionMap, configuration, type,
			serviceContext);
	}

	public Element addGroupElement(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String configuration, int type, ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, BlueprintsActionKeys.ADD_ELEMENT);

		return elementLocalService.addElement(
			getUserId(), groupId, titleMap, descriptionMap, configuration, type,
			serviceContext);
	}

	public Element deleteElement(long elementId) throws PortalException {
		_elementModelResourcePermission.check(
			getPermissionChecker(), elementId, ActionKeys.DELETE);

		return elementLocalService.deleteElement(elementId);
	}

	public Element getElement(long elementId) throws PortalException {
		Element element = _elementLocalService.getElement(elementId);

		_elementModelResourcePermission.check(
			getPermissionChecker(), element, ActionKeys.VIEW);

		return element;
	}

	public List<Element> getGroupElements(
		long companyId, int type, int start, int end) {

		return getGroupElements(
			companyId, WorkflowConstants.STATUS_APPROVED, type, start, end);
	}

	public List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return elementPersistence.filterFindByG_T(
				companyId, type, start, end);
		}

		return elementPersistence.filterFindByG_S_T(
			companyId, status, type, start, end);
	}

	public List<Element> getGroupElements(
		long companyId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return elementPersistence.findByG_T(
				companyId, type, start, end, orderByComparator);
		}

		return elementPersistence.filterFindByG_S_T(
			companyId, status, type, start, end, orderByComparator);
	}

	public List<Element> getGroupElements(
		long companyId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getGroupElements(
			companyId, WorkflowConstants.STATUS_APPROVED, type, start, end,
			orderByComparator);
	}

	public int getGroupElementsCount(long companyId, int type) {
		return getGroupElementsCount(
			companyId, WorkflowConstants.STATUS_APPROVED, type);
	}

	public int getGroupElementsCount(long companyId, int status, int type) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return elementPersistence.countByG_T(companyId, type);
		}

		return elementPersistence.countByG_S_T(companyId, status, type);
	}

	public Element updateElement(
			long elementId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String configuration,
			ServiceContext serviceContext)
		throws PortalException {

		_elementModelResourcePermission.check(
			getPermissionChecker(), elementId, ActionKeys.UPDATE);

		return _elementLocalService.updateElement(
			getUserId(), elementId, titleMap, descriptionMap, configuration,
			serviceContext);
	}

	private long _getCompanyGroupId(ServiceContext serviceContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		return company.getGroupId();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ElementLocalService _elementLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.portal.search.tuning.blueprints.model.Element)"
	)
	private volatile ModelResourcePermission<Element>
		_elementModelResourcePermission;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(resource.name=" + BlueprintsConstants.RESOURCE_NAME + ")"
	)
	private volatile PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}