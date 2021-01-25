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
package com.liferay.headless.admin.content.internal.dto.v1_0.util;

import com.liferay.headless.admin.content.dto.v1_0.StructuredContentVersionInformation;
import com.liferay.headless.admin.content.dto.v1_0.VersionInformation;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Luis Miguel Barcos
 */
public class StructuredContentVersionInformationBuilder {


	public StructuredContentVersionInformationBuilder(
		StructuredContent structuredContent) {

		_structuredContent = structuredContent;
		_structuredContentVersionInformation =
			new StructuredContentVersionInformation();
	}

	public StructuredContentVersionInformation build() {
		_buildStructuredContentVersionInformation(
			_structuredContent, _structuredContentVersionInformation);

		return _structuredContentVersionInformation;
	}

	public StructuredContentVersionInformationBuilder setVersionInformation(
		VersionInformation versionInformation) {

		_structuredContentVersionInformation.setVersionInformation(
			versionInformation);

		return this;
	}

	private void _buildStructuredContentVersionInformation(
		StructuredContent source, StructuredContentVersionInformation target) {

		Class<? extends StructuredContent> sourceClass = source.getClass();

		Class<?> sourceClassSuperclass = sourceClass.getSuperclass();

		Field[] sourceFields = sourceClassSuperclass.getDeclaredFields();

		Class<? extends StructuredContentVersionInformation> targetClass =
			target.getClass();

		Class<?> targetClassSuperclass = targetClass.getSuperclass();

		Field[] targetFields = targetClassSuperclass.getDeclaredFields();

		Stream<Field> targetFieldsStream = Arrays.stream(targetFields);

		targetFieldsStream.forEach(
			targetField -> {
				Stream<Field> sourceFieldsStream = Arrays.stream(sourceFields);

				Stream<Field> commonSourceTargetFields =
					sourceFieldsStream.filter(
						sourceField -> {
							String targetFieldName = targetField.getName();

							return targetFieldName.equals(
								sourceField.getName());
						});

				commonSourceTargetFields.forEach(
					sourceField -> {
						try {
							sourceField.setAccessible(true);
							targetField.setAccessible(true);
							targetField.set(target, sourceField.get(source));
						}
						catch (IllegalAccessException illegalAccessException) {
							throw new RuntimeException(illegalAccessException);
						}
					});
			});
	}

	private final StructuredContent _structuredContent;
	private final StructuredContentVersionInformation
		_structuredContentVersionInformation;

}
