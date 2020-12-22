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

<div class="lfr-search-container-wrapper" id="<portlet:namespace />layoutTypes">
	<ul class="card-page card-page-equal-height">

		<%
		for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPageTemplateEntries()) {
		%>

			<li class="card-page-item card-page-item-asset">
				<clay:vertical-card
					verticalCard="<%= new SelectBasicTemplatesVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse) %>"
				/>
			</li>

		<%
		}
		%>

	</ul>

	<c:if test="<%= selectLayoutPageTemplateEntryDisplayContext.getTypesCount() > 0 %>">
		<h6 class="sheet-subtitle">
			<liferay-ui:message key="other" />
		</h6>

		<ul class="card-page card-page-equal-height">

			<%
			for (String type : selectLayoutPageTemplateEntryDisplayContext.getTypes()) {
			%>

				<li class="card-page-item card-page-item-directory">
					<clay:navigation-card
						navigationCard="<%= new SelectBasicTemplatesNavigationCard(type, renderRequest, renderResponse) %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</c:if>
</div>