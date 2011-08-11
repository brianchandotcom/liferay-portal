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

<%@ include file="/html/taglib/ui/logo_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_logo_selector") + StringPool.UNDERLINE;

String defaultLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:defaultLogoURL");
String editLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:editLogoURL");
long imageId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:logo-selector:imageId"));
String logoDisplaySelector = (String)request.getAttribute("liferay-ui:logo-selector:logoDisplaySelector");
boolean showBackground = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:showBackground"));

boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");

String imageSrc = null;

if (deleteLogo || (imageId == 0)) {
	imageSrc = defaultLogoURL;
}
else {
	imageSrc = themeDisplay.getPathImage() + "/logo?img_id=" + imageId + "&t" + WebServerServletTokenUtil.getToken(imageId);
}
%>

<div class="taglib-logo-selector" id="<%= randomNamespace %>taglibLogoSelector">
	<div class="taglib-logo-selector-content" id="<%= randomNamespace %>taglibLogoSelectorContent">
		<a class='lfr-change-logo edit-logo-link <%= showBackground ? "show-background" : StringPool.BLANK %>' href="javascript:;">
			<img alt="<liferay-ui:message key="change-logo" />" class="avatar" id="<%= randomNamespace %>avatar" src="<%= imageSrc %>" />
		</a>

		<div class="portrait-icons">
			<liferay-ui:icon
				cssClass="edit-logo-link"
				image="edit"
				label="<%= true %>"
				message="change"
				url="javascript:;"
			/>

			<liferay-ui:icon
				cssClass='<%= "modify-link" + (imageId != 0 ? StringPool.BLANK : " aui-helper-hidden") %>'
				id='<%= randomNamespace + "deleteLogoLink" %>'
				image="delete"
				label="<%= true %>"
				url="javascript:;"
			/>

			<aui:input name="deleteLogo" type="hidden" value="<%= deleteLogo %>" />
		</div>
	</div>
</div>

<aui:script use="liferay-logo-selector">
	new Liferay.LogoSelector(
		{
			boundingBox: '#<%= randomNamespace %>taglibLogoSelector',
			contentBox: '#<%= randomNamespace %>taglibLogoSelectorContent',
			defaultLogoURL: '<%= defaultLogoURL %>',
			editLogoURL: '<%= editLogoURL %>',
			randomNamespace: '<%= randomNamespace %>',
			logoDisplaySelector: '<%= logoDisplaySelector %>',
			portletNamespace: '<portlet:namespace />'
		}
	).render();
</aui:script>