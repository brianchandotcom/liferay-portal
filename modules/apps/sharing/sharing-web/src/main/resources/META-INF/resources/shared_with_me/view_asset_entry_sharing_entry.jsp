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

<%@ include file="/shared_with_me/init.jsp" %>

<%
AssetRenderer assetRenderer = (AssetRenderer)renderRequest.getAttribute(AssetRenderer.class.getName());

AssetRendererFactory assetRendererFactory = assetRenderer.getAssetRendererFactory();

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
String assetTitle = assetRenderer.getTitle(locale);
%>

<div class="upper-tbar-container-fixed">
	<div class="tbar upper-tbar">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<div class="tbar-section text-left">
						<h2 class="text-truncate-inline upper-tbar-title" title="<%= HtmlUtil.escapeAttribute(assetTitle) %>">
							<span class="text-truncate"><%= HtmlUtil.escape(assetTitle) %></span>
						</h2>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>

<liferay-asset:asset-display
	assetEntry="<%= assetEntry %>"
	assetRenderer="<%= assetRenderer %>"
	assetRendererFactory="<%= assetRendererFactory %>"
	showExtraInfo="<%= true %>"
	template="<%= AssetRenderer.TEMPLATE_FULL_CONTENT %>"
/>

<c:if test="<%= assetRenderer.isCommentable() %>">
	<div class="container-fluid-1280">
		<liferay-ui:panel
			collapsible="<%= true %>"
			cssClass="panel-group"
			extended="<%= true %>"
			markupView="lexicon"
			persistState="<%= true %>"
			title='<%= LanguageUtil.get(request, "comments") %>'
		>
			<liferay-comment:discussion
				className="<%= assetEntry.getClassName() %>"
				classPK="<%= assetEntry.getClassPK() %>"
				formName='<%= "fm" + assetEntry.getClassPK() %>'
				ratingsEnabled="<%= false %>"
				redirect="<%= currentURL %>"
				userId="<%= assetRenderer.getUserId() %>"
			/>
		</liferay-ui:panel>
	</div>
</c:if>