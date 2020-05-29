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
SegmentsContextVocabularyConfigurationFactoryDisplayContext segmentsContextVocabularyConfigurationFactoryDisplayContext = new SegmentsContextVocabularyConfigurationFactoryDisplayContext(renderRequest, renderResponse);
%>

<clay:sheet>
	<clay:content-row>
		<clay:content-col>
			<h2><liferay-ui:message key="segments-context-vocabulary-configuration-name" /></h2>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="true"
		>
			<span class="heading-text">
				<liferay-ui:message key="configuration-entries" />
			</span>
		</clay:content-col>

		<clay:content-col
			containerElement="span"
		>
			<span class="heading-end">
				<a class="btn btn-secondary btn-sm" href="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getAddConfigurationURL() %>"><liferay-ui:message key="add" /></a>
			</span>
		</clay:content-col>
	</clay:content-row>

	<liferay-ui:search-container
		emptyResultsMessage="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getEmptyResultMessage() %>"
		iteratorURL="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getIteratorURL() %>"
		total="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getTotal() %>"
	>
		<liferay-ui:search-container-results
			results="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getResults(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="org.osgi.service.cm.Configuration"
			keyProperty="ID"
			modelVar="configuration"
		>
			<liferay-ui:search-container-column-text
				name="<%= segmentsContextVocabularyConfigurationFactoryDisplayContext.getTitle() %>"
			>
				<aui:a href="<%= String.valueOf(segmentsContextVocabularyConfigurationFactoryDisplayContext.getEditConfigurationURL(configuration)) %>"><strong><%= configuration.getProperties().get("entityfield") %></strong></aui:a>
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
						url="<%= String.valueOf(segmentsContextVocabularyConfigurationFactoryDisplayContext.getEditConfigurationURL(configuration)) %>"
					/>

					<liferay-ui:icon
						message="delete"
						method="post"
						url="<%= String.valueOf(segmentsContextVocabularyConfigurationFactoryDisplayContext.getDeleteConfigurationURL(configuration)) %>"
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
</clay:sheet>