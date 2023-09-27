<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/import_entity/init.jsp" %>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.EXPORT_IMPORT_PORTLET_INFO) %>">
	<liferay-ui:icon
		message="import"
		url='<%=
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, ExportImportPortletKeys.EXPORT_IMPORT, PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/import_portlet.jsp"
			).setParameter(
				"portletResource", ChangesetPortletKeys.CHANGESET
			).buildString()
		%>'
	/>
</c:if>