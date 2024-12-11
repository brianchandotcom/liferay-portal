/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Correa
 */
public class ObjectRelatedModelsProviderRegistrarHelperUtil {

	public static ServiceRegistration<?> register(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider) {

		return register(
			bundleContext, objectDefinition, objectRelatedModelsProvider, null);
	}

	public static ServiceRegistration<?> register(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider,
		Integer serviceRanking) {

		return bundleContext.registerService(
			ObjectRelatedModelsProvider.class, objectRelatedModelsProvider,
			HashMapDictionaryBuilder.<String, Object>put(
				Constants.SERVICE_RANKING, () -> serviceRanking
			).put(
				ObjectDefinitionConstants.KEY_OBJECT_DEFINITION_ERC,
				objectDefinition.getExternalReferenceCode()
			).put(
				ObjectRelationshipConstants.KEY_RELATIONSHIP_TYPE,
				objectRelatedModelsProvider.getObjectRelationshipType()
			).build());
	}

}