/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.link.constants.AssetLinkConstants;
import com.liferay.asset.link.exception.NoSuchLinkException;
import com.liferay.asset.link.model.AssetLink;
import com.liferay.asset.link.model.AssetLinkTable;
import com.liferay.asset.link.model.adapter.StagedAssetLink;
import com.liferay.asset.link.service.base.AssetLinkLocalServiceBaseImpl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.asset.link.model.AssetLink",
	service = AopService.class
)
public class AssetLinkLocalServiceImpl extends AssetLinkLocalServiceBaseImpl {

	/**
	 * Adds a new asset link.
	 *
	 * @param  userId the primary key of the link's creator
	 * @param  entryId1 the primary key of the first asset entry
	 * @param  entryId2 the primary key of the second asset entry
	 * @param  type the link type. Acceptable values include {@link
	 *         AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	 *         relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	 *         unidirectional relationship. For more information see {@link
	 *         AssetLinkConstants}
	 * @param  weight the weight of the relationship, allowing precedence
	 *         ordering of links
	 * @return the asset link
	 */
	@Override
	public AssetLink addLink(
			long userId, long entryId1, long entryId2, int type, int weight)
		throws PortalException {

		User user = _userLocalService.getUser(userId);
		Date date = new Date();

		long linkId1 = counterLocalService.increment();

		AssetLink link1 = assetLinkPersistence.create(linkId1);

		link1.setCompanyId(user.getCompanyId());
		link1.setUserId(user.getUserId());
		link1.setUserName(user.getFullName());
		link1.setCreateDate(date);
		link1.setEntryId1(entryId1);
		link1.setEntryId2(entryId2);
		link1.setType(type);
		link1.setWeight(weight);

		link1 = assetLinkPersistence.update(link1);

		if (AssetLinkConstants.isTypeBi(type)) {
			long linkId2 = counterLocalService.increment();

			AssetLink link2 = assetLinkPersistence.create(linkId2);

			link2.setCompanyId(user.getCompanyId());
			link2.setUserId(user.getUserId());
			link2.setUserName(user.getFullName());
			link2.setCreateDate(date);
			link2.setEntryId1(entryId2);
			link2.setEntryId2(entryId1);
			link2.setType(type);
			link2.setWeight(weight);

			assetLinkPersistence.update(link2);
		}

		return link1;
	}

	@Override
	public AssetLink deleteAssetLink(AssetLink assetLink) {
		AssetLink deletedAssetLink = super.deleteAssetLink(assetLink);

		_addDeletionSystemEvent(assetLink);

		return deletedAssetLink;
	}

	@Override
	public AssetLink deleteAssetLink(long linkId) throws PortalException {
		AssetLink assetLink = super.deleteAssetLink(linkId);

		_addDeletionSystemEvent(assetLink);

		return assetLink;
	}

