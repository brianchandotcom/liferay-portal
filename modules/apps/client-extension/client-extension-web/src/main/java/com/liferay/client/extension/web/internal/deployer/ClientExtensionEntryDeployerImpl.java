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

package com.liferay.client.extension.web.internal.deployer;

import com.liferay.client.extension.constants.ClientExtensionConstants;
import com.liferay.client.extension.deployer.ClientExtensionEntryDeployer;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.web.internal.portlet.RemoteAppEntryFriendlyURLMapper;
import com.liferay.client.extension.web.internal.portlet.RemoteAppEntryPortlet;
import com.liferay.client.extension.web.internal.portlet.action.RemoteAppEntryConfigurationAction;
import com.liferay.client.extension.web.internal.servlet.taglib.RemoteAppTopHeadDynamicInclude;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ClientExtensionEntryDeployer.class)
public class ClientExtensionEntryDeployerImpl
	implements ClientExtensionEntryDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(
		ClientExtensionEntry clientExtensionEntry) {

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		serviceRegistrations.add(
			_registerConfigurationAction(clientExtensionEntry));

		if (!clientExtensionEntry.isInstanceable() &&
			Validator.isNotNull(clientExtensionEntry.getFriendlyURLMapping())) {

			serviceRegistrations.add(
				_registerFriendlyURLMapper(clientExtensionEntry));
		}

		serviceRegistrations.add(_registerPortlet(clientExtensionEntry));

		return serviceRegistrations;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getPortletCategoryName(
		ClientExtensionEntry clientExtensionEntry) {

		String portletCategoryName =
			clientExtensionEntry.getPortletCategoryName();

		if (Validator.isNull(portletCategoryName)) {
			return "category.remote-apps";
		}

		return portletCategoryName;
	}

	private String _getPortletId(ClientExtensionEntry clientExtensionEntry) {
		return "com_liferay_remote_app_web_internal_portlet_" +
			"RemoteAppEntryPortlet_" +
				clientExtensionEntry.getClientExtensionEntryId();
	}

	private ServiceRegistration<ConfigurationAction>
		_registerConfigurationAction(
			ClientExtensionEntry clientExtensionEntry) {

		return _bundleContext.registerService(
			ConfigurationAction.class, new RemoteAppEntryConfigurationAction(),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(clientExtensionEntry)
			).build());
	}

	private ServiceRegistration<FriendlyURLMapper> _registerFriendlyURLMapper(
		ClientExtensionEntry clientExtensionEntry) {

		return _bundleContext.registerService(
			FriendlyURLMapper.class,
			new RemoteAppEntryFriendlyURLMapper(clientExtensionEntry),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(clientExtensionEntry)
			).build());
	}

	private ServiceRegistration<Portlet> _registerPortlet(
		ClientExtensionEntry clientExtensionEntry) {

		String portletName = _getPortletId(clientExtensionEntry);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.company",
				clientExtensionEntry.getCompanyId()
			).put(
				"com.liferay.portlet.css-class-wrapper", "portlet-remote-app"
			).put(
				"com.liferay.portlet.display-category",
				_getPortletCategoryName(clientExtensionEntry)
			).put(
				"com.liferay.portlet.instanceable",
				clientExtensionEntry.isInstanceable()
			).put(
				"javax.portlet.display-name",
				clientExtensionEntry.getName(LocaleUtil.US)
			).put(
				"javax.portlet.name", portletName
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).build();

		if (Objects.equals(
				clientExtensionEntry.getType(),
				ClientExtensionConstants.TYPE_CUSTOM_ELEMENT)) {

			String customElementURLs =
				clientExtensionEntry.getCustomElementURLs();

			if (clientExtensionEntry.isCustomElementUseESM()) {
				_remoteAppTopHeadDynamicInclude.registerURLs(
					portletName, customElementURLs.split(StringPool.NEW_LINE));
			}
			else {
				dictionary.put(
					"com.liferay.portlet.footer-portal-javascript",
					customElementURLs.split(StringPool.NEW_LINE));
			}

			String customElementCSSURLs =
				clientExtensionEntry.getCustomElementCSSURLs();

			if (Validator.isNotNull(customElementCSSURLs)) {
				dictionary.put(
					"com.liferay.portlet.footer-portal-css",
					customElementCSSURLs.split(StringPool.NEW_LINE));
			}
		}
		else if (Objects.equals(
					clientExtensionEntry.getType(),
					ClientExtensionConstants.TYPE_IFRAME)) {

			dictionary.put(
				"com.liferay.portlet.footer-portlet-css",
				"/display/css/main.css");
		}
		else {
			throw new IllegalArgumentException(
				"Invalid remote app entry type: " +
					clientExtensionEntry.getType());
		}

		return _bundleContext.registerService(
			Portlet.class,
			new RemoteAppEntryPortlet(_npmResolver, clientExtensionEntry),
			dictionary);
	}

	private BundleContext _bundleContext;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private RemoteAppTopHeadDynamicInclude _remoteAppTopHeadDynamicInclude;

}