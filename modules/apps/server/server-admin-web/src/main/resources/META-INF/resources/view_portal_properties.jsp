<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewPortalPropertiesDisplayContext viewPortalPropertiesDisplayContext = new ViewPortalPropertiesDisplayContext(request, liferayPortletRequest, liferayPortletResponse, renderResponse);
%>

<clay:management-toolbar
	clearResultsURL="<%= String.valueOf(viewPortalPropertiesDisplayContext.getClearResultsURL()) %>"
	itemsTotal="<%= viewPortalPropertiesDisplayContext.getSearchContainerTotal() %>"
	searchActionURL="<%= String.valueOf(viewPortalPropertiesDisplayContext.getPortletURL()) %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		searchContainer="<%= viewPortalPropertiesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="java.util.Map.Entry"
			modelVar="entry"
		>

			<%
			String property = (String)entry.getKey();
			String value = (String)entry.getValue();

			List<String> overriddenProperties = viewPortalPropertiesDisplayContext.getOverriddenProperties();

			boolean overriddenPropertyValue = overriddenProperties.contains(property);

			String featureFlagPrefix = "feature.flag.";
			%>

			<c:choose>
				<c:when test="<%= StringUtil.startsWith(property, featureFlagPrefix) %>">
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="property"
						value="<%= HtmlUtil.escape(StringUtil.shorten(property, 80)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="value"
					>
						<select name="<portlet:namespace />portalProperty<%= HtmlUtil.escapeAttribute(property) %>">
							<option <%= Objects.equals("true", value) ? "selected" : StringPool.BLANK %> value="true"><liferay-ui:message key="true" /></option>
							<option <%= Objects.equals("false", value) ? "selected" : StringPool.BLANK %> value="false"><liferay-ui:message key="false" /></option>
						</select>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="source"
					>
						<liferay-ui:icon
							iconCssClass='<%= overriddenPropertyValue ? "icon-hdd" : "icon-file-alt" %>'
							message='<%= LanguageUtil.get(request, overriddenPropertyValue ? "the-value-of-this-property-was-overridden-using-the-control-panel-and-is-stored-in-the-database" : "the-value-of-this-property-is-read-from-a-portal.properties-file-or-one-of-its-extension-files") %>'
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						name="property"
						value="<%= HtmlUtil.escape(StringUtil.shorten(property, 80)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="value"
					>
						<c:if test="<%= Validator.isNotNull(value) %>">
							<c:choose>
								<c:when test="<%= value.length() > 80 %>">
									<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(value) %>">
										<%= HtmlUtil.escape(StringUtil.shorten(value, 80)) %>
									</span>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(value) %>
								</c:otherwise>
							</c:choose>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="source"
					>
						<liferay-ui:icon
							icon='<%= overriddenPropertyValue ? "hdd" : "document" %>'
							markupView="lexicon"
							message='<%= LanguageUtil.get(request, overriddenPropertyValue ? "the-value-of-this-property-was-overridden-using-the-control-panel-and-is-stored-in-the-database" : "the-value-of-this-property-is-read-from-a-portal.properties-file-or-one-of-its-extension-files") %>'
						/>
					</liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<aui:button-row>
		<aui:button cssClass="save-server-button" data-cmd="updatePortalProperties" value="save" />
	</aui:button-row>
</clay:container-fluid>