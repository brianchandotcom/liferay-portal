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

package com.liferay.portlet.asset.service.http;

import com.liferay.asset.kernel.service.AssetCategoryPropertyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AssetCategoryPropertyServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.asset.kernel.model.AssetCategoryPropertySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.asset.kernel.model.AssetCategoryProperty</code>, that is translated to a
 * <code>com.liferay.asset.kernel.model.AssetCategoryPropertySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPropertyServiceHttp
 * @deprecated As of Judson (7.1.x), replaced by {@link
 com.liferay.asset.category.property.service.impl.AssetCategoryPropertyServiceImpl}
 * @generated
 */
@Deprecated
public class AssetCategoryPropertyServiceSoap {

	public static com.liferay.asset.kernel.model.AssetCategoryPropertySoap
			addCategoryProperty(long entryId, String key, String value)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryProperty returnValue =
				AssetCategoryPropertyServiceUtil.addCategoryProperty(
					entryId, key, value);

			return com.liferay.asset.kernel.model.AssetCategoryPropertySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategoryProperty(long categoryPropertyId)
		throws RemoteException {

		try {
			AssetCategoryPropertyServiceUtil.deleteCategoryProperty(
				categoryPropertyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryPropertySoap[]
			getCategoryProperties(long entryId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategoryProperty>
				returnValue =
					AssetCategoryPropertyServiceUtil.getCategoryProperties(
						entryId);

			return com.liferay.asset.kernel.model.AssetCategoryPropertySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryPropertySoap[]
			getCategoryPropertyValues(long companyId, String key)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategoryProperty>
				returnValue =
					AssetCategoryPropertyServiceUtil.getCategoryPropertyValues(
						companyId, key);

			return com.liferay.asset.kernel.model.AssetCategoryPropertySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryPropertySoap
			updateCategoryProperty(
				long userId, long categoryPropertyId, String key, String value)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryProperty returnValue =
				AssetCategoryPropertyServiceUtil.updateCategoryProperty(
					userId, categoryPropertyId, key, value);

			return com.liferay.asset.kernel.model.AssetCategoryPropertySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryPropertySoap
			updateCategoryProperty(
				long categoryPropertyId, String key, String value)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryProperty returnValue =
				AssetCategoryPropertyServiceUtil.updateCategoryProperty(
					categoryPropertyId, key, value);

			return com.liferay.asset.kernel.model.AssetCategoryPropertySoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetCategoryPropertyServiceSoap.class);

}