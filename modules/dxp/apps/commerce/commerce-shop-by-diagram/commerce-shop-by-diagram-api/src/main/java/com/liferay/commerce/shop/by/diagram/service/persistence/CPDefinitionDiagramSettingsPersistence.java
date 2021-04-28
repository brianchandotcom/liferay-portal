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

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingsException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp definition diagram settings service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionDiagramSettingsPersistence
	extends BasePersistence<CPDefinitionDiagramSettings> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionDiagramSettingsUtil} to access the cp definition diagram settings persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId);

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache);

	/**
	 * Removes the cp definition diagram settings where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram settings that was removed
	 */
	public CPDefinitionDiagramSettings removeByCPDefinitionId(
			long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Returns the number of cp definition diagram settingses where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settingses
	 */
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId);

	/**
	 * Returns a range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of matching cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param assetCategoryId the asset category ID
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings findByAssetCategoryId_First(
			long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings fetchByAssetCategoryId_First(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator);

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings findByAssetCategoryId_Last(
			long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	public CPDefinitionDiagramSettings fetchByAssetCategoryId_Last(
		long assetCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator);

	/**
	 * Returns the cp definition diagram settingses before and after the current cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the current cp definition diagram settings
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public CPDefinitionDiagramSettings[] findByAssetCategoryId_PrevAndNext(
			long CPDefinitionDiagramSettingsId, long assetCategoryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Removes all the cp definition diagram settingses where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	public void removeByAssetCategoryId(long assetCategoryId);

	/**
	 * Returns the number of cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching cp definition diagram settingses
	 */
	public int countByAssetCategoryId(long assetCategoryId);

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	public void cacheResult(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings);

	/**
	 * Caches the cp definition diagram settingses in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettingses the cp definition diagram settingses
	 */
	public void cacheResult(
		java.util.List<CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses);

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	public CPDefinitionDiagramSettings create(
		long CPDefinitionDiagramSettingsId);

	/**
	 * Removes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public CPDefinitionDiagramSettings remove(
			long CPDefinitionDiagramSettingsId)
		throws NoSuchCPDefinitionDiagramSettingsException;

	public CPDefinitionDiagramSettings updateImpl(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings);

	/**
	 * Returns the cp definition diagram settings with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	public CPDefinitionDiagramSettings findByPrimaryKey(
			long CPDefinitionDiagramSettingsId)
		throws NoSuchCPDefinitionDiagramSettingsException;

	/**
	 * Returns the cp definition diagram settings with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings, or <code>null</code> if a cp definition diagram settings with the primary key could not be found
	 */
	public CPDefinitionDiagramSettings fetchByPrimaryKey(
		long CPDefinitionDiagramSettingsId);

	/**
	 * Returns all the cp definition diagram settingses.
	 *
	 * @return the cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findAll();

	/**
	 * Returns a range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram settingses
	 */
	public java.util.List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp definition diagram settingses from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	public int countAll();

}