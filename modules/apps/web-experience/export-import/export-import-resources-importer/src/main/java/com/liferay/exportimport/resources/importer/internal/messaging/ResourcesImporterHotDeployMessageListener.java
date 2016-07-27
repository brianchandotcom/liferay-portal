/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.resources.importer.internal.messaging;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.resources.importer.internal.constants.ResourcesImporterDestinationNames;
import com.liferay.exportimport.resources.importer.internal.util.Importer;
import com.liferay.exportimport.resources.importer.internal.util.ImporterException;
import com.liferay.exportimport.resources.importer.internal.util.ImporterFactory;
import com.liferay.exportimport.resources.importer.internal.util.PluginPackageProperties;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.HotDeployMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Ryan Park
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {"destination.name=" + DestinationNames.HOT_DEPLOY},
	service = MessageListener.class
)
public class ResourcesImporterHotDeployMessageListener
	extends HotDeployMessageListener {

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ServletContext.class, null,
			new ServiceReferenceMapper<String, ServletContext>() {

				@Override
				public void map(
					ServiceReference<ServletContext> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					try {
						ServletContext servletContext =
							bundleContext.getService(serviceReference);

						String servletContextName = GetterUtil.getString(
							servletContext.getServletContextName());

						emitter.emit(servletContextName);
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				}

			});

		registerDestination();
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();

			_destination.destroy();
		}

		_serviceTrackerMap.close();
	}

	protected void initialize(Message message) throws Exception {
		String servletContextName = message.getString("servletContextName");

		ServletContext servletContext = _serviceTrackerMap.getService(
			servletContextName);

		if (servletContext == null) {
			return;
		}

		PluginPackageProperties pluginPackageProperties =
			new PluginPackageProperties(servletContext);

		String resourcesDir = pluginPackageProperties.getResourcesDir();

		if ((servletContext.getResource(ImporterFactory.RESOURCES_DIR) ==
				null) &&
			(servletContext.getResource(ImporterFactory.TEMPLATES_DIR) ==
				null) &&
			Validator.isNull(resourcesDir)) {

			return;
		}

		List<Company> companies = _companyLocalService.getCompanies();

		try {
			ExportImportThreadLocal.setLayoutImportInProcess(true);
			ExportImportThreadLocal.setPortletImportInProcess(true);

			for (Company company : companies) {
				_importResources(
					company, servletContext, pluginPackageProperties,
					message.getResponseId());
			}
		}
		finally {
			ExportImportThreadLocal.setLayoutImportInProcess(false);
			ExportImportThreadLocal.setPortletImportInProcess(false);
		}
	}

	@Override
	protected void onDeploy(Message message) throws Exception {
		initialize(message);
	}

	protected synchronized void registerDestination() {
		if ((_bundleContext != null) && (_serviceRegistration == null) &&
			(_destinationFactory != null)) {

			DestinationConfiguration destinationConfiguration =
				new DestinationConfiguration(
					DestinationConfiguration.DESTINATION_TYPE_SERIAL,
					ResourcesImporterDestinationNames.RESOURCES_IMPORTER);

			_destination = _destinationFactory.createDestination(
				destinationConfiguration);

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			dictionary.put("destination.name", _destination.getName());

			_serviceRegistration = _bundleContext.registerService(
				Destination.class, _destination, dictionary);
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.HOT_DEPLOY + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.STATIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setDestinationFactory(
		DestinationFactory destinationFactory) {

		_destinationFactory = destinationFactory;

		registerDestination();
	}

	@Reference(unbind = "-")
	protected void setImporterFactory(ImporterFactory importerFactory) {
		_importerFactory = importerFactory;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.exportimport.service)(release.schema.version=1.0.0))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	private void _importResources(
			Company company, ServletContext servletContext,
			PluginPackageProperties pluginPackageProperties,
			String messageResponseId)
		throws Exception {

		long companyId = CompanyThreadLocal.getCompanyId();

		try {
			CompanyThreadLocal.setCompanyId(company.getCompanyId());

			Importer importer = _importerFactory.createImporter(
				company.getCompanyId(), servletContext,
				pluginPackageProperties);

			if (!importer.isDeveloperModeEnabled() && importer.isExisting() &&
				!importer.isCompanyGroup()) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Group or layout set prototype already exists " +
							"for company " + company.getWebId());
				}

				return;
			}

			long startTime = 0;

			if (_log.isInfoEnabled()) {
				startTime = System.currentTimeMillis();
			}

			importer.importResources();

			if (_log.isInfoEnabled()) {
				long endTime = System.currentTimeMillis() - startTime;

				_log.info(
					"Importing resources from " +
						servletContext.getServletContextName() +
						" to group " + importer.getGroupId() + " takes " +
							endTime + " ms");
			}

			Message message = new Message();

			message.put("companyId", company.getCompanyId());
			message.put(
				"servletContextName", servletContext.getServletContextName());
			message.put("targetClassName", importer.getTargetClassName());
			message.put("targetClassPK", importer.getTargetClassPK());

			if (Validator.isNotNull(messageResponseId)) {
				Map<String, Object> responseMap = new HashMap<>();

				responseMap.put("groupId", importer.getTargetClassPK());

				message.setPayload(responseMap);

				message.setResponseId(messageResponseId);
			}

			MessageBusUtil.sendMessage("liferay/resources_importer", message);
		}
		catch (ImporterException ie) {
			Message message = new Message();

			message.put("companyId", company.getCompanyId());
			message.put("error", ie.getMessage());
			message.put(
				"servletContextName", servletContext.getServletContextName());
			message.put(
				"targetClassName",
				pluginPackageProperties.getTargetClassName());
			message.put("targetClassPK", 0);

			MessageBusUtil.sendMessage("liferay/resources_importer", message);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcesImporterHotDeployMessageListener.class);

	private BundleContext _bundleContext;
	private CompanyLocalService _companyLocalService;
	private Destination _destination;
	private DestinationFactory _destinationFactory;
	private ImporterFactory _importerFactory;
	private ServiceRegistration<Destination> _serviceRegistration;
	private ServiceTrackerMap<String, ServletContext> _serviceTrackerMap;

}