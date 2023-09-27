<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/logo_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_logo_selector") + StringPool.UNDERLINE;

int aspectRatio = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:logo-selector:aspectRatio"));
String currentLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:currentLogoURL");
boolean defaultLogo = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:defaultLogo"));
String defaultLogoURL = (String)request.getAttribute("liferay-ui:logo-selector:defaultLogoURL");
String editLogoFn = GetterUtil.getString((String)request.getAttribute("liferay-ui:logo-selector:editLogoFn"));
String logoDisplaySelector = (String)request.getAttribute("liferay-ui:logo-selector:logoDisplaySelector");
boolean preserveRatio = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:preserveRatio"));
boolean showBackground = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:showBackground"));
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:logo-selector:showButtons"));
String tempImageFileName = (String)request.getAttribute("liferay-ui:logo-selector:tempImageFileName");

boolean deleteLogo = ParamUtil.getBoolean(request, "deleteLogo");
long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

String imageURL = null;

if (deleteLogo) {
	imageURL = defaultLogoURL;
}
else if (fileEntryId > 0) {
	ResourceURL previewURL = PortletURLFactoryUtil.create(portletRequest, PortletKeys.IMAGE_UPLOADER, PortletRequest.RESOURCE_PHASE);

	previewURL.setParameter("mvcRenderCommandName", "/image_uploader/upload_image");
	previewURL.setParameter(Constants.CMD, Constants.GET_TEMP);
	previewURL.setParameter("tempImageFileName", tempImageFileName);

	imageURL = previewURL.toString();
}
else {
	imageURL = currentLogoURL;
}
%>

<c:choose>
	<c:when test="<%= showButtons %>">
		<div class="taglib-logo-selector" id="<%= randomNamespace %>taglibLogoSelector">
			<div class="taglib-logo-selector-content" id="<%= randomNamespace %>taglibLogoSelectorContent">
				<span class="lfr-change-logo <%= showBackground ? "show-background" : StringPool.BLANK %>">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="avatar img-fluid mw-100" id="<%= randomNamespace %>avatar" src="<%= HtmlUtil.escape(imageURL) %>" />
				</span>

				<c:if test='<%= Validator.isNull(imageURL) || imageURL.contains("/spacer.png") %>'>
					<p class="text-muted" id="<%= randomNamespace %>emptyResultMessage">
						<liferay-ui:message key="none" />
					</p>
				</c:if>

				<div class="mb-4 mt-3 portrait-icons">
					<div class="btn-group">
						<div class="btn-group-item">
							<aui:button aria-label='<%= LanguageUtil.get(request, "change-image") %>' cssClass="edit-logo modify-link" value="change" />
						</div>

						<div class="btn-group-item">
							<aui:button aria-label='<%= LanguageUtil.get(request, "delete-image") %>' cssClass="delete-logo modify-link" disabled="<%= defaultLogo && (fileEntryId == 0) %>" value="delete" />
						</div>
					</div>

					<aui:input name="deleteLogo" type="hidden" value="<%= deleteLogo %>" />

					<aui:input name="fileEntryId" type="hidden" value="<%= fileEntryId %>" />
				</div>
			</div>
		</div>

		<liferay-portlet:renderURL portletName="<%= PortletKeys.IMAGE_UPLOADER %>" var="uploadImageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<liferay-portlet:param name="mvcRenderCommandName" value="/image_uploader/upload_image" />
			<liferay-portlet:param name="randomNamespace" value="<%= randomNamespace %>" />
			<liferay-portlet:param name="aspectRatio" value="<%= String.valueOf(aspectRatio) %>" />
			<liferay-portlet:param name="currentLogoURL" value="<%= currentLogoURL %>" />
			<liferay-portlet:param name="preserveRatio" value="<%= String.valueOf(preserveRatio) %>" />
			<liferay-portlet:param name="tempImageFileName" value="<%= tempImageFileName %>" />
		</liferay-portlet:renderURL>

		<aui:script use="liferay-logo-selector">
			new Liferay.LogoSelector(
				{
					boundingBox: '#<%= randomNamespace %>taglibLogoSelector',
					contentBox: '#<%= randomNamespace %>taglibLogoSelectorContent',
					defaultLogo: '<%= defaultLogo %>',
					defaultLogoURL: '<%= defaultLogoURL %>',
					editLogoFn: '<%= editLogoFn %>',
					editLogoURL: '<%= uploadImageURL %>',
					logoDisplaySelector: '<%= logoDisplaySelector %>',
					portletNamespace: '<portlet:namespace />',
					randomNamespace: '<%= randomNamespace %>'
				}
			).render();
		</aui:script>
	</c:when>
	<c:otherwise>
		<div class="taglib-logo-selector" id="<%= randomNamespace %>taglibLogoSelector">
			<div class="taglib-logo-selector-content" id="<%= randomNamespace %>taglibLogoSelectorContent">
				<span class="lfr-change-logo <%= showBackground ? "show-background" : StringPool.BLANK %>">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="avatar img-fluid" id="<%= randomNamespace %>avatar" src="<%= HtmlUtil.escape(imageURL) %>" />
				</span>
			</div>
		</div>
	</c:otherwise>
</c:choose>