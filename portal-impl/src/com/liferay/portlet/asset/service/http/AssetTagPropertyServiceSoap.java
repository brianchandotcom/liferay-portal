/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.asset.model.AssetTagPropertySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.asset.model.AssetTagProperty}, that is translated to a
 * {@link com.liferay.portlet.asset.model.AssetTagPropertySoap}. Methods that SOAP cannot
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
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyServiceHttp
 * @see       com.liferay.portlet.asset.model.AssetTagPropertySoap
 * @see       com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil
 * @generated
 */
public class AssetTagPropertyServiceSoap {
	/**
	* Adds an asset tag property.
	*
	* @param tagId the primary key of the tag
	* @param key the key to be associated to the value
	* @param value the value to which the key will refer
	* @return the created asset tag property
	* @throws PortalException if the user did not have permission to update
	the asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagPropertySoap addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTagProperty returnValue = AssetTagPropertyServiceUtil.addTagProperty(tagId,
					key, value);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the asset tag property with the specified ID.
	*
	* @param tagPropertyId the primary key of the asset tag property instance
	* @throws PortalException if an asset tag property with the primary key
	could not be found or if the user did not have permission to
	update the asset tag property
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTagProperty(long tagPropertyId)
		throws RemoteException {
		try {
			AssetTagPropertyServiceUtil.deleteTagProperty(tagPropertyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns all the asset tag property instances with the specified tag ID.
	*
	* @param tagId the primary key of the tag
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagPropertySoap[] getTagProperties(
		long tagId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> returnValue =
				AssetTagPropertyServiceUtil.getTagProperties(tagId);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns asset tag properties with the specified group and key.
	*
	* @param companyId the primary key of the company
	* @param key the key that refers to some value
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagPropertySoap[] getTagPropertyValues(
		long companyId, java.lang.String key) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> returnValue =
				AssetTagPropertyServiceUtil.getTagPropertyValues(companyId, key);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the asset tag property.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @param key the new key to be associated to the value
	* @param value the new value to which the key will refer
	* @return the updated asset tag property
	* @throws PortalException if an asset tag property with the primary key
	could not be found, if the user did not have permission to
	update the asset tag, or if the key or value were invalid
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagPropertySoap updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTagProperty returnValue = AssetTagPropertyServiceUtil.updateTagProperty(tagPropertyId,
					key, value);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagPropertyServiceSoap.class);
}