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
int totalReviewableUADEntitiesCount = (int)request.getAttribute(UADWebKeys.TOTAL_REVIEWABLE_UAD_ENTITIES_COUNT);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcRenderCommandName", "/view_uad_summary");
backURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(StringBundler.concat(selectedUser.getFullName(), " - ", LanguageUtil.get(request, "personal-data-erasure")));
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(LanguageUtil.get(request, "review-data"));
					});
			}
		} %>'
/>

<div class="container-fluid container-fluid-max-xl container-form-lg">
	<div class="row">
		<div class="col-lg-3">
		</div>

		<div class="col-lg-8">
			<div class="sheet sheet-lg">
				<div class="sheet-header">
					<h2 class="sheet-title"><liferay-ui:message key="review-data" /></h2>
				</div>

				<div class="sheet-section">
					<h3 class="sheet-subtitle">
						<liferay-ui:message key="status-summary" />
					</h3>

					<strong><liferay-ui:message key="remaining-items" />: </strong><%= totalReviewableUADEntitiesCount %>
				</div>

				<div class="sheet-section">
					<h3 class="sheet-subtitle"><liferay-ui:message key="view-data" /></h3>
				</div>
			</div>
		</div>
	</div>
</div>