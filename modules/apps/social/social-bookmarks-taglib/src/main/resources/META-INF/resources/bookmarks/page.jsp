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

<%@ include file="/bookmarks/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_social_bookmarks_page") + StringPool.UNDERLINE;
%>

<liferay-util:html-top
	outputKey="social_bookmarks_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="taglib-social-bookmarks" id="<%= randomNamespace %>socialBookmarks">
	<c:choose>
		<c:when test='<%= displayStyle.equals("menu") || BrowserSnifferUtil.isMobile(request) %>'>
			<clay:dropdown-menu
				borderless="<%= true %>"
				displayType="secondary"
				dropdownItems="<%= SocialBookmarksTagUtil.getDropdownItems(request.getLocale(), types, className, classPK, title, url) %>"
				icon="share"
				label='<%= BrowserSnifferUtil.isMobile(request) ? null : "share" %>'
				propsTransformer="bookmarks/SocialBookmarksDropdownPropsTransformer"
				small="<%= true %>"
			/>
		</c:when>
		<c:otherwise>
			<ul class="list-unstyled <%= displayStyle %>">

				<%
				for (int i = 0; i < Math.min(types.length, maxInlineItems); i++) {
					SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(types[i]);
					String styleClass = "taglib-social-bookmark-" + types[i];
				%>

					<li class="taglib-social-bookmark <%= styleClass %>" onClick="<%= "return " + SocialBookmarksTagUtil.getClickJSCall(className, classPK, types[i], socialBookmark.getPostURL(title, url), url) %>">
						<liferay-social-bookmarks:bookmark
							displayStyle="<%= displayStyle %>"
							target="<%= target %>"
							title="<%= title %>"
							type="<%= types[i] %>"
							url="<%= url %>"
						/>
					</li>

				<%
				}
				%>

			</ul>

			<%
			if (types.length > maxInlineItems) {
			%>

				<%
				String[] remainingTypes = ArrayUtil.subset(types, maxInlineItems, types.length);
				%>

				<clay:dropdown-menu
					borderless="<%= true %>"
					displayType="secondary"
					dropdownItems="<%= SocialBookmarksTagUtil.getDropdownItems(request.getLocale(), remainingTypes, className, classPK, title, url) %>"
					icon="share"
					monospaced="<%= true %>"
					propsTransformer="bookmarks/SocialBookmarksDropdownPropsTransformer"
					small="<%= true %>"
					title="share"
				/>

			<%
			}
			%>

		</c:otherwise>
	</c:choose>
</div>