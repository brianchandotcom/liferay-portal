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

<style>
	.facet-checkbox-label {
		display: block;
	}
</style>

<%
CPOptionFacetsDisplayContext cpOptionFacetsDisplayContext = (CPOptionFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<%
for (Facet facet : cpOptionFacetsDisplayContext.getFacets()) {
%>

	<liferay-ui:panel-container extended="<%= true %>" markupView="lexicon" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="search-facet" markupView="lexicon" persistState="<%= true %>" title="<%= facet.getFieldName() %>">
			<aui:form method="post" name="assetEntriesFacetForm">
				<aui:fieldset>
				<ul class="asset-type list-unstyled">

					<%
					int i = 0;
					FacetCollector facetCollector = facet.getFacetCollector();

					for (TermCollector termCollector : facetCollector.getTermCollectors()) {
						i++;
					%>

						<li class="facet-value">
							<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
								<input
									class="facet-term"
									data-term-id="<%= termCollector.getTerm() %>"
									id="<portlet:namespace />term_<%= i %>"
									name="<portlet:namespace />term_<%= i %>"
									onChange='Liferay.Search.FacetUtil.changeSelection(event);'
									type="checkbox"
								/>

								<span class="term-name">
									<%= HtmlUtil.escape(termCollector.getTerm()) %>
								</span>

								<small class="term-count">
									(<%= termCollector.getFrequency() %>)
								</small>
							</label>
						</li>

					<%
					}
					%>

				</aui:fieldset>
			</aui:form>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

<%
}
%>

<aui:script use="liferay-cp-option-facets-util"></aui:script>