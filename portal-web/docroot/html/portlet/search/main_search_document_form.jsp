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

<span class="document">

	<%
	String className = document.get(Field.ENTRY_CLASS_NAME);
	long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));
	%>

	<span class="toggle-details">[+]</span>

	<span class="entry-title">

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

	<br />

	<span class="entry-type"><%= ResourceActionsUtil.getModelResource(locale, className) %></span>

	<br />

	<%
	String[] tags = document.getValues(Field.ASSET_TAG_NAMES);

	if (Validator.isNotNull(tags[0])) {
		StringBundler sb = new StringBundler();

		for (int k = 0; k < tags.length; k++) {
			String tag = tags[k];

			String newKeywords = tag.trim();

			PortletURL tagURL = PortletURLUtil.clone(portletURL, renderResponse);

			tagURL.setParameter(Field.ASSET_TAG_NAMES, newKeywords);

			if (k == 0) {
				sb.append("<div class=\"entry-tags\">");
				sb.append("<div class=\"taglib-asset-tags-summary\">");
			}

			sb.append("<a class=\"tag\" href=\"");
			sb.append(tagURL.toString());
			sb.append("\">");
			sb.append(tag);
			sb.append("</a>");

			if ((k + 1) == tags.length) {
				sb.append("</div>");
				sb.append("</div>");
			}
		}
	%>

		<%= sb.toString() %>

	<%
	}

	String[] categoryIds = document.getValues(Field.ASSET_CATEGORY_IDS);

	if (Validator.isNotNull(categoryIds[0])) {
		StringBundler sb = new StringBundler();

		for (int k = 0; k < categoryIds.length; k++) {
			String categoryId = categoryIds[k];

			AssetCategory category = null;

			try {
				category = AssetCategoryLocalServiceUtil.getCategory(GetterUtil.getLong(categoryId));
			}
			catch (NoSuchCategoryException nsce) {
				continue;
			}

			AssetVocabulary vocabulary = AssetVocabularyLocalServiceUtil.getVocabulary(category.getVocabularyId());

			PortletURL categoryURL = PortletURLUtil.clone(portletURL, renderResponse);

			categoryURL.setParameter(Field.ASSET_CATEGORY_NAMES, category.getName());

			if (k == 0) {
				sb.append("<div class=\"entry-categories\">");
				sb.append("<div class=\"taglib-asset-categories-summary\">");
				sb.append("<span class=\"asset-vocabulary\">");
				sb.append(HtmlUtil.escape(vocabulary.getTitle(locale)));
				sb.append(":</span> ");
			}

			sb.append("<a class=\"asset-category\" href=\"");
			sb.append(categoryURL.toString());
			sb.append("\">");
			sb.append(_buildCategoryPath(category, locale));
			sb.append("</a>");

			if ((k + 1) == categoryIds.length) {
				sb.append("</div>");
				sb.append("</div>");
			}
		}
	%>

		<%= sb.toString() %>

	<%
	}
	%>

	<table class="lfr-table document-fields aui-helper-hidden">
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