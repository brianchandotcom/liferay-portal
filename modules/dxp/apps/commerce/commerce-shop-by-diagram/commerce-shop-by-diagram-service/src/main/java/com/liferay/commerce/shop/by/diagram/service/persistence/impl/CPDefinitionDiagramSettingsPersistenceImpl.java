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

package com.liferay.commerce.shop.by.diagram.service.persistence.impl;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingsException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettingsTable;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsImpl;
import com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingsPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the cp definition diagram settings service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDefinitionDiagramSettingsPersistenceImpl
	extends BasePersistenceImpl<CPDefinitionDiagramSettings>
	implements CPDefinitionDiagramSettingsPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionDiagramSettingsUtil</code> to access the cp definition diagram settings persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionDiagramSettingsImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByCPDefinitionId;
	private FinderPath _finderPathCountByCPDefinitionId;

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			fetchByCPDefinitionId(CPDefinitionId);

		if (cpDefinitionDiagramSettings == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CPDefinitionId=");
			sb.append(CPDefinitionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionDiagramSettingsException(sb.toString());
		}

		return cpDefinitionDiagramSettings;
	}

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId) {

		return fetchByCPDefinitionId(CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition diagram settings where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CPDefinitionId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCPDefinitionId, finderArgs);
		}

		if (result instanceof CPDefinitionDiagramSettings) {
			CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
				(CPDefinitionDiagramSettings)result;

			if (CPDefinitionId !=
					cpDefinitionDiagramSettings.getCPDefinitionId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				List<CPDefinitionDiagramSettings> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCPDefinitionId, finderArgs, list);
					}
				}
				else {
					CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
						list.get(0);

					result = cpDefinitionDiagramSettings;

					cacheResult(cpDefinitionDiagramSettings);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPDefinitionDiagramSettings)result;
		}
	}

	/**
	 * Removes the cp definition diagram settings where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram settings that was removed
	 */
	@Override
	public CPDefinitionDiagramSettings removeByCPDefinitionId(
			long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			findByCPDefinitionId(CPDefinitionId);

		return remove(cpDefinitionDiagramSettings);
	}

	/**
	 * Returns the number of cp definition diagram settingses where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settingses
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = _finderPathCountByCPDefinitionId;

		Object[] finderArgs = new Object[] {CPDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMSETTINGS_WHERE);

			sb.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPDefinitionId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 =
		"cpDefinitionDiagramSettings.CPDefinitionId = ?";

	private FinderPath _finderPathWithPaginationFindByAssetCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByAssetCategoryId;
	private FinderPath _finderPathCountByAssetCategoryId;

	/**
	 * Returns all the cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the matching cp definition diagram settingses
	 */
	@Override
	public List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId) {

		return findByAssetCategoryId(
			assetCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end) {

		return findByAssetCategoryId(assetCategoryId, start, end, null);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return findByAssetCategoryId(
			assetCategoryId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findByAssetCategoryId(
		long assetCategoryId, int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAssetCategoryId;
				finderArgs = new Object[] {assetCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssetCategoryId;
			finderArgs = new Object[] {
				assetCategoryId, start, end, orderByComparator
			};
		}

		List<CPDefinitionDiagramSettings> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramSettings>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
						list) {

					if (assetCategoryId !=
							cpDefinitionDiagramSettings.getAssetCategoryId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS_WHERE);

			sb.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionDiagramSettingsModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetCategoryId);

				list = (List<CPDefinitionDiagramSettings>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings findByAssetCategoryId_First(
			long assetCategoryId,
			OrderByComparator<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			fetchByAssetCategoryId_First(assetCategoryId, orderByComparator);

		if (cpDefinitionDiagramSettings != null) {
			return cpDefinitionDiagramSettings;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetCategoryId=");
		sb.append(assetCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingsException(sb.toString());
	}

	/**
	 * Returns the first cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings fetchByAssetCategoryId_First(
		long assetCategoryId,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		List<CPDefinitionDiagramSettings> list = findByAssetCategoryId(
			assetCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings findByAssetCategoryId_Last(
			long assetCategoryId,
			OrderByComparator<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			fetchByAssetCategoryId_Last(assetCategoryId, orderByComparator);

		if (cpDefinitionDiagramSettings != null) {
			return cpDefinitionDiagramSettings;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assetCategoryId=");
		sb.append(assetCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionDiagramSettingsException(sb.toString());
	}

	/**
	 * Returns the last cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram settings, or <code>null</code> if a matching cp definition diagram settings could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings fetchByAssetCategoryId_Last(
		long assetCategoryId,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		int count = countByAssetCategoryId(assetCategoryId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionDiagramSettings> list = findByAssetCategoryId(
			assetCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition diagram settingses before and after the current cp definition diagram settings in the ordered set where assetCategoryId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the current cp definition diagram settings
	 * @param assetCategoryId the asset category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings[] findByAssetCategoryId_PrevAndNext(
			long CPDefinitionDiagramSettingsId, long assetCategoryId,
			OrderByComparator<CPDefinitionDiagramSettings> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			findByPrimaryKey(CPDefinitionDiagramSettingsId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramSettings[] array =
				new CPDefinitionDiagramSettingsImpl[3];

			array[0] = getByAssetCategoryId_PrevAndNext(
				session, cpDefinitionDiagramSettings, assetCategoryId,
				orderByComparator, true);

			array[1] = cpDefinitionDiagramSettings;

			array[2] = getByAssetCategoryId_PrevAndNext(
				session, cpDefinitionDiagramSettings, assetCategoryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionDiagramSettings getByAssetCategoryId_PrevAndNext(
		Session session,
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings,
		long assetCategoryId,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS_WHERE);

		sb.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionDiagramSettingsModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(assetCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpDefinitionDiagramSettings)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinitionDiagramSettings> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition diagram settingses where assetCategoryId = &#63; from the database.
	 *
	 * @param assetCategoryId the asset category ID
	 */
	@Override
	public void removeByAssetCategoryId(long assetCategoryId) {
		for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
				findByAssetCategoryId(
					assetCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinitionDiagramSettings);
		}
	}

	/**
	 * Returns the number of cp definition diagram settingses where assetCategoryId = &#63;.
	 *
	 * @param assetCategoryId the asset category ID
	 * @return the number of matching cp definition diagram settingses
	 */
	@Override
	public int countByAssetCategoryId(long assetCategoryId) {
		FinderPath finderPath = _finderPathCountByAssetCategoryId;

		Object[] finderArgs = new Object[] {assetCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITIONDIAGRAMSETTINGS_WHERE);

			sb.append(_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(assetCategoryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_ASSETCATEGORYID_ASSETCATEGORYID_2 =
			"cpDefinitionDiagramSettings.assetCategoryId = ?";

	public CPDefinitionDiagramSettingsPersistenceImpl() {
		setModelClass(CPDefinitionDiagramSettings.class);

		setModelImplClass(CPDefinitionDiagramSettingsImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionDiagramSettingsTable.INSTANCE);
	}

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	@Override
	public void cacheResult(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		entityCache.putResult(
			CPDefinitionDiagramSettingsImpl.class,
			cpDefinitionDiagramSettings.getPrimaryKey(),
			cpDefinitionDiagramSettings);

		finderCache.putResult(
			_finderPathFetchByCPDefinitionId,
			new Object[] {cpDefinitionDiagramSettings.getCPDefinitionId()},
			cpDefinitionDiagramSettings);
	}

	/**
	 * Caches the cp definition diagram settingses in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettingses the cp definition diagram settingses
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionDiagramSettings> cpDefinitionDiagramSettingses) {

		for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
				cpDefinitionDiagramSettingses) {

			if (entityCache.getResult(
					CPDefinitionDiagramSettingsImpl.class,
					cpDefinitionDiagramSettings.getPrimaryKey()) == null) {

				cacheResult(cpDefinitionDiagramSettings);
			}
		}
	}

	/**
	 * Clears the cache for all cp definition diagram settingses.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionDiagramSettingsImpl.class);

		finderCache.clearCache(CPDefinitionDiagramSettingsImpl.class);
	}

	/**
	 * Clears the cache for the cp definition diagram settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		entityCache.removeResult(
			CPDefinitionDiagramSettingsImpl.class, cpDefinitionDiagramSettings);
	}

	@Override
	public void clearCache(
		List<CPDefinitionDiagramSettings> cpDefinitionDiagramSettingses) {

		for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
				cpDefinitionDiagramSettingses) {

			entityCache.removeResult(
				CPDefinitionDiagramSettingsImpl.class,
				cpDefinitionDiagramSettings);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionDiagramSettingsImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDefinitionDiagramSettingsImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionDiagramSettingsModelImpl
			cpDefinitionDiagramSettingsModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionDiagramSettingsModelImpl.getCPDefinitionId()
		};

		finderCache.putResult(
			_finderPathCountByCPDefinitionId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPDefinitionId, args,
			cpDefinitionDiagramSettingsModelImpl);
	}

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	@Override
	public CPDefinitionDiagramSettings create(
		long CPDefinitionDiagramSettingsId) {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			new CPDefinitionDiagramSettingsImpl();

		cpDefinitionDiagramSettings.setNew(true);
		cpDefinitionDiagramSettings.setPrimaryKey(
			CPDefinitionDiagramSettingsId);

		cpDefinitionDiagramSettings.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return cpDefinitionDiagramSettings;
	}

	/**
	 * Removes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings remove(
			long CPDefinitionDiagramSettingsId)
		throws NoSuchCPDefinitionDiagramSettingsException {

		return remove((Serializable)CPDefinitionDiagramSettingsId);
	}

	/**
	 * Removes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings remove(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramSettingsException {

		Session session = null;

		try {
			session = openSession();

			CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
				(CPDefinitionDiagramSettings)session.get(
					CPDefinitionDiagramSettingsImpl.class, primaryKey);

			if (cpDefinitionDiagramSettings == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionDiagramSettingsException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinitionDiagramSettings);
		}
		catch (NoSuchCPDefinitionDiagramSettingsException
					noSuchEntityException) {

			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CPDefinitionDiagramSettings removeImpl(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionDiagramSettings)) {
				cpDefinitionDiagramSettings =
					(CPDefinitionDiagramSettings)session.get(
						CPDefinitionDiagramSettingsImpl.class,
						cpDefinitionDiagramSettings.getPrimaryKeyObj());
			}

			if (cpDefinitionDiagramSettings != null) {
				session.delete(cpDefinitionDiagramSettings);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionDiagramSettings != null) {
			clearCache(cpDefinitionDiagramSettings);
		}

		return cpDefinitionDiagramSettings;
	}

	@Override
	public CPDefinitionDiagramSettings updateImpl(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		boolean isNew = cpDefinitionDiagramSettings.isNew();

		if (!(cpDefinitionDiagramSettings instanceof
				CPDefinitionDiagramSettingsModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					cpDefinitionDiagramSettings.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinitionDiagramSettings);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinitionDiagramSettings proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinitionDiagramSettings implementation " +
					cpDefinitionDiagramSettings.getClass());
		}

		CPDefinitionDiagramSettingsModelImpl
			cpDefinitionDiagramSettingsModelImpl =
				(CPDefinitionDiagramSettingsModelImpl)
					cpDefinitionDiagramSettings;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionDiagramSettings.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionDiagramSettings.setCreateDate(now);
			}
			else {
				cpDefinitionDiagramSettings.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!cpDefinitionDiagramSettingsModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionDiagramSettings.setModifiedDate(now);
			}
			else {
				cpDefinitionDiagramSettings.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinitionDiagramSettings);
			}
			else {
				cpDefinitionDiagramSettings =
					(CPDefinitionDiagramSettings)session.merge(
						cpDefinitionDiagramSettings);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionDiagramSettingsImpl.class,
			cpDefinitionDiagramSettingsModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionDiagramSettingsModelImpl);

		if (isNew) {
			cpDefinitionDiagramSettings.setNew(false);
		}

		cpDefinitionDiagramSettings.resetOriginalValues();

		return cpDefinitionDiagramSettings;
	}

	/**
	 * Returns the cp definition diagram settings with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionDiagramSettingsException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			fetchByPrimaryKey(primaryKey);

		if (cpDefinitionDiagramSettings == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionDiagramSettingsException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinitionDiagramSettings;
	}

	/**
	 * Returns the cp definition diagram settings with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingsException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws NoSuchCPDefinitionDiagramSettingsException if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings findByPrimaryKey(
			long CPDefinitionDiagramSettingsId)
		throws NoSuchCPDefinitionDiagramSettingsException {

		return findByPrimaryKey((Serializable)CPDefinitionDiagramSettingsId);
	}

	/**
	 * Returns the cp definition diagram settings with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings, or <code>null</code> if a cp definition diagram settings with the primary key could not be found
	 */
	@Override
	public CPDefinitionDiagramSettings fetchByPrimaryKey(
		long CPDefinitionDiagramSettingsId) {

		return fetchByPrimaryKey((Serializable)CPDefinitionDiagramSettingsId);
	}

	/**
	 * Returns all the cp definition diagram settingses.
	 *
	 * @return the cp definition diagram settingses
	 */
	@Override
	public List<CPDefinitionDiagramSettings> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<CPDefinitionDiagramSettings> findAll(
		int start, int end,
		OrderByComparator<CPDefinitionDiagramSettings> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CPDefinitionDiagramSettings> list = null;

		if (useFinderCache) {
			list = (List<CPDefinitionDiagramSettings>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS;

				sql = sql.concat(
					CPDefinitionDiagramSettingsModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinitionDiagramSettings>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cp definition diagram settingses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
				findAll()) {

			remove(cpDefinitionDiagramSettings);
		}
	}

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_CPDEFINITIONDIAGRAMSETTINGS);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPDefinitionDiagramSettingsId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionDiagramSettingsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition diagram settings persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionDiagramSettingsPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CPDefinitionDiagramSettingsModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, true);

		_finderPathCountByCPDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPDefinitionId",
			new String[] {Long.class.getName()},
			new String[] {"CPDefinitionId"}, false);

		_finderPathWithPaginationFindByAssetCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssetCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"assetCategoryId"}, true);

		_finderPathWithoutPaginationFindByAssetCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"assetCategoryId"}, true);

		_finderPathCountByAssetCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"assetCategoryId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CPDefinitionDiagramSettingsImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS =
		"SELECT cpDefinitionDiagramSettings FROM CPDefinitionDiagramSettings cpDefinitionDiagramSettings";

	private static final String _SQL_SELECT_CPDEFINITIONDIAGRAMSETTINGS_WHERE =
		"SELECT cpDefinitionDiagramSettings FROM CPDefinitionDiagramSettings cpDefinitionDiagramSettings WHERE ";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMSETTINGS =
		"SELECT COUNT(cpDefinitionDiagramSettings) FROM CPDefinitionDiagramSettings cpDefinitionDiagramSettings";

	private static final String _SQL_COUNT_CPDEFINITIONDIAGRAMSETTINGS_WHERE =
		"SELECT COUNT(cpDefinitionDiagramSettings) FROM CPDefinitionDiagramSettings cpDefinitionDiagramSettings WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpDefinitionDiagramSettings.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinitionDiagramSettings exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinitionDiagramSettings exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDiagramSettingsPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CPDefinitionDiagramSettingsModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CPDefinitionDiagramSettingsModelImpl
				cpDefinitionDiagramSettingsModelImpl =
					(CPDefinitionDiagramSettingsModelImpl)baseModel;

			long columnBitmask =
				cpDefinitionDiagramSettingsModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					cpDefinitionDiagramSettingsModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						cpDefinitionDiagramSettingsModelImpl.getColumnBitmask(
							columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					cpDefinitionDiagramSettingsModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CPDefinitionDiagramSettingsImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CPDefinitionDiagramSettingsTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			CPDefinitionDiagramSettingsModelImpl
				cpDefinitionDiagramSettingsModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						cpDefinitionDiagramSettingsModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						cpDefinitionDiagramSettingsModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |=
				CPDefinitionDiagramSettingsModelImpl.getColumnBitmask("name");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}