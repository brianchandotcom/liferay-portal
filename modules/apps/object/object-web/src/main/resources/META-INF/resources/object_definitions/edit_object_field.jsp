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
ObjectField objectField = (ObjectField)request.getAttribute(ObjectWebKeys.OBJECT_FIELD);
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "field") %>'
>
	<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "saveObjectField();" %>">
		<div class="side-panel-content">
			<div class="side-panel-content__body">
				<div class="sheet">
					<h2 class="sheet-title">
						<liferay-ui:message key="basic-info" />
					</h2>

					<aui:model-context bean="<%= objectField %>" model="<%= ObjectField.class %>" />

					<aui:input name="label" required="<%= true %>" value="<%= objectField.getLabel(themeDisplay.getLocale()) %>" />

					<aui:input disabled="<%= objectDefinition.isApproved() %>" name="name" required="<%= true %>" value="<%= objectField.getName() %>" />

					<aui:select disabled="<%= objectDefinition.isApproved() %>" name="type" onChange='<%= liferayPortletResponse.getNamespace() + "onChangeFieldType(event);" %>' required="<%= true %>">
						<aui:option label="BigDecimal" selected='<%= Objects.equals(objectField.getType(), "BigDecimal") %>' value="BigDecimal" />
						<aui:option label="Boolean" selected='<%= Objects.equals(objectField.getType(), "Boolean") %>' value="Boolean" />
						<aui:option label="Date" selected='<%= Objects.equals(objectField.getType(), "Date") %>' value="Date" />
						<aui:option label="Double" selected='<%= Objects.equals(objectField.getType(), "Double") %>' value="Double" />
						<aui:option label="Integer" selected='<%= Objects.equals(objectField.getType(), "Integer") %>' value="Integer" />
						<aui:option label="Long" selected='<%= Objects.equals(objectField.getType(), "Long") %>' value="Long" />
						<aui:option label="String" selected='<%= Objects.equals(objectField.getType(), "String") %>' value="String" />
					</aui:select>

					<aui:field-wrapper cssClass="form-group lfr-input-text-container">
						<aui:input disabled="<%= objectDefinition.isApproved() %>" inlineLabel="right" label='<%= LanguageUtil.get(request, "mandatory") %>' labelCssClass="simple-toggle-switch" name="required" type="toggle-switch" value="<%= objectField.getRequired() %>" />
					</aui:field-wrapper>
				</div>

				<div class="mt-4 sheet" id="<portlet:namespace />searchableContainer" style="display: <%= Objects.equals(objectField.getType(), "Blob") ? "none;" : "block;" %>">
					<h2 class="sheet-title">
						<liferay-ui:message key="searchable" />
					</h2>

					<aui:field-wrapper cssClass="form-group lfr-input-text-container">
						<aui:input disabled="<%= objectDefinition.isApproved() %>" inlineLabel="right" label='<%= LanguageUtil.get(request, "searchable") %>' labelCssClass="simple-toggle-switch" name="indexed" onChange='<%= liferayPortletResponse.getNamespace() + "onChangeSeachableSwitch(event);" %>' type="toggle-switch" value="<%= objectField.getIndexed() %>" />
					</aui:field-wrapper>

					<div id="<portlet:namespace />indexedGroup" style="display: <%= (Objects.equals(objectField.getType(), "String") && objectField.getIndexed()) ? "block;" : "none;" %>">
						<div class="form-group">
							<clay:radio
								checked="<%= objectField.getIndexed() && objectField.getIndexedAsKeyword() %>"
								disabled="<%= objectDefinition.isApproved() %>"
								id='<%= liferayPortletResponse.getNamespace() + "inputIndexedTypeKeyword" %>'
								label='<%= LanguageUtil.get(request, "keyword") %>'
								name="indexedType"
								onChange='<%= liferayPortletResponse.getNamespace() + "onChangeSeachableType('keyword');" %>'
							/>

							<clay:radio
								checked="<%= objectField.getIndexed() && !objectField.getIndexedAsKeyword() %>"
								disabled="<%= objectDefinition.isApproved() %>"
								id='<%= liferayPortletResponse.getNamespace() + "inputIndexedTypeText" %>'
								label='<%= LanguageUtil.get(request, "text") %>'
								name="indexedType"
								onChange='<%= liferayPortletResponse.getNamespace() + "onChangeSeachableType('text');" %>'
							/>
						</div>

						<div id="<portlet:namespace />indexedLanguageIdGroup" style="display: <%= (!objectField.getIndexed() || objectField.getIndexedAsKeyword()) ? "none;" : "block;" %>">
							<aui:select disabled="<%= objectDefinition.isApproved() %>" label='<%= LanguageUtil.get(request, "language") %>' name="indexedLanguageId">

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
</liferay-frontend:side-panel-content>

