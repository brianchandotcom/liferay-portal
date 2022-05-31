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

<%@ include file="/init.jsp" %>

<%
Group group = layoutsAdminDisplayContext.getGroup();

LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

Theme rootTheme = layoutSet.getTheme();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

String rootNodeName = layoutsAdminDisplayContext.getRootNodeName();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="look-and-feel"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<aui:input name="devices" type="hidden" value="regular" />
<aui:input name="styleBookEntryId" type="hidden" />
<aui:input name="faviconCETExternalReferenceCode" type="hidden" />
<aui:input name="faviconFileEntryId" type="hidden" />

<%
LayoutLookAndFeelDisplayContext layoutLookAndFeelDisplayContext = new LayoutLookAndFeelDisplayContext(request, layoutsAdminDisplayContext, liferayPortletResponse);
%>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="favicon" /></h3>

	<img alt="<%= HtmlUtil.escape(layoutLookAndFeelDisplayContext.getFaviconFileEntryTitle()) %>" class="mb-2" height="16" id="<portlet:namespace />faviconFileEntryImage" src="<%= layoutLookAndFeelDisplayContext.getFaviconImage() %>" width="16" />

	<p>
		<b><liferay-ui:message key="favicon-name" />:</b> <span id="<portlet:namespace />faviconFileEntryTitle"><%= layoutLookAndFeelDisplayContext.getFaviconFileEntryTitle() %></span>
	</p>

	<clay:content-row>
		<clay:content-col
			cssClass="mr-4"
		>
			<clay:button
				additionalProps="<%=
					layoutLookAndFeelDisplayContext.getChangeFaviconButtonAdditionalProps()
				%>"
				displayType="secondary"
				id='<%= liferayPortletResponse.getNamespace() + "changeFaviconButton" %>'
				label="change-favicon"
				propsTransformer="js/layout/ChangeFaviconButtonPropsTransformer"
				small="<%= true %>"
			/>
		</clay:content-col>

		<clay:content-col>
			<clay:button
				additionalProps="<%=
					layoutLookAndFeelDisplayContext.getClearFaviconButtonAdditionalProps()
				%>"
				disabled="<%= !layoutLookAndFeelDisplayContext.isClearFaviconButtonEnabled() %>"
				displayType="secondary"
				id='<%= liferayPortletResponse.getNamespace() + "clearFaviconButton" %>'
				label="clear"
				propsTransformer="js/layout/ClearFaviconButtonPropsTransformer"
				small="<%= true %>"
			/>
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>

<c:if test="<%= layoutLookAndFeelDisplayContext.hasEditableMasterLayout() %>">
	<clay:sheet-section>
		<react:component
			module="js/layout/look_and_feel/MasterLayoutConfiguration"
			props="<%= layoutLookAndFeelDisplayContext.getMasterLayoutConfigurationProps() %>"
		/>
	</clay:sheet-section>
</c:if>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="style-book" /></h3>

	<p>
		<b><liferay-ui:message key="style-book-name" />:</b> <span id="<portlet:namespace />styleBookName"><%= layoutLookAndFeelDisplayContext.getStyleBookEntryName() %></span>
	</p>

	<div class="button-holder">
		<clay:button
			additionalProps="<%=
				layoutLookAndFeelDisplayContext.getChangeStyleBookButtonAdditionalProps()
			%>"
			displayType="secondary"
			id='<%= liferayPortletResponse.getNamespace() + "changeStyleBookButton" %>'
			label="change-style-book"
			propsTransformer="js/layout/ChangeStyleBookButtonPropsTransformer"
			small="<%= true %>"
		/>
	</div>
</clay:sheet-section>

<liferay-util:buffer
	var="rootNodeNameLink"
