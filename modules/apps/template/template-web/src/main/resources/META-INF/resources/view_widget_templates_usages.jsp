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
WidgetTemplatesTemplateViewUsagesDisplayContext widgetTemplatesTemplateViewUsagesDisplayContext = new WidgetTemplatesTemplateViewUsagesDisplayContext(renderRequest, renderResponse);

DDMTemplate ddmTemplate = widgetTemplatesTemplateViewUsagesDisplayContext.getDDMTemplate();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(widgetTemplatesTemplateViewUsagesDisplayContext.getRedirect());

renderResponse.setTitle(HtmlUtil.escape(ddmTemplate.getName(locale)));
%>

<clay:container-fluid
	cssClass="container-form-lg"
>
	<clay:row>
		<clay:col
			lg="3"
		>
			<nav class="menubar menubar-transparent menubar-vertical-expand-lg">
				<ul class="nav nav-nested">
					<li class="nav-item">
						<strong class="text-uppercase">
							<liferay-ui:message key="usages" />
						</strong>

						<ul class="nav nav-stacked">
							<li class="nav-item">
								<a class="active nav-link">
									<liferay-ui:message arguments="<%= widgetTemplatesTemplateViewUsagesDisplayContext.getUsagesCount() %>" key="all-x" />
								</a>
							</li>
						</ul>
					</li>
				</ul>
			</nav>
		</clay:col>

		<clay:col
			lg="9"
		>
			<clay:sheet
				size="full"
			>
				<h2 class="sheet-title">
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col>
							<liferay-ui:message arguments="<%= widgetTemplatesTemplateViewUsagesDisplayContext.getUsagesCount() %>" key="all-x" />
						</clay:content-col>
					</clay:content-row>
				</h2>

				<liferay-ui:search-container
					searchContainer="<%= widgetTemplatesTemplateViewUsagesDisplayContext.getWidgetTemplatesUsagesSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.PortletPreferences"
						keyProperty="portletPreferencesId"
						modelVar="curPortletPreferences"
					>

						<%
						Layout curLayout = LayoutLocalServiceUtil.fetchLayout(curPortletPreferences.getPlid());
						%>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-title"
							name="name"
							value="<%= HtmlUtil.escape(widgetTemplatesTemplateViewUsagesDisplayContext.getDDMTemplateUsageName(curLayout)) %>"
						/>

						<liferay-ui:search-container-column-text
							name="type"
							translate="<%= true %>"
							value="<%= widgetTemplatesTemplateViewUsagesDisplayContext.getDDMTemplateUsageType(curLayout) %>"
						/>

						<%
						Portlet portlet = PortletLocalServiceUtil.fetchPortletById(company.getCompanyId(), curPortletPreferences.getPortletId());
						%>

						<c:if test="<%= (portlet != null) && portlet.isInstanceable() %>">
							<liferay-ui:search-container-column-text
								name="instance-id"
								value="<%= PortletIdCodec.decodeInstanceId(curPortletPreferences.getPortletId()) %>"
							/>
						</c:if>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="list"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</clay:sheet>
		</clay:col>
	</clay:row>
</clay:container-fluid>