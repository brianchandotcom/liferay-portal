/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.saml.persistence.exception.NoSuchSpSessionException;
import com.liferay.saml.persistence.model.SamlSpSession;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the saml sp session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpSessionUtil
 * @generated
 */
@ProviderType
public interface SamlSpSessionPersistence
	extends BasePersistence<SamlSpSession> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlSpSessionUtil} to access the saml sp session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @return the matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId);

	/**
	 * Returns a range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end);

	/**
	 * Returns an ordered range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findBySamlPeerBindingId(
		long samlPeerBindingId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findBySamlPeerBindingId_First(
			long samlPeerBindingId,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Returns the first saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchBySamlPeerBindingId_First(
		long samlPeerBindingId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns the last saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findBySamlPeerBindingId_Last(
			long samlPeerBindingId,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Returns the last saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchBySamlPeerBindingId_Last(
		long samlPeerBindingId,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns the saml sp sessions before and after the current saml sp session in the ordered set where samlPeerBindingId = &#63;.
	 *
	 * @param samlSpSessionId the primary key of the current saml sp session
	 * @param samlPeerBindingId the saml peer binding ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	public SamlSpSession[] findBySamlPeerBindingId_PrevAndNext(
			long samlSpSessionId, long samlPeerBindingId,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Removes all the saml sp sessions where samlPeerBindingId = &#63; from the database.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 */
	public void removeBySamlPeerBindingId(long samlPeerBindingId);

	/**
	 * Returns the number of saml sp sessions where samlPeerBindingId = &#63;.
	 *
	 * @param samlPeerBindingId the saml peer binding ID
	 * @return the number of matching saml sp sessions
	 */
	public int countBySamlPeerBindingId(long samlPeerBindingId);

	/**
	 * Returns the saml sp session where jSessionId = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param jSessionId the j session ID
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findByJSessionId(String jSessionId)
		throws NoSuchSpSessionException;

	/**
	 * Returns the saml sp session where jSessionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param jSessionId the j session ID
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchByJSessionId(String jSessionId);

	/**
	 * Returns the saml sp session where jSessionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param jSessionId the j session ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchByJSessionId(
		String jSessionId, boolean useFinderCache);

	/**
	 * Removes the saml sp session where jSessionId = &#63; from the database.
	 *
	 * @param jSessionId the j session ID
	 * @return the saml sp session that was removed
	 */
	public SamlSpSession removeByJSessionId(String jSessionId)
		throws NoSuchSpSessionException;

	/**
	 * Returns the number of saml sp sessions where jSessionId = &#63;.
	 *
	 * @param jSessionId the j session ID
	 * @return the number of matching saml sp sessions
	 */
	public int countByJSessionId(String jSessionId);

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findBySamlSpSessionKey(String samlSpSessionKey)
		throws NoSuchSpSessionException;

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchBySamlSpSessionKey(String samlSpSessionKey);

	/**
	 * Returns the saml sp session where samlSpSessionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchBySamlSpSessionKey(
		String samlSpSessionKey, boolean useFinderCache);

	/**
	 * Removes the saml sp session where samlSpSessionKey = &#63; from the database.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the saml sp session that was removed
	 */
	public SamlSpSession removeBySamlSpSessionKey(String samlSpSessionKey)
		throws NoSuchSpSessionException;

	/**
	 * Returns the number of saml sp sessions where samlSpSessionKey = &#63;.
	 *
	 * @param samlSpSessionKey the saml sp session key
	 * @return the number of matching saml sp sessions
	 */
	public int countBySamlSpSessionKey(String samlSpSessionKey);

	/**
	 * Returns all the saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findByC_SI(
		long companyId, String sessionIndex);

	/**
	 * Returns a range of all the saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findByC_SI(
		long companyId, String sessionIndex, int start, int end);

	/**
	 * Returns an ordered range of all the saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findByC_SI(
		long companyId, String sessionIndex, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml sp sessions
	 */
	public java.util.List<SamlSpSession> findByC_SI(
		long companyId, String sessionIndex, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first saml sp session in the ordered set where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findByC_SI_First(
			long companyId, String sessionIndex,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Returns the first saml sp session in the ordered set where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchByC_SI_First(
		long companyId, String sessionIndex,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns the last saml sp session in the ordered set where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session
	 * @throws NoSuchSpSessionException if a matching saml sp session could not be found
	 */
	public SamlSpSession findByC_SI_Last(
			long companyId, String sessionIndex,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Returns the last saml sp session in the ordered set where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml sp session, or <code>null</code> if a matching saml sp session could not be found
	 */
	public SamlSpSession fetchByC_SI_Last(
		long companyId, String sessionIndex,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns the saml sp sessions before and after the current saml sp session in the ordered set where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param samlSpSessionId the primary key of the current saml sp session
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	public SamlSpSession[] findByC_SI_PrevAndNext(
			long samlSpSessionId, long companyId, String sessionIndex,
			com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
				orderByComparator)
		throws NoSuchSpSessionException;

	/**
	 * Removes all the saml sp sessions where companyId = &#63; and sessionIndex = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 */
	public void removeByC_SI(long companyId, String sessionIndex);

	/**
	 * Returns the number of saml sp sessions where companyId = &#63; and sessionIndex = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sessionIndex the session index
	 * @return the number of matching saml sp sessions
	 */
	public int countByC_SI(long companyId, String sessionIndex);

	/**
	 * Caches the saml sp session in the entity cache if it is enabled.
	 *
	 * @param samlSpSession the saml sp session
	 */
	public void cacheResult(SamlSpSession samlSpSession);

	/**
	 * Caches the saml sp sessions in the entity cache if it is enabled.
	 *
	 * @param samlSpSessions the saml sp sessions
	 */
	public void cacheResult(java.util.List<SamlSpSession> samlSpSessions);

	/**
	 * Creates a new saml sp session with the primary key. Does not add the saml sp session to the database.
	 *
	 * @param samlSpSessionId the primary key for the new saml sp session
	 * @return the new saml sp session
	 */
	public SamlSpSession create(long samlSpSessionId);

	/**
	 * Removes the saml sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session that was removed
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	public SamlSpSession remove(long samlSpSessionId)
		throws NoSuchSpSessionException;

	public SamlSpSession updateImpl(SamlSpSession samlSpSession);

	/**
	 * Returns the saml sp session with the primary key or throws a <code>NoSuchSpSessionException</code> if it could not be found.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session
	 * @throws NoSuchSpSessionException if a saml sp session with the primary key could not be found
	 */
	public SamlSpSession findByPrimaryKey(long samlSpSessionId)
		throws NoSuchSpSessionException;

	/**
	 * Returns the saml sp session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlSpSessionId the primary key of the saml sp session
	 * @return the saml sp session, or <code>null</code> if a saml sp session with the primary key could not be found
	 */
	public SamlSpSession fetchByPrimaryKey(long samlSpSessionId);

	/**
	 * Returns all the saml sp sessions.
	 *
	 * @return the saml sp sessions
	 */
	public java.util.List<SamlSpSession> findAll();

	/**
	 * Returns a range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @return the range of saml sp sessions
	 */
	public java.util.List<SamlSpSession> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml sp sessions
	 */
	public java.util.List<SamlSpSession> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator);

	/**
	 * Returns an ordered range of all the saml sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp sessions
	 * @param end the upper bound of the range of saml sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml sp sessions
	 */
	public java.util.List<SamlSpSession> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SamlSpSession>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the saml sp sessions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of saml sp sessions.
	 *
	 * @return the number of saml sp sessions
	 */
	public int countAll();

}