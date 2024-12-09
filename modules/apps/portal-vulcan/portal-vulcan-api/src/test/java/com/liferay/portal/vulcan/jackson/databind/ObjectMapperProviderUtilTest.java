/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jackson.databind;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import javax.validation.Valid;

import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class ObjectMapperProviderUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void test() throws Exception {
		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		ObjectWriter writer = objectMapper.writer();

		Assert.assertEquals(
			"{\n  \"type\" : \"test\"\n}",
			writer.writeValueAsString(new TestSubClass()));
		Assert.assertEquals(
			"{\n  \"type\" : \"ObjectMapperProviderUtilTest$1\"\n}",
			writer.writeValueAsString(
				new TestSubClass() {
					{
					}
				}));
		Assert.assertEquals(
			"{\n  \"type\" : \"ObjectMapperProviderUtilTest$2\",\n  \"type\" " +
				": \"test\"\n}",
			writer.writeValueAsString(
				new TestSubClass() {
					{
						type = "test";
					}
				}));
	}

	@JsonSubTypes(
		{@JsonSubTypes.Type(name = "test", value = TestSubClass.class)}
	)
	@JsonTypeInfo(
		include = JsonTypeInfo.As.PROPERTY, property = "type",
		use = JsonTypeInfo.Id.NAME, visible = true
	)
	@XmlRootElement(name = "TestClass")
	public abstract static class TestClass implements Serializable {

		@JsonGetter("type")
		@Valid
		public String getType() {
			return type;
		}

		@JsonProperty(access = JsonProperty.Access.READ_WRITE)
		protected String type;

	}

	public static class TestSubClass extends TestClass {
	}

}