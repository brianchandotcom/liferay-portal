/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.io.InputStream;

import org.apache.xerces.xni.XNIException;

import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityResolver implements org.xml.sax.EntityResolver {

	@Override
	public InputSource resolveEntity(String publicId, String systemId) {
		ClassLoader classLoader = getClass().getClassLoader();

		if (_log.isDebugEnabled()) {
			_log.debug("Resolving entity " + publicId + " " + systemId);
		}

		if (publicId != null) {
			for (int i = 0; i < _PUBLIC_IDS.length; i++) {
				KeyValuePair kvp = _PUBLIC_IDS[i];

				if (publicId.equals(kvp.getKey())) {
					InputStream is = classLoader.getResourceAsStream(
						_DEFINITIONS_PATH + kvp.getValue());

					if (is == null) {
						is = classLoader.getResourceAsStream(kvp.getValue());
					}

					if (_log.isDebugEnabled()) {
						_log.debug("Entity found for public id " + publicId);
					}

					return new InputSource(is);
				}
			}
		}
		else if (systemId != null) {
			for (int i = 0; i < _SYSTEM_IDS.length; i++) {
				KeyValuePair kvp = _SYSTEM_IDS[i];

				if (systemId.equals(kvp.getKey())) {
					InputStream is = classLoader.getResourceAsStream(
						_DEFINITIONS_PATH + kvp.getValue());

					if (is == null) {
						is = classLoader.getResourceAsStream(kvp.getValue());
					}

					if (_log.isDebugEnabled()) {
						_log.debug("Entity found for system id " + systemId);
					}

					InputSource inputSource = new InputSource(is);

					inputSource.setSystemId(kvp.getKey());

					return inputSource;
				}
			}

			if (!systemId.endsWith(".dtd") && !systemId.endsWith(".xsd")) {
				throw new XNIException("Invalid system id " + systemId);
			}

			if (!systemId.startsWith(Http.HTTP_WITH_SLASH) &&
				!systemId.startsWith(Http.HTTPS_WITH_SLASH)) {

				InputStream inputStream = classLoader.getResourceAsStream(
					systemId);

				if (inputStream != null) {
					InputSource inputSource = new InputSource(inputStream);

					inputSource.setSystemId(systemId);

					return inputSource;
				}
				else {
					throw new XNIException("Invalid system id " + systemId);
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("No entity found for " + publicId + " " + systemId);
		}

		return null;
	}

	private static final String _DEFINITIONS_PATH =
		"com/liferay/portal/definitions/";

	private static final KeyValuePair[] _PUBLIC_IDS = {
		new KeyValuePair(
			"datatypes", "datatypes.dtd"
		),

		new KeyValuePair(
			"-//Sun Microsystems, Inc.//DTD Facelet Taglib 1.0//EN",
			"facelet-taglib_1_0.dtd"
		),

		new KeyValuePair(
			"-//Hibernate/Hibernate Mapping DTD 3.0//EN",
			"hibernate-mapping-3.0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 2.0.0//EN", "liferay-display_2_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 3.5.0//EN", "liferay-display_3_5_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 4.0.0//EN", "liferay-display_4_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 5.0.0//EN", "liferay-display_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 5.1.0//EN", "liferay-display_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 5.2.0//EN", "liferay-display_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 6.0.0//EN", "liferay-display_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 6.1.0//EN", "liferay-display_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Display 6.2.0//EN", "liferay-display_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Friendly URL Routes 6.0.0//EN",
			"liferay-friendly-url-routes_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Friendly URL Routes 6.1.0//EN",
			"liferay-friendly-url-routes_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Friendly URL Routes 6.2.0//EN",
			"liferay-friendly-url-routes_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Hook 5.1.0//EN", "liferay-hook_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Hook 5.2.0//EN", "liferay-hook_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Hook 6.0.0//EN", "liferay-hook_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Hook 6.1.0//EN", "liferay-hook_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Hook 6.2.0//EN", "liferay-hook_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 3.6.0//EN",
			"liferay-layout-templates_3_6_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 4.0.0//EN",
			"liferay-layout-templates_4_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 4.3.0//EN",
			"liferay-layout-templates_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 5.0.0//EN",
			"liferay-layout-templates_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 5.1.0//EN",
			"liferay-layout-templates_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 5.2.0//EN",
			"liferay-layout-templates_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 6.0.0//EN",
			"liferay-layout-templates_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 6.1.0//EN",
			"liferay-layout-templates_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Layout Templates 6.2.0//EN",
			"liferay-layout-templates_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 3.5.0//EN",
			"liferay-look-and-feel_3_5_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 4.0.0//EN",
			"liferay-look-and-feel_4_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 4.3.0//EN",
			"liferay-look-and-feel_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 5.0.0//EN",
			"liferay-look-and-feel_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 5.1.0//EN",
			"liferay-look-and-feel_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 5.2.0//EN",
			"liferay-look-and-feel_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 6.0.0//EN",
			"liferay-look-and-feel_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 6.1.0//EN",
			"liferay-look-and-feel_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Look and Feel 6.2.0//EN",
			"liferay-look-and-feel_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 4.3.0//EN",
			"liferay-plugin-package_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 5.0.0//EN",
			"liferay-plugin-package_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 5.1.0//EN",
			"liferay-plugin-package_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 5.2.0//EN",
			"liferay-plugin-package_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 6.0.0//EN",
			"liferay-plugin-package_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 6.1.0//EN",
			"liferay-plugin-package_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Package 6.2.0//EN",
			"liferay-plugin-package_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 4.3.0//EN",
			"liferay-plugin-repository_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 5.0.0//EN",
			"liferay-plugin-repository_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 5.1.0//EN",
			"liferay-plugin-repository_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 5.2.0//EN",
			"liferay-plugin-repository_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 6.0.0//EN",
			"liferay-plugin-repository_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 6.1.0//EN",
			"liferay-plugin-repository_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Plugin Repository 6.2.0//EN",
			"liferay-plugin-repository_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 3.5.0//EN",
			"liferay-portlet-app_3_5_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.0.0//EN",
			"liferay-portlet-app_4_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.1.0//EN",
			"liferay-portlet-app_4_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.2.0//EN",
			"liferay-portlet-app_4_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.3.0//EN",
			"liferay-portlet-app_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.3.1//EN",
			"liferay-portlet-app_4_3_1.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.3.2//EN",
			"liferay-portlet-app_4_3_2.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.3.3//EN",
			"liferay-portlet-app_4_3_3.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.3.6//EN",
			"liferay-portlet-app_4_3_6.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 4.4.0//EN",
			"liferay-portlet-app_4_4_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 5.0.0//EN",
			"liferay-portlet-app_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 5.1.0//EN",
			"liferay-portlet-app_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 5.2.0//EN",
			"liferay-portlet-app_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 6.0.0//EN",
			"liferay-portlet-app_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 6.1.0//EN",
			"liferay-portlet-app_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Portlet Application 6.2.0//EN",
			"liferay-portlet-app_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Resource Action Mapping 6.0.0//EN",
			"liferay-resource-action-mapping_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Resource Action Mapping 6.1.0//EN",
			"liferay-resource-action-mapping_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Resource Action Mapping 6.2.0//EN",
			"liferay-resource-action-mapping_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 3.5.0//EN",
			"liferay-service-builder_3_5_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 3.6.1//EN",
			"liferay-service-builder_3_6_1.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 4.0.0//EN",
			"liferay-service-builder_4_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 4.2.0//EN",
			"liferay-service-builder_4_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 4.3.0//EN",
			"liferay-service-builder_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 4.3.3//EN",
			"liferay-service-builder_4_3_3.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 4.4.0//EN",
			"liferay-service-builder_4_4_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 5.0.0//EN",
			"liferay-service-builder_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 5.1.0//EN",
			"liferay-service-builder_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 5.2.0//EN",
			"liferay-service-builder_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 6.0.0//EN",
			"liferay-service-builder_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 6.1.0//EN",
			"liferay-service-builder_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Service Builder 6.2.0//EN",
			"liferay-service-builder_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Social 6.1.0//EN", "liferay-social_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Social 6.2.0//EN", "liferay-social_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 4.3.0//EN",
			"liferay-theme-loader_4_3_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 5.0.0//EN",
			"liferay-theme-loader_5_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 5.1.0//EN",
			"liferay-theme-loader_5_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 5.2.0//EN",
			"liferay-theme-loader_5_2_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 6.0.0//EN",
			"liferay-theme-loader_6_0_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 6.1.0//EN",
			"liferay-theme-loader_6_1_0.dtd"
		),

		new KeyValuePair(
			"-//Liferay//DTD Theme Loader 6.2.0//EN",
			"liferay-theme-loader_6_2_0.dtd"
		),

		new KeyValuePair(
			"-//MuleSource //DTD mule-configuration XML V1.0//EN",
			"mule-configuration.dtd"
		),

		new KeyValuePair(
			"-//SPRING//DTD BEAN//EN", "spring-beans.dtd"
		),

		new KeyValuePair(
			"-//Apache Software Foundation//DTD Struts Configuration 1.2//EN",
			"struts-config_1_2.dtd"
		),

		new KeyValuePair(
			"-//Apache Software Foundation//DTD Tiles Configuration 1.1//EN",
			"tiles-config_1_1.dtd"
		),

		new KeyValuePair(
			"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN",
			"web-app_2_3.dtd"
		),

		new KeyValuePair(
			"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.0//EN",
			"web-facesconfig_1_0.dtd"
		),

		new KeyValuePair(
			"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN",
			"web-facesconfig_1_1.dtd"
		),

		new KeyValuePair(
			"-//W3C//DTD XMLSCHEMA 200102//EN", "XMLSchema.dtd"
		)
	};

	private static final KeyValuePair[] _SYSTEM_IDS = {
		new KeyValuePair(
			"http://java.sun.com/xml/ns/j2ee/j2ee_1_4.xsd", "j2ee_1_4.xsd"
		),

		new KeyValuePair(
			"http://www.ibm.com/webservices/xsd/" +
				"j2ee_web_services_client_1_1.xsd",
			"j2ee_web_services_client_1_1.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/javaee_5.xsd", "javaee_5.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/javaee_6.xsd", "javaee_6.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/" +
				"javaee_web_services_client_1_2.xsd",
			"javaee_web_services_client_1_2.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/" +
				"javaee_web_services_client_1_3.xsd",
			"javaee_web_services_client_1_3.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/j2ee/jsp_2_0.xsd", "jsp_2_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/jsp_2_1.xsd", "jsp_2_1.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/jsp_2_2.xsd", "jsp_2_2.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd",
			"portlet-app_1_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd",
			"portlet-app_2_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd", "web-app_2_4.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd",
			"web-app_2_5.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd",
			"web-app_3_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-common_3_0.xsd",
			"web-common_3_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-facesconfig_1_2.xsd",
			"web-facesconfig_1_2.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd",
			"web-facesconfig_2_0.xsd"
		),

		new KeyValuePair(
			"http://java.sun.com/xml/ns/javaee/web-facesconfig_2_1.xsd",
			"web-facesconfig_2_1.xsd"
		),

		new KeyValuePair(
			"http://www.liferay.com/dtd/liferay-workflow-definition_6_0_0.xsd",
			"liferay-workflow-definition_6_0_0.xsd"
		),

		new KeyValuePair(
			"http://www.liferay.com/dtd/liferay-workflow-definition_6_1_0.xsd",
			"liferay-workflow-definition_6_1_0.xsd"
		),

		new KeyValuePair(
			"http://www.w3.org/2001/xml.xsd", "xml.xsd"
		)
	};

	private static Log _log = LogFactoryUtil.getLog(EntityResolver.class);

}