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
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);

final WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

PortletURL viewPageURL = PortletURLFactoryUtil.create(request, WikiPortletKeys.WIKI, PortletRequest.ACTION_PHASE);

viewPageURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view");
viewPageURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));
viewPageURL.setPortletMode(PortletMode.VIEW);
viewPageURL.setWindowState(WindowState.MAXIMIZED);

StringBundler sb = new StringBundler(8);

sb.append(themeDisplay.getPathMain());
sb.append("/wiki/get_page_attachment?p_l_id=");
sb.append(themeDisplay.getPlid());
sb.append("&nodeId=");
sb.append(wikiPage.getNodeId());
sb.append("&title=");
sb.append(URLCodec.encodeURL(wikiPage.getTitle()));
sb.append("&fileName=");

final String redirectURL = currentURL;

final HttpServletRequest httpServletRequest = request;

WikiPageDisplay pageDisplay = WikiPageLocalServiceUtil.getPageDisplay(
	wikiPage, viewPageURL,
	new Supplier<PortletURL>() {

		public PortletURL get() {
			PortletURL editPageURL = PortletURLFactoryUtil.create(httpServletRequest, WikiPortletKeys.WIKI, PortletRequest.ACTION_PHASE);

			editPageURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/edit_page");
			editPageURL.setParameter("redirect", redirectURL);
			editPageURL.setParameter("nodeId", String.valueOf(wikiPage.getNodeId()));

			try {
				editPageURL.setPortletMode(PortletMode.VIEW);
				editPageURL.setWindowState(WindowState.MAXIMIZED);
			}
			catch (Exception e) {
				ReflectionUtil.throwException(e);
			}

			return editPageURL;
		}

	},
	sb.toString(), ServiceContextFactory.getInstance(request));
%>

<%= StringUtil.shorten(HtmlUtil.stripHtml(pageDisplay.getFormattedContent()), abstractLength) %>