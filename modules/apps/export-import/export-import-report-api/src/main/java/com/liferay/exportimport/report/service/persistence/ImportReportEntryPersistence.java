/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.report.service.persistence;

import com.liferay.exportimport.report.exception.NoSuchImportReportEntryException;
import com.liferay.exportimport.report.model.ImportReportEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the import report entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Carlos Correa
 * @see ImportReportEntryUtil
 * @generated
 */
@ProviderType
public interface ImportReportEntryPersistence
	extends BasePersistence<ImportReportEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ImportReportEntryUtil} to access the import report entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching import report entries
	 */
	public java.util.List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK);

	/**
	 * Returns a range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of matching import report entries
	 */
	public java.util.List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end);

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching import report entries
	 */
	public java.util.List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching import report entries
	 */
	public java.util.List<ImportReportEntry> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	public ImportReportEntry findByC_C_C_First(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
				orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the first import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	public ImportReportEntry fetchByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator);

	/**
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry
	 * @throws NoSuchImportReportEntryException if a matching import report entry could not be found
	 */
	public ImportReportEntry findByC_C_C_Last(
			long companyId, long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
				orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the last import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching import report entry, or <code>null</code> if a matching import report entry could not be found
	 */
	public ImportReportEntry fetchByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator);

	/**
	 * Returns the import report entries before and after the current import report entry in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param importReportEntryId the primary key of the current import report entry
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public ImportReportEntry[] findByC_C_C_PrevAndNext(
			long importReportEntryId, long companyId, long classNameId,
			long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
				orderByComparator)
		throws NoSuchImportReportEntryException;

	/**
	 * Removes all the import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Returns the number of import report entries where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching import report entries
	 */
	public int countByC_C_C(long companyId, long classNameId, long classPK);

	/**
	 * Caches the import report entry in the entity cache if it is enabled.
	 *
	 * @param importReportEntry the import report entry
	 */
	public void cacheResult(ImportReportEntry importReportEntry);

	/**
	 * Caches the import report entries in the entity cache if it is enabled.
	 *
	 * @param importReportEntries the import report entries
	 */
	public void cacheResult(
		java.util.List<ImportReportEntry> importReportEntries);

	/**
	 * Creates a new import report entry with the primary key. Does not add the import report entry to the database.
	 *
	 * @param importReportEntryId the primary key for the new import report entry
	 * @return the new import report entry
	 */
	public ImportReportEntry create(long importReportEntryId);

	/**
	 * Removes the import report entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry that was removed
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public ImportReportEntry remove(long importReportEntryId)
		throws NoSuchImportReportEntryException;

	public ImportReportEntry updateImpl(ImportReportEntry importReportEntry);

	/**
	 * Returns the import report entry with the primary key or throws a <code>NoSuchImportReportEntryException</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry
	 * @throws NoSuchImportReportEntryException if a import report entry with the primary key could not be found
	 */
	public ImportReportEntry findByPrimaryKey(long importReportEntryId)
		throws NoSuchImportReportEntryException;

	/**
	 * Returns the import report entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param importReportEntryId the primary key of the import report entry
	 * @return the import report entry, or <code>null</code> if a import report entry with the primary key could not be found
	 */
	public ImportReportEntry fetchByPrimaryKey(long importReportEntryId);

	/**
	 * Returns all the import report entries.
	 *
	 * @return the import report entries
	 */
	public java.util.List<ImportReportEntry> findAll();

	/**
	 * Returns a range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @return the range of import report entries
	 */
	public java.util.List<ImportReportEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of import report entries
	 */
	public java.util.List<ImportReportEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the import report entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ImportReportEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of import report entries
	 * @param end the upper bound of the range of import report entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of import report entries
	 */
	public java.util.List<ImportReportEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ImportReportEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the import report entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of import report entries.
	 *
	 * @return the number of import report entries
	 */
	public int countAll();

}