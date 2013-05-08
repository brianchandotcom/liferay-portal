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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.softwarecatalog.model.SCProductVersion}, that is translated to a
 * {@link com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductVersionServiceHttp
 * @see       com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap
 * @see       com.liferay.portlet.softwarecatalog.service.SCProductVersionServiceUtil
 * @generated
 */
public class SCProductVersionServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap addProductVersion(
		long productEntryId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.addProductVersion(productEntryId,
					version, changeLog, downloadPageURL, directDownloadURL,
					testDirectDownloadURL, repoStoreArtifact,
					frameworkVersionIds, serviceContext);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteProductVersion(long productVersionId)
		throws RemoteException {
		try {
			SCProductVersionServiceUtil.deleteProductVersion(productVersionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap getProductVersion(
		long productVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.getProductVersion(productVersionId);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap[] getProductVersions(
		long productEntryId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> returnValue =
				SCProductVersionServiceUtil.getProductVersions(productEntryId,
					start, end);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getProductVersionsCount(long productEntryId)
		throws RemoteException {
		try {
			int returnValue = SCProductVersionServiceUtil.getProductVersionsCount(productEntryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap updateProductVersion(
		long productVersionId, java.lang.String version,
		java.lang.String changeLog, java.lang.String downloadPageURL,
		java.lang.String directDownloadURL, boolean testDirectDownloadURL,
		boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCProductVersion returnValue =
				SCProductVersionServiceUtil.updateProductVersion(productVersionId,
					version, changeLog, downloadPageURL, directDownloadURL,
					testDirectDownloadURL, repoStoreArtifact,
					frameworkVersionIds);

			return com.liferay.portlet.softwarecatalog.model.SCProductVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCProductVersionServiceSoap.class);
}