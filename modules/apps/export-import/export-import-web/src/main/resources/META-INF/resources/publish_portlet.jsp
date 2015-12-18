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

<liferay-staging:defineObjects />

<%
Layout exportableLayout = ExportImportHelperUtil.getExportableLayout(themeDisplay);

String errorMessageKey = StringPool.BLANK;

Layout targetLayout = null;

if (!layout.isTypeControlPanel()) {
	if (liveGroup == null) {
		errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
	}
	else {
		try {
			if (stagingGroup.isLayout()) {
				targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
			}
			else {
				targetLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(exportableLayout.getUuid(), liveGroup.getGroupId(), exportableLayout.isPrivateLayout());
			}
		}
		catch (NoSuchLayoutException nsle) {
			errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
		}

		if (targetLayout != null) {
			LayoutType layoutType = targetLayout.getLayoutType();

			if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
				errorMessageKey = "this-portlet-has-not-been-added-to-the-live-page-publish-the-page-first";
			}
		}
	}
}
else if (stagingGroup.isLayout()) {
	if (liveGroup == null) {
		errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
	}
	else {
		try {
			targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
		}
		catch (NoSuchLayoutException nsle) {
			errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
		}
	}
}

PortletURL portletURL = currentURLObj;

portletURL.setParameter("tabs3", "current-and-previous");
%>

