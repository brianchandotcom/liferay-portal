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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.pacl.PACLExecutionTestListener;
import com.liferay.portal.security.pacl.PACLIntegrationJUnitTestRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Raymond Augé
 */
@ExecutionTestListeners(listeners = {PACLExecutionTestListener.class})
@RunWith(PACLIntegrationJUnitTestRunner.class)
public class TemplateManagerTest {

	@Test
	public void test1() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("123.ftl", "Hello World!"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("Hello World!", result);
	}

	@Test
	public void test10() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM,
			new StringTemplateResource("123.vm", "#set($sum = 5 + 6)$sum"),
			false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(11, GetterUtil.getInteger(result));
	}

	@Test
	public void test2() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource(
				"123.ftl", "<#if httpUtil??>FAIL<#else>PASS</#if>"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("PASS", result);
	}

	@Test
	public void test3() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource(
				"123.ftl", "<#if !httpUtil??>PASS</#if>"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("PASS", result);
	}

	@Test
	public void test4() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource(
				"123.ftl", "<#if languageUtil??>PASS</#if>"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("PASS", result);
	}

	@Test
	public void test5() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource(
				"123.ftl", "<#assign sum = (5 + 6)>${sum}"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(11, GetterUtil.getInteger(result));
	}

	@Test
	public void test6() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM,
			new StringTemplateResource("123.vm", "Hello World!"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("Hello World!", result);
	}

	@Test
	public void test7() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM,
			new StringTemplateResource(
				"123.vm", "#if ($httpUtil) FAIL #else PASS #end"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString().trim();

		Assert.assertEquals("PASS", result);
	}

	@Test
	public void test8() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM,
			new StringTemplateResource("123.vm", "#if (!$httpUtil)PASS#end"),
			false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("PASS", result);
	}

	@Test
	public void test9() throws Exception {
		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_VM,
			new StringTemplateResource(
				"123.vm", "#if ($languageUtil)PASS#end"), false);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals("PASS", result);
	}

}