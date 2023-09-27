<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
KaleoFormsTaskTemplateSearchDisplayContext kaleoFormsTaskTemplateSearchDisplayContext = new KaleoFormsTaskTemplateSearchDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderRequest);
%>

<div id="<portlet:namespace />formsSearchContainer">
	<liferay-ui:search-container
		searchContainer="<%= kaleoFormsTaskTemplateSearchDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPair"
			modelVar="taskFormsPair"
		>
			<liferay-ui:search-container-row-parameter
				name="backURL"
				value="<%= kaleoFormsTaskTemplateSearchDisplayContext.getBackURL() %>"
			/>

			<liferay-ui:search-container-column-text
				name="task"
				value="<%= HtmlUtil.escape(taskFormsPair.getWorkflowTaskName()) %>"
			/>

			<%
			long ddmTemplateId = taskFormsPair.getDDMTemplateId();

			String formName = StringPool.BLANK;

			if (ddmTemplateId > 0) {
				try {
					DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(ddmTemplateId);

					formName = ddmTemplate.getName(locale);
				}
				catch (PortalException pe) {
				}
			}
			%>

			<liferay-util:buffer
				var="taskInputBuffer"
			>
				<c:if test="<%= taskFormsPair.equals(kaleoFormsTaskTemplateSearchDisplayContext.getInitialStateKaleoTaskFormPair()) %>">
					<aui:input name="ddmTemplateId" required="<%= true %>" type="hidden" value="<%= Validator.isNull(formName) ? StringPool.BLANK : String.valueOf(ddmTemplateId) %>" />
				</c:if>
			</liferay-util:buffer>

			<liferay-ui:search-container-column-text
				name="form"
				value="<%= HtmlUtil.escape(formName) + taskInputBuffer %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/admin/process/task_template_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<portlet:resourceURL id="saveInPortletSession" var="saveInPortletSessionURL" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", HtmlUtil.escapeURL(kaleoFormsTaskTemplateSearchDisplayContext.getBackURL())
		).put(
			"itemSelectorURL",
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, DDMPortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
			).setMVCPath(
				"/select_template.jsp"
			).setParameter(
				"classNameId", PortalUtil.getClassNameId(DDMStructure.class)
			).setParameter(
				"navigationStartsOn", DDMNavigationHelper.SELECT_TEMPLATE
			).setParameter(
				"portletResourceNamespace", liferayPortletResponse.getNamespace()
			).setParameter(
				"refererPortletName", portletDisplay.getId()
			).setParameter(
				"resourceClassNameId", scopeClassNameId
			).setParameter(
				"scopeTitle", LanguageUtil.get(request, "form")
			).setParameter(
				"showBackURL", false
			).setParameter(
				"showHeader", false
			).setParameter(
				"structureAvailableFields", liferayPortletResponse.getNamespace() + "getAvailableFields"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).put(
			"saveInPortletSessionURL", saveInPortletSessionURL
		).build()
	%>'
	module="admin/js/KaleoFormsTemplateSelector"
/>

<aui:script use="aui-base,aui-io-request,liferay-util">
	Liferay.provide(
		window,
		'<portlet:namespace />editFormTemplate',
		(uri) => {
			var A = AUI();

			var WIN = A.config.win;

			Liferay.Util.openWindow({
				dialog: {
					destroyOnHide: true,
				},
				id: A.guid(),
				refreshWindow: WIN,
				title: '<liferay-ui:message key="forms" />',
				uri: uri,
			});
		},
		['liferay-util']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />unassignForm',
		(event) => {
			var A = AUI();

			var data = {};

			data['<portlet:namespace />kaleoProcessLinkDDMStructureId'] =
				event.ddmStructureId;
			data['<portlet:namespace />kaleoProcessLinkDDMTemplateId'] = 0;
			data['<portlet:namespace />kaleoProcessLinkWorkflowDefinition'] =
				event.workflowDefinition;
			data['<portlet:namespace />kaleoProcessLinkWorkflowTaskName'] =
				event.workflowTaskName;

			A.io.request('<portlet:resourceURL id="saveInPortletSession" />', {
				after: {
					success: function () {
						window.location = decodeURIComponent(
							'<%= HtmlUtil.escapeURL(kaleoFormsTaskTemplateSearchDisplayContext.getBackURL()) %>'
						);
					},
				},
				data: data,
			});
		},
		['aui-base']
	);
</aui:script>