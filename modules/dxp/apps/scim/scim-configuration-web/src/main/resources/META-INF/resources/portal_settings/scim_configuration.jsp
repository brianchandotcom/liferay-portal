<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String paramApplicationName = (String)request.getAttribute("applicationName");
String paramMatcherField = (String)request.getAttribute("matcherField");

String paramToken = (String)request.getAttribute("token");

if (paramToken == null) {
	paramToken = StringPool.BLANK;
}
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />

<aui:input label="application-name" name="applicationName" required="<%= true %>" type="text" value="<%= paramApplicationName %>" />

<aui:select helpMessage="scim_matcherField-help" label="scim_matcherField" name="matcherField" required="<%= true %>" value="<%= paramMatcherField %>">
	<aui:option label="" value="" />
	<aui:option label="userName" localizeLabel="<%= false %>" value="userName" />
	<aui:option label="email" localizeLabel="<%= false %>" value="email" />
</aui:select>

<c:choose>
	<c:when test="<%= paramApplicationName != null %>">

		<%
		String paramTokenInputId = liferayPortletResponse.getNamespace() + "paramToken";
		%>

		<div class="form-group">
			<label for="<%= paramTokenInputId %>">
				<liferay-ui:message key="access-token" />
			</label>

			<div class="input-group input-group-sm">
				<div class="input-group-item input-group-prepend">
					<input class="form-control" id="<%= paramTokenInputId %>" readonly value="<%= paramToken %>" />
				</div>

				<span class="input-group-append input-group-item input-group-item-shrink">
					<clay:button
						name="copyAccessToken"
						id="copyAccessToken"
						cssClass="scim-infopanel-copy-clipboard lfr-portal-tooltip"
						data-clipboard-target='<%= "#" + paramTokenInputId %>'
						displayType="secondary"
						icon="paste"
						title="copy-link"
					/>
				</span>
			</div>
		</div>

		<label for="<portlet:namespace />genetareAccessToken">
			<liferay-ui:message key="scim-generate-access-token" />

			<liferay-ui:icon-help message='<%= LanguageUtil.get(request, "scim-generate-access-token-help") %>' />
		</label>

		<div class="input-group input-group-sm">
			<aui:button id="genetareAccessToken" label="discard-changes" name="genetareAccessToken" small="<%= true %>" value="generate" />
		</div>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(paramToken) %>">
				<label for="<portlet:namespace />revokeAccessToken">
					<liferay-ui:message key="scim-revoke-all" />

					<liferay-ui:icon-help message='<%= LanguageUtil.get(request, "scim-revoke-all-help") %>' />
				</label>

				<div class="input-group input-group-sm">
					<aui:button id="revokeAccessToken" label="discard-changes" name="revokeAccessToken" small="<%= true %>" value="revoke" />
				</div>
			</c:when>
		</c:choose>
	</c:when>
</c:choose>

<liferay-frontend:component
	module="portal_settings/js/InfoPanel.es"
/>

<script>
	var genetareAccessToken = document.getElementById(
		'<portlet:namespace />genetareAccessToken'
	);

	if (genetareAccessToken) {
		genetareAccessToken.addEventListener('click', (event) => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-generate-a-new-access-token" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						var form = window.document['<portlet:namespace />fm'];
						form['<portlet:namespace /><%= Constants.CMD %>'].value =
							'generate';

						form.submit();
					}
				},
			});
		});
	}

	var revokeAccessToken = document.getElementById(
		'<portlet:namespace />revokeAccessToken'
	);

	if (revokeAccessToken) {
		revokeAccessToken.addEventListener('click', (event) => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-revoke-all-access-tokens" />',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						var form = window.document['<portlet:namespace />fm'];
						form['<portlet:namespace /><%= Constants.CMD %>'].value =
							'revoke';

						form.submit();
					}
				},
			});
		});
	}

	var copyccessToken = document.getElementById(
		'<portlet:namespace />copyccessToken'
	);

	if (copyccessToken) {
		copyccessToken.addEventListener('click', (event) => {
			this._clipboard = new ClipboardJS('.scim-infopanel-copy-clipboard');

			this._clipboard.on('success', this._handleClipboardSuccess.bind(this));
		});
	}
</script>