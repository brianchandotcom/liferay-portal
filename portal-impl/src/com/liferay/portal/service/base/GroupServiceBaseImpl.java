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

package com.liferay.portal.service.base;

import com.liferay.asset.kernel.service.persistence.AssetCategoryFinder;
import com.liferay.asset.kernel.service.persistence.AssetCategoryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetEntryFinder;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagFinder;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyFinder;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyPersistence;

import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;

import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationFinder;
import com.liferay.exportimport.kernel.service.persistence.ExportImportConfigurationPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.persistence.AccountPersistence;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.GroupFinder;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutFinder;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutPrototypePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetPrototypePersistence;
import com.liferay.portal.kernel.service.persistence.MembershipRequestPersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationFinder;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.kernel.service.persistence.ResourceActionPersistence;
import com.liferay.portal.kernel.service.persistence.ResourceBlockFinder;
import com.liferay.portal.kernel.service.persistence.ResourceBlockPersistence;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionFinder;
import com.liferay.portal.kernel.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.ResourceTypePermissionFinder;
import com.liferay.portal.kernel.service.persistence.ResourceTypePermissionPersistence;
import com.liferay.portal.kernel.service.persistence.RoleFinder;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.SubscriptionPersistence;
import com.liferay.portal.kernel.service.persistence.TeamFinder;
import com.liferay.portal.kernel.service.persistence.TeamPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRoleFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupRoleFinder;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.kernel.util.PortalUtil;

import com.liferay.social.kernel.service.persistence.SocialActivityFinder;
import com.liferay.social.kernel.service.persistence.SocialActivityPersistence;
import com.liferay.social.kernel.service.persistence.SocialActivitySettingPersistence;
import com.liferay.social.kernel.service.persistence.SocialRequestPersistence;

import com.liferay.trash.kernel.service.persistence.TrashEntryPersistence;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the group remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.GroupServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.GroupServiceImpl
 * @see com.liferay.portal.kernel.service.GroupServiceUtil
 * @generated
 */
