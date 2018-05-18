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
CPDefinitionLinkDisplayContext cpDefinitionLinkDisplayContext = (CPDefinitionLinkDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionLinkDisplayContext.getCPDefinition();

CPDefinitionLink cpDefinitionLink = cpDefinitionLinkDisplayContext.getCPDefinitionLink();

long cpDefinitionLinkId = cpDefinitionLinkDisplayContext.getCPDefinitionLinkId();

CPDefinition cpDefinition2 = cpDefinitionLink.getCPDefinition2();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "editCPDefinitionLink");

String screenNavigationCategoryKey = cpDefinitionLinkDisplayContext.getScreenNavigationCategoryKey();

PortletURL relatedProductsURL = renderResponse.createRenderURL();

relatedProductsURL.setParameter("mvcRenderCommandName", "editProductDefinition");
relatedProductsURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
relatedProductsURL.setParameter("screenNavigationCategoryKey", screenNavigationCategoryKey);
relatedProductsURL.setParameter("type", String.valueOf(cpDefinitionLink.getType()));

String title = cpDefinition2.getName(languageId);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, cpDefinition.getName(languageId), String.valueOf(cpDefinitionLinkDisplayContext.getEditProductDefinitionURL()), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationCategoryKey), relatedProductsURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);
%>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCPDefinitionLink" var="editCPDefinitionLinkActionURL" />

<aui:form action="<%= editCPDefinitionLinkActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= relatedProductsURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpDefinitionLinkId" type="hidden" value="<%= cpDefinitionLinkId %>" />
	<aui:input name="type" type="hidden" value="<%= cpDefinitionLink.getType() %>" />

	<aui:model-context bean="<%= cpDefinitionLink %>" model="<%= CPDefinitionLink.class %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="priority" />
			</aui:fieldset>

			<c:if test="<%= cpDefinitionLinkDisplayContext.hasCustomAttributesAvailable() %>">
				<aui:fieldset collapsible="<%= true %>" label="custom-attribute">
					<liferay-expando:custom-attribute-list
						className="<%= CPDefinitionLink.class.getName() %>"
						classPK="<%= (cpDefinitionLink != null) ? cpDefinitionLink.getCPDefinitionLinkId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

			<aui:button cssClass="btn-lg" href="<%= relatedProductsURL.toString() %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>