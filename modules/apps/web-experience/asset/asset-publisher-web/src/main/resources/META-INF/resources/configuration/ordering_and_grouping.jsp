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

<aui:col width="<%= 30 %>">

	<%
	String orderByColumn1 = assetPublisherDisplayContext.getOrderByColumn1();
	%>

	<aui:select label="order-by" name="preferences--orderByColumn1--" value="<%= orderByColumn1 %>" wrapperCssClass="field-inline w90">
		<c:if test="<%= assetPublisherDisplayContext.isOrderingByTitleEnabled() %>">
			<aui:option label="title" />
		</c:if>

		<aui:option label="create-date" value="createDate" />
		<aui:option label="modified-date" value="modifiedDate" />
		<aui:option label="publish-date" value="publishDate" />
		<aui:option label="expiration-date" value="expirationDate" />
		<aui:option label="priority" value="priority" />

		<c:if test="<%= !assetPublisherWebConfiguration.searchWithIndex() %>">
			<aui:option label="view-count" value="viewCount" />
			<aui:option label="ratings" value="ratings" />
		</c:if>
	</aui:select>

	<%
	String orderByType1 = assetPublisherDisplayContext.getOrderByType1();

	String orderByType1OnClick = renderResponse.getNamespace() + "toggleOrderByType(1);";
	String orderByType1AscIconCssClass = (StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "" : "text-muted") + " icon-long-arrow-up order-switch";
	String orderByType1DescIconCssClass = (StringUtil.equalsIgnoreCase(orderByType1, "ASC") ? "text-muted" : "") + " icon-long-arrow-down order-switch";
	%>

	<aui:field-wrapper cssClass="field-label-inline">
		<liferay-ui:icon
			iconCssClass="<%= orderByType1AscIconCssClass %>"
			id="orderByType1Asc"
			markupView="lexicon"
			onClick="<%= orderByType1OnClick %>"
			url="javascript:;"
		/>

		<liferay-ui:icon
			iconCssClass="<%= orderByType1DescIconCssClass %>"
			id="orderByType1Desc"
			markupView="lexicon"
			onClick="<%= orderByType1OnClick %>"
			url="javascript:;"
		/>
	</aui:field-wrapper>

	<aui:input name="preferences--orderByType1--" type="hidden" value="<%= orderByType1 %>" />
</aui:col>

<aui:col width="<%= 30 %>">

	<%
	String orderByColumn2 = assetPublisherDisplayContext.getOrderByColumn2();
	%>

	<aui:select label="and-then-by" name="preferences--orderByColumn2--" wrapperCssClass="field-inline w90">
		<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
		<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
		<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
		<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
		<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
		<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />

		<c:if test="<%= !assetPublisherWebConfiguration.searchWithIndex() %>">
			<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
			<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
		</c:if>
	</aui:select>

	<%
	String orderByType2 = assetPublisherDisplayContext.getOrderByType2();

	String orderByType2OnClick = renderResponse.getNamespace() + "toggleOrderByType(2);";
	String orderByType2AscIconCssClass = (StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "" : "text-muted") + " icon-long-arrow-up order-switch";
	String orderByType2DescIconCssClass = (StringUtil.equalsIgnoreCase(orderByType2, "ASC") ? "text-muted" : "") + " icon-long-arrow-down order-switch";
	%>

	<aui:field-wrapper cssClass="field-label-inline">
		<liferay-ui:icon
			iconCssClass="<%= orderByType2AscIconCssClass %>"
			id="orderByType2Asc"
			markupView="lexicon"
			onClick="<%= orderByType2OnClick %>"
			url="javascript:;"
		/>

		<liferay-ui:icon
			iconCssClass="<%= orderByType2DescIconCssClass %>"
			id="orderByType2Desc"
			markupView="lexicon"
			onClick="<%= orderByType2OnClick %>"
			url="javascript:;"
		/>
	</aui:field-wrapper>

	<aui:input name="preferences--orderByType2--" type="hidden" value="<%= orderByType2 %>" />
</aui:col>

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
</aui:col>

<aui:script position="" use="aui-base">
	<portlet:namespace/>toggleOrderByType = function(fieldNumber) {
		var hiddenInput = A.one('#<portlet:namespace />orderByType' + fieldNumber);

		var ascSwitch = A.one('#<portlet:namespace />orderByType' + fieldNumber + 'Asc .order-switch');
		var descSwitch = A.one('#<portlet:namespace />orderByType' + fieldNumber + 'Desc .order-switch');

		var newVal = hiddenInput.val() === 'ASC' ? 'DESC' : 'ASC';

		switch (newVal) {
			case 'ASC':
				ascSwitch.removeClass('text-muted');
				descSwitch.addClass('text-muted');
			break;

			case 'DESC':
				ascSwitch.addClass('text-muted');
				descSwitch.removeClass('text-muted');
			break;
		}

		hiddenInput.val(newVal);
	};
</aui:script>