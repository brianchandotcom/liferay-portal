/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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