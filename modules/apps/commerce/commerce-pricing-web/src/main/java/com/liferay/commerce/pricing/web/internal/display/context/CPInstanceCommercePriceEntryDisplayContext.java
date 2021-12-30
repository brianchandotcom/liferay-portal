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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommercePriceListItemSelectorCriterion;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceCommercePriceEntryDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPInstanceCommercePriceEntryDisplayContext(
		ActionHelper actionHelper,
		CommercePriceEntryService commercePriceEntryService,
		CommercePriceListActionHelper commercePriceListActionHelper,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_commercePriceEntryService = commercePriceEntryService;
		_commercePriceListActionHelper = commercePriceListActionHelper;
		_itemSelector = itemSelector;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		return _commercePriceListActionHelper.getCommercePriceEntry(
			cpRequestHelper.getRenderRequest());
	}

	public long getCommercePriceEntryId() throws PortalException {
		long commercePriceEntryId = 0;

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
		}

		return commercePriceEntryId;
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		long cpInstanceId = 0;

		CPInstance cpInstance = getCPInstance();

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return cpInstanceId;
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				CPInstance cpInstance = getCPInstance();

				dropdownItem.setHref(
					liferayPortletResponse.getNamespace() +
						"addCommercePriceEntry");
				dropdownItem.setLabel(
					LanguageUtil.format(
						httpServletRequest, "add-x-to-price-list",
						HtmlUtil.escape(cpInstance.getSku()), false));
				dropdownItem.setTarget("event");
			}
		).build();
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest);

		CommercePriceListItemSelectorCriterion
			commercePriceListItemSelectorCriterion =
				new CommercePriceListItemSelectorCriterion();

		commercePriceListItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "priceListsSelectItem",
				commercePriceListItemSelectorCriterion)
		).setParameter(
			"checkedCommercePriceListIds",
			StringUtil.merge(_getCheckedCommercePriceListIds())
		).buildString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		return PortletURLBuilder.create(
			super.getPortletURL()
		).setMVCRenderCommandName(
			"/cp_definitions/edit_cp_instance"
		).setParameter(
			"cpInstanceId", getCPInstanceId()
		).setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey()
		).setParameter(
			"screenNavigationEntryKey", "price-lists"
		).buildPortletURL();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "price-lists";
	}

	private long[] _getCheckedCommercePriceListIds() throws PortalException {
		List<Long> commercePriceListIds = new ArrayList<>();

		List<CommercePriceEntry> commercePriceEntries =
			_getCommercePriceEntries();

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			commercePriceListIds.add(
				commercePriceEntry.getCommercePriceListId());
		}

		if (!commercePriceListIds.isEmpty()) {
			return ArrayUtil.toLongArray(commercePriceListIds);
		}

		return new long[0];
	}

	private List<CommercePriceEntry> _getCommercePriceEntries()
		throws PortalException {

		return _commercePriceEntryService.getInstanceCommercePriceEntries(
			getCPInstanceId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private final CommercePriceEntryService _commercePriceEntryService;
	private final CommercePriceListActionHelper _commercePriceListActionHelper;
	private CPInstance _cpInstance;
	private final ItemSelector _itemSelector;

}