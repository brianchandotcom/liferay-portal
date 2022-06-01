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

package com.liferay.client.extension.type.factory;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CETCustomElement;
import com.liferay.client.extension.type.CETGlobalCSS;
import com.liferay.client.extension.type.CETGlobalJS;
import com.liferay.client.extension.type.CETIFrame;
import com.liferay.client.extension.type.CETThemeCSS;
import com.liferay.client.extension.type.CETThemeFavicon;
import com.liferay.client.extension.type.CETThemeJS;
import com.liferay.portal.kernel.exception.PortalException;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public interface CETFactory {

	public CET cet(ClientExtensionEntry clientExtensionEntry)
		throws PortalException;

	public CETCustomElement cetCustomElement(
		ClientExtensionEntry clientExtensionEntry);

	public CETCustomElement cetCustomElement(PortletRequest portletRequest);

	public CETGlobalCSS cetGlobalCSS(ClientExtensionEntry clientExtensionEntry);

	public CETGlobalCSS cetGlobalCSS(PortletRequest portletRequest);

	public CETGlobalJS cetGlobalJS(ClientExtensionEntry clientExtensionEntry);

	public CETGlobalJS cetGlobalJS(PortletRequest portletRequest);

	public CETIFrame cetIFrame(ClientExtensionEntry clientExtensionEntry);

	public CETIFrame cetIFrame(PortletRequest portletRequest);

	public CETThemeCSS cetThemeCSS(ClientExtensionEntry clientExtensionEntry);

	public CETThemeCSS cetThemeCSS(PortletRequest portletRequest);

	public CETThemeFavicon cetThemeFavicon(
		ClientExtensionEntry clientExtensionEntry);

	public CETThemeFavicon cetThemeFavicon(PortletRequest portletRequest);

	public CETThemeJS cetThemeJS(ClientExtensionEntry clientExtensionEntry);

	public CETThemeJS cetThemeJS(PortletRequest portletRequest);

	public String typeSettings(PortletRequest portletRequest, String type)
		throws PortalException;

}