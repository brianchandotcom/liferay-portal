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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp definition diagram entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntryUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionDiagramEntryPersistence
	extends BasePersistence<CPDefinitionDiagramEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionDiagramEntryUtil} to access the cp definition diagram entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @return the matching cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry>
		findByCPDefinitionDiagramSettingsId(long CPDefinitionDiagramSettingsId);

	/**
	 * Returns a range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of matching cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry>
		findByCPDefinitionDiagramSettingsId(
			long CPDefinitionDiagramSettingsId, int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry>
		findByCPDefinitionDiagramSettingsId(
			long CPDefinitionDiagramSettingsId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry>
		findByCPDefinitionDiagramSettingsId(
			long CPDefinitionDiagramSettingsId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramEntry> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	public CPDefinitionDiagramEntry findByCPDefinitionDiagramSettingsId_First(
			long CPDefinitionDiagramSettingsId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException;

	/**
	 * Returns the first cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public CPDefinitionDiagramEntry fetchByCPDefinitionDiagramSettingsId_First(
		long CPDefinitionDiagramSettingsId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramEntry> orderByComparator);

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a matching cp definition diagram entry could not be found
	 */
	public CPDefinitionDiagramEntry findByCPDefinitionDiagramSettingsId_Last(
			long CPDefinitionDiagramSettingsId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException;

	/**
	 * Returns the last cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram entry, or <code>null</code> if a matching cp definition diagram entry could not be found
	 */
	public CPDefinitionDiagramEntry fetchByCPDefinitionDiagramSettingsId_Last(
		long CPDefinitionDiagramSettingsId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramEntry> orderByComparator);

	/**
	 * Returns the cp definition diagram entries before and after the current cp definition diagram entry in the ordered set where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the current cp definition diagram entry
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public CPDefinitionDiagramEntry[]
			findByCPDefinitionDiagramSettingsId_PrevAndNext(
				long CPDefinitionDiagramEntryId,
				long CPDefinitionDiagramSettingsId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CPDefinitionDiagramEntry> orderByComparator)
		throws NoSuchCPDefinitionDiagramEntryException;

	/**
	 * Removes all the cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63; from the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 */
	public void removeByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId);

	/**
	 * Returns the number of cp definition diagram entries where CPDefinitionDiagramSettingsId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID
	 * @return the number of matching cp definition diagram entries
	 */
	public int countByCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId);

	/**
	 * Caches the cp definition diagram entry in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntry the cp definition diagram entry
	 */
	public void cacheResult(CPDefinitionDiagramEntry cpDefinitionDiagramEntry);

	/**
	 * Caches the cp definition diagram entries in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramEntries the cp definition diagram entries
	 */
	public void cacheResult(
		java.util.List<CPDefinitionDiagramEntry> cpDefinitionDiagramEntries);

	/**
	 * Creates a new cp definition diagram entry with the primary key. Does not add the cp definition diagram entry to the database.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key for the new cp definition diagram entry
	 * @return the new cp definition diagram entry
	 */
	public CPDefinitionDiagramEntry create(long CPDefinitionDiagramEntryId);

	/**
	 * Removes the cp definition diagram entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry that was removed
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public CPDefinitionDiagramEntry remove(long CPDefinitionDiagramEntryId)
		throws NoSuchCPDefinitionDiagramEntryException;

	public CPDefinitionDiagramEntry updateImpl(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry);

	/**
	 * Returns the cp definition diagram entry with the primary key or throws a <code>NoSuchCPDefinitionDiagramEntryException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry
	 * @throws NoSuchCPDefinitionDiagramEntryException if a cp definition diagram entry with the primary key could not be found
	 */
	public CPDefinitionDiagramEntry findByPrimaryKey(
			long CPDefinitionDiagramEntryId)
		throws NoSuchCPDefinitionDiagramEntryException;

	/**
	 * Returns the cp definition diagram entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramEntryId the primary key of the cp definition diagram entry
	 * @return the cp definition diagram entry, or <code>null</code> if a cp definition diagram entry with the primary key could not be found
	 */
	public CPDefinitionDiagramEntry fetchByPrimaryKey(
		long CPDefinitionDiagramEntryId);

	/**
	 * Returns all the cp definition diagram entries.
	 *
	 * @return the cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry> findAll();

	/**
	 * Returns a range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @return the range of cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram entries
	 * @param end the upper bound of the range of cp definition diagram entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram entries
	 */
	public java.util.List<CPDefinitionDiagramEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp definition diagram entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp definition diagram entries.
	 *
	 * @return the number of cp definition diagram entries
	 */
	public int countAll();

}