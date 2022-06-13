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

package com.liferay.asset.util;

import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class LinkedAssetEntryIdsUtil {

	public static void addLinkedAssetEntryId(
		HttpServletRequest httpServletRequest, long assetEntryId) {

		Set<Long> linkedAssetEntryIds = Optional.ofNullable(
			(Set<Long>)httpServletRequest.getAttribute(
				WebKeys.LINKED_ASSET_ENTRY_IDS)
		).orElse(
			new HashSet<>()
		);

		linkedAssetEntryIds.add(assetEntryId);

		httpServletRequest.setAttribute(
			WebKeys.LINKED_ASSET_ENTRY_IDS, linkedAssetEntryIds);
	}

	public static void addLinkedAssetEntryId(
		PortletRequest portletRequest, long assetEntryId) {

		Set<Long> linkedAssetEntryIds = Optional.ofNullable(
			(Set<Long>)portletRequest.getAttribute(
				WebKeys.LINKED_ASSET_ENTRY_IDS)
		).orElse(
			new HashSet<>()
		);

		linkedAssetEntryIds.add(assetEntryId);

		portletRequest.setAttribute(
			WebKeys.LINKED_ASSET_ENTRY_IDS, linkedAssetEntryIds);
	}

	public static void replaceLinkedAssetEntryId(
		HttpServletRequest httpServletRequest, long oldAssetEntryId,
		long newAssetEntryId) {

		Set<Long> linkedAssetEntryIds = Optional.ofNullable(
			(Set<Long>)httpServletRequest.getAttribute(
				WebKeys.LINKED_ASSET_ENTRY_IDS)
		).orElse(
			new HashSet<>()
		);

		linkedAssetEntryIds.remove(oldAssetEntryId);

		linkedAssetEntryIds.add(newAssetEntryId);

		httpServletRequest.setAttribute(
			WebKeys.LINKED_ASSET_ENTRY_IDS, linkedAssetEntryIds);
	}

}