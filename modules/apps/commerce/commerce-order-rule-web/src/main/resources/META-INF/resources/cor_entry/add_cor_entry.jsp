<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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