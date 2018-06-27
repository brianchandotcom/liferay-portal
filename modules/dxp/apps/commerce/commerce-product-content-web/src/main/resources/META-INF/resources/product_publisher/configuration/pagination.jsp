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
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset markupView="lexicon">
	<aui:input checked="<%= cpPublisherConfigurationDisplayContext.isPaginate() %>" label="paginate" name="preferences--paginate--" type="toggle-switch" />

	<aui:input label="pagination-delta" name="preferences--paginationDelta--" type="number" value="<%= cpPublisherConfigurationDisplayContext.getPaginationDelta() %>" />

	<aui:select label="pagination-type" name="preferences--paginationType--" value="<%= cpPublisherConfigurationDisplayContext.getPaginationType() %>">

		<%
		for (String paginationType : CPContentConstants.PAGINATION_TYPES) {
		%>

			<aui:option label="<%= paginationType %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>