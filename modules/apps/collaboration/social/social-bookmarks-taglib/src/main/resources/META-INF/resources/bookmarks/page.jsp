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
		<c:when test='<%= displayStyle.equals("menu") %>'>
			<clay:dropdown-menu
				label="<%= LanguageUtil.get(request, "share") %>"
				icon="share"
				style="secondary"
				triggerCssClasses="btn-outline-borderless btn-sm"
				items="<%=
					new JSPDropdownItemList(pageContext) {
						{
							for (int i = 0; i < types.length; i++) {
								SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(types[i]);
								final String type = types[i];

								if (socialBookmark != null) {
									add(
										dropdownItem -> {
											dropdownItem.setHref("javascript:" + SocialBookmarksTagUtil.getClickJSCall(className, classPK, type, socialBookmark.getPostURL(title, url), url));
											dropdownItem.setLabel(socialBookmark.getName(request.getLocale()));
										});
								}
							}
						}
					}
				%>"
			/>
		</c:when>
		<c:otherwise>
			<ul class="list-unstyled <%= displayStyle %>">

				<%
				int maxInlineElements = 3;

				for (int i = 0; i < Math.min(types.length, maxInlineElements); i++) {
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
			if (types.length > maxInlineElements) {
			%>

				<clay:dropdown-menu
					icon="share"
					style="secondary"
					triggerCssClasses="btn-outline-borderless btn-sm"
					items="<%=
						new JSPDropdownItemList(pageContext) {
							{
								for (int i = maxInlineElements; i < types.length; i++) {
									SocialBookmark socialBookmark = SocialBookmarksRegistryUtil.getSocialBookmark(types[i]);
									final String type = types[i];

									if (socialBookmark != null) {
										add(
											dropdownItem -> {
												dropdownItem.setHref("javascript:" + SocialBookmarksTagUtil.getClickJSCall(className, classPK, type, socialBookmark.getPostURL(title, url), url));
												dropdownItem.setLabel(socialBookmark.getName(request.getLocale()));
											});
									}
								}
							}
						}
					%>"
				/>

			<%
			}
			%>

		</c:otherwise>
	</c:choose>

	<liferay-util:html-bottom
		outputKey="social_bookmarks"
	>
		<aui:script>
			function socialBookmarks_handleItemClick(className, classPK, type, shareURL, url) {
				var SHARE_WINDOW_HEIGHT = 436;
				var SHARE_WINDOW_WIDTH = 626;

				var shareWindowFeatures = [
					'left=' + (window.innerWidth / 2 - SHARE_WINDOW_WIDTH / 2),
					'height=' + SHARE_WINDOW_HEIGHT,
					'toolbar=0',
					'top=' + (window.innerHeight / 2 - SHARE_WINDOW_HEIGHT / 2),
					'status=0',
					'width=' + SHARE_WINDOW_WIDTH
				];

				window.open(shareURL, null, shareWindowFeatures.join()).focus();

				Liferay.fire(
					'socialBookmarks:share',
					{
						className: className,
						classPK: classPK,
						type: type,
						url: url
					}
				);

				return false;
			}
		</aui:script>
	</liferay-util:html-bottom>
</div>