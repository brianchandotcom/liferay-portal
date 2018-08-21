/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.xml.local.resolver.internal.util;

import java.io.InputStream;

import org.xml.sax.InputSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Shin
 */
public class DefinitionsUtil {

	public static InputSource resolve(
		String publicId, String systemId, ClassLoader classLoader) {

		if (_logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Resolving ");
			sb.append(publicId);
			sb.append(" ");
			sb.append(systemId);

			if (_logger.isDebugEnabled()) {
				_logger.debug(sb.toString());
			}
		}

		if (publicId != null) {
			for (String[] publicIdArray : _PUBLIC_IDS) {
				if (publicId.equals(publicIdArray[0])) {
					InputStream inputStream = classLoader.getResourceAsStream(
						_DEFINITIONS_PATH + publicIdArray[1]);

					if (_logger.isDebugEnabled()) {
						String s = "Definition found for public id " + publicId;

						_logger.debug(s);
					}

					return new InputSource(inputStream);
				}
			}
		}
		else if (systemId != null) {
			for (String[] systemIdArray : _SYSTEM_IDS) {
				if (systemId.equals(systemIdArray[0])) {
					InputStream inputStream = classLoader.getResourceAsStream(
						_DEFINITIONS_PATH + systemIdArray[1]);

					if (_logger.isDebugEnabled()) {
						String s = "Definition found for system id " + systemId;

						_logger.debug(s);
					}

					InputSource inputSource = new InputSource(inputStream);

					inputSource.setSystemId(systemIdArray[0]);

					return inputSource;
				}
			}

			if (!systemId.startsWith("http://") &&
				!systemId.startsWith("https://")) {

				InputStream inputStream = classLoader.getResourceAsStream(
					systemId);

				if (inputStream != null) {
					InputSource inputSource = new InputSource(inputStream);

					inputSource.setSystemId(systemId);

					return inputSource;
				}
			}
		}

		return null;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		DefinitionsUtil.class);

	private static final String _DEFINITIONS_PATH =
		"com/liferay/xml/local/resolver/dependencies/definitions/";

