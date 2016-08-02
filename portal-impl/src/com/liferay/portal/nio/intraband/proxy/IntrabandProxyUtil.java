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

package com.liferay.portal.nio.intraband.proxy;

import com.liferay.portal.asm.ASMUtil;
import com.liferay.portal.asm.MethodNodeGenerator;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.proxy.ExceptionHandler;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeleton;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Id;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Proxy;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCResponse;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.TableSwitchGenerator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Shuyang Zhou
 */
public class IntrabandProxyUtil {

	public static final String SKELETON_POSTFIX = "__IntrabandProxy__Skeleton";

	public static final String STUB_POSTFIX = "__IntrabandProxy__Stub";

	public static String[] getProxyMethodSignatures(Class<?> clazz) {
		try {
			Field field = clazz.getField(_PROXY_METHOD_SIGNATURES_FIELD_NAME);

			return (String[])field.get(null);
		}
		catch (Exception e) {
			return null;
		}
	}

	public static Class<?> getStubClass(Class<?> clazz, String skeletonId) {
		return getStubClass(clazz.getClassLoader(), clazz, skeletonId);
	}

	public static Class<?> getStubClass(
		ClassLoader classLoader, Class<?> clazz, String skeletonId) {

		Class<?> stubClass = loadClass(classLoader, clazz, STUB_POSTFIX);

		if (stubClass != null) {
			return stubClass;
		}

		synchronized (classLoader) {
			stubClass = loadClass(classLoader, clazz, STUB_POSTFIX);

			if (stubClass != null) {
				return stubClass;
			}

			validate(classLoader, clazz, false);

			stubClass = generateStubClass(classLoader, clazz, skeletonId);
		}

		return stubClass;
	}

	public static <T> T newStubInstance(
		Class<? extends T> stubClass, String id,
		RegistrationReference registrationReference,
		ExceptionHandler exceptionHandler) {

		try {
			Constructor<? extends T> constructor = stubClass.getConstructor(
				String.class, RegistrationReference.class,
				ExceptionHandler.class);

			return constructor.newInstance(
				id, registrationReference, exceptionHandler);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void checkField(
		Field[] fields, String name, Class<?> clazz, boolean isStatic) {

		for (Field field : fields) {
			if (name.equals(field.getName())) {
				if ((field.getType() != clazz) ||
					(Modifier.isStatic(field.getModifiers()) != isStatic)) {

					throw new IllegalArgumentException(
						"Field " + field + " is expected to be of type " +
							clazz + " and " + (!isStatic ? "not " : "") +
								"static");
				}

				break;
			}
		}
	}

	protected static MethodNode createProxyMethodNode(
		Method method, int index, String skeletonId, Type stubType) {

		MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
			method);

		// Serializer serializer = new Serializer();

		methodNodeGenerator.newInstance(_SERIALIZER_TYPE);

		methodNodeGenerator.dup();

		methodNodeGenerator.invokeSpecial(
			_SERIALIZER_TYPE.getInternalName(), "<init>", Type.VOID_TYPE);

		int serializerIndex = methodNodeGenerator.newLocal(_SERIALIZER_TYPE);

		methodNodeGenerator.storeLocal(serializerIndex);

		// serializer.writeString(skeletonId);

		methodNodeGenerator.loadLocal(serializerIndex);

		methodNodeGenerator.push(skeletonId);

		serializerWrite(methodNodeGenerator, _STRING_TYPE);

		// serializer.writeString(_id);

		methodNodeGenerator.loadLocal(serializerIndex);

		methodNodeGenerator.loadThis();

		methodNodeGenerator.getField(stubType, "_id", _STRING_TYPE);

		serializerWrite(methodNodeGenerator, _STRING_TYPE);

		// serializer.writeInt(index);

		methodNodeGenerator.loadLocal(serializerIndex);

		methodNodeGenerator.push(index);

		serializerWrite(methodNodeGenerator, Type.INT_TYPE);

		// serializer.writeXXX(parameters...)

		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = 0; i < parameterTypes.length; i++) {
			methodNodeGenerator.loadLocal(serializerIndex);

			methodNodeGenerator.loadArg(i);

			serializerWrite(
				methodNodeGenerator, Type.getType(parameterTypes[i]));
		}

		// this._send(Serializer) / return this._syncSend(Serializer)

		methodNodeGenerator.loadThis();

		methodNodeGenerator.loadLocal(serializerIndex);

		Class<?> returnClass = method.getReturnType();

		if (returnClass == void.class) {

			// this._send(Serializer)

			methodNodeGenerator.invokeSpecial(
				stubType.getInternalName(), "_send", Type.VOID_TYPE,
				_SERIALIZER_TYPE);

			methodNodeGenerator.returnValue();
		}
		else {

			// return this._syncSend(Serializer)

			methodNodeGenerator.invokeSpecial(
				stubType.getInternalName(), "_syncSend", _SERIALIZABLE_TYPE,
				_SERIALIZER_TYPE);

			Type returnType = Type.getType(returnClass);

			if (returnClass.isPrimitive()) {

				// Null check and unbox

				int returnValueIndex = methodNodeGenerator.newLocal(
					_OBJECT_TYPE);

				methodNodeGenerator.storeLocal(returnValueIndex);

				methodNodeGenerator.loadLocal(returnValueIndex);

				Label nullCheckLabel = new Label();

				methodNodeGenerator.ifNull(nullCheckLabel);

				methodNodeGenerator.loadLocal(returnValueIndex);

				methodNodeGenerator.unbox(returnType);

				methodNodeGenerator.returnValue();

				methodNodeGenerator.visitLabel(nullCheckLabel);

				ASMUtil.addDefaultReturnInsns(methodNodeGenerator, returnType);
			}
			else {
				if (returnClass != Object.class) {
					methodNodeGenerator.checkCast(returnType);
				}

				methodNodeGenerator.returnValue();
			}
		}

		methodNodeGenerator.endMethod();

		return methodNodeGenerator.getMethodNode();
	}

