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
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="trash"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "icon", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= trashDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			label='<%= Objects.equals(trashDisplayContext.getNavigation(), "all") ? "all" : ResourceActionsUtil.getModelResource(locale, trashDisplayContext.getNavigation()) %>'
		>

			<%
			PortletURL allURL = trashDisplayContext.getPortletURL();

			allURL.setParameter("navigation", "all");
			%>

			<liferay-frontend:management-bar-filter-item
				active='<%= Objects.equals(trashDisplayContext.getNavigation(), "all") %>'
				label="all"
				url="<%= allURL.toString() %>"
			/>

			<%
			List<TrashHandler> trashHandlers = TrashHandlerRegistryUtil.getTrashHandlers();

			for (TrashHandler trashHandler : trashHandlers) {
				PortletURL trashHandlerURL = trashDisplayContext.getPortletURL();

				trashHandlerURL.setParameter("navigation", trashHandler.getClassName());
			%>

				<liferay-frontend:management-bar-filter-item
					active="<%= Objects.equals(trashDisplayContext.getNavigation(), trashHandler.getClassName()) %>"
					label="<%= ResourceActionsUtil.getModelResource(locale, trashHandler.getClassName()) %>"
					url="<%= trashHandlerURL.toString() %>"
				/>

			<%
			}
			%>

		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= trashDisplayContext.getOrderByCol() %>"
			orderByType="<%= trashDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"removed-date"} %>'
			portletURL="<%= trashDisplayContext.getPortletURL() %>"
		/>

		<li>
			<liferay-portlet:renderURL varImpl="searchURL" />

			<aui:form action="<%= searchURL.toString() %>" method="get" name="searchFm">
				<liferay-portlet:renderURLParams varImpl="searchURL" />
				<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
				<aui:input name="deleteTrashEntryIds" type="hidden" />
				<aui:input name="restoreTrashEntryIds" type="hidden" />

				<liferay-ui:input-search
					autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
					markupView="lexicon"
					placeholder='<%= LanguageUtil.get(request, "search") %>'
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedEntries"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedEntries').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>