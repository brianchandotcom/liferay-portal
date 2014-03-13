<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", BookmarksUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", BookmarksUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));

try {
	BookmarksFolder rootFolder = BookmarksFolderLocalServiceUtil.getFolder(rootFolderId);

	rootFolderName = rootFolder.getName();

	if (rootFolder.getGroupId() != scopeGroupId) {
		rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		rootFolderName = StringPool.BLANK;
	}
}
catch (NoSuchFolderException nsfe) {
	rootFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
}

String currentLanguageId = LanguageUtil.getLanguageId(request);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="languageId" type="hidden" value="<%= currentLanguageId %>" />

	<liferay-ui:tabs
		names="display-settings,email-from,entry-added-email,entry-updated-email"
		refresh="<%= false %>"
	>
		<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
		<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
		<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
		<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />
		<liferay-ui:error key="rootFolderId" message="please-enter-a-valid-root-folder" />

		<liferay-ui:section>
			<%@ include file="/html/portlet/bookmarks/display_settings.jspf" %>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />
			</aui:fieldset>
		</liferay-ui:section>

		<%
		Map<String, String> emailDefinitionTerms = BookmarksUtil.getEmailDefinitionTerms(renderRequest, emailFromAddress, emailFromName);
		%>

		<liferay-ui:section>
			<aui:select label="language" name="emailEntryAddedLanguageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this.value);" %>'>

				<%
				Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

				for (int i = 0; i < locales.length; i++) {
					String style = StringPool.BLANK;

					if (Validator.isNotNull(portletPreferences.getValue("emailEntryAddedSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
						Validator.isNotNull(portletPreferences.getValue("emailEntryAddedBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

						style = "font-weight: bold;";
					}
				%>

					<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

				<%
				}
				%>

			</aui:select>

			<liferay-ui:email-notification-settings
				emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailEntryAddedBody_" + currentLanguageId, ContentUtil.get(PropsValues.BOOKMARKS_EMAIL_ENTRY_ADDED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailEntryAddedEnabled--", BookmarksUtil.getEmailEntryAddedEnabled(portletPreferences)) %>'
				emailParam="emailEntryAdded"
				emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailEntryAddedSubject_" + currentLanguageId, ContentUtil.get(PropsValues.BOOKMARKS_EMAIL_ENTRY_ADDED_SUBJECT)) %>'
				languageId="<%= currentLanguageId %>"
			/>
		</liferay-ui:section>

		<liferay-ui:section>
			<aui:select label="language" name="emailEntryUpdatedLanguageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this.value);" %>'>

				<%
				Locale[] locales = LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());

				for (int i = 0; i < locales.length; i++) {
					String style = StringPool.BLANK;

					if (Validator.isNotNull(portletPreferences.getValue("emailEntryUpdatedSubject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
						Validator.isNotNull(portletPreferences.getValue("emailEntryUpdatedBody_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

						style = "font-weight: bold;";
					}
				%>

					<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

				<%
				}
				%>

			</aui:select>

			<liferay-ui:email-notification-settings
				emailBody='<%= PrefsParamUtil.getString(portletPreferences, request, "emailEntryUpdatedBody_" + currentLanguageId, ContentUtil.get(PropsValues.BOOKMARKS_EMAIL_ENTRY_UPDATED_BODY)) %>'
				emailDefinitionTerms="<%= emailDefinitionTerms %>"
				emailEnabled='<%= ParamUtil.getBoolean(request, "preferences--emailEntryUpdatedEnabled--", BookmarksUtil.getEmailEntryUpdatedEnabled(portletPreferences)) %>'
				emailParam="emailEntryUpdated"
				emailSubject='<%= PrefsParamUtil.getString(portletPreferences, request, "emailEntryUpdatedSubject_" + currentLanguageId, ContentUtil.get(PropsValues.BOOKMARKS_EMAIL_ENTRY_UPDATED_SUBJECT)) %>'
				languageId="<%= currentLanguageId %>"
			/>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updateLanguage(languageId) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';
		document.<portlet:namespace />fm.<portlet:namespace />languageId.value = languageId;

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<portlet:namespace />saveEmails();

			document.<portlet:namespace />fm.<portlet:namespace />folderColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentFolderColumns);
			document.<portlet:namespace />fm.<portlet:namespace />entryColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentEntryColumns);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);

	function <portlet:namespace />saveEmails() {
		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailEntryAddedBody_<%= currentLanguageId %>--'].value = window['<portlet:namespace />emailEntryAdded'].getHTML();
		}
		catch (e) {
		}

		try {
			document.<portlet:namespace />fm['<portlet:namespace />preferences--emailEntryUpdatedBody_<%= currentLanguageId %>--'].value = window['<portlet:namespace />emailEntryUpdated'].getHTML();
		}
		catch (e) {
		}
	}
</aui:script>