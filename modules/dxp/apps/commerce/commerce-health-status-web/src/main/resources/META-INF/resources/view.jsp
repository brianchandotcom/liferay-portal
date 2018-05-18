<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceHealthStatusDisplayContext commerceHealthStatusDisplayContext = (CommerceHealthStatusDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceHealthStatus> commerceHealthStatusSearchContainer = commerceHealthStatusDisplayContext.getSearchContainer();
%>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceHealthStatuses"
		searchContainer="<%= commerceHealthStatusSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.health.status.CommerceHealthStatus"
			modelVar="commerceHealthStatus"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= commerceHealthStatus.getName(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				value="<%= commerceHealthStatus.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="status"
			>

				<%
				String iconCheckCssClass = "commerce-health-status-check-row-icon-check" + row.getRowId() + StringPool.SPACE;
				String iconSpinnerCssClass = "hide icon-spinner icon-spin commerce-health-status-check-row-icon-spinner" + row.getRowId();
				String iconTimesCssClass = "commerce-health-status-check-row-icon-times" + row.getRowId() + StringPool.SPACE;

				if (commerceHealthStatus.isFixed(themeDisplay.getScopeGroupId())) {
					iconTimesCssClass += "hide";
				}
				else {
					iconCheckCssClass += "hide";
				}
				%>

				<div class="<%= iconCheckCssClass %>">
					<liferay-ui:icon
						cssClass="commerce-admin-icon-check"
						icon="check"
						markupView="lexicon"
					/>
				</div>

				<div class="<%= iconTimesCssClass %>">
					<liferay-ui:icon
						cssClass="commerce-admin-icon-times"
						icon="times"
						markupView="lexicon"
					/>
				</div>

				<span aria-hidden="true" class="<%= iconSpinnerCssClass %>"></span>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/health_status_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>