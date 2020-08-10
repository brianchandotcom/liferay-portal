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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);
%>

<div class="col-md-4">
	<div class="card">
		<a class="aspect-ratio" href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">

			<%
			String img = cpCatalogEntry.getDefaultImageFileUrl();
			%>

			<c:if test="<%= Validator.isNotNull(img) %>">
				<img class="aspect-ratio-item-center-middle aspect-ratio-item-fluid" src="<%= img %>" />
			</c:if>
		</a>

		<div class="card-row card-row-padded card-row-valign-top">
			<div class="card-col-content">
				<a class="truncate-text" href="<%= cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay) %>">
					<%= HtmlUtil.escape(cpCatalogEntry.getName()) %>
				</a>
			</div>
		</div>
	</div>
</div>