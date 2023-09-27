<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPSpecificationOptionFacetsDisplayContext cpSpecificationOptionFacetsDisplayContext = (CPSpecificationOptionFacetsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !cpSpecificationOptionFacetsDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		int j = 0;

		for (CPSpecificationOptionsSearchFacetDisplayContext cpSpecificationOptionsSearchFacetDisplayContext : cpSpecificationOptionFacetsDisplayContext.getCPSpecificationOptionsSearchFacetDisplayContexts()) {
			j++;

			Facet facet = cpSpecificationOptionsSearchFacetDisplayContext.getFacet();

			String parameterName = cpSpecificationOptionsSearchFacetDisplayContext.getParameterName();

			String panelTitle = HtmlUtil.escape(cpSpecificationOptionsSearchFacetDisplayContext.getCPSpecificationOptionTitle(facet.getFieldName()));

			String panelContainerId = liferayPortletResponse.getNamespace() + "facetCPSpecificationOptionsPanelContainer" + j;

			String panelId = liferayPortletResponse.getNamespace() + "facetCPSpecificationOptionsPanel" + j;
		%>

			<aui:form method="post" name='<%= "assetEntriesFacetForm_" + parameterName %>'>
				<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(cpSpecificationOptionsSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= cpSpecificationOptionsSearchFacetDisplayContext.getParameterValue() %>" />
				<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= parameterName %>" />
				<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= cpSpecificationOptionsSearchFacetDisplayContext.getPaginationStartParameterName() %>" />

				<liferay-ddm:template-renderer
					className="<%= CPSpecificationOptionsSearchFacetTermDisplayContext.class.getName() %>"
					contextObjects='<%=
						HashMapBuilder.<String, Object>put(
							"cpSpecificationOptionsSearchFacetDisplayContext", cpSpecificationOptionsSearchFacetDisplayContext
						).put(
							"namespace", liferayPortletResponse.getNamespace()
						).put(
							"panelContainerId", panelContainerId
						).put(
							"panelId", panelId
						).put(
							"panelTitle", panelTitle
						).build()
					%>'
					displayStyle='<%= portletPreferences.getValue("displayStyle", "") %>'
					displayStyleGroupId="<%= cpSpecificationOptionFacetsDisplayContext.getDisplayStyleGroupId() %>"
					entries="<%= cpSpecificationOptionsSearchFacetDisplayContext.getTermDisplayContexts() %>"
				>
					<liferay-ui:panel-container
						extended="<%= true %>"
						id="<%= panelContainerId %>"
						markupView="lexicon"
						persistState="<%= true %>"
					>
						<liferay-ui:panel
							collapsible="<%= true %>"
							cssClass="search-facet"
							id="<%= panelId %>"
							markupView="lexicon"
							persistState="<%= true %>"
							title="<%= panelTitle %>"
						>
							<c:if test="<%= cpSpecificationOptionsSearchFacetDisplayContext.isShowClear() %>">
								<aui:button cssClass="btn-link btn-unstyled facet-clear-btn" onClick="Liferay.Search.FacetUtil.clearSelections(event);" value="clear" />
							</c:if>

							<aui:fieldset>
								<ul class="list-unstyled" data-qa-id="<%= HtmlUtil.escapeAttribute(cpSpecificationOptionsSearchFacetDisplayContext.getCPSpecificationOptionTitle(facet.getFieldName())) %>">

									<%
									int i = 0;

									for (CPSpecificationOptionsSearchFacetTermDisplayContext cpSpecificationOptionsSearchFacetTermDisplayContext : cpSpecificationOptionsSearchFacetDisplayContext.getTermDisplayContexts()) {
										i++;
									%>

										<li class="facet-value">
											<div class="custom-checkbox custom-control">
												<label for="<portlet:namespace />term_<%= i %>">
													<input
														<%= cpSpecificationOptionsSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %>
														class="custom-control-input facet-term"
														data-term-id="<%= cpSpecificationOptionsSearchFacetTermDisplayContext.getDisplayName() %>"
														id="<portlet:namespace />term_<%= parameterName + i %>"
														name="<portlet:namespace />term_<%= parameterName + i %>"
														onChange="Liferay.Search.FacetUtil.changeSelection(event);"
														type="checkbox"
													/>

													<span class="custom-control-label term-name <%= cpSpecificationOptionsSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
														<span class="custom-control-label-text"><%= HtmlUtil.escape(cpSpecificationOptionsSearchFacetTermDisplayContext.getDisplayName()) %></span>
													</span>

													<c:if test="<%= cpSpecificationOptionsSearchFacetTermDisplayContext.isFrequencyVisible() %>">
														<small class="term-count">
															(<%= cpSpecificationOptionsSearchFacetTermDisplayContext.getFrequency() %>)
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
						</liferay-ui:panel>
					</liferay-ui:panel-container>
				</liferay-ddm:template-renderer>
			</aui:form>

		<%
		}
		%>

	</c:otherwise>
</c:choose>

<aui:script use="liferay-search-facet-util" />