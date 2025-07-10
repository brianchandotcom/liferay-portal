<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/import/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL())));
portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());
renderResponse.setTitle(LanguageUtil.get(request, "error-details"));
%>

<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPD-35914") %>'>
	<react:component
		module="{ViewImportErrorDetail} from exportimport-web"
	/>
</c:if>