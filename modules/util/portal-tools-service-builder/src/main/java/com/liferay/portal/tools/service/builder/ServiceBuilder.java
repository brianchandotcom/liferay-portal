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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ArrayUtil_IW;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.portal.xml.SAXReaderFactory;
import com.liferay.util.xml.Dom4jUtil;
import com.liferay.util.xml.XMLSafeReader;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractBaseJavaEntity;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.ClassLibrary;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.qdox.model.Type;

import freemarker.ext.beans.BeansWrapper;

import freemarker.log.Logger;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

import java.beans.Introspector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 * @author Harry Mark
 * @author Tariq Dweik
 * @author Glenn Powell
 * @author Raymond Augé
 * @author Prashant Dighe
 * @author Shuyang Zhou
 * @author James Lefeu
 * @author Miguel Pastor
 * @author Cody Hoag
 * @author James Hinkey
 * @author Hugo Huijser
 */
public class ServiceBuilder {

	public static final String AUTHOR = "Brian Wing Shun Chan";

	public static boolean hasAnnotation(
		AbstractBaseJavaEntity abstractBaseJavaEntity, String annotationName) {

		Annotation[] annotations = abstractBaseJavaEntity.getAnnotations();

		if (annotations == null) {
			return false;
		}

		for (int i = 0; i < annotations.length; i++) {
			Type type = annotations[i].getType();

			JavaClass javaClass = type.getJavaClass();

			if (annotationName.equals(javaClass.getName())) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String apiDirName = arguments.get("service.api.dir");
		boolean autoImportDefaultReferences = GetterUtil.getBoolean(
			arguments.get("service.auto.import.default.references"), true);
		boolean autoNamespaceTables = GetterUtil.getBoolean(
			arguments.get("service.auto.namespace.tables"));
		String beanLocatorUtil = arguments.get("service.bean.locator.util");
		long buildNumber = GetterUtil.getLong(
			arguments.get("service.build.number"), 1);
		boolean buildNumberIncrement = GetterUtil.getBoolean(
			arguments.get("service.build.number.increment"), true);
		String hbmFileName = arguments.get("service.hbm.file");
		String implDirName = arguments.get("service.impl.dir");
		String inputFileName = arguments.get("service.input.file");
		String[] modelHintsConfigs = StringUtil.split(
			GetterUtil.getString(
				arguments.get("service.model.hints.configs"),
				StringUtil.merge(ServiceBuilderArgs.MODEL_HINTS_CONFIGS)));
		String modelHintsFileName = arguments.get("service.model.hints.file");
		boolean osgiModule = GetterUtil.getBoolean(
			arguments.get("service.osgi.module"));
		String pluginName = arguments.get("service.plugin.name");
		String propsUtil = arguments.get("service.props.util");
		String[] readOnlyPrefixes = StringUtil.split(
			GetterUtil.getString(
				arguments.get("service.read.only.prefixes"),
				StringUtil.merge(ServiceBuilderArgs.READ_ONLY_PREFIXES)));
		String[] resourceActionsConfigs = StringUtil.split(
			GetterUtil.getString(
				arguments.get("service.resource.actions.configs"),
				StringUtil.merge(ServiceBuilderArgs.RESOURCE_ACTION_CONFIGS)));
		String resourcesDirName = arguments.get("service.resources.dir");
		String springFileName = arguments.get("service.spring.file");
		String[] springNamespaces = StringUtil.split(
			arguments.get("service.spring.namespaces"));
		String sqlDirName = arguments.get("service.sql.dir");
		String sqlFileName = arguments.get("service.sql.file");
		String sqlIndexesFileName = arguments.get("service.sql.indexes.file");
		String sqlSequencesFileName = arguments.get(
			"service.sql.sequences.file");
		String targetEntityName = arguments.get("service.target.entity.name");
		String testDirName = arguments.get("service.test.dir");

		Set<String> resourceActionModels = readResourceActionModels(
			implDirName, resourcesDirName, resourceActionsConfigs);

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		modelHintsImpl.setModelHintsConfigs(modelHintsConfigs);

		modelHintsImpl.afterPropertiesSet();

		modelHintsUtil.setModelHints(modelHintsImpl);

		try {
			ServiceBuilder serviceBuilder = new ServiceBuilder(
				apiDirName, autoImportDefaultReferences, autoNamespaceTables,
				beanLocatorUtil, buildNumber, buildNumberIncrement, hbmFileName,
				implDirName, inputFileName, modelHintsFileName, osgiModule,
				pluginName, propsUtil, readOnlyPrefixes, resourceActionModels,
				resourcesDirName, springFileName, springNamespaces, sqlDirName,
				sqlFileName, sqlIndexesFileName, sqlSequencesFileName,
				targetEntityName, testDirName, true);

			String modifiedFileNames = StringUtil.merge(
				serviceBuilder.getModifiedFileNames());

			System.setProperty(
				ServiceBuilderArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Throwable t) {
			String message =
				"Please set these arguments. Sample values are:\n" +
				"\n" +
				"\tservice.api.dir=${basedir}/../portal-service/src\n" +
				"\tservice.auto.import.default.references=true\n" +
				"\tservice.auto.namespace.tables=false\n" +
				"\tservice.bean.locator.util=com.liferay.portal.kernel.bean.PortalBeanLocatorUtil\n" +
				"\tservice.build.number=1\n" +
				"\tservice.build.number.increment=true\n" +
				"\tservice.hbm.file=${basedir}/src/META-INF/portal-hbm.xml\n" +
				"\tservice.impl.dir=${basedir}/src\n" +
				"\tservice.input.file=${service.file}\n" +
				"\tservice.model.hints.configs=" + StringUtil.merge(ServiceBuilderArgs.MODEL_HINTS_CONFIGS) + "\n" +
				"\tservice.model.hints.file=${basedir}/src/META-INF/portal-model-hints.xml\n" +
				"\tservice.osgi.module=false\n" +
				"\tservice.plugin.name=\n" +
				"\tservice.props.util=com.liferay.portal.util.PropsUtil\n" +
				"\tservice.read.only.prefixes=" + StringUtil.merge(ServiceBuilderArgs.READ_ONLY_PREFIXES) + "\n" +
				"\tservice.resource.actions.configs=" + StringUtil.merge(ServiceBuilderArgs.RESOURCE_ACTION_CONFIGS) + "\n" +
				"\tservice.resources.dir=${basedir}/src\n" +
				"\tservice.spring.file=${basedir}/src/META-INF/portal-spring.xml\n" +
				"\tservice.spring.namespaces=beans\n" +
				"\tservice.sql.dir=${basedir}/../sql\n" +
				"\tservice.sql.file=portal-tables.sql\n" +
				"\tservice.sql.indexes.file=indexes.sql\n" +
				"\tservice.sql.sequences.file=sequences.sql\n" +
				"\tservice.target.entity.name=${service.target.entity.name}\n" +
				"\tservice.test.dir=${basedir}/test/integration\n" +
				"\n" +
				"You can also customize the generated code by overriding the default templates with these optional system properties:\n" +
				"\n" +
				"\t-Dservice.tpl.bad_alias_names=" + _TPL_ROOT + "bad_alias_names.txt\n"+
				"\t-Dservice.tpl.bad_column_names=" + _TPL_ROOT + "bad_column_names.txt\n"+
				"\t-Dservice.tpl.bad_json_types=" + _TPL_ROOT + "bad_json_types.txt\n"+
				"\t-Dservice.tpl.bad_table_names=" + _TPL_ROOT + "bad_table_names.txt\n"+
				"\t-Dservice.tpl.base_mode_impl=" + _TPL_ROOT + "base_mode_impl.ftl\n"+
				"\t-Dservice.tpl.blob_model=" + _TPL_ROOT + "blob_model.ftl\n"+
				"\t-Dservice.tpl.copyright.txt=copyright.txt\n"+
				"\t-Dservice.tpl.ejb_pk=" + _TPL_ROOT + "ejb_pk.ftl\n"+
				"\t-Dservice.tpl.exception=" + _TPL_ROOT + "exception.ftl\n"+
				"\t-Dservice.tpl.export_actionable_dynamic_query=" + _TPL_ROOT + "export_actionable_dynamic_query.ftl\n"+
				"\t-Dservice.tpl.extended_model=" + _TPL_ROOT + "extended_model.ftl\n"+
				"\t-Dservice.tpl.extended_model_base_impl=" + _TPL_ROOT + "extended_model_base_impl.ftl\n"+
				"\t-Dservice.tpl.extended_model_impl=" + _TPL_ROOT + "extended_model_impl.ftl\n"+
				"\t-Dservice.tpl.finder=" + _TPL_ROOT + "finder.ftl\n"+
				"\t-Dservice.tpl.finder_base_impl=" + _TPL_ROOT + "finder_base_impl.ftl\n"+
				"\t-Dservice.tpl.finder_util=" + _TPL_ROOT + "finder_util.ftl\n"+
				"\t-Dservice.tpl.hbm_xml=" + _TPL_ROOT + "hbm_xml.ftl\n"+
				"\t-Dservice.tpl.json_js=" + _TPL_ROOT + "json_js.ftl\n"+
				"\t-Dservice.tpl.json_js_method=" + _TPL_ROOT + "json_js_method.ftl\n"+
				"\t-Dservice.tpl.model=" + _TPL_ROOT + "model.ftl\n"+
				"\t-Dservice.tpl.model_cache=" + _TPL_ROOT + "model_cache.ftl\n"+
				"\t-Dservice.tpl.model_hints_xml=" + _TPL_ROOT + "model_hints_xml.ftl\n"+
				"\t-Dservice.tpl.model_impl=" + _TPL_ROOT + "model_impl.ftl\n"+
				"\t-Dservice.tpl.model_soap=" + _TPL_ROOT + "model_soap.ftl\n"+
				"\t-Dservice.tpl.model_wrapper=" + _TPL_ROOT + "model_wrapper.ftl\n"+
				"\t-Dservice.tpl.persistence=" + _TPL_ROOT + "persistence.ftl\n"+
				"\t-Dservice.tpl.persistence_impl=" + _TPL_ROOT + "persistence_impl.ftl\n"+
				"\t-Dservice.tpl.persistence_util=" + _TPL_ROOT + "persistence_util.ftl\n"+
				"\t-Dservice.tpl.props=" + _TPL_ROOT + "props.ftl\n"+
				"\t-Dservice.tpl.service=" + _TPL_ROOT + "service.ftl\n"+
				"\t-Dservice.tpl.service_base_impl=" + _TPL_ROOT + "service_base_impl.ftl\n"+
				"\t-Dservice.tpl.service_clp=" + _TPL_ROOT + "service_clp.ftl\n"+
				"\t-Dservice.tpl.service_clp_invoker=" + _TPL_ROOT + "service_clp_invoker.ftl\n"+
				"\t-Dservice.tpl.service_clp_message_listener=" + _TPL_ROOT + "service_clp_message_listener.ftl\n"+
				"\t-Dservice.tpl.service_clp_serializer=" + _TPL_ROOT + "service_clp_serializer.ftl\n"+
				"\t-Dservice.tpl.service_http=" + _TPL_ROOT + "service_http.ftl\n"+
				"\t-Dservice.tpl.service_impl=" + _TPL_ROOT + "service_impl.ftl\n"+
				"\t-Dservice.tpl.service_props_util=" + _TPL_ROOT + "service_props_util.ftl\n"+
				"\t-Dservice.tpl.service_soap=" + _TPL_ROOT + "service_soap.ftl\n"+
				"\t-Dservice.tpl.service_util=" + _TPL_ROOT + "service_util.ftl\n"+
				"\t-Dservice.tpl.service_wrapper=" + _TPL_ROOT + "service_wrapper.ftl\n"+
				"\t-Dservice.tpl.spring_xml=" + _TPL_ROOT + "spring_xml.ftl\n"+
				"\t-Dservice.tpl.spring_xml_session=" + _TPL_ROOT + "spring_xml_session.ftl";

			if (t instanceof ServiceBuilderException) {
				ServiceBuilderException serviceBuilderException =
					(ServiceBuilderException)t;

				System.err.println(serviceBuilderException.getMessage());
			}
			else if (t instanceof Exception) {
				System.out.println(message);

				ArgumentsUtil.processMainException(arguments, (Exception)t);
			}
			else {
				t.printStackTrace();
			}
		}

		try {
			ClearThreadLocalUtil.clearThreadLocal();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}

		Introspector.flushCaches();
	}

	public static Set<String> readResourceActionModels(
			String implDir, String resourcesDir,
			String[] resourceActionsConfigs)
		throws Exception {

		Set<String> resourceActionModels = new HashSet<>();

		ClassLoader classLoader = ServiceBuilder.class.getClassLoader();

		for (String config : resourceActionsConfigs) {
			if (config.startsWith("classpath*:")) {
				String name = config.substring("classpath*:".length());

				Enumeration<URL> enu = classLoader.getResources(name);

				while (enu.hasMoreElements()) {
					URL url = enu.nextElement();

					InputStream inputStream = url.openStream();

					_readResourceActionModels(
						implDir, resourcesDir, inputStream,
						resourceActionModels);
				}
			}
			else {
				Enumeration<URL> urls = classLoader.getResources(config);

				if (urls.hasMoreElements()) {
					while (urls.hasMoreElements()) {
						URL url = urls.nextElement();

						try (InputStream inputStream = url.openStream()) {
							_readResourceActionModels(
								implDir, resourcesDir, inputStream,
								resourceActionModels);
						}
					}
				}
				else {
					File file = new File(config);

					if (!file.exists()) {
						file = new File(implDir, config);
					}

					if (!file.exists() && Validator.isNotNull(resourcesDir)) {
						file = new File(resourcesDir, config);
					}

					if (!file.exists()) {
						continue;
					}

					try (InputStream inputStream = new FileInputStream(file)) {
						_readResourceActionModels(
							implDir, resourcesDir, inputStream,
							resourceActionModels);
					}
				}
			}
		}

		return resourceActionModels;
	}

	public static String toHumanName(String name) {
		if (name == null) {
			return null;
		}

		String humanName = TextFormatter.format(name, TextFormatter.H);

		if (humanName.equals("id")) {
			humanName = "ID";
		}
		else if (humanName.equals("ids")) {
			humanName = "IDs";
		}

		if (humanName.endsWith(" id")) {
			humanName = humanName.substring(0, humanName.length() - 3) + " ID";
		}
		else if (humanName.endsWith(" ids")) {
			humanName = humanName.substring(0, humanName.length() - 4) + " IDs";
		}

		if (humanName.contains(" id ")) {
			humanName = StringUtil.replace(humanName, " id ", " ID ");
		}
		else if (humanName.contains(" ids ")) {
			humanName = StringUtil.replace(humanName, " ids ", " IDs ");
		}

		return humanName;
	}

	public ServiceBuilder(
			String apiDirName, boolean autoImportDefaultReferences,
			boolean autoNamespaceTables, String beanLocatorUtil,
			long buildNumber, boolean buildNumberIncrement, String hbmFileName,
			String implDirName, String inputFileName, String modelHintsFileName,
			boolean osgiModule, String pluginName, String propsUtil,
			String[] readOnlyPrefixes, Set<String> resourceActionModels,
			String resourcesDirName, String springFileName,
			String[] springNamespaces, String sqlDirName, String sqlFileName,
			String sqlIndexesFileName, String sqlSequencesFileName,
			String targetEntityName, String testDirName, boolean build)
		throws Exception {

		_tplBadAliasNames = _getTplProperty(
			"bad_alias_names", _tplBadAliasNames);
		_tplBadColumnNames = _getTplProperty(
			"bad_column_names", _tplBadColumnNames);
		_tplBadTableNames = _getTplProperty(
			"bad_table_names", _tplBadTableNames);
		_tplBlobModel = _getTplProperty("blob_model", _tplBlobModel);
		_tplEjbPk = _getTplProperty("ejb_pk", _tplEjbPk);
		_tplException = _getTplProperty("exception", _tplException);
		_tplExtendedModel = _getTplProperty(
			"extended_model", _tplExtendedModel);
		_tplExtendedModelBaseImpl = _getTplProperty(
			"extended_model_base_impl", _tplExtendedModelBaseImpl);
		_tplExtendedModelImpl = _getTplProperty(
			"extended_model_impl", _tplExtendedModelImpl);
		_tplFinder = _getTplProperty("finder", _tplFinder);
		_tplFinderBaseImpl = _getTplProperty(
			"finder_base_impl", _tplFinderBaseImpl);
		_tplFinderUtil = _getTplProperty("finder_util", _tplFinderUtil);
		_tplHbmXml = _getTplProperty("hbm_xml", _tplHbmXml);
		_tplJsonJs = _getTplProperty("json_js", _tplJsonJs);
		_tplJsonJsMethod = _getTplProperty("json_js_method", _tplJsonJsMethod);
		_tplModel = _getTplProperty("model", _tplModel);
		_tplModelCache = _getTplProperty("model_cache", _tplModelCache);
		_tplModelClp = _getTplProperty("model", _tplModelClp);
		_tplModelHintsXml = _getTplProperty(
			"model_hints_xml", _tplModelHintsXml);
		_tplModelImpl = _getTplProperty("model_impl", _tplModelImpl);
		_tplModelSoap = _getTplProperty("model_soap", _tplModelSoap);
		_tplModelWrapper = _getTplProperty("model_wrapper", _tplModelWrapper);
		_tplPersistence = _getTplProperty("persistence", _tplPersistence);
		_tplPersistenceImpl = _getTplProperty(
			"persistence_impl", _tplPersistenceImpl);
		_tplPersistenceUtil = _getTplProperty(
			"persistence_util", _tplPersistenceUtil);
		_tplProps = _getTplProperty("props", _tplProps);
		_tplService = _getTplProperty("service", _tplService);
		_tplServiceBaseImpl = _getTplProperty(
			"service_base_impl", _tplServiceBaseImpl);
		_tplServiceClp = _getTplProperty("service_clp", _tplServiceClp);
		_tplServiceClpInvoker = _getTplProperty(
			"service_clp_invoker", _tplServiceClpInvoker);
		_tplServiceClpMessageListener = _getTplProperty(
			"service_clp_message_listener", _tplServiceClpMessageListener);
		_tplServiceClpSerializer = _getTplProperty(
			"service_clp_serializer", _tplServiceClpSerializer);
		_tplServiceHttp = _getTplProperty("service_http", _tplServiceHttp);
		_tplServiceImpl = _getTplProperty("service_impl", _tplServiceImpl);
		_tplServicePropsUtil = _getTplProperty(
			"service_props_util", _tplServicePropsUtil);
		_tplServiceSoap = _getTplProperty("service_soap", _tplServiceSoap);
		_tplServiceUtil = _getTplProperty("service_util", _tplServiceUtil);
		_tplServiceWrapper = _getTplProperty(
			"service_wrapper", _tplServiceWrapper);
		_tplSpringXml = _getTplProperty("spring_xml", _tplSpringXml);

		try {
			_apiDirName = apiDirName;
			_autoImportDefaultReferences = autoImportDefaultReferences;
			_autoNamespaceTables = autoNamespaceTables;
			_beanLocatorUtil = beanLocatorUtil;
			_buildNumber = buildNumber;
			_buildNumberIncrement = buildNumberIncrement;
			_hbmFileName = hbmFileName;
			_implDirName = implDirName;
			_modelHintsFileName = modelHintsFileName;
			_osgiModule = osgiModule;
			_pluginName = GetterUtil.getString(pluginName);
			_propsUtil = propsUtil;
			_readOnlyPrefixes = readOnlyPrefixes;
			_resourceActionModels = resourceActionModels;
			_resourcesDirName = resourcesDirName;
			_springFileName = springFileName;

			_springNamespaces = springNamespaces;

			if (!ArrayUtil.contains(
					_springNamespaces, _SPRING_NAMESPACE_BEANS)) {

				_springNamespaces = ArrayUtil.append(
					_springNamespaces, _SPRING_NAMESPACE_BEANS);
			}

			_sqlDirName = sqlDirName;
			_sqlFileName = sqlFileName;
			_sqlIndexesFileName = sqlIndexesFileName;
			_sqlSequencesFileName = sqlSequencesFileName;
			_targetEntityName = targetEntityName;
			_testDirName = testDirName;
			_build = build;

			_badTableNames = _readLines(_tplBadTableNames);
			_badAliasNames = _readLines(_tplBadAliasNames);
			_badColumnNames = _readLines(_tplBadColumnNames);

			_beanLocatorUtilShortName = _beanLocatorUtil.substring(
				_beanLocatorUtil.lastIndexOf(".") + 1);

			SAXReader saxReader = _getSAXReader();

			Document document = saxReader.read(
				new XMLSafeReader(ToolsUtil.getContent(inputFileName)));

			Element rootElement = document.getRootElement();

			String packagePath = rootElement.attributeValue("package-path");

			if (Validator.isNull(packagePath)) {
				throw new IllegalArgumentException(
					"The package-path attribute is required");
			}

			_outputPath =
				_implDirName + "/" + StringUtil.replace(packagePath, ".", "/");

			_serviceOutputPath =
				_apiDirName + "/" + StringUtil.replace(packagePath, ".", "/");

			if (Validator.isNotNull(_testDirName)) {
				_testOutputPath =
					_testDirName + "/" +
						StringUtil.replace(packagePath, ".", "/");
			}

			_packagePath = packagePath;

			_autoImportDefaultReferences = GetterUtil.getBoolean(
				rootElement.attributeValue("auto-import-default-references"),
				_autoImportDefaultReferences);
			_autoNamespaceTables = GetterUtil.getBoolean(
				rootElement.attributeValue("auto-namespace-tables"),
				_autoNamespaceTables);
			_mvccEnabled = GetterUtil.getBoolean(
				rootElement.attributeValue("mvcc-enabled"));

			Element authorElement = rootElement.element("author");

			if (authorElement != null) {
				_author = authorElement.getText();
			}
			else {
				_author = AUTHOR;
			}

			Element portletElement = rootElement.element("portlet");
			Element namespaceElement = rootElement.element("namespace");

			if (portletElement != null) {
				_portletName = portletElement.attributeValue("name");

				_portletShortName = portletElement.attributeValue("short-name");

				_portletPackageName = TextFormatter.format(
					_portletName, TextFormatter.B);

				_outputPath += "/" + _portletPackageName;

				_serviceOutputPath += "/" + _portletPackageName;

				_testOutputPath += "/" + _portletPackageName;

				_packagePath += "." + _portletPackageName;
			}
			else {
				_portletShortName = namespaceElement.getText();
			}

			_portletShortName = _portletShortName.trim();

			for (char c : _portletShortName.toCharArray()) {
				if (!Validator.isChar(c) && (c != CharPool.UNDERLINE)) {
					throw new RuntimeException(
						"The namespace element must be a valid keyword");
				}
			}

			_ejbList = new ArrayList<>();
			_entityMappings = new HashMap<>();

			List<Element> entityElements = rootElement.elements("entity");

			for (Element entityElement : entityElements) {
				_parseEntity(entityElement);
			}

			List<String> exceptionList = new ArrayList<>();

			Element exceptionsElement = rootElement.element("exceptions");

			if (exceptionsElement != null) {
				List<Element> exceptionElements = exceptionsElement.elements(
					"exception");

				for (Element exceptionElement : exceptionElements) {
					exceptionList.add(exceptionElement.getText());
				}
			}

			if (build) {
				for (int x = 0; x < _ejbList.size(); x++) {
					Entity entity = _ejbList.get(x);

					if (_isTargetEntity(entity)) {
						System.out.println("Building " + entity.getName());

						_resolveEntity(entity);

						if (entity.hasActionableDynamicQuery()) {
							_createActionableDynamicQuery(entity);

							if (entity.isStagedModel()) {
								_createExportActionableDynamicQuery(entity);
							}
							else {
								_removeExportActionableDynamicQuery(entity);
							}
						}
						else {
							_removeActionableDynamicQuery(entity);
							_removeExportActionableDynamicQuery(entity);
						}

						if (entity.hasColumns()) {
							_createHbm(entity);
							_createHbmUtil(entity);

							_createPersistenceImpl(entity);
							_createPersistence(entity);
							_createPersistenceUtil(entity);

							if (Validator.isNotNull(_testDirName)) {
								_createPersistenceTest(entity);
							}

							_createModelImpl(entity);
							_createExtendedModelBaseImpl(entity);
							_createExtendedModelImpl(entity);

							entity.setTransients(_getTransients(entity, false));
							entity.setParentTransients(
								_getTransients(entity, true));

							_createModel(entity);
							_createExtendedModel(entity);

							_createModelCache(entity);
							_createModelClp(entity);
							_createModelWrapper(entity);

							_createModelSoap(entity);

							_createBlobModels(entity);

							_createPool(entity);

							if (entity.getPKList().size() > 1) {
								_createEJBPK(entity);
							}
						}

						_createFinder(entity);
						_createFinderBaseImpl(entity);
						_createFinderUtil(entity);

						if (entity.hasLocalService()) {
							_createServiceImpl(entity, _SESSION_TYPE_LOCAL);
							_createServiceBaseImpl(entity, _SESSION_TYPE_LOCAL);
							_createService(entity, _SESSION_TYPE_LOCAL);
							_createServiceFactory(entity, _SESSION_TYPE_LOCAL);
							_createServiceUtil(entity, _SESSION_TYPE_LOCAL);

							_createServiceClp(entity, _SESSION_TYPE_LOCAL);
							_createServiceClpInvoker(
								entity, _SESSION_TYPE_LOCAL);
							_createServiceWrapper(entity, _SESSION_TYPE_LOCAL);
						}
						else {
							_removeServiceImpl(entity, _SESSION_TYPE_LOCAL);
							_removeServiceBaseImpl(entity, _SESSION_TYPE_LOCAL);
							_removeService(entity, _SESSION_TYPE_LOCAL);
							_removeServiceUtil(entity, _SESSION_TYPE_LOCAL);

							_removeServiceClp(entity, _SESSION_TYPE_LOCAL);
							_removeServiceClpInvoker(
								entity, _SESSION_TYPE_LOCAL);
							_removeServiceWrapper(entity, _SESSION_TYPE_LOCAL);
						}

						if (entity.hasRemoteService()) {
							_createServiceImpl(entity, _SESSION_TYPE_REMOTE);
							_createServiceBaseImpl(
								entity, _SESSION_TYPE_REMOTE);
							_createService(entity, _SESSION_TYPE_REMOTE);
							_createServiceFactory(entity, _SESSION_TYPE_REMOTE);
							_createServiceUtil(entity, _SESSION_TYPE_REMOTE);

							_createServiceClp(entity, _SESSION_TYPE_REMOTE);
							_createServiceClpInvoker(
								entity, _SESSION_TYPE_REMOTE);
							_createServiceWrapper(entity, _SESSION_TYPE_REMOTE);

							_createServiceHttp(entity);

							_createServiceJson(entity);

							if (entity.hasColumns()) {
								_createServiceJsonSerializer(entity);
							}

							_createServiceSoap(entity);
						}
						else {
							_removeServiceImpl(entity, _SESSION_TYPE_REMOTE);
							_removeServiceBaseImpl(
								entity, _SESSION_TYPE_REMOTE);
							_removeService(entity, _SESSION_TYPE_REMOTE);
							_removeServiceUtil(entity, _SESSION_TYPE_REMOTE);

							_removeServiceClp(entity, _SESSION_TYPE_REMOTE);
							_removeServiceClpInvoker(
								entity, _SESSION_TYPE_REMOTE);
							_removeServiceWrapper(entity, _SESSION_TYPE_REMOTE);

							_removeServiceHttp(entity);

							_removeServiceSoap(entity);
						}
					}
					else {
						if (entity.hasColumns()) {
							entity.setTransients(_getTransients(entity, false));
							entity.setParentTransients(
								_getTransients(entity, true));
						}
					}
				}

				_createHbmXml();
				_createModelHintsXml();
				_createSpringXml();

				_createExceptions(exceptionList);

				_createServiceClpMessageListener();
				_createServiceClpSerializer(exceptionList);
				_createServicePropsUtil();

				_createSQLIndexes();
				_createSQLTables();
				_createSQLSequences();

				_createProps();

				_deleteOrmXml();
				_deleteSpringLegacyXml();
			}
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
	}

	public ServiceBuilder(
			String apiDir, boolean autoImportDefaultReferences,
			boolean autoNamespaceTables, String beanLocatorUtil,
			String hbmFileName, String implDir, String inputFileName,
			String modelHintsFileName, boolean osgiModule, String pluginName,
			String propsUtil, String[] readOnlyPrefixes,
			Set<String> resourceActionModels, String resourcesDir,
			String springFileName, String[] springNamespaces, String sqlDir,
			String sqlFileName, String sqlIndexesFileName,
			String sqlSequencesFileName, String targetEntityName,
			String testDir)
		throws Exception {

		this(
			apiDir, autoImportDefaultReferences, autoNamespaceTables,
			beanLocatorUtil, 1, true, hbmFileName, implDir, inputFileName,
			modelHintsFileName, osgiModule, pluginName, propsUtil,
			readOnlyPrefixes, resourceActionModels, resourcesDir,
			springFileName, springNamespaces, sqlDir, sqlFileName,
			sqlIndexesFileName, sqlSequencesFileName, targetEntityName, testDir,
			true);
	}

	public String annotationToString(Annotation annotation) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.AT);

		Type type = annotation.getType();

		sb.append(type.getValue());

		Map<String, Object> namedParameters = annotation.getNamedParameterMap();

		if (namedParameters.isEmpty()) {
			return sb.toString();
		}

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (Map.Entry<String, Object> entry : namedParameters.entrySet()) {
			sb.append(entry.getKey());

			sb.append(StringPool.EQUAL);

			Object value = entry.getValue();

			if (value instanceof List) {
				List<?> values = (List<?>)value;

				sb.append(StringPool.OPEN_CURLY_BRACE);

				for (Object object : values) {
					if (object instanceof Annotation) {
						sb.append(annotationToString((Annotation)object));
					}
					else {
						sb.append(object);
					}

					sb.append(StringPool.COMMA_AND_SPACE);
				}

				if (!values.isEmpty()) {
					sb.setIndex(sb.index() - 1);
				}

				sb.append(StringPool.CLOSE_CURLY_BRACE);
			}
			else {
				sb.append(value);
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public String getCacheFieldMethodName(JavaField javaField) {
		Annotation[] annotations = javaField.getAnnotations();

		for (Annotation annotation : annotations) {
			Type type = annotation.getType();

			String className = type.getFullyQualifiedName();

			if (className.equals(CacheField.class.getName())) {
				String methodName = null;

				Object namedParameter = annotation.getNamedParameter(
					"methodName");

				if (namedParameter != null) {
					methodName = StringUtil.unquote(
						StringUtil.trim(namedParameter.toString()));
				}

				if (Validator.isNull(methodName)) {
					methodName = TextFormatter.format(
						getVariableName(javaField), TextFormatter.G);
				}

				return methodName;
			}
		}

		throw new IllegalArgumentException(javaField + " is not a cache field");
	}

	public String getClassName(Type type) {
		int dimensions = type.getDimensions();
		String name = type.getValue();

		if (dimensions == 0) {
			return name;
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < dimensions; i++) {
			sb.append("[");
		}

		if (name.equals("boolean")) {
			return sb.append("Z").toString();
		}
		else if (name.equals("byte")) {
			return sb.append("B").toString();
		}
		else if (name.equals("char")) {
			return sb.append("C").toString();
		}
		else if (name.equals("double")) {
			return sb.append("D").toString();
		}
		else if (name.equals("float")) {
			return sb.append("F").toString();
		}
		else if (name.equals("int")) {
			return sb.append("I").toString();
		}
		else if (name.equals("long")) {
			return sb.append("J").toString();
		}
		else if (name.equals("short")) {
			return sb.append("S").toString();
		}
		else {
			return sb.append("L").append(name).append(";").toString();
		}
	}

	public String getCreateMappingTableSQL(EntityMapping entityMapping)
		throws Exception {

		String createMappingTableSQL = _getCreateMappingTableSQL(entityMapping);

		createMappingTableSQL = StringUtil.replace(
			createMappingTableSQL, "\n", "");
		createMappingTableSQL = StringUtil.replace(
			createMappingTableSQL, "\t", "");
		createMappingTableSQL = createMappingTableSQL.substring(
			0, createMappingTableSQL.length() - 1);

		return createMappingTableSQL;
	}

	public String getCreateTableSQL(Entity entity) {
		String createTableSQL = _getCreateTableSQL(entity);

		createTableSQL = StringUtil.replace(createTableSQL, "\n", "");
		createTableSQL = StringUtil.replace(createTableSQL, "\t", "");
		createTableSQL = createTableSQL.substring(
			0, createTableSQL.length() - 1);

		return createTableSQL;
	}

	public String getDimensions(int dims) {
		String dimensions = "";

		for (int i = 0; i < dims; i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	public String getDimensions(String dims) {
		return getDimensions(GetterUtil.getInteger(dims));
	}

	public Entity getEntity(String name) throws Exception {
		Entity entity = _entityPool.get(name);

		if (entity != null) {
			return entity;
		}

		int pos = name.lastIndexOf(".");

		if (pos == -1) {
			pos = _ejbList.indexOf(new Entity(name));

			if (pos == -1) {
				throw new ServiceBuilderException(
					"Unable to find " + name + " in " +
						ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
			}

			entity = _ejbList.get(pos);

			_entityPool.put(name, entity);

			return entity;
		}

		String refPackage = name.substring(0, pos);
		String refEntity = name.substring(pos + 1);

		if (refPackage.equals(_packagePath)) {
			pos = _ejbList.indexOf(new Entity(refEntity));

			if (pos == -1) {
				throw new ServiceBuilderException(
					"Unable to find " + refEntity + " in " +
						ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
			}

			entity = _ejbList.get(pos);

			_entityPool.put(name, entity);

			return entity;
		}

		String refPackageDirName = StringUtil.replace(refPackage, ".", "/");

		String refFileName =
			_implDirName + "/" + refPackageDirName + "/service.xml";

		File refFile = new File(refFileName);

		boolean useTempFile = false;

		if (!refFile.exists()) {
			refFileName = String.valueOf(System.currentTimeMillis());
			refFile = new File(_TMP_DIR, refFileName);

			ClassLoader classLoader = getClass().getClassLoader();

			String refContent = null;

			try {
				refContent = StringUtil.read(
					classLoader, refPackageDirName + "/service.xml");
			}
			catch (IOException ioe) {
				throw new ServiceBuilderException(
					"Unable to find " + refEntity + " in " +
						ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
			}

			_write(refFile, refContent);

			useTempFile = true;
		}

		ServiceBuilder serviceBuilder = new ServiceBuilder(
			_apiDirName, _autoImportDefaultReferences, _autoNamespaceTables,
			_beanLocatorUtil, _buildNumber, _buildNumberIncrement, _hbmFileName,
			_implDirName, refFile.getAbsolutePath(), _modelHintsFileName,
			_osgiModule, _pluginName, _propsUtil, _readOnlyPrefixes,
			_resourceActionModels, _resourcesDirName, _springFileName,
			_springNamespaces, _sqlDirName, _sqlFileName, _sqlIndexesFileName,
			_sqlSequencesFileName, _targetEntityName, _testDirName, false);

		entity = serviceBuilder.getEntity(refEntity);

		entity.setPortalReference(useTempFile);

		_entityPool.put(name, entity);

		_modifiedFileNames.addAll(serviceBuilder.getModifiedFileNames());

		if (useTempFile) {
			refFile.deleteOnExit();
		}

		return entity;
	}

	public Entity getEntityByGenericsName(String genericsName) {
		try {
			String name = genericsName;

			if (name.startsWith("<")) {
				name = name.substring(1, name.length() - 1);
			}

			name = StringUtil.replace(name, ".model.", ".");

			return getEntity(name);
		}
		catch (Exception e) {
			return null;
		}
	}

	public Entity getEntityByParameterTypeValue(String parameterTypeValue) {
		try {
			String name = parameterTypeValue;

			name = StringUtil.replace(name, ".model.", ".");

			return getEntity(name);
		}
		catch (Exception e) {
			return null;
		}
	}

	public EntityMapping getEntityMapping(String mappingTable) {
		return _entityMappings.get(mappingTable);
	}

	public String getGeneratorClass(String idType) {
		if (Validator.isNull(idType)) {
			idType = "assigned";
		}

		return idType;
	}

	public String getJavadocComment(JavaClass javaClass) {
		return _formatComment(
			javaClass.getComment(), javaClass.getTags(), StringPool.BLANK);
	}

	public String getJavadocComment(JavaMethod javaMethod) {
		return _formatComment(
			javaMethod.getComment(), javaMethod.getTags(), StringPool.TAB);
	}

	public String getListActualTypeArguments(Type type) {
		if (type.getValue().equals("java.util.List")) {
			Type[] types = type.getActualTypeArguments();

			if (types != null) {
				return getTypeGenericsName(types[0]);
			}
		}

		return getTypeGenericsName(type);
	}

	public String getLiteralClass(Type type) {
		StringBundler sb = new StringBundler(type.getDimensions() + 2);

		sb.append(type.getValue());

		for (int i = 0; i < type.getDimensions(); i++) {
			sb.append("[]");
		}

		sb.append(".class");

		return sb.toString();
	}

	public List<EntityColumn> getMappingEntities(String mappingTable)
		throws Exception {

		List<EntityColumn> mappingEntitiesPKList = new ArrayList<>();

		EntityMapping entityMapping = _entityMappings.get(mappingTable);

		for (int i = 0; i < 3; i++) {
			Entity entity = getEntity(entityMapping.getEntity(i));

			if (entity == null) {
				return null;
			}

			mappingEntitiesPKList.addAll(entity.getPKList());
		}

		return mappingEntitiesPKList;
	}

	public int getMaxLength(String model, String field) {
		Map<String, String> hints = ModelHintsUtil.getHints(model, field);

		if (hints == null) {
			return _DEFAULT_COLUMN_MAX_LENGTH;
		}

		return GetterUtil.getInteger(
			hints.get("max-length"), _DEFAULT_COLUMN_MAX_LENGTH);
	}

	public Set<String> getModifiedFileNames() {
		return _modifiedFileNames;
	}

	public String getNoSuchEntityException(Entity entity) {
		String noSuchEntityException = entity.getName();

		if (Validator.isNull(entity.getPortletShortName()) ||
			(noSuchEntityException.startsWith(entity.getPortletShortName()) &&
			 !noSuchEntityException.equals(entity.getPortletShortName()))) {

			noSuchEntityException = noSuchEntityException.substring(
				entity.getPortletShortName().length());
		}

		noSuchEntityException = "NoSuch" + noSuchEntityException;

		return noSuchEntityException;
	}

	public String getParameterType(JavaParameter parameter) {
		Type returnType = parameter.getType();

		return getTypeGenericsName(returnType);
	}

	public String getPrimitiveObj(String type) {
		if (type.equals("boolean")) {
			return "Boolean";
		}
		else if (type.equals("double")) {
			return "Double";
		}
		else if (type.equals("float")) {
			return "Float";
		}
		else if (type.equals("int")) {
			return "Integer";
		}
		else if (type.equals("long")) {
			return "Long";
		}
		else if (type.equals("short")) {
			return "Short";
		}
		else {
			return type;
		}
	}

	public String getPrimitiveObjValue(String colType) {
		if (colType.equals("Boolean")) {
			return ".booleanValue()";
		}
		else if (colType.equals("Double")) {
			return ".doubleValue()";
		}
		else if (colType.equals("Float")) {
			return ".floatValue()";
		}
		else if (colType.equals("Integer")) {
			return ".intValue()";
		}
		else if (colType.equals("Long")) {
			return ".longValue()";
		}
		else if (colType.equals("Short")) {
			return ".shortValue()";
		}

		return StringPool.BLANK;
	}

	public String getReturnType(JavaMethod method) {
		Type returnType = method.getReturnType();

		return getTypeGenericsName(returnType);
	}

	public List<String> getServiceBaseExceptions(
		List<JavaMethod> methods, String methodName, List<String> args,
		List<String> exceptions) {

		boolean foundMethod = false;

		for (JavaMethod method : methods) {
			JavaParameter[] parameters = method.getParameters();

			if (method.getName().equals(methodName) &&
				(parameters.length == args.size())) {

				for (int i = 0; i < parameters.length; i++) {
					JavaParameter parameter = parameters[i];

					String arg = args.get(i);

					if (getParameterType(parameter).equals(arg)) {
						exceptions = ListUtil.copy(exceptions);

						Type[] methodExceptions = method.getExceptions();

						for (Type methodException : methodExceptions) {
							String exception = methodException.getValue();

							if (exception.equals(
									PortalException.class.getName())) {

								exception = "PortalException";
							}

							if (exception.equals(
									SystemException.class.getName())) {

								exception = "SystemException";
							}

							if (!exceptions.contains(exception)) {
								exceptions.add(exception);
							}
						}

						Collections.sort(exceptions);

						foundMethod = true;

						break;
					}
				}
			}

			if (foundMethod) {
				break;
			}
		}

		if (!exceptions.isEmpty()) {
			return exceptions;
		}
		else {
			return Collections.emptyList();
		}
	}

	public String getSqlType(String type) {
		if (type.equals("boolean") || type.equals("Boolean")) {
			return "BOOLEAN";
		}
		else if (type.equals("double") || type.equals("Double")) {
			return "DOUBLE";
		}
		else if (type.equals("float") || type.equals("Float")) {
			return "FLOAT";
		}
		else if (type.equals("int") || type.equals("Integer")) {
			return "INTEGER";
		}
		else if (type.equals("long") || type.equals("Long")) {
			return "BIGINT";
		}
		else if (type.equals("short") || type.equals("Short")) {
			return "INTEGER";
		}
		else if (type.equals("Date")) {
			return "TIMESTAMP";
		}
		else {
			return null;
		}
	}

	public String getSqlType(String model, String field, String type) {
		if (type.equals("boolean") || type.equals("Boolean")) {
			return "BOOLEAN";
		}
		else if (type.equals("double") || type.equals("Double")) {
			return "DOUBLE";
		}
		else if (type.equals("float") || type.equals("Float")) {
			return "FLOAT";
		}
		else if (type.equals("int") || type.equals("Integer")) {
			return "INTEGER";
		}
		else if (type.equals("long") || type.equals("Long")) {
			return "BIGINT";
		}
		else if (type.equals("short") || type.equals("Short")) {
			return "INTEGER";
		}
		else if (type.equals("Blob")) {
			return "BLOB";
		}
		else if (type.equals("Date")) {
			return "TIMESTAMP";
		}
		else if (type.equals("Map")) {
			return "CLOB";
		}
		else if (type.equals("String")) {
			int maxLength = getMaxLength(model, field);

			if (maxLength == 2000000) {
				return "CLOB";
			}

			return "VARCHAR";
		}
		else {
			return null;
		}
	}

	public String getTypeGenericsName(Type type) {
		StringBundler sb = new StringBundler();

		sb.append(type.getValue());

		Type[] actualTypeArguments = type.getActualTypeArguments();

		if (actualTypeArguments != null) {
			sb.append(StringPool.LESS_THAN);

			for (Type actualTypeArgument : actualTypeArguments) {
				sb.append(getTypeGenericsName(actualTypeArgument));

				sb.append(StringPool.COMMA_AND_SPACE);
			}

			sb.setIndex(sb.index() - 1);

			sb.append(StringPool.GREATER_THAN);
		}

		sb.append(getDimensions(type.getDimensions()));

		return sb.toString();
	}

	public String getVariableName(JavaField field) {
		String fieldName = field.getName();

		if ((fieldName.length() > 0) && (fieldName.charAt(0) == '_')) {
			fieldName = fieldName.substring(1);
		}

		return fieldName;
	}

	public boolean hasEntityByGenericsName(String genericsName) {
		if (Validator.isNull(genericsName)) {
			return false;
		}

		if (!genericsName.contains(".model.")) {
			return false;
		}

		if (getEntityByGenericsName(genericsName) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean hasEntityByParameterTypeValue(String parameterTypeValue) {
		if (Validator.isNull(parameterTypeValue)) {
			return false;
		}

		if (!parameterTypeValue.contains(".model.")) {
			return false;
		}

		if (getEntityByParameterTypeValue(parameterTypeValue) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isBasePersistenceMethod(JavaMethod method) {
		String methodName = method.getName();

		if (methodName.equals("clearCache") ||
			methodName.equals("findWithDynamicQuery")) {

			return true;
		}
		else if (methodName.equals("findByPrimaryKey") ||
				 methodName.equals("fetchByPrimaryKey") ||
				 methodName.equals("remove")) {

			JavaParameter[] parameters = method.getParameters();

			if ((parameters.length == 1) &&
				parameters[0].getName().equals("primaryKey")) {

				return true;
			}

			if (methodName.equals("remove")) {
				Type[] methodExceptions = method.getExceptions();

				for (Type methodException : methodExceptions) {
					String exception = methodException.getValue();

					if (exception.contains("NoSuch")) {
						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	public boolean isCustomMethod(JavaMethod method) {
		String methodName = method.getName();

		if (methodName.equals("afterPropertiesSet") ||
			methodName.equals("destroy") || methodName.equals("equals") ||
			methodName.equals("getClass") || methodName.equals("hashCode") ||
			methodName.equals("notify") || methodName.equals("notifyAll") ||
			methodName.equals("toString") || methodName.equals("wait")) {

			return false;
		}
		else if (methodName.equals("getPermissionChecker")) {
			return false;
		}
		else if (methodName.equals("getUser") &&
				 (method.getParameters().length == 0)) {

			return false;
		}
		else if (methodName.equals("getUserId") &&
				 (method.getParameters().length == 0)) {

			return false;
		}
		else if (methodName.endsWith("Finder") &&
				 (methodName.startsWith("get") ||
				  methodName.startsWith("set"))) {

			return false;
		}
		else if (methodName.endsWith("Persistence") &&
				 (methodName.startsWith("get") ||
				  methodName.startsWith("set"))) {

			return false;
		}
		else if (methodName.endsWith("Service") &&
				 (methodName.startsWith("get") ||
				  methodName.startsWith("set"))) {

			return false;
		}
		else {
			return true;
		}
	}

	public boolean isHBMCamelCasePropertyAccessor(String propertyName) {
		if (propertyName.length() < 3) {
			return false;
		}

		char[] chars = propertyName.toCharArray();

		char c0 = chars[0];
		char c1 = chars[1];
		char c2 = chars[2];

		if (Character.isLowerCase(c0) && Character.isUpperCase(c1) &&
			Character.isLowerCase(c2)) {

			return true;
		}

		return false;
	}

	public boolean isReadOnlyMethod(
		JavaMethod method, List<String> txRequiredList, String[] prefixes) {

		String methodName = method.getName();

		if (isTxRequiredMethod(method, txRequiredList)) {
			return false;
		}

		for (String prefix : prefixes) {
			if (methodName.startsWith(prefix)) {
				return true;
			}
		}

		return false;
	}

	public boolean isServiceReadOnlyMethod(
		JavaMethod method, List<String> txRequiredList) {

		return isReadOnlyMethod(method, txRequiredList, _readOnlyPrefixes);
	}

	public boolean isSoapMethod(JavaMethod method) {
		Type returnType = method.getReturnType();

		String returnTypeGenericsName = getTypeGenericsName(returnType);
		String returnValueName = returnType.getValue();

		if (returnTypeGenericsName.contains(
				"com.liferay.portal.kernel.search.") ||
			returnTypeGenericsName.contains("com.liferay.portal.model.Theme") ||
			returnTypeGenericsName.contains(
				"com.liferay.portlet.social.model.SocialActivityDefinition") ||
			returnTypeGenericsName.equals("java.util.List<java.lang.Object>") ||
			returnValueName.equals("com.liferay.portal.model.Lock") ||
			returnValueName.equals(
				"com.liferay.portlet.messageboards.model.MBMessageDisplay") ||
			returnValueName.startsWith("java.io") ||
			returnValueName.equals("java.util.Map") ||
			returnValueName.equals("java.util.Properties") ||
			returnValueName.startsWith("javax")) {

			return false;
		}

		if (returnTypeGenericsName.contains(
				"com.liferay.portal.kernel.repository.model.FileEntry") ||
			returnTypeGenericsName.contains(
				"com.liferay.portal.kernel.repository.model.Folder")) {
		}
		else if (returnTypeGenericsName.contains(
					"com.liferay.portal.kernel.repository.")) {

			return false;
		}

		JavaParameter[] parameters = method.getParameters();

		for (JavaParameter javaParameter : parameters) {
			Type type = javaParameter.getType();

			String parameterTypeName = type.getValue() + _getDimensions(type);

			if (parameterTypeName.equals(
					"com.liferay.portal.kernel.util.UnicodeProperties") ||
				parameterTypeName.equals(
					"com.liferay.portal.theme.ThemeDisplay") ||
				parameterTypeName.equals(
					"com.liferay.portlet.PortletPreferencesImpl") ||
				parameterTypeName.equals(
					"com.liferay.portlet.dynamicdatamapping.Fields") ||
				parameterTypeName.startsWith("java.io") ||
				parameterTypeName.startsWith("java.util.LinkedHashMap") ||
				//parameterTypeName.startsWith("java.util.List") ||
				//parameterTypeName.startsWith("java.util.Locale") ||
				(parameterTypeName.startsWith("java.util.Map") &&
				 !_isStringLocaleMap(javaParameter)) ||
				parameterTypeName.startsWith("java.util.Properties") ||
				parameterTypeName.startsWith("javax")) {

				return false;
			}
		}

		return true;
	}

	public boolean isTxRequiredMethod(
		JavaMethod method, List<String> txRequiredList) {

		if (txRequiredList == null) {
			return false;
		}

		if (txRequiredList.contains(method.getName())) {
			return true;
		}

		return false;
	}

	private static SAXReader _getSAXReader() {
		return SAXReaderFactory.getSAXReader(null, false, false);
	}

	private static void _mkdir(File dir) throws IOException {
		Files.createDirectories(dir.toPath());
	}

	private static void _move(File sourceFile, File destinationFile)
		throws IOException {

		Path destinationPath = destinationFile.toPath();

		if (!Files.exists(destinationPath)) {
			Files.createDirectories(destinationPath);
		}

		Files.move(sourceFile.toPath(), destinationPath);
	}

	private static String _read(File file) throws IOException {
		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
	}

	private static void _readResourceActionModels(
			String implDir, String resourcesDir, InputStream inputStream,
			Set<String> resourceActionModels)
		throws Exception {

		SAXReader saxReader = _getSAXReader();

		Document document = saxReader.read(inputStream);

		Element rootElement = document.getRootElement();

		List<Element> resourceElements = rootElement.elements("resource");

		for (Element resourceElement : resourceElements) {
			resourceActionModels.addAll(
				readResourceActionModels(
					implDir, resourcesDir,
					new String[] {resourceElement.attributeValue("file")}));
		}

		XPath xPath = document.createXPath("//model-resource/model-name");

		List<Element> elements = xPath.selectNodes(rootElement);

		for (Element element : elements) {
			resourceActionModels.add(element.getText().trim());
		}
	}

	private static void _touch(File file) throws IOException {
		_mkdir(file.getParentFile());

		Files.createFile(file.toPath());
	}

	private static void _write(File file, String s) throws IOException {
		Path path = file.toPath();

		Files.createDirectories(path.getParent());

		Files.write(path, s.getBytes(StandardCharsets.UTF_8));
	}

	private void _addIndexMetadata(
		Map<String, List<IndexMetadata>> indexMetadataMap, String tableName,
		IndexMetadata indexMetadata) {

		List<IndexMetadata> indexMetadataList = indexMetadataMap.get(tableName);

		if (indexMetadataList == null) {
			indexMetadataList = new ArrayList<>();

			indexMetadataMap.put(tableName, indexMetadataList);
		}

		Iterator<IndexMetadata> iterator = indexMetadataList.iterator();

		while (iterator.hasNext()) {
			IndexMetadata currentIndexMetadata = iterator.next();

			Boolean redundant = currentIndexMetadata.redundantTo(indexMetadata);

			if (redundant == null) {
				continue;
			}

			if (redundant) {
				iterator.remove();
			}
			else {
				indexMetadata = null;

				break;
			}
		}

		if (indexMetadata != null) {
			indexMetadataList.add(indexMetadata);
		}
	}

	private void _createActionableDynamicQuery(Entity entity) throws Exception {
		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ActionableDynamicQuery.java");

		if (_osgiModule) {
			ejbFile.delete();

			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplActionableDynamicQuery, context);

		// Write file

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createBlobModels(Entity entity) throws Exception {
		List<EntityColumn> blobList = new ArrayList<>(entity.getBlobList());

		Iterator<EntityColumn> itr = blobList.iterator();

		while (itr.hasNext()) {
			EntityColumn col = itr.next();

			if (!col.isLazy()) {
				itr.remove();
			}
		}

		if (blobList.isEmpty()) {
			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		for (EntityColumn col : blobList) {
			context.put("column", col);

			// Content

			String content = _processTemplate(_tplBlobModel, context);

			// Write file

			File blobModelFile = new File(
				_serviceOutputPath + "/model/" + entity.getName() +
					col.getMethodName() + "BlobModel.java");

			ToolsUtil.writeFile(
				blobModelFile, content, _author, _modifiedFileNames);
		}
	}

	private void _createEJBPK(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplEjbPk, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" +
				entity.getPKClassName() + ".java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createExceptions(List<String> exceptions) throws Exception {
		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!_isTargetEntity(entity)) {
				continue;
			}

			if (entity.hasColumns()) {
				exceptions.add(getNoSuchEntityException(entity));
			}
		}

		for (String exception : exceptions) {
			String dirName = StringPool.BLANK;

			if (_osgiModule) {
				dirName = "exception/";
			}

			File exceptionFile = new File(
				_serviceOutputPath + "/" + dirName + exception +
					"Exception.java");

			if (!exceptionFile.exists()) {
				Map<String, Object> context = _getContext();

				context.put("exception", exception);

				String content = _processTemplate(_tplException, context);

				if (exception.startsWith("NoSuch")) {
					content = StringUtil.replace(
						content, "PortalException", "NoSuchModelException");
					content = StringUtil.replace(
						content, "kernel.exception.NoSuchModelException",
						"NoSuchModelException");
				}

				content = StringUtil.replace(content, "\r\n", "\n");

				ToolsUtil.writeFileRaw(
					exceptionFile, content, _modifiedFileNames);
			}

			if (exception.startsWith("NoSuch")) {
				String content = _read(exceptionFile);

				if (!content.contains("NoSuchModelException")) {
					content = StringUtil.replace(
						content, "PortalException", "NoSuchModelException");
					content = StringUtil.replace(
						content, "kernel.exception.NoSuchModelException",
						"NoSuchModelException");

					ToolsUtil.writeFileRaw(
						exceptionFile, content, _modifiedFileNames);
				}
			}
		}
	}

	private void _createExportActionableDynamicQuery(Entity entity)
		throws Exception {

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ExportActionableDynamicQuery.java");

		if (_osgiModule) {
			ejbFile.delete();

			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(
			_tplExportActionableDynamicQuery, context);

		// Write file

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createExtendedModel(Entity entity) throws Exception {
		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		List<JavaMethod> methods = ListUtil.fromArray(
			_getMethods(modelImplJavaClass));

		Iterator<JavaMethod> itr = methods.iterator();

		while (itr.hasNext()) {
			JavaMethod method = itr.next();

			String methodName = method.getName();

			if (methodName.equals("getStagedModelType")) {
				itr.remove();
			}
		}

		JavaClass modelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		for (JavaMethod method : _getMethods(modelJavaClass)) {
			methods.remove(method);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods.toArray(new Object[methods.size()]));

		context = _putDeprecatedKeys(context, modelJavaClass);

		// Content

		String content = _processTemplate(_tplExtendedModel, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + ".java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createExtendedModelBaseImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplExtendedModelBaseImpl, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() + "BaseImpl.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createExtendedModelImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplExtendedModelImpl, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		if (modelFile.exists()) {
			content = _read(modelFile);

			content = content.replaceAll(
				"extends\\s+" + entity.getName() +
					"ModelImpl\\s+implements\\s+" + entity.getName(),
				"extends " + entity.getName() + "BaseImpl");

			ToolsUtil.writeFileRaw(modelFile, content, _modifiedFileNames);
		}
		else {
			ToolsUtil.writeFile(
				modelFile, content, _author, _modifiedFileNames);
		}
	}

	private void _createFinder(Entity entity) throws Exception {
		if (!entity.hasFinderClass()) {
			_removeFinder(entity);

			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"FinderImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplFinder, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Finder.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createFinderBaseImpl(Entity entity) throws Exception {
		if (!entity.hasFinderClass() ||
			_packagePath.equals("com.liferay.counter")) {

			_removeFinderBaseImpl(entity);

			return;
		}

		File finderImplFile = new File(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"FinderImpl.java");

		if (finderImplFile.exists()) {
			String content = _read(finderImplFile);

			content = StringUtil.replace(
				content,
				"import com.liferay.portal.service.persistence.impl." +
					"BasePersistenceImpl;\n",
				"");

			content = StringUtil.replace(
				content, "BasePersistenceImpl<" + entity.getName() + ">",
				entity.getName() + "FinderBaseImpl");

			ToolsUtil.writeFileRaw(finderImplFile, content, _modifiedFileNames);
		}

		JavaClass javaClass = _getJavaClass(finderImplFile.getPath());

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplFinderBaseImpl, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"FinderBaseImpl.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createFinderUtil(Entity entity) throws Exception {
		if (!entity.hasFinderClass() || _osgiModule) {
			_removeFinderUtil(entity);

			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"FinderImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplFinderUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"FinderUtil.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createHbm(Entity entity) {
		File ejbFile = new File(
			_outputPath + "/service/persistence/" + entity.getName() +
				"HBM.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createHbmUtil(Entity entity) {
		File ejbFile = new File(
			_outputPath + "/service/persistence/" + entity.getName() +
				"HBMUtil.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createHbmXml() throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);

		// Content

		String content = _processTemplate(_tplHbmXml, context);

		int lastImportStart = content.lastIndexOf("<import class=");
		int lastImportEnd = content.indexOf("/>", lastImportStart) + 3;

		String imports = content.substring(0, lastImportEnd);

		content = content.substring(lastImportEnd + 1);

		File xmlFile = new File(_hbmFileName);

		if (!xmlFile.exists()) {
			String xml =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n" +
				"\n" +
				"<hibernate-mapping default-lazy=\"false\" auto-import=\"false\">\n" +
				"</hibernate-mapping>";

			_write(xmlFile, xml);
		}

		String oldContent = _read(xmlFile);
		String newContent = _fixHbmXml(oldContent);

		int firstImport = newContent.indexOf(
			"<import class=\"" + _packagePath + ".model.");
		int lastImport = newContent.lastIndexOf(
			"<import class=\"" + _packagePath + ".model.");

		if (firstImport == -1) {
			int x = newContent.indexOf("<class");

			if (x != -1) {
				newContent =
					newContent.substring(0, x) + imports +
						newContent.substring(x);
			}
			else {
				content = imports + content;
			}
		}
		else {
			firstImport = newContent.indexOf("<import", firstImport) - 1;
			lastImport = newContent.indexOf("/>", lastImport) + 3;

			newContent =
				newContent.substring(0, firstImport) + imports +
					newContent.substring(lastImport);
		}

		int firstClass = newContent.indexOf(
			"<class name=\"" + _packagePath + ".model.");
		int lastClass = newContent.lastIndexOf(
			"<class name=\"" + _packagePath + ".model.");

		if (firstClass == -1) {
			int x = newContent.indexOf("</hibernate-mapping>");

			if (x != -1) {
				newContent =
					newContent.substring(0, x) + content +
						newContent.substring(x);
			}
		}
		else {
			firstClass = newContent.lastIndexOf("<class", firstClass) - 1;
			lastClass = newContent.indexOf("</class>", lastClass) + 9;

			newContent =
				newContent.substring(0, firstClass) + content +
					newContent.substring(lastClass);
		}

		ToolsUtil.writeFileRaw(
			xmlFile, _formatXml(newContent), _modifiedFileNames);
	}

	private void _createModel(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplModel, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createModelCache(Entity entity) throws Exception {
		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("cacheFields", _getCacheFields(modelImplJavaClass));

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplModelCache, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() +
				"CacheModel.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createModelClp(Entity entity) throws Exception {
		if (Validator.isNull(_pluginName)) {
			return;
		}

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, JavaMethod> methods = new HashMap<>();

		for (JavaMethod method : modelImplJavaClass.getMethods()) {
			methods.put(method.getDeclarationSignature(false), method);
		}

		Type superClass = modelImplJavaClass.getSuperClass();

		String superClassValue = superClass.getValue();

		while (!superClassValue.endsWith("BaseModelImpl")) {
			int pos = superClassValue.lastIndexOf(StringPool.PERIOD);

			if (pos > 0) {
				superClassValue = superClassValue.substring(pos + 1);
			}

			JavaClass javaClass = _getJavaClass(
				_outputPath + "/model/impl/" + superClassValue + ".java");

			for (JavaMethod method : _getMethods(javaClass)) {
				methods.remove(method.getDeclarationSignature(false));
			}

			superClass = javaClass.getSuperClass();
			superClassValue = superClass.getValue();
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods.values());

		// Content

		String content = _processTemplate(_tplModelClp, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Clp.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createModelHintsXml() throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);

		// Content

		String content = _processTemplate(_tplModelHintsXml, context);

		File xmlFile = new File(_modelHintsFileName);

		if (!xmlFile.exists()) {
			String xml =
				"<?xml version=\"1.0\"?>\n" +
				"\n" +
				"<model-hints>\n" +
				"</model-hints>";

			_write(xmlFile, xml);
		}

		String oldContent = _read(xmlFile);
		String newContent = oldContent;

		int firstModel = newContent.indexOf(
			"<model name=\"" + _packagePath + ".model.");
		int lastModel = newContent.lastIndexOf(
			"<model name=\"" + _packagePath + ".model.");

		if (firstModel == -1) {
			int x = newContent.indexOf("</model-hints>");

			newContent =
				newContent.substring(0, x) + content + newContent.substring(x);
		}
		else {
			firstModel = newContent.lastIndexOf("<model", firstModel) - 1;
			lastModel = newContent.indexOf("</model>", lastModel) + 9;

			newContent =
				newContent.substring(0, firstModel) + content +
					newContent.substring(lastModel);
		}

		ToolsUtil.writeFileRaw(
			xmlFile, _formatXml(newContent), _modifiedFileNames);
	}

	private void _createModelImpl(Entity entity) throws Exception {
		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("cacheFields", _getCacheFields(modelImplJavaClass));

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplModelImpl, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() + "ModelImpl.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createModelSoap(Entity entity) throws Exception {
		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Soap.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplModelSoap, context);

		// Write file

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createModelWrapper(Entity entity) throws Exception {
		JavaClass modelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		JavaMethod[] methods = _getMethods(modelJavaClass);

		JavaClass extendedModelBaseImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "BaseImpl.java");

		methods = _mergeMethods(
			methods, _getMethods(extendedModelBaseImplJavaClass), false);

		JavaClass extendedModelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + ".java");

		methods = _mergeMethods(
			methods, _getMethods(extendedModelJavaClass), false);

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);

		context = _putDeprecatedKeys(context, modelJavaClass);

		// Content

		String content = _processTemplate(_tplModelWrapper, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Wrapper.java");

		ToolsUtil.writeFile(modelFile, content, _author, _modifiedFileNames);
	}

	private void _createPersistence(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"PersistenceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplPersistence, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Persistence.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createPersistenceImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("referenceList", _mergeReferenceList(entity));

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		Logger.selectLoggerLibrary(Logger.LIBRARY_NONE);

		String content = _processTemplate(_tplPersistenceImpl, context);

		Logger.selectLoggerLibrary(Logger.LIBRARY_AUTO);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"PersistenceImpl.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);

		ejbFile = new File(
			_outputPath + "/service/persistence/" + entity.getName() +
				"PersistenceImpl.java");

		if (ejbFile.exists()) {
			System.out.println("Relocating " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createPersistenceTest(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		context = _putDeprecatedKeys(context, modelImplJavaClass);

		// Content

		String content = _processTemplate(_tplPersistenceTest, context);

		// Write file

		File ejbFile = new File(
			_testOutputPath + "/service/persistence/test/" + entity.getName() +
				"PersistenceTest.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);

		ejbFile = new File(
			_testOutputPath + "/service/persistence/" + entity.getName() +
				"PersistenceTest.java");

		if (ejbFile.exists()) {
			System.out.println("Relocating " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createPersistenceUtil(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"PersistenceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplPersistenceUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Util.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createPool(Entity entity) {
		File ejbFile = new File(
			_outputPath + "/service/persistence/" + entity.getName() +
				"Pool.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createProps() throws Exception {
		if (Validator.isNull(_pluginName) && !_osgiModule) {
			return;
		}

		// Content

		File propsFile = null;

		if (Validator.isNotNull(_resourcesDirName)) {
			propsFile = new File(_resourcesDirName + "/service.properties");
		}
		else {

			// Backwards compatibility

			propsFile = new File(_implDirName + "/service.properties");
		}

		long buildNumber = 1;
		long buildDate = System.currentTimeMillis();

		if (propsFile.exists()) {
			Properties properties = PropertiesUtil.load(_read(propsFile));

			if (!_buildNumberIncrement) {
				buildDate = GetterUtil.getLong(
					properties.getProperty("build.date"));
				buildNumber = GetterUtil.getLong(
					properties.getProperty("build.number"));
			}
			else {
				buildNumber = GetterUtil.getLong(
					properties.getProperty("build.number")) + 1;
			}
		}

		if (!_buildNumberIncrement && (buildNumber < _buildNumber)) {
			buildNumber = _buildNumber;
			buildDate = System.currentTimeMillis();
		}

		Map<String, Object> context = _getContext();

		context.put("buildNumber", buildNumber);
		context.put("currentTimeMillis", buildDate);

		String content = _processTemplate(_tplProps, context);

		// Write file

		ToolsUtil.writeFileRaw(propsFile, content, _modifiedFileNames);
	}

	private void _createService(Entity entity, int sessionType)
		throws Exception {

		Set<String> imports = new HashSet<>();

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceImpl.java");

		JavaSource javaSource = javaClass.getSource();

		imports.addAll(Arrays.asList(javaSource.getImports()));

		JavaMethod[] methods = _getMethods(javaClass);

		Type superClass = javaClass.getSuperClass();

		String superClassValue = superClass.getValue();

		if (superClassValue.endsWith(
				entity.getName() + _getSessionTypeName(sessionType) +
					"ServiceBaseImpl")) {

			JavaClass parentJavaClass = _getJavaClass(
				_outputPath + "/service/base/" + entity.getName() +
					_getSessionTypeName(sessionType) + "ServiceBaseImpl.java");

			JavaSource parentJavaSource = parentJavaClass.getSource();

			imports.addAll(Arrays.asList(parentJavaSource.getImports()));

			methods = _mergeMethods(
				methods, parentJavaClass.getMethods(), true);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("imports", imports);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplService, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceBaseImpl(Entity entity, int sessionType)
		throws Exception {

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				(sessionType != _SESSION_TYPE_REMOTE ? "Local" : "") +
					"ServiceImpl.java");

		JavaMethod[] methods = _getMethods(javaClass);

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));
		context.put("referenceList", _mergeReferenceList(entity));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceBaseImpl, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceBaseImpl.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceClp(Entity entity, int sessionType)
		throws Exception {

		if (Validator.isNull(_pluginName)) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceClp, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClp.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceClpInvoker(Entity entity, int sessionType)
		throws Exception {

		if (Validator.isNull(_pluginName)) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceImpl.java");

		JavaMethod[] methods = _getMethods(javaClass);

		Type superClass = javaClass.getSuperClass();

		String superClassValue = superClass.getValue();

		if (superClassValue.endsWith(
				entity.getName() + _getSessionTypeName(sessionType) +
					"ServiceBaseImpl")) {

			JavaClass parentJavaClass = _getJavaClass(
				_outputPath + "/service/base/" + entity.getName() +
					_getSessionTypeName(sessionType) + "ServiceBaseImpl.java");

			methods = ArrayUtil.append(parentJavaClass.getMethods(), methods);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceClpInvoker, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClpInvoker.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceClpMessageListener() throws Exception {
		if (Validator.isNull(_pluginName)) {
			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);

		// Content

		String content = _processTemplate(
			_tplServiceClpMessageListener, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/messaging/ClpMessageListener.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceClpSerializer(List<String> exceptions)
		throws Exception {

		if (Validator.isNull(_pluginName)) {
			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);
		context.put("exceptions", exceptions);

		// Content

		String content = _processTemplate(_tplServiceClpSerializer, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/ClpSerializer.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceFactory(Entity entity, int sessionType) {
		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceFactory.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}

		ejbFile = new File(
			_outputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceFactory.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createServiceHttp(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				"ServiceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));
		context.put("hasHttpMethods", _hasHttpMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceHttp, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceHttp.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceImpl(Entity entity, int sessionType)
		throws Exception {

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		// Content

		String content = _processTemplate(_tplServiceImpl, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/impl/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceImpl.java");

		if (!ejbFile.exists()) {
			ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
		}
	}

	private void _createServiceJson(Entity entity) {
		File ejbFile = new File(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceJSON.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createServiceJsonSerializer(Entity entity) {
		File ejbFile = new File(
			_serviceOutputPath + "/service/http/" + entity.getName() +
				"JSONSerializer.java");

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createServicePropsUtil() throws Exception {
		if (!_osgiModule) {
			return;
		}

		File file = new File(
			_implDirName + "/" + StringUtil.replace(_propsUtil, ".", "/") +
				".java");

		Map<String, Object> context = _getContext();

		int index = _propsUtil.lastIndexOf(".");

		context.put(
			"servicePropsUtilClassName", _propsUtil.substring(index + 1));
		context.put(
			"servicePropsUtilPackagePath", _propsUtil.substring(0, index));

		String content = _processTemplate(_tplServicePropsUtil, context);

		ToolsUtil.writeFile(file, content, _modifiedFileNames);
	}

	private void _createServiceSoap(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				"ServiceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceSoap, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceSoap.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceUtil(Entity entity, int sessionType)
		throws Exception {

		JavaClass javaClass = _getJavaClass(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceUtil.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createServiceWrapper(Entity entity, int sessionType)
		throws Exception {

		JavaClass javaClass = _getJavaClass(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		context = _putDeprecatedKeys(context, javaClass);

		// Content

		String content = _processTemplate(_tplServiceWrapper, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceWrapper.java");

		ToolsUtil.writeFile(ejbFile, content, _author, _modifiedFileNames);
	}

	private void _createSpringXml() throws Exception {
		if (_packagePath.equals("com.liferay.counter")) {
			return;
		}

		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);

		// Content

		String content = _processTemplate(_tplSpringXml, context);

		File xmlFile = new File(_springFileName);

		String xml =
			"<?xml version=\"1.0\"?>\n" +
			"\n" +
			"<beans\n" +
			"\tdefault-destroy-method=\"destroy\"\n" +
			"\tdefault-init-method=\"afterPropertiesSet\"\n" +
			_getSpringNamespacesDeclarations() +
			"\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
			"\txsi:schemaLocation=\"" + _getSpringSchemaLocations() + "\">\n" +
			"</beans>";

		if (!xmlFile.exists()) {
			_write(xmlFile, xml);
		}

		String oldContent = _read(xmlFile);

		if (Validator.isNotNull(_pluginName) &&
			oldContent.contains("DOCTYPE beans PUBLIC")) {

			oldContent = xml;
		}

		String newContent = _fixSpringXml(oldContent);

		int x = oldContent.indexOf("<beans");
		int y = oldContent.lastIndexOf("</beans>");

		int firstSession = newContent.indexOf(
			"<bean id=\"" + _packagePath + ".service.", x);

		int lastSession = newContent.lastIndexOf(
			"<bean id=\"" + _packagePath + ".service.", y);

		if ((firstSession == -1) || (firstSession > y)) {
			x = newContent.indexOf("</beans>");

			newContent =
				newContent.substring(0, x) + content + newContent.substring(x);
		}
		else {
			firstSession = newContent.lastIndexOf("<bean", firstSession) - 1;

			int tempLastSession = newContent.indexOf(
				"<bean id=\"", lastSession + 1);

			if (tempLastSession == -1) {
				tempLastSession = newContent.indexOf("</beans>", lastSession);
			}

			lastSession = tempLastSession;

			newContent =
				newContent.substring(0, firstSession) + content +
					newContent.substring(lastSession);
		}

		ToolsUtil.writeFileRaw(
			xmlFile, _formatXml(newContent), _modifiedFileNames);
	}

	private void _createSQLIndexes() throws Exception {
		File sqlDir = new File(_sqlDirName);

		if (!sqlDir.exists()) {
			_mkdir(sqlDir);
		}

		// indexes.sql loading

		File sqlFile = new File(_sqlDirName + "/" + _sqlIndexesFileName);

		if (!sqlFile.exists()) {
			_touch(sqlFile);
		}

		Map<String, List<IndexMetadata>> indexMetadataMap = new TreeMap<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new FileReader(sqlFile))) {

			while (true) {
				String indexSQL = unsyncBufferedReader.readLine();

				if (indexSQL == null) {
					break;
				}

				indexSQL = indexSQL.trim();

				if (Validator.isNull(indexSQL)) {
					continue;
				}

				IndexMetadata indexMetadata =
					IndexMetadataFactoryUtil.createIndexMetadata(indexSQL);

				Entity entity = _getEntityByTableName(
					indexMetadata.getTableName());

				if (entity != null) {
					indexMetadata = new IndexMetadata(
						indexMetadata.getIndexName(),
						indexMetadata.getTableName(), indexMetadata.isUnique(),
						indexMetadata.getColumnNames());
				}

				_addIndexMetadata(
					indexMetadataMap, indexMetadata.getTableName(),
					indexMetadata);
			}
		}

		// indexes.sql appending

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!_isTargetEntity(entity)) {
				continue;
			}

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			List<EntityFinder> finderList = entity.getFinderList();

			for (int j = 0; j < finderList.size(); j++) {
				EntityFinder finder = finderList.get(j);

				if (finder.isDBIndex()) {
					List<String> finderColsNames = new ArrayList<>();

					List<EntityColumn> finderColsList = finder.getColumns();

					for (int k = 0; k < finderColsList.size(); k++) {
						EntityColumn col = finderColsList.get(k);

						finderColsNames.add(col.getDBName());
					}

					if (finderColsNames.isEmpty()) {
						continue;
					}

					IndexMetadata indexMetadata =
						IndexMetadataFactoryUtil.createIndexMetadata(
							finder.isUnique(), entity.getTable(),
							finderColsNames.toArray(
								new String[finderColsNames.size()]));

					_addIndexMetadata(
						indexMetadataMap, indexMetadata.getTableName(),
						indexMetadata);
				}
			}
		}

		for (Map.Entry<String, EntityMapping> entry :
				_entityMappings.entrySet()) {

			EntityMapping entityMapping = entry.getValue();

			_getCreateMappingTableIndex(entityMapping, indexMetadataMap);
		}

		StringBundler sb = new StringBundler();

		for (List<IndexMetadata> indexMetadataList :
				indexMetadataMap.values()) {

			Collections.sort(indexMetadataList);

			for (IndexMetadata indexMetadata : indexMetadataList) {
				sb.append(
					indexMetadata.getCreateSQL(
						_getColumnLengths(indexMetadata)));

				sb.append(StringPool.NEW_LINE);
			}

			sb.append(StringPool.NEW_LINE);
		}

		if (!indexMetadataMap.isEmpty()) {
			sb.setIndex(sb.index() - 2);
		}

		ToolsUtil.writeFileRaw(sqlFile, sb.toString(), _modifiedFileNames);

		// indexes.properties

		File file = new File(_sqlDirName, "indexes.properties");

		file.delete();
	}

	private void _createSQLMappingTables(
			File sqlFile, String newCreateTableString,
			EntityMapping entityMapping, boolean addMissingTables)
		throws IOException {

		if (!sqlFile.exists()) {
			_touch(sqlFile);
		}

		String content = _read(sqlFile);

		int x = content.indexOf(
			_SQL_CREATE_TABLE + entityMapping.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2);

				ToolsUtil.writeFileRaw(sqlFile, content, _modifiedFileNames);
			}
		}
		else if (addMissingTables) {
			try (UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(new UnsyncStringReader(content))) {

				StringBundler sb = new StringBundler();

				String line = null;
				boolean appendNewTable = true;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
						x = _SQL_CREATE_TABLE.length();
						y = line.indexOf(" ", x);

						String tableName = line.substring(x, y);

						if (tableName.compareTo(entityMapping.getTable()) > 0) {
							sb.append(newCreateTableString);
							sb.append("\n\n");

							appendNewTable = false;
						}
					}

					sb.append(line);
					sb.append("\n");
				}

				if (appendNewTable) {
					sb.append("\n");
					sb.append(newCreateTableString);
				}

				ToolsUtil.writeFileRaw(
					sqlFile, sb.toString(), _modifiedFileNames);
			}
		}
	}

	private void _createSQLSequences() throws IOException {
		File sqlDir = new File(_sqlDirName);

		if (!sqlDir.exists()) {
			_mkdir(sqlDir);
		}

		File sqlFile = new File(_sqlDirName + "/" + _sqlSequencesFileName);

		if (!sqlFile.exists()) {
			_touch(sqlFile);
		}

		Set<String> sequenceSQLs = new TreeSet<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new FileReader(sqlFile))) {

			while (true) {
				String sequenceSQL = unsyncBufferedReader.readLine();

				if (sequenceSQL == null) {
					break;
				}

				if (Validator.isNotNull(sequenceSQL)) {
					sequenceSQLs.add(sequenceSQL);
				}
			}
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!_isTargetEntity(entity)) {
				continue;
			}

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			List<EntityColumn> columnList = entity.getColumnList();

			for (int j = 0; j < columnList.size(); j++) {
				EntityColumn column = columnList.get(j);

				if ("sequence".equals(column.getIdType())) {
					StringBundler sb = new StringBundler();

					String sequenceName = column.getIdParam();

					if (sequenceName.length() > 30) {
						sequenceName = sequenceName.substring(0, 30);
					}

					sb.append("create sequence ");
					sb.append(sequenceName);
					sb.append(";");

					String sequenceSQL = sb.toString();

					if (!sequenceSQLs.contains(sequenceSQL)) {
						sequenceSQLs.add(sequenceSQL);
					}
				}
			}
		}

		StringBundler sb = new StringBundler(sequenceSQLs.size() * 2);

		for (String sequenceSQL : sequenceSQLs) {
			sb.append(sequenceSQL);
			sb.append("\n");
		}

		if (!sequenceSQLs.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		ToolsUtil.writeFileRaw(sqlFile, sb.toString(), _modifiedFileNames);
	}

	private void _createSQLTables() throws Exception {
		File sqlDir = new File(_sqlDirName);

		if (!sqlDir.exists()) {
			_mkdir(sqlDir);
		}

		File sqlFile = new File(_sqlDirName + "/" + _sqlFileName);

		if (!sqlFile.exists()) {
			_touch(sqlFile);
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = _ejbList.get(i);

			if (!_isTargetEntity(entity)) {
				continue;
			}

			if (!entity.isDefaultDataSource()) {
				continue;
			}

			String createTableSQL = _getCreateTableSQL(entity);

			if (Validator.isNotNull(createTableSQL)) {
				_createSQLTables(sqlFile, createTableSQL, entity, true);

				Path updateSQLFilePath = _getUpdateSQLFilePath();

				if ((updateSQLFilePath != null) &&
					Files.exists(updateSQLFilePath)) {

					_createSQLTables(
						updateSQLFilePath.toFile(), createTableSQL, entity,
						false);
				}
			}
		}

		for (Map.Entry<String, EntityMapping> entry :
				_entityMappings.entrySet()) {

			EntityMapping entityMapping = entry.getValue();

			String createMappingTableSQL = _getCreateMappingTableSQL(
				entityMapping);

			if (Validator.isNotNull(createMappingTableSQL)) {
				_createSQLMappingTables(
					sqlFile, createMappingTableSQL, entityMapping, true);
			}
		}

		String content = _read(sqlFile);

		ToolsUtil.writeFileRaw(sqlFile, content.trim(), _modifiedFileNames);
	}

	private void _createSQLTables(
			File sqlFile, String newCreateTableString, Entity entity,
			boolean addMissingTables)
		throws IOException {

		if (!sqlFile.exists()) {
			_touch(sqlFile);
		}

		String content = _read(sqlFile);

		int x = content.indexOf(_SQL_CREATE_TABLE + entity.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x, y + 2);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2);

				_write(sqlFile, content);
			}
		}
		else if (addMissingTables) {
			try (UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(new UnsyncStringReader(content))) {

				StringBundler sb = new StringBundler();

				String line = null;
				boolean appendNewTable = true;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
						x = _SQL_CREATE_TABLE.length();
						y = line.indexOf(" ", x);

						String tableName = line.substring(x, y);

						if (tableName.compareTo(entity.getTable()) > 0) {
							sb.append(newCreateTableString);
							sb.append("\n\n");

							appendNewTable = false;
						}
					}

					sb.append(line);
					sb.append("\n");
				}

				if (appendNewTable) {
					sb.append("\n");
					sb.append(newCreateTableString);
				}

				ToolsUtil.writeFileRaw(
					sqlFile, sb.toString(), _modifiedFileNames);
			}
		}
	}

	private void _deleteFile(String fileName) {
		File file = new File(fileName);

		file.delete();
	}

	private void _deleteOrmXml() throws Exception {
		if (Validator.isNull(_pluginName)) {
			return;
		}

		_deleteFile("docroot/WEB-INF/src/META-INF/portlet-orm.xml");
	}

	private void _deleteSpringLegacyXml() throws Exception {
		if (Validator.isNull(_pluginName)) {
			return;
		}

		_deleteFile("docroot/WEB-INF/src/META-INF/base-spring.xml");
		_deleteFile("docroot/WEB-INF/src/META-INF/cluster-spring.xml");
		_deleteFile("docroot/WEB-INF/src/META-INF/data-source-spring.xml");
		_deleteFile(
			"docroot/WEB-INF/src/META-INF/dynamic-data-source-spring.xml");
		_deleteFile("docroot/WEB-INF/src/META-INF/hibernate-spring.xml");
		_deleteFile("docroot/WEB-INF/src/META-INF/infrastructure-spring.xml");
		_deleteFile("docroot/WEB-INF/src/META-INF/misc-spring.xml");
	}

	private String _fixHbmXml(String content) throws IOException {
		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.startsWith("\t<class name=\"")) {
					line = StringUtil.replace(
						line,
						new String[] {
							".service.persistence.", "HBM\" table=\""
						},
						new String[] {".model.", "\" table=\""});

					if (!line.contains(".model.impl.") &&
						!line.contains("BlobModel")) {

						line = StringUtil.replace(
							line, new String[] {".model.", "\" table=\""},
							new String[] {".model.impl.", "Impl\" table=\""});
					}
				}

				sb.append(line);
				sb.append('\n');
			}

			return sb.toString().trim();
		}
	}

	private String _fixSpringXml(String content) {
		return StringUtil.replace(content, ".service.spring.", ".service.");
	}

	private String _formatComment(
		String comment, DocletTag[] tags, String indentation) {

		StringBundler sb = new StringBundler();

		if (Validator.isNull(comment) && (tags.length <= 0)) {
			return sb.toString();
		}

		sb.append(indentation);
		sb.append("/**\n");

		if (Validator.isNotNull(comment)) {
			comment = comment.replaceAll("(?m)^", indentation + " * ");

			sb.append(comment);
			sb.append("\n");

			if (tags.length > 0) {
				sb.append(indentation);
				sb.append(" *\n");
			}
		}

		for (DocletTag tag : tags) {
			String tagValue = tag.getValue();

			sb.append(indentation);
			sb.append(" * @");
			sb.append(tag.getName());
			sb.append(" ");

			if (_currentTplName.equals(_tplServiceSoap)) {
				if (tagValue.startsWith(PortalException.class.getName())) {
					tagValue = tagValue.replaceFirst(
						PortalException.class.getName(), "RemoteException");
				}
				else if (tagValue.startsWith(
							PrincipalException.class.getName())) {

					tagValue = tagValue.replaceFirst(
						PrincipalException.class.getName(), "RemoteException");
				}
			}

			sb.append(tagValue);

			sb.append("\n");
		}

		sb.append(indentation);
		sb.append(" */\n");

		return sb.toString();
	}

	private String _formatXml(String xml)
		throws DocumentException, IOException {

		String doctype = null;

		int x = xml.indexOf("<!DOCTYPE");

		if (x != -1) {
			int y = xml.indexOf(">", x) + 1;

			doctype = xml.substring(x, y);

			xml = xml.substring(0, x) + "\n" + xml.substring(y);
		}

		xml = StringUtil.replace(xml, '\r', "");
		xml = Dom4jUtil.toString(xml);
		xml = StringUtil.replace(xml, "\"/>", "\" />");

		if (Validator.isNotNull(doctype)) {
			x = xml.indexOf("?>") + 2;

			xml = xml.substring(0, x) + "\n" + doctype + xml.substring(x);
		}

		return xml;
	}

	private JavaField[] _getCacheFields(JavaClass javaClass) {
		if (javaClass == null) {
			return new JavaField[0];
		}

		List<JavaField> javaFields = new ArrayList<>();

		for (JavaField javaField : javaClass.getFields()) {
			Annotation[] annotations = javaField.getAnnotations();

			for (Annotation annotation : annotations) {
				Type type = annotation.getType();

				String className = type.getFullyQualifiedName();

				if (className.equals(CacheField.class.getName())) {
					javaFields.add(javaField);

					break;
				}
			}
		}

		return javaFields.toArray(new JavaField[javaFields.size()]);
	}

	private int[] _getColumnLengths(IndexMetadata indexMetadata) {
		Entity entity = _getEntityByTableName(indexMetadata.getTableName());

		if (entity == null) {
			return null;
		}

		String[] columnNames = indexMetadata.getColumnNames();

		int[] columnLengths = new int[columnNames.length];

		for (int i = 0; i < columnNames.length; i++) {
			EntityColumn entityColumn = _getEntityColumnByColumnDBName(
				entity, columnNames[i]);

			String colType = entityColumn.getType();

			if (colType.equals("String")) {
				columnLengths[i] = getMaxLength(
					_packagePath + ".model." + entity.getName(),
					entityColumn.getName());
			}
		}

		return columnLengths;
	}

	private Map<String, Object> _getContext() throws TemplateModelException {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

		TemplateHashModel staticModels = wrapper.getStaticModels();

		Map<String, Object> context = new HashMap<>();

		context.put("apiDir", _apiDirName);
		context.put("arrayUtil", ArrayUtil_IW.getInstance());
		context.put("author", _author);
		context.put("beanLocatorUtil", _beanLocatorUtil);
		context.put("beanLocatorUtilShortName", _beanLocatorUtilShortName);
		context.put("hbmFileName", _hbmFileName);
		context.put("implDir", _implDirName);
		context.put("modelHintsFileName", _modelHintsFileName);
		context.put("modelHintsUtil", ModelHintsUtil.getModelHints());
		context.put("osgiModule", _osgiModule);
		context.put("outputPath", _outputPath);
		context.put("packagePath", _packagePath);
		context.put("pluginName", _pluginName);
		context.put("portletName", _portletName);
		context.put("portletPackageName", _portletPackageName);
		context.put("portletShortName", _portletShortName);
		context.put("propsUtil", _propsUtil);
		context.put("serviceBuilder", this);
		context.put("serviceOutputPath", _serviceOutputPath);
		context.put("springFileName", _springFileName);
		context.put("sqlDir", _sqlDirName);
		context.put("sqlFileName", _sqlFileName);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("system", staticModels.get("java.lang.System"));
		context.put("tempMap", wrapper.wrap(new HashMap<String, Object>()));
		context.put(
			"textFormatter", staticModels.get(TextFormatter.class.getName()));
		context.put("validator", Validator_IW.getInstance());

		return context;
	}

	private void _getCreateMappingTableIndex(
			EntityMapping entityMapping,
			Map<String, List<IndexMetadata>> indexMetadataMap)
		throws Exception {

		Entity[] entities = new Entity[3];

		for (int i = 0; i < entities.length; i++) {
			entities[i] = getEntity(entityMapping.getEntity(i));

			if (entities[i] == null) {
				return;
			}
		}

		String tableName = entityMapping.getTable();

		for (Entity entity : entities) {
			List<EntityColumn> pkList = entity.getPKList();

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn col = pkList.get(j);

				String colDBName = col.getDBName();

				IndexMetadata indexMetadata =
					IndexMetadataFactoryUtil.createIndexMetadata(
						false, tableName, colDBName);

				_addIndexMetadata(indexMetadataMap, tableName, indexMetadata);
			}
		}
	}

	private String _getCreateMappingTableSQL(EntityMapping entityMapping)
		throws Exception {

		Entity[] entities = new Entity[3];

		for (int i = 0; i < entities.length; i++) {
			entities[i] = getEntity(entityMapping.getEntity(i));

			if (entities[i] == null) {
				return null;
			}
		}

		Arrays.sort(
			entities,
			new Comparator<Entity>() {

				@Override
				public int compare(Entity entity1, Entity entity2) {
					String name1 = entity1.getName();
					String name2 = entity2.getName();

					if (Validator.equals(
							entity1.getPackagePath(), "com.liferay.portal") &&
						name1.equals("Company")) {

						return -1;
					}

					if (Validator.equals(
							entity2.getPackagePath(), "com.liferay.portal") &&
						name2.equals("Company")) {

						return 1;
					}

					return name1.compareTo(name2);
				}

			});

		StringBundler sb = new StringBundler();

		sb.append(_SQL_CREATE_TABLE);
		sb.append(entityMapping.getTable());
		sb.append(" (\n");

		for (Entity entity : entities) {
			List<EntityColumn> pkList = entity.getPKList();

			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = pkList.get(i);

				String colName = col.getName();
				String colType = col.getType();

				sb.append("\t");
				sb.append(col.getDBName());
				sb.append(" ");

				if (StringUtil.equalsIgnoreCase(colType, "boolean")) {
					sb.append("BOOLEAN");
				}
				else if (StringUtil.equalsIgnoreCase(colType, "double") ||
						 StringUtil.equalsIgnoreCase(colType, "float")) {

					sb.append("DOUBLE");
				}
				else if (colType.equals("int") || colType.equals("Integer") ||
						 StringUtil.equalsIgnoreCase(colType, "short")) {

					sb.append("INTEGER");
				}
				else if (StringUtil.equalsIgnoreCase(colType, "long")) {
					sb.append("LONG");
				}
				else if (colType.equals("Map")) {
					sb.append("TEXT");
				}
				else if (colType.equals("String")) {
					int maxLength = getMaxLength(
						_packagePath + ".model." + entity.getName(), colName);

					if (col.isLocalized()) {
						maxLength = 4000;
					}

					if (maxLength < 4000) {
						sb.append("VARCHAR(");
						sb.append(maxLength);
						sb.append(")");
					}
					else if (maxLength == 4000) {
						sb.append("STRING");
					}
					else if (maxLength > 4000) {
						sb.append("TEXT");
					}
				}
				else if (colType.equals("Date")) {
					sb.append("DATE");
				}
				else {
					sb.append("invalid");
				}

				if (col.isPrimary()) {
					sb.append(" not null");
				}
				else if (colType.equals("Date") || colType.equals("Map") ||
						 colType.equals("String")) {

					sb.append(" null");
				}

				sb.append(",\n");
			}
		}

		sb.append("\tprimary key (");

		for (int i = 0; i < entities.length; i++) {
			Entity entity = entities[i];

			List<EntityColumn> pkList = entity.getPKList();

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn col = pkList.get(j);

				String colDBName = col.getDBName();

				if ((i != 0) || (j != 0)) {
					sb.append(", ");
				}

				sb.append(colDBName);
			}
		}

		sb.append(")\n");
		sb.append(");");

		return sb.toString();
	}

	private String _getCreateTableSQL(Entity entity) {
		List<EntityColumn> pkList = entity.getPKList();
		List<EntityColumn> regularColList = entity.getRegularColList();

		if (regularColList.isEmpty()) {
			return null;
		}

		StringBundler sb = new StringBundler();

		sb.append(_SQL_CREATE_TABLE);
		sb.append(entity.getTable());
		sb.append(" (\n");

		for (int i = 0; i < regularColList.size(); i++) {
			EntityColumn col = regularColList.get(i);

			String colName = col.getName();
			String colType = col.getType();
			String colIdType = col.getIdType();

			sb.append("\t");
			sb.append(col.getDBName());
			sb.append(" ");

			if (StringUtil.equalsIgnoreCase(colType, "boolean")) {
				sb.append("BOOLEAN");
			}
			else if (StringUtil.equalsIgnoreCase(colType, "double") ||
					 StringUtil.equalsIgnoreCase(colType, "float")) {

				sb.append("DOUBLE");
			}
			else if (colType.equals("int") || colType.equals("Integer") ||
					 StringUtil.equalsIgnoreCase(colType, "short")) {

				sb.append("INTEGER");
			}
			else if (StringUtil.equalsIgnoreCase(colType, "long")) {
				sb.append("LONG");
			}
			else if (colType.equals("Blob")) {
				sb.append("BLOB");
			}
			else if (colType.equals("Date")) {
				sb.append("DATE");
			}
			else if (colType.equals("Map")) {
				sb.append("TEXT");
			}
			else if (colType.equals("String")) {
				int maxLength = getMaxLength(
					_packagePath + ".model." + entity.getName(), colName);

				if (col.isLocalized() && (maxLength < 4000)) {
					maxLength = 4000;
				}

				if (maxLength < 4000) {
					sb.append("VARCHAR(");
					sb.append(maxLength);
					sb.append(")");
				}
				else if (maxLength == 4000) {
					sb.append("STRING");
				}
				else if (maxLength > 4000) {
					sb.append("TEXT");
				}
			}
			else {
				sb.append("invalid");
			}

			if (col.isPrimary()) {
				sb.append(" not null");

				if (!entity.hasCompoundPK()) {
					sb.append(" primary key");
				}
			}
			else if (colType.equals("Date") || colType.equals("Map") ||
					 colType.equals("String")) {

				sb.append(" null");
			}

			if (Validator.isNotNull(colIdType) &&
				colIdType.equals("identity")) {

				sb.append(" IDENTITY");
			}

			if (colName.equals("mvccVersion")) {
				sb.append(" default 0");
			}

			if (((i + 1) != regularColList.size()) || entity.hasCompoundPK()) {
				sb.append(",");
			}

			sb.append("\n");
		}

		if (entity.hasCompoundPK()) {
			sb.append("\tprimary key (");

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn pk = pkList.get(j);

				sb.append(pk.getDBName());

				if ((j + 1) != pkList.size()) {
					sb.append(", ");
				}
			}

			sb.append(")\n");
		}

		sb.append(");");

		return sb.toString();
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private Entity _getEntityByTableName(String tableName) {
		for (Entity entity : _ejbList) {
			if (tableName.equals(entity.getTable())) {
				return entity;
			}
		}

		return null;
	}

	private EntityColumn _getEntityColumnByColumnDBName(
		Entity entity, String columnDBName) {

		for (EntityColumn entityColumn : entity.getFinderColumnsList()) {
			if (columnDBName.equals(entityColumn.getDBName())) {
				return entityColumn;
			}
		}

		throw new IllegalArgumentException(
			"No entity column exist with column database name " + columnDBName +
				" for entity " + entity.getName());
	}

	private JavaClass _getJavaClass(String fileName) throws IOException {
		int pos = 0;

		if (fileName.startsWith(_implDirName)) {
			pos = _implDirName.length() + 1;
		}
		else if (fileName.startsWith(_apiDirName)) {
			pos = _apiDirName.length() + 1;
		}
		else {
			return null;
		}

		String fullyQualifiedClassName = StringUtil.replace(
			fileName.substring(pos, fileName.length() - 5),
			new String[] {StringPool.BACK_SLASH, StringPool.SLASH},
			new String[] {StringPool.PERIOD, StringPool.PERIOD});

		JavaClass javaClass = _javaClasses.get(fullyQualifiedClassName);

		if (javaClass == null) {
			ClassLibrary classLibrary = new ClassLibrary();

			classLibrary.addClassLoader(getClass().getClassLoader());

			JavaDocBuilder builder = new JavaDocBuilder(classLibrary);

			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}

			builder.addSource(file);

			javaClass = builder.getClassByName(fullyQualifiedClassName);

			_javaClasses.put(fullyQualifiedClassName, javaClass);
		}

		return javaClass;
	}

	private String _getMethodKey(JavaMethod javaMethod) {
		StringBundler sb = new StringBundler();

		if (!javaMethod.isConstructor()) {
			sb.append(getTypeGenericsName(javaMethod.getReturnType()));
			sb.append(StringPool.SPACE);
		}

		sb.append(javaMethod.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);

		JavaParameter[] javaParameters = javaMethod.getParameters();

		for (JavaParameter javaParameter : javaParameters) {
			sb.append(getTypeGenericsName(javaParameter.getType()));

			sb.append(StringPool.COMMA);
		}

		if (javaParameters.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private JavaMethod[] _getMethods(JavaClass javaClass) {
		return _getMethods(javaClass, false);
	}

	private JavaMethod[] _getMethods(
		JavaClass javaClass, boolean superclasses) {

		JavaMethod[] methods = javaClass.getMethods(superclasses);

		for (JavaMethod method : methods) {
			Arrays.sort(method.getExceptions());
		}

		return methods;
	}

	private String _getSessionTypeName(int sessionType) {
		if (sessionType == _SESSION_TYPE_LOCAL) {
			return "Local";
		}
		else {
			return "";
		}
	}

	private String _getSpringNamespacesDeclarations() {
		StringBundler sb = new StringBundler(_springNamespaces.length * 4);

		for (String namespace : _springNamespaces) {
			sb.append("\txmlns");

			if (!_SPRING_NAMESPACE_BEANS.equals(namespace)) {
				sb.append(":");
				sb.append(namespace);
			}

			sb.append("=\"http://www.springframework.org/schema/");
			sb.append(namespace);
			sb.append("\"\n");
		}

		return sb.toString();
	}

	private String _getSpringSchemaLocations() {
		StringBundler sb = new StringBundler(_springNamespaces.length * 6);

		for (String namespace : _springNamespaces) {
			sb.append("\thttp://www.springframework.org/schema/");
			sb.append(namespace);
			sb.append(" http://www.springframework.org/schema/");
			sb.append(namespace);
			sb.append("/spring-");
			sb.append(namespace);
			sb.append(".xsd");
		}

		return sb.toString();
	}

	private String _getTplProperty(String key, String defaultValue) {
		return System.getProperty("service.tpl." + key, defaultValue);
	}

	private List<String> _getTransients(Entity entity, boolean parent)
		throws Exception {

		File modelFile = null;

		if (parent) {
			modelFile = new File(
				_outputPath + "/model/impl/" + entity.getName() +
					"ModelImpl.java");
		}
		else {
			modelFile = new File(
				_outputPath + "/model/impl/" + entity.getName() + "Impl.java");
		}

		String content = _read(modelFile);

		Matcher matcher = _getterPattern.matcher(content);

		Set<String> getters = new HashSet<>();

		while (!matcher.hitEnd()) {
			boolean found = matcher.find();

			if (found) {
				String property = matcher.group();

				if (property.contains("get")) {
					property = property.substring(
						property.indexOf("get") + 3, property.length() - 1);
				}
				else {
					property = property.substring(
						property.indexOf("is") + 2, property.length() - 1);
				}

				if (!entity.hasColumn(property) &&
					!entity.hasColumn(Introspector.decapitalize(property))) {

					property = Introspector.decapitalize(property);

					getters.add(property);
				}
			}
		}

		matcher = _setterPattern.matcher(content);

		Set<String> setters = new HashSet<>();

		while (!matcher.hitEnd()) {
			boolean found = matcher.find();

			if (found) {
				String property = matcher.group();

				property = property.substring(
					property.indexOf("set") + 3, property.length() - 1);

				if (!entity.hasColumn(property) &&
					!entity.hasColumn(Introspector.decapitalize(property))) {

					property = Introspector.decapitalize(property);

					setters.add(property);
				}
			}
		}

		getters.retainAll(setters);

		List<String> transients = new ArrayList<>(getters);

		Collections.sort(transients);

		return transients;
	}

	private Path _getUpdateSQLFilePath() throws IOException {
		if (!_osgiModule) {
			return Paths.get(_sqlDirName, "update-6.2.0-7.0.0.sql");
		}

		final AtomicReference<Path> atomicReference = new AtomicReference<>();

		FileSystem fileSystem = FileSystems.getDefault();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:**/dependencies/update.sql");

		Files.walkFileTree(
			Paths.get(_resourcesDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (!pathMatcher.matches(path)) {
						return FileVisitResult.CONTINUE;
					}

					Path updateSQLFilePath = atomicReference.get();

					if (updateSQLFilePath == null) {
						atomicReference.set(path);
					}
					else {
						Version updateSQLFileVersion = _getUpdateSQLFileVersion(
							updateSQLFilePath);
						Version version = _getUpdateSQLFileVersion(path);

						if (updateSQLFileVersion.compareTo(version) < 0) {
							atomicReference.set(path);
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return atomicReference.get();
	}

	private Version _getUpdateSQLFileVersion(Path path) {
		path = path.getName(path.getNameCount() - 3);

		String version = path.toString();

		version = version.replace('_', '.');
		version = version.substring(1);

		return Version.getInstance(version);
	}

	private boolean _hasHttpMethods(JavaClass javaClass) {
		JavaMethod[] methods = _getMethods(javaClass);

		for (JavaMethod javaMethod : methods) {
			if (!javaMethod.isConstructor() && javaMethod.isPublic() &&
				isCustomMethod(javaMethod)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isStringLocaleMap(JavaParameter javaParameter) {
		Type type = javaParameter.getType();

		Type[] actualArgumentTypes = type.getActualTypeArguments();

		if (actualArgumentTypes.length != 2) {
			return false;
		}

		if (!_isTypeValue(actualArgumentTypes[0], Locale.class.getName()) ||
			!_isTypeValue(actualArgumentTypes[1], String.class.getName())) {

			return false;
		}

		return true;
	}

	private boolean _isTargetEntity(Entity entity) {
		if ((_targetEntityName == null) || _targetEntityName.startsWith("$")) {
			return true;
		}

		return _targetEntityName.equals(entity.getName());
	}

	private boolean _isTypeValue(Type type, String value) {
		return value.equals(type.getValue());
	}

	private Annotation[] _mergeAnnotations(
		Annotation[] annotations1, Annotation[] annotations2) {

		Map<Type, Annotation> annotationsMap = new HashMap<>();

		for (Annotation annotation : annotations2) {
			annotationsMap.put(annotation.getType(), annotation);
		}

		for (Annotation annotation : annotations1) {
			annotationsMap.put(annotation.getType(), annotation);
		}

		List<Annotation> annotations = new ArrayList<>(annotationsMap.values());

		Comparator<Annotation> comparator = new Comparator<Annotation>() {

			@Override
			public int compare(Annotation annotation1, Annotation annotation2) {
				String annotationString1 = annotation1.toString();
				String annotationString2 = annotation2.toString();

				return annotationString1.compareTo(annotationString2);
			}

		};

		Collections.sort(annotations, comparator);

		return annotations.toArray(new Annotation[annotations.size()]);
	}

	private JavaMethod[] _mergeMethods(
		JavaMethod[] javaMethods1, JavaMethod[] javaMethods2,
		boolean mergeAnnotations) {

		Map<String, JavaMethod> javaMethodMap = new HashMap<>();

		for (JavaMethod javaMethod : javaMethods2) {
			javaMethodMap.put(_getMethodKey(javaMethod), javaMethod);
		}

		for (JavaMethod javaMethod : javaMethods1) {
			String javaMethodKey = _getMethodKey(javaMethod);

			JavaMethod existingJavaMethod = javaMethodMap.get(javaMethodKey);

			if (existingJavaMethod == null) {
				javaMethodMap.put(javaMethodKey, javaMethod);
			}
			else if (mergeAnnotations) {
				Annotation[] annotations = _mergeAnnotations(
					javaMethod.getAnnotations(),
					existingJavaMethod.getAnnotations());

				existingJavaMethod.setAnnotations(annotations);
			}
		}

		List<JavaMethod> javaMethods = new ArrayList<>(javaMethodMap.values());

		Comparator<JavaMethod> comparator = new Comparator<JavaMethod>() {

			@Override
			public int compare(JavaMethod javaMethod1, JavaMethod javaMethod2) {
				String callSignature1 = javaMethod1.getCallSignature();
				String callSignature2 = javaMethod2.getCallSignature();

				return callSignature1.compareTo(callSignature2);
			}

		};

		Collections.sort(javaMethods, comparator);

		return javaMethods.toArray(new JavaMethod[javaMethods.size()]);
	}

	private List<Entity> _mergeReferenceList(Entity entity) {
		List<Entity> referenceList = entity.getReferenceList();

		Set<Entity> set = new LinkedHashSet<>();

		if (_autoImportDefaultReferences) {
			set.addAll(_ejbList);
		}
		else {
			set.add(entity);
		}

		set.addAll(referenceList);

		return new ArrayList<>(set);
	}

	private void _parseEntity(Element entityElement) throws Exception {
		String ejbName = entityElement.attributeValue("name");
		String humanName = entityElement.attributeValue("human-name");

		String table = entityElement.attributeValue("table");

		if (Validator.isNull(table)) {
			table = ejbName;

			if (_badTableNames.contains(ejbName)) {
				table += StringPool.UNDERLINE;
			}

			if (_autoNamespaceTables) {
				table = _portletShortName + StringPool.UNDERLINE + ejbName;
			}
		}

		boolean uuid = GetterUtil.getBoolean(
			entityElement.attributeValue("uuid"));
		boolean uuidAccessor = GetterUtil.getBoolean(
			entityElement.attributeValue("uuid-accessor"));
		boolean localService = GetterUtil.getBoolean(
			entityElement.attributeValue("local-service"));
		boolean remoteService = GetterUtil.getBoolean(
			entityElement.attributeValue("remote-service"), true);
		String persistenceClass = GetterUtil.getString(
			entityElement.attributeValue("persistence-class"),
			_packagePath + ".service.persistence.impl." + ejbName +
				"PersistenceImpl");

		String finderClass = "";

		File originalFinderImplFile = new File(
			_outputPath + "/service/persistence/" + ejbName +
				"FinderImpl.java");
		File newFinderImplFile = new File(
			_outputPath + "/service/persistence/impl/" + ejbName +
				"FinderImpl.java");

		if (originalFinderImplFile.exists()) {
			_move(originalFinderImplFile, newFinderImplFile);

			String content = _read(newFinderImplFile);

			StringBundler sb = new StringBundler();

			sb.append(
				"package " + _packagePath + ".service.persistence.impl;\n\n");
			sb.append(
				"import " + _packagePath + ".service.persistence." + ejbName +
					"Finder;\n");
			sb.append(
				"import " + _packagePath + ".service.persistence." + ejbName +
					"Util;");

			content = StringUtil.replace(
				content, "package " + _packagePath + ".service.persistence;",
				sb.toString());

			ToolsUtil.writeFileRaw(
				newFinderImplFile, content, _modifiedFileNames);
		}

		if (newFinderImplFile.exists()) {
			finderClass =
				_packagePath +
					".service.persistence.impl." + ejbName + "FinderImpl";
		}

		String dataSource = entityElement.attributeValue("data-source");
		String sessionFactory = entityElement.attributeValue("session-factory");
		String txManager = entityElement.attributeValue("tx-manager");
		boolean cacheEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("cache-enabled"), true);
		boolean jsonEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("json-enabled"), remoteService);
		boolean mvccEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("mvcc-enabled"), _mvccEnabled);
		boolean trashEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("trash-enabled"));
		boolean deprecated = GetterUtil.getBoolean(
			entityElement.attributeValue("deprecated"));

		boolean dynamicUpdateEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("dynamic-update-enabled"),
			mvccEnabled);

		List<EntityColumn> pkList = new ArrayList<>();
		List<EntityColumn> regularColList = new ArrayList<>();
		List<EntityColumn> blobList = new ArrayList<>();
		List<EntityColumn> collectionList = new ArrayList<>();
		List<EntityColumn> columnList = new ArrayList<>();

		boolean permissionedModel = false;

		List<Element> columnElements = entityElement.elements("column");

		if (uuid) {
			Element columnElement = DocumentHelper.createElement("column");

			columnElement.addAttribute("name", "uuid");
			columnElement.addAttribute("type", "String");

			columnElements.add(0, columnElement);
		}

		if (mvccEnabled && !columnElements.isEmpty()) {
			Element columnElement = DocumentHelper.createElement("column");

			columnElement.addAttribute("name", "mvccVersion");
			columnElement.addAttribute("type", "long");

			columnElements.add(0, columnElement);
		}

		for (Element columnElement : columnElements) {
			String columnName = columnElement.attributeValue("name");

			if (columnName.equals("resourceBlockId") &&
				!ejbName.equals("ResourceBlock")) {

				permissionedModel = true;
			}

			String columnDBName = columnElement.attributeValue("db-name");

			if (Validator.isNull(columnDBName)) {
				columnDBName = columnName;

				if (_badColumnNames.contains(columnName)) {
					columnDBName += StringPool.UNDERLINE;
				}
			}

			String columnType = columnElement.attributeValue("type");
			boolean primary = GetterUtil.getBoolean(
				columnElement.attributeValue("primary"));
			boolean accessor = GetterUtil.getBoolean(
				columnElement.attributeValue("accessor"));
			boolean filterPrimary = GetterUtil.getBoolean(
				columnElement.attributeValue("filter-primary"));
			String collectionEntity = columnElement.attributeValue("entity");

			String mappingTable = columnElement.attributeValue("mapping-table");

			if (Validator.isNotNull(mappingTable)) {
				if (_badTableNames.contains(mappingTable)) {
					mappingTable += StringPool.UNDERLINE;
				}

				if (_autoNamespaceTables) {
					mappingTable =
						_portletShortName + StringPool.UNDERLINE + mappingTable;
				}
			}

			String idType = columnElement.attributeValue("id-type");
			String idParam = columnElement.attributeValue("id-param");
			boolean convertNull = GetterUtil.getBoolean(
				columnElement.attributeValue("convert-null"), true);
			boolean lazy = GetterUtil.getBoolean(
				columnElement.attributeValue("lazy"), true);
			boolean localized = GetterUtil.getBoolean(
				columnElement.attributeValue("localized"));
			boolean colJsonEnabled = GetterUtil.getBoolean(
				columnElement.attributeValue("json-enabled"), jsonEnabled);
			boolean containerModel = GetterUtil.getBoolean(
				columnElement.attributeValue("container-model"));
			boolean parentContainerModel = GetterUtil.getBoolean(
				columnElement.attributeValue("parent-container-model"));

			EntityColumn col = new EntityColumn(
				columnName, columnDBName, columnType, primary, accessor,
				filterPrimary, collectionEntity, mappingTable, idType, idParam,
				convertNull, lazy, localized, colJsonEnabled, containerModel,
				parentContainerModel);

			if (primary) {
				pkList.add(col);
			}

			if (columnType.equals("Collection")) {
				collectionList.add(col);
			}
			else {
				regularColList.add(col);

				if (columnType.equals("Blob")) {
					blobList.add(col);
				}
			}

			columnList.add(col);

			if (Validator.isNotNull(collectionEntity) &&
				Validator.isNotNull(mappingTable)) {

				EntityMapping entityMapping = new EntityMapping(
					mappingTable, ejbName, collectionEntity);

				if (!_entityMappings.containsKey(mappingTable)) {
					_entityMappings.put(mappingTable, entityMapping);
				}
			}
		}

		EntityOrder order = null;

		Element orderElement = entityElement.element("order");

		if (orderElement != null) {
			boolean asc = true;

			if ((orderElement.attribute("by") != null) &&
				orderElement.attributeValue("by").equals("desc")) {

				asc = false;
			}

			List<EntityColumn> orderColsList = new ArrayList<>();

			order = new EntityOrder(asc, orderColsList);

			List<Element> orderColumnElements = orderElement.elements(
				"order-column");

			for (Element orderColElement : orderColumnElements) {
				String orderColName = orderColElement.attributeValue("name");
				boolean orderColCaseSensitive = GetterUtil.getBoolean(
					orderColElement.attributeValue("case-sensitive"), true);

				boolean orderColByAscending = asc;

				String orderColBy = GetterUtil.getString(
					orderColElement.attributeValue("order-by"));

				if (orderColBy.equals("asc")) {
					orderColByAscending = true;
				}
				else if (orderColBy.equals("desc")) {
					orderColByAscending = false;
				}

				EntityColumn col = Entity.getColumn(orderColName, columnList);

				col.setOrderColumn(true);

				col = (EntityColumn)col.clone();

				col.setCaseSensitive(orderColCaseSensitive);
				col.setOrderByAscending(orderColByAscending);

				orderColsList.add(col);
			}
		}

		List<EntityFinder> finderList = new ArrayList<>();

		List<Element> finderElements = entityElement.elements("finder");

		if (uuid) {
			if (columnList.contains(new EntityColumn("companyId"))) {
				Element finderElement = DocumentHelper.createElement("finder");

				finderElement.addAttribute("name", "Uuid_C");
				finderElement.addAttribute("return-type", "Collection");

				Element finderColumnElement = finderElement.addElement(
					"finder-column");

				finderColumnElement.addAttribute("name", "uuid");

				finderColumnElement = finderElement.addElement("finder-column");

				finderColumnElement.addAttribute("name", "companyId");

				finderElements.add(0, finderElement);
			}

			if (columnList.contains(new EntityColumn("groupId"))) {
				Element finderElement = DocumentHelper.createElement("finder");

				if (ejbName.equals("Layout")) {
					finderElement.addAttribute("name", "UUID_G_P");
				}
				else {
					finderElement.addAttribute("name", "UUID_G");
				}

				finderElement.addAttribute("return-type", ejbName);
				finderElement.addAttribute("unique", "true");

				Element finderColumnElement = finderElement.addElement(
					"finder-column");

				finderColumnElement.addAttribute("name", "uuid");

				finderColumnElement = finderElement.addElement("finder-column");

				finderColumnElement.addAttribute("name", "groupId");

				if (ejbName.equals("Layout")) {
					finderColumnElement = finderElement.addElement(
						"finder-column");

					finderColumnElement.addAttribute("name", "privateLayout");
				}

				finderElements.add(0, finderElement);
			}

			Element finderElement = DocumentHelper.createElement("finder");

			finderElement.addAttribute("name", "Uuid");
			finderElement.addAttribute("return-type", "Collection");

			Element finderColumnElement = finderElement.addElement(
				"finder-column");

			finderColumnElement.addAttribute("name", "uuid");

			finderElements.add(0, finderElement);
		}

		if (permissionedModel) {
			Element finderElement = DocumentHelper.createElement("finder");

			finderElement.addAttribute("name", "ResourceBlockId");
			finderElement.addAttribute("return-type", "Collection");

			Element finderColumnElement = finderElement.addElement(
				"finder-column");

			finderColumnElement.addAttribute("name", "resourceBlockId");

			finderElements.add(0, finderElement);
		}

		String alias = TextFormatter.format(ejbName, TextFormatter.I);

		if (_badAliasNames.contains(StringUtil.toLowerCase(alias))) {
			alias += StringPool.UNDERLINE;
		}

		for (Element finderElement : finderElements) {
			String finderName = finderElement.attributeValue("name");
			String finderReturn = finderElement.attributeValue("return-type");
			boolean finderUnique = GetterUtil.getBoolean(
				finderElement.attributeValue("unique"));

			String finderWhere = finderElement.attributeValue("where");

			if (Validator.isNotNull(finderWhere)) {
				for (EntityColumn column : columnList) {
					String name = column.getName();

					finderWhere = StringUtil.replace(
						finderWhere, name, alias + "." + name);
				}
			}

			boolean finderDBIndex = GetterUtil.getBoolean(
				finderElement.attributeValue("db-index"), true);

			List<EntityColumn> finderColsList = new ArrayList<>();

			List<Element> finderColumnElements = finderElement.elements(
				"finder-column");

			for (Element finderColumnElement : finderColumnElements) {
				String finderColName = finderColumnElement.attributeValue(
					"name");
				boolean finderColCaseSensitive = GetterUtil.getBoolean(
					finderColumnElement.attributeValue("case-sensitive"), true);
				String finderColComparator = GetterUtil.getString(
					finderColumnElement.attributeValue("comparator"), "=");
				String finderColArrayableOperator = GetterUtil.getString(
					finderColumnElement.attributeValue("arrayable-operator"));

				EntityColumn col = Entity.getColumn(finderColName, columnList);

				if (!col.isFinderPath()) {
					col.setFinderPath(true);
				}

				col = (EntityColumn)col.clone();

				col.setCaseSensitive(finderColCaseSensitive);
				col.setComparator(finderColComparator);
				col.setArrayableOperator(finderColArrayableOperator);

				col.validate();

				finderColsList.add(col);
			}

			finderList.add(
				new EntityFinder(
					finderName, finderReturn, finderUnique, finderWhere,
					finderDBIndex, finderColsList));
		}

		List<Entity> referenceList = new ArrayList<>();
		List<String> unresolvedReferenceList = new ArrayList<>();

		if (_build) {
			List<Element> referenceElements = entityElement.elements(
				"reference");

			Set<String> referenceSet = new TreeSet<>();

			for (Element referenceElement : referenceElements) {
				String referencePackage = referenceElement.attributeValue(
					"package-path");
				String referenceEntity = referenceElement.attributeValue(
					"entity");

				referenceSet.add(referencePackage + "." + referenceEntity);
			}

			if (!_packagePath.equals("com.liferay.counter")) {
				referenceSet.add("com.liferay.counter.Counter");
			}

			if (_autoImportDefaultReferences) {
				referenceSet.add("com.liferay.portal.ClassName");
				referenceSet.add("com.liferay.portal.Resource");
				referenceSet.add("com.liferay.portal.User");
			}

			for (String referenceName : referenceSet) {
				try {
					referenceList.add(getEntity(referenceName));
				}
				catch (RuntimeException re) {
					unresolvedReferenceList.add(referenceName);
				}
			}
		}

		List<String> txRequiredList = new ArrayList<>();

		List<Element> txRequiredElements = entityElement.elements(
			"tx-required");

		for (Element txRequiredEl : txRequiredElements) {
			String txRequired = txRequiredEl.getText();

			txRequiredList.add(txRequired);
		}

		boolean resourceActionModel = _resourceActionModels.contains(
			_packagePath + ".model." + ejbName);

		_ejbList.add(
			new Entity(
				_packagePath, _portletName, _portletShortName, ejbName,
				humanName, table, alias, uuid, uuidAccessor, localService,
				remoteService, persistenceClass, finderClass, dataSource,
				sessionFactory, txManager, cacheEnabled, dynamicUpdateEnabled,
				jsonEnabled, mvccEnabled, trashEnabled, deprecated, pkList,
				regularColList, blobList, collectionList, columnList, order,
				finderList, referenceList, unresolvedReferenceList,
				txRequiredList, resourceActionModel));
	}

	private String _processTemplate(String name, Map<String, Object> context)
		throws Exception {

		_currentTplName = name;

		return StringUtil.strip(FreeMarkerUtil.process(name, context), '\r');
	}

	private Map<String, Object> _putDeprecatedKeys(
		Map<String, Object> context, JavaClass javaClass) {

		context.put("classDeprecated", false);

		if (javaClass != null) {
			DocletTag tag = javaClass.getTagByName("deprecated");

			if (tag != null) {
				context.put("classDeprecated", true);
				context.put("classDeprecatedComment", tag.getValue());
			}
		}

		return context;
	}

	private Set<String> _readLines(String fileName) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();

		Set<String> lines = new HashSet<>();

		StringUtil.readLines(classLoader.getResourceAsStream(fileName), lines);

		return lines;
	}

	private void _removeActionableDynamicQuery(Entity entity) {
		_deleteFile(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ActionableDynamicQuery.java");
	}

	private void _removeExportActionableDynamicQuery(Entity entity) {
		_deleteFile(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ExportActionableDynamicQuery.java");
	}

	private void _removeFinder(Entity entity) {
		_deleteFile(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Finder.java");
	}

	private void _removeFinderBaseImpl(Entity entity) {
		_deleteFile(
			_outputPath + "/service/persistence/impl/" + entity.getName() +
				"FinderBaseImpl.java");
	}

	private void _removeFinderUtil(Entity entity) {
		_deleteFile(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"FinderUtil.java");
	}

	private void _removeService(Entity entity, int sessionType) {
		_deleteFile(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");
	}

	private void _removeServiceBaseImpl(Entity entity, int sessionType) {
		_deleteFile(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceBaseImpl.java");
	}

	private void _removeServiceClp(Entity entity, int sessionType) {
		_deleteFile(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClp.java");
	}

	private void _removeServiceClpInvoker(Entity entity, int sessionType) {
		_deleteFile(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClpInvoker.java");
	}

	private void _removeServiceHttp(Entity entity) {
		_deleteFile(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceHttp.java");
	}

	private void _removeServiceImpl(Entity entity, int sessionType) {
		_deleteFile(
			_outputPath + "/service/impl/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceImpl.java");
	}

	private void _removeServiceSoap(Entity entity) {
		_deleteFile(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceSoap.java");
	}

	private void _removeServiceUtil(Entity entity, int sessionType) {
		_deleteFile(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceUtil.java");
	}

	private void _removeServiceWrapper(Entity entity, int sessionType) {
		_deleteFile(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceWrapper.java");
	}

	private void _resolveEntity(Entity entity) throws Exception {
		if (entity.isResolved()) {
			return;
		}

		for (String referenceName : entity.getUnresolvedReferenceList()) {
			Entity referenceEntity = getEntity(referenceName);

			if (referenceEntity == null) {
				throw new ServiceBuilderException(
					"Unable to resolve reference " + referenceName + " in " +
						ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
			}

			entity.addReference(referenceEntity);
		}

		entity.setResolved();
	}

	private static final int _DEFAULT_COLUMN_MAX_LENGTH = 75;

	private static final int _SESSION_TYPE_LOCAL = 1;

	private static final int _SESSION_TYPE_REMOTE = 0;

	private static final String _SPRING_NAMESPACE_BEANS = "beans";

	private static final String _SQL_CREATE_TABLE = "create table ";

	private static final String _TMP_DIR = System.getProperty("java.io.tmpdir");

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/service/builder/dependencies/";

	private static Pattern _getterPattern = Pattern.compile(
		"public .* get.*" + Pattern.quote("(") + "|public boolean is.*" +
			Pattern.quote("("));
	private static Pattern _setterPattern = Pattern.compile(
		"public void set.*" + Pattern.quote("("));

	private String _apiDirName;
	private String _author;
	private boolean _autoImportDefaultReferences;
	private boolean _autoNamespaceTables;
	private Set<String> _badAliasNames;
	private Set<String> _badColumnNames;
	private Set<String> _badTableNames;
	private String _beanLocatorUtil;
	private String _beanLocatorUtilShortName;
	private boolean _build;
	private long _buildNumber;
	private boolean _buildNumberIncrement;
	private String _currentTplName;
	private List<Entity> _ejbList;
	private Map<String, EntityMapping> _entityMappings;
	private Map<String, Entity> _entityPool = new HashMap<>();
	private String _hbmFileName;
	private String _implDirName;
	private Map<String, JavaClass> _javaClasses = new HashMap<>();
	private String _modelHintsFileName;
	private Set<String> _modifiedFileNames = new HashSet<>();
	private boolean _mvccEnabled;
	private boolean _osgiModule;
	private String _outputPath;
	private String _packagePath;
	private String _pluginName;
	private String _portletName = StringPool.BLANK;
	private String _portletPackageName = StringPool.BLANK;
	private String _portletShortName = StringPool.BLANK;
	private String _propsUtil;
	private String[] _readOnlyPrefixes;
	private Set<String> _resourceActionModels = new HashSet<>();
	private String _resourcesDirName;
	private String _serviceOutputPath;
	private String _springFileName;
	private String[] _springNamespaces;
	private String _sqlDirName;
	private String _sqlFileName;
	private String _sqlIndexesFileName;
	private String _sqlSequencesFileName;
	private String _targetEntityName;
	private String _testDirName;
	private String _testOutputPath;
	private String _tplActionableDynamicQuery =
		_TPL_ROOT + "actionable_dynamic_query.ftl";
	private String _tplBadAliasNames = _TPL_ROOT + "bad_alias_names.txt";
	private String _tplBadColumnNames = _TPL_ROOT + "bad_column_names.txt";
	private String _tplBadTableNames = _TPL_ROOT + "bad_table_names.txt";
	private String _tplBlobModel = _TPL_ROOT + "blob_model.ftl";
	private String _tplEjbPk = _TPL_ROOT + "ejb_pk.ftl";
	private String _tplException = _TPL_ROOT + "exception.ftl";
	private String _tplExportActionableDynamicQuery =
		_TPL_ROOT + "export_actionable_dynamic_query.ftl";
	private String _tplExtendedModel = _TPL_ROOT + "extended_model.ftl";
	private String _tplExtendedModelBaseImpl =
		_TPL_ROOT + "extended_model_base_impl.ftl";
	private String _tplExtendedModelImpl =
		_TPL_ROOT + "extended_model_impl.ftl";
	private String _tplFinder = _TPL_ROOT + "finder.ftl";
	private String _tplFinderBaseImpl = _TPL_ROOT + "finder_base_impl.ftl";
	private String _tplFinderUtil = _TPL_ROOT + "finder_util.ftl";
	private String _tplHbmXml = _TPL_ROOT + "hbm_xml.ftl";
	private String _tplJsonJs = _TPL_ROOT + "json_js.ftl";
	private String _tplJsonJsMethod = _TPL_ROOT + "json_js_method.ftl";
	private String _tplModel = _TPL_ROOT + "model.ftl";
	private String _tplModelCache = _TPL_ROOT + "model_cache.ftl";
	private String _tplModelClp = _TPL_ROOT + "model_clp.ftl";
	private String _tplModelHintsXml = _TPL_ROOT + "model_hints_xml.ftl";
	private String _tplModelImpl = _TPL_ROOT + "model_impl.ftl";
	private String _tplModelSoap = _TPL_ROOT + "model_soap.ftl";
	private String _tplModelWrapper = _TPL_ROOT + "model_wrapper.ftl";
	private String _tplPersistence = _TPL_ROOT + "persistence.ftl";
	private String _tplPersistenceImpl = _TPL_ROOT + "persistence_impl.ftl";
	private String _tplPersistenceTest = _TPL_ROOT + "persistence_test.ftl";
	private String _tplPersistenceUtil = _TPL_ROOT + "persistence_util.ftl";
	private String _tplProps = _TPL_ROOT + "props.ftl";
	private String _tplService = _TPL_ROOT + "service.ftl";
	private String _tplServiceBaseImpl = _TPL_ROOT + "service_base_impl.ftl";
	private String _tplServiceClp = _TPL_ROOT + "service_clp.ftl";
	private String _tplServiceClpInvoker =
		_TPL_ROOT + "service_clp_invoker.ftl";
	private String _tplServiceClpMessageListener =
		_TPL_ROOT + "service_clp_message_listener.ftl";
	private String _tplServiceClpSerializer =
		_TPL_ROOT + "service_clp_serializer.ftl";
	private String _tplServiceHttp = _TPL_ROOT + "service_http.ftl";
	private String _tplServiceImpl = _TPL_ROOT + "service_impl.ftl";
	private String _tplServicePropsUtil = _TPL_ROOT + "service_props_util.ftl";
	private String _tplServiceSoap = _TPL_ROOT + "service_soap.ftl";
	private String _tplServiceUtil = _TPL_ROOT + "service_util.ftl";
	private String _tplServiceWrapper = _TPL_ROOT + "service_wrapper.ftl";
	private String _tplSpringXml = _TPL_ROOT + "spring_xml.ftl";

}