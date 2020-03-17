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
SegmentsVocabularyFieldCustomizerFactoryDisplayContext segmentsVocabularyFieldCustomizerFactoryDisplayContext = new SegmentsVocabularyFieldCustomizerFactoryDisplayContext(renderRequest, renderResponse);
%>

<h3 class="autofit-row sheet-subtitle">
	<span class="autofit-col autofit-col-expand">
		<span class="heading-text">
			<liferay-ui:message key="configuration-entries" />
		</span>
	</span>
	<span class="autofit-col">
		<span class="heading-end">
			<a class="btn btn-secondary btn-sm" href="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getAddConfigurationURL() %>"><liferay-ui:message key="add" /></a>
		</span>
	</span>
</h3>

<liferay-ui:search-container
	emptyResultsMessage="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getEmptyResultMessage() %>"
	iteratorURL="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getIteratorURL() %>"
	total="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getTotal() %>"
>
	<liferay-ui:search-container-results
		results="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getResults(searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="org.osgi.service.cm.Configuration"
		keyProperty="ID"
		modelVar="configuration"
	>
		<liferay-ui:search-container-column-text
			name="<%= segmentsVocabularyFieldCustomizerFactoryDisplayContext.getTitle() %>"
		>
			<aui:a href="<%= String.valueOf(segmentsVocabularyFieldCustomizerFactoryDisplayContext.getEditConfigurationURL(configuration)) %>"><strong><%= configuration.getProperties().get("fieldName") %></strong></aui:a>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			align="right"
			cssClass="entry-action"
			name=""
		>
			<liferay-ui:icon-menu
				direction="down"
				markupView="lexicon"
				showWhenSingleIcon="<%= true %>"
			>
				<liferay-ui:icon
					message="edit"
					method="post"
					url="<%= String.valueOf(segmentsVocabularyFieldCustomizerFactoryDisplayContext.getEditConfigurationURL(configuration)) %>"
				/>

				<liferay-ui:icon
					message="delete"
					method="post"
					url="<%= String.valueOf(segmentsVocabularyFieldCustomizerFactoryDisplayContext.getDeleteConfigurationURL(configuration)) %>"
				/>
			</liferay-ui:icon-menu>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
		searchResultCssClass="show-quick-actions-on-hover table table-autofit"
	>
	</liferay-ui:search-iterator>
</liferay-ui:search-container>