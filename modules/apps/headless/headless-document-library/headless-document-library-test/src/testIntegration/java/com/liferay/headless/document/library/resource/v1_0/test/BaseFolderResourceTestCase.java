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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

	protected void assertResponseCode(int expectedResponseCode, Http.Response actualResponse) {
		Assert.assertEquals(expectedResponseCode, actualResponse.getResponseCode());
	}

	protected Page<Folder> invokeGetContentSpaceFoldersPage(
				Long contentSpaceId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/folders", contentSpaceId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), new TypeReference<Page<FolderImpl>>() {});
	}

	protected Http.Response invokeGetContentSpaceFoldersPageResponse(
				Long contentSpaceId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/folders", contentSpaceId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Folder invokePostContentSpaceFolder(
				Long contentSpaceId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/folders", contentSpaceId));

				options.setPost(true);

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), FolderImpl.class);
	}

	protected Http.Response invokePostContentSpaceFolderResponse(
				Long contentSpaceId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/content-spaces/{content-space-id}/folders", contentSpaceId));

				options.setPost(true);

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected boolean invokeDeleteFolder(
				Long folderId)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setDelete(true);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteFolderResponse(
				Long folderId)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setDelete(true);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Folder invokeGetFolder(
				Long folderId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), FolderImpl.class);
	}

	protected Http.Response invokeGetFolderResponse(
				Long folderId)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Folder invokePutFolder(
				Long folderId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

				options.setPut(true);

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), FolderImpl.class);
	}

	protected Http.Response invokePutFolderResponse(
				Long folderId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}", folderId));

				options.setPut(true);

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Page<Folder> invokeGetFolderFoldersPage(
				Long folderId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), new TypeReference<Page<FolderImpl>>() {});
	}

	protected Http.Response invokeGetFolderFoldersPageResponse(
				Long folderId,Pagination pagination)
			throws Exception {

			Http.Options options = _createHttpOptions();

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}
	protected Folder invokePostFolderFolder(
				Long folderId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

				options.setPost(true);

				return _outputObjectMapper.readValue(HttpUtil.URLtoString(options), FolderImpl.class);
	}

	protected Http.Response invokePostFolderFolderResponse(
				Long folderId,Folder folder)
			throws Exception {

			Http.Options options = _createHttpOptions();

				options.setBody(_inputObjectMapper.writeValueAsString(folder), ContentTypes.APPLICATION_JSON, StringPool.UTF8);

			options.setLocation(_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

				options.setPost(true);

			HttpUtil.URLtoString(options);

			return options.getResponse();
	}

	protected void assertEquals(Folder folder1, Folder folder2) {
		Assert.assertTrue(folder1 + " does not equal " + folder2, equals(folder1, folder2));
	}

	protected void assertEqualsIgnoringOrder(
		List<Folder> folders1, List<Folder> folders2) {

		Assert.assertEquals(folders1.size(), folders2.size());

		for (Folder randomFolder : folders1) {
			Stream<Folder> stream = folders2.stream();

			Folder folder = stream.filter(
				aFolder -> Objects.equals(
					randomFolder.getName(), aFolder.getName())
			).findFirst(
			).get();

			assertEquals(randomFolder, folder);
		}
	}

	protected void assertEquals(List<Folder> folders1, List<Folder> folders2) {
		Assert.assertEquals(folders1.size(), folders2.size());

		for (int i = 0; i < folders1.size(); i++) {
			Folder folder1 = folders1.get(i);
			Folder folder2 = folders2.get(i);

			assertEquals(folder1, folder2);
	}
	}

	protected boolean equals(Folder folder1, Folder folder2) {
		if (folder1 == folder2) {
			return true;
	}

		return false;
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

	protected Group testGroup;

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

	public String toString() {
			StringBundler sb = new StringBundler();

			sb.append("{");

					sb.append("dateCreated=");

				sb.append(dateCreated);
					sb.append(", dateModified=");

				sb.append(dateModified);
					sb.append(", description=");

				sb.append(description);
					sb.append(", hasDocuments=");

				sb.append(hasDocuments);
					sb.append(", hasFolders=");

				sb.append(hasFolders);
					sb.append(", id=");

				sb.append(id);
					sb.append(", name=");

				sb.append(name);
					sb.append(", repositoryId=");

				sb.append(repositoryId);

			sb.append("}");

			return sb.toString();
	}

	}

	protected static class Page<T> {

	public Collection<T> getItems() {
			return new ArrayList<>(items);
	}

	public int getItemsPerPage() {
			return itemsPerPage;
	}

	public int getLastPageNumber() {
			return lastPageNumber;
	}

	public int getPageNumber() {
			return pageNumber;
	}

	public int getTotalCount() {
			return totalCount;
	}

	@JsonProperty
	protected Collection<T> items;

	@JsonProperty
	protected int itemsPerPage;

	@JsonProperty
	protected int lastPageNumber;

	@JsonProperty
	protected int pageNumber;

	@JsonProperty
	protected int totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(userNameAndPassword.getBytes());

		options.addHeader("Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _resourceURL;

}