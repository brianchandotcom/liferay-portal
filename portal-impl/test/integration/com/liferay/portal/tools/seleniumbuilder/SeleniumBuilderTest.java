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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hugo Huijser
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SeleniumBuilderTest {

	@Before
	public void setUp() throws Exception {
		_seleniumBuilderFileUtil = new SeleniumBuilderFileUtil(".");
	}

	@Test
	public void testFunction() throws Exception {
		read("Function.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionCommandElement1() throws Exception {
		read("FunctionCommandElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionCommandElement2() throws Exception {
		read("FunctionCommandElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionCommandElement3() throws Exception {
		read("FunctionCommandElement3.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionCommandElement4() throws Exception {
		read("FunctionCommandElement4.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionCommandElement5() throws Exception {
		read("FunctionCommandElement5.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement1() throws Exception {
		read("FunctionConditionElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement10() throws Exception {
		read("FunctionConditionElement10.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement11() throws Exception {
		read("FunctionConditionElement11.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement12() throws Exception {
		read("FunctionConditionElement12.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement13() throws Exception {
		read("FunctionConditionElement13.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement2() throws Exception {
		read("FunctionConditionElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement3() throws Exception {
		read("FunctionConditionElement3.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement4() throws Exception {
		read("FunctionConditionElement4.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement5() throws Exception {
		read("FunctionConditionElement5.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement6() throws Exception {
		read("FunctionConditionElement6.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement7() throws Exception {
		read("FunctionConditionElement7.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement8() throws Exception {
		read("FunctionConditionElement8.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionConditionElement9() throws Exception {
		read("FunctionConditionElement9.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionDefinitionElement1() throws Exception {
		read("FunctionDefinitionElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionDefinitionElement2() throws Exception {
		read("FunctionDefinitionElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionElseElement1() throws Exception {
		read("FunctionElseElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionElseElement2() throws Exception {
		read("FunctionElseElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement1() throws Exception {
		read("FunctionExecuteElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement10() throws Exception {
		read("FunctionExecuteElement10.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement11() throws Exception {
		read("FunctionExecuteElement11.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement2() throws Exception {
		read("FunctionExecuteElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement3() throws Exception {
		read("FunctionExecuteElement3.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement4() throws Exception {
		read("FunctionExecuteElement4.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement5() throws Exception {
		read("FunctionExecuteElement5.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement6() throws Exception {
		read("FunctionExecuteElement6.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement7() throws Exception {
		read("FunctionExecuteElement7.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement8() throws Exception {
		read("FunctionExecuteElement8.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionExecuteElement9() throws Exception {
		read("FunctionExecuteElement9.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionIfElement1() throws Exception {
		read("FunctionIfElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionIfElement2() throws Exception {
		read("FunctionIfElement2.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionThenElement1() throws Exception {
		read("FunctionThenElement1.function");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFunctionThenElement2() throws Exception {
		read("FunctionThenElement2.function");
	}

	protected void read(String fileName) throws Exception {
		fileName =
			"portal-impl/test/integration/com/liferay/portal/tools/" +
				"seleniumbuilder/dependencies/" + fileName;

		_seleniumBuilderFileUtil.getRootElement(fileName);
	}

	private SeleniumBuilderFileUtil _seleniumBuilderFileUtil;

}