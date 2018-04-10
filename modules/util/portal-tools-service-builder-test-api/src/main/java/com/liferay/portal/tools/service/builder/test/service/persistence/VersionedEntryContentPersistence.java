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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContent;

/**
 * The persistence interface for the versioned entry content service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.impl.VersionedEntryContentPersistenceImpl
 * @see VersionedEntryContentUtil
 * @generated
 */
@ProviderType
public interface VersionedEntryContentPersistence extends BasePersistence<VersionedEntryContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VersionedEntryContentUtil} to access the versioned entry content persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the versioned entry contents where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the matching versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId);

	/**
	* Returns a range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @return the range of matching versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end);

	/**
	* Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry contents where versionedEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param versionedEntryId the versioned entry ID
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent findByVersionedEntryId_First(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the first versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByVersionedEntryId_First(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator);

	/**
	* Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent findByVersionedEntryId_Last(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the last versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByVersionedEntryId_Last(
		long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator);

	/**
	* Returns the versioned entry contents before and after the current versioned entry content in the ordered set where versionedEntryId = &#63;.
	*
	* @param versionedEntryContentId the primary key of the current versioned entry content
	* @param versionedEntryId the versioned entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next versioned entry content
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public VersionedEntryContent[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentId, long versionedEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator)
		throws NoSuchVersionedEntryContentException;

	/**
	* Removes all the versioned entry contents where versionedEntryId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	*/
	public void removeByVersionedEntryId(long versionedEntryId);

	/**
	* Returns the number of versioned entry contents where versionedEntryId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @return the number of matching versioned entry contents
	*/
	public int countByVersionedEntryId(long versionedEntryId);

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent findByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId);

	/**
	* Returns the versioned entry content where versionedEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the versioned entry content where versionedEntryId = &#63; and languageId = &#63; from the database.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the versioned entry content that was removed
	*/
	public VersionedEntryContent removeByVersionedEntryId_LanguageId(
		long versionedEntryId, java.lang.String languageId)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the number of versioned entry contents where versionedEntryId = &#63; and languageId = &#63;.
	*
	* @param versionedEntryId the versioned entry ID
	* @param languageId the language ID
	* @return the number of matching versioned entry contents
	*/
	public int countByVersionedEntryId_LanguageId(long versionedEntryId,
		java.lang.String languageId);

	/**
	* Returns the versioned entry content where headId = &#63; or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param headId the head ID
	* @return the matching versioned entry content
	* @throws NoSuchVersionedEntryContentException if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent findByHeadId(long headId)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param headId the head ID
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByHeadId(long headId);

	/**
	* Returns the versioned entry content where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param headId the head ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching versioned entry content, or <code>null</code> if a matching versioned entry content could not be found
	*/
	public VersionedEntryContent fetchByHeadId(long headId,
		boolean retrieveFromCache);

	/**
	* Removes the versioned entry content where headId = &#63; from the database.
	*
	* @param headId the head ID
	* @return the versioned entry content that was removed
	*/
	public VersionedEntryContent removeByHeadId(long headId)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the number of versioned entry contents where headId = &#63;.
	*
	* @param headId the head ID
	* @return the number of matching versioned entry contents
	*/
	public int countByHeadId(long headId);

	/**
	* Caches the versioned entry content in the entity cache if it is enabled.
	*
	* @param versionedEntryContent the versioned entry content
	*/
	public void cacheResult(VersionedEntryContent versionedEntryContent);

	/**
	* Caches the versioned entry contents in the entity cache if it is enabled.
	*
	* @param versionedEntryContents the versioned entry contents
	*/
	public void cacheResult(
		java.util.List<VersionedEntryContent> versionedEntryContents);

	/**
	* Creates a new versioned entry content with the primary key. Does not add the versioned entry content to the database.
	*
	* @param versionedEntryContentId the primary key for the new versioned entry content
	* @return the new versioned entry content
	*/
	public VersionedEntryContent create(long versionedEntryContentId);

	/**
	* Removes the versioned entry content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content that was removed
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public VersionedEntryContent remove(long versionedEntryContentId)
		throws NoSuchVersionedEntryContentException;

	public VersionedEntryContent updateImpl(
		VersionedEntryContent versionedEntryContent);

	/**
	* Returns the versioned entry content with the primary key or throws a {@link NoSuchVersionedEntryContentException} if it could not be found.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content
	* @throws NoSuchVersionedEntryContentException if a versioned entry content with the primary key could not be found
	*/
	public VersionedEntryContent findByPrimaryKey(long versionedEntryContentId)
		throws NoSuchVersionedEntryContentException;

	/**
	* Returns the versioned entry content with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param versionedEntryContentId the primary key of the versioned entry content
	* @return the versioned entry content, or <code>null</code> if a versioned entry content with the primary key could not be found
	*/
	public VersionedEntryContent fetchByPrimaryKey(long versionedEntryContentId);

	@Override
	public java.util.Map<java.io.Serializable, VersionedEntryContent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the versioned entry contents.
	*
	* @return the versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findAll();

	/**
	* Returns a range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @return the range of versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findAll(int start, int end);

	/**
	* Returns an ordered range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator);

	/**
	* Returns an ordered range of all the versioned entry contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of versioned entry contents
	* @param end the upper bound of the range of versioned entry contents (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of versioned entry contents
	*/
	public java.util.List<VersionedEntryContent> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<VersionedEntryContent> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the versioned entry contents from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of versioned entry contents.
	*
	* @return the number of versioned entry contents
	*/
	public int countAll();
}