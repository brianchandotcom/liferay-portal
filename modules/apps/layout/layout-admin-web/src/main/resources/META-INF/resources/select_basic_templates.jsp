<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request, liferayPortletResponse);
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
		<h3 class="h6 sheet-subtitle">
			<liferay-ui:message key="other" />
		</h3>

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