<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-map:map-provider-selector
	mapProviderKey="<%= (String)request.getAttribute(MapProviderWebKeys.MAP_PROVIDER_KEY) %>"
	name='<%= "TypeSettingsProperties--" + MapProviderWebKeys.MAP_PROVIDER_KEY + "--" %>'
/>