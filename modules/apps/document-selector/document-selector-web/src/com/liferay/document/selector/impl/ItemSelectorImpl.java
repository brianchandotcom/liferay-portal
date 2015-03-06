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

package com.liferay.document.selector.impl;

import com.liferay.document.selector.ItemSelector;
import com.liferay.document.selector.ItemSelectorCriterion;
import com.liferay.document.selector.ItemSelectorCriterionHandler;
import com.liferay.document.selector.ItemSelectorView;
import com.liferay.document.selector.ItemSelectorViewWithCriterion;
import com.liferay.document.selector.RequestDescriptor;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.StringBundler;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component
public class ItemSelectorImpl implements ItemSelector {

	public static final String PARAM_CRITERIA = "criteria";

	public static final String PORTLET_URL = "/item_selector";

	@Override
	public RequestDescriptor getRequestDescriptor(
		ItemSelectorCriterion... itemSelectorCriteria) {

		Map<String, String> params = new HashMap<>();

		_putCriteriaParam(params, itemSelectorCriteria);

		for (int i = 0; i<itemSelectorCriteria.length; i++) {
			ItemSelectorCriterion itemSelectorCriterion =
				itemSelectorCriteria[i];

			String paramPrefix = i + "_";

			_putDesiredReturnTypesParam(
				params, paramPrefix, itemSelectorCriterion);

			_putItemCriterionSelectionParams(
				params, paramPrefix, itemSelectorCriterion);
		}

		return new RequestDescriptor(PORTLET_URL, params);
	}

	@Override
	public List<ItemSelectorViewWithCriterion<?>> processRequestDescriptor(
		RequestDescriptor requestDescriptor) {

		List<ItemSelectorViewWithCriterion<?>> itemSelectorViewWithCriterions =
			new ArrayList<>();

		List<Class> itemSelectorCriterionClasses =
			_getItemSelectorCriterionClasses(requestDescriptor);

		for (int i = 0; i<itemSelectorCriterionClasses.size(); i++) {
			Class itemSelectorCriterionClass = itemSelectorCriterionClasses.get(
				i);

			ItemSelectorCriterionHandler itemSelectorCriterionHandler =
				_itemSelectionCriterionHandlers.get(
					itemSelectorCriterionClass.getName());

			ItemSelectorCriterion itemSelectorCriterion =
				_getItemSelectorCriterion(
					requestDescriptor.getParams(), i + "_",
					itemSelectorCriterionClass);

			List<ItemSelectorView> itemSelectorViews =
				itemSelectorCriterionHandler.getItemSelectorViews(
					itemSelectorCriterion);

			for (ItemSelectorView itemSelectorView : itemSelectorViews) {
				itemSelectorViewWithCriterions.add(
					new ItemSelectorViewWithCriterion(
						itemSelectorView, itemSelectorCriterion)
				);
			}
		}

		return itemSelectorViewWithCriterions;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected <T extends ItemSelectorCriterion>
		void setItemSelectionCriterionHandler(
			ItemSelectorCriterionHandler<?> itemSelectionCriterionHandler) {

		Class<?> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.put(
			itemSelectorCriterionClass.getName(),
			itemSelectionCriterionHandler);
	}

	protected <T extends ItemSelectorCriterion>
		void unsetItemSelectionCriterionHandler(
			ItemSelectorCriterionHandler<T> itemSelectionCriterionHandler) {

		Class<T> itemSelectorCriterionClass =
			itemSelectionCriterionHandler.getItemSelectorCriterionClass();

		_itemSelectionCriterionHandlers.remove(
			itemSelectorCriterionClass.getName());
	}

	private <T> String _getCommaSeparatedList(
		Iterable<T> items, Function<T, ?> mapper) {

		StringBundler sb = new StringBundler();

		boolean first = true;

		for (T item : items) {
			if (first) {
				first = false;
			}
			else {
				sb.append(",");
			}

			Object mappedItem = item;

			if (mapper != null) {
				mappedItem = mapper.apply(item);
			}

			sb.append(mappedItem.toString());
		}

		return sb.toString();
	}

	private ItemSelectorCriterion _getItemSelectorCriterion(
		Map<String, String> params, String paramPrefix,
		Class<?> itemSelectorCriterionClass) {

		try {
			ItemSelectorCriterion itemSelectorCriterion =
				(ItemSelectorCriterion)itemSelectorCriterionClass.newInstance();

			Map<String, String> properties = new HashMap<>();

			for (String key : params.keySet()) {
				if (key.startsWith(paramPrefix)) {
					properties.put(
						key.substring(paramPrefix.length()), params.get(key));
				}
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

	private List<Class> _getItemSelectorCriterionClasses(
		RequestDescriptor requestDescriptor) {

		Map<String, String> params = requestDescriptor.getParams();

		String criteria = params.get(PARAM_CRITERIA);

		String[] itemSelectorCriterionClassNames = criteria.split(",");

		List<Class> itemSelectorCriterionClasses = new ArrayList<>();

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

	private void _putCriteriaParam(
		Map<String, String> params,
		ItemSelectorCriterion[] itemSelectorCriteria) {

		params.put(
			PARAM_CRITERIA,
			_getCommaSeparatedList(
				Arrays.asList(itemSelectorCriteria),
				new Function<ItemSelectorCriterion, String>() {

					@Override
					public String apply(
						ItemSelectorCriterion itemSelectorCriterion) {

						Class<?> clazz = itemSelectorCriterion.getClass();

						return clazz.getName();
					}
				}));
	}

	private void _putDesiredReturnTypesParam(
		Map<String, String> params, String paramPrefix,
		ItemSelectorCriterion itemSelectorCriterion) {

		Set<Class<?>> desiredReturnTypes =
			itemSelectorCriterion.getDesiredReturnTypes();

		Set<Class<?>> availableReturnTypes =
			itemSelectorCriterion.getAvailableReturnTypes();

		if (desiredReturnTypes.size() != availableReturnTypes.size()) {
			params.put(
				paramPrefix + "desiredReturnTypes",
				_getCommaSeparatedList(
					desiredReturnTypes,
					new Function<Class<?>, String>() {
						@Override
						public String apply(Class<?> clazz) {
							return clazz.getName();
					}
				}));
		}
	}

	private void _putItemCriterionSelectionParams(
		Map<String, String> params, String paramPrefix,
		ItemSelectorCriterion itemSelectorCriterion) {

		try {
			Map<String, ?> properties = BeanUtils.describe(
				itemSelectorCriterion);

			for (Map.Entry<String, ?> entry : properties.entrySet()) {
				String key = entry.getKey();

				if (key.equals("availableReturnTypes") ||
					key.equals("class") ||
					key.equals("desiredReturnTypes")) {
					continue;
				}

				Object value = entry.getValue();

				params.put(paramPrefix + key, value.toString());
			}
		}
		catch (InvocationTargetException | NoSuchMethodException |
				IllegalAccessException e) {

			throw new SystemException(
				"Unable to marshall item selector criterion", e);
		}
	}

	private final ConcurrentMap<String, ItemSelectorCriterionHandler>
		_itemSelectionCriterionHandlers = new ConcurrentHashMap<>();

}