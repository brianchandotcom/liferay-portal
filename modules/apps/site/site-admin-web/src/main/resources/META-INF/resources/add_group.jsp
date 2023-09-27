<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AddGroupDisplayContext addGroupDisplayContext = (AddGroupDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="add-group-alert-container"></div>

<clay:container-fluid
	cssClass="add-group-container"
>
	<liferay-frontend:edit-form
		action="<%= addGroupDisplayContext.getAddGroupURL() %>"
		cssClass="add-group-form pt-0"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
		validateOnBlur="<%= false %>"
	>
		<div class="add-group-content">
			<div class="lfr-form-content">
				<aui:input label="name" name="name" required="<%= true %>" />

				<c:if test="<%= addGroupDisplayContext.isShowLayoutSetVisibilityPrivateCheckbox() %>">
					<aui:input label="create-default-pages-as-private-available-only-to-members-if-unchecked-they-will-be-public-available-to-anyone" name="layoutSetVisibilityPrivate" type="checkbox" />
				</c:if>

				<c:if test="<%= addGroupDisplayContext.hasRequiredVocabularies() %>">
					<aui:fieldset cssClass="mb-4">
						<div class="h3 sheet-subtitle"><liferay-ui:message key="categorization" /></div>

						<c:choose>
							<c:when test="<%= addGroupDisplayContext.isShowCategorization() %>">
								<liferay-asset:asset-categories-selector
									className="<%= Group.class.getName() %>"
									classPK="<%= 0 %>"
									groupIds="<%= addGroupDisplayContext.getGroupIds() %>"
									showOnlyRequiredVocabularies="<%= true %>"
									visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
								/>
							</c:when>
							<c:otherwise>
								<clay:alert
									displayType="warning"
									message="sites-have-required-vocabularies.-you-need-to-create-at-least-one-category-in-all-required-vocabularies-in-order-to-create-a-site"
								/>
							</c:otherwise>
						</c:choose>
					</aui:fieldset>
				</c:if>
			</div>
		</div>

		<div class="add-group-loading align-items-center d-none flex-column justify-content-center">
			<span aria-hidden="true" class="loading-animation mb-4"></span>

			<p class="text-3 text-center text-secondary"><liferay-ui:message key="the-creation-of-the-site-may-take-some-time-.closing-the-window-will-not-cancel-the-process" /></p>
		</div>

		<liferay-frontend:edit-form-footer>
			<liferay-frontend:edit-form-buttons
				submitLabel="add"
			/>
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</clay:container-fluid>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "addGroup" %>'
	module="js/AddGroup"
/>