<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

List results = (List)request.getAttribute("view.jsp-results");

if (Validator.isNull(redirect) && results.isEmpty()) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/asset_publisher/view");

	redirect = portletURL.toString();
}

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute("view.jsp-assetRenderer");

String languageId = LanguageUtil.getLanguageId(request);

String title = assetRenderer.getTitle(LocaleUtil.fromLanguageId(languageId));

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();
boolean print = ((Boolean)request.getAttribute("view.jsp-print")).booleanValue();

request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

request.setAttribute("view.jsp-fullContentRedirect", currentURL);
request.setAttribute("view.jsp-showIconLabel", true);
%>

<c:if test="<%= assetPublisherDisplayContext.isShowAssetTitle() %>">
	<liferay-ui:header
		backURL="<%= print ? null : redirect %>"
		localizeTitle="<%= false %>"
		title="<%= title %>"
	/>
</c:if>

<div class="asset-full-content <%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), portletResource) ? "default-asset-publisher" : StringPool.BLANK %> <%= assetPublisherDisplayContext.isShowAssetTitle() ? "show-asset-title" : "no-title" %>">
	<c:if test="<%= !print %>">
		<liferay-util:include page="/html/portlet/asset_publisher/asset_actions.jsp" />
	</c:if>

	<c:if test="<%= (assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible()) || (assetPublisherDisplayContext.isEnablePrint() && assetRenderer.isPrintable()) || (assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable()) %>">
		<div class="asset-user-actions">
			<c:if test="<%= assetPublisherDisplayContext.isEnablePrint() %>">
				<div class="print-action">
					<%@ include file="/html/portlet/asset_publisher/asset_print.jspf" %>
				</div>
			</c:if>

			<c:if test="<%= (assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible()) && !print %>">
				<div class="export-actions">
					<%@ include file="/html/portlet/asset_publisher/asset_export.jspf" %>
				</div>
			</c:if>
			<c:if test="<%= (assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable()) && !print %>">

				<%
				String[] availableLanguageIds = assetRenderer.getAvailableLanguageIds();
				%>

				<c:if test="<%= availableLanguageIds.length > 1 %>">
					<c:if test="<%= assetPublisherDisplayContext.isEnableConversions() || assetPublisherDisplayContext.isEnablePrint() %>">
						<div class="locale-separator"> </div>
					</c:if>

					<div class="locale-actions">
						<liferay-ui:language displayStyle="<%= 0 %>" formAction="<%= currentURL %>" languageId="<%= languageId %>" languageIds="<%= availableLanguageIds %>" />
					</div>
				</c:if>
			</c:if>
		</div>
	</c:if>

	<%

	// Dynamically created asset entries are never persisted so incrementing the view counter breaks

	if (!assetEntry.isNew() && assetEntry.isVisible()) {
		AssetEntry incrementAssetEntry = null;

		if (assetPublisherDisplayContext.isEnablePermissions()) {
			incrementAssetEntry = AssetEntryServiceUtil.incrementViewCounter(assetEntry.getClassName(), assetEntry.getClassPK());
		}
		else {
			incrementAssetEntry = AssetEntryLocalServiceUtil.incrementViewCounter(user.getUserId(), assetEntry.getClassName(), assetEntry.getClassPK());
		}

		if (incrementAssetEntry != null) {
			assetEntry = incrementAssetEntry;
		}
	}

	if (assetPublisherDisplayContext.isShowContextLink()) {
		if (PortalUtil.getPlidFromPortletId(assetRenderer.getGroupId(), assetRendererFactory.getPortletId()) == 0) {
			assetPublisherDisplayContext.setShowContextLink(false);
		}
	}

	PortletURL viewFullContentURL = renderResponse.createRenderURL();

	viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());
	viewFullContentURL.setParameter("viewMode", print ? Constants.PRINT : Constants.VIEW);

	if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
		if (assetRenderer.getGroupId() != scopeGroupId) {
			viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
		}

		viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
	}

	String viewFullContentURLString = viewFullContentURL.toString();

	viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

	String socialBookmarksDisplayPosition = assetPublisherDisplayContext.getSocialBookmarksDisplayPosition();
	%>

	<div class="asset-content" id="<portlet:namespace /><%= assetEntry.getEntryId() %>">
		<c:if test='<%= assetPublisherDisplayContext.isEnableSocialBookmarks() && socialBookmarksDisplayPosition.equals("top") && !print %>'>
			<liferay-ui:social-bookmarks
				contentId="<%= String.valueOf(assetEntry.getEntryId()) %>"
				displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
				target="_blank"
				title="<%= title %>"
				url="<%= PortalUtil.getCanonicalURL(viewFullContentURL.toString(), themeDisplay, layout) %>"
			/>
		</c:if>

		<%
		String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);

		request.setAttribute(WebKeys.ASSET_RENDERER_FACTORY, assetRendererFactory);
		request.setAttribute(WebKeys.ASSET_RENDERER, assetRenderer);
		%>

		<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>">
			<liferay-util:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
			<liferay-util:param name="showExtraInfo" value="<%= String.valueOf(assetPublisherDisplayContext.isShowExtraInfo()) %>" />
		</liferay-util:include>

		<c:if test="<%= assetPublisherDisplayContext.isEnableFlags() %>">
			<div class="asset-flag">
				<liferay-ui:flags
					className="<%= assetEntry.getClassName() %>"
					classPK="<%= assetEntry.getClassPK() %>"
					contentTitle="<%= title %>"
					reportedUserId="<%= assetRenderer.getUserId() %>"
				/>
			</div>
		</c:if>

		<c:if test='<%= assetPublisherDisplayContext.isEnableSocialBookmarks() && socialBookmarksDisplayPosition.equals("bottom") && !print %>'>
			<liferay-ui:social-bookmarks
				displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
				target="_blank"
				title="<%= title %>"
				url="<%= PortalUtil.getCanonicalURL(viewFullContentURL.toString(), themeDisplay, layout) %>"
			/>
		</c:if>

		<c:if test="<%= assetPublisherDisplayContext.isEnableRatings() %>">
			<div class="asset-ratings">

				<%
				String assetEntryClassName = assetEntry.getClassName();

				String ratingsType = "stars";

				if (assetEntryClassName.equals(MBDiscussion.class.getName()) || assetEntryClassName.equals(MBMessage.class.getName())) {
					ratingsType = "thumbs";
				}
				%>

				<liferay-ui:ratings
					className="<%= assetEntry.getClassName() %>"
					classPK="<%= assetEntry.getClassPK() %>"
					type="<%= ratingsType %>"
				/>
			</div>
		</c:if>

		<c:if test="<%= assetPublisherDisplayContext.isShowContextLink() && !print && assetEntry.isVisible() %>">
			<div class="asset-more">
				<a href="<%= assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString) %>"><liferay-ui:message key="<%= assetRenderer.getViewInContextMessage() %>" /> &raquo;</a>
			</div>
		</c:if>

		<br />

		<c:if test="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>">
			<liferay-ui:asset-links
				assetEntryId="<%= assetEntry.getEntryId() %>"
			/>
		</c:if>

		<c:if test="<%= Validator.isNotNull(assetRenderer.getDiscussionPath()) && assetPublisherDisplayContext.isEnableComments() %>">
			<br />

			<portlet:actionURL var="discussionURL">
				<portlet:param name="struts_action" value='<%= "/asset_publisher/" + assetRenderer.getDiscussionPath() %>' />
			</portlet:actionURL>

			<liferay-ui:discussion
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				formAction="<%= discussionURL %>"
				formName='<%= "fm" + assetEntry.getClassPK() %>'
				ratingsEnabled="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>"
				redirect="<%= currentURL %>"
				userId="<%= assetRenderer.getUserId() %>"
			/>
		</c:if>
	</div>

	<c:if test="<%= show %>">
		<div class="asset-metadata">

			<%
			String[] metadataFields = assetPublisherDisplayContext.getMetadataFields();
			%>

			<%@ include file="/html/portlet/asset_publisher/asset_metadata.jspf" %>
		</div>
	</c:if>
</div>

<c:if test="<%= !assetPublisherDisplayContext.isShowAssetTitle() && ((assetEntryIndex + 1) < results.size()) %>">
	<div class="separator"><!-- --></div>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.asset_publisher.display_full_content_jsp");
%>