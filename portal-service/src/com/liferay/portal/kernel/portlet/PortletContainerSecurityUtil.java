package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Set;

/**
 * @author Tomas Polesovsky
 */
public class PortletContainerSecurityUtil {

	public static Set<String> getPortletAddDefaultResourceCheckWhitelist() {
		return getPortletContainerSecurity().
			getPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		getPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortletContainerSecurity().
			getPortletAddDefaultResourceCheckWhitelistActions();
	}

	public static PortletContainerSecurity getPortletContainerSecurity() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletContainerSecurity.class);

		return _portletContainerSecurity;
	}

	public static Set<String> resetPortletAddDefaultResourceCheckWhitelist() {
		return getPortletContainerSecurity().
			resetPortletAddDefaultResourceCheckWhitelist();
	}

	public static Set<String>
		resetPortletAddDefaultResourceCheckWhitelistActions() {

		return getPortletContainerSecurity().
			resetPortletAddDefaultResourceCheckWhitelistActions();
	}

	public void setPortletContainerSecurity(
		PortletContainerSecurity portletContainerSecurity){

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletContainerSecurity = portletContainerSecurity;
	}

	private static PortletContainerSecurity _portletContainerSecurity;
}
