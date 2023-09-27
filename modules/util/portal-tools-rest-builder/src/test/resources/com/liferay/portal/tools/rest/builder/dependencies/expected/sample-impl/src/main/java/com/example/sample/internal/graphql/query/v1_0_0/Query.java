/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.internal.graphql.query.v1_0_0;

import com.example.sample.dto.v1_0_0.Document;
import com.example.sample.dto.v1_0_0.Folder;
import com.example.sample.dto.v1_0_0.Test;
import com.example.sample.resource.v1_0_0.DocumentResource;
import com.example.sample.resource.v1_0_0.FolderResource;
import com.example.sample.resource.v1_0_0.TestResource;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class Query {

	public static void setDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<DocumentResource>
			documentResourceComponentServiceObjects) {

		_documentResourceComponentServiceObjects =
			documentResourceComponentServiceObjects;
	}

	public static void setFolderResourceComponentServiceObjects(
		ComponentServiceObjects<FolderResource>
			folderResourceComponentServiceObjects) {

		_folderResourceComponentServiceObjects =
			folderResourceComponentServiceObjects;
	}

	public static void setTestResourceComponentServiceObjects(
		ComponentServiceObjects<TestResource>
			testResourceComponentServiceObjects) {

		_testResourceComponentServiceObjects =
			testResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {document(documentId: ___){dateCreated, dateModified, description, documentId, folderId, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Document document(@GraphQLName("documentId") Long documentId)
		throws Exception {

		return _applyComponentServiceObjects(
			_documentResourceComponentServiceObjects,
			this::_populateResourceContext,
			documentResource -> documentResource.getDocument(documentId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {folder{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public FolderPage folder() throws Exception {
		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> new FolderPage(folderResource.getFolderPage()));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {folder(folderId: ___){dateCreated, dateModified, description, documentsRepository, externalReferenceElement1s, id, name, self, subFolders}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Folder folder(@GraphQLName("folderId") Long folderId)
		throws Exception {

		return _applyComponentServiceObjects(
			_folderResourceComponentServiceObjects,
			this::_populateResourceContext,
			folderResource -> folderResource.getFolder(folderId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {test(testId: ___){id, jsonProperty, property-with-hyphens}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Test test(@GraphQLName("testId") Long testId) throws Exception {
		return _applyComponentServiceObjects(
			_testResourceComponentServiceObjects,
			this::_populateResourceContext,
			testResource -> testResource.getTest(testId));
	}

	@GraphQLTypeExtension(Document.class)
	public class GetFolderTypeExtension {

		public GetFolderTypeExtension(Document document) {
			_document = document;
		}

		@GraphQLField
		public Folder folder() throws Exception {
			return _applyComponentServiceObjects(
				_folderResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				folderResource -> folderResource.getFolder(
					_document.getFolderId()));
		}

		private Document _document;

	}

	@GraphQLName("DocumentPage")
	public class DocumentPage {

		public DocumentPage(Page documentPage) {
			actions = documentPage.getActions();

			items = documentPage.getItems();
			lastPage = documentPage.getLastPage();
			page = documentPage.getPage();
			pageSize = documentPage.getPageSize();
			totalCount = documentPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Document> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FolderPage")
	public class FolderPage {

		public FolderPage(Page folderPage) {
			actions = folderPage.getActions();

			items = folderPage.getItems();
			lastPage = folderPage.getLastPage();
			page = folderPage.getPage();
			pageSize = folderPage.getPageSize();
			totalCount = folderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Folder> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TestPage")
	public class TestPage {

		public TestPage(Page testPage) {
			actions = testPage.getActions();

			items = testPage.getItems();
			lastPage = testPage.getLastPage();
			page = testPage.getPage();
			pageSize = testPage.getPageSize();
			totalCount = testPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Test> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(DocumentResource documentResource)
		throws Exception {

		documentResource.setContextAcceptLanguage(_acceptLanguage);
		documentResource.setContextCompany(_company);
		documentResource.setContextHttpServletRequest(_httpServletRequest);
		documentResource.setContextHttpServletResponse(_httpServletResponse);
		documentResource.setContextUriInfo(_uriInfo);
		documentResource.setContextUser(_user);
		documentResource.setGroupLocalService(_groupLocalService);
		documentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(FolderResource folderResource)
		throws Exception {

		folderResource.setContextAcceptLanguage(_acceptLanguage);
		folderResource.setContextCompany(_company);
		folderResource.setContextHttpServletRequest(_httpServletRequest);
		folderResource.setContextHttpServletResponse(_httpServletResponse);
		folderResource.setContextUriInfo(_uriInfo);
		folderResource.setContextUser(_user);
		folderResource.setGroupLocalService(_groupLocalService);
		folderResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(TestResource testResource)
		throws Exception {

		testResource.setContextAcceptLanguage(_acceptLanguage);
		testResource.setContextCompany(_company);
		testResource.setContextHttpServletRequest(_httpServletRequest);
		testResource.setContextHttpServletResponse(_httpServletResponse);
		testResource.setContextUriInfo(_uriInfo);
		testResource.setContextUser(_user);
		testResource.setGroupLocalService(_groupLocalService);
		testResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DocumentResource>
		_documentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FolderResource>
		_folderResourceComponentServiceObjects;
	private static ComponentServiceObjects<TestResource>
		_testResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}