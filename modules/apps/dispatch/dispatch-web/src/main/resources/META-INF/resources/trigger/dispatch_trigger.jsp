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

<%
DispatchTrigger dispatchTrigger = (DispatchTrigger)request.getAttribute(DispatchWebKeys.DISPATCH_TRIGGER);

Date endDate = (dispatchTrigger.getEndDate() == null) ? new Date() : dispatchTrigger.getEndDate();

Date startDate = (dispatchTrigger.getStartDate() == null) ? new Date() : dispatchTrigger.getStartDate();

Calendar endDateCalendar = CalendarFactoryUtil.getCalendar(endDate.getTime());

Calendar startDateCalendar = CalendarFactoryUtil.getCalendar(startDate.getTime());

int endDateAmPm = endDateCalendar.get(Calendar.AM_PM);
int endDateDay = endDateCalendar.get(Calendar.DATE);
int endDateHour = endDateCalendar.get(Calendar.HOUR);
int endDateMinute = endDateCalendar.get(Calendar.MINUTE);
int endDateMonth = endDateCalendar.get(Calendar.MONTH);
int endDateYear = endDateCalendar.get(Calendar.YEAR);

int startDateAmPm = startDateCalendar.get(Calendar.AM_PM);
int startDateDay = startDateCalendar.get(Calendar.DATE);
int startDateHour = startDateCalendar.get(Calendar.HOUR);
int startDateMinute = startDateCalendar.get(Calendar.MINUTE);
int startDateMonth = startDateCalendar.get(Calendar.MONTH);
int startDateYear = startDateCalendar.get(Calendar.YEAR);

boolean neverEnd = ParamUtil.getBoolean(request, "neverEnd", true);

if ((dispatchTrigger != null) && (dispatchTrigger.getEndDate() != null)) {
	neverEnd = false;
}
%>

<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="editDispatchTriggerActionURL" />

<aui:form action="<%= editDispatchTriggerActionURL %>" cssClass="container-fluid container-fluid-max-xl container-form-lg" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="schedule" />
	<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

			<div class="lfr-form-content">
				<aui:fieldset>
					<aui:input name="active" />

					<c:choose>
						<c:when test="<%= ClusterExecutorUtil.isEnabled() %>">
							<aui:select label="task-execution-cluster-mode" name="dispatchTaskClusterMode">

								<%
								for (DispatchTaskClusterMode dispatchTaskClusterMode : DispatchTaskClusterMode.values()) {
									if (dispatchTaskClusterMode == DispatchTaskClusterMode.NOT_APPLICABLE) {
										continue;
									}
								%>

									<aui:option label="<%= dispatchTaskClusterMode.getLabel() %>" selected="<%= dispatchTrigger.getDispatchTaskClusterMode() == dispatchTaskClusterMode.getMode() %>" value="<%= dispatchTaskClusterMode.getMode() %>" />

								<%
								}
								%>

							</aui:select>
						</c:when>
						<c:otherwise>
							<aui:select disabled="<%= true %>" helpMessage="this-option-is-enabled-only-in-a-clustered-environment" label="task-execution-cluster-mode" name="dispatchTaskClusterMode">
								<aui:option label="<%= DispatchTaskClusterMode.NOT_APPLICABLE.getLabel() %>" />
							</aui:select>
						</c:otherwise>
					</c:choose>

					<aui:input name="overlapAllowed" />

					<aui:input name="cronExpression" />

					<aui:field-wrapper label="start-date">
						<liferay-ui:input-date
							dayParam="startDateDay"
							dayValue="<%= startDateDay %>"
							monthParam="startDateMonth"
							monthValue="<%= startDateMonth %>"
							yearParam="startDateYear"
							yearValue="<%= startDateYear %>"
						/>

						<liferay-ui:icon
							icon="calendar"
							markupView="lexicon"
						/>

						<liferay-ui:input-time
							amPmParam="startDateAmPm"
							amPmValue="<%= startDateAmPm %>"
							hourParam="startDateHour"
							hourValue="<%= startDateHour %>"
							minuteParam="startDateMinute"
							minuteValue="<%= startDateMinute %>"
						/>
					</aui:field-wrapper>

					<aui:field-wrapper label="end-date">
						<aui:input name="neverEnd" onClick='<%= liferayPortletResponse.getNamespace() + "updateEndDateTimeInputsDisabled(this.checked);" %>' type="checkbox" value="<%= neverEnd %>" />

						<span class="end-date-input-selector">
							<liferay-ui:input-date
								dayParam="endDateDay"
								dayValue="<%= endDateDay %>"
								disabled="<%= neverEnd %>"
								monthParam="endDateMonth"
								monthValue="<%= endDateMonth %>"
								yearParam="endDateYear"
								yearValue="<%= endDateYear %>"
							/>
						</span>

						<liferay-ui:icon
							icon="calendar"
							markupView="lexicon"
						/>

						<span class="end-time-input-selector">
							<liferay-ui:input-time
								amPmParam="endDateAmPm"
								amPmValue="<%= endDateAmPm %>"
								disabled="<%= neverEnd %>"
								hourParam="endDateHour"
								hourValue="<%= endDateHour %>"
								minuteParam="endDateMinute"
								minuteValue="<%= endDateMinute %>"
							/>
						</span>
					</aui:field-wrapper>
				</aui:fieldset>

				<div class="sheet-footer">
					<aui:button type="submit" value="save" />

					<aui:button href="<%= backURL %>" type="cancel" />
				</div>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateEndDateTimeInputsDisabled',
		function (checked) {
			document
				.querySelectorAll(
					'.end-date-input-selector input, .end-time-input-selector input'
				)
				.forEach(function (input) {
					input.disabled = checked;
				});
		}
	);
</aui:script>