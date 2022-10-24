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

<c:if test="<%= (themeDisplay.getURLPublishToLive() != null) || layout.isTypeControlPanel() %>">

	<%
	String tabs3 = ParamUtil.getString(request, "tabs3", "new-publish-process");

	PortletURL portletURL = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCRenderCommandName(
		"/export_import/publish_portlet"
	).setPortletResource(
		portletResource
	).buildPortletURL();
	%>

	<clay:navigation-bar
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					portletURL.setParameter("tabs3", "new-publish-process");

					add(
						navigationItem -> {
							navigationItem.setActive(tabs3.equals("new-publish-process"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "new-publish-process"));
						});

					Group scopeGroup = themeDisplay.getScopeGroup();

					if (!scopeGroup.isStagedRemotely()) {
						portletURL.setParameter("tabs3", "copy-from-live");

						add(
							navigationItem -> {
								navigationItem.setActive(tabs3.equals("copy-from-live"));
								navigationItem.setHref(portletURL.toString());
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "copy-from-live"));
							});
					}

					portletURL.setParameter("tabs3", "current-and-previous");

					add(
						navigationItem -> {
							navigationItem.setActive(tabs3.equals("current-and-previous"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "current-and-previous"));
						});
				}
			}
		%>'
	/>
</c:if>