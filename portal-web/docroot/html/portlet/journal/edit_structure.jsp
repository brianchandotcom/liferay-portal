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
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

JournalStructure structure = (JournalStructure)request.getAttribute(WebKeys.JOURNAL_STRUCTURE);

long classNameId = PortalUtil.getClassNameId(JournalStructure.class.getName());

long classPK = 0;

if (structure != null) {
	classPK = structure.getId();
}

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

Group group = GroupLocalServiceUtil.getGroup(groupId);

String structureId = BeanParamUtil.getString(structure, request, "structureId");
String newStructureId = ParamUtil.getString(request, "newStructureId");

JournalStructure parentStructure = null;

String parentStructureId = BeanParamUtil.getString(structure, request, "parentStructureId");

String parentStructureName = StringPool.BLANK;

if (Validator.isNotNull(parentStructureId)) {
	try {
		parentStructure = JournalStructureLocalServiceUtil.getStructure(groupId, parentStructureId);
	}
	catch (NoSuchStructureException nsse) {
	}
}

if ((parentStructure == null) && (groupId != themeDisplay.getCompanyGroupId())) {
	try {
		parentStructure = JournalStructureLocalServiceUtil.getStructure(themeDisplay.getCompanyGroupId(), parentStructureId);
	}
	catch (NoSuchStructureException nsse) {
	}
}

if (parentStructure != null) {
	parentStructureName = parentStructure.getName(locale);
}

String xsd = ParamUtil.getString(request, "xsd");

if (Validator.isNull(xsd)) {
	xsd = "<root></root>";

	if (structure != null) {
		xsd = structure.getXsd();
	}
}

// Bug with dom4j requires you to remove "\r\n" and "  " or else root.elements()
// and root.content() will return different number of objects

xsd = JournalUtil.processXMLAttributes(xsd);
xsd = StringUtil.replace(xsd, StringPool.RETURN_NEW_LINE, StringPool.BLANK);
xsd = StringUtil.replace(xsd, StringPool.DOUBLE_SPACE, StringPool.BLANK);

int tabIndex = 1;
%>

<aui:form method="post" name="fm2">
	<input name="xml" type="hidden" value="" />
</aui:form>

<portlet:actionURL var="editStructureURL">
	<portlet:param name="struts_action" value="/journal/edit_structure" />
</portlet:actionURL>

