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

<liferay-ui:panel-container id="tabs" markupView="lexicon">

	<%
	for (int i = 0; i < deprecatedCategorySections.length; i++) {
		String section = deprecatedCategorySections[i];

		String sectionId = namespace + _getSectionId(section);
		String sectionJsp = jspPath + _getSectionJsp(section) + ".jsp";
	%>

		<!-- Begin fragment <%= sectionId %> -->

		<liferay-ui:panel id="<%= sectionId %>" markupView="lexicon" title="<%= section %>">
			<liferay-util:include page="<%= sectionJsp %>" portletId="<%= portletDisplay.getRootPortletId() %>" />
		</liferay-ui:panel>

		<!-- End fragment <%= sectionId %> -->

	<%
	}

	List<FormNavigatorEntry<Object>> formNavigatorEntries = FormNavigatorEntryUtil.getFormNavigatorEntries(id, user, formModelBean);

	for (int i = 0; i < formNavigatorEntries.size(); i++) {
		final FormNavigatorEntry formNavigatorEntry = formNavigatorEntries.get(i);

		String sectionId = namespace + _getSectionId(formNavigatorEntry.getKey());
	%>

		<!-- Begin fragment <%= sectionId %> -->

		<liferay-ui:panel id="<%= sectionId %>" markupView="lexicon" title="<%= formNavigatorEntry.getLabel(locale) %>">

			<%
			PortalIncludeUtil.include(
				pageContext,
				new PortalIncludeUtil.HTMLRenderer() {

					public void renderHTML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
						formNavigatorEntry.include(request, response);
					}

				});
			%>

		</liferay-ui:panel>

		<!-- End fragment <%= sectionId %> -->

	<%
	}
	%>

</liferay-ui:panel-container>

<c:if test="<%= showButtons %>">
	<aui:button-row>
		<aui:button cssClass="btn-lg btn-primary" type="submit" />
	</aui:button-row>
</c:if>