<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<aui:input label="please-provide-the-reason-for-your-request" name="justification" required="<%= true %>" />
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