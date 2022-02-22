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
String tabs1 = ParamUtil.getString(request, "tabs1", "timeline");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

long receiverUserId = ParamUtil.getLong(request, "receiverUserId");
long parentMicroblogsEntryId = ParamUtil.getLong(request, "parentMicroblogsEntryId");

String assetTagName = ParamUtil.getString(request, "assetTagName");

boolean userPublicPage = false;

Group group = themeDisplay.getScopeGroup();

if (group.isUser() && layout.isPublicLayout()) {
	userPublicPage = true;
}

String tabs1Names = "timeline,mentions";

if (!tabs1.equals("mentions") && !tabs1.equals("timeline")) {
	tabs1Names += "," + tabs1;

	assetTagName = StringUtil.toLowerCase(tabs1);
}

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/microblogs/view.jsp"
).setTabs1(
	tabs1
).setWindowState(
	WindowState.NORMAL
).buildPortletURL();
%>

<div class="microblogs-container">
	<c:if test="<%= MicroblogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) && !userPublicPage %>">
		<liferay-util:include page="/microblogs/edit_microblogs_entry.jsp" servletContext="<%= application %>" />
	</c:if>

	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		param="tabs1"
		url="<%= portletURL.toString() %>"
	/>

	<%
	SearchContainer<MicroblogsEntry> searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 10, portletURL, null, null);

	searchContainer.setDeltaConfigurable(false);

	if (tabs1.equals("mentions")) {
		receiverUserId = themeDisplay.getUserId();

		if (userPublicPage) {
			receiverUserId = group.getClassPK();
		}

		try {
			User taggedUser = UserLocalServiceUtil.getUserById(receiverUserId);

			assetTagName = taggedUser.getScreenName();
		}
		catch (NoSuchUserException nsue) {
		}

		String microblogsAssetTagName = assetTagName;

		searchContainer.setResultsAndTotal(() -> MicroblogsEntryServiceUtil.getMicroblogsEntries(microblogsAssetTagName, searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryServiceUtil.getMicroblogsEntriesCount(microblogsAssetTagName));
	}
	else if (parentMicroblogsEntryId > 0) {
		List<MicroblogsEntry> results = new ArrayList<MicroblogsEntry>();
		int total = 0;

		MicroblogsEntry microblogsEntry = MicroblogsEntryLocalServiceUtil.fetchMicroblogsEntry(parentMicroblogsEntryId);

		if (microblogsEntry != null) {
			results.add(microblogsEntry);
			total = 1;
		}

		searchContainer.setResultsAndTotal(() -> results, total);

		portletURL.setParameter("parentMicroblogsEntryId", String.valueOf(parentMicroblogsEntryId));
	}
	else if ((receiverUserId > 0) && (receiverUserId == themeDisplay.getUserId())) {
		long microblogsReceiverUserId = receiverUserId;

		searchContainer.setResultsAndTotal(() -> MicroblogsEntryLocalServiceUtil.getUserMicroblogsEntries(microblogsReceiverUserId, searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryLocalServiceUtil.getUserMicroblogsEntriesCount(microblogsReceiverUserId));

		portletURL.setParameter("receiverUserId", String.valueOf(microblogsReceiverUserId));
	}
	else if (receiverUserId > 0) {
		long microblogsReceiverUserId = receiverUserId;

		searchContainer.setResultsAndTotal(() -> MicroblogsEntryServiceUtil.getUserMicroblogsEntries(microblogsReceiverUserId, searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryServiceUtil.getUserMicroblogsEntriesCount(microblogsReceiverUserId));

		portletURL.setParameter("receiverUserId", String.valueOf(microblogsReceiverUserId));
	}
	else if (Validator.isNotNull(assetTagName)) {
		String microblogsAssetTagName = assetTagName;

		searchContainer.setResultsAndTotal(() -> MicroblogsEntryServiceUtil.getMicroblogsEntries(microblogsAssetTagName, searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryServiceUtil.getMicroblogsEntriesCount(microblogsAssetTagName));

		portletURL.setParameter("assetTagName", microblogsAssetTagName);
	}
	else if (tabs1.equals("timeline")) {
		if (userPublicPage) {
			searchContainer.setResultsAndTotal(() -> MicroblogsEntryServiceUtil.getUserMicroblogsEntries(group.getClassPK(), searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryServiceUtil.getUserMicroblogsEntriesCount(group.getClassPK()));
		}
		else {
			searchContainer.setResultsAndTotal(() -> MicroblogsEntryServiceUtil.getMicroblogsEntries(searchContainer.getStart(), searchContainer.getEnd()), MicroblogsEntryServiceUtil.getMicroblogsEntriesCount());
		}
	}

	PortletURL microblogsEntriesURL = PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/microblogs/view.jsp"
	).setTabs1(
		tabs1
	).setParameter(
		"cur", cur
	).setWindowState(
		LiferayWindowState.EXCLUSIVE
	).buildPortletURL();

	request.setAttribute(WebKeys.MICROBLOGS_ENTRIES, searchContainer.getResults());
	request.setAttribute(WebKeys.MICROBLOGS_ENTRIES_URL, microblogsEntriesURL);
	%>

	<liferay-util:include page="/microblogs/view_microblogs_entries.jsp" servletContext="<%= application %>" />

	<liferay-ui:search-paginator
		searchContainer="<%= searchContainer %>"
		type="article"
	/>
</div>

<aui:script use="aui-base,aui-io-deprecated">
	AUI().ready(function () {
		Liferay.Microblogs.init({
			baseActionURL:
				'<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), PortletRequest.ACTION_PHASE) %>',
			microblogsEntriesURL:
				'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/microblogs/view.jsp" /><portlet:param name="tabs1" value="timeline" /></portlet:renderURL>',
		});

		Liferay.Microblogs.updateViewCount(<%= parentMicroblogsEntryId %>);
	});

	var microblogsContainer = A.one(
		'#p_p_id<portlet:namespace /> .microblogs-container'
	);

	var showComments = function (microblogsEntryId) {
		var uri =
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/microblogs/view_comments.jsp" /></portlet:renderURL>';

		uri =
			Liferay.Util.addParams(
				'<portlet:namespace />parentMicroblogsEntryId=' + microblogsEntryId,
				uri
			) || uri;

		var commentsContainer = A.one(
			'#<portlet:namespace />commentsContainer' + microblogsEntryId
		);

		var commentsContainerContent = commentsContainer.one(
			'.comments-container-content'
		);

		if (!commentsContainerContent) {
			if (!commentsContainer.io) {
				commentsContainer.plug(A.Plugin.IO, {
					autoLoad: false,
					method: 'POST',
				});
			}

			commentsContainer.io.set('uri', uri);

			commentsContainer.io.start();
		}

		var microblogsEntry = microblogsContainer.one(
			'#<portlet:namespace />microblogsEntry' + microblogsEntryId
		);

		microblogsEntry.toggleClass('show-comments');
	};

	microblogsContainer.delegate(
		'click',
		function (event) {
			event.preventDefault();

			showComments(
				event.currentTarget.getAttribute('data-microblogsEntryId')
			);
		},
		'.microblogs-entry .comment a'
	);

	microblogsContainer.delegate(
		'click',
		function (event) {
			event.preventDefault();

			var uri = event.currentTarget.getAttribute('href');

			var microblogsEntryId = event.currentTarget.getAttribute(
				'data-microblogsEntryId'
			);

			var microblogsEntry = A.one(
				'#<portlet:namespace />microblogsEntry' + microblogsEntryId
			);

			var editContainer = microblogsEntry.one('.edit-container');

			var editForm = editContainer.one(
				'#<portlet:namespace />fm' + microblogsEntryId
			);

			if (!editForm) {
				if (!editContainer.io) {
					editContainer.plug(A.Plugin.IO, {
						autoLoad: false,
						method: 'GET',
					});
				}

				editContainer.io.set('uri', uri);
				editContainer.io.start();
			}
			else {
				editForm.toggle();
			}

			var content = microblogsEntry.one('.content');

			content.toggle();
		},
		'.microblogs-entry .edit a'
	);

	microblogsContainer.delegate(
		'click',
		function (event) {
			event.preventDefault();

			if (confirm('Are you sure you want to delete this post?')) {
				Liferay.Util.fetch(event.currentTarget.getAttribute('href'), {
					method: 'POST',
				}).then(function () {
					var updateContainer = A.one(
						'#p_p_id<portlet:namespace /> .portlet-body'
					);

					Liferay.Microblogs.updateMicroblogsList(
						'<%= microblogsEntriesURL %>',
						updateContainer
					);
				});
			}
		},
		'.microblogs-entry .delete a'
	);

	<c:if test="<%= parentMicroblogsEntryId > 0 %>">
		showComments('<%= parentMicroblogsEntryId %>');
	</c:if>
</aui:script>