<aui:form action="<%= editStructureURL %>" method="post" name="fm1" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<input name="scroll" type="hidden" value="" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="originalRedirect" type="hidden" value="<%= originalRedirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
	<aui:input name="move_up" type="hidden" />
	<aui:input name="move_depth" type="hidden" />
	<aui:input name="saveAndContinue" type="hidden" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (structure == null) %>"
		title='<%= (structure == null) ? "new-structure" : structure.getName(locale) %>'
	/>

	<liferay-ui:error exception="<%= DuplicateStructureElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= DuplicateStructureIdException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= StructureIdException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= StructureInheritanceException.class %>" message="this-structure-is-already-within-the-inheritance-path-of-the-selected-parent-please-select-another-parent-structure" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= structure %>" model="<%= JournalStructure.class %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test="<%= structure == null %>">
				<c:choose>
					<c:when test="<%= PropsValues.JOURNAL_STRUCTURE_FORCE_AUTOGENERATE_ID %>">
						<aui:input name="newStructureId" type="hidden" />
						<aui:input name="autoStructureId" type="hidden" value="<%= true %>" />
					</c:when>
					<c:otherwise>
						<aui:input cssClass="lfr-input-text-container" field="structureId" fieldParam="newStructureId" label="id" name="newStructureId" value="<%= newStructureId %>" />

						<aui:input label="autogenerate-id" name="autoStructureId" type="checkbox" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<aui:field-wrapper label="id">
					<%= structureId %>
				</aui:field-wrapper>
			</c:otherwise>
		</c:choose>

		<aui:input name="name" />

		<aui:input name="description" />

		<aui:field-wrapper label="parent-structure">
			<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />

			<c:choose>
				<c:when test="<%= (structure == null) || (Validator.isNotNull(parentStructureId)) %>">
					<portlet:renderURL var="parentStructureURL">
						<portlet:param name="struts_action" value="/journal/edit_structure" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<portlet:param name="parentStructureId" value="<%= parentStructureId %>" />
					</portlet:renderURL>

					<aui:a href="<%= parentStructureURL %>" label="<%= parentStructureName %>" id="parentStructureName" />
				</c:when>
				<c:otherwise>
					<aui:a href="" id="parentStructureName" />
				</c:otherwise>
			</c:choose>

			<aui:button onClick='<%= renderResponse.getNamespace() + "openParentStructureSelector();" %>' value="select" />

			<aui:button name="removeParentStructureButton" onClick='<%= renderResponse.getNamespace() + "removeParentStructure();" %>' value="remove" />
		</aui:field-wrapper>

		<c:if test="<%= structure != null %>">
			<aui:field-wrapper label="url">
				<liferay-ui:input-resource url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/journal/get_structure?groupId=" + groupId + "&structureId=" + structureId %>' />
			</aui:field-wrapper>

			<c:if test="<%= portletDisplay.isWebDAVEnabled() %>">
				<aui:field-wrapper label="webdav-url">
					<liferay-ui:input-resource url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/api/secure/webdav" + group.getFriendlyURL() + "/journal/Structures/" + structureId %>' />
				</aui:field-wrapper>
			</c:if>
		</c:if>

		<c:if test="<%= structure == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions modelName="<%= JournalStructure.class.getName() %>" />
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<liferay-ui:panel-container extended="<%= true %>" id="journalStructurePanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="journalXSDPanel" persistState="<%= true %>" title="xsd">
			<aui:fieldset>
				<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

				<aui:input name="xsd" type="hidden" value="<%= JS.encodeURIComponent(xsd) %>" />

				<%
				String taglibEditElement = renderResponse.getNamespace() + "editElement('add', -1);";
				%>

				<aui:button onClick="<%= taglibEditElement %>" value="add-row" />

				<aui:button name="editorButton" value="launch-editor" />

				<c:if test="<%= structure != null %>">
					<aui:button onClick='<%= renderResponse.getNamespace() + "downloadStructureContent();" %>' value="download" />
				</c:if>

				<table class="taglib-search-iterator">

				<%
				Document doc = SAXReaderUtil.read(xsd);

				Element root = doc.getRootElement();

				String moveUpParam = request.getParameter("move_up");
				String moveDepthParam = request.getParameter("move_depth");

				if (Validator.isNotNull(moveUpParam) && Validator.isNotNull(moveDepthParam)) {
					_move(root, new IntegerWrapper(0), GetterUtil.getBoolean(moveUpParam), GetterUtil.getInteger(moveDepthParam), new BooleanWrapper(false));
				}

				IntegerWrapper tabIndexWrapper = new IntegerWrapper(tabIndex);

				_format(root, new IntegerWrapper(0), new Integer(-1), tabIndexWrapper, pageContext, request);

				tabIndex = tabIndexWrapper.getValue();
				%>

				</table>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick='<%= renderResponse.getNamespace() + "saveAndContinueStructure();" %>' value="save-and-continue" />

		<c:if test="<%= structure != null %>">
			<aui:button onClick='<%= renderResponse.getNamespace() + "saveAndEditDefaultValues();" %>' value="save-and-edit-default-values" />
		</c:if>

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	var xmlIndent = "<%= StringPool.DOUBLE_SPACE %>";

	function <portlet:namespace />downloadStructureContent() {
		document.<portlet:namespace />fm2.action = "<%= themeDisplay.getPathMain() %>/journal/get_structure_content";
		document.<portlet:namespace />fm2.target = "_self";
		document.<portlet:namespace />fm2.xml.value = <portlet:namespace />getXsd();
		document.<portlet:namespace />fm2.submit();
	}

	function <portlet:namespace />getXsd(cmd, elCount) {
		if (cmd == null) {
			cmd = "add";
		}

		var xsd = "<root>\n";

		if ((cmd == "add") && (elCount == -1)) {
			xsd += "<dynamic-element name='' type=''></dynamic-element>\n"
		}

		for (i = 0; i >= 0; i++) {
			var elDepth = document.getElementById("<portlet:namespace />structure_el" + i + "_depth");
			var elMetadataXML = document.getElementById("<portlet:namespace />structure_el" + i + "_metadata_xml");
			var elName = document.getElementById("<portlet:namespace />structure_el" + i + "_name");
			var elType = document.getElementById("<portlet:namespace />structure_el" + i + "_type");
			var elIndexType = document.getElementById("<portlet:namespace />structure_el" + i + "_index_type");
			var elRepeatable = document.getElementById("<portlet:namespace />structure_el" + i + "_repeatable");

			if ((elDepth != null) && (elName != null) && (elType != null)) {
				var elDepthValue = elDepth.value;
				var elNameValue = encodeURIComponent(elName.value);
				var elTypeValue = encodeURIComponent(elType.value);
				var elIndexTypeValue = (elIndexType != null) ? elIndexType.value : "";
				var elRepeatableValue = (elRepeatable != null) ? elRepeatable.checked : false;

				if ((cmd == "add") || ((cmd == "remove") && (elCount != i))) {
					for (var j = 0; j <= elDepthValue; j++) {
						xsd += xmlIndent;
					}

					xsd += "<dynamic-element name='" + elNameValue + "' type='" + elTypeValue + "' index-type='" + elIndexTypeValue + "' repeatable='" + elRepeatableValue + "'>";

					if ((cmd == "add") && (elCount == i)) {
						xsd += "<dynamic-element name='' type='' repeatable='false'></dynamic-element>\n";
					}
					else {
						if (elMetadataXML.value) {
							var metadataXML = decodeURIComponent(elMetadataXML.value).replace(/[+]/g, ' ');

							xsd += "\n";
							xsd += xmlIndent;
							xsd += metadataXML;
							xsd += "\n";
						}
					}

					var nextElDepth = document.getElementById("<portlet:namespace />structure_el" + (i + 1) + "_depth");

					if (nextElDepth != null) {
						var nextElDepthValue = nextElDepth.value;

						if (elDepthValue == nextElDepthValue) {
							for (var j = 0; j < elDepthValue; j++) {
								xsd += xmlIndent;
							}

							xsd += "</dynamic-element>\n";
						}
						else if (elDepthValue > nextElDepthValue) {
							var depthDiff = elDepthValue - nextElDepthValue;

							for (var j = 0; j <= depthDiff; j++) {
								if (j != 0) {
									for (var k = 0; k <= depthDiff - j; k++) {
										xsd += xmlIndent;
									}
								}

								xsd += "</dynamic-element>\n";
							}
						}
						else {
							xsd += "\n";
						}
					}
					else {
						for (var j = 0; j <= elDepthValue; j++) {
							if (j != 0) {
								for (var k = 0; k <= elDepthValue - j; k++) {
									xsd += xmlIndent;
								}
							}

							xsd += "</dynamic-element>\n";
						}
					}
				}
				else if ((cmd == "remove") && (elCount == i)) {
					var nextElDepth = document.getElementById("<portlet:namespace />structure_el" + (i + 1) + "_depth");

					if (nextElDepth != null) {
						var nextElDepthValue = nextElDepth.value;

						if (elDepthValue > nextElDepthValue) {
							var depthDiff = elDepthValue - nextElDepthValue;

							for (var j = 0; j < depthDiff; j++) {
								xsd += "</dynamic-element>\n";
							}
						}
					}
					else {
						for (var j = 0; j < elDepthValue; j++) {
							xsd += "</dynamic-element>\n";
						}
					}
				}
			}
			else {
				break;
			}
		}

		xsd += "</root>";

		return xsd;
	}

	function <portlet:namespace />editElement(cmd, elCount) {
		document.<portlet:namespace />fm1.scroll.value = "<portlet:namespace />xsd";
		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd(cmd, elCount);
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />moveElement(moveUp, elCount) {
		document.<portlet:namespace />fm1.scroll.value = "<portlet:namespace />xsd";
		document.<portlet:namespace />fm1.<portlet:namespace />move_up.value = moveUp;
		document.<portlet:namespace />fm1.<portlet:namespace />move_depth.value = elCount;
		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd();
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />openParentStructureSelector() {
		Liferay.Util.openWindow(
			{
				dialog: {
					width: 680
				},
				title: '<%= UnicodeLanguageUtil.get(pageContext, "structure") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>'
			}
		);
	}

	function <portlet:namespace />removeParentStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />parentStructureId.value = "";

		var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

		nameEl.href = "#";
		nameEl.innerHTML = "";

		document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = true;
	}

	function <portlet:namespace />saveAndContinueStructure() {
		document.<portlet:namespace />fm1.<portlet:namespace />saveAndContinue.value = "1";
		<portlet:namespace />saveStructure();
	}

	function <portlet:namespace />saveAndEditDefaultValues() {
		<portlet:renderURL var="editStructureDefaultValuesURL">
			<portlet:param name="struts_action" value="/journal/edit_article" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="structureId" value="<%= structureId %>" />
		</portlet:renderURL>

		document.<portlet:namespace />fm1.<portlet:namespace />redirect.value = "<%= editStructureDefaultValuesURL %>";
		<portlet:namespace />saveStructure();
	}

	function <portlet:namespace />saveStructure(addAnother) {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (structure == null) ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= structure == null %>">
			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = document.<portlet:namespace />fm1.<portlet:namespace />newStructureId.value;
		</c:if>

		document.<portlet:namespace />fm1.<portlet:namespace />xsd.value = <portlet:namespace />getXsd();
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />selectStructure(parentStructureId, parentStructureName, dialog) {
		document.<portlet:namespace />fm1.<portlet:namespace />parentStructureId.value = parentStructureId;

		var nameEl = document.getElementById("<portlet:namespace />parentStructureName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>&<portlet:namespace />parentStructureId=" + parentStructureId;
		nameEl.innerHTML = parentStructureName + "&nbsp;";

		document.getElementById("<portlet:namespace />removeParentStructureButton").disabled = false;

		if (dialog) {
			dialog.close();
		}
	}

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoStructureIdCheckbox','<portlet:namespace />newStructureId', true);

	Liferay.Util.inlineEditor(
		{
			button: '#<portlet:namespace />editorButton',
			dialog: {
				after: {
					init: function(event) {
						this.on(
							'update',
							function() {
								submitForm(document.<portlet:namespace />fm1);
							}
						);
					}
				}
			},
			id: '<portlet:namespace />xsdContentIFrame',
			textarea: '<portlet:namespace />xsdContent',
			title: '<%= UnicodeLanguageUtil.get(pageContext, "editor") %>',
			uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template_xsl" /><portlet:param name="langType" value="xsd" /><portlet:param name="editorContentInputElement" value='<%= "#" + renderResponse.getNamespace() + "xsd" %>' /><portlet:param name="editorContentOutputElement" value='<%= "#" + renderResponse.getNamespace() + "xsd" %>' /></portlet:renderURL>'
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_STRUCTURE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (structure == null) ? "newStructureId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>

<%!
private void _format(Element root, IntegerWrapper count, Integer depth, IntegerWrapper tabIndex, PageContext pageContext, HttpServletRequest request) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	List children = root.elements();

	Boolean hasSiblings = null;

	if (children.size() > 1) {
		hasSiblings = Boolean.TRUE;
	}
	else {
		hasSiblings = Boolean.FALSE;
	}

	Iterator itr = children.iterator();

	while (itr.hasNext()) {
		Element el = (Element)itr.next();

		if (el.getName().equals("meta-data")) {
			continue;
		}

		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, el);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);
		request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_SIBLINGS, hasSiblings);
		request.setAttribute(WebKeys.TAB_INDEX, tabIndex);

		pageContext.include("/html/portlet/journal/edit_structure_xsd_el.jsp");

		count.increment();

		_format(el, count, depth, tabIndex, pageContext, request);
	}
}

private void _move(Element root, IntegerWrapper count, boolean up, int depth, BooleanWrapper halt) throws Exception {
	List children = root.elements();

	for (int i = 0; i < children.size(); i++) {
		Element el = (Element)children.get(i);

		String nodeName = el.getName();

		if (Validator.isNotNull(nodeName) && nodeName.equals("meta-data")) {
			continue;
		}

		if (halt.getValue()) {
			return;
		}

		if (count.getValue() == depth) {
			if (up) {
				if (i == 0) {
					children.remove(i);
					children.add(children.size(), el);
				}
				else {
					children.remove(i);
					children.add(i - 1, el);
				}
			}
			else {
				if ((i + 1) == children.size()) {
					children.remove(i);
					children.add(0, el);
				}
				else {
					children.remove(i);
					children.add(i + 1, el);
				}
			}

			halt.setValue(true);

			return;
		}

		count.increment();

		_move(el, count, up, depth, halt);
	}
}
%>