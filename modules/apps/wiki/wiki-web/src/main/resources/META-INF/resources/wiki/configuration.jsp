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

<%@ include file="/wiki/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="visibleNodesCount" message="please-specify-at-least-one-visible-node" />

		<liferay-frontend:fieldset-group>
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
					<liferay-ddm:template-selector
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
				Set<String> currentVisibleNodes = new HashSet<String>(wikiPortletInstanceSettingsHelper.getAllNodeNames());

				// Left list

				List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

				String[] visibleNodeNames = wikiPortletInstanceSettingsHelper.getVisibleNodeNames();

				for (String folderColumn : visibleNodeNames) {
					if (currentVisibleNodes.contains(folderColumn)) {
						leftList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
					}
				}

				Arrays.sort(visibleNodeNames);

				String[] hiddenNodes = wikiPortletInstanceSettingsHelper.getHiddenNodes();

				Arrays.sort(hiddenNodes);

				for (String folderColumn : currentVisibleNodes) {
					if ((Arrays.binarySearch(hiddenNodes, folderColumn) < 0) && (Arrays.binarySearch(visibleNodeNames, folderColumn) < 0)) {
						leftList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
					}
				}

				// Right list

				List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

				for (String folderColumn : hiddenNodes) {
					if (currentVisibleNodes.contains(folderColumn)) {
						if (Arrays.binarySearch(visibleNodeNames, folderColumn) < 0) {
							rightList.add(new KeyValuePair(folderColumn, HtmlUtil.escape(LanguageUtil.get(request, folderColumn))));
						}
					}
				}

				rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
				%>

				<liferay-ui:input-move-boxes
					leftBoxName="currentVisibleNodes"
					leftList="<%= leftList %>"
					leftReorder="<%= Boolean.TRUE.toString() %>"
					leftTitle="visible"
					rightBoxName="availableVisibleNodes"
					rightList="<%= rightList %>"
					rightTitle="hidden"
				/>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<script>
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
					hiddenNodes: Liferay.Util.listSelect(availableVisibleNodes),
					visibleNodes: Liferay.Util.listSelect(currentVisibleNodes)
				}
			});
		}
	}
</script>