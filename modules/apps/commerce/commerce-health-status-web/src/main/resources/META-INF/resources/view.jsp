<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceHealthStatusDisplayContext commerceHealthStatusDisplayContext = (CommerceHealthStatusDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceHealthStatusDisplayContext.hasManageCommerceHealthStatusPermission() %>">
	<div class="container-fluid container-fluid-max-xl pt-4">
		<liferay-ui:search-container
			id="commerceHealthStatuses"
			searchContainer="<%= commerceHealthStatusDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.health.status.CommerceHealthStatus"
				modelVar="commerceHealthStatus"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="name"
					value="<%= HtmlUtil.escape(commerceHealthStatus.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="description"
					value="<%= HtmlUtil.escape(commerceHealthStatus.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="status"
				>

					<%
					String iconCheckCssClass = "commerce-health-status-check-row-icon-check" + row.getRowId() + StringPool.SPACE;
					String iconSpinnerCssClass = "hide icon-spinner icon-spin commerce-health-status-check-row-icon-spinner" + row.getRowId();
					String iconTimesCssClass = "commerce-health-status-check-row-icon-times" + row.getRowId() + StringPool.SPACE;

					if (commerceHealthStatus.isFixed(company.getCompanyId(), themeDisplay.getScopeGroupId())) {
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
</c:if>