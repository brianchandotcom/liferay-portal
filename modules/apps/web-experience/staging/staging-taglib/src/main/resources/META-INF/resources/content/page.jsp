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

<%@ include file="/content/init.jsp" %>

<c:if test="<%= !dataSiteLevelPortlets.isEmpty() %>">
	<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="content" markupView="lexicon">
		<ul class="list-unstyled">
			<li class="tree-item">
				<aui:select disabled="<%= disableInputs %>" inlineField="<%= true %>" label="" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>">
					<aui:option id="allContent" label="all-content" selected="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" value="<%= true %>" />
					<aui:option id="chooseContent" label="choose-content" selected="<%= !MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" value="<%= false %>" />
				</aui:select>

				<aui:input disabled="<%= disableInputs %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA, true) %>" />
				<aui:input disabled="<%= disableInputs %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true) %>" />

				<ul class="<%= disableInputs ? "" : "hide" %>" id="<portlet:namespace />selectContents">
					<li>
						<div id="<portlet:namespace />range">
							<ul class="list-unstyled">
								<li class="tree-item">
									<aui:fieldset cssClass="portlet-data-section" label="date-range">
										<div class="flex-container">

											<%
											String selectedRange = MapUtil.getString(parameterMap, "range", defaultRange);
											%>

											<div class="flex-item-center range-options">
												<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_ALL) %>" disabled="<%= disableInputs %>" id="rangeAll" label="all" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_ALL %>" />
											</div>

											<c:if test="<%= !type.equals(Constants.EXPORT) %>">
												<div class="flex-item-center range-options">
													<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE) %>" disabled="<%= disableInputs %>" id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE %>" />
												</div>
											</c:if>

											<div class="flex-item-center range-options">
												<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_DATE_RANGE) %>" disabled="<%= disableInputs %>" helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_DATE_RANGE %>" />
											</div>

											<div class="flex-item-center range-options">
												<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_LAST) %>" disabled="<%= disableInputs %>" id="rangeLast" label='<%= LanguageUtil.get(request, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_LAST %>" />
											</div>

											<div class="flex-item-center range-options <%= disableInputs ? "hide" : StringPool.BLANK %>">
												<liferay-ui:icon icon="reload" markupView="lexicon" />

												<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" method="get">
													<liferay-ui:message key="refresh-counts" />
												</aui:a>
											</div>
										</div>

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
											<li class="flex-container">
												<liferay-ui:input-date
													cssClass="form-group form-group-inline"
													dayParam="startDateDay"
													dayValue="<%= startCalendar.get(Calendar.DATE) %>"
													disabled="<%= disableInputs %>"
													firstDayOfWeek="<%= startCalendar.getFirstDayOfWeek() - 1 %>"
													lastEnabledDate="<%= (!cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? null : new Date() %>"
													monthParam="startDateMonth"
													monthValue="<%= startCalendar.get(Calendar.MONTH) %>"
													name="startDate"
													yearParam="startDateYear"
													yearValue="<%= startCalendar.get(Calendar.YEAR) %>"
												/>

												<liferay-ui:icon icon="calendar" markupView="lexicon" />

												<liferay-ui:input-time
													amPmParam='<%= "startDateAmPm" %>'
													amPmValue="<%= startCalendar.get(Calendar.AM_PM) %>"
													cssClass="form-group form-group-inline range-options"
													dateParam="startDateTime"
													dateValue="<%= startCalendar.getTime() %>"
													disabled="<%= disableInputs %>"
													hourParam='<%= "startDateHour" %>'
													hourValue="<%= startCalendar.get(Calendar.HOUR) %>"
													minuteParam='<%= "startDateMinute" %>'
													minuteValue="<%= startCalendar.get(Calendar.MINUTE) %>"
													name="startTime"
												/>

												<liferay-ui:input-date
													cssClass="form-group form-group-inline"
													dayParam="endDateDay"
													dayValue="<%= endCalendar.get(Calendar.DATE) %>"
													disabled="<%= disableInputs %>"
													firstDayOfWeek="<%= endCalendar.getFirstDayOfWeek() - 1 %>"
													lastEnabledDate="<%= (!cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? null : new Date() %>"
													monthParam="endDateMonth"
													monthValue="<%= endCalendar.get(Calendar.MONTH) %>"
													name="endDate"
													yearParam="endDateYear"
													yearValue="<%= endCalendar.get(Calendar.YEAR) %>"
												/>

												<liferay-ui:icon icon="calendar" markupView="lexicon" />

												<liferay-ui:input-time
													amPmParam='<%= "endDateAmPm" %>'
													amPmValue="<%= endCalendar.get(Calendar.AM_PM) %>"
													cssClass="form-group form-group-inline"
													dateParam="endDateTime"
													dateValue="<%= endCalendar.getTime() %>"
													disabled="<%= disableInputs %>"
													hourParam='<%= "endDateHour" %>'
													hourValue="<%= endCalendar.get(Calendar.HOUR) %>"
													minuteParam='<%= "endDateMinute" %>'
													minuteValue="<%= endCalendar.get(Calendar.MINUTE) %>"
													name="endTime"
												/>
											</li>
										</ul>

										<ul class="hide list-unstyled" id="<portlet:namespace />rangeLastInputs">
											<li>
												<aui:select cssClass="relative-range" disabled="<%= disableInputs %>" label="" name="last">

													<%
													String last = MapUtil.getString(parameterMap, "last");
													%>

													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "12", false) %>' selected='<%= last.equals("12") %>' value="12" />
													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "24", false) %>' selected='<%= last.equals("24") %>' value="24" />
													<aui:option label='<%= LanguageUtil.format(request, "x-hours", "48", false) %>' selected='<%= last.equals("48") %>' value="48" />
													<aui:option label='<%= LanguageUtil.format(request, "x-days", "7", false) %>' selected='<%= last.equals("168") %>' value="168" />
												</aui:select>
											</li>
										</ul>
									</aui:fieldset>
								</li>
							</ul>
						</div>
					</li>
					<li class="options">
						<liferay-staging:portlet-list disableInputs="<%= disableInputs %>" exportImportConfigurationId="<%= exportImportConfigurationId %>" portlets="<%= dataSiteLevelPortlets %>" showAllPortlets="<%= showAllPortlets %>" type="<%= type %>" />
					</li>
				</ul>
			</li>
		</ul>
	</aui:fieldset>
</c:if>

<aui:script>
	Liferay.Util.toggleSelectBox('<portlet:namespace /><%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>', 'false', '<portlet:namespace />selectContents');
</aui:script>