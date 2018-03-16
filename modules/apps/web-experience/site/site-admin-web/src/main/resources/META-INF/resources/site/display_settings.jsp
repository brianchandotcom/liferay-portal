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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="display-settings" />

<h4 class="text-default"><liferay-ui:message key="language" /></h4>

<%
UnicodeProperties typeSettingsProperties = null;

if (liveGroup != null) {
	typeSettingsProperties = liveGroup.getTypeSettingsProperties();
}
else {
	typeSettingsProperties = new UnicodeProperties();
}

boolean inheritLocales = GetterUtil.getBoolean(typeSettingsProperties.getProperty(GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES), true);

LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();
LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

boolean disabledLocaleInput = false;

if (publicLayoutSet.isLayoutSetPrototypeLinkEnabled() || privateLayoutSet.isLayoutSetPrototypeLinkEnabled()) {
	disabledLocaleInput = true;
}
%>

<c:if test="<%= disabledLocaleInput %>">
	<p class="text-muted">
		<liferay-ui:message key="the-language-settings-cannot-be-edited-while-propagation-of-changes-from-the-site-template-is-enabled" />
	</p>
</c:if>

<aui:input checked="<%= inheritLocales %>" disabled="<%= disabledLocaleInput %>" id="<%= GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES %>" label="use-the-default-language-options" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= true %>" />

<aui:input checked="<%= !inheritLocales %>" disabled="<%= disabledLocaleInput %>" id="customLocales" label="define-a-custom-default-language-and-additional-available-languages-for-this-site" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= false %>" />

<aui:fieldset id='<%= renderResponse.getNamespace() + "customLocalesFieldset" %>'>
	<aui:fieldset cssClass="default-language">
		<h4 class="text-muted"><liferay-ui:message key="default-language" /></h4>

		<%
		User user2 = company.getDefaultUser();

		Locale defaultLocale = user2.getLocale();
		%>

		<p class="text-muted">
			<%= defaultLocale.getDisplayName(locale) %>
		</p>
	</aui:fieldset>

	<aui:fieldset cssClass="available-languages">
		<h4 class="text-muted"><liferay-ui:message key="available-languages" /></h4>

		<p class="text-muted">

			<%
			for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			%>

				<%= availableLocale.getDisplayName(locale) %>,

			<%
			}
			%>

		</p>
	</aui:fieldset>
</aui:fieldset>

<aui:fieldset id='<%= renderResponse.getNamespace() + "inheritLocalesFieldset" %>'>
	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<c:choose>
			<c:when test="<%= le.getType() == LocaleException.TYPE_DEFAULT %>">
				<liferay-ui:message arguments="<%= StringUtil.merge(LocaleUtil.toDisplayNames(le.getTargetAvailableLocales(), locale), StringPool.COMMA_AND_SPACE) %>" key="please-select-a-default-language-among-the-available-languages-of-the-site-x" translateArguments="<%= false %>" />
			</c:when>
			<c:when test="<%= le.getType() == LocaleException.TYPE_DISPLAY_SETTINGS %>">
				<liferay-ui:message arguments="<%= StringUtil.merge(LocaleUtil.toDisplayNames(le.getSourceAvailableLocales(), locale), StringPool.COMMA_AND_SPACE) %>" key="please-select-the-available-languages-of-the-site-among-the-available-languages-of-the-portal-x" translateArguments="<%= false %>" />
			</c:when>
		</c:choose>
	</liferay-ui:error>

	<%
	Set<Locale> siteAvailableLocales = LanguageUtil.getAvailableLocales(liveGroup.getGroupId());
	%>

	<aui:fieldset cssClass="default-language">
		<h4 class="text-default"><liferay-ui:message key="default-language" /></h4>

		<aui:select disabled="<%= disabledLocaleInput %>" label="" name="TypeSettingsProperties--languageId--" title="language">

			<%
			Locale siteDefaultLocale = PortalUtil.getSiteDefaultLocale(liveGroup.getGroupId());

			for (Locale siteAvailableLocale : siteAvailableLocales) {
			%>

				<aui:option label="<%= siteAvailableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(siteAvailableLocale) %>" selected="<%= (siteDefaultLocale.getLanguage().equals(siteAvailableLocale.getLanguage()) && siteDefaultLocale.getCountry().equals(siteAvailableLocale.getCountry())) %>" value="<%= LocaleUtil.toLanguageId(siteAvailableLocale) %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>

	<div id="<portlet:namespace />languageWarning"></div>

	<aui:fieldset cssClass="available-languages">
		<h4 class="text-default"><liferay-ui:message key="available-languages" /></h4>

		<aui:input name='<%= "TypeSettingsProperties--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= StringUtil.merge(LocaleUtil.toLanguageIds(siteAvailableLocales)) %>" />

		<%

		// Left list

		List leftList = new ArrayList();

		for (Locale siteAvailableLocale : siteAvailableLocales) {
			leftList.add(new KeyValuePair(LocaleUtil.toLanguageId(siteAvailableLocale), siteAvailableLocale.getDisplayName(locale)));
		}

		leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

		// Right list

		List rightList = new ArrayList();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			if (!siteAvailableLocales.contains(availableLocale)) {
				rightList.add(new KeyValuePair(LocaleUtil.toLanguageId(availableLocale), availableLocale.getDisplayName(locale)));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<liferay-ui:input-move-boxes
			leftBoxName="currentLanguageIds"
			leftList="<%= leftList %>"
			leftTitle="current"
			rightBoxName="availableLanguageIds"
			rightList="<%= rightList %>"
			rightTitle="available"
		/>
	</aui:fieldset>
</aui:fieldset>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />customLocales', '<portlet:namespace />inheritLocalesFieldset', '<portlet:namespace />customLocalesFieldset');
	Liferay.Util.toggleRadio('<portlet:namespace />inheritLocales', '<portlet:namespace />customLocalesFieldset', '<portlet:namespace />inheritLocalesFieldset');

	function <portlet:namespace />saveLocales() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= PropsKeys.LOCALES %>').val(Liferay.Util.listSelect(form.fm('currentLanguageIds')));
	}
</aui:script>

<aui:script use="aui-base,aui-alert">
	var languageSelectInput = A.one('#<portlet:namespace />languageId');

	if (languageSelectInput) {
		languageSelectInput.on(
			'change',
			function() {
				new A.Alert(
					{
						bodyContent: '<liferay-ui:message key="this-change-will-only-affect-the-newly-created-localized-content" />',
						boundingBox: '#<portlet:namespace />languageWarning',
						closeable: true,
						cssClass: 'alert-warning',
						destroyOnHide: false,
						render: true
					}
				);
			}
		);
	}
</aui:script>