	protected static void deserializerRead(
		MethodNodeGenerator methodNodeGenerator, Type type) {

		String owner = _DESERIALIZER_TYPE.getInternalName();

		if (type.getSort() <= Type.DOUBLE) {
			String name = TextFormatter.format(
				type.getClassName(), TextFormatter.G);

			methodNodeGenerator.invokeVirtual(owner, "read".concat(name), type);
		}
		else if (type.equals(_STRING_TYPE)) {
			methodNodeGenerator.invokeVirtual(
				owner, "readString", _STRING_TYPE);
		}
		else {
			methodNodeGenerator.invokeVirtual(
				owner, "readObject", _SERIALIZABLE_TYPE);
		}
	}

	protected static MethodsBag extractMethods(Class<?> clazz) {
		List<Method> idMethods = new ArrayList<>();
		List<Method> proxyMethods = new ArrayList<>();
		List<Method> emptyMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			Id id = method.getAnnotation(Id.class);

			if (id != null) {
				if (Modifier.isStatic(method.getModifiers())) {
					throw new IllegalArgumentException(
						"The @Id annotated method " + method +
							" must not be static");
				}

				Class<?>[] parameterTypes = method.getParameterTypes();

				if (parameterTypes.length > 0) {
					throw new IllegalArgumentException(
						"The @Id annotated method " + method +
							" must not have parameters");
				}

				if (method.getReturnType() != String.class) {
					throw new IllegalArgumentException(
						"The @Id annotated method " + method +
							" must not return String");
				}

				idMethods.add(method);

				continue;
			}

			Proxy proxy = method.getAnnotation(Proxy.class);

			if (proxy != null) {
				if (Modifier.isStatic(method.getModifiers())) {
					throw new IllegalArgumentException(
						"Static proxy method violation for " + method);
				}

				proxyMethods.add(method);

				continue;
			}

			if (Modifier.isAbstract(method.getModifiers())) {
				emptyMethods.add(method);
			}
		}

