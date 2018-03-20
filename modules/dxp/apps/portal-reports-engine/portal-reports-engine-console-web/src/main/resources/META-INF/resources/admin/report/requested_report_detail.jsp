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
long entryId = ParamUtil.getLong(request, "entryId", -1);

Entry entry = EntryLocalServiceUtil.getEntry(entryId);

String status = entry.getStatus();

Definition definition = DefinitionLocalServiceUtil.getDefinition(entry.getDefinitionId());

portletDisplay.setShowBackIcon(true);

PortletURL searchRequestURL = reportsEngineDisplayContext.getPortletURL();

searchRequestURL.setParameter("mvcPath", "/admin/view.jsp");

portletDisplay.setURLBack(searchRequestURL.toString());

renderResponse.setTitle(definition.getName(locale));
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= status.equals(ReportStatus.ERROR.getValue()) %>">
			<div class="portlet-msg-error">
				<liferay-ui:message key="an-error-occurred-while-processing-the-report" />
			</div>
		</c:when>
		<c:when test="<%= status.equals(ReportStatus.PENDING.getValue()) %>">
			<div class="portlet-msg-info">
				<liferay-ui:message key="processing-report.-this-may-take-several-minutes" />
			</div>
		</c:when>
	</c:choose>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:row cssClass="lfr-asset-column lfr-asset-column-details">
				<aui:col width="<%= 50 %>">
					<aui:field-wrapper label="requested-report-id">
						<%= entry.getEntryId() %>
					</aui:field-wrapper>

					<aui:field-wrapper label="definition-name">
						<%= HtmlUtil.escape(definition.getName(locale)) %>
					</aui:field-wrapper>

					<aui:field-wrapper label="description">
						<%= HtmlUtil.escape(definition.getDescription(locale)) %>
					</aui:field-wrapper>

					<aui:field-wrapper label="data-source-name">

						<%
						Source source = SourceLocalServiceUtil.fetchSource(definition.getSourceId());
						%>

						<%= (source == null) ? ReportDataSourceType.PORTAL.getValue() : HtmlUtil.escape(source.getName(locale)) %>
					</aui:field-wrapper>
				</aui:col>

				<aui:col width="<%= 50 %>">
					<c:if test="<%= entry.isScheduleRequest() %>">
						<aui:field-wrapper label="is-schedule-request">

							<%
							StringBundler sb = new StringBundler((entry.getEndDate() != null) ? 18 : 12);

							sb.append("<br />");
							sb.append(LanguageUtil.get(request, "scheduler-from"));
							sb.append(StringPool.BLANK);
							sb.append(StringPool.COLON);
							sb.append(StringPool.BLANK);
							sb.append(dateFormatDateTime.format(entry.getStartDate()));

							if (entry.getEndDate() != null) {
								sb.append("<br />");
								sb.append(LanguageUtil.get(request, "scheduler-to"));
								sb.append(StringPool.BLANK);
								sb.append(StringPool.COLON);
								sb.append(StringPool.BLANK);
								sb.append(dateFormatDateTime.format(entry.getEndDate()));
							}

							sb.append("<br />");
							sb.append(LanguageUtil.get(request, "scheduler-crontext"));
							sb.append(StringPool.BLANK);
							sb.append(StringPool.COLON);
							sb.append(StringPool.BLANK);
							sb.append(HtmlUtil.escape(entry.getRecurrence()));
							%>

							<%= sb.toString() %>
						</aui:field-wrapper>
					</c:if>

					<aui:field-wrapper label="requested-by">
						<%= HtmlUtil.escape(entry.getUserName()) %>
					</aui:field-wrapper>

					<aui:field-wrapper label="requested-date">
						<%= entry.getCreateDate() %>
					</aui:field-wrapper>

					<aui:field-wrapper label="completion-date">
						<%= entry.getModifiedDate() %>
					</aui:field-wrapper>
				</aui:col>
			</aui:row>
		</aui:fieldset>

		<%
		String[] reportParameters = StringUtil.split(entry.getReportParameters());
		JSONArray reportParametersJSONArray = JSONFactoryUtil.createJSONArray(entry.getReportParameters());
		%>

		<c:if test="<%= reportParameters.length > 0 %>">
			<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="report-parameters">
				<table class="table table-autofit table-list">
					<thead>
						<tr>
							<th><liferay-ui:message key="key" /></th>
							<th><liferay-ui:message key="value" /></th>
						</tr>
					</thead>

					<tbody>

						<%
						for (int i = 0; i < reportParametersJSONArray.length(); i++) {
							JSONObject reportParameterJSONObject = reportParametersJSONArray.getJSONObject(i);

							String key = reportParameterJSONObject.getString("key");
							String value = reportParameterJSONObject.getString("value");
						%>

							<tr>
								<td class="table-cell-content">
									<span class="truncate-text"><%= HtmlUtil.escape(key) %></span>
								</td>
								<td class="table-cell-content">
									<span class="truncate-text"><%= HtmlUtil.escape(value) %></span>
								</td>
							</tr>

						<%
						}
						%>

					</tbody>
				</table>

			</aui:fieldset>
		</c:if>

		<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="attachments">

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("file");
			headerNames.add("download");

			List<String> attachmentsFiles = Arrays.asList(entry.getAttachmentsFiles());

			request.setAttribute("entry", entry);

			PortletURL portletURL = renderResponse.createRenderURL();

			portletURL.setParameter("mvcPath", "/admin/report/requested_report_detail.jsp");
			portletURL.setParameter("entryId", String.valueOf(entryId));
			portletURL.setWindowState(WindowState.NORMAL);
			%>

			<liferay-ui:search-container
				delta="<%= 2 %>"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null) %>"
				total="<%= attachmentsFiles.size() %>"
			>
				<liferay-ui:search-container-results
					results="<%= ListUtil.subList(attachmentsFiles, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="java.lang.String"
					modelVar="fileName"
				>
					<liferay-ui:search-container-column-text
						name="file"
						value="<%= HtmlUtil.escape(StringUtil.extractLast(fileName, StringPool.SLASH)) %>"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						path="/admin/report/report_file_actions.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:fieldset>
	</aui:fieldset-group>
</div>