package com.liferay.portal.kernel.portlet;


import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Tomas Polesovsky
 */
public class PortletContainerSecurityUtil {

	public static PortletContainerSecurity getPortletContainerSecurity() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletContainerSecurity.class);

		return _portletContainerSecurity;
	}

	public void setPortletContainerSecurity(
		PortletContainerSecurity portletContainerSecurity){

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletContainerSecurity = portletContainerSecurity;
	}

	private static PortletContainerSecurity _portletContainerSecurity;
}
