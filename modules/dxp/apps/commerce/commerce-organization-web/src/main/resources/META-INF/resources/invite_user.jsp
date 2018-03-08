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
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();
%>

<liferay-util:buffer var="removeUserEmailAddressIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="inviteUser" var="inviteUserActionURL" />

<aui:form action="<%= inviteUserActionURL %>" method="post" name="inviteUserFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />
	<aui:input name="emailAddresses" type="hidden" />

	<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />

	<div class="lfr-form-content">
		<div class="user-invitation-header">
			<aui:input label="" name="emailAddress" type="text" />

			<aui:button name="addButton" value="add" />
		</div>

		<div id="<portlet:namespace />userInvitationContent"></div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" value="save" />

		<aui:button cssClass="btn-lg" name="cancelButton" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	$('#<portlet:namespace />addButton').on(
		'click',
		function(event) {
			var inputValue = $('#<portlet:namespace />emailAddress').val();

			if (inputValue) {
				var content =
					'<span class="label label-dismissible label-secondary label-user-mail-address">' +
						inputValue +
							'<a class="modify-link" data-emailAddress="' +
								inputValue +
									'" href="javascript:;"><%= UnicodeFormatter.toString(removeUserEmailAddressIcon) %>' +
										'</a></span>'

				$('#<portlet:namespace />userInvitationContent').append(content);
			}
		}
	);
</aui:script>

<aui:script use="aui-base,aui-io-request">
	A.one('#<portlet:namespace />userInvitationContent').delegate(
		'click',
		function(event) {
			var curTarget = event.currentTarget;

			var node = curTarget.ancestor('span');

			node.remove();
		},
		'.modify-link'
	);

	A.one('#<portlet:namespace />saveButton').on(
		'click',
		function(event) {
			var A = AUI();

			var emailAddresses = [];

			var nodes = A.all('.modify-link');

			nodes._nodes.forEach(
				function(item, index) {
					var emailAddress = item.attributes["data-emailAddress"].value;

					emailAddresses.push(emailAddress);
				},
				'callback'
			);

			document.<portlet:namespace />inviteUserFm.<portlet:namespace />emailAddresses.value = emailAddresses.join(',');

			var url = '<%= inviteUserActionURL.toString() %>';

			A.io.request(
				url,
				{
					form: {
						id: '<portlet:namespace />inviteUserFm'
					},
					method: 'POST',
					on: {
						success: function() {
							Liferay.Portlet.refresh('#p_p_id<portlet:namespace/>');

							Liferay.Util.getOpener().closePopup('inviteUserDialog');
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace />cancelButton').on(
		'click',
		function(event) {
			Liferay.Util.getOpener().closePopup('inviteUserDialog');
		}
	);
</aui:script>