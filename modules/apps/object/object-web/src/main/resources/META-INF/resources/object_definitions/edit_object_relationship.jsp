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

					<aui:model-context bean="<%= objectRelationship %>" model="<%= ObjectRelationship.class %>" />

					<aui:input name="label" required="<%= true %>" value="<%= objectRelationship.getLabel(themeDisplay.getLocale()) %>" />

					<aui:input disabled="<%= true %>" name="name" required="<%= true %>" value="<%= objectRelationship.getName() %>" />

					<aui:select disabled="<%= true %>" name="type" required="<%= true %>">
						<aui:option label="one-to-one" selected='<%= Objects.equals(objectRelationship.getType(), "one_to_one") %>' value="one_to_one" />
						<aui:option label="one-to-many" selected='<%= Objects.equals(objectRelationship.getType(), "one_to_many") %>' value="one_to_many" />
						<aui:option label="many-to-many" selected='<%= Objects.equals(objectRelationship.getType(), "many_to_many") %>' value="many_to_many" />
					</aui:select>

					<aui:select disabled="<%= true %>" name="object" required="<%= true %>">
						<aui:option label="<%= objectDefinition.getShortName() %>" selected="<%= true %>" value="<%= objectDefinition.getObjectDefinitionId() %>" />
					</aui:select>
				</div>
			</div>

			<div class="side-panel-content__footer">
				<aui:button cssClass="btn-cancel mr-1" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />

				<aui:button name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
			</div>
		</div>
	</form>
</liferay-frontend:side-panel-content>

<script>
	function getNode(name) {
		return document.querySelector('#<portlet:namespace />' + name);
	}

	function <portlet:namespace />saveObjectRelationship() {
		const localizedInputs = document.querySelectorAll(
			"input[id^='<portlet:namespace />'][type='hidden']"
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
					label: localizedLabels,
				}),
				headers: new Headers({
					Accept: 'application/json',
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