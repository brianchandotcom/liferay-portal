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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.configuration.SimilarResultsPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDisplayContext" %><%@
page import="com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDocumentDisplayContext" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
SimilarResultsDisplayContext similarResultsDisplayContext = (SimilarResultsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SimilarResultsPortletInstanceConfiguration similarResultsPortletInstanceConfiguration = similarResultsDisplayContext.getSimilarResultsPortletInstanceConfiguration();

Map<String, Object> contextObjects = new HashMap<String, Object>();

contextObjects.put("similarResultsDisplayContext", similarResultsDisplayContext);

List<SimilarResultsDocumentDisplayContext> similarResultsDocumentDisplayContexts = similarResultsDisplayContext.getSimilarResultsDocumentDisplayContexts();

if (similarResultsDocumentDisplayContexts.isEmpty()) {
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
}
%>

<c:choose>
	<c:when test="<%= similarResultsDocumentDisplayContexts.isEmpty() %>">
		<div class="alert alert-info text-center">
			<div>
				<liferay-ui:message key="there-are-no-similar-results-available" />
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-ddm:template-renderer
			className="<%= SimilarResultsDocumentDisplayContext.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= similarResultsPortletInstanceConfiguration.displayStyle() %>"
			displayStyleGroupId="<%= similarResultsDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= similarResultsDocumentDisplayContexts %>"
		/>
	</c:otherwise>
</c:choose>