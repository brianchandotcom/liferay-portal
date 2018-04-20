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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentCriterionTypeOrganizationDisplayContext
	extends CommerceUserSegmentDisplayContext {

	public CommerceUserSegmentCriterionTypeOrganizationDisplayContext(
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
			commerceUserSegmentCriterion.getTypeSettings(), StringPool.COMMA);

		for (String organizationId : organizationIds) {
			Organization organization =
				_organizationLocalService.fetchOrganization(
					Long.parseLong(organizationId));

			if (organization != null) {
				organizations.add(organization);
			}
		}

		return organizations;
	}

	protected long[] getCheckedOrganizationIds() throws PortalException {
		List<Long> organizationIds = new ArrayList<>();

		List<Organization> organizations = getOrganizations();

		for (Organization organization : organizations) {
			organizationIds.add(organization.getOrganizationId());
		}

		if (!organizationIds.isEmpty()) {
			return ArrayUtil.toLongArray(organizationIds);
		}

		return new long[0];
	}

	private final ItemSelector _itemSelector;
	private final OrganizationLocalService _organizationLocalService;

}