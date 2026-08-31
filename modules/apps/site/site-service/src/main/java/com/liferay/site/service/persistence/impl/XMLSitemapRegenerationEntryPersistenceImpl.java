/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.service.persistence.impl;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.site.exception.NoSuchXMLSitemapRegenerationEntryException;
import com.liferay.site.model.XMLSitemapRegenerationEntry;
import com.liferay.site.model.XMLSitemapRegenerationEntryTable;
import com.liferay.site.model.impl.XMLSitemapRegenerationEntryImpl;
import com.liferay.site.model.impl.XMLSitemapRegenerationEntryModelImpl;
import com.liferay.site.service.persistence.XMLSitemapRegenerationEntryPersistence;
import com.liferay.site.service.persistence.XMLSitemapRegenerationEntryUtil;
import com.liferay.site.service.persistence.impl.constants.SitePersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the xml sitemap regeneration entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = XMLSitemapRegenerationEntryPersistence.class)
public class XMLSitemapRegenerationEntryPersistenceImpl
	extends BasePersistenceImpl
		<XMLSitemapRegenerationEntry,
		 NoSuchXMLSitemapRegenerationEntryException>
	implements XMLSitemapRegenerationEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>XMLSitemapRegenerationEntryUtil</code> to access the xml sitemap regeneration entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		XMLSitemapRegenerationEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private CollectionPersistenceFinder
		<XMLSitemapRegenerationEntry,
		 NoSuchXMLSitemapRegenerationEntryException>
			_collectionPersistenceFinderByCompanyId;

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	@Override
	public List<XMLSitemapRegenerationEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByCompanyId.find(
			finderCache, new Object[] {companyId}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a matching xml sitemap regeneration entry could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator)
		throws NoSuchXMLSitemapRegenerationEntryException {

		return _collectionPersistenceFinderByCompanyId.findFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry, or <code>null</code> if a matching xml sitemap regeneration entry could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator) {

		return _collectionPersistenceFinderByCompanyId.fetchFirst(
			finderCache, new Object[] {companyId}, orderByComparator);
	}

	/**
	 * Removes all the xml sitemap regeneration entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		_collectionPersistenceFinderByCompanyId.remove(
			finderCache, new Object[] {companyId});
	}

	/**
	 * Returns the number of xml sitemap regeneration entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching xml sitemap regeneration entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		return _collectionPersistenceFinderByCompanyId.count(
			finderCache, new Object[] {companyId});
	}

	private CollectionPersistenceFinder
		<XMLSitemapRegenerationEntry,
		 NoSuchXMLSitemapRegenerationEntryException>
			_collectionPersistenceFinderByG_C_A;

	/**
	 * Returns an ordered range of all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>XMLSitemapRegenerationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param start the lower bound of the range of xml sitemap regeneration entries
	 * @param end the upper bound of the range of xml sitemap regeneration entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching xml sitemap regeneration entries
	 */
	@Override
	public List<XMLSitemapRegenerationEntry> findByG_C_A(
		long groupId, long companyId, String assetTypeKey, int start, int end,
		OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByG_C_A.find(
			finderCache, new Object[] {groupId, companyId, assetTypeKey}, start,
			end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a matching xml sitemap regeneration entry could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry findByG_C_A_First(
			long groupId, long companyId, String assetTypeKey,
			OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator)
		throws NoSuchXMLSitemapRegenerationEntryException {

		return _collectionPersistenceFinderByG_C_A.findFirst(
			finderCache, new Object[] {groupId, companyId, assetTypeKey},
			orderByComparator);
	}

	/**
	 * Returns the first xml sitemap regeneration entry in the ordered set where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching xml sitemap regeneration entry, or <code>null</code> if a matching xml sitemap regeneration entry could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry fetchByG_C_A_First(
		long groupId, long companyId, String assetTypeKey,
		OrderByComparator<XMLSitemapRegenerationEntry> orderByComparator) {

		return _collectionPersistenceFinderByG_C_A.fetchFirst(
			finderCache, new Object[] {groupId, companyId, assetTypeKey},
			orderByComparator);
	}

	/**
	 * Removes all the xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 */
	@Override
	public void removeByG_C_A(
		long groupId, long companyId, String assetTypeKey) {

		_collectionPersistenceFinderByG_C_A.remove(
			finderCache, new Object[] {groupId, companyId, assetTypeKey});
	}

	/**
	 * Returns the number of xml sitemap regeneration entries where groupId = &#63; and companyId = &#63; and assetTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param assetTypeKey the asset type key
	 * @return the number of matching xml sitemap regeneration entries
	 */
	@Override
	public int countByG_C_A(long groupId, long companyId, String assetTypeKey) {
		return _collectionPersistenceFinderByG_C_A.count(
			finderCache, new Object[] {groupId, companyId, assetTypeKey});
	}

	public XMLSitemapRegenerationEntryPersistenceImpl() {
		setModelClass(XMLSitemapRegenerationEntry.class);

		setModelImplClass(XMLSitemapRegenerationEntryImpl.class);
		setModelPKClass(long.class);

		setTable(XMLSitemapRegenerationEntryTable.INSTANCE);
	}

	/**
	 * Creates a new xml sitemap regeneration entry with the primary key. Does not add the xml sitemap regeneration entry to the database.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key for the new xml sitemap regeneration entry
	 * @return the new xml sitemap regeneration entry
	 */
	@Override
	public XMLSitemapRegenerationEntry create(
		long xmlSitemapRegenerationEntryId) {

		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry =
			new XMLSitemapRegenerationEntryImpl();

		xmlSitemapRegenerationEntry.setNew(true);
		xmlSitemapRegenerationEntry.setPrimaryKey(
			xmlSitemapRegenerationEntryId);

		xmlSitemapRegenerationEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return xmlSitemapRegenerationEntry;
	}

	/**
	 * Removes the xml sitemap regeneration entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry that was removed
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry remove(
			long xmlSitemapRegenerationEntryId)
		throws NoSuchXMLSitemapRegenerationEntryException {

		return remove((Serializable)xmlSitemapRegenerationEntryId);
	}

	@Override
	protected XMLSitemapRegenerationEntry removeImpl(
		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(xmlSitemapRegenerationEntry)) {
				xmlSitemapRegenerationEntry =
					(XMLSitemapRegenerationEntry)session.get(
						XMLSitemapRegenerationEntryImpl.class,
						xmlSitemapRegenerationEntry.getPrimaryKeyObj());
			}

			if (xmlSitemapRegenerationEntry != null) {
				session.delete(xmlSitemapRegenerationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (xmlSitemapRegenerationEntry != null) {
			clearCache(xmlSitemapRegenerationEntry);
		}

		return xmlSitemapRegenerationEntry;
	}

	@Override
	public XMLSitemapRegenerationEntry updateImpl(
		XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry) {

		boolean isNew = xmlSitemapRegenerationEntry.isNew();

		if (!(xmlSitemapRegenerationEntry instanceof
				XMLSitemapRegenerationEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					xmlSitemapRegenerationEntry.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					xmlSitemapRegenerationEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in xmlSitemapRegenerationEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom XMLSitemapRegenerationEntry implementation " +
					xmlSitemapRegenerationEntry.getClass());
		}

		XMLSitemapRegenerationEntryModelImpl
			xmlSitemapRegenerationEntryModelImpl =
				(XMLSitemapRegenerationEntryModelImpl)
					xmlSitemapRegenerationEntry;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(xmlSitemapRegenerationEntry);
			}
			else {
				xmlSitemapRegenerationEntry =
					(XMLSitemapRegenerationEntry)session.merge(
						xmlSitemapRegenerationEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(xmlSitemapRegenerationEntry, false);

		if (isNew) {
			xmlSitemapRegenerationEntry.setNew(false);
		}

		xmlSitemapRegenerationEntry.resetOriginalValues();

		return xmlSitemapRegenerationEntry;
	}

	/**
	 * Returns the xml sitemap regeneration entry with the primary key or throws a <code>NoSuchXMLSitemapRegenerationEntryException</code> if it could not be found.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry
	 * @throws NoSuchXMLSitemapRegenerationEntryException if a xml sitemap regeneration entry with the primary key could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry findByPrimaryKey(
			long xmlSitemapRegenerationEntryId)
		throws NoSuchXMLSitemapRegenerationEntryException {

		return findByPrimaryKey((Serializable)xmlSitemapRegenerationEntryId);
	}

	/**
	 * Returns the xml sitemap regeneration entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param xmlSitemapRegenerationEntryId the primary key of the xml sitemap regeneration entry
	 * @return the xml sitemap regeneration entry, or <code>null</code> if a xml sitemap regeneration entry with the primary key could not be found
	 */
	@Override
	public XMLSitemapRegenerationEntry fetchByPrimaryKey(
		long xmlSitemapRegenerationEntryId) {

		return fetchByPrimaryKey((Serializable)xmlSitemapRegenerationEntryId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "xmlSitemapRegenerationEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_XMLSITEMAPREGENERATIONENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return XMLSitemapRegenerationEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the xml sitemap regeneration entry persistence.
	 */
	@Activate
	public void activate() {
		_collectionPersistenceFinderByCompanyId =
			new CollectionPersistenceFinder<>(
				this,
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
					new String[] {
						Long.class.getName(), Integer.class.getName(),
						Integer.class.getName(),
						OrderByComparator.class.getName()
					},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, true),
				new FinderPath(
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"countByCompanyId", new String[] {Long.class.getName()},
					new String[] {"companyId"}, false),
				_SQL_SELECT_XMLSITEMAPREGENERATIONENTRY_WHERE,
				_SQL_COUNT_XMLSITEMAPREGENERATIONENTRY_WHERE,
				XMLSitemapRegenerationEntryModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX, "", "", null,
				new FinderColumn<>(
					"xmlSitemapRegenerationEntry.", "companyId",
					FinderColumn.Type.LONG, "=", true, true,
					XMLSitemapRegenerationEntry::getCompanyId));

		_collectionPersistenceFinderByG_C_A = new CollectionPersistenceFinder<>(
			this,
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_A",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"groupId", "companyId", "assetTypeKey"}, true),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_A",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName()
				},
				new String[] {"groupId", "companyId", "assetTypeKey"}, 0, 4,
				true, null),
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_A",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					String.class.getName()
				},
				new String[] {"groupId", "companyId", "assetTypeKey"}, 0, 4,
				false, null),
			_SQL_SELECT_XMLSITEMAPREGENERATIONENTRY_WHERE,
			_SQL_COUNT_XMLSITEMAPREGENERATIONENTRY_WHERE,
			XMLSitemapRegenerationEntryModelImpl.ORDER_BY_JPQL,
			_ENTITY_ALIAS_PREFIX, "", "", null,
			new FinderColumn<>(
				"xmlSitemapRegenerationEntry.", "groupId",
				FinderColumn.Type.LONG, "=", true, true,
				XMLSitemapRegenerationEntry::getGroupId),
			new FinderColumn<>(
				"xmlSitemapRegenerationEntry.", "companyId",
				FinderColumn.Type.LONG, "=", true, true,
				XMLSitemapRegenerationEntry::getCompanyId),
			new FinderColumn<>(
				"xmlSitemapRegenerationEntry.", "assetTypeKey",
				FinderColumn.Type.STRING, "=", true, true,
				XMLSitemapRegenerationEntry::getAssetTypeKey));

		XMLSitemapRegenerationEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		XMLSitemapRegenerationEntryUtil.setPersistence(null);

		entityCache.removeCache(
			XMLSitemapRegenerationEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SitePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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
		XMLSitemapRegenerationEntryModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_XMLSITEMAPREGENERATIONENTRY =
		"SELECT xmlSitemapRegenerationEntry FROM XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry";

	private static final String _SQL_SELECT_XMLSITEMAPREGENERATIONENTRY_WHERE =
		"SELECT xmlSitemapRegenerationEntry FROM XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry WHERE ";

	private static final String _SQL_COUNT_XMLSITEMAPREGENERATIONENTRY_WHERE =
		"SELECT COUNT(xmlSitemapRegenerationEntry) FROM XMLSitemapRegenerationEntry xmlSitemapRegenerationEntry WHERE ";

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1334388892