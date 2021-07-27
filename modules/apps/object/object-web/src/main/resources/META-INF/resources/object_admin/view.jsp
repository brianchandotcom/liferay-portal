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
ObjectAdminDisplayContext objectAdminDisplayContext = (ObjectAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div id="<portlet:namespace />addObjectDefinition" style="display: none;">
	<aui:input name="name" required="<%= true %>" />
</div>

<clay:headless-data-set-display
	apiURL="<%= objectAdminDisplayContext.getAPIURL() %>"
	clayDataSetActionDropdownItems="<%= objectAdminDisplayContext.getClayDataSetActionDropdownItems() %>"
	creationMenu="<%= objectAdminDisplayContext.getCreationMenu() %>"
	formId="fm"
	id="<%= ObjectAdminDataSetConstants.OBJECT_ADMIN_DATA_SET_KEY %>"
	itemsPerPage="<%= 20 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= liferayPortletResponse.createRenderURL() %>"
	style="fluid"
/>

<script>
	function handleCreateObjectDefinitionClick(event) {
		event.preventDefault();

		const addObjectDefinition = document.querySelector(
			'#<portlet:namespace />addObjectDefinition'
		);

		Liferay.Util.openModal({
			title: '<liferay-ui:message key="create-new-object" />',
			bodyHTML: addObjectDefinition.innerHTML,
			buttons: [
				{
					displayType: 'secondary',
					label: '<liferay-ui:message key="cancel" />',
					type: 'cancel',
				},
				{
					label: '<liferay-ui:message key="save" />',
					onClick: () => {
						const name = document.querySelector(
							'.modal-body #<portlet:namespace />name'
						);

						const formattedData = {
							name: name.value,
							objectFields: [],
						};

						Liferay.Util.fetch(
							'<%= objectAdminDisplayContext.getAPIURL() %>',
							{
								headers: new Headers({
									Accept: 'application/json',
									'Content-Type': 'application/json',
								}),
								body: JSON.stringify(formattedData),
								method: 'POST',
							}
						)
							.then((response) => {
								if (response.ok) {
									window.location.reload();
								}
								else {
									return response.json();
								}
							})
							.then(({title}) => {
								Liferay.Util.openToast({
									message: title,
									type: 'danger',
								});
							});
					},
				},
			],
		});
	}

	function handleDestroyPortlet() {
		Liferay.detach('destroyPortlet', handleDestroyPortlet);
		Liferay.detach('addObjectDefinition', handleCreateObjectDefinitionClick);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
	Liferay.on('addObjectDefinition', handleCreateObjectDefinitionClick);
</script>