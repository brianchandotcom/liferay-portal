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
FeatureFlagsDisplayContext featureFlagsDisplayContext = (FeatureFlagsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String displayStyle = featureFlagsDisplayContext.getDisplayStyle();
%>

<clay:container-fluid>
	<clay:sheet>
		<clay:sheet-header>
			<h2 class="sheet-title"><%= featureFlagsDisplayContext.getTitle() %></h2>

			<div class="sheet-text"><%= featureFlagsDisplayContext.getDescription() %></div>
		</clay:sheet-header>

		<clay:sheet-section><clay:management-toolbar
			managementToolbarDisplayContext="<%= featureFlagsDisplayContext.getManagementToolbarDisplayContext() %>"
		/>

			<liferay-ui:search-container
				cssClass="table-valign-top"
				searchContainer="<%= featureFlagsDisplayContext.getSearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.feature.flag.web.internal.model.FeatureFlagDisplay"
					keyProperty="key"
				>

					<%
					FeatureFlagDisplay featureFlagDisplay = (FeatureFlagDisplay)model;
					%>

					<c:choose>
						<c:when test='<%= Objects.equals("list", displayStyle) %>'>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-expand-smallest"
								name="name"
								property="title"
							/>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand"
								name="description"
								property="description"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-text
								colspan="<%= 11 %>"
							>
								<h5>
									<strong><%= featureFlagDisplay.getTitle() %>
									</strong>
								</h5>

								<h6 class="text-default">
									<%= featureFlagDisplay.getDescription() %>
								</h6>
							</liferay-ui:search-container-column-text>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-text
						colspan="<%= 1 %>"
						name="action"
					>
						<%@ include file="/toggle_switch.jspf" %>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= displayStyle %>"
					markupView="lexicon"
					searchResultCssClass="<%= featureFlagsDisplayContext.getSearchResultCssClass() %>"
				/>
			</liferay-ui:search-container></clay:sheet-section>
	</clay:sheet>
</clay:container-fluid>