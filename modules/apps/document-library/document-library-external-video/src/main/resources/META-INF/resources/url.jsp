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
String eventName = (String)request.getAttribute(DLExternalVideoVideoURLItemSelectorView.EVENT_NAME);
%>

<liferay-util:html-top
	outputKey="document_library_external_video_file_picker_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/file_picker.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="lfr-form-content">
	<clay:sheet>
		<div class="panel-group panel-group-flush">
			<liferay-portlet:resourceURL id="/document_library_external_video/get_dl_external_video_fields" portletName="<%= DLPortletKeys.DOCUMENT_LIBRARY %>" var="getDLExternalVideoFieldsURL" />

			<react:component
				module="js/ItemSelectorUrlVideo"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"eventName", eventName
					).put(
						"getDLExternalVideoFieldsURL", getDLExternalVideoFieldsURL
					).put(
						"namespace", PortalUtil.getPortletNamespace(DLPortletKeys.DOCUMENT_LIBRARY)
					).build()
				%>'
			/>
		</div>
	</clay:sheet>
</div>