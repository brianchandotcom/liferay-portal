<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean portalUser = ParamUtil.getBoolean(request, "portalUser");
%>

<div>
	<c:choose>
		<c:when test="<%= !portalUser %>">

			<%
			long entryId = ParamUtil.getLong(request, "entryId");
			%>

			<c:if test="<%= entryId > 0 %>">

				<%
				Entry entry = EntryLocalServiceUtil.getEntry(entryId);

				String redirect = ParamUtil.getString(request, "redirect");
				%>

				<div id="<portlet:namespace />contactSummary">
					<liferay-util:include page="/contacts_center/view_entry.jsp" servletContext="<%= application %>" />
				</div>

				<span id="<portlet:namespace />contactsToolbar">
					<div class="lfr-button-column">
						<div class="lfr-button-column-content">
							<aui:button-row cssClass="edit-toolbar" id='<%= liferayPortletResponse.getNamespace() + "entryToolbar" %>' />
						</div>
					</div>

					<aui:script position="inline" use="aui-toolbar">
						var buttonRow = A.one('#<portlet:namespace />entryToolbar');

						var contactsToolbarChildren = [];

						<portlet:renderURL var="viewEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
							<portlet:param name="mvcPath" value="/contacts_center/edit_entry.jsp" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="entryId" value="<%= String.valueOf(entryId) %>" />
						</portlet:renderURL>

						contactsToolbarChildren.push({
							on: {
								click: function (event) {
									Liferay.component('contactsCenter').showPopup(
										'<%= UnicodeLanguageUtil.get(request, "update-contact") %>',
										'<%= viewEntryURL %>'
									);
								},
							},
							icon: 'icon-edit',
							id: '<portlet:namespace />edit',
							label: '<%= UnicodeLanguageUtil.get(request, "edit") %>',
						});

						contactsToolbarChildren.push({
							on: {
								click: function (event) {
									var confirmMessage =
										'<%= UnicodeLanguageUtil.format(request, "are-you-sure-you-want-to-delete-x-from-your-contacts", entry.getFullName(), false) %>';

									Liferay.Util.openConfirmModal({
										message: confirmMessage,
										onConfirm: (isConfirmed) => {
											if (isConfirmed) {
												var data = new URLSearchParams();
												data.append(
													'<portlet:namespace />entryId',
													<%= entryId %>
												);

												Liferay.Util.fetch(
													'<portlet:actionURL name="deleteEntry" />',
													{
														body: data,
														method: 'POST',
													}
												)
													.then((response) => {
														return response.text();
													})
													.then((data) => {
														location.href =
															'<%= HtmlUtil.escape(redirect) %>';
													})
													.catch(() => {
														Liferay.component('contactsCenter').showMessage(
															false
														);
													});
											}
										},
									});
								},
							},
							icon: 'icon-remove',
							id: '<portlet:namespace />delete',
							label: '<%= UnicodeLanguageUtil.get(request, "delete") %>',
						});

						new A.Toolbar({
							activeState: false,
							boundingBox: buttonRow,
							children: contactsToolbarChildren,
						}).render();
					</aui:script>
				</span>
			</c:if>
		</c:when>
		<c:otherwise>

			<%
			long userId = ParamUtil.getLong(request, "userId");

			User user2 = null;

			if (userId > 0) {
				user2 = UserLocalServiceUtil.getUser(userId);
			}
			%>

			<c:if test="<%= user2 != null %>">
				<div id="<portlet:namespace />contactSummary">
					<liferay-util:include page="/view_user.jsp" servletContext="<%= application %>" />
				</div>
			</c:if>

			<span id="<portlet:namespace />contactsToolbar">
				<c:choose>
					<c:when test='<%= ParamUtil.getBoolean(request, "showDetailView") %>'>
						<div class="lfr-button-column">
							<div class="lfr-button-column-content">
								<aui:button-row cssClass="edit-toolbar" id='<%= liferayPortletResponse.getNamespace() + "userToolbar" %>' />
							</div>
						</div>

						<aui:script position="inline" use="aui-base,aui-toolbar,liferay-contacts-center">
							var buttonRow = A.one('#<portlet:namespace />userToolbar');

							var contactsToolbarChildren = [];

							contactsToolbarChildren.push({
								icon: 'icon-chevron-sign-left',
								id: '<portlet:namespace />backSelection',
								label: '<%= UnicodeLanguageUtil.get(request, "back-to-selection") %>',
								on: {
									click: function (event) {
										Liferay.component('contactsCenter')._setVisibleSelectedUsersView();
									},
								},
							});

							new A.Toolbar({
								activeState: false,
								boundingBox: buttonRow,
								children: contactsToolbarChildren,
							}).render();
						</aui:script>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/contacts_center/contacts_center_toolbar.jsp" servletContext="<%= application %>" />
					</c:otherwise>
				</c:choose>
			</span>
		</c:otherwise>
	</c:choose>
</div>