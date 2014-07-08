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
 * Provides a wrapper for {@link PasswordPolicyLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyLocalService
 * @generated
 */
@ProviderType
public class PasswordPolicyLocalServiceWrapper
	implements PasswordPolicyLocalService,
		ServiceWrapper<PasswordPolicyLocalService> {
	public PasswordPolicyLocalServiceWrapper(
		PasswordPolicyLocalService passwordPolicyLocalService) {
		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	/**
	* Adds the password policy to the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was added
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		return _passwordPolicyLocalService.addPasswordPolicy(passwordPolicy);
	}

	/**
	* Creates a new password policy with the primary key. Does not add the password policy to the database.
	*
	* @param passwordPolicyId the primary key for the new password policy
	* @return the new password policy
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy createPasswordPolicy(
		long passwordPolicyId) {
		return _passwordPolicyLocalService.createPasswordPolicy(passwordPolicyId);
	}

	/**
	* Deletes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy that was removed
	* @throws PortalException if a password policy with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy deletePasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.deletePasswordPolicy(passwordPolicyId);
	}

	/**
	* Deletes the password policy from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy deletePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _passwordPolicyLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _passwordPolicyLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _passwordPolicyLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _passwordPolicyLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicy(
		long passwordPolicyId) {
		return _passwordPolicyLocalService.fetchPasswordPolicy(passwordPolicyId);
	}

	/**
	* Returns the password policy with the matching UUID and company.
	*
	* @param uuid the password policy's UUID
	* @param companyId the primary key of the company
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _passwordPolicyLocalService.fetchPasswordPolicyByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns the password policy with the primary key.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy
	* @throws PortalException if a password policy with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPasswordPolicy(passwordPolicyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _passwordPolicyLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return _passwordPolicyLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the password policy with the matching UUID and company.
	*
	* @param uuid the password policy's UUID
	* @param companyId the primary key of the company
	* @return the matching password policy
	* @throws PortalException if a matching password policy could not be found
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPasswordPolicyByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of all the password policies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of password policies
	* @param end the upper bound of the range of password policies (not inclusive)
	* @return the range of password policies
	*/
	@Override
	public java.util.List<com.liferay.portal.model.PasswordPolicy> getPasswordPolicies(
		int start, int end) {
		return _passwordPolicyLocalService.getPasswordPolicies(start, end);
	}

	/**
	* Returns the number of password policies.
	*
	* @return the number of password policies
	*/
	@Override
	public int getPasswordPoliciesCount() {
		return _passwordPolicyLocalService.getPasswordPoliciesCount();
	}

	/**
	* Updates the password policy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was updated
	*/
	@Override
	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicy);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _passwordPolicyLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_passwordPolicyLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addPasswordPolicy(long,
	boolean, String, String, boolean, boolean, long, boolean,
	boolean, int, int, int, int, int, int, String, boolean, int,
	boolean, long, long, int, boolean, int, long, long, long,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.addPasswordPolicy(userId,
			defaultPolicy, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			history, historyCount, expireable, maxAge, warningTime, graceLimit,
			lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		java.lang.String regex, boolean history, int historyCount,
		boolean expireable, long maxAge, long warningTime, int graceLimit,
		boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.addPasswordPolicy(userId,
			defaultPolicy, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			regex, history, historyCount, expireable, maxAge, warningTime,
			graceLimit, lockout, maxFailure, lockoutDuration,
			resetFailureCount, resetTicketMaxAge, serviceContext);
	}

	@Override
	public void checkDefaultPasswordPolicy(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_passwordPolicyLocalService.checkDefaultPasswordPolicy(companyId);
	}

	@Override
	public void deleteNondefaultPasswordPolicies(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_passwordPolicyLocalService.deleteNondefaultPasswordPolicies(companyId);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicy(
		long companyId, java.lang.String name) {
		return _passwordPolicyLocalService.fetchPasswordPolicy(companyId, name);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);
	}

	/**
	* @deprecated As of 6.1.0
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long organizationId, long locationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationId, locationId);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPasswordPolicy(companyId,
			organizationIds);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId) throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.getPasswordPolicyByUserId(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.PasswordPolicy> obc) {
		return _passwordPolicyLocalService.search(companyId, name, start, end,
			obc);
	}

	@Override
	public int searchCount(long companyId, java.lang.String name) {
		return _passwordPolicyLocalService.searchCount(companyId, name);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updatePasswordPolicy(long,
	String, String, boolean, boolean, long, boolean, boolean,
	int, int, int, int, int, int, String, boolean, int, boolean,
	long, long, int, boolean, int, long, long, long,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphanumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge);
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		java.lang.String regex, boolean history, int historyCount,
		boolean expireable, long maxAge, long warningTime, int graceLimit,
		boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyLocalService.updatePasswordPolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphanumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, regex, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public PasswordPolicyLocalService getWrappedPasswordPolicyLocalService() {
		return _passwordPolicyLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedPasswordPolicyLocalService(
		PasswordPolicyLocalService passwordPolicyLocalService) {
		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	@Override
	public PasswordPolicyLocalService getWrappedService() {
		return _passwordPolicyLocalService;
	}

	@Override
	public void setWrappedService(
		PasswordPolicyLocalService passwordPolicyLocalService) {
		_passwordPolicyLocalService = passwordPolicyLocalService;
	}

	private PasswordPolicyLocalService _passwordPolicyLocalService;
}