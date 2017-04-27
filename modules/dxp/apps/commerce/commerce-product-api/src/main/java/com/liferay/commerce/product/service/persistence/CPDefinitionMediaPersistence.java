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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException;
import com.liferay.commerce.product.model.CPDefinitionMedia;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cp definition media service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.persistence.impl.CPDefinitionMediaPersistenceImpl
 * @see CPDefinitionMediaUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionMediaPersistence extends BasePersistence<CPDefinitionMedia> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionMediaUtil} to access the cp definition media persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cp definition medias where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63;.
	*
	* @param CPDefinitionMediaId the primary key of the current cp definition media
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public CPDefinitionMedia[] findByUuid_PrevAndNext(
		long CPDefinitionMediaId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Removes all the cp definition medias where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of cp definition medias where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp definition medias
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the cp definition media where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the cp definition media where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp definition media that was removed
	*/
	public CPDefinitionMedia removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the number of cp definition medias where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp definition medias
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the first cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media
	* @throws NoSuchCPDefinitionMediaException if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the last cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp definition media, or <code>null</code> if a matching cp definition media could not be found
	*/
	public CPDefinitionMedia fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns the cp definition medias before and after the current cp definition media in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDefinitionMediaId the primary key of the current cp definition media
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public CPDefinitionMedia[] findByUuid_C_PrevAndNext(
		long CPDefinitionMediaId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Removes all the cp definition medias where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of cp definition medias where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp definition medias
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Caches the cp definition media in the entity cache if it is enabled.
	*
	* @param cpDefinitionMedia the cp definition media
	*/
	public void cacheResult(CPDefinitionMedia cpDefinitionMedia);

	/**
	* Caches the cp definition medias in the entity cache if it is enabled.
	*
	* @param cpDefinitionMedias the cp definition medias
	*/
	public void cacheResult(
		java.util.List<CPDefinitionMedia> cpDefinitionMedias);

	/**
	* Creates a new cp definition media with the primary key. Does not add the cp definition media to the database.
	*
	* @param CPDefinitionMediaId the primary key for the new cp definition media
	* @return the new cp definition media
	*/
	public CPDefinitionMedia create(long CPDefinitionMediaId);

	/**
	* Removes the cp definition media with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media that was removed
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public CPDefinitionMedia remove(long CPDefinitionMediaId)
		throws NoSuchCPDefinitionMediaException;

	public CPDefinitionMedia updateImpl(CPDefinitionMedia cpDefinitionMedia);

	/**
	* Returns the cp definition media with the primary key or throws a {@link NoSuchCPDefinitionMediaException} if it could not be found.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media
	* @throws NoSuchCPDefinitionMediaException if a cp definition media with the primary key could not be found
	*/
	public CPDefinitionMedia findByPrimaryKey(long CPDefinitionMediaId)
		throws NoSuchCPDefinitionMediaException;

	/**
	* Returns the cp definition media with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDefinitionMediaId the primary key of the cp definition media
	* @return the cp definition media, or <code>null</code> if a cp definition media with the primary key could not be found
	*/
	public CPDefinitionMedia fetchByPrimaryKey(long CPDefinitionMediaId);

	@Override
	public java.util.Map<java.io.Serializable, CPDefinitionMedia> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cp definition medias.
	*
	* @return the cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findAll();

	/**
	* Returns a range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @return the range of cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findAll(int start, int end);

	/**
	* Returns an ordered range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator);

	/**
	* Returns an ordered range of all the cp definition medias.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionMediaModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition medias
	* @param end the upper bound of the range of cp definition medias (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp definition medias
	*/
	public java.util.List<CPDefinitionMedia> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CPDefinitionMedia> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cp definition medias from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cp definition medias.
	*
	* @return the number of cp definition medias
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}