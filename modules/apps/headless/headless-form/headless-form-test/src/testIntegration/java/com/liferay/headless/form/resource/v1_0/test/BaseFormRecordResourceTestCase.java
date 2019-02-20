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

package com.liferay.headless.form.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.FieldValues;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormRecord;
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

import java.util.Date;

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
public abstract class BaseFormRecordResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-form/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetFormRecord() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutFormRecord() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFormFormRecordsPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostFormFormRecord() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetFormRecord(
				Long formRecordId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/form-records/{form-record-id}",
					formRecordId
				);
	}
	protected Response invokePutFormRecord(
				Long formRecordId,FormRecord formRecord)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.body(
					formRecord
				).when(
				).put(
					_resourceURL + "/form-records/{form-record-id}",
					formRecordId
				);
	}
	protected Response invokeGetFormFormRecordsPage(
				Long formId,Pagination pagination)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/forms/{form-id}/form-records",
					formId
				);
	}
	protected Response invokePostFormFormRecord(
				Long formId,FormRecord formRecord)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.body(
					formRecord
				).when(
				).post(
					_resourceURL + "/forms/{form-id}/form-records",
					formId
				);
	}

	protected FormRecord randomFormRecord() {
		return new FormRecordImpl() {
			{

						dateCreated = RandomTestUtil.nextDate();
						dateModified = RandomTestUtil.nextDate();
						datePublished = RandomTestUtil.nextDate();
						draft = RandomTestUtil.randomBoolean();
						formId = RandomTestUtil.randomLong();
						id = RandomTestUtil.randomLong();
	}
		};
	}

	protected Group testGroup;

	protected class FormRecordImpl implements FormRecord {

	public Creator getCreator() {
				return creator;
	}

	public void setCreator(Creator creator) {
				this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
				UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

				try {
					creator = creatorUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
				return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
				this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
				UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

				try {
					dateCreated = dateCreatedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
				return dateModified;
	}

	public void setDateModified(Date dateModified) {
				this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
				UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

				try {
					dateModified = dateModifiedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date dateModified;
	public Date getDatePublished() {
				return datePublished;
	}

	public void setDatePublished(Date datePublished) {
				this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
				UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

				try {
					datePublished = datePublishedUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Date datePublished;
	public Boolean getDraft() {
				return draft;
	}

	public void setDraft(Boolean draft) {
				this.draft = draft;
	}

	@JsonIgnore
	public void setDraft(
				UnsafeSupplier<Boolean, Throwable> draftUnsafeSupplier) {

				try {
					draft = draftUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Boolean draft;
	public FieldValues[] getFieldValues() {
				return fieldValues;
	}

	public void setFieldValues(FieldValues[] fieldValues) {
				this.fieldValues = fieldValues;
	}

	@JsonIgnore
	public void setFieldValues(
				UnsafeSupplier<FieldValues[], Throwable> fieldValuesUnsafeSupplier) {

				try {
					fieldValues = fieldValuesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected FieldValues[] fieldValues;
	public Form getForm() {
				return form;
	}

	public void setForm(Form form) {
				this.form = form;
	}

	@JsonIgnore
	public void setForm(
				UnsafeSupplier<Form, Throwable> formUnsafeSupplier) {

				try {
					form = formUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Form form;
	public Long getFormId() {
				return formId;
	}

	public void setFormId(Long formId) {
				this.formId = formId;
	}

	@JsonIgnore
	public void setFormId(
				UnsafeSupplier<Long, Throwable> formIdUnsafeSupplier) {

				try {
					formId = formIdUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long formId;
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