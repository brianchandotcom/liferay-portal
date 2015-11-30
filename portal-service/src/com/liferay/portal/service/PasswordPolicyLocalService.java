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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.SystemEventConstants;

/**
 * Provides the local service interface for PasswordPolicy. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyLocalServiceUtil
 * @see com.liferay.portal.service.base.PasswordPolicyLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PasswordPolicyLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface PasswordPolicyLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PasswordPolicyLocalServiceUtil} to access the password policy local service. Add custom service methods to {@link com.liferay.portal.service.impl.PasswordPolicyLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the password policy to the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #addPasswordPolicy(long,
	boolean, String, String, boolean, boolean, long, boolean,
	boolean, int, int, int, int, int, int, String, boolean, int,
	boolean, long, long, int, boolean, int, long, long, long,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.PasswordPolicy addPasswordPolicy(
		long userId, boolean defaultPolicy, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws PortalException;

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
		throws PortalException;

	public void checkDefaultPasswordPolicy(long companyId)
		throws PortalException;

	/**
	* Creates a new password policy with the primary key. Does not add the password policy to the database.
	*
	* @param passwordPolicyId the primary key for the new password policy
	* @return the new password policy
	*/
	public com.liferay.portal.model.PasswordPolicy createPasswordPolicy(
		long passwordPolicyId);

	public void deleteNondefaultPasswordPolicies(long companyId)
		throws PortalException;

	/**
	* Deletes the password policy from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was removed
	* @throws PortalException
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	@com.liferay.portal.kernel.systemevent.SystemEvent(action = SystemEventConstants.ACTION_SKIP, type = SystemEventConstants.TYPE_DELETE)
	public com.liferay.portal.model.PasswordPolicy deletePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy)
		throws PortalException;

	/**
	* Deletes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy that was removed
	* @throws PortalException if a password policy with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portal.model.PasswordPolicy deletePasswordPolicy(
		long passwordPolicyId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicy(
		long companyId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicy(
		long passwordPolicyId);

	/**
	* Returns the password policy with the matching UUID and company.
	*
	* @param uuid the password policy's UUID
	* @param companyId the primary key of the company
	* @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy fetchPasswordPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getDefaultPasswordPolicy(
		long companyId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.PasswordPolicy> getPasswordPolicies(
		int start, int end);

	/**
	* Returns the number of password policies.
	*
	* @return the number of password policies
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPasswordPoliciesCount();

	/**
	* @deprecated As of 6.1.0
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long organizationId, long locationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long companyId, long[] organizationIds) throws PortalException;

	/**
	* Returns the password policy with the primary key.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @return the password policy
	* @throws PortalException if a password policy with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy(
		long passwordPolicyId) throws PortalException;

	@com.liferay.portal.kernel.cache.thread.local.ThreadLocalCachable
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUserId(
		long userId) throws PortalException;

	/**
	* Returns the password policy with the matching UUID and company.
	*
	* @param uuid the password policy's UUID
	* @param companyId the primary key of the company
	* @return the matching password policy
	* @throws PortalException if a matching password policy could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicyByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.PasswordPolicy> obc);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String name);

	/**
	* Updates the password policy in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param passwordPolicy the password policy
	* @return the password policy that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		com.liferay.portal.model.PasswordPolicy passwordPolicy);

	/**
	* @deprecated As of 6.2.0, replaced by {@link #updatePasswordPolicy(long,
	String, String, boolean, boolean, long, boolean, boolean,
	int, int, int, int, int, int, String, boolean, int, boolean,
	long, long, int, boolean, int, long, long, long,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portal.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, java.lang.String name,
		java.lang.String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		boolean history, int historyCount, boolean expireable, long maxAge,
		long warningTime, int graceLimit, boolean lockout, int maxFailure,
		long lockoutDuration, long resetFailureCount, long resetTicketMaxAge)
		throws PortalException;

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
		throws PortalException;
}