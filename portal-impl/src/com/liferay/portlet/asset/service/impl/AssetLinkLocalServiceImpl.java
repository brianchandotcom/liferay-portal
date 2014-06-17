/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.NoSuchLinkException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.service.base.AssetLinkLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class implements the methods needed to handle AssetLinks, the entity
 * that relates different assets in the portal.
 *
 * The basic information stored for every link includes both assets entry IDs,
 * the userId, the link type and the link's weight.
 *
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetLinkLocalServiceImpl extends AssetLinkLocalServiceBaseImpl {

	/**
	 * Adds a new asset link.
	 *
	 * @param  userId the primary key of the link's creator
	 * @param  entryId1 the primary key of the first asset entry
	 * @param  entryId2 the primary key of the second asset entry
	 * @param  type the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @param  weight the weight of the relationship, allowing precedence
	 *         ordering of links
	 * @return the asset link
	 * @throws PortalException if the user could not be found
	 */
	@Override
	public AssetLink addLink(
			long userId, long entryId1, long entryId2, int type, int weight)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long linkId = counterLocalService.increment();

		AssetLink link = assetLinkPersistence.create(linkId);

		link.setCompanyId(user.getCompanyId());
		link.setUserId(user.getUserId());
		link.setUserName(user.getFullName());
		link.setCreateDate(now);
		link.setEntryId1(entryId1);
		link.setEntryId2(entryId2);
		link.setType(type);
		link.setWeight(weight);

		assetLinkPersistence.update(link);

		if (AssetLinkConstants.isTypeBi(type)) {
			long linkId2 = counterLocalService.increment();

			AssetLink link2 = assetLinkPersistence.create(linkId2);

			link2.setCompanyId(user.getCompanyId());
			link2.setUserId(user.getUserId());
			link2.setUserName(user.getFullName());
			link2.setCreateDate(now);
			link2.setEntryId1(entryId2);
			link2.setEntryId2(entryId1);
			link2.setType(type);
			link2.setWeight(weight);

			assetLinkPersistence.update(link2);
		}

		return link;
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param  link the asset link
	 */
	@Override
	public void deleteLink(AssetLink link) {
		if (AssetLinkConstants.isTypeBi(link.getType())) {
			try {
				assetLinkPersistence.removeByE_E_T(
					link.getEntryId2(), link.getEntryId1(), link.getType());
			}
			catch (NoSuchLinkException nsle) {
			}
		}

		assetLinkPersistence.remove(link);
	}

	/**
	 * Deletes the asset link.
	 *
	 * @param  linkId the primary key of the asset link
	 * @throws PortalException if the asset link could not be found
	 */
	@Override
	public void deleteLink(long linkId) throws PortalException {
		AssetLink link = assetLinkPersistence.findByPrimaryKey(linkId);

		deleteLink(link);
	}

	/**
	 * Deletes all links associated with the asset entry.
	 *
	 * @param  entryId the primary key of the asset entry
	 */
	@Override
	public void deleteLinks(long entryId) {
		for (AssetLink link : assetLinkPersistence.findByE1(entryId)) {
			deleteLink(link);
		}

		for (AssetLink link : assetLinkPersistence.findByE2(entryId)) {
			deleteLink(link);
		}
	}

	/**
	 * Delete all links that associate the two asset entries.
	 *
	 * @param  entryId1 the primary key of the first asset entry
	 * @param  entryId2 the primary key of the second asset entry
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
		List<AssetLink> assetLinks = assetLinkPersistence.findByE1(entryId);

		if (!assetLinks.isEmpty()) {
			List<AssetLink> filteredAssetLinks = new ArrayList<AssetLink>(
				assetLinks.size());

			for (AssetLink assetLink : assetLinks) {
				AssetEntry assetEntry = assetEntryPersistence.fetchByPrimaryKey(
					assetLink.getEntryId2());

				if ((assetEntry != null) && assetEntry.isVisible()) {
					filteredAssetLinks.add(assetLink);
				}
			}

			assetLinks = Collections.unmodifiableList(filteredAssetLinks);
		}

		return assetLinks;
	}

	/**
	 * Returns all the asset links of the given link type whose first entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset links of the given link type whose first entry ID is
	 *         the given entry ID
	 */
	@Override
	public List<AssetLink> getDirectLinks(long entryId, int typeId) {
		List<AssetLink> assetLinks = assetLinkPersistence.findByE1_T(
			entryId, typeId);

		if (!assetLinks.isEmpty()) {
			List<AssetLink> filteredAssetLinks = new ArrayList<AssetLink>(
				assetLinks.size());

			for (AssetLink assetLink : assetLinks) {
				AssetEntry assetEntry = assetEntryPersistence.fetchByPrimaryKey(
					assetLink.getEntryId2());

				if ((assetEntry != null) && assetEntry.isVisible()) {
					filteredAssetLinks.add(assetLink);
				}
			}

			assetLinks = Collections.unmodifiableList(filteredAssetLinks);
		}

		return assetLinks;
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
		List<AssetLink> e1Links = assetLinkPersistence.findByE1(entryId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2(entryId);

		List<AssetLink> links = new ArrayList<AssetLink>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	/**
	 * Returns all the asset links of the given link type whose first or second
	 * entry ID is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @return the asset links of the given link type whose first or second
	 *         entry ID is the given entry ID
	 */
	@Override
	public List<AssetLink> getLinks(long entryId, int typeId) {
		List<AssetLink> e1Links = assetLinkPersistence.findByE1_T(
			entryId, typeId);
		List<AssetLink> e2Links = assetLinkPersistence.findByE2_T(
			entryId, typeId);

		List<AssetLink> links = new ArrayList<AssetLink>(
			e1Links.size() + e2Links.size());

		links.addAll(e1Links);
		links.addAll(e2Links);

		return links;
	}

	/**
	 * Returns all the asset links of the given link type whose second entry ID
	 * is the given entry ID.
	 *
	 * @param  entryId the primary key of the asset entry
	 * @param  typeId the link type. Acceptable values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
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

		assetLinkPersistence.update(assetLink);

		return assetLink;
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
	 * @param  userId the primary key of the user updating the links
	 * @param  entryId the primary key of the asset entry to be managed
	 * @param  linkEntryIds the primary keys of the asset entries to be linked
	 *         with the asset entry to be managed
	 * @param  typeId the type of the asset links to be created. Acceptable
	 *         values include {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	 *         which is a bidirectional relationship and {@link
	 *         com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	 *         which is a unidirectional relationship. For more information see
	 *         {@link com.liferay.portlet.asset.model.AssetLinkConstants}
	 * @throws PortalException if the user could not be found
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

				deleteLink(link);
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

}