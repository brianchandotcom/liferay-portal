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

<%@ include file="/dynamic_include/init.jsp" %>

<%
PortletURL manageCollaboratorsURL = PortletURLBuilder.create(
	PortletProviderUtil.getPortletURL(request, SharingEntry.class.getName(), PortletProvider.Action.MANAGE)
).setWindowState(
	LiferayWindowState.POP_UP
).buildPortletURL();

PortletURL sharingURL = PortletURLBuilder.create(
	PortletProviderUtil.getPortletURL(request, SharingEntry.class.getName(), PortletProvider.Action.EDIT)
).setWindowState(
	LiferayWindowState.POP_UP
).buildPortletURL();
%>

<aui:script sandbox="<%= true %>">
	function showDialog(uri, title) {
		Liferay.Util.openModal({
			id: 'sharingDialog',
			iframeBodyCssClass: 'sharing-dialog',
			height: 475,
			size: 'md',
			title: title,
			url: uri,
		});
	}

	var Sharing = {
		manageCollaborators: function (classNameId, classPK) {
			var manageCollaboratorsParameters = {
				classNameId: classNameId,
				classPK: classPK,
			};

			var manageCollaboratorsURL = Liferay.Util.PortletURL.createPortletURL(
				'<%= manageCollaboratorsURL.toString() %>',
				manageCollaboratorsParameters
			);

			showDialog(
				manageCollaboratorsURL.toString(),
				'<%= LanguageUtil.get(resourceBundle, "manage-collaborators") %>'
			);
		},

		share: function (classNameId, classPK, title) {
			var sharingParameters = {
				classNameId: classNameId,
				classPK: classPK,
			};

			var sharingURL = Liferay.Util.PortletURL.createPortletURL(
				'<%= sharingURL.toString() %>',
				sharingParameters
			);

			showDialog(sharingURL.toString(), title);
		},
	};

	Liferay.Sharing = Sharing;
</aui:script>