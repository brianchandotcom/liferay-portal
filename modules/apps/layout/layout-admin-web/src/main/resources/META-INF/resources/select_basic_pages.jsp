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
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);
%>

<div class="lfr-search-container-wrapper" id="<portlet:namespace/>layoutTypes">
	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="main-types" />
		</h6>

		<div class="row">

			<%
			for (String primaryType : selectLayoutPageTemplateEntryDisplayContext.getPrimaryTypes()) {
			%>

				<div class="col-md-4">
					<clay:vertical-card
						verticalCard="<%= new SelectBasicPagesVerticalCard(primaryType, renderRequest, renderResponse) %>"
					/>
				</div>

			<%
			}
			%>

		</div>
	</c:if>

	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="other" />
		</h6>

		<div class="row">

			<%
			for (String type : selectLayoutPageTemplateEntryDisplayContext.getTypes()) {
			%>

				<div class="col-md-4">
					<clay:horizontal-card
						horizontalCard="<%= new SelectBasicPagesHorizontalCard(type, renderRequest, renderResponse) %>"
					/>
				</div>

			<%
			}
			%>

		</div>
	</c:if>
</div>