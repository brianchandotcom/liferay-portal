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

package com.liferay.message.boards.service.http;

import com.liferay.message.boards.service.MBCategoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>MBCategoryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.message.boards.model.MBCategorySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.message.boards.model.MBCategory</code>, that is translated to a
 * <code>com.liferay.message.boards.model.MBCategorySoap</code>. Methods that SOAP
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
 * @see MBCategoryServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class MBCategoryServiceSoap {

	public static com.liferay.message.boards.model.MBCategorySoap addCategory(
			long userId, long parentCategoryId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.addCategory(
					userId, parentCategoryId, name, description,
					serviceContext);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap addCategory(
			long parentCategoryId, String name, String description,
			String displayStyle, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean allowAnonymousEmail,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.addCategory(
					parentCategoryId, name, description, displayStyle,
					emailAddress, inProtocol, inServerName, inServerPort,
					inUseSSL, inUserName, inPassword, inReadInterval,
					outEmailAddress, outCustom, outServerName, outServerPort,
					outUseSSL, outUserName, outPassword, mailingListActive,
					allowAnonymousEmail, serviceContext);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCategory(
			long categoryId, boolean includeTrashedEntries)
		throws RemoteException {

		try {
			MBCategoryServiceUtil.deleteCategory(
				categoryId, includeTrashedEntries);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCategory(long groupId, long categoryId)
		throws RemoteException {

		try {
			MBCategoryServiceUtil.deleteCategory(groupId, categoryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(groupId);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(long groupId, int status)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, status);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long parentCategoryId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, parentCategoryId, start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long parentCategoryId, int status, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, parentCategoryId, status, start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long excludedCategoryId, long parentCategoryId,
				int status, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, excludedCategoryId, parentCategoryId, status,
					start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long parentCategoryId,
				com.liferay.portal.kernel.dao.orm.QueryDefinition
					<com.liferay.message.boards.model.MBCategory>
						queryDefinition)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, parentCategoryId, queryDefinition);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long[] parentCategoryIds, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, parentCategoryIds, start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long[] parentCategoryIds, int status, int start,
				int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, parentCategoryIds, status, start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getCategories(
				long groupId, long[] excludedCategoryIds,
				long[] parentCategoryIds, int status, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getCategories(
					groupId, excludedCategoryIds, parentCategoryIds, status,
					start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesAndThreadsCount(
			long groupId, long categoryId)
		throws RemoteException {

		try {
			int returnValue =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					groupId, categoryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesAndThreadsCount(
			long groupId, long categoryId, int status)
		throws RemoteException {

		try {
			int returnValue =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					groupId, categoryId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesAndThreadsCount(
			long groupId, long categoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws RemoteException {

		try {
			int returnValue =
				MBCategoryServiceUtil.getCategoriesAndThreadsCount(
					groupId, categoryId, queryDefinition);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, parentCategoryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(
			long groupId, long parentCategoryId, int status)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, parentCategoryId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(
			long groupId, long excludedCategoryId, long parentCategoryId,
			int status)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, excludedCategoryId, parentCategoryId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(
			long groupId, long parentCategoryId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, parentCategoryId, queryDefinition);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, parentCategoryIds);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(
			long groupId, long[] parentCategoryIds, int status)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, parentCategoryIds, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCategoriesCount(
			long groupId, long[] excludedCategoryIds, long[] parentCategoryIds,
			int status)
		throws RemoteException {

		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(
				groupId, excludedCategoryIds, parentCategoryIds, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap getCategory(
			long categoryId)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static long[] getCategoryIds(long groupId, long categoryId)
		throws RemoteException {

		try {
			long[] returnValue = MBCategoryServiceUtil.getCategoryIds(
				groupId, categoryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static Long[] getSubcategoryIds(
			Long[] categoryIds, long groupId, long categoryId)
		throws RemoteException {

		try {
			java.util.List<Long> returnValue =
				MBCategoryServiceUtil.getSubcategoryIds(
					ListUtil.toList(categoryIds), groupId, categoryId);

			return returnValue.toArray(new Long[returnValue.size()]);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap[]
			getSubscribedCategories(
				long groupId, long userId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.message.boards.model.MBCategory>
				returnValue = MBCategoryServiceUtil.getSubscribedCategories(
					groupId, userId, start, end);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws RemoteException {

		try {
			int returnValue =
				MBCategoryServiceUtil.getSubscribedCategoriesCount(
					groupId, userId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap moveCategory(
			long categoryId, long parentCategoryId,
			boolean mergeWithParentCategory)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.moveCategory(
					categoryId, parentCategoryId, mergeWithParentCategory);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap
			moveCategoryFromTrash(long categoryId, long newCategoryId)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.moveCategoryFromTrash(
					categoryId, newCategoryId);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap
			moveCategoryToTrash(long categoryId)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.moveCategoryToTrash(categoryId);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void restoreCategoryFromTrash(long categoryId)
		throws RemoteException {

		try {
			MBCategoryServiceUtil.restoreCategoryFromTrash(categoryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void subscribeCategory(long groupId, long categoryId)
		throws RemoteException {

		try {
			MBCategoryServiceUtil.subscribeCategory(groupId, categoryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unsubscribeCategory(long groupId, long categoryId)
		throws RemoteException {

		try {
			MBCategoryServiceUtil.unsubscribeCategory(groupId, categoryId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.message.boards.model.MBCategorySoap
			updateCategory(
				long categoryId, long parentCategoryId, String name,
				String description, String displayStyle, String emailAddress,
				String inProtocol, String inServerName, int inServerPort,
				boolean inUseSSL, String inUserName, String inPassword,
				int inReadInterval, String outEmailAddress, boolean outCustom,
				String outServerName, int outServerPort, boolean outUseSSL,
				String outUserName, String outPassword,
				boolean mailingListActive, boolean allowAnonymousEmail,
				boolean mergeWithParentCategory,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.message.boards.model.MBCategory returnValue =
				MBCategoryServiceUtil.updateCategory(
					categoryId, parentCategoryId, name, description,
					displayStyle, emailAddress, inProtocol, inServerName,
					inServerPort, inUseSSL, inUserName, inPassword,
					inReadInterval, outEmailAddress, outCustom, outServerName,
					outServerPort, outUseSSL, outUserName, outPassword,
					mailingListActive, allowAnonymousEmail,
					mergeWithParentCategory, serviceContext);

			return com.liferay.message.boards.model.MBCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBCategoryServiceSoap.class);

}