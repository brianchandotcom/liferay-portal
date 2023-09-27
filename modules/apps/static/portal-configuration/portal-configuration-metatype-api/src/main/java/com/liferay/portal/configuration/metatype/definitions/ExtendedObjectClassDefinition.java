/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.definitions;

import java.util.Map;
import java.util.Set;

import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Iván Zaera
 */
public interface ExtendedObjectClassDefinition extends ObjectClassDefinition {

	@Override
	public ExtendedAttributeDefinition[] getAttributeDefinitions(int filter);

	public Map<String, String> getExtensionAttributes(String uri);

	public Set<String> getExtensionUris();

}