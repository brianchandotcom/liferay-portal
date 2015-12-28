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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link PasswordPolicyRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRelLocalService
 * @generated
 */
@ProviderType
public class PasswordPolicyRelLocalServiceWrapper
	implements PasswordPolicyRelLocalService,
		ServiceWrapper<PasswordPolicyRelLocalService> {
	public PasswordPolicyRelLocalServiceWrapper(
		PasswordPolicyRelLocalService passwordPolicyRelLocalService) {
		_passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	@Override
	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK) {
		return _passwordPolicyRelLocalService.addPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	/**
	* Adds the password policy rel to the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyRel the password policy rel
	* @return the password policy rel that was added
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel addPasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		return _passwordPolicyRelLocalService.addPasswordPolicyRel(passwordPolicyRel);
	}

	@Override
	public void addPasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs) {
		_passwordPolicyRelLocalService.addPasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	/**
	* Creates a new password policy rel with the primary key. Does not add the password policy rel to the database.
	*
	* @param passwordPolicyRelId the primary key for the new password policy rel
	* @return the new password policy rel
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel createPasswordPolicyRel(
		long passwordPolicyRelId) {
		return _passwordPolicyRelLocalService.createPasswordPolicyRel(passwordPolicyRelId);
	}

	@Override
	public void deletePasswordPolicyRel(java.lang.String className, long classPK) {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(className,
			classPK);
	}

	@Override
	public void deletePasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK) {
		_passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	/**
	* Deletes the password policy rel from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyRel the password policy rel
	* @return the password policy rel that was removed
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel deletePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		return _passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyRel);
	}

	/**
	* Deletes the password policy rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyRelId the primary key of the password policy rel
	* @return the password policy rel that was removed
	* @throws PortalException if a password policy rel with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel deletePasswordPolicyRel(
		long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.deletePasswordPolicyRel(passwordPolicyRelId);
	}

	@Override
	public void deletePasswordPolicyRels(long passwordPolicyId) {
		_passwordPolicyRelLocalService.deletePasswordPolicyRels(passwordPolicyId);
	}

	@Override
	public void deletePasswordPolicyRels(long passwordPolicyId,
		java.lang.String className, long[] classPKs) {
		_passwordPolicyRelLocalService.deletePasswordPolicyRels(passwordPolicyId,
			className, classPKs);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _passwordPolicyRelLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _passwordPolicyRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _passwordPolicyRelLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _passwordPolicyRelLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _passwordPolicyRelLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _passwordPolicyRelLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicyRel fetchPasswordPolicyRel(
		java.lang.String className, long classPK) {
		return _passwordPolicyRelLocalService.fetchPasswordPolicyRel(className,
			classPK);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicyRel fetchPasswordPolicyRel(
		long passwordPolicyRelId) {
		return _passwordPolicyRelLocalService.fetchPasswordPolicyRel(passwordPolicyRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _passwordPolicyRelLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _passwordPolicyRelLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _passwordPolicyRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(className,
			classPK);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	/**
	* Returns the password policy rel with the primary key.
	*
	* @param passwordPolicyRelId the primary key of the password policy rel
	* @return the password policy rel
	* @throws PortalException if a password policy rel with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel getPasswordPolicyRel(
		long passwordPolicyRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.getPasswordPolicyRel(passwordPolicyRelId);
	}

	/**
	* Returns a range of all the password policy rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policy rels
	* @param end the upper bound of the range of password policy rels (not inclusive)
	* @return the range of password policy rels
	*/
	@Override
	public java.util.List<com.liferay.portal.model.PasswordPolicyRel> getPasswordPolicyRels(
		int start, int end) {
		return _passwordPolicyRelLocalService.getPasswordPolicyRels(start, end);
	}

	/**
	* Returns the number of password policy rels.
	*
	* @return the number of password policy rels
	*/
	@Override
	public int getPasswordPolicyRelsCount() {
		return _passwordPolicyRelLocalService.getPasswordPolicyRelsCount();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyRelLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasPasswordPolicyRel(long passwordPolicyId,
		java.lang.String className, long classPK) {
		return _passwordPolicyRelLocalService.hasPasswordPolicyRel(passwordPolicyId,
			className, classPK);
	}

	/**
	* Updates the password policy rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyRel the password policy rel
	* @return the password policy rel that was updated
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicyRel updatePasswordPolicyRel(
		com.liferay.portal.model.PasswordPolicyRel passwordPolicyRel) {
		return _passwordPolicyRelLocalService.updatePasswordPolicyRel(passwordPolicyRel);
	}

	@Override
	public PasswordPolicyRelLocalService getWrappedService() {
		return _passwordPolicyRelLocalService;
	}

	@Override
	public void setWrappedService(
		PasswordPolicyRelLocalService passwordPolicyRelLocalService) {
		_passwordPolicyRelLocalService = passwordPolicyRelLocalService;
	}

	private PasswordPolicyRelLocalService _passwordPolicyRelLocalService;
}