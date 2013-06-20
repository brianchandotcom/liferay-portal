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

Group liveGroup = group;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
}

long liveGroupId = ParamUtil.getLong(request, "liveGroupId", liveGroup.getGroupId());

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

String rootNodeName = ParamUtil.getString(request, "rootNodeName");

Date startDate = null;

long startDateTime = ParamUtil.getLong(request, "startDate");

if (startDateTime > 0) {
	startDate = new Date(startDateTime);
}

Date endDate = null;

long endDateTime = ParamUtil.getLong(request, "endDate");

if (endDateTime > 0) {
	endDate = new Date(endDateTime);
}
%>

<div id="<portlet:namespace />exportImportOptions">
	<portlet:actionURL var="exportPagesURL">
		<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:actionURL>

	<aui:form action='<%= exportPagesURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EXPORT %>" />

		<div class="export-dialog-tree">
			<aui:input cssClass="file-selector" label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" size="50" value='<%= HtmlUtil.escape(StringUtil.replace(rootNodeName, " ", "_")) + "-" + Time.getShortTimestamp() + ".lar" %>' />

			<aui:input name="layoutIds" type="hidden" />

			<c:if test="<%= !group.isLayoutPrototype() %>">
				<aui:fieldset cssClass="options-group" label="pages">
					<div class="selected-labels" id="<portlet:namespace />selectedPages"></div>

					<aui:a cssClass="modify-link" href="javascript:;" id="pagesLink" label="change" method="get" />

					<div class="hide" id="<portlet:namespace />pages">
						<aui:fieldset cssClass="portlet-data-section" label="pages-to-export">
							<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
								<liferay-util:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
								<liferay-util:param name="treeId" value="layoutsExportTree" />
								<liferay-util:param name="defaultStateChecked" value="1" />
								<liferay-util:param name="expandFirstNode" value="1" />
								<liferay-util:param name="saveState" value="0" />
								<liferay-util:param name="selectableTree" value="1" />
							</liferay-util:include>

							<aui:input label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= true %>" />
						</aui:fieldset>

						<aui:fieldset cssClass="portlet-data-section" label="look-and-feel">
							<aui:input helpMessage="export-import-theme-help" label="theme" name="<%= PortletDataHandlerKeys.THEME %>" type="checkbox" value="<%= false %>" />

							<aui:input helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= true %>" />

							<aui:input label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= true %>" />
						</aui:fieldset>
					</div>
				</aui:fieldset>
			</c:if>

			<%
			List<Portlet> portletDataHandlerPortlets = LayoutExporter.getPortletDataHandlerPortlets(liveGroupId, privateLayout);
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
									<div class="selected-labels" id="<portlet:namespace />selectedGlobalConfiguration"></div>

									<aui:a cssClass="modify-link" href="javascript:;" id="globalConfigurationLink" label="change" method="get" />
								</li>
							</ul>

							<aui:input helpMessage="choose-applications-export-help" id="chooseApplications" label="choose-applications" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="radio" value="<%= false %>" />

							<c:if test="<%= !group.isLayoutPrototype() %>">
								<ul class="hide export-import-content" id="<portlet:namespace />selectApplications">
									<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

									<%
									Set<String> portletDataHandlerClasses = new HashSet<String>();

									portletDataHandlerPortlets = ListUtil.sort(portletDataHandlerPortlets, new PortletTitleComparator(application, locale));

									for (Portlet portlet : portletDataHandlerPortlets) {
										PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

										if ((portletDataHandler != null) && (portletDataHandler.getConfigurationControls(portlet) != null)) {
											String portletTitle = PortalUtil.getPortletTitle(portlet, application, locale);
									%>

											<li class="tree-item">
												<aui:input label="<%= portletTitle %>" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + portlet.getRootPortletId() %>" type="checkbox" value="<%= portletDataHandler.isPublishToLiveByDefault() %>" />

												<div class="hide" id="<portlet:namespace />configuration_<%= portlet.getRootPortletId() %>">
													<aui:fieldset cssClass="portlet-type-data-section" label="<%= portletTitle %>">
														<ul class="lfr-tree unstyled">

															<%
															request.setAttribute("render_controls.jsp-action", Constants.EXPORT);
															request.setAttribute("render_controls.jsp-controls", portletDataHandler.getConfigurationControls(portlet));
															request.setAttribute("render_controls.jsp-portletId", portlet.getRootPortletId());
															%>

															<liferay-util:include page="/html/portlet/layouts_admin/render_controls.jsp" />
														</ul>
													</aui:fieldset>
												 </div>

												<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= portlet.getRootPortletId() %>">
													<li>
														<div class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= portlet.getRootPortletId() %>"></div>

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
			%>

			<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
				<aui:fieldset cssClass="options-group" label="content">
					<ul class="lfr-tree unstyled">
						<li class="tree-item">
							<aui:input checked="<%= true %>" helpMessage="all-content-export-help" id="allContent" label="all-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= true %>" />

							<aui:input helpMessage="choose-content-export-help" id="chooseContent" label="choose-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type="radio" value="<%= false %>" />

							<ul class="hide" id="<portlet:namespace />selectContents">
								<li>
									<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= true %>" />

									<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= true %>" />

									<div class="hide" id="<portlet:namespace />range">
										<ul class="lfr-tree unstyled">
											<li class="tree-item">
												<aui:fieldset cssClass="portlet-data-section" label="date-range">
													<aui:input checked="<%= true %>" id="rangeAll" label="all" name="range" type="radio" value="all" />

													<aui:input id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="fromLastPublishDate" />

													<aui:input helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="dateRange" />

													<%
													Calendar today = CalendarFactoryUtil.getCalendar(timeZone, locale);

													Calendar yesterday = CalendarFactoryUtil.getCalendar(timeZone, locale);

													yesterday.add(Calendar.DATE, -1);
													%>

													<ul class="date-range-options hide unstyled" id="<portlet:namespace />startEndDate">
														<li>
															<aui:fieldset label="start-date">
																<liferay-ui:input-date
																	dayParam="startDateDay"
																	dayValue="<%= yesterday.get(Calendar.DATE) %>"
																	disabled="<%= false %>"
																	firstDayOfWeek="<%= yesterday.getFirstDayOfWeek() - 1 %>"
																	monthParam="startDateMonth"
																	monthValue="<%= yesterday.get(Calendar.MONTH) %>"
																	yearParam="startDateYear"
																	yearRangeEnd="<%= yesterday.get(Calendar.YEAR) %>"
																	yearRangeStart="<%= yesterday.get(Calendar.YEAR) - 100 %>"
																	yearValue="<%= yesterday.get(Calendar.YEAR) %>"
																/>

																&nbsp;

																<liferay-ui:input-time
																	amPmParam='<%= "startDateAmPm" %>'
																	amPmValue="<%= yesterday.get(Calendar.AM_PM) %>"
																	disabled="<%= false %>"
																	hourParam='<%= "startDateHour" %>'
																	hourValue="<%= yesterday.get(Calendar.HOUR) %>"
																	minuteInterval="<%= 1 %>"
																	minuteParam='<%= "startDateMinute" %>'
																	minuteValue="<%= yesterday.get(Calendar.MINUTE) %>"
																/>
															</aui:fieldset>
														</li>

														<li>
															<aui:fieldset label="end-date">
																<liferay-ui:input-date
																	dayParam="endDateDay"
																	dayValue="<%= today.get(Calendar.DATE) %>"
																	disabled="<%= false %>"
																	firstDayOfWeek="<%= today.getFirstDayOfWeek() - 1 %>"
																	monthParam="endDateMonth"
																	monthValue="<%= today.get(Calendar.MONTH) %>"
																	yearParam="endDateYear"
																	yearRangeEnd="<%= today.get(Calendar.YEAR) %>"
																	yearRangeStart="<%= today.get(Calendar.YEAR) - 100 %>"
																	yearValue="<%= today.get(Calendar.YEAR) %>"
																/>

																&nbsp;

																<liferay-ui:input-time
																	amPmParam='<%= "endDateAmPm" %>'
																	amPmValue="<%= today.get(Calendar.AM_PM) %>"
																	disabled="<%= false %>"
																	hourParam='<%= "endDateHour" %>'
																	hourValue="<%= today.get(Calendar.HOUR) %>"
																	minuteInterval="<%= 1 %>"
																	minuteParam='<%= "endDateMinute" %>'
																	minuteValue="<%= today.get(Calendar.MINUTE) %>"
																/>
															</aui:fieldset>
														</li>
													</ul>

													<aui:input id="rangeLast" inlineField="<%= true %>" label="last" name="range" type="radio" value="last" />

													<aui:select inlineField="<%= true %>" label="" name="last">
														<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "12") %>' value="12" />
														<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "24") %>' value="24" />
														<aui:option label='<%= LanguageUtil.format(pageContext, "x-hours", "48") %>' value="48" />
														<aui:option label='<%= LanguageUtil.format(pageContext, "x-days", "7") %>' value="168" />
													</aui:select>
												</aui:fieldset>
											</li>
										</ul>
									</div>

									<liferay-util:buffer var="selectedLabels">
										<div class="selected-labels" id="<portlet:namespace />selectedRange"></div>
	
										<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
									</liferay-util:buffer>

									<liferay-ui:icon
										image="calendar"
										label="<%= true %>"
										message='<%= LanguageUtil.get(locale, "date-range") + selectedLabels %>'
									/>
								</li>

								<li class="export-import-content">
									<aui:input helpMessage="export-import-categories-help" label="categories" name="<%= PortletDataHandlerKeys.CATEGORIES %>" type="checkbox" value="<%= true %>" />

									<%
									Set<String> displayedControls = new HashSet<String>();
									Set<String> portletDataHandlerClasses = new HashSet<String>();

									PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(themeDisplay, startDate, endDate);

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

										ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

										long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);
									%>

										<c:if test="<%= exportModelCount != 0 %>">
											<liferay-util:buffer var="count">
												<span class="count-display"><%= exportModelCount > 0 ? exportModelCount : StringPool.BLANK %></span>
											</liferay-util:buffer>

											<aui:input checked="<%= portletDataHandler.isPublishToLiveByDefault() %>" label='<%= portletTitle + count %>' name="<%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + portlet.getPortletId() %>" type="checkbox" />

											<%
											PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
											PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

											if (Validator.isNotNull(exportControls) || Validator.isNotNull(metadataControls)) {
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

																	<aui:field-wrapper label='<%= Validator.isNotNull(metadataControls) ? "content" : StringPool.BLANK %>'>
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

																		if ((childrenControls != null) && (childrenControls.length > 0)) {
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
														<div class="selected-labels" id="<portlet:namespace />selectedContent_<%= portlet.getPortletId() %>"></div>

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

										</c:if>

									<%
									}
									%>

									<aui:fieldset cssClass="comments-and-ratings" label="for-each-of-the-selected-content-types,-export-their">
										<div class="selected-labels" id="<portlet:namespace />selectedCommentsAndRatings"></div>

										<aui:a cssClass="modify-link" href="javascript:;" id="commentsAndRatingsLink" label="change" method="get" />

										<div class="hide" id="<portlet:namespace />commentsAndRatings">
											<ul class="lfr-tree unstyled">
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
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			archivedSetupsNode: '#<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>Checkbox',
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			layoutSetSettingsNode: '#<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>Checkbox',
			logoNode: '#<%= PortletDataHandlerKeys.LOGO %>Checkbox',
			namespace: '<portlet:namespace />',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
			ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>Checkbox',
			setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>Checkbox',
			themeNode: '#<%= PortletDataHandlerKeys.THEME %>Checkbox',
			themeReferenceNode: '#<%= PortletDataHandlerKeys.THEME_REFERENCE %>Checkbox',
			userPreferencesNode: '#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>Checkbox'
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
	Liferay.Util.toggleRadio('<portlet:namespace />chooseApplications', '<portlet:namespace />selectApplications', ['<portlet:namespace />showChangeGlobalConfiguration']);
	Liferay.Util.toggleRadio('<portlet:namespace />allApplications', '<portlet:namespace />showChangeGlobalConfiguration', ['<portlet:namespace />selectApplications']);

	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '', ['<portlet:namespace />startEndDate']);

	Liferay.Util.toggleRadio('<portlet:namespace />chooseContent', '<portlet:namespace />selectContents', ['<portlet:namespace />showChangeGlobalContent']);
	Liferay.Util.toggleRadio('<portlet:namespace />allContent', '<portlet:namespace />showChangeGlobalContent', ['<portlet:namespace />selectContents']);
</aui:script>