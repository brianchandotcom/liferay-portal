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

package com.liferay.apio.architect.writer;

import static com.liferay.apio.architect.writer.url.URLCreator.createCollectionPageURL;
import static com.liferay.apio.architect.writer.url.URLCreator.createCollectionURL;
import static com.liferay.apio.architect.writer.url.URLCreator.createNestedCollectionURL;
import static com.liferay.apio.architect.writer.util.WriterUtil.getFieldsWriter;
import static com.liferay.apio.architect.writer.util.WriterUtil.getPathOptional;

import com.google.gson.JsonObject;

import com.liferay.apio.architect.list.FunctionalList;
import com.liferay.apio.architect.message.json.JSONObjectBuilder;
import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.pagination.Page;
import com.liferay.apio.architect.pagination.PageType;
import com.liferay.apio.architect.request.RequestInfo;
import com.liferay.apio.architect.single.model.SingleModel;
import com.liferay.apio.architect.uri.Path;
import com.liferay.apio.architect.writer.alias.PathFunction;
import com.liferay.apio.architect.writer.alias.RepresentorFunction;
import com.liferay.apio.architect.writer.alias.ResourceNameFunction;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Writes a page.
 *
 * @author Alejandro Hernández
 * @param  <T> the model's type
 * @review
 */
public class PageWriter<T> {

	/**
	 * Creates a new {@code PageWriter} object, without creating the builder.
	 *
	 * @param  function the function that creates a {@code PageWriter} from a
	 *         builder
	 * @return the {@code PageWriter} instance
	 */
	public static <T> PageWriter<T> create(
		Function<Builder<T>, PageWriter<T>> function) {

		return function.apply(new Builder<>());
	}

	public PageWriter(Builder<T> builder) {
		_page = builder._page;
		_pageMessageMapper = builder._pageMessageMapper;
		_pathFunction = builder._pathFunction;
		_representorFunction = builder._representorFunction;
		_requestInfo = builder._requestInfo;
		_resourceNameFunction = builder._resourceNameFunction;

		_jsonObjectBuilder = new JSONObjectBuilder();
	}

	/**
	 * Writes the handled {@link Page} to a string. This method uses a {@link
	 * FieldsWriter} to write the different fields of its items' {@link
	 * com.liferay.apio.architect.representor.Representor}. If no {@code
	 * Representor} or {@code Path} exist for the model, this method returns
	 * {@code Optional#empty()}.
	 *
	 * @return the representation of the {@code Page}, if the {@code
	 *         Representor} and {@code Path} exist for the model; returns {@code
	 *         Optional#empty()} otherwise
	 */
	public String write() {
		_pageMessageMapper.onStart(
			_jsonObjectBuilder, _page, _requestInfo.getHttpHeaders());

		_pageMessageMapper.mapItemTotalCount(
			_jsonObjectBuilder, _page.getTotalCount());

		Collection<T> items = _page.getItems();

		_pageMessageMapper.mapPageCount(_jsonObjectBuilder, items.size());

		_writePageURLs();

		Optional<String> optional = _getCollectionURLOptional();

		optional.ifPresent(
			url -> _pageMessageMapper.mapCollectionURL(
				_jsonObjectBuilder, url));

		items.forEach(
			model -> _writeItem(
				new SingleModel<>(model, _page.getModelClass())));

		_pageMessageMapper.onFinish(
			_jsonObjectBuilder, _page, _requestInfo.getHttpHeaders());

		JsonObject jsonObject = _jsonObjectBuilder.build();

		return jsonObject.toString();
	}

	/**
	 * Creates {@code PageWriter} instances.
	 *
	 * @param  <T> the model's type
	 * @review
	 */
	public static class Builder<T> {

		/**
		 * Adds information about the page being written to the builder.
		 *
		 * @param  page the page being written
		 * @return the updated builder
		 */
		public PageMessageMapperStep page(Page<T> page) {
			_page = page;

			return new PageMessageMapperStep();
		}

		public class BuildStep {

			/**
			 * Constructs and returns a {@code PageWriter} instance with the
			 * information provided to the builder.
			 *
			 * @return the {@code PageWriter} instance
			 */
			public PageWriter<T> build() {
				return new PageWriter<>(Builder.this);
			}

		}

		public class PageMessageMapperStep {

			/**
			 * Adds information to the builder about the {@link
			 * PageMessageMapper}.
			 *
			 * @param  pageMessageMapper the {@code PageMessageMapper} headers.
			 * @return the updated builder
			 */
			public PathFunctionStep pageMessageMapper(
				PageMessageMapper<T> pageMessageMapper) {

				_pageMessageMapper = pageMessageMapper;

				return new PathFunctionStep();
			}

		}

		public class PathFunctionStep {

