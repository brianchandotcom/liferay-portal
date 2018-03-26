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
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="dataProviderInstance"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-util:include page="/display_style_buttons.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/display_add_button.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= ddmDataProviderDisplayContext.getOrderByCol() %>"
			orderByType="<%= ddmDataProviderDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date", "modified-date", "name"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<c:if test="<%= ddmDataProviderDisplayContext.isShowSearch() %>">
				<aui:form action="<%= ddmDataProviderDisplayContext.getPortletURL() %>" method="post" name="fm1">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</c:if>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteDataProviderInstances();" %>'
			icon="trash"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteDataProviderInstances() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			var form = AUI.$(document.<portlet:namespace />searchContainerForm);

			var searchContainer = AUI.$('#<portlet:namespace />dataProviderInstance', form);

			form.attr('method', 'post');
			form.fm('deleteDataProviderInstanceIds').val(Liferay.Util.listCheckedExcept(searchContainer, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="deleteDataProvider"><portlet:param name="mvcPath" value="/view.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
		}
	}
</aui:script>