/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.persistence;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ddm form instance report service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceReportUtil
 * @generated
 */
@ProviderType
public interface DDMFormInstanceReportPersistence
	extends BasePersistence<DDMFormInstanceReport>,
			CTPersistence<DDMFormInstanceReport> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMFormInstanceReportUtil} to access the ddm form instance report persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the ddm form instance report where formInstanceId = &#63; or throws a <code>NoSuchFormInstanceReportException</code> if it could not be found.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance report
	 * @throws NoSuchFormInstanceReportException if a matching ddm form instance report could not be found
	 */
	public DDMFormInstanceReport findByFormInstanceId(long formInstanceId)
		throws NoSuchFormInstanceReportException;

	/**
	 * Returns the ddm form instance report where formInstanceId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance report, or <code>null</code> if a matching ddm form instance report could not be found
	 */
	public DDMFormInstanceReport fetchByFormInstanceId(long formInstanceId);

	/**
	 * Returns the ddm form instance report where formInstanceId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm form instance report, or <code>null</code> if a matching ddm form instance report could not be found
	 */
	public DDMFormInstanceReport fetchByFormInstanceId(
		long formInstanceId, boolean useFinderCache);

	/**
	 * Removes the ddm form instance report where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the ddm form instance report that was removed
	 */
	public DDMFormInstanceReport removeByFormInstanceId(long formInstanceId)
		throws NoSuchFormInstanceReportException;

	/**
	 * Returns the number of ddm form instance reports where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance reports
	 */
	public int countByFormInstanceId(long formInstanceId);

	/**
	 * Caches the ddm form instance report in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceReport the ddm form instance report
	 */
	public void cacheResult(DDMFormInstanceReport ddmFormInstanceReport);

	/**
	 * Caches the ddm form instance reports in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceReports the ddm form instance reports
	 */
	public void cacheResult(
		java.util.List<DDMFormInstanceReport> ddmFormInstanceReports);

	/**
	 * Creates a new ddm form instance report with the primary key. Does not add the ddm form instance report to the database.
	 *
	 * @param formInstanceReportId the primary key for the new ddm form instance report
	 * @return the new ddm form instance report
	 */
	public DDMFormInstanceReport create(long formInstanceReportId);

	/**
	 * Removes the ddm form instance report with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report that was removed
	 * @throws NoSuchFormInstanceReportException if a ddm form instance report with the primary key could not be found
	 */
	public DDMFormInstanceReport remove(long formInstanceReportId)
		throws NoSuchFormInstanceReportException;

	public DDMFormInstanceReport updateImpl(
		DDMFormInstanceReport ddmFormInstanceReport);

	/**
	 * Returns the ddm form instance report with the primary key or throws a <code>NoSuchFormInstanceReportException</code> if it could not be found.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report
	 * @throws NoSuchFormInstanceReportException if a ddm form instance report with the primary key could not be found
	 */
	public DDMFormInstanceReport findByPrimaryKey(long formInstanceReportId)
		throws NoSuchFormInstanceReportException;

	/**
	 * Returns the ddm form instance report with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceReportId the primary key of the ddm form instance report
	 * @return the ddm form instance report, or <code>null</code> if a ddm form instance report with the primary key could not be found
	 */
	public DDMFormInstanceReport fetchByPrimaryKey(long formInstanceReportId);

	/**
	 * Returns all the ddm form instance reports.
	 *
	 * @return the ddm form instance reports
	 */
	public java.util.List<DDMFormInstanceReport> findAll();

	/**
	 * Returns a range of all the ddm form instance reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance reports
	 * @param end the upper bound of the range of ddm form instance reports (not inclusive)
	 * @return the range of ddm form instance reports
	 */
	public java.util.List<DDMFormInstanceReport> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ddm form instance reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance reports
	 * @param end the upper bound of the range of ddm form instance reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance reports
	 */
	public java.util.List<DDMFormInstanceReport> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceReport>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ddm form instance reports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMFormInstanceReportModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance reports
	 * @param end the upper bound of the range of ddm form instance reports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm form instance reports
	 */
	public java.util.List<DDMFormInstanceReport> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMFormInstanceReport>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ddm form instance reports from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ddm form instance reports.
	 *
	 * @return the number of ddm form instance reports
	 */
	public int countAll();

}