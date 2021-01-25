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

<%@ include file="/document_library/init.jsp" %>

<%
FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");
FileVersion fileVersion = (FileVersion)request.getAttribute("info_panel.jsp-fileVersion");
boolean hideActions = GetterUtil.getBoolean(request.getAttribute("info_panel_file_entry.jsp-hideActions"));

DLPortletInstanceSettings dlPortletInstanceSettings = dlRequestHelper.getDLPortletInstanceSettings();
DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileVersion);

long assetClassPK = DLAssetHelperUtil.getAssetClassPK(fileEntry, fileVersion);
%>

<div class="sidebar-header">
	<clay:content-row
		cssClass="sidebar-section"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:content-section>
				<h1 class="component-title">
					<%= HtmlUtil.escape(fileVersion.getTitle()) %>
				</h1>

				<c:if test="<%= !Objects.equals(fileVersion.getTitle(), fileVersion.getFileName()) %>">
					<p class="component-subtitle">
						<%= HtmlUtil.escape(fileVersion.getFileName()) %>
					</p>
				</c:if>

				<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
					<clay:label
						displayType="info"
						label='<%= LanguageUtil.get(request, "version") + StringPool.SPACE + fileVersion.getVersion() %>'
					/>
				</c:if>

				<aui:model-context bean="<%= fileVersion %>" model="<%= DLFileVersion.class %>" />

				<aui:workflow-status model="<%= DLFileEntry.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fileVersion.getStatus() %>" />
			</clay:content-section>
		</clay:content-col>

		<c:if test="<%= !hideActions %>">
			<clay:content-col>
				<ul class="autofit-padded-no-gutters autofit-row">
					<li class="autofit-col">
						<liferay-util:include page="/document_library/file_entry_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>
			</clay:content-col>
		</c:if>
	</clay:content-row>
</div>

