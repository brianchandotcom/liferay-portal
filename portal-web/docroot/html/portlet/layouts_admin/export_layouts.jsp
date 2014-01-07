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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
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

String rootNodeName = ParamUtil.getString(request, "rootNodeName");

DateRange dateRange = ExportImportHelperUtil.getDateRange(renderRequest, liveGroupId, privateLayout, 0, null, "all");

Date startDate = dateRange.getStartDate();
Date endDate = dateRange.getEndDate();

String treeId = "layoutsExportTree" + liveGroupId + privateLayout;

long[] selectedLayoutIds = GetterUtil.getLongValues(StringUtil.split(SessionTreeJSClicks.getOpenNodes(request, treeId + "SelectedNode"), ','));

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
portletURL.setParameter("tabs2", "current-and-previous");
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("liveGroupId", String.valueOf(liveGroupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("rootNodeName", rootNodeName);
%>

<liferay-ui:tabs
	names="new-export-process,current-and-previous"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<div id="<portlet:namespace />exportImportOptions">

			<%
			int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(liveGroupId, LayoutExportBackgroundTaskExecutor.class.getName(), false);
			%>

			<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
				<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
					<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
				</liferay-util:include>
			</div>

			<portlet:actionURL var="exportPagesURL">
				<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
				<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
			</portlet:actionURL>

			<aui:form action='<%= exportPagesURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EXPORT %>" />
				<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

				<div class="export-dialog-tree">
					<aui:input cssClass="file-selector" label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" showRequiredLabel="<%= false %>" size="50" value='<%= HtmlUtil.escape(StringUtil.replace(rootNodeName, " ", "_")) + "-" + Time.getShortTimestamp() + ".lar" %>'>
						<aui:validator name="maxLength">75</aui:validator>
						<aui:validator name="required" />
					</aui:input>

					<aui:input name="layoutIds" type="hidden" />

					<c:if test="<%= !group.isLayoutPrototype() && !group.isCompany() %>">
						<aui:fieldset cssClass="options-group" label="pages">
							<span class="selected-labels" id="<portlet:namespace />selectedPages"></span>

							<aui:a cssClass="modify-link" href="javascript:;" id="pagesLink" label="change" method="get" />

							<div class="hide" id="<portlet:namespace />pages">
								<aui:fieldset cssClass="portlet-data-section" label="pages-to-export">
									<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
										<liferay-util:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
										<liferay-util:param name="treeId" value="<%= treeId %>" />
										<liferay-util:param name="defaultStateChecked" value="1" />
										<liferay-util:param name="selectableTree" value="1" />
									</liferay-util:include>

									<aui:input label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= true %>" />
								</aui:fieldset>

								<aui:fieldset cssClass="portlet-data-section" label="look-and-feel">
									<aui:input helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= true %>" />

									<aui:input label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= true %>" />
								</aui:fieldset>
							</div>
						</aui:fieldset>
					</c:if>

					<%
					List<Portlet> portletDataHandlerPortlets = LayoutExporter.getPortletDataHandlerPortlets(selectedLayouts);
					%>

					<c:if test="<%= !portletDataHandlerPortlets.isEmpty() %>">
						<aui:fieldset cssClass="options-group" label="application-configuration">
							<ul class="lfr-tree unstyled">
								<li class="tree-item">
									<aui:input checked="<%= true %>" helpMessage="all-applications-export-help" id="allApplications" label="all-applications" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="radio" value="<%= true %>" />

									<div class="hide" id="<portlet:namespace />globalConfiguration">
										<aui:fieldset cssClass="portlet-data-section" label="all-applications">
											<aui:input label="setup" name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="checkbox" value="<%= true %>" />

											<aui:input label="archived-setups" name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="checkbox" value="<%= true %>" />

											<aui:input helpMessage="import-user-preferences-help" label="user-preferences" name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="checkbox" value="<%= true %>" />
										</aui:fieldset>
									</div>

									<ul class="hide" id="<portlet:namespace />showChangeGlobalConfiguration">
										<li class="tree-item">
											<span class="selected-labels" id="<portlet:namespace />selectedGlobalConfiguration"></span>

											<aui:a cssClass="modify-link" href="javascript:;" id="globalConfigurationLink" label="change" method="get" />
										</li>
									</ul>

									<aui:input helpMessage="choose-applications-export-help" id="chooseApplications" label="choose-applications" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="radio" value="<%= false %>" />

									<c:if test="<%= !group.isLayoutPrototype() %>">
										<ul class="hide options portlet-list select-options" id="<portlet:namespace />selectApplications">
											<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

											<%
											portletDataHandlerPortlets = ListUtil.sort(portletDataHandlerPortlets, new PortletTitleComparator(application, locale));

											for (Portlet portlet : portletDataHandlerPortlets) {
												PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

												PortletDataHandlerControl[] configurationControls = portletDataHandler.getExportConfigurationControls(company.getCompanyId(), liveGroupId, portlet, privateLayout);

												String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);
											%>

												<li class="tree-item">
													<aui:input label="<%= portletTitle %>" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + portlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

													<div class="hide" id="<portlet:namespace />configuration_<%= portlet.getRootPortletId() %>">
														<ul class="lfr-tree unstyled">
															<li class="tree-item">
																<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">
																	<ul class="lfr-tree unstyled">

																		<%
																		request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
																		request.setAttribute("render_controls.jsp-controls", configurationControls);
																		request.setAttribute("render_controls.jsp-portletId", portlet.getRootPortletId());
																		%>

																		<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
																	</ul>
																</aui:fieldset>
															</li>
														</ul>
													</div>

													<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= portlet.getRootPortletId() %>">
														<li>
															<span class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= portlet.getRootPortletId() %>"></span>

															<%
															Map<String,Object> data = new HashMap<String,Object>();

															data.put("portletid", portlet.getRootPortletId());
															data.put("portlettitle", portletTitle);
															%>

															<aui:a cssClass="configuration-link modify-link" data="<%= data %>" href="javascript:;" label="change" method="get" />
														</li>
													</ul>

													<aui:script>
														Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + portlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeConfiguration<%= StringPool.UNDERLINE + portlet.getRootPortletId() %>');
													</aui:script>
												</li>

											<%
											}
											%>

										</ul>
									</c:if>
								</li>
							</ul>
						</aui:fieldset>
					</c:if>

					<%
					List<Portlet> dataSiteLevelPortlets = LayoutExporter.getDataSiteLevelPortlets(company.getCompanyId());

					PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(themeDisplay, startDate, endDate);

					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();
					%>

					<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
						<aui:fieldset cssClass="options-group" label="content">
							<ul class="lfr-tree unstyled">
								<li class="tree-item">
									<aui:input checked="<%= true %>" helpMessage="all-content-export-help" id="allContent" label="all-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= true %>" />

									<aui:input helpMessage="choose-content-export-help" id="chooseContent" label="choose-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= false %>" />

									<ul class="hide select-options" id="<portlet:namespace />selectContents">
										<li>
											<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />

											<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= true %>" />

											<div class="hide" id="<portlet:namespace />range">
												<ul class="lfr-tree unstyled">
													<li class="tree-item">
														<aui:fieldset cssClass="portlet-data-section" label="date-range">
															<aui:input checked="<%= true %>" id="rangeAll" label="all" name="range" type="radio" value="all" />

															<aui:input helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="dateRange" />

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

															<ul class="date-range-options hide unstyled" id="<portlet:namespace />startEndDate">
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

															<aui:input id="rangeLast" label='<%= LanguageUtil.get(pageContext, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="last" />

															<ul class="hide unstyled" id="<portlet:namespace />rangeLastInputs">
																<li>
																	<aui:select cssClass="relative-range" label="" name="last">
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "12") %>' value="12" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "24") %>' value="24" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "48") %>' value="48" />
																		<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "7") %>' value="168" />
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
												image="calendar"
												label="<%= true %>"
												message='<%= LanguageUtil.get(locale, "date-range") + selectedLabelsHTML %>'
											/>
										</li>

										<li class="options">
											<ul class="portlet-list">
												<li class="tree-item">
													<aui:input helpMessage="export-import-categories-help" label="categories" name="<%= PortletDataHandlerKeys.CATEGORIES %>" type="checkbox" value="<%= true %>" />
												</li>

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

															<aui:input checked="<%= portletDataHandler.isPublishToLiveByDefault() %>" label="<%= portletTitle + badgeHTML %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" />

															<%
															PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
															PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

															if (ArrayUtil.isNotEmpty(exportControls) || ArrayUtil.isNotEmpty(metadataControls)) {
															%>

																<div class="hide" id="<portlet:namespace />content_<%= portlet.getPortletId() %>">
																	<ul class="lfr-tree unstyled">
																		<li class="tree-item">
																			<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">

																				<%
																				if (exportControls != null) {
																					request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
																					request.setAttribute("render_controls.jsp-controls", exportControls);
																					request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
																					request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
																				%>

																					<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
																						<ul class="lfr-tree unstyled">
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
																							<ul class="lfr-tree unstyled">
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
																	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>Checkbox', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + portlet.getPortletId() %>');
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
													<ul class="lfr-tree unstyled">
														<li class="tree-item">
															<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= true %>" />

															<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= true %>" />

															<%
															long modelDeletionCount = manifestSummary.getModelDeletionCount();
															%>

															<c:if test="<%= modelDeletionCount != 0 %>">

																<%
																String deletionsLabel = LanguageUtil.get(pageContext, "deletions") + (modelDeletionCount > 0 ? " (" + modelDeletionCount + ")" : StringPool.BLANK);
																%>

																<aui:input data-name="<%= deletionsLabel %>" helpMessage="deletions-help" label="<%= deletionsLabel %>" name="<%= PortletDataHandlerKeys.DELETIONS %>" type="checkbox" />
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
						<ul class="lfr-tree unstyled">
							<li class="tree-item">
								<aui:input label="permissions" name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" />

								<ul id="<portlet:namespace />selectPermissions">
									<li>
										<aui:input label="permissions-assigned-to-roles" name="permissionsAssignedToRoles" type="checkbox" value="<%= true %>" />
									</li>
								</ul>

								<aui:script>
									Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PERMISSIONS %>Checkbox', '<portlet:namespace />selectPermissions');
								</aui:script>
							</li>
						</ul>
					</aui:fieldset>
				</div>

				<aui:button-row>
					<aui:button type="submit" value="export" />

					<aui:button href="<%= currentURL %>" type="cancel" />
				</aui:button-row>
			</aui:form>
		</div>
	</liferay-ui:section>

	<liferay-ui:section>
		<div class="process-list" id="<portlet:namespace />exportProcesses">
			<liferay-util:include page="/html/portlet/layouts_admin/export_layouts_processes.jsp" >
				<liferay-util:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
			</liferay-util:include>
		</div>
	</liferay-ui:section>
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
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>Checkbox',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>Checkbox',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>Checkbox',
			namespace: '<portlet:namespace />',
			pageTreeId: '<%= treeId %>',
			processesNode: '#exportProcesses',
			processesResourceURL: '<%= exportProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>Checkbox',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>Checkbox',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>Checkbox'
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

				var checkedNodes = selectedContents.all('input[type=checkbox]');

				checkedNodes.each(
					function(item, index, collection) {
						if (!item.attr('checked')) {
							item.attr('checked', true);

							Liferay.Util.updateCheckboxValue(item);
						}
					}
				);

				var portletDataControlDefault = A.one('#<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>');

				portletDataControlDefault.val(true);
			}

			submitForm(form, form.attr('action'), false);
		}
	);
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