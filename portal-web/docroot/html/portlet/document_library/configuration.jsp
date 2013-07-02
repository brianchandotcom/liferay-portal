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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = "/document_library_display";

if (portletResource.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	strutsAction = "/document_library";
}

String tabs2 = ParamUtil.getString(request, "tabs2", "display-settings");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", DLUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", DLUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));

boolean emailFileEntryAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailFileEntryAddedEnabled--", DLUtil.getEmailFileEntryAddedEnabled(portletPreferences));
boolean emailFileEntryUpdatedEnabled = ParamUtil.getBoolean(request, "preferences--emailFileEntryUpdatedEnabled--", DLUtil.getEmailFileEntryUpdatedEnabled(portletPreferences));

String emailParam = StringPool.BLANK;
String defaultEmailSubject = StringPool.BLANK;
String defaultEmailBody = StringPool.BLANK;

if (tabs2.equals("document-added-email")) {
	emailParam = "emailFileEntryAdded";
	defaultEmailSubject = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
}
else if (tabs2.equals("document-updated-email")) {
	emailParam = "emailFileEntryUpdated";
	defaultEmailSubject = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
}

String currentLanguageId = LanguageUtil.getLanguageId(request);

String emailSubjectParam = emailParam + "Subject_" + currentLanguageId;
String emailBodyParam = emailParam + "Body_" + currentLanguageId;