	@Override
	public void deleteGroupLinks(long groupId) {
		for (AssetLink assetLink :
				getLinks(
					groupId, null, null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			deleteAssetLink(assetLink);
		}
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param link the asset link
	 */
	@Override
	public void deleteLink(AssetLink link) {
		if (AssetLinkConstants.isTypeBi(link.getType())) {
			try {
				AssetLink assetLink = assetLinkPersistence.findByE_E_T(
					link.getEntryId2(), link.getEntryId1(), link.getType());

				deleteAssetLink(assetLink);
			}
			catch (NoSuchLinkException noSuchLinkException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to delete asset link", noSuchLinkException);
				}
			}
		}

		assetLinkPersistence.remove(link);

		_addDeletionSystemEvent(link);
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param linkId the primary key of the asset link
	 */
	@Override
	public void deleteLink(long linkId) throws PortalException {
		AssetLink link = assetLinkPersistence.findByPrimaryKey(linkId);

		deleteLink(link);
	}

	/**
	 * Deletes all links associated with the asset entry.
	 *
	 * @param entryId the primary key of the asset entry
	 */
	@Override
	public void deleteLinks(long entryId) {
		for (AssetLink link : assetLinkPersistence.findByEntryId1(entryId)) {
			deleteLink(link);
		}

		for (AssetLink link : assetLinkPersistence.findByEntryId2(entryId)) {
			deleteLink(link);
		}
	}

	/**
	 * Delete all links that associate the two asset entries.
	 *
	 * @param entryId1 the primary key of the first asset entry
	 * @param entryId2 the primary key of the second asset entry
	 */
	@Override
	public void deleteLinks(long entryId1, long entryId2) {
		List<AssetLink> links = assetLinkPersistence.findByE_E(
			entryId1, entryId2);

		for (AssetLink link : links) {
			deleteLink(link);
		}
	}

	/**
	 * Returns all the asset links whose first entry ID is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset links whose first entry ID is the given entry ID
	 */
	@Override
	public List<AssetLink> getDirectLinks(long entryId) {
		return getDirectLinks(entryId, true);
	}

	@Override
	public List<AssetLink> getDirectLinks(
		long entryId, boolean excludeInvisibleLinks) {

		List<AssetLink> assetLinks = assetLinkPersistence.findByEntryId1(
			entryId);

		return _filterAssetLinks(assetLinks, excludeInvisibleLinks);
	}

	/**
	 * Returns all the asset links of the given link type whose first entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	 *         relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	 *         unidirectional relationship. For more information see {@link
	 *         AssetLinkConstants}
	 * @return the asset links of the given link type whose first entry ID is
	 *         the given entry ID
	 */
	@Override
	public List<AssetLink> getDirectLinks(long entryId, int typeId) {
		return getDirectLinks(entryId, typeId, true);
	}

	@Override
	public List<AssetLink> getDirectLinks(
		long entryId, int typeId, boolean excludeInvisibleLinks) {

		List<AssetLink> assetLinks = assetLinkPersistence.findByE1_T(
			entryId, typeId);

		return _filterAssetLinks(assetLinks, excludeInvisibleLinks);
	}

	/**
	 * Returns all the asset links whose first or second entry ID is the given
	 * entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @return the asset links whose first or second entry ID is the given entry
	 *         ID
	 */
	@Override
	public List<AssetLink> getLinks(long entryId) {
		List<AssetLink> e1Links = assetLinkPersistence.findByEntryId1(entryId);
		List<AssetLink> e2Links = assetLinkPersistence.findByEntryId2(entryId);

		List<AssetLink> links = new ArrayList<>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	@Override
	public List<AssetLink> getLinks(
		long groupId, Date startDate, Date endDate, int start, int end) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			AssetEntryTable.INSTANCE.entryId
		).from(
			AssetEntryTable.INSTANCE
		).where(
			AssetEntryTable.INSTANCE.groupId.eq(
				groupId
			).and(
				() -> {
					if ((startDate == null) && (endDate == null)) {
						return null;
					}

					if ((startDate != null) && (endDate == null)) {
						return AssetLinkTable.INSTANCE.createDate.gt(startDate);
					}

					if (startDate == null) {
						return AssetLinkTable.INSTANCE.createDate.lt(startDate);
					}

					return AssetLinkTable.INSTANCE.createDate.gt(
						startDate
					).and(
						AssetLinkTable.INSTANCE.createDate.lt(startDate)
					);
				}
			)
		);

		return assetLinkPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				AssetLinkTable.INSTANCE
			).from(
				AssetLinkTable.INSTANCE
			).where(
				AssetLinkTable.INSTANCE.entryId1.in(
					dslQuery
				).or(
					AssetLinkTable.INSTANCE.entryId2.in(dslQuery)
				)
			).limit(
				start, end
			));
	}

	/**
	 * Returns all the asset links of the given link type whose first or second
	 * entry ID is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	 *         relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	 *         unidirectional relationship. For more information see {@link
	 *         AssetLinkConstants}
	 * @return the asset links of the given link type whose first or second
	 *         entry ID is the given entry ID
	 */
	@Override
	public List<AssetLink> getLinks(long entryId, int typeId) {
		List<AssetLink> e1Links = assetLinkPersistence.findByE1_T(
			entryId, typeId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2_T(
			entryId, typeId);

		List<AssetLink> links = new ArrayList<>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	/**
	 * Returns all the asset links of an AssetEntry.
	 *
	 * @param  classNameId AssetEntry's classNameId
	 * @param  classPK AssetEntry's classPK
	 * @return the asset links of the given entry params
	 */
	@Override
	public List<AssetLink> getLinks(long classNameId, long classPK) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			classNameId, classPK);

		if (assetEntry == null) {
			return Collections.emptyList();
		}

		return assetLinkPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				AssetLinkTable.INSTANCE
			).from(
				AssetLinkTable.INSTANCE
			).where(
				AssetLinkTable.INSTANCE.entryId1.eq(
					assetEntry.getEntryId()
				).or(
					AssetLinkTable.INSTANCE.entryId2.eq(assetEntry.getEntryId())
				)
			));
	}

	/**
	 * Returns all the asset links of the given link type whose second entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	 *         relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	 *         unidirectional relationship. For more information see {@link
	 *         AssetLinkConstants}
	 * @return the asset links of the given link type whose second entry ID is
	 *         the given entry ID
	 */
	@Override
	public List<AssetLink> getReverseLinks(long entryId, int typeId) {
		return assetLinkPersistence.findByE2_T(entryId, typeId);
	}

	@Override
	public AssetLink updateLink(
			long userId, long entryId1, long entryId2, int typeId, int weight)
		throws PortalException {

		AssetLink assetLink = assetLinkPersistence.fetchByE_E_T(
			entryId1, entryId2, typeId);

		if (assetLink == null) {
			return addLink(userId, entryId1, entryId2, typeId, weight);
		}

		assetLink.setWeight(weight);

		return assetLinkPersistence.update(assetLink);
	}

	/**
	 * Updates all links of the asset entry, replacing them with links
	 * associating the asset entry with the asset entries of the given link
	 * entry IDs.
	 *
	 * <p>
	 * If no link exists with a given link entry ID, a new link is created
	 * associating the current asset entry with the asset entry of that link
	 * entry ID. An existing link is deleted if either of its entry IDs is not
	 * contained in the given link entry IDs.
	 * </p>
	 *
	 * @param userId the primary key of the user updating the links
	 * @param entryId the primary key of the asset entry to be managed
	 * @param linkEntryIds the primary keys of the asset entries to be linked
	 *        with the asset entry to be managed
	 * @param typeId the type of the asset links to be created. Acceptable
	 *        values include {@link AssetLinkConstants#TYPE_RELATED} which is a
	 *        bidirectional relationship and {@link
	 *        AssetLinkConstants#TYPE_CHILD} which is a unidirectional
	 *        relationship. For more information see {@link AssetLinkConstants}
	 */
	@Override
	public void updateLinks(
			long userId, long entryId, long[] linkEntryIds, int typeId)
		throws PortalException {

		if (linkEntryIds == null) {
			return;
		}

		List<AssetLink> links = getLinks(entryId, typeId);

		for (AssetLink link : links) {
			if (((link.getEntryId1() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId2())) ||
				((link.getEntryId2() == entryId) &&
				 !ArrayUtil.contains(linkEntryIds, link.getEntryId1()))) {

				deleteAssetLink(link);
			}
		}

		for (long assetLinkEntryId : linkEntryIds) {
			if (assetLinkEntryId != entryId) {
				AssetLink link = assetLinkPersistence.fetchByE_E_T(
					entryId, assetLinkEntryId, typeId);

				if (link == null) {
					addLink(userId, entryId, assetLinkEntryId, typeId, 0);
				}
			}
		}
	}

	private void _addDeletionSystemEvent(AssetLink assetLink) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetLink.getEntryId1());

		if (assetEntry == null) {
			return;
		}

		StagedAssetLink stagedAssetLink = ModelAdapterUtil.adapt(
			assetLink, AssetLink.class, StagedAssetLink.class);

		StagedModelType stagedModelType = stagedAssetLink.getStagedModelType();

		try {
			_systemEventLocalService.addSystemEvent(
				0, assetEntry.getGroupId(), stagedModelType.getClassName(),
				stagedAssetLink.getPrimaryKey(), stagedAssetLink.getUuid(),
				null, SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private List<AssetLink> _filterAssetLinks(
		List<AssetLink> assetLinks, boolean excludeInvisibleLinks) {

		if (assetLinks.isEmpty() || !excludeInvisibleLinks) {
			return assetLinks;
		}

		List<AssetLink> filteredAssetLinks = new ArrayList<>(assetLinks.size());

		for (AssetLink assetLink : assetLinks) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				assetLink.getEntryId2());

			if ((assetEntry != null) && assetEntry.isVisible()) {
				filteredAssetLinks.add(assetLink);
			}
		}

		return Collections.unmodifiableList(filteredAssetLinks);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetLinkLocalServiceImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

	@Reference
	private UserLocalService _userLocalService;

}