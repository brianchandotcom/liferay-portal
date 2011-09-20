<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document document = (Document)row.getObject();

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
String[] queryTerms = (String[])request.getAttribute("view.jsp-queryTerms");

String entryClassName = document.get(Field.ENTRY_CLASS_NAME);

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entryClassName);
%>

<span class="asset-entry">
	<span class="asset-entry-type">
		<%= ResourceActionsUtil.getModelResource(locale, entryClassName) %>
	</span>

	<span class="toggle-details">[+]</span>

	<span class="asset-entry-title">
		<c:if test="<%= assetRendererFactory != null %>">
			<img alt="\" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" />
		</c:if>

		<%
		String name = document.get(locale, Field.NAME);

		if (Validator.isNull(name)) {
			name = document.get(locale, Field.TITLE);
		}

		if (Validator.isNull(name)) {
			name = document.get(locale, "fullName");
		}
		%>

		<%= name %>
	</span>

	<%
	String[] tags = document.getValues(Field.ASSET_TAG_NAMES);
	%>

	<c:if test="<%= Validator.isNotNull(tags[0]) %>">
		<div class="asset-entry-tags">

			<%
			for (int k = 0; k < tags.length; k++) {
				String tag = tags[k];

				String newKeywords = tag.trim();

				PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

				tagURL.setParameter(Field.ASSET_TAG_NAMES, newKeywords);
			%>

				<c:if test="<%= k == 0 %>">
					<div class="taglib-asset-tags-summary">
				</c:if>

				<a class="tag" href="<%= tagURL.toString() %>"><%= tag %></a>

				<c:if test="<%= (k + 1) == tags.length %>">
					</div>
				</c:if>

			<%
			}
			%>

		</div>
	</c:if>

	<%
	String[] categoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);
	%>

	<c:if test="<%= Validator.isNotNull(categoryIds[0]) %>">
		<div class="asset-entry-categories">

			<%
			for (int k = 0; k < categoryIds.length; k++) {

				String categoryId = categoryIds[k];

				AssetCategory assetCategory = null;

				try {
					assetCategory = AssetCategoryLocalServiceUtil.getCategory(GetterUtil.getLong(categoryId));
				}
				catch (NoSuchCategoryException nsce) {
				}

				if (assetCategory == null) {
					continue;
				}

				AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(assetCategory.getVocabularyId());

				PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

				categoryURL.setParameter(Field.ASSET_CATEGORY_NAMES, assetCategory.getName());
			%>

				<c:if test="<%= k == 0 %>">
					<div class="taglib-asset-categories-summary">
						<span class="asset-vocabulary">
							<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>:
						</span>
				</c:if>

				<a class="asset-category" href="<%= categoryURL.toString() %>">
					<%= _buildCategoryPath(assetCategory, locale) %>
				</a>

				<c:if test="<%= (k + 1) == categoryIds.length %>">
					</div>
				</c:if>

			<%
			}
			%>

		</div>
	</c:if>

	<table class="lfr-table asset-entry-fields aui-helper-hidden">
		<thead>
			<tr>
				<th class="key"><liferay-ui:message key="key" /></th>
				<th class="value"><liferay-ui:message key="value" /></th>
			</tr>
		</thead>

		<tbody>

			<%
			List<Map.Entry<String,Field>> fields = new LinkedList(document.getFields().entrySet());

			Collections.sort(fields, new Comparator<Map.Entry<String,Field>>() {
				public int compare(Map.Entry<String,Field> o1, Map.Entry<String,Field> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});

			for (Map.Entry<String,Field> entry : fields) {
				Field field = entry.getValue();

				if (field.getName().equals(Field.UID)) {
					continue;
				}

				boolean isArray = field.getValues().length > 1;
			%>

				<tr>
					<td class="key" valign="top">
						<strong><%= HtmlUtil.escape(field.getName()) %></strong>

						<br />

						<em>
							<liferay-ui:message key="array" /> = <%= isArray %>,
							<liferay-ui:message key="boost" /> = <%= field.getBoost() %>, <br />
							<liferay-ui:message key="numeric" /> = <%= field.isNumeric() %>,
							<liferay-ui:message key="tokenized" /> = <%= field.isTokenized() %>
						</em>
					</td>
					<td class="value" valign="top">
						<div class="container">
							<code>
								<%
								String[] values = field.getValues();
								%>

								<c:if test="<%= isArray %>">[</c:if><%for (int i = 0; i < values.length; i++) {%><c:if test="<%= i > 0 %>">, </c:if><c:if test="<%= !field.isNumeric() %>">"</c:if><%= HtmlUtil.escape(values[i]) %><c:if test="<%= !field.isNumeric() %>">"</c:if><%}%><c:if test="<%= isArray %>">]</c:if>
							</code>
						</div>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
	</table>
</span>