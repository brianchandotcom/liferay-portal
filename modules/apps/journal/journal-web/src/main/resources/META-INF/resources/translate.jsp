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

JournalTranslateDisplayContext journalTranslateDisplayContext = new JournalTranslateDisplayContext(liferayPortletRequest, liferayPortletResponse);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(journalTranslateDisplayContext.getTitle());
%>

<aui:form action="<%= journalTranslateDisplayContext.getUpdateTranslationPortletURL() %>" cssClass="translate-article" name="translate_fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="sourceLanguageId" type="hidden" value="<%= journalTranslateDisplayContext.getSourceLanguageId() %>" />
	<aui:input name="targetLanguageId" type="hidden" value="<%= journalTranslateDisplayContext.getTargetLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

	<nav class="component-tbar subnav-tbar-light tbar">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<c:if test="<%= journalTranslateDisplayContext.hasTranslationPermission() %>">
						<div class="tbar-section text-left">
							<react:component
								module="js/translate/TranslateLanguagesSelector"
								props="<%= journalTranslateDisplayContext.getTranslateLanguagesSelectorData() %>"
							/>
						</div>
					</c:if>
				</li>
				<li class="tbar-item">
					<div class="metadata-type-button-row tbar-section text-right">
						<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<aui:button cssClass="btn-sm mr-3" disabled="<%= journalTranslateDisplayContext.isSaveButtonDisabled() %>" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= journalTranslateDisplayContext.getSaveButtonLabel() %>" />

						<aui:button cssClass="btn-sm" disabled="<%= journalTranslateDisplayContext.isPublishButtonDisabled() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= journalTranslateDisplayContext.getPublishButtonLabel() %>" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet translate-body-form">
			<c:choose>
				<c:when test="<%= !journalTranslateDisplayContext.hasTranslationPermission() %>">
					<clay:alert
						message="you-do-not-have-permissions-to-translate-to-any-of-the-available-languages"
					/>
				</c:when>
				<c:otherwise>
					<clay:row>
						<clay:col
							md="6"
						>

							<%
							String sourceLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getSourceLanguageId());
							%>

							<clay:icon
								symbol="<%= StringUtil.toLowerCase(sourceLanguageIdTitle) %>"
							/>

							<span class="ml-1"> <%= sourceLanguageIdTitle %> </span>

							<div class="separator"><!-- --></div>
						</clay:col>

						<clay:col
							md="6"
						>

							<%
							String targetLanguageIdTitle = journalTranslateDisplayContext.getLanguageIdTitle(journalTranslateDisplayContext.getTargetLanguageId());
							%>

							<clay:icon
								symbol="<%= StringUtil.toLowerCase(targetLanguageIdTitle) %>"
							/>

							<span class="ml-1"> <%= targetLanguageIdTitle %> </span>

							<div class="separator"><!-- --></div>
						</clay:col>
					</clay:row>

					<%
					for (InfoFieldSetEntry infoFieldSetEntry : journalTranslateDisplayContext.getInfoFieldSetEntries()) {
						List<InfoField> infoFields = journalTranslateDisplayContext.getInfoFields(infoFieldSetEntry);

						if (ListUtil.isEmpty(infoFields)) {
							continue;
						}

						String infoFieldSetLabel = journalTranslateDisplayContext.getInfoFieldSetLabel(infoFieldSetEntry, locale);

						if (Validator.isNotNull(infoFieldSetLabel)) {
					%>

							<clay:row>
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

						<%
						}

						for (InfoField<TextInfoFieldType> infoField : infoFields) {
							boolean html = journalTranslateDisplayContext.getBooleanValue(infoField, TextInfoFieldType.HTML);
							String label = journalTranslateDisplayContext.getInfoFieldLabel(infoField);
							boolean multiline = journalTranslateDisplayContext.getBooleanValue(infoField, TextInfoFieldType.MULTILINE);
						%>

							<clay:row>
								<clay:col
									md="6"
								>

									<%
									String sourceContent = journalTranslateDisplayContext.getSourceStringValue(infoField, journalTranslateDisplayContext.getSourceLocale());
									String sourceContentDir = LanguageUtil.get(journalTranslateDisplayContext.getSourceLocale(), "lang.dir");
									%>

									<c:choose>
										<c:when test="<%= html %>">
											<label class="control-label">
												<%= label %>
											</label>

											<div class="translate-editor-preview" dir="<%= sourceContentDir %>">
												<%= sourceContent %>
											</div>
										</c:when>
										<c:otherwise>
											<aui:input dir="<%= sourceContentDir %>" label="<%= label %>" name="<%= label %>" readonly="true" tabIndex="-1" type='<%= multiline ? "textarea" : "text" %>' value="<%= sourceContent %>" />
										</c:otherwise>
									</c:choose>
								</clay:col>

								<clay:col
									md="6"
								>

									<%
									String id = "infoField--" + infoField.getName() + "--";
									String targetContent = journalTranslateDisplayContext.getTargetStringValue(infoField, journalTranslateDisplayContext.getTargetLocale());
									%>

									<c:choose>
										<c:when test="<%= html %>">
											<liferay-editor:editor
												configKey="translateEditor"
												contents="<%= targetContent %>"
												contentsLanguageId="<%= journalTranslateDisplayContext.getTargetLanguageId() %>"
												name="<%= id %>"
												onChangeMethod="onInputChange"
												placeholder="<%= label %>"
												toolbarSet="simple"
											/>
										</c:when>
										<c:otherwise>
											<aui:input dir='<%= LanguageUtil.get(journalTranslateDisplayContext.getTargetLocale(), "lang.dir") %>' label="<%= label %>" name="<%= id %>" onChange='<%= liferayPortletResponse.getNamespace() + "onInputChange();" %>' type='<%= multiline ? "textarea" : "text" %>' value="<%= targetContent %>" />
										</c:otherwise>
									</c:choose>
								</clay:col>
							</clay:row>

					<%
						}
					}
					%>

				</c:otherwise>
			</c:choose>
		</div>
	</clay:container-fluid>
</aui:form>

<script>
	var saveDraftBtn = document.getElementById('<portlet:namespace />saveDraftBtn');

	saveDraftBtn.addEventListener('click', function () {
		var workflowActionInput = document.getElementById(
			'<portlet:namespace />workflowAction'
		);

		workflowActionInput.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';
	});

	function <portlet:namespace />onInputChange(value) {
		var translateLanguageComponent = Liferay.component(
			'<portlet:namespace />TranslateLanguagesSelector'
		);

		translateLanguageComponent.onFormChange();
	}
</script>