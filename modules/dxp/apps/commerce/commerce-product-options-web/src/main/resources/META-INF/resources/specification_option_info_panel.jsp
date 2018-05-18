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

<%
List<CPSpecificationOption> cpSpecificationOptions = (List<CPSpecificationOption>)request.getAttribute(CPWebKeys.CP_SPECIFICATION_OPTIONS);

if (cpSpecificationOptions == null) {
	cpSpecificationOptions = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpSpecificationOptions.size() == 1 %>">

		<%
		CPSpecificationOption cpSpecificationOption = cpSpecificationOptions.get(0);

		request.setAttribute("info_panel.jsp-entry", cpSpecificationOption);
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include page="/specification_option_action.jsp" servletContext="<%= application %>" />
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpSpecificationOption.getTitle(locale)) %></h4>
		</div>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpSpecificationOption.getCPSpecificationOptionId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpSpecificationOptions.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>