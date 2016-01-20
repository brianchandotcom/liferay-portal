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

<%@ include file="/html/taglib/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_portlet_preview_page") + StringPool.UNDERLINE;

String portletResource = (String)request.getAttribute("liferay-portlet:preview:portletName");
String queryString = (String)request.getAttribute("liferay-portlet:preview:queryString");
boolean showBorders = GetterUtil.getBoolean((String)request.getAttribute("liferay-portlet:preview:showBorders"));
String width = (String)request.getAttribute("liferay-portlet:preview:width");

String previewWidth = ParamUtil.getString(request, "previewWidth");

if (Validator.isNull(width)) {
	previewWidth = width;
}
%>

<div class="taglib-portlet-preview <%= showBorders ? "show-borders" : StringPool.BLANK %>">
	<c:if test="<%= showBorders %>">
		<div class="title">
			<liferay-ui:message key="preview" />
		</div>
	</c:if>

	<div class="preview" id="<%= randomNamespace %>">
		<div style="margin: 3px; width: <%= Validator.isNotNull(previewWidth) ? ((GetterUtil.getInteger(previewWidth) + 20) + "px") : "100%" %>;">
			<liferay-portlet:runtime
				persistSettings="<%= false %>"
				portletName="<%= portletResource %>"
				queryString="<%= queryString %>"
			/>
		</div>
	</div>
</div>

<aui:script>
	Liferay.Util.disableElements('#<%= randomNamespace %>');
</aui:script>