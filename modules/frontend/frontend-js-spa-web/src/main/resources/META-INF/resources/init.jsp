<%--
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
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.theme.ThemeDisplay" %>

<%@ page import="java.util.List" %>

<liferay-theme:defineObjects />

<aui:script position="inline" require="frontend-js-spa-web@1.0.0/liferay/init.es">
	Liferay.SPA.app.setBlacklist(<%= getPortletsBlacklist(themeDisplay) %>);
</aui:script>

<%!
protected String getPortletsBlacklist(ThemeDisplay themeDisplay) {
	JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

	List<Portlet> companyPortlets = PortletLocalServiceUtil.getPortlets(themeDisplay.getCompanyId());

	for (Portlet portlet : companyPortlets) {
		if (portlet.isActive() && portlet.isReady() && !portlet.isUndeployedPortlet() && !portlet.isSinglePageApplication()) {
			jsonObject.put(portlet.getPortletId(), true);
		}
	}

	return jsonObject.toString();
}
%>