	private static final String[][] _PUBLIC_IDS = {
		{"-//Apache Software Foundation//DTD Struts Configuration 1.2//EN", "struts-config_1_2.dtd"},
		{"-//Apache Software Foundation//DTD Tiles Configuration 1.1//EN", "tiles-config_1_1.dtd"},
		{"-//Hibernate/Hibernate Mapping DTD 3.0//EN", "hibernate-mapping-3.0.dtd"},
		{"-//MuleSource //DTD mule-configuration XML V1.0//EN", "mule-configuration.dtd"},
		{"-//SPRING//DTD BEAN//EN", "spring-beans.dtd"},
		{"-//Sun Microsystems, Inc.//DTD Facelet Taglib 1.0//EN", "facelet-taglib_1_0.dtd"},
		{"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN", "web-jsptaglibrary_1_2.dtd"},
		{"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.0//EN", "web-facesconfig_1_0.dtd"},
		{"-//Sun Microsystems, Inc.//DTD JavaServer Faces Config 1.1//EN", "web-facesconfig_1_1.dtd"},
		{"-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", "web-app_2_3.dtd"},
		{"-//W3C//DTD XMLSCHEMA 200102//EN", "XMLSchema.dtd"},

		{"datatypes", "datatypes.dtd"},

		{"-//Liferay//DTD Display 2.0.0//EN", "liferay-display_2_0_0.dtd"},
		{"-//Liferay//DTD Display 3.5.0//EN", "liferay-display_3_5_0.dtd"},
		{"-//Liferay//DTD Display 4.0.0//EN", "liferay-display_4_0_0.dtd"},
		{"-//Liferay//DTD Display 5.0.0//EN", "liferay-display_5_0_0.dtd"},
		{"-//Liferay//DTD Display 5.1.0//EN", "liferay-display_5_1_0.dtd"},
		{"-//Liferay//DTD Display 5.2.0//EN", "liferay-display_5_2_0.dtd"},
		{"-//Liferay//DTD Display 6.0.0//EN", "liferay-display_6_0_0.dtd"},
		{"-//Liferay//DTD Display 6.1.0//EN", "liferay-display_6_1_0.dtd"},
		{"-//Liferay//DTD Display 6.2.0//EN", "liferay-display_6_2_0.dtd"},
		{"-//Liferay//DTD Display 7.0.0//EN", "liferay-display_7_0_0.dtd"},
		{"-//Liferay//DTD Display 7.1.0//EN", "liferay-display_7_1_0.dtd"},
		{"-//Liferay//DTD Friendly URL Routes 6.0.0//EN", "liferay-friendly-url-routes_6_0_0.dtd"},
		{"-//Liferay//DTD Friendly URL Routes 6.1.0//EN", "liferay-friendly-url-routes_6_1_0.dtd"},
		{"-//Liferay//DTD Friendly URL Routes 6.2.0//EN", "liferay-friendly-url-routes_6_2_0.dtd"},
		{"-//Liferay//DTD Friendly URL Routes 7.0.0//EN", "liferay-friendly-url-routes_7_0_0.dtd"},
		{"-//Liferay//DTD Friendly URL Routes 7.1.0//EN", "liferay-friendly-url-routes_7_1_0.dtd"},
		{"-//Liferay//DTD Hook 5.1.0//EN", "liferay-hook_5_1_0.dtd"},
		{"-//Liferay//DTD Hook 5.2.0//EN", "liferay-hook_5_2_0.dtd"},
		{"-//Liferay//DTD Hook 6.0.0//EN", "liferay-hook_6_0_0.dtd"},
		{"-//Liferay//DTD Hook 6.1.0//EN", "liferay-hook_6_1_0.dtd"},
		{"-//Liferay//DTD Hook 6.2.0//EN", "liferay-hook_6_2_0.dtd"},
		{"-//Liferay//DTD Hook 7.0.0//EN", "liferay-hook_7_0_0.dtd"},
		{"-//Liferay//DTD Hook 7.1.0//EN", "liferay-hook_7_1_0.dtd"},
		{"-//Liferay//DTD Layout Templates 3.6.0//EN", "liferay-layout-templates_3_6_0.dtd"},
		{"-//Liferay//DTD Layout Templates 4.0.0//EN", "liferay-layout-templates_4_0_0.dtd"},
		{"-//Liferay//DTD Layout Templates 4.3.0//EN", "liferay-layout-templates_4_3_0.dtd"},
		{"-//Liferay//DTD Layout Templates 5.0.0//EN", "liferay-layout-templates_5_0_0.dtd"},
		{"-//Liferay//DTD Layout Templates 5.1.0//EN", "liferay-layout-templates_5_1_0.dtd"},
		{"-//Liferay//DTD Layout Templates 5.2.0//EN", "liferay-layout-templates_5_2_0.dtd"},
		{"-//Liferay//DTD Layout Templates 6.0.0//EN", "liferay-layout-templates_6_0_0.dtd"},
		{"-//Liferay//DTD Layout Templates 6.1.0//EN", "liferay-layout-templates_6_1_0.dtd"},
		{"-//Liferay//DTD Layout Templates 6.2.0//EN", "liferay-layout-templates_6_2_0.dtd"},
		{"-//Liferay//DTD Layout Templates 7.0.0//EN", "liferay-layout-templates_7_0_0.dtd"},
		{"-//Liferay//DTD Layout Templates 7.1.0//EN", "liferay-layout-templates_7_1_0.dtd"},
		{"-//Liferay//DTD Look and Feel 3.5.0//EN", "liferay-look-and-feel_3_5_0.dtd"},
		{"-//Liferay//DTD Look and Feel 4.0.0//EN", "liferay-look-and-feel_4_0_0.dtd"},
		{"-//Liferay//DTD Look and Feel 4.3.0//EN", "liferay-look-and-feel_4_3_0.dtd"},
		{"-//Liferay//DTD Look and Feel 5.0.0//EN", "liferay-look-and-feel_5_0_0.dtd"},
		{"-//Liferay//DTD Look and Feel 5.1.0//EN", "liferay-look-and-feel_5_1_0.dtd"},
		{"-//Liferay//DTD Look and Feel 5.2.0//EN", "liferay-look-and-feel_5_2_0.dtd"},
		{"-//Liferay//DTD Look and Feel 6.0.0//EN", "liferay-look-and-feel_6_0_0.dtd"},
		{"-//Liferay//DTD Look and Feel 6.1.0//EN", "liferay-look-and-feel_6_1_0.dtd"},
		{"-//Liferay//DTD Look and Feel 6.2.0//EN", "liferay-look-and-feel_6_2_0.dtd"},
		{"-//Liferay//DTD Look and Feel 7.0.0//EN", "liferay-look-and-feel_7_0_0.dtd"},
		{"-//Liferay//DTD Look and Feel 7.1.0//EN", "liferay-look-and-feel_7_1_0.dtd"},
		{"-//Liferay//DTD Plugin Package 4.3.0//EN", "liferay-plugin-package_4_3_0.dtd"},
		{"-//Liferay//DTD Plugin Package 5.0.0//EN", "liferay-plugin-package_5_0_0.dtd"},
		{"-//Liferay//DTD Plugin Package 5.1.0//EN", "liferay-plugin-package_5_1_0.dtd"},
		{"-//Liferay//DTD Plugin Package 5.2.0//EN", "liferay-plugin-package_5_2_0.dtd"},
		{"-//Liferay//DTD Plugin Package 6.0.0//EN", "liferay-plugin-package_6_0_0.dtd"},
		{"-//Liferay//DTD Plugin Package 6.1.0//EN", "liferay-plugin-package_6_1_0.dtd"},
		{"-//Liferay//DTD Plugin Package 6.2.0//EN", "liferay-plugin-package_6_2_0.dtd"},
		{"-//Liferay//DTD Plugin Package 7.0.0//EN", "liferay-plugin-package_7_0_0.dtd"},
		{"-//Liferay//DTD Plugin Package 7.1.0//EN", "liferay-plugin-package_7_1_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 4.3.0//EN", "liferay-plugin-repository_4_3_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 5.0.0//EN", "liferay-plugin-repository_5_0_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 5.1.0//EN", "liferay-plugin-repository_5_1_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 5.2.0//EN", "liferay-plugin-repository_5_2_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 6.0.0//EN", "liferay-plugin-repository_6_0_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 6.1.0//EN", "liferay-plugin-repository_6_1_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 6.2.0//EN", "liferay-plugin-repository_6_2_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 7.0.0//EN", "liferay-plugin-repository_7_0_0.dtd"},
		{"-//Liferay//DTD Plugin Repository 7.1.0//EN", "liferay-plugin-repository_7_1_0.dtd"},
		{"-//Liferay//DTD Portlet Application 3.5.0//EN", "liferay-portlet-app_3_5_0.dtd"},
		{"-//Liferay//DTD Portlet Application 4.0.0//EN", "liferay-portlet-app_4_0_0.dtd"},
		{"-//Liferay//DTD Portlet Application 4.1.0//EN", "liferay-portlet-app_4_1_0.dtd"},
		{"-//Liferay//DTD Portlet Application 4.2.0//EN", "liferay-portlet-app_4_2_0.dtd"},
		{"-//Liferay//DTD Portlet Application 4.3.0//EN", "liferay-portlet-app_4_3_0.dtd"},
		{"-//Liferay//DTD Portlet Application 4.3.1//EN", "liferay-portlet-app_4_3_1.dtd"},
		{"-//Liferay//DTD Portlet Application 4.3.2//EN", "liferay-portlet-app_4_3_2.dtd"},
		{"-//Liferay//DTD Portlet Application 4.3.3//EN", "liferay-portlet-app_4_3_3.dtd"},
		{"-//Liferay//DTD Portlet Application 4.3.6//EN", "liferay-portlet-app_4_3_6.dtd"},
		{"-//Liferay//DTD Portlet Application 4.4.0//EN", "liferay-portlet-app_4_4_0.dtd"},
		{"-//Liferay//DTD Portlet Application 5.0.0//EN", "liferay-portlet-app_5_0_0.dtd"},
		{"-//Liferay//DTD Portlet Application 5.1.0//EN", "liferay-portlet-app_5_1_0.dtd"},
		{"-//Liferay//DTD Portlet Application 5.2.0//EN", "liferay-portlet-app_5_2_0.dtd"},
		{"-//Liferay//DTD Portlet Application 6.0.0//EN", "liferay-portlet-app_6_0_0.dtd"},
		{"-//Liferay//DTD Portlet Application 6.1.0//EN", "liferay-portlet-app_6_1_0.dtd"},
		{"-//Liferay//DTD Portlet Application 6.2.0//EN", "liferay-portlet-app_6_2_0.dtd"},
		{"-//Liferay//DTD Portlet Application 7.0.0//EN", "liferay-portlet-app_7_0_0.dtd"},
		{"-//Liferay//DTD Portlet Application 7.1.0//EN", "liferay-portlet-app_7_1_0.dtd"},
		{"-//Liferay//DTD Resource Action Mapping 6.0.0//EN", "liferay-resource-action-mapping_6_0_0.dtd"},
		{"-//Liferay//DTD Resource Action Mapping 6.1.0//EN", "liferay-resource-action-mapping_6_1_0.dtd"},
		{"-//Liferay//DTD Resource Action Mapping 6.2.0//EN", "liferay-resource-action-mapping_6_2_0.dtd"},
		{"-//Liferay//DTD Resource Action Mapping 7.0.0//EN", "liferay-resource-action-mapping_7_0_0.dtd"},
		{"-//Liferay//DTD Resource Action Mapping 7.1.0//EN", "liferay-resource-action-mapping_7_1_0.dtd"},
		{"-//Liferay//DTD Service Builder 3.5.0//EN", "liferay-service-builder_3_5_0.dtd"},
		{"-//Liferay//DTD Service Builder 3.6.1//EN", "liferay-service-builder_3_6_1.dtd"},
		{"-//Liferay//DTD Service Builder 4.0.0//EN", "liferay-service-builder_4_0_0.dtd"},
		{"-//Liferay//DTD Service Builder 4.2.0//EN", "liferay-service-builder_4_2_0.dtd"},
		{"-//Liferay//DTD Service Builder 4.3.0//EN", "liferay-service-builder_4_3_0.dtd"},
		{"-//Liferay//DTD Service Builder 4.3.3//EN", "liferay-service-builder_4_3_3.dtd"},
		{"-//Liferay//DTD Service Builder 4.4.0//EN", "liferay-service-builder_4_4_0.dtd"},
		{"-//Liferay//DTD Service Builder 5.0.0//EN", "liferay-service-builder_5_0_0.dtd"},
		{"-//Liferay//DTD Service Builder 5.1.0//EN", "liferay-service-builder_5_1_0.dtd"},
		{"-//Liferay//DTD Service Builder 5.2.0//EN", "liferay-service-builder_5_2_0.dtd"},
		{"-//Liferay//DTD Service Builder 6.0.0//EN", "liferay-service-builder_6_0_0.dtd"},
		{"-//Liferay//DTD Service Builder 6.1.0//EN", "liferay-service-builder_6_1_0.dtd"},
		{"-//Liferay//DTD Service Builder 6.2.0//EN", "liferay-service-builder_6_2_0.dtd"},
		{"-//Liferay//DTD Service Builder 7.0.0//EN", "liferay-service-builder_7_0_0.dtd"},
		{"-//Liferay//DTD Service Builder 7.1.0//EN", "liferay-service-builder_7_1_0.dtd"},
		{"-//Liferay//DTD Social 6.1.0//EN", "liferay-social_6_1_0.dtd"},
		{"-//Liferay//DTD Social 6.2.0//EN", "liferay-social_6_2_0.dtd"},
		{"-//Liferay//DTD Social 7.0.0//EN", "liferay-social_7_0_0.dtd"},
		{"-//Liferay//DTD Social 7.1.0//EN", "liferay-social_7_1_0.dtd"},
		{"-//Liferay//DTD Theme Loader 4.3.0//EN", "liferay-theme-loader_4_3_0.dtd"},
		{"-//Liferay//DTD Theme Loader 5.0.0//EN", "liferay-theme-loader_5_0_0.dtd"},
		{"-//Liferay//DTD Theme Loader 5.1.0//EN", "liferay-theme-loader_5_1_0.dtd"},
		{"-//Liferay//DTD Theme Loader 5.2.0//EN", "liferay-theme-loader_5_2_0.dtd"},
		{"-//Liferay//DTD Theme Loader 6.0.0//EN", "liferay-theme-loader_6_0_0.dtd"},
		{"-//Liferay//DTD Theme Loader 6.1.0//EN", "liferay-theme-loader_6_1_0.dtd"},
		{"-//Liferay//DTD Theme Loader 6.2.0//EN", "liferay-theme-loader_6_2_0.dtd"},
		{"-//Liferay//DTD Theme Loader 7.0.0//EN", "liferay-theme-loader_7_0_0.dtd"},
		{"-//Liferay//DTD Theme Loader 7.1.0//EN", "liferay-theme-loader_7_1_0.dtd"},
		{"-//Liferay//DTD User Notification Definition 6.2.0//EN", "liferay-user-notification-definitions_6_2_0.dtd"},
		{"-//Liferay//DTD User Notification Definition 7.0.0//EN", "liferay-user-notification-definitions_7_0_0.dtd"},
		{"-//Liferay//DTD User Notification Definition 7.1.0//EN", "liferay-user-notification-definitions_7_1_0.dtd"}
	};