		return new MethodsBag(idMethods, proxyMethods, emptyMethods);
	}

	protected static Class<? extends IntrabandProxySkeleton>
		generateSkeletonClass(ClassLoader classLoader, Class<?> clazz) {

		Type targetType = Type.getType(clazz);

		String internalName = targetType.getInternalName();

		ClassNode classNode = ASMUtil.loadAndRename(
			TemplateSkeleton.class, internalName.concat(SKELETON_POSTFIX));

		classNode.access &= ~Opcodes.ACC_ABSTRACT;
		classNode.access |= Opcodes.ACC_PUBLIC;

		FieldNode proxyMethodsMappingFieldNode = ASMUtil.findFieldNode(
			classNode.fields, "_PROXY_METHODS_MAPPING");

		proxyMethodsMappingFieldNode.access |= Opcodes.ACC_FINAL;

		FieldNode targetLocatorFieldNode = ASMUtil.findFieldNode(
			classNode.fields, "_targetLocator");

		targetLocatorFieldNode.access |= Opcodes.ACC_FINAL;

		MethodNode doDispatchMethodNode = ASMUtil.findMethodNode(
			classNode.methods, "doDispatch", Type.VOID_TYPE,
			_REGISTRATION_REFERENCE_TYPE, _DATAGRAM_TYPE, _DESERIALIZER_TYPE);

		doDispatchMethodNode.access &= ~Opcodes.ACC_ABSTRACT;

		MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
			doDispatchMethodNode);

		// T target = (T)_targetLocator.getTarget()

		methodNodeGenerator.loadThis();

		methodNodeGenerator.getField(
			Type.getObjectType(classNode.name), "_targetLocator",
			_TARGET_LOCATOR_TYPE);

		methodNodeGenerator.loadArg(2);

		deserializerRead(methodNodeGenerator, _STRING_TYPE);

		methodNodeGenerator.invokeInterface(
			_TARGET_LOCATOR_TYPE.getInternalName(), "getTarget", _OBJECT_TYPE,
			_STRING_TYPE);

		methodNodeGenerator.checkCast(targetType);

		int typedTargetIndex = methodNodeGenerator.newLocal(targetType);

		methodNodeGenerator.storeLocal(typedTargetIndex);

		// int index = deserializer.readInt();

		methodNodeGenerator.loadArg(2);

		deserializerRead(methodNodeGenerator, Type.INT_TYPE);

		methodNodeGenerator.dup();

		int indexIndex = methodNodeGenerator.newLocal(Type.INT_TYPE);

		methodNodeGenerator.storeLocal(indexIndex);

		// switch (index)

		MethodsBag methodsBag = extractMethods(clazz);

		List<Method> proxyMethods = methodsBag.proxyMethods;

		int[] keys = new int[proxyMethods.size()];

		for (int i = 0; i < keys.length; i++) {
			keys[i] = i;
		}

		methodNodeGenerator.tableSwitch(
			keys,
			new SkeletonDispatchTableSwitchGenerator(
				methodNodeGenerator, proxyMethods, classNode.name,
				typedTargetIndex, indexIndex),
			true);

		methodNodeGenerator.returnValue();

		methodNodeGenerator.endMethod();

		rewriteGetProxyMethodSignaturesMethodNode(
			classNode, methodsBag.proxyMethodSignatures);

		return (Class<? extends IntrabandProxySkeleton>)toClass(
			classNode, classLoader);
	}

	protected static Class<?> generateStubClass(
		ClassLoader classLoader, Class<?> clazz, String skeletonId) {

		String internalName = Type.getInternalName(clazz);

		ClassNode classNode = ASMUtil.loadAndRename(
			clazz, internalName.concat(STUB_POSTFIX));

		// Class modifiers and hierarchy

		classNode.access &= ~(Opcodes.ACC_ABSTRACT | Opcodes.ACC_INTERFACE);
		classNode.access |= Opcodes.ACC_PUBLIC;

		if (clazz.isInterface()) {
			List<String> interfaces = classNode.interfaces;

			interfaces.clear();

			interfaces.add(internalName);
		}

		// Clean up MethodNodes that are going to be generated

		List<MethodNode> methodNodes = classNode.methods;

		MethodNode defaultInitMethodNode = ASMUtil.removeMethodNode(
			methodNodes, "<init>", Type.VOID_TYPE);

		ASMUtil.removeMethodNodes(methodNodes, "<init>");
		ASMUtil.removeMethodNodes(methodNodes, Opcodes.ACC_ABSTRACT);
		ASMUtil.removeMethodNodes(methodNodes, _annotationDescriptors);

		// Apply template class

		ClassNode templateClassNode = ASMUtil.loadAndRename(
			TemplateStub.class, classNode.name);

		List<FieldNode> templateFieldNodes = templateClassNode.fields;

		FieldNode idFieldNode = ASMUtil.findFieldNode(
			templateFieldNodes, "_id");

		idFieldNode.access |= Opcodes.ACC_FINAL;

		FieldNode intrabandFieldNode = ASMUtil.findFieldNode(
			templateFieldNodes, "_intraband");

		intrabandFieldNode.access |= Opcodes.ACC_FINAL;

		FieldNode registrationReferenceFieldNode = ASMUtil.findFieldNode(
			templateFieldNodes, "_registrationReference");

		registrationReferenceFieldNode.access |= Opcodes.ACC_FINAL;

		ASMUtil.addFieldNodes(classNode.fields, templateFieldNodes);

		List<MethodNode> templateMethodNodes = templateClassNode.methods;

		MethodNode templateInitMethodNode = ASMUtil.findMethodNode(
			templateMethodNodes, "<init>", Type.VOID_TYPE, _STRING_TYPE,
			_REGISTRATION_REFERENCE_TYPE, _EXCEPTION_HANDLER_TYPE);

		if (defaultInitMethodNode != null) {
			ASMUtil.mergeMethods(
				templateInitMethodNode, defaultInitMethodNode,
				templateInitMethodNode);
		}

		MethodNode defaultClinitMethodNode = ASMUtil.removeMethodNode(
			methodNodes, "<clinit>", Type.VOID_TYPE);

		if (defaultClinitMethodNode != null) {
			MethodNode templateClinitMethodNode = ASMUtil.findMethodNode(
				templateMethodNodes, "<clinit>", Type.VOID_TYPE);

			ASMUtil.mergeMethods(
				templateClinitMethodNode, defaultClinitMethodNode,
				templateClinitMethodNode);
		}

		methodNodes.addAll(templateMethodNodes);

		Type stubType = Type.getType(classNode.name);

		// Id methods

		MethodsBag methodsBag = extractMethods(clazz);

		for (Method idMethod : methodsBag.idMethods) {
			MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
				idMethod);

			methodNodeGenerator.loadThis();

			methodNodeGenerator.getField(stubType, "_id", _STRING_TYPE);

			methodNodeGenerator.returnValue();

			methodNodeGenerator.endMethod();

			methodNodes.add(methodNodeGenerator.getMethodNode());
		}

		// Proxy methods

		List<Method> proxyMethods = methodsBag.proxyMethods;

		for (int i = 0; i < proxyMethods.size(); i++) {
			methodNodes.add(
				createProxyMethodNode(
					proxyMethods.get(i), i, skeletonId, stubType));
		}

		// Empty methods

		for (Method emptyMethod : methodsBag.emptyMethods) {
			MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
				emptyMethod);

			ASMUtil.addDefaultReturnInsns(
				methodNodeGenerator, Type.getType(emptyMethod.getReturnType()));

			methodNodeGenerator.endMethod();

			methodNodes.add(methodNodeGenerator.getMethodNode());
		}

		rewriteGetProxyMethodSignaturesMethodNode(
			classNode, methodsBag.proxyMethodSignatures);

		return toClass(classNode, classLoader);
	}

	protected static Class<?> getSkeletonClass(
			ClassLoader classLoader, Class<?> clazz)
		throws Exception {

		Class<?> skeletonClass = loadClass(
			classLoader, clazz, SKELETON_POSTFIX);

		if (skeletonClass != null) {
			return skeletonClass;
		}

		synchronized (classLoader) {
			skeletonClass = loadClass(classLoader, clazz, SKELETON_POSTFIX);

			if (skeletonClass != null) {
				return skeletonClass;
			}

			validate(classLoader, clazz, true);

			skeletonClass = generateSkeletonClass(classLoader, clazz);
		}

		return skeletonClass;
	}

	protected static Class<?> loadClass(
		ClassLoader classLoader, Class<?> clazz, String postfix) {

		String className = clazz.getName();

		try {
			return Class.forName(className.concat(postfix), false, classLoader);
		}
		catch (ClassNotFoundException cnfe) {
		}

		return null;
	}

	protected static void rewriteGetProxyMethodSignaturesMethodNode(
		ClassNode classNode, String[] proxyMethodSignatures) {

		MethodNode methodNode = ASMUtil.findMethodNode(
			classNode.methods, "_getProxyMethodSignatures", _STRING_ARRAY_TYPE);

		InsnList insnList = methodNode.instructions;

		insnList.clear();

		MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
			methodNode);

		methodNodeGenerator.push(proxyMethodSignatures.length);

		methodNodeGenerator.newArray(_STRING_TYPE);

		for (int i = 0; i < proxyMethodSignatures.length; i++) {
			methodNodeGenerator.dup();

			methodNodeGenerator.push(i);
			methodNodeGenerator.push(proxyMethodSignatures[i]);

			methodNodeGenerator.arrayStore(_STRING_TYPE);
		}

		methodNodeGenerator.returnValue();

		methodNodeGenerator.endMethod();
	}

	protected static void serializerWrite(
		MethodNodeGenerator methodNodeGenerator, Type type) {

		String owner = _SERIALIZER_TYPE.getInternalName();

		if (type.getSort() <= Type.DOUBLE) {
			String name = TextFormatter.format(
				type.getClassName(), TextFormatter.G);

			methodNodeGenerator.invokeVirtual(
				owner, "write".concat(name), Type.VOID_TYPE, type);
		}
		else if (type.equals(_STRING_TYPE)) {
			methodNodeGenerator.invokeVirtual(
				owner, "writeString", Type.VOID_TYPE, _STRING_TYPE);
		}
		else {
			methodNodeGenerator.invokeVirtual(
				owner, "writeObject", Type.VOID_TYPE, _SERIALIZABLE_TYPE);
		}
	}

	protected static Class<?> toClass(
		ClassNode classNode, ClassLoader classLoader) {

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		classNode.accept(classWriter);

		byte[] data = classWriter.toByteArray();

		try {
			if (PropsValues.INTRABAND_PROXY_DUMP_CLASSES_ENABLED) {
				File classFile = new File(
					_DUMP_DIR, classNode.name.concat(".class"));

				FileUtil.write(classFile, data);

				if (_log.isInfoEnabled()) {
					_log.info("Dumpped class " + classFile.getAbsolutePath());
				}
			}

			return (Class<?>)_defineClassMethod.invoke(
				classLoader,
				StringUtil.replace(
					classNode.name, CharPool.SLASH, CharPool.PERIOD),
				data, 0, data.length);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static void validate(
		ClassLoader classLoader, Class<?> clazz, boolean skeletonOrStub) {

		if (clazz.isAnnotation()) {
			throw new IllegalArgumentException(clazz + " is an annotation");
		}

		if (clazz.isArray()) {
			throw new IllegalArgumentException(clazz + " is an array");
		}

		if (clazz.isEnum()) {
			throw new IllegalArgumentException(clazz + " is an enum");
		}

		if (clazz.isPrimitive()) {
			throw new IllegalArgumentException(clazz + " is a primitive");
		}

		Class<?> reloadedClass = null;

		try {
			reloadedClass = Class.forName(clazz.getName(), false, classLoader);
		}
		catch (ClassNotFoundException cnfe) {
		}

		if (reloadedClass != clazz) {
			throw new IllegalArgumentException(
				clazz + " is not visible from class loader " + classLoader);
		}

		Field[] fields = clazz.getDeclaredFields();

		checkField(
			fields, _PROXY_METHOD_SIGNATURES_FIELD_NAME, String[].class, true);

		if (skeletonOrStub) {
			checkField(
				fields, _PROXY_METHODS_MAPPING_FIELD_NAME, String.class, true);
			checkField(fields, "_log", Log.class, true);
			checkField(fields, "_targetLocator", TargetLocator.class, false);
		}
		else {
			checkField(
				fields, "_exceptionHandler", ExceptionHandler.class, false);
			checkField(fields, "_id", String.class, false);
			checkField(fields, "_intraband", Intraband.class, false);
			checkField(fields, "_proxyType", byte.class, true);
			checkField(
				fields, "_registrationReference", RegistrationReference.class,
				false);
		}
	}

	protected static class MethodComparator implements Comparator<Method> {

		@Override
		public int compare(Method method1, Method method2) {
			String methodId1 = _getMethodId(method1);
			String methodId2 = _getMethodId(method2);

			return methodId1.compareTo(methodId2);
		}

		private static String _getMethodId(Method method) {
			Proxy proxy = method.getAnnotation(Proxy.class);

			String methodName = proxy.name();

			if (methodName.isEmpty()) {
				methodName = method.getName();
			}

			return methodName.concat(StringPool.DASH).concat(
				Type.getMethodDescriptor(method));
		}

	}

	protected static class MethodsBag {

		public MethodsBag(
			List<Method> idMethods, List<Method> proxyMethods,
			List<Method> emptyMethods) {

			this.idMethods = idMethods;
			this.proxyMethods = proxyMethods;
			this.emptyMethods = emptyMethods;

			Collections.sort(proxyMethods, _methodComparator);

			proxyMethodSignatures = new String[proxyMethods.size()];

			for (int i = 0; i < proxyMethods.size(); i++) {
				Method proxyMethod = proxyMethods.get(i);

				String name = proxyMethod.getName();

				proxyMethodSignatures[i] = name.concat(StringPool.DASH).concat(
					Type.getMethodDescriptor(proxyMethod));
			}
		}

		protected List<Method> emptyMethods;
		protected List<Method> idMethods;
		protected List<Method> proxyMethods;
		protected String[] proxyMethodSignatures;

	}

	protected abstract static class TemplateSkeleton
		implements IntrabandProxySkeleton {

		public static final String[] PROXY_METHOD_SIGNATURES =
			_getProxyMethodSignatures();

		public TemplateSkeleton(TargetLocator targetLocator) {
			if (targetLocator == null) {
				throw new NullPointerException("Target locator is null");
			}

			_targetLocator = targetLocator;
		}

		@Override
		public void dispatch(
			RegistrationReference registrationReference, Datagram datagram,
			Deserializer deserializer) {

			try {
				doDispatch(registrationReference, datagram, deserializer);
			}
			catch (Exception e) {
				_log.error("Unable to dispatch", e);

				_sendResponse(
					registrationReference, datagram, new RPCResponse(e));
			}
		}

		protected abstract void doDispatch(
				RegistrationReference registrationReference, Datagram datagram,
				Deserializer deserializer)
			throws Exception;

		private static String[] _getProxyMethodSignatures() {
			return new String[0];
		}

		private static String _getProxyMethodsMapping(
			String[] proxyMethodsSignatures) {

			StringBundler sb = new StringBundler(
				proxyMethodsSignatures.length * 4 + 1);

			sb.append(StringPool.OPEN_CURLY_BRACE);

			for (int i = 0; i < proxyMethodsSignatures.length; i++) {
				sb.append(i);
				sb.append(" -> ");
				sb.append(proxyMethodsSignatures[i]);
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			if (proxyMethodsSignatures.length > 0) {
				sb.setIndex(sb.index() - 1);
			}

			sb.append(StringPool.CLOSE_CURLY_BRACE);

			return sb.toString();
		}

		private void _sendResponse(
			RegistrationReference registrationReference, Datagram datagram,
			RPCResponse rpcResponse) {

			Serializer serializer = new Serializer();

			serializer.writeObject(rpcResponse);

			Intraband intraband = registrationReference.getIntraband();

			intraband.sendDatagram(
				registrationReference,
				Datagram.createResponseDatagram(
					datagram, serializer.toByteBuffer()));
		}

		@SuppressWarnings("unused")
		private void _unknownMethodIndex(int methodIndex) {
			throw new IllegalArgumentException(
				"Unknow method index " + methodIndex +
					" for proxy methods mappings " + _PROXY_METHODS_MAPPING);
		}

		private static final String _PROXY_METHODS_MAPPING =
			_getProxyMethodsMapping(PROXY_METHOD_SIGNATURES);

		private static final Log _log = LogFactoryUtil.getLog(
			TemplateSkeleton.class);

		@SuppressWarnings("unused")
		private TargetLocator _targetLocator;

	}

	protected static class TemplateStub {

		public static final String[] PROXY_METHOD_SIGNATURES =
			_getProxyMethodSignatures();

		public TemplateStub(
			String id, RegistrationReference registrationReference,
			ExceptionHandler exceptionHandler) {

			if (id == null) {
				throw new NullPointerException("Id is null");
			}

			if (registrationReference == null) {
				throw new NullPointerException(
					"Registration reference is null");
			}

			_id = id;
			_registrationReference = registrationReference;
			_exceptionHandler = exceptionHandler;

			_intraband = registrationReference.getIntraband();
		}

		private static String[] _getProxyMethodSignatures() {
			return new String[0];
		}

		@SuppressWarnings("unused")
		private void _send(Serializer serializer) {
			_intraband.sendDatagram(
				_registrationReference,
				Datagram.createRequestDatagram(
					_PROXY_TYPE, serializer.toByteBuffer()));
		}

		@SuppressWarnings("unused")
		private <T extends Serializable> T _syncSend(Serializer serializer) {
			try {
				Datagram responseDatagram = _intraband.sendSyncDatagram(
					_registrationReference,
					Datagram.createRequestDatagram(
						_PROXY_TYPE, serializer.toByteBuffer()));

				Deserializer deserializer = new Deserializer(
					responseDatagram.getDataByteBuffer());

				RPCResponse rpcResponse = deserializer.readObject();

				Exception e = rpcResponse.getException();

				if (e != null) {
					throw e;
				}

				return (T)rpcResponse.getResult();
			}
			catch (Exception e) {
				if (_exceptionHandler != null) {
					_exceptionHandler.onException(e);
				}

				return null;
			}
		}

		private static final byte _PROXY_TYPE = SystemDataType.PROXY.getValue();

		private final ExceptionHandler _exceptionHandler;

		@SuppressWarnings("unused")
		private String _id;

		private final Intraband _intraband;
		private final RegistrationReference _registrationReference;

	}

	private static final Type _DATAGRAM_TYPE = Type.getType(Datagram.class);

	private static final Type _DESERIALIZER_TYPE = Type.getType(
		Deserializer.class);

	private static final File _DUMP_DIR = new File(
		SystemProperties.get(SystemProperties.TMP_DIR),
		PropsValues.INTRABAND_PROXY_DUMP_CLASSES_DIR);

	private static final Type _EXCEPTION_HANDLER_TYPE = Type.getType(
		ExceptionHandler.class);

	private static final Type _OBJECT_TYPE = Type.getType(Object.class);

	private static final String _PROXY_METHOD_SIGNATURES_FIELD_NAME =
		"PROXY_METHOD_SIGNATURES";

	private static final String _PROXY_METHODS_MAPPING_FIELD_NAME =
		"_PROXY_METHODS_MAPPING";

	private static final Type _REGISTRATION_REFERENCE_TYPE = Type.getType(
		RegistrationReference.class);

	private static final Type _RPC_RESPONSE_TYPE = Type.getType(
		RPCResponse.class);

	private static final Type _SERIALIZABLE_TYPE = Type.getType(
		Serializable.class);

	private static final Type _SERIALIZER_TYPE = Type.getType(Serializer.class);

	private static final Type _STRING_ARRAY_TYPE = Type.getType(String[].class);

	private static final Type _STRING_TYPE = Type.getType(String.class);

	private static final Type _TARGET_LOCATOR_TYPE = Type.getType(
		TargetLocator.class);

	private static final Log _log = LogFactoryUtil.getLog(
		IntrabandProxyUtil.class);

	private static final Set<String> _annotationDescriptors =
		new HashSet<String>(
			Arrays.asList(
				Type.getDescriptor(Id.class), Type.getDescriptor(Proxy.class)));
	private static final Method _defineClassMethod;
	private static final Comparator<Method> _methodComparator =
		new MethodComparator();

	static {
		try {
			_defineClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "defineClass", String.class, byte[].class,
				int.class, int.class);
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

	private static class SkeletonDispatchTableSwitchGenerator
		implements TableSwitchGenerator {

		public SkeletonDispatchTableSwitchGenerator(
			MethodNodeGenerator methodNodeGenerator, List<Method> proxyMethods,
			String owner, int typedTargetIndex, int indexIndex) {

			_methodNodeGenerator = methodNodeGenerator;
			_proxyMethods = proxyMethods;
			_owner = owner;
			_typedTargetIndex = typedTargetIndex;
			_indexIndex = indexIndex;
		}

		@Override
		public void generateCase(int key, Label end) {
			Method proxyMethod = _proxyMethods.get(key);

			Class<?> returnClass = proxyMethod.getReturnType();

			if (returnClass != void.class) {
				_methodNodeGenerator.loadThis();

				_methodNodeGenerator.loadArg(0);
				_methodNodeGenerator.loadArg(1);

				_methodNodeGenerator.newInstance(_RPC_RESPONSE_TYPE);

				_methodNodeGenerator.dup();
			}

			_methodNodeGenerator.loadLocal(_typedTargetIndex);

			for (Class<?> parameterClass : proxyMethod.getParameterTypes()) {
				_methodNodeGenerator.loadArg(2);

				Type parameterType = Type.getType(parameterClass);

				deserializerRead(_methodNodeGenerator, parameterType);

				if (!parameterClass.isPrimitive() &&
					(parameterClass != Object.class) &&
					(parameterClass != String.class)) {

					_methodNodeGenerator.checkCast(parameterType);
				}
			}

			Class<?> declaringClass = proxyMethod.getDeclaringClass();

			if (declaringClass.isInterface()) {
				_methodNodeGenerator.invokeInterface(
					Type.getInternalName(declaringClass), proxyMethod);
			}
			else {
				_methodNodeGenerator.invokeVirtual(
					Type.getInternalName(declaringClass), proxyMethod);
			}

			if (returnClass != void.class) {
				if (returnClass.isPrimitive()) {
					_methodNodeGenerator.box(Type.getType(returnClass));
				}
				else if (Serializable.class.isAssignableFrom(returnClass)) {
					_methodNodeGenerator.checkCast(_SERIALIZABLE_TYPE);
				}

				_methodNodeGenerator.invokeSpecial(
					_RPC_RESPONSE_TYPE.getInternalName(), "<init>",
					Type.VOID_TYPE, _SERIALIZABLE_TYPE);

				_methodNodeGenerator.invokeSpecial(
					_owner, "_sendResponse", Type.VOID_TYPE,
					_REGISTRATION_REFERENCE_TYPE, _DATAGRAM_TYPE,
					_RPC_RESPONSE_TYPE);
			}

			_methodNodeGenerator.goTo(end);
		}

		@Override
		public void generateDefault() {
			_methodNodeGenerator.loadThis();

			_methodNodeGenerator.loadLocal(_indexIndex);

			_methodNodeGenerator.invokeSpecial(
				_owner, "_unknownMethodIndex", Type.VOID_TYPE, Type.INT_TYPE);
		}

		private final int _indexIndex;
		private final MethodNodeGenerator _methodNodeGenerator;
		private final String _owner;
		private final List<Method> _proxyMethods;
		private final int _typedTargetIndex;

	}

}