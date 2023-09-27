/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.definitions.equinox.internal;

import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.eclipse.equinox.metatype.Extendable;

import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Iván Zaera
 */
public class EquinoxExtendedObjectClassDefinition
	implements ExtendedObjectClassDefinition {

	public EquinoxExtendedObjectClassDefinition(
		ObjectClassDefinition objectClassDefinition) {

		_objectClassDefinition = objectClassDefinition;

		if (!(objectClassDefinition instanceof Extendable)) {
			Class<?> clazz = objectClassDefinition.getClass();

			throw new IllegalArgumentException(
				clazz.getName() + " does not implement " +
					Extendable.class.getName());
		}

		_extendable = (Extendable)objectClassDefinition;
	}

	@Override
	public ExtendedAttributeDefinition[] getAttributeDefinitions(int filter) {
		if (_extendedAttributeDefinitions == null) {
			AttributeDefinition[] attributeDefinitions =
				_objectClassDefinition.getAttributeDefinitions(filter);

			_extendedAttributeDefinitions =
				new ExtendedAttributeDefinition[attributeDefinitions.length];

			for (int i = 0; i < attributeDefinitions.length; i++) {
				_extendedAttributeDefinitions[i] =
					new EquinoxExtendedAttributeDefinition(
						attributeDefinitions[i]);
			}
		}

		return _extendedAttributeDefinitions;
	}

	@Override
	public String getDescription() {
		return _objectClassDefinition.getDescription();
	}

	@Override
	public Map<String, String> getExtensionAttributes(String uri) {
		Set<String> extensionUris = _extendable.getExtensionUris();

		if (extensionUris.contains(uri)) {
			return _extendable.getExtensionAttributes(uri);
		}

		return Collections.emptyMap();
	}

	@Override
	public Set<String> getExtensionUris() {
		return _extendable.getExtensionUris();
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		return _objectClassDefinition.getIcon(size);
	}

	@Override
	public String getID() {
		return _objectClassDefinition.getID();
	}

	@Override
	public String getName() {
		return _objectClassDefinition.getName();
	}

	private final Extendable _extendable;
	private ExtendedAttributeDefinition[] _extendedAttributeDefinitions;
	private final ObjectClassDefinition _objectClassDefinition;

}