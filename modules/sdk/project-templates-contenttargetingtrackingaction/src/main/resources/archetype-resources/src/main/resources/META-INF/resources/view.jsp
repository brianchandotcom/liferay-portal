<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.content.targeting.util.ContentTargetingUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("context");
%>

<aui:input helpMessage="name-help" label="name" name='<%= ContentTargetingUtil.GUID_REPLACEMENT + "alias" %>' type="text" value='<%= GetterUtil.getString(context.get("alias")) %>'>
	<aui:validator name="required" />
</aui:input>

<aui:input helpMessage="enter-the-id-of-the-link-to-be-tracked" label="element-id" name='<%= ContentTargetingUtil.GUID_REPLACEMENT + "elementId" %>' type="text" value='<%= GetterUtil.getString(context.get("elementId")) %>'>
	<aui:validator name="required" />
</aui:input>

<aui:select label="event-type" name='<%= ContentTargetingUtil.GUID_REPLACEMENT + "eventType" %>'>

	<%
	for (String eventType : (List<String>)context.get("eventTypes")) {
	%>

		<aui:option label="<%= eventType %>" selected='<%= eventType.equals(GetterUtil.getString(context.get("eventType"), "click")) %>' value="<%= eventType %>" />

	<%
	}
	%>

</aui:select>