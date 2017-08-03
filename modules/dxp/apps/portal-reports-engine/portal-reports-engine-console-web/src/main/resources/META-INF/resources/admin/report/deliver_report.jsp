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
String fileName = ParamUtil.getString(request, "fileName");

portletDisplay.setShowBackIcon(true);

PortletURL backURL = reportsEngineDisplayContext.getPortletURL();

backURL.setParameter("mvcPath", "/admin/report/requested_report_detail.jsp");
backURL.setParameter("entryId", String.valueOf(entryId));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "deliver-report"));
%>

<portlet:actionURL name="deliverReport" var="actionURL">
	<portlet:param name="redirect" value="<%= backURL.toString() %>" />
	<portlet:param name="entryId" value="<%= String.valueOf(entryId) %>" />
	<portlet:param name="fileName" value="<%= fileName %>" />
</portlet:actionURL>

<portlet:actionURL name="editDataSource" var="editDataSourceURL">
	<portlet:param name="mvcPath" value="/admin/data_source/edit_data_source.jsp" />
</portlet:actionURL>

<aui:form action="<%= actionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />

	<liferay-ui:error exception="<%= EntryEmailDeliveryException.class %>" message="please-enter-a-valid-email-address" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:field-wrapper label="report-name">
				<%= HtmlUtil.escape(StringUtil.extractLast(fileName, StringPool.FORWARD_SLASH)) %>
			</aui:field-wrapper>

			<aui:input label="email-recipient" name="emailAddresses" type="text" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="deliver" />

		<aui:button cssClass="btn-lg" href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>