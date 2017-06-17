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

package com.liferay.portal.tools.db.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Cleans the Liferay database from the Service Builder tables and rows of a module.",
	commandNames = "clean-service-builder"
)
public class CleanServiceBuilderCommand extends BaseCommand {

	public void setServiceXmlFile(File serviceXmlFile) {
		_serviceXmlFile = serviceXmlFile;
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;
	}

	@Override
	protected void execute(Connection connection) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(_serviceXmlFile);

		Element serviceBuilderElement = document.getDocumentElement();

		boolean autoNamespaceTables = true;

		String autoNamespaceTablesString = serviceBuilderElement.getAttribute(
			"auto-namespace-tables");

		if ((autoNamespaceTablesString != null) &&
			!autoNamespaceTablesString.isEmpty()) {

			autoNamespaceTables = Boolean.parseBoolean(
				autoNamespaceTablesString);
		}

		NodeList namespaceNodeList = serviceBuilderElement.getElementsByTagName(
			"namespace");

		if (namespaceNodeList.getLength() != 1) {
			throw new IllegalArgumentException(
				"Unable to get namespace from " + _serviceXmlFile);
		}

		Element namespaceElement = (Element)namespaceNodeList.item(0);

		String namespace = namespaceElement.getTextContent();

		NodeList entityNodeList = document.getElementsByTagName("entity");

		for (int i = 0; i < entityNodeList.getLength(); i++) {
			Element entityElement = (Element)entityNodeList.item(i);

			String tableName = entityElement.getAttribute("table");

			if ((tableName == null) || tableName.isEmpty()) {
				String entityName = entityElement.getAttribute("name");

				tableName = entityName;

				if (_badTableNames.contains(tableName)) {
					tableName += '_';
				}

				if (autoNamespaceTables) {
					tableName = namespace + "_" + entityName;
				}
			}

			_dropTable(connection, tableName);

			if (_hasLocalizationTable(entityElement)) {
				_dropTable(connection, tableName + "Localization");
			}
		}

		_deleteReleaseRows(connection);
		_deleteServiceComponentRows(connection, namespace);
	}

	private void _deleteReleaseRows(Connection connection) throws SQLException {
		String sql = "delete from Release_ where servletContextName = ?";

		try (PreparedStatement preparedStatement =
				connection.prepareStatement(sql)) {

			preparedStatement.setString(1, _servletContextName);

			preparedStatement.executeUpdate();
		}
	}

	private void _deleteServiceComponentRows(
			Connection connection, String namespace)
		throws SQLException {

		String sql = "delete from ServiceComponent where buildNamespace = ?";

		try (PreparedStatement preparedStatement =
				connection.prepareStatement(sql)) {

			preparedStatement.setString(1, namespace);

			preparedStatement.executeUpdate();
		}
	}

	private void _dropTable(Connection connection, String tableName)
		throws SQLException {

		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
		}
	}

	private boolean _hasLocalizationTable(Element entityElement) {
		NodeList columnNodeList = entityElement.getElementsByTagName("column");

		for (int i = 0; i < columnNodeList.getLength(); i++) {
			Element columnElement = (Element)columnNodeList.item(i);

			String localized = columnElement.getAttribute("localized");

			if ("extra-table".equals(localized)) {
				return true;
			}
		}

		return false;
	}

	private static final Set<String> _badTableNames = new HashSet<>();

	static {
		ClassLoader classLoader =
			CleanServiceBuilderCommand.class.getClassLoader();

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(
					classLoader.getResourceAsStream(
						"com/liferay/portal/tools/service/builder" +
							"/dependencies/bad_table_names.txt"),
					StandardCharsets.UTF_8))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				_badTableNames.add(line);
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	@Parameter(
		converter = FileConverter.class, description = "The service.xml file.",
		names = {"-x", "--service-xml-file"}, required = true
	)
	private File _serviceXmlFile;

	@Parameter(
		description = "The servlet context name (usually the value of the \"Bundle-Symbolic-Name\" manifest header) of the module.",
		names = {"-o", "--servlet-context-name"}, required = true
	)
	private String _servletContextName;

}