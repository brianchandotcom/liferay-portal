<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<portlet:defineObjects />

<%
String backURL = (String)request.getAttribute("liferay-ui:form-navigator:backURL");
String[][] categorySections = (String[][])request.getAttribute("liferay-ui:form-navigator:categorySections");
String[] categoryNames = (String[])request.getAttribute("liferay-ui:form-navigator:categoryNames");
String formName = GetterUtil.getString((String)request.getAttribute("liferay-ui:form-navigator:formName"));
String htmlBottom = (String)request.getAttribute("liferay-ui:form-navigator:htmlBottom");
String htmlTop = (String)request.getAttribute("liferay-ui:form-navigator:htmlTop");
String jspPath = (String)request.getAttribute("liferay-ui:form-navigator:jspPath");
boolean showButtons = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:form-navigator:showButtons"));

if (Validator.isNull(backURL)) {
	PortletURL portletURL = liferayPortletResponse.createRenderURL();

	backURL = portletURL.toString();
}

String[] allSections = new String[0];

for (String[] categorySection : categorySections) {
	allSections = ArrayUtil.append(allSections, categorySection);
}

String curSection = StringPool.BLANK;

if (categorySections[0].length > 0) {
	curSection = categorySections[0][0];
}

String historyKey = ParamUtil.getString(request, "historyKey");

if (Validator.isNotNull(historyKey)) {
	curSection = historyKey;
}
%>

<div class="taglib-form-navigator">
	<div id="<portlet:namespace />sectionsContainer">

		<%
		for (String section : allSections) {
			String sectionId = namespace + _getSectionId(section);
			String sectionJsp = jspPath + _getSectionJsp(section) + ".jsp";
		%>

			<!-- Begin fragment <%= sectionId %> -->

			<div class="form-section <%= (curSection.equals(section) || curSection.equals(sectionId)) ? "selected" : "aui-helper-hidden-accessible" %>" id="<%= sectionId %>">
				<liferay-util:include page="<%= sectionJsp %>" portletId="<%= portletDisplay.getRootPortletId() %>" />
			</div>

			<!-- End fragment <%= sectionId %> -->

		<%
		}
		%>

		<div class="lfr-component form-navigator">
			<%= Validator.isNotNull(htmlTop) ? htmlTop : StringPool.BLANK %>

			<%
			String[] modifiedSections = StringUtil.split(ParamUtil.getString(request, "modifiedSections"));

			for (int i = 0; i < categoryNames.length; i++) {
				String category = categoryNames[i];
				String[] sections = categorySections[i];

				if (sections.length > 0) {
			%>

					<div class="menu-group">
						<c:if test="<%= Validator.isNotNull(category) %>">
							<h3><liferay-ui:message key="<%= category %>" /></h3>
						</c:if>

						<ul>

							<%
							String errorSection = (String)request.getAttribute("errorSection");

							if (Validator.isNotNull(errorSection)) {
								curSection = StringPool.BLANK;
							}

							for (String section : sections) {
								String sectionId = namespace + _getSectionId(section);

								Boolean show = (Boolean)request.getAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + sectionId);

								if ((show != null) && !show.booleanValue()) {
									continue;
								}

								boolean error = false;

								if (sectionId.equals(errorSection)) {
									error = true;

									curSection = section;
								}

								String cssClass = StringPool.BLANK;

								if (curSection.equals(section) || curSection.equals(sectionId)) {
									cssClass += "selected";
								}

								if (ArrayUtil.contains(modifiedSections, sectionId)) {
									cssClass += " section-modified";
								}

								if (error) {
									cssClass += " section-error";
								}
							%>

								<li class="<%= cssClass %>">
									<a href="#<%= sectionId %>" id="<%= sectionId %>Link">

									<liferay-ui:message key="<%= section %>" />

									<span class="modified-notice"> (<liferay-ui:message key="modified" />) </span>

									<c:if test="<%= error %>">
										<span class="error-notice"> (<liferay-ui:message key="error" />) </span>
									</c:if>

									</a>
								</li>

							<%
							}
							%>

						</ul>
					</div>

			<%
				}
			}
			%>

			<c:if test="<%= showButtons %>">
				<aui:button-row>
					<aui:button type="submit" />

					<%
					String taglibOnClick = "location.href = location.href.replace(location.hash, '');";
					%>

					<aui:button href="<%= backURL %>" onClick="<%= taglibOnClick %>" type="cancel" />
				</aui:button-row>
			</c:if>

			<%= Validator.isNotNull(htmlBottom) ? htmlBottom : StringPool.BLANK %>
		</div>
	</div>
</div>

<aui:script use="liferay-form-navigator">
	var <portlet:namespace />formNavigator = new Liferay.FormNavigator(
		{
			container: '#<portlet:namespace />sectionsContainer',
			defaultModifiedSections: <%= JS.toScript(modifiedSections) %>,
			formName: '<portlet:namespace /><%= formName %>',
			modifiedSections: '<portlet:namespace />modifiedSections',
			namespace: '<portlet:namespace />'
		}
	);

	<%
	String errorSection = (String)request.getAttribute("errorSection");
	%>

	<c:if test="<%= Validator.isNotNull(errorSection) %>">
		<portlet:namespace />formNavigator._revealSection('#<%= namespace + errorSection %>', '');
	</c:if>
</aui:script>

<aui:script use="aui-base">
	var modifyLinks = A.all('.modify-link');

	if (modifyLinks) {
		modifyLinks.on(
			'click',
			function() {
				A.fire(
					'formNavigator:trackChanges',
					A.one('.selected .modify-link')
				);
			}
		);
	}
</aui:script>

<%!
private String _getSectionId(String name) {
	return TextFormatter.format(name, TextFormatter.M);
}

private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}
%>