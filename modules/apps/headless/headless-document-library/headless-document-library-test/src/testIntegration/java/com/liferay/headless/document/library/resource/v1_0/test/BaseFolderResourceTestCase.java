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

package com.liferay.headless.document.library.resource.v1_0.test;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import static com.liferay.portal.kernel.util.ContentTypes.APPLICATION_JSON;
import static com.liferay.portal.kernel.util.Http.Response;
import static com.liferay.portal.kernel.util.Http.Options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.HttpUtil;

import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceFoldersPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostContentSpaceFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testDeleteFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPutFolder() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testGetFolderFoldersPage() throws Exception {
			Assert.assertTrue(true);
	}
	@Test
	public void testPostFolderFolder() throws Exception {
			Assert.assertTrue(true);
	}

	protected Folder invokePostContentSpaceFolder(
		Long contentSpaceId, Folder folder)
		throws Exception {
		Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder), APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(
			_resourceURL + _toPath(
				"/content-spaces/{content-space-id}/folders", contentSpaceId));
		options.setPost(true);

		return _URLtoFolder(options);
	}

	private static Options _createHttpOptions() {
		Options options = new Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private static String _toPath(String template, Long contentSpaceId) {
		return template.replaceAll("\\{.*\\}", String.valueOf(contentSpaceId));
	}

	protected Response invokeDeleteFolderResponse(Long folderId)
		throws Exception {
		Options options = _createHttpOptions();

		options.setDelete(true);
		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		Boolean deleteBoolean = _URLtoBoolean(options);

		Assert.assertEquals(true, deleteBoolean);

		return options.getResponse();
	}

	protected Folder invokeGetFolder(Long folderId) throws Exception {
		Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		return _URLtoFolder(options);
	}

	protected Response invokeGetFolderResponse(Long folderId)
		throws Exception {

		Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		 _URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokePutFolder(Long folderId, Folder folder)
		throws Exception {
		Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder), APPLICATION_JSON,
			StringPool.UTF8
		);
		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));
		options.setPut(true);

		return _URLtoFolder(options);
	}

	protected Folder invokePostFolderFolder(Long folderId, Folder folder)
		throws Exception {
		Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder), APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));
		options.setPost(true);

		return _URLtoFolder(options);
	}

	protected Folder randomFolder() {
		return new FolderImpl() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				hasDocuments = RandomTestUtil.randomBoolean();
				hasFolders = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				repositoryId = RandomTestUtil.randomLong();
			}
		};
	}

	protected static class FolderImpl implements Folder {

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
	public String getDescription() {
				return description;
	}

	public void setDescription(String description) {
				this.description = description;
	}

	@JsonIgnore
	public void setDescription(
				UnsafeSupplier<String, Throwable> descriptionUnsafeSupplier) {

				try {
					description = descriptionUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String description;
	public Boolean getHasDocuments() {
				return hasDocuments;
	}

	public void setHasDocuments(Boolean hasDocuments) {
				this.hasDocuments = hasDocuments;
	}

	@JsonIgnore
	public void setHasDocuments(
				UnsafeSupplier<Boolean, Throwable> hasDocumentsUnsafeSupplier) {

				try {
					hasDocuments = hasDocumentsUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Boolean hasDocuments;
	public Boolean getHasFolders() {
				return hasFolders;
	}

	public void setHasFolders(Boolean hasFolders) {
				this.hasFolders = hasFolders;
	}

	@JsonIgnore
	public void setHasFolders(
				UnsafeSupplier<Boolean, Throwable> hasFoldersUnsafeSupplier) {

				try {
					hasFolders = hasFoldersUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Boolean hasFolders;
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
	public String getName() {
				return name;
	}

	public void setName(String name) {
				this.name = name;
	}

	@JsonIgnore
	public void setName(
				UnsafeSupplier<String, Throwable> nameUnsafeSupplier) {

				try {
					name = nameUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected String name;
	public Long getRepositoryId() {
				return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
				this.repositoryId = repositoryId;
	}

	@JsonIgnore
	public void setRepositoryId(
				UnsafeSupplier<Long, Throwable> repositoryIdUnsafeSupplier) {

				try {
					repositoryId = repositoryIdUnsafeSupplier.get();
	}
				catch (Throwable t) {
					throw new RuntimeException(t);
	}
	}

	@JsonProperty
	protected Long repositoryId;

	}

	protected Group testGroup;

	private static Boolean _URLtoBoolean(Options options)
		throws java.io.IOException {
		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	private static Folder _URLtoFolder(Options options)
		throws java.io.IOException {
		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), FolderImpl.class);
	}

	private static String _URLtoString(Options options)
		throws java.io.IOException {
			return	HttpUtil.URLtoString(options);
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(Include.NON_NULL);
		}
	};

	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}