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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Document doc = (Document)objArray[0];
Summary summary = (Summary)objArray[1];

String articleId = doc.get(Field.ARTICLE_ID);
long articleGroupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));

List hitLayoutIds = JournalContentSearchLocalServiceUtil.getLayoutIds(layout.getGroupId(), false, articleId);
%>

<%= summary.getHighlightedContent() %><br />

<c:choose>
	<c:when test="<%= !hitLayoutIds.isEmpty() %>">
		<span style="font-size: xx-small;">

		<%
		for (int i = 0; i < hitLayoutIds.size(); i++) {
			Long hitLayoutId = (Long)hitLayoutIds.get(i);

			Layout hitLayout = null;

			try {
				hitLayout = LayoutLocalServiceUtil.getLayout(layout.getGroupId(), false, hitLayoutId.longValue());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Journal content search is stale and contains layout {" + layout.getGroupId() + ", " + false + ", " + hitLayoutId.longValue() + "}");
				}

				continue;
			}

			String hitLayoutURL = PortalUtil.getLayoutFullURL(hitLayout, themeDisplay);
		%>

			<br /><a href="<%= hitLayoutURL %>"><%= StringUtil.shorten(hitLayoutURL, 100) %></a>

		<%
		}
		%>

		</span>
	</c:when>
	<c:when test="<%= Validator.isNotNull(targetPortletId) %>">
		<span style="font-size: xx-small;">
			<br />

			<%
			PortletURL webContentPortletURL = PortletURLFactoryUtil.create(request, targetPortletId, plid, PortletRequest.RENDER_PHASE);

			webContentPortletURL.setParameter("groupId", String.valueOf(articleGroupId));
			webContentPortletURL.setParameter("articleId", articleId);
			%>

			<a href="<%= webContentPortletURL.toString() %>"><%= StringUtil.shorten(webContentPortletURL.toString(), 100) %></a>
		</span>
	</c:when>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal_content_search.article_content_jsp");
%>