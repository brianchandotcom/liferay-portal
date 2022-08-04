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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	redirect = currentURL;
}

SegmentsCompanyConfigurationDisplayContext segmentsCompanyConfigurationDisplayContext = (SegmentsCompanyConfigurationDisplayContext)request.getAttribute(SegmentsCompanyConfigurationDisplayContext.class.getName());
%>

<liferay-util:html-top
	outputKey="configuration_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/configuration.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<clay:sheet
	cssClass="segments-configuration"
	size="full"
>
	<h2>
		<liferay-ui:message key="segments-service-company-configuration-name" />

		<c:if test="<%= segmentsCompanyConfigurationDisplayContext.isSegmentsCompanyConfigurationDefined() %>">
			<liferay-ui:icon-menu
				cssClass="float-right"
				direction="right"
				markupView="lexicon"
				showWhenSingleIcon="<%= true %>"
			>
				<liferay-ui:icon
					message="reset-default-values"
					method="post"
					url="<%= segmentsCompanyConfigurationDisplayContext.getDeleteConfigurationActionURL() %>"
				/>

				<liferay-ui:icon
					message="export"
					method="get"
					url="<%= segmentsCompanyConfigurationDisplayContext.getExportConfigurationActionURL() %>"
				/>
			</liferay-ui:icon-menu>
		</c:if>
	</h2>

	<aui:form action="<%= segmentsCompanyConfigurationDisplayContext.getBindConfigurationActionURL() %>" cssClass="mt-3" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<c:if test="<%= !segmentsCompanyConfigurationDisplayContext.isSegmentationEnabled() %>">
			<clay:alert
				cssClass="c-my-4"
				defaultTitleDisabled="<%= true %>"
				displayType="warning"
			>
				<strong><%= LanguageUtil.get(request, "segmentation-is-disabled-in-system-settings") %></strong>

				<%
				String segmentsConfigurationURL = segmentsCompanyConfigurationDisplayContext.getSegmentsCompanyConfigurationURL();
				%>

				<c:choose>
					<c:when test="<%= segmentsConfigurationURL != null %>">
						<clay:link
							href="<%= segmentsConfigurationURL %>"
							label='<%= LanguageUtil.get(request, "to-enable,-go-to-system-settings") %>'
						/>
					</c:when>
					<c:otherwise>
						<span><liferay-ui:message key="contact-your-system-administrator-to-enable-it" /></span>
					</c:otherwise>
				</c:choose>
			</clay:alert>
		</c:if>

		<c:if test="<%= !segmentsCompanyConfigurationDisplayContext.isRoleSegmentationEnabled() %>">
			<clay:alert
				cssClass="c-my-4"
				defaultTitleDisabled="<%= true %>"
				displayType="warning"
			>
				<strong><%= LanguageUtil.get(request, "assign-roles-by-segment-is-disabled-in-system-settings") %></strong>

				<%
				String segmentsConfigurationURL = segmentsCompanyConfigurationDisplayContext.getSegmentsCompanyConfigurationURL();
				%>

				<c:choose>
					<c:when test="<%= segmentsConfigurationURL != null %>">
						<clay:link
							href="<%= segmentsConfigurationURL %>"
							label='<%=
								LanguageUtil.get(request, "to-enable,-go-to-system-settings")
