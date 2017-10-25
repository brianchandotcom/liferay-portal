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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver.AttributePublisher;

import java.net.URI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;

/**
 * @author Carlos Sierra Andrés
 */
public class AttributePublisherImpl implements AttributePublisher {

	@Override
	public AttributeValue buildBase64(String value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeBase64Value(value));
	}

	@Override
	public AttributeValue buildBoolean(boolean value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeValue(value));
	}

	@Override
	public AttributeValue buildDateTime(Date value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeValue(new DateTime(value)));
	}

	@Override
	public AttributeValue buildInt(int value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeValue(value));
	}

	@Override
	public AttributeValue buildString(String value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeValue(value));
	}

	@Override
	public AttributeValue buildURI(URI value) {
		return new AttributeValueWrapper(
			OpenSamlUtil.buildAttributeValue(value));
	}

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	@Override
	public void publish(String name, AttributeValue... attributeValues) {
		publish(name, null, attributeValues);
	}

	@Override
	public void publish(
		String name, String nameFormat, AttributeValue... attributeValues) {

		publish(name, null, nameFormat, attributeValues);
	}

	@Override
	public void publish(
		String name, String friendlyName, String nameFormat,
		AttributeValue... attributeValues) {

		Attribute attribute = OpenSamlUtil.buildAttribute(
			name, friendlyName, nameFormat);

		List<XMLObject> attributeXmlObjects =
			attribute.getAttributeValues();

		Stream<AttributeValue> attributeValuesStream = Arrays.stream(
			attributeValues);

		attributeXmlObjects.addAll(
			attributeValuesStream.map(
				AttributeValueWrapper.class::cast
			).map(
				AttributeValueWrapper::getXmlObject
			).collect(
				Collectors.toList()
			)
		);

		_attributes.add(attribute);
	}

	private final List<Attribute> _attributes = new ArrayList<>();

	private static class AttributeValueWrapper implements AttributeValue {

		public AttributeValueWrapper(XMLObject xmlObject) {
			_xmlObject = xmlObject;
		}

		public XMLObject getXmlObject() {
			return _xmlObject;
		}

		private final XMLObject _xmlObject;

	}

}