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

package com.liferay.apio.architect.wiring.osgi.internal.manager.cache;

import static javax.ws.rs.core.Variant.VariantListBuilder.newInstance;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.message.json.DocumentationMessageMapper;
import com.liferay.apio.architect.message.json.ErrorMessageMapper;
import com.liferay.apio.architect.message.json.FormMessageMapper;
import com.liferay.apio.architect.message.json.PageMessageMapper;
import com.liferay.apio.architect.message.json.SingleModelMessageMapper;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.apio.architect.unsafe.Unsafe;
import com.liferay.apio.architect.wiring.osgi.internal.alias.EmptyFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Variant;
import javax.ws.rs.core.Variant.VariantListBuilder;

/**
 * Acts as a central cache for most of the managers.
 *
 * <p>
 * There should only be one instance of this class, accessible through {@link
 * #INSTANCE}.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class ManagerCache {

	/**
	 * The {@link ManagerCache} instance.
	 */
	public static final ManagerCache INSTANCE = new ManagerCache();

	/**
	 * Clears the cache.
	 */
	public void clear() {
		_collectionRoutes = null;
		_documentationMessageMappers = null;
		_errorMessageMappers = null;
		_formMessageMappers = null;
		_identifierClasses = null;
		_itemRoutes = null;
		_names = null;
		_nestedCollectionRoutes = null;
		_pageMessageMappers = null;
		_representors = null;
		_reusableNestedCollectionRoutes = null;
		_rootResourceNames = null;
		_singleModelMessageMappers = null;
	}

	public Map<String, CollectionRoutes> getCollectionRoutes(
		EmptyFunction computeEmptyFunction) {

		if (_collectionRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return _collectionRoutes;
	}

	/**
	 * Returns the collection routes for the collection resource's name.
	 *
	 * @param  name the collection resource's name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the collection routes
	 */
	public <T> Optional<CollectionRoutes<T>> getCollectionRoutesOptional(
		String name, EmptyFunction computeEmptyFunction) {

		if (_collectionRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_collectionRoutes
		).map(
			map -> map.get(name)
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns the documentation message mapper, if present, for the current
	 * request; {@code Optional#empty()} otherwise.
	 *
	 * @param  request the current request
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the documentation message mapper, if present; {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<DocumentationMessageMapper>
		getDocumentationMessageMapperOptional(
			Request request, EmptyFunction computeEmptyFunction) {

		if (_documentationMessageMappers == null) {
			computeEmptyFunction.invoke();
		}

		Optional<DocumentationMessageMapper> optional =
			_getMessageMapperOptional(request, _documentationMessageMappers);

		return optional.map(Unsafe::unsafeCast);
	}

	/**
	 * Returns the error message mapper, if present, for the current request;
	 * {@code Optional#empty()} otherwise.
	 *
	 * @param  request the current request
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the error message mapper, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public Optional<ErrorMessageMapper> getErrorMessageMapperOptional(
		Request request, EmptyFunction computeEmptyFunction) {

		if (_errorMessageMappers == null) {
			computeEmptyFunction.invoke();
		}

		Optional<ErrorMessageMapper> optional = _getMessageMapperOptional(
			request, _errorMessageMappers);

		return optional.map(Unsafe::unsafeCast);
	}

	/**
	 * Returns the form message mapper, if present, for the current request;
	 * {@code Optional#empty()} otherwise.
	 *
	 * @param  request the current request
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the form message mapper, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public Optional<FormMessageMapper> getFormMessageMapperOptional(
		Request request, EmptyFunction computeEmptyFunction) {

		if (_formMessageMappers == null) {
			computeEmptyFunction.invoke();
		}

		Optional<FormMessageMapper> optional = _getMessageMapperOptional(
			request, _formMessageMappers);

		return optional.map(Unsafe::unsafeCast);
	}

	/**
	 * Returns the resource name's identifier class.
	 *
	 * @param  name the resource name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the resource name's identifier class
	 */
	public <T extends Identifier> Optional<Class<T>> getIdentifierClassOptional(
		String name, EmptyFunction computeEmptyFunction) {

		if (_identifierClasses == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_identifierClasses
		).map(
			map -> map.get(name)
		).map(
			Unsafe::unsafeCast
		);
	}

	public Map<String, ItemRoutes> getItemRoutesMap(
		EmptyFunction computeEmptyFunction) {

		if (_itemRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return _itemRoutes;
	}

	/**
	 * Returns the item routes for the item resource's name.
	 *
	 * @param  name the item resource's name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the item routes
	 */
	public <T, S> Optional<ItemRoutes<T, S>> getItemRoutesOptional(
		String name, EmptyFunction computeEmptyFunction) {

		if (_itemRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_itemRoutes
		).map(
			map -> map.get(name)
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns the name of a collection resource that matches the specified
	 * class name.
	 *
	 * @param  className the collection resource's class name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the collection resource's name
	 */
	public Optional<String> getNameOptional(
		String className, EmptyFunction computeEmptyFunction) {

		if (_names == null) {
			computeEmptyFunction.invoke();
		}

		Optional<Map<String, String>> optional = getNamesOptional();

		return optional.map(map -> map.get(className));
	}

	/**
	 * Returns the map containing the names for the different resource
	 * identifier classes, if they've been set; returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @return the map containing the names for the different resource
	 *         identifier classes, if they've been set; returns {@code
	 *         Optional#empty()} otherwise
	 */
	public Optional<Map<String, String>> getNamesOptional() {
		return Optional.ofNullable(_names);
	}

	/**
	 * Returns the nested collection routes for the nested collection resource's
	 * name.
	 *
	 * @param  name the parent resource's name
	 * @param  nestedName the nested collection resource's name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the nested collection routes
	 */
	public <S, T> Optional<NestedCollectionRoutes<T, S>>
		getNestedCollectionRoutesOptional(
			String name, String nestedName,
			EmptyFunction computeEmptyFunction) {

		if (_nestedCollectionRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_nestedCollectionRoutes
		).map(
			map -> map.get(name + "-" + nestedName)
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns the page message mapper, if present, for the current request;
	 * {@code Optional#empty()} otherwise.
	 *
	 * @param  request the current request
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the page message mapper, if present; {@code Optional#empty()}
	 *         otherwise
	 */
	public <T> Optional<PageMessageMapper<T>> getPageMessageMapperOptional(
		Request request, EmptyFunction computeEmptyFunction) {

		if (_pageMessageMappers == null) {
			computeEmptyFunction.invoke();
		}

		Optional<PageMessageMapper> optional = _getMessageMapperOptional(
			request, _pageMessageMappers);

		return optional.map(Unsafe::unsafeCast);
	}

	public Map<String, Representor> getRepresentorMap(
		EmptyFunction computeEmptyFunction) {

		if (_representors == null) {
			computeEmptyFunction.invoke();
		}

		return _representors;
	}

	/**
	 * Returns the representor, if present, of the collection resource's model
	 * class; {@code Optional#empty()} otherwise.
	 *
	 * @param  name the representor's name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the representor, if present; {@code Optional#empty()} otherwise
	 */
	public <U, T> Optional<Representor<T>> getRepresentorOptional(
		String name, EmptyFunction computeEmptyFunction) {

		if (_representors == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_representors
		).map(
			map -> map.get(name)
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns the nested collection routes for the reusable nested collection
	 * resource's name.
	 *
	 * @param  name the reusable nested collection resource's name
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the nested collection routes
	 */
	public <S, T> Optional<NestedCollectionRoutes<T, S>>
		getReusableNestedCollectionRoutesOptional(
			String name, EmptyFunction computeEmptyFunction) {

		if (_reusableNestedCollectionRoutes == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_reusableNestedCollectionRoutes
		).map(
			map -> map.get(name)
		).map(
			Unsafe::unsafeCast
		);
	}

	/**
	 * Returns a list containing the names of the root resources with routes.
	 *
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the list of root resources
	 */
	public List<String> getRootResourceNames(
		EmptyFunction computeEmptyFunction) {

		if (_rootResourceNames == null) {
			computeEmptyFunction.invoke();
		}

		return Optional.ofNullable(
			_rootResourceNames
		).orElseGet(
			Collections::emptyList
		);
	}

	/**
	 * Returns the single model message mapper, if present, for the current
	 * request; {@code Optional#empty()} otherwise.
	 *
	 * @param  request the current request
	 * @param  computeEmptyFunction the function that can be called to compute
	 *         the data
	 * @return the single model message mapper, if present; {@code
	 *         Optional#empty()} otherwise
	 */
	public <T> Optional<SingleModelMessageMapper<T>>
		getSingleModelMessageMapperOptional(
			Request request, EmptyFunction computeEmptyFunction) {

		if (_singleModelMessageMappers == null) {
			computeEmptyFunction.invoke();
		}

		Optional<SingleModelMessageMapper> optional = _getMessageMapperOptional(
			request, _singleModelMessageMappers);

		return optional.map(Unsafe::unsafeCast);
	}

	/**
	 * Adds collection routes.
	 *
	 * @param key the key
	 * @param collectionRoutes the collection routes
	 */
	public void putCollectionRoutes(
		String key, CollectionRoutes collectionRoutes) {

		if (_collectionRoutes == null) {
			_collectionRoutes = new HashMap<>();
		}

		_collectionRoutes.put(key, collectionRoutes);
	}

	/**
	 * Adds a documentation message mapper.
	 *
	 * @param mediaType the media type
	 * @param documentationMessageMapper the documentation message mapper
	 */
	public void putDocumentationMessageMapper(
		MediaType mediaType,
		DocumentationMessageMapper documentationMessageMapper) {

		if (_documentationMessageMappers == null) {
			_documentationMessageMappers = new HashMap<>();
		}

		_documentationMessageMappers.put(mediaType, documentationMessageMapper);
	}

	/**
	 * Adds an error message mapper.
	 *
	 * @param mediaType the media type
	 * @param errorMessageMapper the error message mapper
	 */
	public void putErrorMessageMapper(
		MediaType mediaType, ErrorMessageMapper errorMessageMapper) {

		if (_errorMessageMappers == null) {
			_errorMessageMappers = new HashMap<>();
		}

		_errorMessageMappers.put(mediaType, errorMessageMapper);
	}

	/**
	 * Adds a form message mapper.
	 *
	 * @param mediaType the media type
	 * @param formMessageMapper the form message mapper
	 */
	public void putFormMessageMapper(
		MediaType mediaType, FormMessageMapper formMessageMapper) {

		if (_formMessageMappers == null) {
			_formMessageMappers = new HashMap<>();
		}

		_formMessageMappers.put(mediaType, formMessageMapper);
	}

	/**
	 * Adds an identifier class.
	 *
	 * @param key the key
	 * @param identifierClass the identifier class
	 */
	public void putIdentifierClass(
		String key, Class<Identifier> identifierClass) {

		if (_identifierClasses == null) {
			_identifierClasses = new HashMap<>();
		}

		_identifierClasses.put(key, identifierClass);
	}

	/**
	 * Adds item routes.
	 *
	 * @param key the key
	 * @param itemRoutes the item routes
	 */
	public void putItemRoutes(String key, ItemRoutes itemRoutes) {
		if (_itemRoutes == null) {
			_itemRoutes = new HashMap<>();
		}

		_itemRoutes.put(key, itemRoutes);
	}

	/**
	 * Adds a resource name.
	 *
	 * @param key the key
	 * @param name the resource name
	 */
	public void putName(String key, String name) {
		if (_names == null) {
			_names = new HashMap<>();
		}

		_names.put(key, name);
	}

	/**
	 * Adds nested collection routes.
	 *
	 * @param key the key
	 * @param nestedCollectionRoutes the nested collection routes
	 */
	public void putNestedCollectionRoutes(
		String key, NestedCollectionRoutes nestedCollectionRoutes) {

		if (_nestedCollectionRoutes == null) {
			_nestedCollectionRoutes = new HashMap<>();
		}

		_nestedCollectionRoutes.put(key, nestedCollectionRoutes);
	}

	/**
	 * Adds a page message mapper.
	 *
	 * @param mediaType the media type
	 * @param pageMessageMapper the page message mapper
	 */
	public void putPageMessageMapper(
		MediaType mediaType, PageMessageMapper pageMessageMapper) {

		if (_pageMessageMappers == null) {
			_pageMessageMappers = new HashMap<>();
		}

		_pageMessageMappers.put(mediaType, pageMessageMapper);
	}

	/**
	 * Adds a representor.
	 *
	 * @param key the key
	 * @param representor the representor
	 */
	public void putRepresentor(String key, Representor representor) {
		if (_representors == null) {
			_representors = new HashMap<>();
		}

		_representors.put(key, representor);
	}

	/**
	 * Adds reusable nested collection routes.
	 *
	 * @param key the key
	 * @param reusableNestedCollectionRoutes the reusable nested collection
	 *        routes
	 */
	public void putReusableNestedCollectionRoutes(
		String key, NestedCollectionRoutes reusableNestedCollectionRoutes) {

		if (_reusableNestedCollectionRoutes == null) {
			_reusableNestedCollectionRoutes = new HashMap<>();
		}

		_reusableNestedCollectionRoutes.put(
			key, reusableNestedCollectionRoutes);
	}

	/**
	 * Adds a root resource name.
	 *
	 * @param rootResourceName the root resource name
	 */
	public void putRootResourceName(String rootResourceName) {
		if (_rootResourceNames == null) {
			_rootResourceNames = new ArrayList<>();
		}

		_rootResourceNames.add(rootResourceName);
	}

	/**
	 * Adds a single model message mapper.
	 *
	 * @param mediaType the media type
	 * @param singleModelMessageMapper the single model message mapper
	 */
	public void putSingleModelMessageMapper(
		MediaType mediaType,
		SingleModelMessageMapper singleModelMessageMapper) {

		if (_singleModelMessageMappers == null) {
			_singleModelMessageMappers = new HashMap<>();
		}

		_singleModelMessageMappers.put(mediaType, singleModelMessageMapper);
	}

	private ManagerCache() {
	}

	private <T> Optional<T> _getMessageMapperOptional(
		Request request, Map<MediaType, T> messageMappers) {

		return Optional.ofNullable(
			messageMappers
		).map(
			Map::keySet
		).map(
			Set::stream
		).map(
			stream -> stream.toArray(MediaType[]::new)
		).map(
			this::_getVariantListBuilder
		).map(
			VariantListBuilder::build
		).map(
			request::selectVariant
		).map(
			Variant::getMediaType
		).flatMap(
			mediaType -> Optional.ofNullable(
				messageMappers
			).map(
				map -> map.get(mediaType)
			)
		);
	}

	private VariantListBuilder _getVariantListBuilder(MediaType[] mediaTypes) {
		VariantListBuilder variantListBuilder = newInstance();

		List<MediaType> list = Arrays.asList(mediaTypes);

		if (list.contains(_MEDIA_TYPE)) {
			return variantListBuilder.mediaTypes(
				_MEDIA_TYPE
			).add(
			).mediaTypes(
				mediaTypes
			);
		}

		return variantListBuilder.mediaTypes(mediaTypes);
	}

	private static final MediaType _MEDIA_TYPE = MediaType.valueOf(
		"application/ld+json");

	private Map<String, CollectionRoutes> _collectionRoutes;
	private Map<MediaType, DocumentationMessageMapper>
		_documentationMessageMappers;
	private Map<MediaType, ErrorMessageMapper> _errorMessageMappers;
	private Map<MediaType, FormMessageMapper> _formMessageMappers;
	private Map<String, Class<Identifier>> _identifierClasses;
	private Map<String, ItemRoutes> _itemRoutes;
	private Map<String, String> _names;
	private Map<String, NestedCollectionRoutes> _nestedCollectionRoutes;
	private Map<MediaType, PageMessageMapper> _pageMessageMappers;
	private Map<String, Representor> _representors;
	private Map<String, NestedCollectionRoutes> _reusableNestedCollectionRoutes;
	private List<String> _rootResourceNames;
	private Map<MediaType, SingleModelMessageMapper> _singleModelMessageMappers;

}