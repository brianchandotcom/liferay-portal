<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = currentURL;
}

CookiesPreferenceHandlingConfigurationDisplayContext cookiesPreferenceHandlingConfigurationDisplayContext = (CookiesPreferenceHandlingConfigurationDisplayContext)request.getAttribute(CookiesBannerWebKeys.COOKIES_PREFERENCE_HANDLING_CONFIGURATION_DISPLAY_CONTEXT);
%>

<clay:sheet
	cssClass="cookies-preference-handling-configuration"
	size="full"
>
	<h2>
		<liferay-ui:message key="cookie-preference-handling-configuration-name" />

		<c:if test="<%= cookiesPreferenceHandlingConfigurationDisplayContext.isCookiesPreferenceHandlingConfigurationDefined() %>">

			<%
			CookiesPreferenceHandlingConfigurationActionDropdownItemsProvider cookiesPreferenceHandlingConfigurationActionDropdownItemsProvider = new CookiesPreferenceHandlingConfigurationActionDropdownItemsProvider(cookiesPreferenceHandlingConfigurationDisplayContext, request);
			%>

			<div class="float-right">
				<clay:dropdown-actions
					aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
					dropdownItems="<%= cookiesPreferenceHandlingConfigurationActionDropdownItemsProvider.getActionDropdownItems() %>"
				/>
			</div>
		</c:if>
	</h2>

	<aui:form action="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getEditCookiesPreferenceHandlingConfigurationURL() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<c:if test="<%= !cookiesPreferenceHandlingConfigurationDisplayContext.isCookiesPreferenceHandlingConfigurationDefined() %>">
			<clay:alert
				cssClass="c-mb-4"
				displayType="info"
				id="errorAlert"
				message="this-configuration-is-not-saved-yet.-the-values-shown-are-the-default"
			/>
		</c:if>

		<div class="c-mt-5 row">
			<div class="col-sm-12 form-group">
				<div class="form-group__inner">
					<c:choose>
						<c:when test="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>">
							<input disabled name='<%= liferayPortletResponse.getNamespace() + "enabled" %>' type="hidden" value='false' />
						</c:when>
						<c:otherwise>
							<input name="<portlet:namespace />enabled" type="hidden" value="false" />
						</c:otherwise>
					</c:choose>

					<clay:checkbox
						checked="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>"
						id='<%= liferayPortletResponse.getNamespace() + "enabled" %>'
						label="enabled"
						name='<%= liferayPortletResponse.getNamespace() + "enabled" %>'
					/>

					<div aria-hidden="true" class="form-feedback-group">
						<div class="form-text text-weight-normal"><liferay-ui:message key="cookie-enabled-help" /></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12 form-group mb-0">
				<div class="form-group__inner">
					<c:choose>
						<c:when test="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingExplicitConsentMode() %>">
							<input disabled name='<%= liferayPortletResponse.getNamespace() + "explicitConsentMode" %>' type="hidden" value='false' />
						</c:when>
						<c:otherwise>
							<input name="<portlet:namespace />explicitConsentMode" type="hidden" value="false" />
						</c:otherwise>
					</c:choose>

					<clay:checkbox
						checked="<%= !cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() || cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingExplicitConsentMode() %>"
						disabled="<%= !cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>"
						id='<%= liferayPortletResponse.getNamespace() + "explicitConsentMode" %>'
						label="cookie-explicit-consent-mode"
						name='<%= liferayPortletResponse.getNamespace() + "explicitConsentMode" %>'
					/>

					<div aria-hidden="true" class="form-feedback-group">
						<div class="form-text text-weight-normal">
							<liferay-ui:message key="cookie-explicit-consent-mode-help" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="sheet-footer">
			<div class="btn-group-item">
				<c:choose>
					<c:when test="<%= cookiesPreferenceHandlingConfigurationDisplayContext.isCookiesPreferenceHandlingConfigurationDefined() %>">
						<clay:button
							cssClass="submit-btn"
							data-qa-id="submitConfiguration"
							displayType="primary"
							id='<%= liferayPortletResponse.getNamespace() + "update" %>'
							label='<%= LanguageUtil.get(request, "update") %>'
							name='<%= liferayPortletResponse.getNamespace() + "update" %>'
							type="submit"
						/>
					</c:when>
					<c:otherwise>
						<clay:button
							cssClass="submit-btn"
							data-qa-id="submitConfiguration"
							displayType="primary"
							id='<%= liferayPortletResponse.getNamespace() + "save" %>'
							label='<%= LanguageUtil.get(request, "save") %>'
							name='<%= liferayPortletResponse.getNamespace() + "save" %>'
							type="submit"
						/>
					</c:otherwise>
				</c:choose>

				<clay:link
					displayType="secondary"
					href="<%= redirect %>"
					id='<%= liferayPortletResponse.getNamespace() + "cancel" %>'
					label="cancel"
					type="button"
				/>
			</div>
		</div>
	</aui:form>
</clay:sheet>

<liferay-frontend:component
	module="{ConfigurationFormEventHandler} from cookies-banner-web"
/>