<div class="sidebar-body">

	<%
	String tabsNames = "details";

	if (dlViewFileVersionDisplayContext.isVersionInfoVisible()) {
		tabsNames += ",versions";
	}
	%>

	<liferay-ui:tabs
		cssClass="navbar-no-collapse"
		names="<%= tabsNames %>"
		refresh="<%= false %>"
	>
		<liferay-ui:section>

			<%
			String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay);
			%>

			<c:if test="<%= Validator.isNotNull(thumbnailSrc) %>">
				<div class="aspect-ratio aspect-ratio-16-to-9 sidebar-panel thumbnail">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="thumbnail" />" class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= DLURLHelperUtil.getThumbnailSrc(fileEntry, fileVersion, themeDisplay) %>" />
				</div>
			</c:if>

			<liferay-dynamic-section:dynamic-section
				name="com.liferay.document.library.web#/document_library/info_panel_file_entry.jsp#fileEntryOwner"
			>
				<clay:content-row
					cssClass="sidebar-section widget-metadata"
					noGutters="true"
				>

					<%
					User owner = UserLocalServiceUtil.fetchUser(fileEntry.getUserId());
					%>

					<clay:content-col>
						<liferay-ui:user-portrait
							user="<%= owner %>"
						/>
					</clay:content-col>

					<clay:content-col
						expand="<%= true %>"
					>
						<div class="component-title username">
							<c:if test="<%= owner != null %>">
								<a href="<%= owner.isDefaultUser() ? StringPool.BLANK : owner.getDisplayURL(themeDisplay) %>"><%= HtmlUtil.escape(owner.getFullName()) %></a>
							</c:if>
						</div>

						<div class="component-subtitle">
							<liferay-ui:message key="owner" />
						</div>
					</clay:content-col>
				</clay:content-row>
			</liferay-dynamic-section:dynamic-section>

			<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() || dlViewFileVersionDisplayContext.isSharingLinkVisible() %>">
				<div class="sidebar-section">
					<div class="btn-group">
						<c:if test="<%= dlViewFileVersionDisplayContext.isDownloadLinkVisible() %>">
							<c:choose>
								<c:when test="<%= PropsValues.DL_FILE_ENTRY_CONVERSIONS_ENABLED && DocumentConversionUtil.isEnabled() %>">

									<%
									String[] conversions = DocumentConversionUtil.getConversions(fileVersion.getExtension());
									%>

									<c:choose>
										<c:when test="<%= conversions.length > 0 %>">
											<div class="btn-group-item" data-analytics-file-entry-id="<%= String.valueOf(fileEntry.getFileEntryId()) %>">
												<clay:dropdown-menu
													dropdownItems='<%=
														new JSPDropdownItemList(pageContext) {
															{
																ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

																Map<String, Object> data = HashMapBuilder.<String, Object>put(
																	"analytics-file-entry-id", String.valueOf(fileEntry.getFileEntryId())
																).build();

																add(
																	dropdownItem -> {
																		dropdownItem.setData(data);
																		dropdownItem.setHref(DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false, true));
																		dropdownItem.setLabel(LanguageUtil.get(httpServletRequest, "this-version"));
																		dropdownItem.setSeparator(true);
																	});

																addGroup(
																	dropdownGroupItem -> {
																		dropdownGroupItem.setDropdownItems(
																			new DropdownItemList() {
																				{
																					for (String conversion : conversions) {
																						add(
																							dropdownItem -> {
																								dropdownItem.setData(data);
																								dropdownItem.setHref(DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, "&targetExtension=" + conversion));
																								dropdownItem.setLabel(StringUtil.toUpperCase(conversion));
																							});
																					}
																				}
																			});
																		dropdownGroupItem.setLabel(LanguageUtil.get(httpServletRequest, "convert-to"));
																	});
															}
														}
													%>'
													label="download"
													small="<%= true %>"
												/>
											</div>
										</c:when>
										<c:otherwise>
											<div class="btn-group-item">
												<clay:link
													data-analytics-file-entry-id="<%= fileEntry.getFileEntryId() %>"
													displayType="primary"
													href="<%= DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false, true) %>"
													label="download"
													small="<%= true %>"
													title='<%= LanguageUtil.format(resourceBundle, "file-size-x", LanguageUtil.formatStorageSize(fileVersion.getSize(), locale), false) %>'
													type="button"
												/>
											</div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div class="btn-group-item" data-analytics-file-entry-id="<%= String.valueOf(fileEntry.getFileEntryId()) %>">
										<clay:link
											displayType="primary"
											href="<%= DLURLHelperUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, false, true) %>"
											label="download"
											small="<%= true %>"
											title='<%= LanguageUtil.format(resourceBundle, "file-size-x", LanguageUtil.formatStorageSize(fileVersion.getSize(), locale), false) %>'
											type="button"
										/>
									</div>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="<%= dlViewFileVersionDisplayContext.isSharingLinkVisible() %>">
							<div class="btn-group-item">
								<liferay-sharing:button
									className="<%= DLFileEntryConstants.getClassName() %>"
									classPK="<%= fileEntry.getFileEntryId() %>"
								/>
							</div>
						</c:if>
					</div>
				</div>

				<div class="sidebar-section">

					<%
					boolean isLatestVersion = fileVersion.equals(fileEntry.getLatestFileVersion());

					String urlLabel = null;

					if (isLatestVersion) {
						urlLabel = LanguageUtil.get(resourceBundle, "latest-version-url");
					}
					else {
						urlLabel = LanguageUtil.format(request, "version-x-url", fileVersion.getVersion());
					}

					String urlInputId = liferayPortletResponse.getNamespace() + "urlInput";
					%>

					<div class="form-group">
						<label for="<%= urlInputId %>"><%= urlLabel %></label>

						<div class="input-group input-group-sm">
							<div class="input-group-item input-group-prepend">
								<input class="form-control" id="<%= urlInputId %>" value="<%= DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK, !isLatestVersion, true) %>" />
							</div>

							<span class="input-group-append input-group-item input-group-item-shrink">
								<clay:button
									cssClass="dm-infopanel-copy-clipboard lfr-portal-tooltip"
									data-clipboard-target='<%= "#" + urlInputId %>'
									displayType="secondary"
									icon="paste"
									title="copy-link"
								/>
							</span>
						</div>
					</div>

					<c:if test="<%= portletDisplay.isWebDAVEnabled() && fileEntry.isSupportsSocial() && isLatestVersion %>">

						<%
						String webDavHelpMessage = null;

						if (BrowserSnifferUtil.isWindows(request)) {
							webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"https://support.microsoft.com/en-us/kb/892211", "https://help.liferay.com/hc/en-us/articles/360028720352-Desktop-Access-to-Documents-and-Media"}, false);
						}
						else {
							webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "https://help.liferay.com/hc/en-us/articles/360028720352-Desktop-Access-to-Documents-and-Media", false);
						}

						String webDavURLInputId = liferayPortletResponse.getNamespace() + "webDavURLInput";
						%>

						<div class="form-group">
							<label for="<%= webDavURLInputId %>">
								<liferay-ui:message key='<%= TextFormatter.format("webDavURL", TextFormatter.K) %>' />

								<liferay-ui:icon-help message="<%= webDavHelpMessage %>" />
							</label>

							<div class="input-group input-group-sm">
								<div class="input-group-item input-group-prepend">
									<input class="form-control" id="<%= webDavURLInputId %>" value="<%= DLURLHelperUtil.getWebDavURL(themeDisplay, fileEntry.getFolder(), fileEntry) %>" />
								</div>

								<span class="input-group-append input-group-item input-group-item-shrink">
									<clay:button
										cssClass="dm-infopanel-copy-clipboard lfr-portal-tooltip"
										data-clipboard-target='<%= "#" + webDavURLInputId %>'
										displayType="secondary"
										icon="paste"
										title="copy-link"
									/>
								</span>
							</div>
						</div>
					</c:if>
				</div>
			</c:if>

			<dl class="sidebar-dl sidebar-section">
				<c:if test="<%= fileVersion.getModel() instanceof DLFileVersion %>">

					<%
					DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

					DLFileEntryType dlFileEntryType = dlFileVersion.getDLFileEntryType();
					%>

					<dt class="sidebar-dt">
						<liferay-ui:message key="document-type" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(dlFileEntryType.getName(locale)) %>
					</dd>
				</c:if>

				<c:if test="<%= Validator.isNotNull(fileVersion.getExtension()) %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="extension" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.escape(fileVersion.getExtension()) %>
					</dd>
				</c:if>

				<dt class="sidebar-dt">
					<liferay-ui:message key="size" />
				</dt>
				<dd class="sidebar-dd">
					<%= HtmlUtil.escape(LanguageUtil.formatStorageSize(fileVersion.getSize(), locale)) %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="modified" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message arguments="<%= new Object[] {dateFormatDateTime.format(fileVersion.getModifiedDate()), HtmlUtil.escape(fileVersion.getStatusByUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="created" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message arguments="<%= new Object[] {dateFormatDateTime.format(fileEntry.getCreateDate()), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-by-x" translateArguments="<%= false %>" />
				</dd>

				<%
				request.setAttribute("info_panel_location.jsp-parentFolder", fileEntry.getFolder());
				%>

				<liferay-util:include page="/document_library/info_panel_location.jsp" servletContext="<%= application %>" />

				<liferay-asset:asset-tags-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= assetClassPK %>"
				>
					<dt class="sidebar-dt">
						<liferay-ui:message key="tags" />
					</dt>
					<dd class="sidebar-dd">
						<liferay-asset:asset-tags-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
						/>
					</dd>
				</liferay-asset:asset-tags-available>

				<c:if test="<%= dlPortletInstanceSettings.isEnableRatings() && fileEntry.isSupportsSocial() %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="ratings" />
					</dt>
					<dd class="sidebar-dd">
						<liferay-ratings:ratings
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileEntry.getFileEntryId() %>"
							inTrash="<%= fileEntry.isInTrash() %>"
						/>
					</dd>
				</c:if>

				<c:if test="<%= Validator.isNotNull(fileEntry.getDescription()) %>">
					<dt class="sidebar-dt">
						<liferay-ui:message key="description" />
					</dt>
					<dd class="sidebar-dd">
						<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(fileVersion.getDescription())) %>
					</dd>
				</c:if>

				<liferay-asset:asset-categories-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= assetClassPK %>"
				>
					<dt class="sidebar-dt">
						<liferay-ui:message key="categories" />
					</dt>
					<dd class="sidebar-dd">
						<liferay-asset:asset-categories-summary
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= assetClassPK %>"
							displayStyle="simple-category"
						/>
					</dd>
				</liferay-asset:asset-categories-available>
			</dl>

			<%
			AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(DLFileEntryConstants.getClassName(), assetClassPK);
			%>

			<c:if test="<%= (layoutAssetEntry != null) && dlPortletInstanceSettings.isEnableRelatedAssets() && fileEntry.isSupportsSocial() %>">
				<liferay-asset:asset-links
					assetEntryId="<%= layoutAssetEntry.getEntryId() %>"
				/>
			</c:if>

			<liferay-ui:panel-container
				cssClass="metadata-panel-container panel-group-flush panel-group-sm"
				extended="<%= true %>"
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<c:if test="<%= dlViewFileVersionDisplayContext.getDDMStructuresCount() > 0 %>">

					<%
					try {
						List<DDMStructure> ddmStructures = dlViewFileVersionDisplayContext.getDDMStructures();

						for (DDMStructure ddmStructure : ddmStructures) {
							DDMFormValues ddmFormValues = null;

							try {
								ddmFormValues = dlViewFileVersionDisplayContext.getDDMFormValues(ddmStructure);
							}
							catch (Exception e) {
							}
					%>

							<c:if test="<%= ddmFormValues != null %>">
								<liferay-ui:panel
									collapsible="<%= true %>"
									cssClass="metadata"
									defaultState="closed"
									extended="<%= true %>"
									id='<%= "documentLibraryMetadataPanel" + StringPool.UNDERLINE + ddmStructure.getStructureId() %>'
									markupView="lexicon"
									persistState="<%= true %>"
									title="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
								>
									<liferay-data-engine:data-layout-renderer
										containerId='<%= liferayPortletResponse.getNamespace() + "dataEngineLayoutRenderer" + ddmStructure.getStructureId() %>'
										dataDefinitionId="<%= ddmStructure.getStructureId() %>"
										dataRecordValues="<%= DataRecordValuesUtil.getDataRecordValues(ddmFormValues, ddmStructure) %>"
										namespace="<%= liferayPortletResponse.getNamespace() + ddmStructure.getStructureId() %>"
										readOnly="<%= true %>"
									/>
								</liferay-ui:panel>
							</c:if>

					<%
						}
					}
					catch (Exception e) {
					}
					%>

				</c:if>

				<liferay-expando:custom-attributes-available
					className="<%= DLFileEntryConstants.getClassName() %>"
					classPK="<%= fileVersion.getFileVersionId() %>"
					editable="<%= false %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						cssClass="lfr-custom-fields"
						defaultState="closed"
						id="documentLibraryCustomFieldsPanel"
						markupView="lexicon"
						persistState="<%= true %>"
						title="custom-fields"
					>
						<liferay-expando:custom-attribute-list
							className="<%= DLFileEntryConstants.getClassName() %>"
							classPK="<%= fileVersion.getFileVersionId() %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</liferay-ui:panel>
				</liferay-expando:custom-attributes-available>

				<%
				try {
					List<DDMStructure> ddmStructures = DDMStructureManagerUtil.getClassStructures(company.getCompanyId(), PortalUtil.getClassNameId(RawMetadataProcessor.class), DDMStructureManager.STRUCTURE_COMPARATOR_STRUCTURE_KEY);

					for (DDMStructure ddmStructure : ddmStructures) {
						DDMFormValues ddmFormValues = null;

						DLFileEntryMetadata fileEntryMetadata = DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(ddmStructure.getStructureId(), fileVersion.getFileVersionId());

						if (fileEntryMetadata != null) {
							ddmFormValues = dlViewFileVersionDisplayContext.getDDMFormValues(fileEntryMetadata.getDDMStorageId());
						}

						if (ddmFormValues != null) {
				%>

							<liferay-ui:panel
								collapsible="<%= true %>"
								cssClass="lfr-asset-metadata"
								defaultState="closed"
								id='<%= "documentLibraryMetadataPanel" + StringPool.UNDERLINE + ddmStructure.getStructureId() %>'
								markupView="lexicon"
								persistState="<%= true %>"
								title='<%= "metadata." + ddmStructure.getStructureKey() %>'
							>
								<liferay-ddm:html
									classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
									classPK="<%= ddmStructure.getPrimaryKey() %>"
									ddmFormValues="<%= ddmFormValues %>"
									fieldsNamespace="<%= String.valueOf(ddmStructure.getPrimaryKey()) %>"
									groupId="<%= fileVersion.getGroupId() %>"
									readOnly="<%= true %>"
									requestedLocale="<%= ddmFormValues.getDefaultLocale() %>"
									showEmptyFieldLabel="<%= false %>"
								/>
							</liferay-ui:panel>

				<%
						}
					}
				}
				catch (Exception e) {
				}
				%>

			</liferay-ui:panel-container>
		</liferay-ui:section>

		<c:if test="<%= dlViewFileVersionDisplayContext.isVersionInfoVisible() %>">
			<liferay-ui:section>

				<%
				request.setAttribute("info_panel.jsp-fileEntry", fileEntry);
				%>

				<liferay-util:include page="/document_library/file_entry_history.jsp" servletContext="<%= application %>" />
			</liferay-ui:section>
		</c:if>
	</liferay-ui:tabs>
</div>

<liferay-frontend:component
	module="document_library/js/InfoPanel.es"
/>