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

redirect = PortalUtil.escapeRedirect(redirect);

boolean showBackURL = GetterUtil.getBoolean(request.getAttribute("view.jsp-showBackURL"));

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcPath", "/view.jsp");

	redirect = portletURL.toString();
}

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

long previewClassNameId = ParamUtil.getLong(request, "previewClassNameId");
long previewClassPK = ParamUtil.getLong(request, "previewClassPK");

boolean print = GetterUtil.getBoolean(request.getAttribute("view.jsp-print"));

assetPublisherDisplayContext.setLayoutAssetEntry(assetEntry);

assetEntry = assetPublisherDisplayContext.incrementViewCounter(assetEntry);

String title = assetRenderer.getTitle(locale);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("mvcPath", "/view_content.jsp");
viewFullContentURL.setParameter("type", assetRendererFactory.getType());

if (print) {
	viewFullContentURL.setParameter("viewMode", Constants.PRINT);
}

if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
	if (assetRenderer.getGroupId() != scopeGroupId) {
		viewFullContentURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
	}

	viewFullContentURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
}

String viewInContextURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, HttpUtil.setParameter(viewFullContentURL.toString(), "redirect", currentURL));

Map<String, Object> fragmentsEditorData = new HashMap<>();

fragmentsEditorData.put("fragments-editor-item-id", PortalUtil.getClassNameId(assetRenderer.getClassName()) + "-" + assetRenderer.getClassPK());
fragmentsEditorData.put("fragments-editor-item-type", "fragments-editor-mapped-item");
%>

