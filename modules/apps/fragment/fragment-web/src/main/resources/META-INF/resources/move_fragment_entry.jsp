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
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fragmentDisplayContext.getRedirect());

renderResponse.setTitle(fragmentDisplayContext.getFragmentEntryTitle());
%>

<portlet:actionURL name="/fragment/move_fragment_entry" var="moveFragmentEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/fragment/move_fragment_entry" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= moveFragmentEntryURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= fragmentDisplayContext.getRedirect() %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentDisplayContext.getFragmentCollectionId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentDisplayContext.getFragmentEntryId() %>" />

				<%
				FragmentCollection fragmentCollection = fragmentDisplayContext.getFragmentCollection();
				%>

				<aui:input label='<%= LanguageUtil.format(request, "new-x", "collection") %>' name="fragmentCollectionName" title='<%= LanguageUtil.format(request, "new-x", "collection") %>' type="resource" value="<%= fragmentCollection.getName() %>" />

				<aui:button name="selectFragmentCollectionButton" value="select" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="move" />

		<aui:button href="<%= fragmentDisplayContext.getRedirect() %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	AUI.$('#<portlet:namespace />selectFragmentCollectionButton').on(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectFragmentCollection',
					id: '<portlet:namespace />selectSiteNavigationMenu',
					title: '<liferay-ui:message arguments="collection" key="select-x" />',

					<portlet:renderURL var="selectFragmentCollectionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="/fragment/select_fragment_collection" />
					</portlet:renderURL>

					uri: '<%= selectFragmentCollectionURL %>'
				},
				function(selectedItem) {
					if (selectedItem) {
						var fragmentCollectionData = {
							idString: 'fragmentCollectionId',
							idValue: selectedItem.id,
							nameString: 'fragmentCollectionName',
							nameValue: selectedItem.name
						};

						Liferay.Util.selectFolder(fragmentCollectionData, '<portlet:namespace />');
					}
				}
			);
		}
	);
</aui:script>