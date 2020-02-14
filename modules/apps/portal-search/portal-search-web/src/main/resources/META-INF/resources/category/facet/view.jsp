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
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.category.facet.configuration.CategoryFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetTermDisplayContext" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<portlet:defineObjects />

<%
AssetCategoriesSearchFacetDisplayContext assetCategoriesSearchFacetDisplayContext = (AssetCategoriesSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (assetCategoriesSearchFacetDisplayContext.isRenderNothing()) {
	return;
}

CategoryFacetPortletInstanceConfiguration categoryFacetPortletInstanceConfiguration = assetCategoriesSearchFacetDisplayContext.getCategoryFacetPortletInstanceConfiguration();

Map<String, Object> contextObjects = new HashMap<String, Object>();

contextObjects.put("assetCategoriesSearchFacetDisplayContext", assetCategoriesSearchFacetDisplayContext);
contextObjects.put("namespace", renderResponse.getNamespace());

List<AssetCategoriesSearchFacetTermDisplayContext> assetCategoriesSearchFacetTermDisplayContexts = assetCategoriesSearchFacetDisplayContext.getTermDisplayContexts();
%>

<c:choose>
	<c:when test="<%= assetCategoriesSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetCategoriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetCategoriesSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<aui:form method="post" name="categoryFacetFm">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetCategoriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetCategoriesSearchFacetDisplayContext.getParameterValue() %>" />
			<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= assetCategoriesSearchFacetDisplayContext.getParameterName() %>" />

			<liferay-ddm:template-renderer
				className="<%= AssetCategoriesSearchFacetTermDisplayContext.class.getName() %>"
				contextObjects="<%= contextObjects %>"
				displayStyle="<%= categoryFacetPortletInstanceConfiguration.displayStyle() %>"
				displayStyleGroupId="<%= assetCategoriesSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= assetCategoriesSearchFacetTermDisplayContexts %>"
			>
				<liferay-ui:panel-container
					extended="<%= true %>"
					id='<%= renderResponse.getNamespace() + "facetAssetCategoriesPanelContainer" %>'
					markupView="lexicon"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						cssClass="search-facet"
						id='<%= renderResponse.getNamespace() + "facetAssetCategoriesPanel" %>'
						markupView="lexicon"
						persistState="<%= true %>"
						title="category"
					>
						<aui:fieldset>
							<ul class="<%= assetCategoriesSearchFacetDisplayContext.isCloud() ? "tag-cloud" : "tag-list" %> list-unstyled">

								<%
								int i = 0;

								for (AssetCategoriesSearchFacetTermDisplayContext assetCategoriesSearchFacetTermDisplayContext : assetCategoriesSearchFacetDisplayContext.getTermDisplayContexts()) {
									i++;
								%>

									<li class="facet-value tag-popularity-<%= assetCategoriesSearchFacetTermDisplayContext.getPopularity() %>">
										<div class="custom-checkbox custom-control">
											<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
												<input
													<%= assetCategoriesSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %>
													class="custom-control-input facet-term"
													data-term-id="<%= assetCategoriesSearchFacetTermDisplayContext.getAssetCategoryId() %>"
													disabled
													id="<portlet:namespace />term_<%= i %>"
													name="<portlet:namespace />term_<%= i %>"
													onChange="Liferay.Search.FacetUtil.changeSelection(event);"
													type="checkbox"
												/>

												<span class="custom-control-label term-name <%= assetCategoriesSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
													<span class="custom-control-label-text"><%= HtmlUtil.escape(assetCategoriesSearchFacetTermDisplayContext.getDisplayName()) %></span>
												</span>

												<c:if test="<%= assetCategoriesSearchFacetTermDisplayContext.isFrequencyVisible() %>">
													<small class="term-count">
														(<%= assetCategoriesSearchFacetTermDisplayContext.getFrequency() %>)
													</small>
												</c:if>
											</label>
										</div>
									</li>

								<%
								}
								%>

							</ul>
						</aui:fieldset>

						<c:if test="<%= !assetCategoriesSearchFacetDisplayContext.isNothingSelected() %>">
							<aui:button cssClass="btn-link btn-unstyled facet-clear-btn" onClick="Liferay.Search.FacetUtil.clearSelections(event);" value="clear" />
						</c:if>
					</liferay-ui:panel>
				</liferay-ui:panel-container>
			</liferay-ddm:template-renderer>
		</aui:form>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util">
	Liferay.Search.FacetUtil.enableInputs(
		document.querySelectorAll(
			'#<portlet:namespace />categoryFacetFm .facet-term'
		)
	);
</aui:script>