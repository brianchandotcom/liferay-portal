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

package com.liferay.document.library.internal.versioning;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.versioning.VersioningPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = VersioningPolicy.class)
public class AssetVersioningPolicy implements VersioningPolicy {

	@Override
	public Optional<DLVersionNumberIncrease> computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion,
		String[] newTagNames) {

		if (newTagNames == null) {
			newTagNames = new String[0];
		}

		List<String> currentTagNames = _getCurrentTagNames(nextDLFileVersion);

		List<String> newTagNamesList = new ArrayList(
			Arrays.asList(newTagNames));

		Collections.sort(newTagNamesList);

		Collections.sort(currentTagNames);

		if (!currentTagNames.equals(newTagNamesList)) {
			return Optional.of(DLVersionNumberIncrease.AUTOMATIC);
		}

		return Optional.empty();
	}

	private List<String> _getCurrentTagNames(DLFileVersion nextDLFileVersion) {
		List<AssetTag> currentTags = _assetTagLocalService.getEntryTags(
			nextDLFileVersion.getFileEntryId());

		Stream<AssetTag> stream = currentTags.stream();

		return stream.map(
			assetTag -> assetTag.getName()
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}