<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());

DLPortletInstanceSettings dlPortletInstanceSettings = dlRequestHelper.getDLPortletInstanceSettings();

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<liferay-frontend:edit-form-body>
		<aui:input name="preferences--selectedRepositoryId--" type="hidden" value="<%= dlAdminDisplayContext.getSelectedRepositoryId() %>" />
		<aui:input name="preferences--displayViews--" type="hidden" />
		<aui:input name="preferences--entryColumns--" type="hidden" />

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			id="documentLibraryDisplay"
			label="display-settings"
		>
			<aui:input id="showActions" label="show-actions" name="preferences--showActions--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowActions() %>" />

			<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= dlPortletInstanceSettings.isShowFoldersSearch() %>" />

			<aui:input label="show-related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRelatedAssets() %>" />

			<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

				<%
				for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
				%>

					<aui:option label="<%= pageDeltaValue %>" selected="<%= dlPortletInstanceSettings.getEntriesPerPage() == pageDeltaValue %>" />

				<%
				}
				%>

			</aui:select>

			<aui:field-wrapper label="display-style-views">
				<liferay-ui:input-move-boxes
					leftBoxName="currentDisplayViews"
					leftList="<%= dlPortletInstanceSettingsHelper.getCurrentDisplayViews() %>"
					leftReorder="<%= Boolean.TRUE.toString() %>"
					leftTitle="current"
					rightBoxName="availableDisplayViews"
					rightList="<%= dlPortletInstanceSettingsHelper.getAvailableDisplayViews() %>"
					rightTitle="available"
				/>
			</aui:field-wrapper>
		</liferay-frontend:fieldset>

		<%
		String warningMessage = null;

		if (dlAdminDisplayContext.isRootFolderInTrash()) {
			warningMessage = LanguageUtil.get(request, "the-selected-root-folder-is-in-the-recycle-bin-please-remove-it-or-select-another-one");
		}

		if (dlAdminDisplayContext.isRootFolderNotFound()) {
			warningMessage = LanguageUtil.get(request, "the-selected-root-folder-cannot-be-found-please-select-another-one");
		}
		%>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			id="documentLibraryItemsListingPanel"
			label="folders-listing"
		>
			<liferay-frontend:resource-selector
				inputLabel='<%= LanguageUtil.get(request, "root-folder") %>'
				inputName="preferences--rootFolderId--"
				modalTitle='<%= LanguageUtil.get(request, "select-folder") %>'
				resourceName="<%= dlAdminDisplayContext.getRootFolderName() %>"
				resourceValue="<%= String.valueOf(dlAdminDisplayContext.getRootFolderId()) %>"
				selectEventName="folderSelected"
				selectResourceURL="<%= dlAdminDisplayContext.getSelectRootFolderURL() %>"
				showRemoveButton="<%= true %>"
				warningMessage="<%= warningMessage %>"
			/>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			id="documentLibraryEntriesListingPanel"
			label="entries-listing-for-table-display-style"
		>
			<liferay-frontend:fieldset>
				<aui:field-wrapper label="show-columns">
					<liferay-ui:input-move-boxes
						leftBoxName="currentEntryColumns"
						leftList="<%= dlPortletInstanceSettingsHelper.getCurrentEntryColumns() %>"
						leftReorder="<%= Boolean.TRUE.toString() %>"
						leftTitle="current"
						rightBoxName="availableEntryColumns"
						rightList="<%= dlPortletInstanceSettingsHelper.getAvailableEntryColumns() %>"
						rightTitle="available"
					/>
				</aui:field-wrapper>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			id="documentLibraryDocumentsRatingsPanel"
			label="ratings"
		>
			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableRatings() %>" />
			<aui:input label="enable-ratings-for-comments" name="preferences--enableCommentRatings--" type="checkbox" value="<%= dlPortletInstanceSettings.isEnableCommentRatings() %>" />
		</liferay-frontend:fieldset>

		<aui:script sandbox="<%= true %>">
			var showActionsInput = document.getElementById(
				'<portlet:namespace />showActions'
			);

			if (showActionsInput) {
				showActionsInput.addEventListener('change', (event) => {
					var currentColumnsElement = document.getElementById(
						'<portlet:namespace />currentEntryColumns'
					);

					if (currentColumnsElement) {
						if (showActionsInput.checked) {
							currentColumnsElement.appendChild(
								document
									.createRange()
									.createContextualFragment(
										'<option value="action"><%= UnicodeLanguageUtil.get(request, "action") %></option>'
									)
							);
						}
						else {
							var options = document.querySelectorAll(
								'#<portlet:namespace />currentEntryColumns option[value="action"], #<portlet:namespace />availableEntryColumns option[value="action"]'
							);

							Array.prototype.forEach.call(options, (option) => {
								option.remove();
							});
						}
					}
				});
			}
		</aui:script>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var Util = Liferay.Util;

		Util.postForm(form, {
			data: {
				displayViews: Util.getSelectedOptionValues(
					Util.getFormElement(form, 'currentDisplayViews')
				),
				entryColumns: Util.getSelectedOptionValues(
					Util.getFormElement(form, 'currentEntryColumns')
				),
			},
		});
	}
</aui:script>