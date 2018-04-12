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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<c:choose>
	<c:when test="<%= !cpAttachmentFileEntriesDisplayContext.hasOptions() %>">
		<div class="alert alert-info">
			<liferay-ui:message key="there-are-no-options-set-as-sku-contributor" />
		</div>
	</c:when>
	<c:otherwise>
		<%= cpAttachmentFileEntriesDisplayContext.renderOptions(renderRequest, renderResponse) %>

		<aui:input name="ddmFormValues" type="hidden" />
	</c:otherwise>
</c:choose>