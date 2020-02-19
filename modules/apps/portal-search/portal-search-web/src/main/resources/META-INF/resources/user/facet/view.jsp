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
page import="com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetTermDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<portlet:defineObjects />

<%
UserSearchFacetDisplayContext userSearchFacetDisplayContext = (UserSearchFacetDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));

if (userSearchFacetDisplayContext.isRenderNothing()) {
	return;
}

UserFacetPortletInstanceConfiguration userFacetPortletInstanceConfiguration = userSearchFacetDisplayContext.getUserFacetPortletInstanceConfiguration();

Map<String, Object> contextObjects = new HashMap<String, Object>();

contextObjects.put("namespace", renderResponse.getNamespace());
contextObjects.put("userSearchFacetDisplayContext", userSearchFacetDisplayContext);

List<UserSearchFacetTermDisplayContext> userSearchFacetTermDisplayContexts = userSearchFacetDisplayContext.getTermDisplayContexts();
%>

<c:choose>
	<c:when test="<%= userSearchFacetDisplayContext.isRenderNothing() %>">
		<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParamName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParamValue() %>" />
	</c:when>
	<c:otherwise>
		<aui:form method="post" name="userFacetFm">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(userSearchFacetDisplayContext.getParamName()) %>" type="hidden" value="<%= userSearchFacetDisplayContext.getParamValue() %>" />
			<aui:input cssClass="facet-parameter-name" name="facet-parameter-name" type="hidden" value="<%= userSearchFacetDisplayContext.getParamName() %>" />

			<liferay-ddm:template-renderer
				className="<%= UserSearchFacetTermDisplayContext.class.getName() %>"
				contextObjects="<%= contextObjects %>"
				displayStyle="<%= userFacetPortletInstanceConfiguration.displayStyle() %>"
				displayStyleGroupId="<%= userSearchFacetDisplayContext.getDisplayStyleGroupId() %>"
				entries="<%= userSearchFacetTermDisplayContexts %>"
			>
				<liferay-ui:panel-container
					extended="<%= true %>"
					id='<%= renderResponse.getNamespace() + "facetUserPanelContainer" %>'
					markupView="lexicon"
					persistState="<%= true %>"
				>
					<liferay-ui:panel
						collapsible="<%= true %>"
						cssClass="search-facet"
						id='<%= renderResponse.getNamespace() + "facetUserPanel" %>'
						markupView="lexicon"
						persistState="<%= true %>"
						title="user"
					>
						<aui:fieldset>
							<ul class="list-unstyled">

								<%
								int i = 0;

								for (UserSearchFacetTermDisplayContext userSearchFacetTermDisplayContext : userSearchFacetDisplayContext.getTermDisplayContexts()) {
									i++;
								%>

									<li class="facet-value">
										<div class="custom-checkbox custom-control">
											<label class="facet-checkbox-label" for="<portlet:namespace />term_<%= i %>">
												<input
													<%= userSearchFacetTermDisplayContext.isSelected() ? "checked" : StringPool.BLANK %>
													class="custom-control-input facet-term"
													data-term-id="<%= HtmlUtil.escapeAttribute(userSearchFacetTermDisplayContext.getUserName()) %>"
													disabled
													id="<portlet:namespace />term_<%= i %>"name="<portlet:namespace />term_<%= i %>"
													onChange="Liferay.Search.FacetUtil.changeSelection(event);"
													type="checkbox"
												/>

												<span class="custom-control-label term-name <%= userSearchFacetTermDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>">
													<span class="custom-control-label-text">
														<%= HtmlUtil.escape(userSearchFacetTermDisplayContext.getUserName()) %>
													</span>
												</span>

												<c:if test="<%= userSearchFacetTermDisplayContext.isFrequencyVisible() %>">
													<small class="term-count">
														(<%= userSearchFacetTermDisplayContext.getFrequency() %>)
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

						<c:if test="<%= !userSearchFacetDisplayContext.isNothingSelected() %>">
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
		document.querySelectorAll('#<portlet:namespace />userFacetFm .facet-term')
	);
</aui:script>