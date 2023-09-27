/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectValidationRuleSettingException;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object validation rule setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectValidationRuleSettingUtil
 * @generated
 */
@ProviderType
public interface ObjectValidationRuleSettingPersistence
	extends BasePersistence<ObjectValidationRuleSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectValidationRuleSettingUtil} to access the object validation rule setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting[] findByUuid_PrevAndNext(
			long objectValidationRuleSettingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Removes all the object validation rule settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object validation rule settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object validation rule settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the first object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the last object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting[] findByUuid_C_PrevAndNext(
			long objectValidationRuleSettingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Removes all the object validation rule settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object validation rule settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object validation rule settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(long objectValidationRuleId);

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting>
		findByObjectValidationRuleId(
			long objectValidationRuleId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByObjectValidationRuleId_First(
			long objectValidationRuleId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByObjectValidationRuleId_First(
		long objectValidationRuleId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByObjectValidationRuleId_Last(
			long objectValidationRuleId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByObjectValidationRuleId_Last(
		long objectValidationRuleId,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting[]
			findByObjectValidationRuleId_PrevAndNext(
				long objectValidationRuleSettingId, long objectValidationRuleId,
				com.liferay.portal.kernel.util.OrderByComparator
					<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 */
	public void removeByObjectValidationRuleId(long objectValidationRuleId);

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @return the number of matching object validation rule settings
	 */
	public int countByObjectValidationRuleId(long objectValidationRuleId);

	/**
	 * Returns all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name);

	/**
	 * Returns a range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end);

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findByOVRI_N(
		long objectValidationRuleId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByOVRI_N_First(
			long objectValidationRuleId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the first object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByOVRI_N_First(
		long objectValidationRuleId, String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByOVRI_N_Last(
			long objectValidationRuleId, String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the last object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByOVRI_N_Last(
		long objectValidationRuleId, String name,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns the object validation rule settings before and after the current object validation rule setting in the ordered set where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleSettingId the primary key of the current object validation rule setting
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting[] findByOVRI_N_PrevAndNext(
			long objectValidationRuleSettingId, long objectValidationRuleId,
			String name,
			com.liferay.portal.kernel.util.OrderByComparator
				<ObjectValidationRuleSetting> orderByComparator)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Removes all the object validation rule settings where objectValidationRuleId = &#63; and name = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 */
	public void removeByOVRI_N(long objectValidationRuleId, String name);

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @return the number of matching object validation rule settings
	 */
	public int countByOVRI_N(long objectValidationRuleId, String name);

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting findByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value);

	/**
	 * Returns the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object validation rule setting, or <code>null</code> if a matching object validation rule setting could not be found
	 */
	public ObjectValidationRuleSetting fetchByOVRI_N_V(
		long objectValidationRuleId, String name, String value,
		boolean useFinderCache);

	/**
	 * Removes the object validation rule setting where objectValidationRuleId = &#63; and name = &#63; and value = &#63; from the database.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the object validation rule setting that was removed
	 */
	public ObjectValidationRuleSetting removeByOVRI_N_V(
			long objectValidationRuleId, String name, String value)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the number of object validation rule settings where objectValidationRuleId = &#63; and name = &#63; and value = &#63;.
	 *
	 * @param objectValidationRuleId the object validation rule ID
	 * @param name the name
	 * @param value the value
	 * @return the number of matching object validation rule settings
	 */
	public int countByOVRI_N_V(
		long objectValidationRuleId, String name, String value);

	/**
	 * Caches the object validation rule setting in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSetting the object validation rule setting
	 */
	public void cacheResult(
		ObjectValidationRuleSetting objectValidationRuleSetting);

	/**
	 * Caches the object validation rule settings in the entity cache if it is enabled.
	 *
	 * @param objectValidationRuleSettings the object validation rule settings
	 */
	public void cacheResult(
		java.util.List<ObjectValidationRuleSetting>
			objectValidationRuleSettings);

	/**
	 * Creates a new object validation rule setting with the primary key. Does not add the object validation rule setting to the database.
	 *
	 * @param objectValidationRuleSettingId the primary key for the new object validation rule setting
	 * @return the new object validation rule setting
	 */
	public ObjectValidationRuleSetting create(
		long objectValidationRuleSettingId);

	/**
	 * Removes the object validation rule setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting that was removed
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting remove(
			long objectValidationRuleSettingId)
		throws NoSuchObjectValidationRuleSettingException;

	public ObjectValidationRuleSetting updateImpl(
		ObjectValidationRuleSetting objectValidationRuleSetting);

	/**
	 * Returns the object validation rule setting with the primary key or throws a <code>NoSuchObjectValidationRuleSettingException</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting
	 * @throws NoSuchObjectValidationRuleSettingException if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting findByPrimaryKey(
			long objectValidationRuleSettingId)
		throws NoSuchObjectValidationRuleSettingException;

	/**
	 * Returns the object validation rule setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectValidationRuleSettingId the primary key of the object validation rule setting
	 * @return the object validation rule setting, or <code>null</code> if a object validation rule setting with the primary key could not be found
	 */
	public ObjectValidationRuleSetting fetchByPrimaryKey(
		long objectValidationRuleSettingId);

	/**
	 * Returns all the object validation rule settings.
	 *
	 * @return the object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findAll();

	/**
	 * Returns a range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @return the range of object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the object validation rule settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectValidationRuleSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object validation rule settings
	 * @param end the upper bound of the range of object validation rule settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object validation rule settings
	 */
	public java.util.List<ObjectValidationRuleSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<ObjectValidationRuleSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object validation rule settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object validation rule settings.
	 *
	 * @return the number of object validation rule settings
	 */
	public int countAll();

}