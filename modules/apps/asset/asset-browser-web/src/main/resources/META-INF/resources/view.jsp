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
	displayContext="<%= new AssetBrowserManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, assetBrowserDisplayContext) %>"
/>

<aui:form action="<%= assetBrowserDisplayContext.getPortletURL() %>" cssClass="container-fluid-1280" method="post" name="selectAssetFm">
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
			AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

			AssetRendererFactory assetRendererFactory = assetRenderer.getAssetRendererFactory();

			Group group = GroupLocalServiceUtil.getGroup(assetEntry.getGroupId());

			String cssClass = StringPool.BLANK;

			Map<String, Object> data = new HashMap<String, Object>();

			if (assetEntry.getEntryId() != assetBrowserDisplayContext.getRefererAssetEntryId()) {
				data.put("assetclassname", assetEntry.getClassName());
				data.put("assetclassnameid", assetEntry.getClassNameId());
				data.put("assetclasspk", assetEntry.getClassPK());
				data.put("assettitle", assetRenderer.getTitle(locale));
				data.put("assettitlemap", JSONFactoryUtil.looseSerialize(LocalizationUtil.getLocalizationMap(assetEntry.getTitle())));
				data.put("assettype", assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId()));
				data.put("entityid", assetEntry.getEntryId());
				data.put("groupdescriptivename", group.getDescriptiveName(locale));

				if (assetBrowserDisplayContext.isMultipleSelection()) {
					row.setData(data);
				}

				cssClass = "selector-button";
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

						String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
						%>

						<h6 class="text-default">
							<span><liferay-ui:message arguments="<%= modifiedDateDescription %>" key="modified-x-ago" /></span>
						</h6>

						<h5>
							<c:choose>
								<c:when test="<%= (assetEntry.getEntryId() != assetBrowserDisplayContext.getRefererAssetEntryId()) && !assetBrowserDisplayContext.isMultipleSelection() %>">
									<aui:a cssClass="<%= cssClass %>" data="<%= data %>" href="javascript:;">
										<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
								</c:otherwise>
							</c:choose>
						</h5>

						<c:if test="<%= Validator.isNull(assetBrowserDisplayContext.getTypeSelection()) %>">
							<h6 class="text-muted">
								<%= HtmlUtil.escape(assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId())) %>
							</h6>
						</c:if>

						<h6 class="text-default">
							<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetBrowserDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new AssetEntryVerticalCard(assetEntry, renderRequest, assetBrowserDisplayContext) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(assetBrowserDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						name="title"
						truncate="<%= true %>"
					>
						<c:choose>
							<c:when test="<%= (assetEntry.getEntryId() != assetBrowserDisplayContext.getRefererAssetEntryId()) && !assetBrowserDisplayContext.isMultipleSelection() %>">
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
							truncate="<%= true %>"
							value="<%= HtmlUtil.escape(assetRendererFactory.getTypeName(locale, assetBrowserDisplayContext.getSubtypeSelectionId())) %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-text
						name="description"
						truncate="<%= true %>"
						value="<%= HtmlUtil.escape(assetRenderer.getSummary(renderRequest, renderResponse)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= PortalUtil.getUserName(assetEntry) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= assetEntry.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-text
						name="site"
						value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= assetBrowserDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<c:choose>
	<c:when test="<%= assetBrowserDisplayContext.isMultipleSelection() %>">
		<aui:script use="liferay-search-container">
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />selectAssetEntries'
			);

			searchContainer.on('rowToggled', function(event) {
				var selectedItems = event.elements.allSelectedElements;

				var arr = [];

				selectedItems.each(function() {
					var domElement = this.ancestor('tr');

					if (domElement == null) {
						domElement = this.ancestor('li');
					}

					if (domElement != null) {
						var data = domElement.getDOM().dataset;

						arr.push(data);
					}
				});

				Liferay.Util.getOpener().Liferay.fire(
					'<%= HtmlUtil.escapeJS(assetBrowserDisplayContext.getEventName()) %>',
					{
						data: arr
					}
				);
			});
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script>
			Liferay.Util.selectEntityHandler(
				'#<portlet:namespace />selectAssetFm',
				'<%= HtmlUtil.escapeJS(assetBrowserDisplayContext.getEventName()) %>'
			);
		</aui:script>
	</c:otherwise>
</c:choose>