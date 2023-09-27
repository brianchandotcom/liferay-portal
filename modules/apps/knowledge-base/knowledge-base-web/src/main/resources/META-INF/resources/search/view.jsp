<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/search/init.jsp" %>

<liferay-portlet:renderURL varImpl="searchURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/knowledge_base/search" />
</liferay-portlet:renderURL>

<div class="form-search">
	<aui:form action="<%= searchURL %>" method="get" name="searchFm">
		<liferay-portlet:renderURLParams varImpl="searchURL" />

		<div class="input-group">
			<div class="input-group-item">
				<input aria-label="<%= LanguageUtil.get(request, "search") %>" class="form-control input-group-inset input-group-inset-after search-query" data-qa-id="searchInput" id="<portlet:namespace />keywords" name="<portlet:namespace />keywords" placeholder="<%= LanguageUtil.get(request, "keywords") %>" title="<%= LanguageUtil.get(request, "search") %>" type="text" value="<%= HtmlUtil.escapeAttribute(ParamUtil.getString(request, "keywords")) %>" />

				<div class="input-group-inset-item input-group-inset-item-after">
					<clay:button
						data-qa-id="searchButton"
						icon="search"
						displayType="unstyled"
						monospaced="<%= false %>"
						type="submit"
					/>
				</div>
			</div>
		</div>
	</aui:form>
</div>