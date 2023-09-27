/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.enterprise;

import aQute.bnd.component.DSAnnotationReader;
import aQute.bnd.component.HeaderReader;
import aQute.bnd.component.TagResource;
import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Clazz;
import aQute.bnd.osgi.Constants;
import aQute.bnd.osgi.Descriptors;
import aQute.bnd.osgi.EmbeddedResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
import aQute.bnd.service.AnalyzerPlugin;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Tina Tian
 */
public class EnterpriseAnalyzerPlugin implements AnalyzerPlugin {

	@Override
	public boolean analyzeJar(Analyzer analyzer) throws Exception {
		String liferayEnterpriseApp = analyzer.getProperty(
			"Liferay-Enterprise-App");

		if (liferayEnterpriseApp == null) {
			return false;
		}

		String serviceComponent = analyzer.getProperty("Service-Component");

		if ((serviceComponent == null) || serviceComponent.isEmpty() ||
			serviceComponent.contains("ModulePortalProfile.xml")) {

			return false;
		}

		_processServiceComponent(analyzer, serviceComponent);

		_processProvideCapability(analyzer);

		return true;
	}

	private Resource _generateClass(String classBinaryName) {
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		classWriter.visit(
			Opcodes.V1_6, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER,
			classBinaryName, null, _BASE_CLASS_BINARY_NAME, new String[0]);

		// Constructor

		MethodVisitor constructorMethodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, "<init>",
			Type.getMethodDescriptor(Type.VOID_TYPE), null, null);

		constructorMethodVisitor.visitCode();

		constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

		constructorMethodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, _BASE_CLASS_BINARY_NAME, "<init>", "()V",
			false);

		constructorMethodVisitor.visitInsn(Opcodes.RETURN);

		constructorMethodVisitor.visitMaxs(0, 0);

		constructorMethodVisitor.visitEnd();

		// Method

		MethodVisitor methodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PROTECTED, "activate", _METHOD_DESCRIPTOR, null, null);

		methodVisitor.visitCode();

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

		methodVisitor.visitMethodInsn(
			Opcodes.INVOKEVIRTUAL, classBinaryName, "init", _METHOD_DESCRIPTOR,
			false);

		methodVisitor.visitInsn(Opcodes.RETURN);

		methodVisitor.visitMaxs(0, 0);

		methodVisitor.visitEnd();

		classWriter.visitEnd();

		return new EmbeddedResource(classWriter.toByteArray(), 0);
	}

	private Resource _generateComponentDefinition(
			Analyzer analyzer, String modulePortalProfileClassName)
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("activate:", "activate");
		properties.put("enabled:", "true");
		properties.put("immediate:", "true");
		properties.put("provide:", _INTERFACE_NAME);
		properties.put("version:", String.valueOf(DSAnnotationReader.V1_3));

		HeaderReader headerReader = new HeaderReader(analyzer);

		return new TagResource(
			headerReader.createComponentTag(
				modulePortalProfileClassName, modulePortalProfileClassName,
				properties));
	}

	private void _processProvideCapability(Analyzer analyzer) {
		Attrs attrs = new Attrs();

		attrs.put("objectClass:List<String>", _INTERFACE_NAME);
		attrs.put(Constants.USES_DIRECTIVE, "com.liferay.portal.profile");

		Parameters provideCapabilityHeaders = new Parameters(
			analyzer.getProperty(Constants.PROVIDE_CAPABILITY));

		provideCapabilityHeaders.add("osgi.service", attrs);

		analyzer.setProperty(
			Constants.PROVIDE_CAPABILITY, provideCapabilityHeaders.toString());
	}

	private void _processServiceComponent(
			Analyzer analyzer, String serviceComponent)
		throws Exception {

		String bundleSymbolicName = analyzer.getBsn();

		String className = bundleSymbolicName.concat(
			".internal.portal.profile.ModulePortalProfile");

		String classBinaryName = className.replace('.', '/');

		Resource classResource = _generateClass(classBinaryName);

		String classFile = classBinaryName.concat(".class");

		Jar jar = analyzer.getJar();

		jar.putResource(classFile, classResource);

		Map<Descriptors.TypeRef, Clazz> classes = analyzer.getClassspace();

		classes.put(
			analyzer.getTypeRefFromFQN(className),
			new Clazz(analyzer, classFile, classResource));

		String componentDefinitionFileName = "OSGI-INF/" + className + ".xml";

		jar.putResource(
			componentDefinitionFileName,
			_generateComponentDefinition(analyzer, className));

		analyzer.setProperty(
			Constants.SERVICE_COMPONENT,
			serviceComponent + "," + componentDefinitionFileName);
	}

	private static final String _BASE_CLASS_BINARY_NAME =
		"com/liferay/portal/profile/BaseEnterpriseDSModulePortalProfile";

	private static final String _INTERFACE_NAME =
		"com.liferay.portal.profile.PortalProfile";

	private static final String _METHOD_DESCRIPTOR = Type.getMethodDescriptor(
		Type.VOID_TYPE,
		Type.getObjectType("org/osgi/service/component/ComponentContext"));

}