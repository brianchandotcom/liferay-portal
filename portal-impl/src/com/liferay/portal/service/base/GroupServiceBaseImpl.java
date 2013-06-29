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

package com.liferay.portal.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.persistence.AccountPersistence;
import com.liferay.portal.service.persistence.AddressPersistence;
import com.liferay.portal.service.persistence.BackgroundTaskPersistence;
import com.liferay.portal.service.persistence.BrowserTrackerPersistence;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.ClusterGroupPersistence;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.ContactPersistence;
import com.liferay.portal.service.persistence.CountryPersistence;
import com.liferay.portal.service.persistence.EmailAddressPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.LayoutBranchPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutFriendlyURLPersistence;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.LayoutPrototypePersistence;
import com.liferay.portal.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.service.persistence.LayoutSetPersistence;
import com.liferay.portal.service.persistence.LayoutSetPrototypePersistence;
import com.liferay.portal.service.persistence.ListTypePersistence;
import com.liferay.portal.service.persistence.LockFinder;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.MembershipRequestPersistence;
import com.liferay.portal.service.persistence.OrgGroupRolePersistence;
import com.liferay.portal.service.persistence.OrgLaborPersistence;
import com.liferay.portal.service.persistence.OrganizationFinder;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.PasswordPolicyFinder;
import com.liferay.portal.service.persistence.PasswordPolicyPersistence;
import com.liferay.portal.service.persistence.PasswordPolicyRelPersistence;
import com.liferay.portal.service.persistence.PasswordTrackerPersistence;
import com.liferay.portal.service.persistence.PhonePersistence;
import com.liferay.portal.service.persistence.PluginSettingPersistence;
import com.liferay.portal.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.service.persistence.PortletItemPersistence;
import com.liferay.portal.service.persistence.PortletPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.RegionPersistence;
import com.liferay.portal.service.persistence.ReleasePersistence;
import com.liferay.portal.service.persistence.RepositoryEntryPersistence;
import com.liferay.portal.service.persistence.RepositoryPersistence;
import com.liferay.portal.service.persistence.ResourceActionPersistence;
import com.liferay.portal.service.persistence.ResourceBlockFinder;
import com.liferay.portal.service.persistence.ResourceBlockPermissionPersistence;
import com.liferay.portal.service.persistence.ResourceBlockPersistence;
import com.liferay.portal.service.persistence.ResourcePermissionFinder;
import com.liferay.portal.service.persistence.ResourcePermissionPersistence;
import com.liferay.portal.service.persistence.ResourceTypePermissionFinder;
import com.liferay.portal.service.persistence.ResourceTypePermissionPersistence;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.service.persistence.ShardPersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.SystemEventPersistence;
import com.liferay.portal.service.persistence.TeamFinder;
import com.liferay.portal.service.persistence.TeamPersistence;
import com.liferay.portal.service.persistence.TicketPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupGroupRolePersistence;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserGroupRoleFinder;
import com.liferay.portal.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.service.persistence.UserIdMapperPersistence;
import com.liferay.portal.service.persistence.UserNotificationDeliveryPersistence;
import com.liferay.portal.service.persistence.UserNotificationEventPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserTrackerPathPersistence;
import com.liferay.portal.service.persistence.UserTrackerPersistence;
import com.liferay.portal.service.persistence.VirtualHostPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WebsitePersistence;
import com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;

import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyFinder;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderFinder;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypeFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinder;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBBanPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryFinder;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence;
import com.liferay.portlet.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivitySettingPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionPersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryPersistence;
import com.liferay.portlet.wiki.service.persistence.WikiNodePersistence;

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
 * @see com.liferay.portal.service.GroupServiceUtil
 * @generated
 */
