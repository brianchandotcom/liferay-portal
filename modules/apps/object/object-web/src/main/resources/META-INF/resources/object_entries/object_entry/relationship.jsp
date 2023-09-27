<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ObjectEntryDisplayContext objectEntryDisplayContext = (ObjectEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectDefinition objectDefinition2 = objectEntryDisplayContext.getObjectDefinition2();
ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();
ObjectRelationship objectRelationship = objectEntryDisplayContext.getObjectRelationship();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry_related_model" var="editObjectEntryRelatedModelActionURL" />

<aui:form action="<%= editObjectEntryRelatedModelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="objectRelationshipId" type="hidden" value="<%= objectRelationship.getObjectRelationshipId() %>" />
	<aui:input name="objectEntryId" type="hidden" value="<%= (objectEntry == null) ? 0 : objectEntry.getObjectEntryId() %>" />
	<aui:input name="objectRelationshipPrimaryKey2" type="hidden" value="" />

	<c:choose>
		<c:when test="<%= objectDefinition2.isUnmodifiableSystemObject() %>">
			<frontend-data-set:classic-display
				contextParams="<%= objectEntryDisplayContext.getRelationshipContextParams() %>"
				creationMenu="<%= objectEntryDisplayContext.getRelatedModelCreationMenu(objectRelationship) %>"
				dataProviderKey="<%= ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS %>"
				formName="fm"
				id="<%= ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS %>"
				style="fluid"
			/>
		</c:when>
		<c:otherwise>
			<frontend-data-set:classic-display
				contextParams="<%= objectEntryDisplayContext.getRelationshipContextParams() %>"
				creationMenu="<%= objectEntryDisplayContext.getRelatedModelCreationMenu(objectRelationship) %>"
				dataProviderKey="<%= ObjectEntriesFDSNames.RELATED_MODELS %>"
				formName="fm"
				id="<%= ObjectEntriesFDSNames.RELATED_MODELS %>"
				style="fluid"
			/>
		</c:otherwise>
	</c:choose>
</aui:form>

<c:if test="<%= !objectEntryDisplayContext.isGuestUser() %>">
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
						'<%= objectEntryDisplayContext.getRelatedObjectEntryItemSelectorURL(objectRelationship) %>',
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