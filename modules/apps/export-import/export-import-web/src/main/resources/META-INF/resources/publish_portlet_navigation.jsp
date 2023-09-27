<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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