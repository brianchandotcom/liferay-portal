<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
User selUser = PortalUtil.getSelectedUser(request);

long maxFileSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) / 1024;
%>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
		<aui:script>
			opener.<portlet:namespace />changeLogo('<%= selUser.getPortraitURL(themeDisplay) %>');

			window.close();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="editUserPortraitURL">
			<portlet:param name="struts_action" value="/users_admin/edit_user_portrait" />
		</portlet:actionURL>

		<aui:form action="<%= editUserPortraitURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="p_u_i_d" type="hidden" value="<%= selUser.getUserId() %>" />
			<aui:input name="cropRegion" type="hidden" />

			<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<liferay-ui:error exception="<%= UserPortraitSizeException.class %>">

				<liferay-ui:message arguments="<%= PrefsPropsUtil.getLong(PropsKeys.USERS_IMAGE_MAX_SIZE) / 1024 %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" />
			</liferay-ui:error>

			<aui:fieldset>
				<aui:input autoFocus="<= windowState.equals(WindowState.MAXIMIZED) %>" label='<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", maxFileSize, false) %>' name="fileName" size="50" type="file" />

				<div class="lfr-change-logo lfr-portrait-preview" id="<portlet:namespace />portraitPreview">
					<img class="lfr-portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= HtmlUtil.escape(selUser.getPortraitURL(themeDisplay)) %>" />
				</div>

				<aui:button-row>
					<aui:button name="submitButton" type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<aui:script use="liferay-logo-editor">
			new Liferay.LogoEditor(
				{
					maxFileSize: '<%= maxFileSize %>',
					namespace: '<portlet:namespace />',
					previewURL: '<portlet:resourceURL><portlet:param name="struts_action" value="/users_admin/edit_user_portrait" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" /><portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:resourceURL>',
					uploadURL: '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user_portrait" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="p_u_i_d" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:actionURL>'
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>