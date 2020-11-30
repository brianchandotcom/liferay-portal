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
String eventName = (String)request.getAttribute(DLExternalVideoWebKeys.EVENT_NAME);
%>

<liferay-util:html-top
	outputKey="document_library_external_video_preview_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/external_video_preview.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="lfr-form-content">
	<clay:sheet>
		<div class="panel-group panel-group-flush">
			<aui:input disabled="<%= true %>" label="video-url" name="externalVideoURL" wrapperCssClass="mb-0" />

			<p class="form-text"><liferay-ui:message key="video-url-help" /></p>

			<aui:button disabled="<%= true %>" primary="<%= true %>" value="add" />

			<div class="external-video-preview mt-4">
				<div class="external-video-preview-aspect-ratio">
					<div class="external-video-preview-placeholder">
						<clay:icon
							symbol="video"
						/>
					</div>
				</div>
			</div>

			<liferay-portlet:resourceURL id="/document_library_external_video/get_dl_external_video_fields" portletName="<%= DLPortletKeys.DOCUMENT_LIBRARY %>" var="getDLExternalVideoFieldsURL" />

			<react:component
				module="js/DLExternalVideoVideoURLItemSelectorView"
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