<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = null;
String langType = null;
boolean prototypeGroup = false;
String templateContent = null;
String templateId = null;

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

if (selLayout != null) {
	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	String layoutTemplateId = selLayoutTypePortlet.getLayoutTemplateId();

	if (Validator.isNull(layoutTemplateId)) {
		layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;
	}

	group = selLayout.getGroup();

	if (group.isLayoutPrototype() || group.isLayoutSetPrototype()) {
		prototypeGroup = true;
	}

	Theme selLayoutTheme = selLayout.getTheme();

	if (!prototypeGroup) {
		LayoutTemplate layoutTemplate = LayoutTemplateLocalServiceUtil.getLayoutTemplate(layoutTemplateId, false, selLayoutTheme.getThemeId());

		if (layoutTemplate != null) {
			templateId = layoutTemplate.getThemeId() + LayoutTemplateConstants.CUSTOM_SEPARATOR + selLayoutTypePortlet.getLayoutTemplateId();

			templateContent = LayoutTemplateLocalServiceUtil.getContent(selLayoutTypePortlet.getLayoutTemplateId(), false, layoutTemplate.getThemeId());
		}
	}

	langType = LayoutTemplateLocalServiceUtil.getLangType(layoutTemplateId, false, selLayoutTheme.getThemeId());
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="customization-settings"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<c:choose>
	<c:when test="<%= prototypeGroup %>">
		<clay:alert
			displayType="warning"
			message="it-is-not-possible-to-specify-customization-settings-for-pages-in-site-templates-or-page-templates"
		/>
	</c:when>
	<c:otherwise>
		<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "customizableDescription" %>' label="customizable" labelCssClass="font-weight-normal" name='<%= "TypeSettingsProperties--" + LayoutConstants.CUSTOMIZABLE_LAYOUT + "--" %>' type="checkbox" value="<%= selLayout.isCustomizable() %>" wrapperCssClass="c-mb-2" />

		<p class="text-3 text-secondary" id="<portlet:namespace />customizableDescription">
			<liferay-ui:message key="customizable-help" />
		</p>

		<div class="customization-settings" id="<portlet:namespace />customizationSettingsOptions">

			<%
			if (Validator.isNotNull(templateId) && Validator.isNotNull(templateContent)) {
				CustomizationSettingsUtil.processCustomizationSettings(request, response, new StringTemplateResource(templateId, templateContent), langType);
			}
			%>

		</div>

		<aui:script>
			Liferay.Util.toggleBoxes(
				'<portlet:namespace /><%= LayoutConstants.CUSTOMIZABLE_LAYOUT %>',
				'<portlet:namespace />customizationSettingsOptions'
			);
		</aui:script>
	</c:otherwise>
</c:choose>