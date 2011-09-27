<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/mobile_device_rules_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MDRRuleGroupInstance ruleGroupInstance = (MDRRuleGroupInstance) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP_INSTANCE);
MDRRuleGroup ruleGroup = (MDRRuleGroup) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP);
String className = ParamUtil.getString(renderRequest, MDRPortletConstants.CLASS_NAME);
long classPK = ParamUtil.getLong(renderRequest, MDRPortletConstants.CLASS_PK);

boolean isAdd = Validator.isNull(ruleGroupInstance);

long ruleGroupInstanceId = 0;
long ruleGroupId = 0;

if (!isAdd) {
	ruleGroupInstanceId = ruleGroupInstance.getRuleGroupInstanceId();
	ruleGroupId = ruleGroupInstance.getRuleGroupId();
	className = ruleGroupInstance.getClassName();
	classPK = ruleGroupInstance.getClassPK();
}

Layout selLayout = null;
LayoutSet selLayoutSet = null;

if (className.equals(LayoutSet.class.getName())) {
	selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(classPK);
	groupId = selLayoutSet.getGroupId();
}
else if (className.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(classPK);
	groupId = selLayout.getGroupId();
}
%>

<liferay-ui:header
	title="edit-rule-group-instance"
	backURL="<%= redirect %>"
/>

<liferay-ui:icon
	image="add"
	label="<%= true %>"
	message="select-rule-group"
	url='<%= "javascript:" + renderResponse.getNamespace() + "openSelector();" %>'
/>

<portlet:actionURL var="editRuleGroupInstanceURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group_instance" />
    <portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editRuleGroupInstanceURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveRuleGroupInstance();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="<%= MDRPortletConstants.MDR_RULE_GROUP_ID %>" type="hidden" value="<%= ruleGroupId %>" />
	<aui:input name="<%= MDRPortletConstants.GROUP_ID %>" type="hidden" value="<%= groupId %>" />
	<aui:input name="<%= MDRPortletConstants.CLASS_NAME %>" type="hidden" value="<%= className %>" />
	<aui:input name="<%= MDRPortletConstants.CLASS_PK %>" type="hidden" value="<%= classPK %>" />

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist"/>

	<div id="<%= renderResponse.getNamespace() %>editor">
		<c:choose>
			<c:when test="<%= isAdd %>">
				<h3><%= LanguageUtil.get(pageContext, "no-rule-group-selected") %></h3>
			</c:when>
			<c:otherwise>
				<c:import url="/html/portlet/mobile_device_rules_admin/device_rule_group_instance_editor.jsp" />
			</c:otherwise>
		</c:choose>
	</div>
</aui:form>

<portlet:resourceURL var="editorURL">
    <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group_instance_editor" />
</portlet:resourceURL>

<aui:script>
	Liferay.provide(
		window,
        '<portlet:namespace />openSelector',
        function(groupId) {
            var config = {
                chooseCallback: '<portlet:namespace />finishCall',
                dialog: {
                    stack: false,
                    width: 820,
                    align: Liferay.Util.Window.ALIGN_CENTER
                }
            };

            var mdrURL = Liferay.PortletURL.createRenderURL();

            mdrURL.setEscapeXML(false);

            mdrURL.setParameter('struts_action', '/mobile_device_rules_admin/view');
            mdrURL.setParameter("groupId", groupId);
			mdrURL.setParameter('chooseCallback', config.chooseCallback);
			mdrURL.setParameter("groupId", '<%= groupId %>');

            mdrURL.setPortletId(<%= PortletKeys.MOBILE_DEVICE_ADMIN_SITE %>);
            mdrURL.setWindowState('pop_up');

            config.uri = mdrURL.toString();

            Liferay.Util.openWindow(config);
        },
        ['liferay-portlet-url']
    );

	Liferay.provide(
		window,
		'<portlet:namespace />finishCall',
        function(ruleGroupId, dialog) {
			var A = AUI();

			document.<portlet:namespace />fm.<portlet:namespace /><%=MDRPortletConstants.MDR_RULE_GROUP_ID%>.value = ruleGroupId;

            if (dialog) {
                dialog.close();
            }

			<portlet:namespace />changeDisplay();
        },
		['aui-base']
	);

	Liferay.provide(
		window,
        '<portlet:namespace />changeDisplay',
        function() {
            var A = AUI();

            var ruleGroupId = document.<portlet:namespace />fm.<portlet:namespace /><%=MDRPortletConstants.MDR_RULE_GROUP_ID%>.value;

            A.io(
                '<%= editorURL.toString() %>',
                {
                    data: {
                        <%=MDRPortletConstants.CLASS_PK%>: '<%= classPK %>',
						<%=MDRPortletConstants.CLASS_NAME%>: '<%= className %>',
						<%=MDRPortletConstants.MDR_RULE_GROUP_ID%>: ruleGroupId,
						redirect: '<%= redirect %>'
                    },
                    method: AUI.defaults.io.method,
                    on: {
                        complete: <portlet:namespace />displayEditor
                    }
                }
            )
        },
		['aui-base']
    );

    function <portlet:namespace />displayEditor(id, obj) {
        document.getElementById('<portlet:namespace />editor').innerHTML=obj.responseText;
    };

	function <portlet:namespace />saveRuleGroupInstance() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EDIT %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>