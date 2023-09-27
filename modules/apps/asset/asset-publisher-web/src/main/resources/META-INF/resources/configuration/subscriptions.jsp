<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", assetPublisherWebHelper.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", assetPublisherWebHelper.getEmailFromAddress(portletPreferences, company.getCompanyId()));

boolean emailAssetEntryAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailAssetEntryAddedEnabled--", assetPublisherWebHelper.getEmailAssetEntryAddedEnabled(portletPreferences));
%>

<liferay-ui:error key="emailAssetEntryAddedBody" message="please-enter-a-valid-body" />
<liferay-ui:error key="emailAssetEntryAddedSubject" message="please-enter-a-valid-subject" />
<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

<aui:input id="enableEmailSubscription" inlineLabel="right" label="enable-email-subscription" labelCssClass="simple-toggle-switch" name="preferences--emailAssetEntryAddedEnabled--" type="toggle-switch" value="<%= emailAssetEntryAddedEnabled %>" />

<div class="<%= emailAssetEntryAddedEnabled ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />emailSubscriptionSettings">
	<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

	<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />

	<liferay-frontend:email-notification-settings
		emailBodyLocalizedValuesMap="<%= assetPublisherDisplayContext.getEmailAssetEntryAddedBody() %>"
		emailDefinitionTerms="<%= assetPublisherWebHelper.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName) %>"
		emailEnabled="<%= emailAssetEntryAddedEnabled %>"
		emailParam="emailAssetEntryAdded"
		emailSubjectLocalizedValuesMap="<%= assetPublisherDisplayContext.getEmailAssetEntryAddedSubject() %>"
		showEmailEnabled="<%= false %>"
	/>
</div>

<aui:script sandbox="<%= true %>">
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />enableEmailSubscription',
		'<portlet:namespace />emailSubscriptionSettings'
	);
</aui:script>