			/**
			 * Adds information to the builder about the function that converts
			 * an identifier to a {@link Path}.
			 *
			 * @param  pathFunction the function to map an identifier to a
			 *         {@code Path}
			 * @return the updated builder
			 */
			public ResourceNameFunctionStep pathFunction(
				PathFunction pathFunction) {

				_pathFunction = pathFunction;

				return new ResourceNameFunctionStep();
			}

		}

		public class RepresentorFunctionStep {

			/**
			 * Adds information to the builder about the function that gets a
			 * class's {@link
			 * com.liferay.apio.architect.representor.Representor}.
			 *
			 * @param  representorFunction the function that gets a class's
			 *         {@code Representor}
			 * @return the updated builder
			 */
			public RequestInfoStep representorFunction(
				RepresentorFunction representorFunction) {

				_representorFunction = representorFunction;

				return new RequestInfoStep();
			}

		}

		public class RequestInfoStep {

			/**
			 * Adds information to the builder about the request.
			 *
			 * @param  requestInfo the information obtained from the request.
			 *         This can be created by using a {@link
			 *         RequestInfo.Builder}
			 * @return the updated builder
			 */
			public BuildStep requestInfo(RequestInfo requestInfo) {
				_requestInfo = requestInfo;

				return new BuildStep();
			}

		}

		public class ResourceNameFunctionStep {

			/**
			 * Adds information to the builder about the function that gets the
			 * name of a class's {@link
			 * com.liferay.apio.architect.representor.Representor}.
			 *
			 * @param  resourceNameFunction the function that gets the name of a
			 *         class's {@code Representor}
			 * @return the updated builder
			 */
			public RepresentorFunctionStep resourceNameFunction(
				ResourceNameFunction resourceNameFunction) {

				_resourceNameFunction = resourceNameFunction;

				return new RepresentorFunctionStep();
			}

		}

		private Page<T> _page;
		private PageMessageMapper<T> _pageMessageMapper;
		private PathFunction _pathFunction;
		private RepresentorFunction _representorFunction;
		private RequestInfo _requestInfo;
		private ResourceNameFunction _resourceNameFunction;

	}

	private Optional<String> _getCollectionURLOptional() {
		Class<T> modelClass = _page.getModelClass();

		Optional<String> optional = _resourceNameFunction.apply(
			modelClass.getName());

		return optional.map(
			name -> {
				Optional<Path> pathOptional = _page.getPathOptional();

				if (pathOptional.isPresent()) {
					return createNestedCollectionURL(
						_requestInfo.getServerURL(), pathOptional.get(), name);
				}

				return createCollectionURL(_requestInfo.getServerURL(), name);
			});
	}

