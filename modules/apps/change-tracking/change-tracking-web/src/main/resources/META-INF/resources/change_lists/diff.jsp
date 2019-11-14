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

<%@ include file="/change_lists/init.jsp" %>

<liferay-portlet:renderURL var="backURL">
	<portlet:param name="mvcRenderCommandName" value="/change_lists/view" />
</liferay-portlet:renderURL>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

CTEntryDiffDisplay ctEntryDiffDisplay = (CTEntryDiffDisplay)request.getAttribute(CTWebKeys.CT_ENTRY_DIFF_DISPLAY);
%>

<div class="container-fluid-1280">
	<div class="row">
		<div class="col-sm-6">
			<div class="overview-card-sheet">
				<div class="overview-card-container overview-card-header">
					<div class="row">
						<h4><%= ctEntryDiffDisplay.getLeftTitle() %></h4>
					</div>
				</div>

				<div class="overview-card-sheet-divider"></div>
				<div class="overview-card-body overview-card-container">

					<%
					ctEntryDiffDisplay.renderLeftView();
					%>

				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="overview-card-sheet">
				<div class="overview-card-container overview-card-header">
					<div class="row">
						<h4><%= ctEntryDiffDisplay.getRightTitle() %></h4>
					</div>
				</div>

				<div class="overview-card-sheet-divider"></div>
				<div class="overview-card-body overview-card-container">

					<%
					ctEntryDiffDisplay.renderRightView();
					%>

				</div>
			</div>
		</div>
	</div>
</div>