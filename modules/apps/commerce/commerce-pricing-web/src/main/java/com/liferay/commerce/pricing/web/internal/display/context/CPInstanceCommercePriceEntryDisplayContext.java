/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.item.selector.criterion.CommercePriceListItemSelectorCriterion;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures()
		throws PortalException {

		CPInstance cpInstance = getCPInstance();

		return cpInstance.getCPInstanceUnitOfMeasures(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = new CreationMenu();

		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			getCPInstanceUnitOfMeasures();

		if (cpInstanceUnitOfMeasures.isEmpty()) {
			CPInstance cpInstance = getCPInstance();

			return creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						liferayPortletResponse.getNamespace() +
							"addCommercePriceEntry");
					dropdownItem.setLabel(
						LanguageUtil.format(
							httpServletRequest, "add-x-to-price-list",
							HtmlUtil.escape(cpInstance.getSku()), false));
					dropdownItem.setTarget("event");
				});
		}

		for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
				cpInstanceUnitOfMeasures) {

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						StringBundler.concat(
							liferayPortletResponse.getNamespace(),
							"addCommercePriceEntry",
							HtmlUtil.escapeJS(
								cpInstanceUnitOfMeasure.getKey())));
					dropdownItem.setLabel(
						LanguageUtil.format(
							httpServletRequest, "add-x-to-price-list",
							HtmlUtil.escape(cpInstanceUnitOfMeasure.getKey()),
							false));
					dropdownItem.setTarget("event");
				});
		}

		return creationMenu;
	}

	public String getItemSelectorUrl(String unitOfMeasureKey)
		throws PortalException {

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
			StringUtil.merge(_getCheckedCommercePriceListIds(unitOfMeasureKey))
		).buildString();
	}

	public HashMap<String, Object> getJSContext() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		List<String> eventNames = new ArrayList<>();
		List<String> keys = new ArrayList<>();
		List<String> titles = new ArrayList<>();
		List<String> urls = new ArrayList<>();

		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			getCPInstanceUnitOfMeasures();

		if (cpInstanceUnitOfMeasures.isEmpty()) {
			eventNames.add("addCommercePriceEntry");
			keys.add(StringPool.BLANK);
			titles.add(
				LanguageUtil.format(
					httpServletRequest, "add-x-to-price-list",
					HtmlUtil.escape(cpInstance.getSku()), false));
			urls.add(getItemSelectorUrl(null));
		}
		else {
			for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
					cpInstanceUnitOfMeasures) {

				String key = cpInstanceUnitOfMeasure.getKey();

				eventNames.add(
					"addCommercePriceEntry" + HtmlUtil.escapeJS(key));
				keys.add(key);
				titles.add(
					LanguageUtil.format(
						httpServletRequest, "add-x-to-price-list",
						HtmlUtil.escape(cpInstance.getSku() + " " + key),
						false));
				urls.add(getItemSelectorUrl(key));
			}
		}

		return HashMapBuilder.<String, Object>put(
			"eventNames", eventNames.toArray(new String[0])
		).put(
			"keys", keys.toArray(new String[0])
		).put(
			"titles", titles.toArray(new String[0])
		).put(
			"urls", urls.toArray(new String[0])
		).build();
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

	private long[] _getCheckedCommercePriceListIds(String unitOfMeasureKey)
		throws PortalException {

		List<Long> commercePriceListIds = new ArrayList<>();

		List<CommercePriceEntry> commercePriceEntries =
			_commercePriceEntryService.getInstanceCommercePriceEntries(
				getCPInstanceId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			if (Validator.isNotNull(commercePriceEntry.getUnitOfMeasureKey()) &&
				Validator.isNotNull(unitOfMeasureKey) &&
				!Objects.equals(
					unitOfMeasureKey,
					commercePriceEntry.getUnitOfMeasureKey())) {

				continue;
			}

			commercePriceListIds.add(
				commercePriceEntry.getCommercePriceListId());
		}

		if (!commercePriceListIds.isEmpty()) {
			return ArrayUtil.toLongArray(commercePriceListIds);
		}

		return new long[0];
	}

	private final CommercePriceEntryService _commercePriceEntryService;
	private final CommercePriceListActionHelper _commercePriceListActionHelper;
	private CPInstance _cpInstance;
	private final ItemSelector _itemSelector;

}