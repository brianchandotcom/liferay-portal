/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.saml.persistence.exception.NoSuchSpAuthRequestException;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the saml sp auth request service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestUtil
 * @generated
 */
@ProviderType
public interface SamlSpAuthRequestPersistence
	extends BasePersistence<SamlSpAuthRequest> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlSpAuthRequestUtil} to access the saml sp auth request persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findByCreateDate(Date createDate);

	/**
	 * Returns a range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of matching saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end);

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp auth requests where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findByCreateDate(
		Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest findByCreateDate_First(
			Date createDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
				orderByComparator)
		throws NoSuchSpAuthRequestException;

	/**
	 * Returns the first saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest fetchByCreateDate_First(
		Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator);

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest findByCreateDate_Last(
			Date createDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
				orderByComparator)
		throws NoSuchSpAuthRequestException;

	/**
	 * Returns the last saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest fetchByCreateDate_Last(
		Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator);

	/**
	 * Returns the saml sp auth requests before and after the current saml sp auth request in the ordered set where createDate &lt; &#63;.
	 *
	 * @param samlSpAuthnRequestId the primary key of the current saml sp auth request
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public SamlSpAuthRequest[] findByCreateDate_PrevAndNext(
			long samlSpAuthnRequestId, Date createDate,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
				orderByComparator)
		throws NoSuchSpAuthRequestException;

	/**
	 * Removes all the saml sp auth requests where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	public void removeByCreateDate(Date createDate);

	/**
	 * Returns the number of saml sp auth requests where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching saml sp auth requests
	 */
	public int countByCreateDate(Date createDate);

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest findBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws NoSuchSpAuthRequestException;

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey);

	/**
	 * Returns the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp auth request, or <code>null</code> if a matching saml sp auth request could not be found
	 */
	public SamlSpAuthRequest fetchBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey,
		boolean useFinderCache);

	/**
	 * Removes the saml sp auth request where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63; from the database.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the saml sp auth request that was removed
	 */
	public SamlSpAuthRequest removeBySIEI_SSARK(
			String samlIdpEntityId, String samlSpAuthRequestKey)
		throws NoSuchSpAuthRequestException;

	/**
	 * Returns the number of saml sp auth requests where samlIdpEntityId = &#63; and samlSpAuthRequestKey = &#63;.
	 *
	 * @param samlIdpEntityId the saml idp entity ID
	 * @param samlSpAuthRequestKey the saml sp auth request key
	 * @return the number of matching saml sp auth requests
	 */
	public int countBySIEI_SSARK(
		String samlIdpEntityId, String samlSpAuthRequestKey);

	/**
	 * Caches the saml sp auth request in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 */
	public void cacheResult(SamlSpAuthRequest samlSpAuthRequest);

	/**
	 * Caches the saml sp auth requests in the entity cache if it is enabled.
	 *
	 * @param samlSpAuthRequests the saml sp auth requests
	 */
	public void cacheResult(
		java.util.List<SamlSpAuthRequest> samlSpAuthRequests);

	/**
	 * Creates a new saml sp auth request with the primary key. Does not add the saml sp auth request to the database.
	 *
	 * @param samlSpAuthnRequestId the primary key for the new saml sp auth request
	 * @return the new saml sp auth request
	 */
	public SamlSpAuthRequest create(long samlSpAuthnRequestId);

	/**
	 * Removes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request that was removed
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public SamlSpAuthRequest remove(long samlSpAuthnRequestId)
		throws NoSuchSpAuthRequestException;

	public SamlSpAuthRequest updateImpl(SamlSpAuthRequest samlSpAuthRequest);

	/**
	 * Returns the saml sp auth request with the primary key or throws a <code>NoSuchSpAuthRequestException</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request
	 * @throws NoSuchSpAuthRequestException if a saml sp auth request with the primary key could not be found
	 */
	public SamlSpAuthRequest findByPrimaryKey(long samlSpAuthnRequestId)
		throws NoSuchSpAuthRequestException;

	/**
	 * Returns the saml sp auth request with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request, or <code>null</code> if a saml sp auth request with the primary key could not be found
	 */
	public SamlSpAuthRequest fetchByPrimaryKey(long samlSpAuthnRequestId);

	/**
	 * Returns all the saml sp auth requests.
	 *
	 * @return the saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findAll();

	/**
	 * Returns a range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlSpAuthRequestModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp auth requests
	 */
	public java.util.List<SamlSpAuthRequest> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpAuthRequest>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the saml sp auth requests from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of saml sp auth requests.
	 *
	 * @return the number of saml sp auth requests
	 */
	public int countAll();

}