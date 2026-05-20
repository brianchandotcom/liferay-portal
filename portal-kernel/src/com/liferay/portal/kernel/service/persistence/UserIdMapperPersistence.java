/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchUserIdMapperException;
import com.liferay.portal.kernel.model.UserIdMapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the user ID mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperUtil
 * @generated
 */
@ProviderType
public interface UserIdMapperPersistence extends BasePersistence<UserIdMapper> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserIdMapperUtil} to access the user ID mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching user ID mappers
	 */
	public java.util.List<UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserIdMapper>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	public UserIdMapper findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<UserIdMapper>
				orderByComparator)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the first user ID mapper in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	public UserIdMapper fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<UserIdMapper>
			orderByComparator);

	/**
	 * Removes all the user ID mappers where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of user ID mappers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching user ID mappers
	 */
	public int countByUserId(long userId);

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	public UserIdMapper findByU_T(long userId, String type)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	public UserIdMapper fetchByU_T(
		long userId, String type, boolean useFinderCache);

	/**
	 * Removes the user ID mapper where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the user ID mapper that was removed
	 */
	public UserIdMapper removeByU_T(long userId, String type)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the number of user ID mappers where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching user ID mappers
	 */
	public int countByU_T(long userId, String type);

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the matching user ID mapper
	 * @throws NoSuchUserIdMapperException if a matching user ID mapper could not be found
	 */
	public UserIdMapper findByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	public UserIdMapper fetchByT_E(
		String type, String externalUserId, boolean useFinderCache);

	/**
	 * Removes the user ID mapper where type = &#63; and externalUserId = &#63; from the database.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the user ID mapper that was removed
	 */
	public UserIdMapper removeByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the number of user ID mappers where type = &#63; and externalUserId = &#63;.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the number of matching user ID mappers
	 */
	public int countByT_E(String type, String externalUserId);

	/**
	 * Creates a new user ID mapper with the primary key. Does not add the user ID mapper to the database.
	 *
	 * @param userIdMapperId the primary key for the new user ID mapper
	 * @return the new user ID mapper
	 */
	public UserIdMapper create(long userIdMapperId);

	/**
	 * Removes the user ID mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper that was removed
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	public UserIdMapper remove(long userIdMapperId)
		throws NoSuchUserIdMapperException;

	public UserIdMapper updateImpl(UserIdMapper userIdMapper);

	/**
	 * Returns the user ID mapper with the primary key or throws a <code>NoSuchUserIdMapperException</code> if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper
	 * @throws NoSuchUserIdMapperException if a user ID mapper with the primary key could not be found
	 */
	public UserIdMapper findByPrimaryKey(long userIdMapperId)
		throws NoSuchUserIdMapperException;

	/**
	 * Returns the user ID mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user ID mapper
	 * @return the user ID mapper, or <code>null</code> if a user ID mapper with the primary key could not be found
	 */
	public UserIdMapper fetchByPrimaryKey(long userIdMapperId);

	/**
	 * Returns the user ID mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	public default UserIdMapper fetchByU_T(long userId, String type) {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Returns the user ID mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type
	 * @param externalUserId the external user ID
	 * @return the matching user ID mapper, or <code>null</code> if a matching user ID mapper could not be found
	 */
	public default UserIdMapper fetchByT_E(String type, String externalUserId) {
		return fetchByT_E(type, externalUserId, true);
	}

	/**
	 * Returns all the user ID mappers where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching user ID mappers
	 */
	public default java.util.List<UserIdMapper> findByUserId(long userId) {
		return findByUserId(
			userId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @return the range of matching user ID mappers
	 */
	public default java.util.List<UserIdMapper> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the user ID mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserIdMapperModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of user ID mappers
	 * @param end the upper bound of the range of user ID mappers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching user ID mappers
	 */
	public default java.util.List<UserIdMapper> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<UserIdMapper>
			orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1432785736