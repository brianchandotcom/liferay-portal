/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.graphql.mutation.v1_0;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.SentenceTransformerValidationResult;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;
import com.liferay.search.experiences.rest.resource.v1_0.SentenceTransformerValidationResultResource;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setSXPBlueprintResourceComponentServiceObjects(
		ComponentServiceObjects<SXPBlueprintResource>
			sxpBlueprintResourceComponentServiceObjects) {

		_sxpBlueprintResourceComponentServiceObjects =
			sxpBlueprintResourceComponentServiceObjects;
	}

	public static void setSXPElementResourceComponentServiceObjects(
		ComponentServiceObjects<SXPElementResource>
			sxpElementResourceComponentServiceObjects) {

		_sxpElementResourceComponentServiceObjects =
			sxpElementResourceComponentServiceObjects;
	}

	public static void setSearchResponseResourceComponentServiceObjects(
		ComponentServiceObjects<SearchResponseResource>
			searchResponseResourceComponentServiceObjects) {

		_searchResponseResourceComponentServiceObjects =
			searchResponseResourceComponentServiceObjects;
	}

	public static void
		setSentenceTransformerValidationResultResourceComponentServiceObjects(
			ComponentServiceObjects<SentenceTransformerValidationResultResource>
				sentenceTransformerValidationResultResourceComponentServiceObjects) {

		_sentenceTransformerValidationResultResourceComponentServiceObjects =
			sentenceTransformerValidationResultResourceComponentServiceObjects;
	}

	@GraphQLField
	public SXPBlueprint createSXPBlueprint(
			@GraphQLName("sxpBlueprint") SXPBlueprint sxpBlueprint)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.postSXPBlueprint(
				sxpBlueprint));
	}

	@GraphQLField
	public Response createSXPBlueprintBatch(
			@GraphQLName("sxpBlueprint") SXPBlueprint sxpBlueprint,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.postSXPBlueprintBatch(
				sxpBlueprint, callbackURL, object));
	}

	@GraphQLField
	public SXPBlueprint createSXPBlueprintValidate(
			@GraphQLName("string") String string)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource ->
				sxpBlueprintResource.postSXPBlueprintValidate(string));
	}

	@GraphQLField
	public boolean deleteSXPBlueprint(
			@GraphQLName("sxpBlueprintId") Long sxpBlueprintId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.deleteSXPBlueprint(
				sxpBlueprintId));

		return true;
	}

	@GraphQLField
	public Response deleteSXPBlueprintBatch(
			@GraphQLName("sxpBlueprintId") Long sxpBlueprintId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource ->
				sxpBlueprintResource.deleteSXPBlueprintBatch(
					sxpBlueprintId, callbackURL, object));
	}

	@GraphQLField
	public SXPBlueprint patchSXPBlueprint(
			@GraphQLName("sxpBlueprintId") Long sxpBlueprintId,
			@GraphQLName("sxpBlueprint") SXPBlueprint sxpBlueprint)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.patchSXPBlueprint(
				sxpBlueprintId, sxpBlueprint));
	}

	@GraphQLField
	public SXPBlueprint createSXPBlueprintCopy(
			@GraphQLName("sxpBlueprintId") Long sxpBlueprintId)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.postSXPBlueprintCopy(
				sxpBlueprintId));
	}

	@GraphQLField
	public SXPElement createSXPElement(
			@GraphQLName("sxpElement") SXPElement sxpElement)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.postSXPElement(
				sxpElement));
	}

	@GraphQLField
	public Response createSXPElementBatch(
			@GraphQLName("sxpElement") SXPElement sxpElement,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.postSXPElementBatch(
				sxpElement, callbackURL, object));
	}

	@GraphQLField
	public SXPElement createSXPElementValidate(
			@GraphQLName("string") String string)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.postSXPElementValidate(
				string));
	}

	@GraphQLField
	public boolean deleteSXPElement(
			@GraphQLName("sxpElementId") Long sxpElementId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.deleteSXPElement(
				sxpElementId));

		return true;
	}

	@GraphQLField
	public Response deleteSXPElementBatch(
			@GraphQLName("sxpElementId") Long sxpElementId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.deleteSXPElementBatch(
				sxpElementId, callbackURL, object));
	}

	@GraphQLField
	public SXPElement patchSXPElement(
			@GraphQLName("sxpElementId") Long sxpElementId,
			@GraphQLName("sxpElement") SXPElement sxpElement)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.patchSXPElement(
				sxpElementId, sxpElement));
	}

	@GraphQLField
	public SXPElement createSXPElementCopy(
			@GraphQLName("sxpElementId") Long sxpElementId)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.postSXPElementCopy(
				sxpElementId));
	}

	@GraphQLField
	public SearchResponse createSearch(
			@GraphQLName("query") String query,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sxpBlueprint") SXPBlueprint sxpBlueprint)
		throws Exception {

		return _applyComponentServiceObjects(
			_searchResponseResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchResponseResource -> searchResponseResource.postSearch(
				query, Pagination.of(page, pageSize), sxpBlueprint));
	}

	@GraphQLField
	public SentenceTransformerValidationResult
			createSentenceTransformerValidateConfiguration(
				@GraphQLName("string") String string)
		throws Exception {

		return _applyComponentServiceObjects(
			_sentenceTransformerValidationResultResourceComponentServiceObjects,
			this::_populateResourceContext,
			sentenceTransformerValidationResultResource ->
				sentenceTransformerValidationResultResource.
					postSentenceTransformerValidateConfiguration(string));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			SXPBlueprintResource sxpBlueprintResource)
		throws Exception {

		sxpBlueprintResource.setContextAcceptLanguage(_acceptLanguage);
		sxpBlueprintResource.setContextCompany(_company);
		sxpBlueprintResource.setContextHttpServletRequest(_httpServletRequest);
		sxpBlueprintResource.setContextHttpServletResponse(
			_httpServletResponse);
		sxpBlueprintResource.setContextUriInfo(_uriInfo);
		sxpBlueprintResource.setContextUser(_user);
		sxpBlueprintResource.setGroupLocalService(_groupLocalService);
		sxpBlueprintResource.setRoleLocalService(_roleLocalService);

		sxpBlueprintResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(SXPElementResource sxpElementResource)
		throws Exception {

		sxpElementResource.setContextAcceptLanguage(_acceptLanguage);
		sxpElementResource.setContextCompany(_company);
		sxpElementResource.setContextHttpServletRequest(_httpServletRequest);
		sxpElementResource.setContextHttpServletResponse(_httpServletResponse);
		sxpElementResource.setContextUriInfo(_uriInfo);
		sxpElementResource.setContextUser(_user);
		sxpElementResource.setGroupLocalService(_groupLocalService);
		sxpElementResource.setRoleLocalService(_roleLocalService);

		sxpElementResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(
			SearchResponseResource searchResponseResource)
		throws Exception {

		searchResponseResource.setContextAcceptLanguage(_acceptLanguage);
		searchResponseResource.setContextCompany(_company);
		searchResponseResource.setContextHttpServletRequest(
			_httpServletRequest);
		searchResponseResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchResponseResource.setContextUriInfo(_uriInfo);
		searchResponseResource.setContextUser(_user);
		searchResponseResource.setGroupLocalService(_groupLocalService);
		searchResponseResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SentenceTransformerValidationResultResource
				sentenceTransformerValidationResultResource)
		throws Exception {

		sentenceTransformerValidationResultResource.setContextAcceptLanguage(
			_acceptLanguage);
		sentenceTransformerValidationResultResource.setContextCompany(_company);
		sentenceTransformerValidationResultResource.
			setContextHttpServletRequest(_httpServletRequest);
		sentenceTransformerValidationResultResource.
			setContextHttpServletResponse(_httpServletResponse);
		sentenceTransformerValidationResultResource.setContextUriInfo(_uriInfo);
		sentenceTransformerValidationResultResource.setContextUser(_user);
		sentenceTransformerValidationResultResource.setGroupLocalService(
			_groupLocalService);
		sentenceTransformerValidationResultResource.setRoleLocalService(
			_roleLocalService);
	}

	private static ComponentServiceObjects<SXPBlueprintResource>
		_sxpBlueprintResourceComponentServiceObjects;
	private static ComponentServiceObjects<SXPElementResource>
		_sxpElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchResponseResource>
		_searchResponseResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<SentenceTransformerValidationResultResource>
			_sentenceTransformerValidationResultResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}