<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
QuestionsConfiguration questionsConfiguration = ConfigurationProviderUtil.getPortletInstanceConfiguration(QuestionsConfiguration.class, themeDisplay);

String rootTopicExternalReferenceCode = questionsConfiguration.rootTopicExternalReferenceCode();

MBCategory mbCategory = null;

String rootTopicName = StringPool.BLANK;

try {
	mbCategory = MBCategoryLocalServiceUtil.getMBCategoryByExternalReferenceCode(rootTopicExternalReferenceCode, themeDisplay.getScopeGroupId());

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
	<aui:input name="preferences--rootTopicExternalReferenceCode--" type="hidden" value="<%= rootTopicExternalReferenceCode %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			collapsible="<%= false %>"
			label="general-settings"
		>
			<aui:input name="preferences--showCardsForTopicNavigation--" type="checkbox" value="<%= questionsConfiguration.showCardsForTopicNavigation() %>" />

			<div class="form-group">
				<label>
					<liferay-ui:message key="ask-question-button-text" />
				</label>

				<liferay-ui:input-localized
					fieldPrefix="preferences"
					fieldPrefixSeparator="--"
					name="askQuestionButtonTextAsLocalizedXML"
					xml="<%= questionsConfiguration.askQuestionButtonTextAsLocalizedXML() %>"
				/>
			</div>

			<div class="form-group">
				<label>
					<liferay-ui:message key="edit-question-page-title" />
				</label>

				<liferay-ui:input-localized
					fieldPrefix="preferences"
					fieldPrefixSeparator="--"
					name="editQuestionPageTitleAsLocalizedXML"
					xml="<%= questionsConfiguration.editQuestionPageTitleAsLocalizedXML() %>"
				/>
			</div>

			<div class="form-group">
				<label>
					<liferay-ui:message key="new-question-page-title" />
				</label>

				<liferay-ui:input-localized
					fieldPrefix="preferences"
					fieldPrefixSeparator="--"
					name="newQuestionPageTitleAsLocalizedXML"
					xml="<%= questionsConfiguration.newQuestionPageTitleAsLocalizedXML() %>"
				/>
			</div>

			<div class="form-group">
				<label>
					<liferay-ui:message key="post-your-question-button-text" />
				</label>

				<liferay-ui:input-localized
					fieldPrefix="preferences"
					fieldPrefixSeparator="--"
					name="postYourQuestionButtonTextAsLocalizedXML"
					xml="<%= questionsConfiguration.postYourQuestionButtonTextAsLocalizedXML() %>"
				/>
			</div>

			<div class="form-group">
				<label>
					<liferay-ui:message key="update-your-question-button-text" />
				</label>

				<liferay-ui:input-localized
					fieldPrefix="preferences"
					fieldPrefixSeparator="--"
					name="updateYourQuestionButtonTextAsLocalizedXML"
					xml="<%= questionsConfiguration.updateYourQuestionButtonTextAsLocalizedXML() %>"
				/>
			</div>

			<div class="form-group">
				<aui:input label="root-topic" name="rootTopicName" type="resource" value="<%= rootTopicName %>" />

				<aui:button name="selectRootTopicButton" value="select" />

				<aui:button disabled="<%= Validator.isBlank(rootTopicExternalReferenceCode) %>" name="removeRootTopicButton" value="remove" />
			</div>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	var selectRootTopicButton = document.getElementById(
		'<portlet:namespace />selectRootTopicButton'
	);

	if (selectRootTopicButton) {
		selectRootTopicButton.addEventListener('click', (event) => {
			Liferay.Util.openSelectionModal({
				onSelect: function (event) {
					var form = document.<portlet:namespace />fm;

					Liferay.Util.setFormValues(form, {
						rootTopicExternalReferenceCode:
							event.resourceexternalreferencecode,
						rootTopicName: Liferay.Util.unescape(event.resourcename),
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
					"mbCategoryExternalReferenceCode", rootTopicExternalReferenceCode
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildPortletURL();
				%>

				url: '<%= selectMBCategoryURL.toString() %>',
			});
		});
	}

	var removeRootTopicButton = document.getElementById(
		'<portlet:namespace />removeRootTopicButton'
	);

	if (removeRootTopicButton) {
		removeRootTopicButton.addEventListener('click', (event) => {
			const form = document.<portlet:namespace />fm;

			if (!form) {
				return;
			}

			Liferay.Util.setFormValues(form, {
				rootTopicExternalReferenceCode: '',
				rootTopicName: '',
			});

			Liferay.Util.toggleDisabled(removeRootTopicButton, true);
		});
	}
</aui:script>

<%!
private static final Log _log = LogFactoryUtil.getLog("com_liferay_questions_web.configuarion_jsp");
%>