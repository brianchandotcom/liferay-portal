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

List results = (List)request.getAttribute("view.jsp-results");

int assetEntryIndex = ((Integer)request.getAttribute("view.jsp-assetEntryIndex")).intValue();

AssetEntry assetEntry = (AssetEntry)request.getAttribute("view.jsp-assetEntry");
AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)request.getAttribute("view.jsp-assetRendererFactory");
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute("view.jsp-assetRenderer");

String languageId = LanguageUtil.getLanguageId(request);

String title = assetRenderer.getTitle(LocaleUtil.fromLanguageId(languageId));

boolean print = ((Boolean)request.getAttribute("view.jsp-print")).booleanValue();
boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(assetEntry.getCompanyId(), assetEntry.getGroupId(), assetEntry.getClassName());

assetPublisherDisplayContext.setLayoutAssetEntry(assetEntry);

assetEntry = assetPublisherDisplayContext.incrementViewCounter(assetEntry);

request.setAttribute("view.jsp-fullContentRedirect", workflowEnabled ? redirect : currentURL);
request.setAttribute("view.jsp-showIconLabel", true);
%>

<div class="h2">
	<c:if test="<%= assetPublisherDisplayContext.isShowAssetTitle() %>">
		<c:if test="<%= showBackURL && Validator.isNotNull(redirect) %>">
			<liferay-ui:icon
				cssClass="header-back-to"
				icon="angle-left"
				markupView="lexicon"
				url="<%= redirect %>"
			/>
		</c:if>

		<span class="header-title"><%= HtmlUtil.escape(title) %></span>
	</c:if>

	<c:if test="<%= !print %>">
		<liferay-util:include page="/asset_actions.jsp" servletContext="<%= application %>" />
	</c:if>
</div>

<div class="asset-full-content clearfix <%= assetPublisherDisplayContext.isDefaultAssetPublisher() ? "default-asset-publisher" : StringPool.BLANK %> <%= assetPublisherDisplayContext.isShowAssetTitle() ? "show-asset-title" : "no-title" %>">
	<c:if test="<%= (assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible()) || (assetPublisherDisplayContext.isEnablePrint() && assetRenderer.isPrintable()) || (assetPublisherDisplayContext.isShowAvailableLocales() && assetRenderer.isLocalizable()) %>">
		<div class="asset-user-actions">
			<c:if test="<%= assetPublisherDisplayContext.isEnablePrint() %>">
				<div class="print-action">

					<%
					PortletURL printAssetURL = renderResponse.createRenderURL();

					printAssetURL.setParameter("mvcPath", "/view_content.jsp");
					printAssetURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));
					printAssetURL.setParameter("viewMode", Constants.PRINT);
					printAssetURL.setParameter("type", assetRendererFactory.getType());
					printAssetURL.setParameter("languageId", languageId);

					String urlTitle = assetRenderer.getUrlTitle(locale);

					if (Validator.isNotNull(urlTitle)) {
						if (assetRenderer.getGroupId() != scopeGroupId) {
							printAssetURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
						}

						printAssetURL.setParameter("urlTitle", urlTitle);
					}

					printAssetURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					<c:choose>
						<c:when test="<%= print %>">
							<liferay-ui:icon
								icon="print"
								label="<%= true %>"
								markupView="lexicon"
								message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(assetRenderer.getTitle(locale))}, false) %>'
								url="javascript:print();"
							/>

							<aui:script>
								print();
							</aui:script>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								icon="print"
								label="<%= true %>"
								markupView="lexicon"
								message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(assetRenderer.getTitle(locale))}, false) %>'
								url='<%= "javascript:" + renderResponse.getNamespace() + "printPage_" + assetEntryIndex + "();" %>'
							/>

							<aui:script>
								function <portlet:namespace />printPage_<%= assetEntryIndex %>() {
								window.open('<%= printAssetURL %>', '', 'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640');
								}
							</aui:script>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>

			<c:if test="<%= assetPublisherDisplayContext.isEnableConversions() && assetRenderer.isConvertible() && !print %>">
				<div class="export-actions">

					<%
					PortletURL exportAssetURL = assetRenderer.getURLExport(liferayPortletRequest, liferayPortletResponse);

					exportAssetURL.setParameter("plid", String.valueOf(themeDisplay.getPlid()));
					exportAssetURL.setParameter("portletResource", portletDisplay.getId());
					exportAssetURL.setWindowState(LiferayWindowState.EXCLUSIVE);
					%>

					<liferay-ui:icon-list>

						<%
						for (String extension : assetPublisherDisplayContext.getExtensions(assetRenderer)) {
							Map<String, Object> data = new HashMap<String, Object>();

							exportAssetURL.setParameter("targetExtension", extension);

							data.put("resource-href", exportAssetURL.toString());
						%>

							<liferay-ui:icon
								data="<%= data %>"
								iconCssClass="<%= DLUtil.getFileIconCssClass(extension) %>"
								label="<%= true %>"
								message='<%= LanguageUtil.format(request, "x-convert-x-to-x", new Object[] {"hide-accessible", assetRenderer.getTitle(locale), StringUtil.toUpperCase(HtmlUtil.escape(extension))}, false) %>'
								method="get"
								url="<%= exportAssetURL.toString() %>"
							/>

						<%
						}
						%>

					</liferay-ui:icon-list>
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
						<liferay-ui:language
							formAction="<%= currentURL %>"
							languageId="<%= languageId %>"
							languageIds="<%= availableLanguageIds %>"
						/>
					</div>
				</c:if>
			</c:if>
		</div>
	</c:if>

	<%
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
	%>

	<div class="asset-content" id="<portlet:namespace /><%= assetEntry.getEntryId() %>">
		<liferay-asset:asset-display
			assetEntry="<%= assetEntry %>"
			assetRenderer="<%= assetRenderer %>"
			assetRendererFactory="<%= assetRendererFactory %>"
			showExtraInfo="<%= assetPublisherDisplayContext.isShowExtraInfo() %>"
		/>

		<div class="pull-right">
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

		<c:if test="<%= assetPublisherDisplayContext.isEnableFlags() %>">

			<%
			TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(assetRenderer.getClassName());

			boolean inTrash = trashHandler.isInTrash(assetEntry.getClassPK());
			%>

			<div class="asset-flag">
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

		<c:if test="<%= assetPublisherDisplayContext.isEnableRatings() && assetRenderer.isRatable() %>">
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

		<c:if test="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>">

			<%
			PortletURL assetLingsURL = renderResponse.createRenderURL();

			assetLingsURL.setParameter("mvcPath", "/view_content.jsp");

			if (print) {
				assetLingsURL.setParameter("viewMode", Constants.PRINT);
			}
			%>

			<liferay-asset:asset-links
				assetEntryId="<%= assetEntry.getEntryId() %>"
				portletURL="<%= assetLingsURL %>"
				viewInContext="<%= assetPublisherDisplayContext.isAssetLinkBehaviorViewInPortlet() %>"
			/>
		</c:if>

		<c:if test="<%= assetPublisherDisplayContext.isEnableComments() && assetRenderer.isCommentable() %>">
			<div class="col-md-12">
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

	<liferay-asset:asset-metadata
		className="<%= assetEntry.getClassName() %>"
		classPK="<%= assetEntry.getClassPK() %>"
		filterByMetadata="<%= true %>"
		metadataFields="<%= assetPublisherDisplayContext.getMetadataFields() %>"
	/>
</div>

<c:if test="<%= !assetPublisherDisplayContext.isShowAssetTitle() && ((assetEntryIndex + 1) < results.size()) %>">
	<div class="separator"><!-- --></div>
</c:if>