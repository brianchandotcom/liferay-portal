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

package com.liferay.portlet.softwarecatalog.action;

import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public class ActionUtil {

	public static void getFrameworkVersion(HttpServletRequest request)
		throws Exception {

		long frameworkVersionId = ParamUtil.getLong(
			request, "frameworkVersionId");

		SCFrameworkVersion frameworkVersion = null;

		if (frameworkVersionId > 0) {
			frameworkVersion =
				SCFrameworkVersionServiceUtil.getFrameworkVersion(
					frameworkVersionId);
		}

		request.setAttribute(
			WebKeys.SOFTWARE_CATALOG_FRAMEWORK_VERSION, frameworkVersion);
	}

	public static void getFrameworkVersion(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getFrameworkVersion(request);
	}

	public static void getLicense(HttpServletRequest request) throws Exception {
		long licenseId = ParamUtil.getLong(request, "licenseId");

		SCLicense license = null;

		if (licenseId > 0) {
			license = SCLicenseServiceUtil.getLicense(licenseId);
		}

		request.setAttribute(WebKeys.SOFTWARE_CATALOG_LICENSE, license);
	}

	public static void getLicense(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getLicense(request);
	}

	public static void getProductEntry(HttpServletRequest request)
		throws Exception {

		long productEntryId = ParamUtil.getLong(request, "productEntryId");

		SCProductEntry productEntry = null;

		if (productEntryId > 0) {
			productEntry = SCProductEntryServiceUtil.getProductEntry(
				productEntryId);
		}

		request.setAttribute(
			WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY, productEntry);
	}

	public static void getProductEntry(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getProductEntry(request);
	}

	public static void getProductVersion(HttpServletRequest request)
		throws Exception {

		long productVersionId = ParamUtil.getLong(request, "productVersionId");
		long copyProductVersionId = ParamUtil.getLong(
			request, "copyProductVersionId");

		SCProductVersion productVersion = null;
		SCProductEntry productEntry = null;

		if (productVersionId > 0) {
			productVersion = SCProductVersionServiceUtil.getProductVersion(
				productVersionId);

			productEntry = SCProductEntryServiceUtil.getProductEntry(
				productVersion.getProductEntryId());

			request.setAttribute(
				WebKeys.SOFTWARE_CATALOG_PRODUCT_VERSION, productVersion);

			request.setAttribute(
				WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY, productEntry);
		}
		else if (copyProductVersionId > 0) {
			productVersion = SCProductVersionServiceUtil.getProductVersion(
				copyProductVersionId);

			productEntry = SCProductEntryServiceUtil.getProductEntry(
				productVersion.getProductEntryId());

			String oldVersion = productVersion.getVersion();

			Version version = Version.getInstance(oldVersion);

			version = Version.incrementBuildNumber(version);

			String newVersion = version.toString();

			productVersion.setVersion(newVersion);

			String directDownloadURL = productVersion.getDirectDownloadURL();

			directDownloadURL = StringUtil.replace(
				directDownloadURL, oldVersion, newVersion);

			productVersion.setDirectDownloadURL(directDownloadURL);

			request.setAttribute(
				WebKeys.SOFTWARE_CATALOG_PRODUCT_VERSION, productVersion);

			request.setAttribute(
				WebKeys.SOFTWARE_CATALOG_PRODUCT_ENTRY, productEntry);
		}
		else {
			getProductEntry(request);
		}
	}

	public static void getProductVersion(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getProductVersion(request);
	}

}