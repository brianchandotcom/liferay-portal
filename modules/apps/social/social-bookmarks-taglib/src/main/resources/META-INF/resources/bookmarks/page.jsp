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

String dropdownMenuComponentId = randomNamespace + "socialBookmarksDropdownMenu";
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
				componentId="<%= dropdownMenuComponentId %>"
				dropdownItems="<%= SocialBookmarksTagUtil.getDropdownItems(request.getLocale(), types, className, classPK, title, url) %>"
				icon="share"
				label='<%= BrowserSnifferUtil.isMobile(request) ? null : LanguageUtil.get(request, "share") %>'
				style="secondary"
				triggerCssClasses="btn-outline-borderless btn-outline-secondary btn-sm"
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
					componentId="<%= dropdownMenuComponentId %>"
					dropdownItems="<%= SocialBookmarksTagUtil.getDropdownItems(request.getLocale(), remainingTypes, className, classPK, title, url) %>"
					icon="share"
					style="secondary"
					triggerCssClasses="btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
					triggerTitle='<%= LanguageUtil.get(request, "share") %>'
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
			function socialBookmarks_handleItemClick(
				event,
				className,
				classPK,
				type,
				postURL,
				url
			) {
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

				event.preventDefault();
				event.stopPropagation();

				window.open(postURL, null, shareWindowFeatures.join()).focus();

				Liferay.fire('socialBookmarks:share', {
					className: className,
					classPK: classPK,
					type: type,
					url: url
				});

				return false;
			}
		</aui:script>
	</liferay-util:html-bottom>

	<aui:script sandbox="<%= true %>">
		Liferay.componentReady('<%= dropdownMenuComponentId %>').then(function(
			dropdownMenu
		) {
			dropdownMenu.on(['itemClicked'], function(event) {
				event.preventDefault();

				var data = event.data.item.data;

				socialBookmarks_handleItemClick(
					event,
					data.className,
					parseInt(data.classPK),
					data.type,
					data.postURL,
					data.url
				);
			});
		});
	</aui:script>
</div>