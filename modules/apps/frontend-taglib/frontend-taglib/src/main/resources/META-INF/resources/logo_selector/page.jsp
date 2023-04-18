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

<%@ include file="/logo_selector/init.jsp" %>

<%
boolean defaultLogo = GetterUtil.getBoolean((String)request.getAttribute("liferay-frontend:logo-selector:defaultLogo"));
String defaultLogoURL = (String)request.getAttribute("liferay-frontend:logo-selector:defaultLogoURL");
String imageURL = (String)request.getAttribute("liferay-frontend:logo-selector:imageURL");
String randomNamespace = (String)request.getAttribute("liferay-frontend:logo-selector:randomNamespace");
String uploadImageURL = (String)request.getAttribute("liferay-frontend:logo-selector:uploadImageURL");

long fileEntryId = ParamUtil.getLong(request, "fileEntryId");
%>

<div class="taglib-logo-selector" id="<%= randomNamespace %>taglibLogoSelector">
	<div class="taglib-logo-selector-content" id="<%= randomNamespace %>taglibLogoSelectorContent">
		<span class="lfr-change-logo">
			<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="avatar img-fluid mw-100" id="<%= randomNamespace %>avatar" src="<%= HtmlUtil.escape(imageURL) %>" />
		</span>

		<c:if test='<%= Validator.isNull(imageURL) || imageURL.contains("/spacer.png") %>'>
			<p class="text-muted" id="<%= randomNamespace %>emptyResultMessage">
				<liferay-ui:message key="none" />
			</p>
		</c:if>

		<div class="mb-4 mt-3 portrait-icons">
			<div class="btn-group button-holder">
				<aui:button aria-label='<%= LanguageUtil.get(request, "change-image") %>' cssClass="edit-logo modify-link mr-3" value="change" />

				<aui:button aria-label='<%= LanguageUtil.get(request, "delete-image") %>' cssClass="delete-logo modify-link" disabled="<%= defaultLogo && (fileEntryId == 0) %>" value="delete" />
			</div>

			<aui:input name="deleteLogo" type="hidden" value='<%= ParamUtil.getBoolean(request, "deleteLogo") %>' />

			<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
		</div>
	</div>
</div>

<aui:script use="liferay-logo-selector">
	new Liferay.LogoSelector({
		boundingBox: '#<%= randomNamespace %>taglibLogoSelector',
		contentBox: '#<%= randomNamespace %>taglibLogoSelectorContent',
		defaultLogo: '<%= defaultLogo %>',
		defaultLogoURL: '<%= defaultLogoURL %>',
		editLogoURL: '<%= uploadImageURL %>',
		portletNamespace: '<portlet:namespace />',
		randomNamespace: '<%= randomNamespace %>',
	}).render();
</aui:script>