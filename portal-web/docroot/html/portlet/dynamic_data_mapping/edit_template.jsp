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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String closeRedirect = ParamUtil.getString(request, "closeRedirect");
boolean showBackURL = ParamUtil.getBoolean(request, "showBackURL", true);

String portletResource = ParamUtil.getString(request, "portletResource");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMTemplate template = (DDMTemplate)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE);

long templateId = BeanParamUtil.getLong(template, request, "templateId");

long groupId = BeanParamUtil.getLong(template, request, "groupId", scopeGroupId);
long classNameId = BeanParamUtil.getLong(template, request, "classNameId");
long classPK = BeanParamUtil.getLong(template, request, "classPK");

boolean smallImage = BeanParamUtil.getBoolean(template, request, "smallImage");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

if ((structure == null) && (template != null)) {
	structure = DDMTemplateHelperUtil.fetchStructure(template);
}

String type = BeanParamUtil.getString(template, request, "type", DDMTemplateConstants.TEMPLATE_TYPE_FORM);
String mode = BeanParamUtil.getString(template, request, "mode", DDMTemplateConstants.TEMPLATE_MODE_CREATE);
String language = BeanParamUtil.getString(template, request, "language", PropsValues.DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_DEFAULT);
String script = BeanParamUtil.getString(template, request, "script");

if (Validator.isNull(script)) {
	TemplateHandler templateHandler = TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

	if (templateHandler != null) {
		script = templateHandler.getTemplatesHelpContent(language);
	}
	else if ((structure != null) && Validator.equals(structure.getClassName(), JournalArticle.class.getName())) {
		script = ContentUtil.get(PropsUtil.get(PropsKeys.JOURNAL_TEMPLATE_LANGUAGE_CONTENT, new Filter(language)));
	}
	else if (!type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM)) {
		script = ContentUtil.get(PropsUtil.get(PropsKeys.DYNAMIC_DATA_MAPPING_TEMPLATE_LANGUAGE_CONTENT, new Filter(language)));
	}
}

JSONArray scriptJSONArray = null;

if (type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) && Validator.isNotNull(script)) {
	scriptJSONArray = DDMXSDUtil.getJSONArray(script);
}

String structureAvailableFields = ParamUtil.getString(request, "structureAvailableFields");

if (Validator.isNotNull(structureAvailableFields)) {
	scopeAvailableFields = structureAvailableFields;
}
%>

<portlet:actionURL var="editTemplateURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
</portlet:actionURL>

