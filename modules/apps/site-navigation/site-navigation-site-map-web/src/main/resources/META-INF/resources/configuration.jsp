<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<div class="display-template">
				<liferay-template:template-selector
					className="<%= LayoutSet.class.getName() %>"
					displayStyle="<%= siteNavigationSiteMapPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupKey="<%= siteNavigationSiteMapDisplayContext.getDisplayStyleGroupKey() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>

			<%
			Layout rootLayout = siteNavigationSiteMapDisplayContext.getRootLayout();
			%>

			<liferay-frontend:resource-selector
				inputLabel='<%= LanguageUtil.get(request, "root-layout") %>'
				inputName="preferences--rootLayoutUuid--"
				modalTitle='<%= LanguageUtil.get(request, "select-layout") %>'
				resourceName="<%= Validator.isNotNull(rootLayout) ? rootLayout.getName(themeDisplay.getSiteDefaultLocale()) : StringPool.BLANK %>"
				resourceNameKey="name"
				resourceValue="<%= Validator.isNotNull(rootLayout) ? siteNavigationSiteMapPortletInstanceConfiguration.rootLayoutUuid() : StringPool.BLANK %>"
				resourceValueKey="id"
				selectEventName="selectLayout"
				selectResourceURL="<%= siteNavigationSiteMapDisplayContext.getItemSelectorURL() %>"
				showRemoveButton="<%= false %>"
			/>

			<aui:select name="preferences--displayDepth--">
				<aui:option label="unlimited" value="0" />

				<%
				for (int i = 1; i <= 20; i++) {
				%>

					<aui:option label="<%= i %>" selected="<%= siteNavigationSiteMapPortletInstanceConfiguration.displayDepth() == i %>" />

				<%
				}
				%>

			</aui:select>

			<div class="<%= Validator.isNotNull(siteNavigationSiteMapPortletInstanceConfiguration.rootLayoutUuid()) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />includeRootInTreeContainer">
				<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--includeRootInTree--" type="toggle-switch" value="<%= siteNavigationSiteMapDisplayContext.isIncludeRootInTree() %>" />
			</div>

			<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--showCurrentPage--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.showCurrentPage() %>" />

			<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--useHtmlTitle--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.useHtmlTitle() %>" />

			<aui:input inlineLabel="right" labelCssClass="simple-toggle-switch" name="preferences--showHiddenPages--" type="toggle-switch" value="<%= siteNavigationSiteMapPortletInstanceConfiguration.showHiddenPages() %>" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />isVisible(currentValue, value) {
		return currentValue != '';
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />rootLayoutUuid',
		<portlet:namespace />isVisible,
		'<portlet:namespace />includeRootInTreeContainer'
	);
</aui:script>