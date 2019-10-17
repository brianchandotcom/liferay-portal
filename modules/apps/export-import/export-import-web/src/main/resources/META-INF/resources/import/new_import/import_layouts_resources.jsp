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

<%@ include file="/import/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = (Group)request.getAttribute(WebKeys.GROUP);
}

FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(groupId, themeDisplay.getUserId(), ExportImportHelper.TEMP_FOLDER_NAME);

ManifestSummary manifestSummary = ExportImportHelperUtil.getManifestSummary(user.getUserId(), themeDisplay.getSiteGroupId(), new HashMap<String, String[]>(), fileEntry);
%>

<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />

<liferay-ui:error exception="<%= LARFileSizeException.class %>">
	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(UploadServletRequestConfigurationHelperUtil.getMaxSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= LARTypeException.class %>">

	<%
	LARTypeException lte = (LARTypeException)errorException;
	%>

	<liferay-ui:message arguments="<%= lte.getMessage() %>" key="please-import-a-lar-file-of-the-correct-type-x" />
</liferay-ui:error>

<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />

<liferay-ui:error exception="<%= LayoutPrototypeException.class %>">

	<%
	LayoutPrototypeException lpe = (LayoutPrototypeException)errorException;
	%>

	<liferay-ui:message key="the-lar-file-could-not-be-imported-because-it-requires-page-templates-or-site-templates-that-could-not-be-found.-please-import-the-following-templates-manually" />

	<ul>

		<%
		List<Tuple> missingLayoutPrototypes = lpe.getMissingLayoutPrototypes();

		for (Tuple missingLayoutPrototype : missingLayoutPrototypes) {
			String layoutPrototypeClassName = (String)missingLayoutPrototype.getObject(0);
			String layoutPrototypeUuid = (String)missingLayoutPrototype.getObject(1);
			String layoutPrototypeName = (String)missingLayoutPrototype.getObject(2);
		%>

			<li>
				<%= ResourceActionsUtil.getModelResource(locale, layoutPrototypeClassName) %>: <strong><%= HtmlUtil.escape(layoutPrototypeName) %></strong> (<%= HtmlUtil.escape(layoutPrototypeUuid) %>)
			</li>

		<%
		}
		%>

	</ul>
</liferay-ui:error>

<liferay-ui:error exception="<%= LocaleException.class %>">

	<%
	LocaleException le = (LocaleException)errorException;
	%>

	<c:if test="<%= le.getType() == LocaleException.TYPE_EXPORT_IMPORT %>">
		<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-available-languages-in-the-lar-file-x-do-not-match-the-site's-available-languages-x" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>">

	<%
	StructureDuplicateStructureKeyException sdske = (StructureDuplicateStructureKeyException)errorException;
	%>

	<liferay-ui:message arguments="<%= sdske.getStructureKey() %>" key="dynamic-data-mapping-structure-with-structure-key-x-already-exists" translateArguments="<%= false %>" />
</liferay-ui:error>

<portlet:actionURL name="importLayouts" var="importPagesURL">
	<portlet:param name="mvcRenderCommandName" value="viewImport" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:actionURL>

<aui:form action="<%= importPagesURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">
	<portlet:renderURL var="portletURL">
		<portlet:param name="mvcRenderCommandName" value="importLayoutsView" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:renderURL>

	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<aui:input name="<%= PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

	<div class="export-dialog-tree">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset cssClass="options-group" label="file-summary">
				<dl class="import-file-details options">
					<dt>
						<liferay-ui:message key="name" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(fileEntry.getTitle()) %>
					</dd>
					<dt>
						<liferay-ui:message key="export" />
					</dt>
					<dd>

						<%
						Date exportDate = manifestSummary.getExportDate();
						%>

						<span onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(exportDate)) %>')">
							<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - exportDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
						</span>
					</dd>
					<dt>
						<liferay-ui:message key="author" />
					</dt>
					<dd>
						<%= HtmlUtil.escape(fileEntry.getUserName()) %>
					</dd>
					<dt>
						<liferay-ui:message key="size" />
					</dt>
					<dd>
						<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>
					</dd>
				</dl>
			</aui:fieldset>

			<c:choose>
				<c:when test="<%= !group.isLayoutPrototype() && !group.isLayoutSetPrototype() && !group.isCompany() %>">
					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="pages">
						<aui:input id="publicPages" label="public-pages" name="privateLayout" type="radio" value="<%= false %>" />

						<aui:input id="privatePages" label="private-pages" name="privateLayout" type="radio" value="<%= true %>" />

						<%
						String taglibThemeSettingsLabel = "<span style='font-weight: bold;'>" + LanguageUtil.get(request, "theme-settings") + ":</span> " + LanguageUtil.get(request, "export-import-theme-settings-help");
						%>

						<aui:input label="<%= taglibThemeSettingsLabel %>" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= true %>" />

						<aui:input label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= true %>" />

						<aui:input label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= true %>" />

						<aui:input label="site-template-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS %>" type="checkbox" value="<%= true %>" />

						<%
						String taglibDeleteMissingLayoutsLabel = "<span style='font-weight: bold;'>" + LanguageUtil.get(request, "delete-missing-layouts") + ":</span> " + LanguageUtil.get(request, "delete-missing-layouts-help");
						%>

						<aui:input label="<%= taglibDeleteMissingLayoutsLabel %>" name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" value="<%= false %>" />
					</aui:fieldset>
				</c:when>
				<c:otherwise>
					<aui:input name="privateLayout" type="hidden" value="<%= true %>" />
				</c:otherwise>
			</c:choose>

			<%
			List<Portlet> dataPortlets = ListUtil.sort(manifestSummary.getDataPortlets(), new PortletTitleComparator(application, locale));
			%>

			<c:if test="<%= !dataPortlets.isEmpty() %>">
				<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="content">
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= true %>" />
					<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />

					<ul class="lfr-tree list-unstyled">
						<li class="tree-item">
							<ul class="select-options" id="<portlet:namespace />selectContents">
								<li class="options">
									<ul class="portlet-list">

										<%
										Set<String> displayedControls = new HashSet<String>();
										Set<String> portletDataHandlerClassNames = new HashSet<String>();

										for (Portlet portlet : dataPortlets) {
											PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

											Class<?> portletDataHandlerClass = portletDataHandler.getClass();

											String portletDataHandlerClassName = portletDataHandlerClass.getName();

											if (!portletDataHandlerClassNames.contains(portletDataHandlerClassName)) {
												portletDataHandlerClassNames.add(portletDataHandlerClassName);
											}
											else {
												continue;
											}

											String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

											long importModelCount = portletDataHandler.getExportModelCount(manifestSummary);

											long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());
										%>

											<c:if test="<%= (importModelCount != 0) || (modelDeletionCount != 0) %>">
												<li class="tree-item">
													<liferay-util:buffer
														var="badgeHTML"
													>
														<span class="badge badge-info"><%= (importModelCount > 0) ? importModelCount : StringPool.BLANK %></span>
														<span class="badge badge-warning deletions"><%= (modelDeletionCount > 0) ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
													</liferay-util:buffer>

													<%
													String rootControlId = PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getRootPortletId();
													%>

													<aui:input checked="<%= true %>" label="<%= portletTitle + badgeHTML %>" name="<%= rootControlId %>" type="checkbox" />

													<%
													PortletDataHandlerControl[] importControls = portletDataHandler.getImportControls();
													PortletDataHandlerControl[] importMetadataControls = portletDataHandler.getImportMetadataControls();

													if (ArrayUtil.isNotEmpty(importControls) || ArrayUtil.isNotEmpty(importMetadataControls)) {
													%>

														<div class="hide" id="<portlet:namespace />content_<%= portlet.getRootPortletId() %>">
															<ul class="lfr-tree list-unstyled">
																<li class="tree-item">
																	<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

																		<%
																		if (importControls != null) {
																			request.setAttribute("render_controls.jsp-action", Constants.IMPORT);
																			request.setAttribute("render_controls.jsp-childControl", false);
																			request.setAttribute("render_controls.jsp-controls", importControls);
																			request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
																			request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
																			request.setAttribute("render_controls.jsp-portletId", portlet.getPortletId());
																			request.setAttribute("render_controls.jsp-rootControlId", rootControlId);
																		%>

																			<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(importMetadataControls) ? "content" : StringPool.BLANK %>'>
																				<ul class="lfr-tree list-unstyled">
																					<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
																				</ul>
																			</aui:field-wrapper>

																		<%
																		}

																		if (importMetadataControls != null) {
																			for (PortletDataHandlerControl metadataControl : importMetadataControls) {
																				if (!displayedControls.contains(metadataControl.getControlName())) {
																					displayedControls.add(metadataControl.getControlName());
																				}
																				else {
																					continue;
																				}

																				PortletDataHandlerBoolean control = (PortletDataHandlerBoolean)metadataControl;

																				PortletDataHandlerControl[] childrenControls = control.getChildren();

																				if (ArrayUtil.isNotEmpty(childrenControls)) {
																					request.setAttribute("render_controls.jsp-controls", childrenControls);
																		%>

																					<aui:field-wrapper label="content-metadata">
																						<ul class="lfr-tree list-unstyled">
																							<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
																						</ul>
																					</aui:field-wrapper>

																		<%
																				}
																			}
																		}
																		%>

																	</aui:fieldset>
																</li>
															</ul>
														</div>

														<ul class="hide" id="<portlet:namespace />showChangeContent_<%= portlet.getRootPortletId() %>">
															<li>
																<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= portlet.getRootPortletId() %>"></span>

																<%
																Map<String, Object> data = new HashMap<String, Object>();

																data.put("portletid", portlet.getRootPortletId());
																data.put("portlettitle", portletTitle);
																%>

																<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portlet.getRootPortletId() %>' label="change" method="get" />
															</li>
														</ul>

														<aui:script>
															Liferay.Util.toggleBoxes(
																'<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getRootPortletId() %>',
																'<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getRootPortletId() %>'
															);
														</aui:script>

													<%
													}
													%>

												</li>
											</c:if>

										<%
										}
										%>

									</ul>

									<aui:fieldset cssClass="content-options" label="for-each-of-the-selected-content-types,-import-their">
										<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

										<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />

										<div class="hide" id="<portlet:namespace />contentOptions">
											<ul class="lfr-tree list-unstyled">
												<li class="tree-item">
													<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= true %>" />

													<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= true %>" />
												</li>
											</ul>
										</div>
									</aui:fieldset>
								</li>
							</ul>
						</li>
					</ul>
				</aui:fieldset>
			</c:if>

			<liferay-staging:deletions
				cmd="<%= Constants.IMPORT %>"
			/>

			<liferay-staging:permissions
				action="<%= Constants.IMPORT %>"
				descriptionCSSClass="permissions-description"
				global="<%= group.isCompany() %>"
				labelCSSClass="permissions-label"
			/>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="update-data">

				<%
				String taglibMirrorLabel = LanguageUtil.get(request, "mirror") + ": <span style='font-weight: normal'>" + LanguageUtil.get(request, "import-data-strategy-mirror-help") + "</span>";
				%>

				<aui:input checked="<%= true %>" id="mirror" label="<%= taglibMirrorLabel %>" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR %>" />

				<%
				String taglibMirrorWithOverwritingLabel = LanguageUtil.get(request, "mirror-with-overwriting") + ": <span style='font-weight: normal'>" + LanguageUtil.get(request, "import-data-strategy-mirror-with-overwriting-help") + "</span>";
				%>

				<aui:input id="mirrorWithOverwriting" label="<%= taglibMirrorWithOverwritingLabel %>" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE %>" />

				<%
				String taglibCopyAsNewLabel = LanguageUtil.get(request, "copy-as-new") + ": <span style='font-weight: normal'>" + LanguageUtil.get(request, "import-data-strategy-copy-as-new-help") + "</span>";
				%>

				<aui:input id="copyAsNew" label="<%= taglibCopyAsNewLabel %>" name="<%= PortletDataHandlerKeys.DATA_STRATEGY %>" type="radio" value="<%= PortletDataHandlerKeys.DATA_STRATEGY_COPY_AS_NEW %>" />
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" cssClass="options-group" label="authorship-of-the-content">

				<%
				String taglibUseTheOriginalAuthorLabel = LanguageUtil.get(request, "use-the-original-author") + ": <span style='font-weight: normal'>" + LanguageUtil.get(request, "use-the-original-author-help") + "</span>";
				%>

				<aui:input checked="<%= true %>" id="currentUserId" label="<%= taglibUseTheOriginalAuthorLabel %>" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.CURRENT_USER_ID %>" />

				<%
				String taglibUseTheCurrentUserAsAuthorLabel = LanguageUtil.get(request, "use-the-current-user-as-author") + ": <span style='font-weight: normal'>" + LanguageUtil.get(request, "use-the-current-user-as-author-help") + "</span>";
				%>

				<aui:input id="alwaysCurrentUserId" label="<%= taglibUseTheCurrentUserAsAuthorLabel %>" name="<%= PortletDataHandlerKeys.USER_ID_STRATEGY %>" type="radio" value="<%= UserIdStrategy.ALWAYS_CURRENT_USER_ID %>" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<portlet:renderURL var="backURL">
			<portlet:param name="mvcRenderCommandName" value="importLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:renderURL>

		<aui:button href="<%= backURL %>" name="back" value="back" />

		<aui:button type="submit" value="import" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '', [
		'<portlet:namespace />selectApplications'
	]);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />chooseApplications',
		'<portlet:namespace />selectApplications',
		''
	);
</aui:script>

<aui:script use="liferay-export-import-export-import">
	new Liferay.ExportImport({
		archivedSetupsNode:
			'#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>',
		commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
		deleteMissingLayoutsNode:
			'#<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>',
		deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
		form: document.<portlet:namespace />fm1,
		layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
		locale: '<%= locale.toLanguageTag() %>',
		logoNode: '#<%= PortletDataHandlerKeys.LOGO %>',
		namespace: '<portlet:namespace />',
		ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
		setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
		themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
		timeZoneOffset: <%= timeZoneOffset %>,
		userPreferencesNode:
			'#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
	});
</aui:script>