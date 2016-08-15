<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.content.targeting.util.ContentTargetingUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.Map" %><%@
page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("context");
%>

<liferay-ui:message key="these-are-custom-settings-for-users-configuring-your-report" />

<aui:select name="setting1" value='<%= GetterUtil.getString(context.get("setting1")) %>'>
	<aui:option label="A" value="A" />
	<aui:option label="B" value="B" />
	<aui:option label="C" value="C" />
</aui:select>

<aui:input name="setting2" value='<%= GetterUtil.getString(context.get("setting2")) %>' />