<aui:form action="<%= editTemplateURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveTemplate();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (template != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="portletResourceNamespace" type="hidden" value="<%= portletResourceNamespace %>" />
	<aui:input name="templateId" type="hidden" value="<%= templateId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />
	<aui:input name="structureAvailableFields" type="hidden" value="<%= structureAvailableFields %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />

	<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">

		<%
		String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_EXTENSIONS, ",");
		%>

		<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, StringPool.COMMA) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>">

		<%
		long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.DYNAMIC_DATA_MAPPING_IMAGE_SMALL_MAX_SIZE);
		%>

		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<%
	String title = StringPool.BLANK;

	if ((structure != null) || (template != null)) {
		title = ddmDisplay.getEditTemplateTitle(structure, template, locale);
	}
	else {
		title = ddmDisplay.getEditTemplateTitle(classNameId, locale);
	}
	%>

	<liferay-ui:header
		backURL="<%= ddmDisplay.getEditTemplateBackURL(liferayPortletRequest, liferayPortletResponse, classNameId, classPK, portletResource) %>"
		localizeTitle="<%= false %>"
		showBackURL="<%= showBackURL %>"
		title="<%= title %>"
	/>

	<aui:model-context bean="<%= template %>" model="<%= DDMTemplate.class %>" />

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

		<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="templateDetailsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" defaultState="closed" extended="<%= false %>" id="templateDetailsSectionPanel" persistState="<%= true %>" title="details">
				<c:if test="<%= ddmDisplay.isShowStructureSelector() %>">
					<div class="form-group">
						<aui:input helpMessage="structure-help" name="structure" type="resource" value="<%= (structure != null) ? structure.getName(locale) : StringPool.BLANK %>" />

						<c:if test="<%= ((template == null) || (template.getClassPK() == 0)) %>">
							<liferay-ui:icon
								iconCssClass="icon-search"
								label="<%= true %>"
								linkCssClass="btn btn-default"
								message="select"
								url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
							/>
						</c:if>
					</div>
				</c:if>

				<c:if test="<%= type.equals(DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY) %>">
					<aui:select changesContext="<%= true %>" helpMessage='<%= (template == null) ? StringPool.BLANK : "changing-the-language-will-not-automatically-translate-the-existing-template-script" %>' label="language" name="language">

						<%
						for (String curLangType : ddmDisplay.getTemplateLanguageTypes()) {
							StringBundler sb = new StringBundler(6);

							sb.append(LanguageUtil.get(pageContext, curLangType + "[stands-for]"));
							sb.append(StringPool.SPACE);
							sb.append(StringPool.OPEN_PARENTHESIS);
							sb.append(StringPool.PERIOD);
							sb.append(curLangType);
							sb.append(StringPool.CLOSE_PARENTHESIS);
						%>

							<aui:option label="<%= sb.toString() %>" selected="<%= language.equals(curLangType) %>" value="<%= curLangType %>" />

						<%
						}
						%>

					</aui:select>
				</c:if>

				<aui:input name="description" />

				<c:if test="<%= template != null %>">
					<aui:input helpMessage="template-key-help" name="templateKey" type="resource" value="<%= template.getTemplateKey() %>" />

					<aui:input name="url" type="resource" value='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/dynamic_data_mapping/get_template?templateId=" + templateId %>' />

					<c:if test="<%= Validator.isNotNull(refererWebDAVToken) %>">
						<aui:input name="webDavURL" type="resource" value="<%= template.getWebDavURL(themeDisplay, refererWebDAVToken) %>" />
					</c:if>
				</c:if>

				<c:choose>
					<c:when test="<%= type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">
						<aui:select helpMessage="only-allow-deleting-required-fields-in-edit-mode" label="mode" name="mode">
							<aui:option label="create" />
							<aui:option label="edit" />
						</aui:select>
					</c:when>
					<c:otherwise>
						<div id="<portlet:namespace />smallImageContainer">
							<div class="lfr-ddm-small-image-header">
								<aui:input name="smallImage" />
							</div>

							<div class="lfr-ddm-small-image-content toggler-content-collapsed">
								<aui:row>
									<c:if test="<%= smallImage && (template != null) %>">
										<aui:col width="<%= 50 %>">
											<img alt="<liferay-ui:message key="preview" />" class="lfr-ddm-small-image-preview" src="<%= Validator.isNotNull(template.getSmallImageURL()) ? HtmlUtil.escapeHREF(template.getSmallImageURL()) : themeDisplay.getPathImage() + "/template?img_id=" + template.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(template.getSmallImageId()) %>" />
										</aui:col>
									</c:if>

									<aui:col width="<%= (smallImage && (template != null)) ? 50 : 100 %>">
										<aui:fieldset>
											<aui:input cssClass="lfr-ddm-small-image-type" inlineField="<%= true %>" label="small-image-url" name="type" type="radio" />

											<aui:input cssClass="lfr-ddm-small-image-value" inlineField="<%= true %>" label="" name="smallImageURL" title="small-image-url" />
										</aui:fieldset>

										<aui:fieldset>
											<aui:input cssClass="lfr-ddm-small-image-type" inlineField="<%= true %>" label="small-image" name="type" type="radio" />

											<aui:input cssClass="lfr-ddm-small-image-value" inlineField="<%= true %>" label="" name="smallImageFile" type="file" />
										</aui:fieldset>
									</aui:col>
								</aui:row>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<c:choose>
			<c:when test="<%= type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">
				<%@ include file="/html/portlet/dynamic_data_mapping/edit_template_form.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/dynamic_data_mapping/edit_template_display.jspf" %>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</aui:form>

