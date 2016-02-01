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

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateLinkException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m template link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplateLinkPersistenceImpl
 * @see DDMTemplateLinkUtil
 * @generated
 */
@ProviderType
public interface DDMTemplateLinkPersistence extends BasePersistence<DDMTemplateLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateLinkUtil} to access the d d m template link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m template links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByClassNameId(long classNameId);

	/**
	* Returns a range of all the d d m template links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @return the range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByClassNameId(long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m template links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template links where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template link
	* @throws NoSuchTemplateLinkException if a matching d d m template link could not be found
	*/
	public DDMTemplateLink findByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the first d d m template link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns the last d d m template link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template link
	* @throws NoSuchTemplateLinkException if a matching d d m template link could not be found
	*/
	public DDMTemplateLink findByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the last d d m template link in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns the d d m template links before and after the current d d m template link in the ordered set where classNameId = &#63;.
	*
	* @param templateLinkId the primary key of the current d d m template link
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template link
	* @throws NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	*/
	public DDMTemplateLink[] findByClassNameId_PrevAndNext(
		long templateLinkId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Removes all the d d m template links where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByClassNameId(long classNameId);

	/**
	* Returns the number of d d m template links where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching d d m template links
	*/
	public int countByClassNameId(long classNameId);

	/**
	* Returns all the d d m template links where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByTemplateId(long templateId);

	/**
	* Returns a range of all the d d m template links where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @return the range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByTemplateId(long templateId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m template links where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByTemplateId(long templateId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template links where templateId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateId the template ID
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m template links
	*/
	public java.util.List<DDMTemplateLink> findByTemplateId(long templateId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template link in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template link
	* @throws NoSuchTemplateLinkException if a matching d d m template link could not be found
	*/
	public DDMTemplateLink findByTemplateId_First(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the first d d m template link in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByTemplateId_First(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns the last d d m template link in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template link
	* @throws NoSuchTemplateLinkException if a matching d d m template link could not be found
	*/
	public DDMTemplateLink findByTemplateId_Last(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the last d d m template link in the ordered set where templateId = &#63;.
	*
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByTemplateId_Last(long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns the d d m template links before and after the current d d m template link in the ordered set where templateId = &#63;.
	*
	* @param templateLinkId the primary key of the current d d m template link
	* @param templateId the template ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template link
	* @throws NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	*/
	public DDMTemplateLink[] findByTemplateId_PrevAndNext(long templateLinkId,
		long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	* Removes all the d d m template links where templateId = &#63; from the database.
	*
	* @param templateId the template ID
	*/
	public void removeByTemplateId(long templateId);

	/**
	* Returns the number of d d m template links where templateId = &#63;.
	*
	* @param templateId the template ID
	* @return the number of matching d d m template links
	*/
	public int countByTemplateId(long templateId);

	/**
	* Returns the d d m template link where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchTemplateLinkException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m template link
	* @throws NoSuchTemplateLinkException if a matching d d m template link could not be found
	*/
	public DDMTemplateLink findByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the d d m template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the d d m template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template link, or <code>null</code> if a matching d d m template link could not be found
	*/
	public DDMTemplateLink fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the d d m template link where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the d d m template link that was removed
	*/
	public DDMTemplateLink removeByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the number of d d m template links where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m template links
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the d d m template link in the entity cache if it is enabled.
	*
	* @param ddmTemplateLink the d d m template link
	*/
	public void cacheResult(DDMTemplateLink ddmTemplateLink);

	/**
	* Caches the d d m template links in the entity cache if it is enabled.
	*
	* @param ddmTemplateLinks the d d m template links
	*/
	public void cacheResult(java.util.List<DDMTemplateLink> ddmTemplateLinks);

	/**
	* Creates a new d d m template link with the primary key. Does not add the d d m template link to the database.
	*
	* @param templateLinkId the primary key for the new d d m template link
	* @return the new d d m template link
	*/
	public DDMTemplateLink create(long templateLinkId);

	/**
	* Removes the d d m template link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateLinkId the primary key of the d d m template link
	* @return the d d m template link that was removed
	* @throws NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	*/
	public DDMTemplateLink remove(long templateLinkId)
		throws NoSuchTemplateLinkException;

	public DDMTemplateLink updateImpl(DDMTemplateLink ddmTemplateLink);

	/**
	* Returns the d d m template link with the primary key or throws a {@link NoSuchTemplateLinkException} if it could not be found.
	*
	* @param templateLinkId the primary key of the d d m template link
	* @return the d d m template link
	* @throws NoSuchTemplateLinkException if a d d m template link with the primary key could not be found
	*/
	public DDMTemplateLink findByPrimaryKey(long templateLinkId)
		throws NoSuchTemplateLinkException;

	/**
	* Returns the d d m template link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateLinkId the primary key of the d d m template link
	* @return the d d m template link, or <code>null</code> if a d d m template link with the primary key could not be found
	*/
	public DDMTemplateLink fetchByPrimaryKey(long templateLinkId);

	@Override
	public java.util.Map<java.io.Serializable, DDMTemplateLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m template links.
	*
	* @return the d d m template links
	*/
	public java.util.List<DDMTemplateLink> findAll();

	/**
	* Returns a range of all the d d m template links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @return the range of d d m template links
	*/
	public java.util.List<DDMTemplateLink> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m template links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m template links
	*/
	public java.util.List<DDMTemplateLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m template links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template links
	* @param end the upper bound of the range of d d m template links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m template links
	*/
	public java.util.List<DDMTemplateLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m template links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m template links.
	*
	* @return the number of d d m template links
	*/
	public int countAll();
}