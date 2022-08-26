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

<%@ include file="/sidebar_toggler_button/init.jsp" %>

<aui:a cssClass="<%= cssClass %>" href='<%= "#" + sidenavId %>' id="<%= StringUtil.randomId() %>">
	<c:if test="<%= Validator.isNotNull(icon) %>">
		<aui:icon cssClass="icon-monospaced" image="<%= icon %>" markupView="lexicon" />
	</c:if>

	<span><liferay-ui:message key="<%= label %>" /></span>
</aui:a>

<aui:script>
	var sidenavToggle = document.querySelector('[href="#<%= sidenavId %>"]');

	if (!Liferay.SideNavigation.instance(sidenavToggle)) {
		var sidenavInstance = Liferay.SideNavigation.initialize(sidenavToggle, {
			position: 'right',
			type: 'relative',
			typeMobile: '<%= typeMobile %>',
			width: '320px',
		});

		sidenavInstance.on('closed.lexicon.sidenav', (event) => {
			Liferay.Util.Session.set(
				'com.liferay.info.panel_<%= sidenavId %>',
				'closed'
			);
		});

		sidenavInstance.on('open.lexicon.sidenav', (event) => {
			Liferay.Util.Session.set(
				'com.liferay.info.panel_<%= sidenavId %>',
				'open'
			);
		});
	}
</aui:script>