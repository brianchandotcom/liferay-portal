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

package com.liferay.commerce.product.internal.util.comparator;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Comparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionOptionRelComparator
	implements Comparator<CPDefinitionOptionRel> {

	@Override
	public int compare(
		CPDefinitionOptionRel cpDefinitionOptionRel1,
		CPDefinitionOptionRel cpDefinitionOptionRel2) {

		int compare = Double.compare(
			cpDefinitionOptionRel1.getPriority(),
			cpDefinitionOptionRel2.getPriority());

		if (compare != 0) {
			return compare;
		}

		String lowerCaseName1 = StringUtil.toLowerCase(
			cpDefinitionOptionRel1.getName());
		String lowerCaseName2 = StringUtil.toLowerCase(
			cpDefinitionOptionRel2.getName());

		compare = lowerCaseName1.compareTo(lowerCaseName2);

		if (compare != 0) {
			return compare;
		}

		return Long.compare(
			cpDefinitionOptionRel1.getCPDefinitionOptionRelId(),
			cpDefinitionOptionRel2.getCPDefinitionOptionRelId());
	}

}