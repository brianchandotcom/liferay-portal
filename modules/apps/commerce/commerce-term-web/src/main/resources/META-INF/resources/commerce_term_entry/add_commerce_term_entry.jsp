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