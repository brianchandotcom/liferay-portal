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

package com.liferay.item.selector.web.impl;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewRenderer;
import com.liferay.item.selector.web.constants.ItemSelectorPortletKeys;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.apache.commons.beanutils.BeanUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera
 */
@Component(service = ItemSelector.class)
public class ItemSelectorImpl implements ItemSelector {

	public static final String PARAMETER_CRITERIA = "criteria";

	public static final String PARAMETER_ITEM_SELECTED_CALLBACK =
		"itemSelectedCallback";

	public static final String PARAMETER_SELECTED_TAB = "selectedTab";

	@Override
	public ItemSelectorRendering getItemSelectorRendering(
		PortletRequest portletRequest) {

		Map<String, String[]> parameters = portletRequest.getParameterMap();

		String itemSelectedCallback = getValue(
			parameters, PARAMETER_ITEM_SELECTED_CALLBACK);

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

				PortletURL portletURL = getItemSelectorURL(
					portletRequest, itemSelectedCallback,
					itemSelectorCriteriaArray);

				portletURL.setParameter(
					PARAMETER_SELECTED_TAB,
					itemSelectorView.getTitle(portletRequest.getLocale()));

				itemSelectorViewRenderers.add(
					new ItemSelectorViewRendererImpl(
						itemSelectorView, itemSelectorCriterion, portletURL,
						itemSelectedCallback));
			}
		}

		return new ItemSelectorRenderingImpl(
			itemSelectedCallback, getValue(parameters, PARAMETER_SELECTED_TAB),
			itemSelectorViewRenderers);
	}

	@Override
	public PortletURL getItemSelectorURL(
		PortletRequest portletRequest, String itemSelectedCallback,
		ItemSelectorCriterion... itemSelectorCriteria) {

		Map<String, String[]> parameters = getItemSelectorParameters(
			itemSelectedCallback, itemSelectorCriteria);

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, ItemSelectorPortletKeys.ITEM_SELECTOR,
			themeDisplay.getPlid(), PortletRequest.ACTION_PHASE);

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

		portletURL.setParameter(ActionRequest.ACTION_NAME, "showItemSelector");

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			portletURL.setParameter(entry.getKey(), entry.getValue());
		}

		return portletURL;
	}

	protected <T extends ItemSelectorCriterion> T getItemSelectorCriterion(
		Map<String, String[]> parameters, String paramPrefix,
		Class<T> itemSelectorCriterionClass) {

		try {
			T itemSelectorCriterion = itemSelectorCriterionClass.newInstance();

			Map<String, String> properties = new HashMap<>();

			for (String key : parameters.keySet()) {
				if (!key.startsWith(paramPrefix)) {
					continue;
				}

				properties.put(
					key.substring(paramPrefix.length()),
					getValue(parameters, key));
			}

			BeanUtils.populate(itemSelectorCriterion, properties);

			return itemSelectorCriterion;
		}
		catch (InvocationTargetException | InstantiationException |
			IllegalAccessException e) {

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

			itemSelectorCriterionClasses.add(
				itemSelectorCriterionHandler.getItemSelectorCriterionClass());
		}

		return itemSelectorCriterionClasses;
	}

	protected Map<String, String[]> getItemSelectorParameters(
		String itemSelectedCallback,
		ItemSelectorCriterion... itemSelectorCriteria) {

		Map<String, String[]> parameters = new HashMap<>();

		parameters.put(
			PARAMETER_ITEM_SELECTED_CALLBACK,
			new String[] {itemSelectedCallback});

		populateCriteria(parameters, itemSelectorCriteria);

		for (int i = 0; i < itemSelectorCriteria.length; i++) {
			ItemSelectorCriterion itemSelectorCriterion =
				itemSelectorCriteria[i];

			String paramPrefix = i + "_";

			populateDesiredReturnTypes(
				parameters, paramPrefix, itemSelectorCriterion);
			populateItemSelectorCriteria(
				parameters, paramPrefix, itemSelectorCriterion);
		}

		return parameters;
	}

	protected String getValue(Map<String, String[]> parameters, String name) {
		String[] values = parameters.get(name);

		if (ArrayUtil.isEmpty(values)) {
			return StringPool.BLANK;
		}

		return values[0];
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

	protected void populateDesiredReturnTypes(
		Map<String, String[]> parameters, String paramPrefix,
		ItemSelectorCriterion itemSelectorCriterion) {

		Set<Class<?>> desiredReturnTypes =
			itemSelectorCriterion.getDesiredReturnTypes();

		Set<Class<?>> availableReturnTypes =
			itemSelectorCriterion.getAvailableReturnTypes();

		if (desiredReturnTypes.size() == availableReturnTypes.size()) {
			return;
		}

		Accessor<Class<?>, String> accessor = new Accessor<Class<?>, String>() {

			@Override
			public String get(Class<?> clazz) {
				return clazz.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			@SuppressWarnings("rawtypes")
			public Class<Class<?>> getTypeClass() {
				return (Class)Class.class;
			}

		};

		parameters.put(
			paramPrefix + "desiredReturnTypes",
			new String[] {
				ArrayUtil.toString(
					desiredReturnTypes.toArray(
						new Class<?>[desiredReturnTypes.size()]),
					accessor)
			});
	}

	protected void populateItemSelectorCriteria(
		Map<String, String[]> parameters, String paramPrefix,
		ItemSelectorCriterion itemSelectorCriterion) {

		try {
			Map<String, ?> properties = BeanUtils.describe(
				itemSelectorCriterion);

			for (Map.Entry<String, ?> entry : properties.entrySet()) {
				String key = entry.getKey();

				if (key.equals("availableReturnTypes") || key.equals("class") ||
					key.equals("desiredReturnTypes")) {

					continue;
				}

				Object value = entry.getValue();

				parameters.put(
					paramPrefix + key, new String[] {value.toString()});
			}
		}
		catch (IllegalAccessException | InvocationTargetException |
			NoSuchMethodException e) {

			throw new SystemException(
				"Unable to marshall item selector criterion", e);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	@SuppressWarnings("rawtypes")
	protected <T extends ItemSelectorCriterion> void
		setItemSelectionCriterionHandler(
			ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.put(
			itemSelectorCriterionClass.getName(),
			(ItemSelectorCriterionHandler)itemSelectionCriterionHandler);
	}

	protected <T extends ItemSelectorCriterion>
		void unsetItemSelectionCriterionHandler(
			ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.remove(
			itemSelectorCriterionClass.getName());
	}

	protected List<ItemSelectorCriterion> getItemSelectorCriteria(
		Map<String, String[]> parameters) {

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		List<Class<? extends ItemSelectorCriterion>>
			itemSelectorCriterionClasses = getItemSelectorCriterionClasses(
				parameters);

		for (int i = 0; i<itemSelectorCriterionClasses.size(); i++) {
			Class<? extends ItemSelectorCriterion> itemSelectorCriterionClass =
				itemSelectorCriterionClasses.get(i);

			String paramPrefix = i + "_";

			itemSelectorCriteria.add(
				getItemSelectorCriterion(
					parameters, paramPrefix, itemSelectorCriterionClass));
		}

		return itemSelectorCriteria;
	}

	private final ConcurrentMap
		<String, ItemSelectorCriterionHandler<ItemSelectorCriterion>>
			_itemSelectionCriterionHandlers = new ConcurrentHashMap<>();

}