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

<portlet:actionURL name="generateReport" var="generateReportURL">
	<portlet:param name="mvcPath" value="/admin/report/generate_report.jsp" />
	<portlet:param name="redirect" value="<%= searchRequestsURL %>" />
</portlet:actionURL>

<aui:form action="<%= generateReportURL %>" cssClass="container-fluid-1280" method="post" name="fm">
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
				%>

					<aui:row>
						<c:choose>
							<c:when test='<%= type.equals("date") %>'>
								<aui:col width="<%= 20 %>">
									<%= HtmlUtil.escape(key) %>
								</aui:col>

								<aui:col width="<%= 80 %>">

									<%
									String[] date = value.split("-");

									Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

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
							</c:when>
							<c:otherwise>
								<aui:col width="<%= 20 %>">
									<%= HtmlUtil.escape(key) %>
								</aui:col>

								<aui:col width="<%= 80 %>">
									<span class="field field-text">
										<input class="form-control" name="<portlet:namespace /><%= "parameterValue" + HtmlUtil.escapeAttribute(key) %>" type="text" value="<%= HtmlUtil.escapeAttribute(value) %>" />
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
		<aui:button cssClass="btn-lg" type="submit" value="generate" />

		<aui:button cssClass="btn-lg" href="<%= searchDefinitionURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>