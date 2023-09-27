<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTermEntryDisplayContext commerceTermEntryDisplayContext = (CommerceTermEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_term_entry/edit_commerce_term_entry" var="editCommerceTermEntryActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-term") %>'
>
	<aui:form method="post" name="fm">
		<aui:model-context bean="<%= commerceTermEntryDisplayContext.getCommerceTermEntry() %>" model="<%= CommerceTermEntry.class %>" />

		<aui:input name="name" required="<%= true %>" />

		<aui:select name="type" required="<%= true %>">

			<%
			for (CommerceTermEntryType commerceTermEntryType : commerceTermEntryDisplayContext.getCommerceTermEntryTypes()) {
			%>

				<aui:option label="<%= commerceTermEntryType.getLabel(locale) %>" value="<%= commerceTermEntryType.getKey() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="priority" />
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", themeDisplay.getLanguageId()
			).put(
				"editCommerceTermEntryPortletURL", String.valueOf(commerceTermEntryDisplayContext.getEditCommerceTermEntryRenderURL())
			).build()
		%>'
		module="js/addCommerceTermEntry"
	/>
</commerce-ui:modal-content>