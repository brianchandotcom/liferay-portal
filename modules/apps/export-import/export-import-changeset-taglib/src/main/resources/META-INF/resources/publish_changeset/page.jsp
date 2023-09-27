<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publish_changeset/init.jsp" %>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.EXPORT_IMPORT_PORTLET_INFO) && showMenuItem %>">
	<liferay-ui:icon
		message="publish-to-live"
		url='<%=
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, ChangesetPortletKeys.CHANGESET, PortletRequest.ACTION_PHASE)
			).setActionName(
				"/export_import_changeset/export_import_changeset"
			).setMVCRenderCommandName(
				"/export_import_changeset/export_import_changeset"
			).setCMD(
				ChangesetConstants.PUBLISH_CHANGESET
			).setBackURL(
				currentURL
			).setParameter(
				"changesetUuid", changesetUuid
			).setParameter(
				"groupId", changesetGroupId
			).setParameter(
				"portletId", portletDisplay.getId()
			).buildString()
		%>'
	/>
</c:if>