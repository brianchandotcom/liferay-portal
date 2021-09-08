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
<aui:input name="masterLayoutPlid" type="hidden" />
<aui:input name="styleBookEntryId" type="hidden" />

<%
LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntryByPlid(selLayout.getPlid());

if (layoutPageTemplateEntry == null) {
	layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntryByPlid(selLayout.getClassPK());
}

boolean editableMasterLayout = false;

if ((layoutPageTemplateEntry == null) || !Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {
	editableMasterLayout = true;
}
%>

<c:if test="<%= editableMasterLayout %>">

	<%
	LayoutPageTemplateEntry masterLayoutPageTemplateEntry = null;

	if (selLayout.getMasterLayoutPlid() > 0) {
		masterLayoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.fetchLayoutPageTemplateEntryByPlid(selLayout.getMasterLayoutPlid());
	}
	%>

	<clay:sheet-section>
		<h3 class="sheet-subtitle"><liferay-ui:message key="master" /></h3>

		<p>
			<b><liferay-ui:message key="master-name" />:</b> <span id="<portlet:namespace />masterLayoutName"><%= (masterLayoutPageTemplateEntry != null) ? masterLayoutPageTemplateEntry.getName() : LanguageUtil.get(request, "blank") %></span>
		</p>

		<clay:content-row>

			<%
			String editMasterLayoutURL = StringPool.BLANK;

			if (selLayout.getMasterLayoutPlid() > 0) {
				Layout masterLayout = LayoutLocalServiceUtil.getLayout(selLayout.getMasterLayoutPlid());

				String editLayoutURL = HttpUtil.addParameter(HttpUtil.addParameter(PortalUtil.getLayoutFullURL(selLayout, themeDisplay), "p_l_mode", Constants.EDIT), "p_l_back_url", ParamUtil.getString(request, "redirect"));

				editMasterLayoutURL = HttpUtil.addParameter(HttpUtil.addParameter(PortalUtil.getLayoutFullURL(masterLayout.fetchDraftLayout(), themeDisplay), "p_l_mode", Constants.EDIT), "p_l_back_url", editLayoutURL);
			}
			%>

			<clay:content-col
				cssClass='<%= (masterLayoutPageTemplateEntry == null) ? "hide" : "mr-4" %>'
			>
				<clay:button
					additionalProps='<%=
						HashMapBuilder.<String, Object>put(
							"editableMasterLayout", editableMasterLayout
						).put(
							"editMasterLayoutURL", editMasterLayoutURL
						).build()
					%>'
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "editMasterLayoutButton" %>'
					label="edit-master"
					propsTransformer="js/layout/EditMasterLayoutButtonPropsTransformer"
					small="<%= true %>"
				/>
			</clay:content-col>

			<portlet:renderURL var="changeMasterLayoutURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/select_master_layout.jsp" />
			</portlet:renderURL>

			<clay:content-col>
				<clay:button
					additionalProps='<%=
						HashMapBuilder.<String, Object>put(
							"url", changeMasterLayoutURL.toString()
						).build()
					%>'
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "changeMasterLayoutButton" %>'
					label="change-master"
					propsTransformer="js/layout/ChangeMasterLayoutButtonPropsTransformer"
					small="<%= true %>"
				/>
			</clay:content-col>
		</clay:content-row>
	</clay:sheet-section>
</c:if>

<%
StyleBookEntry styleBookEntry = null;

Group liveGroup = StagingUtil.getLiveGroup(group);

boolean hasStyleBooks = StyleBookEntryLocalServiceUtil.getStyleBookEntriesCount(liveGroup.getGroupId()) > 0;

if (hasStyleBooks && (selLayout.getStyleBookEntryId() > 0)) {
	styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(selLayout.getStyleBookEntryId());
}
%>

<clay:sheet-section>
	<h3 class="sheet-subtitle"><liferay-ui:message key="style-book" /></h3>

	<p>
		<b><liferay-ui:message key="style-book-name" />:</b> <span id="<portlet:namespace />styleBookName"><%= (styleBookEntry != null) ? styleBookEntry.getName() : LanguageUtil.get(request, "inherited") %></span>
	</p>

	<portlet:renderURL var="changeStyleBookURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/select_style_book.jsp" />
		<portlet:param name="selPlid" value="<%= String.valueOf(selLayout.getPlid()) %>" />
		<portlet:param name="editableMasterLayout" value="<%= String.valueOf(editableMasterLayout) %>" />
	</portlet:renderURL>

	<div class="button-holder">
		<clay:button
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"url", changeStyleBookURL.toString()
				).build()
			%>'
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

<c:if test="<%= hasStyleBooks %>">
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