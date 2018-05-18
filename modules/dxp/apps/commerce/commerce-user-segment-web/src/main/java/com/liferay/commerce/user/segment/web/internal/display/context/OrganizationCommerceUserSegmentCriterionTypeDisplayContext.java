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

package com.liferay.commerce.user.segment.web.internal.display.context;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeJSPContributorRegistry;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class OrganizationCommerceUserSegmentCriterionTypeDisplayContext
	extends CommerceUserSegmentDisplayContext {

	public OrganizationCommerceUserSegmentCriterionTypeDisplayContext(
		CommerceUserSegmentCriterionService commerceUserSegmentCriterionService,
		CommerceUserSegmentCriterionTypeJSPContributorRegistry
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
		CommerceUserSegmentCriterionTypeRegistry
			commerceUserSegmentCriterionTypeRegistry,
		CommerceUserSegmentEntryService commerceUserSegmentEntryService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector,
		OrganizationLocalService organizationLocalService) {

		super(
			commerceUserSegmentCriterionService,
			commerceUserSegmentCriterionTypeJSPContributorRegistry,
			commerceUserSegmentCriterionTypeRegistry,
			commerceUserSegmentEntryService, httpServletRequest);

		_itemSelector = itemSelector;
		_organizationLocalService = organizationLocalService;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				commerceUserSegmentRequestHelper.getRequest());

		OrganizationItemSelectorCriterion organizationItemSelectorCriterion =
			new OrganizationItemSelectorCriterion();

		organizationItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "organizationsSelectItem",
			organizationItemSelectorCriterion);

		String checkedOrganizationIds = StringUtil.merge(
			getCheckedOrganizationIds());

		itemSelectorURL.setParameter(
			"checkedOrganizationIds", checkedOrganizationIds);

		return itemSelectorURL.toString();
	}

	public List<Organization> getOrganizations() throws PortalException {
		List<Organization> organizations = new ArrayList<>();

		CommerceUserSegmentCriterion commerceUserSegmentCriterion =
			getCommerceUserSegmentCriterion();

		if (commerceUserSegmentCriterion == null) {
			return organizations;
		}

		String[] organizationIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String organizationId : organizationIds) {
			Organization organization =
				_organizationLocalService.fetchOrganization(
					GetterUtil.getLong(organizationId));

			if (organization != null) {
				organizations.add(organization);
			}
		}

		return organizations;
	}

	protected long[] getCheckedOrganizationIds() throws PortalException {
		return ListUtil.toLongArray(
			getOrganizations(), Organization.ORGANIZATION_ID_ACCESSOR);
	}

	private final ItemSelector _itemSelector;
	private final OrganizationLocalService _organizationLocalService;

}