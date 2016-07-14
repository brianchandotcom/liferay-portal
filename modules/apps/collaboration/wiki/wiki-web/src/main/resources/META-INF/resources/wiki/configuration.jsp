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

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<liferay-ui:error key="visibleNodesCount" message="please-specify-at-least-one-visible-node" />

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset collapsible="<%= true %>" label="display-settings">
					<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableRelatedAssets() %>" />

					<aui:input name="preferences--enablePageRatings--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnablePageRatings() %>" />

					<aui:input name="preferences--enableComments--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableComments() %>" />

					<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= wikiPortletInstanceSettingsHelper.isEnableCommentRatings() %>" />
				</aui:fieldset>

				<aui:fieldset collapsible="<%= true %>" label="templates">
					<div class="display-template">
						<liferay-ddm:template-selector
							className="<%= WikiPage.class.getName() %>"
							displayStyle="<%= wikiPortletInstanceSettingsHelper.getDisplayStyle() %>"
							displayStyleGroupId="<%= wikiPortletInstanceSettingsHelper.getDisplayStyleGroupId() %>"
							refreshURL="<%= configurationRenderURL %>"
							showEmptyOption="<%= true %>"
						/>
					</div>
				</aui:fieldset>

				<aui:fieldset collapsible="<%= true %>" label="visible-wikis">
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
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var Util = Liferay.Util;

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('visibleNodes').val(Util.listSelect(form.fm('currentVisibleNodes')));
		form.fm('hiddenNodes').val(Util.listSelect(form.fm('availableVisibleNodes')));

		submitForm(form);
	}
</aui:script>