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

import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>AssetTagServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.asset.kernel.model.AssetTagSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.asset.kernel.model.AssetTag</code>, that is translated to a
 * <code>com.liferay.asset.kernel.model.AssetTagSoap</code>. Methods that SOAP
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
 * @see AssetTagServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AssetTagServiceSoap {

	public static com.liferay.asset.kernel.model.AssetTagSoap addTag(
			long groupId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetTag returnValue =
				AssetTagServiceUtil.addTag(groupId, name, serviceContext);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteTag(long tagId) throws RemoteException {
		try {
			AssetTagServiceUtil.deleteTag(tagId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteTags(long[] tagIds) throws RemoteException {
		try {
			AssetTagServiceUtil.deleteTags(tagIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getGroupsTags(
			long[] groupIds)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getGroupsTags(groupIds);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getGroupTags(
			long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getGroupTags(groupId);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getGroupTags(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetTag> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getGroupTags(
					groupId, start, end, orderByComparator);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getGroupTagsCount(long groupId) throws RemoteException {
		try {
			int returnValue = AssetTagServiceUtil.getGroupTagsCount(groupId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagDisplay
			getGroupTagsDisplay(long groupId, String name, int start, int end)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetTagDisplay returnValue =
				AssetTagServiceUtil.getGroupTagsDisplay(
					groupId, name, start, end);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap getTag(long tagId)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetTag returnValue =
				AssetTagServiceUtil.getTag(tagId);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long groupId, long classNameId, String name)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupId, classNameId, name);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long groupId, long classNameId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetTag> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupId, classNameId, name, start, end, orderByComparator);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long groupId, String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupId, name, start, end);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetTag> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupId, name, start, end, orderByComparator);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long[] groupIds, String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupIds, name, start, end);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			long[] groupIds, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetTag> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(
					groupIds, name, start, end, orderByComparator);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap[] getTags(
			String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetTag>
				returnValue = AssetTagServiceUtil.getTags(className, classPK);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getTagsCount(long groupId, String name)
		throws RemoteException {

		try {
			int returnValue = AssetTagServiceUtil.getTagsCount(groupId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getTagsCount(long[] groupIds, String name)
		throws RemoteException {

		try {
			int returnValue = AssetTagServiceUtil.getTagsCount(groupIds, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getVisibleAssetsTagsCount(
			long groupId, long classNameId, String name)
		throws RemoteException {

		try {
			int returnValue = AssetTagServiceUtil.getVisibleAssetsTagsCount(
				groupId, classNameId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getVisibleAssetsTagsCount(long groupId, String name)
		throws RemoteException {

		try {
			int returnValue = AssetTagServiceUtil.getVisibleAssetsTagsCount(
				groupId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void mergeTags(long fromTagId, long toTagId)
		throws RemoteException {

		try {
			AssetTagServiceUtil.mergeTags(fromTagId, toTagId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void mergeTags(long[] fromTagIds, long toTagId)
		throws RemoteException {

		try {
			AssetTagServiceUtil.mergeTags(fromTagIds, toTagId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String search(long groupId, String name, int start, int end)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				AssetTagServiceUtil.search(groupId, name, start, end);

			return returnValue.toString();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static String search(
			long[] groupIds, String name, int start, int end)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				AssetTagServiceUtil.search(groupIds, name, start, end);

			return returnValue.toString();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetTagSoap updateTag(
			long tagId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetTag returnValue =
				AssetTagServiceUtil.updateTag(tagId, name, serviceContext);

			return com.liferay.asset.kernel.model.AssetTagSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagServiceSoap.class);

}