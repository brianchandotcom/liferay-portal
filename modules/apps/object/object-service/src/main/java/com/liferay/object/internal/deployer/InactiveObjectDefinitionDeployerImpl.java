/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.deployer;

import com.liferay.object.deployer.InactiveObjectDefinitionDeployer;
import com.liferay.object.internal.related.models.ObjectEntry1to1ObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntry1toMObjectRelatedModelsProviderImpl;
import com.liferay.object.internal.related.models.ObjectEntryMtoMObjectRelatedModelsProviderImpl;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.related.models.ManyToOneObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
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
public class InactiveObjectDefinitionDeployerImpl
	implements InactiveObjectDefinitionDeployer {

	public InactiveObjectDefinitionDeployerImpl(
		BundleContext bundleContext, ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_bundleContext = bundleContext;
		_objectEntryService = objectEntryService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		return ListUtil.fromArray(
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new ObjectEntry1to1ObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryService,
					_objectFieldLocalService, _objectRelationshipLocalService),
				null),
			_bundleContext.registerService(
				new String[] {
					ManyToOneObjectRelatedModelsProvider.class.getName(),
					ObjectRelatedModelsProvider.class.getName()
				},
				new ObjectEntry1toMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryService,
					_objectFieldLocalService, _objectRelationshipLocalService),
				null),
			_bundleContext.registerService(
				ObjectRelatedModelsProvider.class,
				new ObjectEntryMtoMObjectRelatedModelsProviderImpl(
					objectDefinition, _objectEntryService,
					_objectRelationshipLocalService),
				null));
	}

	private final BundleContext _bundleContext;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}