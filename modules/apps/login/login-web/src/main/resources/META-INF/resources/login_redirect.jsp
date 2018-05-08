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
String emailAddress = ParamUtil.getString(request, "emailAddress");

boolean anonymousAccount = ParamUtil.getBoolean(request, "anonymousUser");
%>

<c:if test="<%= anonymousAccount && company.isStrangers() %>">
	<div class="hide lfr-message-response" id="<portlet:namespace />login-status-messages"></div>

	<div class="anonymous-account">
		<portlet:actionURL name="/login/create_anonymous_account" var="updateIncompleteUserURL">
			<portlet:param name="mvcRenderCommandName" value="/login/create_anonymous_account" />
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
	function <portlet:namespace />activateAccount() {
		var $ = AUI.$;

		var form = $(document.<portlet:namespace />fm);

		function onError() {
			var message = '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>';

			<portlet:namespace />showStatusMessage('danger', message);

			$('.anonymous-account').addClass('hide');
		}

		form.ajaxSubmit(
			{
				dataType: 'json',
				error: onError,
				success: function(responseData) {
					var exception = responseData.exception;

					var message;

					if (!exception) {
						var userStatus = responseData.userStatus;

						if (userStatus == 'user_added') {
							message = '<%= UnicodeLanguageUtil.format(request, "thank-you-for-creating-an-account-your-password-was-sent-to-x", HtmlUtil.escape(emailAddress), false) %>';
						}
						else if (userStatus == 'user_pending') {
							message = '<%= UnicodeLanguageUtil.format(request, "thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved", HtmlUtil.escape(emailAddress), false) %>';
						}

						<portlet:namespace />showStatusMessage('success', message);

						$('.anonymous-account').addClass('hide');
					}
					else {
						onError();
					}
				}
			}
		);
	}

	function <portlet:namespace />closeDialog() {
		var namespace = window.parent.namespace;

		Liferay.fire(
			'closeWindow',
			{
				id: namespace + 'signInDialog'
			}
		);
	}

	function <portlet:namespace />showStatusMessage(type, message) {
		var messageContainer = AUI.$('#<portlet:namespace />login-status-messages');

		messageContainer.removeClass('alert-danger').removeClass('alert-success');

		messageContainer.addClass('alert alert-' + type);

		messageContainer.html(message).removeClass('hide');
	}

	<c:if test="<%= !company.isStrangers() && (user == null) %>">
		<portlet:namespace />closeDialog();
	</c:if>
</aui:script>

<aui:script sandbox="<%= true %>">
	var afterLogin;
	var namespace;
	var randomNamespace;

	if (window.opener) {
		namespace = window.opener.parent.namespace;
		randomNamespace = window.opener.parent.randomNamespace;

		afterLogin = window.opener.parent[randomNamespace + 'afterLogin'];

		if (typeof afterLogin == 'function') {
			afterLogin('<%= HtmlUtil.escape(emailAddress) %>', <%= anonymousAccount %>);

			if (<%= !anonymousAccount %>) {
				window.opener.parent.Liferay.fire(
					'closeWindow',
					{
						id: namespace + 'signInDialog'
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
		namespace = window.parent.namespace;
		randomNamespace = window.parent.randomNamespace;

		afterLogin = window.parent[randomNamespace + 'afterLogin'];

		afterLogin('<%= HtmlUtil.escape(emailAddress) %>', <%= anonymousAccount %>);

		if (<%= !anonymousAccount %>) {
			Liferay.fire(
				'closeWindow',
				{
					id: namespace + 'signInDialog'
				}
			);
		}
	}
</aui:script>