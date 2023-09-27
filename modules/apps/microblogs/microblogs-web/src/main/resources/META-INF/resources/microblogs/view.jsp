<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
MicroblogsDisplayContext microblogsDisplayContext = new MicroblogsDisplayContext(request, renderRequest, renderResponse);
%>

<div class="microblogs-container">
	<c:if test="<%= MicroblogsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ENTRY) && !microblogsDisplayContext.isUserPublicPage() %>">
		<liferay-util:include page="/microblogs/edit_microblogs_entry.jsp" servletContext="<%= application %>" />
	</c:if>

	<liferay-ui:tabs
		names="<%= microblogsDisplayContext.getTabs1Names() %>"
		param="tabs1"
		url="<%= String.valueOf(microblogsDisplayContext.getPortletURL()) %>"
	/>

	<%
	request.setAttribute(WebKeys.MICROBLOGS_ENTRIES, microblogsDisplayContext.getSearchContainerResults());
	request.setAttribute(WebKeys.MICROBLOGS_ENTRIES_URL, microblogsDisplayContext.getMicroblogsEntriesURL());
	%>

	<liferay-util:include page="/microblogs/view_microblogs_entries.jsp" servletContext="<%= application %>" />

	<liferay-ui:search-paginator
		searchContainer="<%= microblogsDisplayContext.getSearchContainer() %>"
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

		Liferay.Microblogs.updateViewCount(
			<%= microblogsDisplayContext.getParentMicroblogsEntryId() %>
		);
	});

	const microblogsContainer = A.one(
		'#p_p_id<portlet:namespace /> .microblogs-container'
	);

	const showComments = function (microblogsEntryId) {
		let uri =
			'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/microblogs/view_comments.jsp" /></portlet:renderURL>';

		uri =
			Liferay.Util.addParams(
				'<portlet:namespace />parentMicroblogsEntryId=' + microblogsEntryId,
				uri
			) || uri;

		const commentsContainer = A.one(
			'#<portlet:namespace />commentsContainer' + microblogsEntryId
		);

		const commentsContainerContent = commentsContainer.one(
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

		const microblogsEntry = microblogsContainer.one(
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

			const uri = event.currentTarget.getAttribute('href');

			const microblogsEntryId = event.currentTarget.getAttribute(
				'data-microblogsEntryId'
			);

			const microblogsEntry = A.one(
				'#<portlet:namespace />microblogsEntry' + microblogsEntryId
			);

			const editContainer = microblogsEntry.one('.edit-container');

			const editForm = editContainer.one(
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

			const content = microblogsEntry.one('.content');

			content.toggle();
		},
		'.microblogs-entry .edit a'
	);

	microblogsContainer.delegate(
		'click',
		function (event) {
			event.preventDefault();

			Liferay.Util.openConfirmModal({
				message: 'Are you sure you want to delete this post?',
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						Liferay.Util.fetch(
							event.currentTarget.getAttribute('href'),
							{
								method: 'POST',
							}
						).then(function () {
							const updateContainer = A.one(
								'#p_p_id<portlet:namespace /> .portlet-body'
							);

							Liferay.Microblogs.updateMicroblogsList(
								'<%= microblogsDisplayContext.getMicroblogsEntriesURL() %>',
								updateContainer
							);
						});
					}
				},
			});
		},
		'.microblogs-entry .delete a'
	);

	<c:if test="<%= microblogsDisplayContext.getParentMicroblogsEntryId() > 0 %>">
		showComments('<%= microblogsDisplayContext.getParentMicroblogsEntryId() %>');
	</c:if>
</aui:script>