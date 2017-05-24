
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

	<aui:model-context bean="<%= foo %>" model="<%= Foo.class %>" />

	<aui:fieldset>
		<aui:input name="field1" />

		<aui:input name="field2" />

		<aui:input name="field3" />

		<aui:input name="field4" />

		<aui:input name="field5" />

		<aui:input name="categories" type="assetCategories" />

		<aui:input name="tags" type="assetTags" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>
