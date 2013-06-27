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
%>

<portlet:actionURL var="exportPortletURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
</portlet:actionURL>

<aui:form action='<%= exportPortletURL + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="fm1">
	<aui:input name="tabs1" type="hidden" value="export_import" />
	<aui:input name="tabs2" type="hidden" value="export" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="plid" type="hidden" value="<%= exportableLayout.getPlid() %>" />
	<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EXPORT %>" />

	<div class="export-dialog-tree">
		<aui:input label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" size="50" value='<%= StringUtil.replace(selPortlet.getDisplayName(), " ", "_") + "-" + Time.getShortTimestamp() + ".portlet.lar" %>' />

		<%
		PortletDataHandler portletDataHandler = selPortlet.getPortletDataHandlerInstance();

		PortletDataHandlerControl[] configurationControls = null;

		if (portletDataHandler != null) {
			configurationControls = portletDataHandler.getExportConfigurationControls(company.getCompanyId(), themeDisplay.getScopeGroupId(), selPortlet, exportableLayout.getPlid(), false);
		}
		%>

		<c:if test="<%= Validator.isNotNull(configurationControls) %>">
			<aui:fieldset cssClass="options-group" label="application">
				<ul class="lfr-tree unstyled">
					<li class="tree-item">
						<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION %>" type="hidden" value="<%= true %>" />

						<aui:input label="configuration" name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>" type="checkbox" value="<%= true %>" />

						<div class="hide" id="<portlet:namespace />configuration_<%= selPortlet.getRootPortletId() %>">
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
						</div>

						<ul class="hide" id="<portlet:namespace />showChangeConfiguration_<%= selPortlet.getRootPortletId() %>">
							<li>
								<div class="selected-labels" id="<portlet:namespace />selectedConfiguration_<%= selPortlet.getRootPortletId() %>"></div>

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
			</aui:fieldset>
		</c:if>

		<c:if test="<%= portletDataHandler != null %>">

			<%
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

			PortletDataContext portletDataContext = PortletDataContextFactoryUtil.createPreparePortletDataContext(themeDisplay, startDate, endDate);

			portletDataHandler.prepareManifestSummary(portletDataContext);

			ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

			long exportModelCount = portletDataHandler.getExportModelCount(manifestSummary);
			%>

			<c:if test="<%= (exportModelCount > 0) || (startDate != null) || (endDate != null) %>">
				<aui:fieldset cssClass="options-group" label="content">
					<ul class="lfr-tree unstyled">
						<li class="tree-item">
							<div class="hide" id="<portlet:namespace />range">
								<aui:fieldset cssClass="portlet-data-section" label="date-range">
									<aui:input data-name='<%= LanguageUtil.get(pageContext, "all") %>' id="rangeAll" label="all" name="range" type="radio" value="all" />

									<aui:input checked="<%= true %>" data-name='<%= LanguageUtil.get(pageContext, "from-last-publish-date") %>' id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="fromLastPublishDate" />

									<aui:input data-name='<%= LanguageUtil.get(pageContext, "date-range") %>' helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="dateRange" />

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
													minuteParam='<%= "endDateMinute" %>'
													minuteValue="<%= today.get(Calendar.MINUTE) %>"
												/>
											</aui:fieldset>
										</li>
									</ul>
								</aui:fieldset>
							</div>

							<liferay-ui:icon
								image="calendar"
								label="<%= true %>"
								message="date-range"
							/>

							<ul>
								<li>
									<div class="selected-labels" id="<portlet:namespace />selectedRange"></div>

									<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
								</li>
							</ul>
						</li>

						<c:if test="<%= exportModelCount != 0 %>">
							<li class="tree-item">
								<aui:input name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= false %>" />

								<aui:input label='<%= LanguageUtil.get(pageContext, "content") + (exportModelCount > 0 ? " (" + exportModelCount + ")" : StringPool.BLANK) %>' name='<%= PortletDataHandlerKeys.PORTLET_DATA + "_" + selPortlet.getRootPortletId() %>' type="checkbox" value="<%= portletDataHandler.isPublishToLiveByDefault() %>" />

								<%
								PortletDataHandlerControl[] exportControls = portletDataHandler.getExportControls();
								PortletDataHandlerControl[] metadataControls = portletDataHandler.getExportMetadataControls();

								if (Validator.isNotNull(exportControls) || Validator.isNotNull(metadataControls)) {
								%>

									<div class="hide" id="<portlet:namespace />content_<%= selPortlet.getRootPortletId() %>">
										<c:if test="<%= exportControls != null %>">

											<%
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
										</c:if>

										<c:if test="<%= metadataControls != null %>">

											<%
											for (PortletDataHandlerControl metadataControl : metadataControls) {
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
											%>

										</c:if>
									</div>

									<ul id="<portlet:namespace />showChangeContent">
										<li>
											<div class="selected-labels" id="<portlet:namespace />selectedContent_<%= selPortlet.getRootPortletId() %>"></div>

											<%
											Map<String,Object> data = new HashMap<String,Object>();

											data.put("portletid", selPortlet.getRootPortletId());
											%>

											<aui:a cssClass="content-link modify-link" data="<%= data %>" href="javascript:;" id='<%= "contentLink_" + selPortlet.getRootPortletId() %>' label="change" method="get" />
										</li>
									</ul>

									<aui:script>
										Liferay.Util.toggleBoxes('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE + selPortlet.getRootPortletId() %>Checkbox', '<portlet:namespace />showChangeContent');
									</aui:script>

								<%
								}
								%>

							</li>

							<li>
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

<aui:script use="liferay-export-import">
	new Liferay.ExportImport(
		{
			commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>Checkbox',
			form: document.<portlet:namespace />fm1,
			namespace: '<portlet:namespace />',
			rangeAllNode: '#rangeAll',
			rangeDateRangeNode: '#rangeDateRange',
			rangeLastNode: '#rangeLast',
			rangeLastPublishNode: '#rangeLastPublish',
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

	Liferay.Util.toggleRadio('<portlet:namespace />rangeDateRange', '<portlet:namespace />startEndDate');
	Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', ['<portlet:namespace />startEndDate']);
	Liferay.Util.toggleRadio('<portlet:namespace />rangeLast', '', ['<portlet:namespace />startEndDate']);
</aui:script>