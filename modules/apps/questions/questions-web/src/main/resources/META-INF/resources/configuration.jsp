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
QuestionsConfiguration questionsConfiguration = portletDisplay.getPortletInstanceConfiguration(QuestionsConfiguration.class);

long rootTopicId = questionsConfiguration.rootTopicId();

MBCategory mbCategory = null;

String rootTopicName = StringPool.BLANK;

try {
	mbCategory = MBCategoryLocalServiceUtil.getCategory(rootTopicId);

	rootTopicName = mbCategory.getName();
}
catch (Exception exception) {
	if (_log.isDebugEnabled()) {
		_log.debug(exception);
	}
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--rootTopicId--" type="hidden" value="<%= rootTopicId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsible="<%= false %>"
				label="general-settings"
			>
				<aui:input name="preferences--showCardsForTopicNavigation--" type="checkbox" value="<%= questionsConfiguration.showCardsForTopicNavigation() %>" />

				<div class="form-group">
					<aui:input label="root-topic-id" name="rootTopicName" type="resource" value="<%= rootTopicName %>" />

					<aui:button name="selectRootTopicButton" value="select" />

					<%
					String taglibRemoveRootTopic = "Liferay.Util.removeEntitySelection('rootTopicId', 'rootTopicName', this, '" + liferayPortletResponse.getNamespace() + "');";
					%>

					<aui:button disabled="<%= rootTopicId <= 0 %>" name="removeRootTopicButton" onClick="<%= taglibRemoveRootTopic %>" value="remove" />
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
	var selectRootTopicButton = document.getElementById(
		'<portlet:namespace />selectRootTopicButton'
	);

	if (selectRootTopicButton) {
		selectRootTopicButton.addEventListener('click', (event) => {
			Liferay.Util.openSelectionModal({
				onSelect: function (event) {
					var form = document.<portlet:namespace />fm;

					Liferay.Util.setFormValues(form, {
						rootTopicName: Liferay.Util.unescape(event.name),
						rootTopicId: event.categoryid,
					});

					var removeRootTopicButton = document.getElementById(
						'<portlet:namespace />removeRootTopicButton'
					);

					if (removeRootTopicButton) {
						Liferay.Util.toggleDisabled(removeRootTopicButton, false);
					}
				},
				selectEventName: '<portlet:namespace />selectCategory',
				title: '<liferay-ui:message arguments="category" key="select-x" />',

				<%
				PortletURL selectMBCategoryURL = PortletURLBuilder.create(
					PortletProviderUtil.getPortletURL(request, MBCategory.class.getName(), PortletProvider.Action.EDIT)
				).setMVCRenderCommandName(
					"/message_boards/select_category"
				).setParameter(
					"mbCategoryId", rootTopicId
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildPortletURL();
				%>

				url: '<%= selectMBCategoryURL.toString() %>',
			});
		});
	}
</script>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_questions_web.configuarion_jsp");
%>