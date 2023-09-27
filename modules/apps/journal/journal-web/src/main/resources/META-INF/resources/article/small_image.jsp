<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

int smallImageSource = journalEditArticleDisplayContext.getSmallImageSource();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="featured-image"
/>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<%
JournalFileUploadsConfiguration journalFileUploadsConfiguration = (JournalFileUploadsConfiguration)request.getAttribute(JournalFileUploadsConfiguration.class.getName());
%>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">
	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalFileUploadsConfiguration.imageExtensions(), ", ")) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>">
	<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(journalFileUploadsConfiguration.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<c:choose>
	<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-180855") %>'>
		<div>
			<react:component
				module="js/SmallImage"
				props="<%= journalEditArticleDisplayContext.getProps() %>"
			/>
		</div>
	</c:when>
	<c:otherwise>
		<aui:select ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageSource" value="<%= smallImageSource %>" wrapperCssClass="mb-3">
			<aui:option label="no-image" value="<%= JournalArticleConstants.SMALL_IMAGE_SOURCE_NONE %>" />
			<aui:option label="from-url" value="<%= JournalArticleConstants.SMALL_IMAGE_SOURCE_URL %>" />
			<aui:option label="from-your-computer" value="<%= JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER %>" />
		</aui:select>

		<div class="<%= (smallImageSource == JournalArticleConstants.SMALL_IMAGE_SOURCE_URL) ? "" : "hide" %>" id="<portlet:namespace />smallImageURLContainer">
			<c:if test="<%= (article != null) && Validator.isNotNull(article.getArticleImageURL(themeDisplay)) %>">
				<div class="aspect-ratio aspect-ratio-16-to-9">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="aspect-ratio-item-fluid" src="<%= HtmlUtil.escapeAttribute(article.getArticleImageURL(themeDisplay)) %>" />
				</div>
			</c:if>

			<aui:input ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageURL" title="small-image-url" wrapperCssClass="mb-3" />
		</div>

		<div class="<%= (smallImageSource == JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER) ? "" : "hide" %>" id="<portlet:namespace />smallFileContainer">
			<div>

				<%
				ThemeDisplay finalThemeDisplay = themeDisplay;
				%>

				<react:component
					module="js/ImageInput.es"
					props='<%=
						HashMapBuilder.<String, Object>put(
							"name",
							() -> {
								if (!journalEditArticleDisplayContext.isChangeStructure()) {
									return "smallFile";
								}

								return StringPool.BLANK;
							}
						).put(
							"previewURL",
							() -> {
								if ((article != null) && Validator.isNotNull(article.getArticleImageURL(finalThemeDisplay))) {
									return article.getArticleImageURL(finalThemeDisplay);
								}

								return StringPool.BLANK;
							}
						).build()
					%>'
				/>
			</div>
		</div>

		<aui:script>
			Liferay.Util.toggleSelectBox(
				'<portlet:namespace />smallImageSource',
				'<%= JournalArticleConstants.SMALL_IMAGE_SOURCE_URL %>',
				'<portlet:namespace />smallImageURLContainer'
			);
			Liferay.Util.toggleSelectBox(
				'<portlet:namespace />smallImageSource',
				'<%= JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER %>',
				'<portlet:namespace />smallFileContainer'
			);
		</aui:script>
	</c:otherwise>
</c:choose>