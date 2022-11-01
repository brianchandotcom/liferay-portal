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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoCollectionTextFormatter.class)
public class KeyLocalizedLabelPairCommaSeparatedLabelsInfoTextFormatter
	implements InfoCollectionTextFormatter<KeyLocalizedLabelPair> {

	@Override
	public String format(
		Collection<KeyLocalizedLabelPair> keyLocalizedLabelPairs,
		Locale locale) {

		Stream<KeyLocalizedLabelPair> stream = keyLocalizedLabelPairs.stream();

		return stream.map(
			keyLocalizedLabelPair -> {
				String title = keyLocalizedLabelPair.getLabel(locale);

				if (Validator.isNull(title)) {
					return keyLocalizedLabelPair.getKey();
				}

				return title;
			}
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

}