	private static final String[][] _SYSTEM_IDS = {
		{"http://java.sun.com/xml/ns/j2ee/j2ee_1_4.xsd", "j2ee_1_4.xsd"},
		{"http://java.sun.com/xml/ns/javaee/javaee_5.xsd", "javaee_5.xsd"},
		{"http://java.sun.com/xml/ns/javaee/javaee_6.xsd", "javaee_6.xsd"},
		{"http://java.sun.com/xml/ns/javaee/javaee_web_services_client_1_2.xsd", "javaee_web_services_client_1_2.xsd"},
		{"http://java.sun.com/xml/ns/javaee/javaee_web_services_client_1_3.xsd", "javaee_web_services_client_1_3.xsd"},
		{"http://java.sun.com/xml/ns/j2ee/jsp_2_0.xsd", "jsp_2_0.xsd"},
		{"http://java.sun.com/xml/ns/javaee/jsp_2_1.xsd", "jsp_2_1.xsd"},
		{"http://java.sun.com/xml/ns/javaee/jsp_2_2.xsd", "jsp_2_2.xsd"},
		{"http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd", "portlet-app_1_0.xsd"},
		{"http://java.sun.com/xml/ns/portlet/portlet-app_2_0.xsd", "portlet-app_2_0.xsd"},
		{"http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd", "web-app_2_4.xsd"},
		{"http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd", "web-jsptaglibrary_2_0.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd", "web-app_2_5.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd", "web-app_3_0.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-common_3_0.xsd", "web-common_3_0.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-facesconfig_1_2.xsd", "web-facesconfig_1_2.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd", "web-facesconfig_2_0.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-facesconfig_2_1.xsd", "web-facesconfig_2_1.xsd"},
		{"http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd", "web-jsptaglibrary_2_1.xsd"},
		{"http://www.ibm.com/webservices/xsd/j2ee_web_services_client_1_1.xsd", "j2ee_web_services_client_1_1.xsd"},
		{"http://www.w3.org/2001/xml.xsd", "xml.xsd"},

		{"http://www.liferay.com/dtd/liferay-ddm-structure_6_2_0.xsd", "liferay-ddm-structure_6_2_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-ddm-structure_7_0_0.xsd", "liferay-ddm-structure_7_0_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-ddm-structure_7_1_0.xsd", "liferay-ddm-structure_7_1_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-workflow-definition_6_0_0.xsd", "liferay-workflow-definition_6_0_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-workflow-definition_6_1_0.xsd", "liferay-workflow-definition_6_1_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-workflow-definition_6_2_0.xsd", "liferay-workflow-definition_6_2_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-workflow-definition_7_0_0.xsd", "liferay-workflow-definition_7_0_0.xsd"},
		{"http://www.liferay.com/dtd/liferay-workflow-definition_7_1_0.xsd", "liferay-workflow-definition_7_1_0.xsd"}
	};

}