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

package com.liferay.object.admin.rest.internal.graphql.query.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectFieldSetting;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectValidationRuleResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setObjectActionResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectActionResource>
			objectActionResourceComponentServiceObjects) {

		_objectActionResourceComponentServiceObjects =
			objectActionResourceComponentServiceObjects;
	}

	public static void setObjectDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectDefinitionResource>
			objectDefinitionResourceComponentServiceObjects) {

		_objectDefinitionResourceComponentServiceObjects =
			objectDefinitionResourceComponentServiceObjects;
	}

	public static void setObjectFieldResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectFieldResource>
			objectFieldResourceComponentServiceObjects) {

		_objectFieldResourceComponentServiceObjects =
			objectFieldResourceComponentServiceObjects;
	}

	public static void setObjectLayoutResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectLayoutResource>
			objectLayoutResourceComponentServiceObjects) {

		_objectLayoutResourceComponentServiceObjects =
			objectLayoutResourceComponentServiceObjects;
	}

	public static void setObjectRelationshipResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectRelationshipResource>
			objectRelationshipResourceComponentServiceObjects) {

		_objectRelationshipResourceComponentServiceObjects =
			objectRelationshipResourceComponentServiceObjects;
	}

	public static void setObjectValidationRuleResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectValidationRuleResource>
			objectValidationRuleResourceComponentServiceObjects) {

		_objectValidationRuleResourceComponentServiceObjects =
			objectValidationRuleResourceComponentServiceObjects;
	}

	public static void setObjectViewResourceComponentServiceObjects(
		ComponentServiceObjects<ObjectViewResource>
			objectViewResourceComponentServiceObjects) {

		_objectViewResourceComponentServiceObjects =
			objectViewResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectAction(objectActionId: ___){actions, active, conditionExpression, dateCreated, dateModified, description, id, name, objectActionExecutorKey, objectActionTriggerKey, parameters, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectAction objectAction(
			@GraphQLName("objectActionId") Long objectActionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> objectActionResource.getObjectAction(
				objectActionId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectActions(objectDefinitionId: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectActionPage objectDefinitionObjectActions(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectActionResource -> new ObjectActionPage(
				objectActionResource.getObjectDefinitionObjectActionsPage(
					objectDefinitionId, search,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitions(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectDefinitionPage objectDefinitions(
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource -> new ObjectDefinitionPage(
				objectDefinitionResource.getObjectDefinitionsPage(
					search,
					_aggregationBiFunction.apply(
						objectDefinitionResource, aggregations),
					_filterBiFunction.apply(
						objectDefinitionResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						objectDefinitionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionByExternalReferenceCode(externalReferenceCode: ___){accountEntryRestricted, accountEntryRestrictedObjectFieldId, actions, active, dateCreated, dateModified, enableCategorization, enableComments, enableObjectEntryHistory, externalReferenceCode, id, label, name, objectActions, objectFields, objectLayouts, objectRelationships, objectViews, panelAppOrder, panelCategoryKey, parameterRequired, pluralLabel, portlet, restContextPath, scope, status, storageType, system, titleObjectFieldName}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectDefinition objectDefinitionByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.
					getObjectDefinitionByExternalReferenceCode(
						externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinition(objectDefinitionId: ___){accountEntryRestricted, accountEntryRestrictedObjectFieldId, actions, active, dateCreated, dateModified, enableCategorization, enableComments, enableObjectEntryHistory, externalReferenceCode, id, label, name, objectActions, objectFields, objectLayouts, objectRelationships, objectViews, panelAppOrder, panelCategoryKey, parameterRequired, pluralLabel, portlet, restContextPath, scope, status, storageType, system, titleObjectFieldName}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectDefinition objectDefinition(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectDefinitionResource ->
				objectDefinitionResource.getObjectDefinition(
					objectDefinitionId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectFields(filter: ___, objectDefinitionId: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectFieldPage objectDefinitionObjectFields(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> new ObjectFieldPage(
				objectFieldResource.getObjectDefinitionObjectFieldsPage(
					objectDefinitionId, search,
					_filterBiFunction.apply(objectFieldResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(objectFieldResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectField(objectFieldId: ___){DBType, actions, businessType, defaultValue, externalReferenceCode, id, indexed, indexedAsKeyword, indexedLanguageId, label, listTypeDefinitionExternalReferenceCode, listTypeDefinitionId, name, objectFieldSettings, relationshipType, required, state, system, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectField objectField(
			@GraphQLName("objectFieldId") Long objectFieldId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectFieldResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectFieldResource -> objectFieldResource.getObjectField(
				objectFieldId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectLayouts(objectDefinitionId: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectLayoutPage objectDefinitionObjectLayouts(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource -> new ObjectLayoutPage(
				objectLayoutResource.getObjectDefinitionObjectLayoutsPage(
					objectDefinitionId, search,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectLayout(objectLayoutId: ___){actions, dateCreated, dateModified, defaultObjectLayout, id, name, objectDefinitionId, objectLayoutTabs}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectLayout objectLayout(
			@GraphQLName("objectLayoutId") Long objectLayoutId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectLayoutResource -> objectLayoutResource.getObjectLayout(
				objectLayoutId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectRelationships(filter: ___, objectDefinitionId: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectRelationshipPage objectDefinitionObjectRelationships(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource -> new ObjectRelationshipPage(
				objectRelationshipResource.
					getObjectDefinitionObjectRelationshipsPage(
						objectDefinitionId, search,
						_filterBiFunction.apply(
							objectRelationshipResource, filterString),
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectRelationship(objectRelationshipId: ___){actions, deletionType, id, label, name, objectDefinitionExternalReferenceCode2, objectDefinitionId1, objectDefinitionId2, objectDefinitionName2, parameterObjectFieldId, reverse, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectRelationship objectRelationship(
			@GraphQLName("objectRelationshipId") Long objectRelationshipId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectRelationshipResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectRelationshipResource ->
				objectRelationshipResource.getObjectRelationship(
					objectRelationshipId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectValidationRules(objectDefinitionId: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectValidationRulePage objectDefinitionObjectValidationRules(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectValidationRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectValidationRuleResource -> new ObjectValidationRulePage(
				objectValidationRuleResource.
					getObjectDefinitionObjectValidationRulesPage(
						objectDefinitionId, search,
						Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectValidationRule(objectValidationRuleId: ___){actions, active, dateCreated, dateModified, engine, engineLabel, errorLabel, id, name, objectDefinitionId, script}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectValidationRule objectValidationRule(
			@GraphQLName("objectValidationRuleId") Long objectValidationRuleId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectValidationRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectValidationRuleResource ->
				objectValidationRuleResource.getObjectValidationRule(
					objectValidationRuleId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectDefinitionObjectViews(objectDefinitionId: ___, page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectViewPage objectDefinitionObjectViews(
			@GraphQLName("objectDefinitionId") Long objectDefinitionId,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> new ObjectViewPage(
				objectViewResource.getObjectDefinitionObjectViewsPage(
					objectDefinitionId, search,
					Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {objectView(objectViewId: ___){actions, dateCreated, dateModified, defaultObjectView, id, name, objectDefinitionId, objectViewColumns, objectViewFilterColumns, objectViewSortColumns}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ObjectView objectView(@GraphQLName("objectViewId") Long objectViewId)
		throws Exception {

		return _applyComponentServiceObjects(
			_objectViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			objectViewResource -> objectViewResource.getObjectView(
				objectViewId));
	}

	@GraphQLTypeExtension(ObjectLayoutTab.class)
	public class GetObjectRelationshipTypeExtension {

		public GetObjectRelationshipTypeExtension(
			ObjectLayoutTab objectLayoutTab) {

			_objectLayoutTab = objectLayoutTab;
		}

		@GraphQLField
		public ObjectRelationship objectRelationship() throws Exception {
			return _applyComponentServiceObjects(
				_objectRelationshipResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				objectRelationshipResource ->
					objectRelationshipResource.getObjectRelationship(
						_objectLayoutTab.getObjectRelationshipId()));
		}

		private ObjectLayoutTab _objectLayoutTab;

	}

	@GraphQLTypeExtension(ObjectView.class)
	public class GetObjectDefinitionTypeExtension {

		public GetObjectDefinitionTypeExtension(ObjectView objectView) {
			_objectView = objectView;
		}

		@GraphQLField
		public ObjectDefinition objectDefinition() throws Exception {
			return _applyComponentServiceObjects(
				_objectDefinitionResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				objectDefinitionResource ->
					objectDefinitionResource.getObjectDefinition(
						_objectView.getObjectDefinitionId()));
		}

		private ObjectView _objectView;

	}

	@GraphQLTypeExtension(ObjectFieldSetting.class)
	public class GetObjectFieldTypeExtension {

		public GetObjectFieldTypeExtension(
			ObjectFieldSetting objectFieldSetting) {

			_objectFieldSetting = objectFieldSetting;
		}

		@GraphQLField
		public ObjectField objectField() throws Exception {
			return _applyComponentServiceObjects(
				_objectFieldResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				objectFieldResource -> objectFieldResource.getObjectField(
					_objectFieldSetting.getObjectFieldId()));
		}

		private ObjectFieldSetting _objectFieldSetting;

	}

	@GraphQLTypeExtension(ObjectDefinition.class)
	public class GetObjectDefinitionObjectValidationRulesPageTypeExtension {

		public GetObjectDefinitionObjectValidationRulesPageTypeExtension(
			ObjectDefinition objectDefinition) {

			_objectDefinition = objectDefinition;
		}

		@GraphQLField
		public ObjectValidationRulePage objectValidationRules(
				@GraphQLName("search") String search,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_objectValidationRuleResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				objectValidationRuleResource -> new ObjectValidationRulePage(
					objectValidationRuleResource.
						getObjectDefinitionObjectValidationRulesPage(
							_objectDefinition.getId(), search,
							Pagination.of(page, pageSize))));
		}

		private ObjectDefinition _objectDefinition;

	}

	@GraphQLName("ObjectActionPage")
	public class ObjectActionPage {

		public ObjectActionPage(Page objectActionPage) {
			actions = objectActionPage.getActions();

			facets = objectActionPage.getFacets();

			items = objectActionPage.getItems();
			lastPage = objectActionPage.getLastPage();
			page = objectActionPage.getPage();
			pageSize = objectActionPage.getPageSize();
			totalCount = objectActionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectAction> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectDefinitionPage")
	public class ObjectDefinitionPage {

		public ObjectDefinitionPage(Page objectDefinitionPage) {
			actions = objectDefinitionPage.getActions();

			facets = objectDefinitionPage.getFacets();

			items = objectDefinitionPage.getItems();
			lastPage = objectDefinitionPage.getLastPage();
			page = objectDefinitionPage.getPage();
			pageSize = objectDefinitionPage.getPageSize();
			totalCount = objectDefinitionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectDefinition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectFieldPage")
	public class ObjectFieldPage {

		public ObjectFieldPage(Page objectFieldPage) {
			actions = objectFieldPage.getActions();

			facets = objectFieldPage.getFacets();

			items = objectFieldPage.getItems();
			lastPage = objectFieldPage.getLastPage();
			page = objectFieldPage.getPage();
			pageSize = objectFieldPage.getPageSize();
			totalCount = objectFieldPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectField> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectLayoutPage")
	public class ObjectLayoutPage {

		public ObjectLayoutPage(Page objectLayoutPage) {
			actions = objectLayoutPage.getActions();

			facets = objectLayoutPage.getFacets();

			items = objectLayoutPage.getItems();
			lastPage = objectLayoutPage.getLastPage();
			page = objectLayoutPage.getPage();
			pageSize = objectLayoutPage.getPageSize();
			totalCount = objectLayoutPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectLayout> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectRelationshipPage")
	public class ObjectRelationshipPage {

		public ObjectRelationshipPage(Page objectRelationshipPage) {
			actions = objectRelationshipPage.getActions();

			facets = objectRelationshipPage.getFacets();

			items = objectRelationshipPage.getItems();
			lastPage = objectRelationshipPage.getLastPage();
			page = objectRelationshipPage.getPage();
			pageSize = objectRelationshipPage.getPageSize();
			totalCount = objectRelationshipPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectRelationship> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectValidationRulePage")
	public class ObjectValidationRulePage {

		public ObjectValidationRulePage(Page objectValidationRulePage) {
			actions = objectValidationRulePage.getActions();

			facets = objectValidationRulePage.getFacets();

			items = objectValidationRulePage.getItems();
			lastPage = objectValidationRulePage.getLastPage();
			page = objectValidationRulePage.getPage();
			pageSize = objectValidationRulePage.getPageSize();
			totalCount = objectValidationRulePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectValidationRule> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ObjectViewPage")
	public class ObjectViewPage {

		public ObjectViewPage(Page objectViewPage) {
			actions = objectViewPage.getActions();

			facets = objectViewPage.getFacets();

			items = objectViewPage.getItems();
			lastPage = objectViewPage.getLastPage();
			page = objectViewPage.getPage();
			pageSize = objectViewPage.getPageSize();
			totalCount = objectViewPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<ObjectView> items;

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

	private void _populateResourceContext(
			ObjectActionResource objectActionResource)
		throws Exception {

		objectActionResource.setContextAcceptLanguage(_acceptLanguage);
		objectActionResource.setContextCompany(_company);
		objectActionResource.setContextHttpServletRequest(_httpServletRequest);
		objectActionResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectActionResource.setContextUriInfo(_uriInfo);
		objectActionResource.setContextUser(_user);
		objectActionResource.setGroupLocalService(_groupLocalService);
		objectActionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectDefinitionResource objectDefinitionResource)
		throws Exception {

		objectDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		objectDefinitionResource.setContextCompany(_company);
		objectDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectDefinitionResource.setContextUriInfo(_uriInfo);
		objectDefinitionResource.setContextUser(_user);
		objectDefinitionResource.setGroupLocalService(_groupLocalService);
		objectDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectFieldResource objectFieldResource)
		throws Exception {

		objectFieldResource.setContextAcceptLanguage(_acceptLanguage);
		objectFieldResource.setContextCompany(_company);
		objectFieldResource.setContextHttpServletRequest(_httpServletRequest);
		objectFieldResource.setContextHttpServletResponse(_httpServletResponse);
		objectFieldResource.setContextUriInfo(_uriInfo);
		objectFieldResource.setContextUser(_user);
		objectFieldResource.setGroupLocalService(_groupLocalService);
		objectFieldResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectLayoutResource objectLayoutResource)
		throws Exception {

		objectLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		objectLayoutResource.setContextCompany(_company);
		objectLayoutResource.setContextHttpServletRequest(_httpServletRequest);
		objectLayoutResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectLayoutResource.setContextUriInfo(_uriInfo);
		objectLayoutResource.setContextUser(_user);
		objectLayoutResource.setGroupLocalService(_groupLocalService);
		objectLayoutResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectRelationshipResource objectRelationshipResource)
		throws Exception {

		objectRelationshipResource.setContextAcceptLanguage(_acceptLanguage);
		objectRelationshipResource.setContextCompany(_company);
		objectRelationshipResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectRelationshipResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectRelationshipResource.setContextUriInfo(_uriInfo);
		objectRelationshipResource.setContextUser(_user);
		objectRelationshipResource.setGroupLocalService(_groupLocalService);
		objectRelationshipResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ObjectValidationRuleResource objectValidationRuleResource)
		throws Exception {

		objectValidationRuleResource.setContextAcceptLanguage(_acceptLanguage);
		objectValidationRuleResource.setContextCompany(_company);
		objectValidationRuleResource.setContextHttpServletRequest(
			_httpServletRequest);
		objectValidationRuleResource.setContextHttpServletResponse(
			_httpServletResponse);
		objectValidationRuleResource.setContextUriInfo(_uriInfo);
		objectValidationRuleResource.setContextUser(_user);
		objectValidationRuleResource.setGroupLocalService(_groupLocalService);
		objectValidationRuleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(ObjectViewResource objectViewResource)
		throws Exception {

		objectViewResource.setContextAcceptLanguage(_acceptLanguage);
		objectViewResource.setContextCompany(_company);
		objectViewResource.setContextHttpServletRequest(_httpServletRequest);
		objectViewResource.setContextHttpServletResponse(_httpServletResponse);
		objectViewResource.setContextUriInfo(_uriInfo);
		objectViewResource.setContextUser(_user);
		objectViewResource.setGroupLocalService(_groupLocalService);
		objectViewResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ObjectActionResource>
		_objectActionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectDefinitionResource>
		_objectDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectFieldResource>
		_objectFieldResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectLayoutResource>
		_objectLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectRelationshipResource>
		_objectRelationshipResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectValidationRuleResource>
		_objectValidationRuleResourceComponentServiceObjects;
	private static ComponentServiceObjects<ObjectViewResource>
		_objectViewResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, List<String>, Aggregation>
		_aggregationBiFunction;
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