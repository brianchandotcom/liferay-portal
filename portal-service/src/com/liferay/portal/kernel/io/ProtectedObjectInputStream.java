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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mika Koivisto
 */
public class ProtectedObjectInputStream extends ObjectInputStream {

	public ProtectedObjectInputStream(InputStream in) throws IOException {
		super(in);
	}

	protected Class<?> doResolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException, IOException {

		return super.resolveClass(osc);
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException, IOException {

		for (Pattern restrictedClassPattern : _restrictedClassesPatterns) {
			Matcher restrictedClassMatcher = restrictedClassPattern.matcher(
				osc.getName());

			if (restrictedClassMatcher.find()) {
				throw new InvalidClassException(
					"Trying to load restricted class: " + osc.getName());
			}
		}

		boolean allowedClass = false;

		for (Pattern allowedClassPattern : _allowedClassesPatterns) {
			Matcher allowedClassMatcher = allowedClassPattern.matcher(
				osc.getName());

			if (allowedClassMatcher.find()) {
				allowedClass = true;
				break;
			}
		}

		if (!allowedClass) {
			throw new InvalidClassException(
				"Trying to load restricted class: " + osc.getName());
		}

		return doResolveClass(osc);
	}

	private static final Pattern[] _allowedClassesPatterns;
	private static final Pattern[] _restrictedClassesPatterns;

	static {
		String[] allowedClassesRegex = StringUtil.split(
			System.getProperty(
				ProtectedObjectInputStream.class.getName() +
					".allowed.classes.regex"));

		List<Pattern> patterns = new ArrayList<>(allowedClassesRegex.length);

		for (String allowedClassRegex : allowedClassesRegex) {
			patterns.add(Pattern.compile(allowedClassRegex));
		}

		_allowedClassesPatterns = patterns.toArray(
			new Pattern[allowedClassesRegex.length]);

		String[] restrictedClassesRegex = StringUtil.split(
			System.getProperty(
				ProtectedObjectInputStream.class.getName() +
					".restricted.classes.regex"));

		patterns = new ArrayList<>(restrictedClassesRegex.length);

		for (String restrictedClassRegex : restrictedClassesRegex) {
			patterns.add(Pattern.compile(restrictedClassRegex));
		}

		_restrictedClassesPatterns = patterns.toArray(
			new Pattern[restrictedClassesRegex.length]);
	}

}