public abstract class GroupServiceBaseImpl extends BaseServiceImpl
	implements GroupService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.kernel.service.GroupServiceUtil} to access the group remote service.
	 */

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the account local service.
	 *
	 * @return the account local service
	 */
	public com.liferay.portal.kernel.service.AccountLocalService getAccountLocalService() {
		return accountLocalService;
	}

	/**
	 * Sets the account local service.
	 *
	 * @param accountLocalService the account local service
	 */
	public void setAccountLocalService(
		com.liferay.portal.kernel.service.AccountLocalService accountLocalService) {
		this.accountLocalService = accountLocalService;
	}

	/**
	 * Returns the account remote service.
	 *
	 * @return the account remote service
	 */
	public com.liferay.portal.kernel.service.AccountService getAccountService() {
		return accountService;
	}

	/**
	 * Sets the account remote service.
	 *
	 * @param accountService the account remote service
	 */
	public void setAccountService(
		com.liferay.portal.kernel.service.AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * Returns the account persistence.
	 *
	 * @return the account persistence
	 */
	public AccountPersistence getAccountPersistence() {
		return accountPersistence;
	}

	/**
	 * Sets the account persistence.
	 *
	 * @param accountPersistence the account persistence
	 */
	public void setAccountPersistence(AccountPersistence accountPersistence) {
		this.accountPersistence = accountPersistence;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.kernel.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.kernel.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.kernel.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.kernel.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company remote service.
	 *
	 * @return the company remote service
	 */
	public com.liferay.portal.kernel.service.CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * Sets the company remote service.
	 *
	 * @param companyService the company remote service
	 */
	public void setCompanyService(
		com.liferay.portal.kernel.service.CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	/**
	 * Returns the asset category local service.
	 *
	 * @return the asset category local service
	 */
	public com.liferay.asset.kernel.service.AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	/**
	 * Sets the asset category local service.
	 *
	 * @param assetCategoryLocalService the asset category local service
	 */
	public void setAssetCategoryLocalService(
		com.liferay.asset.kernel.service.AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	/**
	 * Returns the asset category remote service.
	 *
	 * @return the asset category remote service
	 */
	public com.liferay.asset.kernel.service.AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	/**
	 * Sets the asset category remote service.
	 *
	 * @param assetCategoryService the asset category remote service
	 */
	public void setAssetCategoryService(
		com.liferay.asset.kernel.service.AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	/**
	 * Returns the asset category persistence.
	 *
	 * @return the asset category persistence
	 */
	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	/**
	 * Sets the asset category persistence.
	 *
	 * @param assetCategoryPersistence the asset category persistence
	 */
	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	/**
	 * Returns the asset category finder.
	 *
	 * @return the asset category finder
	 */
	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	/**
	 * Sets the asset category finder.
	 *
	 * @param assetCategoryFinder the asset category finder
	 */
	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	/**
	 * Returns the asset entry local service.
	 *
	 * @return the asset entry local service
	 */
	public com.liferay.asset.kernel.service.AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry remote service.
	 *
	 * @return the asset entry remote service
	 */
	public com.liferay.asset.kernel.service.AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	/**
	 * Sets the asset entry remote service.
	 *
	 * @param assetEntryService the asset entry remote service
	 */
	public void setAssetEntryService(
		com.liferay.asset.kernel.service.AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	/**
	 * Returns the asset entry persistence.
	 *
	 * @return the asset entry persistence
	 */
	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	/**
	 * Sets the asset entry persistence.
	 *
	 * @param assetEntryPersistence the asset entry persistence
	 */
	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	/**
	 * Returns the asset entry finder.
	 *
	 * @return the asset entry finder
	 */
	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	/**
	 * Sets the asset entry finder.
	 *
	 * @param assetEntryFinder the asset entry finder
	 */
	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	/**
	 * Returns the asset tag local service.
	 *
	 * @return the asset tag local service
	 */
	public com.liferay.asset.kernel.service.AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	/**
	 * Sets the asset tag local service.
	 *
	 * @param assetTagLocalService the asset tag local service
	 */
	public void setAssetTagLocalService(
		com.liferay.asset.kernel.service.AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	/**
	 * Returns the asset tag remote service.
	 *
	 * @return the asset tag remote service
	 */
	public com.liferay.asset.kernel.service.AssetTagService getAssetTagService() {
		return assetTagService;
	}

	/**
	 * Sets the asset tag remote service.
	 *
	 * @param assetTagService the asset tag remote service
	 */
	public void setAssetTagService(
		com.liferay.asset.kernel.service.AssetTagService assetTagService) {
		this.assetTagService = assetTagService;
	}

	/**
	 * Returns the asset tag persistence.
	 *
	 * @return the asset tag persistence
	 */
	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	/**
	 * Sets the asset tag persistence.
	 *
	 * @param assetTagPersistence the asset tag persistence
	 */
	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	/**
	 * Returns the asset tag finder.
	 *
	 * @return the asset tag finder
	 */
	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	/**
	 * Sets the asset tag finder.
	 *
	 * @param assetTagFinder the asset tag finder
	 */
	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
	}

	/**
	 * Returns the asset vocabulary local service.
	 *
	 * @return the asset vocabulary local service
	 */
	public com.liferay.asset.kernel.service.AssetVocabularyLocalService getAssetVocabularyLocalService() {
		return assetVocabularyLocalService;
	}

	/**
	 * Sets the asset vocabulary local service.
	 *
	 * @param assetVocabularyLocalService the asset vocabulary local service
	 */
	public void setAssetVocabularyLocalService(
		com.liferay.asset.kernel.service.AssetVocabularyLocalService assetVocabularyLocalService) {
		this.assetVocabularyLocalService = assetVocabularyLocalService;
	}

	/**
	 * Returns the asset vocabulary remote service.
	 *
	 * @return the asset vocabulary remote service
	 */
	public com.liferay.asset.kernel.service.AssetVocabularyService getAssetVocabularyService() {
		return assetVocabularyService;
	}

	/**
	 * Sets the asset vocabulary remote service.
	 *
	 * @param assetVocabularyService the asset vocabulary remote service
	 */
	public void setAssetVocabularyService(
		com.liferay.asset.kernel.service.AssetVocabularyService assetVocabularyService) {
		this.assetVocabularyService = assetVocabularyService;
	}

	/**
	 * Returns the asset vocabulary persistence.
	 *
	 * @return the asset vocabulary persistence
	 */
	public AssetVocabularyPersistence getAssetVocabularyPersistence() {
		return assetVocabularyPersistence;
	}

	/**
	 * Sets the asset vocabulary persistence.
	 *
	 * @param assetVocabularyPersistence the asset vocabulary persistence
	 */
	public void setAssetVocabularyPersistence(
		AssetVocabularyPersistence assetVocabularyPersistence) {
		this.assetVocabularyPersistence = assetVocabularyPersistence;
	}

	/**
	 * Returns the asset vocabulary finder.
	 *
	 * @return the asset vocabulary finder
	 */
	public AssetVocabularyFinder getAssetVocabularyFinder() {
		return assetVocabularyFinder;
	}

	/**
	 * Sets the asset vocabulary finder.
	 *
	 * @param assetVocabularyFinder the asset vocabulary finder
	 */
	public void setAssetVocabularyFinder(
		AssetVocabularyFinder assetVocabularyFinder) {
		this.assetVocabularyFinder = assetVocabularyFinder;
	}

	/**
	 * Returns the dl app local service.
	 *
	 * @return the dl app local service
	 */
	public com.liferay.document.library.kernel.service.DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the dl app local service.
	 *
	 * @param dlAppLocalService the dl app local service
	 */
	public void setDLAppLocalService(
		com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the dl app remote service.
	 *
	 * @return the dl app remote service
	 */
	public com.liferay.document.library.kernel.service.DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the dl app remote service.
	 *
	 * @param dlAppService the dl app remote service
	 */
	public void setDLAppService(
		com.liferay.document.library.kernel.service.DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the expando row local service.
	 *
	 * @return the expando row local service
	 */
	public com.liferay.expando.kernel.service.ExpandoRowLocalService getExpandoRowLocalService() {
		return expandoRowLocalService;
	}

	/**
	 * Sets the expando row local service.
	 *
	 * @param expandoRowLocalService the expando row local service
	 */
	public void setExpandoRowLocalService(
		com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService) {
		this.expandoRowLocalService = expandoRowLocalService;
	}

	/**
	 * Returns the expando row persistence.
	 *
	 * @return the expando row persistence
	 */
	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	/**
	 * Sets the expando row persistence.
	 *
	 * @param expandoRowPersistence the expando row persistence
	 */
	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	/**
	 * Returns the export import local service.
	 *
	 * @return the export import local service
	 */
	public com.liferay.exportimport.kernel.service.ExportImportLocalService getExportImportLocalService() {
		return exportImportLocalService;
	}

	/**
	 * Sets the export import local service.
	 *
	 * @param exportImportLocalService the export import local service
	 */
	public void setExportImportLocalService(
		com.liferay.exportimport.kernel.service.ExportImportLocalService exportImportLocalService) {
		this.exportImportLocalService = exportImportLocalService;
	}

	/**
	 * Returns the export import remote service.
	 *
	 * @return the export import remote service
	 */
	public com.liferay.exportimport.kernel.service.ExportImportService getExportImportService() {
		return exportImportService;
	}

	/**
	 * Sets the export import remote service.
	 *
	 * @param exportImportService the export import remote service
	 */
	public void setExportImportService(
		com.liferay.exportimport.kernel.service.ExportImportService exportImportService) {
		this.exportImportService = exportImportService;
	}

	/**
	 * Returns the export import configuration local service.
	 *
	 * @return the export import configuration local service
	 */
	public com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService getExportImportConfigurationLocalService() {
		return exportImportConfigurationLocalService;
	}

	/**
	 * Sets the export import configuration local service.
	 *
	 * @param exportImportConfigurationLocalService the export import configuration local service
	 */
	public void setExportImportConfigurationLocalService(
		com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService exportImportConfigurationLocalService) {
		this.exportImportConfigurationLocalService = exportImportConfigurationLocalService;
	}

	/**
	 * Returns the export import configuration remote service.
	 *
	 * @return the export import configuration remote service
	 */
	public com.liferay.exportimport.kernel.service.ExportImportConfigurationService getExportImportConfigurationService() {
		return exportImportConfigurationService;
	}

	/**
	 * Sets the export import configuration remote service.
	 *
	 * @param exportImportConfigurationService the export import configuration remote service
	 */
	public void setExportImportConfigurationService(
		com.liferay.exportimport.kernel.service.ExportImportConfigurationService exportImportConfigurationService) {
		this.exportImportConfigurationService = exportImportConfigurationService;
	}

	/**
	 * Returns the export import configuration persistence.
	 *
	 * @return the export import configuration persistence
	 */
	public ExportImportConfigurationPersistence getExportImportConfigurationPersistence() {
		return exportImportConfigurationPersistence;
	}

	/**
	 * Sets the export import configuration persistence.
	 *
	 * @param exportImportConfigurationPersistence the export import configuration persistence
	 */
	public void setExportImportConfigurationPersistence(
		ExportImportConfigurationPersistence exportImportConfigurationPersistence) {
		this.exportImportConfigurationPersistence = exportImportConfigurationPersistence;
	}

	/**
	 * Returns the export import configuration finder.
	 *
	 * @return the export import configuration finder
	 */
	public ExportImportConfigurationFinder getExportImportConfigurationFinder() {
		return exportImportConfigurationFinder;
	}

	/**
	 * Sets the export import configuration finder.
	 *
	 * @param exportImportConfigurationFinder the export import configuration finder
	 */
	public void setExportImportConfigurationFinder(
		ExportImportConfigurationFinder exportImportConfigurationFinder) {
		this.exportImportConfigurationFinder = exportImportConfigurationFinder;
	}

	/**
	 * Returns the staging local service.
	 *
	 * @return the staging local service
	 */
	public com.liferay.exportimport.kernel.service.StagingLocalService getStagingLocalService() {
		return stagingLocalService;
	}

	/**
	 * Sets the staging local service.
	 *
	 * @param stagingLocalService the staging local service
	 */
	public void setStagingLocalService(
		com.liferay.exportimport.kernel.service.StagingLocalService stagingLocalService) {
		this.stagingLocalService = stagingLocalService;
	}

	/**
	 * Returns the staging remote service.
	 *
	 * @return the staging remote service
	 */
	public com.liferay.exportimport.kernel.service.StagingService getStagingService() {
		return stagingService;
	}

	/**
	 * Sets the staging remote service.
	 *
	 * @param stagingService the staging remote service
	 */
	public void setStagingService(
		com.liferay.exportimport.kernel.service.StagingService stagingService) {
		this.stagingService = stagingService;
	}

	/**
	 * Returns the social activity local service.
	 *
	 * @return the social activity local service
	 */
	public com.liferay.social.kernel.service.SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	/**
	 * Sets the social activity local service.
	 *
	 * @param socialActivityLocalService the social activity local service
	 */
	public void setSocialActivityLocalService(
		com.liferay.social.kernel.service.SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	/**
	 * Returns the social activity remote service.
	 *
	 * @return the social activity remote service
	 */
	public com.liferay.social.kernel.service.SocialActivityService getSocialActivityService() {
		return socialActivityService;
	}

	/**
	 * Sets the social activity remote service.
	 *
	 * @param socialActivityService the social activity remote service
	 */
	public void setSocialActivityService(
		com.liferay.social.kernel.service.SocialActivityService socialActivityService) {
		this.socialActivityService = socialActivityService;
	}

	/**
	 * Returns the social activity persistence.
	 *
	 * @return the social activity persistence
	 */
	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	/**
	 * Sets the social activity persistence.
	 *
	 * @param socialActivityPersistence the social activity persistence
	 */
	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	/**
	 * Returns the social activity finder.
	 *
	 * @return the social activity finder
	 */
	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	/**
	 * Sets the social activity finder.
	 *
	 * @param socialActivityFinder the social activity finder
	 */
	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	/**
	 * Returns the social activity setting local service.
	 *
	 * @return the social activity setting local service
	 */
	public com.liferay.social.kernel.service.SocialActivitySettingLocalService getSocialActivitySettingLocalService() {
		return socialActivitySettingLocalService;
	}

	/**
	 * Sets the social activity setting local service.
	 *
	 * @param socialActivitySettingLocalService the social activity setting local service
	 */
	public void setSocialActivitySettingLocalService(
		com.liferay.social.kernel.service.SocialActivitySettingLocalService socialActivitySettingLocalService) {
		this.socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	/**
	 * Returns the social activity setting remote service.
	 *
	 * @return the social activity setting remote service
	 */
	public com.liferay.social.kernel.service.SocialActivitySettingService getSocialActivitySettingService() {
		return socialActivitySettingService;
	}

	/**
	 * Sets the social activity setting remote service.
	 *
	 * @param socialActivitySettingService the social activity setting remote service
	 */
	public void setSocialActivitySettingService(
		com.liferay.social.kernel.service.SocialActivitySettingService socialActivitySettingService) {
		this.socialActivitySettingService = socialActivitySettingService;
	}

	/**
	 * Returns the social activity setting persistence.
	 *
	 * @return the social activity setting persistence
	 */
	public SocialActivitySettingPersistence getSocialActivitySettingPersistence() {
		return socialActivitySettingPersistence;
	}

	/**
	 * Sets the social activity setting persistence.
	 *
	 * @param socialActivitySettingPersistence the social activity setting persistence
	 */
	public void setSocialActivitySettingPersistence(
		SocialActivitySettingPersistence socialActivitySettingPersistence) {
		this.socialActivitySettingPersistence = socialActivitySettingPersistence;
	}

	/**
	 * Returns the social request local service.
	 *
	 * @return the social request local service
	 */
	public com.liferay.social.kernel.service.SocialRequestLocalService getSocialRequestLocalService() {
		return socialRequestLocalService;
	}

	/**
	 * Sets the social request local service.
	 *
	 * @param socialRequestLocalService the social request local service
	 */
	public void setSocialRequestLocalService(
		com.liferay.social.kernel.service.SocialRequestLocalService socialRequestLocalService) {
		this.socialRequestLocalService = socialRequestLocalService;
	}

	/**
	 * Returns the social request remote service.
	 *
	 * @return the social request remote service
	 */
	public com.liferay.social.kernel.service.SocialRequestService getSocialRequestService() {
		return socialRequestService;
	}

	/**
	 * Sets the social request remote service.
	 *
	 * @param socialRequestService the social request remote service
	 */
	public void setSocialRequestService(
		com.liferay.social.kernel.service.SocialRequestService socialRequestService) {
		this.socialRequestService = socialRequestService;
	}

	/**
	 * Returns the social request persistence.
	 *
	 * @return the social request persistence
	 */
	public SocialRequestPersistence getSocialRequestPersistence() {
		return socialRequestPersistence;
	}

	/**
	 * Sets the social request persistence.
	 *
	 * @param socialRequestPersistence the social request persistence
	 */
	public void setSocialRequestPersistence(
		SocialRequestPersistence socialRequestPersistence) {
		this.socialRequestPersistence = socialRequestPersistence;
	}

	/**
	 * Returns the trash entry local service.
	 *
	 * @return the trash entry local service
	 */
	public com.liferay.trash.kernel.service.TrashEntryLocalService getTrashEntryLocalService() {
		return trashEntryLocalService;
	}

	/**
	 * Sets the trash entry local service.
	 *
	 * @param trashEntryLocalService the trash entry local service
	 */
	public void setTrashEntryLocalService(
		com.liferay.trash.kernel.service.TrashEntryLocalService trashEntryLocalService) {
		this.trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Returns the trash entry remote service.
	 *
	 * @return the trash entry remote service
	 */
	public com.liferay.trash.kernel.service.TrashEntryService getTrashEntryService() {
		return trashEntryService;
	}

	/**
	 * Sets the trash entry remote service.
	 *
	 * @param trashEntryService the trash entry remote service
	 */
	public void setTrashEntryService(
		com.liferay.trash.kernel.service.TrashEntryService trashEntryService) {
		this.trashEntryService = trashEntryService;
	}

	/**
	 * Returns the trash entry persistence.
	 *
	 * @return the trash entry persistence
	 */
	public TrashEntryPersistence getTrashEntryPersistence() {
		return trashEntryPersistence;
	}

	/**
	 * Sets the trash entry persistence.
	 *
	 * @param trashEntryPersistence the trash entry persistence
	 */
	public void setTrashEntryPersistence(
		TrashEntryPersistence trashEntryPersistence) {
		this.trashEntryPersistence = trashEntryPersistence;
	}

	/**
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public com.liferay.portal.kernel.service.LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(
		com.liferay.portal.kernel.service.LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout remote service.
	 *
	 * @return the layout remote service
	 */
	public com.liferay.portal.kernel.service.LayoutService getLayoutService() {
		return layoutService;
	}

	/**
	 * Sets the layout remote service.
	 *
	 * @param layoutService the layout remote service
	 */
	public void setLayoutService(
		com.liferay.portal.kernel.service.LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	/**
	 * Returns the layout persistence.
	 *
	 * @return the layout persistence
	 */
	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	/**
	 * Sets the layout persistence.
	 *
	 * @param layoutPersistence the layout persistence
	 */
	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	/**
	 * Returns the layout finder.
	 *
	 * @return the layout finder
	 */
	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	/**
	 * Sets the layout finder.
	 *
	 * @param layoutFinder the layout finder
	 */
	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	/**
	 * Returns the layout prototype local service.
	 *
	 * @return the layout prototype local service
	 */
	public com.liferay.portal.kernel.service.LayoutPrototypeLocalService getLayoutPrototypeLocalService() {
		return layoutPrototypeLocalService;
	}

	/**
	 * Sets the layout prototype local service.
	 *
	 * @param layoutPrototypeLocalService the layout prototype local service
	 */
	public void setLayoutPrototypeLocalService(
		com.liferay.portal.kernel.service.LayoutPrototypeLocalService layoutPrototypeLocalService) {
		this.layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	/**
	 * Returns the layout prototype remote service.
	 *
	 * @return the layout prototype remote service
	 */
	public com.liferay.portal.kernel.service.LayoutPrototypeService getLayoutPrototypeService() {
		return layoutPrototypeService;
	}

	/**
	 * Sets the layout prototype remote service.
	 *
	 * @param layoutPrototypeService the layout prototype remote service
	 */
	public void setLayoutPrototypeService(
		com.liferay.portal.kernel.service.LayoutPrototypeService layoutPrototypeService) {
		this.layoutPrototypeService = layoutPrototypeService;
	}

	/**
	 * Returns the layout prototype persistence.
	 *
	 * @return the layout prototype persistence
	 */
	public LayoutPrototypePersistence getLayoutPrototypePersistence() {
		return layoutPrototypePersistence;
	}

	/**
	 * Sets the layout prototype persistence.
	 *
	 * @param layoutPrototypePersistence the layout prototype persistence
	 */
	public void setLayoutPrototypePersistence(
		LayoutPrototypePersistence layoutPrototypePersistence) {
		this.layoutPrototypePersistence = layoutPrototypePersistence;
	}

	/**
	 * Returns the layout set local service.
	 *
	 * @return the layout set local service
	 */
	public com.liferay.portal.kernel.service.LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	/**
	 * Sets the layout set local service.
	 *
	 * @param layoutSetLocalService the layout set local service
	 */
	public void setLayoutSetLocalService(
		com.liferay.portal.kernel.service.LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	/**
	 * Returns the layout set remote service.
	 *
	 * @return the layout set remote service
	 */
	public com.liferay.portal.kernel.service.LayoutSetService getLayoutSetService() {
		return layoutSetService;
	}

	/**
	 * Sets the layout set remote service.
	 *
	 * @param layoutSetService the layout set remote service
	 */
	public void setLayoutSetService(
		com.liferay.portal.kernel.service.LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	/**
	 * Returns the layout set persistence.
	 *
	 * @return the layout set persistence
	 */
	public LayoutSetPersistence getLayoutSetPersistence() {
		return layoutSetPersistence;
	}

	/**
	 * Sets the layout set persistence.
	 *
	 * @param layoutSetPersistence the layout set persistence
	 */
	public void setLayoutSetPersistence(
		LayoutSetPersistence layoutSetPersistence) {
		this.layoutSetPersistence = layoutSetPersistence;
	}

	/**
	 * Returns the layout set branch local service.
	 *
	 * @return the layout set branch local service
	 */
	public com.liferay.portal.kernel.service.LayoutSetBranchLocalService getLayoutSetBranchLocalService() {
		return layoutSetBranchLocalService;
	}

	/**
	 * Sets the layout set branch local service.
	 *
	 * @param layoutSetBranchLocalService the layout set branch local service
	 */
	public void setLayoutSetBranchLocalService(
		com.liferay.portal.kernel.service.LayoutSetBranchLocalService layoutSetBranchLocalService) {
		this.layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	/**
	 * Returns the layout set branch remote service.
	 *
	 * @return the layout set branch remote service
	 */
	public com.liferay.portal.kernel.service.LayoutSetBranchService getLayoutSetBranchService() {
		return layoutSetBranchService;
	}

	/**
	 * Sets the layout set branch remote service.
	 *
	 * @param layoutSetBranchService the layout set branch remote service
	 */
	public void setLayoutSetBranchService(
		com.liferay.portal.kernel.service.LayoutSetBranchService layoutSetBranchService) {
		this.layoutSetBranchService = layoutSetBranchService;
	}

	/**
	 * Returns the layout set branch persistence.
	 *
	 * @return the layout set branch persistence
	 */
	public LayoutSetBranchPersistence getLayoutSetBranchPersistence() {
		return layoutSetBranchPersistence;
	}

	/**
	 * Sets the layout set branch persistence.
	 *
	 * @param layoutSetBranchPersistence the layout set branch persistence
	 */
	public void setLayoutSetBranchPersistence(
		LayoutSetBranchPersistence layoutSetBranchPersistence) {
		this.layoutSetBranchPersistence = layoutSetBranchPersistence;
	}

	/**
	 * Returns the layout set prototype local service.
	 *
	 * @return the layout set prototype local service
	 */
	public com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService getLayoutSetPrototypeLocalService() {
		return layoutSetPrototypeLocalService;
	}

	/**
	 * Sets the layout set prototype local service.
	 *
	 * @param layoutSetPrototypeLocalService the layout set prototype local service
	 */
	public void setLayoutSetPrototypeLocalService(
		com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {
		this.layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	/**
	 * Returns the layout set prototype remote service.
	 *
	 * @return the layout set prototype remote service
	 */
	public com.liferay.portal.kernel.service.LayoutSetPrototypeService getLayoutSetPrototypeService() {
		return layoutSetPrototypeService;
	}

	/**
	 * Sets the layout set prototype remote service.
	 *
	 * @param layoutSetPrototypeService the layout set prototype remote service
	 */
	public void setLayoutSetPrototypeService(
		com.liferay.portal.kernel.service.LayoutSetPrototypeService layoutSetPrototypeService) {
		this.layoutSetPrototypeService = layoutSetPrototypeService;
	}

	/**
	 * Returns the layout set prototype persistence.
	 *
	 * @return the layout set prototype persistence
	 */
	public LayoutSetPrototypePersistence getLayoutSetPrototypePersistence() {
		return layoutSetPrototypePersistence;
	}

	/**
	 * Sets the layout set prototype persistence.
	 *
	 * @param layoutSetPrototypePersistence the layout set prototype persistence
	 */
	public void setLayoutSetPrototypePersistence(
		LayoutSetPrototypePersistence layoutSetPrototypePersistence) {
		this.layoutSetPrototypePersistence = layoutSetPrototypePersistence;
	}

	/**
	 * Returns the membership request local service.
	 *
	 * @return the membership request local service
	 */
	public com.liferay.portal.kernel.service.MembershipRequestLocalService getMembershipRequestLocalService() {
		return membershipRequestLocalService;
	}

	/**
	 * Sets the membership request local service.
	 *
	 * @param membershipRequestLocalService the membership request local service
	 */
	public void setMembershipRequestLocalService(
		com.liferay.portal.kernel.service.MembershipRequestLocalService membershipRequestLocalService) {
		this.membershipRequestLocalService = membershipRequestLocalService;
	}

	/**
	 * Returns the membership request remote service.
	 *
	 * @return the membership request remote service
	 */
	public com.liferay.portal.kernel.service.MembershipRequestService getMembershipRequestService() {
		return membershipRequestService;
	}

	/**
	 * Sets the membership request remote service.
	 *
	 * @param membershipRequestService the membership request remote service
	 */
	public void setMembershipRequestService(
		com.liferay.portal.kernel.service.MembershipRequestService membershipRequestService) {
		this.membershipRequestService = membershipRequestService;
	}

	/**
	 * Returns the membership request persistence.
	 *
	 * @return the membership request persistence
	 */
	public MembershipRequestPersistence getMembershipRequestPersistence() {
		return membershipRequestPersistence;
	}

	/**
	 * Sets the membership request persistence.
	 *
	 * @param membershipRequestPersistence the membership request persistence
	 */
	public void setMembershipRequestPersistence(
		MembershipRequestPersistence membershipRequestPersistence) {
		this.membershipRequestPersistence = membershipRequestPersistence;
	}

	/**
	 * Returns the organization local service.
	 *
	 * @return the organization local service
	 */
	public com.liferay.portal.kernel.service.OrganizationLocalService getOrganizationLocalService() {
		return organizationLocalService;
	}

	/**
	 * Sets the organization local service.
	 *
	 * @param organizationLocalService the organization local service
	 */
	public void setOrganizationLocalService(
		com.liferay.portal.kernel.service.OrganizationLocalService organizationLocalService) {
		this.organizationLocalService = organizationLocalService;
	}

	/**
	 * Returns the organization remote service.
	 *
	 * @return the organization remote service
	 */
	public com.liferay.portal.kernel.service.OrganizationService getOrganizationService() {
		return organizationService;
	}

	/**
	 * Sets the organization remote service.
	 *
	 * @param organizationService the organization remote service
	 */
	public void setOrganizationService(
		com.liferay.portal.kernel.service.OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/**
	 * Returns the organization persistence.
	 *
	 * @return the organization persistence
	 */
	public OrganizationPersistence getOrganizationPersistence() {
		return organizationPersistence;
	}

	/**
	 * Sets the organization persistence.
	 *
	 * @param organizationPersistence the organization persistence
	 */
	public void setOrganizationPersistence(
		OrganizationPersistence organizationPersistence) {
		this.organizationPersistence = organizationPersistence;
	}

	/**
	 * Returns the organization finder.
	 *
	 * @return the organization finder
	 */
	public OrganizationFinder getOrganizationFinder() {
		return organizationFinder;
	}

	/**
	 * Sets the organization finder.
	 *
	 * @param organizationFinder the organization finder
	 */
	public void setOrganizationFinder(OrganizationFinder organizationFinder) {
		this.organizationFinder = organizationFinder;
	}

	/**
	 * Returns the portlet local service.
	 *
	 * @return the portlet local service
	 */
	public com.liferay.portal.kernel.service.PortletLocalService getPortletLocalService() {
		return portletLocalService;
	}

	/**
	 * Sets the portlet local service.
	 *
	 * @param portletLocalService the portlet local service
	 */
	public void setPortletLocalService(
		com.liferay.portal.kernel.service.PortletLocalService portletLocalService) {
		this.portletLocalService = portletLocalService;
	}

	/**
	 * Returns the portlet remote service.
	 *
	 * @return the portlet remote service
	 */
	public com.liferay.portal.kernel.service.PortletService getPortletService() {
		return portletService;
	}

	/**
	 * Sets the portlet remote service.
	 *
	 * @param portletService the portlet remote service
	 */
	public void setPortletService(
		com.liferay.portal.kernel.service.PortletService portletService) {
		this.portletService = portletService;
	}

	/**
	 * Returns the portlet persistence.
	 *
	 * @return the portlet persistence
	 */
	public PortletPersistence getPortletPersistence() {
		return portletPersistence;
	}

	/**
	 * Sets the portlet persistence.
	 *
	 * @param portletPersistence the portlet persistence
	 */
	public void setPortletPersistence(PortletPersistence portletPersistence) {
		this.portletPersistence = portletPersistence;
	}

	/**
	 * Returns the portlet preferences local service.
	 *
	 * @return the portlet preferences local service
	 */
	public com.liferay.portal.kernel.service.PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	/**
	 * Sets the portlet preferences local service.
	 *
	 * @param portletPreferencesLocalService the portlet preferences local service
	 */
	public void setPortletPreferencesLocalService(
		com.liferay.portal.kernel.service.PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	/**
	 * Returns the portlet preferences remote service.
	 *
	 * @return the portlet preferences remote service
	 */
	public com.liferay.portal.kernel.service.PortletPreferencesService getPortletPreferencesService() {
		return portletPreferencesService;
	}

	/**
	 * Sets the portlet preferences remote service.
	 *
	 * @param portletPreferencesService the portlet preferences remote service
	 */
	public void setPortletPreferencesService(
		com.liferay.portal.kernel.service.PortletPreferencesService portletPreferencesService) {
		this.portletPreferencesService = portletPreferencesService;
	}

	/**
	 * Returns the portlet preferences persistence.
	 *
	 * @return the portlet preferences persistence
	 */
	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	/**
	 * Sets the portlet preferences persistence.
	 *
	 * @param portletPreferencesPersistence the portlet preferences persistence
	 */
	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	/**
	 * Returns the portlet preferences finder.
	 *
	 * @return the portlet preferences finder
	 */
	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	/**
	 * Sets the portlet preferences finder.
	 *
	 * @param portletPreferencesFinder the portlet preferences finder
	 */
	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource action local service.
	 *
	 * @return the resource action local service
	 */
	public com.liferay.portal.kernel.service.ResourceActionLocalService getResourceActionLocalService() {
		return resourceActionLocalService;
	}

	/**
	 * Sets the resource action local service.
	 *
	 * @param resourceActionLocalService the resource action local service
	 */
	public void setResourceActionLocalService(
		com.liferay.portal.kernel.service.ResourceActionLocalService resourceActionLocalService) {
		this.resourceActionLocalService = resourceActionLocalService;
	}

	/**
	 * Returns the resource action persistence.
	 *
	 * @return the resource action persistence
	 */
	public ResourceActionPersistence getResourceActionPersistence() {
		return resourceActionPersistence;
	}

	/**
	 * Sets the resource action persistence.
	 *
	 * @param resourceActionPersistence the resource action persistence
	 */
	public void setResourceActionPersistence(
		ResourceActionPersistence resourceActionPersistence) {
		this.resourceActionPersistence = resourceActionPersistence;
	}

	/**
	 * Returns the resource block local service.
	 *
	 * @return the resource block local service
	 */
	public com.liferay.portal.kernel.service.ResourceBlockLocalService getResourceBlockLocalService() {
		return resourceBlockLocalService;
	}

	/**
	 * Sets the resource block local service.
	 *
	 * @param resourceBlockLocalService the resource block local service
	 */
	public void setResourceBlockLocalService(
		com.liferay.portal.kernel.service.ResourceBlockLocalService resourceBlockLocalService) {
		this.resourceBlockLocalService = resourceBlockLocalService;
	}

	/**
	 * Returns the resource block remote service.
	 *
	 * @return the resource block remote service
	 */
	public com.liferay.portal.kernel.service.ResourceBlockService getResourceBlockService() {
		return resourceBlockService;
	}

	/**
	 * Sets the resource block remote service.
	 *
	 * @param resourceBlockService the resource block remote service
	 */
	public void setResourceBlockService(
		com.liferay.portal.kernel.service.ResourceBlockService resourceBlockService) {
		this.resourceBlockService = resourceBlockService;
	}

	/**
	 * Returns the resource block persistence.
	 *
	 * @return the resource block persistence
	 */
	public ResourceBlockPersistence getResourceBlockPersistence() {
		return resourceBlockPersistence;
	}

	/**
	 * Sets the resource block persistence.
	 *
	 * @param resourceBlockPersistence the resource block persistence
	 */
	public void setResourceBlockPersistence(
		ResourceBlockPersistence resourceBlockPersistence) {
		this.resourceBlockPersistence = resourceBlockPersistence;
	}

	/**
	 * Returns the resource block finder.
	 *
	 * @return the resource block finder
	 */
	public ResourceBlockFinder getResourceBlockFinder() {
		return resourceBlockFinder;
	}

	/**
	 * Sets the resource block finder.
	 *
	 * @param resourceBlockFinder the resource block finder
	 */
	public void setResourceBlockFinder(ResourceBlockFinder resourceBlockFinder) {
		this.resourceBlockFinder = resourceBlockFinder;
	}

	/**
	 * Returns the resource permission local service.
	 *
	 * @return the resource permission local service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionLocalService getResourcePermissionLocalService() {
		return resourcePermissionLocalService;
	}

	/**
	 * Sets the resource permission local service.
	 *
	 * @param resourcePermissionLocalService the resource permission local service
	 */
	public void setResourcePermissionLocalService(
		com.liferay.portal.kernel.service.ResourcePermissionLocalService resourcePermissionLocalService) {
		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	 * Returns the resource permission remote service.
	 *
	 * @return the resource permission remote service
	 */
	public com.liferay.portal.kernel.service.ResourcePermissionService getResourcePermissionService() {
		return resourcePermissionService;
	}

	/**
	 * Sets the resource permission remote service.
	 *
	 * @param resourcePermissionService the resource permission remote service
	 */
	public void setResourcePermissionService(
		com.liferay.portal.kernel.service.ResourcePermissionService resourcePermissionService) {
		this.resourcePermissionService = resourcePermissionService;
	}

	/**
	 * Returns the resource permission persistence.
	 *
	 * @return the resource permission persistence
	 */
	public ResourcePermissionPersistence getResourcePermissionPersistence() {
		return resourcePermissionPersistence;
	}

	/**
	 * Sets the resource permission persistence.
	 *
	 * @param resourcePermissionPersistence the resource permission persistence
	 */
	public void setResourcePermissionPersistence(
		ResourcePermissionPersistence resourcePermissionPersistence) {
		this.resourcePermissionPersistence = resourcePermissionPersistence;
	}

	/**
	 * Returns the resource permission finder.
	 *
	 * @return the resource permission finder
	 */
	public ResourcePermissionFinder getResourcePermissionFinder() {
		return resourcePermissionFinder;
	}

	/**
	 * Sets the resource permission finder.
	 *
	 * @param resourcePermissionFinder the resource permission finder
	 */
	public void setResourcePermissionFinder(
		ResourcePermissionFinder resourcePermissionFinder) {
		this.resourcePermissionFinder = resourcePermissionFinder;
	}

	/**
	 * Returns the resource type permission local service.
	 *
	 * @return the resource type permission local service
	 */
	public com.liferay.portal.kernel.service.ResourceTypePermissionLocalService getResourceTypePermissionLocalService() {
		return resourceTypePermissionLocalService;
	}

	/**
	 * Sets the resource type permission local service.
	 *
	 * @param resourceTypePermissionLocalService the resource type permission local service
	 */
	public void setResourceTypePermissionLocalService(
		com.liferay.portal.kernel.service.ResourceTypePermissionLocalService resourceTypePermissionLocalService) {
		this.resourceTypePermissionLocalService = resourceTypePermissionLocalService;
	}

	/**
	 * Returns the resource type permission persistence.
	 *
	 * @return the resource type permission persistence
	 */
	public ResourceTypePermissionPersistence getResourceTypePermissionPersistence() {
		return resourceTypePermissionPersistence;
	}

	/**
	 * Sets the resource type permission persistence.
	 *
	 * @param resourceTypePermissionPersistence the resource type permission persistence
	 */
	public void setResourceTypePermissionPersistence(
		ResourceTypePermissionPersistence resourceTypePermissionPersistence) {
		this.resourceTypePermissionPersistence = resourceTypePermissionPersistence;
	}

	/**
	 * Returns the resource type permission finder.
	 *
	 * @return the resource type permission finder
	 */
	public ResourceTypePermissionFinder getResourceTypePermissionFinder() {
		return resourceTypePermissionFinder;
	}

	/**
	 * Sets the resource type permission finder.
	 *
	 * @param resourceTypePermissionFinder the resource type permission finder
	 */
	public void setResourceTypePermissionFinder(
		ResourceTypePermissionFinder resourceTypePermissionFinder) {
		this.resourceTypePermissionFinder = resourceTypePermissionFinder;
	}

	/**
	 * Returns the role local service.
	 *
	 * @return the role local service
	 */
	public com.liferay.portal.kernel.service.RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.kernel.service.RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role remote service.
	 *
	 * @return the role remote service
	 */
	public com.liferay.portal.kernel.service.RoleService getRoleService() {
		return roleService;
	}

	/**
	 * Sets the role remote service.
	 *
	 * @param roleService the role remote service
	 */
	public void setRoleService(
		com.liferay.portal.kernel.service.RoleService roleService) {
		this.roleService = roleService;
	}

	/**
	 * Returns the role persistence.
	 *
	 * @return the role persistence
	 */
	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	/**
	 * Sets the role persistence.
	 *
	 * @param rolePersistence the role persistence
	 */
	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	/**
	 * Returns the role finder.
	 *
	 * @return the role finder
	 */
	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	/**
	 * Sets the role finder.
	 *
	 * @param roleFinder the role finder
	 */
	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
	}

	/**
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.kernel.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.kernel.service.SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * Returns the subscription persistence.
	 *
	 * @return the subscription persistence
	 */
	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	/**
	 * Sets the subscription persistence.
	 *
	 * @param subscriptionPersistence the subscription persistence
	 */
	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
	}

	/**
	 * Returns the team local service.
	 *
	 * @return the team local service
	 */
	public com.liferay.portal.kernel.service.TeamLocalService getTeamLocalService() {
		return teamLocalService;
	}

	/**
	 * Sets the team local service.
	 *
	 * @param teamLocalService the team local service
	 */
	public void setTeamLocalService(
		com.liferay.portal.kernel.service.TeamLocalService teamLocalService) {
		this.teamLocalService = teamLocalService;
	}

	/**
	 * Returns the team remote service.
	 *
	 * @return the team remote service
	 */
	public com.liferay.portal.kernel.service.TeamService getTeamService() {
		return teamService;
	}

	/**
	 * Sets the team remote service.
	 *
	 * @param teamService the team remote service
	 */
	public void setTeamService(
		com.liferay.portal.kernel.service.TeamService teamService) {
		this.teamService = teamService;
	}

	/**
	 * Returns the team persistence.
	 *
	 * @return the team persistence
	 */
	public TeamPersistence getTeamPersistence() {
		return teamPersistence;
	}

	/**
	 * Sets the team persistence.
	 *
	 * @param teamPersistence the team persistence
	 */
	public void setTeamPersistence(TeamPersistence teamPersistence) {
		this.teamPersistence = teamPersistence;
	}

	/**
	 * Returns the team finder.
	 *
	 * @return the team finder
	 */
	public TeamFinder getTeamFinder() {
		return teamFinder;
	}

	/**
	 * Sets the team finder.
	 *
	 * @param teamFinder the team finder
	 */
	public void setTeamFinder(TeamFinder teamFinder) {
		this.teamFinder = teamFinder;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.kernel.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.kernel.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the user group local service.
	 *
	 * @return the user group local service
	 */
	public com.liferay.portal.kernel.service.UserGroupLocalService getUserGroupLocalService() {
		return userGroupLocalService;
	}

	/**
	 * Sets the user group local service.
	 *
	 * @param userGroupLocalService the user group local service
	 */
	public void setUserGroupLocalService(
		com.liferay.portal.kernel.service.UserGroupLocalService userGroupLocalService) {
		this.userGroupLocalService = userGroupLocalService;
	}

	/**
	 * Returns the user group remote service.
	 *
	 * @return the user group remote service
	 */
	public com.liferay.portal.kernel.service.UserGroupService getUserGroupService() {
		return userGroupService;
	}

	/**
	 * Sets the user group remote service.
	 *
	 * @param userGroupService the user group remote service
	 */
	public void setUserGroupService(
		com.liferay.portal.kernel.service.UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	/**
	 * Returns the user group persistence.
	 *
	 * @return the user group persistence
	 */
	public UserGroupPersistence getUserGroupPersistence() {
		return userGroupPersistence;
	}

	/**
	 * Sets the user group persistence.
	 *
	 * @param userGroupPersistence the user group persistence
	 */
	public void setUserGroupPersistence(
		UserGroupPersistence userGroupPersistence) {
		this.userGroupPersistence = userGroupPersistence;
	}

	/**
	 * Returns the user group finder.
	 *
	 * @return the user group finder
	 */
	public UserGroupFinder getUserGroupFinder() {
		return userGroupFinder;
	}

	/**
	 * Sets the user group finder.
	 *
	 * @param userGroupFinder the user group finder
	 */
	public void setUserGroupFinder(UserGroupFinder userGroupFinder) {
		this.userGroupFinder = userGroupFinder;
	}

	/**
	 * Returns the user group group role local service.
	 *
	 * @return the user group group role local service
	 */
	public com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService getUserGroupGroupRoleLocalService() {
		return userGroupGroupRoleLocalService;
	}

	/**
	 * Sets the user group group role local service.
	 *
	 * @param userGroupGroupRoleLocalService the user group group role local service
	 */
	public void setUserGroupGroupRoleLocalService(
		com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		this.userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	/**
	 * Returns the user group group role remote service.
	 *
	 * @return the user group group role remote service
	 */
	public com.liferay.portal.kernel.service.UserGroupGroupRoleService getUserGroupGroupRoleService() {
		return userGroupGroupRoleService;
	}

	/**
	 * Sets the user group group role remote service.
	 *
	 * @param userGroupGroupRoleService the user group group role remote service
	 */
	public void setUserGroupGroupRoleService(
		com.liferay.portal.kernel.service.UserGroupGroupRoleService userGroupGroupRoleService) {
		this.userGroupGroupRoleService = userGroupGroupRoleService;
	}

	/**
	 * Returns the user group group role persistence.
	 *
	 * @return the user group group role persistence
	 */
	public UserGroupGroupRolePersistence getUserGroupGroupRolePersistence() {
		return userGroupGroupRolePersistence;
	}

	/**
	 * Sets the user group group role persistence.
	 *
	 * @param userGroupGroupRolePersistence the user group group role persistence
	 */
	public void setUserGroupGroupRolePersistence(
		UserGroupGroupRolePersistence userGroupGroupRolePersistence) {
		this.userGroupGroupRolePersistence = userGroupGroupRolePersistence;
	}

	/**
	 * Returns the user group group role finder.
	 *
	 * @return the user group group role finder
	 */
	public UserGroupGroupRoleFinder getUserGroupGroupRoleFinder() {
		return userGroupGroupRoleFinder;
	}

	/**
	 * Sets the user group group role finder.
	 *
	 * @param userGroupGroupRoleFinder the user group group role finder
	 */
	public void setUserGroupGroupRoleFinder(
		UserGroupGroupRoleFinder userGroupGroupRoleFinder) {
		this.userGroupGroupRoleFinder = userGroupGroupRoleFinder;
	}

	/**
	 * Returns the user group role local service.
	 *
	 * @return the user group role local service
	 */
	public com.liferay.portal.kernel.service.UserGroupRoleLocalService getUserGroupRoleLocalService() {
		return userGroupRoleLocalService;
	}

	/**
	 * Sets the user group role local service.
	 *
	 * @param userGroupRoleLocalService the user group role local service
	 */
	public void setUserGroupRoleLocalService(
		com.liferay.portal.kernel.service.UserGroupRoleLocalService userGroupRoleLocalService) {
		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	/**
	 * Returns the user group role remote service.
	 *
	 * @return the user group role remote service
	 */
	public com.liferay.portal.kernel.service.UserGroupRoleService getUserGroupRoleService() {
		return userGroupRoleService;
	}

	/**
	 * Sets the user group role remote service.
	 *
	 * @param userGroupRoleService the user group role remote service
	 */
	public void setUserGroupRoleService(
		com.liferay.portal.kernel.service.UserGroupRoleService userGroupRoleService) {
		this.userGroupRoleService = userGroupRoleService;
	}

	/**
	 * Returns the user group role persistence.
	 *
	 * @return the user group role persistence
	 */
	public UserGroupRolePersistence getUserGroupRolePersistence() {
		return userGroupRolePersistence;
	}

	/**
	 * Sets the user group role persistence.
	 *
	 * @param userGroupRolePersistence the user group role persistence
	 */
	public void setUserGroupRolePersistence(
		UserGroupRolePersistence userGroupRolePersistence) {
		this.userGroupRolePersistence = userGroupRolePersistence;
	}

	/**
	 * Returns the user group role finder.
	 *
	 * @return the user group role finder
	 */
	public UserGroupRoleFinder getUserGroupRoleFinder() {
		return userGroupRoleFinder;
	}

	/**
	 * Sets the user group role finder.
	 *
	 * @param userGroupRoleFinder the user group role finder
	 */
	public void setUserGroupRoleFinder(UserGroupRoleFinder userGroupRoleFinder) {
		this.userGroupRoleFinder = userGroupRoleFinder;
	}

	/**
	 * Returns the workflow definition link local service.
	 *
	 * @return the workflow definition link local service
	 */
	public com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService getWorkflowDefinitionLinkLocalService() {
		return workflowDefinitionLinkLocalService;
	}

	/**
	 * Sets the workflow definition link local service.
	 *
	 * @param workflowDefinitionLinkLocalService the workflow definition link local service
	 */
	public void setWorkflowDefinitionLinkLocalService(
		com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		this.workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	/**
	 * Returns the workflow definition link persistence.
	 *
	 * @return the workflow definition link persistence
	 */
	public WorkflowDefinitionLinkPersistence getWorkflowDefinitionLinkPersistence() {
		return workflowDefinitionLinkPersistence;
	}

	/**
	 * Sets the workflow definition link persistence.
	 *
	 * @param workflowDefinitionLinkPersistence the workflow definition link persistence
	 */
	public void setWorkflowDefinitionLinkPersistence(
		WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence) {
		this.workflowDefinitionLinkPersistence = workflowDefinitionLinkPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return GroupService.class.getName();
	}

	protected Class<?> getModelClass() {
		return Group.class;
	}

	protected String getModelClassName() {
		return Group.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = groupPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portal.kernel.service.GroupLocalService.class)
	protected com.liferay.portal.kernel.service.GroupLocalService groupLocalService;
	@BeanReference(type = GroupService.class)
	protected GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.AccountLocalService.class)
	protected com.liferay.portal.kernel.service.AccountLocalService accountLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.AccountService.class)
	protected com.liferay.portal.kernel.service.AccountService accountService;
	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ClassNameService.class)
	protected com.liferay.portal.kernel.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.CompanyLocalService.class)
	protected com.liferay.portal.kernel.service.CompanyLocalService companyLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.CompanyService.class)
	protected com.liferay.portal.kernel.service.CompanyService companyService;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetCategoryLocalService.class)
	protected com.liferay.asset.kernel.service.AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetCategoryService.class)
	protected com.liferay.asset.kernel.service.AssetCategoryService assetCategoryService;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryFinder.class)
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetEntryLocalService.class)
	protected com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetEntryService.class)
	protected com.liferay.asset.kernel.service.AssetEntryService assetEntryService;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetEntryFinder.class)
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetTagLocalService.class)
	protected com.liferay.asset.kernel.service.AssetTagLocalService assetTagLocalService;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetTagService.class)
	protected com.liferay.asset.kernel.service.AssetTagService assetTagService;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagFinder.class)
	protected AssetTagFinder assetTagFinder;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetVocabularyLocalService.class)
	protected com.liferay.asset.kernel.service.AssetVocabularyLocalService assetVocabularyLocalService;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetVocabularyService.class)
	protected com.liferay.asset.kernel.service.AssetVocabularyService assetVocabularyService;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = AssetVocabularyFinder.class)
	protected AssetVocabularyFinder assetVocabularyFinder;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLAppLocalService.class)
	protected com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLAppService.class)
	protected com.liferay.document.library.kernel.service.DLAppService dlAppService;
	@BeanReference(type = com.liferay.expando.kernel.service.ExpandoRowLocalService.class)
	protected com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService;
	@BeanReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(type = com.liferay.exportimport.kernel.service.ExportImportLocalService.class)
	protected com.liferay.exportimport.kernel.service.ExportImportLocalService exportImportLocalService;
	@BeanReference(type = com.liferay.exportimport.kernel.service.ExportImportService.class)
	protected com.liferay.exportimport.kernel.service.ExportImportService exportImportService;
	@BeanReference(type = com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService.class)
	protected com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService exportImportConfigurationLocalService;
	@BeanReference(type = com.liferay.exportimport.kernel.service.ExportImportConfigurationService.class)
	protected com.liferay.exportimport.kernel.service.ExportImportConfigurationService exportImportConfigurationService;
	@BeanReference(type = ExportImportConfigurationPersistence.class)
	protected ExportImportConfigurationPersistence exportImportConfigurationPersistence;
	@BeanReference(type = ExportImportConfigurationFinder.class)
	protected ExportImportConfigurationFinder exportImportConfigurationFinder;
	@BeanReference(type = com.liferay.exportimport.kernel.service.StagingLocalService.class)
	protected com.liferay.exportimport.kernel.service.StagingLocalService stagingLocalService;
	@BeanReference(type = com.liferay.exportimport.kernel.service.StagingService.class)
	protected com.liferay.exportimport.kernel.service.StagingService stagingService;
	@BeanReference(type = com.liferay.social.kernel.service.SocialActivityLocalService.class)
	protected com.liferay.social.kernel.service.SocialActivityLocalService socialActivityLocalService;
	@BeanReference(type = com.liferay.social.kernel.service.SocialActivityService.class)
	protected com.liferay.social.kernel.service.SocialActivityService socialActivityService;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityFinder.class)
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(type = com.liferay.social.kernel.service.SocialActivitySettingLocalService.class)
	protected com.liferay.social.kernel.service.SocialActivitySettingLocalService socialActivitySettingLocalService;
	@BeanReference(type = com.liferay.social.kernel.service.SocialActivitySettingService.class)
	protected com.liferay.social.kernel.service.SocialActivitySettingService socialActivitySettingService;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = com.liferay.social.kernel.service.SocialRequestLocalService.class)
	protected com.liferay.social.kernel.service.SocialRequestLocalService socialRequestLocalService;
	@BeanReference(type = com.liferay.social.kernel.service.SocialRequestService.class)
	protected com.liferay.social.kernel.service.SocialRequestService socialRequestService;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = com.liferay.trash.kernel.service.TrashEntryLocalService.class)
	protected com.liferay.trash.kernel.service.TrashEntryLocalService trashEntryLocalService;
	@BeanReference(type = com.liferay.trash.kernel.service.TrashEntryService.class)
	protected com.liferay.trash.kernel.service.TrashEntryService trashEntryService;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutService.class)
	protected com.liferay.portal.kernel.service.LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutPrototypeLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutPrototypeLocalService layoutPrototypeLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutPrototypeService.class)
	protected com.liferay.portal.kernel.service.LayoutPrototypeService layoutPrototypeService;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetService.class)
	protected com.liferay.portal.kernel.service.LayoutSetService layoutSetService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetBranchLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutSetBranchLocalService layoutSetBranchLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetBranchService.class)
	protected com.liferay.portal.kernel.service.LayoutSetBranchService layoutSetBranchService;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService layoutSetPrototypeLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutSetPrototypeService.class)
	protected com.liferay.portal.kernel.service.LayoutSetPrototypeService layoutSetPrototypeService;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.MembershipRequestLocalService.class)
	protected com.liferay.portal.kernel.service.MembershipRequestLocalService membershipRequestLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.MembershipRequestService.class)
	protected com.liferay.portal.kernel.service.MembershipRequestService membershipRequestService;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.OrganizationLocalService.class)
	protected com.liferay.portal.kernel.service.OrganizationLocalService organizationLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.OrganizationService.class)
	protected com.liferay.portal.kernel.service.OrganizationService organizationService;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrganizationFinder.class)
	protected OrganizationFinder organizationFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.PortletLocalService.class)
	protected com.liferay.portal.kernel.service.PortletLocalService portletLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.PortletService.class)
	protected com.liferay.portal.kernel.service.PortletService portletService;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.PortletPreferencesLocalService.class)
	protected com.liferay.portal.kernel.service.PortletPreferencesLocalService portletPreferencesLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.PortletPreferencesService.class)
	protected com.liferay.portal.kernel.service.PortletPreferencesService portletPreferencesService;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = PortletPreferencesFinder.class)
	protected PortletPreferencesFinder portletPreferencesFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceActionLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceActionLocalService resourceActionLocalService;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceBlockLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceBlockLocalService resourceBlockLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceBlockService.class)
	protected com.liferay.portal.kernel.service.ResourceBlockService resourceBlockService;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockFinder.class)
	protected ResourceBlockFinder resourceBlockFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourcePermissionLocalService.class)
	protected com.liferay.portal.kernel.service.ResourcePermissionLocalService resourcePermissionLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourcePermissionService.class)
	protected com.liferay.portal.kernel.service.ResourcePermissionService resourcePermissionService;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourcePermissionFinder.class)
	protected ResourcePermissionFinder resourcePermissionFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceTypePermissionLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceTypePermissionLocalService resourceTypePermissionLocalService;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionFinder.class)
	protected ResourceTypePermissionFinder resourceTypePermissionFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.RoleLocalService.class)
	protected com.liferay.portal.kernel.service.RoleLocalService roleLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.RoleService.class)
	protected com.liferay.portal.kernel.service.RoleService roleService;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = RoleFinder.class)
	protected RoleFinder roleFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.SubscriptionLocalService.class)
	protected com.liferay.portal.kernel.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.TeamLocalService.class)
	protected com.liferay.portal.kernel.service.TeamLocalService teamLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.TeamService.class)
	protected com.liferay.portal.kernel.service.TeamService teamService;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TeamFinder.class)
	protected TeamFinder teamFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserService.class)
	protected com.liferay.portal.kernel.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupLocalService.class)
	protected com.liferay.portal.kernel.service.UserGroupLocalService userGroupLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupService.class)
	protected com.liferay.portal.kernel.service.UserGroupService userGroupService;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupFinder.class)
	protected UserGroupFinder userGroupFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService.class)
	protected com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService userGroupGroupRoleLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupGroupRoleService.class)
	protected com.liferay.portal.kernel.service.UserGroupGroupRoleService userGroupGroupRoleService;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupGroupRoleFinder.class)
	protected UserGroupGroupRoleFinder userGroupGroupRoleFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupRoleLocalService.class)
	protected com.liferay.portal.kernel.service.UserGroupRoleLocalService userGroupRoleLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserGroupRoleService.class)
	protected com.liferay.portal.kernel.service.UserGroupRoleService userGroupRoleService;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserGroupRoleFinder.class)
	protected UserGroupRoleFinder userGroupRoleFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService.class)
	protected com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
}