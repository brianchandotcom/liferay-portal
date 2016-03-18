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

package com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.WebXMLDefinition;
import com.liferay.portal.osgi.web.wab.extender.internal.definition.ordering.Ordering.Path;

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
 *
 */
public class WebFragmentOrderUtil {

	public static Map<String, WebXMLDefinition> getConfigMap(
		List<WebXMLDefinition> webXMLs) {

		Map<String, WebXMLDefinition> configMap = new HashMap<>();

		for (WebXMLDefinition webxML : webXMLs) {
			String name = webxML.getFragmentName();
			configMap.put(name, webxML);
		}

		return configMap;
	}

	/**
	 * This method returns an ordered version of the specified list of
	 * web-fragment.xml descriptors and assumes that there is no
	 * absolute ordering.
	 * @param configList
	 * @return
	 * @throws OrderingBeforeAndAfterException
	 * @throws OrderingCircularDependencyException
	 * @throws OrderingMaxAttemptsException
	 */
	public static List<WebXMLDefinition> getOrder(
			List<WebXMLDefinition> configList)
		throws OrderingBeforeAndAfterException,
			OrderingCircularDependencyException, OrderingMaxAttemptsException {

		// Check for "duplicate name exception" and "circular references"
		// as described in 8.2.2 Ordering of web.xml and web-fragment.xml

		checkForSpecExceptions(configList);

		// It turns out that some of the specified ordering, if it was not
		// discovered by the sort routine until later in its processing,
		// was not being considered correctly in the ordering algorithm.

		// This preSort method puts all of the documents with specified

		//ordering as early on in the list of documents as possible for to
		//consider it quickly, and be able to use its ordering algorithm
		//to the best of its ability to achieve the specified ordering.

		configList = preSort(configList);

		WebXMLDefinition[] configs = configList.toArray(
			new WebXMLDefinition[configList.size()]);

		// This is a multiple pass sorting routine which gets the documents

		//close to the order they need to be in

		innerSort(configs);

		// This is the final sort which checks the list from left to right to

		//see if they are in the specified order and if they are not, it moves
		//the incorrectly placed document(s) to the right into its proper place,
		//and shifts others left as necessary.

		postSort(configs);

		return new ArrayList<>(Arrays.asList(configs));
	}

	/**
	 * This method returns an ordered version of the specified list of
	 * web-fragment.xml descriptors, taking the specified absolute ordering
	 * into account.
	 * @param configs
	 * @param absoluteOrder
	 * @return
	 * @throws OrderingMaxAttemptsException
	 * @throws OrderingCircularDependencyException
	 * @throws OrderingBeforeAndAfterException
	 */
	public static List<WebXMLDefinition> getOrder(
			List<WebXMLDefinition> configs, List<String> absoluteOrder)
		throws OrderingBeforeAndAfterException,
			OrderingCircularDependencyException, OrderingMaxAttemptsException {

		if (ListUtil.isEmpty(absoluteOrder)) {
			return getOrder(configs);
		}
		else {
			return getOrderWithAbsoluteOrder(configs, absoluteOrder);
		}
	}

	private static String[] appendAndSort(String[]... groups) {
		HashMap<String, Integer> map = new HashMap<>();

		// retain OTHERS, if it is in the first group, but do not allow
		// OTHERS to be appended

		if (groups[0] != null) {
			if (containsOthers(groups[0])) {
				map.put(OrderingImpl.OTHERS, 1);
			}
		}

		for (String[] group : groups) {
			for (String name : group) {
				if (!name.equals(OrderingImpl.OTHERS)) {
					map.put(name, 1);
				}
			}
		}

		Set<String> keySet = map.keySet();
		String[] orderedNames = keySet.toArray(new String[keySet.size()]);
		Arrays.sort(orderedNames);

		return orderedNames;
	}

	private static void checkForBothBeforeAndAfter(WebXMLDefinition config)
		throws OrderingBeforeAndAfterException {

		String configName = config.getFragmentName();
		Ordering configOrdering = config.getOrdering();
		EnumMap<Ordering.Path, String[]> orderingRoutes =
			configOrdering.getRoutes();

		HashMap<String, Integer> map = new HashMap<>();

		String[] beforeRoutes = orderingRoutes.get(Ordering.Path.BEFORE);

		for (String name : beforeRoutes) {
			Integer value = map.get(name);

			if (value == null) {
				value = 1;
			}
			else {
				value += 1;
			}

			map.put(name, value);
		}

		String[] afterRoutes = orderingRoutes.get(Ordering.Path.AFTER);

		for (String name : afterRoutes) {
			Integer value = map.get(name);

			if (value == null) {
				value = 1;
			}
			else {
				value += 1;
			}

			map.put(name, value);
		}

		Set<String> keySet = map.keySet();
		String[] namesToCheck = keySet.toArray(new String[keySet.size()]);

		for (String name : namesToCheck) {
			if (map.get(name) > 1) {
				throw new OrderingBeforeAndAfterException(configName, name);
			}
		}
	}

