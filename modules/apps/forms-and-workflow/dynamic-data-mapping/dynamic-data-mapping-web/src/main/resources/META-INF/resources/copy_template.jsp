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
DDMTemplate template = (DDMTemplate)request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE);

String portletResource = ParamUtil.getString(request, "portletResource");
long templateId = BeanParamUtil.getLong(template, request, "templateId");
long classNameId = BeanParamUtil.getLong(template, request, "classNameId");
long classPK = BeanParamUtil.getLong(template, request, "classPK");
long resourceClassNameId = BeanParamUtil.getLong(template, request, "resourceClassNameId");

DDMTemplateVersion templateVersion = template.getTemplateVersion();

boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);
%>

<portlet:actionURL name="copyTemplate" var="copyTemplateURL">
	<portlet:param name="mvcPath" value="/copy_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= copyTemplateURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= ddmDisplay.getEditTemplateBackURL(liferayPortletRequest, liferayPortletResponse, classNameId, classPK, resourceClassNameId, portletResource) %>" />

	<aui:input name="templateId" type="hidden" value="<%= String.valueOf(templateId) %>" />
	<aui:input name="status" type="hidden" value="<%= templateVersion.getStatus() %>" />

	<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />

	<c:if test="<%= showBackURL && ddmDisplay.isShowBackURLInTitleBar() %>">

		<%
		String title = LanguageUtil.get(request, "copy-template");

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(ddmDisplay.getEditTemplateBackURL(liferayPortletRequest, liferayPortletResponse, classNameId, classPK, resourceClassNameId, portletResource));

		renderResponse.setTitle(title);
		%>

	</c:if>

	<aui:model-context bean="<%= template %>" model="<%= DDMTemplate.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" value="copy" />

		<aui:button href="<%= ddmDisplay.getEditTemplateBackURL(liferayPortletRequest, liferayPortletResponse, classNameId, classPK, resourceClassNameId, portletResource) %>" type="cancel" />
	</aui:button-row>
</aui:form>