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
ViewUADEntitiesDisplay viewUADEntitiesDisplay = (ViewUADEntitiesDisplay)request.getAttribute(UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcRenderCommandName", "/view_uad_applications_summary");
backURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure"), " - ", viewUADEntitiesDisplay.getTypeName()));
%>

<clay:navigation-bar
	items="<%= viewUADEntitiesDisplay.getNavigationItems() %>"
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="UADEntities"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>

			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "doAnonymizeMultiple();" %>'
				icon="magic"
				label="anonymize"
			/>

			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "doDeleteMultiple();" %>'
				icon="trash"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form method="post" name="viewUADEntitiesFm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="p_u_i_d" type="hidden" value="<%= String.valueOf(selectedUser.getUserId()) %>" />
	<aui:input name="uadRegistryKey" type="hidden" value="<%= viewUADEntitiesDisplay.getUADRegistryKey() %>" />
	<aui:input name="primaryKeys" type="hidden" />

	<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= true %>" id="/info_panel" var="entityTypeSidebarURL" />

		<liferay-frontend:sidebar-panel
			resourceURL="<%= entityTypeSidebarURL %>"
			searchContainerId="UADEntities"
		>
			<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
		</liferay-frontend:sidebar-panel>

		<div class="sidenav-content">
			<liferay-ui:search-container
				emptyResultsMessage="no-entities-remain-of-this-type"
				id="UADEntities"
				searchContainer="<%= viewUADEntitiesDisplay.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.user.associated.data.web.internal.display.UADEntity"
					escapedModel="<%= true %>"
					keyProperty="primaryKey"
					modelVar="uadEntity"
				>

					<%
					List<KeyValuePair> columnEntries = uadEntity.getColumnEntries();

					for (KeyValuePair columnEntry : columnEntries) {
						String cssClass = columnEntry.equals(columnEntries.get(0)) ? "table-cell-expand table-list-title" : "table-cell-expand";
					%>

						<liferay-ui:search-container-column-text
							cssClass="<%= cssClass %>"
							href="<%= uadEntity.getEditURL() %>"
							name="<%= columnEntry.getKey() %>"
							value="<%= StringUtil.shorten(columnEntry.getValue(), 200) %>"
						/>

					<%
					}
					%>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/uad_entity_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace/>doAnonymizeMultiple() {
		<portlet:namespace />doMultiple(
			'<portlet:actionURL name="/anonymize_uad_entities" />',
			'<liferay-ui:message key="are-you-sure-you-want-to-anonymize-the-selected items" />'
		);
	}

	function <portlet:namespace/>doDeleteMultiple() {
		<portlet:namespace />doMultiple(
			'<portlet:actionURL name="/delete_uad_entities" />',
			'<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected items" />'
		);
	}

	function <portlet:namespace/>doMultiple(actionURL, message) {
		var form = document.forms['<portlet:namespace />viewUADEntitiesFm'];

		form.elements['<portlet:namespace />primaryKeys'].value = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds');

		<portlet:namespace />confirmAction('viewUADEntitiesFm', actionURL, message);
	}
</aui:script>

<%@ include file="/action/confirm_action_js.jspf" %>