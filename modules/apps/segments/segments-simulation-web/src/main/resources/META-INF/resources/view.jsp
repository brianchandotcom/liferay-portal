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
SegmentsSimulationDisplayContext segmentsSimulationDisplayContext = (SegmentsSimulationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	cssClass="p-0 segments-simulation"
	id='<%= liferayPortletResponse.getNamespace() + "segmentsSimulationContainer" %>'
>
	<c:choose>
		<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-186155") %>'>
			<react:component
				module="js/components/SegmentsAndExperiencesSelector"
				props="<%= segmentsSimulationDisplayContext.getData() %>"
			/>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="<%= segmentsSimulationDisplayContext.isShowEmptyMessage() %>">
					<p class="mb-4 mt-1 small">
						<liferay-ui:message key="no-segments-have-been-added-yet" />
					</p>
				</c:when>
				<c:otherwise>
					<aui:form method="post" name="segmentsSimulationFm">
						<ul class="list-unstyled">
							<c:if test="<%= !segmentsSimulationDisplayContext.isSegmentationEnabled() %>">
								<clay:alert
									defaultTitleDisabled="<%= true %>"
									dismissible="<%= true %>"
									displayType="warning"
								>
									<strong><liferay-ui:message key="experiences-cannot-be-displayed-because-segmentation-is-disabled" /></strong>

									<c:choose>
										<c:when test="<%= segmentsSimulationDisplayContext.getSegmentsCompanyConfigurationURL() != null %>">
											<clay:link
												href="<%= segmentsSimulationDisplayContext.getSegmentsCompanyConfigurationURL() %>"
												label='<%= LanguageUtil.get(request, "to-enable,-go-to-instance-settings") %>'
											/>
										</c:when>
										<c:otherwise>
											<span><liferay-ui:message key="contact-your-system-administrator-to-enable-it" /></span>
										</c:otherwise>
									</c:choose>
								</clay:alert>
							</c:if>

							<%
							for (SegmentsEntry segmentsEntry : segmentsSimulationDisplayContext.getSegmentsEntries()) {
							%>

								<li class="bg-transparent border-0 list-group-item list-group-item-flex pb-3 pt-0 px-0">
									<span>
										<div class="custom-checkbox">
											<label class="position-relative">
												<input class="custom-control-input simulated-segment" name="<%= segmentsSimulationDisplayContext.getPortletNamespace() %>segmentsEntryId" type="checkbox" value="<%= String.valueOf(segmentsEntry.getSegmentsEntryId()) %>" />

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

					<liferay-frontend:component
						context='<%=
							HashMapBuilder.<String, Object>put(
								"deactivateSimulationURL", segmentsSimulationDisplayContext.getDeactivateSimulationURL()
							).put(
								"simulateSegmentsEntriesURL", segmentsSimulationDisplayContext.getSimulateSegmentsEntriesURL()
							).build()
						%>'
						module="js/main"
					/>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>