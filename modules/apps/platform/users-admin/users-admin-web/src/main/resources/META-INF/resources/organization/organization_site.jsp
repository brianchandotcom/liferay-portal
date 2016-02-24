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
long organizationId = ParamUtil.getLong(request, "organizationId");

Organization organization = OrganizationServiceUtil.fetchOrganization(organizationId);

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

boolean site = false;

Group organizationGroup = null;

if (organization != null) {
	organizationGroup = organization.getGroup();

	site = organizationGroup.isSite();

	if (site) {
		try {
			LayoutLocalServiceUtil.getLayouts(organizationGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(organizationGroup.getGroupId(), true);

			privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}

		try {
			LayoutLocalServiceUtil.getLayouts(organizationGroup.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(organizationGroup.getGroupId(), false);

			publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

			String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

			if (Validator.isNotNull(layoutSetPrototypeUuid)) {
				publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
			}
		}
		catch (Exception e) {
		}
	}
}
%>

<c:choose>
	<c:when test="<%= (organizationGroup == null) || GroupPermissionUtil.contains(permissionChecker, organizationGroup, ActionKeys.UPDATE) %>">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= (organization == null) || ((publicLayoutSetPrototype == null) && (privateLayoutSetPrototype == null)) %>">
					<aui:input label="create-site" name="site" type="checkbox" value="<%= site %>" />
				</c:when>
				<c:otherwise>
					<aui:input label="create-site" name="site" type="hidden" value="<%= site %>" />
				</c:otherwise>
			</c:choose>

			<c:if test="<%= site %>">

				<%
				PortletURL editOrganizationSiteURL = PortletProviderUtil.getPortletURL(request, organizationGroup, Group.class.getName(), PortletProvider.Action.EDIT);

				editOrganizationSiteURL.setParameter("viewOrganizationsRedirect", currentURL);
				%>

				<aui:field-wrapper>
					<liferay-ui:icon
						iconCssClass="icon-cog"
						label="<%= true %>"
						message="manage-site"
						url="<%= editOrganizationSiteURL.toString() %>"
					/>
				</aui:field-wrapper>
			</c:if>

			<%
			boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
			%>

			<div id="<portlet:namespace />siteTemplates">
				<c:choose>
					<c:when test="<%= ((organization == null) || ((publicLayoutSetPrototype == null) && (organization.getPublicLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
						<aui:select label="public-pages" name="publicLayoutSetPrototypeId">
							<aui:option label="none" selected="<%= true %>" value="" />

							<%
							for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
							%>

								<aui:option label="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

							<%
							}
							%>

						</aui:select>

						<c:choose>
							<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
								<div class="hide" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
									<aui:input
										helpMessage="enable-propagation-of-changes-from-the-site-template-help"
										label="enable-propagation-of-changes-from-the-site-template"
										name="publicLayoutSetPrototypeLinkEnabled"
										type="checkbox"
										value="<%= true %>"
									/>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<aui:field-wrapper label="public-pages">
							<c:choose>
								<c:when test="<%= organization != null %>">
									<aui:field-wrapper>
										<c:choose>
											<c:when test="<%= organization.getPublicLayoutsPageCount() > 0 %>">
												<liferay-ui:icon
													iconCssClass="icon-search"
													label="<%= true %>"
													message="open-public-pages"
													method="get"
													target="_blank"
													url="<%= organizationGroup.getDisplayURL(themeDisplay, false) %>"
												/>
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="this-organization-does-not-have-any-public-pages" />
											</c:otherwise>
										</c:choose>
									</aui:field-wrapper>

									<aui:field-wrapper>
										<c:choose>
											<c:when test="<%= (publicLayoutSetPrototype != null) && !organizationGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
												<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(locale)), false) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
											<c:when test="<%= publicLayoutSetPrototype != null %>">
												<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(publicLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

												<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
										</c:choose>
									</aui:field-wrapper>
								</c:when>
							</c:choose>
						</aui:field-wrapper>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="<%= ((organization == null) || ((privateLayoutSetPrototype == null) && (organization.getPrivateLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
						<aui:select label="private-pages" name="privateLayoutSetPrototypeId">
							<aui:option label="none" selected="<%= true %>" value="" />

							<%
							for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
							%>

								<aui:option label="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

							<%
							}
							%>

						</aui:select>

						<c:choose>
							<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
								<div class="hide" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
									<aui:input
										helpMessage="enable-propagation-of-changes-from-the-site-template-help"
										label="enable-propagation-of-changes-from-the-site-template"
										name="privateLayoutSetPrototypeLinkEnabled"
										type="checkbox"
										value="<%= true %>"
									/>
								</div>
							</c:when>
							<c:otherwise>
								<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<aui:field-wrapper label="private-pages">
							<c:choose>
								<c:when test="<%= organization != null %>">
									<aui:field-wrapper>
										<c:choose>
											<c:when test="<%= organization.getPrivateLayoutsPageCount() > 0 %>">
												<liferay-ui:icon
													iconCssClass="icon-search"
													label="<%= true %>"
													message="open-private-pages"
													method="get"
													target="_blank"
													url="<%= organizationGroup.getDisplayURL(themeDisplay, true) %>"
												/>
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="this-organization-does-not-have-any-private-pages" />
											</c:otherwise>
										</c:choose>
									</aui:field-wrapper>

									<aui:field-wrapper>
										<c:choose>
											<c:when test="<%= (privateLayoutSetPrototype != null) && !organizationGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
												<aui:input label='<%= LanguageUtil.format(request, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(locale)), false) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
											<c:when test="<%= privateLayoutSetPrototype != null %>">
												<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" translateArguments="<%= false %>" />

												<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
										</c:choose>
									</aui:field-wrapper>
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

		<c:if test="<%= !site %>">
			<aui:script>
				function <portlet:namespace />isVisible(currentValue, value) {
					return currentValue != '';
				}

				Liferay.Util.toggleBoxes('<portlet:namespace />site', '<portlet:namespace />siteTemplates');

				Liferay.Util.toggleSelectBox('<portlet:namespace />publicLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />publicLayoutSetPrototypeIdOptions');
				Liferay.Util.toggleSelectBox('<portlet:namespace />privateLayoutSetPrototypeId', <portlet:namespace />isVisible, '<portlet:namespace />privateLayoutSetPrototypeIdOptions');
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<aui:input name="site" type="hidden" value="<%= site %>" />

		<liferay-ui:message key="you-do-not-have-the-required-permissions" />
	</c:otherwise>
</c:choose>