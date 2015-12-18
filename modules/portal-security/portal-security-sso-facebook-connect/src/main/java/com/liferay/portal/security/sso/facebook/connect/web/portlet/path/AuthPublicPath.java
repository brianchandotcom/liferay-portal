package com.liferay.portal.security.sso.facebook.connect.web.portlet.path;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = {
		"auth.public.path=/facebook_connect/facebook_connect_oauth"
	},
	service = Object.class
)
public class AuthPublicPath {
}