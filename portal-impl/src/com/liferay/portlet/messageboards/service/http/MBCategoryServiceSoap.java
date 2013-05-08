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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.messageboards.service.MBCategoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.messageboards.model.MBCategorySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.messageboards.model.MBCategory}, that is translated to a
 * {@link com.liferay.portlet.messageboards.model.MBCategorySoap}. Methods that SOAP cannot
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
 * @see       MBCategoryServiceHttp
 * @see       com.liferay.portlet.messageboards.model.MBCategorySoap
 * @see       com.liferay.portlet.messageboards.service.MBCategoryServiceUtil
 * @generated
 */
public class MBCategoryServiceSoap {
	public static com.liferay.portlet.messageboards.model.MBCategorySoap addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.addCategory(userId,
					parentCategoryId, name, description, serviceContext);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.addCategory(parentCategoryId,
					name, description, displayStyle, emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, mailingListActive, allowAnonymousEmail,
					serviceContext);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long categoryId,
		boolean includeTrashedEntries) throws RemoteException {
		try {
			MBCategoryServiceUtil.deleteCategory(categoryId,
				includeTrashedEntries);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long groupId, long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.deleteCategory(groupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, int status) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, status);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, parentCategoryId,
					start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, long parentCategoryId, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, parentCategoryId,
					status, start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, long[] parentCategoryIds, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, parentCategoryIds,
					start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, long[] parentCategoryIds, int status, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, parentCategoryIds,
					status, start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId,
		int status) throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long[] parentCategoryIds)
		throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryIds);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId,
		long[] parentCategoryIds, int status) throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryIds, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap getCategory(
		long categoryId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long[] getCategoryIds(long groupId, long categoryId)
		throws RemoteException {
		try {
			long[] returnValue = MBCategoryServiceUtil.getCategoryIds(groupId,
					categoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.Long[] getSubcategoryIds(Long[] categoryIds,
		long groupId, long categoryId) throws RemoteException {
		try {
			java.util.List<java.lang.Long> returnValue = MBCategoryServiceUtil.getSubcategoryIds(ListUtil.toList(
						categoryIds), groupId, categoryId);

			return returnValue.toArray(new java.lang.Long[returnValue.size()]);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getSubscribedCategories(
		long groupId, long userId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getSubscribedCategories(groupId, userId,
					start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getSubscribedCategoriesCount(long groupId, long userId)
		throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getSubscribedCategoriesCount(groupId,
					userId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap moveCategory(
		long categoryId, long parentCategoryId, boolean mergeWithParentCategory)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.moveCategory(categoryId,
					parentCategoryId, mergeWithParentCategory);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap moveCategoryFromTrash(
		long categoryId, long newCategoryId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.moveCategoryFromTrash(categoryId,
					newCategoryId);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap moveCategoryToTrash(
		long categoryId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.moveCategoryToTrash(categoryId);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreCategoryFromTrash(long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.restoreCategoryFromTrash(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeCategory(long groupId, long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.subscribeCategory(groupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeCategory(long groupId, long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.unsubscribeCategory(groupId, categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String displayStyle,
		java.lang.String emailAddress, java.lang.String inProtocol,
		java.lang.String inServerName, int inServerPort, boolean inUseSSL,
		java.lang.String inUserName, java.lang.String inPassword,
		int inReadInterval, java.lang.String outEmailAddress,
		boolean outCustom, java.lang.String outServerName, int outServerPort,
		boolean outUseSSL, java.lang.String outUserName,
		java.lang.String outPassword, boolean mailingListActive,
		boolean allowAnonymousEmail, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.updateCategory(categoryId,
					parentCategoryId, name, description, displayStyle,
					emailAddress, inProtocol, inServerName, inServerPort,
					inUseSSL, inUserName, inPassword, inReadInterval,
					outEmailAddress, outCustom, outServerName, outServerPort,
					outUseSSL, outUserName, outPassword, mailingListActive,
					allowAnonymousEmail, mergeWithParentCategory, serviceContext);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBCategoryServiceSoap.class);
}