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

ObjectDefinition objectDefinition2 = objectEntryDisplayContext.getObjectDefinition2();
ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();
ObjectLayoutTab objectLayoutTab = objectEntryDisplayContext.getObjectLayoutTab();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry_related_model" var="editObjectEntryRelatedModelActionURL" />

<aui:form action="<%= editObjectEntryRelatedModelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="objectRelationshipId" type="hidden" value="<%= objectLayoutTab.getObjectRelationshipId() %>" />
	<aui:input name="objectEntryId" type="hidden" value="<%= (objectEntry == null) ? 0 : objectEntry.getId() %>" />
	<aui:input name="objectRelationshipPrimaryKey2" type="hidden" value="" />

	<c:choose>
		<c:when test="<%= objectDefinition2.isSystem() %>">
			<frontend-data-set:classic-display
				contextParams="<%= objectEntryDisplayContext.getRelationshipContextParams() %>"
				creationMenu="<%= objectEntryDisplayContext.getRelatedModelCreationMenu(objectDefinition2) %>"
				dataProviderKey="<%= ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS %>"
				formName="fm"
				id="<%= ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS %>"
				style="fluid"
			/>
		</c:when>
		<c:otherwise>
			<frontend-data-set:classic-display
				contextParams="<%= objectEntryDisplayContext.getRelationshipContextParams() %>"
				creationMenu="<%= objectEntryDisplayContext.getRelatedModelCreationMenu(objectDefinition2) %>"
				dataProviderKey="<%= ObjectEntriesFDSNames.RELATED_MODELS %>"
				formName="fm"
				id="<%= ObjectEntriesFDSNames.RELATED_MODELS %>"
				style="fluid"
			/>
		</c:otherwise>
	</c:choose>
</aui:form>

<c:if test="<%= !objectEntryDisplayContext.isDefaultUser() %>">
	<aui:script sandbox="<%= true %>">
		const eventHandlers = [];

		const selectRelatedModelHandler = Liferay.on(
			'<portlet:namespace />selectRelatedModel',
			() => {
				Liferay.Util.openSelectionModal({
					multiple: false,
					onSelect: (selectedItem) => {
						const objectEntry = JSON.parse(selectedItem.value);

						const objectRelationshipPrimaryKey2Input = document.getElementById(
							'<portlet:namespace />objectRelationshipPrimaryKey2'
						);

						objectRelationshipPrimaryKey2Input.value = objectEntry.classPK;

						const form = document.getElementById('<portlet:namespace />fm');

						if (form) {
							submitForm(form);
						}
					},
					selectEventName: '<portlet:namespace />selectRelatedModalEntry',
					title: '<liferay-ui:message key="select" />',
					url:
						'<%= objectEntryDisplayContext.getRelatedObjectEntryItemSelectorURL() %>',
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
</c:if>