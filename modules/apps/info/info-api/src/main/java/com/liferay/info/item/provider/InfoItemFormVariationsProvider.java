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

package com.liferay.info.item.provider;

import com.liferay.info.item.InfoItemFormVariation;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemFormVariationsProvider<T> {

	public default InfoItemFormVariation getInfoItemFormVariation(
		long groupId, String formVariationKey) {

		Collection<InfoItemFormVariation> infoItemFormVariations =
			getInfoItemFormVariations(groupId);

		Stream<InfoItemFormVariation> stream = infoItemFormVariations.stream();

		Optional<InfoItemFormVariation> infoItemFormVariationOptional =
			stream.filter(
				infoItemFormVariation -> Objects.equals(
					formVariationKey, infoItemFormVariation.getKey())
			).findFirst();

		return infoItemFormVariationOptional.orElse(null);
	}

	public Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long groupId);

	public default Collection<InfoItemFormVariation> getInfoItemFormVariations(
		long[] groupIds) {

		return Collections.emptyList();
	}

}