<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
<%@ include file="/html/portal/init.jsp" %>

<liferay-util:buffer var="buffer">

	<%
	if (themeDisplay.isStatePopUp() || layoutTypePortlet.hasStateMax()) {
		String ppid = ParamUtil.getString(request, "p_p_id");

		String velocityTemplateId = null;
		String velocityTemplateContent = null;

		if (themeDisplay.isStatePopUp()) {
			velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "pop_up";
			velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("pop_up", true, theme.getThemeId());
		}
		else {
			ppid = StringUtil.split(layoutTypePortlet.getStateMax())[0];

			velocityTemplateId = theme.getThemeId() + LayoutTemplateConstants.STANDARD_SEPARATOR + "max";
			velocityTemplateContent = LayoutTemplateLocalServiceUtil.getContent("max", true, theme.getThemeId());
		}

		if (Validator.isNotNull(velocityTemplateId) && Validator.isNotNull(velocityTemplateContent)) {
			RuntimePageUtil.processTemplate(pageContext, ppid, new StringTemplateResource(velocityTemplateId, velocityTemplateContent));
		}
	}
	else {
		UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

		String description = typeSettingsProperties.getProperty("description");

		if (Validator.isNull(description)) {
			description = LanguageUtil.get(pageContext, "please-select-a-tool-from-the-left-menu");
		}
	%>

		<h2>
			<%= HtmlUtil.escape(layout.getName(locale)) %>
		</h2>

		<div class="alert alert-info">
			<%= HtmlUtil.escape(description) %>
		</div>

	<%
	}
	%>

</liferay-util:buffer>

<c:choose>
	<c:when test="<%= !themeDisplay.isStatePopUp() %>">
		<aui:container class="lfr-panel-page" id="main-content">
			<aui:row>

				<%
				String panelBodyCssClass = "panel-page-body";

				if (!layoutTypePortlet.hasStateMax()) {
					panelBodyCssClass += " panel-page-frontpage";
				}
				else {
					panelBodyCssClass += "panel-page-application";
				}
				%>

				<aui:col cssClass="panel-page-menu" width="<%= 20 %>">

					<%
					PortletCategory portletCategory = (PortletCategory)WebAppPool.get(company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

					portletCategory = PortletCategoryUtil.getRelevantPortletCategory(permissionChecker, user.getCompanyId(), layout, portletCategory, layoutTypePortlet);

					List<PortletCategory> categories = ListUtil.fromCollection(portletCategory.getCategories());

					categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

					for (PortletCategory curPortletCategory : categories) {
					%>

						<c:if test="<%= !curPortletCategory.isHidden() %>">

							<%
							request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
							%>

							<liferay-util:include page="/html/portal/layout/view/view_category.jsp" />
						</c:if>

					<%
					}
					%>

				</aui:col>
				<aui:col cssClass="<%= panelBodyCssClass %>"  width="<%= 80 %>">
					<%= buffer %>
				</aui:col>
			</aui:row>
		</aui:container>
	</c:when>
	<c:otherwise>
		<%= buffer %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portal/layout/view/common.jspf" %>