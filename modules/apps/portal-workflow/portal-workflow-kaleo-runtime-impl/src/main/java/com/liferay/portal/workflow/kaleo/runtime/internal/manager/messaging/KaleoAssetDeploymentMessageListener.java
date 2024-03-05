/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.manager.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
import com.liferay.portal.workflow.kaleo.runtime.manager.PortalKaleoManager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "destination.name=" + KaleoRuntimeDestinationNames.WORKFLOW_DEFINITION_LINK,
	service = MessageListener.class
)
public class KaleoAssetDeploymentMessageListener extends BaseMessageListener {

	public void setPortalKaleoManager(PortalKaleoManager portalKaleoManager) {
		_portalKaleoManager = portalKaleoManager;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				KaleoRuntimeDestinationNames.WORKFLOW_DEFINITION_LINK));

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String assetClassName = (String)message.get("ASSET_CLASS_NAME");

		_portalKaleoManager.deployDefaultDefinitionLink(assetClassName);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private PortalKaleoManager _portalKaleoManager;

	private ServiceRegistration<Destination> _serviceRegistration;

}