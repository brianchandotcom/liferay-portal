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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");
%>

<liferay-ui:error-marker key="errorSection" value="abstract" />

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="abstract" /></h3>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">

	<%
	String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);
	%>

	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>">

	<%
	long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE);
	%>

	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:fieldset>
	<aui:input label="summary" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="description" />

	<c:if test="<%= Validator.isNull(toLanguageId) %>">
		<div id="<portlet:namespace />smallImageContainer">
			<div class="lfr-journal-small-image-header">
				<aui:input label="use-small-image" name="smallImage" />
			</div>

			<div class="lfr-journal-small-image-content toggler-content-collapsed">
				<aui:row>
					<c:if test="<%= smallImage && (article != null) %>">
						<aui:col width="<%= 50 %>">
							<img alt="<liferay-ui:message key="preview" />" class="lfr-journal-small-image-preview" src="<%= Validator.isNotNull(article.getSmallImageURL()) ? HtmlUtil.escapeAttribute(article.getSmallImageURL()) : themeDisplay.getPathImage() + "/template?img_id=" + article.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(article.getSmallImageId()) %>" />
						</aui:col>
					</c:if>

					<aui:col width="<%= (smallImage && (article != null)) ? 50 : 100 %>">
						<aui:fieldset>
							<aui:input cssClass="lfr-journal-small-image-type" inlineField="<%= true %>" label="small-image-url" name="smallImageType" type="radio" />

							<aui:input cssClass="lfr-journal-small-image-value" inlineField="<%= true %>" label="" name="smallImageURL" title="small-image-url" />
						</aui:fieldset>

						<aui:fieldset>
							<aui:input cssClass="lfr-journal-small-image-type" inlineField="<%= true %>" label="small-image" name="smallImageType" type="radio" />

							<aui:input cssClass="lfr-journal-small-image-value" inlineField="<%= true %>" label="" name="smallFile" type="file" />
						</aui:fieldset>
					</aui:col>
				</aui:row>
			</div>
		</div>

		<aui:script use="aui-toggler">
			var container = A.one('#<portlet:namespace />smallImageContainer');

			var types = container.all('.lfr-journal-small-image-type');
			var values = container.all('.lfr-journal-small-image-value');

			var selectSmallImageType = function(index) {
				types.set('checked', false);

				types.item(index).set('checked', true);

				values.set('disabled', true);

				values.item(index).set('disabled', false);
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

							A.one('#<portlet:namespace />smallImage').set('checked', expanded);

							if (expanded) {
								types.each(
									function(item, index) {
										if (item.get('checked')) {
											values.item(index).set('disabled', false);
										}
									}
								);
							}
							else {
								values.set('disabled', true);
							}
						}
					}
				}
			);

			selectSmallImageType('<%= (article != null) && Validator.isNotNull(article.getSmallImageURL()) ? 0 : 1 %>');
		</aui:script>
	</c:if>
</aui:fieldset>