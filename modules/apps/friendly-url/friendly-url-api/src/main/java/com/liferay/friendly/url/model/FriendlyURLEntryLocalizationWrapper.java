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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link FriendlyURLEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationWrapper extends BaseModelWrapper<FriendlyURLEntryLocalization>
	implements FriendlyURLEntryLocalization,
		ModelWrapper<FriendlyURLEntryLocalization> {
	public FriendlyURLEntryLocalizationWrapper(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		super(friendlyURLEntryLocalization);
	}

	/**
	* Returns the fully qualified class name of this friendly url entry localization.
	*
	* @return the fully qualified class name of this friendly url entry localization
	*/
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	* Returns the class name ID of this friendly url entry localization.
	*
	* @return the class name ID of this friendly url entry localization
	*/
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	* Returns the class pk of this friendly url entry localization.
	*
	* @return the class pk of this friendly url entry localization
	*/
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	* Returns the company ID of this friendly url entry localization.
	*
	* @return the company ID of this friendly url entry localization
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the friendly url entry ID of this friendly url entry localization.
	*
	* @return the friendly url entry ID of this friendly url entry localization
	*/
	@Override
	public long getFriendlyURLEntryId() {
		return model.getFriendlyURLEntryId();
	}

	/**
	* Returns the friendly url entry localization ID of this friendly url entry localization.
	*
	* @return the friendly url entry localization ID of this friendly url entry localization
	*/
	@Override
	public long getFriendlyURLEntryLocalizationId() {
		return model.getFriendlyURLEntryLocalizationId();
	}

	/**
	* Returns the group ID of this friendly url entry localization.
	*
	* @return the group ID of this friendly url entry localization
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the language ID of this friendly url entry localization.
	*
	* @return the language ID of this friendly url entry localization
	*/
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	* Returns the mvcc version of this friendly url entry localization.
	*
	* @return the mvcc version of this friendly url entry localization
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this friendly url entry localization.
	*
	* @return the primary key of this friendly url entry localization
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the url title of this friendly url entry localization.
	*
	* @return the url title of this friendly url entry localization
	*/
	@Override
	public String getUrlTitle() {
		return model.getUrlTitle();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	* Sets the class name ID of this friendly url entry localization.
	*
	* @param classNameId the class name ID of this friendly url entry localization
	*/
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this friendly url entry localization.
	*
	* @param classPK the class pk of this friendly url entry localization
	*/
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this friendly url entry localization.
	*
	* @param companyId the company ID of this friendly url entry localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the friendly url entry ID of this friendly url entry localization.
	*
	* @param friendlyURLEntryId the friendly url entry ID of this friendly url entry localization
	*/
	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		model.setFriendlyURLEntryId(friendlyURLEntryId);
	}

	/**
	* Sets the friendly url entry localization ID of this friendly url entry localization.
	*
	* @param friendlyURLEntryLocalizationId the friendly url entry localization ID of this friendly url entry localization
	*/
	@Override
	public void setFriendlyURLEntryLocalizationId(
		long friendlyURLEntryLocalizationId) {
		model.setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalizationId);
	}

	/**
	* Sets the group ID of this friendly url entry localization.
	*
	* @param groupId the group ID of this friendly url entry localization
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this friendly url entry localization.
	*
	* @param languageId the language ID of this friendly url entry localization
	*/
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	* Sets the mvcc version of this friendly url entry localization.
	*
	* @param mvccVersion the mvcc version of this friendly url entry localization
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this friendly url entry localization.
	*
	* @param primaryKey the primary key of this friendly url entry localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the url title of this friendly url entry localization.
	*
	* @param urlTitle the url title of this friendly url entry localization
	*/
	@Override
	public void setUrlTitle(String urlTitle) {
		model.setUrlTitle(urlTitle);
	}

	@Override
	protected FriendlyURLEntryLocalizationWrapper wrap(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		return new FriendlyURLEntryLocalizationWrapper(friendlyURLEntryLocalization);
	}
}