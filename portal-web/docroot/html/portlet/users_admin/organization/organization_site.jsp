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

<%@ include file="/html/portlet/users_admin/init.jsp" %>

<%
Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet layoutSet = null;
LayoutSetPrototype layoutSetPrototype = null;
boolean layoutSetPrototypeLinkEnabled = true;

boolean site = false;

Group organizationGroup = null;

if (organization != null) {
	organizationGroup = organization.getGroup();

	site = organizationGroup.isSite();

	if (site) {
		try {
			LayoutLocalServiceUtil.getLayouts(organizationGroup.getGroupId(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(organizationGroup.getGroupId());

			layoutSetPrototypeLinkEnabled = layoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = layoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}
	}
}
%>

<h3><liferay-ui:message key="organization-site" /></h3>

<c:choose>
	<c:when test="<%= (organizationGroup == null) || GroupPermissionUtil.contains(permissionChecker, organizationGroup, ActionKeys.UPDATE) %>">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= (organization == null) || (layoutSetPrototype == null) %>">
					<aui:input label="create-site" name="site" type="checkbox" value="<%= site %>" />
				</c:when>
				<c:otherwise>
					<aui:input label="create-site" name="site" type="hidden" value="<%= site %>" />
				</c:otherwise>
			</c:choose>

			<%
			boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
			%>

			<div id="<portlet:namespace />siteTemplates">
				<c:choose>
					<c:when test="<%= ((organization == null) || ((layoutSetPrototype == null) && (organization.getLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
						<aui:select label="pages" name="layoutSetPrototypeId">
							<aui:option label="none" selected="<%= true %>" value="" />

							<%
							for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
							%>

								<aui:option label="<%= HtmlUtil.escape(curLayoutSetPrototype.getName(locale)) %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>" />

							<%
							}
							%>

						</aui:select>

						<c:choose>
							<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
								<div class="hide" id="<portlet:namespace />layoutSetPrototypeIdOptions">
									<aui:input
										helpMessage="enable-propagation-of-changes-from-the-site-template-help"
										label="enable-propagation-of-changes-from-the-site-template"
										name="layoutSetPrototypeLinkEnabled"
										type="checkbox"
										value="<%= true %>"
									/>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<aui:field-wrapper label="pages">
							<c:choose>
								<c:when test="<%= organization != null %>">
									<c:choose>
										<c:when test="<%= organization.hasLayouts() %>">
											<liferay-ui:icon
												iconCssClass="icon-search"
												label="<%= true %>"
												message="open-pages"
												method="get"
												target="_blank"
												url="<%= organizationGroup.getDisplayURL(themeDisplay) %>"
											/>
										</c:when>
										<c:otherwise>
											<liferay-ui:message key="this-organization-does-not-have-any-pages" />
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="<%= (layoutSetPrototype != null) && !organizationGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
											<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(layoutSetPrototype.getName(locale)), false) %>' name="layoutSetPrototypeLinkEnabled" type="checkbox" value="<%= layoutSetPrototypeLinkEnabled %>" />
										</c:when>
										<c:when test="<%= layoutSetPrototype != null %>">
											<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(layoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

											<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= layoutSetPrototypeLinkEnabled %>" />
										</c:when>
									</c:choose>
								</c:when>
							</c:choose>
						</aui:field-wrapper>
					</c:otherwise>
				</c:choose>
			</div>
		</aui:fieldset>

		<%
		if ((organization == null) && layoutSetPrototypes.isEmpty()) {
			request.setAttribute(WebKeys.FORM_NAVIGATOR_SECTION_SHOW + "pages", Boolean.FALSE);
		}
		%>

		<aui:script>
			function <portlet:namespace />isVisible(currentValue, value) {
				return currentValue != '';
			}

			Liferay.Util.toggleBoxes('<portlet:namespace />site', '<portlet:namespace />siteTemplates');

			Liferay.Util.toggleSelectBox('<portlet:namespace />layoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />layoutSetPrototypeIdOptions');
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:input name="site" type="hidden" value="<%= site %>" />

		<liferay-ui:message key="you-do-not-have-the-required-permissions" />
	</c:otherwise>
</c:choose>