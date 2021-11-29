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

package com.liferay.remote.app.web.internal.deployer;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployer;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.web.internal.language.RemoteAppEntryResourceBundle;
import com.liferay.remote.app.web.internal.portlet.RemoteAppEntryFriendlyURLMapper;
import com.liferay.remote.app.web.internal.portlet.RemoteAppEntryPortlet;
import com.liferay.remote.app.web.internal.portlet.action.RemoteAppEntryConfigurationAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = RemoteAppEntryDeployer.class)
public class RemoteAppEntryDeployerImpl implements RemoteAppEntryDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(RemoteAppEntry remoteAppEntry) {
		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		serviceRegistrations.add(_registerConfigurationAction(remoteAppEntry));

		if (!remoteAppEntry.isInstanceable() &&
			Validator.isNotNull(remoteAppEntry.getFriendlyURLMapping())) {

			serviceRegistrations.add(
				_registerFriendlyURLMapper(remoteAppEntry));
		}

		serviceRegistrations.add(_registerPortlet(remoteAppEntry));

		serviceRegistrations.addAll(_registerResourceBundles(remoteAppEntry));

		return serviceRegistrations;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getPortletCategoryName(RemoteAppEntry remoteAppEntry) {
		String portletCategoryName = remoteAppEntry.getPortletCategoryName();

		if (Validator.isNull(portletCategoryName)) {
			return "category.remote-apps";
		}

		return portletCategoryName;
	}

	private String _getPortletId(RemoteAppEntry remoteAppEntry) {
		return "com_liferay_remote_app_web_internal_portlet_" +
			"RemoteAppEntryPortlet_" + remoteAppEntry.getRemoteAppEntryId();
	}

	private ServiceRegistration<ConfigurationAction>
		_registerConfigurationAction(RemoteAppEntry remoteAppEntry) {

		return _bundleContext.registerService(
			ConfigurationAction.class, new RemoteAppEntryConfigurationAction(),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(remoteAppEntry)
			).build());
	}

	private ServiceRegistration<FriendlyURLMapper> _registerFriendlyURLMapper(
		RemoteAppEntry remoteAppEntry) {

		return _bundleContext.registerService(
			FriendlyURLMapper.class,
			new RemoteAppEntryFriendlyURLMapper(remoteAppEntry),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(remoteAppEntry)
			).build());
	}

	private ServiceRegistration<Portlet> _registerPortlet(
		RemoteAppEntry remoteAppEntry) {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.company", remoteAppEntry.getCompanyId()
			).put(
				"com.liferay.portlet.css-class-wrapper", "portlet-remote-app"
			).put(
				"com.liferay.portlet.display-category",
				_getPortletCategoryName(remoteAppEntry)
			).put(
				"com.liferay.portlet.instanceable",
				remoteAppEntry.isInstanceable()
			//).put(
			//	"javax.portlet.display-name",
			//	remoteAppEntry.getName(LocaleUtil.US)
			).put(
				"javax.portlet.name", _getPortletId(remoteAppEntry)
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).build();

		if (Objects.equals(
				remoteAppEntry.getType(),
				RemoteAppConstants.TYPE_CUSTOM_ELEMENT)) {

			String customElementURLs = remoteAppEntry.getCustomElementURLs();

			dictionary.put(
				"com.liferay.portlet.footer-portal-javascript",
				customElementURLs.split(StringPool.NEW_LINE));

			String customElementCSSURLs =
				remoteAppEntry.getCustomElementCSSURLs();

			if (Validator.isNotNull(customElementCSSURLs)) {
				dictionary.put(
					"com.liferay.portlet.footer-portal-css",
					customElementCSSURLs.split(StringPool.NEW_LINE));
			}
		}
		else if (Objects.equals(
					remoteAppEntry.getType(), RemoteAppConstants.TYPE_IFRAME)) {

			dictionary.put(
				"com.liferay.portlet.footer-portlet-css",
				"/display/css/main.css");
		}
		else {
			throw new IllegalArgumentException(
				"Invalid remote app entry type: " + remoteAppEntry.getType());
		}

		return _bundleContext.registerService(
			Portlet.class,
			new RemoteAppEntryPortlet(_npmResolver, remoteAppEntry),
			dictionary);
	}

	private Collection<? extends ServiceRegistration<?>>
		_registerResourceBundles(RemoteAppEntry remoteAppEntry) {

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			serviceRegistrations.add(
				_bundleContext.registerService(
					ResourceBundle.class,
					new RemoteAppEntryResourceBundle(
						locale, _getPortletId(remoteAppEntry), remoteAppEntry),
					MapUtil.singletonDictionary(
						"language.id", LocaleUtil.toLanguageId(locale))));
		}

		return serviceRegistrations;
	}

	private BundleContext _bundleContext;

	@Reference
	private NPMResolver _npmResolver;

}