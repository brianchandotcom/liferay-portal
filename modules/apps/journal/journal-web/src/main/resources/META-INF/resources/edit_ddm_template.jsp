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
JournalEditDDMTemplateDisplayContext journalEditDDMTemplateDisplayContext = new JournalEditDDMTemplateDisplayContext(request);

DDMTemplate ddmTemplate = journalEditDDMTemplateDisplayContext.getDDMTemplate();

DDMStructure ddmStructure = journalEditDDMTemplateDisplayContext.getDDMStructure();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(journalEditDDMTemplateDisplayContext.getRedirect());

renderResponse.setTitle(journalEditDDMTemplateDisplayContext.getTitle());
%>

<portlet:actionURL name="/journal/add_ddm_template" var="addDDMTemplateURL">
	<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
</portlet:actionURL>

<portlet:actionURL name="/journal/update_ddm_template" var="updateDDMTemplateURL">
	<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= (ddmTemplate == null) ? addDDMTemplateURL : updateDDMTemplateURL %>"
	enctype="multipart/form-data"
	fluid="<%= true %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault();" %>'
>
	<aui:input name="redirect" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getRedirect() %>" />
	<aui:input name="ddmTemplateId" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getDDMTemplateId() %>" />
	<aui:input name="groupId" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getGroupId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= journalEditDDMTemplateDisplayContext.getClassPK() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />
		<liferay-ui:error exception="<%= TemplateSmallImageContentException.class %>" message="the-small-image-file-could-not-be-saved" />

		<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">
			<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalEditDDMTemplateDisplayContext.imageExtensions(), StringPool.COMMA)) %>.
		</liferay-ui:error>

		<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(journalEditDDMTemplateDisplayContext.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<c:if test="<%= (ddmTemplate != null) && (journalEditDDMTemplateDisplayContext.getGroupId() != scopeGroupId) %>">
					<div class="alert alert-warning">
						<liferay-ui:message key="this-template-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-template" />
					</div>
				</c:if>

				<aui:input name="name" />

				<liferay-frontend:fieldset
					collapsed="<%= true %>"
					collapsible="<%= true %>"
					label="details"
				>
					<div class="form-group">
						<aui:input helpMessage="structure-help" name="structure" type="resource" value="<%= (ddmStructure != null) ? ddmStructure.getName(locale) : StringPool.BLANK %>" />

						<c:if test="<%= (ddmTemplate == null) || (ddmTemplate.getClassPK() == 0) %>">
							<liferay-ui:icon
								iconCssClass="icon-search"
								label="<%= true %>"
								linkCssClass="btn btn-default"
								message="select"
								url='<%= "javascript:" + renderResponse.getNamespace() + "openDDMStructureSelector();" %>'
							/>
						</c:if>
					</div>

					<aui:select changesContext="<%= true %>" helpMessage='<%= (ddmTemplate == null) ? StringPool.BLANK : "changing-the-language-does-not-automatically-translate-the-existing-template-script" %>' label="language" name="language">

						<%
						String[] templateLanguageTypes = {TemplateConstants.LANG_TYPE_FTL, TemplateConstants.LANG_TYPE_VM, TemplateConstants.LANG_TYPE_XSL};

						for (String curLangType : templateLanguageTypes) {
							StringBundler sb = new StringBundler(6);

							sb.append(LanguageUtil.get(request, curLangType + "[stands-for]"));
							sb.append(StringPool.SPACE);
							sb.append(StringPool.OPEN_PARENTHESIS);
							sb.append(StringPool.PERIOD);
							sb.append(curLangType);
							sb.append(StringPool.CLOSE_PARENTHESIS);
						%>

							<aui:option label="<%= sb.toString() %>" selected="<%= Objects.equals(journalEditDDMTemplateDisplayContext.getLanguage(), curLangType) %>" value="<%= curLangType %>" />

						<%
						}
						%>

					</aui:select>

					<c:if test="<%= !journalEditDDMTemplateDisplayContext.autogenerateDDMTemplateKey() %>">
						<aui:input disabled="<%= ddmTemplate != null %>" name="ddmTemplateKey" />
					</c:if>

					<aui:input name="description" />

					<c:if test="<%= ddmTemplate != null %>">
						<aui:input helpMessage="template-key-help" name="ddmTemplateKey" type="resource" value="<%= ddmTemplate.getTemplateKey() %>" />

						<portlet:resourceURL id="/journal/get_ddm_template" var="getDDMTemplateURL">
							<portlet:param name="ddmTemplateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
						</portlet:resourceURL>

						<aui:input name="url" type="resource" value="<%= getDDMTemplateURL %>" />

						<%
						Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
						%>

						<aui:input name="webDavURL" type="resource" value="<%= ddmTemplate.getWebDavURL(themeDisplay, WebDAVUtil.getStorageToken(portlet)) %>" />
					</c:if>

					<aui:input helpMessage="journal-template-cacheable-help" name="cacheable" value="<%= journalEditDDMTemplateDisplayContext.isCacheable() %>" />

					<div id="<portlet:namespace />smallImageContainer">
						<div class="lfr-ddm-small-image-header">
							<aui:input name="smallImage" />
						</div>

						<div class="lfr-ddm-small-image-content toggler-content-collapsed">
							<aui:row>
								<c:if test="<%= journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null) %>">
									<aui:col width="<%= 50 %>">
										<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="preview" />" class="lfr-ddm-small-image-preview" src="<%= HtmlUtil.escapeAttribute(ddmTemplate.getTemplateImageURL(themeDisplay)) %>" />
									</aui:col>
								</c:if>

								<aui:col width="<%= (journalEditDDMTemplateDisplayContext.isSmallImage() && (ddmTemplate != null)) ? 50 : 100 %>">
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
				</liferay-frontend:fieldset>

				<%@ include file="/edit_ddm_template_display.jspf" %>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>

		<%
		String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
		%>

		<aui:button onClick="<%= taglibOnClick %>" type="submit" value="save" />

		<aui:button href="<%= journalEditDDMTemplateDisplayContext.getRedirect() %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />smallImageContainer');

	var types = container.all('.lfr-ddm-small-image-type');
	var values = container.all('.lfr-ddm-small-image-value');

	var selectSmallImageType = function(index) {
		types.attr('checked', false);

		types.item(index).attr('checked', true);

		values.attr('disabled', true);

		values.item(index).attr('disabled', false);
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
			expanded: <%= journalEditDDMTemplateDisplayContext.isSmallImage() %>,
			header: '#<portlet:namespace />smallImageContainer .lfr-ddm-small-image-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />smallImage').attr('checked', expanded);

					if (expanded) {
						types.each(
							function(item, index) {
								if (item.get('checked')) {
									values.item(index).attr('disabled', false);
								}
							}
						);
					}
					else {
						values.attr('disabled', true);
					}
				}
			}
		}
	);

	selectSmallImageType('<%= ((ddmTemplate != null) && Validator.isNotNull(ddmTemplate.getSmallImageURL())) ? 0 : 1 %>');
</aui:script>

<c:if test="<%= (ddmTemplate == null) || (ddmTemplate.getClassPK() == 0) %>">
	<aui:script>
		function <portlet:namespace />openDDMStructureSelector() {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectDDMStructure',
					title: '<%= UnicodeLanguageUtil.get(request, "structures") %>',
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_ddm_structure.jsp" /></portlet:renderURL>'
				},
				function(event) {
					if (document.<portlet:namespace />fm.<portlet:namespace />classPK.value != event.ddmstructureid) {
						document.<portlet:namespace />fm.<portlet:namespace />classPK.value = event.ddmstructureid;

						Liferay.fire('<portlet:namespace />refreshEditor');
					}
				}
			);
		}
	</aui:script>
</c:if>

<aui:script>
	Liferay.after(
		'<portlet:namespace />saveTemplate',
		function() {
			submitForm(document.<portlet:namespace />fm);
		}
	);
</aui:script>