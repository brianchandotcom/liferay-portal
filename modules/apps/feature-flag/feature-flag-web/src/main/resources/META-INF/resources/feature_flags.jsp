<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

					String titleId = "titleId" + featureFlagDisplay.getKey();
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
									<strong id="<%= titleId %>"><%= featureFlagDisplay.getTitle() %>
									</strong><span class="text-muted"> (<%= featureFlagDisplay.getKey() %>)</span>
								</h5>

								<h6 class="text-default">
									<%= featureFlagDisplay.getDescription() %>
								</h6>

								<c:if test="<%= !ArrayUtil.isEmpty(featureFlagDisplay.getDependencyKeys()) %>">
									<h6>
										<liferay-ui:message arguments="<%= StringUtil.merge(featureFlagDisplay.getDependencyKeys(), StringPool.COMMA_AND_SPACE) %>" key="dependencies-x" />
									</h6>
								</c:if>
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