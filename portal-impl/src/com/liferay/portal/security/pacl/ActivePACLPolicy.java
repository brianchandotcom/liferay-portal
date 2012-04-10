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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import edu.emory.mathcs.backport.java.util.Collections;

import java.io.FilePermission;
import java.io.*;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.security.Permission;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class ActivePACLPolicy extends BasePACLPolicy {

	public ActivePACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		super(servletContextName, classLoader, properties);

		_rootDir = WebDirDetector.getRootDir(classLoader);

		initFiles();
		initHookLanguagePropertiesLocales();
		initHookPortalPropertiesKeys();
		initHookServices();
		initServices();
		initSocketConnectHostsAndPorts();
		initSocketListenPorts();
		initSQLStatements();
	}

	public boolean hasDynamicQuery(Class<?> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == this) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}

		return false;
	}

	public boolean hasFileDelete(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_DELETE);

		for (Permission deleteFilePermission : _deleteFilePermissions) {
			if (deleteFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileExecute(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_EXECUTE);

		for (Permission executeFilePermission : _executeFilePermissions) {
			if (executeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileRead(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_READ);

		for (Permission readFilePermission : _readFilePermissions) {
			if (readFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasFileWrite(String fileName) {
		Permission permission = new FilePermission(
			fileName, _FILE_PERMISSION_WRITE);

		for (Permission writeFilePermission : _writeFilePermissions) {
			if (writeFilePermission.implies(permission)) {
				return true;
			}
		}

		return false;
	}

	public boolean hasHookLanguagePropertiesLocale(Locale locale) {
		if (_hookLanguagePropertiesLanguageIds.contains(locale.getLanguage())) {
			return true;
		}

		if (_hookLanguagePropertiesLanguageIds.contains(
				locale.getLanguage() + "_" + locale.getCountry())) {

			return true;
		}

		return false;
	}

	public boolean hasHookPortalPropertiesKey(String key) {
		return _hookPortalPropertiesKeys.contains(key);
	}

	public boolean hasHookService(String className) {
		return _hookServices.contains(className);
	}

	public boolean hasSQLStatement(String sql) {
		String normalizedSQL = sql.trim().toLowerCase();

		if(normalizedSQL.charAt(0) == 'i'){
			String tableName = normalizedSQL.substring(12, normalizedSQL.indexOf(" ", 12));
			return isTableAllowed(SQL_STATEMENT_TYPE.INSERT, tableName);
		}
		if(normalizedSQL.charAt(0) == 'u'){
			String tableName = normalizedSQL.substring(7, normalizedSQL.indexOf(" ", 7));
			return isTableAllowed(SQL_STATEMENT_TYPE.UPDATE, tableName);
		}
		if(normalizedSQL.charAt(0) == 'd'){
			String tableName = normalizedSQL.substring(12, normalizedSQL.indexOf(" ", 12));
			return isTableAllowed(SQL_STATEMENT_TYPE.DELETE, tableName);
		}

		if(normalizedSQL.charAt(0) == 's'){

			int fromIdx = normalizedSQL.indexOf(" from ");
			int whereIdx = normalizedSQL.indexOf(" where ", fromIdx);

			String tablesDefinition = normalizedSQL.substring(fromIdx + 6, whereIdx > 0 ? whereIdx : normalizedSQL.length());
			String[] tablesDefinitionParts = StringUtil.split(tablesDefinition, " join ");

			if(tablesDefinition.length() > 0){
				String fromDefinition = tablesDefinitionParts[0];
				String[] fromTables = StringUtil.split(fromDefinition);

				for(String table : fromTables){
					String tableNormalized = table.trim();
					int spaceIdx = tableNormalized.indexOf(" ");
					String tableName = spaceIdx > -1 ? tableNormalized.substring(0, spaceIdx) : tableNormalized;
					if(!isTableAllowed(SQL_STATEMENT_TYPE.SELECT, tableName)){
						return false;
					}
				}

				for (int i = 1; i < tablesDefinitionParts.length; i++) {
					String tableNormalized = tablesDefinitionParts[i].trim();
					int spaceIdx = tableNormalized.indexOf(" ");
					String tableName = spaceIdx > -1 ? tableNormalized.substring(0, spaceIdx) : tableNormalized;
					if(!isTableAllowed(SQL_STATEMENT_TYPE.SELECT, tableName)){
						return false;
					}
				}
			}

			return true;
		}

		return false;
	}

	public boolean hasService(Object object, Method method) {
		Class<?> clazz = object.getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		PACLPolicy paclPolicy = PACLPolicyManager.getPACLPolicy(classLoader);

		if (paclPolicy == this) {
			return true;
		}

		Set<String> services = getServices(paclPolicy);

		String className = getInterfaceName(clazz.getName());

		if (services.contains(className)) {
			return true;
		}

		String methodName = method.getName();

		if (services.contains(
				className.concat(StringPool.POUND).concat(methodName))) {

			return true;
		}

		return false;
	}

	public boolean hasSocketConnect(String host, int port) {
		Set<Integer> ports = _socketConnectHostsAndPorts.get(host);

		if (ports == null) {
			return false;
		}

		return ports.contains(port);
	}

	public boolean hasSocketListen(int port) {
		return _socketListenPorts.contains(port);
	}

	public boolean isActive() {
		return true;
	}

	protected void addFilePermission(
		List<Permission> permissions, String path, String action) {

		if (_log.isDebugEnabled()) {
			_log.debug("Allowing " + action + " on " + path);
		}

		Permission permission = new FilePermission(path, action);

		permissions.add(permission);
	}

	protected List<Permission> getFilePermissions(String key, String action) {
		List<Permission> permissions = new CopyOnWriteArrayList<Permission>();

		String value = getProperty(key);

		String[] paths = StringUtil.split(value);

		if (value.contains("${comma}")) {
			for (int i = 0; i < paths.length; i++) {
				paths[i] = StringUtil.replace(
					paths[i], "${comma}", StringPool.COMMA);
			}
		}

		for (String path : paths) {
			addFilePermission(permissions, path, action);
		}

		if (!action.equals(_FILE_PERMISSION_READ)) {
			return permissions;
		}

		String catalinaHome = System.getProperty("catalina.home") + "/";

		String portalWebDir = PortalUtil.getPortalWebDir();

		String[] defaultPaths = {

			// Plugin

			_rootDir + "-",

			// Plugin runtime

			catalinaHome + "work/Catalina/localhost/" +
				getServletContextName() + "/-",

			// Portal

			portalWebDir + "html/common/-", portalWebDir + "html/taglib/-",
			portalWebDir + "localhost/html/taglib/-",

			// Portal runtime

			portalWebDir + "WEB-INF/classes/org/apache/jasper/-",
			portalWebDir + "WEB-INF/classes/org/apache/tomcat/-",
			portalWebDir +
				"WEB-INF/classes/services/javax.el.ExpressionFactory",
			catalinaHome + "work/Catalina/localhost/_",
			catalinaHome + "work/Catalina/localhost/_/-"
		};

		for (String defaultPath : defaultPaths) {
			addFilePermission(permissions, defaultPath, action);
		}

		return permissions;
	}

	protected String getInterfaceName(String className) {
		int pos = className.indexOf(".impl.");

		if (pos != -1) {
			className =
				className.substring(0, pos + 1) + className.substring(pos + 6);
		}

		if (className.endsWith("Impl")) {
			className = className.substring(0, className.length() - 4);
		}

		return className;
	}

	protected Set<String> getServices(PACLPolicy paclPolicy) {
		Set<String> services = null;

		if (paclPolicy == null) {
			services = _portalServices;
		}
		else {
			services = _pluginServices.get(paclPolicy.getServletContextName());
		}

		return services;
	}

	protected boolean isTableAllowed(SQL_STATEMENT_TYPE type, String tableName){
		for(String allowedTables : _databaseTables.get(type)){
			if(allowedTables.equals(tableName)){
				return true;
			}
		}
		return false;
	}

	protected void initFiles() {
		_deleteFilePermissions = getFilePermissions(
			"security-manager-files-delete", _FILE_PERMISSION_DELETE);
		_executeFilePermissions = getFilePermissions(
			"security-manager-files-execute", _FILE_PERMISSION_EXECUTE);
		_readFilePermissions = getFilePermissions(
			"security-manager-files-read", _FILE_PERMISSION_READ);
		_writeFilePermissions = getFilePermissions(
			"security-manager-files-write", _FILE_PERMISSION_WRITE);
	}

	protected void initHookLanguagePropertiesLocales() {
		_hookLanguagePropertiesLanguageIds = getPropertySet(
			"security-manager-hook-language-properties-locales");

		if (_log.isDebugEnabled()) {
			Set<String> languageIds = new TreeSet<String>(
				_hookLanguagePropertiesLanguageIds);

			for (String languageId : languageIds) {
				_log.debug("Allowing language " + languageId);
			}
		}
	}

	protected void initHookPortalPropertiesKeys() {
		_hookPortalPropertiesKeys = getPropertySet(
			"security-manager-hook-portal-properties-keys");

		if (_log.isDebugEnabled()) {
			Set<String> keys = new TreeSet<String>(_hookPortalPropertiesKeys);

			for (String key : keys) {
				_log.debug("Allowing portal.properties key " + key);
			}
		}
	}

	protected void initHookServices() {
		_hookServices = getPropertySet("security-manager-hook-services");
	}

	protected void initServices() {
		Properties properties = getProperties();

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (!key.startsWith("security-manager-services[")) {
				continue;
			}

			int x = key.indexOf("[");
			int y = key.indexOf("]", x);

			String servicesServletContextName = key.substring(x + 1, y);

			Set<String> services = SetUtil.fromArray(StringUtil.split(value));

			if (servicesServletContextName.equals(
					_PORTAL_SERVLET_CONTEXT_NAME)) {

				_portalServices = services;
			}
			else {
				_pluginServices.put(servicesServletContextName, services);
			}
		}
	}

	protected void initSocketConnectHostsAndPorts() {
		String[] socketConnectParts = getPropertyArray(
			"security-manager-sockets-connect");

		for (String socketConnectPart : socketConnectParts) {
			initSocketConnectHostsAndPorts(socketConnectPart);
		}
	}

	protected void initSocketConnectHostsAndPorts(String socketConnectPart) {
		int pos = socketConnectPart.indexOf(StringPool.COLON);

		if (pos == -1) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine socket connect host and port from " +
						socketConnectPart +
							" because it is missing a colon delimeter");
			}

			return;
		}

		String host = socketConnectPart.substring(0, pos);

		if (!Validator.isDomain(host)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket connect host " + host + " is not a valid domain");
			}

			return;
		}

		String portString = socketConnectPart.substring(pos + 1);

		int port = GetterUtil.getInteger(portString);

		if (port <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Socket connect port " + portString +
						" is less than or equal to 0");
			}

			return;
		}

		InetAddress[] inetAddresses = null;

		try {
			inetAddresses = InetAddress.getAllByName(host);
		}
		catch (UnknownHostException uhe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to resolve host " + host);
			}

			return;
		}

		for (InetAddress inetAddress : inetAddresses) {
			Set<Integer> ports = _socketConnectHostsAndPorts.get(
				inetAddress.getHostAddress());

			if (ports == null) {
				ports = new HashSet<Integer>();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Allowing socket connect host " + host + " with IP " +
							inetAddress.getHostAddress() + " on port " + port);
				}

				_socketConnectHostsAndPorts.put(
					inetAddress.getHostAddress(), ports);
			}

			ports.add(port);
		}
	}

	protected void initSocketListenPorts() {
		String[] socketListenParts = getPropertyArray(
			"security-manager-sockets-listen");

		for (String socketListenPart : socketListenParts) {
			initSocketListenPorts(socketListenPart);
		}
	}

	protected void initSocketListenPorts(String socketListenPart) {
		int pos = socketListenPart.indexOf(StringPool.DASH);

		if (pos == -1) {
			if (!Validator.isNumber(socketListenPart)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + socketListenPart +
							" is not a number");
				}

				return;
			}

			int port = GetterUtil.getInteger(socketListenPart);

			if (_log.isDebugEnabled()) {
				_log.debug("Allowing socket listen port " + port);
			}

			_socketListenPorts.add(port);
		}
		else {
			String portString1 = socketListenPart.substring(0, pos);
			String portString2 = socketListenPart.substring(pos + 1);

			if (!Validator.isNumber(portString1)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + portString1 +
							" is not a number");
				}

				return;
			}

			if (!Validator.isNumber(portString2)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port " + portString2 +
							" is not a number");
				}

				return;
			}

			int port1 = GetterUtil.getInteger(portString1);
			int port2 = GetterUtil.getInteger(portString2);

			if (port1 >= port2) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Socket listen port range " + socketListenPart +
							" is invalid");
				}

				return;
			}

			for (int i = port1; i <= port2; i++) {
				if (_log.isDebugEnabled()) {
					_log.debug("Allowing socket listen port " + i);
				}

				_socketListenPorts.add(i);
			}
		}
	}

	protected void initSQLStatements(){
		Properties properties = getProperties();
		for(SQL_STATEMENT_TYPE type : SQL_STATEMENT_TYPE.values()){
			_databaseTables.put(type, new HashSet<String>());
		}

		initDBTables(SQL_STATEMENT_TYPE.INSERT,
				properties.getProperty(
						"security-manager-db-tables-insert"));
		initDBTables(SQL_STATEMENT_TYPE.SELECT,
				properties.getProperty(
						"security-manager-db-tables-select"));
		initDBTables(SQL_STATEMENT_TYPE.UPDATE,
				properties.getProperty(
						"security-manager-db-tables-update"));
		initDBTables(SQL_STATEMENT_TYPE.DELETE,
				properties.getProperty(
						"security-manager-db-tables-delete"));

		InputStream is = getClassLoader().getResourceAsStream("/META-INF/portlet-hbm.xml");
		Pattern tablePattern = Pattern.compile("^.*class .* table=\"([^\"]+)\".*$");
		try {
			if(is != null){
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = br.readLine();
				while(line != null){
					Matcher m = tablePattern.matcher(line);
					if(m.matches()){
						String tableName = m.group(1);
						// TODO: we need to be aware of portal tables that can be referenced from the portlet hibernate also (which is not common but can happen). In this case we should not allow this automatic addition and force user to specify permissions for these portal tables explicitly in the properties file
						initDBTables(SQL_STATEMENT_TYPE.INSERT, tableName);
						initDBTables(SQL_STATEMENT_TYPE.SELECT, tableName);
						initDBTables(SQL_STATEMENT_TYPE.UPDATE, tableName);
						initDBTables(SQL_STATEMENT_TYPE.DELETE, tableName);
					}
					line = br.readLine();
				}
			}
		} catch(Exception ex) {
			_log.error(ex);
		} finally {
			if(is!=null){
				try {
					is.close();
				} catch(IOException ex){
					_log.error(ex);
				}
			}
		}

	}

	protected void initDBTables(SQL_STATEMENT_TYPE type, String tables){
		if(tables != null && tables.length() > 0){
			String[] tableParts = StringUtil.split(tables);
			for (String table : tableParts) {
				if(table.length() > 0){
					if(_log.isDebugEnabled()){
						_log.debug("Allowing SQL operation " + type + " on " + table);
					}
					Set<String> dbTable = _databaseTables.get(type);
					dbTable.add(table.toLowerCase().trim());
				}
			}
		}
	}

	private static final String _FILE_PERMISSION_DELETE = "delete";

	private static final String _FILE_PERMISSION_EXECUTE = "execute";

	private static final String _FILE_PERMISSION_READ = "read";

	private static final String _FILE_PERMISSION_WRITE = "write";

	private static final String _PORTAL_SERVLET_CONTEXT_NAME = "portal";

	private enum SQL_STATEMENT_TYPE {
		INSERT, UPDATE, DELETE, SELECT
	}

	private static Log _log = LogFactoryUtil.getLog(ActivePACLPolicy.class);

	private List<Permission> _deleteFilePermissions;
	private List<Permission> _executeFilePermissions;
	private Set<String> _hookLanguagePropertiesLanguageIds =
		Collections.emptySet();
	private Set<String> _hookPortalPropertiesKeys = Collections.emptySet();
	private Set<String> _hookServices = Collections.emptySet();
	private Map<String, Set<String>> _pluginServices =
		new HashMap<String, Set<String>>();
	private Set<String> _portalServices;
	private List<Permission> _readFilePermissions;
	private String _rootDir;
	private Map<String, Set<Integer>> _socketConnectHostsAndPorts =
		new HashMap<String, Set<Integer>>();
	private Set<Integer> _socketListenPorts = new HashSet<Integer>();
	private Map<SQL_STATEMENT_TYPE, Set<String>> _databaseTables = new HashMap<SQL_STATEMENT_TYPE, Set<String>>();
	private List<Permission> _writeFilePermissions;

}
