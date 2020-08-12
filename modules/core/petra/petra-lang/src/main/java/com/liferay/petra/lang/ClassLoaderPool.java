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

package com.liferay.petra.lang;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Maps servlet context names to/from the servlet context's class loader.
 *
 * @author Shuyang Zhou
 */
public class ClassLoaderPool {

	/**
	 * Returns the class loader associated with the context name.
	 *
	 * <p>
	 * If no class loader is found for the context name, the thread's context
	 * class loader is returned as a fallback.
	 * </p>
	 *
	 * @param  contextName the servlet context's name
	 * @return the class loader associated with the context name
	 */
	public static ClassLoader getClassLoader(String contextName) {
		ClassLoader classLoader = null;

		if ((contextName != null) && !contextName.equals("null")) {
			classLoader = _classLoaders.get(contextName);

			if (classLoader == null) {
				int index = contextName.lastIndexOf('_');

				if (index > 0) {
					ConcurrentNavigableMap<Version, ClassLoader> classLoaders =
						_fallbackClassLoaders.get(
							contextName.substring(0, index));

					if (classLoaders != null) {
						Map.Entry<Version, ClassLoader> entry =
							classLoaders.lastEntry();

						if (entry != null) {
							classLoader = entry.getValue();
						}
					}
				}
			}
		}

		if (classLoader == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();
		}

		return classLoader;
	}

	/**
	 * Returns the context name associated with the class loader.
	 *
	 * <p>
	 * If the class loader is <code>null</code> or if no context name is
	 * associated with the class loader, {@link <code>"null"</code>} is
	 * returned.
	 * </p>
	 *
	 * @param  classLoader the class loader
	 * @return the context name associated with the class loader
	 */
	public static String getContextName(ClassLoader classLoader) {
		if (classLoader == null) {
			return "null";
		}

		String contextName = _contextNames.get(classLoader);

		if (contextName == null) {
			contextName = "null";
		}

		return contextName;
	}

	public static void register(String contextName, ClassLoader classLoader) {
		_classLoaders.put(contextName, classLoader);
		_contextNames.put(classLoader, contextName);

		Map.Entry<String, Version> entry = _parseContextName(contextName);

		if (entry == null) {
			return;
		}

		_fallbackClassLoaders.compute(
			entry.getKey(),
			(key, classLoaders) -> {
				if (classLoaders == null) {
					classLoaders = new ConcurrentSkipListMap<>();
				}

				classLoaders.put(entry.getValue(), classLoader);

				return classLoaders;
			});
	}

	public static void unregister(ClassLoader classLoader) {
		String contextName = _contextNames.remove(classLoader);

		if (contextName != null) {
			_classLoaders.remove(contextName);

			_unregisterFallback(contextName);
		}
	}

	public static void unregister(String contextName) {
		ClassLoader classLoader = _classLoaders.remove(contextName);

		if (classLoader != null) {
			_contextNames.remove(classLoader);

			_unregisterFallback(contextName);
		}
	}

	private static Map.Entry<String, Version> _parseContextName(
		String contextName) {

		int index = contextName.lastIndexOf('_');

		if ((index > 0) && (index < (contextName.length() - 1))) {
			Version version = _parseVersion(contextName.substring(index + 1));

			if (version != null) {
				return new AbstractMap.SimpleEntry<>(
					contextName.substring(0, index), version);
			}
		}

		return null;
	}

	private static Version _parseVersion(String version) {
		int major = 0;
		int minor = 0;
		int micro = 0;
		String qualifier = "";

		List<String> parts = _split(version);

		int size = parts.size();

		try {
			if (size >= 1) {
				major = Integer.parseInt(parts.get(0));
			}

			if (size >= 2) {
				minor = Integer.parseInt(parts.get(1));
			}

			if (size >= 3) {
				micro = Integer.parseInt(parts.get(2));
			}

			if (size >= 4) {
				qualifier = parts.get(3);
			}
		}
		catch (NumberFormatException numberFormatException) {
			return null;
		}

		return new Version(major, minor, micro, qualifier);
	}

	private static List<String> _split(String s) {
		List<String> values = new ArrayList<>();

		int offset = 0;
		int pos;

		while ((pos = s.indexOf('.', offset)) != -1) {
			if (offset < pos) {
				values.add(s.substring(offset, pos));
			}

			offset = pos + 1;
		}

		if (offset < s.length()) {
			values.add(s.substring(offset));
		}

		return values;
	}

	private static void _unregisterFallback(String contextName) {
		Map.Entry<String, Version> entry = _parseContextName(contextName);

		if (entry == null) {
			return;
		}

		_fallbackClassLoaders.computeIfPresent(
			entry.getKey(),
			(key, classLoaders) -> {
				classLoaders.remove(entry.getValue());

				if (classLoaders.isEmpty()) {
					return null;
				}

				return classLoaders;
			});
	}

	private static final Map<String, ClassLoader> _classLoaders =
		new ConcurrentHashMap<>();
	private static final Map<ClassLoader, String> _contextNames =
		new ConcurrentHashMap<>();
	private static final Map
		<String, ConcurrentNavigableMap<Version, ClassLoader>>
			_fallbackClassLoaders = new ConcurrentHashMap<>();

	static {
		register("SystemClassLoader", ClassLoader.getSystemClassLoader());
		register("GlobalClassLoader", ClassLoaderPool.class.getClassLoader());
	}

	private static class Version implements Comparable<Version> {

		@Override
		public int compareTo(Version version) {
			if (version == this) {
				return 0;
			}

			int result = _major - version._major;

			if (result != 0) {
				return result;
			}

			result = _minor - version._minor;

			if (result != 0) {
				return result;
			}

			result = _micro - version._micro;

			if (result != 0) {
				return result;
			}

			return _qualifier.compareTo(version._qualifier);
		}

		private Version(int major, int minor, int micro, String qualifier) {
			_major = major;
			_minor = minor;
			_micro = micro;
			_qualifier = qualifier;
		}

		private final int _major;
		private final int _micro;
		private final int _minor;
		private final String _qualifier;

	}

}