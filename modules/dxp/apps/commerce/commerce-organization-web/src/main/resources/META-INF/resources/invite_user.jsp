<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();
%>

<portlet:actionURL name="inviteUser" var="inviteUserActionURL" />

<aui:form action="<%= inviteUserActionURL %>" method="post" name="inviteUserFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ASSIGN %>" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />
	<aui:input name="emailAddresses" type="hidden" />

	<liferay-ui:error exception="<%= UserEmailAddressException.MustValidate.class %>" message="please-enter-a-valid-email-address" />

	<div class="lfr-form-content">
		<div class="form-group-autofit">
			<aui:input label="" name="emailAddress" type="text" wrapperCssClass="form-group-item" />

			<aui:button cssClass="form-group-item-shrink" name="addButton" onClick='<%= renderResponse.getNamespace() + "addMember();" %>' value="add" />
		</div>

		<aui:container fluid="<%= true %>">
			<aui:row>
				<aui:col width="<%= 100 %>">
					<div id="<portlet:namespace />userInvitationContent"></div>
				</aui:col>
			</aui:row>
		</aui:container>
	</div>

	<aui:button-row>
		<aui:button name="saveButton" onClick='<%= renderResponse.getNamespace() + "submitFm();" %>' primary="<%= true %>" value="save" />

		<aui:button name="cancelButton" onClick='<%= renderResponse.getNamespace() + "closeDialog();" %>' value="cancel" />
	</aui:button-row>
</aui:form>

<liferay-util:buffer
	var="removeUserEmailAddressIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:script>
	function <portlet:namespace />closeDialog() {
		Liferay.Util.getOpener().<portlet:namespace />closePopup('inviteUserDialog');
	}

	Liferay.provide(
		window,
		'<portlet:namespace />addMember',
		function() {
			var A = AUI();

			var emailAddress = A.one('#<portlet:namespace />emailAddress');

			if (emailAddress) {
				var emailAddressVal = emailAddress.val();

				if (emailAddressVal) {
					emailAddressVal = A.Lang.String.escapeHTML(emailAddressVal);

					var content =
						'<span class="label label-dismissible label-secondary label-user-mail-address">' +
						emailAddressVal +
						'<a class="modify-link" data-emailAddress="' +
							emailAddressVal +
							'" href="javascript:;">' +
								'<%= UnicodeFormatter.toString(removeUserEmailAddressIcon) %>' +
						'</a>' +
					'</span>';

					var userInvitationContent = A.one('#<portlet:namespace />userInvitationContent');

					if (userInvitationContent) {
						userInvitationContent.append(content);
					}

					emailAddress.val('');
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />submitFm',
		function() {
			var A = AUI();

			var loadingMask = new A.LoadingMask(
				{
					'strings.loading': '<%= UnicodeLanguageUtil.get(request, "users-are-being-invited") %>',
					target: A.getBody()
				}
			);

			loadingMask.show();

			var arrayEmailAddresses = [];

			var modifyLinks = A.all('#<portlet:namespace />userInvitationContent .modify-link');

			modifyLinks.each(
				function(item) {
					var emailAddress = item.attr('data-emailAddress');

					arrayEmailAddresses.push(emailAddress);
				}
			);

			var emailAddresses = A.one('#<portlet:namespace />emailAddresses');

			if (emailAddresses) {
				emailAddresses.val(arrayEmailAddresses.join(','));
			}

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
							loadingMask.hide();

							Liferay.Util.getOpener().<portlet:namespace />closePopup('inviteUserDialog');
							Liferay.Util.getOpener().<portlet:namespace />refreshPortlet();
						}
					}
				}
			);
		},
		['aui-io-request', 'aui-loading-mask-deprecated']
	);
</aui:script>

<aui:script use="aui-base">
	var userInvitationContent = A.one('#<portlet:namespace />userInvitationContent');

	if (userInvitationContent) {
		userInvitationContent.delegate(
			'click',
			function(event) {
				var currentTarget = event.currentTarget;

				var node = currentTarget.ancestor('span');

				if (node) {
					node.remove();
				}
			},
			'.modify-link'
		);
	}
</aui:script>