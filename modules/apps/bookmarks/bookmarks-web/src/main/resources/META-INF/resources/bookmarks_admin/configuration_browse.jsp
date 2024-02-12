<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
BookmarksConfigurationDisplayContext bookmarksConfigurationDisplayContext = new BookmarksConfigurationDisplayContext(request, renderRequest, renderResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(bookmarksConfigurationDisplayContext.getBackURL());
portletDisplay.setURLBackTitle("bookmarks");
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="settings" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= bookmarksConfigurationDisplayContext.getSettingsVerticalNavItemList() %>"
			/>

			<p class="c-mb-1 sheet-tertiary-title text-2 text-secondary">
				<liferay-ui:message key="notifications" />
			</p>

			<clay:vertical-nav
				verticalNavItems="<%= bookmarksConfigurationDisplayContext.getNotificationsVerticalNavItemList() %>"
			/>
		</clay:col>

		<clay:col
			lg="9"
		>
			<h2>
				<%= bookmarksConfigurationDisplayContext.getTitle() %>
			</h2>

			<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL">
				<portlet:param name="navigation" value="<%= bookmarksConfigurationDisplayContext.getNavigation() %>" />
				<portlet:param name="serviceName" value="<%= BookmarksConstants.SERVICE_NAME %>" />
				<portlet:param name="settingsScope" value="group" />
			</liferay-portlet:actionURL>

			<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
				<clay:sheet
					cssClass="c-mb-4 c-mt-4 c-p-0"
					size="full"
				>
					<h3 class="c-pl-4 c-pr-4 c-pt-4 sheet-title">
						<clay:content-row
							verticalAlign="center"
						>
							<clay:content-col>
								<%= bookmarksConfigurationDisplayContext.getSubtitle() %>
							</clay:content-col>
						</clay:content-row>
					</h3>

					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
					<aui:input name="redirect" type="hidden" value="<%= bookmarksConfigurationDisplayContext.getRedirect() %>" />

					<liferay-ui:error key="emailEntryAddedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error key="emailEntryAddedSubject" message="please-enter-a-valid-subject" />
					<liferay-ui:error key="emailEntryUpdatedBody" message="please-enter-a-valid-body" />
					<liferay-ui:error key="emailEntryUpdatedSubject" message="please-enter-a-valid-subject" />

					<%
					Map<String, String> emailDefinitionTerms = BookmarksUtil.getEmailDefinitionTerms(renderRequest, bookmarksGroupServiceOverriddenConfiguration.emailFromAddress(), bookmarksGroupServiceOverriddenConfiguration.emailFromName());
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(bookmarksConfigurationDisplayContext.getNavigation(), "display-settings") %>'>
							<div class="c-px-4">
								<aui:input name="preferences--folderColumns--" type="hidden" />
								<aui:input name="preferences--entryColumns--" type="hidden" />

								<liferay-frontend:resource-selector
									inputLabel='<%= LanguageUtil.get(request, "root-folder") %>'
									inputName="preferences--rootFolderId--"
									modalTitle='<%= LanguageUtil.get(request, "select-folder") %>'
									resourceName="<%= rootFolderName %>"
									resourceValue="<%= String.valueOf(rootFolderId) %>"
									selectEventName="selectFolder"
									selectResourceURL='<%=
										PortletURLBuilder.create(
											PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), BookmarksPortletKeys.BOOKMARKS_ADMIN, 0, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
										).setMVCRenderCommandName(
											"/bookmarks/select_folder"
										).setPortletResource(
											portletResource
										).setWindowState(
											LiferayWindowState.POP_UP
										).buildString()
									%>'
									showRemoveButton="<%= true %>"
									warningMessage='<%= rootFolderInTrash ? LanguageUtil.get(request, "the-selected-root-folder-is-in-the-recycle-bin-please-remove-it-or-select-another-one") : null %>'
								/>

								<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.showFoldersSearch() %>" />

								<aui:input name="preferences--showSubfolders--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.showSubfolders() %>" />

								<aui:field-wrapper label="show-columns">

									<%
									Set<String> availableFolderColumns = SetUtil.fromArray(StringUtil.split(allFolderColumns));

									// Left list

									List<KeyValuePair> leftList = new ArrayList<>();

									for (String folderColumn : folderColumns) {
										leftList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
									}

									// Right list

									List<KeyValuePair> rightList = new ArrayList<>();

									Arrays.sort(folderColumns);

									for (String folderColumn : availableFolderColumns) {
										if (Arrays.binarySearch(folderColumns, folderColumn) < 0) {
											rightList.add(new KeyValuePair(folderColumn, LanguageUtil.get(request, folderColumn)));
										}
									}

									rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
									%>

									<liferay-ui:input-move-boxes
										leftBoxName="currentFolderColumns"
										leftList="<%= leftList %>"
										leftReorder="<%= Boolean.TRUE.toString() %>"
										leftTitle="current"
										rightBoxName="availableFolderColumns"
										rightList="<%= rightList %>"
										rightTitle="available"
									/>
								</aui:field-wrapper>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(bookmarksConfigurationDisplayContext.getNavigation(), "entry-added-email") %>'>
							<div class="c-px-4">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedEnabled() %>"
									emailParam="emailEntryAdded"
									emailSubjectLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryAddedSubject() %>"
								/>
							</div>
						</c:when>
						<c:when test='<%= Objects.equals(bookmarksConfigurationDisplayContext.getNavigation(), "entry-updated-email") %>'>
							<div class="c-px-4">
								<liferay-frontend:email-notification-settings
									emailBodyLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedBody() %>"
									emailDefinitionTerms="<%= emailDefinitionTerms %>"
									emailEnabled="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedEnabled() %>"
									emailParam="emailEntryUpdated"
									emailSubjectLocalizedValuesMap="<%= bookmarksGroupServiceOverriddenConfiguration.emailEntryUpdatedSubject() %>"
								/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="c-px-4">
								<liferay-frontend:fieldset>
									<aui:input cssClass="lfr-input-text-container" label="name" name="preferences--emailFromName--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromName() %>">
										<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
									</aui:input>

									<aui:input cssClass="lfr-input-text-container" label="address" name="preferences--emailFromAddress--" value="<%= bookmarksGroupServiceOverriddenConfiguration.emailFromAddress() %>">
										<aui:validator errorMessage="please-enter-a-valid-email-address" name="required" />
										<aui:validator name="email" />
									</aui:input>
								</liferay-frontend:fieldset>
							</div>
						</c:otherwise>
					</c:choose>
				</clay:sheet>

				<c:if test='<%= Objects.equals(bookmarksConfigurationDisplayContext.getNavigation(), "display-settings") %>'>
					<clay:sheet
						cssClass="c-mb-4 c-p-0 mt-4"
						size="full"
					>
						<h3 class="c-pl-4 c-pr-4 c-pt-4 sheet-title">
							<clay:content-row
								verticalAlign="center"
							>
								<clay:content-col>
									<liferay-ui:message key="bookmarks-listing" />
								</clay:content-col>
							</clay:content-row>
						</h3>

						<div class="c-px-4">
							<aui:input label="show-related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= bookmarksGroupServiceOverriddenConfiguration.enableRelatedAssets() %>" />

							<aui:field-wrapper label="show-columns">

								<%
								Set<String> availableEntryColumns = SetUtil.fromArray(StringUtil.split(allEntryColumns));

								// Left list

								List<KeyValuePair> leftList = new ArrayList<>();

								for (int i = 0; i < entryColumns.length; i++) {
									String entryColumn = entryColumns[i];

									leftList.add(new KeyValuePair(entryColumn, LanguageUtil.get(request, entryColumn)));
								}

								// Right list

								List<KeyValuePair> rightList = new ArrayList<>();

								Arrays.sort(entryColumns);

								for (String entryColumn : availableEntryColumns) {
									if (Arrays.binarySearch(entryColumns, entryColumn) < 0) {
										rightList.add(new KeyValuePair(entryColumn, LanguageUtil.get(request, entryColumn)));
									}
								}

								rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
								%>

								<liferay-ui:input-move-boxes
									leftBoxName="currentEntryColumns"
									leftList="<%= leftList %>"
									leftReorder="<%= Boolean.TRUE.toString() %>"
									leftTitle="current"
									rightBoxName="availableEntryColumns"
									rightList="<%= rightList %>"
									rightTitle="available"
								/>
							</aui:field-wrapper>
						</div>
					</clay:sheet>
				</c:if>

				<aui:button-row>
					<div class="c-gap-1 d-flex">
						<clay:button
							cssClass="c-mr-2"
							label="save"
							propsTransformer="{SaveConfigurationButtonPropsTransformer} from bookmarks-web"
						/>

						<clay:link
							displayType="secondary"
							href="<%= bookmarksConfigurationDisplayContext.getBackURL() %>"
							label="cancel"
							type="button"
						/>
					</div>
				</aui:button-row>
			</aui:form>
		</clay:col>
	</clay:row>
</clay:container-fluid>