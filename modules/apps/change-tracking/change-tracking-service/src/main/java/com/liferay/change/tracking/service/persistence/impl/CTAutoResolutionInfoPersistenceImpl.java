/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchAutoResolutionInfoException;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.change.tracking.model.CTAutoResolutionInfoTable;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoImpl;
import com.liferay.change.tracking.model.impl.CTAutoResolutionInfoModelImpl;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoPersistence;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoUtil;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct auto resolution info service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTAutoResolutionInfoPersistence.class)
public class CTAutoResolutionInfoPersistenceImpl
	extends BasePersistenceImpl
		<CTAutoResolutionInfo, NoSuchAutoResolutionInfoException>
	implements CTAutoResolutionInfoPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTAutoResolutionInfoUtil</code> to access the ct auto resolution info persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTAutoResolutionInfoImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;
	private CollectionPersistenceFinder<CTAutoResolutionInfo>
		_collectionPersistenceFinderByCtCollectionId;

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId) {

		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCtCollectionId.find(
			finderCache, new Object[] {ctCollectionId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByCtCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		throw new NoSuchAutoResolutionInfoException(
			_collectionPersistenceFinderByCtCollectionId.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY, new Object[] {ctCollectionId}));
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByCtCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return _collectionPersistenceFinderByCtCollectionId.fetchFirst(
			finderCache, new Object[] {ctCollectionId}, orderByComparator);
	}

	/**
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		_collectionPersistenceFinderByCtCollectionId.remove(
			finderCache, new Object[] {ctCollectionId});
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		return _collectionPersistenceFinderByCtCollectionId.count(
			finderCache, new Object[] {ctCollectionId});
	}

	private FinderPath _finderPathWithPaginationFindByC_MCNI_SMCPK;
	private FinderPath _finderPathWithoutPaginationFindByC_MCNI_SMCPK;
	private FinderPath _finderPathCountByC_MCNI_SMCPK;
	private FinderPath _finderPathWithPaginationCountByC_MCNI_SMCPK;

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_MCNI_SMCPK;
				finderArgs = new Object[] {
					ctCollectionId, modelClassNameId, sourceModelClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_MCNI_SMCPK;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, sourceModelClassPK, start,
				end, orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if ((ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) ||
						(modelClassNameId !=
							ctAutoResolutionInfo.getModelClassNameId()) ||
						(sourceModelClassPK !=
							ctAutoResolutionInfo.getSourceModelClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(sourceModelClassPK);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
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
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByC_MCNI_SMCPK_First(
			long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
			OrderByComparator<CTAutoResolutionInfo> orderByComparator)
		throws NoSuchAutoResolutionInfoException {

		CTAutoResolutionInfo ctAutoResolutionInfo = fetchByC_MCNI_SMCPK_First(
			ctCollectionId, modelClassNameId, sourceModelClassPK,
			orderByComparator);

		if (ctAutoResolutionInfo != null) {
			return ctAutoResolutionInfo;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append(", modelClassNameId=");
		sb.append(modelClassNameId);

		sb.append(", sourceModelClassPK=");
		sb.append(sourceModelClassPK);

		sb.append("}");

		throw new NoSuchAutoResolutionInfoException(sb.toString());
	}

	/**
	 * Returns the first ct auto resolution info in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct auto resolution info, or <code>null</code> if a matching ct auto resolution info could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByC_MCNI_SMCPK_First(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		List<CTAutoResolutionInfo> list = findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @return the matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId,
		long[] sourceModelClassPKs) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @return the range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator) {

		return findByC_MCNI_SMCPK(
			ctCollectionId, modelClassNameId, sourceModelClassPKs, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTAutoResolutionInfoModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @param start the lower bound of the range of ct auto resolution infos
	 * @param end the upper bound of the range of ct auto resolution infos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct auto resolution infos
	 */
	@Override
	public List<CTAutoResolutionInfo> findByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long[] sourceModelClassPKs,
		int start, int end,
		OrderByComparator<CTAutoResolutionInfo> orderByComparator,
		boolean useFinderCache) {

		if (sourceModelClassPKs == null) {
			sourceModelClassPKs = new long[0];
		}
		else if (sourceModelClassPKs.length > 1) {
			sourceModelClassPKs = ArrayUtil.sortedUnique(sourceModelClassPKs);
		}

		if (sourceModelClassPKs.length == 1) {
			return findByC_MCNI_SMCPK(
				ctCollectionId, modelClassNameId, sourceModelClassPKs[0], start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					ctCollectionId, modelClassNameId,
					StringUtil.merge(sourceModelClassPKs)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId,
				StringUtil.merge(sourceModelClassPKs), start, end,
				orderByComparator
			};
		}

		List<CTAutoResolutionInfo> list = null;

		if (useFinderCache) {
			list = (List<CTAutoResolutionInfo>)finderCache.getResult(
				_finderPathWithPaginationFindByC_MCNI_SMCPK, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTAutoResolutionInfo ctAutoResolutionInfo : list) {
					if ((ctCollectionId !=
							ctAutoResolutionInfo.getCtCollectionId()) ||
						(modelClassNameId !=
							ctAutoResolutionInfo.getModelClassNameId()) ||
						!ArrayUtil.contains(
							sourceModelClassPKs,
							ctAutoResolutionInfo.getSourceModelClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			if (sourceModelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7);

				sb.append(StringUtil.merge(sourceModelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				list = (List<CTAutoResolutionInfo>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByC_MCNI_SMCPK, finderArgs,
						list);
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
	 * Removes all the ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 */
	@Override
	public void removeByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				findByC_MCNI_SMCPK(
					ctCollectionId, modelClassNameId, sourceModelClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctAutoResolutionInfo);
		}
	}

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPK the source model class pk
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId, long sourceModelClassPK) {

		FinderPath finderPath = _finderPathCountByC_MCNI_SMCPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, sourceModelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				queryPos.add(sourceModelClassPK);

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

	/**
	 * Returns the number of ct auto resolution infos where ctCollectionId = &#63; and modelClassNameId = &#63; and sourceModelClassPK = any &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param sourceModelClassPKs the source model class pks
	 * @return the number of matching ct auto resolution infos
	 */
	@Override
	public int countByC_MCNI_SMCPK(
		long ctCollectionId, long modelClassNameId,
		long[] sourceModelClassPKs) {

		if (sourceModelClassPKs == null) {
			sourceModelClassPKs = new long[0];
		}
		else if (sourceModelClassPKs.length > 1) {
			sourceModelClassPKs = ArrayUtil.sortedUnique(sourceModelClassPKs);
		}

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId,
			StringUtil.merge(sourceModelClassPKs)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByC_MCNI_SMCPK, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2);

			if (sourceModelClassPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7);

				sb.append(StringUtil.merge(sourceModelClassPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				queryPos.add(modelClassNameId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByC_MCNI_SMCPK, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_C_MCNI_SMCPK_CTCOLLECTIONID_2 =
		"ctAutoResolutionInfo.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_SMCPK_MODELCLASSNAMEID_2 =
		"ctAutoResolutionInfo.modelClassNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_2 =
			"ctAutoResolutionInfo.sourceModelClassPK = ?";

	private static final String
		_FINDER_COLUMN_C_MCNI_SMCPK_SOURCEMODELCLASSPK_7 =
			"ctAutoResolutionInfo.sourceModelClassPK IN (";

	public CTAutoResolutionInfoPersistenceImpl() {
		setModelClass(CTAutoResolutionInfo.class);

		setModelImplClass(CTAutoResolutionInfoImpl.class);
		setModelPKClass(long.class);

		setTable(CTAutoResolutionInfoTable.INSTANCE);
	}

	/**
	 * Creates a new ct auto resolution info with the primary key. Does not add the ct auto resolution info to the database.
	 *
	 * @param ctAutoResolutionInfoId the primary key for the new ct auto resolution info
	 * @return the new ct auto resolution info
	 */
	@Override
	public CTAutoResolutionInfo create(long ctAutoResolutionInfoId) {
		CTAutoResolutionInfo ctAutoResolutionInfo =
			new CTAutoResolutionInfoImpl();

		ctAutoResolutionInfo.setNew(true);
		ctAutoResolutionInfo.setPrimaryKey(ctAutoResolutionInfoId);

		ctAutoResolutionInfo.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctAutoResolutionInfo;
	}

	/**
	 * Removes the ct auto resolution info with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info that was removed
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo remove(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return remove((Serializable)ctAutoResolutionInfoId);
	}

	@Override
	protected CTAutoResolutionInfo removeImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctAutoResolutionInfo)) {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.get(
					CTAutoResolutionInfoImpl.class,
					ctAutoResolutionInfo.getPrimaryKeyObj());
			}

			if (ctAutoResolutionInfo != null) {
				session.delete(ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctAutoResolutionInfo != null) {
			clearCache(ctAutoResolutionInfo);
		}

		return ctAutoResolutionInfo;
	}

	@Override
	public CTAutoResolutionInfo updateImpl(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		boolean isNew = ctAutoResolutionInfo.isNew();

		if (!(ctAutoResolutionInfo instanceof CTAutoResolutionInfoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctAutoResolutionInfo.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ctAutoResolutionInfo);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctAutoResolutionInfo proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTAutoResolutionInfo implementation " +
					ctAutoResolutionInfo.getClass());
		}

		CTAutoResolutionInfoModelImpl ctAutoResolutionInfoModelImpl =
			(CTAutoResolutionInfoModelImpl)ctAutoResolutionInfo;

		if (isNew && (ctAutoResolutionInfo.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				ctAutoResolutionInfo.setCreateDate(date);
			}
			else {
				ctAutoResolutionInfo.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctAutoResolutionInfo);
			}
			else {
				ctAutoResolutionInfo = (CTAutoResolutionInfo)session.merge(
					ctAutoResolutionInfo);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(ctAutoResolutionInfo, false);

		if (isNew) {
			ctAutoResolutionInfo.setNew(false);
		}

		ctAutoResolutionInfo.resetOriginalValues();

		return ctAutoResolutionInfo;
	}

	/**
	 * Returns the ct auto resolution info with the primary key or throws a <code>NoSuchAutoResolutionInfoException</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info
	 * @throws NoSuchAutoResolutionInfoException if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo findByPrimaryKey(long ctAutoResolutionInfoId)
		throws NoSuchAutoResolutionInfoException {

		return findByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	/**
	 * Returns the ct auto resolution info with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctAutoResolutionInfoId the primary key of the ct auto resolution info
	 * @return the ct auto resolution info, or <code>null</code> if a ct auto resolution info with the primary key could not be found
	 */
	@Override
	public CTAutoResolutionInfo fetchByPrimaryKey(long ctAutoResolutionInfoId) {
		return fetchByPrimaryKey((Serializable)ctAutoResolutionInfoId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "ctAutoResolutionInfoId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTAUTORESOLUTIONINFO;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTAutoResolutionInfoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct auto resolution info persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);

		_collectionPersistenceFinderByCtCollectionId =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByCtCollectionId,
				_finderPathWithoutPaginationFindByCtCollectionId,
				_finderPathCountByCtCollectionId,
				_SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE,
				_SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE,
				CTAutoResolutionInfoModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"ctAutoResolutionInfo.", "ctCollectionId",
					FinderColumn.Type.LONG, "=", true, true,
					CTAutoResolutionInfo::getCtCollectionId));

		_finderPathWithPaginationFindByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			true);

		_finderPathWithoutPaginationFindByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			true);

		_finderPathCountByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			false);

		_finderPathWithPaginationCountByC_MCNI_SMCPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_MCNI_SMCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"ctCollectionId", "modelClassNameId", "sourceModelClassPK"
			},
			false);

		CTAutoResolutionInfoUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTAutoResolutionInfoUtil.setPersistence(null);

		entityCache.removeCache(CTAutoResolutionInfoImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _ENTITY_ALIAS_PREFIX =
		CTAutoResolutionInfoModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo";

	private static final String _SQL_SELECT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT ctAutoResolutionInfo FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _SQL_COUNT_CTAUTORESOLUTIONINFO_WHERE =
		"SELECT COUNT(ctAutoResolutionInfo) FROM CTAutoResolutionInfo ctAutoResolutionInfo WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTAutoResolutionInfo exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTAutoResolutionInfoPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:2029145134