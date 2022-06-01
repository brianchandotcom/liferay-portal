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

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETGlobalCSS;
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.CETThemeJS;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.client.extension.type.internal.CETCustomElementImpl;
import com.liferay.client.extension.type.internal.CETGlobalCSSImpl;
import com.liferay.client.extension.type.internal.CETGlobalJSImpl;
import com.liferay.client.extension.type.internal.CETIFrameImpl;
import com.liferay.client.extension.type.internal.CETThemeCSSImpl;
import com.liferay.client.extension.type.internal.CETThemeFaviconImpl;
import com.liferay.client.extension.type.internal.CETThemeJSImpl;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Objects;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = CETFactory.class)
public class CETFactoryImpl implements CETFactory {

	@Override
	public CET cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException {

		String type = clientExtensionEntry.getType();

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			return cetCustomElement(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS)) {

			return cetGlobalCSS(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_JS)) {

			return cetGlobalJS(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return cetIFrame(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

			return cetThemeCSS(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			return cetThemeFavicon(clientExtensionEntry);
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_JS)) {

			return cetThemeJS(clientExtensionEntry);
		}
		else {
			throw new ClientExtensionEntryTypeException("Invalid type " + type);
		}
	}

	@Override
	public CETCustomElement cetCustomElement(
		ClientExtensionEntry clientExtensionEntry) {

		return new CETCustomElementImpl(clientExtensionEntry);
	}

	@Override
	public CETCustomElement cetCustomElement(PortletRequest portletRequest) {
		return new CETCustomElementImpl(portletRequest);
	}

	@Override
	public CETGlobalCSS cetGlobalCSS(
		ClientExtensionEntry clientExtensionEntry) {

		return new CETGlobalCSSImpl(clientExtensionEntry);
	}

	@Override
	public CETGlobalCSS cetGlobalCSS(PortletRequest portletRequest) {
		return new CETGlobalCSSImpl(portletRequest);
	}

	@Override
	public CETGlobalJS cetGlobalJS(ClientExtensionEntry clientExtensionEntry) {
		return new CETGlobalJSImpl(clientExtensionEntry);
	}

	@Override
	public CETGlobalJS cetGlobalJS(PortletRequest portletRequest) {
		return new CETGlobalJSImpl(portletRequest);
	}

	@Override
	public CETIFrame cetIFrame(ClientExtensionEntry clientExtensionEntry) {
		return new CETIFrameImpl(clientExtensionEntry);
	}

	@Override
	public CETIFrame cetIFrame(PortletRequest portletRequest) {
		return new CETIFrameImpl(portletRequest);
	}

	@Override
	public CETThemeCSS cetThemeCSS(ClientExtensionEntry clientExtensionEntry) {
		return new CETThemeCSSImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeCSS cetThemeCSS(PortletRequest portletRequest) {
		return new CETThemeCSSImpl(portletRequest);
	}

	@Override
	public CETThemeFavicon cetThemeFavicon(
		ClientExtensionEntry clientExtensionEntry) {

		return new CETThemeFaviconImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeFavicon cetThemeFavicon(PortletRequest portletRequest) {
		return new CETThemeFaviconImpl(portletRequest);
	}

	@Override
	public CETThemeJS cetThemeJS(ClientExtensionEntry clientExtensionEntry) {
		return new CETThemeJSImpl(clientExtensionEntry);
	}

	@Override
	public CETThemeJS cetThemeJS(PortletRequest portletRequest) {
		return new CETThemeJSImpl(portletRequest);
	}

	@Override
	public String typeSettings(PortletRequest portletRequest, String type)
		throws PortalException {

		if (Objects.equals(
				type, ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			return String.valueOf(cetCustomElement(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS)) {

			return String.valueOf(cetGlobalCSS(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_GLOBAL_JS)) {

			return String.valueOf(cetGlobalJS(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return String.valueOf(cetIFrame(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_CSS)) {

			return String.valueOf(cetThemeCSS(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_FAVICON)) {

			return String.valueOf(cetThemeFavicon(portletRequest));
		}
		else if (Objects.equals(
					type, ClientExtensionEntryConstants.TYPE_THEME_JS)) {

			return String.valueOf(cetThemeJS(portletRequest));
		}
		else {
			throw new ClientExtensionEntryTypeException("Invalid type " + type);
		}
	}

}