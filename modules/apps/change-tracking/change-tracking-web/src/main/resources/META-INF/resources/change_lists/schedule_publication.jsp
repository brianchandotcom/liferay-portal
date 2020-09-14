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

<%@ include file="/change_lists/init.jsp" %>

<%
boolean scheduled = ParamUtil.getBoolean(request, "scheduled");

Calendar calendar = null;
CTCollection ctCollection = null;

SchedulePublicationDisplayContext schedulePublicationDisplayContext = (SchedulePublicationDisplayContext)request.getAttribute(CTWebKeys.SCHEDULE_PUBLICATION_DISPLAY_CONTEXT);

if (schedulePublicationDisplayContext != null) {
	calendar = schedulePublicationDisplayContext.getCalendar();
	ctCollection = schedulePublicationDisplayContext.getCTCollection();
	scheduled = schedulePublicationDisplayContext.isScheduled();
}
else {
	int day = ParamUtil.getInteger(request, "publishTimeDay");
	int minute = ParamUtil.getInteger(request, "publishTimeMinute");
	int month = ParamUtil.getInteger(request, "publishTimeMonth");
	int year = ParamUtil.getInteger(request, "publishTimeYear");

	int hour = ParamUtil.getInteger(request, "publishTimeHour");

	if (ParamUtil.getInteger(request, "publishTimeAmPm") == Calendar.PM) {
		hour += 12;
	}

	calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

	calendar.setTime(PortalUtil.getDate(month, day, year, hour, minute, timeZone, PortalException.class));

	long ctCollectionId = ParamUtil.getLong(request, "ctCollectionId");

	ctCollection = CTCollectionLocalServiceUtil.getCTCollection(ctCollectionId);
}

String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setURLBack(redirect);

portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, scheduled ? "reschedule" : "schedule-to-publish-later"), ": ", ctCollection.getName()));
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<liferay-portlet:actionURL name='<%= scheduled ? "/change_lists/reschedule_publication" : "/change_lists/schedule_publication" %>' var="submitURL">
		<portlet:param name="redirect" value="<%= redirect %>" />
	</liferay-portlet:actionURL>

	<aui:form action="<%= submitURL %>" method="post" name="schedulePublicationFm">
		<aui:input name="ctCollectionId" type="hidden" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		<aui:input name="scheduled" type="hidden" value="<%= String.valueOf(scheduled) %>" />

		<div class="sheet-lg table-responsive">
			<table class="table table-autofit table-list">
				<c:if test="<%= !scheduled %>">
					<tr>
						<td class="text-muted" id="changeListsHeader">
							<div class="autofit-row">
								<div class="autofit-col autofit-col-expand">
									<span>
										<h5>
											<liferay-ui:message key="schedule-publication" />
										</h5>
									</span>
											</div>

											<div class="autofit-col">
									<span>
										<h5><liferay-ui:message arguments="<%= new Object[] {2, 2} %>" key="step-x-of-x" translateArguments="<%= false %>" /></h5>
									</span>
								</div>
							</div>
						</td>
					</tr>
				</c:if>

				<tr>
					<td>
						<liferay-ui:error key="publishTime" message="the-publish-time-must-be-in-the-future" />

						<aui:field-wrapper>
							<label><liferay-ui:message key="date-and-time" /></label>

							<div class="autofit-row">
								<div class="autofit-col" id="changeListsInputDate">
									<liferay-ui:input-date
										dayParam="publishTimeDay"
										dayValue="<%= calendar.get(Calendar.DATE) %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= calendar.getFirstDayOfWeek() - 1 %>"
										monthParam="publishTimeMonth"
										monthValue="<%= calendar.get(Calendar.MONTH) %>"
										name="publishDate"
										yearParam="publishTimeYear"
										yearValue="<%= calendar.get(Calendar.YEAR) %>"
									/>
								</div>

								<div class="autofit-col" id="changeListInputTime">
									<liferay-ui:input-time
										amPmParam="publishTimeAmPm"
										amPmValue="<%= calendar.get(Calendar.AM_PM) %>"
										dateParam="publishDateTime"
										dateValue="<%= calendar.getTime() %>"
										disabled="<%= false %>"
										hourParam="publishTimeHour"
										hourValue="<%= calendar.get(Calendar.HOUR) %>"
										minuteParam="publishTimeMinute"
										minuteValue="<%= calendar.get(Calendar.MINUTE) %>"
										name="publishTime"
									/>
								</div>

								<div class="autofit-col">

									<%
									String timeZoneDisplay = StringPool.OPEN_PARENTHESIS + StringPool.UTC + StringPool.CLOSE_PARENTHESIS;

									if (!Objects.equals(timeZone.getID(), StringPool.UTC)) {
										Instant instant = Instant.now();

										timeZoneDisplay = StringBundler.concat(StringPool.OPEN_PARENTHESIS, timeZone.getDisplayName(false, TimeZone.SHORT, locale), StringPool.SPACE, String.format("%tz", instant.atZone(timeZone.toZoneId())), StringPool.CLOSE_PARENTHESIS);
									}
									%>

									<span class="change-lists-time-zone"><h5 class="text-secondary"><%= timeZoneDisplay %></h5></span>
								</div>
							</div>
						</aui:field-wrapper>
					</td>
				</tr>
				<tr><td id="changeListsFooter">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<span>
								<aui:button href="<%= redirect %>" type="cancel" />
							</span>
						</div>

						<c:choose>
							<c:when test="<%= scheduled %>">
								<div class="autofit-col">
									<span>
										<liferay-portlet:actionURL name="/change_lists/unschedule_publication" var="unscheduleURL">
											<portlet:param name="redirect" value="<%= redirect %>" />
											<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
										</liferay-portlet:actionURL>

										<aui:button href="<%= unscheduleURL %>" type="cancel" value="unschedule" />
									</span>
								</div>
							</c:when>
							<c:otherwise>
								<div class="autofit-col">
									<span>
										<liferay-portlet:renderURL var="conflictsURL">
											<portlet:param name="mvcRenderCommandName" value="/change_lists/view_conflicts" />
											<portlet:param name="redirect" value="<%= redirect %>" />
											<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
											<portlet:param name="schedule" value="<%= Boolean.TRUE.toString() %>" />
										</liferay-portlet:renderURL>

										<aui:button href="<%= conflictsURL %>" type="cancel" value="previous" />
									</span>
								</div>
							</c:otherwise>
						</c:choose>

						<div class="autofit-col">
							<span>
								<aui:button primary="true" type="submit" value="schedule" />
							</span>
						</div>
					</div>
				</td></tr>
			</table>
		</div>
	</aui:form>
</clay:container-fluid>