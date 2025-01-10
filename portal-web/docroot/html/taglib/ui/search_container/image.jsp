<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/search_container/init.jsp" %>

<%
String src = HtmlUtil.escapeAttribute((String)request.getAttribute("liferay-ui:search-container-column-icon:src"));
boolean toggleRowChecker = GetterUtil.getBoolean(request.getAttribute("liferay-ui:search-container-column-icon:toggleRowChecker"));
String uuid = String.valueOf(UUID.randomUUID());
%>

<c:if test="<%= Validator.isNotNull(src) %>">
	<aui:style type="text/css">
		#image_<%= uuid %> {
			background-image: url("<%= src %>");
		}
	</aui:style>

	<div class="aspect-ratio-bg-cover <%= toggleRowChecker ? "click-selector" : StringPool.BLANK %> sticker sticker-static" id="image_<%= uuid %>">
		<img alt="thumbnail" class="sr-only" src="<%= src %>" />
	</div>
</c:if>