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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();
ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry_rel" var="editRelatedModelActionURL" />

<aui:form action="<%= editRelatedModelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="objectEntryId" type="hidden" value="<%= (objectEntry == null) ? 0 : objectEntry.getObjectEntryId() %>" />
	<aui:input name="objectEntryRelId" type="hidden" value="" />
	<aui:input name="objectRelationshipId" type="hidden" value="<%= objectLayoutTab.getObjectRelationshipId() %>" />

	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"objectEntryId", String.valueOf(objectEntry.getObjectEntryId())
			).put(
				"objectRelationshipId", String.valueOf(objectLayoutTab.getObjectRelationshipId())
			).build()
		%>'
		creationMenu="<%= objectEntryDisplayContext.getRelatedModelCreationMenu() %>"
		dataProviderKey="<%= ObjectEntriesClayDataSetDisplayNames.RELATED_MODELS %>"
		formId="fm"
		id="<%= ObjectEntriesClayDataSetDisplayNames.RELATED_MODELS %>"
		itemsPerPage="<%= 20 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= liferayPortletResponse.createRenderURL() %>"
		style="fluid"
	/>
</aui:form>

<aui:script sandbox="<%= true %>">
	const eventHandlers = [];

	const selectRelatedModelHandler = Liferay.on(
		'<portlet:namespace />selectRelatedModel',
		() => {
			Liferay.Util.openSelectionModal({
				selectEventName: `<portlet:namespace />selectRelatedModalEntry`,
				multiple: false,
				onSelect: (selectedItem) => {
					const objectEntry = JSON.parse(selectedItem.value);

					const objectEntryRelIdInput = document.getElementById(
						'<portlet:namespace />objectEntryRelId'
					);

					objectEntryRelIdInput.value = objectEntry.classPK;

					const form = document.getElementById('<portlet:namespace />fm');

					if (form) {
						submitForm(form);
					}
				},
				title: '<liferay-ui:message key="select" />',
				url: '<%= objectEntryDisplayContext.getRelatedObjectEntryItemSelectorUrl() %>',
			});
		}
	);

	eventHandlers.push(selectRelatedModelHandler);

	Liferay.on('destroyPortlet', () => {
		eventHandlers.forEach((eventHandler) => {
			eventHandler.detach();
		});
	});
</aui:script>