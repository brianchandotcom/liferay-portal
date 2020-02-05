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
	<div class="login-container">
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

				<aui:button onClick='<%= renderResponse.getNamespace() + "closeDialog(window.parent.namespace);" %>' value="cancel" />
			</aui:form>
		</div>
	</div>

	<aui:script sandbox="<%= true %>">
		var showStatusMessage = Liferay.lazyLoad('metal-dom/src/dom', function(
			dom,
			type,
			message
		) {
			var messageContainer = document.getElementById(
				'<portlet:namespace />login-status-messages'
			);

			if (messageContainer) {
				dom.removeClasses(messageContainer, 'alert-danger');
				dom.removeClasses(messageContainer, 'alert-success');

				dom.addClasses(messageContainer, 'alert alert-' + type);

				messageContainer.innerHTML = message;

				dom.removeClasses(messageContainer, 'hide');
			}
		});

		window.<portlet:namespace />activateAccount = Liferay.lazyLoad(
			'metal-dom/src/dom',
			function(dom) {
				var form = document.getElementById('<portlet:namespace />fm');

				function onError() {
					var message =
						'<liferay-ui:message key="your-request-failed-to-complete" />';

					showStatusMessage('danger', message);

					var anonymousAccount = form.querySelector('.anonymous-account');

					if (anonymousAccount) {
						dom.addClasses(anonymousAccount, 'hide');

						dom.removeClasses(anonymousAccount, 'show');
					}
				}

				Liferay.Util.fetch('<%= updateIncompleteUserURL %>', {
					body: new FormData(form),
					headers: new Headers({
						'Content-Type': 'application/json'
					}),
					method: 'POST'
				})
					.then(function(response) {
						return response.ok ? response.json() : Promise.reject();
					})
					.then(function(data) {
						return !data.exception ? data.userStatus : Promise.reject();
					})
					.then(function(userStatus) {
						var message = '';

						if (userStatus == 'user_added') {
							message =
								'<liferay-ui:message key="thank-you-for-creating-an-account" /> <liferay-ui:message arguments="<%= emailAddress %>" key="you-can-set-your-password-following-instructions-sent-to-x" translateArguments="<%= false %>" />';
						}
						else if (userStatus == 'user_pending') {
							message =
								'<liferay-ui:message arguments="<%= emailAddress %>" key="thank-you-for-creating-an-account.-you-will-be-notified-via-email-at-x-when-your-account-has-been-approved" translateArguments="<%= false %>" />';
						}

						showStatusMessage('success', message);

						var anonymousAccount = document.querySelector(
							'.anonymous-account'
						);

						if (anonymousAccount) {
							dom.addClasses(anonymousAccount, 'hide');

							dom.removeClasses(anonymousAccount, 'show');
						}
					})
					.catch(onError);
			}
		);
	</aui:script>
</c:if>

<aui:script sandbox="<%= true %>">
	window.<portlet:namespace />closeDialog = function(namespace) {
		Liferay.fire('closeWindow', {
			id: namespace + 'signInDialog'
		});
	};

	var parentWindow = window.opener ? window.opener.parent : window.parent;

	var namespace = parentWindow.namespace;
	var randomNamespace = parentWindow.randomNamespace;

	var afterLogin = parentWindow[randomNamespace + 'afterLogin'];

	if (typeof afterLogin === 'function') {
		parentWindow.document.getElementsByName('p_auth')[0].value =
			'<%= AuthTokenUtil.getToken(request) %>';

		afterLogin(
			'<%= HtmlUtil.escapeJS(emailAddress) %>',
			<%= anonymousAccount %>
		);

		if (<%= !anonymousAccount || !company.isStrangers() %>) {
			window.<portlet:namespace />closeDialog(namespace);

			window.close();
		}
	}
	else {
		window.opener.parent.location.href =
			'<%= HtmlUtil.escapeJS(themeDisplay.getURLSignIn()) %>';

		window.close();
	}
</aui:script>