<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
long assetVocabularyId = 0;

if (assetVocabulary != null) {
	assetVocabularyId = assetVocabulary.getVocabularyId();
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="display-template">
						<liferay-ddm:template-selector
							className="<%= CPAssetCategoriesNavigationPortlet.class.getName() %>"
							displayStyle="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyle() %>"
							displayStyleGroupId="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyleGroupId() %>"
							refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
							showEmptyOption="<%= true %>"
						/>
					</div>

					<%
					boolean useRootCategory = cpAssetCategoriesNavigationDisplayContext.useRootCategory();
					%>

					<aui:input id="preferencesUseRootCategory" label="use-root-category" name="preferences--useRootCategory--" type="toggle-switch" value="<%= useRootCategory %>" />

					<%
					String assetVocabularyContainerCssClass = StringPool.BLANK;
					String rootAssetCategoryContainerCssClass = "hide";

					if (useRootCategory) {
						assetVocabularyContainerCssClass += "hide";
						rootAssetCategoryContainerCssClass = StringPool.BLANK;
					}
					%>

					<div class="<%= assetVocabularyContainerCssClass %>" id="<portlet:namespace />assetVocabularyContainer">
						<aui:select label="vocabulary" name="preferences--assetVocabularyId--" showEmptyOption="<%= true %>">

							<%
							for (AssetVocabulary curAssetVocabulary : cpAssetCategoriesNavigationDisplayContext.getAssetVocabularies()) {
							%>

								<aui:option label="<%= curAssetVocabulary.getTitle(locale) %>" selected="<%= curAssetVocabulary.getVocabularyId() == assetVocabularyId %>" value="<%= curAssetVocabulary.getVocabularyId() %>" />

							<%
							}
							%>

						</aui:select>
					</div>

					<div class="<%= rootAssetCategoryContainerCssClass %>" id="<portlet:namespace />rootAssetCategoryContainer">

						<%
						boolean useCategoryFromRequest = cpAssetCategoriesNavigationDisplayContext.useCategoryFromRequest();
						%>

						<aui:input id="preferencesUseCategoryFromRequest" label="use-category-from-request" name="preferences--useCategoryFromRequest--" type="toggle-switch" value="<%= useCategoryFromRequest %>" />

						<%
						String rootAssetCategoryIdInputContainerCssClass = StringPool.BLANK;

						if (useCategoryFromRequest) {
							rootAssetCategoryIdInputContainerCssClass += "hide";
						}
						%>

						<div class="<%= rootAssetCategoryIdInputContainerCssClass %>" id="<portlet:namespace />rootAssetCategoryIdInputContainer">
							<aui:input id="preferencesRootAssetCategoryId" name="preferences--rootAssetCategoryId--" type="hidden" />

							<liferay-asset:asset-categories-selector
								categoryIds="<%= cpAssetCategoriesNavigationDisplayContext.getRootAssetCategoryId() %>"
								hiddenInput="assetCategoriesSelectorCategoryId"
								singleSelect="<%= true %>"
							/>
						</div>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,event-input">
	A.one('#<portlet:namespace />submitButton').on(
		'click',
		function() {
			if (A.one('#<portlet:namespace />preferencesUseRootCategory').attr('checked')) {
				var preferencesRootAssetCategoryId = A.one('#<portlet:namespace />preferencesRootAssetCategoryId');
				var assetCategoriesSelectorCategoryId = A.one('#<portlet:namespace />assetCategoriesSelectorCategoryId');

				preferencesRootAssetCategoryId.val(assetCategoriesSelectorCategoryId.val());
			}

			submitForm(A.one('#<portlet:namespace />fm'));
		}
	);

	A.one('#<portlet:namespace />preferencesUseRootCategory').on(
		'change',
		function() {
			if (this.attr('checked')) {
				A.one('#<portlet:namespace />assetVocabularyContainer').addClass('hide');
				A.one('#<portlet:namespace />rootAssetCategoryContainer').removeClass('hide');
			}
			else {
				A.one('#<portlet:namespace />rootAssetCategoryContainer').addClass('hide');
				A.one('#<portlet:namespace />assetVocabularyContainer').removeClass('hide');
			}
		}
	);

	A.one('#<portlet:namespace />preferencesUseCategoryFromRequest').on(
		'change',
		function() {
			if (this.attr('checked')) {
				A.one('#<portlet:namespace />rootAssetCategoryIdInputContainer').addClass('hide');
			}
			else {
				A.one('#<portlet:namespace />rootAssetCategoryIdInputContainer').removeClass('hide');
			}
		}
	);
</aui:script>