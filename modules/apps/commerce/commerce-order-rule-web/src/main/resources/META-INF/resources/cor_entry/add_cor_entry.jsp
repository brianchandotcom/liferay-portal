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
COREntryDisplayContext corEntryDisplayContext = (COREntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/cor_entry/edit_cor_entry" var="editCOREntryActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-order-rule") %>'
>
	<aui:form method="post" name="fm">
		<aui:input bean="<%= corEntryDisplayContext.getCOREntry() %>" label="name" model="<%= COREntry.class %>" name="name" required="<%= true %>" />

		<aui:input name="description" type="textarea" />

		<aui:select label="type" name="type" required="<%= true %>">

			<%
			for (COREntryType corEntryType : corEntryDisplayContext.getCOREntryTypes()) {
			%>

				<aui:option label="<%= corEntryType.getLabel(locale) %>" value="<%= corEntryType.getKey() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", themeDisplay.getLanguageId()
			).put(
				"editCOREntryPortletURL", String.valueOf(corEntryDisplayContext.getEditCOREntryRenderURL())
			).build()
		%>'
		module="js/addCOREntry"
	/>
</commerce-ui:modal-content>