public abstract class GroupServiceBaseImpl extends BaseServiceImpl
	implements GroupService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.service.GroupServiceUtil} to access the group remote service.
	 */

	/**
	 * Returns the account local service.
	 *
	 * @return the account local service
	 */
	public com.liferay.portal.service.AccountLocalService getAccountLocalService() {
		return accountLocalService;
	}

	/**
	 * Sets the account local service.
	 *
	 * @param accountLocalService the account local service
	 */
	public void setAccountLocalService(
		com.liferay.portal.service.AccountLocalService accountLocalService) {
		this.accountLocalService = accountLocalService;
	}

	/**
	 * Returns the account remote service.
	 *
	 * @return the account remote service
	 */
	public com.liferay.portal.service.AccountService getAccountService() {
		return accountService;
	}

	/**
	 * Sets the account remote service.
	 *
	 * @param accountService the account remote service
	 */
	public void setAccountService(
		com.liferay.portal.service.AccountService accountService) {
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
	 * Returns the address local service.
	 *
	 * @return the address local service
	 */
	public com.liferay.portal.service.AddressLocalService getAddressLocalService() {
		return addressLocalService;
	}

	/**
	 * Sets the address local service.
	 *
	 * @param addressLocalService the address local service
	 */
	public void setAddressLocalService(
		com.liferay.portal.service.AddressLocalService addressLocalService) {
		this.addressLocalService = addressLocalService;
	}

	/**
	 * Returns the address remote service.
	 *
	 * @return the address remote service
	 */
	public com.liferay.portal.service.AddressService getAddressService() {
		return addressService;
	}

	/**
	 * Sets the address remote service.
	 *
	 * @param addressService the address remote service
	 */
	public void setAddressService(
		com.liferay.portal.service.AddressService addressService) {
		this.addressService = addressService;
	}

	/**
	 * Returns the address persistence.
	 *
	 * @return the address persistence
	 */
	public AddressPersistence getAddressPersistence() {
		return addressPersistence;
	}

	/**
	 * Sets the address persistence.
	 *
	 * @param addressPersistence the address persistence
	 */
	public void setAddressPersistence(AddressPersistence addressPersistence) {
		this.addressPersistence = addressPersistence;
	}

	/**
	 * Returns the background task local service.
	 *
	 * @return the background task local service
	 */
	public com.liferay.portal.service.BackgroundTaskLocalService getBackgroundTaskLocalService() {
		return backgroundTaskLocalService;
	}

	/**
	 * Sets the background task local service.
	 *
	 * @param backgroundTaskLocalService the background task local service
	 */
	public void setBackgroundTaskLocalService(
		com.liferay.portal.service.BackgroundTaskLocalService backgroundTaskLocalService) {
		this.backgroundTaskLocalService = backgroundTaskLocalService;
	}

	/**
	 * Returns the background task persistence.
	 *
	 * @return the background task persistence
	 */
	public BackgroundTaskPersistence getBackgroundTaskPersistence() {
		return backgroundTaskPersistence;
	}

	/**
	 * Sets the background task persistence.
	 *
	 * @param backgroundTaskPersistence the background task persistence
	 */
	public void setBackgroundTaskPersistence(
		BackgroundTaskPersistence backgroundTaskPersistence) {
		this.backgroundTaskPersistence = backgroundTaskPersistence;
	}

	/**
	 * Returns the browser tracker local service.
	 *
	 * @return the browser tracker local service
	 */
	public com.liferay.portal.service.BrowserTrackerLocalService getBrowserTrackerLocalService() {
		return browserTrackerLocalService;
	}

	/**
	 * Sets the browser tracker local service.
	 *
	 * @param browserTrackerLocalService the browser tracker local service
	 */
	public void setBrowserTrackerLocalService(
		com.liferay.portal.service.BrowserTrackerLocalService browserTrackerLocalService) {
		this.browserTrackerLocalService = browserTrackerLocalService;
	}

	/**
	 * Returns the browser tracker persistence.
	 *
	 * @return the browser tracker persistence
	 */
	public BrowserTrackerPersistence getBrowserTrackerPersistence() {
		return browserTrackerPersistence;
	}

	/**
	 * Sets the browser tracker persistence.
	 *
	 * @param browserTrackerPersistence the browser tracker persistence
	 */
	public void setBrowserTrackerPersistence(
		BrowserTrackerPersistence browserTrackerPersistence) {
		this.browserTrackerPersistence = browserTrackerPersistence;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
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
	 * Returns the cluster group local service.
	 *
	 * @return the cluster group local service
	 */
	public com.liferay.portal.service.ClusterGroupLocalService getClusterGroupLocalService() {
		return clusterGroupLocalService;
	}

	/**
	 * Sets the cluster group local service.
	 *
	 * @param clusterGroupLocalService the cluster group local service
	 */
	public void setClusterGroupLocalService(
		com.liferay.portal.service.ClusterGroupLocalService clusterGroupLocalService) {
		this.clusterGroupLocalService = clusterGroupLocalService;
	}

	/**
	 * Returns the cluster group persistence.
	 *
	 * @return the cluster group persistence
	 */
	public ClusterGroupPersistence getClusterGroupPersistence() {
		return clusterGroupPersistence;
	}

	/**
	 * Sets the cluster group persistence.
	 *
	 * @param clusterGroupPersistence the cluster group persistence
	 */
	public void setClusterGroupPersistence(
		ClusterGroupPersistence clusterGroupPersistence) {
		this.clusterGroupPersistence = clusterGroupPersistence;
	}

	/**
	 * Returns the c m i s repository local service.
	 *
	 * @return the c m i s repository local service
	 */
	public com.liferay.portal.service.CMISRepositoryLocalService getCMISRepositoryLocalService() {
		return cmisRepositoryLocalService;
	}

	/**
	 * Sets the c m i s repository local service.
	 *
	 * @param cmisRepositoryLocalService the c m i s repository local service
	 */
	public void setCMISRepositoryLocalService(
		com.liferay.portal.service.CMISRepositoryLocalService cmisRepositoryLocalService) {
		this.cmisRepositoryLocalService = cmisRepositoryLocalService;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company remote service.
	 *
	 * @return the company remote service
	 */
	public com.liferay.portal.service.CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * Sets the company remote service.
	 *
	 * @param companyService the company remote service
	 */
	public void setCompanyService(
		com.liferay.portal.service.CompanyService companyService) {
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
	 * Returns the contact local service.
	 *
	 * @return the contact local service
	 */
	public com.liferay.portal.service.ContactLocalService getContactLocalService() {
		return contactLocalService;
	}

	/**
	 * Sets the contact local service.
	 *
	 * @param contactLocalService the contact local service
	 */
	public void setContactLocalService(
		com.liferay.portal.service.ContactLocalService contactLocalService) {
		this.contactLocalService = contactLocalService;
	}

	/**
	 * Returns the contact remote service.
	 *
	 * @return the contact remote service
	 */
	public com.liferay.portal.service.ContactService getContactService() {
		return contactService;
	}

	/**
	 * Sets the contact remote service.
	 *
	 * @param contactService the contact remote service
	 */
	public void setContactService(
		com.liferay.portal.service.ContactService contactService) {
		this.contactService = contactService;
	}

	/**
	 * Returns the contact persistence.
	 *
	 * @return the contact persistence
	 */
	public ContactPersistence getContactPersistence() {
		return contactPersistence;
	}

	/**
	 * Sets the contact persistence.
	 *
	 * @param contactPersistence the contact persistence
	 */
	public void setContactPersistence(ContactPersistence contactPersistence) {
		this.contactPersistence = contactPersistence;
	}

	/**
	 * Returns the country remote service.
	 *
	 * @return the country remote service
	 */
	public com.liferay.portal.service.CountryService getCountryService() {
		return countryService;
	}

	/**
	 * Sets the country remote service.
	 *
	 * @param countryService the country remote service
	 */
	public void setCountryService(
		com.liferay.portal.service.CountryService countryService) {
		this.countryService = countryService;
	}

	/**
	 * Returns the country persistence.
	 *
	 * @return the country persistence
	 */
	public CountryPersistence getCountryPersistence() {
		return countryPersistence;
	}

	/**
	 * Sets the country persistence.
	 *
	 * @param countryPersistence the country persistence
	 */
	public void setCountryPersistence(CountryPersistence countryPersistence) {
		this.countryPersistence = countryPersistence;
	}

	/**
	 * Returns the email address local service.
	 *
	 * @return the email address local service
	 */
	public com.liferay.portal.service.EmailAddressLocalService getEmailAddressLocalService() {
		return emailAddressLocalService;
	}

	/**
	 * Sets the email address local service.
	 *
	 * @param emailAddressLocalService the email address local service
	 */
	public void setEmailAddressLocalService(
		com.liferay.portal.service.EmailAddressLocalService emailAddressLocalService) {
		this.emailAddressLocalService = emailAddressLocalService;
	}

	/**
	 * Returns the email address remote service.
	 *
	 * @return the email address remote service
	 */
	public com.liferay.portal.service.EmailAddressService getEmailAddressService() {
		return emailAddressService;
	}

	/**
	 * Sets the email address remote service.
	 *
	 * @param emailAddressService the email address remote service
	 */
	public void setEmailAddressService(
		com.liferay.portal.service.EmailAddressService emailAddressService) {
		this.emailAddressService = emailAddressService;
	}

	/**
	 * Returns the email address persistence.
	 *
	 * @return the email address persistence
	 */
	public EmailAddressPersistence getEmailAddressPersistence() {
		return emailAddressPersistence;
	}

	/**
	 * Sets the email address persistence.
	 *
	 * @param emailAddressPersistence the email address persistence
	 */
	public void setEmailAddressPersistence(
		EmailAddressPersistence emailAddressPersistence) {
		this.emailAddressPersistence = emailAddressPersistence;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group remote service.
	 *
	 * @return the group remote service
	 */
	public com.liferay.portal.service.GroupService getGroupService() {
		return groupService;
	}

	/**
	 * Sets the group remote service.
	 *
	 * @param groupService the group remote service
	 */
	public void setGroupService(
		com.liferay.portal.service.GroupService groupService) {
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
	 * Returns the image local service.
	 *
	 * @return the image local service
	 */
	public com.liferay.portal.service.ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	/**
	 * Sets the image local service.
	 *
	 * @param imageLocalService the image local service
	 */
	public void setImageLocalService(
		com.liferay.portal.service.ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	/**
	 * Returns the image remote service.
	 *
	 * @return the image remote service
	 */
	public com.liferay.portal.service.ImageService getImageService() {
		return imageService;
	}

	/**
	 * Sets the image remote service.
	 *
	 * @param imageService the image remote service
	 */
	public void setImageService(
		com.liferay.portal.service.ImageService imageService) {
		this.imageService = imageService;
	}

	/**
	 * Returns the image persistence.
	 *
	 * @return the image persistence
	 */
	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	/**
	 * Sets the image persistence.
	 *
	 * @param imagePersistence the image persistence
	 */
	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	/**
	 * Returns the layout local service.
	 *
	 * @return the layout local service
	 */
	public com.liferay.portal.service.LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	/**
	 * Sets the layout local service.
	 *
	 * @param layoutLocalService the layout local service
	 */
	public void setLayoutLocalService(
		com.liferay.portal.service.LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	/**
	 * Returns the layout remote service.
	 *
	 * @return the layout remote service
	 */
	public com.liferay.portal.service.LayoutService getLayoutService() {
		return layoutService;
	}

	/**
	 * Sets the layout remote service.
	 *
	 * @param layoutService the layout remote service
	 */
	public void setLayoutService(
		com.liferay.portal.service.LayoutService layoutService) {
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
	 * Returns the layout branch local service.
	 *
	 * @return the layout branch local service
	 */
	public com.liferay.portal.service.LayoutBranchLocalService getLayoutBranchLocalService() {
		return layoutBranchLocalService;
	}

	/**
	 * Sets the layout branch local service.
	 *
	 * @param layoutBranchLocalService the layout branch local service
	 */
	public void setLayoutBranchLocalService(
		com.liferay.portal.service.LayoutBranchLocalService layoutBranchLocalService) {
		this.layoutBranchLocalService = layoutBranchLocalService;
	}

	/**
	 * Returns the layout branch remote service.
	 *
	 * @return the layout branch remote service
	 */
	public com.liferay.portal.service.LayoutBranchService getLayoutBranchService() {
		return layoutBranchService;
	}

	/**
	 * Sets the layout branch remote service.
	 *
	 * @param layoutBranchService the layout branch remote service
	 */
	public void setLayoutBranchService(
		com.liferay.portal.service.LayoutBranchService layoutBranchService) {
		this.layoutBranchService = layoutBranchService;
	}

	/**
	 * Returns the layout branch persistence.
	 *
	 * @return the layout branch persistence
	 */
	public LayoutBranchPersistence getLayoutBranchPersistence() {
		return layoutBranchPersistence;
	}

	/**
	 * Sets the layout branch persistence.
	 *
	 * @param layoutBranchPersistence the layout branch persistence
	 */
	public void setLayoutBranchPersistence(
		LayoutBranchPersistence layoutBranchPersistence) {
		this.layoutBranchPersistence = layoutBranchPersistence;
	}

	/**
	 * Returns the layout friendly u r l local service.
	 *
	 * @return the layout friendly u r l local service
	 */
	public com.liferay.portal.service.LayoutFriendlyURLLocalService getLayoutFriendlyURLLocalService() {
		return layoutFriendlyURLLocalService;
	}

	/**
	 * Sets the layout friendly u r l local service.
	 *
	 * @param layoutFriendlyURLLocalService the layout friendly u r l local service
	 */
	public void setLayoutFriendlyURLLocalService(
		com.liferay.portal.service.LayoutFriendlyURLLocalService layoutFriendlyURLLocalService) {
		this.layoutFriendlyURLLocalService = layoutFriendlyURLLocalService;
	}

	/**
	 * Returns the layout friendly u r l persistence.
	 *
	 * @return the layout friendly u r l persistence
	 */
	public LayoutFriendlyURLPersistence getLayoutFriendlyURLPersistence() {
		return layoutFriendlyURLPersistence;
	}

	/**
	 * Sets the layout friendly u r l persistence.
	 *
	 * @param layoutFriendlyURLPersistence the layout friendly u r l persistence
	 */
	public void setLayoutFriendlyURLPersistence(
		LayoutFriendlyURLPersistence layoutFriendlyURLPersistence) {
		this.layoutFriendlyURLPersistence = layoutFriendlyURLPersistence;
	}

	/**
	 * Returns the layout prototype local service.
	 *
	 * @return the layout prototype local service
	 */
	public com.liferay.portal.service.LayoutPrototypeLocalService getLayoutPrototypeLocalService() {
		return layoutPrototypeLocalService;
	}

	/**
	 * Sets the layout prototype local service.
	 *
	 * @param layoutPrototypeLocalService the layout prototype local service
	 */
	public void setLayoutPrototypeLocalService(
		com.liferay.portal.service.LayoutPrototypeLocalService layoutPrototypeLocalService) {
		this.layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	/**
	 * Returns the layout prototype remote service.
	 *
	 * @return the layout prototype remote service
	 */
	public com.liferay.portal.service.LayoutPrototypeService getLayoutPrototypeService() {
		return layoutPrototypeService;
	}

	/**
	 * Sets the layout prototype remote service.
	 *
	 * @param layoutPrototypeService the layout prototype remote service
	 */
	public void setLayoutPrototypeService(
		com.liferay.portal.service.LayoutPrototypeService layoutPrototypeService) {
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
	 * Returns the layout revision local service.
	 *
	 * @return the layout revision local service
	 */
	public com.liferay.portal.service.LayoutRevisionLocalService getLayoutRevisionLocalService() {
		return layoutRevisionLocalService;
	}

	/**
	 * Sets the layout revision local service.
	 *
	 * @param layoutRevisionLocalService the layout revision local service
	 */
	public void setLayoutRevisionLocalService(
		com.liferay.portal.service.LayoutRevisionLocalService layoutRevisionLocalService) {
		this.layoutRevisionLocalService = layoutRevisionLocalService;
	}

	/**
	 * Returns the layout revision remote service.
	 *
	 * @return the layout revision remote service
	 */
	public com.liferay.portal.service.LayoutRevisionService getLayoutRevisionService() {
		return layoutRevisionService;
	}

	/**
	 * Sets the layout revision remote service.
	 *
	 * @param layoutRevisionService the layout revision remote service
	 */
	public void setLayoutRevisionService(
		com.liferay.portal.service.LayoutRevisionService layoutRevisionService) {
		this.layoutRevisionService = layoutRevisionService;
	}

	/**
	 * Returns the layout revision persistence.
	 *
	 * @return the layout revision persistence
	 */
	public LayoutRevisionPersistence getLayoutRevisionPersistence() {
		return layoutRevisionPersistence;
	}

	/**
	 * Sets the layout revision persistence.
	 *
	 * @param layoutRevisionPersistence the layout revision persistence
	 */
	public void setLayoutRevisionPersistence(
		LayoutRevisionPersistence layoutRevisionPersistence) {
		this.layoutRevisionPersistence = layoutRevisionPersistence;
	}

	/**
	 * Returns the layout set local service.
	 *
	 * @return the layout set local service
	 */
	public com.liferay.portal.service.LayoutSetLocalService getLayoutSetLocalService() {
		return layoutSetLocalService;
	}

	/**
	 * Sets the layout set local service.
	 *
	 * @param layoutSetLocalService the layout set local service
	 */
	public void setLayoutSetLocalService(
		com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService) {
		this.layoutSetLocalService = layoutSetLocalService;
	}

	/**
	 * Returns the layout set remote service.
	 *
	 * @return the layout set remote service
	 */
	public com.liferay.portal.service.LayoutSetService getLayoutSetService() {
		return layoutSetService;
	}

	/**
	 * Sets the layout set remote service.
	 *
	 * @param layoutSetService the layout set remote service
	 */
	public void setLayoutSetService(
		com.liferay.portal.service.LayoutSetService layoutSetService) {
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
	public com.liferay.portal.service.LayoutSetBranchLocalService getLayoutSetBranchLocalService() {
		return layoutSetBranchLocalService;
	}

	/**
	 * Sets the layout set branch local service.
	 *
	 * @param layoutSetBranchLocalService the layout set branch local service
	 */
	public void setLayoutSetBranchLocalService(
		com.liferay.portal.service.LayoutSetBranchLocalService layoutSetBranchLocalService) {
		this.layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	/**
	 * Returns the layout set branch remote service.
	 *
	 * @return the layout set branch remote service
	 */
	public com.liferay.portal.service.LayoutSetBranchService getLayoutSetBranchService() {
		return layoutSetBranchService;
	}

	/**
	 * Sets the layout set branch remote service.
	 *
	 * @param layoutSetBranchService the layout set branch remote service
	 */
	public void setLayoutSetBranchService(
		com.liferay.portal.service.LayoutSetBranchService layoutSetBranchService) {
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
	public com.liferay.portal.service.LayoutSetPrototypeLocalService getLayoutSetPrototypeLocalService() {
		return layoutSetPrototypeLocalService;
	}

	/**
	 * Sets the layout set prototype local service.
	 *
	 * @param layoutSetPrototypeLocalService the layout set prototype local service
	 */
	public void setLayoutSetPrototypeLocalService(
		com.liferay.portal.service.LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {
		this.layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	/**
	 * Returns the layout set prototype remote service.
	 *
	 * @return the layout set prototype remote service
	 */
	public com.liferay.portal.service.LayoutSetPrototypeService getLayoutSetPrototypeService() {
		return layoutSetPrototypeService;
	}

	/**
	 * Sets the layout set prototype remote service.
	 *
	 * @param layoutSetPrototypeService the layout set prototype remote service
	 */
	public void setLayoutSetPrototypeService(
		com.liferay.portal.service.LayoutSetPrototypeService layoutSetPrototypeService) {
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
	 * Returns the layout template local service.
	 *
	 * @return the layout template local service
	 */
	public com.liferay.portal.service.LayoutTemplateLocalService getLayoutTemplateLocalService() {
		return layoutTemplateLocalService;
	}

	/**
	 * Sets the layout template local service.
	 *
	 * @param layoutTemplateLocalService the layout template local service
	 */
	public void setLayoutTemplateLocalService(
		com.liferay.portal.service.LayoutTemplateLocalService layoutTemplateLocalService) {
		this.layoutTemplateLocalService = layoutTemplateLocalService;
	}

	/**
	 * Returns the list type remote service.
	 *
	 * @return the list type remote service
	 */
	public com.liferay.portal.service.ListTypeService getListTypeService() {
		return listTypeService;
	}

	/**
	 * Sets the list type remote service.
	 *
	 * @param listTypeService the list type remote service
	 */
	public void setListTypeService(
		com.liferay.portal.service.ListTypeService listTypeService) {
		this.listTypeService = listTypeService;
	}

	/**
	 * Returns the list type persistence.
	 *
	 * @return the list type persistence
	 */
	public ListTypePersistence getListTypePersistence() {
		return listTypePersistence;
	}

	/**
	 * Sets the list type persistence.
	 *
	 * @param listTypePersistence the list type persistence
	 */
	public void setListTypePersistence(ListTypePersistence listTypePersistence) {
		this.listTypePersistence = listTypePersistence;
	}

	/**
	 * Returns the lock local service.
	 *
	 * @return the lock local service
	 */
	public com.liferay.portal.service.LockLocalService getLockLocalService() {
		return lockLocalService;
	}

	/**
	 * Sets the lock local service.
	 *
	 * @param lockLocalService the lock local service
	 */
	public void setLockLocalService(
		com.liferay.portal.service.LockLocalService lockLocalService) {
		this.lockLocalService = lockLocalService;
	}

	/**
	 * Returns the lock persistence.
	 *
	 * @return the lock persistence
	 */
	public LockPersistence getLockPersistence() {
		return lockPersistence;
	}

	/**
	 * Sets the lock persistence.
	 *
	 * @param lockPersistence the lock persistence
	 */
	public void setLockPersistence(LockPersistence lockPersistence) {
		this.lockPersistence = lockPersistence;
	}

	/**
	 * Returns the lock finder.
	 *
	 * @return the lock finder
	 */
	public LockFinder getLockFinder() {
		return lockFinder;
	}

	/**
	 * Sets the lock finder.
	 *
	 * @param lockFinder the lock finder
	 */
	public void setLockFinder(LockFinder lockFinder) {
		this.lockFinder = lockFinder;
	}

	/**
	 * Returns the membership request local service.
	 *
	 * @return the membership request local service
	 */
	public com.liferay.portal.service.MembershipRequestLocalService getMembershipRequestLocalService() {
		return membershipRequestLocalService;
	}

	/**
	 * Sets the membership request local service.
	 *
	 * @param membershipRequestLocalService the membership request local service
	 */
	public void setMembershipRequestLocalService(
		com.liferay.portal.service.MembershipRequestLocalService membershipRequestLocalService) {
		this.membershipRequestLocalService = membershipRequestLocalService;
	}

	/**
	 * Returns the membership request remote service.
	 *
	 * @return the membership request remote service
	 */
	public com.liferay.portal.service.MembershipRequestService getMembershipRequestService() {
		return membershipRequestService;
	}

	/**
	 * Sets the membership request remote service.
	 *
	 * @param membershipRequestService the membership request remote service
	 */
	public void setMembershipRequestService(
		com.liferay.portal.service.MembershipRequestService membershipRequestService) {
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
	public com.liferay.portal.service.OrganizationLocalService getOrganizationLocalService() {
		return organizationLocalService;
	}

	/**
	 * Sets the organization local service.
	 *
	 * @param organizationLocalService the organization local service
	 */
	public void setOrganizationLocalService(
		com.liferay.portal.service.OrganizationLocalService organizationLocalService) {
		this.organizationLocalService = organizationLocalService;
	}

	/**
	 * Returns the organization remote service.
	 *
	 * @return the organization remote service
	 */
	public com.liferay.portal.service.OrganizationService getOrganizationService() {
		return organizationService;
	}

	/**
	 * Sets the organization remote service.
	 *
	 * @param organizationService the organization remote service
	 */
	public void setOrganizationService(
		com.liferay.portal.service.OrganizationService organizationService) {
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
	 * Returns the org group role persistence.
	 *
	 * @return the org group role persistence
	 */
	public OrgGroupRolePersistence getOrgGroupRolePersistence() {
		return orgGroupRolePersistence;
	}

	/**
	 * Sets the org group role persistence.
	 *
	 * @param orgGroupRolePersistence the org group role persistence
	 */
	public void setOrgGroupRolePersistence(
		OrgGroupRolePersistence orgGroupRolePersistence) {
		this.orgGroupRolePersistence = orgGroupRolePersistence;
	}

	/**
	 * Returns the org labor local service.
	 *
	 * @return the org labor local service
	 */
	public com.liferay.portal.service.OrgLaborLocalService getOrgLaborLocalService() {
		return orgLaborLocalService;
	}

	/**
	 * Sets the org labor local service.
	 *
	 * @param orgLaborLocalService the org labor local service
	 */
	public void setOrgLaborLocalService(
		com.liferay.portal.service.OrgLaborLocalService orgLaborLocalService) {
		this.orgLaborLocalService = orgLaborLocalService;
	}

	/**
	 * Returns the org labor remote service.
	 *
	 * @return the org labor remote service
	 */
	public com.liferay.portal.service.OrgLaborService getOrgLaborService() {
		return orgLaborService;
	}

	/**
	 * Sets the org labor remote service.
	 *
	 * @param orgLaborService the org labor remote service
	 */
	public void setOrgLaborService(
		com.liferay.portal.service.OrgLaborService orgLaborService) {
		this.orgLaborService = orgLaborService;
	}

	/**
	 * Returns the org labor persistence.
	 *
	 * @return the org labor persistence
	 */
	public OrgLaborPersistence getOrgLaborPersistence() {
		return orgLaborPersistence;
	}

	/**
	 * Sets the org labor persistence.
	 *
	 * @param orgLaborPersistence the org labor persistence
	 */
	public void setOrgLaborPersistence(OrgLaborPersistence orgLaborPersistence) {
		this.orgLaborPersistence = orgLaborPersistence;
	}

	/**
	 * Returns the password policy local service.
	 *
	 * @return the password policy local service
	 */
	public com.liferay.portal.service.PasswordPolicyLocalService getPasswordPolicyLocalService() {
		return passwordPolicyLocalService;
	}

	/**
	 * Sets the password policy local service.
	 *
	 * @param passwordPolicyLocalService the password policy local service
	 */
	public void setPasswordPolicyLocalService(
		com.liferay.portal.service.PasswordPolicyLocalService passwordPolicyLocalService) {
		this.passwordPolicyLocalService = passwordPolicyLocalService;
	}

	/**
	 * Returns the password policy remote service.
	 *
	 * @return the password policy remote service
	 */
	public com.liferay.portal.service.PasswordPolicyService getPasswordPolicyService() {
		return passwordPolicyService;
	}

	/**
	 * Sets the password policy remote service.
	 *
	 * @param passwordPolicyService the password policy remote service
	 */
	public void setPasswordPolicyService(
		com.liferay.portal.service.PasswordPolicyService passwordPolicyService) {
		this.passwordPolicyService = passwordPolicyService;
	}

	/**
	 * Returns the password policy persistence.
	 *
	 * @return the password policy persistence
	 */
	public PasswordPolicyPersistence getPasswordPolicyPersistence() {
		return passwordPolicyPersistence;
	}

	/**
	 * Sets the password policy persistence.
	 *
	 * @param passwordPolicyPersistence the password policy persistence
	 */
	public void setPasswordPolicyPersistence(
		PasswordPolicyPersistence passwordPolicyPersistence) {
		this.passwordPolicyPersistence = passwordPolicyPersistence;
	}

	/**
	 * Returns the password policy finder.
	 *
	 * @return the password policy finder
	 */
	public PasswordPolicyFinder getPasswordPolicyFinder() {
		return passwordPolicyFinder;
	}

	/**
	 * Sets the password policy finder.
	 *
	 * @param passwordPolicyFinder the password policy finder
	 */
	public void setPasswordPolicyFinder(
		PasswordPolicyFinder passwordPolicyFinder) {
		this.passwordPolicyFinder = passwordPolicyFinder;
	}

	/**
	 * Returns the password policy rel local service.
	 *
	 * @return the password policy rel local service
	 */
	public com.liferay.portal.service.PasswordPolicyRelLocalService getPasswordPolicyRelLocalService() {
		return passwordPolicyRelLocalService;
	}

	/**
	 * Sets the password policy rel local service.
	 *
	 * @param passwordPolicyRelLocalService the password policy rel local service
	 */
	public void setPasswordPolicyRelLocalService(
		com.liferay.portal.service.PasswordPolicyRelLocalService passwordPolicyRelLocalService) {
		this.passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	/**
	 * Returns the password policy rel persistence.
	 *
	 * @return the password policy rel persistence
	 */
	public PasswordPolicyRelPersistence getPasswordPolicyRelPersistence() {
		return passwordPolicyRelPersistence;
	}

	/**
	 * Sets the password policy rel persistence.
	 *
	 * @param passwordPolicyRelPersistence the password policy rel persistence
	 */
	public void setPasswordPolicyRelPersistence(
		PasswordPolicyRelPersistence passwordPolicyRelPersistence) {
		this.passwordPolicyRelPersistence = passwordPolicyRelPersistence;
	}

	/**
	 * Returns the password tracker local service.
	 *
	 * @return the password tracker local service
	 */
	public com.liferay.portal.service.PasswordTrackerLocalService getPasswordTrackerLocalService() {
		return passwordTrackerLocalService;
	}

	/**
	 * Sets the password tracker local service.
	 *
	 * @param passwordTrackerLocalService the password tracker local service
	 */
	public void setPasswordTrackerLocalService(
		com.liferay.portal.service.PasswordTrackerLocalService passwordTrackerLocalService) {
		this.passwordTrackerLocalService = passwordTrackerLocalService;
	}

	/**
	 * Returns the password tracker persistence.
	 *
	 * @return the password tracker persistence
	 */
	public PasswordTrackerPersistence getPasswordTrackerPersistence() {
		return passwordTrackerPersistence;
	}

	/**
	 * Sets the password tracker persistence.
	 *
	 * @param passwordTrackerPersistence the password tracker persistence
	 */
	public void setPasswordTrackerPersistence(
		PasswordTrackerPersistence passwordTrackerPersistence) {
		this.passwordTrackerPersistence = passwordTrackerPersistence;
	}

	/**
	 * Returns the permission remote service.
	 *
	 * @return the permission remote service
	 */
	public com.liferay.portal.service.PermissionService getPermissionService() {
		return permissionService;
	}

	/**
	 * Sets the permission remote service.
	 *
	 * @param permissionService the permission remote service
	 */
	public void setPermissionService(
		com.liferay.portal.service.PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	/**
	 * Returns the phone local service.
	 *
	 * @return the phone local service
	 */
	public com.liferay.portal.service.PhoneLocalService getPhoneLocalService() {
		return phoneLocalService;
	}

	/**
	 * Sets the phone local service.
	 *
	 * @param phoneLocalService the phone local service
	 */
	public void setPhoneLocalService(
		com.liferay.portal.service.PhoneLocalService phoneLocalService) {
		this.phoneLocalService = phoneLocalService;
	}

	/**
	 * Returns the phone remote service.
	 *
	 * @return the phone remote service
	 */
	public com.liferay.portal.service.PhoneService getPhoneService() {
		return phoneService;
	}

	/**
	 * Sets the phone remote service.
	 *
	 * @param phoneService the phone remote service
	 */
	public void setPhoneService(
		com.liferay.portal.service.PhoneService phoneService) {
		this.phoneService = phoneService;
	}

	/**
	 * Returns the phone persistence.
	 *
	 * @return the phone persistence
	 */
	public PhonePersistence getPhonePersistence() {
		return phonePersistence;
	}

	/**
	 * Sets the phone persistence.
	 *
	 * @param phonePersistence the phone persistence
	 */
	public void setPhonePersistence(PhonePersistence phonePersistence) {
		this.phonePersistence = phonePersistence;
	}

	/**
	 * Returns the plugin setting local service.
	 *
	 * @return the plugin setting local service
	 */
	public com.liferay.portal.service.PluginSettingLocalService getPluginSettingLocalService() {
		return pluginSettingLocalService;
	}

	/**
	 * Sets the plugin setting local service.
	 *
	 * @param pluginSettingLocalService the plugin setting local service
	 */
	public void setPluginSettingLocalService(
		com.liferay.portal.service.PluginSettingLocalService pluginSettingLocalService) {
		this.pluginSettingLocalService = pluginSettingLocalService;
	}

	/**
	 * Returns the plugin setting remote service.
	 *
	 * @return the plugin setting remote service
	 */
	public com.liferay.portal.service.PluginSettingService getPluginSettingService() {
		return pluginSettingService;
	}

	/**
	 * Sets the plugin setting remote service.
	 *
	 * @param pluginSettingService the plugin setting remote service
	 */
	public void setPluginSettingService(
		com.liferay.portal.service.PluginSettingService pluginSettingService) {
		this.pluginSettingService = pluginSettingService;
	}

	/**
	 * Returns the plugin setting persistence.
	 *
	 * @return the plugin setting persistence
	 */
	public PluginSettingPersistence getPluginSettingPersistence() {
		return pluginSettingPersistence;
	}

	/**
	 * Sets the plugin setting persistence.
	 *
	 * @param pluginSettingPersistence the plugin setting persistence
	 */
	public void setPluginSettingPersistence(
		PluginSettingPersistence pluginSettingPersistence) {
		this.pluginSettingPersistence = pluginSettingPersistence;
	}

	/**
	 * Returns the portal local service.
	 *
	 * @return the portal local service
	 */
	public com.liferay.portal.service.PortalLocalService getPortalLocalService() {
		return portalLocalService;
	}

	/**
	 * Sets the portal local service.
	 *
	 * @param portalLocalService the portal local service
	 */
	public void setPortalLocalService(
		com.liferay.portal.service.PortalLocalService portalLocalService) {
		this.portalLocalService = portalLocalService;
	}

	/**
	 * Returns the portal remote service.
	 *
	 * @return the portal remote service
	 */
	public com.liferay.portal.service.PortalService getPortalService() {
		return portalService;
	}

	/**
	 * Sets the portal remote service.
	 *
	 * @param portalService the portal remote service
	 */
	public void setPortalService(
		com.liferay.portal.service.PortalService portalService) {
		this.portalService = portalService;
	}

	/**
	 * Returns the portal preferences local service.
	 *
	 * @return the portal preferences local service
	 */
	public com.liferay.portal.service.PortalPreferencesLocalService getPortalPreferencesLocalService() {
		return portalPreferencesLocalService;
	}

	/**
	 * Sets the portal preferences local service.
	 *
	 * @param portalPreferencesLocalService the portal preferences local service
	 */
	public void setPortalPreferencesLocalService(
		com.liferay.portal.service.PortalPreferencesLocalService portalPreferencesLocalService) {
		this.portalPreferencesLocalService = portalPreferencesLocalService;
	}

	/**
	 * Returns the portal preferences persistence.
	 *
	 * @return the portal preferences persistence
	 */
	public PortalPreferencesPersistence getPortalPreferencesPersistence() {
		return portalPreferencesPersistence;
	}

	/**
	 * Sets the portal preferences persistence.
	 *
	 * @param portalPreferencesPersistence the portal preferences persistence
	 */
	public void setPortalPreferencesPersistence(
		PortalPreferencesPersistence portalPreferencesPersistence) {
		this.portalPreferencesPersistence = portalPreferencesPersistence;
	}

	/**
	 * Returns the portlet local service.
	 *
	 * @return the portlet local service
	 */
	public com.liferay.portal.service.PortletLocalService getPortletLocalService() {
		return portletLocalService;
	}

	/**
	 * Sets the portlet local service.
	 *
	 * @param portletLocalService the portlet local service
	 */
	public void setPortletLocalService(
		com.liferay.portal.service.PortletLocalService portletLocalService) {
		this.portletLocalService = portletLocalService;
	}

	/**
	 * Returns the portlet remote service.
	 *
	 * @return the portlet remote service
	 */
	public com.liferay.portal.service.PortletService getPortletService() {
		return portletService;
	}

	/**
	 * Sets the portlet remote service.
	 *
	 * @param portletService the portlet remote service
	 */
	public void setPortletService(
		com.liferay.portal.service.PortletService portletService) {
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
	 * Returns the portlet item local service.
	 *
	 * @return the portlet item local service
	 */
	public com.liferay.portal.service.PortletItemLocalService getPortletItemLocalService() {
		return portletItemLocalService;
	}

	/**
	 * Sets the portlet item local service.
	 *
	 * @param portletItemLocalService the portlet item local service
	 */
	public void setPortletItemLocalService(
		com.liferay.portal.service.PortletItemLocalService portletItemLocalService) {
		this.portletItemLocalService = portletItemLocalService;
	}

	/**
	 * Returns the portlet item persistence.
	 *
	 * @return the portlet item persistence
	 */
	public PortletItemPersistence getPortletItemPersistence() {
		return portletItemPersistence;
	}

	/**
	 * Sets the portlet item persistence.
	 *
	 * @param portletItemPersistence the portlet item persistence
	 */
	public void setPortletItemPersistence(
		PortletItemPersistence portletItemPersistence) {
		this.portletItemPersistence = portletItemPersistence;
	}

	/**
	 * Returns the portlet preferences local service.
	 *
	 * @return the portlet preferences local service
	 */
	public com.liferay.portal.service.PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	/**
	 * Sets the portlet preferences local service.
	 *
	 * @param portletPreferencesLocalService the portlet preferences local service
	 */
	public void setPortletPreferencesLocalService(
		com.liferay.portal.service.PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	/**
	 * Returns the portlet preferences remote service.
	 *
	 * @return the portlet preferences remote service
	 */
	public com.liferay.portal.service.PortletPreferencesService getPortletPreferencesService() {
		return portletPreferencesService;
	}

	/**
	 * Sets the portlet preferences remote service.
	 *
	 * @param portletPreferencesService the portlet preferences remote service
	 */
	public void setPortletPreferencesService(
		com.liferay.portal.service.PortletPreferencesService portletPreferencesService) {
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
	 * Returns the quartz local service.
	 *
	 * @return the quartz local service
	 */
	public com.liferay.portal.service.QuartzLocalService getQuartzLocalService() {
		return quartzLocalService;
	}

	/**
	 * Sets the quartz local service.
	 *
	 * @param quartzLocalService the quartz local service
	 */
	public void setQuartzLocalService(
		com.liferay.portal.service.QuartzLocalService quartzLocalService) {
		this.quartzLocalService = quartzLocalService;
	}

	/**
	 * Returns the region remote service.
	 *
	 * @return the region remote service
	 */
	public com.liferay.portal.service.RegionService getRegionService() {
		return regionService;
	}

	/**
	 * Sets the region remote service.
	 *
	 * @param regionService the region remote service
	 */
	public void setRegionService(
		com.liferay.portal.service.RegionService regionService) {
		this.regionService = regionService;
	}

	/**
	 * Returns the region persistence.
	 *
	 * @return the region persistence
	 */
	public RegionPersistence getRegionPersistence() {
		return regionPersistence;
	}

	/**
	 * Sets the region persistence.
	 *
	 * @param regionPersistence the region persistence
	 */
	public void setRegionPersistence(RegionPersistence regionPersistence) {
		this.regionPersistence = regionPersistence;
	}

	/**
	 * Returns the release local service.
	 *
	 * @return the release local service
	 */
	public com.liferay.portal.service.ReleaseLocalService getReleaseLocalService() {
		return releaseLocalService;
	}

	/**
	 * Sets the release local service.
	 *
	 * @param releaseLocalService the release local service
	 */
	public void setReleaseLocalService(
		com.liferay.portal.service.ReleaseLocalService releaseLocalService) {
		this.releaseLocalService = releaseLocalService;
	}

	/**
	 * Returns the release persistence.
	 *
	 * @return the release persistence
	 */
	public ReleasePersistence getReleasePersistence() {
		return releasePersistence;
	}

	/**
	 * Sets the release persistence.
	 *
	 * @param releasePersistence the release persistence
	 */
	public void setReleasePersistence(ReleasePersistence releasePersistence) {
		this.releasePersistence = releasePersistence;
	}

	/**
	 * Returns the repository local service.
	 *
	 * @return the repository local service
	 */
	public com.liferay.portal.service.RepositoryLocalService getRepositoryLocalService() {
		return repositoryLocalService;
	}

	/**
	 * Sets the repository local service.
	 *
	 * @param repositoryLocalService the repository local service
	 */
	public void setRepositoryLocalService(
		com.liferay.portal.service.RepositoryLocalService repositoryLocalService) {
		this.repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Returns the repository remote service.
	 *
	 * @return the repository remote service
	 */
	public com.liferay.portal.service.RepositoryService getRepositoryService() {
		return repositoryService;
	}

	/**
	 * Sets the repository remote service.
	 *
	 * @param repositoryService the repository remote service
	 */
	public void setRepositoryService(
		com.liferay.portal.service.RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * Returns the repository persistence.
	 *
	 * @return the repository persistence
	 */
	public RepositoryPersistence getRepositoryPersistence() {
		return repositoryPersistence;
	}

	/**
	 * Sets the repository persistence.
	 *
	 * @param repositoryPersistence the repository persistence
	 */
	public void setRepositoryPersistence(
		RepositoryPersistence repositoryPersistence) {
		this.repositoryPersistence = repositoryPersistence;
	}

	/**
	 * Returns the repository entry local service.
	 *
	 * @return the repository entry local service
	 */
	public com.liferay.portal.service.RepositoryEntryLocalService getRepositoryEntryLocalService() {
		return repositoryEntryLocalService;
	}

	/**
	 * Sets the repository entry local service.
	 *
	 * @param repositoryEntryLocalService the repository entry local service
	 */
	public void setRepositoryEntryLocalService(
		com.liferay.portal.service.RepositoryEntryLocalService repositoryEntryLocalService) {
		this.repositoryEntryLocalService = repositoryEntryLocalService;
	}

	/**
	 * Returns the repository entry persistence.
	 *
	 * @return the repository entry persistence
	 */
	public RepositoryEntryPersistence getRepositoryEntryPersistence() {
		return repositoryEntryPersistence;
	}

	/**
	 * Sets the repository entry persistence.
	 *
	 * @param repositoryEntryPersistence the repository entry persistence
	 */
	public void setRepositoryEntryPersistence(
		RepositoryEntryPersistence repositoryEntryPersistence) {
		this.repositoryEntryPersistence = repositoryEntryPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource action local service.
	 *
	 * @return the resource action local service
	 */
	public com.liferay.portal.service.ResourceActionLocalService getResourceActionLocalService() {
		return resourceActionLocalService;
	}

	/**
	 * Sets the resource action local service.
	 *
	 * @param resourceActionLocalService the resource action local service
	 */
	public void setResourceActionLocalService(
		com.liferay.portal.service.ResourceActionLocalService resourceActionLocalService) {
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
	public com.liferay.portal.service.ResourceBlockLocalService getResourceBlockLocalService() {
		return resourceBlockLocalService;
	}

	/**
	 * Sets the resource block local service.
	 *
	 * @param resourceBlockLocalService the resource block local service
	 */
	public void setResourceBlockLocalService(
		com.liferay.portal.service.ResourceBlockLocalService resourceBlockLocalService) {
		this.resourceBlockLocalService = resourceBlockLocalService;
	}

	/**
	 * Returns the resource block remote service.
	 *
	 * @return the resource block remote service
	 */
	public com.liferay.portal.service.ResourceBlockService getResourceBlockService() {
		return resourceBlockService;
	}

	/**
	 * Sets the resource block remote service.
	 *
	 * @param resourceBlockService the resource block remote service
	 */
	public void setResourceBlockService(
		com.liferay.portal.service.ResourceBlockService resourceBlockService) {
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
	 * Returns the resource block permission local service.
	 *
	 * @return the resource block permission local service
	 */
	public com.liferay.portal.service.ResourceBlockPermissionLocalService getResourceBlockPermissionLocalService() {
		return resourceBlockPermissionLocalService;
	}

	/**
	 * Sets the resource block permission local service.
	 *
	 * @param resourceBlockPermissionLocalService the resource block permission local service
	 */
	public void setResourceBlockPermissionLocalService(
		com.liferay.portal.service.ResourceBlockPermissionLocalService resourceBlockPermissionLocalService) {
		this.resourceBlockPermissionLocalService = resourceBlockPermissionLocalService;
	}

	/**
	 * Returns the resource block permission persistence.
	 *
	 * @return the resource block permission persistence
	 */
	public ResourceBlockPermissionPersistence getResourceBlockPermissionPersistence() {
		return resourceBlockPermissionPersistence;
	}

	/**
	 * Sets the resource block permission persistence.
	 *
	 * @param resourceBlockPermissionPersistence the resource block permission persistence
	 */
	public void setResourceBlockPermissionPersistence(
		ResourceBlockPermissionPersistence resourceBlockPermissionPersistence) {
		this.resourceBlockPermissionPersistence = resourceBlockPermissionPersistence;
	}

	/**
	 * Returns the resource permission local service.
	 *
	 * @return the resource permission local service
	 */
	public com.liferay.portal.service.ResourcePermissionLocalService getResourcePermissionLocalService() {
		return resourcePermissionLocalService;
	}

	/**
	 * Sets the resource permission local service.
	 *
	 * @param resourcePermissionLocalService the resource permission local service
	 */
	public void setResourcePermissionLocalService(
		com.liferay.portal.service.ResourcePermissionLocalService resourcePermissionLocalService) {
		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	 * Returns the resource permission remote service.
	 *
	 * @return the resource permission remote service
	 */
	public com.liferay.portal.service.ResourcePermissionService getResourcePermissionService() {
		return resourcePermissionService;
	}

	/**
	 * Sets the resource permission remote service.
	 *
	 * @param resourcePermissionService the resource permission remote service
	 */
	public void setResourcePermissionService(
		com.liferay.portal.service.ResourcePermissionService resourcePermissionService) {
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
	public com.liferay.portal.service.ResourceTypePermissionLocalService getResourceTypePermissionLocalService() {
		return resourceTypePermissionLocalService;
	}

	/**
	 * Sets the resource type permission local service.
	 *
	 * @param resourceTypePermissionLocalService the resource type permission local service
	 */
	public void setResourceTypePermissionLocalService(
		com.liferay.portal.service.ResourceTypePermissionLocalService resourceTypePermissionLocalService) {
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
	public com.liferay.portal.service.RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	/**
	 * Sets the role local service.
	 *
	 * @param roleLocalService the role local service
	 */
	public void setRoleLocalService(
		com.liferay.portal.service.RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	/**
	 * Returns the role remote service.
	 *
	 * @return the role remote service
	 */
	public com.liferay.portal.service.RoleService getRoleService() {
		return roleService;
	}

	/**
	 * Sets the role remote service.
	 *
	 * @param roleService the role remote service
	 */
	public void setRoleService(
		com.liferay.portal.service.RoleService roleService) {
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
	 * Returns the service component local service.
	 *
	 * @return the service component local service
	 */
	public com.liferay.portal.service.ServiceComponentLocalService getServiceComponentLocalService() {
		return serviceComponentLocalService;
	}

	/**
	 * Sets the service component local service.
	 *
	 * @param serviceComponentLocalService the service component local service
	 */
	public void setServiceComponentLocalService(
		com.liferay.portal.service.ServiceComponentLocalService serviceComponentLocalService) {
		this.serviceComponentLocalService = serviceComponentLocalService;
	}

	/**
	 * Returns the service component persistence.
	 *
	 * @return the service component persistence
	 */
	public ServiceComponentPersistence getServiceComponentPersistence() {
		return serviceComponentPersistence;
	}

	/**
	 * Sets the service component persistence.
	 *
	 * @param serviceComponentPersistence the service component persistence
	 */
	public void setServiceComponentPersistence(
		ServiceComponentPersistence serviceComponentPersistence) {
		this.serviceComponentPersistence = serviceComponentPersistence;
	}

	/**
	 * Returns the shard local service.
	 *
	 * @return the shard local service
	 */
	public com.liferay.portal.service.ShardLocalService getShardLocalService() {
		return shardLocalService;
	}

	/**
	 * Sets the shard local service.
	 *
	 * @param shardLocalService the shard local service
	 */
	public void setShardLocalService(
		com.liferay.portal.service.ShardLocalService shardLocalService) {
		this.shardLocalService = shardLocalService;
	}

	/**
	 * Returns the shard persistence.
	 *
	 * @return the shard persistence
	 */
	public ShardPersistence getShardPersistence() {
		return shardPersistence;
	}

	/**
	 * Sets the shard persistence.
	 *
	 * @param shardPersistence the shard persistence
	 */
	public void setShardPersistence(ShardPersistence shardPersistence) {
		this.shardPersistence = shardPersistence;
	}

	/**
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService) {
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
	 * Returns the system event local service.
	 *
	 * @return the system event local service
	 */
	public com.liferay.portal.service.SystemEventLocalService getSystemEventLocalService() {
		return systemEventLocalService;
	}

	/**
	 * Sets the system event local service.
	 *
	 * @param systemEventLocalService the system event local service
	 */
	public void setSystemEventLocalService(
		com.liferay.portal.service.SystemEventLocalService systemEventLocalService) {
		this.systemEventLocalService = systemEventLocalService;
	}

	/**
	 * Returns the system event persistence.
	 *
	 * @return the system event persistence
	 */
	public SystemEventPersistence getSystemEventPersistence() {
		return systemEventPersistence;
	}

	/**
	 * Sets the system event persistence.
	 *
	 * @param systemEventPersistence the system event persistence
	 */
	public void setSystemEventPersistence(
		SystemEventPersistence systemEventPersistence) {
		this.systemEventPersistence = systemEventPersistence;
	}

	/**
	 * Returns the team local service.
	 *
	 * @return the team local service
	 */
	public com.liferay.portal.service.TeamLocalService getTeamLocalService() {
		return teamLocalService;
	}

	/**
	 * Sets the team local service.
	 *
	 * @param teamLocalService the team local service
	 */
	public void setTeamLocalService(
		com.liferay.portal.service.TeamLocalService teamLocalService) {
		this.teamLocalService = teamLocalService;
	}

	/**
	 * Returns the team remote service.
	 *
	 * @return the team remote service
	 */
	public com.liferay.portal.service.TeamService getTeamService() {
		return teamService;
	}

	/**
	 * Sets the team remote service.
	 *
	 * @param teamService the team remote service
	 */
	public void setTeamService(
		com.liferay.portal.service.TeamService teamService) {
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
	 * Returns the theme local service.
	 *
	 * @return the theme local service
	 */
	public com.liferay.portal.service.ThemeLocalService getThemeLocalService() {
		return themeLocalService;
	}

	/**
	 * Sets the theme local service.
	 *
	 * @param themeLocalService the theme local service
	 */
	public void setThemeLocalService(
		com.liferay.portal.service.ThemeLocalService themeLocalService) {
		this.themeLocalService = themeLocalService;
	}

	/**
	 * Returns the theme remote service.
	 *
	 * @return the theme remote service
	 */
	public com.liferay.portal.service.ThemeService getThemeService() {
		return themeService;
	}

	/**
	 * Sets the theme remote service.
	 *
	 * @param themeService the theme remote service
	 */
	public void setThemeService(
		com.liferay.portal.service.ThemeService themeService) {
		this.themeService = themeService;
	}

	/**
	 * Returns the ticket local service.
	 *
	 * @return the ticket local service
	 */
	public com.liferay.portal.service.TicketLocalService getTicketLocalService() {
		return ticketLocalService;
	}

	/**
	 * Sets the ticket local service.
	 *
	 * @param ticketLocalService the ticket local service
	 */
	public void setTicketLocalService(
		com.liferay.portal.service.TicketLocalService ticketLocalService) {
		this.ticketLocalService = ticketLocalService;
	}

	/**
	 * Returns the ticket persistence.
	 *
	 * @return the ticket persistence
	 */
	public TicketPersistence getTicketPersistence() {
		return ticketPersistence;
	}

	/**
	 * Sets the ticket persistence.
	 *
	 * @param ticketPersistence the ticket persistence
	 */
	public void setTicketPersistence(TicketPersistence ticketPersistence) {
		this.ticketPersistence = ticketPersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
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
	public com.liferay.portal.service.UserGroupLocalService getUserGroupLocalService() {
		return userGroupLocalService;
	}

	/**
	 * Sets the user group local service.
	 *
	 * @param userGroupLocalService the user group local service
	 */
	public void setUserGroupLocalService(
		com.liferay.portal.service.UserGroupLocalService userGroupLocalService) {
		this.userGroupLocalService = userGroupLocalService;
	}

	/**
	 * Returns the user group remote service.
	 *
	 * @return the user group remote service
	 */
	public com.liferay.portal.service.UserGroupService getUserGroupService() {
		return userGroupService;
	}

	/**
	 * Sets the user group remote service.
	 *
	 * @param userGroupService the user group remote service
	 */
	public void setUserGroupService(
		com.liferay.portal.service.UserGroupService userGroupService) {
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
	public com.liferay.portal.service.UserGroupGroupRoleLocalService getUserGroupGroupRoleLocalService() {
		return userGroupGroupRoleLocalService;
	}

	/**
	 * Sets the user group group role local service.
	 *
	 * @param userGroupGroupRoleLocalService the user group group role local service
	 */
	public void setUserGroupGroupRoleLocalService(
		com.liferay.portal.service.UserGroupGroupRoleLocalService userGroupGroupRoleLocalService) {
		this.userGroupGroupRoleLocalService = userGroupGroupRoleLocalService;
	}

	/**
	 * Returns the user group group role remote service.
	 *
	 * @return the user group group role remote service
	 */
	public com.liferay.portal.service.UserGroupGroupRoleService getUserGroupGroupRoleService() {
		return userGroupGroupRoleService;
	}

	/**
	 * Sets the user group group role remote service.
	 *
	 * @param userGroupGroupRoleService the user group group role remote service
	 */
	public void setUserGroupGroupRoleService(
		com.liferay.portal.service.UserGroupGroupRoleService userGroupGroupRoleService) {
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
	 * Returns the user group role local service.
	 *
	 * @return the user group role local service
	 */
	public com.liferay.portal.service.UserGroupRoleLocalService getUserGroupRoleLocalService() {
		return userGroupRoleLocalService;
	}

	/**
	 * Sets the user group role local service.
	 *
	 * @param userGroupRoleLocalService the user group role local service
	 */
	public void setUserGroupRoleLocalService(
		com.liferay.portal.service.UserGroupRoleLocalService userGroupRoleLocalService) {
		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	/**
	 * Returns the user group role remote service.
	 *
	 * @return the user group role remote service
	 */
	public com.liferay.portal.service.UserGroupRoleService getUserGroupRoleService() {
		return userGroupRoleService;
	}

	/**
	 * Sets the user group role remote service.
	 *
	 * @param userGroupRoleService the user group role remote service
	 */
	public void setUserGroupRoleService(
		com.liferay.portal.service.UserGroupRoleService userGroupRoleService) {
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
	 * Returns the user ID mapper local service.
	 *
	 * @return the user ID mapper local service
	 */
	public com.liferay.portal.service.UserIdMapperLocalService getUserIdMapperLocalService() {
		return userIdMapperLocalService;
	}

	/**
	 * Sets the user ID mapper local service.
	 *
	 * @param userIdMapperLocalService the user ID mapper local service
	 */
	public void setUserIdMapperLocalService(
		com.liferay.portal.service.UserIdMapperLocalService userIdMapperLocalService) {
		this.userIdMapperLocalService = userIdMapperLocalService;
	}

	/**
	 * Returns the user ID mapper persistence.
	 *
	 * @return the user ID mapper persistence
	 */
	public UserIdMapperPersistence getUserIdMapperPersistence() {
		return userIdMapperPersistence;
	}

	/**
	 * Sets the user ID mapper persistence.
	 *
	 * @param userIdMapperPersistence the user ID mapper persistence
	 */
	public void setUserIdMapperPersistence(
		UserIdMapperPersistence userIdMapperPersistence) {
		this.userIdMapperPersistence = userIdMapperPersistence;
	}

	/**
	 * Returns the user notification delivery local service.
	 *
	 * @return the user notification delivery local service
	 */
	public com.liferay.portal.service.UserNotificationDeliveryLocalService getUserNotificationDeliveryLocalService() {
		return userNotificationDeliveryLocalService;
	}

	/**
	 * Sets the user notification delivery local service.
	 *
	 * @param userNotificationDeliveryLocalService the user notification delivery local service
	 */
	public void setUserNotificationDeliveryLocalService(
		com.liferay.portal.service.UserNotificationDeliveryLocalService userNotificationDeliveryLocalService) {
		this.userNotificationDeliveryLocalService = userNotificationDeliveryLocalService;
	}

	/**
	 * Returns the user notification delivery persistence.
	 *
	 * @return the user notification delivery persistence
	 */
	public UserNotificationDeliveryPersistence getUserNotificationDeliveryPersistence() {
		return userNotificationDeliveryPersistence;
	}

	/**
	 * Sets the user notification delivery persistence.
	 *
	 * @param userNotificationDeliveryPersistence the user notification delivery persistence
	 */
	public void setUserNotificationDeliveryPersistence(
		UserNotificationDeliveryPersistence userNotificationDeliveryPersistence) {
		this.userNotificationDeliveryPersistence = userNotificationDeliveryPersistence;
	}

	/**
	 * Returns the user notification event local service.
	 *
	 * @return the user notification event local service
	 */
	public com.liferay.portal.service.UserNotificationEventLocalService getUserNotificationEventLocalService() {
		return userNotificationEventLocalService;
	}

	/**
	 * Sets the user notification event local service.
	 *
	 * @param userNotificationEventLocalService the user notification event local service
	 */
	public void setUserNotificationEventLocalService(
		com.liferay.portal.service.UserNotificationEventLocalService userNotificationEventLocalService) {
		this.userNotificationEventLocalService = userNotificationEventLocalService;
	}

	/**
	 * Returns the user notification event persistence.
	 *
	 * @return the user notification event persistence
	 */
	public UserNotificationEventPersistence getUserNotificationEventPersistence() {
		return userNotificationEventPersistence;
	}

	/**
	 * Sets the user notification event persistence.
	 *
	 * @param userNotificationEventPersistence the user notification event persistence
	 */
	public void setUserNotificationEventPersistence(
		UserNotificationEventPersistence userNotificationEventPersistence) {
		this.userNotificationEventPersistence = userNotificationEventPersistence;
	}

	/**
	 * Returns the user tracker local service.
	 *
	 * @return the user tracker local service
	 */
	public com.liferay.portal.service.UserTrackerLocalService getUserTrackerLocalService() {
		return userTrackerLocalService;
	}

	/**
	 * Sets the user tracker local service.
	 *
	 * @param userTrackerLocalService the user tracker local service
	 */
	public void setUserTrackerLocalService(
		com.liferay.portal.service.UserTrackerLocalService userTrackerLocalService) {
		this.userTrackerLocalService = userTrackerLocalService;
	}

	/**
	 * Returns the user tracker persistence.
	 *
	 * @return the user tracker persistence
	 */
	public UserTrackerPersistence getUserTrackerPersistence() {
		return userTrackerPersistence;
	}

	/**
	 * Sets the user tracker persistence.
	 *
	 * @param userTrackerPersistence the user tracker persistence
	 */
	public void setUserTrackerPersistence(
		UserTrackerPersistence userTrackerPersistence) {
		this.userTrackerPersistence = userTrackerPersistence;
	}

	/**
	 * Returns the user tracker path local service.
	 *
	 * @return the user tracker path local service
	 */
	public com.liferay.portal.service.UserTrackerPathLocalService getUserTrackerPathLocalService() {
		return userTrackerPathLocalService;
	}

	/**
	 * Sets the user tracker path local service.
	 *
	 * @param userTrackerPathLocalService the user tracker path local service
	 */
	public void setUserTrackerPathLocalService(
		com.liferay.portal.service.UserTrackerPathLocalService userTrackerPathLocalService) {
		this.userTrackerPathLocalService = userTrackerPathLocalService;
	}

	/**
	 * Returns the user tracker path persistence.
	 *
	 * @return the user tracker path persistence
	 */
	public UserTrackerPathPersistence getUserTrackerPathPersistence() {
		return userTrackerPathPersistence;
	}

	/**
	 * Sets the user tracker path persistence.
	 *
	 * @param userTrackerPathPersistence the user tracker path persistence
	 */
	public void setUserTrackerPathPersistence(
		UserTrackerPathPersistence userTrackerPathPersistence) {
		this.userTrackerPathPersistence = userTrackerPathPersistence;
	}

	/**
	 * Returns the virtual host local service.
	 *
	 * @return the virtual host local service
	 */
	public com.liferay.portal.service.VirtualHostLocalService getVirtualHostLocalService() {
		return virtualHostLocalService;
	}

	/**
	 * Sets the virtual host local service.
	 *
	 * @param virtualHostLocalService the virtual host local service
	 */
	public void setVirtualHostLocalService(
		com.liferay.portal.service.VirtualHostLocalService virtualHostLocalService) {
		this.virtualHostLocalService = virtualHostLocalService;
	}

	/**
	 * Returns the virtual host persistence.
	 *
	 * @return the virtual host persistence
	 */
	public VirtualHostPersistence getVirtualHostPersistence() {
		return virtualHostPersistence;
	}

	/**
	 * Sets the virtual host persistence.
	 *
	 * @param virtualHostPersistence the virtual host persistence
	 */
	public void setVirtualHostPersistence(
		VirtualHostPersistence virtualHostPersistence) {
		this.virtualHostPersistence = virtualHostPersistence;
	}

	/**
	 * Returns the web d a v props local service.
	 *
	 * @return the web d a v props local service
	 */
	public com.liferay.portal.service.WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	/**
	 * Sets the web d a v props local service.
	 *
	 * @param webDAVPropsLocalService the web d a v props local service
	 */
	public void setWebDAVPropsLocalService(
		com.liferay.portal.service.WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	/**
	 * Returns the web d a v props persistence.
	 *
	 * @return the web d a v props persistence
	 */
	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	/**
	 * Sets the web d a v props persistence.
	 *
	 * @param webDAVPropsPersistence the web d a v props persistence
	 */
	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	/**
	 * Returns the website local service.
	 *
	 * @return the website local service
	 */
	public com.liferay.portal.service.WebsiteLocalService getWebsiteLocalService() {
		return websiteLocalService;
	}

	/**
	 * Sets the website local service.
	 *
	 * @param websiteLocalService the website local service
	 */
	public void setWebsiteLocalService(
		com.liferay.portal.service.WebsiteLocalService websiteLocalService) {
		this.websiteLocalService = websiteLocalService;
	}

	/**
	 * Returns the website remote service.
	 *
	 * @return the website remote service
	 */
	public com.liferay.portal.service.WebsiteService getWebsiteService() {
		return websiteService;
	}

	/**
	 * Sets the website remote service.
	 *
	 * @param websiteService the website remote service
	 */
	public void setWebsiteService(
		com.liferay.portal.service.WebsiteService websiteService) {
		this.websiteService = websiteService;
	}

	/**
	 * Returns the website persistence.
	 *
	 * @return the website persistence
	 */
	public WebsitePersistence getWebsitePersistence() {
		return websitePersistence;
	}

	/**
	 * Sets the website persistence.
	 *
	 * @param websitePersistence the website persistence
	 */
	public void setWebsitePersistence(WebsitePersistence websitePersistence) {
		this.websitePersistence = websitePersistence;
	}

	/**
	 * Returns the workflow definition link local service.
	 *
	 * @return the workflow definition link local service
	 */
	public com.liferay.portal.service.WorkflowDefinitionLinkLocalService getWorkflowDefinitionLinkLocalService() {
		return workflowDefinitionLinkLocalService;
	}

	/**
	 * Sets the workflow definition link local service.
	 *
	 * @param workflowDefinitionLinkLocalService the workflow definition link local service
	 */
	public void setWorkflowDefinitionLinkLocalService(
		com.liferay.portal.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
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

	/**
	 * Returns the workflow instance link local service.
	 *
	 * @return the workflow instance link local service
	 */
	public com.liferay.portal.service.WorkflowInstanceLinkLocalService getWorkflowInstanceLinkLocalService() {
		return workflowInstanceLinkLocalService;
	}

	/**
	 * Sets the workflow instance link local service.
	 *
	 * @param workflowInstanceLinkLocalService the workflow instance link local service
	 */
	public void setWorkflowInstanceLinkLocalService(
		com.liferay.portal.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		this.workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	 * Returns the workflow instance link persistence.
	 *
	 * @return the workflow instance link persistence
	 */
	public WorkflowInstanceLinkPersistence getWorkflowInstanceLinkPersistence() {
		return workflowInstanceLinkPersistence;
	}

	/**
	 * Sets the workflow instance link persistence.
	 *
	 * @param workflowInstanceLinkPersistence the workflow instance link persistence
	 */
	public void setWorkflowInstanceLinkPersistence(
		WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence) {
		this.workflowInstanceLinkPersistence = workflowInstanceLinkPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the asset category local service.
	 *
	 * @return the asset category local service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	/**
	 * Sets the asset category local service.
	 *
	 * @param assetCategoryLocalService the asset category local service
	 */
	public void setAssetCategoryLocalService(
		com.liferay.portlet.asset.service.AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	/**
	 * Returns the asset category remote service.
	 *
	 * @return the asset category remote service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	/**
	 * Sets the asset category remote service.
	 *
	 * @param assetCategoryService the asset category remote service
	 */
	public void setAssetCategoryService(
		com.liferay.portlet.asset.service.AssetCategoryService assetCategoryService) {
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
	public com.liferay.portlet.asset.service.AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		com.liferay.portlet.asset.service.AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry remote service.
	 *
	 * @return the asset entry remote service
	 */
	public com.liferay.portlet.asset.service.AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	/**
	 * Sets the asset entry remote service.
	 *
	 * @param assetEntryService the asset entry remote service
	 */
	public void setAssetEntryService(
		com.liferay.portlet.asset.service.AssetEntryService assetEntryService) {
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
	public com.liferay.portlet.asset.service.AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	/**
	 * Sets the asset tag local service.
	 *
	 * @param assetTagLocalService the asset tag local service
	 */
	public void setAssetTagLocalService(
		com.liferay.portlet.asset.service.AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	/**
	 * Returns the asset tag remote service.
	 *
	 * @return the asset tag remote service
	 */
	public com.liferay.portlet.asset.service.AssetTagService getAssetTagService() {
		return assetTagService;
	}

	/**
	 * Sets the asset tag remote service.
	 *
	 * @param assetTagService the asset tag remote service
	 */
	public void setAssetTagService(
		com.liferay.portlet.asset.service.AssetTagService assetTagService) {
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
	public com.liferay.portlet.asset.service.AssetVocabularyLocalService getAssetVocabularyLocalService() {
		return assetVocabularyLocalService;
	}

	/**
	 * Sets the asset vocabulary local service.
	 *
	 * @param assetVocabularyLocalService the asset vocabulary local service
	 */
	public void setAssetVocabularyLocalService(
		com.liferay.portlet.asset.service.AssetVocabularyLocalService assetVocabularyLocalService) {
		this.assetVocabularyLocalService = assetVocabularyLocalService;
	}

	/**
	 * Returns the asset vocabulary remote service.
	 *
	 * @return the asset vocabulary remote service
	 */
	public com.liferay.portlet.asset.service.AssetVocabularyService getAssetVocabularyService() {
		return assetVocabularyService;
	}

	/**
	 * Sets the asset vocabulary remote service.
	 *
	 * @param assetVocabularyService the asset vocabulary remote service
	 */
	public void setAssetVocabularyService(
		com.liferay.portlet.asset.service.AssetVocabularyService assetVocabularyService) {
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
	 * Returns the blogs entry local service.
	 *
	 * @return the blogs entry local service
	 */
	public com.liferay.portlet.blogs.service.BlogsEntryLocalService getBlogsEntryLocalService() {
		return blogsEntryLocalService;
	}

	/**
	 * Sets the blogs entry local service.
	 *
	 * @param blogsEntryLocalService the blogs entry local service
	 */
	public void setBlogsEntryLocalService(
		com.liferay.portlet.blogs.service.BlogsEntryLocalService blogsEntryLocalService) {
		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	/**
	 * Returns the blogs entry remote service.
	 *
	 * @return the blogs entry remote service
	 */
	public com.liferay.portlet.blogs.service.BlogsEntryService getBlogsEntryService() {
		return blogsEntryService;
	}

	/**
	 * Sets the blogs entry remote service.
	 *
	 * @param blogsEntryService the blogs entry remote service
	 */
	public void setBlogsEntryService(
		com.liferay.portlet.blogs.service.BlogsEntryService blogsEntryService) {
		this.blogsEntryService = blogsEntryService;
	}

	/**
	 * Returns the blogs entry persistence.
	 *
	 * @return the blogs entry persistence
	 */
	public BlogsEntryPersistence getBlogsEntryPersistence() {
		return blogsEntryPersistence;
	}

	/**
	 * Sets the blogs entry persistence.
	 *
	 * @param blogsEntryPersistence the blogs entry persistence
	 */
	public void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {
		this.blogsEntryPersistence = blogsEntryPersistence;
	}

	/**
	 * Returns the blogs entry finder.
	 *
	 * @return the blogs entry finder
	 */
	public BlogsEntryFinder getBlogsEntryFinder() {
		return blogsEntryFinder;
	}

	/**
	 * Sets the blogs entry finder.
	 *
	 * @param blogsEntryFinder the blogs entry finder
	 */
	public void setBlogsEntryFinder(BlogsEntryFinder blogsEntryFinder) {
		this.blogsEntryFinder = blogsEntryFinder;
	}

	/**
	 * Returns the blogs stats user local service.
	 *
	 * @return the blogs stats user local service
	 */
	public com.liferay.portlet.blogs.service.BlogsStatsUserLocalService getBlogsStatsUserLocalService() {
		return blogsStatsUserLocalService;
	}

	/**
	 * Sets the blogs stats user local service.
	 *
	 * @param blogsStatsUserLocalService the blogs stats user local service
	 */
	public void setBlogsStatsUserLocalService(
		com.liferay.portlet.blogs.service.BlogsStatsUserLocalService blogsStatsUserLocalService) {
		this.blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	/**
	 * Returns the blogs stats user persistence.
	 *
	 * @return the blogs stats user persistence
	 */
	public BlogsStatsUserPersistence getBlogsStatsUserPersistence() {
		return blogsStatsUserPersistence;
	}

	/**
	 * Sets the blogs stats user persistence.
	 *
	 * @param blogsStatsUserPersistence the blogs stats user persistence
	 */
	public void setBlogsStatsUserPersistence(
		BlogsStatsUserPersistence blogsStatsUserPersistence) {
		this.blogsStatsUserPersistence = blogsStatsUserPersistence;
	}

	/**
	 * Returns the blogs stats user finder.
	 *
	 * @return the blogs stats user finder
	 */
	public BlogsStatsUserFinder getBlogsStatsUserFinder() {
		return blogsStatsUserFinder;
	}

	/**
	 * Sets the blogs stats user finder.
	 *
	 * @param blogsStatsUserFinder the blogs stats user finder
	 */
	public void setBlogsStatsUserFinder(
		BlogsStatsUserFinder blogsStatsUserFinder) {
		this.blogsStatsUserFinder = blogsStatsUserFinder;
	}

	/**
	 * Returns the bookmarks folder local service.
	 *
	 * @return the bookmarks folder local service
	 */
	public com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService getBookmarksFolderLocalService() {
		return bookmarksFolderLocalService;
	}

	/**
	 * Sets the bookmarks folder local service.
	 *
	 * @param bookmarksFolderLocalService the bookmarks folder local service
	 */
	public void setBookmarksFolderLocalService(
		com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService bookmarksFolderLocalService) {
		this.bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	/**
	 * Returns the bookmarks folder remote service.
	 *
	 * @return the bookmarks folder remote service
	 */
	public com.liferay.portlet.bookmarks.service.BookmarksFolderService getBookmarksFolderService() {
		return bookmarksFolderService;
	}

	/**
	 * Sets the bookmarks folder remote service.
	 *
	 * @param bookmarksFolderService the bookmarks folder remote service
	 */
	public void setBookmarksFolderService(
		com.liferay.portlet.bookmarks.service.BookmarksFolderService bookmarksFolderService) {
		this.bookmarksFolderService = bookmarksFolderService;
	}

	/**
	 * Returns the bookmarks folder persistence.
	 *
	 * @return the bookmarks folder persistence
	 */
	public BookmarksFolderPersistence getBookmarksFolderPersistence() {
		return bookmarksFolderPersistence;
	}

	/**
	 * Sets the bookmarks folder persistence.
	 *
	 * @param bookmarksFolderPersistence the bookmarks folder persistence
	 */
	public void setBookmarksFolderPersistence(
		BookmarksFolderPersistence bookmarksFolderPersistence) {
		this.bookmarksFolderPersistence = bookmarksFolderPersistence;
	}

	/**
	 * Returns the bookmarks folder finder.
	 *
	 * @return the bookmarks folder finder
	 */
	public BookmarksFolderFinder getBookmarksFolderFinder() {
		return bookmarksFolderFinder;
	}

	/**
	 * Sets the bookmarks folder finder.
	 *
	 * @param bookmarksFolderFinder the bookmarks folder finder
	 */
	public void setBookmarksFolderFinder(
		BookmarksFolderFinder bookmarksFolderFinder) {
		this.bookmarksFolderFinder = bookmarksFolderFinder;
	}

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(
		com.liferay.portlet.documentlibrary.service.DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	/**
	 * Returns the d l app remote service.
	 *
	 * @return the d l app remote service
	 */
	public com.liferay.portlet.documentlibrary.service.DLAppService getDLAppService() {
		return dlAppService;
	}

	/**
	 * Sets the d l app remote service.
	 *
	 * @param dlAppService the d l app remote service
	 */
	public void setDLAppService(
		com.liferay.portlet.documentlibrary.service.DLAppService dlAppService) {
		this.dlAppService = dlAppService;
	}

	/**
	 * Returns the document library file entry type local service.
	 *
	 * @return the document library file entry type local service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService getDLFileEntryTypeLocalService() {
		return dlFileEntryTypeLocalService;
	}

	/**
	 * Sets the document library file entry type local service.
	 *
	 * @param dlFileEntryTypeLocalService the document library file entry type local service
	 */
	public void setDLFileEntryTypeLocalService(
		com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		this.dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	/**
	 * Returns the document library file entry type remote service.
	 *
	 * @return the document library file entry type remote service
	 */
	public com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService getDLFileEntryTypeService() {
		return dlFileEntryTypeService;
	}

	/**
	 * Sets the document library file entry type remote service.
	 *
	 * @param dlFileEntryTypeService the document library file entry type remote service
	 */
	public void setDLFileEntryTypeService(
		com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService dlFileEntryTypeService) {
		this.dlFileEntryTypeService = dlFileEntryTypeService;
	}

	/**
	 * Returns the document library file entry type persistence.
	 *
	 * @return the document library file entry type persistence
	 */
	public DLFileEntryTypePersistence getDLFileEntryTypePersistence() {
		return dlFileEntryTypePersistence;
	}

	/**
	 * Sets the document library file entry type persistence.
	 *
	 * @param dlFileEntryTypePersistence the document library file entry type persistence
	 */
	public void setDLFileEntryTypePersistence(
		DLFileEntryTypePersistence dlFileEntryTypePersistence) {
		this.dlFileEntryTypePersistence = dlFileEntryTypePersistence;
	}

	/**
	 * Returns the document library file entry type finder.
	 *
	 * @return the document library file entry type finder
	 */
	public DLFileEntryTypeFinder getDLFileEntryTypeFinder() {
		return dlFileEntryTypeFinder;
	}

	/**
	 * Sets the document library file entry type finder.
	 *
	 * @param dlFileEntryTypeFinder the document library file entry type finder
	 */
	public void setDLFileEntryTypeFinder(
		DLFileEntryTypeFinder dlFileEntryTypeFinder) {
		this.dlFileEntryTypeFinder = dlFileEntryTypeFinder;
	}

	/**
	 * Returns the journal article local service.
	 *
	 * @return the journal article local service
	 */
	public com.liferay.portlet.journal.service.JournalArticleLocalService getJournalArticleLocalService() {
		return journalArticleLocalService;
	}

	/**
	 * Sets the journal article local service.
	 *
	 * @param journalArticleLocalService the journal article local service
	 */
	public void setJournalArticleLocalService(
		com.liferay.portlet.journal.service.JournalArticleLocalService journalArticleLocalService) {
		this.journalArticleLocalService = journalArticleLocalService;
	}

	/**
	 * Returns the journal article remote service.
	 *
	 * @return the journal article remote service
	 */
	public com.liferay.portlet.journal.service.JournalArticleService getJournalArticleService() {
		return journalArticleService;
	}

	/**
	 * Sets the journal article remote service.
	 *
	 * @param journalArticleService the journal article remote service
	 */
	public void setJournalArticleService(
		com.liferay.portlet.journal.service.JournalArticleService journalArticleService) {
		this.journalArticleService = journalArticleService;
	}

	/**
	 * Returns the journal article persistence.
	 *
	 * @return the journal article persistence
	 */
	public JournalArticlePersistence getJournalArticlePersistence() {
		return journalArticlePersistence;
	}

	/**
	 * Sets the journal article persistence.
	 *
	 * @param journalArticlePersistence the journal article persistence
	 */
	public void setJournalArticlePersistence(
		JournalArticlePersistence journalArticlePersistence) {
		this.journalArticlePersistence = journalArticlePersistence;
	}

	/**
	 * Returns the journal article finder.
	 *
	 * @return the journal article finder
	 */
	public JournalArticleFinder getJournalArticleFinder() {
		return journalArticleFinder;
	}

	/**
	 * Sets the journal article finder.
	 *
	 * @param journalArticleFinder the journal article finder
	 */
	public void setJournalArticleFinder(
		JournalArticleFinder journalArticleFinder) {
		this.journalArticleFinder = journalArticleFinder;
	}

	/**
	 * Returns the journal structure local service.
	 *
	 * @return the journal structure local service
	 */
	@SuppressWarnings("deprecation")
	public com.liferay.portlet.journal.service.JournalStructureLocalService getJournalStructureLocalService() {
		return journalStructureLocalService;
	}

	/**
	 * Sets the journal structure local service.
	 *
	 * @param journalStructureLocalService the journal structure local service
	 */
	@SuppressWarnings("deprecation")
	public void setJournalStructureLocalService(
		com.liferay.portlet.journal.service.JournalStructureLocalService journalStructureLocalService) {
		this.journalStructureLocalService = journalStructureLocalService;
	}

	/**
	 * Returns the journal structure remote service.
	 *
	 * @return the journal structure remote service
	 */
	@SuppressWarnings("deprecation")
	public com.liferay.portlet.journal.service.JournalStructureService getJournalStructureService() {
		return journalStructureService;
	}

	/**
	 * Sets the journal structure remote service.
	 *
	 * @param journalStructureService the journal structure remote service
	 */
	@SuppressWarnings("deprecation")
	public void setJournalStructureService(
		com.liferay.portlet.journal.service.JournalStructureService journalStructureService) {
		this.journalStructureService = journalStructureService;
	}

	/**
	 * Returns the journal template local service.
	 *
	 * @return the journal template local service
	 */
	@SuppressWarnings("deprecation")
	public com.liferay.portlet.journal.service.JournalTemplateLocalService getJournalTemplateLocalService() {
		return journalTemplateLocalService;
	}

	/**
	 * Sets the journal template local service.
	 *
	 * @param journalTemplateLocalService the journal template local service
	 */
	@SuppressWarnings("deprecation")
	public void setJournalTemplateLocalService(
		com.liferay.portlet.journal.service.JournalTemplateLocalService journalTemplateLocalService) {
		this.journalTemplateLocalService = journalTemplateLocalService;
	}

	/**
	 * Returns the journal template remote service.
	 *
	 * @return the journal template remote service
	 */
	@SuppressWarnings("deprecation")
	public com.liferay.portlet.journal.service.JournalTemplateService getJournalTemplateService() {
		return journalTemplateService;
	}

	/**
	 * Sets the journal template remote service.
	 *
	 * @param journalTemplateService the journal template remote service
	 */
	@SuppressWarnings("deprecation")
	public void setJournalTemplateService(
		com.liferay.portlet.journal.service.JournalTemplateService journalTemplateService) {
		this.journalTemplateService = journalTemplateService;
	}

	/**
	 * Returns the message boards ban local service.
	 *
	 * @return the message boards ban local service
	 */
	public com.liferay.portlet.messageboards.service.MBBanLocalService getMBBanLocalService() {
		return mbBanLocalService;
	}

	/**
	 * Sets the message boards ban local service.
	 *
	 * @param mbBanLocalService the message boards ban local service
	 */
	public void setMBBanLocalService(
		com.liferay.portlet.messageboards.service.MBBanLocalService mbBanLocalService) {
		this.mbBanLocalService = mbBanLocalService;
	}

	/**
	 * Returns the message boards ban remote service.
	 *
	 * @return the message boards ban remote service
	 */
	public com.liferay.portlet.messageboards.service.MBBanService getMBBanService() {
		return mbBanService;
	}

	/**
	 * Sets the message boards ban remote service.
	 *
	 * @param mbBanService the message boards ban remote service
	 */
	public void setMBBanService(
		com.liferay.portlet.messageboards.service.MBBanService mbBanService) {
		this.mbBanService = mbBanService;
	}

	/**
	 * Returns the message boards ban persistence.
	 *
	 * @return the message boards ban persistence
	 */
	public MBBanPersistence getMBBanPersistence() {
		return mbBanPersistence;
	}

	/**
	 * Sets the message boards ban persistence.
	 *
	 * @param mbBanPersistence the message boards ban persistence
	 */
	public void setMBBanPersistence(MBBanPersistence mbBanPersistence) {
		this.mbBanPersistence = mbBanPersistence;
	}

	/**
	 * Returns the message boards category local service.
	 *
	 * @return the message boards category local service
	 */
	public com.liferay.portlet.messageboards.service.MBCategoryLocalService getMBCategoryLocalService() {
		return mbCategoryLocalService;
	}

	/**
	 * Sets the message boards category local service.
	 *
	 * @param mbCategoryLocalService the message boards category local service
	 */
	public void setMBCategoryLocalService(
		com.liferay.portlet.messageboards.service.MBCategoryLocalService mbCategoryLocalService) {
		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	/**
	 * Returns the message boards category remote service.
	 *
	 * @return the message boards category remote service
	 */
	public com.liferay.portlet.messageboards.service.MBCategoryService getMBCategoryService() {
		return mbCategoryService;
	}

	/**
	 * Sets the message boards category remote service.
	 *
	 * @param mbCategoryService the message boards category remote service
	 */
	public void setMBCategoryService(
		com.liferay.portlet.messageboards.service.MBCategoryService mbCategoryService) {
		this.mbCategoryService = mbCategoryService;
	}

	/**
	 * Returns the message boards category persistence.
	 *
	 * @return the message boards category persistence
	 */
	public MBCategoryPersistence getMBCategoryPersistence() {
		return mbCategoryPersistence;
	}

	/**
	 * Sets the message boards category persistence.
	 *
	 * @param mbCategoryPersistence the message boards category persistence
	 */
	public void setMBCategoryPersistence(
		MBCategoryPersistence mbCategoryPersistence) {
		this.mbCategoryPersistence = mbCategoryPersistence;
	}

	/**
	 * Returns the message boards category finder.
	 *
	 * @return the message boards category finder
	 */
	public MBCategoryFinder getMBCategoryFinder() {
		return mbCategoryFinder;
	}

	/**
	 * Sets the message boards category finder.
	 *
	 * @param mbCategoryFinder the message boards category finder
	 */
	public void setMBCategoryFinder(MBCategoryFinder mbCategoryFinder) {
		this.mbCategoryFinder = mbCategoryFinder;
	}

	/**
	 * Returns the message boards stats user local service.
	 *
	 * @return the message boards stats user local service
	 */
	public com.liferay.portlet.messageboards.service.MBStatsUserLocalService getMBStatsUserLocalService() {
		return mbStatsUserLocalService;
	}

	/**
	 * Sets the message boards stats user local service.
	 *
	 * @param mbStatsUserLocalService the message boards stats user local service
	 */
	public void setMBStatsUserLocalService(
		com.liferay.portlet.messageboards.service.MBStatsUserLocalService mbStatsUserLocalService) {
		this.mbStatsUserLocalService = mbStatsUserLocalService;
	}

	/**
	 * Returns the message boards stats user persistence.
	 *
	 * @return the message boards stats user persistence
	 */
	public MBStatsUserPersistence getMBStatsUserPersistence() {
		return mbStatsUserPersistence;
	}

	/**
	 * Sets the message boards stats user persistence.
	 *
	 * @param mbStatsUserPersistence the message boards stats user persistence
	 */
	public void setMBStatsUserPersistence(
		MBStatsUserPersistence mbStatsUserPersistence) {
		this.mbStatsUserPersistence = mbStatsUserPersistence;
	}

	/**
	 * Returns the message boards thread local service.
	 *
	 * @return the message boards thread local service
	 */
	public com.liferay.portlet.messageboards.service.MBThreadLocalService getMBThreadLocalService() {
		return mbThreadLocalService;
	}

	/**
	 * Sets the message boards thread local service.
	 *
	 * @param mbThreadLocalService the message boards thread local service
	 */
	public void setMBThreadLocalService(
		com.liferay.portlet.messageboards.service.MBThreadLocalService mbThreadLocalService) {
		this.mbThreadLocalService = mbThreadLocalService;
	}

	/**
	 * Returns the message boards thread remote service.
	 *
	 * @return the message boards thread remote service
	 */
	public com.liferay.portlet.messageboards.service.MBThreadService getMBThreadService() {
		return mbThreadService;
	}

	/**
	 * Sets the message boards thread remote service.
	 *
	 * @param mbThreadService the message boards thread remote service
	 */
	public void setMBThreadService(
		com.liferay.portlet.messageboards.service.MBThreadService mbThreadService) {
		this.mbThreadService = mbThreadService;
	}

	/**
	 * Returns the message boards thread persistence.
	 *
	 * @return the message boards thread persistence
	 */
	public MBThreadPersistence getMBThreadPersistence() {
		return mbThreadPersistence;
	}

	/**
	 * Sets the message boards thread persistence.
	 *
	 * @param mbThreadPersistence the message boards thread persistence
	 */
	public void setMBThreadPersistence(MBThreadPersistence mbThreadPersistence) {
		this.mbThreadPersistence = mbThreadPersistence;
	}

	/**
	 * Returns the message boards thread finder.
	 *
	 * @return the message boards thread finder
	 */
	public MBThreadFinder getMBThreadFinder() {
		return mbThreadFinder;
	}

	/**
	 * Sets the message boards thread finder.
	 *
	 * @param mbThreadFinder the message boards thread finder
	 */
	public void setMBThreadFinder(MBThreadFinder mbThreadFinder) {
		this.mbThreadFinder = mbThreadFinder;
	}

	/**
	 * Returns the polls question local service.
	 *
	 * @return the polls question local service
	 */
	public com.liferay.portlet.polls.service.PollsQuestionLocalService getPollsQuestionLocalService() {
		return pollsQuestionLocalService;
	}

	/**
	 * Sets the polls question local service.
	 *
	 * @param pollsQuestionLocalService the polls question local service
	 */
	public void setPollsQuestionLocalService(
		com.liferay.portlet.polls.service.PollsQuestionLocalService pollsQuestionLocalService) {
		this.pollsQuestionLocalService = pollsQuestionLocalService;
	}

	/**
	 * Returns the polls question remote service.
	 *
	 * @return the polls question remote service
	 */
	public com.liferay.portlet.polls.service.PollsQuestionService getPollsQuestionService() {
		return pollsQuestionService;
	}

	/**
	 * Sets the polls question remote service.
	 *
	 * @param pollsQuestionService the polls question remote service
	 */
	public void setPollsQuestionService(
		com.liferay.portlet.polls.service.PollsQuestionService pollsQuestionService) {
		this.pollsQuestionService = pollsQuestionService;
	}

	/**
	 * Returns the polls question persistence.
	 *
	 * @return the polls question persistence
	 */
	public PollsQuestionPersistence getPollsQuestionPersistence() {
		return pollsQuestionPersistence;
	}

	/**
	 * Sets the polls question persistence.
	 *
	 * @param pollsQuestionPersistence the polls question persistence
	 */
	public void setPollsQuestionPersistence(
		PollsQuestionPersistence pollsQuestionPersistence) {
		this.pollsQuestionPersistence = pollsQuestionPersistence;
	}

	/**
	 * Returns the shopping cart local service.
	 *
	 * @return the shopping cart local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingCartLocalService getShoppingCartLocalService() {
		return shoppingCartLocalService;
	}

	/**
	 * Sets the shopping cart local service.
	 *
	 * @param shoppingCartLocalService the shopping cart local service
	 */
	public void setShoppingCartLocalService(
		com.liferay.portlet.shopping.service.ShoppingCartLocalService shoppingCartLocalService) {
		this.shoppingCartLocalService = shoppingCartLocalService;
	}

	/**
	 * Returns the shopping cart persistence.
	 *
	 * @return the shopping cart persistence
	 */
	public ShoppingCartPersistence getShoppingCartPersistence() {
		return shoppingCartPersistence;
	}

	/**
	 * Sets the shopping cart persistence.
	 *
	 * @param shoppingCartPersistence the shopping cart persistence
	 */
	public void setShoppingCartPersistence(
		ShoppingCartPersistence shoppingCartPersistence) {
		this.shoppingCartPersistence = shoppingCartPersistence;
	}

	/**
	 * Returns the shopping category local service.
	 *
	 * @return the shopping category local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingCategoryLocalService getShoppingCategoryLocalService() {
		return shoppingCategoryLocalService;
	}

	/**
	 * Sets the shopping category local service.
	 *
	 * @param shoppingCategoryLocalService the shopping category local service
	 */
	public void setShoppingCategoryLocalService(
		com.liferay.portlet.shopping.service.ShoppingCategoryLocalService shoppingCategoryLocalService) {
		this.shoppingCategoryLocalService = shoppingCategoryLocalService;
	}

	/**
	 * Returns the shopping category remote service.
	 *
	 * @return the shopping category remote service
	 */
	public com.liferay.portlet.shopping.service.ShoppingCategoryService getShoppingCategoryService() {
		return shoppingCategoryService;
	}

	/**
	 * Sets the shopping category remote service.
	 *
	 * @param shoppingCategoryService the shopping category remote service
	 */
	public void setShoppingCategoryService(
		com.liferay.portlet.shopping.service.ShoppingCategoryService shoppingCategoryService) {
		this.shoppingCategoryService = shoppingCategoryService;
	}

	/**
	 * Returns the shopping category persistence.
	 *
	 * @return the shopping category persistence
	 */
	public ShoppingCategoryPersistence getShoppingCategoryPersistence() {
		return shoppingCategoryPersistence;
	}

	/**
	 * Sets the shopping category persistence.
	 *
	 * @param shoppingCategoryPersistence the shopping category persistence
	 */
	public void setShoppingCategoryPersistence(
		ShoppingCategoryPersistence shoppingCategoryPersistence) {
		this.shoppingCategoryPersistence = shoppingCategoryPersistence;
	}

	/**
	 * Returns the shopping coupon local service.
	 *
	 * @return the shopping coupon local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingCouponLocalService getShoppingCouponLocalService() {
		return shoppingCouponLocalService;
	}

	/**
	 * Sets the shopping coupon local service.
	 *
	 * @param shoppingCouponLocalService the shopping coupon local service
	 */
	public void setShoppingCouponLocalService(
		com.liferay.portlet.shopping.service.ShoppingCouponLocalService shoppingCouponLocalService) {
		this.shoppingCouponLocalService = shoppingCouponLocalService;
	}

	/**
	 * Returns the shopping coupon remote service.
	 *
	 * @return the shopping coupon remote service
	 */
	public com.liferay.portlet.shopping.service.ShoppingCouponService getShoppingCouponService() {
		return shoppingCouponService;
	}

	/**
	 * Sets the shopping coupon remote service.
	 *
	 * @param shoppingCouponService the shopping coupon remote service
	 */
	public void setShoppingCouponService(
		com.liferay.portlet.shopping.service.ShoppingCouponService shoppingCouponService) {
		this.shoppingCouponService = shoppingCouponService;
	}

	/**
	 * Returns the shopping coupon persistence.
	 *
	 * @return the shopping coupon persistence
	 */
	public ShoppingCouponPersistence getShoppingCouponPersistence() {
		return shoppingCouponPersistence;
	}

	/**
	 * Sets the shopping coupon persistence.
	 *
	 * @param shoppingCouponPersistence the shopping coupon persistence
	 */
	public void setShoppingCouponPersistence(
		ShoppingCouponPersistence shoppingCouponPersistence) {
		this.shoppingCouponPersistence = shoppingCouponPersistence;
	}

	/**
	 * Returns the shopping coupon finder.
	 *
	 * @return the shopping coupon finder
	 */
	public ShoppingCouponFinder getShoppingCouponFinder() {
		return shoppingCouponFinder;
	}

	/**
	 * Sets the shopping coupon finder.
	 *
	 * @param shoppingCouponFinder the shopping coupon finder
	 */
	public void setShoppingCouponFinder(
		ShoppingCouponFinder shoppingCouponFinder) {
		this.shoppingCouponFinder = shoppingCouponFinder;
	}

	/**
	 * Returns the shopping order local service.
	 *
	 * @return the shopping order local service
	 */
	public com.liferay.portlet.shopping.service.ShoppingOrderLocalService getShoppingOrderLocalService() {
		return shoppingOrderLocalService;
	}

	/**
	 * Sets the shopping order local service.
	 *
	 * @param shoppingOrderLocalService the shopping order local service
	 */
	public void setShoppingOrderLocalService(
		com.liferay.portlet.shopping.service.ShoppingOrderLocalService shoppingOrderLocalService) {
		this.shoppingOrderLocalService = shoppingOrderLocalService;
	}

	/**
	 * Returns the shopping order remote service.
	 *
	 * @return the shopping order remote service
	 */
	public com.liferay.portlet.shopping.service.ShoppingOrderService getShoppingOrderService() {
		return shoppingOrderService;
	}

	/**
	 * Sets the shopping order remote service.
	 *
	 * @param shoppingOrderService the shopping order remote service
	 */
	public void setShoppingOrderService(
		com.liferay.portlet.shopping.service.ShoppingOrderService shoppingOrderService) {
		this.shoppingOrderService = shoppingOrderService;
	}

	/**
	 * Returns the shopping order persistence.
	 *
	 * @return the shopping order persistence
	 */
	public ShoppingOrderPersistence getShoppingOrderPersistence() {
		return shoppingOrderPersistence;
	}

	/**
	 * Sets the shopping order persistence.
	 *
	 * @param shoppingOrderPersistence the shopping order persistence
	 */
	public void setShoppingOrderPersistence(
		ShoppingOrderPersistence shoppingOrderPersistence) {
		this.shoppingOrderPersistence = shoppingOrderPersistence;
	}

	/**
	 * Returns the shopping order finder.
	 *
	 * @return the shopping order finder
	 */
	public ShoppingOrderFinder getShoppingOrderFinder() {
		return shoppingOrderFinder;
	}

	/**
	 * Sets the shopping order finder.
	 *
	 * @param shoppingOrderFinder the shopping order finder
	 */
	public void setShoppingOrderFinder(ShoppingOrderFinder shoppingOrderFinder) {
		this.shoppingOrderFinder = shoppingOrderFinder;
	}

	/**
	 * Returns the social activity local service.
	 *
	 * @return the social activity local service
	 */
	public com.liferay.portlet.social.service.SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	/**
	 * Sets the social activity local service.
	 *
	 * @param socialActivityLocalService the social activity local service
	 */
	public void setSocialActivityLocalService(
		com.liferay.portlet.social.service.SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
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
	public com.liferay.portlet.social.service.SocialActivitySettingLocalService getSocialActivitySettingLocalService() {
		return socialActivitySettingLocalService;
	}

	/**
	 * Sets the social activity setting local service.
	 *
	 * @param socialActivitySettingLocalService the social activity setting local service
	 */
	public void setSocialActivitySettingLocalService(
		com.liferay.portlet.social.service.SocialActivitySettingLocalService socialActivitySettingLocalService) {
		this.socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	/**
	 * Returns the social activity setting remote service.
	 *
	 * @return the social activity setting remote service
	 */
	public com.liferay.portlet.social.service.SocialActivitySettingService getSocialActivitySettingService() {
		return socialActivitySettingService;
	}

	/**
	 * Sets the social activity setting remote service.
	 *
	 * @param socialActivitySettingService the social activity setting remote service
	 */
	public void setSocialActivitySettingService(
		com.liferay.portlet.social.service.SocialActivitySettingService socialActivitySettingService) {
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
	 * Returns the s c framework version local service.
	 *
	 * @return the s c framework version local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService getSCFrameworkVersionLocalService() {
		return scFrameworkVersionLocalService;
	}

	/**
	 * Sets the s c framework version local service.
	 *
	 * @param scFrameworkVersionLocalService the s c framework version local service
	 */
	public void setSCFrameworkVersionLocalService(
		com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService scFrameworkVersionLocalService) {
		this.scFrameworkVersionLocalService = scFrameworkVersionLocalService;
	}

	/**
	 * Returns the s c framework version remote service.
	 *
	 * @return the s c framework version remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService getSCFrameworkVersionService() {
		return scFrameworkVersionService;
	}

	/**
	 * Sets the s c framework version remote service.
	 *
	 * @param scFrameworkVersionService the s c framework version remote service
	 */
	public void setSCFrameworkVersionService(
		com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService scFrameworkVersionService) {
		this.scFrameworkVersionService = scFrameworkVersionService;
	}

	/**
	 * Returns the s c framework version persistence.
	 *
	 * @return the s c framework version persistence
	 */
	public SCFrameworkVersionPersistence getSCFrameworkVersionPersistence() {
		return scFrameworkVersionPersistence;
	}

	/**
	 * Sets the s c framework version persistence.
	 *
	 * @param scFrameworkVersionPersistence the s c framework version persistence
	 */
	public void setSCFrameworkVersionPersistence(
		SCFrameworkVersionPersistence scFrameworkVersionPersistence) {
		this.scFrameworkVersionPersistence = scFrameworkVersionPersistence;
	}

	/**
	 * Returns the s c product entry local service.
	 *
	 * @return the s c product entry local service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService getSCProductEntryLocalService() {
		return scProductEntryLocalService;
	}

	/**
	 * Sets the s c product entry local service.
	 *
	 * @param scProductEntryLocalService the s c product entry local service
	 */
	public void setSCProductEntryLocalService(
		com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService) {
		this.scProductEntryLocalService = scProductEntryLocalService;
	}

	/**
	 * Returns the s c product entry remote service.
	 *
	 * @return the s c product entry remote service
	 */
	public com.liferay.portlet.softwarecatalog.service.SCProductEntryService getSCProductEntryService() {
		return scProductEntryService;
	}

	/**
	 * Sets the s c product entry remote service.
	 *
	 * @param scProductEntryService the s c product entry remote service
	 */
	public void setSCProductEntryService(
		com.liferay.portlet.softwarecatalog.service.SCProductEntryService scProductEntryService) {
		this.scProductEntryService = scProductEntryService;
	}

	/**
	 * Returns the s c product entry persistence.
	 *
	 * @return the s c product entry persistence
	 */
	public SCProductEntryPersistence getSCProductEntryPersistence() {
		return scProductEntryPersistence;
	}

	/**
	 * Sets the s c product entry persistence.
	 *
	 * @param scProductEntryPersistence the s c product entry persistence
	 */
	public void setSCProductEntryPersistence(
		SCProductEntryPersistence scProductEntryPersistence) {
		this.scProductEntryPersistence = scProductEntryPersistence;
	}

	/**
	 * Returns the wiki node local service.
	 *
	 * @return the wiki node local service
	 */
	public com.liferay.portlet.wiki.service.WikiNodeLocalService getWikiNodeLocalService() {
		return wikiNodeLocalService;
	}

	/**
	 * Sets the wiki node local service.
	 *
	 * @param wikiNodeLocalService the wiki node local service
	 */
	public void setWikiNodeLocalService(
		com.liferay.portlet.wiki.service.WikiNodeLocalService wikiNodeLocalService) {
		this.wikiNodeLocalService = wikiNodeLocalService;
	}

	/**
	 * Returns the wiki node remote service.
	 *
	 * @return the wiki node remote service
	 */
	public com.liferay.portlet.wiki.service.WikiNodeService getWikiNodeService() {
		return wikiNodeService;
	}

	/**
	 * Sets the wiki node remote service.
	 *
	 * @param wikiNodeService the wiki node remote service
	 */
	public void setWikiNodeService(
		com.liferay.portlet.wiki.service.WikiNodeService wikiNodeService) {
		this.wikiNodeService = wikiNodeService;
	}

	/**
	 * Returns the wiki node persistence.
	 *
	 * @return the wiki node persistence
	 */
	public WikiNodePersistence getWikiNodePersistence() {
		return wikiNodePersistence;
	}

	/**
	 * Sets the wiki node persistence.
	 *
	 * @param wikiNodePersistence the wiki node persistence
	 */
	public void setWikiNodePersistence(WikiNodePersistence wikiNodePersistence) {
		this.wikiNodePersistence = wikiNodePersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return Group.class;
	}

	protected String getModelClassName() {
		return Group.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = groupPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portal.service.AccountLocalService.class)
	protected com.liferay.portal.service.AccountLocalService accountLocalService;
	@BeanReference(type = com.liferay.portal.service.AccountService.class)
	protected com.liferay.portal.service.AccountService accountService;
	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = com.liferay.portal.service.AddressLocalService.class)
	protected com.liferay.portal.service.AddressLocalService addressLocalService;
	@BeanReference(type = com.liferay.portal.service.AddressService.class)
	protected com.liferay.portal.service.AddressService addressService;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = com.liferay.portal.service.BackgroundTaskLocalService.class)
	protected com.liferay.portal.service.BackgroundTaskLocalService backgroundTaskLocalService;
	@BeanReference(type = BackgroundTaskPersistence.class)
	protected BackgroundTaskPersistence backgroundTaskPersistence;
	@BeanReference(type = com.liferay.portal.service.BrowserTrackerLocalService.class)
	protected com.liferay.portal.service.BrowserTrackerLocalService browserTrackerLocalService;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ClusterGroupLocalService.class)
	protected com.liferay.portal.service.ClusterGroupLocalService clusterGroupLocalService;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
	@BeanReference(type = com.liferay.portal.service.CMISRepositoryLocalService.class)
	protected com.liferay.portal.service.CMISRepositoryLocalService cmisRepositoryLocalService;
	@BeanReference(type = com.liferay.portal.service.CompanyLocalService.class)
	protected com.liferay.portal.service.CompanyLocalService companyLocalService;
	@BeanReference(type = com.liferay.portal.service.CompanyService.class)
	protected com.liferay.portal.service.CompanyService companyService;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = com.liferay.portal.service.ContactLocalService.class)
	protected com.liferay.portal.service.ContactLocalService contactLocalService;
	@BeanReference(type = com.liferay.portal.service.ContactService.class)
	protected com.liferay.portal.service.ContactService contactService;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = com.liferay.portal.service.CountryService.class)
	protected com.liferay.portal.service.CountryService countryService;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = com.liferay.portal.service.EmailAddressLocalService.class)
	protected com.liferay.portal.service.EmailAddressLocalService emailAddressLocalService;
	@BeanReference(type = com.liferay.portal.service.EmailAddressService.class)
	protected com.liferay.portal.service.EmailAddressService emailAddressService;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = com.liferay.portal.service.GroupLocalService.class)
	protected com.liferay.portal.service.GroupLocalService groupLocalService;
	@BeanReference(type = com.liferay.portal.service.GroupService.class)
	protected com.liferay.portal.service.GroupService groupService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.service.ImageLocalService.class)
	protected com.liferay.portal.service.ImageLocalService imageLocalService;
	@BeanReference(type = com.liferay.portal.service.ImageService.class)
	protected com.liferay.portal.service.ImageService imageService;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutLocalService.class)
	protected com.liferay.portal.service.LayoutLocalService layoutLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutService.class)
	protected com.liferay.portal.service.LayoutService layoutService;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutFinder.class)
	protected LayoutFinder layoutFinder;
	@BeanReference(type = com.liferay.portal.service.LayoutBranchLocalService.class)
	protected com.liferay.portal.service.LayoutBranchLocalService layoutBranchLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutBranchService.class)
	protected com.liferay.portal.service.LayoutBranchService layoutBranchService;
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutFriendlyURLLocalService.class)
	protected com.liferay.portal.service.LayoutFriendlyURLLocalService layoutFriendlyURLLocalService;
	@BeanReference(type = LayoutFriendlyURLPersistence.class)
	protected LayoutFriendlyURLPersistence layoutFriendlyURLPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutPrototypeLocalService.class)
	protected com.liferay.portal.service.LayoutPrototypeLocalService layoutPrototypeLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutPrototypeService.class)
	protected com.liferay.portal.service.LayoutPrototypeService layoutPrototypeService;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutRevisionLocalService.class)
	protected com.liferay.portal.service.LayoutRevisionLocalService layoutRevisionLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutRevisionService.class)
	protected com.liferay.portal.service.LayoutRevisionService layoutRevisionService;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutSetLocalService.class)
	protected com.liferay.portal.service.LayoutSetLocalService layoutSetLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetService.class)
	protected com.liferay.portal.service.LayoutSetService layoutSetService;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutSetBranchLocalService.class)
	protected com.liferay.portal.service.LayoutSetBranchLocalService layoutSetBranchLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetBranchService.class)
	protected com.liferay.portal.service.LayoutSetBranchService layoutSetBranchService;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutSetPrototypeLocalService.class)
	protected com.liferay.portal.service.LayoutSetPrototypeLocalService layoutSetPrototypeLocalService;
	@BeanReference(type = com.liferay.portal.service.LayoutSetPrototypeService.class)
	protected com.liferay.portal.service.LayoutSetPrototypeService layoutSetPrototypeService;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = com.liferay.portal.service.LayoutTemplateLocalService.class)
	protected com.liferay.portal.service.LayoutTemplateLocalService layoutTemplateLocalService;
	@BeanReference(type = com.liferay.portal.service.ListTypeService.class)
	protected com.liferay.portal.service.ListTypeService listTypeService;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = com.liferay.portal.service.LockLocalService.class)
	protected com.liferay.portal.service.LockLocalService lockLocalService;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = LockFinder.class)
	protected LockFinder lockFinder;
	@BeanReference(type = com.liferay.portal.service.MembershipRequestLocalService.class)
	protected com.liferay.portal.service.MembershipRequestLocalService membershipRequestLocalService;
	@BeanReference(type = com.liferay.portal.service.MembershipRequestService.class)
	protected com.liferay.portal.service.MembershipRequestService membershipRequestService;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = com.liferay.portal.service.OrganizationLocalService.class)
	protected com.liferay.portal.service.OrganizationLocalService organizationLocalService;
	@BeanReference(type = com.liferay.portal.service.OrganizationService.class)
	protected com.liferay.portal.service.OrganizationService organizationService;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrganizationFinder.class)
	protected OrganizationFinder organizationFinder;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = com.liferay.portal.service.OrgLaborLocalService.class)
	protected com.liferay.portal.service.OrgLaborLocalService orgLaborLocalService;
	@BeanReference(type = com.liferay.portal.service.OrgLaborService.class)
	protected com.liferay.portal.service.OrgLaborService orgLaborService;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = com.liferay.portal.service.PasswordPolicyLocalService.class)
	protected com.liferay.portal.service.PasswordPolicyLocalService passwordPolicyLocalService;
	@BeanReference(type = com.liferay.portal.service.PasswordPolicyService.class)
	protected com.liferay.portal.service.PasswordPolicyService passwordPolicyService;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyFinder.class)
	protected PasswordPolicyFinder passwordPolicyFinder;
	@BeanReference(type = com.liferay.portal.service.PasswordPolicyRelLocalService.class)
	protected com.liferay.portal.service.PasswordPolicyRelLocalService passwordPolicyRelLocalService;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = com.liferay.portal.service.PasswordTrackerLocalService.class)
	protected com.liferay.portal.service.PasswordTrackerLocalService passwordTrackerLocalService;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = com.liferay.portal.service.PermissionService.class)
	protected com.liferay.portal.service.PermissionService permissionService;
	@BeanReference(type = com.liferay.portal.service.PhoneLocalService.class)
	protected com.liferay.portal.service.PhoneLocalService phoneLocalService;
	@BeanReference(type = com.liferay.portal.service.PhoneService.class)
	protected com.liferay.portal.service.PhoneService phoneService;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = com.liferay.portal.service.PluginSettingLocalService.class)
	protected com.liferay.portal.service.PluginSettingLocalService pluginSettingLocalService;
	@BeanReference(type = com.liferay.portal.service.PluginSettingService.class)
	protected com.liferay.portal.service.PluginSettingService pluginSettingService;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = com.liferay.portal.service.PortalLocalService.class)
	protected com.liferay.portal.service.PortalLocalService portalLocalService;
	@BeanReference(type = com.liferay.portal.service.PortalService.class)
	protected com.liferay.portal.service.PortalService portalService;
	@BeanReference(type = com.liferay.portal.service.PortalPreferencesLocalService.class)
	protected com.liferay.portal.service.PortalPreferencesLocalService portalPreferencesLocalService;
	@BeanReference(type = PortalPreferencesPersistence.class)
	protected PortalPreferencesPersistence portalPreferencesPersistence;
	@BeanReference(type = com.liferay.portal.service.PortletLocalService.class)
	protected com.liferay.portal.service.PortletLocalService portletLocalService;
	@BeanReference(type = com.liferay.portal.service.PortletService.class)
	protected com.liferay.portal.service.PortletService portletService;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = com.liferay.portal.service.PortletItemLocalService.class)
	protected com.liferay.portal.service.PortletItemLocalService portletItemLocalService;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = com.liferay.portal.service.PortletPreferencesLocalService.class)
	protected com.liferay.portal.service.PortletPreferencesLocalService portletPreferencesLocalService;
	@BeanReference(type = com.liferay.portal.service.PortletPreferencesService.class)
	protected com.liferay.portal.service.PortletPreferencesService portletPreferencesService;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = PortletPreferencesFinder.class)
	protected PortletPreferencesFinder portletPreferencesFinder;
	@BeanReference(type = com.liferay.portal.service.QuartzLocalService.class)
	protected com.liferay.portal.service.QuartzLocalService quartzLocalService;
	@BeanReference(type = com.liferay.portal.service.RegionService.class)
	protected com.liferay.portal.service.RegionService regionService;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = com.liferay.portal.service.ReleaseLocalService.class)
	protected com.liferay.portal.service.ReleaseLocalService releaseLocalService;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = com.liferay.portal.service.RepositoryLocalService.class)
	protected com.liferay.portal.service.RepositoryLocalService repositoryLocalService;
	@BeanReference(type = com.liferay.portal.service.RepositoryService.class)
	protected com.liferay.portal.service.RepositoryService repositoryService;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = com.liferay.portal.service.RepositoryEntryLocalService.class)
	protected com.liferay.portal.service.RepositoryEntryLocalService repositoryEntryLocalService;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceActionLocalService.class)
	protected com.liferay.portal.service.ResourceActionLocalService resourceActionLocalService;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceBlockLocalService.class)
	protected com.liferay.portal.service.ResourceBlockLocalService resourceBlockLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceBlockService.class)
	protected com.liferay.portal.service.ResourceBlockService resourceBlockService;
	@BeanReference(type = ResourceBlockPersistence.class)
	protected ResourceBlockPersistence resourceBlockPersistence;
	@BeanReference(type = ResourceBlockFinder.class)
	protected ResourceBlockFinder resourceBlockFinder;
	@BeanReference(type = com.liferay.portal.service.ResourceBlockPermissionLocalService.class)
	protected com.liferay.portal.service.ResourceBlockPermissionLocalService resourceBlockPermissionLocalService;
	@BeanReference(type = ResourceBlockPermissionPersistence.class)
	protected ResourceBlockPermissionPersistence resourceBlockPermissionPersistence;
	@BeanReference(type = com.liferay.portal.service.ResourcePermissionLocalService.class)
	protected com.liferay.portal.service.ResourcePermissionLocalService resourcePermissionLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourcePermissionService.class)
	protected com.liferay.portal.service.ResourcePermissionService resourcePermissionService;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = ResourcePermissionFinder.class)
	protected ResourcePermissionFinder resourcePermissionFinder;
	@BeanReference(type = com.liferay.portal.service.ResourceTypePermissionLocalService.class)
	protected com.liferay.portal.service.ResourceTypePermissionLocalService resourceTypePermissionLocalService;
	@BeanReference(type = ResourceTypePermissionPersistence.class)
	protected ResourceTypePermissionPersistence resourceTypePermissionPersistence;
	@BeanReference(type = ResourceTypePermissionFinder.class)
	protected ResourceTypePermissionFinder resourceTypePermissionFinder;
	@BeanReference(type = com.liferay.portal.service.RoleLocalService.class)
	protected com.liferay.portal.service.RoleLocalService roleLocalService;
	@BeanReference(type = com.liferay.portal.service.RoleService.class)
	protected com.liferay.portal.service.RoleService roleService;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = RoleFinder.class)
	protected RoleFinder roleFinder;
	@BeanReference(type = com.liferay.portal.service.ServiceComponentLocalService.class)
	protected com.liferay.portal.service.ServiceComponentLocalService serviceComponentLocalService;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = com.liferay.portal.service.ShardLocalService.class)
	protected com.liferay.portal.service.ShardLocalService shardLocalService;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = com.liferay.portal.service.SubscriptionLocalService.class)
	protected com.liferay.portal.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portal.service.SystemEventLocalService.class)
	protected com.liferay.portal.service.SystemEventLocalService systemEventLocalService;
	@BeanReference(type = SystemEventPersistence.class)
	protected SystemEventPersistence systemEventPersistence;
	@BeanReference(type = com.liferay.portal.service.TeamLocalService.class)
	protected com.liferay.portal.service.TeamLocalService teamLocalService;
	@BeanReference(type = com.liferay.portal.service.TeamService.class)
	protected com.liferay.portal.service.TeamService teamService;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = TeamFinder.class)
	protected TeamFinder teamFinder;
	@BeanReference(type = com.liferay.portal.service.ThemeLocalService.class)
	protected com.liferay.portal.service.ThemeLocalService themeLocalService;
	@BeanReference(type = com.liferay.portal.service.ThemeService.class)
	protected com.liferay.portal.service.ThemeService themeService;
	@BeanReference(type = com.liferay.portal.service.TicketLocalService.class)
	protected com.liferay.portal.service.TicketLocalService ticketLocalService;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portal.service.UserGroupLocalService.class)
	protected com.liferay.portal.service.UserGroupLocalService userGroupLocalService;
	@BeanReference(type = com.liferay.portal.service.UserGroupService.class)
	protected com.liferay.portal.service.UserGroupService userGroupService;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupFinder.class)
	protected UserGroupFinder userGroupFinder;
	@BeanReference(type = com.liferay.portal.service.UserGroupGroupRoleLocalService.class)
	protected com.liferay.portal.service.UserGroupGroupRoleLocalService userGroupGroupRoleLocalService;
	@BeanReference(type = com.liferay.portal.service.UserGroupGroupRoleService.class)
	protected com.liferay.portal.service.UserGroupGroupRoleService userGroupGroupRoleService;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = com.liferay.portal.service.UserGroupRoleLocalService.class)
	protected com.liferay.portal.service.UserGroupRoleLocalService userGroupRoleLocalService;
	@BeanReference(type = com.liferay.portal.service.UserGroupRoleService.class)
	protected com.liferay.portal.service.UserGroupRoleService userGroupRoleService;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserGroupRoleFinder.class)
	protected UserGroupRoleFinder userGroupRoleFinder;
	@BeanReference(type = com.liferay.portal.service.UserIdMapperLocalService.class)
	protected com.liferay.portal.service.UserIdMapperLocalService userIdMapperLocalService;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = com.liferay.portal.service.UserNotificationDeliveryLocalService.class)
	protected com.liferay.portal.service.UserNotificationDeliveryLocalService userNotificationDeliveryLocalService;
	@BeanReference(type = UserNotificationDeliveryPersistence.class)
	protected UserNotificationDeliveryPersistence userNotificationDeliveryPersistence;
	@BeanReference(type = com.liferay.portal.service.UserNotificationEventLocalService.class)
	protected com.liferay.portal.service.UserNotificationEventLocalService userNotificationEventLocalService;
	@BeanReference(type = UserNotificationEventPersistence.class)
	protected UserNotificationEventPersistence userNotificationEventPersistence;
	@BeanReference(type = com.liferay.portal.service.UserTrackerLocalService.class)
	protected com.liferay.portal.service.UserTrackerLocalService userTrackerLocalService;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = com.liferay.portal.service.UserTrackerPathLocalService.class)
	protected com.liferay.portal.service.UserTrackerPathLocalService userTrackerPathLocalService;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = com.liferay.portal.service.VirtualHostLocalService.class)
	protected com.liferay.portal.service.VirtualHostLocalService virtualHostLocalService;
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = com.liferay.portal.service.WebDAVPropsLocalService.class)
	protected com.liferay.portal.service.WebDAVPropsLocalService webDAVPropsLocalService;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = com.liferay.portal.service.WebsiteLocalService.class)
	protected com.liferay.portal.service.WebsiteLocalService websiteLocalService;
	@BeanReference(type = com.liferay.portal.service.WebsiteService.class)
	protected com.liferay.portal.service.WebsiteService websiteService;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = com.liferay.portal.service.WorkflowDefinitionLinkLocalService.class)
	protected com.liferay.portal.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = com.liferay.portal.service.WorkflowInstanceLinkLocalService.class)
	protected com.liferay.portal.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryLocalService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryService assetCategoryService;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryFinder.class)
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetEntryLocalService.class)
	protected com.liferay.portlet.asset.service.AssetEntryLocalService assetEntryLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetEntryService.class)
	protected com.liferay.portlet.asset.service.AssetEntryService assetEntryService;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetEntryFinder.class)
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagLocalService.class)
	protected com.liferay.portlet.asset.service.AssetTagLocalService assetTagLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagService.class)
	protected com.liferay.portlet.asset.service.AssetTagService assetTagService;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagFinder.class)
	protected AssetTagFinder assetTagFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetVocabularyLocalService.class)
	protected com.liferay.portlet.asset.service.AssetVocabularyLocalService assetVocabularyLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetVocabularyService.class)
	protected com.liferay.portlet.asset.service.AssetVocabularyService assetVocabularyService;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = AssetVocabularyFinder.class)
	protected AssetVocabularyFinder assetVocabularyFinder;
	@BeanReference(type = com.liferay.portlet.blogs.service.BlogsEntryLocalService.class)
	protected com.liferay.portlet.blogs.service.BlogsEntryLocalService blogsEntryLocalService;
	@BeanReference(type = com.liferay.portlet.blogs.service.BlogsEntryService.class)
	protected com.liferay.portlet.blogs.service.BlogsEntryService blogsEntryService;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BlogsEntryFinder.class)
	protected BlogsEntryFinder blogsEntryFinder;
	@BeanReference(type = com.liferay.portlet.blogs.service.BlogsStatsUserLocalService.class)
	protected com.liferay.portlet.blogs.service.BlogsStatsUserLocalService blogsStatsUserLocalService;
	@BeanReference(type = BlogsStatsUserPersistence.class)
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(type = BlogsStatsUserFinder.class)
	protected BlogsStatsUserFinder blogsStatsUserFinder;
	@BeanReference(type = com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService.class)
	protected com.liferay.portlet.bookmarks.service.BookmarksFolderLocalService bookmarksFolderLocalService;
	@BeanReference(type = com.liferay.portlet.bookmarks.service.BookmarksFolderService.class)
	protected com.liferay.portlet.bookmarks.service.BookmarksFolderService bookmarksFolderService;
	@BeanReference(type = BookmarksFolderPersistence.class)
	protected BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(type = BookmarksFolderFinder.class)
	protected BookmarksFolderFinder bookmarksFolderFinder;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLAppLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLAppLocalService dlAppLocalService;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLAppService.class)
	protected com.liferay.portlet.documentlibrary.service.DLAppService dlAppService;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService dlFileEntryTypeLocalService;
	@BeanReference(type = com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService.class)
	protected com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService dlFileEntryTypeService;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileEntryTypeFinder.class)
	protected DLFileEntryTypeFinder dlFileEntryTypeFinder;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalArticleLocalService.class)
	protected com.liferay.portlet.journal.service.JournalArticleLocalService journalArticleLocalService;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalArticleService.class)
	protected com.liferay.portlet.journal.service.JournalArticleService journalArticleService;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleFinder.class)
	protected JournalArticleFinder journalArticleFinder;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalStructureLocalService.class)
	@SuppressWarnings("deprecation")
	protected com.liferay.portlet.journal.service.JournalStructureLocalService journalStructureLocalService;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalStructureService.class)
	@SuppressWarnings("deprecation")
	protected com.liferay.portlet.journal.service.JournalStructureService journalStructureService;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalTemplateLocalService.class)
	@SuppressWarnings("deprecation")
	protected com.liferay.portlet.journal.service.JournalTemplateLocalService journalTemplateLocalService;
	@BeanReference(type = com.liferay.portlet.journal.service.JournalTemplateService.class)
	@SuppressWarnings("deprecation")
	protected com.liferay.portlet.journal.service.JournalTemplateService journalTemplateService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBBanLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBBanLocalService mbBanLocalService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBBanService.class)
	protected com.liferay.portlet.messageboards.service.MBBanService mbBanService;
	@BeanReference(type = MBBanPersistence.class)
	protected MBBanPersistence mbBanPersistence;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBCategoryLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBCategoryLocalService mbCategoryLocalService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBCategoryService.class)
	protected com.liferay.portlet.messageboards.service.MBCategoryService mbCategoryService;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBCategoryFinder.class)
	protected MBCategoryFinder mbCategoryFinder;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBStatsUserLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBStatsUserLocalService mbStatsUserLocalService;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBThreadLocalService.class)
	protected com.liferay.portlet.messageboards.service.MBThreadLocalService mbThreadLocalService;
	@BeanReference(type = com.liferay.portlet.messageboards.service.MBThreadService.class)
	protected com.liferay.portlet.messageboards.service.MBThreadService mbThreadService;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFinder.class)
	protected MBThreadFinder mbThreadFinder;
	@BeanReference(type = com.liferay.portlet.polls.service.PollsQuestionLocalService.class)
	protected com.liferay.portlet.polls.service.PollsQuestionLocalService pollsQuestionLocalService;
	@BeanReference(type = com.liferay.portlet.polls.service.PollsQuestionService.class)
	protected com.liferay.portlet.polls.service.PollsQuestionService pollsQuestionService;
	@BeanReference(type = PollsQuestionPersistence.class)
	protected PollsQuestionPersistence pollsQuestionPersistence;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingCartLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingCartLocalService shoppingCartLocalService;
	@BeanReference(type = ShoppingCartPersistence.class)
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingCategoryLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingCategoryLocalService shoppingCategoryLocalService;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingCategoryService.class)
	protected com.liferay.portlet.shopping.service.ShoppingCategoryService shoppingCategoryService;
	@BeanReference(type = ShoppingCategoryPersistence.class)
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingCouponLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingCouponLocalService shoppingCouponLocalService;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingCouponService.class)
	protected com.liferay.portlet.shopping.service.ShoppingCouponService shoppingCouponService;
	@BeanReference(type = ShoppingCouponPersistence.class)
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(type = ShoppingCouponFinder.class)
	protected ShoppingCouponFinder shoppingCouponFinder;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingOrderLocalService.class)
	protected com.liferay.portlet.shopping.service.ShoppingOrderLocalService shoppingOrderLocalService;
	@BeanReference(type = com.liferay.portlet.shopping.service.ShoppingOrderService.class)
	protected com.liferay.portlet.shopping.service.ShoppingOrderService shoppingOrderService;
	@BeanReference(type = ShoppingOrderPersistence.class)
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(type = ShoppingOrderFinder.class)
	protected ShoppingOrderFinder shoppingOrderFinder;
	@BeanReference(type = com.liferay.portlet.social.service.SocialActivityLocalService.class)
	protected com.liferay.portlet.social.service.SocialActivityLocalService socialActivityLocalService;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityFinder.class)
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(type = com.liferay.portlet.social.service.SocialActivitySettingLocalService.class)
	protected com.liferay.portlet.social.service.SocialActivitySettingLocalService socialActivitySettingLocalService;
	@BeanReference(type = com.liferay.portlet.social.service.SocialActivitySettingService.class)
	protected com.liferay.portlet.social.service.SocialActivitySettingService socialActivitySettingService;
	@BeanReference(type = SocialActivitySettingPersistence.class)
	protected SocialActivitySettingPersistence socialActivitySettingPersistence;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalService scFrameworkVersionLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionService scFrameworkVersionService;
	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalService scProductEntryLocalService;
	@BeanReference(type = com.liferay.portlet.softwarecatalog.service.SCProductEntryService.class)
	protected com.liferay.portlet.softwarecatalog.service.SCProductEntryService scProductEntryService;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = com.liferay.portlet.wiki.service.WikiNodeLocalService.class)
	protected com.liferay.portlet.wiki.service.WikiNodeLocalService wikiNodeLocalService;
	@BeanReference(type = com.liferay.portlet.wiki.service.WikiNodeService.class)
	protected com.liferay.portlet.wiki.service.WikiNodeService wikiNodeService;
	@BeanReference(type = WikiNodePersistence.class)
	protected WikiNodePersistence wikiNodePersistence;
	private String _beanIdentifier;
}