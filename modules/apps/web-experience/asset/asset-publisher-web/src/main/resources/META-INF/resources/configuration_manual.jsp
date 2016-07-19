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
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String selectScope = (String)request.getAttribute("configuration.jsp-selectScope");
String eventName = "_" + HtmlUtil.escapeJS(assetPublisherDisplayContext.getPortletResource()) + "_selectAsset";
%>

<div class="portlet-configuration-body-content">
	<liferay-ui:tabs
		formName="fm"
		names="asset-selection,display-settings,subscriptions"
		param="tabs2"
		refresh="<%= false %>"
		type="nav-tabs-default tabs"
	>
		<liferay-ui:section>
			<div class="container-fluid-1280">
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="scope">
						<%= selectScope %>
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="model.resource.com.liferay.asset">

						<%
						List<AssetEntry> assetEntries = AssetPublisherUtil.getAssetEntries(renderRequest, portletPreferences, permissionChecker, assetPublisherDisplayContext.getGroupIds(), true, assetPublisherDisplayContext.isEnablePermissions(), true);
						%>

						<liferay-ui:search-container
							compactEmptyResultsMessage="<%= true %>"
							emptyResultsMessage="none"
							iteratorURL="<%= configurationRenderURL %>"
							total="<%= assetEntries.size() %>"
						>
							<liferay-ui:search-container-results
								results="<%= assetEntries.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.asset.kernel.model.AssetEntry"
								escapedModel="<%= true %>"
								keyProperty="entryId"
								modelVar="assetEntry"
							>

								<%
								AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

								AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK(), AssetRendererFactory.TYPE_LATEST);
								%>

								<liferay-ui:search-container-column-text
									name="title"
									truncate="<%= true %>"
								>
									<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>

									<c:if test="<%= !assetEntry.isVisible() %>">
										(<aui:workflow-status
											markupView="lexicon"
											showIcon="<%= false %>"
											showLabel="<%= false %>"
											status="<%= assetRenderer.getStatus() %>"
											statusMessage='<%= (assetRenderer.getStatus() == 0) ? "not-visible" : WorkflowConstants.getStatusLabel(assetRenderer.getStatus()) %>'
										/>)
									</c:if>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									name="type"
									value="<%= assetRendererFactory.getTypeName(locale) %>"
								/>

								<liferay-ui:search-container-column-date
									name="modified-date"
									value="<%= assetEntry.getModifiedDate() %>"
								/>

								<liferay-ui:search-container-column-jsp
									path="/asset_selection_action.jsp"
								/>

								<liferay-ui:search-container-column-jsp
									cssClass="entry-action-column"
									path="/asset_selection_order_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator markupView="lexicon" paginate="<%= total > SearchContainer.DEFAULT_DELTA %>" />
						</liferay-ui:search-container>

						<c:if test='<%= SessionMessages.contains(renderRequest, "deletedMissingAssetEntries") %>'>
							<div class="alert alert-info">
								<liferay-ui:message key="the-selected-assets-have-been-removed-from-the-list-because-they-do-not-belong-in-the-scope-of-this-portlet" />
							</div>
						</c:if>

						<%
						long[] groupIds = assetPublisherDisplayContext.getGroupIds();

						for (long groupId : groupIds) {
							Group group = GroupLocalServiceUtil.getGroup(groupId);
						%>

							<div class="select-asset-selector">
								<div class="edit-controls lfr-meta-actions">
									<liferay-ui:icon-menu
										cssClass="select-existing-selector"
										direction="right"
										message='<%= LanguageUtil.format(request, (groupIds.length == 1) ? "select" : "select-in-x", HtmlUtil.escape(group.getDescriptiveName(locale)), false) %>'
										showArrow="<%= false %>"
										showWhenSingleIcon="<%= true %>"
									>

										<%
										List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

										for (AssetRendererFactory<?> curRendererFactory : assetRendererFactories) {
											if (!curRendererFactory.isSelectable()) {
												continue;
											}

											PortletURL assetBrowserURL = PortletProviderUtil.getPortletURL(request, curRendererFactory.getClassName(), PortletProvider.Action.BROWSE);

											if (assetBrowserURL == null) {
												continue;
											}

											assetBrowserURL.setParameter("groupId", String.valueOf(groupId));
											assetBrowserURL.setParameter("selectedGroupIds", String.valueOf(groupId));
											assetBrowserURL.setParameter("typeSelection", curRendererFactory.getClassName());
											assetBrowserURL.setParameter("showNonindexable", String.valueOf(Boolean.TRUE));
											assetBrowserURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));
											assetBrowserURL.setParameter("eventName", eventName);
											assetBrowserURL.setPortletMode(PortletMode.VIEW);
											assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

											Map<String, Object> data = new HashMap<String, Object>();

											data.put("groupid", String.valueOf(groupId));

											if (!curRendererFactory.isSupportsClassTypes()) {
												data.put("href", assetBrowserURL.toString());

												String type = curRendererFactory.getTypeName(locale);

												data.put("destroyOnHide", true);
												data.put("title", LanguageUtil.format(request, "select-x", type, false));
												data.put("type", type);
										%>

												<liferay-ui:icon
													cssClass="asset-selector"
													data="<%= data %>"
													id="<%= groupId + FriendlyURLNormalizerUtil.normalize(type) %>"
													message="<%= HtmlUtil.escape(type) %>"
													url="javascript:;"
												/>

											<%
											}
											else {
												ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();
												List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), locale);

												for (ClassType assetAvailableClassType : assetAvailableClassTypes) {
													assetBrowserURL.setParameter("subtypeSelectionId", String.valueOf(assetAvailableClassType.getClassTypeId()));
													assetBrowserURL.setParameter("showNonindexable", String.valueOf(Boolean.TRUE));
													assetBrowserURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));

													data.put("href", assetBrowserURL.toString());

													String type = assetAvailableClassType.getName();

													data.put("destroyOnHide", true);
													data.put("title", LanguageUtil.format(request, "select-x", type, false));
													data.put("type", type);
											%>

													<liferay-ui:icon
														cssClass="asset-selector"
														data="<%= data %>"
														id="<%= groupId + FriendlyURLNormalizerUtil.normalize(type) %>"
														message="<%= HtmlUtil.escape(type) %>"
														url="javascript:;"
													/>

										<%
												}
											}
										}
										%>

									</liferay-ui:icon-menu>
								</div>
							</div>

						<%
						}
						%>

					</aui:fieldset>
				</aui:fieldset-group>
			</div>
		</liferay-ui:section>
	</liferay-ui:tabs>
</div>

<aui:button-row>
	<aui:button cssClass="btn-lg" onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script>
	function selectAsset(assetEntryId, assetClassName, assetType, assetEntryTitle, groupName) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'add-selection';
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= HtmlUtil.escapeJS(currentURL) %>';
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;
		document.<portlet:namespace />fm.<portlet:namespace />assetEntryType.value = assetClassName;

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	$('body').on(
		'click',
		'.asset-selector a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<%= eventName %>' + currentTarget.attr('id'),
					title: currentTarget.data('title'),
					uri: currentTarget.data('href')
				},
				function(event) {
					selectAsset(event.assetentryid, event.assetclassname, event.assettype, event.assettitle, event.groupdescriptivename);
				}
			);
		}
	);
</aui:script>