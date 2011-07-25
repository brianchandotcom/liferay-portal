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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

IGImage image = (IGImage)request.getAttribute(WebKeys.IMAGE_GALLERY_IMAGE);

IGFolder folder = image.getFolder();

Image largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());

String imageUrl = renderResponse.getNamespace() + "viewImage(" + largeImage.getImageId() + ", '" + ImageServletTokenUtil.getToken(largeImage.getImageId()) + "', '" + UnicodeFormatter.toString(image.getName()) + "', '" + UnicodeFormatter.toString(image.getDescription()) + "', " + largeImage.getWidth() + ", " + largeImage.getHeight() +")";
String webDavUrl = StringPool.BLANK;

if (portletDisplay.isWebDAVEnabled()) {
	StringBuilder sb = new StringBuilder();

	while (true) {
		sb.insert(0, HttpUtil.encodeURL(folder.getName(), true));
		sb.insert(0, StringPool.SLASH);

		if (folder.getParentFolderId() == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			break;
		}
		else {
			folder = IGFolderLocalServiceUtil.getFolder(folder.getParentFolderId());
		}
	}

	sb.append(StringPool.SLASH);
	sb.append(HttpUtil.encodeURL(image.getNameWithExtension(), true));

	Group group = themeDisplay.getScopeGroup();

	webDavUrl = themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav" + group.getFriendlyURL() + "/image_gallery" + sb.toString();
}

AssetEntry layoutAssetEntry = AssetEntryLocalServiceUtil.getEntry(IGImage.class.getName(), image.getImageId());

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
%>

<liferay-util:include page="/html/portlet/image_gallery/top_links.jsp" />

<c:if test="<%= folder != null %>">

	<%
	String parentFolderName = LanguageUtil.get(pageContext, "images-home");

	if (Validator.isNotNull(folder.getName())) {
		parentFolderName = folder.getName();
	}
	%>

	<portlet:renderURL var="backURL">
		<portlet:param name="struts_action" value="/image_gallery/view" />
		<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
	</portlet:renderURL>

	<liferay-ui:header
		backLabel="<%= parentFolderName %>"
		backURL="<%= backURL.toString() %>"
		localizeTitle="<%= false %>"
		title="<%= image.getName() %>"
	/>

</c:if>

