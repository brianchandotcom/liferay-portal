<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("context");
%>

<div class="card main-content-card taglib-empty-result-message">
	<div class="card-body">
		<div class="taglib-empty-result-message-header"></div>

		<div class="text-center text-muted">
			<liferay-ui:message key="your-data-should-be-displayed-here" />

			<p>
				Setting 1: <%= GetterUtil.getString(context.get("setting1")) %>
			</p>

			<p>
				Setting 2: <%= GetterUtil.getString(context.get("setting2")) %>
			</p>
		</div>
	</div>
</div>