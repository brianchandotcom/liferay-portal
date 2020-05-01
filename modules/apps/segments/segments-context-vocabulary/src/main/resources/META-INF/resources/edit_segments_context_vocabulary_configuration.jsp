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
SegmentsContextVocabularyConfigurationDisplayContext segmentsContextVocabularyConfigurationDisplayContext = (SegmentsContextVocabularyConfigurationDisplayContext)renderRequest.getAttribute(SegmentsContextVocabularyWebKeys.SEGMENTS_CONTEXT_VOCABULARY_CONFIGURATION_DISPLAY_CONTEXT);

segmentsContextVocabularyConfigurationDisplayContext.addPortletBreadcrumbEntries();
%>

<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>">

	<%
	ConfigurationModelListenerException cmle = (ConfigurationModelListenerException)errorException;
	%>

	<liferay-ui:message key="<%= cmle.causeMessage %>" localizeKey="<%= false %>" />
</liferay-ui:error>

<div class="container-fluid container-fluid-max-xl">
	<div class="col-12">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</div>
</div>

<div class="container-fluid container-fluid-max-xl">
	<clay:row>
		<div class="col-md-3">
			<nav class="menubar menubar-transparent menubar-vertical-expand-md">
				<div class="collapse menubar-collapse" id="<%= 12 %>">
					<ul class="nav nav-nested">
						<li class="nav-item">
							<a class="nav-link text-uppercase">
								<liferay-ui:message key='<%= "scope.system" %>' />
							</a>

							<div>
								<ul class="nav nav-stacked">
									<li>
										<aui:a cssClass="nav-link" href="<%= String.valueOf(segmentsContextVocabularyConfigurationDisplayContext.getRedirect()) %>"><liferay-ui:message key="segments-context-vocabulary-configuration-name" /></aui:a>
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</nav>
		</div>

		<div class="col-md-9">
			<div class="sheet sheet-lg">
				<aui:form action="<%= segmentsContextVocabularyConfigurationDisplayContext.getActionURL() %>" method="post" name="fm">
					<aui:input name="factoryPid" type="hidden" value="<%= segmentsContextVocabularyConfigurationDisplayContext.getFactoryPid() %>" />
					<aui:input name="pid" type="hidden" value="<%= segmentsContextVocabularyConfigurationDisplayContext.getPid() %>" />

					<h2>
						<%= HtmlUtil.escape(segmentsContextVocabularyConfigurationDisplayContext.getTitle()) %>
					</h2>

					<c:if test="<%= Validator.isBlank(segmentsContextVocabularyConfigurationDisplayContext.getPid()) %>">
						<aui:alert closeable="<%= false %>" id="errorAlert" type="info">
							<liferay-ui:message key="this-configuration-is-not-saved-yet" />
						</aui:alert>
					</c:if>

					<%
					String description = segmentsContextVocabularyConfigurationDisplayContext.getDescription();
					%>

					<c:if test="<%= !Validator.isBlank(description) %>">
						<p class="text-default">
							<strong><%= description %></strong>
						</p>
					</c:if>

					<div class="form-group">
						<aui:select inlineField="<%= true %>" label="segments-context-vocabulary-configuration-entity-field-name" name="entityField" required="<%= true %>" wrapperCssClass="m-0">

							<%
							for (ConfigurationFieldOptionsProvider.Option option : segmentsContextVocabularyConfigurationDisplayContext.getEntityFieldOptions()) {
							%>

								<aui:option disabled="<%= segmentsContextVocabularyConfigurationDisplayContext.isDisabled(option.getValue()) %>" label="<%= option.getLabel(locale) %>" selected="<%= Objects.equals(segmentsContextVocabularyConfigurationDisplayContext.getEntityField(), option.getValue()) %>" value="<%= option.getValue() %>" />

							<%
							}
							%>

						</aui:select>

						<span class="form-text <%= segmentsContextVocabularyConfigurationDisplayContext.isDuplicated()? "text-danger" : "" %>">
							<c:if test="<%= segmentsContextVocabularyConfigurationDisplayContext.isDuplicated() %>">
								<aui:icon image="exclamation-full" markupView="lexicon" />
							</c:if>

						<%= segmentsContextVocabularyConfigurationDisplayContext.getEntityFieldHelpMessage() %></span>
					</div>

					<div class="form-group">
						<aui:select inlineField="<%= true %>" label="segments-context-vocabulary-configuration-asset-vocabulary-name" name="assetVocabulary" required="<%= true %>" wrapperCssClass="m-0">

							<%
							for (ConfigurationFieldOptionsProvider.Option option : segmentsContextVocabularyConfigurationDisplayContext.getAssetVocabularyOptions()) {
							%>

								<aui:option label="<%= option.getLabel(locale) %>" selected="<%= Objects.equals(segmentsContextVocabularyConfigurationDisplayContext.getAssetVocabulary(), option.getValue()) %>" value="<%= option.getValue() %>" />

							<%
							}
							%>

						</aui:select>

						<span class="form-text"><liferay-ui:message key="segments-context-vocabulary-configuration-asset-vocabulary-description" /></span>
					</div>

					<aui:button-row>
						<c:choose>
							<c:when test="<%= !Validator.isBlank(segmentsContextVocabularyConfigurationDisplayContext.getPid()) %>">
								<aui:button name="update" type="submit" value="update" />
							</c:when>
							<c:otherwise>
								<aui:button name="save" type="submit" value="save" />
							</c:otherwise>
						</c:choose>

						<aui:button href="<%= String.valueOf(segmentsContextVocabularyConfigurationDisplayContext.getRedirect()) %>" name="cancel" type="cancel" />
					</aui:button-row>
				</aui:form>
			</div>
		</div>
	</clay:row>
</div>