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

	<div class="sheet-section">
		<h3 class="sheet-subtitle"><liferay-ui:message key="master" /></h3>

		<p>
			<b><liferay-ui:message key="master-name" />:</b> <span id="<portlet:namespace />masterLayoutName"><%= (masterLayoutPageTemplateEntry != null) ? masterLayoutPageTemplateEntry.getName() : LanguageUtil.get(request, "blank") %></span>
		</p>

		<div class="button-holder">
			<clay:button
				elementClasses='<%= (masterLayoutPageTemplateEntry == null) ? "btn-secondary hide" : "btn-secondary" %>'
				id='<%= renderResponse.getNamespace() + "editMasterLayoutButton" %>'
				label='<%= LanguageUtil.get(request, "edit-master") %>'
				size="sm"
				style="<%= false %>"
			/>

			<clay:button
				elementClasses="btn-secondary"
				id='<%= renderResponse.getNamespace() + "changeMasterLayoutButton" %>'
				label='<%= LanguageUtil.get(request, "change-master") %>'
				size="sm"
				style="<%= false %>"
			/>
		</div>
	</div>
</c:if>

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

<div class="sheet-section <%= (selLayout.getMasterLayoutPlid() <= 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />themeContainer">
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
</div>

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

<c:if test="<%= editableMasterLayout %>">
	<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
		var changeMasterLayoutButton = document.getElementById(
			'<portlet:namespace />changeMasterLayoutButton'
		);

		var editMasterLayoutButton = document.getElementById(
			'<portlet:namespace />editMasterLayoutButton'
		);

		var masterLayoutPlid = document.getElementById(
			'<portlet:namespace />masterLayoutPlid'
		);

		var oldMasterLayoutPlid = masterLayoutPlid.value;

		var themeContainer = document.getElementById(
			'<portlet:namespace />themeContainer'
		);

		var changeMasterLayoutButtonEventListener = changeMasterLayoutButton.addEventListener(
			'click',
			function(event) {
				var itemSelectorDialog = new ItemSelectorDialog.default({
					buttonAddLabel: '<liferay-ui:message key="done" />',
					eventName: '<portlet:namespace />selectMasterLayout',
					title: '<liferay-ui:message key="select-master" />',
					url:
						'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_master_layout.jsp" /></portlet:renderURL>'
				});

				itemSelectorDialog.open();

				itemSelectorDialog.on('selectedItemChange', function(event) {
					var selectedItem = event.selectedItem;

					if (selectedItem) {
						var masterLayoutName = document.getElementById(
							'<portlet:namespace />masterLayoutName'
						);

						masterLayoutName.innerHTML = selectedItem.name;

						masterLayoutPlid.value = selectedItem.plid;

						if (masterLayoutPlid.value == 0) {
							themeContainer.classList.remove('hide');
						}
						else {
							themeContainer.classList.add('hide');
						}

						if (
							masterLayoutPlid.value == oldMasterLayoutPlid &&
							masterLayoutPlid.value != 0
						) {
							editMasterLayoutButton.classList.remove('hide');
						}
						else {
							editMasterLayoutButton.classList.add('hide');
						}
					}
				});
			}
		);

		<%
		String editMasterLayoutURL = StringPool.BLANK;

		if (selLayout.getMasterLayoutPlid() > 0) {
			Layout masterLayout = LayoutLocalServiceUtil.getLayout(selLayout.getMasterLayoutPlid());

			Layout masterDraftLayout = LayoutLocalServiceUtil.fetchLayout(PortalUtil.getClassNameId(Layout.class), masterLayout.getPlid());

			String editLayoutURL = HttpUtil.addParameter(HttpUtil.addParameter(PortalUtil.getLayoutFullURL(selLayout, themeDisplay), "p_l_mode", Constants.EDIT), "p_l_back_url", ParamUtil.getString(request, "redirect"));

			editMasterLayoutURL = HttpUtil.addParameter(HttpUtil.addParameter(PortalUtil.getLayoutFullURL(masterDraftLayout, themeDisplay), "p_l_mode", Constants.EDIT), "p_l_back_url", editLayoutURL);
		}
		%>

		var editMasterLayoutButtonEventListener = editMasterLayoutButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.navigate('<%= editMasterLayoutURL %>');
			}
		);
	</aui:script>
</c:if>