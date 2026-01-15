<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean hasModel = GetterUtil.getBoolean(request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.HAS_MODEL));
Layout selLayout = (Layout)request.getAttribute(WebKeys.SEL_LAYOUT);
SiteNavigationMenuItem siteNavigationMenuItem = (SiteNavigationMenuItem)request.getAttribute(SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM);
boolean useCustomName = GetterUtil.getBoolean(request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.USE_CUSTOM_NAME));

String externalReferenceCode = StringPool.BLANK;
String privateLayout = StringPool.BLANK;
String title = StringPool.BLANK;

if ((selLayout == null) && (siteNavigationMenuItem != null)) {
	UnicodeProperties typeSettingsUnicodeProperties = UnicodePropertiesBuilder.fastLoad(
		siteNavigationMenuItem.getTypeSettings()
	).build();

	externalReferenceCode = typeSettingsUnicodeProperties.getProperty("externalReferenceCode");
	privateLayout = typeSettingsUnicodeProperties.getProperty("privateLayout");
	title = typeSettingsUnicodeProperties.getProperty("title");
}
%>

<aui:fieldset>

	<%
	String taglibOnChange = "Liferay.Util.toggleDisabled('#" + liferayPortletResponse.getNamespace() + "nameBoundingBox input, [for=" + liferayPortletResponse.getNamespace() + "name]', !event.target.checked)";
	%>

	<aui:input checked="<%= useCustomName %>" helpMessage="use-custom-name-help" label="use-custom-name" labelCssClass="font-weight-normal" name="TypeSettingsProperties--useCustomName--" onChange="<%= taglibOnChange %>" type="checkbox" />
</aui:fieldset>

<aui:input disabled="<%= !useCustomName %>" label="name" localized="<%= true %>" maxlength='<%= ModelHintsUtil.getMaxLength(SiteNavigationMenuItem.class.getName(), "name") %>' name="name" placeholder="name" required="<%= true %>" type="text" value='<%= SiteNavigationMenuItemUtil.getSiteNavigationMenuItemXML(siteNavigationMenuItem, "name") %>' />

<aui:input id="privateLayout" name="TypeSettingsProperties--privateLayout--" required="<%= true %>" type="hidden" value="<%= (selLayout != null) ? selLayout.isPrivateLayout() : privateLayout %>" />
<aui:input id="title" name="TypeSettingsProperties--title--" type="hidden" value="<%= (selLayout != null) ? selLayout.getName(locale) : title %>" />

<div class="form-group input-text-wrapper mb-2 text-default">
	<aui:input id="externalReferenceCode" name="TypeSettingsProperties--externalReferenceCode--" required="<%= true %>" type="hidden" value="<%= (selLayout != null) ? selLayout.getExternalReferenceCode() : externalReferenceCode %>" />
</div>

<%
String eventName = liferayPortletResponse.getNamespace() + "selectLayout";

ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR);

LayoutItemSelectorCriterion layoutItemSelectorCriterion = new LayoutItemSelectorCriterion();

layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(new UUIDItemSelectorReturnType());
layoutItemSelectorCriterion.setShowBreadcrumb(false);

PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(renderRequest), eventName, layoutItemSelectorCriterion);

if (selLayout != null) {
	itemSelectorURL.setParameter("layoutUuid", selLayout.getUuid());
}
%>

<div class="<%= hasModel ? "d-flex form-group" : "d-flex form-group has-warning" %>">
	<aui:input cssClass="text-secondary" id="itemInput" name='<%= LanguageUtil.get(request, "item") %>' type="text" value='<%= (selLayout != null) ? HtmlUtil.escape(selLayout.getName(locale)) : LanguageUtil.get(request, "none") %>' wrapperCssClass="mb-0 site-navigation-item-selector" />

	<div class="align-self-end ml-2">
		<clay:button
			additionalProps='<%=
				HashMapBuilder.<String, Object>put(
					"eventName", eventName
				).put(
					"itemSelectorURL", itemSelectorURL.toString()
				).build()
			%>'
			aria-label='<%= LanguageUtil.get(request, "change-item") %>'
			cssClass="btn-monospaced form-group-item"
			displayType="secondary"
			icon="change"
			id='<%= liferayPortletResponse.getNamespace() + "chooseLayout" %>'
			propsTransformer="{ChooseLayoutButtonPropsTransformer} from site-navigation-menu-item-layout"
			title='<%= LanguageUtil.get(request, "change-item") %>'
		/>
	</div>
</div>

<c:if test="<%= !hasModel %>">
	<clay:alert
		cssClass="alert-feedback mt-1"
		defaultTitleDisabled="<%= true %>"
		displayType="warning"
		title="no-reference-found"
	/>

	<p class="small text-secondary">
		<liferay-ui:message key="this-item-references-an-entity-that-is-missing-or-not-yet-available" />
	</p>
</c:if>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "editLayout" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"eventName", eventName
		).put(
			"itemSelectorURL", itemSelectorURL.toString()
		).build()
	%>'
	module="{EditLayout} from site-navigation-menu-item-layout"
	servletContext="<%= application %>"
/>