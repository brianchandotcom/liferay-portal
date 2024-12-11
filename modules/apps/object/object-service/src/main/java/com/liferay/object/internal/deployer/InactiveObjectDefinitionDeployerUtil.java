/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.deployer;

import com.liferay.object.internal.related.models.ObjectEntry1to1ObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntry1toMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntryMtoMObjectRelatedModelsProviderImpl;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistryUtil;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael Bowerman
 */
public class InactiveObjectDefinitionDeployerUtil {

	public static List<ServiceRegistration<?>> deploy(
		BundleContext bundleContext, ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectDefinition objectDefinition) {

		return ListUtil.fromArray(
			ObjectRelatedModelsProviderRegistryUtil.register(
				bundleContext, objectDefinition,
				new ObjectEntryMtoMObjectRelatedModelsProviderImpl(
					objectDefinition, objectEntryService,
					objectRelationshipLocalService)),
			ObjectRelatedModelsProviderRegistryUtil.register(
				bundleContext, objectDefinition,
				new ObjectEntry1toMObjectRelatedModelsProviderImpl(
					objectDefinition, objectEntryService,
					objectFieldLocalService, objectRelationshipLocalService)),
			ObjectRelatedModelsProviderRegistryUtil.register(
				bundleContext, objectDefinition,
				new ObjectEntry1to1ObjectRelatedModelsProviderImpl(
					objectDefinition, objectEntryService,
					objectFieldLocalService, objectRelationshipLocalService)));
	}

}