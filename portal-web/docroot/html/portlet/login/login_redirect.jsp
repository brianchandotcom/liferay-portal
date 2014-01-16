<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
String emailAddress = ParamUtil.getString(request, "emailAddress");

boolean anonymousAccount = ParamUtil.getBoolean(request, "anonymousUser");
%>

<c:if test="<%= anonymousAccount && company.isStrangers() %>">
	<div class="hide lfr-message-response" id="<portlet:namespace />login-status-messages"></div>

	<div class="anonymous-account">
		<portlet:actionURL var="updateIncompleteUserURL">
			<portlet:param name="struts_action" value="/login/create_anonymous_account" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
			<portlet:param name="emailAddress" value="<%= emailAddress %>" />
		</portlet:actionURL>

		<aui:form action="<%= updateIncompleteUserURL %>" method="post" name="fm">
			<div class="alert alert-success">
				<liferay-ui:message key="your-comment-has-already-been-posted.-would-you-like-to-create-an-account-with-the-provided-information" />
			</div>

			<aui:button onClick='<%= renderResponse.getNamespace() + "activateAccount();" %>' value="activate-account" />

			<aui:button onClick='<%= renderResponse.getNamespace() + "closeDialog();" %>' value="cancel" />
		</aui:form>
	</div>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />activateAccount',
		function() {
			var A = AUI();

			var form = A.one(document.<portlet:namespace />fm);

			var uri = form.getAttribute('action');

			A.io.request(
				uri,
				{
					dataType: 'json',
					form: {
						id: form
					},
					on: {
						failure: function(event, id, obj) {
							message = '<%= UnicodeLanguageUtil.get(pageContext, "your-request-failed-to-complete") %>';

							<portlet:namespace />showStatusMessage('error', message);

							A.one('.anonymous-account').hide();
						},
						success: function(event, id, obj) {
							var response = this.get('responseData');

							var exception = response.exception;

							var message;

							if (!exception) {
								var userStatus = response.userStatus;

								if (userStatus == 'user_added') {
									message = '<%= UnicodeLanguageUtil.format(pageContext, "thank-you-for-creating-an-account-your-password-has-been-sent-to-x", emailAddress, false) %>';
								}
								else if (userStatus == 'user_pending') {
									message = '<%= UnicodeLanguageUtil.format(pageContext, "thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved", emailAddress, false) %>';
								}

								<portlet:namespace />showStatusMessage('success', message);

								A.one('.anonymous-account').hide();
							}
							else {
								message = '<%= UnicodeLanguageUtil.get(pageContext, "your-request-failed-to-complete") %>';

								<portlet:namespace />showStatusMessage('error', message);

								A.one('.anonymous-account').hide();
							}
						}
					}
				}
			);
		},
		['aui-io']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />closeDialog',
		function(type, message) {
			var namespace = window.parent.namespace;

			Liferay.fire(
				'closeWindow',
				{
					id: namespace + "signInDialog"
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />showStatusMessage',
		function(type, message) {
			var A = AUI();

			var messageContainer = A.one('#<portlet:namespace />login-status-messages');

			messageContainer.removeClass('alert-error').removeClass('alert-success');

			messageContainer.addClass('alert alert-' + type);

			messageContainer.html(message);

			messageContainer.show();
		},
		['aui-base']
	);

	<c:if test="<%= !company.isStrangers() %>">
		<portlet:namespace />closeDialog();
	</c:if>
</aui:script>

<aui:script use="aui-base">
	if (window.opener) {
		var namespace = window.opener.parent.namespace;
		var randomNamespace = window.opener.parent.randomNamespace;

		var afterLogin = window.opener.parent[randomNamespace + 'afterLogin'];

		if (typeof(afterLogin) == 'function') {
			afterLogin('<%= HtmlUtil.escape(emailAddress) %>', <%= anonymousAccount %>);

			if (<%= !anonymousAccount %>) {
				window.opener.parent.Liferay.fire(
					'closeWindow',
					{
						id: namespace + "signInDialog"
					}
				);

				window.close();
			}
		}
		else {
			window.opener.parent.location.href = '<%= HtmlUtil.escapeJS(themeDisplay.getURLSignIn()) %>';

			window.close();
		}
	}
	else {
		var namespace = window.parent.namespace;
		var randomNamespace = window.parent.randomNamespace;

		var afterLogin = window.parent[randomNamespace + 'afterLogin'];

		afterLogin('<%= HtmlUtil.escape(emailAddress) %>', <%= anonymousAccount %>);

		if (<%= !anonymousAccount %>) {
			Liferay.fire(
				'closeWindow',
				{
					id: namespace + "signInDialog"
				}
			);
		}
	}
</aui:script>