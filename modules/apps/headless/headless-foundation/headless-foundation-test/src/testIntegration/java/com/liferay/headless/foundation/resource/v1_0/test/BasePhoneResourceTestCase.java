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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.Phone;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.net.URL;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BasePhoneResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-foundation/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetGenericParentPhonesPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetPhone() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetGenericParentPhonesPage(
				Object genericParentId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/phones",
					genericParentId
				);
	}
	protected Response invokeGetPhone(
				Long phoneId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/phones/{phone-id}",
					phoneId
				);
	}

	protected Phone randomPhone() {
		return new PhoneImpl() {
			{

						extension = RandomTestUtil.randomString();
						id = RandomTestUtil.randomLong();
						phoneNumber = RandomTestUtil.randomString();
						phoneType = RandomTestUtil.randomString();
	}
		};
	}

	protected Group testGroup;

	protected class PhoneImpl implements Phone {

	public String getExtension() {
				return extension;
	}

	public void setExtension(String extension) {
				this.extension = extension;
	}

	@JsonIgnore
	public void setExtension(
				UnsafeSupplier<String, Throwable> extensionUnsafeSupplier) {

				try {
					extension = extensionUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String extension;
	public Long getId() {
				return id;
	}

	public void setId(Long id) {
				this.id = id;
	}

	@JsonIgnore
	public void setId(
				UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {

				try {
					id = idUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long id;
	public String getPhoneNumber() {
				return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
				this.phoneNumber = phoneNumber;
	}

	@JsonIgnore
	public void setPhoneNumber(
				UnsafeSupplier<String, Throwable> phoneNumberUnsafeSupplier) {

				try {
					phoneNumber = phoneNumberUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String phoneNumber;
	public String getPhoneType() {
				return phoneType;
	}

	public void setPhoneType(String phoneType) {
				this.phoneType = phoneType;
	}

	@JsonIgnore
	public void setPhoneType(
				UnsafeSupplier<String, Throwable> phoneTypeUnsafeSupplier) {

				try {
					phoneType = phoneTypeUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String phoneType;

	}

	private RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}