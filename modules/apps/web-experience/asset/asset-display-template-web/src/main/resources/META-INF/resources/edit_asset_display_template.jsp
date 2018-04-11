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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

AssetDisplayTemplate assetDisplayTemplate = assetDisplayTemplateDisplayContext.getAssetDisplayTemplate();

long classNameId = BeanParamUtil.getLong(assetDisplayTemplate, request, "classNameId");
%>

<portlet:actionURL name="/asset_display_template/edit_asset_display_template" var="editAssetDisplayTemplateURL">
	<portlet:param name="mvcPath" value="/edit_asset_display_template.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editAssetDisplayTemplateURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assetDisplayTemplateId" type="hidden" value="<%= (assetDisplayTemplate != null) ? assetDisplayTemplate.getAssetDisplayTemplateId() : 0 %>" />

	<aui:model-context bean="<%= assetDisplayTemplate %>" model="<%= AssetDisplayTemplate.class %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input autoFocus="<%= true %>" cssClass="asset-display-template-name" name="name" placeholder="name" />

				<aui:select label="asset-type" name="classNameId">

					<%
					for (long availableClassNameId : assetDisplayTemplateDisplayContext.getAvailableClassNameIds()) {
						ClassName className = ClassNameLocalServiceUtil.getClassName(availableClassNameId);

						boolean selected = false;

						if ((assetDisplayTemplate != null) && (availableClassNameId == classNameId)) {
							selected = true;
						}
					%>

						<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= selected %>" value="<%= availableClassNameId %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input checked="<%= (assetDisplayTemplate != null) ? assetDisplayTemplate.isMain() : false %>" label="default-template-for-selected-asset-type" name="main" type="checkbox" />
			</liferay-frontend:fieldset>

			<%@ include file="/edit_asset_display_template_script.jspf" %>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<%
	String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
	%>

	<liferay-frontend:edit-form-footer>
		<aui:button onClick="<%= taglibOnClick %>" type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	Liferay.after(
		'<portlet:namespace />saveTemplate',
		function() {
			submitForm(document.<portlet:namespace />fm);
		}
	);
</aui:script>