	private void _writeItem(SingleModel<T> singleModel) {
		Optional<FieldsWriter<T, ?>> optional = getFieldsWriter(
			singleModel, null, _requestInfo, _pathFunction,
			_representorFunction);

		if (!optional.isPresent()) {
			return;
		}

		FieldsWriter<T, ?> fieldsWriter = optional.get();

		JSONObjectBuilder itemJsonObjectBuilder = new JSONObjectBuilder();

		_pageMessageMapper.onStartItem(
			_jsonObjectBuilder, itemJsonObjectBuilder, singleModel.getModel(),
			singleModel.getModelClass(), _requestInfo.getHttpHeaders());

		fieldsWriter.writeBooleanFields(
			(field, value) -> _pageMessageMapper.mapItemBooleanField(
				_jsonObjectBuilder, itemJsonObjectBuilder, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) -> _pageMessageMapper.mapItemStringField(
				_jsonObjectBuilder, itemJsonObjectBuilder, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) -> _pageMessageMapper.mapItemNumberField(
				_jsonObjectBuilder, itemJsonObjectBuilder, field, value));

		fieldsWriter.writeStringFields(
			(field, value) -> _pageMessageMapper.mapItemStringField(
				_jsonObjectBuilder, itemJsonObjectBuilder, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) -> _pageMessageMapper.mapItemLink(
				_jsonObjectBuilder, itemJsonObjectBuilder, fieldName, link));

		fieldsWriter.writeTypes(
			types -> _pageMessageMapper.mapItemTypes(
				_jsonObjectBuilder, itemJsonObjectBuilder, types));

		fieldsWriter.writeBinaries(
			(field, value) -> _pageMessageMapper.mapItemLink(
				_jsonObjectBuilder, itemJsonObjectBuilder, field, value));

		fieldsWriter.writeSingleURL(
			url -> _pageMessageMapper.mapItemSelfURL(
				_jsonObjectBuilder, itemJsonObjectBuilder, url));

		fieldsWriter.writeRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction, _representorFunction),
			(embeddedSingleModel, embeddedPathElements1) ->
				_writeItemEmbeddedModelFields(
					embeddedSingleModel, embeddedPathElements1,
					itemJsonObjectBuilder),
			(resourceURL, embeddedPathElements) ->
				_pageMessageMapper.mapItemLinkedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, resourceURL),
			(resourceURL, embeddedPathElements) ->
				_pageMessageMapper.mapItemEmbeddedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, resourceURL));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, embeddedPathElements) ->
				_pageMessageMapper.mapItemLinkedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, url));

		_pageMessageMapper.onFinishItem(
			_jsonObjectBuilder, itemJsonObjectBuilder, singleModel.getModel(),
			singleModel.getModelClass(), _requestInfo.getHttpHeaders());
	}

	private <S> void _writeItemEmbeddedModelFields(
		SingleModel<S> singleModel, FunctionalList<String> embeddedPathElements,
		JSONObjectBuilder itemJsonObjectBuilder) {

		Optional<FieldsWriter<S, ?>> optional = getFieldsWriter(
			singleModel, embeddedPathElements, _requestInfo, _pathFunction,
			_representorFunction);

		if (!optional.isPresent()) {
			return;
		}

		FieldsWriter<S, ?> fieldsWriter = optional.get();

		fieldsWriter.writeBooleanFields(
			(field, value) ->
				_pageMessageMapper.mapItemEmbeddedResourceBooleanField(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, field, value));

		fieldsWriter.writeLocalizedStringFields(
			(field, value) ->
				_pageMessageMapper.mapItemEmbeddedResourceStringField(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, field, value));

		fieldsWriter.writeNumberFields(
			(field, value) ->
				_pageMessageMapper.mapItemEmbeddedResourceNumberField(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, field, value));

		fieldsWriter.writeStringFields(
			(field, value) ->
				_pageMessageMapper.mapItemEmbeddedResourceStringField(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					embeddedPathElements, field, value));

		fieldsWriter.writeLinks(
			(fieldName, link) -> _pageMessageMapper.mapItemEmbeddedResourceLink(
				_jsonObjectBuilder, itemJsonObjectBuilder, embeddedPathElements,
				fieldName, link));

		fieldsWriter.writeTypes(
			types -> _pageMessageMapper.mapItemEmbeddedResourceTypes(
				_jsonObjectBuilder, itemJsonObjectBuilder, embeddedPathElements,
				types));

		fieldsWriter.writeBinaries(
			(field, value) -> _pageMessageMapper.mapItemEmbeddedResourceLink(
				_jsonObjectBuilder, itemJsonObjectBuilder, embeddedPathElements,
				field, value));

		fieldsWriter.writeRelatedModels(
			embeddedSingleModel -> getPathOptional(
				embeddedSingleModel, _pathFunction, _representorFunction),
			(embeddedSingleModel, embeddedModelEmbeddedPathElements) ->
				_writeItemEmbeddedModelFields(
					embeddedSingleModel, embeddedModelEmbeddedPathElements,
					itemJsonObjectBuilder),
			(resourceURL, resourceEmbeddedPathElements) ->
				_pageMessageMapper.mapItemLinkedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					resourceEmbeddedPathElements, resourceURL),
			(resourceURL, resourceEmbeddedPathElements) ->
				_pageMessageMapper.mapItemEmbeddedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					resourceEmbeddedPathElements, resourceURL));

		fieldsWriter.writeRelatedCollections(
			_resourceNameFunction,
			(url, resourceEmbeddedPathElements) ->
				_pageMessageMapper.mapItemLinkedResourceURL(
					_jsonObjectBuilder, itemJsonObjectBuilder,
					resourceEmbeddedPathElements, url));
	}

	private void _writePageURLs() {
		Optional<String> optional = _getCollectionURLOptional();

		optional.ifPresent(
			url -> {
				_pageMessageMapper.mapCurrentPageURL(
					_jsonObjectBuilder,
					createCollectionPageURL(url, _page, PageType.CURRENT));

				_pageMessageMapper.mapFirstPageURL(
					_jsonObjectBuilder,
					createCollectionPageURL(url, _page, PageType.FIRST));

				_pageMessageMapper.mapLastPageURL(
					_jsonObjectBuilder,
					createCollectionPageURL(url, _page, PageType.LAST));

				if (_page.hasNext()) {
					_pageMessageMapper.mapNextPageURL(
						_jsonObjectBuilder,
						createCollectionPageURL(url, _page, PageType.NEXT));
				}

				if (_page.hasPrevious()) {
					_pageMessageMapper.mapPreviousPageURL(
						_jsonObjectBuilder,
						createCollectionPageURL(url, _page, PageType.PREVIOUS));
				}
			});
	}

	private final JSONObjectBuilder _jsonObjectBuilder;
	private final Page<T> _page;
	private final PageMessageMapper<T> _pageMessageMapper;
	private final PathFunction _pathFunction;
	private final RepresentorFunction _representorFunction;
	private final RequestInfo _requestInfo;
	private final ResourceNameFunction _resourceNameFunction;

}