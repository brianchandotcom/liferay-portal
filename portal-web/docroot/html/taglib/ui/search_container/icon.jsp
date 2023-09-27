<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/search_container/init.jsp" %>

<%
boolean toggleRowChecker = GetterUtil.getBoolean(request.getAttribute("liferay-ui:search-container-column-icon:toggleRowChecker"));
%>

<div class="<%= toggleRowChecker ? "click-selector" : StringPool.BLANK %> sticker sticker-secondary sticker-static">
	<aui:icon image='<%= (String)request.getAttribute("liferay-ui:search-container-column-icon:icon") %>' markupView="lexicon" url='<%= (String)request.getAttribute("liferay-ui:search-container-column-icon:href") %>' />
</div>