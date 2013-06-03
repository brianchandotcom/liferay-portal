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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group group = (Group)request.getAttribute("site.group");
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute("site.layoutSetPrototype");
boolean showPrototypes = GetterUtil.getBoolean(request.getAttribute("site.showPrototypes"));

List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

LayoutSet privateLayoutSet = null;
LayoutSetPrototype privateLayoutSetPrototype = null;
boolean privateLayoutSetPrototypeLinkEnabled = true;

LayoutSet publicLayoutSet = null;
LayoutSetPrototype publicLayoutSetPrototype = null;
boolean publicLayoutSetPrototypeLinkEnabled = true;

if (showPrototypes && (group != null)) {
	try {
		LayoutLocalServiceUtil.getLayouts(liveGroup.getGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), true);

		privateLayoutSetPrototypeLinkEnabled = privateLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = privateLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			privateLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
		}
	}
	catch (Exception e) {
	}

	try {
		LayoutLocalServiceUtil.getLayouts(liveGroup.getGroupId(), false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(liveGroup.getGroupId(), false);

		publicLayoutSetPrototypeLinkEnabled = publicLayoutSet.isLayoutSetPrototypeLinkEnabled();

		String layoutSetPrototypeUuid = publicLayoutSet.getLayoutSetPrototypeUuid();

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			publicLayoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypeByUuidAndCompanyId(layoutSetPrototypeUuid, company.getCompanyId());
		}
	}
	catch (Exception e) {
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="details" />

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />

<liferay-ui:error exception="<%= GroupParentException.class %>">

	<%
	GroupParentException gpe = (GroupParentException)errorException;
	%>

	<c:if test="<%= gpe.getType() == GroupParentException.CHILD_DESCENDANT %>">
		<liferay-ui:message key="the-site-cannot-have-a-child-as-its-parent-site" />
	</c:if>

	<c:if test="<%= gpe.getType() == GroupParentException.MISSING_PARENT %>">
		<liferay-ui:message key="the-site-must-have-a-parent-site-if-the-membership-type-is-limited-to-parent-site-members" />
	</c:if>

	<c:if test="<%= gpe.getType() == GroupParentException.SELF_DESCENDANT %>">
		<liferay-ui:message key="the-site-cannot-be-its-own-parent-site" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= RequiredGroupException.class %>">

	<%
	RequiredGroupException rge = (RequiredGroupException)errorException;
	%>

	<c:if test="<%= rge.getType() == RequiredGroupException.CURRENT_GROUP %>">
		<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
	</c:if>

	<c:if test="<%= rge.getType() == RequiredGroupException.PARENT_GROUP %>">
		<liferay-ui:message key="you-cannot-delete-sites-that-have-subsites" />
	</c:if>

	<c:if test="<%= rge.getType() == RequiredGroupException.SYSTEM_GROUP %>">
		<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error key="resetMergeFailCountAndMerge" message="unable-to-reset-the-failure-counter-and-propagate-the-changes" />

<aui:fieldset>
	<c:choose>
		<c:when test="<%= ((liveGroup != null) && (liveGroup.isCompany() || PortalUtil.isSystemGroup(liveGroup.getName()))) %>">
			<aui:input name="name" type="hidden" />
		</c:when>
		<c:when test="<%= (liveGroup != null) && liveGroup.isOrganization() %>">
			<aui:field-wrapper helpMessage="the-name-of-this-site-cannot-be-edited-because-it-belongs-to-an-organization" label="name">
				<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>
			</aui:field-wrapper>
		</c:when>
		<c:otherwise>
			<aui:input name="name" />
		</c:otherwise>
	</c:choose>

	<aui:input name="description" />

	<c:if test="<%= (group == null) || !group.isCompany() %>">
		<aui:select label="membership-type" name="type">
			<aui:option label="open" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
			<aui:option label="limited-to-parent-site-members" value="<%= GroupConstants.TYPE_SITE_LIMITED_TO_PARENT_SITE_MEMBERS %>" />
			<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_RESTRICTED %>" />
			<aui:option label="private" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
		</aui:select>

		<aui:input name="active" value="<%= true %>" />
	</c:if>

<%
UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = group.getTypeSettingsProperties();
}

boolean directoryIndexingEnabled = PropertiesParamUtil.getBoolean(groupTypeSettings, request, "directoryIndexingEnabled");
%>

	<aui:input label="directory-indexing-enabled" name="TypeSettingsProperties--directoryIndexingEnabled--" type="checkbox" value="<%= directoryIndexingEnabled %>" />

	<c:if test="<%= liveGroup != null %>">
		<aui:field-wrapper label="site-id">
			<%= liveGroup.getGroupId() %>
		</aui:field-wrapper>
	</c:if>
