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

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionSerializer;
import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.constants.ItemSelectorPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
@Component(service = ItemSelector.class)
public class ItemSelectorImpl implements ItemSelector {

	public static final String JSON = "_json";

	public static final String PARAMETER_CRITERIA = "criteria";

	public static final String PARAMETER_ITEM_SELECTED_EVENT_NAME =
		"itemSelectedEventName";

	public static final String PARAMETER_SELECTED_TAB = "selectedTab";

	@Override
	public String getItemSelectedEventName(String itemSelectorURL) {
		String namespace = _portal.getPortletNamespace(
			ItemSelectorPortletKeys.ITEM_SELECTOR);

		return _http.getParameter(
			itemSelectorURL,
			namespace.concat(PARAMETER_ITEM_SELECTED_EVENT_NAME), false);
	}

	@Override
	public List<ItemSelectorCriterion> getItemSelectorCriteria(
		Map<String, String[]> parameters) {

		List<Class<? extends ItemSelectorCriterion>>
			itemSelectorCriterionClasses = getItemSelectorCriterionClasses(
				parameters);

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>(
			itemSelectorCriterionClasses.size());

		for (int i = 0; i < itemSelectorCriterionClasses.size(); i++) {
			Class<? extends ItemSelectorCriterion> itemSelectorCriterionClass =
				itemSelectorCriterionClasses.get(i);

			String countValue = String.valueOf(i);

			String json = parameters.get(countValue.concat(JSON))[0];

			itemSelectorCriteria.add(
				_itemSelectionCriterionSerializer.deserialize(
					itemSelectorCriterionClass, json));
		}

		return itemSelectorCriteria;
	}

	@Override
	public List<ItemSelectorCriterion> getItemSelectorCriteria(
		String itemSelectorURL) {

		Map<String, String[]> parameters = _http.getParameterMap(
			itemSelectorURL);

		Map<String, String[]> itemSelectorURLParameterMap = new HashMap<>();

		String namespace = _portal.getPortletNamespace(
			ItemSelectorPortletKeys.ITEM_SELECTOR);

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String parameterName = entry.getKey();

			if (parameterName.contains(namespace)) {
				String key = StringUtil.removeSubstring(
					parameterName, namespace);

				itemSelectorURLParameterMap.put(key, entry.getValue());
			}
		}

