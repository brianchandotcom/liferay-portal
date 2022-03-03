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

TranslateDisplayContext translateDisplayContext = (TranslateDisplayContext)request.getAttribute(TranslateDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(translateDisplayContext.getTitle());
%>

<div class="translation">
	<aui:form action="<%= translateDisplayContext.getUpdateTranslationPortletURL() %>" cssClass="translation-edit" name="translate_fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
		<aui:input name="sourceLanguageId" type="hidden" value="<%= translateDisplayContext.getSourceLanguageId() %>" />
		<aui:input name="targetLanguageId" type="hidden" value="<%= translateDisplayContext.getTargetLanguageId() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

		<nav class="component-tbar subnav-tbar-light tbar">
			<clay:container-fluid>
				<ul class="tbar-nav">
					<li class="tbar-item tbar-item-expand"></li>
					<li class="tbar-item">
						<div class="tbar-section text-right">
							<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

							<aui:button cssClass="btn-sm mr-3" disabled="<%= translateDisplayContext.isSaveButtonDisabled() %>" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= translateDisplayContext.getSaveButtonLabel() %>" />

							<aui:button cssClass="btn-sm" disabled="<%= translateDisplayContext.isPublishButtonDisabled() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= translateDisplayContext.getPublishButtonLabel() %>" />
						</div>
					</li>
				</ul>
			</clay:container-fluid>
		</nav>

		<clay:container-fluid
			cssClass="container-view"
		>
			<div class="sheet translation-edit-body-form">
				<c:choose>
					<c:when test="<%= !translateDisplayContext.hasTranslationPermission() %>">
						<clay:alert
							message="you-do-not-have-permissions-to-translate-to-any-of-the-available-languages"
						/>
					</c:when>
					<c:otherwise>
						<clay:row
							cssClass='<%= translateDisplayContext.isAutoTranslateEnabled() ? "row-autotranslate-title" : StringPool.BLANK %>'
						>
							<clay:col
								md="6"
							>

								<%
								String sourceLanguageIdTitle = translateDisplayContext.getLanguageIdTitle(translateDisplayContext.getSourceLanguageId());
								%>

								<clay:icon
									symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
								/>

								<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

								<hr class="separator" />
							</clay:col>

							<clay:col
								md="6"
							>

								<%
								String targetLanguageIdTitle = translateDisplayContext.getLanguageIdTitle(translateDisplayContext.getTargetLanguageId());
								%>

								<clay:icon
									symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
								/>

								<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

								<hr class="separator" />
							</clay:col>
						</clay:row>

						<%
						for (InfoFieldSetEntry infoFieldSetEntry : translateDisplayContext.getInfoFieldSetEntries()) {
							List<InfoField> infoFields = translateDisplayContext.getInfoFields(infoFieldSetEntry);

							if (ListUtil.isEmpty(infoFields)) {
								continue;
							}

							String infoFieldSetLabel = translateDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);
						%>

							<c:if test="<%= Validator.isNotNull(infoFieldSetLabel) %>">
								<clay:row
									cssClass='<%= translateDisplayContext.isAutoTranslateEnabled() ? "row-autotranslate-title" : StringPool.BLANK %>'
								>
									<clay:col
										md="6"
									>
										<div class="fieldset-title">
											<%= infoFieldSetLabel %>
										</div>
									</clay:col>

									<clay:col
										md="6"
									>
										<div class="fieldset-title">
											<%= infoFieldSetLabel %>
										</div>
									</clay:col>
								</clay:row>
							</c:if>

							<%
							for (InfoField<TextInfoFieldType> infoField : infoFields) {
								boolean html = translateDisplayContext.getBooleanValue(infoField, TextInfoFieldType.HTML);
								String label = translateDisplayContext.getInfoFieldLabel(infoField);
								boolean multiline = translateDisplayContext.getBooleanValue(infoField, TextInfoFieldType.MULTILINE);
								String name = infoField.getName();

								String sourceContentDir = LanguageUtil.get(translateDisplayContext.getSourceLocale(), "lang.dir");

								List<String> sourceStringValues = translateDisplayContext.getSourceStringValues(infoField, translateDisplayContext.getSourceLocale());

								Iterator<String> sourceStringValuesIterator = sourceStringValues.iterator();

								List<String> targetStringValues = translateDisplayContext.getTargetStringValues(infoField, translateDisplayContext.getTargetLocale());

								Iterator<String> targetStringValuesIterator = targetStringValues.iterator();
							%>

								<c:choose>
									<c:when test="<%= translateDisplayContext.isAutoTranslateEnabled() %>">

										<%
										while (sourceStringValuesIterator.hasNext() && targetStringValuesIterator.hasNext()) {
											String sourceContent = sourceStringValuesIterator.next();
											String targetContent = targetStringValuesIterator.next();
										%>

											<clay:row>
												<clay:content-col
													cssClass="col-autotranslate-content"
													expand="<%= true %>"
												>
													<%@ include file="/translate_field.jspf" %>
												</clay:content-col>

												<clay:content-col
													cssClass="col-autotranslate-button"
												>
													<clay:button
														disabled="<%= true %>"
														displayType="secondary"
														monospaced="<%= true %>"
													>
														<clay:icon
															symbol="automatic-translate"
														/>

														<span class="sr-only"><liferay-ui:message key="location" /></span>
													</clay:button>
												</clay:content-col>
											</clay:row>

										<%
										}
										%>

									</c:when>
									<c:otherwise>

										<%
										while (sourceStringValuesIterator.hasNext() && targetStringValuesIterator.hasNext()) {
											String sourceContent = sourceStringValuesIterator.next();
											String targetContent = targetStringValuesIterator.next();
										%>

											<%@ include file="/translate_field.jspf" %>

										<%
										}
										%>

									</c:otherwise>
								</c:choose>

						<%
							}
						}
						%>

					</c:otherwise>
				</c:choose>
			</div>
		</clay:container-fluid>
	</aui:form>

	<c:if test="<%= translateDisplayContext.hasTranslationPermission() %>">
		<react:component
			module="js/translate/Translate"
			props="<%= translateDisplayContext.getInfoFieldSetEntriesData() %>"
		/>
	</c:if>
</div>