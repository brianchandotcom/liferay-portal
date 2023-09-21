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
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.configuration.CustomFacetPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortlet" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext" %>

<portlet:defineObjects />

<%
CustomFacetDisplayContext customFacetDisplayContext = (CustomFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

CustomFacetPortletInstanceConfiguration customFacetPortletInstanceConfiguration = customFacetDisplayContext.getCustomFacetPortletInstanceConfiguration();
%>

<c:choose>
	<c:when test="<%= customFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(customFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= customFacetDisplayContext.getParameterValue() %>" />
	</c:when>
	<c:otherwise>
		<aui:form action="#" method="post" name="fm">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(customFacetDisplayContext.getParameterName()) %>" type="hidden" value="<%= customFacetDisplayContext.getParameterValue() %>" />
			<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= customFacetDisplayContext.getParameterName() %>" />
			<aui:input cssClass="start-parameter-name" name="start-parameter-name" type="hidden" value="<%= customFacetDisplayContext.getPaginationStartParameterName() %>" />

			<liferay-ddm:template-renderer
				className="<%= CustomFacetPortlet.class.getName() %>"
				contextObjects='<%=
					HashMapBuilder.<String, Object>put(
						"customFacetDisplayContext", customFacetDisplayContext
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).build()
				%>'
				displayStyle="<%= customFacetPortletInstanceConfiguration.displayStyle() %>"
				displayStyleGroupId="<%= customFacetDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= customFacetDisplayContext.getBucketDisplayContexts() %>"
			>
				<clay:panel-group>
					<clay:panel
						collapseClassNames="search-facet"
						displayTitle="<%= customFacetDisplayContext.getDisplayCaption() %>"
						expanded="<%= true %>"
					>
						<c:if test="<%= !customFacetDisplayContext.isNothingSelected() %>">
							<clay:button
								cssClass="btn-unstyled c-mb-4 facet-clear-btn"
								displayType="link"
								id='<%= liferayPortletResponse.getNamespace() + "facetCustomClear" %>'
								onClick="Liferay.Search.FacetUtil.clearSelections(event);"
							>
								<strong><liferay-ui:message key="clear" /></strong>
							</clay:button>
						</c:if>

						<ul class="list-unstyled">

							<%
							int i = 0;

							for (BucketDisplayContext bucketDisplayContext : customFacetDisplayContext.getBucketDisplayContexts()) {
								i++;
							%>

								<li class="facet-value">
									<div class="custom-checkbox custom-control">
										<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
											<input class="custom-control-input facet-term" data-term-id="<%= HtmlUtil.escapeAttribute(bucketDisplayContext.getFilterValue()) %>" disabled id="<portlet:namespace />term_<%= i %>" name="<portlet:namespace />term_<%= i %>" onChange="Liferay.Search.FacetUtil.changeSelection(event);" type="checkbox" <%= bucketDisplayContext.isSelected() ? "checked" : StringPool.BLANK %> />

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
							var facetTerms = document.querySelectorAll(
								'#<portlet:namespace />fm .facet-term'
							);

							facetTerms.forEach((term) => {
								Liferay.Util.toggleDisabled(term, false);
							});
						</aui:script>
					</clay:panel>
				</clay:panel-group>
			</liferay-ddm:template-renderer>
		</aui:form>
	</c:otherwise>
</c:choose>