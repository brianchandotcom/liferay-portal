/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.service.persistence.impl;

import com.liferay.asset.category.property.exception.DuplicateAssetCategoryPropertyExternalReferenceCodeException;
import com.liferay.asset.category.property.exception.NoSuchCategoryPropertyException;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.model.AssetCategoryPropertyTable;
import com.liferay.asset.category.property.model.impl.AssetCategoryPropertyImpl;
import com.liferay.asset.category.property.model.impl.AssetCategoryPropertyModelImpl;
import com.liferay.asset.category.property.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.asset.category.property.service.persistence.AssetCategoryPropertyUtil;
import com.liferay.asset.category.property.service.persistence.impl.constants.AssetPersistenceConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the asset category property service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetCategoryPropertyPersistence.class)
public class AssetCategoryPropertyPersistenceImpl
	extends BasePersistenceImpl<AssetCategoryProperty>
	implements AssetCategoryPropertyPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetCategoryPropertyUtil</code> to access the asset category property persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetCategoryPropertyImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByCompanyId;
					finderArgs = new Object[] {companyId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByCompanyId;
				finderArgs = new Object[] {
					companyId, start, end, orderByComparator
				};
			}

			List<AssetCategoryProperty> list = null;

			if (useFinderCache) {
				list = (List<AssetCategoryProperty>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetCategoryProperty assetCategoryProperty : list) {
						if (companyId != assetCategoryProperty.getCompanyId()) {
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

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCompanyId_First(
			long companyId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the asset category properties where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCompanyId(long companyId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			FinderPath finderPath = _finderPathCountByCompanyId;

			Object[] finderArgs = new Object[] {companyId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

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
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"assetCategoryProperty.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCategoryId;
	private FinderPath _finderPathCountByCategoryId;

	/**
	 * Returns all the asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(long categoryId) {
		return findByCategoryId(
			categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end) {

		return findByCategoryId(categoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByCategoryId(
			categoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where categoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param categoryId the category ID
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByCategoryId(
		long categoryId, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByCategoryId;
					finderArgs = new Object[] {categoryId};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByCategoryId;
				finderArgs = new Object[] {
					categoryId, start, end, orderByComparator
				};
			}

			List<AssetCategoryProperty> list = null;

			if (useFinderCache) {
				list = (List<AssetCategoryProperty>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetCategoryProperty assetCategoryProperty : list) {
						if (categoryId !=
								assetCategoryProperty.getCategoryId()) {

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

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(categoryId);

					list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	}

	/**
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCategoryId_First(
			long categoryId,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCategoryId_First(
			categoryId, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("categoryId=");
		sb.append(categoryId);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCategoryId_First(
		long categoryId,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByCategoryId(
			categoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the asset category properties where categoryId = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 */
	@Override
	public void removeByCategoryId(long categoryId) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByCategoryId(
					categoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where categoryId = &#63;.
	 *
	 * @param categoryId the category ID
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCategoryId(long categoryId) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			FinderPath finderPath = _finderPathCountByCategoryId;

			Object[] finderArgs = new Object[] {categoryId};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(2);

				sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_CATEGORYID_CATEGORYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(categoryId);

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
	}

	private static final String _FINDER_COLUMN_CATEGORYID_CATEGORYID_2 =
		"assetCategoryProperty.categoryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_K;
	private FinderPath _finderPathWithoutPaginationFindByC_K;
	private FinderPath _finderPathCountByC_K;

	/**
	 * Returns all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(long companyId, String key) {
		return findByC_K(
			companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end) {

		return findByC_K(companyId, key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findByC_K(companyId, key, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			key = Objects.toString(key, "");

			FinderPath finderPath = null;
			Object[] finderArgs = null;

			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindByC_K;
					finderArgs = new Object[] {companyId, key};
				}
			}
			else if (useFinderCache) {
				finderPath = _finderPathWithPaginationFindByC_K;
				finderArgs = new Object[] {
					companyId, key, start, end, orderByComparator
				};
			}

			List<AssetCategoryProperty> list = null;

			if (useFinderCache) {
				list = (List<AssetCategoryProperty>)finderCache.getResult(
					finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (AssetCategoryProperty assetCategoryProperty : list) {
						if ((companyId !=
								assetCategoryProperty.getCompanyId()) ||
							!key.equals(assetCategoryProperty.getKey())) {

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
						4 + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					sb = new StringBundler(4);
				}

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				boolean bindKey = false;

				if (key.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					bindKey = true;

					sb.append(_FINDER_COLUMN_C_K_KEY_2);
				}

				if (orderByComparator != null) {
					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
				}
				else {
					sb.append(AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindKey) {
						queryPos.add(key);
					}

					list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByC_K_First(
			long companyId, String key,
			OrderByComparator<AssetCategoryProperty> orderByComparator)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByC_K_First(
			companyId, key, orderByComparator);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", key=");
		sb.append(key);

		sb.append("}");

		throw new NoSuchCategoryPropertyException(sb.toString());
	}

	/**
	 * Returns the first asset category property in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByC_K_First(
		long companyId, String key,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		List<AssetCategoryProperty> list = findByC_K(
			companyId, key, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the asset category properties where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 */
	@Override
	public void removeByC_K(long companyId, String key) {
		for (AssetCategoryProperty assetCategoryProperty :
				findByC_K(
					companyId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByC_K(long companyId, String key) {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			key = Objects.toString(key, "");

			FinderPath finderPath = _finderPathCountByC_K;

			Object[] finderArgs = new Object[] {companyId, key};

			Long count = (Long)finderCache.getResult(
				finderPath, finderArgs, this);

			if (count == null) {
				StringBundler sb = new StringBundler(3);

				sb.append(_SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				boolean bindKey = false;

				if (key.isEmpty()) {
					sb.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					bindKey = true;

					sb.append(_FINDER_COLUMN_C_K_KEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(companyId);

					if (bindKey) {
						queryPos.add(key);
					}

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
	}

	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 =
		"assetCategoryProperty.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_K_KEY_2 =
		"assetCategoryProperty.key = ?";

	private static final String _FINDER_COLUMN_C_K_KEY_3 =
		"(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = '')";

	private FinderPath _finderPathFetchByCA_K;

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or throws a <code>NoSuchCategoryPropertyException</code> if it could not be found.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByCA_K(
			categoryId, key);

		if (assetCategoryProperty == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("categoryId=");
			sb.append(categoryId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCategoryPropertyException(sb.toString());
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCA_K(long categoryId, String key) {
		return fetchByCA_K(categoryId, key, true);
	}

	/**
	 * Returns the asset category property where categoryId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByCA_K(
		long categoryId, String key, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			key = Objects.toString(key, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {categoryId, key};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByCA_K, finderArgs, this);
			}

			if (result instanceof AssetCategoryProperty) {
				AssetCategoryProperty assetCategoryProperty =
					(AssetCategoryProperty)result;

				if ((categoryId != assetCategoryProperty.getCategoryId()) ||
					!Objects.equals(key, assetCategoryProperty.getKey())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				sb.append(_FINDER_COLUMN_CA_K_CATEGORYID_2);

				boolean bindKey = false;

				if (key.isEmpty()) {
					sb.append(_FINDER_COLUMN_CA_K_KEY_3);
				}
				else {
					bindKey = true;

					sb.append(_FINDER_COLUMN_CA_K_KEY_2);
				}

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					queryPos.add(categoryId);

					if (bindKey) {
						queryPos.add(key);
					}

					List<AssetCategoryProperty> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByCA_K, finderArgs, list);
						}
					}
					else {
						AssetCategoryProperty assetCategoryProperty = list.get(
							0);

						result = assetCategoryProperty;

						cacheResult(assetCategoryProperty);
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
				return (AssetCategoryProperty)result;
			}
		}
	}

	/**
	 * Removes the asset category property where categoryId = &#63; and key = &#63; from the database.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the asset category property that was removed
	 */
	@Override
	public AssetCategoryProperty removeByCA_K(long categoryId, String key)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = findByCA_K(
			categoryId, key);

		return remove(assetCategoryProperty);
	}

	/**
	 * Returns the number of asset category properties where categoryId = &#63; and key = &#63;.
	 *
	 * @param categoryId the category ID
	 * @param key the key
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByCA_K(long categoryId, String key) {
		AssetCategoryProperty assetCategoryProperty = fetchByCA_K(
			categoryId, key);

		if (assetCategoryProperty == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_CA_K_CATEGORYID_2 =
		"assetCategoryProperty.categoryId = ? AND ";

	private static final String _FINDER_COLUMN_CA_K_KEY_2 =
		"assetCategoryProperty.key = ?";

	private static final String _FINDER_COLUMN_CA_K_KEY_3 =
		"(assetCategoryProperty.key IS NULL OR assetCategoryProperty.key = '')";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the asset category property where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchCategoryPropertyException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching asset category property
	 * @throws NoSuchCategoryPropertyException if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByERC_C(
			externalReferenceCode, companyId);

		if (assetCategoryProperty == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCategoryPropertyException(sb.toString());
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the asset category property where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset category property, or <code>null</code> if a matching asset category property could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			externalReferenceCode = Objects.toString(externalReferenceCode, "");

			Object[] finderArgs = null;

			if (useFinderCache) {
				finderArgs = new Object[] {externalReferenceCode, companyId};
			}

			Object result = null;

			if (useFinderCache) {
				result = finderCache.getResult(
					_finderPathFetchByERC_C, finderArgs, this);
			}

			if (result instanceof AssetCategoryProperty) {
				AssetCategoryProperty assetCategoryProperty =
					(AssetCategoryProperty)result;

				if (!Objects.equals(
						externalReferenceCode,
						assetCategoryProperty.getExternalReferenceCode()) ||
					(companyId != assetCategoryProperty.getCompanyId())) {

					result = null;
				}
			}

			if (result == null) {
				StringBundler sb = new StringBundler(4);

				sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE);

				boolean bindExternalReferenceCode = false;

				if (externalReferenceCode.isEmpty()) {
					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
				}
				else {
					bindExternalReferenceCode = true;

					sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
				}

				sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

				String sql = sb.toString();

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					QueryPos queryPos = QueryPos.getInstance(query);

					if (bindExternalReferenceCode) {
						queryPos.add(externalReferenceCode);
					}

					queryPos.add(companyId);

					List<AssetCategoryProperty> list = query.list();

					if (list.isEmpty()) {
						if (useFinderCache) {
							finderCache.putResult(
								_finderPathFetchByERC_C, finderArgs, list);
						}
					}
					else {
						AssetCategoryProperty assetCategoryProperty = list.get(
							0);

						result = assetCategoryProperty;

						cacheResult(assetCategoryProperty);
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
				return (AssetCategoryProperty)result;
			}
		}
	}

	/**
	 * Removes the asset category property where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the asset category property that was removed
	 */
	@Override
	public AssetCategoryProperty removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = findByERC_C(
			externalReferenceCode, companyId);

		return remove(assetCategoryProperty);
	}

	/**
	 * Returns the number of asset category properties where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching asset category properties
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		AssetCategoryProperty assetCategoryProperty = fetchByERC_C(
			externalReferenceCode, companyId);

		if (assetCategoryProperty == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"assetCategoryProperty.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(assetCategoryProperty.externalReferenceCode IS NULL OR assetCategoryProperty.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"assetCategoryProperty.companyId = ?";

	public AssetCategoryPropertyPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetCategoryProperty.class);

		setModelImplClass(AssetCategoryPropertyImpl.class);
		setModelPKClass(long.class);

		setTable(AssetCategoryPropertyTable.INSTANCE);
	}

	/**
	 * Caches the asset category property in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperty the asset category property
	 */
	@Override
	public void cacheResult(AssetCategoryProperty assetCategoryProperty) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetCategoryProperty.getCtCollectionId())) {

			entityCache.putResult(
				AssetCategoryPropertyImpl.class,
				assetCategoryProperty.getPrimaryKey(), assetCategoryProperty);

			finderCache.putResult(
				_finderPathFetchByCA_K,
				new Object[] {
					assetCategoryProperty.getCategoryId(),
					assetCategoryProperty.getKey()
				},
				assetCategoryProperty);

			finderCache.putResult(
				_finderPathFetchByERC_C,
				new Object[] {
					assetCategoryProperty.getExternalReferenceCode(),
					assetCategoryProperty.getCompanyId()
				},
				assetCategoryProperty);
		}
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the asset category properties in the entity cache if it is enabled.
	 *
	 * @param assetCategoryProperties the asset category properties
	 */
	@Override
	public void cacheResult(
		List<AssetCategoryProperty> assetCategoryProperties) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (assetCategoryProperties.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						assetCategoryProperty.getCtCollectionId())) {

				if (entityCache.getResult(
						AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKey()) == null) {

					cacheResult(assetCategoryProperty);
				}
			}
		}
	}

	/**
	 * Clears the cache for all asset category properties.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetCategoryPropertyImpl.class);

		finderCache.clearCache(AssetCategoryPropertyImpl.class);
	}

	/**
	 * Clears the cache for the asset category property.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetCategoryProperty assetCategoryProperty) {
		entityCache.removeResult(
			AssetCategoryPropertyImpl.class, assetCategoryProperty);
	}

	@Override
	public void clearCache(
		List<AssetCategoryProperty> assetCategoryProperties) {

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			entityCache.removeResult(
				AssetCategoryPropertyImpl.class, assetCategoryProperty);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AssetCategoryPropertyImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AssetCategoryPropertyImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl) {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					assetCategoryPropertyModelImpl.getCtCollectionId())) {

			Object[] args = new Object[] {
				assetCategoryPropertyModelImpl.getCategoryId(),
				assetCategoryPropertyModelImpl.getKey()
			};

			finderCache.putResult(
				_finderPathFetchByCA_K, args, assetCategoryPropertyModelImpl);

			args = new Object[] {
				assetCategoryPropertyModelImpl.getExternalReferenceCode(),
				assetCategoryPropertyModelImpl.getCompanyId()
			};

			finderCache.putResult(
				_finderPathFetchByERC_C, args, assetCategoryPropertyModelImpl);
		}
	}

	/**
	 * Creates a new asset category property with the primary key. Does not add the asset category property to the database.
	 *
	 * @param categoryPropertyId the primary key for the new asset category property
	 * @return the new asset category property
	 */
	@Override
	public AssetCategoryProperty create(long categoryPropertyId) {
		AssetCategoryProperty assetCategoryProperty =
			new AssetCategoryPropertyImpl();

		assetCategoryProperty.setNew(true);
		assetCategoryProperty.setPrimaryKey(categoryPropertyId);

		assetCategoryProperty.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetCategoryProperty;
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty remove(long categoryPropertyId)
		throws NoSuchCategoryPropertyException {

		return remove((Serializable)categoryPropertyId);
	}

	/**
	 * Removes the asset category property with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property that was removed
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty remove(Serializable primaryKey)
		throws NoSuchCategoryPropertyException {

		Session session = null;

		try {
			session = openSession();

			AssetCategoryProperty assetCategoryProperty =
				(AssetCategoryProperty)session.get(
					AssetCategoryPropertyImpl.class, primaryKey);

			if (assetCategoryProperty == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCategoryPropertyException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetCategoryProperty);
		}
		catch (NoSuchCategoryPropertyException noSuchEntityException) {
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
	protected AssetCategoryProperty removeImpl(
		AssetCategoryProperty assetCategoryProperty) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetCategoryProperty)) {
				assetCategoryProperty = (AssetCategoryProperty)session.get(
					AssetCategoryPropertyImpl.class,
					assetCategoryProperty.getPrimaryKeyObj());
			}

			if ((assetCategoryProperty != null) &&
				ctPersistenceHelper.isRemove(assetCategoryProperty)) {

				session.delete(assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetCategoryProperty != null) {
			clearCache(assetCategoryProperty);
		}

		return assetCategoryProperty;
	}

	@Override
	public AssetCategoryProperty updateImpl(
		AssetCategoryProperty assetCategoryProperty) {

		boolean isNew = assetCategoryProperty.isNew();

		if (!(assetCategoryProperty instanceof
				AssetCategoryPropertyModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetCategoryProperty.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetCategoryProperty);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetCategoryProperty proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetCategoryProperty implementation " +
					assetCategoryProperty.getClass());
		}

		AssetCategoryPropertyModelImpl assetCategoryPropertyModelImpl =
			(AssetCategoryPropertyModelImpl)assetCategoryProperty;

		if (Validator.isNull(
				assetCategoryProperty.getExternalReferenceCode())) {

			assetCategoryProperty.setExternalReferenceCode(
				String.valueOf(assetCategoryProperty.getPrimaryKey()));
		}
		else {
			if (!Objects.equals(
					assetCategoryPropertyModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					assetCategoryProperty.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = assetCategoryProperty.getCompanyId();

					long groupId = 0;

					long classPK = 0;

					if (!isNew) {
						classPK = assetCategoryProperty.getPrimaryKey();
					}

					try {
						assetCategoryProperty.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								AssetCategoryProperty.class.getName(), classPK,
								ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
								assetCategoryProperty.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			AssetCategoryProperty ercAssetCategoryProperty = fetchByERC_C(
				assetCategoryProperty.getExternalReferenceCode(),
				assetCategoryProperty.getCompanyId());

			if (isNew) {
				if (ercAssetCategoryProperty != null) {
					throw new DuplicateAssetCategoryPropertyExternalReferenceCodeException(
						"Duplicate asset category property with external reference code " +
							assetCategoryProperty.getExternalReferenceCode() +
								" and company " +
									assetCategoryProperty.getCompanyId());
				}
			}
			else {
				if ((ercAssetCategoryProperty != null) &&
					(assetCategoryProperty.getCategoryPropertyId() !=
						ercAssetCategoryProperty.getCategoryPropertyId())) {

					throw new DuplicateAssetCategoryPropertyExternalReferenceCodeException(
						"Duplicate asset category property with external reference code " +
							assetCategoryProperty.getExternalReferenceCode() +
								" and company " +
									assetCategoryProperty.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (assetCategoryProperty.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetCategoryProperty.setCreateDate(date);
			}
			else {
				assetCategoryProperty.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!assetCategoryPropertyModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetCategoryProperty.setModifiedDate(date);
			}
			else {
				assetCategoryProperty.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetCategoryProperty)) {
				if (!isNew) {
					session.evict(
						AssetCategoryPropertyImpl.class,
						assetCategoryProperty.getPrimaryKeyObj());
				}

				session.save(assetCategoryProperty);
			}
			else {
				assetCategoryProperty = (AssetCategoryProperty)session.merge(
					assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AssetCategoryPropertyImpl.class, assetCategoryPropertyModelImpl,
			false, true);

		cacheUniqueFindersCache(assetCategoryPropertyModelImpl);

		if (isNew) {
			assetCategoryProperty.setNew(false);
		}

		assetCategoryProperty.resetOriginalValues();

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCategoryPropertyException {

		AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(
			primaryKey);

		if (assetCategoryProperty == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCategoryPropertyException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or throws a <code>NoSuchCategoryPropertyException</code> if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property
	 * @throws NoSuchCategoryPropertyException if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty findByPrimaryKey(long categoryPropertyId)
		throws NoSuchCategoryPropertyException {

		return findByPrimaryKey((Serializable)categoryPropertyId);
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				AssetCategoryProperty.class, primaryKey)) {

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKey(primaryKey);
			}
		}

		AssetCategoryProperty assetCategoryProperty =
			(AssetCategoryProperty)entityCache.getResult(
				AssetCategoryPropertyImpl.class, primaryKey);

		if (assetCategoryProperty != null) {
			return assetCategoryProperty;
		}

		Session session = null;

		try {
			session = openSession();

			assetCategoryProperty = (AssetCategoryProperty)session.get(
				AssetCategoryPropertyImpl.class, primaryKey);

			if (assetCategoryProperty != null) {
				cacheResult(assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetCategoryProperty;
	}

	/**
	 * Returns the asset category property with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param categoryPropertyId the primary key of the asset category property
	 * @return the asset category property, or <code>null</code> if a asset category property with the primary key could not be found
	 */
	@Override
	public AssetCategoryProperty fetchByPrimaryKey(long categoryPropertyId) {
		return fetchByPrimaryKey((Serializable)categoryPropertyId);
	}

	@Override
	public Map<Serializable, AssetCategoryProperty> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetCategoryProperty.class)) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				return super.fetchByPrimaryKeys(primaryKeys);
			}
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetCategoryProperty> map =
			new HashMap<Serializable, AssetCategoryProperty>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetCategoryProperty assetCategoryProperty = fetchByPrimaryKey(
				primaryKey);

			if (assetCategoryProperty != null) {
				map.put(primaryKey, assetCategoryProperty);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			try (SafeCloseable safeCloseable =
					ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
						AssetCategoryProperty.class, primaryKey)) {

				AssetCategoryProperty assetCategoryProperty =
					(AssetCategoryProperty)entityCache.getResult(
						AssetCategoryPropertyImpl.class, primaryKey);

				if (assetCategoryProperty == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, assetCategoryProperty);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (AssetCategoryProperty assetCategoryProperty :
					(List<AssetCategoryProperty>)query.list()) {

				map.put(
					assetCategoryProperty.getPrimaryKeyObj(),
					assetCategoryProperty);

				cacheResult(assetCategoryProperty);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset category properties.
	 *
	 * @return the asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @return the range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset category properties.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetCategoryPropertyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset category properties
	 * @param end the upper bound of the range of asset category properties (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset category properties
	 */
	@Override
	public List<AssetCategoryProperty> findAll(
		int start, int end,
		OrderByComparator<AssetCategoryProperty> orderByComparator,
		boolean useFinderCache) {

		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

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

			List<AssetCategoryProperty> list = null;

			if (useFinderCache) {
				list = (List<AssetCategoryProperty>)finderCache.getResult(
					finderPath, finderArgs, this);
			}

			if (list == null) {
				StringBundler sb = null;
				String sql = null;

				if (orderByComparator != null) {
					sb = new StringBundler(
						2 + (orderByComparator.getOrderByFields().length * 2));

					sb.append(_SQL_SELECT_ASSETCATEGORYPROPERTY);

					appendOrderByComparator(
						sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

					sql = sb.toString();
				}
				else {
					sql = _SQL_SELECT_ASSETCATEGORYPROPERTY;

					sql = sql.concat(
						AssetCategoryPropertyModelImpl.ORDER_BY_JPQL);
				}

				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(sql);

					list = (List<AssetCategoryProperty>)QueryUtil.list(
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
	}

	/**
	 * Removes all the asset category properties from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetCategoryProperty assetCategoryProperty : findAll()) {
			remove(assetCategoryProperty);
		}
	}

	/**
	 * Returns the number of asset category properties.
	 *
	 * @return the number of asset category properties
	 */
	@Override
	public int countAll() {
		try (SafeCloseable safeCloseable =
				ctPersistenceHelper.setCTCollectionIdWithSafeCloseable(
					AssetCategoryProperty.class)) {

			Long count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);

			if (count == null) {
				Session session = null;

				try {
					session = openSession();

					Query query = session.createQuery(
						_SQL_COUNT_ASSETCATEGORYPROPERTY);

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
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "categoryPropertyId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETCATEGORYPROPERTY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return AssetCategoryPropertyModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetCategoryProperty";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("categoryId");
		ctMergeColumnNames.add("key_");
		ctMergeColumnNames.add("value");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("categoryPropertyId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"categoryId", "key_"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the asset category property persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"categoryId"}, true);

		_finderPathWithoutPaginationFindByCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCategoryId",
			new String[] {Long.class.getName()}, new String[] {"categoryId"},
			true);

		_finderPathCountByCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCategoryId",
			new String[] {Long.class.getName()}, new String[] {"categoryId"},
			false);

		_finderPathWithPaginationFindByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "key_"}, true);

		_finderPathWithoutPaginationFindByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, true);

		_finderPathCountByC_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "key_"}, false);

		_finderPathFetchByCA_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCA_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"categoryId", "key_"}, true);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		AssetCategoryPropertyUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		AssetCategoryPropertyUtil.setPersistence(null);

		entityCache.removeCache(AssetCategoryPropertyImpl.class.getName());
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY =
		"SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty";

	private static final String _SQL_SELECT_ASSETCATEGORYPROPERTY_WHERE =
		"SELECT assetCategoryProperty FROM AssetCategoryProperty assetCategoryProperty WHERE ";

	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY =
		"SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty";

	private static final String _SQL_COUNT_ASSETCATEGORYPROPERTY_WHERE =
		"SELECT COUNT(assetCategoryProperty) FROM AssetCategoryProperty assetCategoryProperty WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"assetCategoryProperty.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetCategoryProperty exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetCategoryProperty exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryPropertyPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"key"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-10675348