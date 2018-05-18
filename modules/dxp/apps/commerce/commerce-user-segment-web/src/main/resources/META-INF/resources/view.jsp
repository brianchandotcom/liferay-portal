<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceUserSegmentDisplayContext commerceUserSegmentDisplayContext = (CommerceUserSegmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceUserSegmentEntry> commerceUserSegmentEntrySearchContainer = commerceUserSegmentDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceUserSegmentEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= commerceUserSegmentDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<liferay-portlet:actionURL name="editCommerceUserSegmentEntry" var="addCommerceUserSegmentEntryURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCommerceUserSegmentEntryMenuItem"
				title='<%= LanguageUtil.get(request, "add-entry") %>'
				url="<%= addCommerceUserSegmentEntryURL.toString() %>"
			/>
		</liferay-frontend:add-menu>

		<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
			function handleAddCommerceUserSegmentEntryMenuItemClick(event) {
				event.preventDefault();

				modalCommands.openSimpleInputModal(
					{
						dialogTitle: '<liferay-ui:message key="add-entry" />',
						formSubmitURL: '<%= addCommerceUserSegmentEntryURL %>',
						mainFieldLabel: '<liferay-ui:message key="name" />',
						mainFieldName: 'name',
						mainFieldPlaceholder: '<liferay-ui:message key="name" />',
						namespace: '<portlet:namespace />',
						spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
					}
				);
			}

			function handleDestroyPortlet () {
				addCommerceUserSegmentEntryMenuItem.removeEventListener('click', handleAddCommerceUserSegmentEntryMenuItemClick);

				Liferay.detach('destroyPortlet', handleDestroyPortlet);
			}

			var addCommerceUserSegmentEntryMenuItem = document.getElementById('<portlet:namespace />addCommerceUserSegmentEntryMenuItem');

			addCommerceUserSegmentEntryMenuItem.addEventListener('click', handleAddCommerceUserSegmentEntryMenuItemClick);

			Liferay.on('destroyPortlet', handleDestroyPortlet);
		</aui:script>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceUserSegmentEntrySearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceUserSegmentEntrySearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"priority"} %>'
			portletURL="<%= commerceUserSegmentDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(commerceUserSegmentDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceUserSegmentEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceUserSegmentEntry" var="editCommerceUserSegmentEntryActionURL" />

	<aui:form action="<%= editCommerceUserSegmentEntryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommerceUserSegmentEntryIds" type="hidden" />

		<liferay-ui:search-container
			id="commerceUserSegmentEntries"
			searchContainer="<%= commerceUserSegmentEntrySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry"
				keyProperty="commerceUserSegmentEntryId"
				modelVar="commerceUserSegmentEntry"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="editCommerceUserSegmentEntry" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="commerceUserSegmentEntryId" value="<%= String.valueOf(commerceUserSegmentEntry.getCommerceUserSegmentEntryId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="important table-cell-content"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(commerceUserSegmentEntry.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					property="priority"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="active"
					value='<%= LanguageUtil.get(request, commerceUserSegmentEntry.isActive() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/user_segment_entry_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceUserSegmentEntries() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-entries" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceUserSegmentEntryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>