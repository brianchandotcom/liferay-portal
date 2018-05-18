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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();

String layoutUuid = cpDefinitionsDisplayContext.getLayoutUuid();

String layoutBreadcrumb = StringPool.BLANK;

if (Validator.isNotNull(layoutUuid)) {
	Layout selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), false);

	if (selLayout == null) {
		selLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layoutUuid, themeDisplay.getSiteGroupId(), true);
	}

	if (selLayout != null) {
		layoutBreadcrumb = cpDefinitionsDisplayContext.getLayoutBreadcrumb(selLayout);
	}
}
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionDisplayPageActionURL" />

<aui:form action="<%= editProductDefinitionDisplayPageActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateCPDisplayLayout" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= layoutUuid %>" />

			<aui:field-wrapper helpMessage="product-display-page-help" label="product-display-page">
				<p class="text-default">
					<span class="<%= Validator.isNull(layoutBreadcrumb) ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />displayPageItemRemove" role="button">
						<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
					</span>
					<span id="<portlet:namespace />displayPageNameInput">
						<c:choose>
							<c:when test="<%= Validator.isNull(layoutBreadcrumb) %>">
								<span class="text-muted"><liferay-ui:message key="none" /></span>
							</c:when>
							<c:otherwise>
								<%= layoutBreadcrumb %>
							</c:otherwise>
						</c:choose>
					</span>
				</p>
			</aui:field-wrapper>

			<aui:button-row>
				<aui:button name="chooseDisplayPage" value="choose" />
			</aui:button-row>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var displayPageItemContainer = $('#<portlet:namespace />displayPageItemContainer');
	var displayPageItemRemove = $('#<portlet:namespace />displayPageItemRemove');
	var displayPageNameInput = $('#<portlet:namespace />displayPageNameInput');
	var pagesContainerInput = $('#<portlet:namespace />pagesContainerInput');

	$('#<portlet:namespace />chooseDisplayPage').on(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'selectDisplayPage',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								pagesContainerInput.val(selectedItem.id);

								displayPageNameInput.html(selectedItem.name);

								displayPageItemRemove.removeClass('hide');
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-product-display-page" />',
					url: '<%= cpDefinitionsDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	displayPageItemRemove.on(
		'click',
		function(event) {
			displayPageNameInput.html('<liferay-ui:message key="none" />');

			pagesContainerInput.val('');

			displayPageItemRemove.addClass('hide');
		}
	);
</aui:script>