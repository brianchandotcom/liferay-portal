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

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}

SegmentsCompanyConfigurationDisplayContext segmentsCompanyConfigurationDisplayContext = (SegmentsCompanyConfigurationDisplayContext)request.getAttribute(SegmentsCompanyConfigurationDisplayContext.class.getName());
%>

<clay:sheet
	size="full"
>
	<h2>
		<liferay-ui:message key="segments-service-company-configuration-name" />
	</h2>

	<aui:form action="<%= segmentsCompanyConfigurationDisplayContext.getBindConfigurationActionURL() %>" method="post" name="fm">
		<c:if test="<%= !segmentsCompanyConfigurationDisplayContext.isSegmentsCompanyConfigurationDefined() %>">
			<aui:alert closeable="<%= false %>" id="errorAlert" type="info">
				<liferay-ui:message key="this-configuration-is-not-saved-yet.-the-values-shown-are-the-default" />
			</aui:alert>
		</c:if>

		<div class="form-group">
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
				className="mb-3"
				disabled="<%= !segmentsCompanyConfigurationDisplayContext.isSegmentationEnabled() %>"
				id='<%= liferayPortletResponse.getNamespace() + "segmentationEnabled" %>'
				label='<%= LanguageUtil.get(request, "segmentation-enabled-name") %>'
				name='<%= liferayPortletResponse.getNamespace() + "segmentationEnabled" %>'
			/>

			<div aria-hidden="true" class="form-feedback-group">
				<div class="form-text text-weight-normal"><liferay-ui:message key="segmentation-enabled-description" /></div>
			</div>
		</div>

		<div class="form-group">
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

		<div class="sheet-footer">
			<div class="btn-group-item">
				<div class="btn-group-item">
					<c:choose>
						<c:when test="<%= true %>">
							<clay:button
								displayType="primary"
								id='<%= liferayPortletResponse.getNamespace() + "update" %>'
								label='<%= LanguageUtil.get(request, "update") %>'
								name='<%= liferayPortletResponse.getNamespace() + "update" %>'
								type="submit"
							/>
						</c:when>
						<c:otherwise>
							<clay:button
								displayType="primary"
								id='<%= liferayPortletResponse.getNamespace() + "save" %>'
								label='<%= LanguageUtil.get(request, "save") %>'
								name='<%= liferayPortletResponse.getNamespace() + "save" %>'
								type="submit"
							/>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="btn-group-item">
					<clay:link
						displayType="secondary"
						href="<%= redirect %>"
						id='<%= liferayPortletResponse.getNamespace() + "cancel" %>'
						label='<%= LanguageUtil.get(request, "cancel") %>'
						type="button"
					/>
				</div>
			</div>
		</div>
	</aui:form>
</clay:sheet>

<liferay-frontend:component
	module="js/ConfigurationFormEventHandler"
/>