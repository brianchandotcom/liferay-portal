<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/form_navigator/init.jsp" %>

<%
FormNavigatorDisplayContext formNavigatorDisplayContext = new FormNavigatorDisplayContext(request);

List<FormNavigatorEntry<Object>> formNavigatorEntries = (List<FormNavigatorEntry<Object>>)request.getAttribute(FormNavigatorWebKeys.FORM_NAVIGATOR_ENTRIES);

String errorSection = null;

int i = 0;

for (FormNavigatorEntry<Object> curFormNavigatorEntry : formNavigatorEntries) {
	String sectionId = namespace + formNavigatorDisplayContext.getSectionId(curFormNavigatorEntry.getKey());

	String label = curFormNavigatorEntry.getLabel(locale);

	if ((i == 0) && (formNavigatorEntries.size() == 1) && (formNavigatorDisplayContext.getType() != FormNavigatorConstants.FormNavigatorType.SHEET_SECTIONS)) {
		label = StringPool.BLANK;
	}
%>

	<!-- Begin fragment <%= HtmlUtil.escape(sectionId) %> -->

	<c:choose>
		<c:when test="<%= formNavigatorDisplayContext.getType() == FormNavigatorConstants.FormNavigatorType.SHEET_SECTIONS %>">

			<%
			String[] categoryKeys = formNavigatorDisplayContext.getCategoryKeys();
			%>

			<clay:sheet
				cssClass="mb-4 mt-4"
				size='<%= (categoryKeys.length == 1) ? "full" : "lg" %>'
			>
				<clay:sheet-section>
					<h3 class="mb-4"><%= label %></h3>

					<%
					PortalIncludeUtil.include(pageContext, curFormNavigatorEntry::include);
					%>

				</clay:sheet-section>
			</clay:sheet>
		</c:when>
		<c:otherwise>
			<liferay-frontend:fieldset
				collapsed="<%= i != 0 %>"
				collapsible="<%= (i != 0) || (formNavigatorEntries.size() > 1) %>"
				cssClass="<%= formNavigatorDisplayContext.getFieldSetCssClass() %>"
				id="<%= formNavigatorDisplayContext.getSectionId(curFormNavigatorEntry.getKey()) %>"
				label="<%= label %>"
			>

				<%
				PortalIncludeUtil.include(pageContext, curFormNavigatorEntry::include);
				%>

			</liferay-frontend:fieldset>
		</c:otherwise>
	</c:choose>

	<!-- End fragment <%= HtmlUtil.escape(sectionId) %> -->

<%
	String curErrorSection = (String)request.getAttribute(WebKeys.ERROR_SECTION);

	if (Objects.equals(formNavigatorDisplayContext.getSectionId(curFormNavigatorEntry.getKey()), formNavigatorDisplayContext.getSectionId(curErrorSection))) {
		errorSection = curErrorSection;

		request.setAttribute(WebKeys.ERROR_SECTION, null);
	}

	i++;
}
%>

<c:if test="<%= Validator.isNotNull(errorSection) %>">

	<%
	String currentTab = (String)request.getAttribute(FormNavigatorWebKeys.CURRENT_TAB);

	request.setAttribute(FormNavigatorWebKeys.ERROR_TAB, currentTab);
	%>

	<aui:script sandbox="<%= true %>">
		var focusField;

		var sectionContent = document.querySelector(
			'#<%= formNavigatorDisplayContext.getSectionId(errorSection) %>Content'
		);

		<%
		String focusField = (String)request.getAttribute("liferay-ui:error:focusField");
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(focusField) %>">
				focusField = sectionContent.querySelector(
					'#<portlet:namespace /><%= HtmlUtil.escapeJS(focusField) %>'
				);
			</c:when>
			<c:otherwise>
				focusField = sectionContent.querySelector('input:not([type="hidden"]).field');
			</c:otherwise>
		</c:choose>

		Liferay.once('<portlet:namespace />formReady', (event) => {
			if (!sectionContent.classList.contains('show')) {
				if (focusField) {
					Liferay.on('liferay.collapse.shown', (event) => {
						var panelId = event.panel.getAttribute('id');

						if (panelId === sectionContent.getAttribute('id')) {
							Liferay.Util.focusFormField(focusField);
						}
					});
				}

				Liferay.CollapseProvider.show({panel: sectionContent});
			}
			else if (focusField) {
				Liferay.Util.focusFormField(focusField);
			}
		});
	</aui:script>
</c:if>