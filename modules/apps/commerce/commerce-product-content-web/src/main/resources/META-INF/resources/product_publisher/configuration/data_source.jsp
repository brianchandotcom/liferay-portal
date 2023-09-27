<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset markupView="lexicon">
	<aui:select name="preferences--dataSource--" showEmptyOption="<%= true %>">

		<%
		for (CPDataSource cpDataSource : cpPublisherConfigurationDisplayContext.getCPDataSources()) {
			String selectedDataSource = cpPublisherConfigurationDisplayContext.getDataSource();
		%>

			<aui:option label="<%= HtmlUtil.escape(cpDataSource.getLabel(locale)) %>" selected="<%= selectedDataSource.equals(cpDataSource.getName()) %>" value="<%= cpDataSource.getName() %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>