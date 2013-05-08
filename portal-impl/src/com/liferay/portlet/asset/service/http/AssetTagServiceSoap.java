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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.asset.service.AssetTagServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.asset.model.AssetTagSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.asset.model.AssetTag}, that is translated to a
 * {@link com.liferay.portlet.asset.model.AssetTagSoap}. Methods that SOAP cannot
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
 * @see       AssetTagServiceHttp
 * @see       com.liferay.portlet.asset.model.AssetTagSoap
 * @see       com.liferay.portlet.asset.service.AssetTagServiceUtil
 * @generated
 */
public class AssetTagServiceSoap {
	public static com.liferay.portlet.asset.model.AssetTagSoap addTag(
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.addTag(name,
					tagProperties, serviceContext);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTag(long tagId) throws RemoteException {
		try {
			AssetTagServiceUtil.deleteTag(tagId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTags(long[] tagIds) throws RemoteException {
		try {
			AssetTagServiceUtil.deleteTags(tagIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getGroupsTags(
		long[] groupIds) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getGroupsTags(groupIds);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getGroupTags(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getGroupTags(groupId);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getGroupTags(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getGroupTags(groupId, start, end, obc);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getGroupTagsCount(long groupId) throws RemoteException {
		try {
			int returnValue = AssetTagServiceUtil.getGroupTagsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagDisplay getGroupTagsDisplay(
		long groupId, java.lang.String name, int start, int end)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTagDisplay returnValue = AssetTagServiceUtil.getGroupTagsDisplay(groupId,
					name, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getGroupTagsDisplay(long,
	String, int, int)}
	*/
	public static java.lang.String getJSONGroupTags(long groupId,
		java.lang.String name, int start, int end) throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONObject returnValue = AssetTagServiceUtil.getJSONGroupTags(groupId,
					name, start, end);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap getTag(
		long tagId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.getTag(tagId);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		long groupId, long classNameId, java.lang.String name)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(groupId, classNameId, name);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(groupId, classNameId, name, start,
					end, obc);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(groupId, name, tagProperties,
					start, end);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		long[] groupIds, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(groupIds, name, tagProperties,
					start, end);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(className, classPK);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getTagsCount(long groupId, long classNameId,
		java.lang.String name) throws RemoteException {
		try {
			int returnValue = AssetTagServiceUtil.getTagsCount(groupId,
					classNameId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getTagsCount(long groupId, java.lang.String name)
		throws RemoteException {
		try {
			int returnValue = AssetTagServiceUtil.getTagsCount(groupId, name);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getTagsCount(long groupId, java.lang.String name,
		java.lang.String[] tagProperties) throws RemoteException {
		try {
			int returnValue = AssetTagServiceUtil.getTagsCount(groupId, name,
					tagProperties);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void mergeTags(long fromTagId, long toTagId,
		boolean overrideProperties) throws RemoteException {
		try {
			AssetTagServiceUtil.mergeTags(fromTagId, toTagId, overrideProperties);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void mergeTags(long[] fromTagIds, long toTagId,
		boolean overrideProperties) throws RemoteException {
		try {
			AssetTagServiceUtil.mergeTags(fromTagIds, toTagId,
				overrideProperties);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String search(long groupId, java.lang.String name,
		java.lang.String[] tagProperties, int start, int end)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue = AssetTagServiceUtil.search(groupId,
					name, tagProperties, start, end);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String search(long[] groupIds,
		java.lang.String name, java.lang.String[] tagProperties, int start,
		int end) throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue = AssetTagServiceUtil.search(groupIds,
					name, tagProperties, start, end);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap updateTag(
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.updateTag(tagId,
					name, tagProperties, serviceContext);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagServiceSoap.class);
}