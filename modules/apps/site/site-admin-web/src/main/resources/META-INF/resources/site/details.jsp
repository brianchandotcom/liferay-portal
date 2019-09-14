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
Group group = (Group)request.getAttribute("site.group");
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

long parentGroupId = ParamUtil.getLong(request, "parentGroupId", group.getParentGroupId());

if (parentGroupId <= 0) {
	parentGroupId = GroupConstants.DEFAULT_PARENT_GROUP_ID;
}

if (liveGroup != null) {
	parentGroupId = liveGroup.getParentGroupId();
}

Group parentGroup = null;

if (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) {
	parentGroup = GroupLocalServiceUtil.fetchGroup(parentGroupId);
}

UnicodeProperties typeSettingsProperties = null;

if (liveGroup != null) {
	typeSettingsProperties = liveGroup.getTypeSettingsProperties();
}
else {
	typeSettingsProperties = group.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
<liferay-ui:error exception="<%= GroupInheritContentException.class %>" message="this-site-cannot-inherit-content-from-its-parent-site" />

<liferay-ui:error exception="<%= GroupKeyException.class %>">
	<p>
		<liferay-ui:message arguments="<%= new String[] {SiteConstants.NAME_LABEL, SiteConstants.getNameGeneralRestrictions(locale), SiteConstants.NAME_RESERVED_WORDS} %>" key="the-x-cannot-be-x-or-a-reserved-word-such-as-x" />
	</p>

	<p>
		<liferay-ui:message arguments="<%= new String[] {SiteConstants.NAME_LABEL, SiteConstants.NAME_INVALID_CHARACTERS} %>" key="the-x-cannot-contain-the-following-invalid-characters-x" />
	</p>
</liferay-ui:error>

<liferay-ui:error exception="<%= GroupNameException.class %>" message="site-name-is-required-for-the-default-language" />
<liferay-ui:error exception="<%= GroupParentException.MustNotBeOwnParent.class %>" message="the-site-cannot-be-its-own-parent-site" />
<liferay-ui:error exception="<%= GroupParentException.MustNotHaveChildParent.class %>" message="the-site-cannot-have-a-child-as-its-parent-site" />
<liferay-ui:error exception="<%= GroupParentException.MustNotHaveStagingParent.class %>" message="the-site-cannot-have-a-staging-site-as-its-parent-site" />
<liferay-ui:error exception="<%= PendingBackgroundTaskException.class %>" message="the-site-cannot-be-deleted-because-it-has-background-tasks-in-progress" />
<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

<liferay-ui:error key="resetMergeFailCountAndMerge" message="unable-to-reset-the-failure-counter-and-propagate-the-changes" />

<c:if test="<%= liveGroup != null %>">
	<aui:input name="siteId" type="resource" value="<%= String.valueOf(liveGroup.getGroupId()) %>" />
</c:if>

<c:choose>
	<c:when test="<%= (liveGroup != null) && liveGroup.isOrganization() %>">
		<aui:input helpMessage="the-name-of-this-site-cannot-be-edited-because-it-belongs-to-an-organization" name="name" placeholder="name" type="resource" value="<%= liveGroup.getDescriptiveName(locale) %>" />
	</c:when>
	<c:when test="<%= (liveGroup == null) || (!liveGroup.isCompany() && !PortalUtil.isSystemGroup(liveGroup.getGroupKey())) %>">
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" placeholder="name" />
	</c:when>
</c:choose>

<aui:input name="description" placeholder="description" />

<c:if test="<%= !group.isCompany() && !group.isGuest() %>">
	<aui:input name="active" type="toggle-switch" value="<%= group.isActive() %>" />
</c:if>

<c:if test="<%= (parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID) && PropsValues.SITES_SHOW_INHERIT_CONTENT_SCOPE_FROM_PARENT_SITE %>">

	<%
	boolean disabled = false;
	boolean value = group.isInheritContent();

	if ((parentGroup != null) && parentGroup.isInheritContent()) {
		disabled = true;
		value = false;
	}
	%>

	<aui:input disabled="<%= disabled %>" helpMessage='<%= disabled ? "this-site-cannot-inherit-the-content-from-its-parent-site-since-the-parent-site-is-already-inheriting-the-content-from-its-parent" : StringPool.BLANK %>' name="inheritContent" type="toggle-switch" value="<%= value %>" />
</c:if>

<h4 class="text-default"><liferay-ui:message key="membership-options" /></h4>

<c:if test="<%= !group.isCompany() %>">
	<aui:select label="membership-type" name="type">
		<aui:option label="open" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
		<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_RESTRICTED %>" />
		<aui:option label="private" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
	</aui:select>

	<%
	boolean manualMembership = true;

	if (liveGroup != null) {
		manualMembership = GetterUtil.getBoolean(liveGroup.isManualMembership(), true);
	}
	%>

	<aui:input label="allow-manual-membership-management" name="manualMembership" type="toggle-switch" value="<%= manualMembership %>" />

	<%
	List<Group> parentGroups = new ArrayList<Group>();

	if (parentGroup != null) {
		parentGroups.add(parentGroup);
	}
	%>

	<liferay-util:buffer
		var="removeGroupIcon"
	>
		<liferay-ui:icon
			icon="times-circle"
			markupView="lexicon"
			message="remove"
		/>
	</liferay-util:buffer>

	<h4 class="text-default"><liferay-ui:message key="parent-site" /></h4>

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="none"
		headerNames="name,type,null"
		id="parentGroupSearchContainer"
		total="<%= parentGroups.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= parentGroups %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="curGroup"
		>
			<liferay-ui:search-container-column-text
				name="name"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= LanguageUtil.get(request, curGroup.getTypeLabel()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= curGroup.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>

	<div class="button-holder">
		<aui:button cssClass="modify-link" id="selectParentSiteLink" value="select" />
	</div>

	<div class="<%= parentGroups.isEmpty() ? "membership-restriction-container hide" : "membership-restriction-container" %>" id="<portlet:namespace />membershipRestrictionContainer">

		<%
		boolean membershipRestriction = false;

		if ((liveGroup != null) && (liveGroup.getMembershipRestriction() == GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS)) {
			membershipRestriction = true;
		}
		%>

		<aui:input label="limit-membership-to-members-of-the-parent-site" name="membershipRestriction" type="toggle-switch" value="<%= membershipRestriction %>" />
	</div>

	<aui:script use="liferay-search-container">
		A.one('#<portlet:namespace />selectParentSiteLink').on('click', function(
			event
		) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					id: '<portlet:namespace />selectGroup',
					title: '<liferay-ui:message arguments="site" key="select-x" />',

					<%
					PortletURL groupSelectorURL = PortletProviderUtil.getPortletURL(request, Group.class.getName(), PortletProvider.Action.BROWSE);

					groupSelectorURL.setParameter("includeCurrentGroup", Boolean.FALSE.toString());
					groupSelectorURL.setParameter("groupId", String.valueOf(group.getGroupId()));
					groupSelectorURL.setParameter("eventName", liferayPortletResponse.getNamespace() + "selectGroup");
					groupSelectorURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= groupSelectorURL.toString() %>'
				},
				function(event) {
					var searchContainer = Liferay.SearchContainer.get(
						'<portlet:namespace />parentGroupSearchContainer'
					);

					var rowColumns = [];

					var href =
						'<portlet:renderURL><portlet:param name="mvcPath" value="/edit_site.jsp" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>&<portlet:namespace />groupId=' +
						event.entityid;

					rowColumns.push(event.entityname);
					rowColumns.push(event.grouptype);
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							event.entityid +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>'
					);

					searchContainer.deleteRow(1, searchContainer.getData());
					searchContainer.addRow(rowColumns, event.entityid);
					searchContainer.updateDataStore(event.entityid);

					A.one(
						'#<portlet:namespace />membershipRestrictionContainer'
					).show();
				}
			);
		});

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />parentGroupSearchContainer'
		);

		searchContainer.get('contentBox').delegate(
			'click',
			function(event) {
				var link = event.currentTarget;

				var tr = link.ancestor('tr');

				searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

				A.one('#<portlet:namespace />membershipRestrictionContainer').hide();
			},
			'.modify-link'
		);
	</aui:script>
</c:if>