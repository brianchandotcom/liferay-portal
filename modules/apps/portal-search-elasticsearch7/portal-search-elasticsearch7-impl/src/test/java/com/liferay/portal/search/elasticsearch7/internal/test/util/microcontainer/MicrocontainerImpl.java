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

package com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
public class MicrocontainerImpl implements Microcontainer {

	@Override
	public void deploy(Object... components) {
		for (Object component : components) {
			_injectComponent(component);
			_deployComponent(component);
		}
	}

	@Override
	public void start() {
		_componentsMap.forEach(
			(lifecycle, components) -> components.forEach(
				MicrocontainerImpl::activate));

		_started = true;
	}

	@Override
	public <T> void wire(
		Class<T> clazz, BiConsumer<T, Map>... referencePropsBiConsumers) {

		_injectorProps.add(new InjectorProp(clazz, referencePropsBiConsumers));
	}

	@Override
	public <T> void wire(Class<T> clazz, Consumer<T>... referenceConsumers) {
		_injectors.add(new Injector(clazz, referenceConsumers));
	}

	public static class Injector<T> {

		public Injector(Class<T> clazz, Consumer<T>[] consumers) {
			_clazz = clazz;
			_consumers = consumers;
		}

		public void inject(Object component) {
			if (_clazz.isInstance(component)) {
				for (Consumer<T> consumer : _consumers) {
					consumer.accept(_clazz.cast(component));
				}
			}
		}

		private final Class<T> _clazz;
		private final Consumer<T>[] _consumers;

	}

	public static class InjectorProp<T> {

		public InjectorProp(
			Class<T> clazz, BiConsumer<T, Map<String, ?>>... biConsumers) {

			_clazz = clazz;
			_biConsumers = biConsumers;
		}

		public void inject(Object component) {
			if (_clazz.isInstance(component)) {
				for (BiConsumer<T, Map<String, ?>> biConsumer : _biConsumers) {
					biConsumer.accept(
						_clazz.cast(component),
						getComponentPropertyMap(component));
				}
			}
		}

		protected Map<String, ?> getComponentPropertyMap(Object component) {
			return ComponentPropertyMapUtil.getComponentPropertyMap(
				ASMUtil.getClassNode(component.getClass()));
		}

		private final BiConsumer<T, Map<String, ?>>[] _biConsumers;
		private final Class<T> _clazz;

	}

	protected static void activate(Object component) {
		Activator activator = new Activator(component);

		activator.activate();
	}

	private Map<String, Collection<Object>> _createComponentsMap() {
		return LinkedHashMapBuilder.<String, Collection<Object>>put(
			StringPool.BLANK, new HashSet<>()
		).put(
			ModuleServiceLifecycle.PORTAL_INITIALIZED, new HashSet<>()
		).build();
	}

	private void _deployComponent(Object component) {
		Collection<Object> lifecycleComponents = _componentsMap.get(
			_getLifecycle(component));

		lifecycleComponents.add(component);
	}

	private MethodNode _findMethodPortalInitialized(ClassNode classNode) {
		for (MethodNode methodNode : classNode.methods) {
			if (_hasReferenceWithTarget(
					methodNode, ModuleServiceLifecycle.PORTAL_INITIALIZED)) {

				return methodNode;
			}
		}

		return null;
	}

	private String _getLifecycle(Object component) {
		if (_hasMethodPortalInitialized(component)) {
			return ModuleServiceLifecycle.PORTAL_INITIALIZED;
		}

		return StringPool.BLANK;
	}

	private boolean _hasMethodPortalInitialized(Object component) {
		ClassNode classNode = ASMUtil.getClassNode(component.getClass());

		MethodNode methodNode = _findMethodPortalInitialized(classNode);

		if (methodNode != null) {
			return true;
		}

		if (classNode.superName == null) {
			return false;
		}

		methodNode = _findMethodPortalInitialized(
			ASMUtil.getClassNode(classNode.superName));

		if (methodNode != null) {
			return true;
		}

		return false;
	}

	private boolean _hasReferenceWithTarget(
		MethodNode methodNode, String lifecycle) {

		List<AnnotationNode> annotationNodes = methodNode.invisibleAnnotations;

		if (annotationNodes == null) {
			return false;
		}

		for (AnnotationNode annotationNode : annotationNodes) {
			if (_hasTarget(annotationNode, lifecycle)) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasTarget(AnnotationNode annotationNode, String target) {
		if (!annotationNode.desc.contains(Reference.class.getSimpleName())) {
			return false;
		}

		List<?> values = annotationNode.values;

		int i = values.indexOf("target");

		if (i == -1) {
			return false;
		}

		if (target.equals(values.get(i + 1))) {
			return true;
		}

		return false;
	}

	private void _injectComponent(Object component) {
		if (_started) {
			activate(component);
		}

		_injectors.forEach(injector -> injector.inject(component));
		_injectorProps.forEach(injectorProp -> injectorProp.inject(component));
	}

	private final Map<String, Collection<Object>> _componentsMap =
		_createComponentsMap();
	private final List<InjectorProp<?>> _injectorProps = new ArrayList<>();
	private final List<Injector<?>> _injectors = new ArrayList<>();
	private boolean _started;

}