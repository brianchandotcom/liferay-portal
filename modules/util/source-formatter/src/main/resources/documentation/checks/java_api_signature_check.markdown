## JavaAPISignatureCheck

The following types should be avoided in API method signatures:

**`HttpServletRequest`**, **`HttpServletResponse`**, **`ThemeDisplay`**, and
**`ServiceContext`**.

These are very loosely typed classes, since they can contain any object as
attributes or parameters. By using it as a parameter for a method in the API,
any user will have to rely on documentation to know how the request should be
populated. It also makes the API harder to test or debug.

Instead, pass the specific parameters attributes as method parameters. When
appropriate group them in a bean with highly cohesive fields.
___

For **`HttpServletRequest`**, **`HttpServletResponse`**, and **`ThemeDisplay`**,
the following exceptions are allowed:

1. The name of the class contains any of the following:

   `Action`, `Checker`, `Cookie`, `HTTP`, `Http`, `JSP`, `Language`, `Param`,
`Portal`, `Portlet`, `Renderer`, `Request`, `Session`, `Template`, `Theme`,
`URL`

2. The name of the method contains any of the following:

   `JSP`, `PortletURL`

3. The name of the method starts with any of the following:

   `include`, `render`

4. The name of the package contains any of the following:

   `alloy.mvc`, `auth`, `axis`, `display.context`, `http`, `jsp`,
`layoutconfiguration.util`, `portal.action`, `portal.events`, `portlet`,
`server.manager`, `servlet`, `spi.agent`, `sso`, `struts`

5. The name of the package starts with any of the following:

   `com.liferay.frontend`, `com.liferay.portal.jsonwebservice`,
`com.liferay.portal.language`, `com.liferay.portal.layoutconfiguration`

6. The name of the package contains both `template` and `internal`

7. The name of the package contains both `web` and `internal`

8. The name of the package contains both `web` and `util`
___
For **`ServiceContext`**, the following exceptions are allowed:

1. The name of the package contains `service`

2. The class is inside a module and the name of the module ends with `-service`