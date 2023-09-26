/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.object.deployer;

import com.liferay.headless.builder.internal.object.related.models.DeleteOnDisassociateObjectRelatedModelsProvider;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistrarHelper;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Correa
 */
@Component(service = ObjectDefinitionDeployer.class)
public class APIPropertyObjectDefinitionDeployerImpl
	implements ObjectDefinitionDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		if (!_isAPIPropertyObjectDefinition(objectDefinition)) {
			return Collections.emptyList();
		}

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			StringBundler.concat(
				"(&(objectClass=", ObjectRelatedModelsProvider.class.getName(),
				")(",
				ObjectRelatedModelsProviderRegistrarHelper.
					OBJECT_DEFINITION_ERC_KEY,
				"=L_API_PROPERTY)(",
				ObjectRelatedModelsProviderRegistrarHelper.
					RELATIONSHIP_TYPE_KEY,
				"=", ObjectRelationshipConstants.TYPE_ONE_TO_MANY, "))"),
			new ObjectRelatedModelsProviderServiceTrackerCustomizer());

		return Collections.emptyList();
	}

	@Override
	public void undeploy(ObjectDefinition objectDefinition) {
		if (!_isAPIPropertyObjectDefinition(objectDefinition)) {
			return;
		}

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}
	}

	private boolean _isAPIPropertyObjectDefinition(
		ObjectDefinition objectDefinition) {

		return Objects.equals(
			objectDefinition.getExternalReferenceCode(), "L_API_PROPERTY");
	}

	private BundleContext _bundleContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelatedModelsProviderRegistrarHelper
		_objectRelatedModelsProviderRegistrarHelper;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private ServiceTracker
		<ObjectRelatedModelsProvider, ObjectRelatedModelsProvider>
			_serviceTracker;

	private class ObjectRelatedModelsProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ObjectRelatedModelsProvider, ObjectRelatedModelsProvider> {

		@Override
		public ObjectRelatedModelsProvider addingService(
			ServiceReference<ObjectRelatedModelsProvider> serviceReference) {

			ObjectRelatedModelsProvider<ObjectEntry>
				objectRelatedModelsProvider = _bundleContext.getService(
					serviceReference);

			if (objectRelatedModelsProvider instanceof
					DeleteOnDisassociateObjectRelatedModelsProvider) {

				return objectRelatedModelsProvider;
			}

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty(Constants.SERVICE_RANKING));

			ObjectRelatedModelsProvider<ObjectEntry>
				deleteOnDisassociateObjectRelatedModelsProvider =
					new DeleteOnDisassociateObjectRelatedModelsProvider(
						_objectEntryLocalService, objectRelatedModelsProvider,
						_objectRelationshipLocalService);

			ServiceRegistration<ObjectRelatedModelsProvider<?>>
				serviceRegistration =
					(ServiceRegistration<ObjectRelatedModelsProvider<?>>)
						_objectRelatedModelsProviderRegistrarHelper.register(
							_bundleContext,
							_objectDefinitionLocalService.
								fetchObjectDefinitionByExternalReferenceCode(
									"L_API_PROPERTY",
									objectRelatedModelsProvider.getCompanyId()),
							deleteOnDisassociateObjectRelatedModelsProvider,
							Math.min(Integer.MAX_VALUE, serviceRanking + 100));

			_serviceRegistrations.put(serviceReference, serviceRegistration);

			return deleteOnDisassociateObjectRelatedModelsProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<ObjectRelatedModelsProvider> serviceReference,
			ObjectRelatedModelsProvider objectRelatedModelsProvider) {

			removedService(serviceReference, objectRelatedModelsProvider);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ObjectRelatedModelsProvider> serviceReference,
			ObjectRelatedModelsProvider objectRelatedModelsProvider) {

			ServiceRegistration<ObjectRelatedModelsProvider<?>>
				serviceRegistration = _serviceRegistrations.remove(
					serviceReference);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private final Map
			<ServiceReference<ObjectRelatedModelsProvider>,
			 ServiceRegistration<ObjectRelatedModelsProvider<?>>>
				_serviceRegistrations = new ConcurrentHashMap<>();

	}

}