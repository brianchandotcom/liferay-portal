<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortlet" %>

<portlet:defineObjects />

<%
AssetEntriesSearchFacetDisplayContext assetEntriesSearchFacetDisplayContext = (AssetEntriesSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (assetEntriesSearchFacetDisplayContext.isRenderNothing()) {
	return;
}

TypeFacetPortletInstanceConfiguration typeFacetPortletInstanceConfiguration = assetEntriesSearchFacetDisplayContext.getTypeFacetPortletInstanceConfiguration();
%>

<c:choose>
	<c:when test="<%= assetEntriesSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<aui:form action="#" method="post" name="fm">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(assetEntriesSearchFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterValue() %>" />
			<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getParameterName() %>" />
			<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= assetEntriesSearchFacetDisplayContext.getPaginationStartParameterName() %>" />

			<liferay-ddm:template-renderer
				className="<%= TypeFacetPortlet.class.getName() %>"
				contextObjects='<%=
					HashMapBuilder.<String, Object>put(
						"assetEntriesSearchFacetDisplayContext", assetEntriesSearchFacetDisplayContext
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).build()
				%>'
				displayStyle="<%= typeFacetPortletInstanceConfiguration.displayStyle() %>"
				displayStyleGroupId="<%= assetEntriesSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= assetEntriesSearchFacetDisplayContext.getBucketDisplayContexts() %>"
			>
				<clay:panel-group>
					<clay:panel
						collapseClassNames="search-facet"
						displayTitle='<%= LanguageUtil.get(request, "type") %>'
						expanded="<%= true %>"
					>
						<c:if test="<%= !assetEntriesSearchFacetDisplayContext.isNothingSelected() %>">
							<clay:button
								cssClass="btn-unstyled c-mb-4 facet-clear-btn"
								displayType="link"
								id='<%= liferayPortletResponse.getNamespace() + "facetAssetEntriesClear" %>'
								onClick="Liferay.Search.FacetUtil.clearSelections(event);"
							>
								<strong><liferay-ui:message key="clear" /></strong>
							</clay:button>
						</c:if>

						<ul class="asset-type list-unstyled">

							<%
							int i = 0;

							for (BucketDisplayContext bucketDisplayContext : assetEntriesSearchFacetDisplayContext.getBucketDisplayContexts()) {
								i++;
							%>

								<li class="facet-value">
									<div class="custom-checkbox custom-control">
										<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
											<input
												autocomplete="off"
												class="custom-control-input facet-term"
												data-term-id="<%= bucketDisplayContext.getFilterValue() %>"
												disabled
												id="<portlet:namespace />term_<%= i %>"
												name="<portlet:namespace />term_<%= i %>"
												onChange="Liferay.Search.FacetUtil.changeSelection(event);"
												type="checkbox"
												<%= bucketDisplayContext.isSelected() ? "checked" : StringPool.BLANK %>
											/>

											<span class="custom-control-label term-name <%= bucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
												<span class="custom-control-label-text">
													<c:choose>
														<c:when test="<%= bucketDisplayContext.isSelected() %>">
															<strong><%= HtmlUtil.escape(bucketDisplayContext.getBucketText()) %></strong>
														</c:when>
														<c:otherwise>
															<%= HtmlUtil.escape(bucketDisplayContext.getBucketText()) %>
														</c:otherwise>
													</c:choose>
												</span>
											</span>

											<c:if test="<%= bucketDisplayContext.isFrequencyVisible() %>">
												<small class="term-count">
													(<%= bucketDisplayContext.getFrequency() %>)
												</small>
											</c:if>
										</label>
									</div>
								</li>

							<%
							}
							%>

						</ul>

						<aui:script use="liferay-search-facet-util">
							Liferay.Search.FacetUtil.enableInputs(
								document.querySelectorAll('#<portlet:namespace />fm .facet-term')
							);
						</aui:script>
					</clay:panel>
				</clay:panel-group>
			</liferay-ddm:template-renderer>
		</aui:form>
	</c:otherwise>
</c:choose>