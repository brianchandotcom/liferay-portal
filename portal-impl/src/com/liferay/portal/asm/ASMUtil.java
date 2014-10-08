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

package com.liferay.portal.asm;

import com.liferay.portal.kernel.util.CharPool;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Shuyang Zhou
 */
public class ASMUtil {

	public static void addDefaultReturnInsns(
		MethodVisitor methodVisitor, Type returnType) {

		int sort = returnType.getSort();

		if ((sort == Type.BOOLEAN) || (sort == Type.CHAR) ||
			(sort == Type.BYTE) || (sort == Type.INT) ||
			(sort == Type.SHORT)) {

			methodVisitor.visitInsn(Opcodes.ICONST_0);
			methodVisitor.visitInsn(Opcodes.IRETURN);
		}
		else if (sort == Type.DOUBLE) {
			methodVisitor.visitInsn(Opcodes.DCONST_0);
			methodVisitor.visitInsn(Opcodes.DRETURN);
		}
		else if (sort == Type.FLOAT) {
			methodVisitor.visitInsn(Opcodes.FCONST_0);
			methodVisitor.visitInsn(Opcodes.FRETURN);
		}
		else if (sort == Type.LONG) {
			methodVisitor.visitInsn(Opcodes.LCONST_0);
			methodVisitor.visitInsn(Opcodes.LRETURN);
		}
		else if (sort == Type.VOID) {
			methodVisitor.visitInsn(Opcodes.RETURN);
		}
		else {
			methodVisitor.visitInsn(Opcodes.ACONST_NULL);
			methodVisitor.visitInsn(Opcodes.ARETURN);
		}
	}

	public static List<FieldNode> addFieldNodes(
		List<FieldNode> fieldNodes, List<FieldNode> newFieldNodes) {

		List<FieldNode> addedFieldNodes = new ArrayList<FieldNode>();

		newFieldNode:
		for (FieldNode newFieldNode : newFieldNodes) {
			String newFieldNodeName = newFieldNode.name;

			for (FieldNode fieldNode : fieldNodes) {
				if (newFieldNodeName.equals(fieldNode.name)) {
					continue newFieldNode;
				}
			}

			addedFieldNodes.add(newFieldNode);
		}

		fieldNodes.addAll(addedFieldNodes);

		return addedFieldNodes;
	}

	public static FieldNode findFieldNode(
		List<FieldNode> fieldNodes, String name) {

		for (FieldNode fieldNode : fieldNodes) {
			if (name.equals(fieldNode.name)) {
				return fieldNode;
			}
		}

		return null;
	}

	public static MethodNode findMethodNode(
		List<MethodNode> methodNodes, String name, Type returnType,
		Type... argumentTypes) {

		String desc = Type.getMethodDescriptor(returnType, argumentTypes);

		for (MethodNode methodNode : methodNodes) {
			if (name.equals(methodNode.name) && desc.equals(methodNode.desc)) {
				return methodNode;
			}
		}

		return null;
	}

	public static ClassNode loadAndRename(Class<?> clazz, String newName) {
		ClassLoader classLoader = clazz.getClassLoader();

		String name = clazz.getName();

		name = name.replace(CharPool.PERIOD, CharPool.SLASH);

		ClassReader classReader = null;

		try {
			classReader = new ClassReader(
				classLoader.getResourceAsStream(name.concat(".class")));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		ClassNode classNode = new ClassNode();

		ClassVisitor classVisitor = new RemappingClassAdapter(
			classNode,
			new RenameClassRemapper(name, newName)) {

				@Override
				public void visitInnerClass(
					String name, String outerName, String innerName,
					int access) {
				}

			};

		classReader.accept(
			classVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

		return classNode;
	}

	public static void mergeMethods(
		MethodNode containerMethodNode, MethodNode headMethodNode,
		MethodNode tailMethodNode) {

		final MethodNode methodNode = new MethodNode();

		headMethodNode.accept(
			new AdviceAdapter(
				Opcodes.ASM5, methodNode, headMethodNode.access,
				headMethodNode.name, headMethodNode.desc) {

					@Override
					protected void onMethodExit(int opcode) {
						mv = _emptyMethodVisitor;
					}

				});

		tailMethodNode.accept(
			new AdviceAdapter(
				Opcodes.ASM5, _emptyMethodVisitor, tailMethodNode.access,
				tailMethodNode.name, tailMethodNode.desc) {

					@Override
					protected void onMethodEnter() {
						mv = methodNode;
					}

				});

		containerMethodNode.instructions = methodNode.instructions;
	}

	public static MethodNode removeMethodNode(
		List<MethodNode> methodNodes, String name, Type returnType,
		Type... argumentTypes) {

		String desc = Type.getMethodDescriptor(returnType, argumentTypes);

		for (MethodNode methodNode : methodNodes) {
			if (name.equals(methodNode.name) && desc.equals(methodNode.desc)) {
				methodNodes.remove(methodNode);

				return methodNode;
			}
		}

		return null;
	}

	public static List<MethodNode> removeMethodNodes(
		List<MethodNode> methodNodes, int access) {

		List<MethodNode> removedMethodNodes = new ArrayList<MethodNode>();

		for (MethodNode methodNode : methodNodes) {
			if ((access & methodNode.access) != 0) {
				removedMethodNodes.add(methodNode);
			}
		}

		methodNodes.removeAll(removedMethodNodes);

		return removedMethodNodes;
	}

	public static List<MethodNode> removeMethodNodes(
		List<MethodNode> methodNodes, Set<String> annotations) {

		List<MethodNode> removedMethodNodes = new ArrayList<MethodNode>();

		for (MethodNode methodNode : methodNodes) {
			List<AnnotationNode> annotationNodes =
				methodNode.visibleAnnotations;

			if (annotationNodes != null) {
				for (AnnotationNode annotationNode : annotationNodes) {
					if (annotations.contains(annotationNode.desc)) {
						removedMethodNodes.add(methodNode);

						break;
					}
				}
			}
		}

		methodNodes.removeAll(removedMethodNodes);

		return removedMethodNodes;
	}

	public static List<MethodNode> removeMethodNodes(
		List<MethodNode> methodNodes, String name) {

		List<MethodNode> removedMethodNodes = new ArrayList<MethodNode>();

		for (MethodNode methodNode : methodNodes) {
			if (name.equals(methodNode.name)) {
				removedMethodNodes.add(methodNode);
			}
		}

		methodNodes.removeAll(removedMethodNodes);

		return removedMethodNodes;
	}

	private static final MethodVisitor _emptyMethodVisitor =
		new MethodVisitor(Opcodes.ASM5) {
		};

	private static class RenameClassRemapper extends Remapper {

		public RenameClassRemapper(String oldClassName, String newClassName) {
			_oldClassName = oldClassName;
			_newClassName = newClassName;
		}

		@Override
		public String map(String typeName) {
			if (typeName.equals(_oldClassName)) {
				return _newClassName;
			}

			return typeName;
		}

		private final String _newClassName;
		private final String _oldClassName;

	}

}