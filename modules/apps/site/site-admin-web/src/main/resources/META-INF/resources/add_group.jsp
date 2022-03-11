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
AddGroupDisplayContext addGroupDisplayContext = (AddGroupDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<clay:container-fluid>
	<liferay-frontend:edit-form
		action="<%= addGroupDisplayContext.getAddGroupURL() %>"
		cssClass="add-group-form"
		method="post"
		name="fm"
		onSubmit="event.preventDefault();"
		validateOnBlur="<%= false %>"
	>
		<div class="add-group-content">
			<liferay-frontend:edit-form-body>
				<aui:input autoFocus="<%= true %>" label="name" name="name" required="<%= true %>" />

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
								<div class="alert alert-warning text-justify">
									<liferay-ui:message key="sites-have-required-vocabularies.-you-need-to-create-at-least-one-category-in-all-required-vocabularies-in-order-to-create-a-site" />
								</div>
							</c:otherwise>
						</c:choose>
					</aui:fieldset>
				</c:if>
			</liferay-frontend:edit-form-body>
		</div>

		<div class="add-group-loading align-items-center d-none flex-column justify-content-center">
			<span aria-hidden="true" class="loading-animation mb-4"></span>

			<p class="text-3 text-center text-secondary"><liferay-ui:message key="the-creation-of-the-site-may-take-some-minutes" /></p>
		</div>

		<liferay-frontend:edit-form-footer>
			<clay:button
				cssClass="d-flex"
				id='<%= liferayPortletResponse.getNamespace() + "addButton" %>'
				label="add"
				type="submit"
			/>

			<clay:button
				cssClass="btn-cancel"
				displayType="secondary"
				label="cancel"
			/>
		</liferay-frontend:edit-form-footer>
	</liferay-frontend:edit-form>
</clay:container-fluid>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "addGroup" %>'
	module="js/AddGroup"
/>