<%@ taglib uri="/META-INF/aui.tld" prefix="aui" %>
<%@ taglib uri="/META-INF/c.tld" prefix="c" %>
<%@ taglib uri="/META-INF/x.tld" prefix="x" %>
<%@ taglib uri="/META-INF/liferay-portlet_2_0.tld" prefix="portlet" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<portlet:defineObjects />

<c:choose>
	<c:when test="${empty param.name}">
		<c:set var="greeting" value="${param.name}"/>
	</c:when>
	<c:otherwise>
		<c:set var="greeting" value="World"/>
	</c:otherwise>
</c:choose>

Hello <c:out value="greeting" />!

<portlet:renderURL var="url">
	<portlet:param name="name" value="Ray" />
</portlet:renderURL>

<aui:a href="${url}">URL</aui:a>

<h2>Some Books processed by XML tags.</h2>

<c:import var="bookInfo" url="http://localhost:8080${pageContext.servletContext.contextPath}/html/books.xml"/>

<x:parse xml="${bookInfo}" var="output"/>

<b>The title of the first book is</b>:

<x:out select="$output/books/book[1]/name" />

<br>

<b>The price of the second book</b>:

<x:out select="$output/books/book[2]/price" />