	private static void checkForSpecExceptions(List<WebXMLDefinition> configs)
		throws OrderingBeforeAndAfterException,
			OrderingCircularDependencyException {

		for (WebXMLDefinition config : configs) {

			// Check for "duplicate name exception"

			checkForBothBeforeAndAfter(config);

			// Map the routes along both paths, checking for

			//"circular references" along each path

			for (Ordering.Path path : Ordering.Path.values()) {
				mapRoutes(config, path, configs);
			}
		}
	}

	private static boolean containsOthers(String[] route) {
		return (Arrays.binarySearch(route, OrderingImpl.OTHERS) >= 0);
	}

	private static <K, V extends Comparable<? super V>> Map<K, V>
		descendingByValue(Map<K, V> map) {

		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

		Collections.sort(
			list,
			new Comparator<Map.Entry<K, V>>() {

				public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
					return (b.getValue()).compareTo(a.getValue());
				}

			});

		Map<K, V> result = new LinkedHashMap<>();

		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	private static LinkedList<String> extractNamesList(
		WebXMLDefinition[] configs) {

		LinkedList<String> names = new LinkedList<>();

		for (WebXMLDefinition config : configs) {
			names.add(config.getFragmentName());
		}

		return names;
	}

	private static List<WebXMLDefinition> getOrderWithAbsoluteOrder(
		List<WebXMLDefinition> configs, List<String> absoluteOrder) {

		List<WebXMLDefinition> orderedList = new ArrayList<>();

		List<WebXMLDefinition> configList = new CopyOnWriteArrayList<>();
		configList.addAll(configs);

		for (String name : absoluteOrder) {
			if (OrderingImpl.OTHERS.equals(name)) {
				continue;
			}

			boolean found = false;

			for (WebXMLDefinition config : configList) {
				String fragmentName = config.getFragmentName();

				if (!found && name.equals(fragmentName)) {
					found = true;
					orderedList.add(config);
					configList.remove(config);
				}
				else if (found && name.equals(fragmentName)) {
					break;
				}
			}
		}

		int othersIndex = absoluteOrder.indexOf(OrderingImpl.OTHERS);

		if (othersIndex != -1) {
			for (WebXMLDefinition config : configList) {
				orderedList.add(othersIndex, config);
			}
		}

		return orderedList;
	}

	private static int innerSort(WebXMLDefinition[] configs)
		throws OrderingMaxAttemptsException {

		int attempts = 0;
		boolean attempting = true;

		while (attempting) {
			if (attempts > _MAX_ATTEMPTS) {
				throw new OrderingMaxAttemptsException(_MAX_ATTEMPTS);
			}
			else {
				attempting = false;
			}

			int last = configs.length - 1;

			for (int i = 0; i < configs.length; i++) {
				int first = i;
				int second = first + 1;

				if (first == last) {
					second = first;
					first = 0;
				}

				if (isDisordered(configs[first], configs[second])) {
					WebXMLDefinition temp = configs[first];
					configs[first] = configs[second];
					configs[second] = temp;
					attempting = true;
				}
			}

			attempts++;
		}

		return attempts;
	}

	private static boolean isDisordered(
		WebXMLDefinition config1, WebXMLDefinition config2) {

		String config1Name = config1.getFragmentName();
		String config2Name = config2.getFragmentName();

		Ordering config1Ordering = config1.getOrdering();
		Ordering config2Ordering = config2.getOrdering();

		if (config1Ordering.isOrdered() && !config2Ordering.isOrdered()) {
			EnumMap<Path, String[]> routes = config1Ordering.getRoutes();

			if (!ArrayUtil.isEmpty(routes.get(Ordering.Path.AFTER)) &&
				!config1Ordering.isBeforeOthers()) {

				return true;
			}
		}

		// they are not in the specified order

		if (config2Ordering.isBefore(config1Name) ||
			config1Ordering.isAfter(config2Name)) {

			return true;
		}

		// config1 should be after others, but it is not

		if (config1Ordering.isAfterOthers() &&
		 !config1Ordering.isBefore(config2Name) &&
				!(config1Ordering.isAfterOthers() &&
				 config2Ordering.isAfterOthers())) {

			return true;
		}

		// config2 should be before others, but it is not

		if (config2Ordering.isBeforeOthers() &&
		 !config2Ordering.isAfter(config1Name) &&
			!(config1Ordering.isBeforeOthers() &&
			 config2Ordering.isBeforeOthers())) {

			return true;
		}

		return false;
	}

