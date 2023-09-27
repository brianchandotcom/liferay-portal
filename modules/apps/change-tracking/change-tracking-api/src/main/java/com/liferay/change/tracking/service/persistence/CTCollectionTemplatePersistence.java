/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchCollectionTemplateException;
import com.liferay.change.tracking.model.CTCollectionTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct collection template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplateUtil
 * @generated
 */
@ProviderType
public interface CTCollectionTemplatePersistence
	extends BasePersistence<CTCollectionTemplate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTCollectionTemplateUtil} to access the ct collection template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct collection templates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of matching ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct collection templates where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection template
	 * @throws NoSuchCollectionTemplateException if a matching ct collection template could not be found
	 */
	public CTCollectionTemplate findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTCollectionTemplate> orderByComparator)
		throws NoSuchCollectionTemplateException;

	/**
	 * Returns the first ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct collection template, or <code>null</code> if a matching ct collection template could not be found
	 */
	public CTCollectionTemplate fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator);

	/**
	 * Returns the last ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection template
	 * @throws NoSuchCollectionTemplateException if a matching ct collection template could not be found
	 */
	public CTCollectionTemplate findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTCollectionTemplate> orderByComparator)
		throws NoSuchCollectionTemplateException;

	/**
	 * Returns the last ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct collection template, or <code>null</code> if a matching ct collection template could not be found
	 */
	public CTCollectionTemplate fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator);

	/**
	 * Returns the ct collection templates before and after the current ct collection template in the ordered set where companyId = &#63;.
	 *
	 * @param ctCollectionTemplateId the primary key of the current ct collection template
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public CTCollectionTemplate[] findByCompanyId_PrevAndNext(
			long ctCollectionTemplateId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTCollectionTemplate> orderByComparator)
		throws NoSuchCollectionTemplateException;

	/**
	 * Returns all the ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct collection templates that the user has permission to view
	 */
	public java.util.List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of matching ct collection templates that the user has permission to view
	 */
	public java.util.List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct collection templates that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct collection templates that the user has permission to view
	 */
	public java.util.List<CTCollectionTemplate> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator);

	/**
	 * Returns the ct collection templates before and after the current ct collection template in the ordered set of ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param ctCollectionTemplateId the primary key of the current ct collection template
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public CTCollectionTemplate[] filterFindByCompanyId_PrevAndNext(
			long ctCollectionTemplateId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CTCollectionTemplate> orderByComparator)
		throws NoSuchCollectionTemplateException;

	/**
	 * Removes all the ct collection templates where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ct collection templates where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct collection templates
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of ct collection templates that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct collection templates that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Caches the ct collection template in the entity cache if it is enabled.
	 *
	 * @param ctCollectionTemplate the ct collection template
	 */
	public void cacheResult(CTCollectionTemplate ctCollectionTemplate);

	/**
	 * Caches the ct collection templates in the entity cache if it is enabled.
	 *
	 * @param ctCollectionTemplates the ct collection templates
	 */
	public void cacheResult(
		java.util.List<CTCollectionTemplate> ctCollectionTemplates);

	/**
	 * Creates a new ct collection template with the primary key. Does not add the ct collection template to the database.
	 *
	 * @param ctCollectionTemplateId the primary key for the new ct collection template
	 * @return the new ct collection template
	 */
	public CTCollectionTemplate create(long ctCollectionTemplateId);

	/**
	 * Removes the ct collection template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template that was removed
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public CTCollectionTemplate remove(long ctCollectionTemplateId)
		throws NoSuchCollectionTemplateException;

	public CTCollectionTemplate updateImpl(
		CTCollectionTemplate ctCollectionTemplate);

	/**
	 * Returns the ct collection template with the primary key or throws a <code>NoSuchCollectionTemplateException</code> if it could not be found.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template
	 * @throws NoSuchCollectionTemplateException if a ct collection template with the primary key could not be found
	 */
	public CTCollectionTemplate findByPrimaryKey(long ctCollectionTemplateId)
		throws NoSuchCollectionTemplateException;

	/**
	 * Returns the ct collection template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctCollectionTemplateId the primary key of the ct collection template
	 * @return the ct collection template, or <code>null</code> if a ct collection template with the primary key could not be found
	 */
	public CTCollectionTemplate fetchByPrimaryKey(long ctCollectionTemplateId);

	/**
	 * Returns all the ct collection templates.
	 *
	 * @return the ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findAll();

	/**
	 * Returns a range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @return the range of ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct collection templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTCollectionTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct collection templates
	 * @param end the upper bound of the range of ct collection templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct collection templates
	 */
	public java.util.List<CTCollectionTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTCollectionTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct collection templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct collection templates.
	 *
	 * @return the number of ct collection templates
	 */
	public int countAll();

}