		return getItemSelectorCriteria(itemSelectorURLParameterMap);
	}

	@Override
	public ItemSelectorRendering getItemSelectorRendering(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		Map<String, String[]> parameters, ThemeDisplay themeDisplay) {

		String itemSelectedEventName = getValue(
			parameters, PARAMETER_ITEM_SELECTED_EVENT_NAME);
		String selectedTab = getValue(parameters, PARAMETER_SELECTED_TAB);

		List<ItemSelectorViewRenderer> itemSelectorViewRenderers =
			new ArrayList<>();

		List<ItemSelectorCriterion> itemSelectorCriteria =
			getItemSelectorCriteria(parameters);

		ItemSelectorCriterion[] itemSelectorCriteriaArray =
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]);

		for (ItemSelectorCriterion itemSelectorCriterion :
				itemSelectorCriteria) {

			Class<? extends ItemSelectorCriterion> itemSelectorCriterionClass =
				itemSelectorCriterion.getClass();

			ItemSelectorCriterionHandler<ItemSelectorCriterion>
				itemSelectorCriterionHandler =
					_itemSelectionCriterionHandlers.get(
						itemSelectorCriterionClass.getName());

			List<ItemSelectorView<ItemSelectorCriterion>> itemSelectorViews =
				itemSelectorCriterionHandler.getItemSelectorViews(
					itemSelectorCriterion);

			for (ItemSelectorView<ItemSelectorCriterion> itemSelectorView :
					itemSelectorViews) {

				if (!itemSelectorView.isVisible(themeDisplay)) {
					continue;
				}

				PortletURL portletURL = getPortletURL(
					requestBackedPortletURLFactory,
					itemSelectorView.getTitle(themeDisplay.getLocale()),
					selectedTab, itemSelectedEventName,
					itemSelectorCriteriaArray, themeDisplay);

				itemSelectorViewRenderers.add(
					new ItemSelectorViewRendererImpl(
						itemSelectorView, itemSelectorCriterion, portletURL,
						itemSelectedEventName, isSearch(parameters)));
			}
		}

		return new ItemSelectorRenderingImpl(
			itemSelectedEventName, selectedTab, itemSelectorViewRenderers);
	}

	@Override
	public PortletURL getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		Group group, long refererGroupId, String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria) {

		PortletURL portletURL =
			requestBackedPortletURLFactory.createControlPanelRenderURL(
				ItemSelectorPortletKeys.ITEM_SELECTOR, group, refererGroupId,
				0);

		try {
			portletURL.setPortletMode(PortletMode.VIEW);
		}
		catch (PortletModeException pme) {
			throw new SystemException(pme);
		}

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			throw new SystemException(wse);
		}

		Map<String, String[]> parameters = getItemSelectorParameters(
			itemSelectedEventName, itemSelectorCriteria);

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			portletURL.setParameter(entry.getKey(), entry.getValue());
		}

		return portletURL;
	}

	@Override
	public PortletURL getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria) {

		return getItemSelectorURL(
			requestBackedPortletURLFactory, null, 0, itemSelectedEventName,
			itemSelectorCriteria);
	}

	protected List<Class<? extends ItemSelectorCriterion>>
		getItemSelectorCriterionClasses(Map<String, String[]> parameters) {

		String criteria = getValue(parameters, PARAMETER_CRITERIA);

		String[] itemSelectorCriterionClassNames = criteria.split(",");

		List<Class<? extends ItemSelectorCriterion>>
			itemSelectorCriterionClasses = new ArrayList<>(
				itemSelectorCriterionClassNames.length);

		for (String itemSelectorCriterionClassName :
				itemSelectorCriterionClassNames) {

			ItemSelectorCriterionHandler<?> itemSelectorCriterionHandler =
				_itemSelectionCriterionHandlers.get(
					itemSelectorCriterionClassName);

			if (itemSelectorCriterionHandler != null) {
				itemSelectorCriterionClasses.add(
					itemSelectorCriterionHandler.
						getItemSelectorCriterionClass());
			}
		}

		return itemSelectorCriterionClasses;
	}

	protected Map<String, String[]> getItemSelectorParameters(
		String itemSelectedEventName,
		ItemSelectorCriterion... itemSelectorCriteria) {

		StringBundler sb = new StringBundler(itemSelectorCriteria.length * 2);

		for (ItemSelectorCriterion itemSelectorCriterion :
				itemSelectorCriteria) {

			Class<?> clazz = itemSelectorCriterion.getClass();

			sb.append(clazz.getName());

			sb.append(StringPool.COMMA);
		}

		if (itemSelectorCriteria.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		Map<String, String[]> parameters = HashMapBuilder.<String, String[]>put(
			PARAMETER_CRITERIA, new String[] {sb.toString()}
		).put(
			PARAMETER_ITEM_SELECTED_EVENT_NAME,
			new String[] {itemSelectedEventName}
		).build();

		for (int i = 0; i < itemSelectorCriteria.length; i++) {
			ItemSelectorCriterion itemSelectorCriterion =
				itemSelectorCriteria[i];

			String countValue = String.valueOf(i);

			parameters.put(
				countValue.concat(JSON),
				new String[] {
					_itemSelectionCriterionSerializer.serialize(
						itemSelectorCriterion)
				});
		}

		return parameters;
	}

	protected PortletURL getPortletURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String title, String selectedTab, String itemSelectedEventName,
		ItemSelectorCriterion[] itemSelectorCriteriaArray,
		ThemeDisplay themeDisplay) {

		PortletURL portletURL = null;

		if (Validator.isNotNull(selectedTab) && selectedTab.equals(title)) {
			portletURL = getItemSelectorURL(
				requestBackedPortletURLFactory, themeDisplay.getScopeGroup(),
				themeDisplay.getRefererGroupId(), itemSelectedEventName,
				itemSelectorCriteriaArray);
		}
		else {
			Group group = themeDisplay.getRefererGroup();

			if (group == null) {
				group = themeDisplay.getScopeGroup();
			}

			portletURL = getItemSelectorURL(
				requestBackedPortletURLFactory, group, 0, itemSelectedEventName,
				itemSelectorCriteriaArray);
		}

		portletURL.setParameter(PARAMETER_SELECTED_TAB, title);

		return portletURL;
	}

	protected String getValue(Map<String, String[]> parameters, String name) {
		String[] values = parameters.get(name);

		if (ArrayUtil.isEmpty(values)) {
			return StringPool.BLANK;
		}

		return values[0];
	}

	protected boolean isSearch(Map<String, String[]> parameters) {
		String keywords = getValue(parameters, "keywords");

		if (Validator.isNotNull(keywords)) {
			return true;
		}

		return false;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	@SuppressWarnings("rawtypes")
	protected
		<T extends ItemSelectorCriterion, S extends ItemSelectorReturnType> void
			setItemSelectionCriterionHandler(
				ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.put(
			itemSelectorCriterionClass.getName(),
			(ItemSelectorCriterionHandler)itemSelectionCriterionHandler);
	}

	@Reference(unbind = "-")
	protected void setItemSelectorCriterionSerializer(
		ItemSelectorCriterionSerializer itemSelectorCriterionSerializer) {

		_itemSelectionCriterionSerializer = itemSelectorCriterionSerializer;
	}

	protected
		<T extends ItemSelectorCriterion, S extends ItemSelectorReturnType> void
			unsetItemSelectionCriterionHandler(
				ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.remove(
			itemSelectorCriterionClass.getName());
	}

	@Reference
	private Http _http;

	private final ConcurrentMap
		<String, ItemSelectorCriterionHandler<ItemSelectorCriterion>>
			_itemSelectionCriterionHandlers = new ConcurrentHashMap<>();
	private ItemSelectorCriterionSerializer _itemSelectionCriterionSerializer;

	@Reference
	private Portal _portal;

}