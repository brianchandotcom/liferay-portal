<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetCategory category = (AssetCategory)request.getAttribute("assetCategory");
String assetCategoryURLSeparator = (String)request.getAttribute("assetCategoryURLSeparator");
String titleMapAsXML = (String)request.getAttribute("titleMapAsXML");
long vocabularyId = ParamUtil.getLong(request, "vocabularyId");

String friendlyURLBase = themeDisplay.getPortalURL() + assetCategoryURLSeparator;

long parentCategoryId = BeanParamUtil.getLong(category, request, "parentCategoryId");

PortletURL categoryRedirectURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view_asset_categories.jsp"
).buildPortletURL();

if (parentCategoryId > 0) {
	categoryRedirectURL.setParameter("categoryId", String.valueOf(parentCategoryId));
}

if (vocabularyId > 0) {
	categoryRedirectURL.setParameter("vocabularyId", String.valueOf(vocabularyId));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(categoryRedirectURL.toString());

renderResponse.setTitle(category.getTitle(locale));
%>

<portlet:actionURL name="/commerce_product_asset_categories/edit_asset_category_friendly_url" var="editCategoryURL">
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editCategoryURL %>"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="categoryId" type="hidden" value="<%= category.getCategoryId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset>
			<label for="<portlet:namespace />urlTitleMapAsXML"><liferay-ui:message key="friendly-url" /><liferay-ui:icon-help message='<%= LanguageUtil.format(request, "for-example-x", "<em>news</em>", false) %>' /></label>

			<liferay-ui:input-localized
				defaultLanguageId="<%= LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale()) %>"
				inputAddon="<%= StringUtil.shorten(friendlyURLBase.toString(), 40) %>"
				name="urlTitleMapAsXML"
				xml="<%= HttpComponentsUtil.decodeURL(titleMapAsXML) %>"
			/>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= categoryRedirectURL.toString() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>