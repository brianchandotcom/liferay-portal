/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.internal.item.provider;

import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Locale;

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

		return StringUtil.merge(
			TransformUtil.transform(
				keyLocalizedLabelPairs,
				keyLocalizedLabelPair -> {
					String title = keyLocalizedLabelPair.getLabel(locale);

					if (Validator.isNotNull(title)) {
						return title;
					}

					return keyLocalizedLabelPair.getKey();
				}),
			StringPool.COMMA_AND_SPACE);
	}

}