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
ObjectField objectField = (ObjectField)request.getAttribute(ObjectWebKeys.OBJECT_FIELD);
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/side-panel.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= ObjectDefinitionsScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_OBJECT_FIELD %>"
	screenNavigatorModelBean="<%= (ObjectField)request.getAttribute(ObjectWebKeys.OBJECT_FIELD) %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title='<%= LanguageUtil.get(request, "field") %>'
/>

<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveObjectField();" %>">
	<div class="side-panel-content">
		<div class="side-panel-content__body">
			<div class="sheet">
				<h2 class="sheet-title">
					<liferay-ui:message key="basic-info" />
				</h2>

				<aui:input disabled="<%= true %>" name="name" required="<%= true %>" value="<%= objectField.getName() %>" />

				<aui:select disabled="<%= true %>" name="type" required="<%= true %>">
					<aui:option label="<%= objectField.getType() %>" value="<%= objectField.getType() %>" />
				</aui:select>

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input inlineLabel="right" label='<%= LanguageUtil.get(request, "mandatory") %>' labelCssClass="simple-toggle-switch" name="required" type="toggle-switch" value="<%= objectField.getRequired() %>" />
				</aui:field-wrapper>
			</div>

			<div class="mt-4 sheet" style="display: <%= objectField.getType().equals("Blob") ? "none;" : "block;" %>">
				<h2 class="sheet-title">
					<liferay-ui:message key="searchable" />
				</h2>

				<aui:field-wrapper cssClass="form-group lfr-input-text-container">
					<aui:input inlineLabel="right" label='<%= LanguageUtil.get(request, "searchable") %>' labelCssClass="simple-toggle-switch" name="indexed" onChange='<%= liferayPortletResponse.getNamespace() + "onChangeIndexed(event);" %>' type="toggle-switch" value="<%= objectField.getIndexed() %>" />
				</aui:field-wrapper>

				<div id="<portlet:namespace />indexedGroup" style="display: <%= (objectField.getType().equals("String") && objectField.getIndexed()) ? "block;" : "none;" %>">
					<div class="form-group">
						<clay:radio
							checked="<%= objectField.getIndexedAsKeyword() %>"
							id='<%= liferayPortletResponse.getNamespace() + "inputIndexedTypeKeyword" %>'
							label='<%= LanguageUtil.get(request, "keyword") %>'
							name="indexedType"
							onChange='<%= liferayPortletResponse.getNamespace() + "onChangeIndexedType('keyword');" %>'
						/>

						<clay:radio
							checked="<%= !objectField.getIndexedAsKeyword() %>"
							id='<%= liferayPortletResponse.getNamespace() + "inputIndexedTypeText" %>'
							label='<%= LanguageUtil.get(request, "text") %>'
							name="indexedType"
							onChange='<%= liferayPortletResponse.getNamespace() + "onChangeIndexedType('text');" %>'
						/>
					</div>

					<div id="<portlet:namespace />indexedLanguageIdGroup" style="display: <%= !objectField.getIndexedAsKeyword() ? "block;" : "none;" %>">
						<aui:select label='<%= LanguageUtil.get(request, "language") %>' name="indexedLanguageId">

							<%
							for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
							%>

								<aui:option label="<%= availableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(availableLocale) %>" selected="<%= LocaleUtil.toLanguageId(availableLocale).equals(objectField.getIndexedLanguageId()) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</div>
			</div>
		</div>

		<div class="side-panel-content__footer">
			<aui:button cssClass="btn-cancel mr-1" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />

			<aui:button name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
		</div>
	</div>
</form>

<script>
	function getNode(name) {
		return document.querySelector('#<portlet:namespace />' + name);
	}

	function <portlet:namespace />saveObjectField() {
		const inputName = getNode('name');
		const inputType = getNode('type');
		const inputRequired = getNode('required');
		const inputIndexed = getNode('indexed');
		const inputIndexedTypeKeyword = getNode(
			'inputIndexedTypeKeyword'
		).querySelector('input');
		const inputIndexedTypeText = getNode('inputIndexedTypeText').querySelector(
			'input'
		);
		const inputIndexedLanguageId = getNode('indexedLanguageId');

		const indexed = inputIndexed.checked;
		const indexedAsKeyword =
			inputIndexed.checked && inputIndexedTypeKeyword.checked;
		const indexedLanguageId =
			inputIndexed.checked &&
			inputIndexedTypeText.checked &&
			inputType.value === 'String'
				? inputIndexedLanguageId.value
				: null;
		const name = inputName.value;
		const required = inputRequired.checked;
		const type = inputType.value;

		Liferay.Util.fetch(
			'/o/object-admin/v1.0/object-fields/<%= objectField.getObjectFieldId() %>',
			{
				body: JSON.stringify({
					indexed,
					indexedAsKeyword,
					indexedLanguageId,
					name,
					required,
					type,
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
							'<%= LanguageUtil.get(request, "the-object-was-published-successfully") %>',
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
				if (response.title) {
					Liferay.Util.openToast({
						message: response.title,
						type: 'danger',
					});
				}
			});
	}

	function <portlet:namespace />onChangeIndexed(event) {
		const indexedGroup = getNode('indexedGroup');
		const type = '<%= objectField.getType() %>';

		indexedGroup.style.display =
			event.target.checked && type === 'String' ? 'block' : 'none';
	}

	function <portlet:namespace />onChangeIndexedType(value) {
		const indexedLanguageIdGroup = getNode('indexedLanguageIdGroup');

		indexedLanguageIdGroup.style.display = value === 'text' ? 'block' : 'none';
	}
</script>