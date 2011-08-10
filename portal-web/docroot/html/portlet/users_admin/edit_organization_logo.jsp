<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
long groupId = ParamUtil.getLong(request, "groupId");
long publicLayoutSetId = ParamUtil.getLong(request, "publicLayoutSetId");
%>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "request_processed") %>'>

		<%
		String logoURL = StringPool.BLANK;

		if (publicLayoutSetId != 0) {
			LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(publicLayoutSetId);

			logoURL = themeDisplay.getPathImage() + "/organization_logo?img_id=" + publicLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(publicLayoutSet.getLogoId());
		}
		%>

		<aui:script>
			window.close();
			opener.<portlet:namespace />changeLogo('<%= logoURL %>');
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="editOrganizationLogoURL">
			<portlet:param name="struts_action" value="/users_admin/edit_organization_logo" />
		</portlet:actionURL>

		<aui:form action="<%= editOrganizationLogoURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
			<aui:input name="publicLayoutSetId" type="hidden" value="<%= publicLayoutSetId %>" />

			<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<aui:fieldset>
				<aui:input label="upload-a-logo-for-the-organization-pages-that-will-be-used-instead-of-the-default-enterprise-logo-in-both-public-and-private-pages" name="fileName" size="50" type="file" />

				<aui:button-row>
					<aui:button type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />fileName);
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>