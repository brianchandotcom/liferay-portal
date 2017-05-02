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

package com.liferay.portlet.exportimport.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.service.StagingServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link StagingServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
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
 * @author Brian Wing Shun Chan
 * @see StagingServiceHttp
 * @see StagingServiceUtil
 * @generated
 */
@ProviderType
public class StagingServiceSoap {
	public static void cleanUpStagingRequest(long stagingRequestId)
		throws RemoteException {
		try {
			StagingServiceUtil.cleanUpStagingRequest(stagingRequestId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long createStagingRequest(long groupId,
		java.lang.String checksum) throws RemoteException {
		try {
			long returnValue = StagingServiceUtil.createStagingRequest(groupId,
					checksum);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void propagateExportImportLifecycleEvent(int code,
		int processFlag, java.lang.String processId,
		java.util.List<java.io.Serializable> arguments)
		throws RemoteException {
		try {
			StagingServiceUtil.propagateExportImportLifecycleEvent(code,
				processFlag, processId, arguments);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.exportimport.kernel.lar.MissingReferences publishStagingRequest(
		long stagingRequestId,
		com.liferay.exportimport.kernel.model.ExportImportConfigurationSoap exportImportConfiguration)
		throws RemoteException {
		try {
			com.liferay.exportimport.kernel.lar.MissingReferences returnValue = StagingServiceUtil.publishStagingRequest(stagingRequestId,
					com.liferay.portlet.exportimport.model.impl.ExportImportConfigurationModelImpl.toModel(
						exportImportConfiguration));

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateStagingRequest(long stagingRequestId,
		java.lang.String fileName, byte[] bytes) throws RemoteException {
		try {
			StagingServiceUtil.updateStagingRequest(stagingRequestId, fileName,
				bytes);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(StagingServiceSoap.class);
}