<div class="asset-full-content clearfix mb-5 <%= assetPublisherDisplayContext.isDefaultAssetPublisher() ? "default-asset-publisher" : StringPool.BLANK %> <%= assetPublisherDisplayContext.isShowAssetTitle() ? "show-asset-title" : "no-title" %> <%= ((previewClassNameId == assetEntry.getClassNameId()) && (previewClassPK == assetEntry.getClassPK())) ? "p-1 preview-asset-entry" : StringPool.BLANK %>" <%= AUIUtil.buildData(fragmentsEditorData) %>>
	<div class="mb-2">
		<c:if test="<%= assetPublisherDisplayContext.isShowAssetTitle() %>">
			<h4 class="component-title">
				<c:if test="<%= showBackURL && Validator.isNotNull(redirect) %>">
					<liferay-ui:icon
						cssClass="header-back-to"
						icon="angle-left"
						markupView="lexicon"
						url="<%= redirect %>"
					/>
				</c:if>

				<span class="asset-title d-inline">
					<%= HtmlUtil.escape(title) %>
				</span>

				<c:if test="<%= !print %>">

					<%
					String fullContentRedirect = currentURL;

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(assetEntry.getCompanyId(), assetEntry.getGroupId(), assetEntry.getClassName())) {
						fullContentRedirect = redirect;
					}

					request.setAttribute("view.jsp-fullContentRedirect", fullContentRedirect);
					%>

					<span class="d-inline-flex">
						<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />
					</span>
				</c:if>
			</h4>
		</c:if>
	</div>

	<span class="asset-anchor lfr-asset-anchor" id="<%= assetEntry.getEntryId() %>"></span>

	<c:if test="<%= assetPublisherDisplayContext.isShowAuthor() || (assetPublisherDisplayContext.isShowCreateDate() && (assetEntry.getCreateDate() != null)) || (assetPublisherDisplayContext.isShowPublishDate() && (assetEntry.getPublishDate() != null)) || (assetPublisherDisplayContext.isShowExpirationDate() && (assetEntry.getExpirationDate() != null)) || (assetPublisherDisplayContext.isShowModifiedDate() && (assetEntry.getModifiedDate() != null)) || assetPublisherDisplayContext.isShowViewCount() %>">
		<div class="autofit-row mb-4 metadata-author">
			<c:if test="<%= assetPublisherDisplayContext.isShowAuthor() %>">
				<div class="asset-avatar autofit-col inline-item-before mr-3 pt-1">
					<liferay-ui:user-portrait
						size="lg"
						userId="<%= assetRenderer.getUserId() %>"
					/>
				</div>
			</c:if>

			<div class="autofit-col autofit-col-expand">
				<div class="autofit-row">
					<div class="autofit-col autofit-col-expand">
						<div class="text-truncate-inline">
							<span class="text-truncate user-info"><strong><%= HtmlUtil.escape(AssetRendererUtil.getAssetRendererUserFullName(assetRenderer, request)) %></strong></span>
						</div>

						<%
						StringBundler sb = new StringBundler(13);

						if (assetPublisherDisplayContext.isShowCreateDate() && (assetEntry.getCreateDate() != null)) {
							sb.append(LanguageUtil.get(request, "created"));
							sb.append(StringPool.SPACE);
							sb.append(dateFormatDate.format(assetEntry.getCreateDate()));
							sb.append(" - ");
						}

						if (assetPublisherDisplayContext.isShowPublishDate() && (assetEntry.getPublishDate() != null)) {
							sb.append(LanguageUtil.get(request, "published"));
							sb.append(StringPool.SPACE);
							sb.append(dateFormatDate.format(assetEntry.getPublishDate()));
							sb.append(" - ");
						}

						if (assetPublisherDisplayContext.isShowExpirationDate() && (assetEntry.getExpirationDate() != null)) {
							sb.append(LanguageUtil.get(request, "expired"));
							sb.append(StringPool.SPACE);
							sb.append(dateFormatDate.format(assetEntry.getExpirationDate()));
							sb.append(" - ");
						}

						if (assetPublisherDisplayContext.isShowModifiedDate() && (assetEntry.getModifiedDate() != null)) {
							Date modifiedDate = assetEntry.getModifiedDate();

							String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);

							sb.append(LanguageUtil.format(request, "modified-x-ago", modifiedDateDescription));
						}
						else if (sb.index() > 1) {
							sb.setIndex(sb.index() - 1);
						}
						%>

						<div class="asset-user-info text-secondary">
							<span class="date-info"><%= sb.toString() %></span>
						</div>

						<c:if test="<%= assetPublisherDisplayContext.isShowViewCount() %>">
							<div class="asset-view-count-info text-secondary">
								<span class="view-count-info"><%= assetEntry.getViewCount() %> <liferay-ui:message key='<%= (assetEntry.getViewCount() == 1) ? "view" : "views" %>' /></span>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</c:if>

	<div class="asset-content mb-3">
		<liferay-asset:asset-display
			assetEntry="<%= assetEntry %>"
			assetRenderer="<%= assetRenderer %>"
			assetRendererFactory="<%= assetRendererFactory %>"
			showExtraInfo="<%= assetPublisherDisplayContext.isShowExtraInfo() %>"
		/>
	</div>

	<c:if test="<%= assetPublisherDisplayContext.isShowCategories() %>">
		<div class="asset-categories mb-3">
			<liferay-asset:asset-categories-summary
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				displayStyle="simple-category"
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>
		</div>
	</c:if>

	<c:if test="<%= assetPublisherDisplayContext.isShowTags() %>">
		<div class="asset-tags mb-3">
			<liferay-asset:asset-tags-summary
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>
		</div>
	</c:if>

	<c:if test="<%= assetPublisherDisplayContext.isShowPriority() %>">
		<div class="asset-priority mb-4 text-secondary">
			<liferay-ui:message key="priority" />: <%= assetEntry.getPriority() %>
		</div>
	</c:if>

	<c:if test="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>">

		<%
		PortletURL assetLingsURL = renderResponse.createRenderURL();

		assetLingsURL.setParameter("mvcPath", "/view_content.jsp");

		if (print) {
			assetLingsURL.setParameter("viewMode", Constants.PRINT);
		}
		%>

		<div class="asset-links mb-4">
			<liferay-asset:asset-links
				assetEntryId="<%= assetEntry.getEntryId() %>"
				portletURL="<%= assetLingsURL %>"
				viewInContext="<%= assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet() %>"
			/>
		</div>
	</c:if>

	<%
	boolean showContextLink = assetPublisherDisplayContext.isShowContextLink(assetRenderer.getGroupId(), assetRendererFactory.getPortletId()) && !print && assetEntry.isVisible();
	boolean showRatings = assetPublisherDisplayContext.isEnableRatings() && assetRenderer.isRatable();
	%>

	<c:if test="<%= showContextLink || showRatings || assetPublisherDisplayContext.isEnableFlags() || assetPublisherDisplayContext.isEnablePrint() || Validator.isNotNull(assetPublisherDisplayContext.getSocialBookmarksTypes()) %>">
		<div class="separator"><!-- --></div>

		<div class="asset-details autofit-float autofit-row autofit-row-center">
			<c:if test="<%= showContextLink %>">
				<div class="asset-more autofit-col mr-3">
					<a href="<%= viewInContextURL %>"><liferay-ui:message key="<%= assetRenderer.getViewInContextMessage() %>" /> &raquo;</a>
				</div>
			</c:if>

			<c:if test="<%= showRatings %>">
				<div class="asset-ratings autofit-col mr-3">
					<liferay-ui:ratings
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= assetPublisherDisplayContext.isEnableFlags() %>">

				<%
				TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(assetRenderer.getClassName());

				boolean inTrash = trashHandler.isInTrash(assetEntry.getClassPK());
				%>

				<div class="asset-flag autofit-col mr-3">
					<liferay-flags:flags
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						contentTitle="<%= title %>"
						enabled="<%= !inTrash %>"
						label="<%= false %>"
						message='<%= inTrash ? "flags-are-disabled-because-this-entry-is-in-the-recycle-bin" : null %>'
						reportedUserId="<%= assetRenderer.getUserId() %>"
					/>
				</div>
			</c:if>

			<c:if test="<%= assetPublisherDisplayContext.isEnablePrint() %>">
				<div class="autofit-col component-subtitle mr-3 print-action">
					<c:choose>
						<c:when test="<%= print %>">
							<liferay-ui:icon
								icon="print"
								linkCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
								markupView="lexicon"
								message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(title)}, false) %>'
								url="javascript:print();"
							/>

							<aui:script>
								print();
							</aui:script>
						</c:when>
						<c:otherwise>

							<%
							String id = assetEntry.getEntryId() + StringUtil.randomId();
							%>

							<liferay-ui:icon
								icon="print"
								linkCssClass="btn btn-monospaced btn-outline-borderless btn-outline-secondary btn-sm"
								markupView="lexicon"
								message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(title)}, false) %>'
								url='<%= "javascript:" + renderResponse.getNamespace() + "printPage_" + id + "();" %>'
							/>

							<%
							PortletURL printAssetURL = renderResponse.createRenderURL();

							printAssetURL.setParameter("mvcPath", "/view_content.jsp");
							printAssetURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
							printAssetURL.setParameter("viewMode", Constants.PRINT);
							printAssetURL.setParameter("type", assetRendererFactory.getType());
							printAssetURL.setParameter("languageId", LanguageUtil.getLanguageId(request));
							printAssetURL.setWindowState(LiferayWindowState.POP_UP);
							%>

							<aui:script>
								function <portlet:namespace />printPage_<%= id %>() {
									window.open(
										'<%= printAssetURL %>',
										'',
										'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640'
									);
								}
							</aui:script>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<c:if test="<%= Validator.isNotNull(assetPublisherDisplayContext.getSocialBookmarksTypes()) %>">
				<div class="autofit-col">
					<liferay-social-bookmarks:bookmarks
						className="<%= assetEntry.getClassName() %>"
						classPK="<%= assetEntry.getClassPK() %>"
						displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
						target="_blank"
						title="<%= title %>"
						types="<%= assetPublisherDisplayContext.getSocialBookmarksTypes() %>"
						urlImpl="<%= viewFullContentURL %>"
					/>
				</div>
			</c:if>
		</div>
	</c:if>

	<%
	boolean showConversions = assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible() && !print;
	boolean showLocalization = (assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable() && !print);
	%>

	<c:if test="<%= showConversions || showLocalization %>">
		<div class="separator"><!-- --></div>

		<div class="asset-details autofit-float autofit-row autofit-row-center">
			<c:if test="<%= showLocalization %>">

				<%
				String languageId = LanguageUtil.getLanguageId(request);

				String[] availableLanguageIds = assetRenderer.getAvailableLanguageIds();

				if (ArrayUtil.isNotEmpty(availableLanguageIds) && !ArrayUtil.contains(availableLanguageIds, languageId)) {
					languageId = assetRenderer.getDefaultLanguageId();
				}
				%>

				<c:if test="<%= availableLanguageIds.length > 1 %>">
					<div class="autofit-col locale-actions mr-3">
						<liferay-ui:language
							formAction="<%= currentURL %>"
							languageId="<%= languageId %>"
							languageIds="<%= availableLanguageIds %>"
						/>
					</div>
				</c:if>
			</c:if>

			<c:if test="<%= showConversions %>">

				<%
				PortletURL exportAssetURL = assetRenderer.getURLExport(liferayPortletRequest, liferayPortletResponse);

				exportAssetURL.setParameter("plid", String.valueOf(themeDisplay.getPlid()));
				exportAssetURL.setParameter("portletResource", portletDisplay.getId());
				exportAssetURL.setWindowState(LiferayWindowState.EXCLUSIVE);

				for (String extension : assetPublisherDisplayContext.getExtensions(assetRenderer)) {
					Map<String, Object> data = new HashMap<>();

					exportAssetURL.setParameter("targetExtension", extension);

					data.put("resource-href", exportAssetURL.toString());
				%>

					<div class="autofit-col component-subtitle export-action">
						<aui:a cssClass="btn btn-outline-borderless btn-outline-secondary btn-sm" data="<%= data %>" href="<%= exportAssetURL.toString() %>" label='<%= LanguageUtil.format(request, "x-convert-x-to-x", new Object[] {"hide-accessible", title, StringUtil.toUpperCase(HtmlUtil.escape(extension))}, false) %>' />
					</div>

				<%
				}
				%>

			</c:if>
		</div>
	</c:if>

	<c:if test="<%= assetPublisherDisplayContext.isEnableComments() && assetRenderer.isCommentable() %>">
		<div class="col-md-12 mt-4">
			<liferay-comment:discussion
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				formName='<%= "fm" + assetEntry.getClassPK() %>'
				ratingsEnabled="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>"
				redirect="<%= currentURL %>"
				userId="<%= assetRenderer.getUserId() %>"
			/>
		</div>
	</c:if>
</div>