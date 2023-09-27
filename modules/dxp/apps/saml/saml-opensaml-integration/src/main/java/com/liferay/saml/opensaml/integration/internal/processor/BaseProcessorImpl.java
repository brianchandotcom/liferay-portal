/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Stian Sigvartsen
 */
public abstract class BaseProcessorImpl
	<M extends BaseModel<M>, PC extends ProcessorContext<M>,
	 FEH extends FieldExpressionHandler<M, PC>,
	 FEHR extends FieldExpressionHandlerRegistry<M, PC, FEH>>
		implements Processor<M> {

	public BaseProcessorImpl(M model, FEHR fieldExpressionHandlerRegistry) {
		_model = model;
		_fieldExpressionHandlerRegistry = fieldExpressionHandlerRegistry;
	}

	@Override
	public M process(ServiceContext serviceContext) throws PortalException {
		_preparePatches();

		_consumePatches(serviceContext);

		return _model;
	}

	@Override
	public <T, V extends T> void setValueArray(
		Class<T> clazz, String fieldExpression, V[] value) {

		Map<String, Object[]> map = _maps.get(clazz);

		if (map == null) {
			map = new HashMap<>();

			_maps.put(clazz, map);
		}

		map.put(fieldExpression, value);
	}

	@Override
	public void setValueArray(String fieldExpression, String[] value) {
		setValueArray(String.class, fieldExpression, value);
	}

	public class BindImpl<T extends BaseModel<T>>
		implements ProcessorContext.Bind<T> {

		public BindImpl(
			Function<M, T> modelGetterFunction, int processingIndex,
			ProcessorContext processorContext, String publicIdentifier,
			ProcessorContext.UpdateFunction<T> updateFunction) {

			_processorContext = processorContext;

			_unsafeConsumers.add(
				new AbstractMap.SimpleEntry<>(
					processingIndex,
					serviceContext -> _patchModel(
						publicIdentifier, modelGetterFunction, _patchingQueue,
						updateFunction, serviceContext)));
		}

		public <V> void handleUnsafeObjectArray(
			String fieldExpression, Class<V> clazz,
			UnsafeBiConsumer<T, V[], ?> unsafeBiConsumer) {

			if (!_processorContext.isDefined(clazz, fieldExpression)) {
				return;
			}

			V[] values = _processorContext.getValueArray(
				clazz, fieldExpression);

			_patchingQueue.add(
				object -> unsafeBiConsumer.accept(object, values));
		}

		@Override
		public void handleUnsafeStringArray(
			String fieldExpression,
			UnsafeBiConsumer<T, String[], ?> unsafeBiConsumer) {

			handleUnsafeObjectArray(
				fieldExpression, String.class, unsafeBiConsumer);
		}

		@Override
		public void mapBoolean(
			String fieldExpression, BiConsumer<T, Boolean> biConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, values) -> biConsumer.accept(
					object, GetterUtil.getBoolean(_head(values))));
		}

		@Override
		public void mapBooleanArray(
			String fieldExpression, BiConsumer<T, boolean[]> biConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, value) -> biConsumer.accept(
					object, GetterUtil.getBooleanValues(value)));
		}

		@Override
		public void mapLong(
			String fieldExpression, BiConsumer<T, Long> biConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, values) -> {
					try {
						biConsumer.accept(object, Long.parseLong(values[0]));
					}
					catch (NumberFormatException numberFormatException) {
						throw numberFormatException;
					}
				});
		}

		@Override
		public void mapLongArray(
			String fieldExpression, BiConsumer<T, long[]> biConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, value) -> {
					try {
						long[] longArray = new long[value.length];

						for (int i = 0; i < longArray.length; i++) {
							longArray[i] = Long.parseLong(value[i]);
						}

						biConsumer.accept(object, longArray);
					}
					catch (NumberFormatException numberFormatException) {
						throw numberFormatException;
					}
				});
		}

		@Override
		public void mapString(
			String fieldExpression, BiConsumer<T, String> biConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, values) -> biConsumer.accept(object, _head(values)));
		}

		@Override
		public void mapStringArray(
			String fieldExpression, BiConsumer<T, String[]> biConsumer) {

			handleUnsafeStringArray(fieldExpression, biConsumer::accept);
		}

		@Override
		public void mapUnsafeString(
			String fieldExpression,
			UnsafeBiConsumer<T, String, ?> unsafeBiConsumer) {

			handleUnsafeStringArray(
				fieldExpression,
				(object, values) -> unsafeBiConsumer.accept(
					object, _head(values)));
		}

		private <V> V _head(V[] values) {
			if ((values == null) || (values.length == 0)) {
				return null;
			}

			return values[0];
		}

		private final Queue<UnsafeConsumer<T, ?>> _patchingQueue =
			new LinkedList<>();
		private final ProcessorContext<M> _processorContext;

	}

	public class ProcessorContextImpl implements ProcessorContext<M> {

		public ProcessorContextImpl(String prefix) {
			_prefix = prefix;
		}

		@Override
		public <T extends BaseModel<T>> Bind<T> bind(
			Function<M, T> modelGetterFunction, int processingIndex,
			String publicIdentifier, UpdateFunction<T> updateFunction) {

			return new BindImpl<>(
				modelGetterFunction, processingIndex, this, publicIdentifier,
				updateFunction);
		}

		@Override
		public Bind<M> bind(
			int processingIndex, UpdateFunction<M> updateFunction) {

			return new BindImpl<>(
				Function.identity(), processingIndex, this, null,
				updateFunction);
		}

		@Override
		public <V> V getValue(Class<V> clazz, String fieldExpression) {
			V[] values = getValueArray(clazz, fieldExpression);

			if ((values == null) || (values.length == 0)) {
				return null;
			}

			return values[0];
		}

		@Override
		public <V> V[] getValueArray(Class<V> clazz, String fieldExpression) {
			if (!Validator.isBlank(_prefix)) {
				fieldExpression = _prefix + ':' + fieldExpression;
			}

			Map<String, Object[]> map = _maps.get(clazz);

			if (map == null) {
				return null;
			}

			return (V[])map.get(fieldExpression);
		}

		public boolean isDefined(Class<?> clazz, String fieldExpression) {
			if (!Validator.isBlank(_prefix)) {
				fieldExpression = _prefix + ':' + fieldExpression;
			}

			Map<String, Object[]> map = _maps.get(clazz);

			if (map == null) {
				return false;
			}

			return map.containsKey(fieldExpression);
		}

		private final String _prefix;

	}

	protected abstract PC getProcessorContext(String prefix);

	private void _consumePatches(ServiceContext serviceContext)
		throws PortalException {

		Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>> entry;

		try {
			while ((entry = _unsafeConsumers.poll()) != null) {
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

	private <T extends BaseModel<T>> T _patchModel(
			String publicIdentifier, Function<M, T> modelGetterFunction,
			Queue<UnsafeConsumer<T, ?>> unsafeConsumers,
			ProcessorContext.UpdateFunction<T> updateFunction,
			ServiceContext serviceContext)
		throws Throwable {

		T model = modelGetterFunction.apply(_model);

		boolean mappedModel = false;

		if (model != _model) {
			if (publicIdentifier == null) {
				throw new SystemException(
					"Mapped models must have a public identifier");
			}

			mappedModel = true;
		}
		else if (publicIdentifier != null) {
			throw new SystemException(
				"The processing model cannot have a public identifier");
		}

		Map.Entry<Class<?>, Serializable> objectKey =
			new AbstractMap.SimpleEntry<>(model.getClass(), publicIdentifier);

		T object = (T)_objectCache.get(objectKey);

		T tNew;

		if (object != null) {
			tNew = object;
			model = (T)object.clone();
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

		if (!mappedModel) {
			_model = (M)tNew;
		}

		return tNew;
	}

	private void _preparePatches() {
		for (String prefix :
				_fieldExpressionHandlerRegistry.
					getFieldExpressionHandlerPrefixes()) {

			FieldExpressionHandler<M, PC> fieldExpressionHandler =
				_fieldExpressionHandlerRegistry.getFieldExpressionHandler(
					prefix);

			fieldExpressionHandler.bindProcessorContext(
				getProcessorContext(prefix));
		}
	}

	private final FEHR _fieldExpressionHandlerRegistry;
	private final Map<Class<?>, Map<String, Object[]>> _maps = new HashMap<>();
	private M _model;
	private final Map<Map.Entry<Class<?>, Serializable>, Object> _objectCache =
		new HashMap<>();
	private final Queue<Map.Entry<Integer, UnsafeConsumer<ServiceContext, ?>>>
		_unsafeConsumers = new PriorityQueue<>(
			Comparator.comparingInt(Map.Entry::getKey));

}