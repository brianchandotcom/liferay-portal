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
VirtualCPTypeHelper virtualCPTypeHelper = (VirtualCPTypeHelper)request.getAttribute(VirtualCPTypeWebKeys.VIRTUAL_CP_TYPE_HELPER);

CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);

CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(request);

CPSku cpSku = cpContentHelper.getDefaultCPSku(cpCatalogEntry);

long cpDefinitionId = cpCatalogEntry.getCPDefinitionId();

long cpInstanceId = 0;

if (cpSku != null) {
	cpInstanceId = cpSku.getCPInstanceId();
}

String sampleURL = virtualCPTypeHelper.getSampleURL(cpDefinitionId, cpInstanceId, themeDisplay);
%>

<c:if test="<%= VirtualCPTypeConstants.NAME.equals(cpCatalogEntry.getProductTypeName()) %>">
	<div class="row">
		<div class="col-md-12">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(sampleURL) %>">
					<a class="btn btn-primary" href="<%= HtmlUtil.escapeHREF(sampleURL) %>">
						<liferay-ui:message key="download-sample-file" />
					</a>
				</c:when>
				<c:otherwise>
					<div class="sampleFile" data-text-cp-instance-sample-file="" data-text-cp-instance-sample-file-show></div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</c:if>