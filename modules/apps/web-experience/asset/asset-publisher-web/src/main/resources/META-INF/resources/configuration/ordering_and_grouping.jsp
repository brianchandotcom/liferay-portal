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

<span class="field-row">

	<%
	String orderByColumn1 = assetPublisherDisplayContext.getOrderByColumn1();
	%>

	<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="preferences--orderByColumn1--" value="<%= orderByColumn1 %>">
		<c:if test="<%= assetPublisherDisplayContext.isOrderingByTitleEnabled() %>">
			<aui:option label="title" />
		</c:if>

		<aui:option label="create-date" value="createDate" />
		<aui:option label="modified-date" value="modifiedDate" />
		<aui:option label="publish-date" value="publishDate" />
		<aui:option label="expiration-date" value="expirationDate" />
		<aui:option label="priority" value="priority" />

		<c:if test="<%= !AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX %>">
			<aui:option label="view-count" value="viewCount" />
			<aui:option label="ratings" value="ratings" />
		</c:if>
	</aui:select>

	<%
	String orderByType1 = assetPublisherDisplayContext.getOrderByType1();
	%>

	<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType1--" title="order-by-type" value="<%= orderByType1 %>">
		<aui:option label="ascending" value="ASC" />
		<aui:option label="descending" value="DESC" />
	</aui:select>
</span>
<span class="field-row">

	<%
	String orderByColumn2 = assetPublisherDisplayContext.getOrderByColumn2();
	%>

	<aui:select inlineField="<%= true %>" inlineLabel="left" label="and-then-by" name="preferences--orderByColumn2--">
		<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
		<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
		<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
		<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
		<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
		<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />

		<c:if test="<%= !AssetPublisherWebConfigurationValues.SEARCH_WITH_INDEX %>">
			<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
			<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
		</c:if>
	</aui:select>

	<%
	String orderByType2 = assetPublisherDisplayContext.getOrderByType2();
	%>

	<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType2--" title="order-by-type">
		<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
		<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
	</aui:select>
</span>
<span class="field-row">

	<%
	long assetVocabularyId = GetterUtil.getLong(portletPreferences.getValue("assetVocabularyId", null));
	%>

	<aui:select inlineField="<%= true %>" inlineLabel="left" label="group-by" name="preferences--assetVocabularyId--">
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
						assetVocabulary = assetVocabulary.toEscapedModel();
					%>

						<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

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
					assetVocabulary = assetVocabulary.toEscapedModel();
				%>

					<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

				<%
				}
				%>

			</optgroup>

		<%
		}
		%>

	</aui:select>
</span>