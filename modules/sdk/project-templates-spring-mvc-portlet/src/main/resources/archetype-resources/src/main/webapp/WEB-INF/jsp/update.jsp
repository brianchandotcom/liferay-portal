
<%@ include file="/WEB-INF/jsp/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

%>

<portlet:actionURL name="updateURL" var="updateURL">
	<portlet:param name="action" value="update" />
</portlet:actionURL>

<aui:form action="<%= updateURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= foo == null ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='update'
	/>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>
