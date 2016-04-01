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

package com.liferay.portal.osgi.web.servlet.context.helper.internal.order;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order;
import com.liferay.portal.osgi.web.servlet.context.helper.order.Order.Path;
import com.liferay.portal.osgi.web.servlet.context.helper.order.OrderBeforeAndAfterException;
import com.liferay.portal.osgi.web.servlet.context.helper.order.OrderCircularDependencyException;
import com.liferay.portal.osgi.web.servlet.context.helper.order.OrderMaxAttemptsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Vernon Singleton
 * @author Juan Gonzalez
 */
public class OrderUtil {

	public static List<WebXMLDefinition> getOrderedWebXMLDefinitions(
			List<WebXMLDefinition> webXMLDefinitions,
			List<String> absoluteOrderNames)
		throws OrderBeforeAndAfterException, OrderCircularDependencyException,
			   OrderMaxAttemptsException {

		if (ListUtil.isEmpty(absoluteOrderNames)) {
			return _getOrderedWebXMLDefinitions(webXMLDefinitions);
		}

		return _getOrderedWebXMLDefinitions(
			webXMLDefinitions, absoluteOrderNames);
	}

	private static String[] _appendAndSort(String[]... groups) {
		Map<String, Integer> map = new HashMap<>();

		if (groups[0] != null) {
			if (Arrays.binarySearch(groups[0], Order.OTHERS) >= 0) {
				map.put(Order.OTHERS, 1);
			}
		}

		for (String[] group : groups) {
			for (String name : group) {
				if (!name.equals(Order.OTHERS)) {
					map.put(name, 1);
				}
			}
		}

		Set<String> set = map.keySet();

		String[] orderedNames = set.toArray(new String[set.size()]);

		Arrays.sort(orderedNames);

		return orderedNames;
	}

	private static void _checkForBothBeforeAndAfter(
			WebXMLDefinition webXMLDefinition)
		throws OrderBeforeAndAfterException {

		String fragmentName = webXMLDefinition.getFragmentName();
		Order order = webXMLDefinition.getOrder();

		EnumMap<Order.Path, String[]> orderRoutes = order.getRoutes();

		Map<String, Integer> map = new HashMap<>();

		String[] beforeRoutes = orderRoutes.get(Order.Path.BEFORE);

		for (String beforeRouteName : beforeRoutes) {
			Integer value = map.get(beforeRouteName);

			if (value == null) {
				value = 1;
			}
			else {
				value += 1;
			}

			map.put(beforeRouteName, value);
		}

		String[] afterRoutes = orderRoutes.get(Order.Path.AFTER);

		for (String afterRouteName : afterRoutes) {
			Integer value = map.get(afterRouteName);

			if (value == null) {
				value = 1;
			}
			else {
				value += 1;
			}

			map.put(afterRouteName, value);
		}

		Set<String> set = map.keySet();

		String[] namesToCheck = set.toArray(new String[set.size()]);

		for (String name : namesToCheck) {
			if (map.get(name) > 1) {
				throw new OrderBeforeAndAfterException(fragmentName, name);
			}
		}
	}

