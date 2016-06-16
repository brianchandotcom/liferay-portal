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

<%@ include file="/wiki/init.jsp" %>

<%
WikiEngineRenderer wikiEngineRenderer = (WikiEngineRenderer)request.getAttribute(WikiWebKeys.WIKI_ENGINE_RENDERER);

WikiPageInfoPanelDisplayContext wikiPageInfoPanelDisplayContext = wikiDisplayContextProvider.getWikiPageInfoPanelDisplayContext(request, response);
%>

<c:if test="<%= wikiPageInfoPanelDisplayContext.isShowSidebarHeader() %>">
	<div class="sidebar-header">
		<c:choose>
			<c:when test="<%= wikiPageInfoPanelDisplayContext.isSinglePageSelection() %>">
				<ul class="sidebar-header-actions">

					<%
					request.setAttribute("page_info_panel.jsp-wikiPage", wikiPageInfoPanelDisplayContext.getFirstPage());
					%>

					<li>
						<liferay-util:include page="/wiki/subscribe.jsp" servletContext="<%= application %>" />
					</li>

					<li>
						<liferay-util:include page="/wiki/page_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>

				<%
				WikiPage wikiPage = wikiPageInfoPanelDisplayContext.getFirstPage();
				%>

				<h4>
					<%= HtmlUtil.escape(wikiPage.getTitle()) %>
				</h4>

				<p>
					<liferay-ui:message key="page" />
				</p>
			</c:when>
			<c:when test="<%= wikiPageInfoPanelDisplayContext.isMultiplePageSelection() %>">
				<h4><liferay-ui:message arguments="<%= wikiPageInfoPanelDisplayContext.getSelectedPagesCount() %>" key="x-items-are-selected" /></h4>
			</c:when>
			<c:otherwise>
				<h4><liferay-ui:message key="pages" /></h4>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<%
String sections = "details";

if (wikiPageInfoPanelDisplayContext.isSinglePageSelection()) {
	sections += ",versions,activity,links";
}
%>