<aui:layout>
	<aui:column columnWidth="<%= 75 %>" cssClass="lfr-asset-column lfr-asset-column-details" first="<%= true %>">
		<div class="lfr-asset-categories">
			<liferay-ui:asset-categories-summary
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
			/>
		</div>

		<div class="lfr-asset-tags">
			<liferay-ui:asset-tags-summary
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
				message="tags"
			/>
		</div>

		<div class="lfr-asset-description">
			<%= image.getDescription() %>
		</div>

		<liferay-ui:custom-attributes-available className="<%= IGImage.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= IGImage.class.getName() %>"
				classPK="<%= (image != null) ? image.getImageId() : 0 %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<div class="lfr-asset-metadata">
			<div class="lfr-asset-icon lfr-asset-author">
				<%= LanguageUtil.format(pageContext, "last-updated-by-x", HtmlUtil.escape(PortalUtil.getUserName(image.getUserId(), themeDisplay.getScopeGroupName()))) %>
			</div>

			<div class="lfr-asset-icon lfr-asset-date last">
				<%= dateFormatDate.format(image.getModifiedDate()) %>
			</div>
		</div>

		<aui:layout cssClass="lfr-asset-attributes">
			<aui:column columnWidth="<%= 15 %>">
				<div class="lfr-asset-field">
					<label><liferay-ui:message key="height" /></label>

					<%= largeImage.getHeight() %>
				</div>
			</aui:column>

			<aui:column columnWidth="<%= 15 %>">
				<div class="lfr-asset-field">
					<label><liferay-ui:message key="width" /></label>

					<%= largeImage.getWidth() %>
				</div>
			</aui:column>

			<aui:column columnWidth="<%= 15 %>">
				<div class="lfr-asset-field">
					<label><liferay-ui:message key="size" /></label>

					<%= TextFormatter.formatKB(largeImage.getSize(), locale) %>k
				</div>
			</aui:column>
		</aui:layout>

		<div class="entry-links">
			<liferay-ui:asset-links
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
			/>
		</div>

		<div class="lfr-asset-ratings">
			<liferay-ui:ratings
				className="<%= IGImage.class.getName() %>"
				classPK="<%= image.getImageId() %>"
			/>
		</div>

		<div class="lfr-asset-field">
			<label><liferay-ui:message key="url" /></label>

			<liferay-ui:input-resource
				url='<%= (Validator.isNull(themeDisplay.getCDNHost()) ? themeDisplay.getPortalURL() : "") + themeDisplay.getPathImage() + "/image_gallery?uuid=" + image.getUuid() + "&groupId=" + image.getGroupId() + "&t=" + ImageServletTokenUtil.getToken(image.getLargeImageId()) %>'
			/>
		</div>

		<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
			<div class="lfr-asset-field">

				<%
				String webDavHelpMessage = null;

				if (BrowserSnifferUtil.isWindows(request)) {
					webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-windows-help", new Object[] {"http://www.microsoft.com/downloads/details.aspx?FamilyId=17C36612-632E-4C04-9382-987622ED1D64", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV"});
				}
				else {
					webDavHelpMessage = LanguageUtil.format(pageContext, "webdav-help", "http://www.liferay.com/web/guest/community/wiki/-/wiki/Main/WebDAV");
				}
				%>

				<aui:field-wrapper helpMessage="<%= webDavHelpMessage %>" label="webdav-url">
					<liferay-ui:input-resource url="<%= webDavUrl %>" />
				</aui:field-wrapper>
			</div>
		</c:if>
	</aui:column>

	<aui:column columnWidth="<%= 25 %>" cssClass="lfr-asset-column lfr-asset-column-actions" last="<%= true %>">
		<div class="lfr-asset-summary">
			<a href="javascript:<%= imageUrl %>">
				<img alt="<%= image.getDescription() %>" class="lfr-asset-avatar" src='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + image.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(image.getSmallImageId()) %>' />
			</a>

			<div class="lfr-asset-name">
				<a href="javascript:<%= imageUrl %>">
					<%= image.getNameWithExtension() %>
				</a>
			</div>
		</div>

		<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

		request.setAttribute("view_image.jsp-view", Boolean.TRUE.toString());
		%>

		<liferay-ui:icon-menu showExpanded="<%= true %>">
			<%@ include file="/html/portlet/image_gallery/image_action.jspf" %>
		</liferay-ui:icon-menu>
	</aui:column>
</aui:layout>

<aui:script>
	var maxDimension = <%= PrefsPropsUtil.getInteger(PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION) %>;

	Liferay.provide(
		window,
		'<portlet:namespace />viewImage',
		function(id, token, name, description, width, height) {
			var A = AUI();

			var page = A.getBody().get('viewportRegion');

			var maxWidth = Math.max(page.right - 150, maxDimension);
			var maxHeight = Math.max(page.bottom - 150, maxDimension);

			var imgWidth = width;
			var imgHeight = height;

			if (imgWidth > maxWidth || imgHeight > maxHeight) {
				if (imgWidth > maxWidth) {
					var x = maxWidth / imgWidth;

					imgWidth = maxWidth;
					imgHeight = x * imgHeight;
				}

				if (imgHeight > maxHeight) {
					var y = maxHeight / imgHeight;

					imgHeight = maxHeight;
					imgWidth = y * imgWidth;
				}
			}

			var winWidth = imgWidth + 36;

			if (winWidth < maxDimension) {
				winWidth = maxDimension;
			}

			var messageId = "<portlet:namespace />popup_" + id;

			var html = "";

			html += "<div class='image-content'>";
			html += "<img alt='" + name + ". " + description + "' src='<%= themeDisplay.getPathImage() %>/image_gallery?img_id=" + id + "&t=" + token + "' style='height: " + imgHeight + "px; width" + imgWidth + "px;' />"
			html += "</div>"

			var popup = new A.Dialog(
				{
					bodyContent: html,
					centered: true,
					destroyOnClose: true,
					draggable: false,
					modal: true,
					title: false,
					width: winWidth
				}
			).render();

			popup.get('boundingBox').addClass('portlet-image-gallery');
			popup.get('contentBox').addClass('image-popup');
		},
		['aui-dialog']
	);
</aui:script>

<%
IGUtil.addPortletBreadcrumbEntries(image, request, renderResponse);
%>