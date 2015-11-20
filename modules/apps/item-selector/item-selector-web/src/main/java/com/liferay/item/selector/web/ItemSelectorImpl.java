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

package com.liferay.item.selector.web;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.web.constants.ItemSelectorPortletKeys;
import com.liferay.item.selector.web.util.ItemSelectorCriterionSerializer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

	public static final String PARAMETER_CRITERIA = "criteria";

	public static final String PARAMETER_ITEM_SELECTED_EVENT_NAME =
		"itemSelectedEventName";

	public static final String PARAMETER_SELECTED_TAB = "selectedTab";

	@Override
	public List<ItemSelectorCriterion> getItemSelectorCriteria(
		Map<String, String[]> parameters) {

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		List<Class<? extends ItemSelectorCriterion>>
			itemSelectorCriterionClasses = getItemSelectorCriterionClasses(
				parameters);

		for (int i = 0; i < itemSelectorCriterionClasses.size(); i++) {
			Class<? extends ItemSelectorCriterion> itemSelectorCriterionClass =
				itemSelectorCriterionClasses.get(i);

			String prefix = i + "_";

			itemSelectorCriteria.add(
				getItemSelectorCriterion(
					parameters, prefix, itemSelectorCriterionClass));
		}

		return itemSelectorCriteria;
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
			itemSelectorCriteria.toArray(
				new ItemSelectorCriterion[itemSelectorCriteria.size()]);

		for (int i = 0; i < itemSelectorCriteria.size(); i++) {
			ItemSelectorCriterion itemSelectorCriterion =
				itemSelectorCriteria.get(i);

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

	protected <T extends ItemSelectorCriterion> T getItemSelectorCriterion(
		Map<String, String[]> parameters, String prefix,
		Class<T> itemSelectorCriterionClass) {

		try {
			Constructor<T> constructor =
				itemSelectorCriterionClass.getConstructor();

			constructor.setAccessible(true);

			T itemSelectorCriterion = constructor.newInstance();

			_itemSelectionCriterionSerializer.setProperties(
				itemSelectorCriterion, prefix, parameters);

			return itemSelectorCriterion;
		}
		catch (InvocationTargetException | InstantiationException |
			   IllegalAccessException | NoSuchMethodException e) {

			throw new SystemException(
				"Unable to unmarshall item selector criterion", e);
		}
	}

	protected List<Class<? extends ItemSelectorCriterion>>
		getItemSelectorCriterionClasses(Map<String, String[]> parameters) {

		String criteria = getValue(parameters, PARAMETER_CRITERIA);

		String[] itemSelectorCriterionClassNames = criteria.split(",");

		List<Class<? extends ItemSelectorCriterion>>
			itemSelectorCriterionClasses = new ArrayList<>();

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

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put(
			PARAMETER_ITEM_SELECTED_EVENT_NAME,
			new String[] {itemSelectedEventName});

		populateCriteria(parameters, itemSelectorCriteria);
		populateItemSelectorCriteria(parameters, itemSelectorCriteria);

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

	protected void populateCriteria(
		Map<String, String[]> parameters,
		ItemSelectorCriterion[] itemSelectorCriteria) {

		Accessor<ItemSelectorCriterion, String> accessor =
			new Accessor<ItemSelectorCriterion, String>() {

				@Override
				public String get(ItemSelectorCriterion itemSelectorCriterion) {
					Class<?> clazz = itemSelectorCriterion.getClass();

					return clazz.getName();
				}

				@Override
				public Class<String> getAttributeClass() {
					return String.class;
				}

				@Override
				public Class<ItemSelectorCriterion> getTypeClass() {
					return ItemSelectorCriterion.class;
				}

			};

		parameters.put(
			PARAMETER_CRITERIA,
			new String[] {ArrayUtil.toString(itemSelectorCriteria, accessor)});
	}

	protected void populateItemSelectorCriteria(
		Map<String, String[]> parameters,
		ItemSelectorCriterion[] itemSelectorCriteria) {

		for (int i = 0; i < itemSelectorCriteria.length; i++) {
			ItemSelectorCriterion itemSelectorCriterion =
				itemSelectorCriteria[i];

			parameters.putAll(
				_itemSelectionCriterionSerializer.getProperties(
					itemSelectorCriterion, i + "_"));
		}
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
		<T extends ItemSelectorCriterion, S extends ItemSelectorReturnType>
			void unsetItemSelectionCriterionHandler(
				ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.remove(
			itemSelectorCriterionClass.getName());
	}

	private final ConcurrentMap
		<String, ItemSelectorCriterionHandler<ItemSelectorCriterion>>
			_itemSelectionCriterionHandlers = new ConcurrentHashMap<>();
	private volatile ItemSelectorCriterionSerializer
		_itemSelectionCriterionSerializer;

}