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

package com.liferay.client.extension.web.internal.type.deployer;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.web.internal.portlet.ClientExtensionEntryFriendlyURLMapper;
import com.liferay.client.extension.web.internal.portlet.ClientExtensionEntryPortlet;
import com.liferay.client.extension.web.internal.portlet.action.ClientExtensionEntryConfigurationAction;
import com.liferay.client.extension.web.internal.servlet.taglib.ClientExtensionTopHeadDynamicInclude;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
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
@Component(immediate = true, service = CETDeployer.class)
public class CETDeployerImpl implements CETDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(CET cet) {
		if (!Objects.equals(
				cet.getType(),
				ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT) &&
			!Objects.equals(
				cet.getType(), ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return Collections.emptyList();
		}

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		serviceRegistrations.add(_registerConfigurationAction(cet));

		CETCustomElement cetCustomElement = null;
		CETIFrame cetIFrame = null;
		String friendlyURLMapping = null;
		boolean instanceable = false;
		String portletCategoryName = null;

		if (Objects.equals(
				cet.getType(),
				ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			cetCustomElement = (CETCustomElement)cet;

			friendlyURLMapping = cetCustomElement.getFriendlyURLMapping();
			instanceable = cetCustomElement.isInstanceable();
			portletCategoryName = cetCustomElement.getPortletCategoryName();
		}
		else if (Objects.equals(
					cet.getType(), ClientExtensionEntryConstants.TYPE_IFRAME)) {

			cetIFrame = (CETIFrame)cet;

			friendlyURLMapping = cetIFrame.getFriendlyURLMapping();
			instanceable = cetIFrame.isInstanceable();
			portletCategoryName = cetIFrame.getPortletCategoryName();
		}

		if (Validator.isNull(portletCategoryName)) {
			portletCategoryName = "category.remote-apps";
		}

		if (!instanceable && Validator.isNotNull(friendlyURLMapping)) {
			serviceRegistrations.add(
				_registerFriendlyURLMapper(cet, friendlyURLMapping));
		}

		serviceRegistrations.add(
			_registerPortlet(
				cet, cetCustomElement, cetIFrame, instanceable,
				portletCategoryName));

		return serviceRegistrations;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private String _getPortletId(CET cet) {
		String primaryKey = cet.getPrimaryKey();

		return "com_liferay_client_extension_web_internal_portlet_" +
			"ClientExtensionEntryPortlet_" +
			   primaryKey.replaceAll("[^a-zA-Z0-9_]", StringPool.UNDERLINE);
	}

	private ServiceRegistration<ConfigurationAction>
		_registerConfigurationAction(CET cet) {

		return _bundleContext.registerService(
			ConfigurationAction.class,
			new ClientExtensionEntryConfigurationAction(),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(cet)
			).build());
	}

	private ServiceRegistration<FriendlyURLMapper> _registerFriendlyURLMapper(
		CET cet, String friendlyURLMapping) {

		return _bundleContext.registerService(
			FriendlyURLMapper.class,
			new ClientExtensionEntryFriendlyURLMapper(friendlyURLMapping),
			HashMapDictionaryBuilder.<String, Object>put(
				"javax.portlet.name", _getPortletId(cet)
			).build());
	}

	private ServiceRegistration<Portlet> _registerPortlet(
		CET cet, CETCustomElement cetCustomElement, CETIFrame cetIFrame,
		boolean instanceable, String portletCategoryName) {

		String portletName = _getPortletId(cet);

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.company", cet.getCompanyId()
			).put(
				"com.liferay.portlet.css-class-wrapper", "portlet-remote-app"
			).put(
				"com.liferay.portlet.display-category", portletCategoryName
			).put(
				"com.liferay.portlet.instanceable", instanceable
			).put(
				"javax.portlet.display-name", cet.getName(LocaleUtil.US)
			).put(
				"javax.portlet.name", portletName
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).build();

		if (cetCustomElement != null) {
			String cssURLs = cetCustomElement.getCSSURLs();

			if (Validator.isNotNull(cssURLs)) {
				dictionary.put(
					"com.liferay.portlet.footer-portal-css",
					cssURLs.split(StringPool.NEW_LINE));
			}

			String urls = cetCustomElement.getURLs();

			if (cetCustomElement.isUseESM()) {
				_clientExtensionTopHeadDynamicInclude.registerURLs(
					portletName, urls.split(StringPool.NEW_LINE));
			}
			else {
				dictionary.put(
					"com.liferay.portlet.footer-portal-javascript",
					urls.split(StringPool.NEW_LINE));
			}
		}
		else if (cetIFrame != null) {
			dictionary.put(
				"com.liferay.portlet.footer-portlet-css",
				"/display/css/main.css");
		}
		else {
			throw new IllegalArgumentException(
				"Invalid remote app entry type: " + cet.getType());
		}

		return _bundleContext.registerService(
			Portlet.class,
			new ClientExtensionEntryPortlet(
				cet, cetCustomElement, cetIFrame, _npmResolver),
			dictionary);
	}

	private BundleContext _bundleContext;

	@Reference
	private CETFactory _cetFactory;

	@Reference
	private ClientExtensionTopHeadDynamicInclude
		_clientExtensionTopHeadDynamicInclude;

	@Reference
	private NPMResolver _npmResolver;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.client.extension.web)(release.schema.version>=2.0.0))"
	)
	private Release _release;

}