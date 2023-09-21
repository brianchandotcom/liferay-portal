<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-frontend:component
	componentId="MoveKBObject"
	module="admin/js/MoveKBObject"
/>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />moveFolder',
		(event, data) => {
			Liferay.componentReady('MoveKBObject').then((component) => {
				component.openModal(data);
			});
		}
	);
</aui:script>