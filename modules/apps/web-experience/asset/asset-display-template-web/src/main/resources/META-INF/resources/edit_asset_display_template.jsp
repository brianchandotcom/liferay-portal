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

Set<Long> availableClassNameIdsSet = SetUtil.fromArray(assetDisplayTemplateDisplayContext.getAvailableClassNameIds());
%>

<portlet:actionURL name="/asset_display_template/edit_asset_display_template" var="editAssetDisplayTemplateURL">
	<portlet:param name="mvcPath" value="/edit_asset_display_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= editAssetDisplayTemplateURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="assetDisplayTemplateId" type="hidden" value='<%= assetDisplayTemplate != null ? String.valueOf(assetDisplayTemplate.getAssetDisplayTemplateId()) : "0" %>' />
	<aui:input name="" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<aui:model-context bean="<%= assetDisplayTemplate %>" model="<%= AssetDisplayTemplate.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" cssClass="asset-display-template-name" name="name" placeholder="name" />

			<aui:select label="asset-type" name="classNameId">

				<%
				for (long curClassNameId : availableClassNameIdsSet) {
					ClassName className = ClassNameLocalServiceUtil.getClassName(curClassNameId);
				%>

					<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= assetDisplayTemplate != null ? curClassNameId == classNameId : false %>" value="<%= curClassNameId %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input checked="<%= assetDisplayTemplate != null ? assetDisplayTemplate.isMain() : false %>" label="default-template-for-this-asset-type" name="main" type="checkbox" />
		</aui:fieldset>

		<%@ include file="/edit_asset_display_template_script.jspf" %>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:script>
			Liferay.after(
				'<portlet:namespace />saveTemplate',
				function() {
					submitForm(document.<portlet:namespace />fm);
				}
			);
		</aui:script>

		<%
		String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
		%>

		<aui:button cssClass="btn-lg" onClick="<%= taglibOnClick %>" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>