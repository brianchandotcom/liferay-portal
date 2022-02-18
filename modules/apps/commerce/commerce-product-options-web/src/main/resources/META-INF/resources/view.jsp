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
CPOptionDisplayContext cpOptionDisplayContext = (CPOptionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="pt-4" id="<portlet:namespace />optionsContainer">
	<div class="container-fluid container-fluid-max-xl">
		<frontend-data-set:headless-display
			apiURL="/o/headless-commerce-admin-catalog/v1.0/options"
			creationMenu="<%= cpOptionDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= cpOptionDisplayContext.getOptionFDSActionDropdownItems() %>"
			id="<%= CommerceOptionFDSNames.OPTIONS %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</div>
</div>