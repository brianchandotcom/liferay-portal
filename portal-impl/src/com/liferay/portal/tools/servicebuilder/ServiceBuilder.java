/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ArrayUtil_IW;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.sourceformatter.SourceFormatter;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.ClassLibrary;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import de.hunsicker.io.FileFormat;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;

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
 */
public class ServiceBuilder {

	public static final String AUTHOR = "Brian Wing Shun Chan";

	public static String getContent(String fileName) throws Exception {
		Document document = _getContentDocument(fileName);

		Element rootElement = document.getRootElement();

		Element authorElement = null;
		Element namespaceElement = null;
		Map<String, Element> entityElements = new TreeMap<String, Element>();
		Map<String, Element> exceptionElements = new TreeMap<String, Element>();

		for (Element element : rootElement.elements()) {
			String elementName = element.getName();

			if (elementName.equals("author")) {
				element.detach();

				if (authorElement != null) {
					throw new IllegalArgumentException(
						"There can only be one author element");
				}

				authorElement = element;
			}
			else if (elementName.equals("namespace")) {
				element.detach();

				if (namespaceElement != null) {
					throw new IllegalArgumentException(
						"There can only be one namespace element");
				}

				namespaceElement = element;
			}
			else if (elementName.equals("entity")) {
				element.detach();

				String name = element.attributeValue("name");

				entityElements.put(name.toLowerCase(), element);
			}
			else if (elementName.equals("exceptions")) {
				element.detach();

				for (Element exceptionElement : element.elements("exception")) {
					exceptionElement.detach();

					exceptionElements.put(
						exceptionElement.getText(), exceptionElement);
				}
			}
		}

		if (authorElement != null) {
			rootElement.add(authorElement);
		}

		if (namespaceElement == null) {
			throw new IllegalArgumentException(
				"The namespace element is required");
		}
		else {
			rootElement.add(namespaceElement);
		}

		_addElements(rootElement, entityElements);

		if (!exceptionElements.isEmpty()) {
			Element exceptionsElement = rootElement.addElement("exceptions");

			_addElements(exceptionsElement, exceptionElements);
		}

		return document.asXML();
	}

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		InitUtil.initWithSpring(true);

		String fileName = arguments.get("service.input.file");
		String hbmFileName = arguments.get("service.hbm.file");
		String ormFileName = arguments.get("service.orm.file");
		String modelHintsFileName = arguments.get("service.model.hints.file");
		String springFileName = arguments.get("service.spring.file");
		String springBaseFileName = arguments.get("service.spring.base.file");
		String springClusterFileName = arguments.get("service.spring.cluster.file");
		String springDynamicDataSourceFileName = arguments.get("service.spring.dynamic.data.source.file");
		String springHibernateFileName = arguments.get("service.spring.hibernate.file");
		String springInfrastructureFileName = arguments.get("service.spring.infrastructure.file");
		String springShardDataSourceFileName = arguments.get("service.spring.shard.data.source.file");
		String apiDir = arguments.get("service.api.dir");
		String implDir = arguments.get("service.impl.dir");
		String remotingFileName = arguments.get("service.remoting.file");
		String sqlDir = arguments.get("service.sql.dir");
		String sqlFileName = arguments.get("service.sql.file");
		String sqlIndexesFileName = arguments.get("service.sql.indexes.file");
		String sqlIndexesPropertiesFileName = arguments.get("service.sql.indexes.properties.file");
		String sqlSequencesFileName = arguments.get("service.sql.sequences.file");
		boolean autoNamespaceTables = GetterUtil.getBoolean(arguments.get("service.auto.namespace.tables"));
		String beanLocatorUtil = arguments.get("service.bean.locator.util");
		String propsUtil = arguments.get("service.props.util");
		String pluginName = arguments.get("service.plugin.name");
		String targetEntityName = arguments.get("service.target.entity.name");
		String testDir = arguments.get("service.test.dir");
		long buildNumber = GetterUtil.getLong(arguments.get("service.build.number"), 1);
		boolean buildNumberIncrement = GetterUtil.getBoolean(arguments.get("service.build.number.increment"), true);

		try {
			new ServiceBuilder(
				fileName, hbmFileName, ormFileName, modelHintsFileName,
				springFileName, springBaseFileName, springClusterFileName,
				springDynamicDataSourceFileName, springHibernateFileName,
				springInfrastructureFileName, springShardDataSourceFileName,
				apiDir, implDir, remotingFileName, sqlDir, sqlFileName,
				sqlIndexesFileName, sqlIndexesPropertiesFileName,
				sqlSequencesFileName, autoNamespaceTables, beanLocatorUtil,
				propsUtil, pluginName, targetEntityName, testDir, true,
				buildNumber, buildNumberIncrement);
		}
		catch (RuntimeException re) {
			System.out.println(
				"Please set these required arguments. Sample values are:\n" +
				"\n" +
				"\tservice.input.file=${service.file}\n" +
				"\tservice.hbm.file=${basedir}/src/META-INF/portal-hbm.xml\n" +
				"\tservice.orm.file=${basedir}/src/META-INF/portal-orm.xml\n" +
				"\tservice.model.hints.file=${basedir}/src/META-INF/portal-model-hints.xml\n" +
				"\tservice.spring.file=${basedir}/src/META-INF/portal-spring.xml\n" +
				"\tservice.api.dir=${basedir}/../portal-service/src\n" +
				"\tservice.impl.dir=${basedir}/src\n" +
				"\tservice.remoting.file=${basedir}/../portal-web/docroot/WEB-INF/remoting-servlet.xml\n" +
				"\tservice.sql.dir=${basedir}/../sql\n" +
				"\tservice.sql.file=portal-tables.sql\n" +
				"\tservice.sql.indexes.file=indexes.sql\n" +
				"\tservice.sql.indexes.properties.file=indexes.properties\n" +
				"\tservice.sql.sequences.file=sequences.sql\n" +
				"\tservice.bean.locator.util=com.liferay.portal.kernel.bean.PortalBeanLocatorUtil\n" +
				"\tservice.props.util=com.liferay.portal.util.PropsUtil\n" +
				"\tservice.target.entity.name=${service.target.entity.name}\n" +
				"\tservice.test.dir=${basedir}/test/integration\n" +
				"\tservice.build.number=1\n" +
				"\tservice.build.number.increment=true\n" +
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
				"\t-Dservice.tpl.finder_util=" + _TPL_ROOT + "finder_util.ftl\n"+
				"\t-Dservice.tpl.hbm_xml=" + _TPL_ROOT + "hbm_xml.ftl\n"+
				"\t-Dservice.tpl.orm_xml=" + _TPL_ROOT + "orm_xml.ftl\n"+
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
				"\t-Dservice.tpl.remoting_xml=" + _TPL_ROOT + "remoting_xml.ftl\n"+
				"\t-Dservice.tpl.service=" + _TPL_ROOT + "service.ftl\n"+
				"\t-Dservice.tpl.service_base_impl=" + _TPL_ROOT + "service_base_impl.ftl\n"+
				"\t-Dservice.tpl.service_clp=" + _TPL_ROOT + "service_clp.ftl\n"+
				"\t-Dservice.tpl.service_clp_invoker=" + _TPL_ROOT + "service_clp_invoker.ftl\n"+
				"\t-Dservice.tpl.service_clp_message_listener=" + _TPL_ROOT + "service_clp_message_listener.ftl\n"+
				"\t-Dservice.tpl.service_clp_serializer=" + _TPL_ROOT + "service_clp_serializer.ftl\n"+
				"\t-Dservice.tpl.service_http=" + _TPL_ROOT + "service_http.ftl\n"+
				"\t-Dservice.tpl.service_impl=" + _TPL_ROOT + "service_impl.ftl\n"+
				"\t-Dservice.tpl.service_soap=" + _TPL_ROOT + "service_soap.ftl\n"+
				"\t-Dservice.tpl.service_util=" + _TPL_ROOT + "service_util.ftl\n"+
				"\t-Dservice.tpl.service_wrapper=" + _TPL_ROOT + "service_wrapper.ftl\n"+
				"\t-Dservice.tpl.spring_base_xml=" + _TPL_ROOT + "spring_base_xml.ftl\n"+
				"\t-Dservice.tpl.spring_hibernate_xml=" + _TPL_ROOT + "spring_hibernate_xml.ftl\n"+
				"\t-Dservice.tpl.spring_infrastructure_xml=" + _TPL_ROOT + "spring_infrastructure_xml.ftl\n"+
				"\t-Dservice.tpl.spring_xml=" + _TPL_ROOT + "spring_xml.ftl\n"+
				"\t-Dservice.tpl.spring_xml_session=" + _TPL_ROOT + "spring_xml_session.ftl");

			throw re;
		}

