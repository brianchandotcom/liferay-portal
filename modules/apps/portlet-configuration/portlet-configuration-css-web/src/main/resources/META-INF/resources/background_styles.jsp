<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
	<liferay-util:param name="color" value="<%= portletConfigurationCSSPortletDisplayContext.getBackgroundColor() %>" />
	<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "background-color") %>' />
	<liferay-util:param name="name" value='<%= liferayPortletResponse.getNamespace() + "backgroundColor" %>' />
</liferay-util:include>