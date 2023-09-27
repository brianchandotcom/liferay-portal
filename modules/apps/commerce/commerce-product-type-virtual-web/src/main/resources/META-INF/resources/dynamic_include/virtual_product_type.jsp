<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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