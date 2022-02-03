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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectMasterLayout");
long masterLayoutPlid = ParamUtil.getLong(request, "masterLayoutPlid");

SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);

List<LayoutPageTemplateEntry> masterLayoutPageTemplateEntries = selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPageTemplateEntries();
%>

<aui:form cssClass="container-fluid container-fluid-max-xl container-view" name="fm">
	<ul class="card-page card-page-equal-height">

		<%
		for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : masterLayoutPageTemplateEntries) {
		%>

			<li class="card-page-item card-page-item-asset">
				<div class="form-check form-check-card">
					<clay:vertical-card
						verticalCard="<%= new SelectMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, Objects.equals(masterLayoutPageTemplateEntry.getPlid(), masterLayoutPlid)) %>"
					/>
				</div>
			</li>

		<%
		}
		%>

	</ul>
</aui:form>

<liferay-frontend:component
	context='<%= HashMapBuilder.<String, Object>put("eventName", HtmlUtil.escape(eventName)).put("selector", ".select-master-layout-option").build() %>'
	module="js/SelectCardHandler"
/>