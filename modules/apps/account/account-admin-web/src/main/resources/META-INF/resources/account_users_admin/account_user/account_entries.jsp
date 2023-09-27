<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

User selUser = PortalUtil.getSelectedUser(request, false);

renderResponse.setTitle(LanguageUtil.format(request, "edit-user-x", HtmlUtil.escape(selUser.getFullName()), false));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL var="actionURL" />

<aui:form action="<%= actionURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />

	<clay:sheet>
		<h2 class="sheet-title">
			<liferay-ui:message key="accounts" />
		</h2>

		<liferay-util:include page="/common/common_user_account_entries.jsp" servletContext="<%= application %>" />

		<clay:sheet-footer>
			<aui:button type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>