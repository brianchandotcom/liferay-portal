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

ObjectDefinition objectDefinition = objectEntryDisplayContext.getObjectDefinition();
ObjectEntry objectEntry = objectEntryDisplayContext.getObjectEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/object_entries/edit_object_entry" var="editObjectEntryURL" />

<liferay-frontend:edit-form
	action="<%= editObjectEntryURL %>"
	name="fm"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (objectEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="objectEntryId" type="hidden" value="<%= (objectEntry == null) ? 0 : objectEntry.getObjectEntryId() %>" />
		<aui:input name="objectDefinitionId" type="hidden" value="<%= objectDefinition.getObjectDefinitionId() %>" />
		<aui:input name="ddmFormValues" type="hidden" value="" />

		<liferay-frontend:fieldset-group>
			<clay:sheet-section>
				<clay:row>
					<clay:col
						md="12"
					>
						<%= objectEntryDisplayContext.renderDDMForm(pageContext) %>
					</clay:col>
				</clay:row>
			</clay:sheet-section>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="save" onClick='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "submitObjectEntry();" %>' type="submit" value="save" />

		<aui:button href="<%= backURL %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />getObjectEntryId() {
		return Number(
			'<%= (objectEntry == null) ? 0 : objectEntry.getObjectEntryId() %>'
		);
	}

	function <portlet:namespace />getPath(objectEntryId) {
		const scope = '<%= objectDefinition.getScope() %>';
		const contextPath = '/o<%= objectDefinition.getRESTContextPath() %>';
		const pathScopedBySite = contextPath.concat(
			`/scopes/\${themeDisplay.getSiteGroupId()}`
		);

		const postPath = scope === 'site' ? pathScopedBySite : contextPath;
		const putPath = contextPath.concat('/', `\${objectEntryId}`);

		return objectEntryId ? putPath : postPath;
	}

	function <portlet:namespace />getValues(fields) {
		return fields.reduce((obj, field) => {
			let value = field.value;
			if (field.type === 'select' && !field.multiple) {
				value = {key: field.value[0]};
			}

			return Object.assign(obj, {[field.fieldName]: value});
		}, {});
	}

	Liferay.provide(
		window,
		'<portlet:namespace />submitObjectEntry',
		() => {
			const form = document.getElementById('<portlet:namespace />fm');

			const DDMFormInstance = Liferay.component('editObjectEntry');

			const current = DDMFormInstance.reactComponentRef.current;

			current.validate().then((result) => {
				if (result) {
					const fields = current.getFields();
					let shouldSubmitForm = true;

					fields.forEach((field) => {
						if (field.type === 'text' && field.value.length > 280) {
							shouldSubmitForm = false;

							Liferay.Util.openToast({
								message:
									'<liferay-ui:message key="the-maximum-length-is-280-characters-for-text-fields" />',
								type: 'warning',
							});

							return false;
						}
					});

					if (shouldSubmitForm) {
						const values = <portlet:namespace />getValues(fields);
						const objectEntryId = <portlet:namespace />getObjectEntryId();
						const path = <portlet:namespace />getPath(objectEntryId);

						Liferay.Util.fetch(path, {
							body: JSON.stringify(values),
							headers: new Headers({
								'Accept': 'application/json',
								'Content-Type': 'application/json',
							}),
							method: objectEntryId ? 'PUT' : 'POST',
						})
							.then((response) => {
								if (response.status === 401) {
									window.location.reload();
								}
								else if (response.ok) {
									Liferay.Util.openToast({
										message:
											'<%= LanguageUtil.get(request, "your-request-completed-successfully") %>',
										type: 'success',
									});

									response.json().then((payload) => {
										var portletURL = new Liferay.PortletURL.createURL(
											'<%= currentURLObj %>'
										);

										portletURL.setParameter(
											'objectEntryId',
											payload.id
										);

										Liferay.Util.navigate(
											portletURL.toString()
										);
									});
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
				}
			});
		},
		['liferay-portlet-url']
	);
</aui:script>