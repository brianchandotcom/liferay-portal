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

package com.liferay.apio.architect.wiring.osgi.internal.manager.representable;

import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.ITEM_IDENTIFIER_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.resource.ResourceClass.MODEL_CLASS;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getGenericClassFromPropertyOrElse;
import static com.liferay.apio.architect.wiring.osgi.internal.manager.util.ManagerUtil.getTypeParamOrFail;

import com.liferay.apio.architect.consumer.TriConsumer;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.wiring.osgi.internal.manager.base.BaseManager;
import com.liferay.apio.architect.wiring.osgi.manager.representable.RepresentableManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class RepresentableManagerImpl
	extends BaseManager<Representable, Representor>
	implements RepresentableManager {

	public RepresentableManagerImpl() {
		super(Representable.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, U> Optional<Representor<T, U>>
		getRepresentorOptional(Class<T> modelClass) {

		Optional<Representor> optional = getServiceOptional(
			modelClass.getName());

		return optional.map(representor -> (Representor<T, U>)representor);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Representor map(
		Representable representable,
		ServiceReference<Representable> serviceReference, Class<?> modelClass) {

		Class<?> identifierClass = getGenericClassFromPropertyOrElse(
			serviceReference, ITEM_IDENTIFIER_CLASS,
			() -> getTypeParamOrFail(representable, Representable.class, 1));

		Supplier<List<RelatedCollection<?, ?>>> relatedCollectionSupplier =
			() -> _relatedCollections.get(modelClass.getName());

		Representor.Builder builder = new Representor.Builder(
			identifierClass, _addRelatedCollectionTriConsumer(modelClass),
			relatedCollectionSupplier);

		return representable.representor(builder);
	}

	@Override
	protected void onRemovedService(
		ServiceReference<Representable> serviceReference,
		Representor representor) {

		Class<?> modelClass = getGenericClassFromPropertyOrElse(
			serviceReference, MODEL_CLASS,
			() -> getTypeParamOrFail(representor, Representor.class, 0));

		_relatedCollections.forEach(
			(className, relatedCollections) -> relatedCollections.removeIf(
				relatedCollection ->
					relatedCollection.getModelClass().equals(modelClass)));
	}

	private <T> TriConsumer<String, Class<?>, Function<?, ?>>
		_addRelatedCollectionTriConsumer(Class<T> relatedModelClass) {

		return (key, modelClass, identifierFunction) -> {
			List<RelatedCollection<?, ?>> relatedCollections =
				_relatedCollections.computeIfAbsent(
					modelClass.getName(), className -> new ArrayList<>());

			relatedCollections.add(
				new RelatedCollection<>(
					key, relatedModelClass, identifierFunction));
		};
	}

	private final Map<String, List<RelatedCollection<?, ?>>>
		_relatedCollections = new ConcurrentHashMap<>();

}