		try {
			ClearThreadLocalUtil.clearThreadLocal();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Introspector.flushCaches();
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

	public static void writeFile(File file, String content)
		throws IOException {

		writeFile(file, content, AUTHOR);
	}

	public static void writeFile(File file, String content, String author)
		throws IOException {

		writeFile(file, content, author, null);
	}

	public static void writeFile(
			File file, String content, String author,
			Map<String, Object> jalopySettings)
		throws IOException {

		String packagePath = _getPackagePath(file);

		String className = file.getName();

		className = className.substring(0, className.length() - 5);

		content = SourceFormatter.stripJavaImports(
			content, packagePath, className);

		File tempFile = new File("ServiceBuilder.temp");

		FileUtil.write(tempFile, content);

		// Beautify

		StringBuffer sb = new StringBuffer();

		Jalopy jalopy = new Jalopy();

		jalopy.setFileFormat(FileFormat.UNIX);
		jalopy.setInput(tempFile);
		jalopy.setOutput(sb);

		File jalopyXmlFile = new File("tools/jalopy.xml");

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../tools/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("misc/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../misc/jalopy.xml");
		}

		if (!jalopyXmlFile.exists()) {
			jalopyXmlFile = new File("../../misc/jalopy.xml");
		}

		try {
			Jalopy.setConvention(jalopyXmlFile);
		}
		catch (FileNotFoundException fnfe) {
		}

		if (jalopySettings == null) {
			jalopySettings = new HashMap<String, Object>();
		}

		Environment env = Environment.getInstance();

		// Author

		author = GetterUtil.getString(
			(String)jalopySettings.get("author"), author);

		env.set("author", author);

		// File name

		env.set("fileName", file.getName());

		Convention convention = Convention.getInstance();

		String classMask = "/**\n * @author $author$\n*/";

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS,
			env.interpolate(classMask));

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE,
			env.interpolate(classMask));

		jalopy.format();

		String newContent = sb.toString();

		// Remove double blank lines after the package or last import

		newContent = newContent.replaceFirst(
			"(?m)^[ \t]*((?:package|import) .*;)\\s*^[ \t]*/\\*\\*",
			"$1\n\n/**");

		/*
		// Remove blank lines after try {

		newContent = StringUtil.replace(newContent, "try {\n\n", "try {\n");

		// Remove blank lines after ) {

		newContent = StringUtil.replace(newContent, ") {\n\n", ") {\n");

		// Remove blank lines empty braces { }

		newContent = StringUtil.replace(newContent, "\n\n\t}", "\n\t}");

		// Add space to last }

		newContent = newContent.substring(0, newContent.length() - 2) + "\n\n}";
		*/

		writeFileRaw(file, newContent);

		tempFile.deleteOnExit();
	}

	public static void writeFileRaw(File file, String content)
		throws IOException {

		// Write file if and only if the file has changed

		if (!file.exists() || !FileUtil.isSameContent(file, content)) {
			FileUtil.write(file, content);

			System.out.println("Writing " + file);
		}
	}

	public ServiceBuilder(
		String fileName, String hbmFileName, String ormFileName,
		String modelHintsFileName, String springFileName,
		String springBaseFileName, String springClusterFileName,
		String springDynamicDataSourceFileName, String springHibernateFileName,
		String springInfrastructureFileName,
		String springShardDataSourceFileName, String apiDir, String implDir,
		String remotingFileName, String sqlDir, String sqlFileName,
		String sqlIndexesFileName, String sqlIndexesPropertiesFileName,
		String sqlSequencesFileName, boolean autoNamespaceTables,
		String beanLocatorUtil, String propsUtil, String pluginName,
		String targetEntityName, String testDir) {

		this(
			fileName, hbmFileName, ormFileName, modelHintsFileName,
			springFileName, springBaseFileName, springClusterFileName,
			springDynamicDataSourceFileName, springHibernateFileName,
			springInfrastructureFileName, springShardDataSourceFileName, apiDir,
			implDir, remotingFileName, sqlDir, sqlFileName, sqlIndexesFileName,
			sqlIndexesPropertiesFileName, sqlSequencesFileName,
			autoNamespaceTables, beanLocatorUtil, propsUtil, pluginName,
			targetEntityName, testDir, true, 1, true);
	}

	public ServiceBuilder(
		String fileName, String hbmFileName, String ormFileName,
		String modelHintsFileName, String springFileName,
		String springBaseFileName, String springClusterFileName,
		String springDynamicDataSourceFileName, String springHibernateFileName,
		String springInfrastructureFileName,
		String springShardDataSourceFileName, String apiDir, String implDir,
		String remotingFileName, String sqlDir, String sqlFileName,
		String sqlIndexesFileName, String sqlIndexesPropertiesFileName,
		String sqlSequencesFileName, boolean autoNamespaceTables,
		String beanLocatorUtil, String propsUtil, String pluginName,
		String targetEntityName, String testDir, boolean build,
		long buildNumber, boolean buildNumberIncrement) {

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
		_tplFinderUtil = _getTplProperty("finder_util", _tplFinderUtil);
		_tplHbmXml = _getTplProperty("hbm_xml", _tplHbmXml);
		_tplOrmXml = _getTplProperty("orm_xml", _tplOrmXml);
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
		_tplRemotingXml = _getTplProperty("remoting_xml", _tplRemotingXml);
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
		_tplServiceSoap = _getTplProperty("service_soap", _tplServiceSoap);
		_tplServiceUtil = _getTplProperty("service_util", _tplServiceUtil);
		_tplServiceWrapper = _getTplProperty(
			"service_wrapper", _tplServiceWrapper);
		_tplSpringBaseXml = _getTplProperty(
			"spring_base_xml", _tplSpringBaseXml);
		_tplSpringClusterXml = _getTplProperty(
			"spring_cluster_xml", _tplSpringClusterXml);
		_tplSpringHibernateXml = _getTplProperty(
			"spring_hibernate_xml", _tplSpringHibernateXml);
		_tplSpringInfrastructureXml = _getTplProperty(
			"spring_infrastructure_xml", _tplSpringInfrastructureXml);
		_tplSpringShardDataSourceXml = _getTplProperty(
			"spring_shard_data_source_xml", _tplSpringShardDataSourceXml);
		_tplSpringXml = _getTplProperty("spring_xml", _tplSpringXml);

		try {
			_badTableNames = _readLines(_tplBadTableNames);
			_badAliasNames = _readLines(_tplBadAliasNames);
			_badColumnNames = _readLines(_tplBadColumnNames);
			_hbmFileName = hbmFileName;
			_ormFileName = ormFileName;
			_modelHintsFileName = modelHintsFileName;
			_springFileName = springFileName;
			_springBaseFileName = springBaseFileName;
			_springClusterFileName = springClusterFileName;
			_springDynamicDataSourceFileName = springDynamicDataSourceFileName;
			_springHibernateFileName = springHibernateFileName;
			_springInfrastructureFileName = springInfrastructureFileName;
			_springShardDataSourceFileName = springShardDataSourceFileName;
			_apiDir = apiDir;
			_implDir = implDir;
			_remotingFileName = remotingFileName;
			_sqlDir = sqlDir;
			_sqlFileName = sqlFileName;
			_sqlIndexesFileName = sqlIndexesFileName;
			_sqlIndexesPropertiesFileName = sqlIndexesPropertiesFileName;
			_sqlSequencesFileName = sqlSequencesFileName;
			_autoNamespaceTables = autoNamespaceTables;
			_beanLocatorUtil = beanLocatorUtil;
			_beanLocatorUtilShortName = _beanLocatorUtil.substring(
				_beanLocatorUtil.lastIndexOf(".") + 1);
			_propsUtil = propsUtil;
			_pluginName = GetterUtil.getString(pluginName);
			_targetEntityName = targetEntityName;
			_testDir = testDir;
			_build = build;
			_buildNumber = buildNumber;
			_buildNumberIncrement = buildNumberIncrement;

			String content = getContent(fileName);

			Document document = SAXReaderUtil.read(content, true);

			Element rootElement = document.getRootElement();

			String packagePath = rootElement.attributeValue("package-path");

			if (Validator.isNull(packagePath)) {
				throw new IllegalArgumentException(
					"The package-path attribute is required");
			}

			_outputPath =
				_implDir + "/" + StringUtil.replace(packagePath, ".", "/");

			_serviceOutputPath =
				_apiDir + "/" + StringUtil.replace(packagePath, ".", "/");

			if (Validator.isNotNull(_testDir)) {
				_testOutputPath =
					_testDir + "/" + StringUtil.replace(packagePath, ".", "/");
			}

			_packagePath = packagePath;

			_autoNamespaceTables = GetterUtil.getBoolean(
				rootElement.attributeValue("auto-namespace-tables"),
				_autoNamespaceTables);

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

			_ejbList = new ArrayList<Entity>();
			_entityMappings = new HashMap<String, EntityMapping>();

			List<Element> entityElements = rootElement.elements("entity");

			for (Element entityElement : entityElements) {
				_parseEntity(entityElement);
			}

			List<String> exceptionList = new ArrayList<String>();

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

						if (entity.hasActionableDynamicQuery()) {
							_createActionableDynamicQuery(entity);

							if (entity.isStagedModel()) {
								_createExportActionableDynamicQuery(entity);
							}
						}

						if (entity.hasColumns()) {
							_createHbm(entity);
							_createHbmUtil(entity);

							_createPersistenceImpl(entity);
							_createPersistence(entity);
							_createPersistenceUtil(entity);

							if (Validator.isNotNull(_testDir)) {
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

							if (Validator.isNotNull(_remotingFileName)) {
								_createServiceHttp(entity);
							}

							_createServiceJson(entity);

							if (entity.hasColumns()) {
								_createServiceJsonSerializer(entity);
							}

							_createServiceSoap(entity);
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
				_createOrmXml();
				_createModelHintsXml();
				_createSpringXml();

				_createExceptions(exceptionList);

				_createServiceClpMessageListener();
				_createServiceClpSerializer(exceptionList);

				if (Validator.isNotNull(_remotingFileName)) {
					_createRemotingXml();
				}

				_createSQLIndexes();
				_createSQLTables();
				_createSQLSequences();

				_createProps();
				_createSpringBaseXml();
				_createSpringClusterXml();
				_createSpringDynamicDataSourceXml();
				_createSpringHibernateXml();
				_createSpringInfrastructureXml();
				_createSpringShardDataSourceXml();
			}
		}
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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
		throws IOException {

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

	public Entity getEntity(String name) throws IOException {
		Entity entity = _entityPool.get(name);

		if (entity != null) {
			return entity;
		}

		int pos = name.lastIndexOf(".");

		if (pos == -1) {
			pos = _ejbList.indexOf(new Entity(name));

			if (pos == -1) {
				throw new RuntimeException(
					"Cannot find " + name + " in " +
						ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
			}

			entity = _ejbList.get(pos);

			_entityPool.put(name, entity);

			return entity;
		}
		else {
			String refPackage = name.substring(0, pos);
			String refEntity = name.substring(pos + 1);

			if (refPackage.equals(_packagePath)) {
				pos = _ejbList.indexOf(new Entity(refEntity));

				if (pos == -1) {
					throw new RuntimeException(
						"Cannot find " + refEntity + " in " +
							ListUtil.toString(_ejbList, Entity.NAME_ACCESSOR));
				}

				entity = _ejbList.get(pos);

				_entityPool.put(name, entity);

				return entity;
			}

			String refPackageDir = StringUtil.replace(refPackage, ".", "/");

			String refFileName =
				_implDir + "/" + refPackageDir + "/service.xml";

			File refFile = new File(refFileName);

			boolean useTempFile = false;

			if (!refFile.exists()) {
				refFileName = Time.getTimestamp();
				refFile = new File(refFileName);

				ClassLoader classLoader = getClass().getClassLoader();

				FileUtil.write(
					refFileName,
					StringUtil.read(
						classLoader, refPackageDir + "/service.xml"));

				useTempFile = true;
			}

			ServiceBuilder serviceBuilder = new ServiceBuilder(
				refFileName, _hbmFileName, _ormFileName, _modelHintsFileName,
				_springFileName, _springBaseFileName, _springClusterFileName,
				_springDynamicDataSourceFileName, _springHibernateFileName,
				_springInfrastructureFileName, _springShardDataSourceFileName,
				_apiDir, _implDir, _remotingFileName, _sqlDir, _sqlFileName,
				_sqlIndexesFileName, _sqlIndexesPropertiesFileName,
				_sqlSequencesFileName, _autoNamespaceTables, _beanLocatorUtil,
				_propsUtil, _pluginName, _targetEntityName, _testDir, false,
				_buildNumber, _buildNumberIncrement);

			entity = serviceBuilder.getEntity(refEntity);

			entity.setPortalReference(useTempFile);

			_entityPool.put(name, entity);

			if (useTempFile) {
				refFile.deleteOnExit();
			}

			return entity;
		}
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
		throws IOException {

		List<EntityColumn> mappingEntitiesPKList =
			new ArrayList<EntityColumn>();

		EntityMapping entityMapping = _entityMappings.get(mappingTable);

		for (int i = 0; i < 2; i++) {
			Entity entity = getEntity(entityMapping.getEntity(i));

			if (entity == null) {
				return null;
			}

			mappingEntitiesPKList.addAll(entity.getPKList());
		}

		return mappingEntitiesPKList;
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
		Type returnType = method.getReturns();

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
		else if (type.equals("String")) {
			Map<String, String> hints = ModelHintsUtil.getHints(model, field);

			if (hints != null) {
				int maxLength = GetterUtil.getInteger(hints.get("max-length"));

				if (maxLength == 2000000) {
					return "CLOB";
				}
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

			for (int i = 0; i < actualTypeArguments.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}

				sb.append(getTypeGenericsName(actualTypeArguments[i]));
			}

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
			methodName.equals("destroy") ||
			methodName.equals("equals") ||
			methodName.equals("getClass") ||
			methodName.equals("hashCode") ||
			methodName.equals("notify") ||
			methodName.equals("notifyAll") ||
			methodName.equals("toString") ||
			methodName.equals("wait")) {

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

	public boolean isDuplicateMethod(
		JavaMethod method, Map<String, Object> tempMap) {

		StringBundler sb = new StringBundler();

		sb.append("isDuplicateMethod ");
		sb.append(getTypeGenericsName(method.getReturns()));
		sb.append(StringPool.SPACE);
		sb.append(method.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);

		JavaParameter[] parameters = method.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			JavaParameter javaParameter = parameters[i];

			sb.append(getTypeGenericsName(javaParameter.getType()));

			if ((i + 1) != parameters.length) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		String key = sb.toString();

		if (tempMap.put(key, key) != null) {
			return true;
		}
		else {
			return false;
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

		return isReadOnlyMethod(
			method, txRequiredList,
			PropsValues.SERVICE_BUILDER_SERVICE_READ_ONLY_PREFIXES);
	}

	public boolean isSoapMethod(JavaMethod method) {
		Type returnType = method.getReturns();

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
					"com.liferay.portlet.dynamicdatamapping.storage.Fields") ||
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

	private static void _addElements(
		Element element, Map<String, Element> elements) {

		for (Map.Entry<String, Element> entry : elements.entrySet()) {
			Element childElement = entry.getValue();

			element.add(childElement);
		}
	}

	private static Document _getContentDocument(String fileName)
		throws Exception {

		String content = FileUtil.read(new File(fileName));

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements()) {
			String elementName = element.getName();

			if (!elementName.equals("service-builder-import")) {
				continue;
			}

			element.detach();

			String dirName = fileName.substring(
				0, fileName.lastIndexOf(StringPool.SLASH) + 1);
			String serviceBuilderImportFileName = element.attributeValue(
				"file");

			Document serviceBuilderImportDocument = _getContentDocument(
				dirName + serviceBuilderImportFileName);

			Element serviceBuilderImportRootElement =
				serviceBuilderImportDocument.getRootElement();

			for (Element serviceBuilderImportElement :
					serviceBuilderImportRootElement.elements()) {

				serviceBuilderImportElement.detach();

				rootElement.add(serviceBuilderImportElement);
			}
		}

		return document;
	}

	private static String _getPackagePath(File file) {
		String fileName = StringUtil.replace(file.toString(), "\\", "/");

		int x = fileName.indexOf("src/");

		if (x == -1) {
			x = fileName.indexOf("test/");
		}

		int y = fileName.lastIndexOf("/");

		fileName = fileName.substring(x + 4, y);

		return StringUtil.replace(fileName, "/", ".");
	}

	private void _createActionableDynamicQuery(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplActionableDynamicQuery, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ActionableDynamicQuery.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createBlobModels(Entity entity) throws Exception {
		List<EntityColumn> blobList = new ArrayList<EntityColumn>(
			entity.getBlobList());

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

			writeFile(blobModelFile, content, _author);
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

		writeFile(ejbFile, content, _author);
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
			File exceptionFile = new File(
				_serviceOutputPath + "/" + exception + "Exception.java");

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

				FileUtil.write(exceptionFile, content);
			}

			if (exception.startsWith("NoSuch")) {
				String content = FileUtil.read(exceptionFile);

				if (!content.contains("NoSuchModelException")) {
					content = StringUtil.replace(
						content, "PortalException", "NoSuchModelException");
					content = StringUtil.replace(
						content, "kernel.exception.NoSuchModelException",
						"NoSuchModelException");

					FileUtil.write(exceptionFile, content);
				}
			}

			if (!_serviceOutputPath.equals(_outputPath)) {
				exceptionFile = new File(
					_outputPath + "/" + exception + "Exception.java");

				if (exceptionFile.exists()) {
					System.out.println("Relocating " + exceptionFile);

					exceptionFile.delete();
				}
			}
		}
	}

	private void _createExportActionableDynamicQuery(Entity entity)
		throws Exception {

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(
			_tplExportActionableDynamicQuery, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" +
				entity.getName() + "ExportActionableDynamicQuery.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createExtendedModel(Entity entity) throws Exception {
		JavaClass modelImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		List<JavaMethod> methods = ListUtil.fromArray(
			_getMethods(modelImplJavaClass));

		JavaClass modelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		for (JavaMethod method : _getMethods(modelJavaClass)) {
			methods.remove(method);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods.toArray(new Object[methods.size()]));

		// Content

		String content = _processTemplate(_tplExtendedModel, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + ".java");

		writeFile(modelFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			modelFile = new File(
				_outputPath + "/model/" + entity.getName() + ".java");

			if (modelFile.exists()) {
				System.out.println("Relocating " + modelFile);

				modelFile.delete();
			}
		}
	}

	private void _createExtendedModelBaseImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplExtendedModelBaseImpl, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() + "BaseImpl.java");

		writeFile(modelFile, content, _author);
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
			content = FileUtil.read(modelFile);

			content = content.replaceAll(
				"extends\\s+" + entity.getName() +
					"ModelImpl\\s+implements\\s+" + entity.getName(),
				"extends " + entity.getName() + "BaseImpl");

			writeFileRaw(modelFile, content);
		}
		else {
			writeFile(modelFile, content, _author);
		}
	}

	private void _createFinder(Entity entity) throws Exception {
		if (!entity.hasFinderClass()) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/" + entity.getName() +
				"FinderImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		// Content

		String content = _processTemplate(_tplFinder, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Finder.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/persistence/" + entity.getName() +
					"Finder.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
	}

	private void _createFinderUtil(Entity entity) throws Exception {
		if (!entity.hasFinderClass()) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/" + entity.getName() +
				"FinderImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		// Content

		String content = _processTemplate(_tplFinderUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"FinderUtil.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/persistence/" + entity.getName() +
					"FinderUtil.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
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

			FileUtil.write(xmlFile, xml);
		}

		String oldContent = FileUtil.read(xmlFile);
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
			"<class name=\"" + _packagePath + ".model.impl.");
		int lastClass = newContent.lastIndexOf(
			"<class name=\"" + _packagePath + ".model.impl.");

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

		newContent = _formatXml(newContent);

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createModel(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplModel, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		writeFile(modelFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			modelFile = new File(
				_outputPath + "/model/" + entity.getName() + "Model.java");

			if (modelFile.exists()) {
				System.out.println("Relocating " + modelFile);

				modelFile.delete();
			}
		}
	}

	private void _createModelCache(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("cacheFields", _getCacheFields(javaClass));

		// Content

		String content = _processTemplate(_tplModelCache, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() +
				"CacheModel.java");

		writeFile(modelFile, content, _author);
	}

	private void _createModelClp(Entity entity) throws Exception {
		if (Validator.isNull(_pluginName)) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, JavaMethod> methods = new HashMap<String, JavaMethod>();

		for (JavaMethod method : javaClass.getMethods()) {
			methods.put(method.getDeclarationSignature(false), method);
		}

		Type superClass = javaClass.getSuperClass();

		String superClassValue = superClass.getValue();

		while (!superClassValue.endsWith("BaseModelImpl")) {
			int pos = superClassValue.lastIndexOf(StringPool.PERIOD);

			if (pos > 0) {
				superClassValue = superClassValue.substring(pos + 1);
			}

			javaClass = _getJavaClass(
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

		writeFile(modelFile, content, _author);
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

			FileUtil.write(xmlFile, xml);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = oldContent;

		int firstModel = newContent.indexOf(
			"<model name=\"" + _packagePath + ".model.");
		int lastModel = newContent.lastIndexOf(
			"<model name=\"" + _packagePath + ".model.");

		if (firstModel == -1) {
			int x = newContent.indexOf("</model-hints>");

			newContent =
				newContent.substring(0, x) + content +
					newContent.substring(x);
		}
		else {
			firstModel = newContent.lastIndexOf("<model", firstModel) - 1;
			lastModel = newContent.indexOf("</model>", lastModel) + 9;

			newContent =
				newContent.substring(0, firstModel) + content +
					newContent.substring(lastModel);
		}

		newContent = _formatXml(newContent);

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createModelImpl(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "Impl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("cacheFields", _getCacheFields(javaClass));

		// Content

		String content = _processTemplate(_tplModelImpl, context);

		// Write file

		File modelFile = new File(
			_outputPath + "/model/impl/" + entity.getName() + "ModelImpl.java");

		writeFile(modelFile, content, _author);
	}

	private void _createModelSoap(Entity entity) throws Exception {
		File modelFile = null;

		if (!_serviceOutputPath.equals(_outputPath)) {
			modelFile = new File(
				_outputPath + "/model/" + entity.getName() + "Soap.java");

			if (modelFile.exists()) {
				System.out.println("Relocating " + modelFile);

				modelFile.delete();
			}
		}

		modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Soap.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplModelSoap, context);

		// Write file

		writeFile(modelFile, content, _author);
	}

	private void _createModelWrapper(Entity entity) throws Exception {
		JavaClass modelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + "Model.java");

		Object[] methods = _getMethods(modelJavaClass);

		JavaClass extendedModelBaseImplJavaClass = _getJavaClass(
			_outputPath + "/model/impl/" + entity.getName() + "BaseImpl.java");

		methods = ArrayUtil.append(
			methods, _getMethods(extendedModelBaseImplJavaClass));

		JavaClass extendedModelJavaClass = _getJavaClass(
			_serviceOutputPath + "/model/" + entity.getName() + ".java");

		methods = ArrayUtil.append(
			methods, _getMethods(extendedModelJavaClass));

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);

		// Content

		String content = _processTemplate(_tplModelWrapper, context);

		// Write file

		File modelFile = new File(
			_serviceOutputPath + "/model/" + entity.getName() + "Wrapper.java");

		writeFile(modelFile, content, _author);
	}

	private void _createOrmXml() throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entities", _ejbList);

		// Content

		String content = _processTemplate(_tplOrmXml, context);

		String mappedClasses = "";

		int lastMappedClassStart = content.lastIndexOf("<mapped-superclass");

		if (lastMappedClassStart != -1) {
			int lastMappedClassEnd = content.indexOf(
				"</mapped-superclass>", lastMappedClassStart) + 20;

			mappedClasses = content.substring(0, lastMappedClassEnd);

			content = content.substring(lastMappedClassEnd + 1);
		}

		File xmlFile = new File(_ormFileName);

		if (!xmlFile.exists()) {
			String xml =
				"<?xml version=\"1.0\"?>\n" +
				"<entity-mappings version=\"1.0\" xmlns=\"http://java.sun.com/xml/ns/persistence/orm\"\n" +
				"\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"\txsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_1_0.xsd\"\n" +
				">\n" +
				"<persistence-unit-metadata>\n" +
				"\t<xml-mapping-metadata-complete />\n" +
				"\t<persistence-unit-defaults>\n" +
				"\t\t<access>PROPERTY</access>\n" +
				"\t</persistence-unit-defaults>\n" +
				"</persistence-unit-metadata>\n" +
				"</entity-mappings>";

			FileUtil.write(xmlFile, xml);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = oldContent;

		int firstMappedClass = newContent.indexOf(
			"<mapped-superclass class=\"" + _packagePath + ".model.");
		int lastMappedClass = newContent.lastIndexOf(
			"<mapped-superclass class=\"" + _packagePath + ".model.");

		if (firstMappedClass == -1) {
			int x = newContent.indexOf("<entity class=");

			if (x != -1) {
				newContent =
					newContent.substring(0, x) + mappedClasses +
						newContent.substring(x);
			}
			else {
				content = mappedClasses + content;
			}
		}
		else {
			firstMappedClass = newContent.indexOf(
				"<mapped-superclass", firstMappedClass) - 1;
			lastMappedClass = newContent.indexOf(
				"</mapped-superclass>", lastMappedClass) + 20;

			newContent =
				newContent.substring(0, firstMappedClass) + mappedClasses +
					newContent.substring(lastMappedClass);
		}

		int firstEntity = newContent.indexOf(
			"<entity class=\"" + _packagePath + ".model.impl.");
		int lastEntity = newContent.lastIndexOf(
			"<entity class=\"" + _packagePath + ".model.impl.");

		if (firstEntity == -1) {
			int x = newContent.indexOf("</entity-mappings>");

			if (x != -1) {
				newContent =
					newContent.substring(0, x) + content +
						newContent.substring(x);
			}
		}
		else {
			firstEntity = newContent.lastIndexOf("<entity", firstEntity) - 1;
			lastEntity = newContent.indexOf("</entity>", lastEntity) + 9;

			newContent =
				newContent.substring(0, firstEntity) + content +
					newContent.substring(lastEntity);
		}

		newContent = _formatXml(newContent);

		newContent = StringUtil.replace(
			newContent,
			new String[] {"<attributes></attributes>", "<attributes/>"},
			new String[] {"<attributes />", "<attributes />"});

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createPersistence(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/" + entity.getName() +
				"PersistenceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		// Content

		String content = _processTemplate(_tplPersistence, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Persistence.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/persistence/" + entity.getName() +
					"Persistence.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
	}

	private void _createPersistenceImpl(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put(
			"referenceList", _mergeReferenceList(entity.getReferenceList()));

		// Content

		Logger.selectLoggerLibrary(Logger.LIBRARY_NONE);

		String content = _processTemplate(_tplPersistenceImpl, context);

		Logger.selectLoggerLibrary(Logger.LIBRARY_AUTO);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/persistence/" + entity.getName() +
				"PersistenceImpl.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createPersistenceTest(Entity entity) throws Exception {
		Map<String, Object> context = _getContext();

		context.put("entity", entity);

		// Content

		String content = _processTemplate(_tplPersistenceTest, context);

		// Write file

		File ejbFile = new File(
			_testOutputPath + "/service/persistence/" + entity.getName() +
				"PersistenceTest.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createPersistenceUtil(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/persistence/" + entity.getName() +
				"PersistenceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		// Content

		String content = _processTemplate(_tplPersistenceUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/persistence/" + entity.getName() +
				"Util.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/persistence/" + entity.getName() +
					"Util.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
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
		if (Validator.isNull(_pluginName)) {
			return;
		}

		// Content

		File propsFile = new File(_implDir + "/service.properties");

		long buildNumber = 1;
		long buildDate = System.currentTimeMillis();

		if (propsFile.exists()) {
			Properties properties = PropertiesUtil.load(
				FileUtil.read(propsFile));

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

		FileUtil.write(propsFile, content, true);
	}

	private void _createRemotingXml() throws Exception {
		StringBundler sb = new StringBundler();

		Document document = SAXReaderUtil.read(new File(_springFileName));

		Element rootElement = document.getRootElement();

		List<Element> beanElements = rootElement.elements("bean");

		for (Element beanElement : beanElements) {
			String beanId = beanElement.attributeValue("id");

			if (beanId.endsWith("Service") &&
				!beanId.endsWith("LocalService")) {

				String entityName = beanId;

				entityName = StringUtil.replaceLast(
					entityName, ".service.", ".");

				int pos = entityName.lastIndexOf("Service");

				entityName = entityName.substring(0, pos);

				Entity entity = getEntity(entityName);

				String serviceName = beanId;

				String serviceMapping = serviceName;

				serviceMapping = StringUtil.replaceLast(
					serviceMapping, ".service.", ".service.spring.");
				serviceMapping = StringUtil.replace(
					serviceMapping, StringPool.PERIOD, StringPool.UNDERLINE);

				Map<String, Object> context = _getContext();

				context.put("entity", entity);
				context.put("serviceName", serviceName);
				context.put("serviceMapping", serviceMapping);

				sb.append(_processTemplate(_tplRemotingXml, context));
			}
		}

		File outputFile = new File(_remotingFileName);

		if (!outputFile.exists()) {
			return;
		}

		String content = FileUtil.read(outputFile);
		String newContent = content;

		int x = content.indexOf("<bean ");
		int y = content.lastIndexOf("</bean>") + 8;

		if (x != -1) {
			newContent =
				content.substring(0, x - 1) + sb.toString() +
					content.substring(y);
		}
		else {
			x = content.indexOf("</beans>");

			if (x != -1) {
				newContent =
					content.substring(0, x) + sb.toString() +
						content.substring(x);
			}
			else {
				x = content.indexOf("<beans/>");
				y = x + 8;

				newContent =
					content.substring(0, x) + "<beans>" + sb.toString() +
						"</beans>" + content.substring(y);
			}
		}

		newContent = _formatXml(newContent);

		if (!content.equals(newContent)) {
			FileUtil.write(outputFile, newContent);

			System.out.println(outputFile.toString());
		}
	}

	private void _createService(Entity entity, int sessionType)
		throws Exception {

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

			methods = ArrayUtil.append(
				parentJavaClass.getMethods(), methods);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		// Content

		String content = _processTemplate(_tplService, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "Service.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/" + entity.getName() +
					_getSessionTypeName(sessionType) + "Service.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
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
		context.put("sessionTypeName",_getSessionTypeName(sessionType));
		context.put(
			"referenceList", _mergeReferenceList(entity.getReferenceList()));

		// Content

		String content = _processTemplate(_tplServiceBaseImpl, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceBaseImpl.java");

		writeFile(ejbFile, content, _author);
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

		// Content

		String content = _processTemplate(_tplServiceClp, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClp.java");

		writeFile(ejbFile, content, _author);
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

			methods = ArrayUtil.append(
				parentJavaClass.getMethods(), methods);
		}

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", methods);
		context.put("sessionTypeName", _getSessionTypeName(sessionType));

		// Content

		String content = _processTemplate(_tplServiceClpInvoker, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/base/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceClpInvoker.java");

		writeFile(ejbFile, content);
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

		writeFile(ejbFile, content);
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

		writeFile(ejbFile, content);
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
		context.put("hasHttpMethods", new Boolean(_hasHttpMethods(javaClass)));

		// Content

		String content = _processTemplate(_tplServiceHttp, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceHttp.java");

		writeFile(ejbFile, content, _author);
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
			writeFile(ejbFile, content, _author);
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

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/http/" + entity.getName() +
					"JSONSerializer.java");

			if (ejbFile.exists()) {
				System.out.println("Removing deprecated " + ejbFile);

				ejbFile.delete();
			}
		}
	}

	private void _createServiceSoap(Entity entity) throws Exception {
		JavaClass javaClass = _getJavaClass(
			_outputPath + "/service/impl/" + entity.getName() +
				"ServiceImpl.java");

		Map<String, Object> context = _getContext();

		context.put("entity", entity);
		context.put("methods", _getMethods(javaClass));

		// Content

		String content = _processTemplate(_tplServiceSoap, context);

		// Write file

		File ejbFile = new File(
			_outputPath + "/service/http/" + entity.getName() +
				"ServiceSoap.java");

		writeFile(ejbFile, content, _author);
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

		// Content

		String content = _processTemplate(_tplServiceUtil, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceUtil.java");

		writeFile(ejbFile, content, _author);

		if (!_serviceOutputPath.equals(_outputPath)) {
			ejbFile = new File(
				_outputPath + "/service/" + entity.getName() +
					_getSessionTypeName(sessionType) + "ServiceUtil.java");

			if (ejbFile.exists()) {
				System.out.println("Relocating " + ejbFile);

				ejbFile.delete();
			}
		}
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

		// Content

		String content = _processTemplate(_tplServiceWrapper, context);

		// Write file

		File ejbFile = new File(
			_serviceOutputPath + "/service/" + entity.getName() +
				_getSessionTypeName(sessionType) + "ServiceWrapper.java");

		writeFile(ejbFile, content, _author);
	}

	private void _createSpringBaseXml() throws Exception {
		if (Validator.isNull(_springBaseFileName)) {
			return;
		}

		// Content

		String content = _processTemplate(_tplSpringBaseXml);

		// Write file

		File ejbFile = new File(_springBaseFileName);

		FileUtil.write(ejbFile, content, true);

		if (Validator.isNotNull(_pluginName)) {
			FileUtil.delete(
				"docroot/WEB-INF/src/META-INF/data-source-spring.xml");
			FileUtil.delete("docroot/WEB-INF/src/META-INF/misc-spring.xml");
		}
	}

	private void _createSpringClusterXml() throws Exception {
		if (Validator.isNull(_springClusterFileName)) {
			return;
		}

		// Content

		String content = _processTemplate(_tplSpringClusterXml);

		// Write file

		File ejbFile = new File(_springClusterFileName);

		FileUtil.write(ejbFile, content, true);
	}

	private void _createSpringDynamicDataSourceXml() throws Exception {
		if (Validator.isNull(_springDynamicDataSourceFileName)) {
			return;
		}

		File ejbFile = new File(_springDynamicDataSourceFileName);

		if (ejbFile.exists()) {
			System.out.println("Removing deprecated " + ejbFile);

			ejbFile.delete();
		}
	}

	private void _createSpringHibernateXml() throws Exception {
		if (Validator.isNull(_springHibernateFileName)) {
			return;
		}

		// Content

		String content = _processTemplate(_tplSpringHibernateXml);

		// Write file

		File ejbFile = new File(_springHibernateFileName);

		FileUtil.write(ejbFile, content, true);
	}

	private void _createSpringInfrastructureXml() throws Exception {
		if (Validator.isNull(_springInfrastructureFileName)) {
			return;
		}

		// Content

		String content = _processTemplate(_tplSpringInfrastructureXml);

		// Write file

		File ejbFile = new File(_springInfrastructureFileName);

		FileUtil.write(ejbFile, content, true);
	}

	private void _createSpringShardDataSourceXml() throws Exception {
		if (Validator.isNull(_springShardDataSourceFileName)) {
			return;
		}

		// Content

		String content = _processTemplate(_tplSpringShardDataSourceXml);

		// Write file

		File ejbFile = new File(_springShardDataSourceFileName);

		FileUtil.write(ejbFile, content, true);
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
			"\txmlns=\"http://www.springframework.org/schema/beans\"\n" +
			"\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
			"\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\"\n" +
			">\n" +
			"</beans>";

		if (!xmlFile.exists()) {
			FileUtil.write(xmlFile, xml);
		}

		String oldContent = FileUtil.read(xmlFile);

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

		newContent = _formatXml(newContent);

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createSQLIndexes() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		// indexes.sql

		File sqlFile = new File(_sqlDir + "/" + _sqlIndexesFileName);

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, "");
		}

		Map<String, String> indexSQLs = new TreeMap<String, String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(sqlFile));

		while (true) {
			String indexSQL = unsyncBufferedReader.readLine();

			if (indexSQL == null) {
				break;
			}

			if (Validator.isNotNull(indexSQL.trim())) {
				int pos = indexSQL.indexOf(" on ");

				String indexSpec = indexSQL.substring(pos + 4);

				indexSQLs.put(indexSpec, indexSQL);
			}
		}

		unsyncBufferedReader.close();

		// indexes.properties

		File propsFile = new File(
			_sqlDir + "/" + _sqlIndexesPropertiesFileName);

		if (!propsFile.exists()) {
			FileUtil.write(propsFile, "");
		}

		Map<String, String> indexProps = new TreeMap<String, String>();

		unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(propsFile));

		while (true) {
			String indexMapping = unsyncBufferedReader.readLine();

			if (indexMapping == null) {
				break;
			}

			if (Validator.isNotNull(indexMapping.trim())) {
				String[] splitIndexMapping = indexMapping.split("\\=");

				indexProps.put(splitIndexMapping[1], splitIndexMapping[0]);
			}
		}

		unsyncBufferedReader.close();

		// indexes.sql

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
					List<String> finderColsNames = new ArrayList<String>();

					List<EntityColumn> finderColsList = finder.getColumns();

					for (int k = 0; k < finderColsList.size(); k++) {
						EntityColumn col = finderColsList.get(k);

						finderColsNames.add(col.getDBName());
					}

					IndexMetadata indexMetadata =
						IndexMetadataFactoryUtil.createIndexMetadata(
							finder.isUnique(), entity.getTable(),
							finderColsNames.toArray(
								new String[finderColsNames.size()]));

					indexSQLs.put(
						indexMetadata.getSpecification(),
						indexMetadata.getCreateSQL());

					String finderName =
						entity.getTable() + StringPool.PERIOD +
							finder.getName();

					indexProps.put(finderName, indexMetadata.getIndexName());
				}
			}
		}

		for (Map.Entry<String, EntityMapping> entry :
				_entityMappings.entrySet()) {

			EntityMapping entityMapping = entry.getValue();

			_getCreateMappingTableIndex(entityMapping, indexSQLs, indexProps);
		}

		StringBundler sb = new StringBundler();

		String prevEntityName = null;

		for (String indexSQL : indexSQLs.values()) {
			int pos = indexSQL.indexOf(" on ");

			String indexSQLSuffix = indexSQL.substring(pos + 4);

			String entityName = indexSQLSuffix.split(" ")[0];

			if ((prevEntityName != null) &&
				!prevEntityName.equals(entityName)) {

				sb.append("\n");
			}

			sb.append(indexSQL);
			sb.append("\n");

			prevEntityName = entityName;
		}

		if (!indexSQLs.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		FileUtil.write(sqlFile, sb.toString(), true);

		// indexes.properties

		sb.setIndex(0);

		prevEntityName = null;

		for (String finderName : indexProps.keySet()) {
			String indexName = indexProps.get(finderName);

			String entityName = finderName.split("\\.")[0];

			if ((prevEntityName != null) &&
				!prevEntityName.equals(entityName)) {

				sb.append("\n");
			}

			sb.append(indexName + StringPool.EQUAL + finderName);
			sb.append("\n");

			prevEntityName = entityName;
		}

		if (!indexProps.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		FileUtil.write(propsFile, sb.toString(), true);
	}

	private void _createSQLMappingTables(
			File sqlFile, String newCreateTableString,
			EntityMapping entityMapping, boolean addMissingTables)
		throws IOException {

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
		}

		String content = FileUtil.read(sqlFile);

		int x = content.indexOf(
			_SQL_CREATE_TABLE + entityMapping.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2);

				FileUtil.write(sqlFile, content);
			}
		}
		else if (addMissingTables) {
			StringBundler sb = new StringBundler();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
					x = _SQL_CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entityMapping.getTable()) > 0) {
						sb.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sb.append(line);
				sb.append("\n");
			}

			if (appendNewTable) {
				sb.append("\n" + newCreateTableString);
			}

			unsyncBufferedReader.close();

			FileUtil.write(sqlFile, sb.toString(), true);
		}
	}

	private void _createSQLSequences() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		File sqlFile = new File(_sqlDir + "/" + _sqlSequencesFileName);

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, "");
		}

		Set<String> sequenceSQLs = new TreeSet<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(sqlFile));

		while (true) {
			String sequenceSQL = unsyncBufferedReader.readLine();

			if (sequenceSQL == null) {
				break;
			}

			if (Validator.isNotNull(sequenceSQL)) {
				sequenceSQLs.add(sequenceSQL);
			}
		}

		unsyncBufferedReader.close();

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

					sb.append("create sequence " + sequenceName + ";");

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

		FileUtil.write(sqlFile, sb.toString(), true);
	}

	private void _createSQLTables() throws IOException {
		if (!FileUtil.exists(_sqlDir)) {
			return;
		}

		File sqlFile = new File(_sqlDir + "/" + _sqlFileName);

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
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

				_updateSQLFile(
					"update-6.1.1-6.2.0.sql", createTableSQL, entity);
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

		String content = FileUtil.read(sqlFile);

		FileUtil.write(sqlFile, content.trim());
	}

	private void _createSQLTables(
			File sqlFile, String newCreateTableString, Entity entity,
			boolean addMissingTables)
		throws IOException {

		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
		}

		String content = FileUtil.read(sqlFile);

		int x = content.indexOf(_SQL_CREATE_TABLE + entity.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2);

				FileUtil.write(sqlFile, content);
			}
		}
		else if (addMissingTables) {
			StringBundler sb = new StringBundler();

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (appendNewTable && line.startsWith(_SQL_CREATE_TABLE)) {
					x = _SQL_CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entity.getTable()) > 0) {
						sb.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sb.append(line);
				sb.append("\n");
			}

			if (appendNewTable) {
				sb.append("\n" + newCreateTableString);
			}

			unsyncBufferedReader.close();

			FileUtil.write(sqlFile, sb.toString(), true);
		}
	}

	private String _fixHbmXml(String content) throws IOException {
		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(content));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.startsWith("\t<class name=\"")) {
				line = StringUtil.replace(
					line,
					new String[] {
						".service.persistence.", "HBM\" table=\""
					},
					new String[] {
						".model.", "\" table=\""
					});

				if (!line.contains(".model.impl.") &&
					!line.contains("BlobModel")) {

					line = StringUtil.replace(
						line,
						new String[] {
							".model.", "\" table=\""
						},
						new String[] {
							".model.impl.", "Impl\" table=\""
						});
				}
			}

			sb.append(line);
			sb.append('\n');
		}

		unsyncBufferedReader.close();

		return sb.toString().trim();
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
			sb.append(indentation);
			sb.append(" * @");
			sb.append(tag.getName());
			sb.append(" ");
			sb.append(tag.getValue());
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
		xml = XMLFormatter.toString(xml);
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

		List<JavaField> javaFields = new ArrayList<JavaField>();

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

	private Map<String, Object> _getContext() throws TemplateModelException {
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();

		TemplateHashModel staticModels = wrapper.getStaticModels();

		Map<String, Object> context = new HashMap<String, Object>();

		context.put("hbmFileName", _hbmFileName);
		context.put("ormFileName", _ormFileName);
		context.put("modelHintsFileName", _modelHintsFileName);
		context.put("springFileName", _springFileName);
		context.put("springBaseFileName", _springBaseFileName);
		context.put("springHibernateFileName", _springHibernateFileName);
		context.put(
			"springInfrastructureFileName", _springInfrastructureFileName);
		context.put("apiDir", _apiDir);
		context.put("implDir", _implDir);
		context.put("sqlDir", _sqlDir);
		context.put("sqlFileName", _sqlFileName);
		context.put("beanLocatorUtil", _beanLocatorUtil);
		context.put("beanLocatorUtilShortName", _beanLocatorUtilShortName);
		context.put("propsUtil", _propsUtil);
		context.put("portletName", _portletName);
		context.put("portletShortName", _portletShortName);
		context.put("portletPackageName", _portletPackageName);
		context.put("outputPath", _outputPath);
		context.put("serviceOutputPath", _serviceOutputPath);
		context.put("packagePath", _packagePath);
		context.put("pluginName", _pluginName);
		context.put("author", _author);
		context.put("serviceBuilder", this);

		context.put("arrayUtil", ArrayUtil_IW.getInstance());
		context.put("modelHintsUtil", ModelHintsUtil.getModelHints());
		context.put(
			"resourceActionsUtil", ResourceActionsUtil.getResourceActions());
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("system", staticModels.get("java.lang.System"));
		context.put("tempMap", wrapper.wrap(new HashMap<String, Object>()));
		context.put(
			"textFormatter", staticModels.get(TextFormatter.class.getName()));
		context.put("validator", Validator_IW.getInstance());

		return context;
	}

	private void _getCreateMappingTableIndex(
			EntityMapping entityMapping, Map<String, String> indexSQLs,
			Map<String, String> indexProps)
		throws IOException {

		Entity[] entities = new Entity[2];

		for (int i = 0; i < entities.length; i++) {
			entities[i] = getEntity(entityMapping.getEntity(i));

			if (entities[i] == null) {
				return;
			}
		}

		for (Entity entity : entities) {
			List<EntityColumn> pkList = entity.getPKList();

			for (int j = 0; j < pkList.size(); j++) {
				EntityColumn col = pkList.get(j);

				String colDBName = col.getDBName();

				String indexSpec =
					entityMapping.getTable() + " (" + colDBName + ");";

				String indexHash = StringUtil.toHexString(
					indexSpec.hashCode()).toUpperCase();

				String indexName = "IX_" + indexHash;

				StringBundler sb = new StringBundler();

				sb.append("create index ");
				sb.append(indexName);
				sb.append(" on ");
				sb.append(indexSpec);

				indexSQLs.put(indexSpec, sb.toString());

				String finderName =
					entityMapping.getTable() + StringPool.PERIOD + colDBName;

				indexProps.put(finderName, indexName);
			}
		}
	}

	private String _getCreateMappingTableSQL(EntityMapping entityMapping)
		throws IOException {

		Entity[] entities = new Entity[2];

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

				sb.append("\t" + col.getDBName());
				sb.append(" ");

				if (colType.equalsIgnoreCase("boolean")) {
					sb.append("BOOLEAN");
				}
				else if (colType.equalsIgnoreCase("double") ||
						 colType.equalsIgnoreCase("float")) {

					sb.append("DOUBLE");
				}
				else if (colType.equals("int") ||
						 colType.equals("Integer") ||
						 colType.equalsIgnoreCase("short")) {

					sb.append("INTEGER");
				}
				else if (colType.equalsIgnoreCase("long")) {
					sb.append("LONG");
				}
				else if (colType.equals("String")) {
					Map<String, String> hints = ModelHintsUtil.getHints(
						_packagePath + ".model." + entity.getName(), colName);

					int maxLength = 75;

					if (hints != null) {
						maxLength = GetterUtil.getInteger(
							hints.get("max-length"), maxLength);
					}

					if (col.isLocalized()) {
						maxLength = 4000;
					}

					if (maxLength < 4000) {
						sb.append("VARCHAR(" + maxLength + ")");
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
				else if (colType.equals("Date") || colType.equals("String")) {
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

		if (regularColList.size() == 0) {
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

			sb.append("\t" + col.getDBName());
			sb.append(" ");

			if (colType.equalsIgnoreCase("boolean")) {
				sb.append("BOOLEAN");
			}
			else if (colType.equalsIgnoreCase("double") ||
					 colType.equalsIgnoreCase("float")) {

				sb.append("DOUBLE");
			}
			else if (colType.equals("int") ||
					 colType.equals("Integer") ||
					 colType.equalsIgnoreCase("short")) {

				sb.append("INTEGER");
			}
			else if (colType.equalsIgnoreCase("long")) {
				sb.append("LONG");
			}
			else if (colType.equals("Blob")) {
				sb.append("BLOB");
			}
			else if (colType.equals("Date")) {
				sb.append("DATE");
			}
			else if (colType.equals("String")) {
				Map<String, String> hints = ModelHintsUtil.getHints(
					_packagePath + ".model." + entity.getName(), colName);

				int maxLength = 75;

				if (hints != null) {
					maxLength = GetterUtil.getInteger(
						hints.get("max-length"), maxLength);
				}

				if (col.isLocalized()) {
					maxLength = 4000;
				}

				if (maxLength < 4000) {
					sb.append("VARCHAR(" + maxLength + ")");
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
			else if (colType.equals("Date") || colType.equals("String")) {
				sb.append(" null");
			}

			if (Validator.isNotNull(colIdType) &&
				colIdType.equals("identity")) {

				sb.append(" IDENTITY");
			}

			if (((i + 1) != regularColList.size()) ||
				entity.hasCompoundPK()) {

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

	private JavaClass _getJavaClass(String fileName) throws IOException {
		int pos = fileName.indexOf(_implDir + "/");

		if (pos != -1) {
			pos += _implDir.length();
		}
		else {
			pos = fileName.indexOf(_apiDir + "/") + _apiDir.length();
		}

		String srcFile = fileName.substring(pos + 1);
		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaClass javaClass = _javaClasses.get(className);

		if (javaClass == null) {
			ClassLibrary classLibrary = new ClassLibrary();

			classLibrary.addClassLoader(getClass().getClassLoader());

			JavaDocBuilder builder = new JavaDocBuilder(classLibrary);

			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}

			builder.addSource(file);

			javaClass = builder.getClassByName(className);

			_javaClasses.put(className, javaClass);
		}

		return javaClass;
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

		String content = FileUtil.read(modelFile);

		Matcher matcher = _getterPattern.matcher(content);

		Set<String> getters = new HashSet<String>();

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

		Set<String> setters = new HashSet<String>();

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

		List<String> transients = new ArrayList<String>(getters);

		Collections.sort(transients);

		return transients;
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

	private List<Entity> _mergeReferenceList(List<Entity> referenceList) {
		List<Entity> list = new ArrayList<Entity>(
			_ejbList.size() + referenceList.size());

		list.addAll(_ejbList);
		list.addAll(referenceList);

		return list;
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
			_packagePath + ".service.persistence." + ejbName +
				"PersistenceImpl");

		String finderClass = "";

		if (FileUtil.exists(
				_outputPath + "/service/persistence/" + ejbName +
					"FinderImpl.java")) {

			finderClass =
				_packagePath + ".service.persistence." + ejbName + "FinderImpl";
		}

		String dataSource = entityElement.attributeValue("data-source");
		String sessionFactory = entityElement.attributeValue("session-factory");
		String txManager = entityElement.attributeValue("tx-manager");
		boolean cacheEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("cache-enabled"), true);
		boolean jsonEnabled = GetterUtil.getBoolean(
			entityElement.attributeValue("json-enabled"), remoteService);

		List<EntityColumn> pkList = new ArrayList<EntityColumn>();
		List<EntityColumn> regularColList = new ArrayList<EntityColumn>();
		List<EntityColumn> blobList = new ArrayList<EntityColumn>();
		List<EntityColumn> collectionList = new ArrayList<EntityColumn>();
		List<EntityColumn> columnList = new ArrayList<EntityColumn>();

		List<Element> columnElements = entityElement.elements("column");

		boolean permissionedModel = false;

		if (uuid) {
			Element columnElement = SAXReaderUtil.createElement("column");

			columnElement.addAttribute("name", "uuid");
			columnElement.addAttribute("type", "String");

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

				int ejbNameWeight = StringUtil.startsWithWeight(
					mappingTable, ejbName);
				int collectionEntityWeight = StringUtil.startsWithWeight(
					mappingTable, collectionEntity);

				if ((ejbNameWeight > collectionEntityWeight) ||
					((ejbNameWeight == collectionEntityWeight) &&
					 (ejbName.compareTo(collectionEntity) > 0))) {

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

			List<EntityColumn> orderColsList = new ArrayList<EntityColumn>();

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

		List<EntityFinder> finderList = new ArrayList<EntityFinder>();

		List<Element> finderElements = entityElement.elements("finder");

		if (uuid) {
			if (columnList.contains(new EntityColumn("companyId"))) {
				Element finderElement = SAXReaderUtil.createElement("finder");

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
				Element finderElement = SAXReaderUtil.createElement("finder");

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

			Element finderElement = SAXReaderUtil.createElement("finder");

			finderElement.addAttribute("name", "Uuid");
			finderElement.addAttribute("return-type", "Collection");

			Element finderColumnElement = finderElement.addElement(
				"finder-column");

			finderColumnElement.addAttribute("name", "uuid");

			finderElements.add(0, finderElement);
		}

		if (permissionedModel) {
			Element finderElement = SAXReaderUtil.createElement("finder");

			finderElement.addAttribute("name", "ResourceBlockId");
			finderElement.addAttribute("return-type", "Collection");

			Element finderColumnElement = finderElement.addElement(
				"finder-column");

			finderColumnElement.addAttribute("name", "resourceBlockId");

			finderElements.add(0, finderElement);
		}

		String alias = TextFormatter.format(ejbName, TextFormatter.I);

		if (_badAliasNames.contains(alias.toLowerCase())) {
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

					if (finderWhere.contains(name)) {
						finderWhere = finderWhere.replaceAll(
							name, alias + "." + name);
					}
				}
			}

			boolean finderDBIndex = GetterUtil.getBoolean(
				finderElement.attributeValue("db-index"), true);

			List<EntityColumn> finderColsList = new ArrayList<EntityColumn>();

			List<Element> finderColumnElements = finderElement.elements(
				"finder-column");

			for (Element finderColumnElement : finderColumnElements) {
				String finderColName = finderColumnElement.attributeValue(
					"name");
				boolean finderColCaseSensitive = GetterUtil.getBoolean(
					finderColumnElement.attributeValue("case-sensitive"), true);
				String finderColComparator = GetterUtil.getString(
					finderColumnElement.attributeValue("comparator"), "=");
				String finderColArrayableOperator =
					GetterUtil.getString(
						finderColumnElement.attributeValue(
						"arrayable-operator"));

				EntityColumn col = Entity.getColumn(finderColName, columnList);

				if (!col.isFinderPath()) {
					col.setFinderPath(true);
				}

				col = (EntityColumn)col.clone();

				col.setCaseSensitive(finderColCaseSensitive);
				col.setComparator(finderColComparator);
				col.setArrayableOperator(finderColArrayableOperator);

				finderColsList.add(col);
			}

			finderList.add(
				new EntityFinder(
					finderName, finderReturn, finderUnique, finderWhere,
					finderDBIndex, finderColsList));
		}

		List<Entity> referenceList = new ArrayList<Entity>();

		if (_build) {
			if (Validator.isNotNull(_pluginName)) {
				for (String config : PropsValues.RESOURCE_ACTIONS_CONFIGS) {
					File file = new File(_implDir + "/" + config);

					if (file.exists()) {
						InputStream inputStream = new FileInputStream(file);

						ResourceActionsUtil.read(_pluginName, inputStream);
					}
				}
			}

			List<Element> referenceElements = entityElement.elements(
				"reference");

			Set<String> referenceSet = new TreeSet<String>();

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

			if (!_packagePath.equals("com.liferay.portal")) {
				referenceSet.add("com.liferay.portal.Resource");
				referenceSet.add("com.liferay.portal.User");
			}

			for (String referenceName : referenceSet) {
				referenceList.add(getEntity(referenceName));
			}
		}

		List<String> txRequiredList = new ArrayList<String>();

		List<Element> txRequiredElements = entityElement.elements(
			"tx-required");

		for (Element txRequiredEl : txRequiredElements) {
			String txRequired = txRequiredEl.getText();

			txRequiredList.add(txRequired);
		}

		_ejbList.add(
			new Entity(
				_packagePath, _portletName, _portletShortName, ejbName,
				humanName, table, alias, uuid, uuidAccessor, localService,
				remoteService, persistenceClass, finderClass, dataSource,
				sessionFactory, txManager, cacheEnabled, jsonEnabled, pkList,
				regularColList, blobList, collectionList, columnList, order,
				finderList, referenceList, txRequiredList));
	}

	private String _processTemplate(String name) throws Exception {
		return _processTemplate(name, _getContext());
	}

	private String _processTemplate(String name, Map<String, Object> context)
		throws Exception {

		return StringUtil.strip(FreeMarkerUtil.process(name, context), '\r');
	}

	private Set<String> _readLines(String fileName) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();

		Set<String> lines = new HashSet<String>();

		StringUtil.readLines(classLoader.getResourceAsStream(fileName), lines);

		return lines;
	}

	private void _updateSQLFile(
			String sqlFileName, String createTableSQL, Entity entity)
		throws IOException {

		File updateSQLFile = new File(_sqlDir + "/" + sqlFileName);

		if (updateSQLFile.exists()) {
			_createSQLTables(updateSQLFile, createTableSQL, entity, false);
		}
	}

	private static final int _SESSION_TYPE_LOCAL = 1;

	private static final int _SESSION_TYPE_REMOTE = 0;

	private static final String _SQL_CREATE_TABLE = "create table ";

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/servicebuilder/dependencies/";

	private static Pattern _getterPattern = Pattern.compile(
		"public .* get.*" + Pattern.quote("(") + "|public boolean is.*" +
			Pattern.quote("("));

	private static Pattern _setterPattern = Pattern.compile(
		"public void set.*" + Pattern.quote("("));

	private String _apiDir;
	private String _author;
	private boolean _autoNamespaceTables;
	private Set<String> _badAliasNames;
	private Set<String> _badColumnNames;
	private Set<String> _badTableNames;
	private String _beanLocatorUtil;
	private String _beanLocatorUtilShortName;
	private boolean _build;
	private long _buildNumber;
	private boolean _buildNumberIncrement;
	private List<Entity> _ejbList;
	private Map<String, EntityMapping> _entityMappings;
	private Map<String, Entity> _entityPool = new HashMap<String, Entity>();
	private String _hbmFileName;
	private String _implDir;
	private Map<String, JavaClass> _javaClasses =
		new HashMap<String, JavaClass>();
	private String _modelHintsFileName;
	private String _ormFileName;
	private String _outputPath;
	private String _packagePath;
	private String _pluginName;
	private String _portletName = StringPool.BLANK;
	private String _portletPackageName = StringPool.BLANK;
	private String _portletShortName = StringPool.BLANK;
	private String _propsUtil;
	private String _remotingFileName;
	private String _serviceOutputPath;
	private String _springBaseFileName;
	private String _springClusterFileName;
	private String _springDynamicDataSourceFileName;
	private String _springFileName;
	private String _springHibernateFileName;
	private String _springInfrastructureFileName;
	private String _springShardDataSourceFileName;
	private String _sqlDir;
	private String _sqlFileName;
	private String _sqlIndexesFileName;
	private String _sqlIndexesPropertiesFileName;
	private String _sqlSequencesFileName;
	private String _targetEntityName;
	private String _testDir;
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
	private String _tplOrmXml = _TPL_ROOT + "orm_xml.ftl";
	private String _tplPersistence = _TPL_ROOT + "persistence.ftl";
	private String _tplPersistenceImpl = _TPL_ROOT + "persistence_impl.ftl";
	private String _tplPersistenceTest = _TPL_ROOT + "persistence_test.ftl";
	private String _tplPersistenceUtil = _TPL_ROOT + "persistence_util.ftl";
	private String _tplProps = _TPL_ROOT + "props.ftl";
	private String _tplRemotingXml = _TPL_ROOT + "remoting_xml.ftl";
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
	private String _tplServiceSoap = _TPL_ROOT + "service_soap.ftl";
	private String _tplServiceUtil = _TPL_ROOT + "service_util.ftl";
	private String _tplServiceWrapper = _TPL_ROOT + "service_wrapper.ftl";
	private String _tplSpringBaseXml = _TPL_ROOT + "spring_base_xml.ftl";
	private String _tplSpringClusterXml = _TPL_ROOT + "spring_cluster_xml.ftl";
	private String _tplSpringHibernateXml =
		_TPL_ROOT + "spring_hibernate_xml.ftl";
	private String _tplSpringInfrastructureXml =
		_TPL_ROOT + "spring_infrastructure_xml.ftl";
	private String _tplSpringShardDataSourceXml =
		_TPL_ROOT + "spring_shard_data_source_xml.ftl";
	private String _tplSpringXml = _TPL_ROOT + "spring_xml.ftl";

}