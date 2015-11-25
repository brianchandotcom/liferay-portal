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
	<aui:fieldset cssClass="options-group" label="content">
		<ul class="lfr-tree list-unstyled">
			<li class="tree-item">
				<aui:input disabled="<%= disableInputs %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA, true) %>" />
				<aui:input checked="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" disabled="<%= disableInputs %>" helpMessage='<%= type.equals(Constants.EXPORT) ? "all-content-export-help" : "all-content-publish-help" %>' id="allContent" label="all-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type='<%= disableInputs ? "hidden" : "radio" %>' value="<%= true %>" />

				<aui:input disabled="<%= disableInputs %>" name="<%= PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT %>" type="hidden" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true) %>" />
				<aui:input checked="<%= !MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL, true) %>" disabled="<%= disableInputs %>" helpMessage='<%= type.equals(Constants.EXPORT) ? "choose-content-export-help" : "choose-content-publish-help" %>' id="chooseContent" label="choose-content" name="<%= PortletDataHandlerKeys.PORTLET_DATA_ALL %>" type='<%= disableInputs ? "hidden" : "radio" %>' value="<%= false %>" />

				<ul class='<%= disableInputs ? "select-options" : "hide select-options" %>' id="<portlet:namespace />selectContents">
					<li>
						<div class="hide" id="<portlet:namespace />range">
							<ul class="lfr-tree list-unstyled">
								<li class="tree-item">
									<aui:fieldset cssClass="portlet-data-section" label="date-range">

										<%
										String selectedRange = MapUtil.getString(parameterMap, "range", defaultRange);
										%>

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_ALL) %>" disabled="<%= disableInputs %>" id="rangeAll" label="all" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_ALL %>" />

										<c:if test="<%= !type.equals(Constants.EXPORT) %>">
											<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE) %>" disabled="<%= disableInputs %>"  id="rangeLastPublish" label="from-last-publish-date" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE %>" />
										</c:if>

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_DATE_RANGE) %>" disabled="<%= disableInputs %>" helpMessage="export-date-range-help" id="rangeDateRange" label="date-range" name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_DATE_RANGE %>" />

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
														lastEnabledDate="<%= (!cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? null : new Date() %>"
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
														lastEnabledDate="<%= (!cmd.equals(Constants.PUBLISH_TO_LIVE) && !cmd.equals(Constants.PUBLISH_TO_REMOTE)) ? null : new Date() %>"
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
														dateParam="endDateTime"
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

										<aui:input checked="<%= selectedRange.equals(ExportImportDateUtil.RANGE_LAST) %>" id="rangeLast" label='<%= LanguageUtil.get(request, "last") + StringPool.TRIPLE_PERIOD %>' name="range" type="radio" value="<%= ExportImportDateUtil.RANGE_LAST %>" />

										<ul class="hide list-unstyled" id="<portlet:namespace />rangeLastInputs">
											<li>
												<aui:select cssClass="relative-range" label="" name="last">

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

						<liferay-util:buffer var="selectedLabelsHTML">
							<span class="selected-labels" id="<portlet:namespace />selectedRange"></span>

							<span <%= !disableInputs ? StringPool.BLANK : "class=\"hide\"" %>>
								<aui:a cssClass="modify-link" href="javascript:;" id="rangeLink" label="change" method="get" />
							</span>
						</liferay-util:buffer>

						<liferay-ui:icon
							iconCssClass="icon-calendar"
							label="<%= true %>"
							message='<%= LanguageUtil.get(request, "date-range") + selectedLabelsHTML %>'
						/>
					</li>

					<li class="options">
						<liferay-staging:portlet-list disableInputs="<%= disableInputs %>" parameterMap="<%= parameterMap %>" portlets="<%= dataSiteLevelPortlets %>" type="<%= type %>" />
					</li>
				</ul>
			</li>
		</ul>
	</aui:fieldset>
</c:if>

<c:if test="<%= !disableInputs %>">
	<aui:script>
		Liferay.Util.toggleRadio('<portlet:namespace />chooseContent', '<portlet:namespace />selectContents');
		Liferay.Util.toggleRadio('<portlet:namespace />allContent', null, ['<portlet:namespace />selectContents']);
	</aui:script>
</c:if>