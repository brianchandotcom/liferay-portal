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

<%@ include file="/dynamic_section/init.jsp" %>

<%
List<ObjectValuePair<SharingEntry, User>> sharingEntryToUserOVPs = (List<ObjectValuePair<SharingEntry, User>>)request.getAttribute("info_panel_file_entry.jsp-sharingEntryToUserOVPs");
%>

<div class="autofit-row sidebar-panel widget-metadata">
	<div class="autofit-col inline-item-before">

		<%
		FileEntry fileEntry = (FileEntry)request.getAttribute("info_panel.jsp-fileEntry");

		User owner = UserLocalServiceUtil.fetchUser(fileEntry.getUserId());
		%>

		<div class="lfr-portal-tooltip" data-title="<%= LanguageUtil.format(resourceBundle, "x-is-the-owner", owner.getFullName()) %>">
			<liferay-ui:user-portrait
				cssClass="user-icon-lg"
				user="<%= owner %>"
			/>
		</div>
	</div>

	<div class="autofit-col autofit-col-expand inline-item-after">
		<div class="autofit-row">

			<%
			for (ObjectValuePair<SharingEntry, User> sharingEntryToUserOVP : sharingEntryToUserOVPs) {
				User sharingEntryToUser = sharingEntryToUserOVP.getValue();
			%>

				<div class="autofit-col">
					<div class="lfr-portal-tooltip" data-title="<%= sharingEntryToUser.getFullName() %>">
						<liferay-ui:user-portrait
							cssClass="user-icon-lg"
							user="<%= sharingEntryToUser %>"
						/>
					</div>
				</div>

			<%
			}

			int countSharingEntryToUserIds = GetterUtil.getInteger(request.getAttribute("info_panel_file_entry.jsp-countSharingEntryToUserIds"));
			%>

			<c:if test="<%= countSharingEntryToUserIds > 4 %>">

				<%
				int moreCollaboratorsCount = countSharingEntryToUserIds - 4;
				%>

				<div class="lfr-portal-tooltip rounded-circle sticker sticker-lg sticker-secondary" data-title="<%= LanguageUtil.format(resourceBundle, (moreCollaboratorsCount == 1) ? "x-more-collaborator" : "x-more-collaborators", moreCollaboratorsCount) %>">
					+<%= moreCollaboratorsCount %>
				</div>
			</c:if>
		</div>
	</div>
</div>

<div id="<portlet:namespace />ManageCollaborators_<%= fileEntry.getFileEntryId() %>"></div>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/dynamic_section/css/ManageCollaborators.css")) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<%
Map<String, Object> context = new HashMap<>();

JSONArray collaboratorsJSONArray = JSONFactoryUtil.createJSONArray();

for (ObjectValuePair<SharingEntry, User> sharingEntryToUserOVP : sharingEntryToUserOVPs) {
	SharingEntry sharingEntry = sharingEntryToUserOVP.getKey();
	User sharingEntryToUser = sharingEntryToUserOVP.getValue();

	JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

	userJSONObject.put("id", sharingEntryToUser.getUserId());
	userJSONObject.put("imageSrc", sharingEntryToUser.getPortraitURL(themeDisplay));
	userJSONObject.put("name", sharingEntryToUser.getFullName());
	userJSONObject.put("sharingEntryId", sharingEntry.getSharingEntryId());

	collaboratorsJSONArray.put(userJSONObject);
}

context.put("collaborators", collaboratorsJSONArray);

String portletId = PortletProviderUtil.getPortletId(SharingEntry.class.getName(), PortletProvider.Action.EDIT);

PortletURL sharingEntryEditURL = liferayPortletResponse.createLiferayPortletURL(portletId, PortletRequest.ACTION_PHASE);

sharingEntryEditURL.setParameter(ActionRequest.ACTION_NAME, "/sharing/manage_collaborators");

context.put("uri", sharingEntryEditURL.toString());

context.put("spritemap", themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
%>

<soy:component-renderer
	componentId='<%= liferayPortletResponse.getNamespace() + "manageCollaborators" %>'
	context="<%= context %>"
	module="sharing-document-library/dynamic_section/js/ManageCollaborators.es"
	templateNamespace="com.liferay.sharing.document.library.ManageCollaborators.render"
/>