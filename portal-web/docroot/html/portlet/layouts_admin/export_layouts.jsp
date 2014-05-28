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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

if (Validator.isNull(cmd)) {
	cmd = Constants.EXPORT;
}

String exportConfigurationButtons = ParamUtil.getString(request, "exportConfigurationButtons", "custom");

long exportImportConfigurationId = 0;

ExportImportConfiguration exportImportConfiguration = null;
Map<String, Serializable> exportImportConfigurationSettingsMap = Collections.emptyMap();
Map<String, String[]> parameterMap = Collections.emptyMap();
long[] selectedLayoutIds = null;

if (SessionMessages.contains(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId")) {
	exportImportConfigurationId = (Long)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);
	}

	exportImportConfigurationSettingsMap = (Map<String, Serializable>)SessionMessages.get(liferayPortletRequest, portletDisplay.getId() + "settingsMap");

	parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
	selectedLayoutIds = GetterUtil.getLongValues(exportImportConfigurationSettingsMap.get("layoutIds"));
}
else {
	exportImportConfigurationId = ParamUtil.getLong(request, "exportImportConfigurationId");

	if (exportImportConfigurationId > 0) {
		exportImportConfiguration = ExportImportConfigurationLocalServiceUtil.getExportImportConfiguration(exportImportConfigurationId);

		exportImportConfigurationSettingsMap = exportImportConfiguration.getSettingsMap();

		parameterMap = (Map<String, String[]>)exportImportConfigurationSettingsMap.get("parameterMap");
		selectedLayoutIds = GetterUtil.getLongValues(exportImportConfigurationSettingsMap.get("layoutIds"));
	}
}

long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = (Group)request.getAttribute(WebKeys.GROUP);
}

long liveGroupId = group.getGroupId();

if (group.isStagingGroup() && !group.isStagedRemotely()) {
	Group liveGroup = group.getLiveGroup();

	liveGroupId = ParamUtil.getLong(request, "liveGroupId", liveGroup.getGroupId());
}

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

String rootNodeName = StringPool.BLANK;

if (privateLayout) {
	rootNodeName = LanguageUtil.get(pageContext, "private-pages");
}
else {
	rootNodeName = LanguageUtil.get(pageContext, "public-pages");
}

DateRange dateRange = ExportImportDateUtil.getDateRange(renderRequest, liveGroupId, privateLayout, 0, null, ExportImportDateUtil.RANGE_ALL);

Date startDate = dateRange.getStartDate();
Date endDate = dateRange.getEndDate();

String treeId = "layoutsExportTree" + liveGroupId + privateLayout;

if (!cmd.equals(Constants.UPDATE)) {
	selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode"), ','));
}

List<Layout> selectedLayouts = new ArrayList<Layout>();

for (int i = 0; i < selectedLayoutIds.length; i++) {
	try {
		selectedLayouts.add(LayoutLocalServiceUtil.getLayout(liveGroupId, privateLayout, selectedLayoutIds[i]));
	}
	catch (NoSuchLayoutException nsle) {
	}
}

if (selectedLayouts.isEmpty()) {
	selectedLayouts = LayoutLocalServiceUtil.getLayouts(liveGroupId, privateLayout);
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/layouts_admin/export_layouts");
portletURL.setParameter(Constants.CMD, Constants.EXPORT);

if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
	portletURL.setParameter("tabs2", "new-export-process");
	portletURL.setParameter("exportConfigurationButtons", "saved");
}
else {
	portletURL.setParameter("tabs2", "current-and-previous");
}

portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("liveGroupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("rootNodeName", rootNodeName);

String tabs2Names = StringPool.BLANK;

if (!cmd.equals(Constants.ADD)) {
	tabs2Names = "new-export-process,current-and-previous";
}
%>

<liferay-ui:trash-undo />

<portlet:renderURL var="backURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= backURL %>"
	title='<%= privateLayout ? LanguageUtil.get(pageContext, "export-private-pages") : LanguageUtil.get(pageContext, "export-public-pages") %>'
/>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	refresh="<%= false %>"
>

	<%
	int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(liveGroupId, LayoutExportBackgroundTaskExecutor.class.getName(), false);
	%>

	<div class='<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>' id="<portlet:namespace />incompleteProcessMessage">
		<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
			<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
		</liferay-util:include>
	</div>

	<liferay-ui:section>
		<div <%= (!cmd.equals(Constants.ADD) && !cmd.equals(Constants.UPDATE)) ? StringPool.BLANK : "class=\"hide\"" %>>
			<aui:nav-bar>
				<aui:nav cssClass="navbar-nav" id="exportConfigurationButtons">
					<aui:nav-item
						data-value="custom"
						iconCssClass="icon-puzzle"
						label="custom"
					/>

					<aui:nav-item
						data-value="saved"
						iconCssClass="icon-archive"
						label="export-templates"
					/>
				</aui:nav>
			</aui:nav-bar>
		</div>

		<div <%= exportConfigurationButtons.equals("custom") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />customConfiguration">
			<portlet:actionURL var="updateExportConfigurationURL">
				<portlet:param name="struts_action" value="/layouts_admin/edit_export_configuration" />
			</portlet:actionURL>

			<portlet:actionURL var="exportPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
				<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
			</portlet:actionURL>

			<aui:form action='<%= cmd.equals(Constants.EXPORT) ? exportPagesURL : updateExportConfigurationURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
				<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
				<aui:input name="groupId" type="hidden" value="<%= String.valueOf(groupId) %>" />
				<aui:input name="liveGroupId" type="hidden" value="<%= String.valueOf(liveGroupId) %>" />
				<aui:input name="privateLayout" type="hidden" value="<%= String.valueOf(privateLayout) %>" />
				<aui:input name="rootNodeName" type="hidden" value="<%= rootNodeName %>" />
				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>"  />
				<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

				<liferay-ui:error exception="<%= LARFileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

				<div class="export-dialog-tree">
					<c:if test="<%= !cmd.equals(Constants.EXPORT) %>">
						<aui:model-context bean="<%= exportImportConfiguration %>" model="<%= ExportImportConfiguration.class %>" />

						<aui:fieldset cssClass="options-group" label='<%= cmd.equals(Constants.ADD) ? "new-export-template" : "edit-template" %>'>
							<aui:input label="name" name="name" showRequiredLabel="<%= false %>">
								<aui:validator name="required" />
							</aui:input>

							<aui:input label="description" name="description" />
						</aui:fieldset>
					</c:if>

					<c:if test="<%= !group.isLayoutPrototype() && !group.isCompany() %>">
						<aui:fieldset cssClass="options-group" label="pages">

							<%
							request.setAttribute("select_pages.jsp-parameterMap", parameterMap);
							%>

							<liferay-util:include page="/html/portlet/layouts_admin/export_configuration/select_pages.jsp">
								<liferay-util:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
								<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
								<liferay-util:param name="treeId" value="<%= treeId %>" />
								<liferay-util:param name="selectedLayoutIds" value="<%= StringUtil.merge(selectedLayoutIds) %>" />
							</liferay-util:include>
						</aui:fieldset>
					</c:if>

					<%
					List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId(), false);

					PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(company.getCompanyId(), liveGroupId, startDate, endDate);

					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();
					%>

					<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
						<aui:fieldset cssClass="options-group" label="content">
							<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA, true) %>" />
							<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true) %>" />

							<ul class="lfr-tree list-unstyled">
								<li class="tree-item">
									<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" helpMessage="all-content-export-help" id="allContent" label="all-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= true %>" />

									<aui:input checked="<%= !MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" helpMessage="choose-content-export-help" id="chooseContent" label="choose-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= false %>" />

									<ul class="hide select-options" id="<portlet:namespace />selectContents">
										<li>
											<div class="hide" id="<portlet:namespace />range">
												<ul class="lfr-tree list-unstyled">
													<li class="tree-item">
														<aui:fieldset cssClass="portlet-data-section" label="date-range">

															<%
															String selectedRange = MapUtil.getString(parameterMap, "range");
															%>

															<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_ALL) %>" id="rangeAll" label="all" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_ALL %>" />

															<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_DATE_RANGE) %>" helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_DATE_RANGE %>" />

															<%
															Calendar endCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

															if (endDate != null) {
																endCalendar.setTime(endDate);
															}

															Calendar startCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

															if (startDate != null) {
																startCalendar.setTime(startDate);
															}
															else {
																startCalendar.add(Calendar.DATE, -1);
															}
															%>

															<ul class="date-range-options hide list-unstyled" id="<portlet:namespace />startEndDate">
																<li>
																	<aui:fieldset label="start-date">
																		<liferay-ui:input-date
																			dayParam="startDateDay"
																			dayValue="<%= startCalendar.get(Calendar.DATE) %>"
																			disabled="<%= false %>"
																			firstDayOfWeek="<%= startCalendar.getFirstDayOfWeek() - 1 %>"
																			monthParam="startDateMonth"
																			monthValue="<%= startCalendar.get(Calendar.MONTH) %>"
																			name="startDate"
																			yearParam="startDateYear"
																			yearValue="<%= startCalendar.get(Calendar.YEAR) %>"
																		/>

																		&nbsp;

																		<liferay-ui:input-time
																			amPmParam='<%= "startDateAmPm" %>'
																			amPmValue="<%= startCalendar.get(Calendar.AM_PM) %>"
																			dateParam="startDateTime"
																			dateValue="<%= startCalendar.getTime() %>"
																			disabled="<%= false %>"
																			hourParam='<%= "startDateHour" %>'
																			hourValue="<%= startCalendar.get(Calendar.HOUR) %>"
																			minuteParam='<%= "startDateMinute" %>'
																			minuteValue="<%= startCalendar.get(Calendar.MINUTE) %>"
																			name="startTime"
																		/>
																	</aui:fieldset>
																</li>

																<li>
																	<aui:fieldset label="end-date">
																		<liferay-ui:input-date
																			dayParam="endDateDay"
																			dayValue="<%= endCalendar.get(Calendar.DATE) %>"
																			disabled="<%= false %>"
																			firstDayOfWeek="<%= endCalendar.getFirstDayOfWeek() - 1 %>"
																			monthParam="endDateMonth"
																			monthValue="<%= endCalendar.get(Calendar.MONTH) %>"
																			name="endDate"
																			yearParam="endDateYear"
																			yearValue="<%= endCalendar.get(Calendar.YEAR) %>"
																		/>

																		&nbsp;

																		<liferay-ui:input-time
																			amPmParam='<%= "endDateAmPm" %>'
																			amPmValue="<%= endCalendar.get(Calendar.AM_PM) %>"
																			dateParam="startDateTime"
																			dateValue="<%= endCalendar.getTime() %>"
																			disabled="<%= false %>"
																			hourParam='<%= "endDateHour" %>'
																			hourValue="<%= endCalendar.get(Calendar.HOUR) %>"
																			minuteParam='<%= "endDateMinute" %>'
																			minuteValue="<%= endCalendar.get(Calendar.MINUTE) %>"
																			name="endTime"
																		/>
																	</aui:fieldset>
																</li>
															</ul>

															<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_LAST) %>" id="rangeLast" label='<%= LanguageUtil.get(pageContext, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_LAST %>" />

															<ul class="hide list-unstyled" id="<portlet:namespace />rangeLastInputs">
																<li>
																	<aui:select cssClass="relative-range" label="" name="last">

																		<%
																		String last = MapUtil.getString(parameterMap, "last");
																		%>

																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "12", false) %>' selected='<%= last.equals("12") %>' value="12" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "24", false) %>' selected='<%= last.equals("24") %>' value="24" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "48", false) %>' selected='<%= last.equals("48") %>' value="48" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "7", false) %>' selected='<%= last.equals("168") %>' value="168" />
																	</aui:select>
																</li>
															</ul>
														</aui:fieldset>
													</li>
												</ul>
											</div>

											<liferay-util:buffer var="selectedLabelsHTML">
												<span class="selected-labels" id="<portlet:namespace />selectedRange"></span>

												<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
											</liferay-util:buffer>

											<liferay-ui:icon
												iconCssClass="icon-calendar"
												label="<%= true %>"
												message='<%= LanguageUtil.get(pageContext, "date-range") + selectedLabelsHTML %>'
											/>
										</li>

										<li class="options">
											<ul class="portlet-list">

												<%
												Set<String> displayedControls = new HashSet<String>();
												Set<String> portletDataHandlerClasses = new HashSet<String>();

												dataSiteLevelPortlets = ListUtil.sort(dataSiteLevelPortlets, new PortletTitleComparator(application, locale));

												for (Portlet portlet : dataSiteLevelPortlets) {
													String portletDataHandlerClass = portlet.getPortletDataHandlerClass();

													if (!portletDataHandlerClasses.contains(portletDataHandlerClass)) {
														portletDataHandlerClasses.add(portletDataHandlerClass);
													}
													else {
														continue;
													}

													String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);

													PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

													portletDataHandler.prepareManifestSummary(portletDataContext);

													long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

													long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());
												%>

													<c:if test="<%= (exportModelCount != 0) || (modelDeletionCount != 0) %>">
														<li class="tree-item">
															<liferay-util:buffer var="badgeHTML">
																<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>
																<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(pageContext, "deletions")) : StringPool.BLANK %></span>
															</liferay-util:buffer>

															<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) %>" label="<%= portletTitle + badgeHTML %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId(), portletDataHandler.isPublishToLiveByDefault()) %>" />

															<%
															PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
															PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

															if (ArrayUtil.isNotEmpty(exportControls) || ArrayUtil.isNotEmpty(metadataControls)) {
															%>

																<div class="hide" id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
																	<ul class="lfr-tree list-unstyled">
																		<li class="tree-item">
																			<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

																				<%
																				if (exportControls != null) {
																					request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
																					request.setAttribute("render_controls.jsp-controls", exportControls);
																					request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
																					request.setAttribute("render_controls.jsp-parameterMap", parameterMap);
																					request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
																				%>

																					<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
																						<ul class="lfr-tree list-unstyled">
																							<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
																						</ul>
																					</aui:field-wrapper>

																				<%
																				}

																				if (metadataControls != null) {
																					for (PortletDataHandlerControl metadataControl : metadataControls) {
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
																								<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
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

																<ul class="hide" id="<portlet:namespace />showChangeContent_<%= portlet.getPortletId() %>">
																	<li>
																		<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= portlet.getPortletId() %>"></span>

																		<%
																		Map<String,Object> data = new HashMap<String,Object>();

																		data.put("portletid", portlet.getPortletId());
																		data.put("portlettitle", portletTitle);
																		%>

																		<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + portlet.getPortletId() %>' label="change" method="get" />
																	</li>
																</ul>

																<aui:script>
																	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getPortletId() %>');
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

											<aui:fieldset cssClass="content-options" label="for-each-of-the-selected-content-types,-export-their">
												<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

												<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />

												<div class="hide" id="<portlet:namespace />contentOptions">
													<ul class="lfr-tree list-unstyled">
														<li class="tree-item">
															<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.COMMENTS, true) %>" />

															<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.RATINGS, true) %>" />

															<%
															long modelDeletionCount = manifestSummary.getModelDeletionCount();
															%>

															<c:if test="<%= modelDeletionCount != 0 %>">

																<%
																String deletionsLabel = LanguageUtil.get(pageContext, "deletions") + (modelDeletionCount > 0 ? " (" + modelDeletionCount + ")" : StringPool.BLANK);
																%>

																<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>" data-name="<%= deletionsLabel %>" helpMessage="deletions-help" label="<%= deletionsLabel %>" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETIONS, false) %>" />
															</c:if>
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

					<aui:fieldset cssClass="options-group" label="permissions">
						<%@ include file="/html/portlet/layouts_admin/export_configuration/permissions.jspf" %>
					</aui:fieldset>
				</div>

				<aui:button-row>
					<c:choose>
						<c:when test="<%= cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE) %>">
							<aui:button type="submit" value="save" />

							<aui:button href="<%= portletURL.toString() %>" type="cancel" />
						</c:when>
						<c:otherwise>
							<aui:button type="submit" value="export" />

							<aui:button href="<%= backURL %>" type="cancel" />
						</c:otherwise>
					</c:choose>
				</aui:button-row>
			</aui:form>
		</div>

		<div <%= exportConfigurationButtons.equals("saved") ? StringPool.BLANK : "class=\"hide\"" %> id="<portlet:namespace />savedConfigurations">
			<liferay-util:include page="/html/portlet/layouts_admin/export_layouts_configurations.jsp">
				<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<liferay-util:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
				<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<liferay-util:param name="rootNodeName" value="<%= rootNodeName %>" />
			</liferay-util:include>
		</div>
	</liferay-ui:section>

	<c:if test="<%= !cmd.equals(Constants.ADD) %>">
		<liferay-ui:section>
			<div class="process-list" id="<portlet:namespace />exportProcesses">
				<liferay-util:include page="/html/portlet/layouts_admin/export_layouts_processes.jsp">
					<liferay-util:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
				</liferay-util:include>
			</div>
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="exportProcessesURL">
		<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
			exportLAR: true,
			form: document.<portlet:namespace />fm1,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#exportProcesses',
			processesResourceURL: '<%= exportProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
		}
	);

	var form = A.one('#<portlet:namespace />fm1');

	form.on(
		'submit',
		function(event) {
			event.preventDefault();

			var A = AUI();

			var allContentRadioChecked = A.one('#<portlet:namespace />allContent').attr('checked');

			if (allContentRadioChecked) {
				var selectedContents = A.one('#<portlet:namespace />selectContents');

				var portletDataControlDefault = A.one('#<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>');

				portletDataControlDefault.val(true);
			}

			submitForm(form, form.attr('action'), false);
		}
	);

	var clickHandler = function(event) {
		var dataValue = event.target.ancestor('li').attr('data-value');

		processDataValue(dataValue);
	};

	var processDataValue = function(dataValue) {
		var customConfiguration = A.one('#<portlet:namespace />customConfiguration');
		var savedConfigurations = A.one('#<portlet:namespace />savedConfigurations');

		if (dataValue === 'custom') {
			savedConfigurations.hide();

			customConfiguration.show();
		}
		else if (dataValue === 'saved') {
			customConfiguration.hide();

			savedConfigurations.show();
		}
	};

	A.one('#<portlet:namespace />exportConfigurationButtons').delegate('click', clickHandler, 'li a');
</aui:script>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />chooseApplications', '<portlet:namespace />selectApplications', ['<portlet:namespace />showChangeGlobalConfiguration']);
	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);

	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);

	Liferay.Util.toggleRadio('<portlet:namespace />chooseContent', '<portlet:namespace />selectContents', ['<portlet:namespace />showChangeGlobalContent']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
</aui:script>