<c:choose>
	<c:when test="<%= type.equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">
		<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>

		<aui:script>
			Liferay.provide(
				window,
				'<portlet:namespace />attachValueChange',
				function(mode) {
					var A = AUI();

					A.one('#<portlet:namespace />mode').on(
						'change',
						function(event) {
							var currentTarget = event.currentTarget;

							<portlet:namespace />toggleMode(currentTarget.get('value'));
						}
					);
				},
				['aui-base']
			);

			Liferay.on(
				'<portlet:namespace />formBuilderLoaded',
				function(event) {
					<portlet:namespace />attachValueChange();

					<portlet:namespace />toggleMode('<%= HtmlUtil.escape(mode) %>');
				}
			);

			Liferay.provide(
				window,
				'<portlet:namespace />setFieldsHiddenAttributes',
				function(item, index, collection, mode) {
					var A = AUI();

					var hiddenAttributesMap = window.<portlet:namespace />formBuilder.MAP_HIDDEN_FIELD_ATTRS;
					var hiddenAttributes = hiddenAttributesMap[item.get('type')] || hiddenAttributesMap.DEFAULT;

					hiddenAttributes = A.Array(hiddenAttributes);

					if (mode === '<%= DDMTemplateConstants.TEMPLATE_MODE_EDIT %>') {
						A.Array.removeItem(hiddenAttributes, 'readOnly');
					}

					item.set('hiddenAttributes', hiddenAttributes);
				},
				['aui-base']
			);

			Liferay.provide(
				window,
				'<portlet:namespace />toggleMode',
				function(mode) {
					var A = AUI();

					var modeEdit = (mode === '<%= DDMTemplateConstants.TEMPLATE_MODE_EDIT %>');

					window.<portlet:namespace />formBuilder.set('allowRemoveRequiredFields', modeEdit);

					window.<portlet:namespace />formBuilder.get('fields').each(A.rbind('<portlet:namespace />setFieldsHiddenAttributes', window, mode));

					A.Array.each(window.<portlet:namespace />formBuilder.get('availableFields'), A.rbind('<portlet:namespace />setFieldsHiddenAttributes', window, mode));

					var editingField = window.<portlet:namespace />formBuilder.editingField;

					if (editingField) {
						window.<portlet:namespace />formBuilder.propertyList.set('data', window.<portlet:namespace />formBuilder.getFieldProperties(editingField));
					}
				},
				['aui-base']
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script use="aui-toggler">
			var container = A.one('#<portlet:namespace />smallImageContainer');

			var types = container.all('.lfr-ddm-small-image-type');
			var values = container.all('.lfr-ddm-small-image-value');

			var selectSmallImageType = function(index) {
				types.set('checked', false);

				types.item(index).set('checked', true);

				values.set('disabled', true);

				values.item(index).set('disabled', false);
			};

			container.delegate(
				'change',
				function(event) {
					var index = types.indexOf(event.currentTarget);

					selectSmallImageType(index);
				},
				'.lfr-ddm-small-image-type'
			);

			new A.Toggler(
				{
					animated: true,
					content: '#<portlet:namespace />smallImageContainer .lfr-ddm-small-image-content',
					expanded: <%= smallImage %>,
					header: '#<portlet:namespace />smallImageContainer .lfr-ddm-small-image-header',
					on: {
						animatingChange: function(event) {
							var instance = this;

							var expanded = !instance.get('expanded');

							A.one('#<portlet:namespace />smallImage').set('checked', expanded);

							if (expanded) {
								types.each(
									function(item, index) {
										if (item.get('checked')) {
											values.item(index).set('disabled', false);
										}
									}
								);
							}
							else {
								values.set('disabled', true);
							}
						}
					}
				}
			);

			selectSmallImageType('<%= (template != null) && Validator.isNotNull(template.getSmallImageURL()) ? 0 : 1 %>');
		</aui:script>
	</c:otherwise>
</c:choose>

<c:if test="<%= ddmDisplay.isShowStructureSelector() && ((template == null) || (template.getClassPK() == 0)) %>">
	<aui:script>
		function <portlet:namespace />openDDMStructureSelector() {
			Liferay.Util.openDDMPortlet(
				{
					basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
					classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
					classPK: 0,
					eventName: '<portlet:namespace />selectStructure',
					groupId: <%= groupId %>,
					refererPortletName: '<%= PortletKeys.JOURNAL %>',
					showAncestorScopes: true,
					struts_action: '/dynamic_data_mapping/select_structure',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "structures") %>'
				},
				function(event) {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>') && (document.<portlet:namespace />fm.<portlet:namespace />classPK.value != event.ddmstructureid)) {
						document.<portlet:namespace />fm.<portlet:namespace />classPK.value = event.ddmstructureid;

						Liferay.fire('<portlet:namespace />refreshEditor');
					}
				}
			);
		}
	</aui:script>
</c:if>

<aui:button-row>
	<aui:script>
		Liferay.after(
			'<portlet:namespace />saveTemplate',
			function() {
				submitForm(document.<portlet:namespace />fm);
			}
		);

		function <portlet:namespace />saveAndContinueTemplate() {
			document.<portlet:namespace />fm.<portlet:namespace />saveAndContinue.value = '1';

			Liferay.fire('<portlet:namespace />saveTemplate');
		}
	</aui:script>

	<%
	String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" primary="<%= true %>" value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<aui:button onClick='<%= renderResponse.getNamespace() + "saveAndContinueTemplate();" %>' value='<%= LanguageUtil.get(pageContext, "save-and-continue") %>' />

	<aui:button href="<%= redirect %>" type="cancel" />
</aui:button-row>