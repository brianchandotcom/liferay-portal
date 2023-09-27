<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = layoutsAdminDisplayContext.getGroup();

LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme rootTheme = layoutSet.getTheme();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="look-and-feel"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<%
String taglibLabel = null;

if (group.isLayoutPrototype()) {
	taglibLabel = LanguageUtil.get(request, "use-the-same-look-and-feel-of-the-pages-in-which-this-template-is-used");
}
else {
	taglibLabel = LanguageUtil.get(request, "use-the-inherited-theme");
}
%>

<div id="<portlet:namespace />themeContainer">
	<clay:radio
		checked="<%= selLayout.isInheritLookAndFeel() %>"
		disabled="<%= layoutsAdminDisplayContext.isReadOnly() %>"
		id='<%= liferayPortletResponse.getNamespace() + "regularInheritLookAndFeel" %>'
		label="<%= taglibLabel %>"
		name='<%= liferayPortletResponse.getNamespace() + "regularInheritLookAndFeel" %>'
		value="true"
	/>

	<clay:radio
		checked="<%= !selLayout.isInheritLookAndFeel() %>"
		disabled="<%= layoutsAdminDisplayContext.isReadOnly() %>"
		id='<%= liferayPortletResponse.getNamespace() + "regularUniqueLookAndFeel" %>'
		label='<%= LanguageUtil.get(request, "define-a-custom-theme-for-this-page") %>'
		name='<%= liferayPortletResponse.getNamespace() + "regularInheritLookAndFeel" %>'
		value="false"
	/>

	<c:if test="<%= !group.isLayoutPrototype() %>">
		<div class="lfr-inherit-theme-options <%= selLayout.isInheritLookAndFeel() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />inheritThemeOptions">
			<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>">
				<liferay-util:param name="companyId" value="<%= String.valueOf(group.getCompanyId()) %>" />
				<liferay-util:param name="editable" value="<%= Boolean.FALSE.toString() %>" />
				<liferay-util:param name="themeId" value="<%= rootTheme.getThemeId() %>" />
			</liferay-util:include>
		</div>
	</c:if>

	<div class="lfr-inherit-theme-options <%= !selLayout.isInheritLookAndFeel() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />themeOptions">
		<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />
	</div>
</div>

<aui:script sandbox="<%= true %>">
	const regularCss = document.getElementById('<portlet:namespace />regularCss');
	const regularCssAlert = document.getElementById(
		'<portlet:namespace />regularCssAlert'
	);
	const regularCssLabel = document.querySelector(
		'[for="<portlet:namespace />regularCss"]'
	);
	const regularInheritLookAndFeel = document.getElementById(
		'<portlet:namespace />regularInheritLookAndFeel'
	);
	const regularUniqueLookAndFeel = document.getElementById(
		'<portlet:namespace />regularUniqueLookAndFeel'
	);
	const inheritThemeOptions = document.getElementById(
		'<portlet:namespace />inheritThemeOptions'
	);
	const themeOptions = document.getElementById(
		'<portlet:namespace />themeOptions'
	);

	const themeContainer = document.getElementById(
		'<portlet:namespace />themeContainer'
	);

	const sheet = themeContainer.closest('.sheet');

	if (<%= selLayout.getMasterLayoutPlid() > 0 %>) {
		sheet.classList.add('hide');
		sheet.setAttribute('aria-hidden', 'true');
	}

	if (regularInheritLookAndFeel) {
		regularInheritLookAndFeel.addEventListener('change', (event) => {
			event.target.checked = true;
			regularUniqueLookAndFeel.checked = false;

			if (inheritThemeOptions) {
				inheritThemeOptions.classList.toggle('hide');
			}

			if (themeOptions) {
				themeOptions.classList.toggle('hide');
			}

			regularCssAlert.classList.toggle('d-none', false);
			Liferay.Util.toggleDisabled([regularCss, regularCssLabel], true);
		});
	}

	if (regularUniqueLookAndFeel) {
		regularUniqueLookAndFeel.addEventListener('change', (event) => {
			event.target.checked = true;
			regularInheritLookAndFeel.checked = false;

			if (inheritThemeOptions) {
				inheritThemeOptions.classList.toggle('hide');
			}

			if (themeOptions) {
				themeOptions.classList.toggle('hide');
			}

			regularCssAlert.classList.toggle('d-none', true);
			Liferay.Util.toggleDisabled([regularCss, regularCssLabel], false);
		});
	}
</aui:script>