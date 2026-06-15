<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect"));
portletDisplay.setURLBackTitle(ParamUtil.getString(request, "backURLTitle"));

renderResponse.setTitle(LanguageUtil.get(request, "element-variations"));
%>

<liferay-product-navigation:control-menu />

<div>
	<react:component
		module="{ElementVariations} from layout-content-page-editor-web"
		props="<%= java.util.Collections.emptyMap() %>"
	/>
</div>
