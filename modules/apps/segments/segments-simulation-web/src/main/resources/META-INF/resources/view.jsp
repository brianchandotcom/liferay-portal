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
SegmentsSimulationDisplayContext segmentsSimulationDisplayContext = new SegmentsSimulationDisplayContext(request, renderResponse);
%>

<aui:form method="post" name="segmentsSimulationFm">
	<div class="container-fluid segments-container">
		<table class="table table-autofit table-condensed table-responsive">
			<tbody>

				<%
				for (SegmentsEntry segmentsEntry : segmentsSimulationDisplayContext.getSegmentsEntries()) {
				%>

					<tr>
						<td class="lfr-checkbox-column text-left text-middle">
							<input class="simulated-segment" name="<%= segmentsSimulationDisplayContext.getPortletNamespace() + "segmentsEntryId" %>" type="checkbox" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />
						</td>
						<td class="table-cell-content">
							<liferay-ui:message key="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>" />
						</td>
					</tr>

				<%
				}
				%>

			</tbody>
		</table>
	</div>
</aui:form>

<aui:script use="liferay-portlet-segments-simulation">
	new Liferay.Portlet.SegmentsSimulation(
		{
			deactivateSimulationUrl: '<%= segmentsSimulationDisplayContext.getDeactivateSimulationURL() %>',
			form: document.<portlet:namespace />segmentsSimulationFm,
			simulateSegmentsEntriesUrl: '<%= segmentsSimulationDisplayContext.getSimulateSegmentsEntriesURL() %>'
		}
	);
</aui:script>