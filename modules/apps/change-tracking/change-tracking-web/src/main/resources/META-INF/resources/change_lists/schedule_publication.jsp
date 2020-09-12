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
SchedulePublicationDisplayContext schedulePublicationDisplayContext = new SchedulePublicationDisplayContext(request, liferayPortletResponse);

CTCollection ctCollection = schedulePublicationDisplayContext.getCTCollection();

portletDisplay.setURLBack(schedulePublicationDisplayContext.getRedirect());
portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(schedulePublicationDisplayContext.getTitle());
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<aui:form action="<%= schedulePublicationDisplayContext.getSubmitURL() %>" method="post" name="schedulePublicationFm">
		<aui:input name="ctCollectionId" type="hidden" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />

		<div class="sheet-lg table-responsive">
			<table class="table table-autofit table-list">
				<c:if test="<%= !schedulePublicationDisplayContext.isScheduled() %>">
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

							<%
							Calendar calendar = schedulePublicationDisplayContext.getCalendar();
							%>

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
									<span class="change-lists-time-zone"><h5 class="text-secondary"><%= schedulePublicationDisplayContext.getTimeZoneDisplay() %></h5></span>
								</div>
							</div>
						</aui:field-wrapper>
					</td>
				</tr>
				<tr><td id="changeListsFooter">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<span>
								<aui:button href="<%= schedulePublicationDisplayContext.getRedirect() %>" type="cancel" />
							</span>
						</div>

						<c:choose>
							<c:when test="<%= schedulePublicationDisplayContext.isScheduled() %>">
								<div class="autofit-col">
									<span>
										<liferay-portlet:actionURL name="/change_lists/unschedule_publication" var="unscheduleURL">
											<portlet:param name="redirect" value="<%= schedulePublicationDisplayContext.getRedirect() %>" />
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
											<portlet:param name="redirect" value="<%= schedulePublicationDisplayContext.getRedirect() %>" />
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