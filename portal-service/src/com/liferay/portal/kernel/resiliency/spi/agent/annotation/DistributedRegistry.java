/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.resiliency.spi.agent.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class DistributedRegistry {

	public static boolean hasDistributed(String name, Direction direction) {
		Direction registeredDirection = _exactMatchMap.get(name);

		if ((registeredDirection == direction) ||
			(registeredDirection == Direction.Duplex)) {

			return true;
		}

		if (registeredDirection == null) {
			for (Map.Entry<String, Direction> entry :
					_postfixMatchMap.entrySet()) {

				String postfix = entry.getKey();

				if (name.endsWith(postfix)) {
					registeredDirection = entry.getValue();

					if ((registeredDirection == direction) ||
						(registeredDirection == Direction.Duplex)) {

						return true;
					}
				}
			}

			for (Map.Entry<String, Direction> entry :
					_prefixMatchMap.entrySet()) {

				String prefix = entry.getKey();

				if (name.startsWith(prefix)) {
					registeredDirection = entry.getValue();

					if ((registeredDirection == direction) ||
						(registeredDirection == Direction.Duplex)) {

						return true;
					}
				}
			}
		}

		return false;
	}

	public static void registerDistributed(Class<?> clazz) {
		Queue<Class<?>> queue = new LinkedList<Class<?>>();

		queue.offer(clazz);

		Class<?> currentClass = null;

		while ((currentClass = queue.poll()) != null) {
			Field[] fields = currentClass.getDeclaredFields();

			for (Field field : fields) {
				Distributed distributed = field.getAnnotation(
					Distributed.class);

				if (distributed != null) {
					int modifiers = field.getModifiers();

					if (Modifier.isPublic(modifiers) &&
						Modifier.isStatic(modifiers) &&
						Modifier.isFinal(modifiers) &&
						(field.getType() == String.class)) {

						try {
							String name = (String)field.get(null);

							registerDistributed(
								name, distributed.direction(),
								distributed.matchType());
						}
						catch (Throwable t) {
							throw new RuntimeException(t);
						}
					}
				}
			}

			Class<?> supperClass = currentClass.getSuperclass();

			if ((supperClass != null) && (supperClass != Object.class)) {
				queue.offer(supperClass);
			}

			Class<?>[] interfaceClasses = currentClass.getInterfaces();

			for (Class<?> interfaceClass : interfaceClasses) {
				if (!queue.contains(interfaceClass)) {
					queue.offer(interfaceClass);
				}
			}
		}
	}

	public static void registerDistributed(
		String name, Direction direction, MatchType matchType) {

		switch (matchType) {
			case Postfix:
				_postfixMatchMap.put(name, direction);
				break;
			case Prefix:
				_prefixMatchMap.put(name, direction);
				break;
			default:
				_exactMatchMap.put(name, direction);
		}
	}

	private static final Map<String, Direction> _exactMatchMap =
		new ConcurrentHashMap<String, Direction>();

	private static final Map<String, Direction> _postfixMatchMap =
		new ConcurrentHashMap<String, Direction>();

	private static final Map<String, Direction> _prefixMatchMap =
		new ConcurrentHashMap<String, Direction>();

}