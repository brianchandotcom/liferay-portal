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
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsRelationshipsDisplayContext objectDefinitionsRelationshipsDisplayContext = (ObjectDefinitionsRelationshipsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectRelationship objectRelationship = (ObjectRelationship)request.getAttribute(ObjectWebKeys.OBJECT_RELATIONSHIP);
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "relationship") %>'
>
	<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveObjectRelationship();" %>">
		<div class="side-panel-content">
			<div class="side-panel-content__body">
				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="basic-info" />
					</h2>

					<c:if test="<%= objectRelationship.isReverse() %>">
						<clay:alert
							displayType="warning"
							message="reverse-object-relationships-cannot-be-updated"
						/>
					</c:if>

					<aui:model-context bean="<%= objectRelationship %>" model="<%= ObjectRelationship.class %>" />

					<aui:input disabled="<%= !objectDefinitionsRelationshipsDisplayContext.hasUpdateObjectDefinitionPermission() || objectRelationship.isReverse() %>" name="label" required="<%= true %>" value="<%= objectRelationship.getLabel(themeDisplay.getLocale()) %>" />

					<aui:input disabled="<%= true %>" name="name" required="<%= true %>" value="<%= objectRelationship.getName() %>" />

					<aui:select disabled="<%= true %>" name="type" required="<%= true %>">
						<c:choose>
							<c:when test="<%= objectDefinitionsRelationshipsDisplayContext.isFFOneToOneRelationshipConfigurationEnabled() %>">
								<aui:option label="one-to-one" selected="<%= Objects.equals(objectRelationship.getType(), ObjectRelationshipConstants.TYPE_ONE_TO_ONE) %>" value="<%= ObjectRelationshipConstants.TYPE_ONE_TO_ONE %>" />
							</c:when>
						</c:choose>

						<aui:option label="one-to-many" selected="<%= Objects.equals(objectRelationship.getType(), ObjectRelationshipConstants.TYPE_ONE_TO_MANY) %>" value="<%= ObjectRelationshipConstants.TYPE_ONE_TO_MANY %>" />
						<aui:option label="many-to-many" selected="<%= Objects.equals(objectRelationship.getType(), ObjectRelationshipConstants.TYPE_MANY_TO_MANY) %>" value="<%= ObjectRelationshipConstants.TYPE_MANY_TO_MANY %>" />
					</aui:select>

					<aui:select disabled="<%= true %>" name="object" required="<%= true %>">
						<aui:option label="<%= objectDefinition.getShortName() %>" selected="<%= true %>" value="<%= objectDefinition.getObjectDefinitionId() %>" />
					</aui:select>

					<aui:select disabled="<%= !objectDefinitionsRelationshipsDisplayContext.hasUpdateObjectDefinitionPermission() || objectRelationship.isReverse() %>" name="deletionType" required="<%= true %>">
						<aui:option label="cascade" selected="<%= Objects.equals(objectRelationship.getDeletionType(), ObjectRelationshipConstants.DELETION_TYPE_CASCADE) %>" value="<%= ObjectRelationshipConstants.DELETION_TYPE_CASCADE %>" />
						<aui:option label="disassociate" selected="<%= Objects.equals(objectRelationship.getDeletionType(), ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE) %>" value="<%= ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE %>" />
						<aui:option label="prevent" selected="<%= Objects.equals(objectRelationship.getDeletionType(), ObjectRelationshipConstants.DELETION_TYPE_PREVENT) %>" value="<%= ObjectRelationshipConstants.DELETION_TYPE_PREVENT %>" />
					</aui:select>
				</div>
			</div>

			<div class="side-panel-content__footer">
				<aui:button cssClass="btn-cancel mr-1" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />

				<aui:button disabled="<%= !objectDefinitionsRelationshipsDisplayContext.hasUpdateObjectDefinitionPermission() || objectRelationship.isReverse() %>" name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
			</div>
		</div>
	</form>
</liferay-frontend:side-panel-content>

<script>
	function <portlet:namespace />saveObjectRelationship() {
		const localizedInputs = document.querySelectorAll(
			"input[id^='<portlet:namespace />'][type='hidden']"
		);

		const deletionType = document.getElementById(
			'<portlet:namespace />deletionType'
		);

		const localizedLabels = Array(...localizedInputs).reduce(
			(prev, cur, index) => {
				if (cur.value) {
					prev[cur.id.replace('<portlet:namespace />label_', '')] =
						cur.value;
				}

				return prev;
			},
			{}
		);

		Liferay.Util.fetch(
			'/o/object-admin/v1.0/object-relationships/<%= objectRelationship.getObjectRelationshipId() %>',
			{
				body: JSON.stringify({
					deletionType: deletionType.value,
					label: localizedLabels,
				}),
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'PUT',
			}
		)
			.then((response) => {
				if (response.status === 401) {
					window.location.reload();
				}
				else if (response.ok) {
					Liferay.Util.openToast({
						message:
							'<%= LanguageUtil.get(request, "the-object-relationship-was-updated-successfully") %>',
						type: 'success',
					});

					setTimeout(() => {
						const parentWindow = Liferay.Util.getOpener();
						parentWindow.Liferay.fire('close-side-panel');
					}, 1500);
				}
				else {
					return response.json();
				}
			})
			.then((response) => {
				if (response && response.title) {
					Liferay.Util.openToast({
						message: response.title,
						type: 'danger',
					});
				}
			});
	}
</script>