<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="visibleNodesCount" message="please-specify-at-least-one-visible-node" />

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="display-settings"
		>
			<aui:input label="show-related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableRelatedAssets() %>" />

			<aui:input name="preferences--enablePageRatings--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnablePageRatings() %>" />

			<aui:input name="preferences--enableComments--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableComments() %>" />

			<aui:input label="enable-ratings-for-comments" name="preferences--enableCommentRatings--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableCommentRatings() %>" />

			<aui:input name="preferences--enableHighlighting--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableHighlighting() %>" />

			<div class="display-template">
				<liferay-template:template-selector
					className="<%= WikiPage.class.getName() %>"
					displayStyle="<%= wikiPortletInstanceSettingsHelper.getDisplayStyle() %>"
					displayStyleGroupId="<%= wikiPortletInstanceSettingsHelper.getDisplayStyleGroupId() %>"
					refreshURL="<%= configurationRenderURL %>"
					showEmptyOption="<%= true %>"
				/>
			</div>
		</liferay-frontend:fieldset>

		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			label="visible-wikis"
		>
			<aui:input name="preferences--visibleNodes--" type="hidden" />
			<aui:input name="preferences--hiddenNodes--" type="hidden" />

			<%
			List<KeyValuePair> currentVisibleNodesList = new ArrayList<KeyValuePair>();
			List<KeyValuePair> availableVisibleNodesList = new ArrayList<KeyValuePair>();
			
			Set<String> currentVisibleNodes = new HashSet<String>(wikiPortletInstanceSettingsHelper.getAllNodeNames());
			String[] visibleNodeNames = wikiPortletInstanceSettingsHelper.getVisibleNodeNames();

			for (String folderColumn : visibleNodeNames) {
				if (currentVisibleNodes.contains(folderColumn)) {
					currentVisibleNodesList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
				}
			}

			Arrays.sort(visibleNodeNames);

			String[] hiddenNodes = wikiPortletInstanceSettingsHelper.getHiddenNodes();

			Arrays.sort(hiddenNodes);

			for (String folderColumn : currentVisibleNodes) {
				if ((Arrays.binarySearch(hiddenNodes, folderColumn) < 0) && (Arrays.binarySearch(visibleNodeNames, folderColumn) < 0)) {
					currentVisibleNodesList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
				}
			}

			for (String folderColumn : hiddenNodes) {
				if (currentVisibleNodes.contains(folderColumn) && (Arrays.binarySearch(visibleNodeNames, folderColumn) < 0)) {
					availableVisibleNodesList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
				}
			}

			availableVisibleNodesList = ListUtil.sort(availableVisibleNodesList, new KeyValuePairComparator(false, true));
			%>

			<liferay-ui:input-move-boxes
				rightBoxName="currentVisibleNodes"
				rightList="<%= currentVisibleNodesList %>"
				rightReorder="<%= Boolean.TRUE.toString() %>"
				rightTitle="visible"
				leftBoxName="availableVisibleNodes"
				leftList="<%= availableVisibleNodesList %>"
				leftTitle="hidden"
			/>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var availableVisibleNodes = Liferay.Util.getFormElement(
			form,
			'availableVisibleNodes'
		);
		var currentVisibleNodes = Liferay.Util.getFormElement(
			form,
			'currentVisibleNodes'
		);

		if (availableVisibleNodes && currentVisibleNodes) {
			Liferay.Util.postForm(form, {
				data: {
					hiddenNodes: Liferay.Util.getSelectedOptionValues(
						availableVisibleNodes
					),
					visibleNodes:
						Liferay.Util.getSelectedOptionValues(currentVisibleNodes),
				},
			});
		}
	}
</aui:script>