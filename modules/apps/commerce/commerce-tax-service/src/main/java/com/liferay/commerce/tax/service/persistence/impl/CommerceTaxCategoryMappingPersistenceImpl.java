/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.persistence.impl;

import com.liferay.commerce.tax.exception.DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException;
import com.liferay.commerce.tax.exception.NoSuchTaxCategoryMappingException;
import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
import com.liferay.commerce.tax.model.CommerceTaxCategoryMappingTable;
import com.liferay.commerce.tax.model.impl.CommerceTaxCategoryMappingImpl;
import com.liferay.commerce.tax.model.impl.CommerceTaxCategoryMappingModelImpl;
import com.liferay.commerce.tax.service.persistence.CommerceTaxCategoryMappingPersistence;
import com.liferay.commerce.tax.service.persistence.CommerceTaxCategoryMappingUtil;
import com.liferay.commerce.tax.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the commerce tax category mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = CommerceTaxCategoryMappingPersistence.class)
public class CommerceTaxCategoryMappingPersistenceImpl
	extends BasePersistenceImpl<CommerceTaxCategoryMapping>
	implements CommerceTaxCategoryMappingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTaxCategoryMappingUtil</code> to access the commerce tax category mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTaxCategoryMappingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommerceTaxCategoryMapping> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxCategoryMapping>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
						list) {

					if (!uuid.equals(commerceTaxCategoryMapping.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxCategoryMappingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CommerceTaxCategoryMapping>)QueryUtil.list(
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
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByUuid_First(
			String uuid,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		List<CommerceTaxCategoryMapping> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxCategoryMapping> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceTaxCategoryMapping);
		}
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXCATEGORYMAPPING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"commerceTaxCategoryMapping.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceTaxCategoryMapping.uuid IS NULL OR commerceTaxCategoryMapping.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByUUID_G(String uuid, long groupId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByUUID_G(
			uuid, groupId);

		if (commerceTaxCategoryMapping == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaxCategoryMappingException(sb.toString());
		}

		return commerceTaxCategoryMapping;
	}

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof CommerceTaxCategoryMapping) {
			CommerceTaxCategoryMapping commerceTaxCategoryMapping =
				(CommerceTaxCategoryMapping)result;

			if (!Objects.equals(uuid, commerceTaxCategoryMapping.getUuid()) ||
				(groupId != commerceTaxCategoryMapping.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<CommerceTaxCategoryMapping> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CommerceTaxCategoryMapping commerceTaxCategoryMapping =
						list.get(0);

					result = commerceTaxCategoryMapping;

					cacheResult(commerceTaxCategoryMapping);
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
			return (CommerceTaxCategoryMapping)result;
		}
	}

	/**
	 * Removes the commerce tax category mapping where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce tax category mapping that was removed
	 */
	@Override
	public CommerceTaxCategoryMapping removeByUUID_G(String uuid, long groupId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = findByUUID_G(
			uuid, groupId);

		return remove(commerceTaxCategoryMapping);
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByUUID_G(
			uuid, groupId);

		if (commerceTaxCategoryMapping == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"commerceTaxCategoryMapping.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(commerceTaxCategoryMapping.uuid IS NULL OR commerceTaxCategoryMapping.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"commerceTaxCategoryMapping.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommerceTaxCategoryMapping> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxCategoryMapping>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
						list) {

					if (!uuid.equals(commerceTaxCategoryMapping.getUuid()) ||
						(companyId !=
							commerceTaxCategoryMapping.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxCategoryMappingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CommerceTaxCategoryMapping>)QueryUtil.list(
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
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		List<CommerceTaxCategoryMapping> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxCategoryMapping> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTaxCategoryMapping);
		}
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETAXCATEGORYMAPPING_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"commerceTaxCategoryMapping.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceTaxCategoryMapping.uuid IS NULL OR commerceTaxCategoryMapping.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceTaxCategoryMapping.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTaxMethodId;
	private FinderPath _finderPathCountByCommerceTaxMethodId;

	/**
	 * Returns all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end) {

		return findByCommerceTaxMethodId(commerceTaxMethodId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceTaxMethodId;
				finderArgs = new Object[] {commerceTaxMethodId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceTaxMethodId;
			finderArgs = new Object[] {
				commerceTaxMethodId, start, end, orderByComparator
			};
		}

		List<CommerceTaxCategoryMapping> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxCategoryMapping>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
						list) {

					if (commerceTaxMethodId !=
							commerceTaxCategoryMapping.
								getCommerceTaxMethodId()) {

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

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTaxCategoryMappingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

				list = (List<CommerceTaxCategoryMapping>)QueryUtil.list(
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
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByCommerceTaxMethodId_First(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		List<CommerceTaxCategoryMapping> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByCommerceTaxMethodId_Last(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByCommerceTaxMethodId_Last(
				commerceTaxMethodId, orderByComparator);

		if (commerceTaxCategoryMapping != null) {
			return commerceTaxCategoryMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);

		sb.append("}");

		throw new NoSuchTaxCategoryMappingException(sb.toString());
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		int count = countByCommerceTaxMethodId(commerceTaxMethodId);

		if (count == 0) {
			return null;
		}

		List<CommerceTaxCategoryMapping> list = findByCommerceTaxMethodId(
			commerceTaxMethodId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the commerce tax category mappings where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	@Override
	public void removeByCommerceTaxMethodId(long commerceTaxMethodId) {
		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				findByCommerceTaxMethodId(
					commerceTaxMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTaxCategoryMapping);
		}
	}

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByCommerceTaxMethodId(long commerceTaxMethodId) {
		FinderPath finderPath = _finderPathCountByCommerceTaxMethodId;

		Object[] finderArgs = new Object[] {commerceTaxMethodId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETAXCATEGORYMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

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
		_FINDER_COLUMN_COMMERCETAXMETHODID_COMMERCETAXMETHODID_2 =
			"commerceTaxCategoryMapping.commerceTaxMethodId = ?";

	private FinderPath _finderPathFetchByC_C;

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByC_C(
			commerceTaxMethodId, CPTaxCategoryId);

		if (commerceTaxCategoryMapping == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceTaxMethodId=");
			sb.append(commerceTaxMethodId);

			sb.append(", CPTaxCategoryId=");
			sb.append(CPTaxCategoryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaxCategoryMappingException(sb.toString());
		}

		return commerceTaxCategoryMapping;
	}

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId) {

		return fetchByC_C(commerceTaxMethodId, CPTaxCategoryId, true);
	}

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceTaxMethodId, CPTaxCategoryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof CommerceTaxCategoryMapping) {
			CommerceTaxCategoryMapping commerceTaxCategoryMapping =
				(CommerceTaxCategoryMapping)result;

			if ((commerceTaxMethodId !=
					commerceTaxCategoryMapping.getCommerceTaxMethodId()) ||
				(CPTaxCategoryId !=
					commerceTaxCategoryMapping.getCPTaxCategoryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCETAXMETHODID_2);

			sb.append(_FINDER_COLUMN_C_C_CPTAXCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTaxMethodId);

				queryPos.add(CPTaxCategoryId);

				List<CommerceTaxCategoryMapping> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CommerceTaxCategoryMapping commerceTaxCategoryMapping =
						list.get(0);

					result = commerceTaxCategoryMapping;

					cacheResult(commerceTaxCategoryMapping);
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
			return (CommerceTaxCategoryMapping)result;
		}
	}

	/**
	 * Removes the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the commerce tax category mapping that was removed
	 */
	@Override
	public CommerceTaxCategoryMapping removeByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = findByC_C(
			commerceTaxMethodId, CPTaxCategoryId);

		return remove(commerceTaxCategoryMapping);
	}

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByC_C(long commerceTaxMethodId, long CPTaxCategoryId) {
		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByC_C(
			commerceTaxMethodId, CPTaxCategoryId);

		if (commerceTaxCategoryMapping == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_C_C_COMMERCETAXMETHODID_2 =
		"commerceTaxCategoryMapping.commerceTaxMethodId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CPTAXCATEGORYID_2 =
		"commerceTaxCategoryMapping.CPTaxCategoryId = ?";

	private FinderPath _finderPathFetchByERC_C;

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByERC_C(
			externalReferenceCode, companyId);

		if (commerceTaxCategoryMapping == null) {
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

			throw new NoSuchTaxCategoryMappingException(sb.toString());
		}

		return commerceTaxCategoryMapping;
	}

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

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

		if (result instanceof CommerceTaxCategoryMapping) {
			CommerceTaxCategoryMapping commerceTaxCategoryMapping =
				(CommerceTaxCategoryMapping)result;

			if (!Objects.equals(
					externalReferenceCode,
					commerceTaxCategoryMapping.getExternalReferenceCode()) ||
				(companyId != commerceTaxCategoryMapping.getCompanyId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE);

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

				List<CommerceTaxCategoryMapping> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByERC_C, finderArgs, list);
					}
				}
				else {
					CommerceTaxCategoryMapping commerceTaxCategoryMapping =
						list.get(0);

					result = commerceTaxCategoryMapping;

					cacheResult(commerceTaxCategoryMapping);
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
			return (CommerceTaxCategoryMapping)result;
		}
	}

	/**
	 * Removes the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce tax category mapping that was removed
	 */
	@Override
	public CommerceTaxCategoryMapping removeByERC_C(
			String externalReferenceCode, long companyId)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping = findByERC_C(
			externalReferenceCode, companyId);

		return remove(commerceTaxCategoryMapping);
	}

	/**
	 * Returns the number of commerce tax category mappings where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		CommerceTaxCategoryMapping commerceTaxCategoryMapping = fetchByERC_C(
			externalReferenceCode, companyId);

		if (commerceTaxCategoryMapping == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"commerceTaxCategoryMapping.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(commerceTaxCategoryMapping.externalReferenceCode IS NULL OR commerceTaxCategoryMapping.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"commerceTaxCategoryMapping.companyId = ?";

	public CommerceTaxCategoryMappingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTaxCategoryMapping.class);

		setModelImplClass(CommerceTaxCategoryMappingImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTaxCategoryMappingTable.INSTANCE);
	}

	/**
	 * Caches the commerce tax category mapping in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMapping the commerce tax category mapping
	 */
	@Override
	public void cacheResult(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		entityCache.putResult(
			CommerceTaxCategoryMappingImpl.class,
			commerceTaxCategoryMapping.getPrimaryKey(),
			commerceTaxCategoryMapping);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				commerceTaxCategoryMapping.getUuid(),
				commerceTaxCategoryMapping.getGroupId()
			},
			commerceTaxCategoryMapping);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commerceTaxCategoryMapping.getCommerceTaxMethodId(),
				commerceTaxCategoryMapping.getCPTaxCategoryId()
			},
			commerceTaxCategoryMapping);

		finderCache.putResult(
			_finderPathFetchByERC_C,
			new Object[] {
				commerceTaxCategoryMapping.getExternalReferenceCode(),
				commerceTaxCategoryMapping.getCompanyId()
			},
			commerceTaxCategoryMapping);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tax category mappings in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMappings the commerce tax category mappings
	 */
	@Override
	public void cacheResult(
		List<CommerceTaxCategoryMapping> commerceTaxCategoryMappings) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTaxCategoryMappings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				commerceTaxCategoryMappings) {

			if (entityCache.getResult(
					CommerceTaxCategoryMappingImpl.class,
					commerceTaxCategoryMapping.getPrimaryKey()) == null) {

				cacheResult(commerceTaxCategoryMapping);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tax category mappings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTaxCategoryMappingImpl.class);

		finderCache.clearCache(CommerceTaxCategoryMappingImpl.class);
	}

	/**
	 * Clears the cache for the commerce tax category mapping.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		entityCache.removeResult(
			CommerceTaxCategoryMappingImpl.class, commerceTaxCategoryMapping);
	}

	@Override
	public void clearCache(
		List<CommerceTaxCategoryMapping> commerceTaxCategoryMappings) {

		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				commerceTaxCategoryMappings) {

			entityCache.removeResult(
				CommerceTaxCategoryMappingImpl.class,
				commerceTaxCategoryMapping);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTaxCategoryMappingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTaxCategoryMappingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTaxCategoryMappingModelImpl
			commerceTaxCategoryMappingModelImpl) {

		Object[] args = new Object[] {
			commerceTaxCategoryMappingModelImpl.getUuid(),
			commerceTaxCategoryMappingModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			commerceTaxCategoryMappingModelImpl);

		args = new Object[] {
			commerceTaxCategoryMappingModelImpl.getCommerceTaxMethodId(),
			commerceTaxCategoryMappingModelImpl.getCPTaxCategoryId()
		};

		finderCache.putResult(
			_finderPathFetchByC_C, args, commerceTaxCategoryMappingModelImpl);

		args = new Object[] {
			commerceTaxCategoryMappingModelImpl.getExternalReferenceCode(),
			commerceTaxCategoryMappingModelImpl.getCompanyId()
		};

		finderCache.putResult(
			_finderPathFetchByERC_C, args, commerceTaxCategoryMappingModelImpl);
	}

	/**
	 * Creates a new commerce tax category mapping with the primary key. Does not add the commerce tax category mapping to the database.
	 *
	 * @param commerceTaxCategoryMappingId the primary key for the new commerce tax category mapping
	 * @return the new commerce tax category mapping
	 */
	@Override
	public CommerceTaxCategoryMapping create(
		long commerceTaxCategoryMappingId) {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			new CommerceTaxCategoryMappingImpl();

		commerceTaxCategoryMapping.setNew(true);
		commerceTaxCategoryMapping.setPrimaryKey(commerceTaxCategoryMappingId);

		String uuid = PortalUUIDUtil.generate();

		commerceTaxCategoryMapping.setUuid(uuid);

		commerceTaxCategoryMapping.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceTaxCategoryMapping;
	}

	/**
	 * Removes the commerce tax category mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping that was removed
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping remove(long commerceTaxCategoryMappingId)
		throws NoSuchTaxCategoryMappingException {

		return remove((Serializable)commerceTaxCategoryMappingId);
	}

	/**
	 * Removes the commerce tax category mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping that was removed
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping remove(Serializable primaryKey)
		throws NoSuchTaxCategoryMappingException {

		Session session = null;

		try {
			session = openSession();

			CommerceTaxCategoryMapping commerceTaxCategoryMapping =
				(CommerceTaxCategoryMapping)session.get(
					CommerceTaxCategoryMappingImpl.class, primaryKey);

			if (commerceTaxCategoryMapping == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaxCategoryMappingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTaxCategoryMapping);
		}
		catch (NoSuchTaxCategoryMappingException noSuchEntityException) {
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
	protected CommerceTaxCategoryMapping removeImpl(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTaxCategoryMapping)) {
				commerceTaxCategoryMapping =
					(CommerceTaxCategoryMapping)session.get(
						CommerceTaxCategoryMappingImpl.class,
						commerceTaxCategoryMapping.getPrimaryKeyObj());
			}

			if (commerceTaxCategoryMapping != null) {
				session.delete(commerceTaxCategoryMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTaxCategoryMapping != null) {
			clearCache(commerceTaxCategoryMapping);
		}

		return commerceTaxCategoryMapping;
	}

	@Override
	public CommerceTaxCategoryMapping updateImpl(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		boolean isNew = commerceTaxCategoryMapping.isNew();

		if (!(commerceTaxCategoryMapping instanceof
				CommerceTaxCategoryMappingModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTaxCategoryMapping.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTaxCategoryMapping);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTaxCategoryMapping proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTaxCategoryMapping implementation " +
					commerceTaxCategoryMapping.getClass());
		}

		CommerceTaxCategoryMappingModelImpl
			commerceTaxCategoryMappingModelImpl =
				(CommerceTaxCategoryMappingModelImpl)commerceTaxCategoryMapping;

		if (Validator.isNull(commerceTaxCategoryMapping.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceTaxCategoryMapping.setUuid(uuid);
		}

		if (Validator.isNull(
				commerceTaxCategoryMapping.getExternalReferenceCode())) {

			commerceTaxCategoryMapping.setExternalReferenceCode(
				commerceTaxCategoryMapping.getUuid());
		}
		else {
			if (!Objects.equals(
					commerceTaxCategoryMappingModelImpl.getColumnOriginalValue(
						"externalReferenceCode"),
					commerceTaxCategoryMapping.getExternalReferenceCode())) {

				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					long companyId = commerceTaxCategoryMapping.getCompanyId();

					long groupId = commerceTaxCategoryMapping.getGroupId();

					long classPK = 0;

					if (!isNew) {
						classPK = commerceTaxCategoryMapping.getPrimaryKey();
					}

					try {
						commerceTaxCategoryMapping.setExternalReferenceCode(
							SanitizerUtil.sanitize(
								companyId, groupId, userId,
								CommerceTaxCategoryMapping.class.getName(),
								classPK, ContentTypes.TEXT_HTML,
								Sanitizer.MODE_ALL,
								commerceTaxCategoryMapping.
									getExternalReferenceCode(),
								null));
					}
					catch (SanitizerException sanitizerException) {
						throw new SystemException(sanitizerException);
					}
				}
			}

			CommerceTaxCategoryMapping ercCommerceTaxCategoryMapping =
				fetchByERC_C(
					commerceTaxCategoryMapping.getExternalReferenceCode(),
					commerceTaxCategoryMapping.getCompanyId());

			if (isNew) {
				if (ercCommerceTaxCategoryMapping != null) {
					throw new DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException(
						"Duplicate commerce tax category mapping with external reference code " +
							commerceTaxCategoryMapping.
								getExternalReferenceCode() + " and company " +
									commerceTaxCategoryMapping.getCompanyId());
				}
			}
			else {
				if ((ercCommerceTaxCategoryMapping != null) &&
					(commerceTaxCategoryMapping.
						getCommerceTaxCategoryMappingId() !=
							ercCommerceTaxCategoryMapping.
								getCommerceTaxCategoryMappingId())) {

					throw new DuplicateCommerceTaxCategoryMappingExternalReferenceCodeException(
						"Duplicate commerce tax category mapping with external reference code " +
							commerceTaxCategoryMapping.
								getExternalReferenceCode() + " and company " +
									commerceTaxCategoryMapping.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTaxCategoryMapping.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTaxCategoryMapping.setCreateDate(date);
			}
			else {
				commerceTaxCategoryMapping.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTaxCategoryMappingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTaxCategoryMapping.setModifiedDate(date);
			}
			else {
				commerceTaxCategoryMapping.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTaxCategoryMapping);
			}
			else {
				commerceTaxCategoryMapping =
					(CommerceTaxCategoryMapping)session.merge(
						commerceTaxCategoryMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTaxCategoryMappingImpl.class,
			commerceTaxCategoryMappingModelImpl, false, true);

		cacheUniqueFindersCache(commerceTaxCategoryMappingModelImpl);

		if (isNew) {
			commerceTaxCategoryMapping.setNew(false);
		}

		commerceTaxCategoryMapping.resetOriginalValues();

		return commerceTaxCategoryMapping;
	}

	/**
	 * Returns the commerce tax category mapping with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaxCategoryMappingException {

		CommerceTaxCategoryMapping commerceTaxCategoryMapping =
			fetchByPrimaryKey(primaryKey);

		if (commerceTaxCategoryMapping == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaxCategoryMappingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTaxCategoryMapping;
	}

	/**
	 * Returns the commerce tax category mapping with the primary key or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping findByPrimaryKey(
			long commerceTaxCategoryMappingId)
		throws NoSuchTaxCategoryMappingException {

		return findByPrimaryKey((Serializable)commerceTaxCategoryMappingId);
	}

	/**
	 * Returns the commerce tax category mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping, or <code>null</code> if a commerce tax category mapping with the primary key could not be found
	 */
	@Override
	public CommerceTaxCategoryMapping fetchByPrimaryKey(
		long commerceTaxCategoryMappingId) {

		return fetchByPrimaryKey((Serializable)commerceTaxCategoryMappingId);
	}

	/**
	 * Returns all the commerce tax category mappings.
	 *
	 * @return the commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax category mappings
	 */
	@Override
	public List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
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

		List<CommerceTaxCategoryMapping> list = null;

		if (useFinderCache) {
			list = (List<CommerceTaxCategoryMapping>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETAXCATEGORYMAPPING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETAXCATEGORYMAPPING;

				sql = sql.concat(
					CommerceTaxCategoryMappingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTaxCategoryMapping>)QueryUtil.list(
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
	 * Removes all the commerce tax category mappings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTaxCategoryMapping commerceTaxCategoryMapping :
				findAll()) {

			remove(commerceTaxCategoryMapping);
		}
	}

	/**
	 * Returns the number of commerce tax category mappings.
	 *
	 * @return the number of commerce tax category mappings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCETAXCATEGORYMAPPING);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceTaxCategoryMappingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETAXCATEGORYMAPPING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTaxCategoryMappingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tax category mapping persistence.
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTaxMethodId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathWithoutPaginationFindByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, true);

		_finderPathCountByCommerceTaxMethodId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTaxMethodId", new String[] {Long.class.getName()},
			new String[] {"commerceTaxMethodId"}, false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceTaxMethodId", "CPTaxCategoryId"}, true);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		CommerceTaxCategoryMappingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceTaxCategoryMappingUtil.setPersistence(null);

		entityCache.removeCache(CommerceTaxCategoryMappingImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCETAXCATEGORYMAPPING =
		"SELECT commerceTaxCategoryMapping FROM CommerceTaxCategoryMapping commerceTaxCategoryMapping";

	private static final String _SQL_SELECT_COMMERCETAXCATEGORYMAPPING_WHERE =
		"SELECT commerceTaxCategoryMapping FROM CommerceTaxCategoryMapping commerceTaxCategoryMapping WHERE ";

	private static final String _SQL_COUNT_COMMERCETAXCATEGORYMAPPING =
		"SELECT COUNT(commerceTaxCategoryMapping) FROM CommerceTaxCategoryMapping commerceTaxCategoryMapping";

	private static final String _SQL_COUNT_COMMERCETAXCATEGORYMAPPING_WHERE =
		"SELECT COUNT(commerceTaxCategoryMapping) FROM CommerceTaxCategoryMapping commerceTaxCategoryMapping WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTaxCategoryMapping.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTaxCategoryMapping exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTaxCategoryMapping exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTaxCategoryMappingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-489687119