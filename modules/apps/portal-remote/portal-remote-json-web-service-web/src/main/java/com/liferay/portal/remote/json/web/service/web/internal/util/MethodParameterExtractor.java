package com.liferay.portal.remote.json.web.service.web.internal.util;

import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MethodParameterExtractor {
	public static List<ExtractedParameter> getMethodParameters(Class<?> clazz, Method method){
		List<ExtractedParameter> extractedParameterList = new ArrayList<>();

		String resourceName = clazz.getName().replace('.', '/') + ".class";
		InputStream classStream = clazz.getClassLoader().getResourceAsStream(resourceName);

		if (classStream == null) {
			throw new IllegalArgumentException("Class not found: " + clazz.getName());
		}

		ClassReader classReader = null;
		try {
			classReader = new ClassReader(classStream);
		}
		catch (IOException e) {
			return new ArrayList<>(); //toDo: what to return when exception happens?
		}

		classReader.accept(new ClassVisitor(Opcodes.ASM9) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
				if (!name.equals(method.getName()) || !Type.getMethodDescriptor(method).equals(descriptor)) {
					return null;
				}

				return new MethodVisitor(Opcodes.ASM9) {

					@Override
					public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {

						if (!Modifier.isStatic(method.getModifiers()) && index == 0) {
							return;
						}

						if (index < method.getParameterCount() + (Modifier.isStatic(method.getModifiers()) ? 0 : 1)) {
							if (signature != null) {
								String parameterSignature = signature.replace(descriptor, "");
								extractedParameterList.add(new ExtractedParameter(name, parameterSignature));
							}
							else {
								extractedParameterList.add(new ExtractedParameter(name, descriptor));
							}
						}
					}
				};
			}
		}, ClassReader.SKIP_FRAMES);

		return extractedParameterList;
	}
}


