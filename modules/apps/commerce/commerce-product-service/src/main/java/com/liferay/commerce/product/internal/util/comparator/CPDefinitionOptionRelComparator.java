/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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