String emailSubject = PrefsParamUtil.getString(portletPreferences, request, emailSubjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(portletPreferences, request, emailBodyParam, defaultEmailBody);
%>

<liferay-portlet:renderURL portletConfiguration="true" var="portletURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<%
	String tabs2Names = "display-settings,email-from,document-added-email,document-updated-email";
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
	<liferay-ui:error key="emailFileEntryAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailFileEntryAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailFileEntryAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFileEntryUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailFileEntryUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailFileEntryUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<c:choose>
		<c:when test='<%= tabs2.equals("display-settings") %>'>
			<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
			<aui:input name="preferences--displayViews--" type="hidden" />
			<aui:input name="preferences--entryColumns--" type="hidden" />

			<liferay-ui:panel-container extended="<%= true %>" id="documentLibrarySettingsPanelContainer" persistState="<%= true %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryItemsListingPanel" persistState="<%= true %>" title="display-settings">
					<aui:fieldset>
						<aui:field-wrapper label="root-folder">
							<portlet:renderURL var="viewFolderURL">
								<portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' />
								<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
							</portlet:renderURL>

							<aui:a href="<%= viewFolderURL %>" id="rootFolderName"><%= rootFolderName %></aui:a>

							<aui:button name="selectFolderButton" value="select" />

							<%
							String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
							%>

							<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
						</aui:field-wrapper>

						<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />

						<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

							<%
							for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
							%>

								<aui:option label="<%= pageDeltaValue %>" selected="<%= entriesPerPage == pageDeltaValue %>" />

							<%
							}
							%>

						</aui:select>

						<aui:field-wrapper label="display-style-views">

							<%
							Set<String> availableDisplayViews = SetUtil.fromArray(PropsValues.DL_DISPLAY_VIEWS);

							// Left list

							List leftList = new ArrayList();

							for (String displayView : displayViews) {
								leftList.add(new KeyValuePair(displayView, LanguageUtil.get(pageContext, displayView)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(displayViews);

							for (String displayView : availableDisplayViews) {
								if (Arrays.binarySearch(displayViews, displayView) < 0) {
									rightList.add(new KeyValuePair(displayView, LanguageUtil.get(pageContext, displayView)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentDisplayViews"
								leftList="<%= leftList %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableDisplayViews"
								rightList="<%= rightList %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</aui:fieldset>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryEntriesListingPanel" persistState="<%= true %>" title="entries-listing">
					<aui:fieldset>
						<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />

						<aui:field-wrapper label="show-columns">

							<%
							Set<String> availableEntryColumns = SetUtil.fromArray(StringUtil.split(allEntryColumns));

							// Left list

							List leftList = new ArrayList();

							for (String entryColumn : entryColumns) {
								leftList.add(new KeyValuePair(entryColumn, LanguageUtil.get(pageContext, entryColumn)));
							}

							// Right list

							List rightList = new ArrayList();

							Arrays.sort(entryColumns);

							for (String entryColumn : availableEntryColumns) {
								if (Arrays.binarySearch(entryColumns, entryColumn) < 0) {
									rightList.add(new KeyValuePair(entryColumn, LanguageUtil.get(pageContext, entryColumn)));
								}
							}

							rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
							%>

							<liferay-ui:input-move-boxes
								leftBoxName="currentEntryColumns"
								leftList="<%= leftList %>"
								leftReorder="true"
								leftTitle="current"
								rightBoxName="availableEntryColumns"
								rightList="<%= rightList %>"
								rightTitle="available"
							/>
						</aui:field-wrapper>
					</aui:fieldset>
				</liferay-ui:panel>

				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDocumentsRatingsPanel" persistState="<%= true %>" title="ratings">
					<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= enableRatings %>" />
					<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= enableCommentRatings %>" />
				</liferay-ui:panel>
			</liferay-ui:panel-container>

			<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value='<%= strutsAction + "/select_folder" %>' />
			</liferay-portlet:renderURL>

			<aui:script use="aui-base">
				A.one('#<portlet:namespace />selectFolderButton').on(
					'click',
					function(event) {
						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									modal: true,
									zIndex: Liferay.zIndex.WINDOW + 2,
									width: 600
								},
								id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
								title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "folder") %>',
								uri: '<%= selectFolderURL.toString() %>'
							},
							function(event) {
								var folderData = {
									idString: 'rootFolderId',
									idValue: event.folderid,
									nameString: 'rootFolderName',
									nameValue: event.foldername
								};

								Liferay.Util.selectFolder(folderData, '<liferay-portlet:renderURL portletName="<%= portletResource %>"><portlet:param name="struts_action" value='<%= strutsAction + "/view" %>' /></liferay-portlet:renderURL>', '<portlet:namespace />');
							}
						);
					}
				);
			</aui:script>
		</c:when>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= emailFromAddress %>" />
			</aui:fieldset>

			<div class="definition-of-terms">
				<h4><liferay-ui:message key="definition-of-terms" /></h4>

				<dl>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-document" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-document" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-document" />
					</dd>
					<dt>
						[$DOCUMENT_STATUS_BY_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-updated-the-document" />
					</dd>
					<dt>
						[$DOCUMENT_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-document" />
					</dd>
					<dt>
						[$DOCUMENT_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-document" />
					</dd>
					<dt>
						[$PORTLET_NAME$]
					</dt>
					<dd>
						<%= PortalUtil.getPortletTitle(renderResponse) %>
					</dd>
					<dt>
						[$SITE_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-site-name-associated-with-the-document" />
					</dd>
				</dl>
			</div>
		</c:when>
		<c:when test='<%= tabs2.startsWith("document-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("document-added-email") %>'>
						<aui:input label="enabled" name="preferences--emailFileEntryAddedEnabled--" type="checkbox" value="<%= emailFileEntryAddedEnabled %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("document-updated-email") %>'>
						<aui:input label="enabled" name="preferences--emailFileEntryUpdatedEnabled--" type="checkbox" value="<%= emailFileEntryUpdatedEnabled %>" />
					</c:when>
				</c:choose>

				<aui:select label="language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage(this);" %>'>

					<%
					Locale[] locales = LanguageUtil.getAvailableLocales();

					for (int i = 0; i < locales.length; i++) {
						String style = StringPool.BLANK;

						if (Validator.isNotNull(portletPreferences.getValue(emailParam + "Subject_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK)) ||
							Validator.isNotNull(portletPreferences.getValue(emailParam + "Body_" + LocaleUtil.toLanguageId(locales[i]), StringPool.BLANK))) {

							style = "font-weight: bold;";
						}
					%>

						<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" style="<%= style %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input cssClass="lfr-input-text-container" label="subject" name='<%= "preferences--" + emailSubjectParam + "--" %>' value="<%= emailSubject %>" />

				<aui:field-wrapper label="body">
					<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

					<aui:input name='<%= "preferences--" + emailBodyParam + "--" %>' type="hidden" />
				</aui:field-wrapper>
			</aui:fieldset>

			<div class="definition-of-terms">
				<h4><liferay-ui:message key="definition-of-terms" /></h4>

				<dl>
					<dt>
						[$COMPANY_ID$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-id-associated-with-the-document" />
					</dd>
					<dt>
						[$COMPANY_MX$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-mx-associated-with-the-document" />
					</dd>
					<dt>
						[$COMPANY_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-company-name-associated-with-the-document" />
					</dd>
					<dt>
						[$DOCUMENT_TITLE$]
					</dt>
					<dd>
						<liferay-ui:message key="the-document-title" />
					</dd>
					<dt>
						[$DOCUMENT_TYPE$]
					</dt>
					<dd>
						<liferay-ui:message key="the-document-type" />
					</dd>
					<dt>
						[$DOCUMENT_USER_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-email-address-of-the-user-who-added-the-document" />
					</dd>
					<dt>
						[$DOCUMENT_USER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-user-who-added-the-document" />
					</dd>
					<dt>
						[$FOLDER_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-folder-in-which-the-document-has-been-added" />
					</dd>
					<dt>
						[$FROM_ADDRESS$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromAddress) %>
					</dd>
					<dt>
						[$FROM_NAME$]
					</dt>
					<dd>
						<%= HtmlUtil.escape(emailFromName) %>
					</dd>
					<dt>
						[$PORTAL_URL$]
					</dt>
					<dd>
						<%= company.getVirtualHostname() %>
					</dd>
					<dt>
						[$PORTLET_NAME$]
					</dt>
					<dd>
						<%= PortalUtil.getPortletTitle(renderResponse) %>
					</dd>
					<dt>
						[$SITE_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-site-name-associated-with-the-document" />
					</dd>
					<dt>
						[$TO_ADDRESS$]
					</dt>
					<dd>
						<liferay-ui:message key="the-address-of-the-email-recipient" />
					</dd>
					<dt>
						[$TO_NAME$]
					</dt>
					<dd>
						<liferay-ui:message key="the-name-of-the-email-recipient" />
					</dd>
				</dl>
			</div>
		</c:when>
	</c:choose>
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}

	function <portlet:namespace />updateLanguage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<c:choose>
				<c:when test='<%= tabs2.startsWith("document-") %>'>
					document.<portlet:namespace />fm.<portlet:namespace /><%= emailBodyParam %>.value = window.<portlet:namespace />editor.getHTML();
				</c:when>
				<c:when test='<%= tabs2.equals("display-settings") %>'>
					document.<portlet:namespace />fm.<portlet:namespace />displayViews.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentDisplayViews);
					document.<portlet:namespace />fm.<portlet:namespace />entryColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentEntryColumns);
				</c:when>
			</c:choose>

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.document_library.configuration.jsp";
%>