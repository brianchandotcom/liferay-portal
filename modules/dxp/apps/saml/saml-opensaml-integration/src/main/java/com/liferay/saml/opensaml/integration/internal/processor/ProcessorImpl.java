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

package com.liferay.saml.opensaml.integration.internal.processor;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.field.expression.handler.FieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.FieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.processor.Processor;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Stian Sigvartsen
 */
public abstract class ProcessorImpl
	<M extends BaseModel<M>, PC extends ProcessorContext<M>,
	 FEH extends FieldExpressionHandler<M, PC>,
	 FEHR extends FieldExpressionHandlerRegistry<M, PC, FEH>>
		implements Processor<M> {

	public ProcessorImpl(M model, FEHR fieldExpressionHandlerRegistry) {
		_model = model;
		_fieldExpressionHandlerRegistry = fieldExpressionHandlerRegistry;
	}

	@Override
	public M process(ServiceContext serviceContext) throws PortalException {
		_preparePatches();

		_consumePatches(_modelUnsafeConsumers, serviceContext);

		_consumePatches(_unsafeConsumers, serviceContext);

		return (M)_objectCache.get(
			new AbstractMap.SimpleEntry<>(_model.getClass(), null));
	}

	@Override
	public <T, V extends T> void setValueArray(
		String fieldExpression, Class<T> clazz, V[] value) {

		Map<String, Object[]> map = _values.computeIfAbsent(
			clazz, c -> new HashMap<>());

		map.put(fieldExpression, value);
	}

	@Override
	public void setValueArray(String fieldExpression, String[] value) {
		setValueArray(fieldExpression, String.class, value);
	}

	public class BindImpl<T extends BaseModel<T>>
		implements ProcessorContext.Bind<T> {

		public BindImpl(
			Function<M, T> modelGetter, int processingIndex,
			ProcessorContext processorContext, String publicIdentifier,
			ProcessorContext.UpdateFunction<T> updateFunction) {

			_processorContext = processorContext;

			_bufferedSetters = getPatchingQueue(
				publicIdentifier, modelGetter, processingIndex, updateFunction);
		}

		public <V, U, Y> void handleMappedUnsafeObject(
			String fieldExpression, Class<V> clazz,
			BiFunction<V, BiFunction<String, Class<Y>, Y>, U> mappingFunction,
			UnsafeBiConsumer<T, U, ?> consumer) {

			_bufferedSetters.add(
				object -> consumer.accept(
					object,
					mappingFunction.apply(
						_processorContext.getValue(fieldExpression, clazz),
						_processorContext::getValue)));
		}

		public <V> void handleUnsafeObjectArray(
			String fieldExpression, Class<V> clazz,
			UnsafeBiConsumer<T, V[], ?> consumer) {

			V[] values = _processorContext.getValueArray(
				fieldExpression, clazz);

			if ((values == null) || (values.length == 0)) {
				return;
			}

			_bufferedSetters.add(object -> consumer.accept(object, values));
		}

		@Override
		public void handleUnsafeStringArray(
			String fieldExpression, UnsafeBiConsumer<T, String[], ?> consumer) {

			handleUnsafeObjectArray(fieldExpression, String.class, consumer);
		}

		@Override
		public void mapBoolean(
			String fieldExpression, BiConsumer<T, Boolean> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, values) -> {
					for (String value : values) {
						consumer.accept(obj, GetterUtil.getBoolean(value));
					}
				});
		}

		@Override
		public void mapBooleanArray(
			String fieldExpression, BiConsumer<T, boolean[]> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, value) -> {
					boolean[] booleanArray = new boolean[value.length];

					for (int i = 0; i < booleanArray.length; i++) {
						booleanArray[i] = GetterUtil.getBoolean(value[i]);
					}

					consumer.accept(obj, booleanArray);
				});
		}

		@Override
		public void mapLong(
			String fieldExpression, BiConsumer<T, Long> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, values) -> {
					for (String value : values) {
						try {
							consumer.accept(obj, Long.parseLong(value));
						}
						catch (NumberFormatException numberFormatException) {
							throw numberFormatException;
						}
					}
				});
		}

		@Override
		public void mapLongArray(
			String fieldExpression, BiConsumer<T, long[]> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, value) -> {
					Stream<String> stream = Arrays.stream(value);

					consumer.accept(
						obj,
						stream.mapToLong(
							Long::parseLong
						).toArray());
				});
		}

		@Override
		public void mapString(
			String fieldExpression, BiConsumer<T, String> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, values) -> consumer.accept(obj, values[0]));
		}

		@Override
		public void mapStringArray(
			String fieldExpression, BiConsumer<T, String[]> consumer) {

			handleUnsafeStringArray(
				fieldExpression, (obj, values) -> consumer.accept(obj, values));
		}

		@Override
		public void mapUnsafeString(
			String fieldExpression, UnsafeBiConsumer<T, String, ?> consumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(obj, values) -> consumer.accept(obj, values[0]));
		}

		private final Queue<UnsafeConsumer<T, ?>> _bufferedSetters;
		private final ProcessorContext<M> _processorContext;

	}

	public class ProcessorContextImpl implements ProcessorContext<M> {

		public ProcessorContextImpl(String prefix) {
			_prefix = prefix;
		}

		@Override
		public Bind<M> bind(
			int processingIndex, UpdateFunction<M> updateFunction) {

			return new BindImpl<>(
				Function.identity(), processingIndex, this, null,
				updateFunction);
		}

		@Override
		public <T extends BaseModel<T>> Bind<T> bind(
			String publicIdentifier, Function<M, T> modelGetter,
			int processingIndex, UpdateFunction<T> updateFunction) {

			return new BindImpl<>(
				modelGetter, processingIndex, this, publicIdentifier,
				updateFunction);
		}

		@Override
		public <V> V getValue(String fieldExpression, Class<V> clazz) {
			V[] values = getValueArray(fieldExpression, clazz);

			if ((values == null) || (values.length == 0)) {
				return null;
			}

			return values[0];
		}

		@Override
		public <V> V[] getValueArray(String fieldExpression, Class<V> clazz) {
			if (!Validator.isBlank(_prefix)) {
				fieldExpression = _prefix + ':' + fieldExpression;
			}

			Map<String, Object[]> map = _values.get(clazz);

			if (map == null) {
				return null;
			}

			return (V[])map.get(fieldExpression);
		}

		private String _prefix;

	}

	protected <T extends BaseModel<T>> Queue<UnsafeConsumer<T, ?>>
		getPatchingQueue(
			String publicIdentifier, Function<M, T> modelGetter,
			int processingIndex,
			ProcessorContext.UpdateFunction<T> updateFunction) {

		T model = modelGetter.apply(_model);

		if (model != _model) {
			if (publicIdentifier == null) {
				throw new SystemException(
					"Mapped models must have a public identifier");
			}

			return _getPatchingQueue(
				publicIdentifier, processingIndex, updateFunction, model,
				_unsafeConsumers);
		}

		if (publicIdentifier != null) {
			throw new SystemException(
				"The processing model can not have a public identifier");
		}

		return _getPatchingQueue(
			publicIdentifier, processingIndex, updateFunction, model,
			_modelUnsafeConsumers);
	}

	protected abstract PC getProcessorContext(String prefix);

	private void _consumePatches(
			Queue<Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>>>
				unsafeConsumers,
			ServiceContext serviceContext)
		throws PortalException {

		Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>> entry;

		try {
			while ((entry = unsafeConsumers.poll()) != null) {
				UnsafeConsumer<ServiceContext, ?> unsafeConsumer =
					entry.getValue();

				unsafeConsumer.accept(serviceContext);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}

			throw new PortalException(throwable);
		}
	}

	private <T extends BaseModel<T>> Queue<UnsafeConsumer<T, ?>>
		_getPatchingQueue(
			String publicIdentifier, int processingIndex,
			ProcessorContext.UpdateFunction<T> updateFunction, T model,
			Queue<Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>>>
				processingQueue) {

		Queue<UnsafeConsumer<T, ?>> bufferedSetters = new LinkedList<>();

		processingQueue.add(
			new AbstractMap.SimpleEntry<>(
				processingIndex,
				serviceContext -> _patchModel(
					publicIdentifier, model, bufferedSetters, updateFunction,
					serviceContext)));

		return bufferedSetters;
	}

	private <T extends BaseModel<T>> T _patchModel(
			String publicIdentifier, T model,
			Queue<UnsafeConsumer<T, ?>> unsafeConsumers,
			ProcessorContext.UpdateFunction<T> updateFunction,
			ServiceContext serviceContext)
		throws Throwable {

		Map.Entry<Class<?>, Serializable> objectKey =
			new AbstractMap.SimpleEntry<>(model.getClass(), publicIdentifier);

		T obj = (T)_objectCache.get(objectKey);

		T tNew;

		if (obj != null) {
			tNew = obj;
			model = (T)obj.clone();
		}
		else {
			tNew = model;
			model = null;
		}

		UnsafeConsumer<T, ?> unsafeConsumer;

		while ((unsafeConsumer = unsafeConsumers.poll()) != null) {
			unsafeConsumer.accept(tNew);
		}

		tNew = updateFunction.update(model, tNew, serviceContext);

		_objectCache.put(objectKey, tNew);

		return tNew;
	}

	private void _preparePatches() {
		for (String prefix :
				_fieldExpressionHandlerRegistry.
					getFieldExpressionHandlerPrefixes()) {

			FieldExpressionHandler<M, PC> fieldExpressionHandler =
				_fieldExpressionHandlerRegistry.getFieldExpressionHandler(
					prefix);

			_prefix = prefix;

			fieldExpressionHandler.bindProcessorContext(
				getProcessorContext(prefix));
		}
	}

	private final FEHR _fieldExpressionHandlerRegistry;
	private final M _model;
	private final Queue<Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>>>
		_modelUnsafeConsumers = new PriorityQueue<>(
			Comparator.comparingInt(Map.Entry::getKey));
	private final Map<Map.Entry<Class<?>, Serializable>, Object> _objectCache =
		new HashMap<>();
	private String _prefix;
	private final Queue<Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>>>
		_unsafeConsumers = new PriorityQueue<>(
			Comparator.comparingInt(Map.Entry::getKey));
	private final Map<Class<?>, Map<String, Object[]>> _values =
		new HashMap<>();

}