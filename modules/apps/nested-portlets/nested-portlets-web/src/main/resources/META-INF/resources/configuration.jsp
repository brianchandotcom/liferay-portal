<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="oldLayoutTemplateId" type="hidden" value="<%= nestedPortletsDisplayContext.getLayoutTemplateId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset
			cssClass="display-style-icon"
		>
			<h4><liferay-ui:message key="layout-template" /></h4>

			<clay:row>

				<%
				String layoutTemplateId = nestedPortletsDisplayContext.getLayoutTemplateId();

				for (LayoutTemplate layoutTemplate : nestedPortletsDisplayContext.getLayoutTemplates()) {
				%>

					<clay:col
						md="3"
						size="6"
						sm="4"
					>
						<div class="radio radio-card radio-top-left">
							<label>
								<aui:input checked="<%= layoutTemplateId.equals(layoutTemplate.getLayoutTemplateId()) %>" label="" name="preferences--layoutTemplateId--" type="radio" value="<%= layoutTemplate.getLayoutTemplateId() %>" />

								<div class="card">
									<div class="aspect-ratio aspect-ratio-bg-cover" style="background-image: url('<%= layoutTemplate.getStaticResourcePath() + HtmlUtil.escapeAttribute(layoutTemplate.getThumbnailPath()) %>');">
									</div>

									<div class="card-body">
										<div class="card-col-field">
											<%= layoutTemplate.getName(locale) %>
										</div>
									</div>
								</div>
							</label>
						</div>
					</clay:col>

				<%
				}
				%>

			</clay:row>
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>