	private static void mapRoutes(
			WebXMLDefinition config, Ordering.Path path,
			List<WebXMLDefinition> webXMLs)
		throws OrderingCircularDependencyException {

		String configName = config.getFragmentName();
		Ordering configOrdering = config.getOrdering();
		EnumMap<Ordering.Path, String[]> configOrderingRoutes =
			configOrdering.getRoutes();
		String[] routePathNames = configOrderingRoutes.get(path);

		for (String routePathName : routePathNames) {
			if (!routePathName.equals(OrderingImpl.OTHERS)) {
				for (WebXMLDefinition otherConfig : webXMLs) {
					String otherConfigName = otherConfig.getFragmentName();

					if (routePathName.equals(otherConfigName)) {
						Ordering otherConfigOrdering =
							otherConfig.getOrdering();

						EnumMap<Ordering.Path, String[]>
							otherConfigOrderingRoutes =
								otherConfigOrdering.getRoutes();

						String[] otherRoutePathNames =
							otherConfigOrderingRoutes.get(path);

						if (Arrays.binarySearch(
								otherRoutePathNames, configName) >= 0) {

							throw new OrderingCircularDependencyException(
								path, webXMLs);
						}

						// If I am before them, they should be informed
						// that they are after me. Similarly, if I am after
						// them, then they should be informed that they are
						// before me.

						Ordering.Path oppositePath;

						if (path == Ordering.Path.BEFORE) {
							oppositePath = Ordering.Path.AFTER;
						}
						else {
							oppositePath = Ordering.Path.BEFORE;
						}

						String[] oppositePathNames =
							otherConfigOrderingRoutes.get(oppositePath);

						if (Arrays.binarySearch(
								oppositePathNames, configName) < 0) {

							EnumMap<Ordering.Path, String[]> routes =
								new EnumMap<>(Ordering.Path.class);

							routes.put(path, otherRoutePathNames);
							routes.put(
								oppositePath,
								appendAndSort(
									otherConfigOrderingRoutes.get(oppositePath),
									new String[] {configName}));

							otherConfigOrdering.setRoutes(routes);
						}

						// If I am before them and they are before others,
						// then I should be informed that I am before
						// others too. Similarly, if I am after them and
						// they are after others, then I should be informed
						// that I am after others too.

						if (ArrayUtil.isNotEmpty(otherRoutePathNames)) {
							EnumMap<Ordering.Path, String[]> routes =
								new EnumMap<>(Ordering.Path.class);
							routes.put(
								path,
								appendAndSort(
									routePathNames, otherRoutePathNames));
							routes.put(
								oppositePath,
								configOrderingRoutes.get(oppositePath));
							configOrdering.setRoutes(routes);
						}
					}
				}
			}
		}
	}

	private static void postSort(WebXMLDefinition[] configs) {
		int i = 0;

		while (i < configs.length) {
			LinkedList<String> names = extractNamesList(configs);

			boolean done = true;

			for (int j = 0; j < configs.length; j++) {
				int k = 0;

				for (String configName : names) {
					String fragmentName = configs[j].getFragmentName();

					if (fragmentName.equals(configName)) {
						break;
					}

					Ordering ordering = configs[j].getOrdering();

					if (ordering.isBefore(configName)) {

						// We have a document that is out of order,
						// and his index is k, he belongs at index j, and all
						// the documents in between need to be shifted left.

						WebXMLDefinition temp = null;

						for (int m = 0; m < configs.length; m++) {

							// This is one that is out of order and needs
							// to be moved.

							if (m == k) {
								temp = configs[m];
							}

							// This is one in between that needs to be shifted
							// left.

							if ((temp != null) && (m != j)) {
								configs[m] = configs[m + 1];
							}

							// This is where the one that is out of order needs

							//to be moved to.

							if (m == j) {
								configs[m] = temp;

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

	private static List<WebXMLDefinition> preSort(
		List<WebXMLDefinition> configs) {

		List<WebXMLDefinition> newConfigList = new ArrayList<>();
		List<WebXMLDefinition> anonAndUnordered = new LinkedList<>();
		Map<String, Integer> namedMap = new LinkedHashMap<>();

		for (WebXMLDefinition config : configs) {
			Ordering configOrdering = config.getOrdering();
			EnumMap<Ordering.Path, String[]> configOrderingRoutes =
				configOrdering.getRoutes();
			String[] beforePathNames = configOrderingRoutes.get(
				Ordering.Path.BEFORE);
			String[] afterPathNames = configOrderingRoutes.get(
				Ordering.Path.AFTER);

			String configName = config.getFragmentName();

			if (Validator.isNull(configName) && !configOrdering.isOrdered()) {
				anonAndUnordered.add(config);
			}
			else {
				int totalPathNames =
					beforePathNames.length + afterPathNames.length;
				namedMap.put(configName, totalPathNames);
			}
		}

		namedMap = descendingByValue(namedMap);

		Map<String, WebXMLDefinition> configMap = getConfigMap(configs);

		// add named configs to the list in the correct preSorted order

		for (Map.Entry<String, Integer> entry : namedMap.entrySet()) {
			String key = entry.getKey();
			newConfigList.add(configMap.get(key));
		}

		// add configs that are both anonymous and unordered, to the list in
		// their original, incoming order

		for (WebXMLDefinition config : anonAndUnordered) {
			newConfigList.add(config);
		}

		return newConfigList;
	}

	private static final int _MAX_ATTEMPTS =
		(Integer.MAX_VALUE / (Byte.MAX_VALUE * Byte.MAX_VALUE *
			Byte.MAX_VALUE));

}