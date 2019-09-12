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

<div class="container-fluid segments-simulation" id="<portlet:namespace />segmentsSimulationContainer">
	<c:choose>
		<c:when test="<%= segmentsSimulationDisplayContext.isShowEmptyMessage() %>">
			<p class="mb-4 mt-1 small">
				<liferay-ui:message key="no-segments-have-been-added-yet" />
			</p>
		</c:when>
		<c:otherwise>
			<aui:form method="post" name="segmentsSimulationFm">
				<ul class="list-unstyled">

					<%
					for (SegmentsEntry segmentsEntry : segmentsSimulationDisplayContext.getSegmentsEntries()) {
					%>

						<li class="bg-transparent list-group-item list-group-item-flex">
							<span>
								<div class="custom-checkbox">
									<label class="position-relative text-light">
										<input class="custom-control-input simulated-segment" name="<%= segmentsSimulationDisplayContext.getPortletNamespace() + "segmentsEntryId" %>" type="checkbox" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />

										<span class="custom-control-label">
											<span class="custom-control-label-text">
												<liferay-ui:message key="<%= HtmlUtil.escape(segmentsEntry.getName(locale)) %>" />
											</span>
										</span>
									</label>
								</div>
							</span>
						</li>

					<%
					}
					%>

				</ul>
			</aui:form>

			<aui:script use="liferay-portlet-segments-simulation">
				new Liferay.Portlet.SegmentsSimulation({
					deactivateSimulationUrl:
						'<%= segmentsSimulationDisplayContext.getDeactivateSimulationURL() %>',
					form: document.<portlet:namespace />segmentsSimulationFm,
					simulateSegmentsEntriesUrl:
						'<%= segmentsSimulationDisplayContext.getSimulateSegmentsEntriesURL() %>'
				});
			</aui:script>
		</c:otherwise>
	</c:choose>
</div>