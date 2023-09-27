<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long templateVersionId = ParamUtil.getLong(request, "templateVersionId");

DDMTemplateVersion templateVersion = DDMTemplateVersionServiceUtil.getTemplateVersion(templateVersionId);

DDMTemplate template = templateVersion.getTemplate();

String title = LanguageUtil.format(request, "x-version-x", new Object[] {templateVersion.getName(locale), templateVersion.getVersion()});

PortletURL backURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view_template_history.jsp"
).setRedirect(
	redirect
).setParameter(
	"templateId", template.getTemplateId()
).buildPortletURL();
%>

<clay:container-fluid>
	<c:choose>
		<c:when test="<%= ddmDisplay.isShowBackURLInTitleBar() %>">

			<%
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(backURL.toString());

			renderResponse.setTitle(title);
			%>

		</c:when>
		<c:otherwise>
			<liferay-ui:header
				backURL="<%= backURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= title %>"
			/>
		</c:otherwise>
	</c:choose>

	<aui:model-context bean="<%= templateVersion %>" model="<%= DDMTemplateVersion.class %>" />

	<aui:input disabled="<%= true %>" name="name" />

	<aui:input disabled="<%= true %>" name="language" />

	<aui:input disabled="<%= true %>" name="description" />

	<c:choose>
		<c:when test="<%= template.getType().equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">

			<%
			DDMStructure structure = ddmDisplayContext.fetchStructure(template);

			String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace", liferayPortletResponse.getNamespace());

			String script = templateVersion.getScript();

			String fieldsJSONArrayString = String.valueOf(_getFormTemplateFieldsJSONArray(structure, script));
			%>

			<%@ include file="/form_builder.jspf" %>
		</c:when>
		<c:otherwise>
			<aui:input cssClass="script" disabled="<%= true %>" name="script" type="textarea" />
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</clay:container-fluid>