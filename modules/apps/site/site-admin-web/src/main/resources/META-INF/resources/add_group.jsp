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
AddGroupDisplayContext addGroupDisplayContext = new AddGroupDisplayContext(request, renderResponse);
%>

<clay:container-fluid>
	<liferay-frontend:edit-form
		action="<%= addGroupDisplayContext.getAddGroupURL() %>"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
	>
		<liferay-frontend:edit-form-body>
			<aui:input autoFocus="<%= true %>" label="name" name="name" required="<%= true %>" />

			<c:if test="<%= addGroupDisplayContext.hasRequiredVocabularies() %>">
				<aui:fieldset cssClass="mb-4">
					<div class="h3 sheet-subtitle"><liferay-ui:message key="categorization" /></div>

					<c:choose>
						<c:when test="<%= addGroupDisplayContext.isShowCategorization() %>">
							<liferay-asset:asset-categories-selector
								className="<%= Group.class.getName() %>"
								classPK="<%= 0 %>"
								groupIds="<%= addGroupDisplayContext.getGroupIds() %>"
								showOnlyRequiredVocabularies="<%= true %>"
								visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
							/>
						</c:when>
						<c:otherwise>
							<div class="alert alert-warning text-justify">
								<liferay-ui:message key="sites-have-required-vocabularies.-you-need-to-create-at-least-one-category-in-all-required-vocabularies-in-order-to-create-a-site" />
							</div>
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</c:if>
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<clay:button
				id='<%= liferayPortletResponse.getNamespace() + "addButton" %>'
				label="add"
				type="submit"
			/>

			<clay:button
				cssClass="btn-cancel"
				displayType="secondary"
				label="cancel"
			/>
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</clay:container-fluid>

<aui:script>
	var addButton = document.getElementById('<portlet:namespace />addButton');

	var form = document.<portlet:namespace />fm;

	form.addEventListener('submit', (event) => {
		event.preventDefault();
		event.stopPropagation();

		if (addButton.disabled) {
			return;
		}

		addButton.disabled = true;

		var formData = new FormData();

		formData.append('p_auth', Liferay.authToken);

		formActionURL = new URL(form.action);

		formActionURL.searchParams.delete('p_auth');

		form.action = formActionURL;

		Array.prototype.slice
			.call(form.querySelectorAll('input'))
			.forEach((input) => {
				if (input.type == 'checkbox' && !input.checked) {
					return;
				}

				if (input.name && input.value) {
					formData.append(input.name, input.value);
				}
			});

		Liferay.Util.fetch(form.action, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				return response.json();
			})
			.then((response) => {
				if (response.redirectURL) {
					var redirectURL = new URL(
						response.redirectURL,
						window.location.origin
					);

					redirectURL.searchParams.set('p_p_state', 'normal');

					var opener = Liferay.Util.getOpener();

					opener.Liferay.fire('closeModal', {
						id: '<portlet:namespace />addSiteDialog',
						redirect: redirectURL.toString(),
					});
				}
				else {
					Liferay.Util.openToast({
						message: response.error,
						type: 'danger',
					});

					addButton.disabled = false;
				}
			});
	});
</aui:script>