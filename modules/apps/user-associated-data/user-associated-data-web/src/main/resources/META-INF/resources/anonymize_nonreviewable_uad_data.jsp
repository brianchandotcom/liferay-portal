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
int totalReviewableUADEntitiesCount = (int)request.getAttribute(UADWebKeys.TOTAL_NONREVIEWABLE_UAD_ENTITIES_COUNT);
SearchContainer<UADApplicationSummaryDisplay> uadApplicationSummarySearchContainer = (SearchContainer<UADApplicationSummaryDisplay>)request.getAttribute(WebKeys.SEARCH_CONTAINER);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcRenderCommandName", "/view_uad_summary");
backURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));

String statusLabelDone = StringUtil.toUpperCase(LanguageUtil.get(request, "done"), locale);
String statusLabelPending = StringUtil.toUpperCase(LanguageUtil.get(request, "pending"), locale);

PortletURL reviewDataURL = liferayPortletResponse.createRenderURL();

reviewDataURL.setParameter("mvcRenderCommandName", "/review_uad_data");
reviewDataURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(false);
						navigationItem.setHref(reviewDataURL.toString());
						navigationItem.setLabel(LanguageUtil.get(request, "review-data"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "anonymize-nonreviewable-data"));
					});
			}
		} %>'
/>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<aui:form method="post" name="nonreviewableUADDataForm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="p_u_i_d" type="hidden" value="<%= String.valueOf(selectedUser.getUserId()) %>" />

		<div class="sheet sheet-lg">
			<div class="sheet-header">
				<h2 class="sheet-title"><liferay-ui:message key="process-automatically-anonymizable-data" /></h2>
			</div>

			<div class="sheet-section">
				<div class="text-muted"><liferay-ui:message key="automatically-anonymize-data-that-does-not-require-review" /></div>
			</div>

			<div class="sheet-section">
				<h3 class="sheet-subtitle">
					<liferay-ui:message key="status-summary" />
				</h3>

				<div class="autofit-row autofit-row-center">
					<div class="autofit-col autofit-col-expand">
						<div class="autofit-section">
							<strong><liferay-ui:message key="remaining-items" />: </strong><%= totalReviewableUADEntitiesCount %>
						</div>
					</div>

					<div class="autofit-col">
						<portlet:actionURL name="/anonymize_nonreviewable_uad_data" var="anonymizeURL" />

						<aui:button cssClass="btn-sm" disabled="<%= totalReviewableUADEntitiesCount == 0 %>" onClick='<%= renderResponse.getNamespace() + "confirmAction('nonreviewableUADDataForm', '" + anonymizeURL.toString() + "', '" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-anonymize-the-users-personal-data") + "')" %>' primary="true" value="delete" />
					</div>
				</div>
			</div>

			<div class="sheet-section">
				<h3 class="sheet-subtitle"><liferay-ui:message key="applications" /></h3>

				<%
				AnonymizeNonreviewableUADDataManagementToolbarDisplayContext anonymizeNonreviewableUADDataManagementToolbarDisplayContext = new AnonymizeNonreviewableUADDataManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, uadApplicationSummarySearchContainer);
				%>

				<clay:management-toolbar
					displayContext="<%= anonymizeNonreviewableUADDataManagementToolbarDisplayContext %>"
				/>

				<liferay-ui:search-container
					searchContainer="<%= uadApplicationSummarySearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay"
						escapedModel="<%= true %>"
						keyProperty="key"
						modelVar="uadApplicationSummaryDisplay"
					>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-list-title"
							name="name"
							value="<%= UADLanguageUtil.getApplicationName(uadApplicationSummaryDisplay.getApplicationKey(), locale) %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand"
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
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
						searchResultCssClass="show-quick-actions-on-hover table table-autofit"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</aui:form>
</div>

<%@ include file="/action/confirm_action_js.jspf" %>