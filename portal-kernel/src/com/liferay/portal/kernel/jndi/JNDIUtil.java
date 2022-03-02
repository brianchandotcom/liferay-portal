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

package com.liferay.portal.kernel.jndi;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class JNDIUtil {

	public static Object lookup(Context context, String location)
		throws NamingException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Set<ClassLoader> familyClassLoaders = new HashSet<>();

		ClassLoader classLoader = contextClassLoader;

		do {
			familyClassLoaders.add(classLoader);
		}
		while ((classLoader = classLoader.getParent()) != null);

		List<ClassLoader> lookupClassLoaders = new ArrayList<>();

		classLoader = contextClassLoader;

		do {
			lookupClassLoaders.add(classLoader);

			// Add branched declaring class loader

			Class<?> clazz = classLoader.getClass();

			ClassLoader declaringClassLoader = clazz.getClassLoader();

			if (!familyClassLoaders.contains(declaringClassLoader)) {
				lookupClassLoaders.add(declaringClassLoader);
			}

			// Add aggregated foreign class loaders

			if (classLoader instanceof AggregateClassLoader) {
				AggregateClassLoader aggregateClassLoader =
					(AggregateClassLoader)classLoader;

				for (ClassLoader currentClassLoader :
						aggregateClassLoader.getClassLoaders()) {

					if (!familyClassLoaders.contains(currentClassLoader)) {
						lookupClassLoaders.add(currentClassLoader);
					}
				}
			}
		}
		while ((classLoader = classLoader.getParent()) != null);

		NamingException namingException1 = null;

		for (ClassLoader lookupClassLoader : lookupClassLoaders) {
			try {
				return _lookup(context, location, lookupClassLoader);
			}
			catch (NamingException namingException2) {
				if (namingException1 == null) {
					namingException1 = namingException2;
				}
				else {
					namingException1.addSuppressed(namingException2);
				}
			}
		}

		throw namingException1;
	}

	private static Object _lookup(Context context, String location)
		throws NamingException {

		if (_log.isDebugEnabled()) {
			_log.debug("Lookup " + location);
		}

		Object object = null;

		try {
			object = context.lookup(location);
		}
		catch (NamingException namingException1) {

			// java:comp/env/ObjectName to ObjectName

			if (location.contains("java:comp/env/")) {
				try {
					String newLocation = StringUtil.removeSubstring(
						location, "java:comp/env/");

					if (_log.isDebugEnabled()) {
						_log.debug(namingException1.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
				catch (NamingException namingException2) {

					// java:comp/env/ObjectName to java:ObjectName

					String newLocation = StringUtil.removeSubstring(
						location, "comp/env/");

					if (_log.isDebugEnabled()) {
						_log.debug(namingException2.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
			}
			else if (location.contains("java:")) {

				// java:ObjectName to ObjectName

				try {
					String newLocation = StringUtil.removeSubstring(
						location, "java:");

					if (_log.isDebugEnabled()) {
						_log.debug(namingException1.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
				catch (NamingException namingException2) {

					// java:ObjectName to java:comp/env/ObjectName

					String newLocation = StringUtil.replace(
						location, "java:", "java:comp/env/");

					if (_log.isDebugEnabled()) {
						_log.debug(namingException2.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
			}
			else if (!location.contains("java:")) {

				// ObjectName to java:ObjectName

				try {
					String newLocation = "java:" + location;

					if (_log.isDebugEnabled()) {
						_log.debug(namingException1.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
				catch (NamingException namingException2) {

					// ObjectName to java:comp/env/ObjectName

					String newLocation = "java:comp/env/" + location;

					if (_log.isDebugEnabled()) {
						_log.debug(namingException2.getMessage());
						_log.debug("Attempt " + newLocation);
					}

					object = context.lookup(newLocation);
				}
			}
			else {
				throw new NamingException();
			}
		}

		return object;
	}

	private static Object _lookup(
			Context context, String location, ClassLoader classLoader)
		throws NamingException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		try {
			return _lookup(context, location);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JNDIUtil.class);

}