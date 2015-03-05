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

<%@ include file="/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

String keywords = ParamUtil.getString(request, "keywords");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/search.jsp");
portletURL.setParameter("redirect", currentURL);
portletURL.setPortletMode(PortletMode.VIEW);
portletURL.setWindowState(WindowState.MAXIMIZED);

pageContext.setAttribute("portletURL", portletURL);
%>

<aui:form action="<%= portletURL %>" method="get" name="fm" onSubmit='<%= renderResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<aui:fieldset>
		<aui:input wrapperCssClass="search-item" inlineField="<%= true %>" label="" name="keywords" size="30" title="search" type="text" value="<%= HtmlUtil.escapeAttribute(keywords) %>" />

		<%
		String taglibOnClick = "Liferay.Util.focusFormField('#" + renderResponse.getNamespace() + "keywords');";
		%>

		<liferay-ui:quick-access-entry label="skip-to-search" onClick="<%= taglibOnClick %>" />

		<aui:select inlineField="<%= true %>" label="" name="groupId" title="scope" wrapperCssClass="search-item">

			<%
			Group group = themeDisplay.getScopeGroup();
			%>

			<c:if test="<%= !group.isStagingGroup() %>">
				<aui:option label="everything" selected="<%= (groupId == 0) %>" value="0" />
			</c:if>

			<aui:option label='<%= "this-" + (group.isOrganization() ? "organization" : "site") %>' selected="<%= (groupId != 0) %>" value="<%= group.getGroupId() %>" />
		</aui:select>

		<aui:field-wrapper cssClass="search-item" inlineField="<%= true %>">
			<aui:button icon="icon-search" onClick='<%= renderResponse.getNamespace() + "search();" %>' value="search" />
		</aui:field-wrapper>
	</aui:fieldset>

	<aui:script>
		function <portlet:namespace />search() {
			var keywords = document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

			keywords = keywords.replace(/^\s+|\s+$/, '');

			if (keywords != '') {
				submitForm(document.<portlet:namespace />fm);
			}
		}
	</aui:script>
</aui:form>