## JSPSendRedirectCheck

Calling `HttpServletResponse.sendRedirect` from a JSP is bad practice. If the
response already has been committed, the method method will throw an
`IllegalStateException` when using WebLogic as the application server.