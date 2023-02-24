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