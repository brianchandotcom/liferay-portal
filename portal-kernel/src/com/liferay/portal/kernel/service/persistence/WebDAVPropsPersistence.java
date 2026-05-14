/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException;
import com.liferay.portal.kernel.model.WebDAVProps;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the web dav props service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsUtil
 * @generated
 */
@ProviderType
public interface WebDAVPropsPersistence extends BasePersistence<WebDAVProps> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebDAVPropsUtil} to access the web dav props persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchWebDAVPropsException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching web dav props
	 * @throws NoSuchWebDAVPropsException if a matching web dav props could not be found
	 */
	public WebDAVProps findByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException;

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	 */
	public WebDAVProps fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the web dav props where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the web dav props that was removed
	 */
	public WebDAVProps removeByC_C(long classNameId, long classPK)
		throws NoSuchWebDAVPropsException;

	/**
	 * Returns the number of web dav propses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching web dav propses
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Creates a new web dav props with the primary key. Does not add the web dav props to the database.
	 *
	 * @param webDavPropsId the primary key for the new web dav props
	 * @return the new web dav props
	 */
	public WebDAVProps create(long webDavPropsId);

	/**
	 * Removes the web dav props with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props that was removed
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	public WebDAVProps remove(long webDavPropsId)
		throws NoSuchWebDAVPropsException;

	public WebDAVProps updateImpl(WebDAVProps webDAVProps);

	/**
	 * Returns the web dav props with the primary key or throws a <code>NoSuchWebDAVPropsException</code> if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props
	 * @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	 */
	public WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws NoSuchWebDAVPropsException;

	/**
	 * Returns the web dav props with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param webDavPropsId the primary key of the web dav props
	 * @return the web dav props, or <code>null</code> if a web dav props with the primary key could not be found
	 */
	public WebDAVProps fetchByPrimaryKey(long webDavPropsId);

	/**
	 * Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	 */
	public default WebDAVProps fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:2137837106