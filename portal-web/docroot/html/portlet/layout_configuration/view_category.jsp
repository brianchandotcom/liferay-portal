<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>

<%
PortletCategory portletCategory = (PortletCategory)request.getAttribute(WebKeys.PORTLET_CATEGORY);

int portletCategoryIndex = GetterUtil.getInteger((String)request.getAttribute(WebKeys.PORTLET_CATEGORY_INDEX));

String oldCategoryPath = (String)request.getAttribute(WebKeys.PORTLET_CATEGORY_PATH);

String newCategoryPath = LanguageUtil.get(pageContext, portletCategory.getName());

Pattern pattern = Pattern.compile(".*");

Matcher matcher = pattern.matcher(newCategoryPath);

StringBundler divId = new StringBundler();

while (matcher.find()) {
	divId.append(matcher.group());
}

newCategoryPath = divId.toString();

if (Validator.isNotNull(oldCategoryPath)) {
	newCategoryPath = oldCategoryPath + ":" + newCategoryPath;
}

List<PortletCategory> categories = ListUtil.fromCollection(portletCategory.getCategories());

categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

List<Portlet> portlets = new ArrayList<Portlet>();

Set<String> portletIds = portletCategory.getPortletIds();

String externalPortletCategory = null;

for (String portletId : portletIds) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

	if ((portlet != null) && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
		portlets.add(portlet);

		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile() && Validator.isNull(externalPortletCategory)) {
			PortletConfig curPortletConfig = PortletConfigFactoryUtil.create(portlet, application);

			ResourceBundle resourceBundle = curPortletConfig.getResourceBundle(locale);

			externalPortletCategory = ResourceBundleUtil.getString(resourceBundle, portletCategory.getName());
		}
	}
}

portlets = ListUtil.sort(portlets, new PortletTitleComparator(application, locale));

if (!categories.isEmpty() || !portlets.isEmpty()) {
%>

	<div class="lfr-add-content <%= layout.isTypePortlet() ? "collapsed" : "" %>" id="<portlet:namespace />portletCategory<%= portletCategoryIndex %>">
		<h2>
			<a href="javascript:;"><%= Validator.isNotNull(externalPortletCategory) ? externalPortletCategory : LanguageUtil.get(pageContext, portletCategory.getName()) %></a>
		</h2>

		<ul class="lfr-content-category <%= layout.isTypePortlet() ? "aui-helper-hidden" : "" %>">

			<%
			for (PortletCategory category : categories) {
				request.setAttribute(WebKeys.PORTLET_CATEGORY, category);
				request.setAttribute(WebKeys.PORTLET_CATEGORY_INDEX, String.valueOf(portletCategoryIndex));
				request.setAttribute(WebKeys.PORTLET_CATEGORY_PATH, newCategoryPath);
			%>

				<liferay-util:include page="/html/portlet/layout_configuration/view_category.jsp" />

			<%
				request.setAttribute(WebKeys.PORTLET_CATEGORY_PATH, oldCategoryPath);

				portletCategoryIndex++;
			}

			for (Portlet portlet : portlets) {
				divId.setIndex(0);

				divId.append(newCategoryPath);
				divId.append(":");

				matcher = pattern.matcher(PortalUtil.getPortletTitle(portlet, application, locale));

				while (matcher.find()) {
					divId.append(matcher.group());
				}

				boolean portletInstanceable = portlet.isInstanceable();

				boolean portletUsed = layoutTypePortlet.hasPortletId(portlet.getPortletId());

				boolean portletLocked = (!portletInstanceable && portletUsed);

				if (portletInstanceable && layout.isTypePanel()) {
					continue;
				}
			%>

				<c:choose>
					<c:when test="<%= layout.isTypePortlet() %>">
						<li
							class="lfr-portlet-item <c:if test="<%= portletLocked %>">lfr-portlet-used</c:if> <c:if test="<%= portletInstanceable %>">lfr-instanceable</c:if>"
							id="<portlet:namespace />portletItem<%= portlet.getPortletId() %>"
							instanceable="<%= portletInstanceable %>"
							plid="<%= plid %>"
							portletId="<%= portlet.getPortletId() %>"
							title="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
						>
							<p><%= PortalUtil.getPortletTitle(portlet, application, locale) %> <a href="javascript:;"><liferay-ui:message key="add" /></a></p>
						</li>

						<input id="<portlet:namespace />portletItem<%= portlet.getPortletId() %>CategoryPath" type="hidden" value="<%= divId.toString().replace(':', '-') %>" />

						<%
						List<PortletItem> portletItems = PortletItemLocalServiceUtil.getPortletItems(themeDisplay.getScopeGroupId(), portlet.getPortletId(), com.liferay.portal.model.PortletPreferences.class.getName());

						for (PortletItem portletItem : portletItems) {
							divId.setIndex(0);

							divId.append(newCategoryPath);
							divId.append(":");
							divId.append(PortalUtil.getPortletTitle(portlet, application, locale));
							divId.append(":");

							matcher = pattern.matcher(HtmlUtil.escape(portletItem.getName()));

							while (matcher.find()) {
								divId.append(matcher.group());
							}
						%>

							<li
								class="lfr-portlet-item lfr-archived-setup"
								id="<portlet:namespace />portletItem<%= portletItem.getPortletItemId() %>"
								instanceable="<%= portletInstanceable %>"
								plid="<%= plid %>"
								portletId="<%= portlet.getPortletId() %>"
								portletItemId="<%= portletItem.getPortletItemId() %>"
								title="<%= HtmlUtil.escape(portletItem.getName()) %>"
							>
								<p><%= HtmlUtil.escape(portletItem.getName()) %> <a href="javascript:;"><liferay-ui:message key="add" /></a></p>
							</li>

							<input id="<portlet:namespace />portletItem<%= portletItem.getPortletItemId() %>CategoryPath" type="hidden" value="<%= divId.toString().replace(':', '-') %>" />

						<%
						}
						%>

					</c:when>
					<c:otherwise>
						<div>
							<a href="<liferay-portlet:renderURL portletName="<%= portlet.getRootPortletId() %>" windowState="<%= WindowState.MAXIMIZED.toString() %>"></liferay-portlet:renderURL>"><%= PortalUtil.getPortletTitle(portlet, application, locale) %></a>
						</div>
					</c:otherwise>
				</c:choose>

			<%
			}
			%>

		</ul>
	</div>

	<input id="<portlet:namespace />portletCategory<%= portletCategoryIndex %>CategoryPath" type="hidden" value="<%= newCategoryPath.replace(':', '-') %>" />

<%
}
%>