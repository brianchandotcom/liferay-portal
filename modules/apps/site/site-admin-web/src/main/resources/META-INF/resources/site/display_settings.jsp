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

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="display-settings"
/>

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

boolean readOnlyLocaleInput = false;

if ((publicLayoutSet.isLayoutSetPrototypeLinkEnabled() || privateLayoutSet.isLayoutSetPrototypeLinkEnabled()) && !siteAdminConfiguration.enableCustomLanguagesWithTemplatePropagation()) {
	readOnlyLocaleInput = true;
}
%>

<c:if test="<%= readOnlyLocaleInput %>">
	<p class="text-muted">
		<liferay-ui:message key="the-language-settings-cannot-be-edited-while-propagation-of-changes-from-the-site-template-is-enabled" />
	</p>
</c:if>

<aui:input checked="<%= inheritLocales %>" id="<%= GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES %>" label="use-the-default-language-options" name="TypeSettingsProperties--inheritLocales--" readonly="<%= readOnlyLocaleInput %>" type="radio" value="<%= true %>" />

<aui:input checked="<%= !inheritLocales %>" id="customLocales" label="define-a-custom-default-language-and-additional-available-languages-for-this-site" name="TypeSettingsProperties--inheritLocales--" readonly="<%= readOnlyLocaleInput %>" type="radio" value="<%= false %>" />

<aui:fieldset id='<%= renderResponse.getNamespace() + "inheritLocalesFieldset" %>'>
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
			<%= StringUtil.merge(LocaleUtil.toDisplayNames(LanguageUtil.getAvailableLocales(), locale), StringPool.COMMA_AND_SPACE) %>
		</p>
	</aui:fieldset>
</aui:fieldset>

<aui:fieldset id='<%= renderResponse.getNamespace() + "customLocalesFieldset" %>'>
	<liferay-ui:error exception="<%= LocaleException.class %>">

		<%
		LocaleException le = (LocaleException)errorException;
		%>

		<c:choose>
			<c:when test="<%= le.getType() == LocaleException.TYPE_DEFAULT %>">
				<liferay-ui:message key="you-cannot-remove-a-language-that-is-the-current-default-language" />
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

		<aui:select label="" name="TypeSettingsProperties--languageId--" readonly="<%= readOnlyLocaleInput %>" title="language">

			<%
			Locale siteDefaultLocale = PortalUtil.getSiteDefaultLocale(liveGroup.getGroupId());

			for (Locale siteAvailableLocale : siteAvailableLocales) {
			%>

				<aui:option data-value="<%= LocaleUtil.toLanguageId(siteAvailableLocale) %>" label="<%= siteAvailableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(siteAvailableLocale) %>" selected="<%= siteDefaultLocale.getLanguage().equals(siteAvailableLocale.getLanguage()) && siteDefaultLocale.getCountry().equals(siteAvailableLocale.getCountry()) %>" value="<%= LocaleUtil.toLanguageId(siteAvailableLocale) %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>

	<div id="<portlet:namespace />languageWarning"></div>

	<div id="<portlet:namespace />defaultLanguageSiteNameWarning"></div>

	<aui:fieldset cssClass="available-languages">
		<h4 class="text-default"><liferay-ui:message key="available-languages" /></h4>

		<aui:input name='<%= "TypeSettingsProperties--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= StringUtil.merge(LocaleUtil.toLanguageIds(siteAvailableLocales)) %>" />

		<%

		// Left list

		List leftList = new ArrayList();

		String groupLanguageIds = typeSettingsProperties.getProperty(PropsKeys.LOCALES);

		if (groupLanguageIds != null) {
			for (Locale currentLocale : LocaleUtil.fromLanguageIds(StringUtil.split(groupLanguageIds))) {
				leftList.add(new KeyValuePair(LanguageUtil.getLanguageId(currentLocale), currentLocale.getDisplayName(locale)));
			}
		}
		else {
			for (Locale siteAvailableLocale : siteAvailableLocales) {
				leftList.add(new KeyValuePair(LocaleUtil.toLanguageId(siteAvailableLocale), siteAvailableLocale.getDisplayName(locale)));
			}
		}

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
			leftReorder="<%= Boolean.TRUE.toString() %>"
			leftTitle="current"
			rightBoxName="availableLanguageIds"
			rightList="<%= rightList %>"
			rightTitle="available"
		/>
	</aui:fieldset>
</aui:fieldset>

<script>
	Liferay.Util.toggleRadio(
		'<portlet:namespace />customLocales',
		'<portlet:namespace />customLocalesFieldset',
		'<portlet:namespace />inheritLocalesFieldset'
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />inheritLocales',
		'<portlet:namespace />inheritLocalesFieldset',
		'<portlet:namespace />customLocalesFieldset'
	);

	function <portlet:namespace />saveLocales() {
		var form = document.<portlet:namespace />fm;

		var currentLanguageIds = Liferay.Util.getFormElement(
			form,
			'currentLanguageIds'
		);

		if (currentLanguageIds) {
			Liferay.Util.setFormValues(form, {
				<%= PropsKeys.LOCALES %>: Liferay.Util.listSelect(
					currentLanguageIds
				)
			});
		}
	}
</script>

<aui:script use="aui-alert,aui-base">
	const languageSelectInput = A.one('#<portlet:namespace />languageId');

	if (languageSelectInput) {
		const nameInput = Liferay.component('<portlet:namespace />name');

		languageSelectInput.on('change', function(event) {
			const select = event.currentTarget.getDOMNode();

			const selectedOption = select.options[select.selectedIndex];

			Liferay.fire('inputLocalized:defaultLocaleChanged', {
				item: selectedOption
			});

			const defaultLanguage = languageSelectInput.val();

			var defaultLanguageSiteName = null;

			if (nameInput) {
				defaultLanguageSiteName = nameInput.getValue(defaultLanguage);
			}

			new A.Alert({
				bodyContent:
					'<liferay-ui:message key="this-change-will-only-affect-the-newly-created-localized-content" />',
				boundingBox: '#<portlet:namespace />languageWarning',
				closeable: true,
				cssClass: 'alert-warning',
				destroyOnHide: false,
				render: true
			});

			if (!defaultLanguageSiteName && <%= !liveGroup.isGuest() %>) {
				new A.Alert({
					bodyContent:
						'<liferay-ui:message key="site-name-will-display-a-generic-text-until-a-translation-is-added" />',
					boundingBox:
						'#<portlet:namespace />defaultLanguageSiteNameWarning',
					closeable: true,
					cssClass: 'alert-warning',
					destroyOnHide: false,
					render: true
				});

				nameInput.updateInput('<liferay-ui:message key="unnamed-site" />');
			}
		});
	}
</aui:script>