<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
Definition definition = (Definition)request.getAttribute(ReportsEngineWebKeys.DEFINITION);

long definitionId = BeanParamUtil.getLong(definition, request, "definitionId");

String reportName = BeanParamUtil.getString(definition, request, "reportName");

portletDisplay.setShowBackIcon(true);

PortletURL searchDefinitionURL = reportsEngineDisplayContext.getPortletURL();

searchDefinitionURL.setParameter("mvcPath", "/admin/view.jsp");
searchDefinitionURL.setParameter("tabs1", "definitions");

portletDisplay.setURLBack(searchDefinitionURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "new-report-entry"));
%>

<portlet:renderURL var="searchRequestsURL">
	<portlet:param name="mvcPath" value="/admin/view.jsp" />
	<portlet:param name="tabs1" value="reports" />
</portlet:renderURL>

<portlet:actionURL name="addScheduler" var="addSchedulerURL">
	<portlet:param name="mvcPath" value="/admin/report/edit_schedule.jsp" />
	<portlet:param name="redirect" value="<%= searchRequestsURL %>" />
</portlet:actionURL>

<aui:form action="<%= addSchedulerURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="definitionId" type="hidden" value="<%= definition.getDefinitionId() %>" />

	<portlet:renderURL var="generatedReportsURL">
		<portlet:param name="mvcPath" value="/admin/report/requested_report_detail.jsp" />
	</portlet:renderURL>

	<aui:input name="generatedReportsURL" type="hidden" value="<%= generatedReportsURL %>" />

	<liferay-ui:error exception="<%= DefinitionNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= EntryEmailDeliveryException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= EntryEmailNotificationsException.class %>" message="please-enter-a-valid-email-address" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="reportName" value="<%= reportName %>" />

			<aui:select label="report-format" name="format">

				<%
				for (ReportFormat reportFormat : ReportFormat.values()) {
				%>

					<aui:option label="<%= reportFormat.getValue() %>" value="<%= reportFormat.getValue() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="email-notifications" name="emailNotifications" type="text" />

			<aui:input label="email-recipient" name="emailDelivery" type="text" />
		</aui:fieldset>

		<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="schedule">
			<liferay-ui:input-scheduler />
		</aui:fieldset>

		<%
		JSONArray reportParametersJSONArray = JSONFactoryUtil.createJSONArray(definition.getReportParameters());
		%>

		<c:if test="<%= reportParametersJSONArray.length() > 0 %>">
			<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="report-parameters">

				<%
				for (int i = 0; i < reportParametersJSONArray.length(); i++) {
					JSONObject reportParameterJSONObject = reportParametersJSONArray.getJSONObject(i);

					String key = reportParameterJSONObject.getString("key");
					String type = reportParameterJSONObject.getString("type");
					String value = reportParameterJSONObject.getString("value");

					String keyJSId = StringUtil.randomId();
				%>

					<aui:row>
						<c:choose>
							<c:when test='<%= type.equals("date") %>'>
								<aui:col width="<%= 20 %>">
									<aui:field-wrapper helpMessage="entry-report-date-parameters-help" label="<%= HtmlUtil.escape(key) %>" />
								</aui:col>

								<aui:col width="<%= 30 %>">

									<%
									Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

									String[] date = value.split("-");

									calendar.set(Calendar.YEAR, GetterUtil.getInteger(date[0]));
									calendar.set(Calendar.MONTH, GetterUtil.getInteger(date[1]) - 1);
									calendar.set(Calendar.DATE, GetterUtil.getInteger(date[2]));
									%>

									<liferay-ui:input-date
										dayParam='<%= key + "Day" %>'
										dayValue="<%= calendar.get(Calendar.DATE) %>"
										disabled="<%= false %>"
										firstDayOfWeek="<%= calendar.getFirstDayOfWeek() - 1 %>"
										monthParam='<%= key + "Month" %>'
										monthValue="<%= calendar.get(Calendar.MONTH) %>"
										yearParam='<%= key +"Year" %>'
										yearValue="<%= calendar.get(Calendar.YEAR) %>"
									/>
								</aui:col>

								<aui:col width="<%= 50 %>">
									<aui:select label="" name='<%= "useVariable" + HtmlUtil.escapeAttribute(key) %>' onChange='<%= "useVariable" + keyJSId + "();" %>' showEmptyOption="<%= true %>">
										<aui:option label="start-date" value="startDate" />
										<aui:option label="end-date" value="endDate" />
									</aui:select>

									<script type="text/javascript">
										function useVariable<%= keyJSId %>() {
											var A = AUI();

											var type = A.one(
												'#<%= renderResponse.getNamespace() + "useVariable" + HtmlUtil.escapeJS(key) %>'
											).get('value');
											var day = A.one(
												'#<%= renderResponse.getNamespace() + HtmlUtil.escapeJS(key) + "Day" %>'
											);
											var month = A.one(
												'#<%= renderResponse.getNamespace() + HtmlUtil.escapeJS(key) + "Month" %>'
											);
											var year = A.one(
												'#<%= renderResponse.getNamespace() + HtmlUtil.escapeJS(key) + "Year" %>'
											);

											if (type == 'startDate' || type == 'endDate') {
												day.attr('disabled', 'disabled');
												month.attr('disabled', 'disabled');
												year.attr('disabled', 'disabled');

												if (type == 'endDate') {
													document.<portlet:namespace />fm.<portlet:namespace />endDateType[1].checked =
														'true';
												}
											}
											else {
												day.attr('disabled', '');
												month.attr('disabled', '');
												year.attr('disabled', '');
											}
										}
									</script>
								</aui:col>
							</c:when>
							<c:otherwise>
								<aui:col width="<%= 20 %>">
									<%= HtmlUtil.escape(key) %>
								</aui:col>

								<aui:col width="<%= 80 %>">
									<span class="field field-text" id="aui_3_2_0_1428">
										<input class="form-control" name="<portlet:namespace /><%= "parameterValue" + HtmlUtil.escapeAttribute(key) %>" type="text" value="<%= HtmlUtil.escapeAttribute(value) %>" /><br />
									</span>
								</aui:col>
							</c:otherwise>
						</c:choose>
					</aui:row>

				<%
				}
				%>

			</aui:fieldset>

		</c:if>

		<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
			<liferay-ui:input-permissions
				modelName="<%= Entry.class.getName() %>"
			/>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="schedule" />

		<aui:button cssClass="btn-lg" href="<%= searchDefinitionURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>