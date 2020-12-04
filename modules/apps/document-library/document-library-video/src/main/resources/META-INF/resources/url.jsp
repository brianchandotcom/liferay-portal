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
String eventName = (String)request.getAttribute(DLVideoWebKeys.EVENT_NAME);
%>

<liferay-util:html-top
	outputKey="document_library_video_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="lfr-form-content">
	<clay:sheet>
		<div class="panel-group panel-group-flush">
			<aui:input disabled="<%= true %>" label="video-url" name="dlVideoExternalShortcutURL" wrapperCssClass="mb-0" />

			<p class="form-text"><liferay-ui:message key="video-url-help" /></p>

			<aui:button disabled="<%= true %>" primary="<%= true %>" value="add" />

			<div class="mt-4 video-preview">
				<div class="video-preview-aspect-ratio">
					<div class="video-preview-placeholder">
						<clay:icon
							symbol="video"
						/>
					</div>
				</div>
			</div>

			<liferay-portlet:resourceURL id="/document_library_video/get_dl_video_external_shortcut_fields" portletName="<%= DLVideoPortletKeys.DL_VIDEO %>" var="getDLVideoExternalShortcutFieldsURL" />

			<react:component
				module="js/DLVideoExternalShortcutURLItemSelectorView"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"eventName", eventName
					).put(
						"getDLVideoExternalShortcutFieldsURL", getDLVideoExternalShortcutFieldsURL
					).put(
						"namespace", PortalUtil.getPortletNamespace(DLVideoPortletKeys.DL_VIDEO)
					).put(
						"returnType", VideoEmbeddableHTMLItemSelectorReturnType.class.getName()
					).build()
				%>'
			/>
		</div>
	</clay:sheet>
</div>