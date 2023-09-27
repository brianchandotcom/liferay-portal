<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:row
	id='<%= liferayPortletResponse.getNamespace() + "grouping" %>'
>
	<clay:col
		md="4"
	>

		<%
		long assetVocabularyId = GetterUtil.getLong(portletPreferences.getValue("assetVocabularyId", null));
		%>

		<aui:select label="group-by" name="preferences--assetVocabularyId--">
			<aui:option value="" />
			<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

			<%
			Group companyGroup = company.getGroup();
			%>

			<c:if test="<%= scopeGroupId != companyGroup.getGroupId() %>">

				<%
				List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);
				%>

				<c:if test="<%= !assetVocabularies.isEmpty() %>">
					<optgroup label="<liferay-ui:message key="vocabularies" />">

						<%
						for (AssetVocabulary assetVocabulary : assetVocabularies) {
						%>

							<aui:option label="<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

						<%
						}
						%>

					</optgroup>
				</c:if>
			</c:if>

			<%
			List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(companyGroup.getGroupId(), false);
			%>

			<c:if test="<%= !assetVocabularies.isEmpty() %>">
				<optgroup label="<liferay-ui:message key="vocabularies" /> (<liferay-ui:message key="global" />)">

					<%
					for (AssetVocabulary assetVocabulary : assetVocabularies) {
					%>

						<aui:option label="<%= HtmlUtil.escape(assetVocabulary.getTitle(locale)) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

					<%
					}
					%>

				</optgroup>
			</c:if>
		</aui:select>
	</clay:col>
</clay:row>