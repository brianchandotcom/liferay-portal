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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PublicRenderParameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletContext;

import javax.xml.namespace.QName;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Shuyang Zhou
 */
public class PortletConfigImpl implements LiferayPortletConfig {

	public PortletConfigImpl(Portlet portlet, PortletContext portletContext) {
		_portlet = portlet;
		_portletContext = portletContext;

		_copyRequestParameters = GetterUtil.getBoolean(
			getInitParameter("copy-request-parameters"));
		_portletApp = portlet.getPortletApp();

		String portletName = portlet.getRootPortletId();

		int pos = portletName.indexOf(PortletConstants.WAR_SEPARATOR);

		if (pos != -1) {
			portletName = portletName.substring(0, pos);
		}

		_portletName = portletName;

		_resourceBundles = new ConcurrentHashMap<>();
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return _portletApp.getContainerRuntimeOptions();
	}

	@Override
	public String getDefaultNamespace() {
		return _portletApp.getDefaultNamespace();
	}

	@Override
	public String getInitParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _portlet.getInitParams().get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_portlet.getInitParams().keySet());
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public PortletContext getPortletContext() {
		return _portletContext;
	}

	@Override
	public String getPortletId() {
		return _portlet.getPortletId();
	}

	@Override
	public String getPortletName() {
		return _portletName;
	}

	@Override
	public Enumeration<QName> getProcessingEventQNames() {
		return Collections.enumeration(
			toJavaxQNames(_portlet.getProcessingEvents()));
	}

	@Override
	public Enumeration<String> getPublicRenderParameterNames() {
		List<String> publicRenderParameterNames = new ArrayList<>();

		for (PublicRenderParameter publicRenderParameter :
				_portlet.getPublicRenderParameters()) {

			publicRenderParameterNames.add(
				publicRenderParameter.getIdentifier());
		}

		return Collections.enumeration(publicRenderParameterNames);
	}

	@Override
	public Enumeration<QName> getPublishingEventQNames() {
		return Collections.enumeration(
			toJavaxQNames(_portlet.getPublishingEvents()));
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		String resourceBundleClassName = _portlet.getResourceBundle();

		ResourceBundle resourceBundle = null;

		if (Validator.isNull(resourceBundleClassName)) {
			String resourceBundleId = _portlet.getPortletId();

			resourceBundle = _resourceBundles.get(resourceBundleId);

			if (resourceBundle == null) {
				resourceBundle = new PortletResourceBundle(
					_portlet.getPortletInfo());

				_resourceBundles.put(resourceBundleId, resourceBundle);
			}

			return resourceBundle;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_portlet.getPortletId());
		sb.append(locale.getLanguage());
		sb.append(locale.getCountry());
		sb.append(locale.getVariant());

		if (resourceBundle == null) {
			if (!_portletApp.isWARFile() &&
				resourceBundleClassName.equals(
					StrutsResourceBundle.class.getName())) {

				String resourceBundleId = sb.toString();

				resourceBundle = _resourceBundles.get(resourceBundleId);

				if (resourceBundle == null) {
					resourceBundle = new StrutsResourceBundle(
						_portletName, locale);
				}

				_resourceBundles.put(resourceBundleId, resourceBundle);
			}
			else {
				PortletBag portletBag = PortletBagPool.get(
					_portlet.getRootPortletId());

				resourceBundle = portletBag.getResourceBundle(locale);
			}

			resourceBundle = new PortletResourceBundle(
				resourceBundle, _portlet.getPortletInfo());
		}

		return resourceBundle;
	}

	@Override
	public Enumeration<Locale> getSupportedLocales() {
		List<Locale> supportedLocales = new ArrayList<>();

		for (String languageId : _portlet.getSupportedLocales()) {
			supportedLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return Collections.enumeration(supportedLocales);
	}

	@Override
	public boolean isCopyRequestParameters() {
		return _copyRequestParameters;
	}

	@Override
	public boolean isWARFile() {
		return _portletApp.isWARFile();
	}

	protected Set<javax.xml.namespace.QName> toJavaxQNames(
		Set<com.liferay.portal.kernel.xml.QName> liferayQNames) {

		Set<QName> javaxQNames = new HashSet<>(liferayQNames.size());

		for (com.liferay.portal.kernel.xml.QName liferayQName : liferayQNames) {
			QName javaxQName = new QName(
				liferayQName.getNamespaceURI(), liferayQName.getLocalPart(),
				liferayQName.getNamespacePrefix());

			javaxQNames.add(javaxQName);
		}

		return javaxQNames;
	}

	private final boolean _copyRequestParameters;
	private final Portlet _portlet;
	private final PortletApp _portletApp;
	private final PortletContext _portletContext;
	private final String _portletName;
	private final Map<String, ResourceBundle> _resourceBundles;

}