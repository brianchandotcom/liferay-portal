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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import com.liferay.headless.foundation.dto.v1_0.AnotherDTO;
import com.liferay.headless.foundation.dto.v1_0.MyDTO;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class ValidArrayMyDTOJSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testEmpty() throws Exception {
		String fileContent = getFileContent("array/empty.json");

		MyDTO[] myDTOS = MyDTO.toMyDtos(fileContent);

		Assert.assertNotNull(myDTOS);
		Assert.assertTrue(myDTOS.toString(), myDTOS.length == 0);
	}

	@Test
	public void testEmptyWhitespace() {
		MyDTO[] myDTOS = MyDTO.toMyDtos(" [ ] ");

		Assert.assertNotNull(myDTOS);
		Assert.assertTrue(myDTOS.toString(), myDTOS.length == 0);
	}

	@Test
	public void testFieldObject() throws Exception {
		String fileContent = getFileContent("array/field-object.json");

		MyDTO[] myDTOS = MyDTO.toMyDtos(fileContent);

		Assert.assertNotNull(myDTOS);
		Assert.assertEquals(Arrays.toString(myDTOS), 2, myDTOS.length);

		for (MyDTO myDTO : myDTOS) {
			AnotherDTO anotherDTO = myDTO.getF9();

			Assert.assertNotNull(anotherDTO);

			Assert.assertEquals("w1", anotherDTO.getA1());
			Assert.assertEquals(true, anotherDTO.getA2());
			Assert.assertEquals((Long)9223372036854775807L, anotherDTO.getA3());
			Assert.assertEquals((Integer)(-2147483648), anotherDTO.getA4());
			Assert.assertArrayEquals(
				new String[] {"w1", "w2", "w3"}, anotherDTO.getA5());
			Assert.assertArrayEquals(
				new Boolean[] {true, false, null}, anotherDTO.getA6());
			Assert.assertArrayEquals(
				new Long[] {-9223372036854775808L, 0L, 9223372036854775807L},
				anotherDTO.getA7());
			Assert.assertArrayEquals(
				new Integer[] {-2147483648, 0, 2147483647}, anotherDTO.getA8());

			MyDTO a9MyDTO = anotherDTO.getA9();

			Assert.assertEquals("u1", a9MyDTO.getF1());

			MyDTO[] nestedMyDTO = anotherDTO.getA10();

			Assert.assertNotNull(nestedMyDTO);

			Assert.assertEquals("t1", nestedMyDTO[0].getF1());
			Assert.assertEquals("t2", nestedMyDTO[1].getF1());
			Assert.assertEquals("t3", nestedMyDTO[2].getF1());
		}
	}

	@Test
	public void testFieldString() throws Exception {
		String fileContent = getFileContent("array/field-string.json");

		MyDTO[] myDTOS = MyDTO.toMyDtos(fileContent);

		Assert.assertNotNull(myDTOS);
		Assert.assertEquals(Arrays.toString(myDTOS), 3, myDTOS.length);

		Assert.assertEquals("v1", myDTOS[0].getF1());
		Assert.assertEquals("v2", myDTOS[1].getF1());
		Assert.assertEquals("v3", myDTOS[2].getF1());
	}

}