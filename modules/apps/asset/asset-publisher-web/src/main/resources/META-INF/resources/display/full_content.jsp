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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = ParamUtil.getString(PortalUtil.getOriginalServletRequest(request), "redirect");
}

List results = (List)request.getAttribute("view.jsp-results");

if (Validator.isNull(redirect) && results.isEmpty()) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/view.jsp");

	redirect = portletURL.toString();
}

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

String languageId = LanguageUtil.getLanguageId(request);

String title = assetRenderer.getTitle(LocaleUtil.fromLanguageId(languageId));

boolean print = ((Boolean)request.getAttribute("view.jsp-print")).booleanValue();

assetPublisherDisplayContext.setLayoutAssetEntry(assetEntry);

assetEntry = assetPublisherDisplayContext.incrementViewCounter(assetEntry);

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

<div class="asset-full-content <%= assetPublisherDisplayContext.isDefaultAssetPublisher() ? "default-asset-publisher" : StringPool.BLANK %> <%= assetPublisherDisplayContext.isShowAssetTitle() ? "show-asset-title" : "no-title" %>">
	<c:if test="<%= !print %>">
		<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />
	</c:if>

	<c:if test="<%= (assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible()) || (assetPublisherDisplayContext.isEnablePrint() && assetRenderer.isPrintable()) || (assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable()) %>">
		<div class="asset-user-actions">
			<c:if test="<%= assetPublisherDisplayContext.isEnablePrint() %>">
				<div class="print-action">
					<%@ include file="/asset_print.jspf" %>
				</div>
			</c:if>

			<c:if test="<%= assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible() && !print %>">
				<div class="export-actions">
					<%@ include file="/asset_export.jspf" %>
				</div>
			</c:if>
			<c:if test="<%= assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable() && !print %>">

				<%
				String[] availableLanguageIds = assetRenderer.getAvailableLanguageIds();
				%>

				<c:if test="<%= availableLanguageIds.length > 1 %>">
					<c:if test="<%= assetPublisherDisplayContext.isEnableConversions() || assetPublisherDisplayContext.isEnablePrint() %>">
						<div class="locale-separator"> </div>
					</c:if>

					<div class="locale-actions">
						<liferay-ui:language formAction="<%= currentURL %>" languageId="<%= languageId %>" languageIds="<%= availableLanguageIds %>" />
					</div>
				</c:if>
			</c:if>
		</div>
	</c:if>

	<%
	PortletURL viewFullContentURL = renderResponse.createRenderURL();

	viewFullContentURL.setParameter("mvcPath", "/view_content.jsp");
	viewFullContentURL.setParameter("type", assetRendererFactory.getType());
	viewFullContentURL.setParameter("viewMode", print ? Constants.PRINT : Constants.VIEW);

	if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
		if (assetRenderer.getGroupId() != scopeGroupId) {
			viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
		}

		viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
	}

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

		<liferay-ui:asset-display
			assetEntry="<%= assetEntry %>"
			assetRenderer="<%= assetRenderer %>"
			assetRendererFactory="<%= assetRendererFactory %>"
			showExtraInfo="<%= assetPublisherDisplayContext.isShowExtraInfo() %>"
		/>

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

		<c:if test="<%= assetRenderer.isEnableRatings() && assetPublisherDisplayContext.isEnableRatings() %>">
			<div class="asset-ratings">
				<liferay-ui:ratings
					className="<%= assetEntry.getClassName() %>"
					classPK="<%= assetEntry.getClassPK() %>"
				/>
			</div>
		</c:if>

		<c:if test="<%= assetPublisherDisplayContext.isShowContextLink(assetRenderer.getGroupId(), assetRendererFactory.getPortletId()) && !print && assetEntry.isVisible() %>">
			<div class="asset-more">
				<a href="<%= assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, HttpUtil.setParameter(viewFullContentURL.toString(), "redirect", currentURL)) %>"><liferay-ui:message key="<%= assetRenderer.getViewInContextMessage() %>" /> &raquo;</a>
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

			<liferay-ui:discussion
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				formName='<%= "fm" + assetEntry.getClassPK() %>'
				ratingsEnabled="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>"
				redirect="<%= currentURL %>"
				userId="<%= assetRenderer.getUserId() %>"
			/>
		</c:if>
	</div>

	<liferay-ui:asset-metadata
		className="<%= assetEntry.getClassName() %>"
		classPK="<%= assetEntry.getClassPK() %>"
		filterByMetadata="<%= true %>"
		metadataFields="<%= assetPublisherDisplayContext.getMetadataFields() %>"
	/>
</div>

<c:if test="<%= !assetPublisherDisplayContext.isShowAssetTitle() && ((assetEntryIndex + 1) < results.size()) %>">
	<div class="separator"><!-- --></div>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_asset_publisher_web.display.full_content_jsp");
%>