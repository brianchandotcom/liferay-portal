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

import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>AssetCategoryServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.asset.kernel.model.AssetCategorySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.asset.kernel.model.AssetCategory</code>, that is translated to a
 * <code>com.liferay.asset.kernel.model.AssetCategorySoap</code>. Methods that SOAP
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
 * @see AssetCategoryServiceHttp
 * @generated
 */
public class AssetCategoryServiceSoap {

	public static com.liferay.asset.kernel.model.AssetCategorySoap addCategory(
			long groupId, long parentCategoryId, String[] titleMapLanguageIds,
			String[] titleMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues, long vocabularyId,
			String[] categoryProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.addCategory(
					groupId, parentCategoryId, titleMap, descriptionMap,
					vocabularyId, categoryProperties, serviceContext);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap addCategory(
			long groupId, String title, long vocabularyId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.addCategory(
					groupId, title, vocabularyId, serviceContext);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategories(long[] categoryIds)
		throws RemoteException {

		try {
			AssetCategoryServiceUtil.deleteCategories(categoryIds);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), Replaced by {@link
	 #deleteCategories(long[])}
	 */
	@Deprecated
	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			deleteCategories(
				long[] categoryIds,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.deleteCategories(
					categoryIds, serviceContext);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long categoryId) throws RemoteException {
		try {
			AssetCategoryServiceUtil.deleteCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap
			fetchCategory(long categoryId)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.fetchCategory(categoryId);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns a range of assetCategories related to an AssetEntry with the
	 * given "classNameId-classPK".
	 *
	 * @param classNameId the className of the asset
	 * @param classPK the classPK of the asset
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the matching assetCategories
	 */
	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getCategories(long classNameId, long classPK, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getCategories(
					classNameId, classPK, start, end);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getCategories(String className, long classPK)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getCategories(
					className, classPK);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of assetCategories related to an AssetEntry with the
	 * given "classNameId-classPK".
	 *
	 * @param classNameId the className of the asset
	 * @param classPK the classPK of the asset
	 * @return the number of matching assetCategories
	 */
	public static int getCategoriesCount(long classNameId, long classPK)
		throws RemoteException {

		try {
			int returnValue = AssetCategoryServiceUtil.getCategoriesCount(
				classNameId, classPK);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap getCategory(
			long categoryId)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String getCategoryPath(long categoryId)
		throws RemoteException {

		try {
			String returnValue = AssetCategoryServiceUtil.getCategoryPath(
				categoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getChildCategories(long parentCategoryId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getChildCategories(
					parentCategoryId);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * eturns a range of child assetCategories.
	 *
	 * @param parentCategoryId the parent category ID
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @param obc the comparator
	 * @return the matching categories
	 * @throws PortalException
	 */
	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getChildCategories(
				long parentCategoryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getChildCategories(
					parentCategoryId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Returns the number of child categories
	 *
	 * @param parentCategoryId the parent category ID
	 * @return the number of child categories
	 * @throws PortalException
	 */
	public static int getChildCategoriesCount(long parentCategoryId)
		throws RemoteException {

		try {
			int returnValue = AssetCategoryServiceUtil.getChildCategoriesCount(
				parentCategoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getVocabularyCategories(
				long vocabularyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getVocabularyCategories(
					vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getVocabularyCategories(
				long parentCategoryId, long vocabularyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getVocabularyCategories(
					parentCategoryId, vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getVocabularyCategories(
				long groupId, long parentCategoryId, long vocabularyId,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getVocabularyCategories(
					groupId, parentCategoryId, vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getVocabularyCategories(
				long groupId, String name, long vocabularyId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.getVocabularyCategories(
					groupId, name, vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getVocabularyCategoriesCount(
			long groupId, long vocabularyId)
		throws RemoteException {

		try {
			int returnValue =
				AssetCategoryServiceUtil.getVocabularyCategoriesCount(
					groupId, vocabularyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getVocabularyCategoriesCount(
			long groupId, long parentCategory, long vocabularyId)
		throws RemoteException {

		try {
			int returnValue =
				AssetCategoryServiceUtil.getVocabularyCategoriesCount(
					groupId, parentCategory, vocabularyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getVocabularyCategoriesCount(
			long groupId, String name, long vocabularyId)
		throws RemoteException {

		try {
			int returnValue =
				AssetCategoryServiceUtil.getVocabularyCategoriesCount(
					groupId, name, vocabularyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			getVocabularyCategoriesDisplay(
				long vocabularyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.getVocabularyCategoriesDisplay(
					vocabularyId, start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			getVocabularyCategoriesDisplay(
				long groupId, String name, long vocabularyId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.getVocabularyCategoriesDisplay(
					groupId, name, vocabularyId, start, end, obc);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[]
			getVocabularyRootCategories(
				long groupId, long vocabularyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue =
					AssetCategoryServiceUtil.getVocabularyRootCategories(
						groupId, vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getVocabularyRootCategoriesCount(
			long groupId, long vocabularyId)
		throws RemoteException {

		try {
			int returnValue =
				AssetCategoryServiceUtil.getVocabularyRootCategoriesCount(
					groupId, vocabularyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap moveCategory(
			long categoryId, long parentCategoryId, long vocabularyId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.moveCategory(
					categoryId, parentCategoryId, vocabularyId, serviceContext);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap[] search(
			long groupId, String keywords, long vocabularyId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.asset.kernel.model.AssetCategory> obc)
		throws RemoteException {

		try {
			java.util.List<com.liferay.asset.kernel.model.AssetCategory>
				returnValue = AssetCategoryServiceUtil.search(
					groupId, keywords, vocabularyId, start, end, obc);

			return com.liferay.asset.kernel.model.AssetCategorySoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String search(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				AssetCategoryServiceUtil.search(
					groupId, name, categoryProperties, start, end);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static String search(
			long[] groupIds, String name, long[] vocabularyIds, int start,
			int end)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.json.JSONArray returnValue =
				AssetCategoryServiceUtil.search(
					groupIds, name, vocabularyIds, start, end);

			return returnValue.toString();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long groupId, String title, long vocabularyId, int start,
				int end)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupId, title, vocabularyId, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long groupId, String title, long parentCategoryId,
				long vocabularyId, int start, int end)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupId, title, parentCategoryId, vocabularyId, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long groupId, String title, long vocabularyId,
				long parentCategoryId, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupId, title, vocabularyId, parentCategoryId, start, end,
					sort);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long[] groupIds, String title, long[] vocabularyIds, int start,
				int end)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupIds, title, vocabularyIds, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long[] groupIds, String title, long[] parentCategoryIds,
				long[] vocabularyIds, int start, int end)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupIds, title, parentCategoryIds, vocabularyIds, start,
					end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategoryDisplay
			searchCategoriesDisplay(
				long[] groupIds, String title, long[] vocabularyIds,
				long[] parentCategoryIds, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			com.liferay.asset.kernel.model.AssetCategoryDisplay returnValue =
				AssetCategoryServiceUtil.searchCategoriesDisplay(
					groupIds, title, vocabularyIds, parentCategoryIds, start,
					end, sort);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.kernel.model.AssetCategorySoap
			updateCategory(
				long categoryId, long parentCategoryId,
				String[] titleMapLanguageIds, String[] titleMapValues,
				String[] descriptionMapLanguageIds,
				String[] descriptionMapValues, long vocabularyId,
				String[] categoryProperties,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.asset.kernel.model.AssetCategory returnValue =
				AssetCategoryServiceUtil.updateCategory(
					categoryId, parentCategoryId, titleMap, descriptionMap,
					vocabularyId, categoryProperties, serviceContext);

			return com.liferay.asset.kernel.model.AssetCategorySoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetCategoryServiceSoap.class);

}