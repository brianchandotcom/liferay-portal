<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchTrigger dispatchTrigger = (DispatchTrigger)request.getAttribute(DispatchWebKeys.DISPATCH_TRIGGER);

Date timeZoneEndDate = (dispatchTrigger.getTimeZoneEndDate() == null) ? new Date() : dispatchTrigger.getTimeZoneEndDate();

Date timeZoneStartDate = (dispatchTrigger.getTimeZoneStartDate() == null) ? new Date() : dispatchTrigger.getTimeZoneStartDate();

Calendar endDateCalendar = CalendarFactoryUtil.getCalendar();

endDateCalendar.setTime(timeZoneEndDate);

Calendar startDateCalendar = CalendarFactoryUtil.getCalendar();

startDateCalendar.setTime(timeZoneStartDate);

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
boolean dispatchTaskExecutorReady = true;

DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

if (dispatchTrigger != null) {
	if (dispatchTrigger.getEndDate() != null) {
		neverEnd = false;
	}

	DispatchTriggerMetadata dispatchTriggerMetadata = dispatchTriggerDisplayContext.getDispatchTriggerMetadata(dispatchTrigger.getDispatchTriggerId());

	dispatchTaskExecutorReady = dispatchTriggerMetadata.isDispatchTaskExecutorReady();
}
%>

<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="editDispatchTriggerActionURL" />

<aui:form action="<%= editDispatchTriggerActionURL %>" cssClass="container-fluid container-fluid-max-xl container-form-lg" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="schedule" />
	<aui:input name="dispatchTriggerId" type="hidden" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />

	<div class="alert alert-warning <%= dispatchTaskExecutorReady ? "hide":"" %>">
		<liferay-ui:message key="task-executor-is-not-ready" />
	</div>

	<div class="sheet">
		<div class="panel-group panel-group-flush">
			<aui:fieldset disabled="<%= !dispatchTaskExecutorReady %>">
				<aui:model-context bean="<%= dispatchTrigger %>" model="<%= DispatchTrigger.class %>" />

				<div class="lfr-form-content">
					<aui:fieldset>
						<aui:input name="active" />

						<c:choose>
							<c:when test="<%= ClusterExecutorUtil.isEnabled() && !dispatchTriggerDisplayContext.isClusterModeSingle(dispatchTrigger.getDispatchTaskExecutorType()) %>">
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

								<%
								DispatchTaskClusterMode dispatchTaskClusterMode = DispatchTaskClusterMode.NOT_APPLICABLE;

								if (dispatchTriggerDisplayContext.isClusterModeSingle(dispatchTrigger.getDispatchTaskExecutorType())) {
									dispatchTaskClusterMode = DispatchTaskClusterMode.SINGLE_NODE_PERSISTED;
								}
								%>

								<aui:select disabled="<%= true %>" helpMessage="this-option-is-enabled-only-in-a-clustered-environment" label="task-execution-cluster-mode" name="dispatchTaskClusterMode">
									<aui:option label="<%= dispatchTaskClusterMode.getLabel() %>" />
								</aui:select>
							</c:otherwise>
						</c:choose>

						<aui:input name="overlapAllowed" />

						<aui:select label="time-zone" name="timeZoneId">

							<%
							String dispatchTriggerTimeZoneId = dispatchTrigger.getTimeZoneId();

							for (String timeZoneId : PropsUtil.getArray(PropsKeys.TIME_ZONES)) {
							%>

								<aui:option label="<%= timeZoneId %>" selected='<%= dispatchTriggerTimeZoneId.isEmpty() ? timeZoneId.equals("UTC") : dispatchTriggerTimeZoneId.equals(timeZoneId) %>' value="<%= timeZoneId %>" />

							<%
							}
							%>

						</aui:select>

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
		</div>
	</div>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />updateEndDateTimeInputsDisabled',
		(checked) => {
			document
				.querySelectorAll(
					'.end-date-input-selector input, .end-time-input-selector input'
				)
				.forEach((input) => {
					input.disabled = checked;
				});
		}
	);
</aui:script>