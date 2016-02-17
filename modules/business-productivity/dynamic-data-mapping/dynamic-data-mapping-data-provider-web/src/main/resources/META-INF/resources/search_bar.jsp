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
DDMDataProviderDisplayTerms displayTerms = new DDMDataProviderDisplayTerms(renderRequest);
PortletURL portletURL = ddmDataProviderDisplayContext.getPortletURL();
%>

<aui:nav-bar cssClass="collapse-basic-search" id="toolbar" markupView="lexicon">
	<aui:nav-bar-search>
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
			<liferay-ui:search-toggle
				autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
				buttonLabel="search"
				displayTerms="<%= displayTerms %>"
				id="toggle_id_dynamic_data_mapping_data_provider_search"
				markupView="lexicon"
			>
				<aui:fieldset>
					<aui:input inlineField="<%= true %>" name="<%= DDMDataProviderDisplayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />

					<aui:input inlineField="<%= true %>" name="<%= DDMDataProviderDisplayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />
				</aui:fieldset>
			</liferay-ui:search-toggle>
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>