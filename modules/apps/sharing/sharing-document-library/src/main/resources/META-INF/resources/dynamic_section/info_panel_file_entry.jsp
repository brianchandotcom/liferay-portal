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
			List<User> sharingEntryToUsers = (List<User>)request.getAttribute("info_panel_file_entry.jsp-sharingEntryToUsers");

			for (User sharingEntryToUser : sharingEntryToUsers) {
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