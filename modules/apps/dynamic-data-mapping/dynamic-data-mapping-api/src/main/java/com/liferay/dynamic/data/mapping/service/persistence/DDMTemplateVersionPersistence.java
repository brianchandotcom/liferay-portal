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

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m template version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateVersionPersistenceImpl
 * @see DDMTemplateVersionUtil
 * @generated
 */
@ProviderType
public interface DDMTemplateVersionPersistence extends BasePersistence<DDMTemplateVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateVersionUtil} to access the d d m template version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m template versions where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByTemplateId(long templateId);

	/**
	* Returns a range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end);

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByTemplateId(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version
	* @throws NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion findByTemplateId_First(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByTemplateId_First(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version
	* @throws NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion findByTemplateId_Last(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByTemplateId_Last(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63;.
	*
	* @param templateVersionId the primary key of the current d d m template version
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template version
	* @throws NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public DDMTemplateVersion[] findByTemplateId_PrevAndNext(
		long templateVersionId, long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Removes all the d d m template versions where templateId = &#63; from the database.
	*
	* @param templateId the template ID
	*/
	public void removeByTemplateId(long templateId);

	/**
	* Returns the number of d d m template versions where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the number of matching d d m template versions
	*/
	public int countByTemplateId(long templateId);

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or throws a {@link NoSuchTemplateVersionException} if it could not be found.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the matching d d m template version
	* @throws NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion findByT_V(long templateId,
		java.lang.String version) throws NoSuchTemplateVersionException;

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByT_V(long templateId,
		java.lang.String version);

	/**
	* Returns the d d m template version where templateId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param templateId the template ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByT_V(long templateId,
		java.lang.String version, boolean retrieveFromCache);

	/**
	* Removes the d d m template version where templateId = &#63; and version = &#63; from the database.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the d d m template version that was removed
	*/
	public DDMTemplateVersion removeByT_V(long templateId,
		java.lang.String version) throws NoSuchTemplateVersionException;

	/**
	* Returns the number of d d m template versions where templateId = &#63; and version = &#63;.
	*
	* @param templateId the template ID
	* @param version the version
	* @return the number of matching d d m template versions
	*/
	public int countByT_V(long templateId, java.lang.String version);

	/**
	* Returns all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @return the matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByT_S(long templateId,
		int status);

	/**
	* Returns a range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template versions where templateId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param status the status
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findByT_S(long templateId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version
	* @throws NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion findByT_S_First(long templateId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Returns the first d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByT_S_First(long templateId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version
	* @throws NoSuchTemplateVersionException if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion findByT_S_Last(long templateId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Returns the last d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template version, or <code>null</code> if a matching d d m template version could not be found
	*/
	public DDMTemplateVersion fetchByT_S_Last(long templateId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns the d d m template versions before and after the current d d m template version in the ordered set where templateId = &#63; and status = &#63;.
	*
	* @param templateVersionId the primary key of the current d d m template version
	* @param templateId the template ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template version
	* @throws NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public DDMTemplateVersion[] findByT_S_PrevAndNext(long templateVersionId,
		long templateId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator)
		throws NoSuchTemplateVersionException;

	/**
	* Removes all the d d m template versions where templateId = &#63; and status = &#63; from the database.
	*
	* @param templateId the template ID
	* @param status the status
	*/
	public void removeByT_S(long templateId, int status);

	/**
	* Returns the number of d d m template versions where templateId = &#63; and status = &#63;.
	*
	* @param templateId the template ID
	* @param status the status
	* @return the number of matching d d m template versions
	*/
	public int countByT_S(long templateId, int status);

	/**
	* Caches the d d m template version in the entity cache if it is enabled.
	*
	* @param ddmTemplateVersion the d d m template version
	*/
	public void cacheResult(DDMTemplateVersion ddmTemplateVersion);

	/**
	* Caches the d d m template versions in the entity cache if it is enabled.
	*
	* @param ddmTemplateVersions the d d m template versions
	*/
	public void cacheResult(
		java.util.List<DDMTemplateVersion> ddmTemplateVersions);

	/**
	* Creates a new d d m template version with the primary key. Does not add the d d m template version to the database.
	*
	* @param templateVersionId the primary key for the new d d m template version
	* @return the new d d m template version
	*/
	public DDMTemplateVersion create(long templateVersionId);

	/**
	* Removes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version that was removed
	* @throws NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public DDMTemplateVersion remove(long templateVersionId)
		throws NoSuchTemplateVersionException;

	public DDMTemplateVersion updateImpl(DDMTemplateVersion ddmTemplateVersion);

	/**
	* Returns the d d m template version with the primary key or throws a {@link NoSuchTemplateVersionException} if it could not be found.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version
	* @throws NoSuchTemplateVersionException if a d d m template version with the primary key could not be found
	*/
	public DDMTemplateVersion findByPrimaryKey(long templateVersionId)
		throws NoSuchTemplateVersionException;

	/**
	* Returns the d d m template version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version, or <code>null</code> if a d d m template version with the primary key could not be found
	*/
	public DDMTemplateVersion fetchByPrimaryKey(long templateVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DDMTemplateVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m template versions.
	*
	* @return the d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findAll();

	/**
	* Returns a range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m template versions
	*/
	public java.util.List<DDMTemplateVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m template versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m template versions.
	*
	* @return the number of d d m template versions
	*/
	public int countAll();
}