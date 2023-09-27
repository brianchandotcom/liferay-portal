<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<liferay-map:map-provider-selector
	configurationPrefix="settings"
	mapProviderKey="<%= (String)request.getAttribute(MapProviderWebKeys.MAP_PROVIDER_KEY) %>"
	name='<%= "settings--" + MapProviderWebKeys.MAP_PROVIDER_KEY + "--" %>'
/>