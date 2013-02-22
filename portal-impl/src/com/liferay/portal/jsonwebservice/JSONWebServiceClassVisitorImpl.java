/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceClassVisitor;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public class JSONWebServiceClassVisitorImpl
		implements JSONWebServiceClassVisitor, ClassVisitor {

	public JSONWebServiceClassVisitorImpl(InputStream inputStream)
		throws IOException {

		_classReader = new ClassReader(inputStream);
	}

	public void accept() throws Exception {
		_classReader.accept(this, 0);
	}

	public String getClassName() {
		return _className;
	}

	public void visit(
			int version, int access, String name, String signature,
			String superName, String[] interfaces) {

		_className = StringUtil.replace(name, CharPool.SLASH, CharPool.PERIOD);
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	public void visitAttribute(Attribute attribute) {
	}

	public void visitEnd() {
	}

	public FieldVisitor visitField(
			int access, String name, String desc, String signature,
			Object value) {

		return null;
	}

	public void visitInnerClass(
			String name, String outerName, String innerName, int access) {
	}

	public MethodVisitor visitMethod(
			int access, String name, String desc, String signature,
			String[] exceptions) {

		return null;
	}

	public void visitOuterClass(String owner, String name, String desc) {
	}

	public void visitSource(String source, String debug) {
	}

	private String _className;
	private ClassReader _classReader;

}