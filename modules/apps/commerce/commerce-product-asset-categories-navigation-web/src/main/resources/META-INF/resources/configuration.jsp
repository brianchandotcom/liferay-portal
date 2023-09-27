<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		<div class="container-fluid container-fluid-max-xl">
			<div class="sheet">
				<div class="panel-group panel-group-flush">
					<aui:fieldset>
						<div class="display-template">
							<liferay-template:template-selector
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

									<aui:option label="<%= HtmlUtil.escape(curAssetVocabulary.getTitle(locale)) %>" selected="<%= curAssetVocabulary.getVocabularyId() == assetVocabularyId %>" value="<%= curAssetVocabulary.getVocabularyId() %>" />

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
				</div>
			</div>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="submitButton" type="submit" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,event-input">
	A.one('#<portlet:namespace />submitButton').on('click', () => {
		if (
			A.one('#<portlet:namespace />preferencesUseRootCategory').attr(
				'checked'
			)
		) {
			var preferencesRootAssetCategoryId = A.one(
				'#<portlet:namespace />preferencesRootAssetCategoryId'
			);

			var form = window.document['<portlet:namespace />fm'];

			var assetCategoryIdsKey = Object.keys(form.elements).filter((input) =>
				input.includes('assetCategoriesSelectorCategoryId')
			);

			for (let i = 0; i < assetCategoryIdsKey.length; i++) {
				let assetCategoryId = assetCategoryIdsKey[i];

				if (form.elements[assetCategoryId].value) {
					preferencesRootAssetCategoryId.val(
						form.elements[assetCategoryId].value
					);
					break;
				}
			}
		}

		submitForm(A.one('#<portlet:namespace />fm'));
	});

	A.one('#<portlet:namespace />preferencesUseRootCategory').on(
		'change',
		function () {
			if (this.attr('checked')) {
				A.one('#<portlet:namespace />assetVocabularyContainer').addClass(
					'hide'
				);
				A.one(
					'#<portlet:namespace />rootAssetCategoryContainer'
				).removeClass('hide');
			}
			else {
				A.one('#<portlet:namespace />rootAssetCategoryContainer').addClass(
					'hide'
				);
				A.one('#<portlet:namespace />assetVocabularyContainer').removeClass(
					'hide'
				);
			}
		}
	);

	A.one('#<portlet:namespace />preferencesUseCategoryFromRequest').on(
		'change',
		function () {
			if (this.attr('checked')) {
				A.one(
					'#<portlet:namespace />rootAssetCategoryIdInputContainer'
				).addClass('hide');
			}
			else {
				A.one(
					'#<portlet:namespace />rootAssetCategoryIdInputContainer'
				).removeClass('hide');
			}
		}
	);
</aui:script>