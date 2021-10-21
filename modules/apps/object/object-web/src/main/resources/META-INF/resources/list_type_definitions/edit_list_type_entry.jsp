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
ListTypeEntry listTypeEntry = (ListTypeEntry)request.getAttribute(ObjectWebKeys.LIST_TYPE_ENTRY);
%>

<form action="javascript:;" onSubmit="<%= liferayPortletResponse.getNamespace() + "editListTypeEntry();" %>">
	<div class="modal-header">
		<div class="modal-title">
			<%= LanguageUtil.get(request, "edit-item") %>
		</div>

		<button aria-label='<%= LanguageUtil.get(request, "close") %>'' class="btn btn-unstyled close modal-closer" type="button">
			<clay:icon
				symbol="times"
			/>
		</button>
	</div>

	<div class="modal-body">
		<aui:model-context bean="<%= listTypeEntry %>" model="<%= ListTypeEntry.class %>" />

		<aui:input name="name" required="<%= true %>" value="<%= listTypeEntry.getName(themeDisplay.getLocale()) %>" />

		<aui:input disabled="<%= true %>" name="key" required="<%= true %>" value="<%= listTypeEntry.getKey() %>" />
	</div>

	<div class="modal-footer">
		<div class="modal-item-first"></div>
		<div class="modal-item"></div>
		<div class="modal-item-last">
			<div class="btn-group" role="group">
				<div class="btn-group-item">
					<aui:button cssClass="btn-cancel modal-closer" name="cancel" value='<%= LanguageUtil.get(request, "cancel") %>' />
				</div>

				<div class="btn-group-item">
					<aui:button name="save" type="submit" value='<%= LanguageUtil.get(request, "save") %>' />
				</div>
			</div>
		</div>
	</div>
</form>

<script>
	const parentWindow = Liferay.Util.getOpener();

	parentWindow.Liferay.fire('is-loading-modal', {isLoading: false});

	document.querySelectorAll('.modal-closer').forEach((trigger) => {
		trigger.addEventListener('click', (e) => {
			e.preventDefault();
			parentWindow.Liferay.fire('close-modal');
		});
	});

	function normalizeLanguageId(languageId) {
		return languageId.replace('_', '-');
	}

	function <portlet:namespace />editListTypeEntry() {
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
					prev[normalizeLanguageId(language)] = cur.value;
				}
				return prev;
			},
			{}
		);

		if (
			!localizedNames[
				normalizeLanguageId(themeDisplay.getDefaultLanguageId())
			]
		) {
			Liferay.Util.openToast({
				message:
					'<%= LanguageUtil.get(request, "name-must-not-be-empty") %>',
				type: 'danger',
			});

			return;
		}

		Liferay.Util.fetch(
			'/o/headless-admin-list-type/v1.0/list-type-entries/<%= listTypeEntry.getListTypeEntryId() %>',
			{
				body: JSON.stringify({
					name_i18n: localizedNames,
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
							'<%= LanguageUtil.get(request, "the-picklist-item-was-updated-successfully") %>',
						type: 'success',
					});
					setTimeout(() => {
						parentWindow.Liferay.fire('close-modal');
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

	function <portlet:namespace />handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', <portlet:namespace />handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', <portlet:namespace />handleDestroyPortlet);
</script>