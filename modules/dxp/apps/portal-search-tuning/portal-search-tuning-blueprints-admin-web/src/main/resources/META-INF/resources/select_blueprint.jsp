<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectBlueprintDisplayContext selectBlueprintDisplayContext = new SelectBlueprintDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	displayContext="<%= new SelectBlueprintManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, selectBlueprintDisplayContext.getSearchContainer()) %>"
/>

<clay:container-fluid>
	<aui:form cssClass="container-fluid-1280" method="post" name="selectBlueprintFm">
		<liferay-ui:search-container
			searchContainer="<%= selectBlueprintDisplayContext.getSearchContainer() %>"
			var="blueprintSearchContainer"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.search.tuning.blueprints.model.Blueprint"
				escapedModel="<%= true %>"
				keyProperty="blueprintId"
				modelVar="blueprint"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
					value="<%= blueprint.getTitle(locale) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="description"
					value="<%= blueprint.getDescription(locale) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="modified-date"
					value="<%= blueprint.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text>
					<aui:button
						cssClass="selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"entityid", blueprint.getBlueprintId()
							).put(
								"entityname", blueprint.getTitle(locale)
							).build()
						%>'
						value="choose"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectBlueprintFm',
		'<%= HtmlUtil.escapeJS(selectBlueprintDisplayContext.getEventName()) %>'
	);
</aui:script>