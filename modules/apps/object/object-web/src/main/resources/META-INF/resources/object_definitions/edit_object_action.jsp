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
ObjectDefinitionsActionsDisplayContext objectDefinitionsActionsDisplayContext = (ObjectDefinitionsActionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectAction objectAction = objectDefinitionsActionsDisplayContext.getObjectAction();
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "action") %>'
>
	<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveObjectAction();" %>">
		<div class="side-panel-content">
			<div class="side-panel-content__body">
				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="basic-info" />
					</h2>

					<aui:model-context bean="<%= objectAction %>" model="<%= ObjectAction.class %>" />

					<aui:input name="name" required="<%= true %>" value="<%= objectAction.getName() %>" />

					<aui:model-context bean="<%= null %>" model="<%= null %>" />

					<aui:input disabled="<%= true %>" label="when" name="objectActionTriggerKey" value="<%= LanguageUtil.get(request, objectAction.getObjectActionTriggerKey()) %>" />
					<aui:input disabled="<%= true %>" label="then" name="objectActionExecutorKey" value="<%= LanguageUtil.get(request, objectAction.getObjectActionExecutorKey()) %>" />

					<aui:model-context bean="<%= objectAction %>" model="<%= ObjectAction.class %>" />

					<aui:field-wrapper cssClass="form-group lfr-input-text-container">
						<aui:input label="" labelOff="inactive" labelOn="active" name="active" type="toggle-switch" value="<%= objectAction.isActive() %>" />
					</aui:field-wrapper>

					<%= objectDefinitionsActionsDisplayContext.renderDDMForm(pageContext) %>
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
	function <portlet:namespace />saveObjectAction() {
		const active = document.getElementById('<portlet:namespace />active');

		const name = document.getElementById('<portlet:namespace />name');

		var values = {};

		const DDMFormInstance = Liferay.component(
			'editObjectActionExecutorSettings'
		);

		if (DDMFormInstance) {
			const current = DDMFormInstance.reactComponentRef.current;

			const fields = current.getFields();

			values = fields
				.map((field) => {
					const {fieldName, type, value} = field;

					if (type === 'object_field' || type === 'select') {
						let newValue = value;

						if (newValue && typeof newValue === 'string') {
							try {
								newValue = JSON.parse(newValue);
							}
							catch (error) {}
						}

						if (Array.isArray(newValue)) {
							newValue = newValue[0];
						}

						return {
							fieldName,
							value: newValue,
						};
					}

					return field;
				})
				.reduce(
					(obj, cur) => Object.assign(obj, {[cur.fieldName]: cur.value}),
					{}
				);
		}

		Liferay.Util.fetch(
			'/o/object-admin/v1.0/object-actions/<%= objectAction.getObjectActionId() %>',
			{
				body: JSON.stringify({
					active: active.checked,
					name: name.value,
					parameters: values,
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
							'<%= LanguageUtil.get(request, "the-object-action-was-updated-successfully") %>',
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