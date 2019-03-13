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

package com.liferay.portal.odata.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eduardo García
 */
public class NormalizerTest {

	@Test
	public void testNormalizeIdentifierForNotNullValue() {
		String identifier = Normalizer.normalizeIdentifier(
			"Aaa Ááá Üüü B'bb (Ccc) Ñññ d_d _[]+-./&ªº!|\"·$=?¿¡`^*¨´Ç};:-");

		Assert.assertEquals("Aaa_Ááá_Üüü_Bbb_Ccc_Ñññ_d_d__ªºÇ", identifier);
	}

}