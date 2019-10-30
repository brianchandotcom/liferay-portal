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

package com.liferay.portal.settings.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.settings.portlet.action.PortalSettingsFormContributor;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	service = PortalSettingsContributorServiceTrackerCustomizer.class
)
public class PortalSettingsContributorServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<PortalSettingsFormContributor, PortalSettingsFormContributor> {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, PortalSettingsFormContributor.class, this);
	}

	@Override
	public PortalSettingsFormContributor addingService(
		ServiceReference<PortalSettingsFormContributor> reference) {

		final PortalSettingsFormContributor portalSettingsFormContributor =
			_bundleContext.getService(reference);

		MVCActionCommandServiceRegistrationHolder
			mvcActionCommandServiceRegistrationHolder =
				new MVCActionCommandServiceRegistrationHolder();

		Optional<String> deleteMVCActionCommandNameOptional =
			portalSettingsFormContributor.
				getDeleteMVCActionCommandNameOptional();

		deleteMVCActionCommandNameOptional.ifPresent(
			mvcActionName -> {
				DeletePortalSettingsFormMVCActionCommand
					deletePortalSettingsFormMVCActionCommand =
						new DeletePortalSettingsFormMVCActionCommand(
							_portletPreferencesLocalService,
							portalSettingsFormContributor);

				mvcActionCommandServiceRegistrationHolder.
					_deleteMVCActionCommandServiceReference =
						registerMVCActionCommand(
							mvcActionName,
							deletePortalSettingsFormMVCActionCommand);
			});

		Optional<String> saveMVCActionCommandNameOptional =
			portalSettingsFormContributor.getSaveMVCActionCommandNameOptional();

		saveMVCActionCommandNameOptional.ifPresent(
			mvcActionName -> {
				SavePortalSettingsFormMVCActionCommand
					savePortalSettingsFormMVCActionCommand =
						new SavePortalSettingsFormMVCActionCommand(
							portalSettingsFormContributor);

				mvcActionCommandServiceRegistrationHolder.
					_saveMVCActionCommandServiceReference =
						registerMVCActionCommand(
							mvcActionName,
							savePortalSettingsFormMVCActionCommand);
			});

		_serviceRegistrationHolders.put(
			portalSettingsFormContributor.getSettingsId(),
			mvcActionCommandServiceRegistrationHolder);

		return portalSettingsFormContributor;
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceTracker.close();
	}

	@Override
	public void modifiedService(
		ServiceReference<PortalSettingsFormContributor> reference,
		PortalSettingsFormContributor service) {

		unregister(service);

		addingService(reference);
	}

	@Override
	public void removedService(
		ServiceReference<PortalSettingsFormContributor> reference,
		PortalSettingsFormContributor service) {

		unregister(service);
	}

	protected ServiceRegistration<MVCActionCommand> registerMVCActionCommand(
		String mvcActionCommandName, MVCActionCommand mvcActionCommand) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"javax.portlet.name",
			ConfigurationAdminPortletKeys.INSTANCE_SETTINGS);
		properties.put("mvc.command.name", mvcActionCommandName);

		return _bundleContext.registerService(
			MVCActionCommand.class, mvcActionCommand, properties);
	}

	protected void unregister(
		PortalSettingsFormContributor portalSettingsFormContributor) {

		MVCActionCommandServiceRegistrationHolder
			mvcActionCommandServiceRegistrationHolder =
				_serviceRegistrationHolders.get(
					portalSettingsFormContributor.getSettingsId());

		if (mvcActionCommandServiceRegistrationHolder == null) {
			return;
		}

		if (mvcActionCommandServiceRegistrationHolder.
				_deleteMVCActionCommandServiceReference != null) {

			mvcActionCommandServiceRegistrationHolder.
				_deleteMVCActionCommandServiceReference.unregister();
		}

		if (mvcActionCommandServiceRegistrationHolder.
				_saveMVCActionCommandServiceReference != null) {

			mvcActionCommandServiceRegistrationHolder.
				_saveMVCActionCommandServiceReference.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private final Map<String, MVCActionCommandServiceRegistrationHolder>
		_serviceRegistrationHolders = new Hashtable<>();
	private ServiceTracker
		<PortalSettingsFormContributor, PortalSettingsFormContributor>
			_serviceTracker;

	private class MVCActionCommandServiceRegistrationHolder {

		private ServiceRegistration<MVCActionCommand>
			_deleteMVCActionCommandServiceReference;
		private ServiceRegistration<MVCActionCommand>
			_saveMVCActionCommandServiceReference;

	}

}