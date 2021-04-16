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

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new AssetBrowserManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, assetBrowserDisplayContext) %>"
/>

<aui:form action="<%= assetBrowserDisplayContext.getPortletURL() %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="selectAssetFm">
	<c:if test="<%= assetBrowserDisplayContext.isShowBreadcrumb() %>">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= assetBrowserDisplayContext.getPortletBreadcrumbEntries() %>"
		/>
	</c:if>

	<aui:input name="typeSelection" type="hidden" value="<%= assetBrowserDisplayContext.getTypeSelection() %>" />

	<liferay-ui:search-container
		id="selectAssetEntries"
		searchContainer="<%= assetBrowserDisplayContext.getAssetBrowserSearch() %>"
		var="assetEntriesSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetEntry"
			escapedModel="<%= true %>"
			modelVar="assetEntry"
		>

			<%
			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();
			%>

			<c:choose>
				<c:when test="<%= (assetRenderer != null) && (assetEntry.getEntryId() != assetBrowserDisplayContext.getRefererAssetEntryId()) %>">

					<%
					AssetRendererFactory<?> assetRendererFactory = assetRenderer.getAssetRendererFactory();

					Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());

					String cssClass = "selector-button";

					Map<String, Object> data = HashMapBuilder.<String, Object>put(
						"assetclassname", assetEntry.getClassName()
					).put(
						"assetclassnameid", assetEntry.getClassNameId()
					).put(
						"assetclasspk", assetEntry.getClassPK()
					).put(
						"assettitle", assetRenderer.getTitle(locale)
					).put(
						"assettitlemap", JSONFactoryUtil.looseSerialize(LocalizationUtil.getLocalizationMap(assetEntry.getTitle()))
					).put(
						"assettype", assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId())
					).put(
						"entityid", assetEntry.getEntryId()
					).put(
						"groupdescriptivename", group.getDescriptiveName(locale)
					).build();

					if (assetBrowserDisplayContext.isMultipleSelection()) {
						row.setData(data);
					}
					%>

					<c:choose>
						<c:when test='<%= Objects.equals(assetBrowserDisplayContext.getDisplayStyle(), "descriptive") %>'>
							<liferay-ui:search-container-column-text>
								<liferay-ui:user-portrait
									userId="<%= assetEntry.getUserId() %>"
								/>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								colspan="<%= 2 %>"
							>

								<%
								Date modifiedDate = assetEntry.getModifiedDate();
								%>

								<h6 class="text-default">
									<span><liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="modified-x-ago" /></span>
								</h6>

								<h5>
									<c:choose>
										<c:when test="<%= !assetBrowserDisplayContext.isMultipleSelection() %>">
											<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="javascript:;">
												<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
											</aui:a>
										</c:when>
										<c:otherwise>
											<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
										</c:otherwise>
									</c:choose>
								</h5>

								<c:if test="<%= assetBrowserDisplayContext.isSearchEverywhere() %>">
									<h6 class="text-default">
										<liferay-ui:message key="location" />:
										<span class="text-secondary">
											<clay:icon
												symbol="<%= assetBrowserDisplayContext.getGroupCssIcon(assetRenderer.getGroupId()) %>"
											/>

											<small><%= assetBrowserDisplayContext.getGroupLabel(assetRenderer.getGroupId(), locale) %></small>
										</span>
									</h6>
								</c:if>

								<c:if test="<%= Validator.isNull(assetBrowserDisplayContext.getTypeSelection()) %>">
									<h6 class="text-muted">
										<%= HtmlUtil.escape(assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId())) %>
									</h6>
								</c:if>

								<c:if test="<%= assetBrowserDisplayContext.isShowAssetEntryStatus() %>">
									<span class="text-default">
										<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= assetRenderer.getStatus() %>" />
									</span>
								</c:if>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(assetBrowserDisplayContext.getDisplayStyle(), "icon") %>'>
							<liferay-ui:search-container-column-text>
								<clay:vertical-card
									verticalCard="<%= new AssetEntryVerticalCard(assetEntry, renderRequest, assetBrowserDisplayContext) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test='<%= Objects.equals(assetBrowserDisplayContext.getDisplayStyle(), "list") %>'>
							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand table-cell-minw-200 table-title"
								name="title"
							>
								<c:choose>
									<c:when test="<%= !assetBrowserDisplayContext.isMultipleSelection() %>">
										<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="javascript:;">
											<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
										</aui:a>
									</c:when>
									<c:otherwise>
										<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
									</c:otherwise>
								</c:choose>
							</liferay-ui:search-container-column-text>

							<c:if test="<%= Validator.isNull(assetBrowserDisplayContext.getTypeSelection()) %>">
								<liferay-ui:search-container-column-text
									name="type"
									value="<%= HtmlUtil.escape(assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId())) %>"
								/>
							</c:if>

							<c:if test="<%= assetBrowserDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= assetBrowserDisplayContext.getGroupCssIcon(assetRenderer.getGroupId()) %>"
										/>

										<small><%= assetBrowserDisplayContext.getGroupLabel(assetRenderer.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								cssClass="table-cell-expand-smallest table-cell-minw-100"
								name="author"
								value="<%= PortalUtil.getUserName(assetEntry) %>"
							/>

							<liferay-ui:search-container-column-date
								cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
								name="modified-date"
								value="<%= assetEntry.getModifiedDate() %>"
							/>

							<c:if test="<%= assetBrowserDisplayContext.isShowAssetEntryStatus() %>">
								<liferay-ui:search-container-column-status
									cssClass="text-nowrap"
									name="status"
									status="<%= assetRenderer.getStatus() %>"
								/>
							</c:if>
						</c:when>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					if (assetRenderer == null) {
						_log.error("Unable to get asset renderer for assetEntry with primary key " + assetEntry.getEntryId());
					}

					row.setSkip(true);
					%>

				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetBrowserDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<c:choose>
	<c:when test="<%= !assetBrowserDisplayContext.isMultipleSelection() %>">
		<aui:script require="frontend-js-web/liferay/delegate/delegate.es as delegateModule">
			var delegate = delegateModule.default;

			var delegateHandler = delegate(
				document.querySelector('#<portlet:namespace />selectAssetFm'),
				'click',
				'.selector-button',
				(event) => {
					event.preventDefault();

					Liferay.Util.getOpener().Liferay.fire(
						'<%= HtmlUtil.escapeJS(assetBrowserDisplayContext.getEventName()) %>',
						{
							data: event.delegateTarget.dataset,
						}
					);
				}
			);

			var onDestroyPortlet = function () {
				delegateHandler.dispose();

				Liferay.detach('destroyPortlet', onDestroyPortlet);
			};

			Liferay.on('destroyPortlet', onDestroyPortlet);
		</aui:script>
	</c:when>
</c:choose>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_asset_browser_web.view_jsp");
%>