	private static void _checkForSpecExceptions(
			List<WebXMLDefinition> webXMLDefinitions)
		throws OrderBeforeAndAfterException, OrderCircularDependencyException {

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			_checkForBothBeforeAndAfter(webXMLDefinition);

			for (Order.Path path : Order.Path.values()) {
				_mapRoutes(webXMLDefinition, path, webXMLDefinitions);
			}
		}
	}

	private static List<String> _getFragmentNames(
		WebXMLDefinition[] webXMLDefinitions) {

		List<String> fragmentNames = new LinkedList<>();

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			fragmentNames.add(webXMLDefinition.getFragmentName());
		}

		return fragmentNames;
	}

	private static List<WebXMLDefinition> _getOrderedWebXMLDefinitions(
			List<WebXMLDefinition> webXMLDefinitions)
		throws OrderBeforeAndAfterException,
			OrderCircularDependencyException, OrderMaxAttemptsException {

		_checkForSpecExceptions(webXMLDefinitions);

		webXMLDefinitions = _preSort(webXMLDefinitions);

		WebXMLDefinition[] webXMLDefinitionsArray = webXMLDefinitions.toArray(
			new WebXMLDefinition[webXMLDefinitions.size()]);

		_innerSort(webXMLDefinitionsArray);

		_postSort(webXMLDefinitionsArray);

		return new ArrayList<>(Arrays.asList(webXMLDefinitionsArray));
	}

	private static List<WebXMLDefinition> _getOrderedWebXMLDefinitions(
		List<WebXMLDefinition> webXMLDefinitions,
		List<String> absoluteOrderNames) {

		List<WebXMLDefinition> orderedList = new ArrayList<>();

		List<WebXMLDefinition> webXMLDefinitionsList =
			new CopyOnWriteArrayList<>();

		webXMLDefinitionsList.addAll(webXMLDefinitions);

		for (String absoluteOrderName : absoluteOrderNames) {
			if (Order.OTHERS.equals(absoluteOrderName)) {
				continue;
			}

			boolean found = false;

			for (WebXMLDefinition webXMLDefinition : webXMLDefinitionsList) {
				String fragmentName = webXMLDefinition.getFragmentName();

				if (!found && absoluteOrderName.equals(fragmentName)) {
					found = true;

					orderedList.add(webXMLDefinition);

					webXMLDefinitionsList.remove(webXMLDefinition);
				}
				else if (found && absoluteOrderName.equals(fragmentName)) {
					break;
				}
			}
		}

		int othersIndex = absoluteOrderNames.indexOf(Order.OTHERS);

		if (othersIndex != -1) {
			for (WebXMLDefinition webXMLDefinition : webXMLDefinitionsList) {
				orderedList.add(othersIndex, webXMLDefinition);
			}
		}

		return orderedList;
	}

	private static Map<String, WebXMLDefinition> _getWebXMLDefinitionsMap(
		List<WebXMLDefinition> webXMLDefinitions) {

		Map<String, WebXMLDefinition> webXMLDefinitionsMap = new HashMap<>();

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			String fragmentName = webXMLDefinition.getFragmentName();

			webXMLDefinitionsMap.put(fragmentName, webXMLDefinition);
		}

		return webXMLDefinitionsMap;
	}

	private static int _innerSort(WebXMLDefinition[] webXMLDefinitions)
		throws OrderMaxAttemptsException {

		int attempts = 0;
		boolean attempting = true;

		while (attempting) {
			if (attempts > _MAX_ATTEMPTS) {
				throw new OrderMaxAttemptsException(_MAX_ATTEMPTS);
			}

			attempting = false;

			int webXMLDefinitionsLength = webXMLDefinitions.length;

			int last = webXMLDefinitionsLength - 1;

			for (int i = 0; i < webXMLDefinitionsLength; i++) {
				int first = i;
				int second = first + 1;

				if (first == last) {
					second = first;
					first = 0;
				}

				if (_isDisordered(
						webXMLDefinitions[first], webXMLDefinitions[second])) {

					WebXMLDefinition webXMLDefinition =
						webXMLDefinitions[first];

					webXMLDefinitions[first] = webXMLDefinitions[second];
					webXMLDefinitions[second] = webXMLDefinition;

					attempting = true;
				}
			}

			attempts++;
		}

		return attempts;
	}

	private static boolean _isDisordered(
		WebXMLDefinition webXMLDefinition1,
		WebXMLDefinition webXMLDefinition2) {

		String fragmentName1 = webXMLDefinition1.getFragmentName();
		String fragmentName2 = webXMLDefinition2.getFragmentName();

		Order order1 = webXMLDefinition1.getOrder();
		Order order2 = webXMLDefinition2.getOrder();

		if (order1.isOrdered() && !order2.isOrdered()) {
			EnumMap<Path, String[]> routes = order1.getRoutes();

			if (!ArrayUtil.isEmpty(routes.get(Order.Path.AFTER)) &&
				!order1.isBeforeOthers()) {

				return true;
			}
		}

		// they are not in the specified order

		if (order2.isBefore(fragmentName1) || order1.isAfter(fragmentName2)) {
			return true;
		}

		// fragmentName1 should be after others, but it is not

		if (order1.isAfterOthers() &&
			!order1.isBefore(fragmentName2) &&
			!(order1.isAfterOthers() &&
			order2.isAfterOthers())) {

			return true;
		}

		// fragmentName2 should be before others, but it is not

		if (order2.isBeforeOthers() &&
			!order2.isAfter(fragmentName1) &&
			!(order1.isBeforeOthers() &&
			order2.isBeforeOthers())) {

			return true;
		}

		return false;
	}

	private static void _mapRoutes(
			WebXMLDefinition webXMLDefinition, Order.Path path,
			List<WebXMLDefinition> webXMLDefinitions)
		throws OrderCircularDependencyException {

		String fragmentName = webXMLDefinition.getFragmentName();
		Order order = webXMLDefinition.getOrder();

		EnumMap<Order.Path, String[]> orderRoutes = order.getRoutes();
		String[] routePathNames = orderRoutes.get(path);

		for (String routePathName : routePathNames) {
			if (routePathName.equals(Order.OTHERS)) {
				continue;
			}

			for (WebXMLDefinition otherConfig : webXMLDefinitions) {
				String otherConfigName = otherConfig.getFragmentName();

				if (!routePathName.equals(otherConfigName)) {
					continue;
				}

				Order otherConfigOrder = otherConfig.getOrder();

				EnumMap<Order.Path, String[]> otherConfigOrderRoutes =
					otherConfigOrder.getRoutes();

				String[] otherRoutePathNames = otherConfigOrderRoutes.get(path);

				if (Arrays.binarySearch(
						otherRoutePathNames, fragmentName) >= 0) {

					throw new OrderCircularDependencyException(
						path, webXMLDefinitions);
				}

				// If I am before them, they should be informed
				// that they are after me. Similarly, if I am after
				// them, then they should be informed that they are
				// before me.

				Order.Path oppositePath;

				if (path == Order.Path.BEFORE) {
					oppositePath = Order.Path.AFTER;
				}
				else {
					oppositePath = Order.Path.BEFORE;
				}

				String[] oppositePathNames = otherConfigOrderRoutes.get(
					oppositePath);

				if (Arrays.binarySearch(oppositePathNames, fragmentName) < 0) {
					EnumMap<Order.Path, String[]> routes = new EnumMap<>(
						Order.Path.class);

					routes.put(path, otherRoutePathNames);
					routes.put(
						oppositePath,
						_appendAndSort(
							otherConfigOrderRoutes.get(oppositePath),
							new String[] {fragmentName}));

					otherConfigOrder.setRoutes(routes);
				}

				// If I am before them and they are before others,
				// then I should be informed that I am before
				// others too. Similarly, if I am after them and
				// they are after others, then I should be informed
				// that I am after others too.

				if (ArrayUtil.isNotEmpty(otherRoutePathNames)) {
					EnumMap<Order.Path, String[]> routes = new EnumMap<>(
						Order.Path.class);

					routes.put(
						path,
						_appendAndSort(routePathNames, otherRoutePathNames));
					routes.put(oppositePath, orderRoutes.get(oppositePath));

					order.setRoutes(routes);
				}
			}
		}
	}

	private static void _postSort(WebXMLDefinition[] webXMLDefinitions) {
		int i = 0;

		while (i < webXMLDefinitions.length) {
			List<String> fragmentNames = _getFragmentNames(webXMLDefinitions);

			boolean done = true;

			for (int j = 0; j < webXMLDefinitions.length; j++) {
				int k = 0;

				for (String currentName : fragmentNames) {
					String fragmentName =
						webXMLDefinitions[j].getFragmentName();

					if (fragmentName.equals(currentName)) {
						break;
					}

					Order order = webXMLDefinitions[j].getOrder();

					if (order.isBefore(currentName)) {

						// We have a document that is out of order,
						// and his index is k, he belongs at index j, and all
						// the documents in between need to be shifted left.

						WebXMLDefinition webXMLDefinition = null;

						for (int m = 0; m < webXMLDefinitions.length; m++) {

							// This is one that is out of order and needs
							// to be moved.

							if (m == k) {
								webXMLDefinition = webXMLDefinitions[m];
							}

							// This is one in between that needs to be shifted
							// left.

							if ((webXMLDefinition != null) && (m != j)) {
								webXMLDefinitions[m] = webXMLDefinitions[m + 1];
							}

							// This is where the one that is out of order needs
							// to be moved to.

							if (m == j) {
								webXMLDefinitions[m] = webXMLDefinition;

								done = false;

								break;
							}
						}

						if (!done) {
							break;
						}
					}

					k = k + 1;
				}
			}

			if (done) {
				break;
			}
		}
	}

	private static List<WebXMLDefinition> _preSort(
		List<WebXMLDefinition> webXMLDefinitions) {

		List<WebXMLDefinition> webXMLDefinitionList = new ArrayList<>();
		List<WebXMLDefinition> anonymousAndUnordered = new LinkedList<>();
		Map<String, Integer> namedMap = new LinkedHashMap<>();

		for (WebXMLDefinition webXMLDefinition : webXMLDefinitions) {
			Order order = webXMLDefinition.getOrder();

			EnumMap<Order.Path, String[]> webXMLDefinitionOrderRoutes =
				order.getRoutes();
			String[] beforePathNames = webXMLDefinitionOrderRoutes.get(
				Order.Path.BEFORE);
			String[] afterPathNames = webXMLDefinitionOrderRoutes.get(
				Order.Path.AFTER);

			String fragmentName = webXMLDefinition.getFragmentName();

			if (Validator.isNull(fragmentName) && !order.isOrdered()) {
				anonymousAndUnordered.add(webXMLDefinition);
			}
			else {
				int totalPathNames =
					beforePathNames.length + afterPathNames.length;
				namedMap.put(fragmentName, totalPathNames);
			}
		}

		namedMap = _sortDescendingByValue(namedMap);

		Map<String, WebXMLDefinition> configMap = _getWebXMLDefinitionsMap(
			webXMLDefinitions);

		// add named definitions to the list in the correct preSorted order

		for (Map.Entry<String, Integer> entry : namedMap.entrySet()) {
			String key = entry.getKey();
			webXMLDefinitionList.add(configMap.get(key));
		}

		// add definitions that are both anonymous and unordered, to the list in
		// their original, incoming order

		for (WebXMLDefinition webXMLDefinition : anonymousAndUnordered) {
			webXMLDefinitionList.add(webXMLDefinition);
		}

		return webXMLDefinitionList;
	}

	private static Map<String, Integer> _sortDescendingByValue(
		Map<String, Integer> map) {

		List<Map.Entry<String, Integer>> list = new LinkedList<>(
			map.entrySet());

		Collections.sort(list, _COMPARATOR);

		Map<String, Integer> result = new LinkedHashMap<>();

		for (Map.Entry<String, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	private static final MapEntryComparator _COMPARATOR =
		new MapEntryComparator();

	private static final int _MAX_ATTEMPTS =
		(Integer.MAX_VALUE / (Byte.MAX_VALUE * Byte.MAX_VALUE *
			Byte.MAX_VALUE));

	private static class MapEntryComparator
		implements Comparator<Map.Entry<String, Integer>> {

		@Override
		public int compare(
			Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {

			Integer valueA = a.getValue();
			Integer valueB = b.getValue();

			return valueB.compareTo(valueA);
		}

	}

}