</aui:fieldset>

<%
boolean hasUnlinkLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.UNLINK_LAYOUT_SET_PROTOTYPE);
%>

<c:if test="<%= (group == null) || !group.isCompany() %>">
	<aui:fieldset>
		<c:choose>
			<c:when test="<%= showPrototypes && ((group != null) || (!layoutSetPrototypes.isEmpty() && (layoutSetPrototype == null))) %>">
				<liferay-ui:panel-container extended="<%= false %>">
					<liferay-ui:panel collapsible="<%= true %>" defaultState='<%= ((group != null) && (group.getPublicLayoutsPageCount() > 0)) ? "open" : "closed" %>' title="public-pages">
						<c:choose>
							<c:when test="<%= ((group == null) || ((publicLayoutSetPrototype == null) && (group.getPublicLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
								<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="publicLayoutSetPrototypeId">
									<aui:option label="none" selected="<%= true %>" value="" />

									<%
									for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
										UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

										String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
									%>

										<aui:option data-servletContextName="<%= servletContextName %>" disabled="<%= (privateLayoutSetPrototype != null) && (curLayoutSetPrototype.getLayoutSetPrototypeId() == privateLayoutSetPrototype.getLayoutSetPrototypeId()) %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

									<%
									}
									%>

								</aui:select>

								<c:choose>
									<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
										<div class="hide" id="<portlet:namespace />publicLayoutSetPrototypeIdOptions">
											<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
										</div>
									</c:when>
									<c:otherwise>
										<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="<%= group != null %>">
										<liferay-portlet:actionURL portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="publicPagesURL">
											<portlet:param name="struts_action" value="/my_sites/view" />
											<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
											<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
										</liferay-portlet:actionURL>

										<c:choose>
											<c:when test="<%= group.getPublicLayoutsPageCount() > 0 %>">
												<liferay-ui:icon
													image="view"
													label="<%= true %>"
													message="open-public-pages"
													method="get"
													target="_blank"
													url="<%= publicPagesURL.toString() %>"
												/>
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="this-site-does-not-have-any-public-pages" />
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="<%= (publicLayoutSetPrototype != null) && !liveGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
												<aui:input label='<%= LanguageUtil.format(pageContext, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(publicLayoutSetPrototype.getName(user.getLanguageId()))) %>' name="publicLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />

												<div class='<%= publicLayoutSetPrototypeLinkEnabled ? "" : "hide" %>' id="<portlet:namespace/>publicLayoutSetPrototypeMergeAlert">

													<%
													request.setAttribute("edit_layout_set_prototype.jsp-groupId", String.valueOf(group.getGroupId()));
													request.setAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype", publicLayoutSetPrototype);
													request.setAttribute("edit_layout_set_prototype.jsp-redirect", currentURL);
													%>

													<liferay-util:include page="/html/portlet/layout_set_prototypes/merge_alert.jsp" />
												</div>
											</c:when>
											<c:when test="<%= publicLayoutSetPrototype != null %>">
												<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(publicLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" />

												<aui:input name="publicLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= publicLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
										</c:choose>
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</liferay-ui:panel>
					<liferay-ui:panel collapsible="<%= true %>" defaultState='<%= ((group != null) && (group.getPrivateLayoutsPageCount() > 0)) ? "open" : "closed" %>' title="private-pages">
						<c:choose>
							<c:when test="<%= ((group == null) || ((privateLayoutSetPrototype == null) && (group.getPrivateLayoutsPageCount() == 0))) && !layoutSetPrototypes.isEmpty() %>">
								<aui:select helpMessage="site-templates-with-an-incompatible-application-adapter-are-disabled" label="site-template" name="privateLayoutSetPrototypeId">
									<aui:option label="none" selected="<%= true %>" value="" />

									<%
									for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
										UnicodeProperties settingsProperties = curLayoutSetPrototype.getSettingsProperties();

										String servletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
									%>

										<aui:option data-servletContextName="<%= servletContextName %>" disabled="<%= (publicLayoutSetPrototype != null) && (curLayoutSetPrototype.getLayoutSetPrototypeId() == publicLayoutSetPrototype.getLayoutSetPrototypeId()) %>" value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= HtmlUtil.escape(curLayoutSetPrototype.getName(user.getLanguageId())) %></aui:option>

									<%
									}
									%>

								</aui:select>

								<c:choose>
									<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
										<div class="hide" id="<portlet:namespace />privateLayoutSetPrototypeIdOptions">
											<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help"  label="enable-propagation-of-changes-from-the-site-template" name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
										</div>
									</c:when>
									<c:otherwise>
										<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="<%= group != null %>">
										<liferay-portlet:actionURL portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="privatePagesURL">
											<portlet:param name="struts_action" value="/my_sites/view" />
											<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
											<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
										</liferay-portlet:actionURL>

										<c:choose>
											<c:when test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
												<liferay-ui:icon
													image="view"
													label="<%= true %>"
													message="open-private-pages"
													method="get"
													target="_blank"
													url="<%= privatePagesURL.toString() %>"
												/>
											</c:when>
											<c:otherwise>
												<liferay-ui:message key="this-site-does-not-have-any-private-pages" />
											</c:otherwise>
										</c:choose>

										<c:choose>
											<c:when test="<%= (privateLayoutSetPrototype != null) && !liveGroup.isStaged() && hasUnlinkLayoutSetPrototypePermission %>">
												<aui:input label='<%= LanguageUtil.format(pageContext, "enable-propagation-of-changes-from-the-site-template-x", HtmlUtil.escape(privateLayoutSetPrototype.getName(user.getLanguageId()))) %>' name="privateLayoutSetPrototypeLinkEnabled" type="checkbox" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />

												<div class='<%= privateLayoutSetPrototypeLinkEnabled ? "" : "hide" %>' id="<portlet:namespace/>privateLayoutSetPrototypeMergeAlert">

													<%
													request.setAttribute("edit_layout_set_prototype.jsp-groupId", String.valueOf(group.getGroupId()));
													request.setAttribute("edit_layout_set_prototype.jsp-layoutSetPrototype", privateLayoutSetPrototype);
													request.setAttribute("edit_layout_set_prototype.jsp-privateLayoutSet", String.valueOf(true));
													request.setAttribute("edit_layout_set_prototype.jsp-redirect", currentURL);
													%>

													<liferay-util:include page="/html/portlet/layout_set_prototypes/merge_alert.jsp" />
												</div>
											</c:when>
											<c:when test="<%= privateLayoutSetPrototype != null %>">
												<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(privateLayoutSetPrototype.getName(locale))} %>" key="these-pages-are-linked-to-site-template-x" />

												<aui:input name="privateLayoutSetPrototypeLinkEnabled" type="hidden" value="<%= privateLayoutSetPrototypeLinkEnabled %>" />
											</c:when>
										</c:choose>
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</liferay-ui:panel>
				</liferay-ui:panel-container>

				<%
				Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();
				%>

				<c:if test="<%= servletContextNames.size() > 0 %>">
					<aui:fieldset label="configuration">

						<%
						String customJspServletContextName = StringPool.BLANK;

						if (liveGroup != null) {
							UnicodeProperties typeSettingsProperties = liveGroup.getTypeSettingsProperties();

							customJspServletContextName = GetterUtil.getString(typeSettingsProperties.get("customJspServletContextName"));
						}
						%>

						<aui:select helpMessage='<%= LanguageUtil.format(pageContext, "application-adapter-help", "http://www.liferay.com/community/wiki/-/wiki/Main/Application+Adapters") %>' label="application-adapter" name="customJspServletContextName">
							<aui:option label="none" value="" />

							<%
							for (String servletContextName : servletContextNames) {
							%>

								<aui:option selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>"><%= CustomJspRegistryUtil.getDisplayName(servletContextName) %></aui:option>

							<%
							}
							%>

						</aui:select>
					</aui:fieldset>
				</c:if>
			</c:when>
			<c:when test="<%= layoutSetPrototype != null %>">
				<aui:fieldset label="pages">
					<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>" />

					<aui:field-wrapper label="copy-as">
						<aui:input checked="<%= true %>" helpMessage='<%= LanguageUtil.format(pageContext, "select-this-to-copy-the-pages-of-the-site-template-x-as-public-pages-for-this-site", HtmlUtil.escape(layoutSetPrototype.getName(user.getLanguageId()))) %>' label="public-pages" name="layoutSetVisibility" type="radio" value="0" />
						<aui:input helpMessage='<%= LanguageUtil.format(pageContext, "select-this-to-copy-the-pages-of-the-site-template-x-as-private-pages-for-this-site", HtmlUtil.escape(layoutSetPrototype.getName(user.getLanguageId()))) %>' label="private-pages" name="layoutSetVisibility" type="radio" value="1" />
					</aui:field-wrapper>

					<c:choose>
						<c:when test="<%= hasUnlinkLayoutSetPrototypePermission %>">
							<aui:input helpMessage="enable-propagation-of-changes-from-the-site-template-help" label="enable-propagation-of-changes-from-the-site-template" name="layoutSetPrototypeLinkEnabled" type="checkbox" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input name="layoutSetPrototypeLinkEnabled" type="hidden" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</c:when>
		</c:choose>
	</aui:fieldset>

	<%
	long parentGroupId = ParamUtil.getLong(request, "parentGroupSearchContainerPrimaryKeys", (group != null) ? group.getParentGroupId() : GroupConstants.DEFAULT_PARENT_GROUP_ID);

	if (parentGroupId <= 0) {
		parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;

		if (group != null) {
			parentGroupId = liveGroup.getParentGroupId();
		}
	}

	Group parentGroup = null;

	if ((group == null) && (parentGroupId == GroupConstants.DEFAULT_PARENT_GROUP_ID) && !permissionChecker.isCompanyAdmin()) {
		List<Group> manageableGroups = new ArrayList<Group>();

		for (Group curGroup : user.getGroups()) {
			if (GroupPermissionUtil.contains(permissionChecker, curGroup.getGroupId(), ActionKeys.MANAGE_SUBGROUPS)) {
				manageableGroups.add(curGroup);
			}
		}

		if (manageableGroups.size() == 1) {
			Group manageableGroup = manageableGroups.get(0);

			parentGroupId = manageableGroup.getGroupId();
		}
	}

	if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
		try {
			parentGroup = GroupLocalServiceUtil.getGroup(parentGroupId);
		}
		catch (NoSuchGroupException nsoe) {
		}
	}

	List<Group> parentGroups = new ArrayList<Group>();

	if (parentGroup != null) {
		parentGroups.add(parentGroup);
	}
	%>

	<liferay-util:buffer var="removeGroupIcon">
		<liferay-ui:icon
			image="unlink"
			label="<%= true %>"
			message="remove"
		/>
	</liferay-util:buffer>

	<h3><liferay-ui:message key="parent-site" /></h3>

	<liferay-ui:search-container
		headerNames="name,type,null"
		id="parentGroupSearchContainer"
	>
		<liferay-ui:search-container-results
			results="<%= parentGroups %>"
			total="<%= parentGroups.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="curGroup"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="struts_action" value="/sites_admin/edit_group" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(curGroup.getGroupId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= LanguageUtil.get(pageContext, curGroup.getTypeLabel()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= curGroup.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" />
	</liferay-ui:search-container>

	<br />

	<liferay-ui:icon
		cssClass="modify-link"
		id="selectParentSiteLink"
		image="add"
		label="<%= true %>"
		message="select"
		url="javascript:;"
	/>

	<br />

	<portlet:renderURL var="groupSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="struts_action" value="/users_admin/select_site" />
		<portlet:param name="groupId" value='<%= (group != null) ? String.valueOf(group.getGroupId()) : "0" %>' />
	</portlet:renderURL>

	<aui:script>
		function <portlet:namespace />isVisible(currentValue, value) {
			return currentValue != '';
		}

		Liferay.Util.toggleBoxes('<portlet:namespace />publicLayoutSetPrototypeLinkEnabledCheckbox','<portlet:namespace />publicLayoutSetPrototypeMergeAlert');
		Liferay.Util.toggleBoxes('<portlet:namespace />privateLayoutSetPrototypeLinkEnabledCheckbox','<portlet:namespace />privateLayoutSetPrototypeMergeAlert');
	</aui:script>

	<aui:script use="escape,liferay-search-container">
		var createURL = function(href, value, onclick) {
			return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
		};

		A.one('#<portlet:namespace />selectParentSiteLink').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							zIndex: Liferay.zIndex.WINDOW + 2,
							width: 600
						},
						id: '<portlet:namespace />selectGroup',
						title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "site") %>',
						uri: '<%= groupSelectorURL.toString() %>'
					},
					function(event) {
						var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />parentGroupSearchContainer');

						var rowColumns = [];

						var href = "<portlet:renderURL><portlet:param name="struts_action" value="/sites_admin/edit_group" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>&<portlet:namespace />groupId=" + event.groupid;

						rowColumns.push(createURL(href, A.Escape.html(event.groupname)));
						rowColumns.push(event.grouptype);
						rowColumns.push('<a class="modify-link" data-rowId="' + event.groupid + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

						searchContainer.deleteRow(1, searchContainer.getData());
						searchContainer.addRow(rowColumns, event.groupid);
						searchContainer.updateDataStore(event.groupid);
					}
				);
			}
		);

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />parentGroupSearchContainer');

		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));
			},
			'.modify-link'
		);
	</aui:script>
</c:if>