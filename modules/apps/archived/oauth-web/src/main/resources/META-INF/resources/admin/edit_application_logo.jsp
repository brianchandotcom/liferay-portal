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
long oAuthApplicationId = ParamUtil.getLong(renderRequest, "oAuthApplicationId");

OAuthApplication oAuthApplication = OAuthApplicationLocalServiceUtil.fetchOAuthApplication(oAuthApplicationId);
%>

<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
		<aui:script>
			window.close();
			opener.<portlet:namespace />changeLogo(
				'<%= themeDisplay.getPathImage() + "/logo?img_id=" + oAuthApplication.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(oAuthApplication.getLogoId()) %>'
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL name="updateLogo" var="updateLogoURL">
			<portlet:param name="mvcPath" value="/admin/edit_application_logo.jsp" />
			<portlet:param name="oAuthApplicationId" value="<%= String.valueOf(oAuthApplicationId) %>" />
		</portlet:actionURL>

		<aui:form action="<%= updateLogoURL %>" enctype="multipart/form-data" method="post" name="fm">
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<aui:fieldset>
				<aui:input label="" name="fileName" size="50" type="file" />

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(
					document.<portlet:namespace />fm.<portlet:namespace />fileName
				);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>