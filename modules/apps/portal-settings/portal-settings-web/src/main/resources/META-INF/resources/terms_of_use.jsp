<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean termsOfUseRequired = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.TERMS_OF_USE_REQUIRED, PropsValues.TERMS_OF_USE_REQUIRED);

TermsOfUseContentProvider termsOfUseContentProvider = (TermsOfUseContentProvider)request.getAttribute(PortalSettingsWebKeys.TERMS_OF_USE_CONTENT_PROVIDER);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input label="require-terms-of-use" name='<%= "settings--" + PropsKeys.TERMS_OF_USE_REQUIRED + "--" %>' type="checkbox" value="<%= termsOfUseRequired %>" />

	<c:if test="<%= termsOfUseContentProvider != null %>">

		<%
		termsOfUseContentProvider.includeConfig(request, response);
		%>

	</c:if>
</aui:fieldset>

<aui:fieldset>
	<aui:field-wrapper label="reset-consent-for-all-users">
		<aui:fieldset>
			<portlet:actionURL name="/portal_settings/reset_terms_of_use_consent" var="resetTermsOfUseConsentURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:script>
				function <portlet:namespace />handleResetConsent(event) {
					event.preventDefault();

					Liferay.Util.openConfirmModal({
						message:
							'<%= UnicodeLanguageUtil.get(request, "this-will-require-all-users-to-re-accept-the-terms-of-use-are-you-sure-you-want-to-proceed") %>',
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								submitForm(
									document.hrefFm,
									'<%= resetTermsOfUseConsentURL.toString() %>'
								);
							}
						},
					});
				}
			</aui:script>

			<aui:button onClick='<%= liferayPortletResponse.getNamespace() + "handleResetConsent(event)" %>' value="reset" />
		</aui:fieldset>
	</aui:field-wrapper>
</aui:fieldset>