<liferay-ui:tabs names="<%= sections %>" refresh="<%= false %>" type="dropdown">
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl>
				<c:choose>
					<c:when test="<%= wikiPageInfoPanelDisplayContext.isSinglePageSelection() %>">

						<%
						WikiPage wikiPage = wikiPageInfoPanelDisplayContext.getFirstPage();
						%>

						<c:if test="<%= Validator.isNotNull(wikiPage.getSummary()) %>">
							<dt class="h5"><liferay-ui:message key="summary" /></dt>

							<dd>
								<%= HtmlUtil.escape(wikiPage.getSummary()) %>
							</dd>
						</c:if>

						<dt class="h5"><liferay-ui:message key="format" /></dt>

						<dd>
							<liferay-ui:message key="<%= wikiEngineRenderer.getFormatLabel(wikiPage.getFormat(), locale) %>" />
						</dd>

						<dt class="h5"><liferay-ui:message key="latest-version" /></dt>

						<dd>
							<%= wikiPage.getVersion() %>

							<c:if test="<%= wikiPage.isMinorEdit() %>">
								(<liferay-ui:message key="minor-edit" />)
							</c:if>
						</dd>

						<dt class="h5"><liferay-ui:message key="create-date" /></dt>

						<dd>
							<%= dateFormatDateTime.format(wikiPage.getCreateDate()) %>
						</dd>

						<dt class="h5"><liferay-ui:message key="last-modified" /></dt>

						<dd>
							<%= dateFormatDateTime.format(wikiPage.getModifiedDate()) %>
						</dd>

						<dt class="h5"><liferay-ui:message key="attachments" /></dt>

						<dd>
							<%= wikiPage.getAttachmentsFileEntriesCount() %>
						</dd>

						<dt class="h5"><liferay-ui:message key="rss" /></dt>

						<dd>
							<aui:a href="<%= wikiPageInfoPanelDisplayContext.getPageRSSURL(wikiPage) %>" target="_blank">
								<liferay-ui:message key="feed" />
							</aui:a>
						</dd>

						<div class="lfr-asset-categories">
							<liferay-ui:asset-categories-summary
								className="<%= WikiPage.class.getName() %>"
								classPK="<%= wikiPage.getResourcePrimKey() %>"
								message="categories"
							/>
						</div>

						<div class="lfr-asset-tags">
							<liferay-ui:asset-tags-summary
								className="<%= WikiPage.class.getName() %>"
								classPK="<%= wikiPage.getResourcePrimKey() %>"
								message="tags"
							/>
						</div>

						<c:if test="<%= wikiPortletInstanceSettingsHelper.isEnablePageRatings() %>">
							<liferay-ui:ratings
								className="<%= WikiPage.class.getName() %>"
								classPK="<%= wikiPage.getResourcePrimKey() %>"
							/>
						</c:if>

						<liferay-ui:custom-attributes-available className="<%= WikiPage.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= WikiPage.class.getName() %>"
								classPK="<%= wikiPage.getResourcePrimKey() %>"
								editable="<%= false %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>

						<%
						AssetEntry wikiPageAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(WikiPage.class.getName(), wikiPage.getPrimaryKey());
						%>

						<c:if test="<%= (wikiPageAssetEntry != null) && wikiPortletInstanceSettingsHelper.isEnableRelatedAssets() %>">
							<div class="entry-links">
								<liferay-ui:asset-links
									assetEntryId="<%= wikiPageAssetEntry.getEntryId() %>"
								/>
							</div>
						</c:if>
					</c:when>
					<c:when test="<%= wikiPageInfoPanelDisplayContext.isMultiplePageSelection() %>">
						<dt class="h5"><liferay-ui:message arguments="<%= wikiPageInfoPanelDisplayContext.getSelectedPagesCount() %>" key="x-items-are-selected" /></dt>
					</c:when>
					<c:otherwise>
						<dt class="h5"><liferay-ui:message key="num-of-items" /></dt>

						<dd>
							<%= wikiPageInfoPanelDisplayContext.getPagesCount() %>
						</dd>
					</c:otherwise>
				</c:choose>
			</dl>
		</div>
	</liferay-ui:section>

	<c:if test="<%= wikiPageInfoPanelDisplayContext.isSinglePageSelection() %>">

		<%
		WikiPage wikiPage = wikiPageInfoPanelDisplayContext.getFirstPage();
		%>

		<liferay-ui:section>
			<div class="sidebar-body">

				<%
				List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(wikiPage.getNodeId(), wikiPage.getTitle(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());

				for (WikiPage curPage : pages) {
				%>

					<div>
						<ul class="sidebar-header-actions">
							<li>

								<%
								request.setAttribute("page_info_panel.jsp-wikiPage", curPage);
								%>

								<liferay-util:include page="/wiki/page_history_action.jsp" servletContext="<%= application %>" />
							</li>
						</ul>

						<dl>
							<dt class="h5">
								<h4><liferay-ui:message arguments="<%= curPage.getVersion() %>" key="version-x" /></h4>
							</dt>

							<dd>
								<small class="text-muted">
									<liferay-ui:message arguments="<%= new Object[] {curPage.getUserName(), dateFormatDateTime.format(curPage.getCreateDate())} %>" key="by-x-on-x" />
								</small>
							</dd>
						</dl>
					</div>

				<%
				}
				%>

			</div>
		</liferay-ui:section>

		<liferay-ui:section>
			<div class="sidebar-body">

				<%
				WikiSocialActivityHelper wikiSocialActivityHelper = new WikiSocialActivityHelper(wikiRequestHelper);

				List<SocialActivity> socialActivities = SocialActivityLocalServiceUtil.getActivities(0, WikiPage.class.getName(), wikiPage.getResourcePrimKey(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (SocialActivity socialActivity : socialActivities) {
					JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(socialActivity.getExtraData());

					String path = wikiSocialActivityHelper.getSocialActivityActionJSP(socialActivity, extraDataJSONObject);
				%>

					<c:if test="<%= Validator.isNotNull(path) %>">
						<div>
							<ul class="sidebar-header-actions">
								<li>

									<%
									request.setAttribute(WikiWebKeys.WIKI_PAGE, wikiPage);
									request.setAttribute("page_info_panel.jsp-socialActivity", socialActivity);
									%>

									<liferay-util:include page="<%= path %>" servletContext="<%= application %>" />
								</li>
							</ul>
						</div>
					</c:if>

					<dl>
						<dt class="h5">
							<h4><%= wikiSocialActivityHelper.getSocialActivityDescription(wikiPage, socialActivity, extraDataJSONObject, resourceBundle) %></h4>
						</dt>

						<dd>
							<small class="text-muted">
								<%= dateFormatDateTime.format(socialActivity.getCreateDate()) %>
							</small>
						</dd>
					</dl>

				<%
				}
				%>

			</div>
		</liferay-ui:section>

		<liferay-ui:section>
			<liferay-util:include page="/wiki/page_links.jsp" servletContext="<%= application %>" />
		</liferay-ui:section>
	</c:if>
</liferay-ui:tabs>