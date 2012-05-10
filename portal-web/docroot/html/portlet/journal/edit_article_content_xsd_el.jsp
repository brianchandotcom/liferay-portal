<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String languageId = LanguageUtil.getLanguageId(request);

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

if (Validator.isNotNull(toLanguageId)) {
	languageId = toLanguageId;
}

long groupId = GetterUtil.getLong((String)request.getAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID));

Group group = GroupLocalServiceUtil.getGroup(groupId);

Group liveGroup = group;

if (liveGroup.isStagingGroup()) {
	liveGroup = liveGroup.getLiveGroup();
}

Element el = (Element)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL);
IntegerWrapper count = (IntegerWrapper)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT);
Integer depth = (Integer)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH);

String elInstanceId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID);
String elName = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME);
String elType = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE);
String elIndexType = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INDEX_TYPE);
boolean elRepeatable = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE));
boolean elRepeatablePrototype = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE));
String elContent = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT);
String elLanguageId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID);
String elParentStructureId = (String)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID);

Map<String, String> elMetaData = (Map<String, String>)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA);

Map <String, Integer> repeatCountMap = (Map<String, Integer>)request.getAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEAT_COUNT_MAP);

if (repeatCountMap == null) {
	repeatCountMap = new HashMap<String, Integer>();
}

String elRepeatCount = String.valueOf(repeatCountMap.get(elName));

if (Validator.isNotNull(elRepeatCount)) {
	elRepeatCount = "_" + elRepeatCount;
}
else {
	elRepeatCount = StringPool.BLANK;
}

String metaData = _buildMetaDataHTMLAttributes(elMetaData, elName);

String elDisplayAsTooltip = elMetaData.get("displayAsTooltip");
String elInstructions = elMetaData.get("instructions");
String elLabel = elMetaData.get("label");
String elPredefinedValue = elMetaData.get("predefinedValue");

boolean displayAsTooltip = false;

if (Validator.isNotNull(elDisplayAsTooltip)) {
	displayAsTooltip = GetterUtil.getBoolean(elDisplayAsTooltip);
}

if (Validator.isNull(elLabel)) {
	elLabel = elName;
}

if (Validator.isNull(elPredefinedValue)) {
	elPredefinedValue = StringPool.BLANK;
}

String css = StringPool.BLANK;

if (!elRepeatablePrototype) {
	css = " repeated-field ";
}

String parentStructureData = StringPool.BLANK;

if (Validator.isNotNull(elParentStructureId)) {
	parentStructureData = "dataParentStructureId='".concat(elParentStructureId).concat("'");

	css = css.concat(" parent-structure-field ");
}

if (Validator.isNull(elContent) && Validator.isNotNull(elPredefinedValue)) {
	elContent = elPredefinedValue;
}

Element contentEl = (Element)request.getAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL);
%>

