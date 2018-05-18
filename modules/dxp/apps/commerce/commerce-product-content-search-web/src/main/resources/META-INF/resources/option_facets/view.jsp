<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

List<Facet> facets = cpOptionFacetsDisplayContext.getFacets();
%>

<c:choose>
	<c:when test="<%= facets.size() > 0 %>">

		<%
		for (Facet facet : facets) {
		%>

		<liferay-ui:panel-container
			extended="<%= true %>"
			markupView="lexicon"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				cssClass="search-facet"
				markupView="lexicon"
				persistState="<%= true %>"
				title="<%= cpOptionFacetsDisplayContext.getCPOptionName(scopeGroupId, facet.getFieldName()) %>"
			>
				<aui:form method="post" name='<%= "assetEntriesFacetForm_" + facet.getFieldName() %>'>
					<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= cpOptionFacetsDisplayContext.getCPOptionKey(scopeGroupId, facet.getFieldName()) %>" />

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
									onChange="Liferay.Search.FacetUtil.changeSelection(event);"
									type="checkbox"
									<%= cpOptionFacetsDisplayContext.isCPOptionValueSelected(scopeGroupId, facet.getFieldName(), termCollector.getTerm()) ? "checked" : "" %>
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

	</c:when>
	<c:otherwise>
		<div class="alert alert-info">
			<liferay-ui:message key="no-facets-were-found" />
		</div>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util"></aui:script>