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

<%@ include file="/init.jsp" %>

<%
String cssClass = "control-menu-nav-item";

if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
	cssClass += " control-menu-nav-item-content";
}

String headerTitle = HtmlUtil.escape(layout.getName(locale));

String portletId = ParamUtil.getString(request, "p_p_id");

if (Validator.isNotNull(portletId) && layout.isSystem() && !layout.isTypeControlPanel() && Objects.equals(layout.getFriendlyURL(), PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {
	headerTitle = PortalUtil.getPortletTitle(portletId, locale);
}

boolean hasDraft = false;
boolean isDraft = false;

if (Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT) || Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
	Layout draftLayout = layout.fetchDraftLayout();

	if (draftLayout != null) {
		boolean published = GetterUtil.getBoolean(draftLayout.getTypeSettingsProperty("published"));

		if ((draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT) || !published) {
			hasDraft = true;
		}
	}
	else {
		boolean published = GetterUtil.getBoolean(layout.getTypeSettingsProperty("published"));

		if ((layout.getStatus() == WorkflowConstants.STATUS_DRAFT) || !published) {
			hasDraft = true;

			String mode = ParamUtil.getString(request, "p_l_mode");

			if (!Objects.equals(mode, Constants.EDIT)) {
				isDraft = true;
			}
		}
	}
}
%>

<li class="<%= cssClass %>">
	<span class="control-menu-level-1-heading text-truncate" data-qa-id="headerTitle">
		<%= headerTitle %><c:if test="<%= hasDraft %>"><sup class="small">*</sup></c:if>
	</span>

	<c:if test="<%= isDraft %>">
		<span class="bg-transparent label label-inverse-secondary ml-2 mr-0">
			<span class="label-item label-item-expand">
				<liferay-ui:message key="draft" />
			</span>
		</span>
	</c:if>
</li>