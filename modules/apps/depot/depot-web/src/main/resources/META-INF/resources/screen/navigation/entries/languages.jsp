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
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = GroupServiceUtil.getGroup(depotEntry.getGroupId());

String nameMap = JSONFactoryUtil.looseSerialize(group.getNameMap());

UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

boolean inheritLocales = GetterUtil.getBoolean(typeSettingsProperties.getProperty(GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES), true);
%>

<liferay-ui:error exception="<%= LocaleException.class %>">

	<%
	LocaleException le = (LocaleException)errorException;
	%>

	<c:choose>
		<c:when test="<%= le.getType() == LocaleException.TYPE_DEFAULT %>">
			<liferay-ui:message key="you-cannot-remove-a-language-that-is-the-current-default-language" />
		</c:when>
		<c:when test="<%= le.getType() == LocaleException.TYPE_DISPLAY_SETTINGS %>">
			<liferay-ui:message arguments='<%= "<em>" + StringUtil.merge(LocaleUtil.toDisplayNames(le.getSourceAvailableLocales(), locale), StringPool.COMMA_AND_SPACE) + "</em>" %>' key="please-select-the-available-languages-of-the-repository-among-the-available-languages-of-the-portal-x" translateArguments="<%= false %>" />
		</c:when>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= DepotEntryNameException.class %>">
	<liferay-ui:message key="repository-name-is-required-for-the-default-language" />
</liferay-ui:error>

<aui:input checked="<%= inheritLocales %>" id="<%= GroupConstants.TYPE_SETTINGS_KEY_INHERIT_LOCALES %>" label="use-the-default-language-options" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= true %>" />

<aui:input checked="<%= !inheritLocales %>" id="customLocales" label="define-a-custom-default-language-and-additional-available-languages-for-this-repository" name="TypeSettingsProperties--inheritLocales--" type="radio" value="<%= false %>" />

<aui:fieldset cssClass='<%= !inheritLocales ? " hide ": StringPool.BLANK %>' id='<%= renderResponse.getNamespace() + "inheritLocalesFieldset" %>'>
	<aui:fieldset cssClass="default-language">
		<h4 class="text-muted"><liferay-ui:message key="default-language" /></h4>

		<%
		User defaultUser = company.getDefaultUser();

		Locale defaultLocale = defaultUser.getLocale();
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

<aui:fieldset cssClass='<%= inheritLocales ? " hide ": StringPool.BLANK %>' id='<%= renderResponse.getNamespace() + "customLocalesFieldset" %>'>

	<%
	Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(group.getGroupId());
	%>

	<aui:fieldset cssClass="default-language">
		<h4 class="text-default"><liferay-ui:message key="default-language" /></h4>

		<aui:select label="" name="TypeSettingsProperties--languageId--" title="language">

			<%
			Locale defaultLocale = PortalUtil.getSiteDefaultLocale(group.getGroupId());

			for (Locale availableLocale : availableLocales) {
			%>

				<aui:option data-value="<%= LocaleUtil.toLanguageId(availableLocale) %>" label="<%= availableLocale.getDisplayName(locale) %>" lang="<%= LocaleUtil.toW3cLanguageId(availableLocale) %>" selected="<%= defaultLocale.getLanguage().equals(availableLocale.getLanguage()) && defaultLocale.getCountry().equals(availableLocale.getCountry()) %>" value="<%= LocaleUtil.toLanguageId(availableLocale) %>" />

			<%
			}
			%>

		</aui:select>
	</aui:fieldset>

	<div id="<portlet:namespace />languageWarning"></div>

	<div id="<portlet:namespace />defaultLanguageNameWarning"></div>

	<aui:fieldset cssClass="available-languages">
		<h4 class="text-default"><liferay-ui:message key="available-languages" /></h4>

		<aui:input name='<%= "TypeSettingsProperties--" + PropsKeys.LOCALES + "--" %>' type="hidden" value="<%= StringUtil.merge(LocaleUtil.toLanguageIds(availableLocales)) %>" />

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
			for (Locale availableLocale : availableLocales) {
				leftList.add(new KeyValuePair(LocaleUtil.toLanguageId(availableLocale), availableLocale.getDisplayName(locale)));
			}
		}

		// Right list

		List rightList = new ArrayList();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			if (!availableLocales.contains(availableLocale)) {
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

	function <portlet:namespace />saveGroup(forceDisable) {
		<c:if test="<%= (group != null) && !group.isCompany() %>">
			<portlet:namespace />saveLocales();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<aui:script use="aui-alert,aui-base">
	const languageSelectInput = A.one('#<portlet:namespace />languageId');

	if (languageSelectInput) {
		const nameMapString = '<%= nameMap %>';

		languageSelectInput.on('change', function(event) {
			const select = event.currentTarget.getDOMNode();

			const selectedOption = select.options[select.selectedIndex];

			Liferay.fire('inputLocalized:defaultLocaleChanged', {
				item: selectedOption
			});

			const defaultLanguage = languageSelectInput.val();

			var defaultLanguageName = null;

			if (nameMapString) {
				try {
					let nameMap = JSON.parse(nameMapString);
					if (nameMap) {
						defaultLanguageName = nameMap[defaultLanguage];
					}
				} catch (e) {}
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

			if (!defaultLanguageName) {
				new A.Alert({
					bodyContent:
						'<liferay-ui:message key="repository-name-will-display-a-generic-text-until-a-translation-is-added" />',
					boundingBox: '#<portlet:namespace />defaultLanguageNameWarning',
					closeable: true,
					cssClass: 'alert-warning',
					destroyOnHide: false,
					render: true
				});
			}
		});
	}

	var form = document.getElementById('<portlet:namespace />fm');

	form.addEventListener('submit', function() {
		event.preventDefault();
		<portlet:namespace />saveGroup();
	});
</aui:script>