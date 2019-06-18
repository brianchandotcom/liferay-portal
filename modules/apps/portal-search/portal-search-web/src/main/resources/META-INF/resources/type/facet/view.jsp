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

<%@ include file="/type/facet/init.jsp" %>

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
AssetEntriesSearchFacetDisplayContext assetEntriesSearchFacetDisplayContext = (AssetEntriesSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
%>

<c:choose>
	<c:when test="<%= assetEntriesSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<liferay-ui:panel-container
			extended="<%= true %>"
			id='<%= renderResponse.getNamespace() + "facetAssetEntriesPanelContainer" %>'
			markupView="lexicon"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="search-facet"
				id='<%= renderResponse.getNamespace() + "facetAssetEntriesPanel" %>'
				markupView="lexicon"
				persistState="<%= true %>"
				title="type"
			>
				<aui:form method="post" name="assetEntriesFacetForm">
					<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
					<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterName() %>" />

					<aui:fieldset>
						<ul class="asset-type list-unstyled">

							<%
							int i = 0;

							for (AssetEntriesSearchFacetTermDisplayContext assetEntriesSearchFacetTermDisplayContext : assetEntriesSearchFacetDisplayContext.getTermDisplayContexts()) {
								i++;
							%>

								<li class="facet-value">
									<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
										<input class="facet-term" data-term-id="<%= assetEntriesSearchFacetTermDisplayContext.getAssetType() %>" id="<portlet:namespace />term_<%= i %>" name="<portlet:namespace />term_<%= i %>" onChange="Liferay.Search.FacetUtil.changeSelection(event);" type="checkbox" <%= assetEntriesSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %> />

										<span class="term-name <%= assetEntriesSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
											<%= HtmlUtil.escape(assetEntriesSearchFacetTermDisplayContext.getTypeName()) %>
										</span>

										<c:if test="<%= assetEntriesSearchFacetTermDisplayContext.isFrequencyVisible() %>">
											<small class="term-count">
												(<%= assetEntriesSearchFacetTermDisplayContext.getFrequency() %>)
											</small>
										</c:if>
									</label>
								</li>

							<%
							}
							%>

						</ul>
					</aui:fieldset>

					<c:if test="<%= !assetEntriesSearchFacetDisplayContext.isNothingSelected() %>">
						<a class="text-default" href="javascript:;" onClick="Liferay.Search.FacetUtil.clearSelections(event);"><small><liferay-ui:message key="clear" /></small></a>
					</c:if>
				</aui:form>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util"></aui:script>