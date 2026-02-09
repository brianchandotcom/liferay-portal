<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CookiesPreferenceHandlingConfigurationDisplayContext cookiesPreferenceHandlingConfigurationDisplayContext = (CookiesPreferenceHandlingConfigurationDisplayContext)request.getAttribute(CookiesBannerWebKeys.COOKIES_PREFERENCE_HANDLING_CONFIGURATION_DISPLAY_CONTEXT);
%>

<div class="c-mt-5 row">
	<div class="col-sm-12 form-group">
		<div class="form-group__inner">
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
	<div class="col-sm-12 form-group">
		<div class="form-group__inner">
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

<div class="row">
	<div class="col-sm-12 form-group">
		<div class="form-group__inner">
			<aui:input disabled="<%= !cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>" id='<%= liferayPortletResponse.getNamespace() + "consentRenewalPeriod" %>' label="cookie-consent-renewal-period" max="12" min="1" name='<%= liferayPortletResponse.getNamespace() + "consentRenewalPeriod" %>' required="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>" type="number" useNamespace="<%= false %>" value="<%= (cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingConsentRenewalPeriod() == 0) ? 12 : cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingConsentRenewalPeriod() %>" />

			<div aria-hidden="true" class="form-feedback-group">
				<div class="form-text text-weight-normal">
					<liferay-ui:message key="cookie-consent-renewal-period-help" />
				</div>
			</div>
		</div>
	</div>
</div>

<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPD-75032") %>'>
	<div class="row">
		<div class="col-sm-12 form-group">
			<div class="form-group__inner">
				<clay:checkbox
					checked="<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingStoreConsent() %>"
					disabled="<%= !cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "storeConsent" %>'
					label="cookie-store-consent"
					name='<%= liferayPortletResponse.getNamespace() + "storeConsent" %>'
				/>

				<div aria-hidden="true" class="form-feedback-group">
					<div class="form-text text-weight-normal">
						<liferay-ui:message key="cookie-store-consent-help" />
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>

<liferay-frontend:component
	module="{ConfigurationFormEventHandler} from cookies-banner-web"
/>

<aui:script>
	var form = document.<portlet:namespace />fm;

	if (form) {
		form.addEventListener('submit', (event) => {
			var consentRenewalPeriod = document.getElementById(
				'<portlet:namespace />consentRenewalPeriod'
			);

			if (!consentRenewalPeriod.value || isNaN(consentRenewalPeriod.value)) {
				event.preventDefault();
				event.stopImmediatePropagation();
				return;
			}

			var enabled = document.getElementById('<portlet:namespace />enabled');

			if (
				enabled.checked &&
				<%= cookiesPreferenceHandlingConfigurationDisplayContext.getCookiesPreferenceHandlingEnabled() %>
			) {
				event.preventDefault();
				event.stopImmediatePropagation();

				Liferay.Util.openConfirmModal({
					message:
						'<liferay-ui:message key="you-are-about-to-change-the-consent-renewal-period" />',
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							form.submit();
						}
					},
				});
			}
		});
	}
</aui:script>