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

package com.liferay.portal.test.aspects;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.apache.bcel.classfile.ClassParser;
import org.aspectj.apache.bcel.classfile.JavaClass;
import org.aspectj.weaver.CrosscuttingMembersSet;
import org.aspectj.weaver.ResolvedType;
import org.aspectj.weaver.bcel.BcelObjectType;
import org.aspectj.weaver.tools.GeneratedClassHandler;
import org.aspectj.weaver.tools.WeavingAdaptor;

/**
 * @author Shuyang Zhou
 */
public class URLWeavingAdapter extends WeavingAdaptor {

	public URLWeavingAdapter(URL[] urls, Class<?>[] aspectClasses) {
		super(null, urls, new URL[0]);

		generatedClassHandler = new RecordGeneratedClassHandler();

		for (Class<?> aspectClass : aspectClasses) {
			_addAspectClass(aspectClass);
		}

		weaver.prepareForWeave();
	}

	public byte[] removeGeneratedClassDate(String name) {
		return _generatedClasses.remove(name);
	}

	private void _addAspectClass(Class<?> aspectClass) {
		Class<?> currentClass = aspectClass;

		while (true) {
			Class<?>[] interfaceClasses = currentClass.getInterfaces();

			for (Class<?> interfaceClass : interfaceClasses) {
				JavaClass javaClass = _classToJavaClass(interfaceClass);

				if (javaClass != null) {
					bcelWorld.addSourceObjectType(javaClass, false);
				}
			}

			currentClass = currentClass.getSuperclass();

			if (currentClass != null) {
				JavaClass javaClass = _classToJavaClass(currentClass);

				if (javaClass != null) {
					bcelWorld.addSourceObjectType(javaClass, false);
				}
			}
			else {
				break;
			}
		}

		JavaClass javaClass = _classToJavaClass(aspectClass);

		BcelObjectType bcelObjectType = bcelWorld.addSourceObjectType(
			javaClass, false);

		ResolvedType resolvedType = bcelObjectType.getResolvedTypeX();

		if (resolvedType.isAspect()) {
			CrosscuttingMembersSet crosscuttingMembersSet =
				bcelWorld.getCrosscuttingMembersSet();

			crosscuttingMembersSet.addOrReplaceAspect(resolvedType);
		}
		else {
			throw new IllegalArgumentException(
				"Class object " + aspectClass + " is not an aspect");
		}
	}

	private JavaClass _classToJavaClass(Class<?> aspectClass) {
		ClassLoader aspectClassLoader = aspectClass.getClassLoader();

		if (aspectClassLoader == null) {
			aspectClassLoader = ClassLoader.getSystemClassLoader();
		}

		String resourcePath = aspectClass.getName();

		resourcePath = StringUtil.replace(resourcePath, '.', '/') + ".class";

		ByteArrayInputStream byteArrayInputStream = null;

		InputStream inputStream = aspectClassLoader.getResourceAsStream(
			resourcePath);

		if (inputStream instanceof ByteArrayInputStream) {
			byteArrayInputStream = (ByteArrayInputStream)inputStream;
		}
		else {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

				byte[] classData =
					unsyncByteArrayOutputStream.unsafeGetByteArray();

				byteArrayInputStream = new ByteArrayInputStream(
					classData, 0, unsyncByteArrayOutputStream.size());
			}
			catch (IOException ioe) {
				throw new RuntimeException("Unable to reload class data", ioe);
			}
		}

		ClassParser classParser = new ClassParser(
			byteArrayInputStream, aspectClass.getSimpleName() + ".class");

		try {
			return classParser.parse();
		}
		catch (Exception e) {
			throw new RuntimeException("Unable to parse class data", e);
		}
	}

	private final Map<String, byte[]> _generatedClasses = new HashMap<>();

	private class RecordGeneratedClassHandler implements GeneratedClassHandler {

		@Override
		public void acceptClass(
			String name, byte[] originalBytes, byte[] weavedBytes) {

			_generatedClasses.put(name, weavedBytes);
		}

	}

}