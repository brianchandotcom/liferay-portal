/*******************************************************************************
 * Copyright (c) 2003, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.osgi.internal.serviceregistry;

import java.lang.reflect.Array;
import java.util.*;
import org.eclipse.osgi.framework.util.CaseInsensitiveDictionaryMap;
import org.eclipse.osgi.internal.messages.Msg;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Constants;

/**
 * Service properties.
 * 
 * Supports case-insensitive key lookup.
 */
class ServiceProperties extends CaseInsensitiveDictionaryMap<String, Object> {
	/**
	 * Create a properties object from a Dictionary.
	 *
	 * @param props The properties for this service.
	 * @throws IllegalArgumentException If a case-variants of a key are
	 * in the props parameter.
	 */
	ServiceProperties(Dictionary<String, ?> props) {
		this(props, 0);
	}

	/**
	 * Create a properties object from a Dictionary.
	 *
	 * @param props The properties for this service.
	 * @param extra Extra capacity in the map.
	 * @throws IllegalArgumentException If a case-variants of a key are
	 * in the props parameter.
	 */
	ServiceProperties(Dictionary<String, ?> props, int extra) {
		super(initialCapacity((props == null) ? extra : props.size() + extra));
		if (props == null) {
			return;
		}
		synchronized (props) {
			Enumeration<?> keysEnum = props.keys();
			while (keysEnum.hasMoreElements()) {
				Object key = keysEnum.nextElement();
				if (key instanceof String) {
					String header = (String) key;
					Object value = cloneValue(props.get(header));
					if (value != null && put(header, value) != null) {
						throw new IllegalArgumentException(NLS.bind(Msg.HEADER_DUPLICATE_KEY_EXCEPTION, key));
					}
				}
			}
		}
	}

	/**
	 * Create a properties object from a Map.
	 *
	 * @param props The properties for this service.
	 * @throws IllegalArgumentException If a case-variants of a key are
	 * in the props parameter.
	 */
	ServiceProperties(Map<String, ?> props) {
		super(initialCapacity((props == null) ? 0 : props.size()));
		if (props == null) {
			return;
		}
		synchronized (props) {
			for (Entry<?, ?> e : props.entrySet()) {
				Object key = e.getKey();
				if (key instanceof String) {
					String header = (String) key;
					Object value = cloneValue(props.get(header));
					if (value != null && put(header, value) != null) {
						throw new IllegalArgumentException(NLS.bind(Msg.HEADER_DUPLICATE_KEY_EXCEPTION, key));
					}
				}
			}
		}
	}

	/**
	 * Attempt to clone the value if necessary and possible.
	 *
	 * For some strange reason, you can test to see if an Object is
	 * Cloneable but you can't call the clone method since it is
	 * protected on Object!
	 *
	 * @param value object to be cloned.
	 * @return cloned object or original object if we didn't clone it.
	 */
	static Object cloneValue(Object value) {
		if (value == null)
			return null;
		if (value instanceof String) /* shortcut String */
			return value;
		if (value instanceof Number) /* shortcut Number */
			return value;
		if (value instanceof Character) /* shortcut Character */
			return value;
		if (value instanceof Boolean) /* shortcut Boolean */
			return value;

		Class<?> clazz = value.getClass();
		if (clazz.isArray()) {
			// Do an array copy
			Class<?> type = clazz.getComponentType();
			int len = Array.getLength(value);
			Object clonedArray = Array.newInstance(type, len);
			System.arraycopy(value, 0, clonedArray, 0, len);
			return clonedArray;
		}

		if (value instanceof Cloneable) {
			// must use reflection because Object clone method is protected!!
			try {
				return clazz.getMethod("clone", (Class<?>[]) null).invoke(value, (Object[]) null); //$NON-NLS-1$
			} catch (Exception e) {
				/* clone is not a public method on value's class */
			}
		}
		return value;
	}

	@Override
	public String toString() {
		Set<String> keys = keySet();

		StringBuilder sb = new StringBuilder(20 * keys.size());

		sb.append('{');

		int n = 0;
		for (String key : keys) {
			if (!key.equals(Constants.OBJECTCLASS)) {
				if (n > 0)
					sb.append(", "); //$NON-NLS-1$

				sb.append(key);
				sb.append('=');
				Object value = get(key);
				if (value.getClass().isArray()) {
					sb.append('[');
					int length = Array.getLength(value);
					for (int j = 0; j < length; j++) {
						if (j > 0)
							sb.append(',');
						sb.append(Array.get(value, j));
					}
					sb.append(']');
				} else {
					sb.append(value);
				}
				n++;
			}
		}

		sb.append('}');

		return sb.toString();
	}
}