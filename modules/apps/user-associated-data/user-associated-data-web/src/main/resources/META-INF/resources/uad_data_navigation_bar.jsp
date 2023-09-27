<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String servletPath = GetterUtil.getString(request.getServletPath());

PortletURL baseURL = PortletURLBuilder.createRenderURL(
	liferayPortletResponse
).setParameter(
	"p_u_i_d", selectedUser.getUserId()
).buildPortletURL();
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						boolean active = servletPath.equals("/review_uad_data.jsp") || servletPath.equals("/view_uad_hierarchy.jsp");

						PortletURL reviewDataURL = null;

						try {
							reviewDataURL = PortletURLBuilder.create(
								PortletURLUtil.clone(baseURL, renderResponse)
							).setMVCRenderCommandName(
								"/user_associated_data/review_uad_data"
							).buildPortletURL();
						}
						catch (PortletException e) {
							reviewDataURL = baseURL;
						}

						navigationItem.setActive(active);
						navigationItem.setHref(reviewDataURL.toString());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "review-data"));
					});
				add(
					navigationItem -> {
						boolean active = servletPath.equals("/anonymize_nonreviewable_uad_data.jsp");

						PortletURL nonreviewableDataURL = null;

						try {
							nonreviewableDataURL = PortletURLBuilder.create(
								PortletURLUtil.clone(baseURL, renderResponse)
							).setMVCRenderCommandName(
								"/user_associated_data/anonymize_nonreviewable_uad_data"
							).buildPortletURL();
						}
						catch (PortletException e) {
							nonreviewableDataURL = baseURL;
						}

						navigationItem.setActive(active);
						navigationItem.setHref(nonreviewableDataURL.toString());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "auto-anonymize-data"));
					});
			}
		}
	%>'
/>