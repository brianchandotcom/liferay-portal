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

package com.liferay.commerce.price.list.qualification.type.web.internal.display.context;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.list.qualification.type.constants.CommercePriceListQualificationTypeConstants;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelService;
import com.liferay.commerce.price.list.qualification.type.web.internal.util.CommercePriceListQualificationTypeUtil;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.organizations.item.selector.OrganizationItemSelectorCriterion;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class OrganizationPriceListQualificationTypeDisplayContext
	extends BaseCommercePriceListDisplayContext<Organization> {

	public OrganizationPriceListQualificationTypeDisplayContext(
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommercePriceListUserRelService commercePriceListUserRelService,
		ItemSelector itemSelector, HttpServletRequest httpServletRequest,
		OrganizationLocalService organizationLocalService) {

		super(commercePriceListActionHelper, httpServletRequest);

		_commercePriceListUserRelService = commercePriceListUserRelService;
		_itemSelector = itemSelector;
		_organizationLocalService = organizationLocalService;

		setDefaultOrderByCol("name");
		setDefaultOrderByType("asc");
	}

	public CommercePriceListQualificationTypeRel
			getCommercePriceListQualificationTypeRel()
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel = null;

		CommercePriceList commercePriceList = getCommercePriceList();

		if (commercePriceList != null) {
			commercePriceListQualificationTypeRel =
				commercePriceList.fetchCommercePriceListQualificationTypeRel(
					CommercePriceListQualificationTypeConstants.
						QUALIFICATION_TYPE_ORGANIZATION);
		}

		return commercePriceListQualificationTypeRel;
	}

	public long getCommercePriceListQualificationTypeRelId()
		throws PortalException {

		CommercePriceListQualificationTypeRel
			commercePriceListQualificationTypeRel =
				getCommercePriceListQualificationTypeRel();

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel.
				getCommercePriceListQualificationTypeRelId();
		}

		return 0;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

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

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePriceList");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "organizations";
	}

	@Override
	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-organizations");

		OrderByComparator<Organization> orderByComparator =
			CommercePriceListQualificationTypeUtil.
				getOrganizationOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commercePriceListUserRelService.getCommercePriceListUserRelsCount(
				getCommercePriceListQualificationTypeRelId(),
				Organization.class.getName());

		searchContainer.setTotal(total);

		List<Organization> results = getOrganizations(
			searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		return searchContainer;
	}

	protected long[] getCheckedOrganizationIds() throws PortalException {
		List<Long> organizationIds = new ArrayList<>();

		List<Organization> organizations = getOrganizations(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Organization organization : organizations) {
			organizationIds.add(organization.getOrganizationId());
		}

		if (!organizationIds.isEmpty()) {
			return ArrayUtil.toLongArray(organizationIds);
		}

		return new long[0];
	}

	protected List<Organization> getOrganizations(int start, int end)
		throws PortalException {

		List<Organization> organizations = new ArrayList<>();

		List<CommercePriceListUserRel> commercePriceListUserRels =
			_commercePriceListUserRelService.getCommercePriceListUserRels(
				getCommercePriceListQualificationTypeRelId(),
				Organization.class.getName(), start, end);

		for (CommercePriceListUserRel commercePriceListUserRel :
				commercePriceListUserRels) {

			Organization organization =
				_organizationLocalService.fetchOrganization(
					commercePriceListUserRel.getClassPK());

			if (organization != null) {
				organizations.add(organization);
			}
		}

		return organizations;
	}

	private final CommercePriceListUserRelService
		_commercePriceListUserRelService;
	private final ItemSelector _itemSelector;
	private final OrganizationLocalService _organizationLocalService;

}