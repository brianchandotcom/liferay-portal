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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetTermDisplayContext" %>

<portlet:defineObjects />

<style>
	.facet-checkbox-label {
		display: block;
	}

	.facet-term-selected {
		font-weight: 600;
	}

	.facet-term-unselected {
		font-weight: 400;
	}
</style>

<%
AssetTagsSearchFacetDisplayContext assetTagsSearchFacetDisplayContext = (AssetTagsSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= assetTagsSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:panel-container
			extended="<%= true %>"
			id='<%= renderResponse.getNamespace() + "facetAssetTagsPanelContainer" %>'
			markupView="lexicon"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="search-facet"
				id='<%= renderResponse.getNamespace() + "facetAssetTagsPanel" %>'
				markupView="lexicon"
				persistState="<%= true %>"
				title="tag"
			>
				<aui:form method="post" name="assetTagsFacetForm">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetTagsSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getParameterValue() %>" />
					<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= assetTagsSearchFacetDisplayContext.getParameterName() %>" />

					<aui:fieldset>
						<ul class="<%= assetTagsSearchFacetDisplayContext.isCloudWithCount() ? "tag-cloud" : "tag-list" %> list-unstyled">

							<%
							int i = 0;

							for (AssetTagsSearchFacetTermDisplayContext assetTagsSearchFacetTermDisplayContext : assetTagsSearchFacetDisplayContext.getTermDisplayContexts()) {
								i++;
							%>

								<li class="facet-value tag-popularity-<%= assetTagsSearchFacetTermDisplayContext.getPopularity() %>">
									<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
										<input class="facet-term" data-term-id="<%= assetTagsSearchFacetTermDisplayContext.getValue() %>" id="<portlet:namespace />term_<%= i %>" name="<portlet:namespace />term_<%= i %>" onChange="Liferay.Search.FacetUtil.changeSelection(event);" type="checkbox" <%= assetTagsSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %> />

										<span class="term-name <%= assetTagsSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
											<%= HtmlUtil.escape(assetTagsSearchFacetTermDisplayContext.getDisplayName()) %>
										</span>

										<c:if test="<%= assetTagsSearchFacetTermDisplayContext.isFrequencyVisible() %>">
											<small class="term-count">
												(<%= assetTagsSearchFacetTermDisplayContext.getFrequency() %>)
											</small>
										</c:if>
									</label>
								</li>

							<%
							}
							%>

						</ul>
					</aui:fieldset>

					<c:if test="<%= !assetTagsSearchFacetDisplayContext.isNothingSelected() %>">
						<aui:a cssClass="text-default" href="javascript:;" onClick="Liferay.Search.FacetUtil.clearSelections(event);"><small><liferay-ui:message key="clear" /></small></aui:a>
					</c:if>
				</aui:form>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util"></aui:script>