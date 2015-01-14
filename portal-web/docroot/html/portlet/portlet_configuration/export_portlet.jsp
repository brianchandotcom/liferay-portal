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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
Layout exportableLayout = ExportImportHelperUtil.getExportableLayout(themeDisplay);

PortletURL portletURL = currentURLObj;

portletURL.setParameter("tabs3", "current-and-previous");
%>

<liferay-ui:tabs
	names="new-export-process,current-and-previous"
	param="tabs3"
	refresh="<%= false %>"
>
	<liferay-ui:section>

		<%
		int incompleteBackgroundTaskCount = BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(themeDisplay.getScopeGroupId(), selPortlet.getPortletId(), PortletExportBackgroundTaskExecutor.class.getName(), false);
		%>

		<div class="<%= (incompleteBackgroundTaskCount == 0) ? "hide" : "in-progress" %>" id="<portlet:namespace />incompleteProcessMessage">
			<liferay-util:include page="/html/portlet/layouts_admin/incomplete_processes_message.jsp">
				<liferay-util:param name="incompleteBackgroundTaskCount" value="<%= String.valueOf(incompleteBackgroundTaskCount) %>" />
			</liferay-util:include>
		</div>

		<portlet:actionURL var="exportPortletURL">
			<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
		</portlet:actionURL>

		<aui:form action='<%= exportPortletURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
			<aui:input name="tabs1" type="hidden" value="export_import" />
			<aui:input name="tabs2" type="hidden" value="export" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="plid" type="hidden" value="<%= exportableLayout.getPlid() %>" />
			<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EXPORT %>" />

			<div class="export-dialog-tree">
				<aui:input label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" showRequiredLabel="<%= false %>" size="50" value='<%= StringUtil.replace(selPortlet.getDisplayName(), " ", "_") + "-" + Time.getShortTimestamp() + ".portlet.lar" %>'>
					<aui:validator name="maxLength">75</aui:validator>
					<aui:validator name="required" />
				</aui:input>

				<%
				PortletDataHandler portletDataHandler = selPortlet.getPortletDataHandlerInstance();

				PortletDataHandlerControl[] configurationControls = portletDataHandler.getExportConfigurationControls(company.getCompanyId(), themeDisplay.getScopeGroupId(), selPortlet, exportableLayout.getPlid(), false);
				%>

				<c:if test="<%= ArrayUtil.isNotEmpty(configurationControls) %>">
					<aui:fieldset cssClass="options-group" label="application">
						<ul class="lfr-tree select-options unstyled">
							<li class="options">
								<ul class="portlet-list">
									<li class="tree-item">
										<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

										<aui:input label="configuration" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

										<div class="hide" id="<portlet:namespace />configuration_<%= selPortlet.getRootPortletId() %>">
											<ul class="lfr-tree unstyled">
												<li class="tree-item">
													<aui:fieldset cssClass="portlet-type-data-section" label="configuration">
														<ul class="lfr-tree unstyled">

															<%
															request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
															request.setAttribute("render_controls.jsp-controls", configurationControls);
															request.setAttribute("render_controls.jsp-portletId", selPortlet.getRootPortletId());
															%>

															<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
														</ul>
													</aui:fieldset>
												</li>
											</ul>
										</div>

										<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= selPortlet.getRootPortletId() %>">
											<li>
												<span class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= selPortlet.getRootPortletId() %>"></span>

												<%
												Map<String,Object> data = new HashMap<String,Object>();

												data.put("portletid", selPortlet.getRootPortletId());
												%>

												<aui:a cssClass="configuration-link modify-link" data="<%= data %>" href="javascript:;" label="change" method="get" />
											</li>
										</ul>

										<aui:script>
											Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeConfiguration<%= StringPool.UNDERLINE + selPortlet.getRootPortletId() %>');
										</aui:script>
									</li>
								</ul>
							</li>
						</ul>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= !portletDataHandler.isDisplayPortlet() %>">

					<%
					DateRange dateRange = ExportImportHelperUtil.getDateRange(renderRequest, themeDisplay.getScopeGroupId(), false, exportableLayout.getPlid(), selPortlet.getPortletId(), "all");

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
							<ul class="lfr-tree select-options unstyled">
								<li class="tree-item">
									<div class="hide" id="<portlet:namespace />range">
										<aui:fieldset cssClass="portlet-data-section" label="date-range">
											<aui:input data-name='<%= LanguageUtil.get(pageContext, "all") %>' id="rangeAll" label="all" name="range" type="radio" value="all" />

											<aui:input data-name='<%= LanguageUtil.get(pageContext, "date-range") %>' helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="dateRange" />

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

								<c:if test="<%= (exportModelCount != 0) || (modelDeletionCount != 0) %>">
									<li class="options">
										<ul class="portlet-list">
											<li class="tree-item">
												<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= false %>" />

												<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= true %>" />

												<liferay-util:buffer var="badgeHTML">
													<span class="badge badge-info"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>
													<span class="badge badge-warning deletions"><%= modelDeletionCount > 0 ? (modelDeletionCount + StringPool.SPACE + LanguageUtil.get(pageContext, "deletions")) : StringPool.BLANK %></span>
												</liferay-util:buffer>

												<aui:input label='<%= LanguageUtil.get(pageContext, "content") + badgeHTML %>' name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

												<%
												PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
												PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

												if (ArrayUtil.isNotEmpty(exportControls) || ArrayUtil.isNotEmpty(metadataControls)) {
												%>

													<div class="hide" id="<portlet:namespace />content_<%= selPortlet.getRootPortletId() %>">
														<ul class="lfr-tree unstyled">
															<li class="tree-item">
																<aui:fieldset cssClass="portlet-type-data-section" label="content">
																	<c:if test="<%= exportControls != null %>">

																		<%
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
																	</c:if>

																	<c:if test="<%= metadataControls != null %>">

																		<%
																		for (PortletDataHandlerControl metadataControl : metadataControls) {
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
															Map<String,Object> data = new HashMap<String,Object>();

															data.put("portletid", selPortlet.getRootPortletId());
															%>

															<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + selPortlet.getRootPortletId() %>' label="change" method="get" />
														</li>
													</ul>

													<aui:script>
														Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeContent<%= StringPool.UNDERLINE + selPortlet.getRootPortletId() %>');
													</aui:script>

												<%
												}
												%>

											</li>
										</ul>

										<ul>
											<aui:fieldset cssClass="content-options" label="for-each-of-the-selected-content-types,-export-their">
												<span class="selected-labels" id="<portlet:namespace />selectedContentOptions"></span>

												<aui:a cssClass="modify-link" href="javascript:;" id="contentOptionsLink" label="change" method="get" />

												<div class="hide" id="<portlet:namespace />contentOptions">
													<ul class="lfr-tree unstyled">
														<li class="tree-item">
															<aui:input label="comments" name="<%= PortletDataHandlerKeys.COMMENTS %>" type="checkbox" value="<%= true %>" />

															<aui:input label="ratings" name="<%= PortletDataHandlerKeys.RATINGS %>" type="checkbox" value="<%= true %>" />

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
										</ul>
									</li>
								</c:if>
							</ul>
						</aui:fieldset>
					</c:if>

					<aui:fieldset cssClass="options-group" label="permissions">
						<ul class="lfr-tree unstyled">
							<li class="tree-item">
								<aui:input helpMessage="export-import-portlet-permissions-help" label="permissions" name="<%= PortletDataHandlerKeys.PERMISSIONS %>" type="checkbox" />

								<ul id="<portlet:namespace />permissionsUl">
									<li class="tree-item">
										<aui:input label="permissions-assigned-to-roles" name="permissionsAssignedToRoles" type="checkbox" value="<%= true %>" />
									</li>
								</ul>
							</li>
						</ul>
					</aui:fieldset>
				</c:if>

				<aui:button-row>
					<aui:button type="submit" value="export" />

					<aui:button href="<%= currentURL %>" type="cancel" />
				</aui:button-row>
			</div>
		</aui:form>
	</liferay-ui:section>

	<liferay-ui:section>
		<div class="process-list" id="<portlet:namespace />exportProcesses">
			<liferay-util:include page="/html/portlet/portlet_configuration/export_portlet_processes.jsp" />
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:script use="liferay-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="exportProcessesURL">
		<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" />
		<portlet:param name="portletResource" value="<%= portletResource %>" />
	</liferay-portlet:resourceURL>

	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			incompleteProcessMessageNode: '#<portlet:namespace />incompleteProcessMessage',
			namespace: '<portlet:namespace />',
			processesNode: '#exportProcesses',
			processesResourceURL: '<%= exportProcessesURL.toString() %>',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox'
		}
	);

	var form = A.one('#<portlet:namespace />fm1');

	form.on(
		'submit',
		function(event) {
			event.preventDefault();

			submitForm(form, form.attr('action'), false);
		}
	);
</aui:script>

<aui:script>
	Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PERMISSIONS %>Checkbox', '<portlet:namespace />permissionsUl');

	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate', '<portlet:namespace />rangeLastInputs');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '<portlet:namespace />rangeLastInputs', ['<portlet:namespace />startEndDate']);
</aui:script>