%>'
						/>
					</c:when>
					<c:otherwise>
				<span><%=
				LanguageUtil.get(
					request, "contact-your-system-administrator-to-enable-it") %></span>
					</c:otherwise>
				</c:choose>
			</clay:alert>
		</c:if>

		<c:if test="<%= !segmentsCompanyConfigurationDisplayContext.isSegmentsCompanyConfigurationDefined() %>">
			<aui:alert closeable="<%= false %>" cssClass="c-mb-4" id="errorAlert" type="info">
				<liferay-ui:message key="this-configuration-is-not-saved-yet.-the-values-shown-are-the-default" />
			</aui:alert>
		</c:if>

		<div class="row <%= (!segmentsCompanyConfigurationDisplayContext.isRoleSegmentationEnabled() || !segmentsCompanyConfigurationDisplayContext.isSegmentationEnabled()) ? "c-mt-5" : "" %>">
			<div class="col-sm-12 form-group">
				<div class="form-group__inner">
					<c:choose>
						<c:when test="<%= segmentsCompanyConfigurationDisplayContext.isSegmentationChecked() || !segmentsCompanyConfigurationDisplayContext.isSegmentationEnabled() %>">
							<input disabled name='<%= liferayPortletResponse.getNamespace() + "segmentationEnabled" %>' type="hidden" value='false' />
						</c:when>
						<c:otherwise>
							<input name="<portlet:namespace />segmentationEnabled" type="hidden" value="false" />
						</c:otherwise>
					</c:choose>

					<clay:checkbox
						checked="<%= segmentsCompanyConfigurationDisplayContext.isSegmentationChecked() %>"
						disabled="<%= !segmentsCompanyConfigurationDisplayContext.isSegmentationEnabled() %>"
						id='<%= liferayPortletResponse.getNamespace() + "segmentationEnabled" %>'
						label='<%= LanguageUtil.get(request, "segmentation-enabled-name") %>'
						name='<%= liferayPortletResponse.getNamespace() + "segmentationEnabled" %>'
					/>

					<div aria-hidden="true" class="form-feedback-group">
						<div class="form-text text-weight-normal"><liferay-ui:message key="segmentation-enabled-description" /></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12 form-group mb-0">
				<div class="form-group__inner">
					<c:choose>
						<c:when test="<%= segmentsCompanyConfigurationDisplayContext.isRoleSegmentationChecked() || !segmentsCompanyConfigurationDisplayContext.isRoleSegmentationEnabled() %>">
							<input disabled name='<%= liferayPortletResponse.getNamespace() + "roleSegmentationEnabled" %>' type="hidden" value='false' />
						</c:when>
						<c:otherwise>
							<input name="<portlet:namespace />roleSegmentationEnabled" type="hidden" value="false" />
						</c:otherwise>
					</c:choose>

					<clay:checkbox
						checked="<%= segmentsCompanyConfigurationDisplayContext.isRoleSegmentationChecked() %>"
						disabled="<%= !segmentsCompanyConfigurationDisplayContext.isRoleSegmentationEnabled() %>"
						id='<%= liferayPortletResponse.getNamespace() + "roleSegmentationEnabled" %>'
						label='<%= LanguageUtil.get(request, "role-segmentation-enabled-name") %>'
						name='<%= liferayPortletResponse.getNamespace() + "roleSegmentationEnabled" %>'
					/>

					<div aria-hidden="true" class="form-feedback-group">
						<div class="form-text text-weight-normal">
							<liferay-ui:message key="role-segmentation-enabled-description" />
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="sheet-footer">
			<div class="btn-group-item">
				<c:choose>
					<c:when test="<%= segmentsCompanyConfigurationDisplayContext.isSegmentsCompanyConfigurationDefined() %>">
						<clay:button
							cssClass="submit-btn"
							displayType="primary"
							id='<%= liferayPortletResponse.getNamespace() + "update" %>'
							label='<%= LanguageUtil.get(request, "update") %>'
							name='<%= liferayPortletResponse.getNamespace() + "update" %>'
							type="submit"
						/>
					</c:when>
					<c:otherwise>
						<clay:button
							cssClass="submit-btn"
							displayType="primary"
							id='<%= liferayPortletResponse.getNamespace() + "save" %>'
							label='<%= LanguageUtil.get(request, "save") %>'
							name='<%= liferayPortletResponse.getNamespace() + "save" %>'
							type="submit"
						/>
					</c:otherwise>
				</c:choose>

				<clay:link
					displayType="secondary"
					href="<%= redirect %>"
					id='<%= liferayPortletResponse.getNamespace() + "cancel" %>'
					label='<%= LanguageUtil.get(request, "cancel") %>'
					type="button"
				/>
			</div>
		</div>
	</aui:form>
</clay:sheet>

<liferay-frontend:component
	module="js/ConfigurationFormEventHandler"
/>