/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.resolver.AttributeResolver;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.Attribute;

/**
 * @author Carlos Sierra Andrés
 */
public class AttributePublisherImpl
	implements AttributeResolver.AttributePublisher {

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	@Override
	public void publish(String name, String nameFormat, String... values) {
		Attribute attribute = OpenSamlUtil.buildAttribute(
			name, null, nameFormat);

		List<XMLObject> attributeXmlObjects = attribute.getAttributeValues();

		attributeXmlObjects.addAll(
			TransformUtil.transformToList(
				values, OpenSamlUtil::buildAttributeValue));

		_attributes.add(attribute);
	}

	private final List<Attribute> _attributes = new ArrayList<>();

}