<li class="structure-field <%= css.trim() %>" <%= parentStructureData %> dataInstanceId='<%= elInstanceId %>' dataName='<%= elName %>' dataRepeatable='<%= elRepeatable %>' dataType='<%= elType %>' dataIndexType='<%= elIndexType %>' <%= metaData %>>
	<span class="journal-article-close"></span>

	<span class="folder">
		<div class="field-container">
			<input class="journal-article-localized" type="hidden" value='<%= !elLanguageId.equals(StringPool.BLANK) ? elLanguageId : "false" %>' />

			<div class="journal-article-move-handler"></div>

			<label class="journal-article-field-label">
				<span><%= elLabel %></span>

				<c:if test="<%= (Validator.isNotNull(elInstructions) && displayAsTooltip) %>">
					<img align="top" class="journal-article-instructions-container" src="/html/themes/classic/images/portlet/help.png" />
				</c:if>
			</label>

			<div class="journal-article-component-container">
				<c:if test='<%= elType.equals("text") %>'>

					<%
					String textInputName = "text_" + elName + elRepeatCount;

					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, textInputName);
					}
					%>

					<aui:input cssClass="lfr-input-text-container" ignoreRequestValue="<%= true %>" label="" name="<%= textInputName %>" size="55" type="text" value="<%= elContent %>" />
				</c:if>

				<c:if test='<%= elType.equals("text_box") %>'>

					<%
					String textBoxInputName = "textBox_" + elName + elRepeatCount;

					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, textBoxInputName);
					}
					%>

					<aui:input cols="60" cssClass="lfr-textarea-container" ignoreRequestValue="<%= true %>" label="" name="<%= textBoxInputName %>" rows="10" type="textarea" value="<%= elContent %>" />
				</c:if>

				<c:if test='<%= elType.equals("text_area") %>'>

					<%
					String textAreaInputName = "structure_el_" + elName + elRepeatCount + "_content";

					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, textAreaInputName);
					}
					%>

					<liferay-ui:input-editor
						editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>"
						height="460"
						initMethod='<%= "initEditor" + elInstanceId %>'
						name="<%= textAreaInputName %>"
						toolbarSet="liferay-article"
						width="500"
					/>

					<aui:script>
						function <portlet:namespace />initEditor<%= elInstanceId %>() {
							return "<%= UnicodeFormatter.toString(elContent) %>";
						}
					</aui:script>
				</c:if>

				<c:if test='<%= elType.equals("image") %>'>
					<aui:input cssClass="journal-image-field lfr-input-text-container flexible" label="" name="image" size="40" type="file" />

					<br />

					<c:if test="<%= Validator.isNotNull(elContent) %>">
						<span class="journal-image-show-hide">
							[ <aui:a cssClass="journal-image-link" href="javascript:void(0);"><span class="show-label"><liferay-ui:message key="show" /></span><span class="hide-label aui-helper-hidden"><liferay-ui:message key="hide" /></span></aui:a> ]
						</span>

						<div class="journal-image-preview aui-helper-hidden">

							<%
							String journalImageContentInputName = "journalImageContent_" + elName + elRepeatCount;
							%>

							<aui:input name="<%= journalImageContentInputName %>" type="hidden" value="<%= elContent %>" />

							<aui:input name="journalImageDelete" type="hidden" value="" />

							<aui:button name="journalImageDeleteButton" value="delete" />

							<br /><br />

							<div class="journal-image-wrapper results-grid">
								<img class="journal-image" hspace="0" src="<%= elContent %>" vspace="0" />
							</div>
						</div>
					</c:if>
				</c:if>

				<c:if test='<%= elType.equals("document_library") %>'>

					<%
					String journalDocumentLibraryInputName = "journalDocumentLibrary_" + elName + elRepeatCount;

					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, journalDocumentLibraryInputName);
					}
					%>

					<aui:input cssClass="lfr-input-text-container" inlineField="<%= true %>" label="" name="<%= journalDocumentLibraryInputName %>" size="55" type="text" value="<%= elContent %>" />

					<%
					long dlScopeGroupId = groupId;

					if (liveGroup.isStaged() && !liveGroup.isStagedRemotely() && !liveGroup.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {
						dlScopeGroupId = liveGroup.getGroupId();
					}
					%>

					<portlet:renderURL var="selectDLURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/journal/select_document_library" />
						<portlet:param name="groupId" value="<%= String.valueOf(dlScopeGroupId) %>" />
					</portlet:renderURL>

					<%
					Map<String,Object> data = new HashMap<String,Object>();

					data.put("DocumentlibraryUrl", selectDLURL);
					%>

					<aui:button cssClass="journal-documentlibrary-button" data="<%= data %>" value="select" />
				</c:if>

				<c:if test='<%= elType.equals("boolean") %>'>

					<%
					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, elName);
					}
					%>

					<div class="journal-subfield">
						<aui:input cssClass="journal-article-field-label" label="<%= elLabel %>" name="<%= elName %>" type="checkbox" value='<%= elContent.equals("true") %>' />
					</div>
				</c:if>

				<c:if test='<%= elType.equals("selection_break") %>'>
					<div class="separator"></div>
				</c:if>

				<c:if test='<%= elType.equals("list") %>'>
					<div class="journal-list-subfield">

						<%
						String listInputName = "listInputName_" + elName + elRepeatCount;

						if (Validator.isNull(elContent)) {
							elContent = ParamUtil.getString(request, listInputName);
						}
						%>

						<aui:select label="" name="<%= listInputName %>">

							<%
							List<Element> children = el.elements();

							for (Element child : children) {
								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								if (Validator.isNull(listElName) && Validator.isNull(listElValue)) {
									continue;
								}
							%>

								<aui:option label="<%= listElName %>" selected="<%= elContent.equals(listElValue) %>" value="<%= listElValue %>" />

							<%
							}
							%>

						</aui:select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon
								image="delete"
							/><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />

							<input class="journal-list-key" size="15" title="<liferay-ui:message key="item-label" />" type="text" value="<liferay-ui:message key="label" />" />

							<input class="journal-list-value" size="15" title="<liferay-ui:message key="item-value" />" type="text" value="value" />

							<span class="journal-icon-button journal-add-field">
								<liferay-ui:icon
									image="add"
								/> <liferay-ui:message key="add-to-list" />
							</span>
						</div>
					</div>
				</c:if>

				<c:if test='<%= elType.equals("multi-list") %>'>
					<div class="journal-list-subfield">

						<%
						String multiListInputName = "multiListInputName_" + elName + elRepeatCount;

						String[] selectedOptions = null;

						if (Validator.isNull(elContent)) {
							selectedOptions = ParamUtil.getParameterValues(request, multiListInputName);
						}
						%>

						<aui:select ignoreRequestValue="<%= true %>" label="" multiple="<%= true %>" name="<%= multiListInputName %>">

							<%
							List<Element> children = el.elements();

							for (Element child : children) {
								String listElName = JS.decodeURIComponent(child.attributeValue("name", StringPool.BLANK));
								String listElValue = JS.decodeURIComponent(child.attributeValue("type", StringPool.BLANK));

								boolean contains = false;

								Element dynConEl = contentEl.element("dynamic-content");

								if (dynConEl != null) {
									List<Element> dynConElements = dynConEl.elements("option");

									for (Element option : dynConElements) {
										if (listElValue.equals(option.getText())) {
											contains = true;
										}
									}

									if (dynConElements.isEmpty() && (selectedOptions != null)) {
										for (String option : selectedOptions) {
											if (listElValue.equals(option)) {
												contains = true;
											}
										}
									}
								}

								if (Validator.isNull(listElName) && Validator.isNull(listElValue)) {
									continue;
								}
							%>

								<aui:option label="<%= listElName %>" selected="<%= contains %>" value="<%= listElValue %>" />

							<%
							}
							%>

						</aui:select>

						<span class="journal-icon-button journal-delete-field">
							<liferay-ui:icon
								image="delete"
							/><liferay-ui:message key="delete-selected-value" />
						</span>

						<div class="journal-edit-field-control">
							<br /><br />

							<input class="journal-list-key" size="15" title="<liferay-ui:message key="item-label" />" type="text" value="<liferay-ui:message key="label" />" />

							<input class="journal-list-value" size="15" title="<liferay-ui:message key="item-value" />" type="text" value="value" />

							<span class="journal-icon-button journal-add-field">
								<liferay-ui:icon
									image="add"
								/> <liferay-ui:message key="add-to-list" />
							</span>
						</div>
					</div>

				</c:if>

				<c:if test='<%= elType.equals("link_to_layout") %>'>

					<%
					String linkSelectName = "structure_el" + elName + elRepeatCount + "_content";

					if (Validator.isNull(elContent)) {
						elContent = ParamUtil.getString(request, linkSelectName);
					}
					%>

					<aui:select label="" name="<%= linkSelectName %>" showEmptyOption="<%= true %>">

						<%
						boolean privateLayout = false;

						LayoutLister layoutLister = new LayoutLister();

						LayoutView layoutView = null;

						List layoutList = null;
						%>

						<%@ include file="/html/portlet/journal/edit_article_content_xsd_el_link_to_layout.jspf" %>

						<%
						privateLayout = true;
						%>

						<%@ include file="/html/portlet/journal/edit_article_content_xsd_el_link_to_layout.jspf" %>
					</aui:select>
				</c:if>
			</div>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<aui:input cssClass="journal-article-localized-checkbox" label="localizable" name='<%= elInstanceId + "localized-checkbox" %>' type="checkbox" value="<%= !elLanguageId.equals(StringPool.BLANK) %>" />
			</c:if>

			<div class="journal-article-required-message portlet-msg-error">
				<liferay-ui:message key="this-field-is-required" />
			</div>

			<c:if test='<%= (Validator.isNotNull(elInstructions) && !displayAsTooltip) %>'>
				<div class="journal-article-instructions-container journal-article-instructions-message portlet-msg-info">
					<%= elInstructions %>
				</div>
			</c:if>

			<div class="journal-article-buttons">
				<aui:input cssClass="journal-article-variable-name" id='<%= elInstanceId + "variableName" %>' inlineField="<%= true %>" label="variable-name" name="variableName" size="25" type="text" value="<%= elName %>" />

				<aui:button cssClass="edit-button" value="edit-options" />

				<aui:button cssClass="repeatable-button aui-helper-hidden" value="repeat" />
			</div>

			<c:if test="<%= elRepeatable %>">
				<span class="repeatable-field-image">
					<liferay-ui:icon cssClass="repeatable-field-add" image="add" />

					<liferay-ui:icon cssClass="repeatable-field-delete" image="delete" />
				</span>
			</c:if>
		</div>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content_xsd_el.jsp";

private String _buildMetaDataHTMLAttributes(Map<String, String> elMetaData, String elName) {
	if (elMetaData.isEmpty()) {
		return StringPool.BLANK;
	}

	StringBundler sb = new StringBundler(elMetaData.size() * 5);

	for (String name : elMetaData.keySet()) {
		String content = elMetaData.get(name);

		sb.append("data");
		sb.append(name);
		sb.append("='");
		sb.append(HtmlUtil.escapeAttribute(content));
		sb.append("' ");
	}

	return sb.toString();
}
%>