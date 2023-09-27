<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

String redirect = ParamUtil.getString(request, "redirect", backURL);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "import-translations"));
%>

<portlet:actionURL name="/portal_language_override/import_translations" var="importTranslationsURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<clay:container-fluid
	cssClass="container-view"
>
	<aui:form action="<%= importTranslationsURL %>" cssClass="sheet sheet-lg" enctype="multipart/form-data" method="post" name="fm">
		<liferay-ui:error exception="<%= PLOEntryKeyException.MustBeShorter.class %>">

			<%
			PLOEntryKeyException.MustBeShorter ploEntryKeyException = (PLOEntryKeyException.MustBeShorter)errorException;
			%>

			<liferay-ui:message arguments="<%= String.valueOf(ploEntryKeyException.maxLength) %>" key="keys-longer-than-x-characters-are-not-allowed" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= PLOEntryKeyException.MustNotBeNull.class %>" message="empty-keys-are-not-allowed" />
		<liferay-ui:error exception="<%= PLOEntryValueException.MustNotBeNull.class %>" message="empty-values-are-not-allowed" />

		<liferay-ui:error key="fileEmpty" message="file-does-not-contain-any-translations" />
		<liferay-ui:error key="fileExtensionInvalid" message='<%= LanguageUtil.format(request, "please-upload-a-file-with-a-valid-extension-x", "properties", false) %>' />
		<liferay-ui:error key="fileInvalid" message="please-select-a-valid-file" />

		<h5><liferay-ui:message key="import-file" /></h5>

		<div class="sheet-text">
			<liferay-ui:message arguments=".properties" key="support-file-format" />
		</div>

		<aui:select label="language" name="languageId">

			<%
			Set<Locale> locales = LanguageUtil.getCompanyAvailableLocales(themeDisplay.getCompanyId());

			for (Locale curLocale : locales) {
				String languageId = LanguageUtil.getLanguageId(curLocale);
			%>

				<aui:option label="<%= TextFormatter.format(languageId, TextFormatter.O) %>" value="<%= languageId %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input id="file" label="file-upload" name="file" required="<%= true %>" type="file">
			<aui:validator name="acceptFiles">
				'properties'
			</aui:validator>
		</aui:input>

		<aui:button-row>
			<aui:button name="submit" type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</clay:container-fluid>