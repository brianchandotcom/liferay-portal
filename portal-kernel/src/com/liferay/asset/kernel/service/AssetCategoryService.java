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

package com.liferay.asset.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryDisplay;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for AssetCategory. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetCategoryServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetCategoryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetCategoryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetCategoryServiceUtil} to access the asset category remote service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetCategoryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetCategory addCategory(long groupId, java.lang.String title,
		long vocabularyId, ServiceContext serviceContext)
		throws PortalException;

	public AssetCategory addCategory(long groupId, long parentCategoryId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap, long vocabularyId,
		java.lang.String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategory fetchCategory(long categoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategory getCategory(long categoryId) throws PortalException;

	public AssetCategory moveCategory(long categoryId, long parentCategoryId,
		long vocabularyId, ServiceContext serviceContext)
		throws PortalException;

	public AssetCategory updateCategory(long categoryId, long parentCategoryId,
		Map<Locale, java.lang.String> titleMap,
		Map<Locale, java.lang.String> descriptionMap, long vocabularyId,
		java.lang.String[] categoryProperties, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay getVocabularyCategoriesDisplay(long groupId,
		java.lang.String name, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay getVocabularyCategoriesDisplay(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long groupId,
		java.lang.String title, long parentCategoryId, long vocabularyId,
		int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long groupId,
		java.lang.String title, long vocabularyId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long groupId,
		java.lang.String title, long vocabularyId, long parentCategoryId,
		int start, int end, Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long[] groupIds,
		java.lang.String title, long[] parentCategoryIds, long[] vocabularyIds,
		int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long[] groupIds,
		java.lang.String title, long[] vocabularyIds, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetCategoryDisplay searchCategoriesDisplay(long[] groupIds,
		java.lang.String title, long[] vocabularyIds, long[] parentCategoryIds,
		int start, int end, Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray search(long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public JSONArray search(long[] groupIds, java.lang.String name,
		long[] vocabularyIds, int start, int end) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVocabularyCategoriesCount(long groupId,
		java.lang.String name, long vocabularyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVocabularyCategoriesCount(long groupId, long parentCategory,
		long vocabularyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVocabularyCategoriesCount(long groupId, long vocabularyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVocabularyRootCategoriesCount(long groupId, long vocabularyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getCategoryPath(long categoryId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* @deprecated As of 7.0.0, Replaced by {@link #deleteCategories(long[])}
	*/
	@java.lang.Deprecated
	public List<AssetCategory> deleteCategories(long[] categoryIds,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getCategories(java.lang.String className,
		long classPK) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getChildCategories(long parentCategoryId,
		int start, int end, OrderByComparator<AssetCategory> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getVocabularyCategories(long groupId,
		java.lang.String name, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getVocabularyCategories(long groupId,
		long parentCategoryId, long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getVocabularyCategories(long parentCategoryId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getVocabularyCategories(long vocabularyId,
		int start, int end, OrderByComparator<AssetCategory> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> getVocabularyRootCategories(long groupId,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetCategory> search(long groupId, java.lang.String keywords,
		long vocabularyId, int start, int end,
		OrderByComparator<AssetCategory> obc);

	public void deleteCategories(long[] categoryIds) throws PortalException;

	public void deleteCategory(long categoryId) throws PortalException;
}