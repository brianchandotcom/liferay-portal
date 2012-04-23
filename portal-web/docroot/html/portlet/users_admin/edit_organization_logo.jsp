<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

String logoURL = StringPool.BLANK;

if (publicLayoutSetId != 0) {
	LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(publicLayoutSetId);

	logoURL = themeDisplay.getPathImage() + "/organization_logo?img_id=" + publicLayoutSet.getLogoId() + "&t=" + WebServerServletTokenUtil.getToken(publicLayoutSet.getLogoId());
}
%>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "request_processed") %>'>

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
			<aui:input name="cropRegion" type="hidden" />
			<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
			<aui:input name="publicLayoutSetId" type="hidden" value="<%= publicLayoutSetId %>" />


			<liferay-ui:error exception="<%= ImageTypeException.class %>" message="please-enter-a-file-with-a-valid-file-type" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<aui:fieldset>
				<aui:input label="upload-a-logo-for-the-organization-pages-that-will-be-used-instead-of-the-default-enterprise-logo-in-both-public-and-private-pages" name="fileName" size="50" type="file" />

				<div class="lfr-change-logo portrait-preview" id="<portlet:namespace />portraitPreview">
					<img class="portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= HtmlUtil.escape(logoURL) %>" />
				</div>

				<aui:button-row>
					<aui:button name="submitButton" type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<aui:script use="aui-io,json,aui-image-cropper,aui-loading-mask">
			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />fileName);
			</c:if>

			var imageCropper;

			var cropRegionNode = A.one('#<portlet:namespace />cropRegion');
			var fileNameNode = A.one('#<portlet:namespace />fileName');
			var formNode = A.one('#<portlet:namespace />fm');
			var portraitPreview = A.one('#<portlet:namespace />portraitPreview');
			var portraitPreviewImg = A.one('#<portlet:namespace />portraitPreviewImg');
			var submitButton = A.one('#<portlet:namespace />submitButton');

			var imageLoadHandler = function(event) {
				if (portraitPreviewImg.attr('src').indexOf('spacer.png') == -1) {
					if (imageCropper) {
						imageCropper.enable();

						imageCropper.syncImageUI();

						imageCropper.setAttrs(
							{
								cropHeight: Math.max(portraitPreviewImg.height() * 0.3, 50),
								cropWidth: Math.max(portraitPreviewImg.width() * 0.3, 50),
								x: 0,
								y: 0
							}
						);
					} else {
						imageCropper = new A.ImageCropper(
							{
								srcNode: portraitPreviewImg
							}
						).render();
					}

					submitButton.attr('disabled', false);
					submitButton.ancestor('.aui-button').removeClass('aui-button-disabled');
				}
			};

			var fileNameChangeHandler = function(event) {
				var previewURL = '<portlet:resourceURL><portlet:param name="struts_action" value="/users_admin/edit_organization_logo" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" /></portlet:resourceURL>';

				var uploadURL = '<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_organization_logo" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /></portlet:actionURL>';

				portraitPreviewImg.addClass('loading');

				portraitPreviewImg.attr('src', '<%= themeDisplay.getPathThemeImages() %>/spacer.png');

				if (imageCropper) {
					imageCropper.disable();
				}

				A.io.request(
					uploadURL,
					{
						method: 'post',
						form: {
							id: '<portlet:namespace />fm',
							upload: true
						},
						on: {
							complete: function(event) {
								previewURL = Liferay.Util.addParams('ts=' + A.Lang.now(), previewURL);

								portraitPreviewImg.attr('src', previewURL);

								portraitPreviewImg.removeClass('loading');
							},
							start: function() {
								submitButton.attr('disabled', true);

								submitButton.ancestor('.aui-button').addClass('aui-button-disabled');
							}
						}
					}
				);
			};

			var submitHandler = function(event) {
				if (imageCropper) {
					cropRegionNode.val(A.JSON.stringify(imageCropper.get('region')));
				}
			};

			fileNameNode.on('change', fileNameChangeHandler);
			formNode.on('submit', submitHandler);
			portraitPreviewImg.on('load', imageLoadHandler);
		</aui:script>
	</c:otherwise>
</c:choose>