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

package com.liferay.portal.kernel.resiliency.spi.agent.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class DistributedRegistry {

	public static boolean isDistributed(String name, Direction direction) {
		Direction registeredDirection = _exactDirections.get(name);

		if ((registeredDirection == direction) ||
			(registeredDirection == Direction.DUPLEX)) {

			return true;
		}

		if (registeredDirection != null) {
			return false;
		}

		for (Map.Entry<String, Direction> entry :
				_postfixDirections.entrySet()) {

			String postfix = entry.getKey();

			if (name.endsWith(postfix)) {
				registeredDirection = entry.getValue();

				if ((registeredDirection == direction) ||
					(registeredDirection == Direction.DUPLEX)) {

					return true;
				}
			}
		}

		for (Map.Entry<String, Direction> entry :
				_prefixDirections.entrySet()) {

			String prefix = entry.getKey();

			if (name.startsWith(prefix)) {
				registeredDirection = entry.getValue();

				if ((registeredDirection == direction) ||
					(registeredDirection == Direction.DUPLEX)) {

					return true;
				}
			}
		}

		return false;
	}

	public static void registerDistributed(Class<?> clazz) {
		processDistributed(clazz, true);
	}

	public static void registerDistributed(
		String name, Direction direction, MatchType matchType) {

		if (matchType.equals(MatchType.POSTFIX)) {
			_postfixDirections.put(name, direction);
		}
		else if (matchType.equals(MatchType.PREFIX)) {
			_prefixDirections.put(name, direction);
		}
		else {
			_exactDirections.put(name, direction);
		}
	}

	public static void unregisterDistributed(Class<?> clazz) {
		processDistributed(clazz, false);
	}

	public static boolean unregisterDistributed(
		String name, Direction direction, MatchType matchType) {

		if (matchType.equals(MatchType.POSTFIX)) {
			if (direction == null) {
				direction = _postfixDirections.remove(name);

				return direction != null;
			}

			return _postfixDirections.remove(name, direction);
		}
		else if (matchType.equals(MatchType.PREFIX)) {
			if (direction == null) {
				direction = _prefixDirections.remove(name);

				return direction != null;
			}

			return _prefixDirections.remove(name, direction);
		}
		else {
			if (direction == null) {
				direction = _exactDirections.remove(name);

				return direction != null;
			}

			return _exactDirections.remove(name, direction);
		}
	}

	protected static void processDistributed(Class<?> clazz, boolean register) {
		Queue<Class<?>> queue = new LinkedList<Class<?>>();

		queue.offer(clazz);

		Class<?> currentClass = null;

		while ((currentClass = queue.poll()) != null) {
			Field[] fields = currentClass.getDeclaredFields();

			for (Field field : fields) {
				Distributed distributed = field.getAnnotation(
					Distributed.class);

				if (distributed == null) {
					continue;
				}

				int modifiers = field.getModifiers();

				if (!Modifier.isPublic(modifiers) ||
					!Modifier.isStatic(modifiers) ||
					!Modifier.isFinal(modifiers) ||
					(field.getType() != String.class)) {

					continue;
				}

				try {
					String name = (String)field.get(null);

					if (register) {
						registerDistributed(
							name, distributed.direction(),
							distributed.matchType());
					}
					else {
						unregisterDistributed(
							name, distributed.direction(),
							distributed.matchType());
					}
				}
				catch (Throwable t) {
					throw new RuntimeException(t);
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

	private static final ConcurrentMap<String, Direction> _exactDirections =
		new ConcurrentHashMap<String, Direction>();
	private static final ConcurrentMap<String, Direction> _postfixDirections =
		new ConcurrentHashMap<String, Direction>();
	private static final ConcurrentMap<String, Direction> _prefixDirections =
		new ConcurrentHashMap<String, Direction>();

}