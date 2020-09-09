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
CommerceApplicationAdminDisplayContext commerceApplicationAdminDisplayContext = (CommerceApplicationAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceApplicationModel commerceApplicationModel = commerceApplicationAdminDisplayContext.getCommerceApplicationModel();

renderResponse.setTitle(LanguageUtil.get(request, "applications"));
%>

<portlet:actionURL name="editCommerceApplicationModel" var="editCommerceApplicationModelActionURL" />

<div class="container-fluid-1280 entry-body">
	<aui:form action="<%= editCommerceApplicationModelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceApplicationModel == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
		<aui:input name="commerceApplicationBrandId" type="hidden" value="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationBrandId() %>" />
		<aui:input name="commerceApplicationModelId" type="hidden" value="<%= commerceApplicationAdminDisplayContext.getCommerceApplicationModelId() %>" />

		<aui:model-context bean="<%= commerceApplicationModel %>" model="<%= CommerceApplicationModel.class %>" />

		<div class="lfr-form-content">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input autoFocus="<%= true %>" name="name" />

					<aui:input name="year" />
				</aui:fieldset>

				<aui:fieldset>
					<aui:button-row>
						<aui:button cssClass="btn-lg" type="submit" value="save" />

						<aui:button cssClass="btn-lg" href="<%= backURL %>" type="cancel" />
					</aui:button-row>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</aui:form>
</div>