<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
LayoutInformationMessagesDisplayContext layoutInformationMessagesDisplayContext = new LayoutInformationMessagesDisplayContext(request);
%>

<li class="control-menu-nav-item lfr-portal-tooltip">
	<clay:button
		aria-label='<%= LanguageUtil.get(request, "additional-information") %>'
		data-qa-id="info"
		displayType="unstyled"
		monospaced="<%= true %>"
		small="<%= true %>"
		symbol="information-live"
		title="additional-information"
	/>

	<react:component
		data="<%= layoutInformationMessagesDisplayContext.getData() %>"
		module="js/dynamic_include/InformationMessages"
	/>
</li>