<liferay-util:include page="/export_import_error.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= (themeDisplay.getURLPublishToLive() == null) && !layout.isTypeControlPanel() %>">
	</c:when>
	<c:when test="<%= Validator.isNotNull(errorMessageKey) %>">
		<liferay-ui:message key="<%= errorMessageKey %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="new-publication-process,current-and-previous"
			param="tabs3"
			refresh="<%= false %>"
		>
			<liferay-ui:section>

				<%
				int incompleteBackgroundTaskCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(StagingUtil.getStagingAndLiveGroupIds(themeDisplay.getScopeGroupId()), selPortlet.getPortletId(), BackgroundTaskExecutorNames.PORTLET_STAGING_BACKGROUND_TASK_EXECUTOR, false);
				%>

				<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
					<liferay-util:include page="/incomplete_processes_message.jsp" servletContext="<%= application %>">
						<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
					</liferay-util:include>
				</div>

				<portlet:actionURL name="publishPortlet" var="publishPortletURL">
					<portlet:param name="mvcRenderCommandName" value="publishPortlet" />
				</portlet:actionURL>

				<liferay-portlet:renderURL var="redirectURL">
					<portlet:param name="mvcRenderCommandName" value="publishPortlet" />
					<portlet:param name="tabs3" value="current-and-previous" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= publishPortletURL %>" cssClass="lfr-export-dialog" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishToLive();" %>'>
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.PUBLISH_TO_LIVE %>" />
					<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
					<aui:input name="plid" type="hidden" value="<%= exportableLayout.getPlid() %>" />
					<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
					<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

					<div class="export-dialog-tree">

						<%
						PortletDataHandler portletDataHandler = selPortlet.getPortletDataHandlerInstance();

						PortletDataHandlerControl[] configurationControls = portletDataHandler.getExportConfigurationControls(company.getCompanyId(), themeDisplay.getScopeGroupId(), selPortlet, exportableLayout.getPlid(), false);
						%>

						<c:if test="<%= ArrayUtil.isNotEmpty(configurationControls) %>">
							<aui:fieldset cssClass="options-group" label="application">
								<ul class="lfr-tree list-unstyled select-options">
									<li class="options">
										<ul class="portlet-list">
											<li class="tree-item">
												<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

												<aui:input label="configuration" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

												<div class="hide" id="<portlet:namespace />configuration_<%= selPortlet.getRootPortletId() %>">
													<ul class="lfr-tree list-unstyled">
														<li class="tree-item">
															<aui:fieldset cssClass="portlet-type-data-section" label="configuration">
																<ul class="lfr-tree list-unstyled">

																	<%
																	request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
																	request.setAttribute("render_controls.jsp-controls", configurationControls);
																	request.setAttribute("render_controls.jsp-portletId", selPortlet.getRootPortletId());
																	%>

																	<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
																</ul>
															</aui:fieldset>
														</li>
													</ul>
												</div>

												<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= selPortlet.getRootPortletId() %>">
													<li>
														<span class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= selPortlet.getRootPortletId() %>"></span>

														<%
														Map<String, Object> data = new HashMap<String, Object>();

														data.put("portletid", selPortlet.getRootPortletId());
														%>

														<aui:a cssClass="configuration-link modify-link" data="<%= data %>" href="javascript:;" label="change" method="get" />
													</li>
												</ul>

												<aui:script>
													Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>', '<portlet:namespace />showChangeConfiguration<%= StringPool.UNDERLINE + selPortlet.getRootPortletId() %>');
												</aui:script>
											</li>
										</ul>
									</li>
								</ul>
							</aui:fieldset>
						</c:if>

						<c:if test="<%= !portletDataHandler.isDisplayPortlet() %>">

							<%
							DateRange dateRange = ExportImportDateUtil.getDateRange(renderRequest, themeDisplay.getScopeGroupId(), false, exportableLayout.getPlid(), selPortlet.getPortletId(), ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

							Date startDate = dateRange.getStartDate();
							Date endDate = dateRange.getEndDate();

							PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(themeDisplay, startDate, endDate);

							portletDataHandler.prepareManifestSummary(portletDataContext, portletPreferences);

							ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

							long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);

							long modelDeletionCount = manifestSummary.getModelDeletionCount(portletDataHandler.getDeletionSystemEventStagedModelTypes());
							%>

							<c:if test="<%= (exportModelCount != 0) || (modelDeletionCount != 0) || (startDate != null) || (endDate != null) %>">
								<aui:fieldset cssClass="options-group" label="content">
									<ul class="lfr-tree list-unstyled select-options">
										<li class="tree-item">
											<div class="hide" id="<portlet:namespace />range">
												<aui:fieldset cssClass="date-range-options" label="date-range">
													<aui:input data-name='<%= LanguageUtil.get(request, "all") %>' id="rangeAll" label="all" name="range" type="radio" value="all" />

													<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(request, "from-last-publish-date") %>' id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="fromLastPublishDate" />

													<aui:input data-name='<%= LanguageUtil.get(request, "date-range") %>' helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="dateRange" />

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
																	lastEnabledDate="<%= new Date() %>"
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
																	lastEnabledDate="<%= new Date() %>"
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

													<aui:input id="rangeLast" label='<%= LanguageUtil.get(request, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="last" />

													<ul class="hide list-unstyled" id="<portlet:namespace />rangeLastInputs">
														<li>
															<aui:select cssClass="relative-range" label="" name="last">
																<aui:option label='<%= LanguageUtil.format(request, "x-hours", "12", false) %>' value="12" />
																<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' value="24" />
																<aui:option label='<%= LanguageUtil.format(request, "x-hours", "48", false) %>' value="48" />
																<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' value="168" />
															</aui:select>
														</li>
													</ul>
												</aui:fieldset>
											</div>

											<liferay-util:buffer var="selectedLabelsHTML">
												<span class="selected-labels" id="<portlet:namespace />selectedRange"></span>

												<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
											</liferay-util:buffer>

											<liferay-ui:icon
												iconCssClass="icon-calendar"
												label="<%= true %>"
												message='<%= LanguageUtil.get(request, "date-range") + selectedLabelsHTML %>'
											/>
										</li>

										<c:if test="<%= (exportModelCount != 0) || (modelDeletionCount != 0) %>">
											<li class="options">
												<ul class="portlet-list">
													<li class="tree-item">
														<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= false %>" />

														<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= true %>" />

														<liferay-util:buffer var="badgeHTML">
															<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>
															<span class="badge badge-warning" id="<portlet:namespace />deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(request, "deletions")) : StringPool.BLANK %></span>
														</liferay-util:buffer>

														<aui:input label='<%= LanguageUtil.get(request, "content") + badgeHTML %>' name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

														<%
														PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
														PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

														if (ArrayUtil.isNotEmpty(exportControls) || ArrayUtil.isNotEmpty(metadataControls)) {
														%>

															<div class="hide" id="<portlet:namespace />content_<%= selPortlet.getRootPortletId() %>">
																<ul class="lfr-tree list-unstyled">
																	<li class="tree-item">
																		<aui:fieldset cssClass="portlet-type-data-section" label="content">
																			<aui:field-wrapper label='<%= ArrayUtil.isNotEmpty(metadataControls) ? "content" : StringPool.BLANK %>'>
																				<c:if test="<%= exportControls != null %>">

																					<%
																					request.setAttribute("render_controls.jsp-action", Constants.PUBLISH);
																					request.setAttribute("render_controls.jsp-controls", exportControls);
																					request.setAttribute("render_controls.jsp-manifestSummary", manifestSummary);
																					request.setAttribute("render_controls.jsp-portletDisabled", !portletDataHandler.isPublishToLiveByDefault());
																					%>

																					<ul class="lfr-tree list-unstyled">
																						<liferay-util:include page="/render_controls.jsp" servletContext="<%= application %>" />
																					</ul>
																				</c:if>
																			</aui:field-wrapper>

																			<c:if test="<%= metadataControls != null %>">

																				<%
																				for (PortletDataHandlerControl metadataControl : metadataControls) {
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
																				%>

																			</c:if>
																		</aui:fieldset>
																	</li>
																</ul>
															</div>

															<ul id="<portlet:namespace />showChangeContent_<%= selPortlet.getRootPortletId() %>">
																<li>
																	<span class="selected-labels" id="<portlet:namespace />selectedContent_<%= selPortlet.getRootPortletId() %>"></span>

																	<%
																	Map<String, Object> data = new HashMap<String, Object>();

																	data.put("portletid", selPortlet.getRootPortletId());
																	%>

																	<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + selPortlet.getRootPortletId() %>' label="change" method="get" />
																</li>
															</ul>

															<aui:script>
																Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + selPortlet.getRootPortletId() %>');
															</aui:script>

														<%
														}
														%>

													</li>
												</ul>

												<ul>
													<aui:fieldset cssClass="content-options" label="for-each-of-the-selected-content-types,-publish-their">
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
												</ul>
											</li>
										</c:if>
									</ul>
								</aui:fieldset>
							</c:if>

							<liferay-staging:deletions cmd="<%= Constants.PUBLISH %>" />

							<aui:fieldset cssClass="options-group" label="permissions">

								<%
								Map<String, String[]> parameterMap = Collections.emptyMap();
								%>

								<%@ include file="/permissions.jspf" %>
							</aui:fieldset>
						</c:if>

						<aui:button-row>
							<aui:button type="submit" value="publish-to-live" />

							<aui:button onClick='<%= renderResponse.getNamespace() + "copyFromLive();" %>' value="copy-from-live" />
						</aui:button-row>
					</div>
				</aui:form>
			</liferay-ui:section>

			<liferay-ui:section>
				<div class="process-list" id="<portlet:namespace />publishProcesses">
					<liferay-util:include page="/publish_portlet_processes.jsp" servletContext="<%= application %>" />
				</div>
			</liferay-ui:section>
		</liferay-ui:tabs>

		<aui:script use="liferay-export-import">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishPortlet" var="publishProcessesURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH %>" />
				<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
				<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" />
				<portlet:param name="portletResource" value="<%= portletResource %>" />
			</liferay-portlet:resourceURL>

			new Liferay.ExportImport(
				{
					commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
					deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
					form: document.<portlet:namespace />fm1,
					incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
					locale: '<%= locale.toLanguageTag() %>',
					namespace: '<portlet:namespace />',
					processesNode: '#publishProcesses',
					processesResourceURL: '<%= publishProcessesURL.toString() %>',
					rangeAllNode: '#rangeAll',
					rangeDateRangeNode: '#rangeDateRange',
					rangeLastNode: '#rangeLast',
					rangeLastPublishNode: '#rangeLastPublish',
					ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
					timeZone: '<%= timeZone.getID() %>'
				}
			);
		</aui:script>

		<aui:script>
			function <portlet:namespace />copyFromLive() {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-portlet-information") %>')) {
					document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = 'copy_from_live';

					submitForm(document.<portlet:namespace />fm1);
				}
			}

			function <portlet:namespace />publishToLive() {
				if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-portlet-data") %>')) {
					submitForm(document.<portlet:namespace />fm1);
				}
			}

			Liferay.Util.toggleRadio('<portlet:namespace />portletMetaDataFilter', '<portlet:namespace />portletMetaDataList');
			Liferay.Util.toggleRadio('<portlet:namespace />portletMetaDataAll', '', ['<portlet:namespace />portletMetaDataList']);

			Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
			Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
			Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
			Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
		</aui:script>
	</c:otherwise>
</c:choose>