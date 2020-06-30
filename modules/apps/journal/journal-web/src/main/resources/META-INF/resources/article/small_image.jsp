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
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="small_image"
/>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<%
JournalFileUploadsConfiguration journalFileUploadsConfiguration = (JournalFileUploadsConfiguration)request.getAttribute(JournalFileUploadsConfiguration.class.getName());
%>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">
	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalFileUploadsConfiguration.imageExtensions(), ", ")) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>">
	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(journalFileUploadsConfiguration.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<div id="<portlet:namespace />smallImageContainer">
	<div class="lfr-journal-small-image-header">
		<aui:input id="useSmallImage" label="use-small-image" name="smallImage" />
	</div>

	<div class="lfr-journal-small-image-content toggler-content-collapsed">
		<aui:row>
			<c:if test="<%= smallImage && (article != null) %>">
				<aui:col width="<%= 50 %>">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="img-responsive lfr-journal-small-image-preview" src="<%= HtmlUtil.escapeAttribute(article.getArticleImageURL(themeDisplay)) %>" />
				</aui:col>
			</c:if>

			<aui:col width="<%= (smallImage && (article != null)) ? 50 : 100 %>">
				<liferay-frontend:fieldset
					id="smallImageURL"
				>
					<aui:input cssClass="lfr-journal-small-image-type" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" inlineField="<%= true %>" label="small-image-url" name="smallImageType" type="radio" />

					<aui:input cssClass="lfr-journal-small-image-value" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" inlineField="<%= true %>" label="" name="smallImageURL" title="small-image-url" />
				</liferay-frontend:fieldset>

				<liferay-frontend:fieldset
					id="smallFile"
				>
					<aui:input cssClass="lfr-journal-small-image-type" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" inlineField="<%= true %>" label="small-image" name="smallImageType" type="radio" />

					<aui:input cssClass="lfr-journal-small-image-value" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" inlineField="<%= true %>" label="" name="smallFile" type="file" />
				</liferay-frontend:fieldset>
			</aui:col>
		</aui:row>
	</div>
</div>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />smallImageContainer');

	var types = container.all('.lfr-journal-small-image-type');
	var values = container.all('.lfr-journal-small-image-value');

	var selectSmallImageType = function(index) {
		types.attr('checked', false);

		types.item(index).attr('checked', true);

		values.attr('disabled', true);

		values.item(index).attr('disabled', false);
	};

	container.delegate(
		'change',
		function(event) {
			var index = types.indexOf(event.currentTarget);

			selectSmallImageType(index);
		},
		'.lfr-journal-small-image-type'
	);

	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />smallImageContainer .lfr-journal-small-image-content',
			expanded: <%= smallImage %>,
			header: '#<portlet:namespace />smallImageContainer .lfr-journal-small-image-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />useSmallImage').attr('checked', expanded);

					if (expanded) {
						types.each(
							function(item, index) {
								if (item.get('checked')) {
									values.item(index).attr('disabled', false);
								}
							}
						);
					}
					else {
						values.attr('disabled', true);
					}
				}
			}
		}
	);

	// LPS-51306

	setTimeout(
		function() {
			selectSmallImageType('<%= ((article != null) && Validator.isNotNull(article.getSmallImageURL())) ? 0 : 1 %>');
		},
		0
	);
</aui:script>