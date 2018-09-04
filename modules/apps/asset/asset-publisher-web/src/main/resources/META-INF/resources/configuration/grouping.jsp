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

<aui:row id="grouping">
	<aui:col width="<%= 30 %>">

		<%
		long assetVocabularyId = GetterUtil.getLong(portletPreferences.getValue("assetVocabularyId", null));
		%>

		<aui:select label="group-by" name="preferences--assetVocabularyId--">
			<aui:option value="" />
			<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

			<%
			Group companyGroup = company.getGroup();

			if (scopeGroupId != companyGroup.getGroupId()) {
				List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);

				if (!assetVocabularies.isEmpty()) {
			%>

					<optgroup label="<liferay-ui:message key="vocabularies" />">

						<%
						for (AssetVocabulary assetVocabulary : assetVocabularies) {
						%>

							<aui:option label="<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

						<%
						}
						%>

					</optgroup>

			<%
				}
			}
			%>

			<%
			List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(companyGroup.getGroupId(), false);

			if (!assetVocabularies.isEmpty()) {
			%>

				<optgroup label="<liferay-ui:message key="vocabularies" /> (<liferay-ui:message key="global" />)">

					<%
					for (AssetVocabulary assetVocabulary : assetVocabularies) {
					%>

						<aui:option label="<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

					<%
					}
					%>

				</optgroup>

			<%
			}
			%>

		</aui:select>
	</aui:col>
</aui:row>