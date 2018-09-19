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

<%@ include file="/shared_with_me/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SharingEntry sharingEntry = (SharingEntry)row.getObject();

SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext = (SharedWithMeViewDisplayContext)renderRequest.getAttribute(SharedWithMeViewDisplayContext.class.getName());

PortletURL editPortletURL = sharedWithMeViewDisplayContext.getURLEdit(sharingEntry, liferayPortletRequest, liferayPortletResponse);

editPortletURL.setWindowState(LiferayWindowState.POP_UP);

editPortletURL.setParameter("hideDefaultSuccessMessage", Boolean.TRUE.toString());
editPortletURL.setParameter("showHeader", Boolean.FALSE.toString());

PortletURL redirectURL = liferayPortletResponse.createLiferayPortletURL(plid, portletDisplay.getId(), PortletRequest.RENDER_PHASE, false);

redirectURL.setParameter("mvcRenderCommandName", "/shared_with_me/close_sharing_entry_edit_dialog");

redirectURL.setWindowState(LiferayWindowState.POP_UP);

editPortletURL.setParameter("redirect", redirectURL.toString());

Map<String, Object> data = new HashMap<String, Object>();

data.put("destroyOnHide", true);
data.put("id", HtmlUtil.escape(renderResponse.getNamespace()) + "editAsset");
data.put("title", LanguageUtil.format(request, "edit-x", HtmlUtil.escape(sharedWithMeViewDisplayContext.getTitle(sharingEntry)), false));
%>

<c:if test="<%= sharedWithMeViewDisplayContext.hasEditPermission(sharingEntry) %>">
	<liferay-ui:icon-menu
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="<%= StringPool.BLANK %>"
		showWhenSingleIcon="<%= true %>"
	>
		<liferay-ui:icon
			data="<%= data %>"
			message='<%= LanguageUtil.format(request, "edit-x", HtmlUtil.escape(sharedWithMeViewDisplayContext.getTitle(sharingEntry)), false) %>'
			method="get"
			url="<%= editPortletURL.toString() %>"
			useDialog="<%= true %>"
		/>
	</liferay-ui:icon-menu>
</c:if>