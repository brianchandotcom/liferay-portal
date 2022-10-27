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

<portlet:actionURL name="/on_demand_admin/request_admin_access" var="requestAdminAccessURL">
	<portlet:param name="companyId" value='<%= ParamUtil.getString(request, "companyId") %>' />
</portlet:actionURL>

<div class="container-fluid container-fluid-max-xl">
	<liferay-frontend:edit-form
		action="<%= requestAdminAccessURL %>"
		method="post"
		name="requestAdminAccessFm"
		onSubmit="event.preventDefault();"
		validateOnBlur="<%= false %>"
	>
		<liferay-frontend:edit-form-body>
			<aui:input autoFocus="<%= true %>" label="please-provide-the-reason-for-your-request" name="justification" required="<%= true %>" />
		</liferay-frontend:edit-form-body>

		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons
				submitLabel="submit"
			/>
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</div>

<aui:script>
	const form = document.getElementById(
		'<portlet:namespace />requestAdminAccessFm'
	);

	form.addEventListener('submit', (event) => {
		const redirectURL = new URL(form.action);

		const input = form.querySelector(
			'input#<portlet:namespace />justification'
		);

		redirectURL.searchParams.set(
			'<portlet:namespace />justification',
			input.value
		);

		window.open(redirectURL.toString(), '_blank', 'noopener,noreferrer');

		Liferay.Util.getOpener().Liferay.fire('closeModal');
	});
</aui:script>