>
	<c:choose>
		<c:when test="<%= themeDisplay.isStateExclusive() %>">
			<%= HtmlUtil.escape(rootNodeName) %>
		</c:when>
		<c:otherwise>
			<aui:a href="<%= redirectURL.toString() %>"><%= HtmlUtil.escape(rootNodeName) %></aui:a>
		</c:otherwise>
	</c:choose>
</liferay-util:buffer>

<%
String taglibLabel = null;

if (group.isLayoutPrototype()) {
	taglibLabel = LanguageUtil.get(request, "use-the-same-look-and-feel-of-the-pages-in-which-this-template-is-used");
}
else {
	taglibLabel = LanguageUtil.format(request, "use-the-same-look-and-feel-of-the-x", rootNodeNameLink, false);
}
%>

<clay:sheet-section
	cssClass='<%= (selLayout.getMasterLayoutPlid() <= 0) ? StringPool.BLANK : "hide" %>'
	id='<%= liferayPortletResponse.getNamespace() + "themeContainer" %>'
>
	<h3 class="sheet-subtitle"><liferay-ui:message key="theme" /></h3>

	<aui:input checked="<%= selLayout.isInheritLookAndFeel() %>" id="regularInheritLookAndFeel" label="<%= taglibLabel %>" name="regularInheritLookAndFeel" type="radio" value="<%= true %>" />

	<aui:input checked="<%= !selLayout.isInheritLookAndFeel() %>" id="regularUniqueLookAndFeel" label="define-a-specific-look-and-feel-for-this-page" name="regularInheritLookAndFeel" type="radio" value="<%= false %>" />

	<c:if test="<%= !group.isLayoutPrototype() %>">
		<div class="lfr-inherit-theme-options" id="<portlet:namespace />inheritThemeOptions">
			<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>">
				<liferay-util:param name="companyId" value="<%= String.valueOf(group.getCompanyId()) %>" />
				<liferay-util:param name="editable" value="<%= Boolean.FALSE.toString() %>" />
				<liferay-util:param name="themeId" value="<%= rootTheme.getThemeId() %>" />
			</liferay-util:include>
		</div>
	</c:if>

	<div class="lfr-theme-options" id="<portlet:namespace />themeOptions">
		<liferay-util:include page="/look_and_feel_themes.jsp" servletContext="<%= application %>" />
	</div>
</clay:sheet-section>

<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153457")) %>'>
	<liferay-util:include page="/look_and_feel_theme_css.jsp" servletContext="<%= application %>" />

	<clay:sheet-section>
		<div>
			<react:component
				module="js/layout/look_and_feel/CSSExtensionsConfiguration"
				props="<%= layoutLookAndFeelDisplayContext.getCSSExtensionsConfigurationProps() %>"
			/>
		</div>
	</clay:sheet-section>
</c:if>

<aui:script>
	Liferay.Util.toggleRadio(
		'<portlet:namespace />regularInheritLookAndFeel',
		'<portlet:namespace />inheritThemeOptions',
		'<portlet:namespace />themeOptions'
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />regularUniqueLookAndFeel',
		'<portlet:namespace />themeOptions',
		'<portlet:namespace />inheritThemeOptions'
	);
</aui:script>

<c:if test="<%= layoutLookAndFeelDisplayContext.hasStyleBooks() %>">
	<aui:script>
		var regularInheritLookAndFeel = document.getElementById(
			'<portlet:namespace />regularInheritLookAndFeel'
		);

		var regularUniqueLookAndFeelCheckbox = document.getElementById(
			'<portlet:namespace />regularUniqueLookAndFeel'
		);

		var styleBookWarning = document.getElementById(
			'<portlet:namespace />styleBookWarning'
		);

		regularInheritLookAndFeel.addEventListener('change', (event) => {
			if (event.target.checked) {
				styleBookWarning.classList.add('hide');
			}
		});

		regularUniqueLookAndFeelCheckbox.addEventListener('change', (event) => {
			if (event.target.checked) {
				styleBookWarning.classList.remove('hide');
			}
		});
	</aui:script>
</c:if>