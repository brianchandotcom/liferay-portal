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
ViewUADApplicationsSummaryDisplay viewUADApplicationsSummaryDisplay = (ViewUADApplicationsSummaryDisplay)request.getAttribute(UADWebKeys.VIEW_UAD_APPLICATIONS_SUMMARY_DISPLAY);

SearchContainer<UADApplicationSummaryDisplay> uadApplicationsSummaryDisplaySearchContainer = viewUADApplicationsSummaryDisplay.getSearchContainer();

UADApplicationsSummaryManagementToolbarDisplayContext uadApplicationsSummaryManagementToolbarDisplayContext = new UADApplicationsSummaryManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, uadApplicationsSummaryDisplaySearchContainer);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcRenderCommandName", "/view_uad_summary");
backURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));

String statusLabelDone = StringUtil.toUpperCase(LanguageUtil.get(request, "done"), locale);
String statusLabelPending = StringUtil.toUpperCase(LanguageUtil.get(request, "pending"), locale);
%>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="application-data-review" /></h2>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle">
				<liferay-ui:message key="status-summary" />
			</h3>

			<div class="autofit-row autofit-row-center">
				<div class="autofit-col autofit-col-expand">
					<div class="autofit-section">
						<strong><liferay-ui:message key="remaining-items" />: </strong><%= viewUADApplicationsSummaryDisplay.getTotalCount() %>
					</div>
				</div>

				<div class="autofit-col">
					<aui:button cssClass="btn-sm" disabled="<%= viewUADApplicationsSummaryDisplay.getTotalCount() > 0 %>" href="<%= backURL.toString() %>" primary="true" value="complete-step" />
				</div>
			</div>
		</div>

		<div class="sheet-section">
			<h3 class="sheet-subtitle"><liferay-ui:message key="applications" /></h3>

			<clay:management-toolbar
				displayContext="<%= uadApplicationsSummaryManagementToolbarDisplayContext %>"
			/>

			<liferay-ui:search-container
				searchContainer="<%= uadApplicationsSummaryDisplaySearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay"
					escapedModel="<%= true %>"
					keyProperty="key"
					modelVar="uadApplicationSummaryDisplay"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand table-list-title"
						href="<%= uadApplicationSummaryDisplay.getViewURL() %>"
						name="name"
						value="<%= UADLanguageUtil.getApplicationName(uadApplicationSummaryDisplay.getApplicationKey(), locale) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						href="<%= uadApplicationSummaryDisplay.getViewURL() %>"
						name="items"
						property="count"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="status"
					>
						<clay:label
							label="<%= uadApplicationSummaryDisplay.hasItems() ? statusLabelPending : statusLabelDone %>"
							style='<%= uadApplicationSummaryDisplay.hasItems() ? "warning" : "success" %>'
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/applications_summary_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchResultCssClass="show-quick-actions-on-hover table table-autofit"
				/>
			</liferay-ui:search-container>
		</div>
	</div>
</div>