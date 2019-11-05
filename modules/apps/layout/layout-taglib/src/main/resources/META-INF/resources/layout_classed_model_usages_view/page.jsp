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

<%@ include file="/layout_classed_model_usages_view/init.jsp" %>

<%
String className = (String)request.getAttribute("liferay-layout:layout-classed-model-usages-view:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-layout:layout-classed-model-usages-view:classPK"));

LayoutClassedModelUsagesDisplayContext layoutClassedModelUsagesDisplayContext = new LayoutClassedModelUsagesDisplayContext(renderRequest, renderResponse, className, classPK);
%>

<div id="<portlet:namespace />layoutClassedModelUsagesList">
	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		searchContainer="<%= layoutClassedModelUsagesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.layout.model.LayoutClassedModelUsage"
			modelVar="layoutClassedModelUsage"
		>
			<liferay-ui:search-container-column-text
				name="pages"
			>
				<h5>
					<%= HtmlUtil.escape(layoutClassedModelUsagesDisplayContext.getLayoutClassedModelUsageName(layoutClassedModelUsage)) %>
				</h5>

				<div class="text-secondary">
					<%= LanguageUtil.get(request, layoutClassedModelUsagesDisplayContext.getLayoutClassedModelUsageTypeLabel(layoutClassedModelUsage)) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="text-right"
			>
				<c:if test="<%= layoutClassedModelUsagesDisplayContext.isShowPreview(layoutClassedModelUsage) %>">

					<%
					Layout curLayout = LayoutLocalServiceUtil.fetchLayout(layoutClassedModelUsage.getPlid());
					%>

					<c:if test="<%= curLayout != null %>">

						<%
						Map<String, String> data = new HashMap<>();

						data.put("href", layoutClassedModelUsagesDisplayContext.getPreviewURL(layoutClassedModelUsage));
						%>

						<clay:button
							data="<%= data %>"
							elementClasses="preview-layout-classed-model-usage table-action-link"
							icon="view"
							monospaced="<%= true %>"
							style="secondary"
						/>
					</c:if>
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
			searchResultCssClass="table table-autofit table-heading-nowrap"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom">
	if (
		document.querySelector('#<portlet:namespace/>layoutClassedModelUsagesList')
	) {
		var previewLayoutClassedModelUsagesList = dom.delegate(
			document.querySelector(
				'#<portlet:namespace/>layoutClassedModelUsagesList'
			),
			'click',
			'.preview-layout-classed-model-usage',
			function(event) {
				var delegateTarget = event.delegateTarget;

				Liferay.Util.openWindow({
					dialog: {
						destroyOnHide: true,
						modal: true
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer article-preview'
					},
					title: '<liferay-ui:message key="preview" />',
					uri: delegateTarget.getAttribute('data-href')
				});
			}
		);

		function removeListener() {
			previewLayoutClassedModelUsagesList.removeListener();

			Liferay.detach('destroyPortlet', removeListener);
		}

		Liferay.on('destroyPortlet', removeListener);
	}
</aui:script>