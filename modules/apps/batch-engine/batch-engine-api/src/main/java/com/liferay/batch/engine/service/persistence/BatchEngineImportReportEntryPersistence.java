/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.persistence;

import com.liferay.batch.engine.exception.NoSuchImportReportEntryException;
import com.liferay.batch.engine.model.BatchEngineImportReportEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the batch engine import report entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportReportEntryUtil
 * @generated
 */
@ProviderType
public interface BatchEngineImportReportEntryPersistence
	extends BasePersistence<BatchEngineImportReportEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BatchEngineImportReportEntryUtil} to access the batch engine import report entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Returns a range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of matching batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	public BatchEngineImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the first batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	public BatchEngineImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator);

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a matching batch engine import report entry could not be found
	 */
	public BatchEngineImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the last batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import report entry, or <code>null</code> if a matching batch engine import report entry could not be found
	 */
	public BatchEngineImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator);

	/**
	 * Returns the batch engine import report entries before and after the current batch engine import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the current batch engine import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public BatchEngineImportReportEntry[] findByC_C_C_PrevAndNext(
			long batchEngineImportReportEntryId, long companyId,
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator
				<BatchEngineImportReportEntry> orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Removes all the batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of batch engine import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching batch engine import report entries
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Caches the batch engine import report entry in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntry the batch engine import report entry
	 */
	public void cacheResult(
		BatchEngineImportReportEntry batchEngineImportReportEntry);

	/**
	 * Caches the batch engine import report entries in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportReportEntries the batch engine import report entries
	 */
	public void cacheResult(
		java.util.List<BatchEngineImportReportEntry>
			batchEngineImportReportEntries);

	/**
	 * Creates a new batch engine import report entry with the primary key. Does not add the batch engine import report entry to the database.
	 *
	 * @param batchEngineImportReportEntryId the primary key for the new batch engine import report entry
	 * @return the new batch engine import report entry
	 */
	public BatchEngineImportReportEntry create(
		long batchEngineImportReportEntryId);

	/**
	 * Removes the batch engine import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public BatchEngineImportReportEntry remove(
			long batchEngineImportReportEntryId)
		throws NoSuchImportReportEntryException;

	public BatchEngineImportReportEntry updateImpl(
		BatchEngineImportReportEntry batchEngineImportReportEntry);

	/**
	 * Returns the batch engine import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry
	 * @throws NoSuchImportReportEntryException if a batch engine import report entry with the primary key could not be found
	 */
	public BatchEngineImportReportEntry findByPrimaryKey(
			long batchEngineImportReportEntryId)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the batch engine import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportReportEntryId the primary key of the batch engine import report entry
	 * @return the batch engine import report entry, or <code>null</code> if a batch engine import report entry with the primary key could not be found
	 */
	public BatchEngineImportReportEntry fetchByPrimaryKey(
		long batchEngineImportReportEntryId);

	/**
	 * Returns all the batch engine import report entries.
	 *
	 * @return the batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findAll();

	/**
	 * Returns a range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @return the range of batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the batch engine import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import report entries
	 * @param end the upper bound of the range of batch engine import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import report entries
	 */
	public java.util.List<BatchEngineImportReportEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<BatchEngineImportReportEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the batch engine import report entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of batch engine import report entries.
	 *
	 * @return the number of batch engine import report entries
	 */
	public int countAll();

}