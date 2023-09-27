<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/export_entity/init.jsp" %>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.EXPORT_IMPORT_PORTLET_INFO) && showMenuItem %>">
	<liferay-ui:icon
		message="export"
		url='<%=
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, ChangesetPortletKeys.CHANGESET, PortletRequest.ACTION_PHASE)
			).setActionName(
				"exportImportEntity"
			).setMVCRenderCommandName(
				"exportImportEntity"
			).setCMD(
				Constants.EXPORT
			).setParameter(
				"classNameId", classNameId
			).setParameter(
				"groupId", exportEntityGroupId
			).setParameter(
				"portletId", portletDisplay.getId()
			).setParameter(
				"uuid", uuid
			).buildString()
		%>'
	/>
</c:if>