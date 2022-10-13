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

<liferay-frontend:edit-form
	action="javascript:void(0);"
	name="inviteUsersFm"
	onSubmit='<%= liferayPortletResponse.getNamespace() + "submit();" %>'
>
	<liferay-frontend:edit-form-body>
		<aui:input helpMessage="please-enter-valid-email-addresses-separated-by-commas" label="email-addresses" name="emailAddresses" required="<%= true %>" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="invite" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />submit() {
		var form = Liferay.Form.get('<portlet:namespace />inviteUsersFm');

		var formValidator = form.formValidator;

		formValidator.validate();

		if (!formValidator.hasErrors()) {
			var openingLiferay = Liferay.Util.getOpener().Liferay;

			openingLiferay.fire(
				'<%= HtmlUtil.escapeJS(liferayPortletResponse.getNamespace() + "inviteUsers") %>',
				{
					accountEntryId:
						'<%= ParamUtil.getString(request, "accountEntryId") %>',
					emailAddresses: document.getElementById(
						'<portlet:namespace />emailAddresses'
					).value,
					redirect: '<%= ParamUtil.getString(request, "redirect") %>',
				}
			);
		}
	}
</aui:script>