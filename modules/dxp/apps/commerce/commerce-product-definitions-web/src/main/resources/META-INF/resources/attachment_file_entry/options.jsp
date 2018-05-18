<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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