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

<%@ include file="/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BookmarksEntry entry = (BookmarksEntry)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

long folderId = BeanParamUtil.getLong(entry, request, "folderId");

if (entry != null) {
	BookmarksUtil.addPortletBreadcrumbEntries(entry, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
}
else {
	BookmarksUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-bookmark"), currentURL);
	}
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

String headerTitle = (entry == null) ? LanguageUtil.get(request, "add-bookmark") : LanguageUtil.format(request, "edit-x", entry.getName(), false);

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<portlet:actionURL name="/bookmarks/edit_entry" var="editEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/bookmarks/edit_entry" />
	</portlet:actionURL>

	<aui:form action="<%= editEntryURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveEntry();" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation && showHeader %>">
			<liferay-ui:header
				backURL="<%= backURL %>"
				localizeTitle="<%= (entry == null) %>"
				title="<%= headerTitle %>"
			/>
		</c:if>

		<liferay-ui:error exception="<%= EntryURLException.class %>" message="please-enter-a-valid-url" />
		<liferay-ui:error exception="<%= NoSuchFolderException.class %>" message="please-enter-a-valid-folder" />

		<liferay-ui:asset-categories-error />

		<liferay-ui:asset-tags-error />

		<aui:model-context bean="<%= entry %>" model="<%= BookmarksEntry.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<c:if test="<%= (entry != null) || (folderId <= 0) || Validator.isNotNull(referringPortletResource) %>">

					<%
					String folderName = StringPool.BLANK;

					if (folderId > 0) {
						BookmarksFolder folder = BookmarksFolderServiceUtil.getFolder(folderId);

						folderId = folder.getFolderId();
						folderName = folder.getName();
					}
					%>

					<div class="form-group">
						<aui:input label="folder" name="folderName" type="resource" value="<%= folderName %>" />

						<aui:button name="selectFolderButton" value="select" />

						<aui:script>
							AUI.$('#<portlet:namespace />selectFolderButton').on(
								'click',
								function(event) {
									Liferay.Util.selectEntity(
										{
											dialog: {
												constrain: true,
												modal: true,
												width: 680
											},
											id: '<portlet:namespace />selectFolder',
											title: '<liferay-ui:message arguments="folder" key="select-x" />',
											uri: '<liferay-portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" /></liferay-portlet:renderURL>'
										},
										function(event) {
											var folderData = {
												idString: 'folderId',
												idValue: event.folderid,
												nameString: 'folderName',
												nameValue: event.name
											};

											Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
										}
									);
								}
							);
						</aui:script>

						<%
						String taglibRemoveFolder = "Liferay.Util.removeEntitySelection('folderId', 'folderName', this, '" + renderResponse.getNamespace() + "');";
						%>

						<aui:button disabled="<%= (folderId <= 0) %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
					</div>
				</c:if>

				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

				<aui:input name="url" />

				<aui:input name="description" />
			</aui:fieldset>

			<liferay-ui:custom-attributes-available className="<%= BookmarksEntry.class.getName() %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
					<liferay-ui:custom-attribute-list
						className="<%= BookmarksEntry.class.getName() %>"
						classPK="<%= entryId %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</liferay-ui:custom-attributes-available>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
				<aui:input name="categories" type="assetCategories" />

				<aui:input name="tags" type="assetTags" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
				<liferay-ui:input-asset-links
					className="<%= BookmarksEntry.class.getName() %>"
					classPK="<%= entryId %>"
				/>
			</aui:fieldset>

			<c:if test="<%= entry == null %>">
				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= BookmarksEntry.class.getName() %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm.<portlet:namespace />name.value + ' ' + document.<portlet:namespace />fm.<portlet:namespace />description.value;
	}

	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (entry == null) ? Constants.ADD : Constants.UPDATE %>';

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>