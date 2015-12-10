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

<%@ include file="/html/taglib/ui/form_navigator/init.jsp" %>

<c:choose>
	<c:when test="<%= deprecatedCategorySections.length > 0 %>">
		<%@ include file="/html/taglib/ui/form_navigator/lexicon/deprecated_sections.jspf" %>
	</c:when>
	<c:when test="<%= categoryKeys.length > 1 %>">
		<liferay-ui:tabs
			names="<%= StringUtil.merge(categoryKeys) %>"
			refresh="<%= false %>"
			type="tabs nav-tabs-default"
		>

			<%
			for (String categoryKey : categoryKeys) {
				List<FormNavigatorEntry<Object>> formNavigatorEntries = FormNavigatorEntryUtil.getFormNavigatorEntries(id, categoryKey, user, formModelBean);
			%>

				<liferay-ui:section>
					<%@ include file="/html/taglib/ui/form_navigator/lexicon/sections.jspf" %>
				</liferay-ui:section>

			<%
			}
			%>

		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>

		<%
		List<FormNavigatorEntry<Object>> formNavigatorEntries = FormNavigatorEntryUtil.getFormNavigatorEntries(id, user, formModelBean);
		%>

		<%@ include file="/html/taglib/ui/form_navigator/lexicon/sections.jspf" %>
	</c:otherwise>
</c:choose>

<c:if test="<%= showButtons %>">
	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" type="submit" />
	</aui:button-row>
</c:if>