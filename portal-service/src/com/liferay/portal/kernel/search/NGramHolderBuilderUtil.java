/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 */
public class NGramHolderBuilderUtil {

	public static NGramHolder buildNGramHolder(String input)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(input);
	}

	public static NGramHolder buildNGramHolder(String input, int maxNGramLength)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(input, maxNGramLength);
	}

	public static NGramHolder buildNGramHolder(
			String input, int nGramMinLength, int nGramMaxLength)
		throws SearchException {

		return getNGramHolderBuilder().buildNGramHolder(
			input, nGramMinLength, nGramMaxLength);
	}

	public static NGramHolderBuilder getNGramHolderBuilder() {
		PortalRuntimePermission.checkGetBeanProperty(
			NGramHolderBuilderUtil.class);

		return _nGramHolderBuilder;
	}

	public void setNGramHolderBuilder(NGramHolderBuilder nGramHolderBuilder) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_nGramHolderBuilder = nGramHolderBuilder;
	}

	private static NGramHolderBuilder _nGramHolderBuilder;

}