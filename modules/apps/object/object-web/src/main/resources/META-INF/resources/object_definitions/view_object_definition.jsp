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

ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "edit-x", objectDefinition.getName(), false));
%>

<liferay-frontend:edit-form
	action="javascript:;"
	onSubmit='<%= liferayPortletResponse.getNamespace() + "submitObjectDefinition();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-frontend:edit-form-body>
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "information") %>
		</h2>

		<liferay-frontend:fieldset-group>
			<clay:sheet-section>
				<h3 class="sheet-subtitle">
					<%= LanguageUtil.get(request, "object-definition-data") %>
				</h3>

				<clay:row>
					<clay:col
						md="11"
					>
						<aui:input cssClass="disabled" label="object-definition-id" name="objectDefinitionId" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getObjectDefinitionId()) %>" />

						<aui:input cssClass="disabled" label="name" name="name" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getName()) %>" />

						<aui:input cssClass="disabled" label="object-definition-table-name" name="dbTableName" readonly="true" type="text" value="<%= String.valueOf(objectDefinition.getDBTableName()) %>" />
					</clay:col>
				</clay:row>

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= objectDefinition.isSystem() %>" />
				</aui:field-wrapper>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button disabled="<%= true %>" name="save" onClick='<%= liferayPortletResponse.getNamespace() + "saveObjectDefinition();" %>' value='<%= LanguageUtil.get(request, "save") %>' />

		<aui:button disabled="<%= objectDefinition.getStatus() == WorkflowConstants.STATUS_APPROVED %>" name="publish" type="submit" value='<%= LanguageUtil.get(request, "publish") %>' />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	function <portlet:namespace />submitObjectDefinition() {
		const objectDefinitionId =
			'<%= objectDefinition.getObjectDefinitionId() %>';

		Liferay.Util.fetch(
			'/o/object-admin/v1.0/object-definitions/' +
				objectDefinitionId +
				'/publish',
			{
				body: JSON.stringify({id: objectDefinitionId}),
				headers: new Headers({
					Accept: 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'POST',
			}
		)
			.then((response) => {
				if (response.status === 401) {
					window.location.reload();
				}
				else if (response.ok) {
					Liferay.Util.openToast({
						message:
							'<%= LanguageUtil.get(request, "the-object-was-published-successfully") %>',
						type: 'success',
					});

					const publishButton = document.querySelector(
						'#<portlet:namespace />publish'
					);
					publishButton.setAttribute('disabled', true);
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

	function <portlet:namespace />saveObjectDefinition() {}
</script>