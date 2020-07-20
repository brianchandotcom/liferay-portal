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

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;

import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.util.List;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntitySerializerModifier extends BeanSerializerModifier {

	@Override
	public List<BeanPropertyWriter> changeProperties(
		SerializationConfig config, BeanDescription beanDescription,
		List<BeanPropertyWriter> beanProperties) {

		return super.changeProperties(config, beanDescription, beanProperties);
	}

	@Override
	public JsonSerializer<?> modifyArraySerializer(
		SerializationConfig config, ArrayType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyArraySerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyCollectionLikeSerializer(
		SerializationConfig config, CollectionLikeType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyCollectionLikeSerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyCollectionSerializer(
		SerializationConfig config, CollectionType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyCollectionSerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyEnumSerializer(
		SerializationConfig config, JavaType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyEnumSerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyKeySerializer(
		SerializationConfig config, JavaType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyKeySerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyMapLikeSerializer(
		SerializationConfig config, MapLikeType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyMapLikeSerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifyMapSerializer(
		SerializationConfig config, MapType valueType,
		BeanDescription beanDescription, JsonSerializer<?> serializer) {

		return super.modifyMapSerializer(
			config, valueType, beanDescription, serializer);
	}

	@Override
	public JsonSerializer<?> modifySerializer(
		SerializationConfig config, BeanDescription beanDescription,
		JsonSerializer<?> serializer) {

		Class<?> beanClass = beanDescription.getBeanClass();

		if (beanClass.equals(ExtendedEntity.class)) {
			return new ExtendedEntitySerializer(
				(JsonSerializer<ExtendedEntity>)serializer);
		}

		return serializer;
	}

	@Override
	public List<BeanPropertyWriter> orderProperties(
		SerializationConfig config, BeanDescription beanDescription,
		List<BeanPropertyWriter> beanProperties) {

		return super.orderProperties(config, beanDescription, beanProperties);
	}

	@Override
	public BeanSerializerBuilder updateBuilder(
		SerializationConfig config, BeanDescription beanDescription,
		BeanSerializerBuilder builder) {

		return super.updateBuilder(config, beanDescription, builder);
	}

}