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

<%@ include file="/browser/init.jsp" %>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL">
		<portlet:param name="mvcPath" value="/browser/view.jsp" />
	</portlet:renderURL>

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="entries" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= ddmFormBrowserDisplayContext.getPortletURL() %>" cssClass="container-fluid-1280" method="post" name="searchFm">
			<liferay-ui:input-search
				markupView="lexicon"
			/>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= ddmFormBrowserDisplayContext.isDisabledManagementBar() %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= ddmFormBrowserDisplayContext.getPortletURL() %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= ddmFormBrowserDisplayContext.getOrderByCol() %>"
				orderByType="<%= ddmFormBrowserDisplayContext.getOrderByType() %>"
				orderColumns='<%= new String[] {"modified-date"} %>'
				portletURL="<%= ddmFormBrowserDisplayContext.getPortletURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= ddmFormBrowserDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= ddmFormBrowserDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= String.valueOf(ddmFormBrowserDisplayContext.getPortletURL()) %>" method="post" name="selectDDLFormFm">
		<liferay-ui:search-container
			id="ddmFormInstance"
			searchContainer="<%= ddmFormBrowserDisplayContext.getFormInstanceSearch() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMFormInstance"
				cssClass="entry-display-style"
				keyProperty="formInstanceId"
				modelVar="formInstance"
			>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("forminstanceid", formInstance.getFormInstanceId());
				data.put("forminstancename", formInstance.getName(locale));
				%>

				<liferay-ui:search-container-column-text
					cssClass="content-column title-column"
					name="name"
					truncate="<%= true %>"
				>
					<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
						<%= HtmlUtil.escape(formInstance.getName(locale)) %>
					</aui:a>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="content-column"
					name="description"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(formInstance.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="modified-date-column text-column"
					name="modified-date"
					value="<%= formInstance.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= ddmFormBrowserDisplayContext.getDisplayStyle() %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectDDLFormFm', '<%= HtmlUtil.escapeJS(ddmFormBrowserDisplayContext.getEventName()) %>');
</aui:script>