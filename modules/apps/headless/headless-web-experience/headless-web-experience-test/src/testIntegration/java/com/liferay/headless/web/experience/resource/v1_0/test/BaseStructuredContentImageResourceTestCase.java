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

package com.liferay.headless.web.experience.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.web.experience.dto.v1_0.Creator;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

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
public abstract class BaseStructuredContentImageResourceTestCase {

	@BeforeClass
	public static void setUpClass() {
		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-web-experience/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetStructuredContentStructuredContentImagesPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteStructuredContentContentDocument() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetStructuredContentContentDocument() throws Exception {
			Assert.assertTrue(true);
	}

	protected Response invokeGetStructuredContentStructuredContentImagesPage(
				Long structuredContentId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/structured-contents/{structured-content-id}/structured-content-images",
					structuredContentId
				);
	}
	protected Response invokeDeleteStructuredContentContentDocument(
				Long structuredContentId,Long contentDocumentId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).delete(
					_resourceURL + "/structured-contents/{structured-content-id}/structured-content-images/{content-document-id}",
					structuredContentId,contentDocumentId
				);
	}
	protected Response invokeGetStructuredContentContentDocument(
				Long structuredContentId,Long contentDocumentId)
			throws Exception {

			RequestSpecification requestSpecification = _createRequestSpecification();

				return requestSpecification.when(
				).get(
					_resourceURL + "/structured-contents/{structured-content-id}/structured-content-images/{content-document-id}",
					structuredContentId,contentDocumentId
				);
	}

	protected StructuredContentImage randomStructuredContentImage() {
		return new StructuredContentImageImpl() {
			{

						contentUrl = RandomTestUtil.randomString();
						dateCreated = RandomTestUtil.nextDate();
						dateModified = RandomTestUtil.nextDate();
						encodingFormat = RandomTestUtil.randomString();
						fileExtension = RandomTestUtil.randomString();
						id = RandomTestUtil.randomLong();
						title = RandomTestUtil.randomString();
	}
		};
	}

	protected Group testGroup;

	protected class StructuredContentImageImpl implements StructuredContentImage {

	public String getContentUrl() {
				return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
				this.contentUrl = contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
				UnsafeSupplier<String, Throwable> contentUrlUnsafeSupplier) {

				try {
					contentUrl = contentUrlUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String contentUrl;
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
	public String getEncodingFormat() {
				return encodingFormat;
	}

	public void setEncodingFormat(String encodingFormat) {
				this.encodingFormat = encodingFormat;
	}

	@JsonIgnore
	public void setEncodingFormat(
				UnsafeSupplier<String, Throwable> encodingFormatUnsafeSupplier) {

				try {
					encodingFormat = encodingFormatUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String encodingFormat;
	public String getFileExtension() {
				return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
				this.fileExtension = fileExtension;
	}

	@JsonIgnore
	public void setFileExtension(
				UnsafeSupplier<String, Throwable> fileExtensionUnsafeSupplier) {

				try {
					fileExtension = fileExtensionUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String fileExtension;
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
	public Number getSizeInBytes() {
				return sizeInBytes;
	}

	public void setSizeInBytes(Number sizeInBytes) {
				this.sizeInBytes = sizeInBytes;
	}

	@JsonIgnore
	public void setSizeInBytes(
				UnsafeSupplier<Number, Throwable> sizeInBytesUnsafeSupplier) {

				try {
					sizeInBytes = sizeInBytesUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Number sizeInBytes;
	public String getTitle() {
				return title;
	}

	public void setTitle(String title) {
				this.title = title;
	}

	@JsonIgnore
	public void setTitle(
				UnsafeSupplier<String, Throwable> titleUnsafeSupplier) {

				try {
					title = titleUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String title;

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