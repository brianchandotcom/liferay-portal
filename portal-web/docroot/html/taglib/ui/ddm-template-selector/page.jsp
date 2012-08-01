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

<%@ include file="/html/taglib/init.jsp" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-selector:classNameId"));
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:ddm-template-selector:icon"), "configuration");
String message = (String)request.getAttribute("liferay-ui:ddm-template-selector:message");
String refreshURL = (String)request.getAttribute("liferay-ui:ddm-template-selector:refreshURL");

long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(request, PortletKeys.PORTLET_DISPLAY_TEMPLATES, controlPanelPlid, PortletRequest.RENDER_PHASE);

liferayPortletURL.setDoAsGroupId(scopeGroupId);
liferayPortletURL.setParameter("struts_action", "/dynamic_data_mapping/view_template");
liferayPortletURL.setPortletMode(PortletMode.VIEW);
liferayPortletURL.setWindowState(LiferayWindowState.POP_UP);

String liferayPortletURLString = liferayPortletURL.toString();

liferayPortletURLString = HttpUtil.addParameter(liferayPortletURLString, "classNameId", classNameId);
%>

<liferay-ui:icon cssClass="manage-display-templates" id="selectDDMTemplate" image="<%= icon %>" label="<%= true %>" message="<%= message %>" url="javascript:;" />

<aui:script use="aui-base">
	var selectDDMTemplate = A.one('#<portlet:namespace />selectDDMTemplate');

	if (selectDDMTemplate) {
		var windowId = A.guid();

		selectDDMTemplate.on(
			'click',
			function (event) {
				Liferay.Util.openWindow(
					{
						dialog: {
							constrain: true,
							width: 820
						},
						id: windowId,
						title: '<%= UnicodeLanguageUtil.get(pageContext, "application-display-templates") %>',
						uri: '<%= liferayPortletURLString %>'
					},
					function (ddmPortletWindow) {
						ddmPortletWindow.once(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									submitForm(document.<portlet:namespace />fm, '<%= refreshURL %>');
								}
							}
						);
					}
				);
			}
		);
	}
</aui:script>