<script>
	function <portlet:namespace />onChangeFieldType(event) {
		const searchableContainer = document.querySelector(
			'#<portlet:namespace />searchableContainer'
		);
		const indexedGroup = document.querySelector(
			'#<portlet:namespace />indexedGroup'
		);
		const indexed = document.querySelector('#<portlet:namespace />indexed')
			.value;

		indexedGroup.style.display =
			indexed && event.target.value === 'String' ? 'block' : 'none';
		searchableContainer.style.display =
			event.target.value !== 'Blob' ? 'block' : 'none';
	}

	function <portlet:namespace />onChangeSeachableSwitch(event) {
		const indexedGroup = document.querySelector(
			'#<portlet:namespace />indexedGroup'
		);
		const type = document.querySelector('#<portlet:namespace />type').value;

		indexedGroup.style.display =
			event.target.checked && type === 'String' ? 'block' : 'none';
	}

	function <portlet:namespace />onChangeSeachableType(value) {
		const indexedLanguageIdGroup = document.querySelector(
			'#<portlet:namespace />indexedLanguageIdGroup'
		);

		indexedLanguageIdGroup.style.display = value === 'text' ? 'block' : 'none';
	}

	function <portlet:namespace />saveObjectField() {
		const inputIndexed = document.querySelector(
			'#<portlet:namespace />indexed'
		);
		const inputIndexedTypeKeyword = document
			.querySelector('#<portlet:namespace />inputIndexedTypeKeyword')
			.querySelector('input');
		const inputIndexedTypeText = document
			.querySelector('#<portlet:namespace />inputIndexedTypeText')
			.querySelector('input');
		const inputIndexedLanguageId = document.querySelector(
			'#<portlet:namespace />indexedLanguageId'
		);
		const inputLabel = document.querySelector('#<portlet:namespace />label');
		const inputName = document.querySelector('#<portlet:namespace />name');
		const inputRequired = document.querySelector(
			'#<portlet:namespace />required'
		);
		const inputType = document.querySelector('#<portlet:namespace />type');

		const indexed = inputIndexed.checked;
		const indexedAsKeyword =
			inputIndexed.checked && inputIndexedTypeKeyword.checked;
		const indexedLanguageId =
			inputIndexed.checked &&
			inputIndexedTypeText.checked &&
			inputType.value === 'String'
				? inputIndexedLanguageId.value
				: null;
		const label = {
			[themeDisplay.getDefaultLanguageId()]: inputLabel.value,
		};
		const name = inputName.value;
		const required = inputRequired.checked;
		const type = inputType.value;

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
			'/o/object-admin/v1.0/object-fields/<%= objectField.getObjectFieldId() %>',
			{
				body: JSON.stringify({
					indexed,
					indexedAsKeyword,
					indexedLanguageId,
					label: localizedLabels,
					listTypeDefinitionId: 0,
					name,
					required,
					type,
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
							'<%= LanguageUtil.get(request, "the-object-field-was-updated-successfully") %>',
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