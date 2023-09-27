<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "verifyPassword");
%>

<aui:form cssClass="modal-body" name="fm">
	<div class="sheet-text"><liferay-ui:message key="changes-to-the-email-address-or-screen-name-require-password-verification" /></div>

	<div class="hide" id="<portlet:namespace />verificationAlert">
		<clay:alert
			displayType="danger"
			message="incorrect-password-please-try-again"
		/>
	</div>

	<aui:field-wrapper cssClass="form-group">

		<!-- Begin LPS-38289, LPS-55993, and LPS-61876 -->

		<input class="hide" type="password" />

		<!-- End LPS-38289, LPS-55993, and LPS-61876 -->

		<aui:input name="userId" type="hidden" value="<%= themeDisplay.getUserId() %>" />
		<aui:input label="your-password" name="password" required="<%= true %>" size="30" type="password" />

		<div class="form-text">
			<liferay-ui:message key="enter-your-current-password-to-confirm-changes" />
		</div>
	</aui:field-wrapper>

	<aui:button-row cssClass="position-fixed">
		<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "onClick();" %>' primary="<%= true %>" value="confirm" />

		<aui:button type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />onClick() {
		var form = Liferay.Form.get('<portlet:namespace />fm');
		var formValidator = form.formValidator;

		formValidator.validate();

		if (!formValidator.hasErrors()) {
			<portlet:namespace />verifyPassword();
		}
	}

	function <portlet:namespace />verifyPassword() {
		var form = document.<portlet:namespace />fm;

		Liferay.Util.fetch(
			'<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/users_admin/authenticate_user" />',
			{
				body: new FormData(form),
				method: 'POST',
			}
		)
			.then((response) => {
				if (!response.ok) {
					throw new Error();
				}

				return response.text();
			})
			.then((response) => {
				var openingLiferay = Liferay.Util.getOpener().Liferay;

				openingLiferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', {
					<c:if test='<%= eventName.equals(liferayPortletResponse.getNamespace() + "verifyPassword") %>'>
						data: document.getElementById('<portlet:namespace />password')
							.value,
					</c:if>
				});
				openingLiferay.fire('closeModal');
			})
			.catch((error) => {
				var verificationAlert = document.getElementById(
					'<portlet:namespace />verificationAlert'
				);

				verificationAlert.classList.remove('hide');

				document.getElementById('<portlet:namespace />password').focus();
			});
	}
</aui:script>