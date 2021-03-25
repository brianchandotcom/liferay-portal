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

<%@ include file="/publish_changeset/init.jsp" %>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.EXPORT_IMPORT_PORTLET_INFO) && showMenuItem %>">
	<liferay-ui:icon
		message="publish-to-live"
		url='<%=
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, ChangesetPortletKeys.CHANGESET, PortletRequest.ACTION_PHASE)
			).setMVCRenderCommandName(
				"/export_import_changeset/export_import_changeset"
			).setActionName(
				"/export_import_changeset/export_import_changeset"
			).setParameter(
				"cmd", ChangesetConstants.PUBLISH_CHANGESET
			).setParameter(
				"backURL", currentURL
			).setParameter(
				"groupId", changesetGroupId
			).setParameter(
				"changesetUuid", changesetUuid
			).setParameter(
				"portletId", portletDisplay.getId()
			).buildString()
		%>'
	/>
</c:if>