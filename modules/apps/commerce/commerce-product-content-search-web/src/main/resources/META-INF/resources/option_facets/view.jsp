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
CPOptionsSearchFacetDisplayContext cpOptionsSearchFacetDisplayContext = (CPOptionsSearchFacetDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !cpOptionsSearchFacetDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		List<Facet> facets = cpOptionsSearchFacetDisplayContext.getFacets();

		long companyId = company.getCompanyId();
		%>

		<c:choose>
			<c:when test="<%= !facets.isEmpty() %>">

				<%
				for (Facet facet : facets) {
					FacetCollector facetCollector = facet.getFacetCollector();

					List<TermCollector> termCollectors = facetCollector.getTermCollectors();
				%>

					<c:if test="<%= !termCollectors.isEmpty() %>">
						<aui:form method="post" name='<%= "assetEntriesFacetForm_" + facet.getFieldName() %>'>
							<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= cpOptionsSearchFacetDisplayContext.getCPOptionKey(companyId, facet.getFieldName()) %>" />
							<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= cpOptionsSearchFacetDisplayContext.getPaginationStartParameterName() %>" />

							<liferay-ddm:template-renderer
								className="<%= CPOptionsSearchFacetDisplayContext.class.getName() %>"
								contextObjects='<%=
									HashMapBuilder.<String, Object>put(
										"companyId", companyId
									).put(
										"cpOptionsSearchFacetDisplayContext", cpOptionsSearchFacetDisplayContext
									).put(
										"fieldName", facet.getFieldName()
									).put(
										"name", liferayPortletResponse.getNamespace() + "term_" + facet.getFieldName()
									).put(
										"namespace", liferayPortletResponse.getNamespace()
									).put(
										"showFrequencies", GetterUtil.getBoolean(portletPreferences.getValue("frequenciesVisible", null), true)
									).put(
										"title", HtmlUtil.escape(cpOptionsSearchFacetDisplayContext.getCPOptionName(companyId, facet.getFieldId()))
									).build()
								%>'
								displayStyle='<%= portletPreferences.getValue("displayStyle", "") %>'
								displayStyleGroupId="<%= cpOptionsSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
								entries="<%= cpOptionsSearchFacetDisplayContext.getTermDisplayContexts() %>"
							>

							<liferay-ui:panel-container
								extended="<%= true %>"
								id='<%= liferayPortletResponse.getNamespace() + "facetCPOptionsPanelContainer" %>'
								markupView="lexicon"
								persistState="<%= true %>"
							>
							<liferay-ui:panel
								collapsible="<%= true %>"
								cssClass="search-facet"
								id='<%= liferayPortletResponse.getNamespace() + "facetCPOptionsPanel" %>'
								markupView="lexicon"
								persistState="<%= true %>"
								title="<%= HtmlUtil.escape(cpOptionsSearchFacetDisplayContext.getCPOptionName(companyId, facet.getFieldId())) %>"
							>

							<aui:fieldset>
								<ul class="list-unstyled" data-qa-id="<%= HtmlUtil.escape(cpOptionsSearchFacetDisplayContext.getCPOptionName(companyId, facet.getFieldId())) %>">

								<%
								int i = 0;

								for (TermCollector termCollector : termCollectors) {
									i++;
								%>

								<li class="facet-value">
									<div class="custom-checkbox custom-control">
										<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= facet.getFieldName() + i %>">
											<input
												class="custom-control-input facet-term"
												data-term-id="<%= HtmlUtil.escapeAttribute(termCollector.getTerm()) %>"
												id="<portlet:namespace />term_<%= facet.getFieldName() + i %>"
												name="<portlet:namespace />term_<%= facet.getFieldName() + i %>"
												onChange="Liferay.Search.FacetUtil.changeSelection(event);"
												type="checkbox"
												<%= cpOptionsSearchFacetDisplayContext.isCPOptionValueSelected(companyId, facet.getFieldName(), termCollector.getTerm()) ? "checked" : "" %>
											/>

											<span class="custom-control-label term-name <%= cpOptionsSearchFacetDisplayContext.isCPOptionValueSelected(companyId, facet.getFieldName(), termCollector.getTerm()) ? "facet-term-selected" : "facet-term-unselected" %>">
												<span class="custom-control-label-text"><%= HtmlUtil.escape(termCollector.getTerm()) %></span>
											</span>

											<c:if test='<%= GetterUtil.getBoolean(portletPreferences.getValue("frequenciesVisible", null), true) %>'>
												<small class="term-count">
													(<%= termCollector.getFrequency() %>)
												</small>
											</c:if>
										</label>
									</div>
								</li>

								<%
								}
								%>

							</aui:fieldset>
							</liferay-ui:panel>
							</liferay-ui:panel-container>
							</liferay-ddm:template-renderer>
						</aui:form>
					</c:if>

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
	</c:otherwise>
</c:choose>