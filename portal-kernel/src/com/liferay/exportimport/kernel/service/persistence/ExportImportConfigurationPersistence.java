/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.service.persistence;

import com.liferay.exportimport.kernel.exception.NoSuchConfigurationException;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the export import configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationUtil
 * @generated
 */
@ProviderType
public interface ExportImportConfigurationPersistence
	extends BasePersistence<ExportImportConfiguration> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExportImportConfigurationUtil} to access the export import configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByGroupId(
		long groupId);

	/**
	 * Returns a range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration[] findByGroupId_PrevAndNext(
			long exportImportConfigurationId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Removes all the export import configurations where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of export import configurations where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching export import configurations
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the first export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the last export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the last export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where companyId = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration[] findByCompanyId_PrevAndNext(
			long exportImportConfigurationId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Removes all the export import configurations where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of export import configurations where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching export import configurations
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T(
		long groupId, int type);

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_T_First(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_T_First(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_T_Last(
			long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_T_Last(
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration[] findByG_T_PrevAndNext(
			long exportImportConfigurationId, long groupId, int type,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public void removeByG_T(long groupId, int type);

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching export import configurations
	 */
	public int countByG_T(long groupId, int type);

	/**
	 * Returns all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_S(
		long groupId, int status);

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_S_First(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_S_Last(
			long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration[] findByG_S_PrevAndNext(
			long exportImportConfigurationId, long groupId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Removes all the export import configurations where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public void removeByG_S(long groupId, int status);

	/**
	 * Returns the number of export import configurations where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	public int countByG_S(long groupId, int status);

	/**
	 * Returns all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status);

	/**
	 * Returns a range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_T_S_First(
			long groupId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the first export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_T_S_First(
		long groupId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration
	 * @throws NoSuchConfigurationException if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration findByG_T_S_Last(
			long groupId, int type, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Returns the last export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching export import configuration, or <code>null</code> if a matching export import configuration could not be found
	 */
	public ExportImportConfiguration fetchByG_T_S_Last(
		long groupId, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns the export import configurations before and after the current export import configuration in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param exportImportConfigurationId the primary key of the current export import configuration
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration[] findByG_T_S_PrevAndNext(
			long exportImportConfigurationId, long groupId, int type,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<ExportImportConfiguration> orderByComparator)
		throws NoSuchConfigurationException;

	/**
	 * Removes all the export import configurations where groupId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 */
	public void removeByG_T_S(long groupId, int type, int status);

	/**
	 * Returns the number of export import configurations where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching export import configurations
	 */
	public int countByG_T_S(long groupId, int type, int status);

	/**
	 * Caches the export import configuration in the entity cache if it is enabled.
	 *
	 * @param exportImportConfiguration the export import configuration
	 */
	public void cacheResult(
		ExportImportConfiguration exportImportConfiguration);

	/**
	 * Caches the export import configurations in the entity cache if it is enabled.
	 *
	 * @param exportImportConfigurations the export import configurations
	 */
	public void cacheResult(
		java.util.List<ExportImportConfiguration> exportImportConfigurations);

	/**
	 * Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	 *
	 * @param exportImportConfigurationId the primary key for the new export import configuration
	 * @return the new export import configuration
	 */
	public ExportImportConfiguration create(long exportImportConfigurationId);

	/**
	 * Removes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration that was removed
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration remove(long exportImportConfigurationId)
		throws NoSuchConfigurationException;

	public ExportImportConfiguration updateImpl(
		ExportImportConfiguration exportImportConfiguration);

	/**
	 * Returns the export import configuration with the primary key or throws a <code>NoSuchConfigurationException</code> if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration
	 * @throws NoSuchConfigurationException if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration findByPrimaryKey(
			long exportImportConfigurationId)
		throws NoSuchConfigurationException;

	/**
	 * Returns the export import configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param exportImportConfigurationId the primary key of the export import configuration
	 * @return the export import configuration, or <code>null</code> if a export import configuration with the primary key could not be found
	 */
	public ExportImportConfiguration fetchByPrimaryKey(
		long exportImportConfigurationId);

	/**
	 * Returns all the export import configurations.
	 *
	 * @return the export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findAll();

	/**
	 * Returns a range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @return the range of export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator);

	/**
	 * Returns an ordered range of all the export import configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ExportImportConfigurationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of export import configurations
	 * @param end the upper bound of the range of export import configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of export import configurations
	 */
	public java.util.List<ExportImportConfiguration> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ExportImportConfiguration> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the export import configurations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of export import configurations.
	 *
	 * @return the number of export import configurations
	 */
	public int countAll();

}