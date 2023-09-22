/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.related.models;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistrarHelper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(service = ObjectRelatedModelsProviderRegistrarHelper.class)
public class ObjectRelatedModelsProviderRegistrarHelperImpl
	implements ObjectRelatedModelsProviderRegistrarHelper {

	@Override
	public ServiceRegistration<?> register(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider,
		Integer serviceRanking) {

		return bundleContext.registerService(
			ObjectRelatedModelsProvider.class, objectRelatedModelsProvider,
			HashMapDictionaryBuilder.<String, Object>put(
				Constants.SERVICE_RANKING, () -> serviceRanking
			).put(
				ObjectRelatedModelsProviderRegistrarHelper.
					OBJECT_DEFINITION_ERC_KEY,
				objectDefinition.getExternalReferenceCode()
			).put(
				ObjectRelatedModelsProviderRegistrarHelper.
					RELATIONSHIP_TYPE_KEY,
				objectRelatedModelsProvider.getObjectRelationshipType()
			).build());
	}

}