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
ListTypeDefinition listTypeDefinition = (ListTypeDefinition)request.getAttribute(ObjectWebKeys.LIST_TYPE_DEFINITION);

ViewListTypeEntriesDisplayContext viewListTypeEntriesDisplayContext = (ViewListTypeEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "picklist") %>'
>
	<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveListTypeDefinition();" %>">
		<div class="side-panel-content">
			<div class="side-panel-content__body">
				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="basic-info" />
					</h2>

					<aui:model-context bean="<%= listTypeDefinition %>" model="<%= ListTypeDefinition.class %>" />

					<aui:input name="name" required="<%= true %>" value="<%= listTypeDefinition.getName(themeDisplay.getLocale()) %>" />
				</div>

				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="items" />
					</h2>

					<clay:headless-data-set-display
						apiURL="<%= viewListTypeEntriesDisplayContext.getAPIURL() %>"
						clayDataSetActionDropdownItems="<%= viewListTypeEntriesDisplayContext.getClayDataSetActionDropdownItems() %>"
						creationMenu="<%= viewListTypeEntriesDisplayContext.getCreationMenu() %>"
						formId="fm"
						id="<%= ListTypeDefinitionsClayDataSetDisplayNames.LIST_TYPE_DEFINITION_ITEMS %>"
						itemsPerPage="<%= 20 %>"
						namespace="<%= liferayPortletResponse.getNamespace() %>"
						pageNumber="<%= 1 %>"
						portletURL="<%= liferayPortletResponse.createRenderURL() %>"
						style="fluid"
					/>
				</div>
			</div>

			<div class="side-panel-content__footer">
				<aui:button cssClass="btn-cancel mr-1" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />

				<aui:button name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
			</div>
		</div>
	</form>
</liferay-frontend:side-panel-content>

<div id="<portlet:namespace />addListTypeEntry">
	<react:component
		module="js/components/ModalAddListTypeEntry"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", viewListTypeEntriesDisplayContext.getAPIURL()
			).put(
				"spritemap", themeDisplay.getPathThemeImages() + "/clay/icons.svg"
			).build()
		%>'
	/>
</div>

<script>
	function <portlet:namespace />saveListTypeDefinition() {
		const inputName = document.querySelector('#<portlet:namespace />name');

		const localizedInputs = document.querySelectorAll(
			"input[id^='<portlet:namespace />'][type='hidden']"
		);
		const localizedNames = Array(...localizedInputs).reduce(
			(prev, cur, index) => {
				if (cur.value) {
					const language = cur.id.replace(
						'<portlet:namespace />name_',
						''
					);
					const formattedLanguage = language.replace('_', '-');

					prev[formattedLanguage] = cur.value;
				}

				return prev;
			},
			{}
		);

		Liferay.Util.fetch(
			'/o/headless-admin-list-type/v1.0/list-type-definitions/<%= listTypeDefinition.getListTypeDefinitionId() %>',
			{
				body: JSON.stringify({
					name_i18n: localizedNames,
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
							'<%= LanguageUtil.get(request, "the-picklist-was-updated-successfully") %>',
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
						message: title,
						type: 'danger',
					});
				}
			});
	}

	function handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</script>