/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.internal.util;

import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.type.Labeled;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class CommaSeparatedInfoCollectionTextFormatter
	implements InfoCollectionTextFormatter<Object> {

	@Override
	public String format(Collection<Object> collection, Locale locale) {
		return StringUtil.merge(
			TransformUtil.transform(
				collection,
				collectionItem -> {
					if (!(collectionItem instanceof Labeled)) {
						return collectionItem.toString();
					}

					Labeled collectionItemLabeled = (Labeled)collectionItem;

					return collectionItemLabeled.getLabel(locale);
				}),
			StringPool.COMMA_AND_SPACE);
	}

}