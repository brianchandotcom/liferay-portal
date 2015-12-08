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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document document = (Document)row.getObject();

String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
long entryClassPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entryClassName);

if (assetRendererFactory != null) {
	long resourcePrimKey = GetterUtil.getLong(document.get(Field.ROOT_ENTRY_CLASS_PK));

	if (resourcePrimKey > 0) {
		entryClassPK = resourcePrimKey;
	}
}
%>

<span class="asset-entry">
	<span class="asset-entry-type">
		<%= ResourceActionsUtil.getModelResource(locale, entryClassName) %>
	</span>

	<span class="toggle-details">[+]</span>

	<span class="asset-entry-title">

		<%
		String name = document.get(locale, Field.NAME);

		if (Validator.isNull(name)) {
			name = document.get(locale, Field.TITLE);
		}

		if (Validator.isNull(name)) {
			name = document.get(locale, "fullName");
		}
		%>

		<%= HtmlUtil.escape(name) %>
	</span>

	<%
	AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(entryClassName, entryClassPK);
	%>

	<c:if test="<%= (assetEntry != null) && (ArrayUtil.isNotEmpty(assetEntry.getCategoryIds()) || ArrayUtil.isNotEmpty(assetEntry.getTagNames())) %>">
		<div class="asset-entry-content">

			<%
			PortletURL portletURL = (PortletURL)request.getAttribute("search.jsp-portletURL");
			%>

			<liferay-ui:asset-tags-summary
				className="<%= entryClassName %>"
				classPK="<%= entryClassPK %>"
				paramName="<%= Field.ASSET_TAG_NAMES %>"
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>

			<liferay-ui:asset-categories-summary
				className="<%= entryClassName %>"
				classPK="<%= entryClassPK %>"
				paramName="<%= Field.ASSET_CATEGORY_IDS %>"
				portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			/>
		</div>
	</c:if>

	<table class="asset-entry-fields hide lfr-table">
		<thead>
			<tr>
				<th class="key">
					<liferay-ui:message key="key" />
				</th>
				<th class="value">
					<liferay-ui:message key="value" />
				</th>
			</tr>
		</thead>
		<tbody>

			<%
			List<Map.Entry<String, Field>> fields = new LinkedList(document.getFields().entrySet());

			Collections.sort(
				fields,
				new Comparator<Map.Entry<String, Field>>() {

					public int compare(Map.Entry<String, Field> entry1, Map.Entry<String, Field> entry2) {
						return entry1.getKey().compareTo(entry2.getKey());
					}

				}
			);

			for (Map.Entry<String, Field> entry : fields) {
				Field field = entry.getValue();

				String fieldName = field.getName();

				if (fieldName.equals(Field.UID)) {
					continue;
				}

				String[] values = field.getValues();
			%>

				<tr>
					<td class="key" valign="top">
						<strong><%= HtmlUtil.escape(field.getName()) %></strong>

						<br />

						<em>
							<liferay-ui:message key="array" /> = <%= values.length > 1 %>, <liferay-ui:message key="boost" /> = <%= field.getBoost() %>,<br />

							<liferay-ui:message key="numeric" /> = <%= field.isNumeric() %>, <liferay-ui:message key="tokenized" /> = <%= field.isTokenized() %>
						</em>
					</td>
					<td class="value" valign="top">
						<div class="container">
							<code>

								<%
								StringBundler sb = new StringBundler(2 + (4 * values.length));

								for (int i = 0; i < values.length; i++) {
									if (field.isNumeric()) {
										sb.append(HtmlUtil.escape(values[i]));
									}
									else {
										sb.append(StringPool.QUOTE);
										sb.append(HtmlUtil.escape(values[i]));
										sb.append(StringPool.QUOTE);
									}

									sb.append(StringPool.COMMA_AND_SPACE);
								}

								sb.setIndex(sb.index() - 1);

								if (values.length > 1) {
									sb.setStringAt(StringPool.OPEN_BRACKET, 0);

									sb.append(StringPool.CLOSE_BRACKET);
								}
								%>

								<%= sb.toString() %>
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