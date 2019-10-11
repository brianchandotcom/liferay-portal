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

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateLinkException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm template link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkUtil
 * @generated
 */
@ProviderType
public interface DDMTemplateLinkPersistence
	extends BasePersistence<DDMTemplateLink> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateLinkUtil} to access the ddm template link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ddm template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByClassNameId(long classNameId);

	/**
	 * Returns a range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm template links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public DDMTemplateLink findByClassNameId_First(
			long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the first ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns the last ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public DDMTemplateLink findByClassNameId_Last(
			long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the last ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns the ddm template links before and after the current ddm template link in the ordered set where classNameId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current ddm template link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public DDMTemplateLink[] findByClassNameId_PrevAndNext(
			long templateLinkId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Removes all the ddm template links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	public void removeByClassNameId(long classNameId);

	/**
	 * Returns the number of ddm template links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching ddm template links
	 */
	public int countByClassNameId(long classNameId);

	/**
	 * Returns all the ddm template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByTemplateId(long templateId);

	/**
	 * Returns a range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end);

	/**
	 * Returns an ordered range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm template links where templateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param templateId the template ID
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm template links
	 */
	public java.util.List<DDMTemplateLink> findByTemplateId(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public DDMTemplateLink findByTemplateId_First(
			long templateId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the first ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByTemplateId_First(
		long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns the last ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public DDMTemplateLink findByTemplateId_Last(
			long templateId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the last ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByTemplateId_Last(
		long templateId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns the ddm template links before and after the current ddm template link in the ordered set where templateId = &#63;.
	 *
	 * @param templateLinkId the primary key of the current ddm template link
	 * @param templateId the template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public DDMTemplateLink[] findByTemplateId_PrevAndNext(
			long templateLinkId, long templateId,
			com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
				orderByComparator)
		throws NoSuchTemplateLinkException;

	/**
	 * Removes all the ddm template links where templateId = &#63; from the database.
	 *
	 * @param templateId the template ID
	 */
	public void removeByTemplateId(long templateId);

	/**
	 * Returns the number of ddm template links where templateId = &#63;.
	 *
	 * @param templateId the template ID
	 * @return the number of matching ddm template links
	 */
	public int countByTemplateId(long templateId);

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchTemplateLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm template link
	 * @throws NoSuchTemplateLinkException if a matching ddm template link could not be found
	 */
	public DDMTemplateLink findByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByC_C(long classNameId, long classPK);

	/**
	 * Returns the ddm template link where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm template link, or <code>null</code> if a matching ddm template link could not be found
	 */
	public DDMTemplateLink fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache);

	/**
	 * Removes the ddm template link where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the ddm template link that was removed
	 */
	public DDMTemplateLink removeByC_C(long classNameId, long classPK)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the number of ddm template links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ddm template links
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Caches the ddm template link in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLink the ddm template link
	 */
	public void cacheResult(DDMTemplateLink ddmTemplateLink);

	/**
	 * Caches the ddm template links in the entity cache if it is enabled.
	 *
	 * @param ddmTemplateLinks the ddm template links
	 */
	public void cacheResult(java.util.List<DDMTemplateLink> ddmTemplateLinks);

	/**
	 * Creates a new ddm template link with the primary key. Does not add the ddm template link to the database.
	 *
	 * @param templateLinkId the primary key for the new ddm template link
	 * @return the new ddm template link
	 */
	public DDMTemplateLink create(long templateLinkId);

	/**
	 * Removes the ddm template link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link that was removed
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public DDMTemplateLink remove(long templateLinkId)
		throws NoSuchTemplateLinkException;

	public DDMTemplateLink updateImpl(DDMTemplateLink ddmTemplateLink);

	/**
	 * Returns the ddm template link with the primary key or throws a <code>NoSuchTemplateLinkException</code> if it could not be found.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link
	 * @throws NoSuchTemplateLinkException if a ddm template link with the primary key could not be found
	 */
	public DDMTemplateLink findByPrimaryKey(long templateLinkId)
		throws NoSuchTemplateLinkException;

	/**
	 * Returns the ddm template link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param templateLinkId the primary key of the ddm template link
	 * @return the ddm template link, or <code>null</code> if a ddm template link with the primary key could not be found
	 */
	public DDMTemplateLink fetchByPrimaryKey(long templateLinkId);

	/**
	 * Returns all the ddm template links.
	 *
	 * @return the ddm template links
	 */
	public java.util.List<DDMTemplateLink> findAll();

	/**
	 * Returns a range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @return the range of ddm template links
	 */
	public java.util.List<DDMTemplateLink> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm template links
	 */
	public java.util.List<DDMTemplateLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm template links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMTemplateLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm template links
	 * @param end the upper bound of the range of ddm template links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm template links
	 */
	public java.util.List<DDMTemplateLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplateLink>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm template links from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm template links.
	 *
	 * @return the number of ddm template links
	 */
	public int countAll();

}