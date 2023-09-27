/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models;

import com.liferay.object.model.ObjectDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Correa
 */
public interface ObjectRelatedModelsProviderRegistrarHelper {

	public static final String KEY_OBJECT_DEFINITION_ERC =
		"object.definition.erc";

	public static final String KEY_RELATIONSHIP_TYPE = "relationship.type";

	public default ServiceRegistration<?> register(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider) {

		return register(
			bundleContext, objectDefinition, objectRelatedModelsProvider, null);
	}

	public ServiceRegistration<?> register(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider,
		Integer serviceRanking);

}