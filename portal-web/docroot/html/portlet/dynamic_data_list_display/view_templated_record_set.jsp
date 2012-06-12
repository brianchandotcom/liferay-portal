<%@ include file="/html/portlet/dynamic_data_list_display/init.jsp" %>

<%
DDLRecordSet recordSet = null;

	if (Validator.isNotNull(recordSetId)) {
		recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(recordSetId);
	}
%>

<liferay-ui:header
	localizeTitle="<%= false %>"
	title="<%= recordSet.getName(locale) %>"
/>
<portlet:actionURL var="editRecordSetURL">
	<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
</portlet:actionURL>

<aui:form action="<%= editRecordSetURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveRecordSet();" %>'>
<c:if test="<%= DDLRecordSetPermission.contains(permissionChecker, recordSet.getRecordSetId(), ActionKeys.ADD_RECORD) && editable%>">
    <aui:button onClick='<%= renderResponse.getNamespace() + "addRecord();" %>' value="add-record" />

    <div class="separator"><!-- --></div>
</c:if>
<%= DDLUtil.getTemplateContent(listDDMTemplateId, recordSet, themeDisplay, renderRequest, renderResponse) %>
</aui:form>
<aui:script>
    function <portlet:namespace />addRecord() {
    submitForm(document.<portlet:namespace />fm, '<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/dynamic_data_lists/edit_record" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="backURL" value="<%= currentURL %>" /><portlet:param name="recordSetId" value="<%= String.valueOf(recordSet.getRecordSetId()) %>" /><portlet:param name="detailDDMTemplateId" value="<%= String.valueOf(detailDDMTemplateId) %>" /></liferay-portlet:renderURL>');
    }
</aui:script>