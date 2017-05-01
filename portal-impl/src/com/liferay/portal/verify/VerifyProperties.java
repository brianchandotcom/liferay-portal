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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyProperties extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifySystemProperties();

		verifyPortalProperties();

		verifyDocumentLibrary();
	}

	protected InputStream getPropertiesResourceAsStream(String resourceName)
		throws FileNotFoundException {

		File propertyFile = new File(resourceName);

		if (propertyFile.exists()) {
			return new FileInputStream(propertyFile);
		}

		ClassLoader classLoader = VerifyProperties.class.getClassLoader();

		return classLoader.getResourceAsStream(resourceName);
	}

	protected Properties loadPortalProperties() {
		Properties properties = new Properties();

		List<String> propertiesResourceNames = ListUtil.fromArray(
			PropsUtil.getArray("include-and-override"));

		propertiesResourceNames.add(0, "portal.properties");

		for (String propertyResourceName : propertiesResourceNames) {
			try (InputStream inputStream = getPropertiesResourceAsStream(
					propertyResourceName)) {

				if (inputStream != null) {
					properties.load(inputStream);
				}
			}
			catch (IOException ioe) {
				_log.error(
					"Unable to load property " + propertyResourceName, ioe);
			}
		}

		return properties;
	}

	protected void verifyDocumentLibrary() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StoreFactory storeFactory = StoreFactory.getInstance();

			storeFactory.checkProperties();
		}
	}

	protected void verifyMigratedPortalProperty(
			Properties portalProperties, String oldKey, String newKey)
		throws Exception {

		if (portalProperties.containsKey(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey +
					"\" was migrated to the system property \"" + newKey +
						"\"");
		}
	}

	protected void verifyMigratedSystemProperty(String oldKey, String newKey)
		throws Exception {

		String value = SystemProperties.get(oldKey);

		if (value != null) {
			_log.error(
				"System property \"" + oldKey +
					"\" was migrated to the portal property \"" + newKey +
						"\"");
		}
	}

	protected void verifyModularizedPortalProperty(
			Properties portalProperties, String oldKey, String newKey,
			String moduleName)
		throws Exception {

		if (portalProperties.containsKey(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey + "\" was modularized to " +
					moduleName + " as \"" + newKey + "\"");
		}
	}

	protected void verifyModularizedSystemProperty(
			Properties systemProperties, String oldKey, String newKey,
			String moduleName)
		throws Exception {

		if (systemProperties.containsKey(oldKey)) {
			_log.error(
				"System property \"" + oldKey + "\" was modularized to " +
					moduleName + " as \"" + newKey + "\"");
		}
	}

	protected void verifyObsoletePortalProperty(
			Properties portalProperties, String key)
		throws Exception {

		if (portalProperties.containsKey(key)) {
			_log.error("Portal property \"" + key + "\" is obsolete");
		}
	}

	protected void verifyObsoleteSystemProperty(String key) throws Exception {
		String value = SystemProperties.get(key);

		if (value != null) {
			_log.error("System property \"" + key + "\" is obsolete");
		}
	}

	protected void verifyPortalProperties() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Properties portalProperties = loadPortalProperties();

			for (String[] keys : _MIGRATED_PORTAL_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];

				verifyMigratedPortalProperty(portalProperties, oldKey, newKey);
			}

			for (String[] keys : _RENAMED_PORTAL_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];

				verifyRenamedPortalProperty(portalProperties, oldKey, newKey);
			}

			for (String key : _OBSOLETE_PORTAL_KEYS) {
				verifyObsoletePortalProperty(portalProperties, key);
			}

			for (String[] keys : _MODULARIZED_PORTAL_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];
				String moduleName = keys[2];

				verifyModularizedPortalProperty(
					portalProperties, oldKey, newKey, moduleName);
			}
		}
	}

	protected void verifyRenamedPortalProperty(
			Properties portalProperties, String oldKey, String newKey)
		throws Exception {

		if (portalProperties.containsKey(oldKey)) {
			_log.error(
				"Portal property \"" + oldKey + "\" was renamed to \"" +
					newKey + "\"");
		}
	}

	protected void verifyRenamedSystemProperty(String oldKey, String newKey)
		throws Exception {

		String value = SystemProperties.get(oldKey);

		if (value != null) {
			_log.error(
				"System property \"" + oldKey + "\" was renamed to \"" +
					newKey + "\"");
		}
	}

	protected void verifySystemProperties() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] keys : _MIGRATED_SYSTEM_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];

				verifyMigratedSystemProperty(oldKey, newKey);
			}

			for (String[] keys : _RENAMED_SYSTEM_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];

				verifyRenamedSystemProperty(oldKey, newKey);
			}

			for (String key : _OBSOLETE_SYSTEM_KEYS) {
				verifyObsoleteSystemProperty(key);
			}

			Properties systemProperties = SystemProperties.getProperties();

			for (String[] keys : _MODULARIZED_SYSTEM_KEYS) {
				String oldKey = keys[0];
				String newKey = keys[1];
				String moduleName = keys[2];

				verifyModularizedSystemProperty(
					systemProperties, oldKey, newKey, moduleName);
			}
		}
	}

	private static final String[][] _MIGRATED_PORTAL_KEYS = new String[][] {
		new String[] {
			"cookie.http.only.names.excludes", "cookie.http.only.names.excludes"
		},
		new String[] {
			"finalize.manager.thread.enabled",
			"com.liferay.portal.kernel.memory.FinalizeManager.thread.enabled"
		},
		new String[] {
			"http.header.secure.x.content.type.options",
			"http.header.secure.x.content.type.options"
		},
		new String[] {
			"http.header.secure.x.content.type.options.urls.excludes",
			"http.header.secure.x.content.type.options.urls.excludes"
		},
		new String[] {
			"http.header.secure.x.frame.options",
			"http.header.secure.x.frame.options"
		},
		new String[] {
			"http.header.secure.x.frame.options.255",
			"http.header.secure.x.frame.options.255"
		},
		new String[] {
			"http.header.secure.x.xss.protection",
			"http.header.secure.x.xss.protection"
		}
	};

	private static final String[][] _MIGRATED_SYSTEM_KEYS = new String[][] {
		new String[] {
			"com.liferay.filters.compression.CompressionFilter",
			"com.liferay.portal.servlet.filters.gzip.GZipFilter"
		},
		new String[] {
			"com.liferay.filters.strip.StripFilter",
			"com.liferay.portal.servlet.filters.strip.StripFilter"
		},
		new String[] {
			"com.liferay.util.Http.max.connections.per.host",
			"com.liferay.portal.util.HttpImpl.max.connections.per.host"
		},
		new String[] {
			"com.liferay.util.Http.max.total.connections",
			"com.liferay.portal.util.HttpImpl.max.total.connections"
		},
		new String[] {
			"com.liferay.util.Http.proxy.auth.type",
			"com.liferay.portal.util.HttpImpl.proxy.auth.type"
		},
		new String[] {
			"com.liferay.util.Http.proxy.ntlm.domain",
			"com.liferay.portal.util.HttpImpl.proxy.ntlm.domain"
		},
		new String[] {
			"com.liferay.util.Http.proxy.ntlm.host",
			"com.liferay.portal.util.HttpImpl.proxy.ntlm.host"
		},
		new String[] {
			"com.liferay.util.Http.proxy.password",
			"com.liferay.portal.util.HttpImpl.proxy.password"
		},
		new String[] {
			"com.liferay.util.Http.proxy.username",
			"com.liferay.portal.util.HttpImpl.proxy.username"
		},
		new String[] {
			"com.liferay.util.Http.timeout",
			"com.liferay.portal.util.HttpImpl.timeout"
		},
		new String[] {
			"com.liferay.util.format.PhoneNumberFormat",
			"phone.number.format.impl"
		},
		new String[] {
			"com.liferay.util.servlet.UploadServletRequest.max.size",
			"com.liferay.portal.upload.UploadServletRequestImpl.max.size"
		},
		new String[] {
			"com.liferay.util.servlet.UploadServletRequest.temp.dir",
			"com.liferay.portal.upload.UploadServletRequestImpl.temp.dir"
		},
		new String[] {
			"com.liferay.util.servlet.fileupload.LiferayFileItem.threshold." +
				"size",
			"com.liferay.portal.upload.LiferayFileItem.threshold.size"
		},
		new String[] {
			"com.liferay.util.servlet.fileupload.LiferayInputStream." +
				"threshold.size",
			"com.liferay.portal.upload.LiferayInputStream.threshold.size"
		}
	};

	private static final String[][] _MODULARIZED_PORTAL_KEYS = {

		// Asset

		new String[] {
			"asset.browser.search.with.database", "search.with.database",
			"com.liferay.asset.browser.web"
		},
		new String[] {
			"asset.categories.navigation.display.templates.config",
			"display.templates.config",
			"com.liferay.asset.categories.navigation.web"
		},
		new String[] {
			"asset.publisher.check.interval", "check.interval",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.from.address", "email.from.address",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.from.name", "email.from.name",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.enabled",
			"email.asset.entry.added.enabled", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.subject",
			"email.asset.entry.added.subject", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.email.asset.entry.added.body",
			"email.asset.entry.added.body", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.style.default", "display.style.default",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.styles", "display.styles",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.display.templates.config",
			"display.templates.config", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.dynamic.subscription.limit",
			"dynamic.subscription.limit", "com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.permission.checking.configurable",
			"permission.checking.configurable",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.publisher.search.with.index", "search.with.index",
			"com.liferay.asset.publisher.web"
		},
		new String[] {
			"asset.tags.navigation.display.templates.config",
			"display.templates.config", "com.liferay.asset.tags.navigation.web"
		},

		// Authentication Verifier

		new String[] {
			"auth.verifier.BasicAuthHeaderAutoLogin.basic_auth",
			"auth.verifier.BasicAuthHeaderAuthVerifier.basic_auth",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.BasicAuthHeaderAutoLogin.hosts.allowed",
			"auth.verifier.BasicAuthHeaderAuthVerifier.hosts.allowed",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.BasicAuthHeaderAutoLogin.urls.excludes",
			"auth.verifier.BasicAuthHeaderAuthVerifier.urls.excludes",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.BasicAuthHeaderAutoLogin.urls.includes",
			"auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes",
			"com.liferay.portal.security.auth.verifier"
		},

		new String[] {
			"auth.verifier.DigestAuthenticationAuthVerifier.digest_auth",
			"auth.verifier.DigestAuthenticationAuthVerifier.digest_auth",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.DigestAuthenticationAuthVerifier.hosts.allowed",
			"auth.verifier.DigestAuthenticationAuthVerifier.hosts.allowed",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.DigestAuthenticationAuthVerifier.urls.excludes",
			"auth.verifier.DigestAuthenticationAuthVerifier.urls.excludes",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.DigestAuthenticationAuthVerifier.urls.includes",
			"auth.verifier.DigestAuthenticationAuthVerifier.urls.includes",
			"com.liferay.portal.security.auth.verifier"
		},

		new String[] {
			"auth.verifier.ParameterAutoLogin.hosts.allowed",
			"auth.verifier.RequestParameterAuthVerifier.hosts.allowed",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.ParameterAutoLogin.urls.excludes",
			"auth.verifier.RequestParameterAuthVerifier.urls.excludes",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.ParameterAutoLogin.urls.includes",
			"auth.verifier.RequestParameterAuthVerifier.urls.includes",
			"com.liferay.portal.security.auth.verifier"
		},

		new String[] {
			"auth.verifier.PortalSessionAuthVerifier.hosts.allowed",
			"auth.verifier.PortalSessionAuthVerifier.hosts.allowed",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.PortalSessionAuthVerifier.urls.excludes",
			"auth.verifier.PortalSessionAuthVerifier.urls.excludes",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.PortalSessionAuthVerifier.urls.includes",
			"auth.verifier.PortalSessionAuthVerifier.urls.includes",
			"com.liferay.portal.security.auth.verifier"
		},

		new String[] {
			"auth.verifier.TunnelingServletAuthVerifier.hosts.allowed",
			"auth.verifier.TunnelAuthVerifier.hosts.allowed",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.TunnelingServletAuthVerifier.urls.excludes",
			"auth.verifier.TunnelAuthVerifier.urls.excludes",
			"com.liferay.portal.security.auth.verifier"
		},
		new String[] {
			"auth.verifier.TunnelingServletAuthVerifier.urls.includes",
			"auth.verifier.TunnelAuthVerifier.urls.includes",
			"com.liferay.portal.security.auth.verifier"
		},

		// Blogs

		new String[] {
			"blogs.display.templates.config", "display.templates.config",
			"com.liferay.blogs.web"
		},

		new String[] {
			"blogs.entry.check.interval", "entry.check.interval",
			"com.liferay.blogs.web"
		},

		new String[] {
			"blogs.linkback.job.interval", "linkback.job.interval",
			"com.liferay.blogs.web"
		},

		// Bookmarks

		new String[] {
			"bookmarks.email.entry.added.body", "email.entry.added.body",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.added.enabled", "email.entry.added.enabled",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.added.subject", "email.entry.added.subject",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.body", "email.entry.updated.body",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.enabled",
			"email.entry.updated.enabled", "com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.entry.updated.subject",
			"email.entry.updated.subject", "com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.from.address", "email.from.address",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.email.from.name", "email.from.name",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.entry.columns", "entry.columns",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.folder.columns", "folder.columns",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.folders.search.visible", "folders.search.visible",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.related.assets.enabled", "related.assets.enabled",
			"com.liferay.bookmarks.service"
		},
		new String[] {
			"bookmarks.subfolders.visible", "subfolders.visible",
			"com.liferay.bookmarks.service"
		},

		// Breadcrumb

		new String[] {
			"breadcrumb.display.style.default", "ddm.template.key.default",
			"com.liferay.site.navigation.breadcrumb.web"
		},
		new String[] {
			"breadcrumb.display.templates.config", "display.templates.config",
			"com.liferay.site.navigation.breadcrumb.web"
		},
		new String[] {
			"breadcrumb.show.guest.group", "show.guest.group",
			"com.liferay.site.navigation.breadcrumb.web"
		},
		new String[] {
			"breadcrumb.show.parent.groups", "show.parent.groups",
			"com.liferay.site.navigation.breadcrumb.web"
		},

		// CAS

		new String[] {
			"cas.auth.enabled", "enabled", "com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.import.from.ldap", "import.from.ldap",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.login.url", "login.url", "com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.logout.on.session.expiration", "logout.on.session.expiration",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.logout.url", "logout.url",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.no.such.user.redirect.url", "no.such.user.redirect.url",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.server.name", "server.name",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.server.url", "server.url",
			"com.liferay.portal.security.sso.cas"
		},
		new String[] {
			"cas.service.url", "service.url",
			"com.liferay.portal.security.sso.cas"
		},

		// Cluster Link

		new String[] {
			"cluster.link.debug.enabled", "cluster.link.debug.enabled",
			"com.liferay.portal.cluster"
		},

		// Currency Converter

		new String[] {
			"currency.converter.symbols", "symbols",
			"com.liferay.currency.converter.web"
		},

		// Document Library

		new String[] {
			"dl.display.templates.config", "display.templates.config",
			"com.liferay.document.library.web"
		},
		new String[] {
			"dl.repository.cmis.delete.depth", "delete.depth",
			"com.liferay.document.library.repository.cmis"
		},
		new String[] {
			"dl.store.advanced.file.system.root.dir", "root.dir",
			"com.liferay.portal.store.filesystem"
		},
		new String[] {
			"dl.store.cmis.credentials.username", "credentials.username",
			"com.liferay.portal.store.cmis"
		},
		new String[] {
			"dl.store.cmis.credentials.password", "credentials.password",
			"com.liferay.portal.store.cmis"
		},
		new String[] {
			"dl.store.cmis.repository.url", "repository.url",
			"com.liferay.portal.store.cmis"
		},
		new String[] {
			"dl.store.cmis.system.root.dir", "system.root.dir",
			"com.liferay.portal.store.cmis"
		},
		new String[] {
			"dl.store.file.system.root.dir", "root.dir",
			"com.liferay.portal.store.filesystem"
		},
		new String[] {
			"dl.store.jcr.fetch.delay", "fetch.delay",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"dl.store.jcr.fetch.max.failures", "fetch.max.failures",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"dl.store.jcr.move.version.labels", "move.version.labels",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"dl.store.s3.access.key", "access.key",
			"com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.bucket.name", "bucket.name",
			"com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.jets3t[httpclient.max-connections]",
			"http.client.max.connections", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.jets3t[s3service.default-bucket-location]",
			"s3service.default.bucket.location", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.jets3t[s3service.default-storage-class]",
			"s3service.default.storage.class", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.jets3t[s3service.s3-endpoint]",
			"s3service.s3.endpoint", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.secret.key", "secret.key",
			"com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.temp.dir.clean.up.expunge",
			"temp.dir.clean.up.expunge", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.store.s3.temp.dir.clean.up.frequency",
			"temp.dir.clean.up.frequency", "com.liferay.portal.store.s3"
		},
		new String[] {
			"dl.temporary.file.entries.check.interval",
			"temporary.file.entries.check.interval",
			"com.liferay.document.library.web"
		},

		// Dynamic Data Lists

		new String[] {
			"dynamic.data.lists.error.template",
			"dynamic.data.lists.error.template",
			"com.liferay.dynamic.data.lists.web"
		},
		new String[] {
			"dynamic.data.lists.storage.type",
			"dynamic.data.lists.storage.type",
			"com.liferay.dynamic.data.lists.web"
		},

		// Dynamic Data Mapping

		new String[] {
			"dynamic.data.mapping.image.extensions",
			"dynamic.data.mapping.image.extensions",
			"com.liferay.dynamic.data.mapping.service"
		},
		new String[] {
			"dynamic.data.mapping.image.small.max.size",
			"dynamic.data.mapping.image.small.max.size",
			"com.liferay.dynamic.data.mapping.service"
		},
		new String[] {
			"dynamic.data.mapping.structure.force.autogenerate.key",
			"dynamic.data.mapping.structure.force.autogenerate.key",
			"com.liferay.dynamic.data.mapping.web"
		},
		new String[] {
			"dynamic.data.mapping.template.force.autogenerate.key",
			"dynamic.data.mapping.template.force.autogenerate.key",
			"com.liferay.dynamic.data.mapping.web"
		},
		new String[] {
			"dynamic.data.mapping.template.language.default",
			"dynamic.data.mapping.template.language.default",
			"com.liferay.dynamic.data.mapping.web"
		},
		new String[] {
			"dynamic.data.mapping.template.language.content",
			"dynamic.data.mapping.template.language.content",
			"com.liferay.dynamic.data.mapping.web"
		},

		// Facebook Connect

		new String[] {
			"facebook.connect.auth.enabled", "enabled",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.app.id", "app.id",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.app.secret", "app.secret",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.graph.url", "graph.url",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.oauth.auth.url", "oauth.auth.url",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.oauth.redirect.url", "oauth.redirect.url",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.oauth.token.url", "oauth.token.url",
			"com.liferay.portal.security.sso.facebook.connect"
		},
		new String[] {
			"facebook.connect.verified.account.required",
			"verified.account.required",
			"com.liferay.portal.security.sso.facebook.connect"
		},

		// Flags

		new String[] {"flags.email.body", "email.body", "com.liferay.flags"},
		new String[] {
			"flags.email.from.address", "email.from.address",
			"com.liferay.flags"
		},
		new String[] {
			"flags.email.from.name", "email.from.name", "com.liferay.flags"
		},
		new String[] {
			"flags.email.subject", "email.subject", "com.liferay.flags"
		},
		new String[] {
			"flags.guest.users.enabled", "guest.users.enabled",
			"com.liferay.flags"
		},
		new String[] {"flags.reasons", "reasons", "com.liferay.flags"},

		// FreeMarker Engine

		new String[] {
			"freemarker.engine.localized.lookup", "localized.lookup",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.macro.library", "macro.library",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.resource.modification.check.interval",
			"resource.modification.check",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.restricted.classes", "restricted.classes",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.restricted.packages", "restricted.packages",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.template.exception.handler",
			"template.exception.handler",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"freemarker.engine.template.parsers", "template.parsers",
			"com.liferay.portal.template.freemarker"
		},
		new String[] {
			"journal.template.freemarker.restricted.variables",
			"restricted.variables", "com.liferay.portal.template.freemarker"
		},

		// IFrame

		new String[] {"iframe.auth", "auth", "com.liferay.iframe.web"},
		new String[] {
			"iframe.auth-type", "auth.type", "com.liferay.iframe.web"
		},
		new String[] {
			"iframe.form-method", "form.method", "com.liferay.iframe.web"
		},
		new String[] {
			"iframe.hidden-variables", "hidden.variables",
			"com.liferay.iframe.web"
		},

		// JCR

		new String[] {
			"jcr.initialize.on.startup", "initialize.on.startup",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.jackrabbit.config.file.path", "jackrabbit.config.file.path",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.jackrabbit.credentials.password",
			"jackrabbit.credentials.password", "com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.jackrabbit.credentials.username",
			"jackrabbit.credentials.username", "com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.jackrabbit.repository.home", "repository.home",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.jackrabbit.repository.root", "repository.root",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.node.documentlibrary", "node.documentlibrary",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.workspace.name", "workspace.name",
			"com.liferay.portal.store.jcr"
		},
		new String[] {
			"jcr.wrap.session", "wrap.session", "com.liferay.portal.store.jcr"
		},

		// Journal

		new String[] {
			"journal.article.check.interval", "check.interval",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.article.comments.enabled",
			"journal.article.comments.enabled", "com.liferay.journal.service"
		},
		new String[] {
			"journal.article.custom.tokens", "journal.article.custom.tokens",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.article.database.keyword.search.content",
			"journal.article.database.keyword.search.content",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.article.expire.all.versions",
			"journal.article.expire.all.versions", "com.liferay.journal.service"
		},
		new String[] {
			"journal.article.force.autogenerate.id",
			"journal.article.force.autogenerate.id", "com.liferay.journal.web"
		},
		new String[] {
			"journal.articles.search.with.index",
			"journal.articles.search.with.index", "com.liferay.journal.web"
		},
		new String[] {
			"journal.article.storage.type", "journal.article.storage.type",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.article.token.page.break",
			"journal.article.token.page.break", "com.liferay.journal.service"
		},
		new String[] {
			"journal.article.view.permission.check.enabled",
			"journal.article.view.permission.check.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.articles.index.all.versions",
			"journal.articles.index.all.versions", "com.liferay.journal.service"
		},
		new String[] {
			"journal.char.blacklist", "char.blacklist",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.content.publish.to.live.by.default",
			"publish.to.live.by.default", "com.liferay.journal.content.web"
		},
		new String[] {
			"journal.content.search.show.listed", "show.listed",
			"com.liferay.journal.content.search.web"
		},
		new String[] {
			"journal.default.display.view", "default.display.view",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.display.views", "display.views", "com.liferay.journal.web"
		},
		new String[] {
			"journal.email.from.address", "email.from.address",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.from.name", "email.from.name",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.added.enabled",
			"email.article.added.enabled", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.added.subject",
			"email.article.added.subject", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.added.body", "email.article.added.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.denied.enabled",
			"email.article.approval.denied.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.denied.subject",
			"email.article.approval.denied.subject",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.denied.body",
			"email.article.approval.denied.body", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.granted.enabled",
			"email.article.approval.granted.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.granted.subject",
			"email.article.approval.granted.subject",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.granted.body",
			"email.article.approval.granted.body", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.requested.enabled",
			"email.article.approval.requested.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.requested.subject",
			"email.article.approval.requested.subject",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.approval.requested.body",
			"email.article.approval.requested.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.to.folder.enabled",
			"email.article.moved.to.folder.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.to.folder.subject",
			"email.article.moved.to.folder.subject",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.from.folder.body",
			"email.article.moved.from.folder.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.from.folder.enabled",
			"email.article.moved.from.folder.enabled",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.from.folder.subject",
			"email.article.moved.from.folder.subject",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.moved.from.folder.body",
			"email.article.moved.from.folder.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.review.enabled",
			"email.article.review.enabled", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.review.subject",
			"email.article.review.subject", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.review.body", "email.article.review.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.updated.enabled",
			"email.article.updated.enabled", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.updated.subject",
			"email.article.updated.subject", "com.liferay.journal.service"
		},
		new String[] {
			"journal.email.article.updated.body", "email.article.updated.body",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.error.template[ftl]", "error.template[ftl]",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.error.template[vm]", "error.template[vm]",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.error.template[xsl]", "error.template[xsl]",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.feed.force.autogenerate.id",
			"journal.feed.force.autogenerate.id", "com.liferay.journal.web"
		},
		new String[] {
			"journal.folder.icon.check.count",
			"journal.folder.icon.check.count", "com.liferay.journal.service"
		},
		new String[] {
			"journal.lar.creation.strategy", "lar.creation.strategy",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.publish.to.live.by.default", "publish.to.live.by.defaul",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.publish.version.history.by.default",
			"publish.version.history.by.default", "com.liferay.journal.web"
		},
		new String[] {
			"journal.sync.content.search.on.startup",
			"sync.content.search.on.startup", "com.liferay.journal.service"
		},
		new String[] {
			"journal.template.language.content[css]",
			"journal.article.template.language.content[css]",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.template.language.content[ftl]",
			"journal.article.template.language.content[ftl]",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.template.language.content[vm]",
			"journal.article.template.language.content[vm]",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.template.language.content[xsl]",
			"journal.article.template.language.content[xsl]",
			"com.liferay.journal.web"
		},
		new String[] {
			"journal.transformer.listener", "transformer.listener",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.transformer.regex.pattern", "transformer.regex.pattern",
			"com.liferay.journal.service"
		},
		new String[] {
			"journal.transformer.regex.replacement",
			"transformer.regex.replacement", "com.liferay.journal.service"
		},
		new String[] {
			"terms.of.use.journal.article.group.id",
			"terms.of.use.journal.article.group.id",
			"com.liferay.journal.service"
		},
		new String[] {
			"terms.of.use.journal.article.id",
			"terms.of.use.journal.article.id", "com.liferay.journal.service"
		},

		// Language

		new String[] {
			"language.display.style.default", "ddm.template.key.default",
			"com.liferay.site.navigation.language.web"
		},
		new String[] {
			"language.display.templates.config", "display.templates.config",
			"com.liferay.site.navigation.language.web"
		},

		// Lucene

		new String[] {
			"lucene.analyzer.max.tokens", "analyzer.max.tokens",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.buffer.size", "buffer.size",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.commit.batch.size", "commit.batch.size",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.commit.time.interval", "commit.time.interval",
			"com.liferay.portal.search.lucene"
		},
		new String[] {"lucene.dir", "dir", "com.liferay.portal.search.lucene"},
		new String[] {
			"lucene.merge.factor", "merge.factor",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.merge.policy", "merge.policy",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.merge.scheduler", "merge.scheduler",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.store.type", "store.type",
			"com.liferay.portal.search.lucene"
		},
		new String[] {
			"lucene.store.type.file.force.mmap", "store.type.file.force.mmp",
			"com.liferay.portal.search.lucene"
		},

		// Message Boards

		new String[] {
			"message.boards.expire.ban.job.interval", "expire.ban.job.interval",
			"com.liferay.message.boards.web"
		},

		// Monitoring

		new String[] {
			"monitoring.portal.request", "monitor.portal.request",
			"com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.action.request",
			"monitor.portlet.action.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.event.request", "monitor.portlet.event.request",
			"com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.render.request",
			"monitor.portlet.render.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.portlet.resource.request",
			"monitor.portlet.resource.request", "com.liferay.portal.monitoring"
		},
		new String[] {
			"monitoring.show.per.request.data.sample",
			"show.per.request.data.sample", "com.liferay.portal.monitoring"
		},

		// Navigation

		new String[] {
			"navigation.display.style.default", "ddm.template.key.default",
			"com.liferay.site.navigation.menu.web"
		},
		new String[] {
			"navigation.display.style.options", "display.style.options",
			"com.liferay.site.navigation.menu.web"
		},

		// Nested Portlets

		new String[] {
			"nested.portlets.layout.template.default",
			"layout.template.default", "com.liferay.nested.portlets.web"
		},
		new String[] {
			"nested.portlets.layout.template.unsupported",
			"layout.template.unsupported", "com.liferay.nested.portlets.web"
		},

		// NTLM

		new String[] {
			"ntlm.auth.enabled", "enabled",
			"com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain", "domain", "com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain.controller", "domain.controller",
			"com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.domain.controller.name", "domain.controller.name",
			"com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.negotiate.flags", "negotiate.flags",
			"com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.service.account", "service.account",
			"com.liferay.portal.security.sso.ntlm"
		},
		new String[] {
			"ntlm.auth.service.password", "service.password",
			"com.liferay.portal.security.sso.ntlm"
		},

		// OpenID

		new String[] {
			"open.id.auth.enabled", "enabled",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.providers", "providers",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.schema[default]", "ax.schema",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.type.email[default]", "ax.type.email",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.type.firstname[default]", "ax.type.firstname",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.type.lastname[default]", "ax.type.lastname",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.schema[yahoo]", "ax.schema",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.type.email[yahoo]", "ax.type.email",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.ax.type.fullname[yahoo]", "ax.type.fullname",
			"com.liferay.portal.security.sso.openid"
		},
		new String[] {
			"open.id.url[yahoo]", "url",
			"com.liferay.portal.security.sso.openid"
		},

		// OpenSSO

		new String[] {
			"open.sso.auth.enabled", "enabled",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.email.address.attr", "email.address.attr",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.first.name.attr", "first.name.attr",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.last.name.attr", "last.name.attr",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.import.from.ldap", "import.from.ldap",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.login.url", "login.url",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.logout.on.session.expiration",
			"logout.on.session.expiration",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.logout.url", "logout.url",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.screen.name.attr", "screen.name.attr",
			"com.liferay.portal.security.sso.opensso"
		},
		new String[] {
			"open.sso.service.url", "service.url",
			"com.liferay.portal.security.sso.opensso"
		},

		// Polls

		new String[] {
			"polls.publish.to.live.by.default", "publish.to.live.by.default",
			"com.liferay.polls.service"
		},

		// Request Header

		new String[] {
			"request.header.auth.hosts.allowed", "authHostsAllowed",
			"com.liferay.portal.security.auto.login.request.header"
		},

		new String[] {
			"request.header.auth.import.from.ldap", "importFromLDAP",
			"com.liferay.portal.security.auto.login.request.header"
		},

		// RSS

		new String[] {
			"rss.display.templates.config", "display.templates.config",
			"com.liferay.rss.web"
		},

		// Shopping

		new String[] {
			"shopping.cart.min.qty.multiple", "cart.min.qty.multiple",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.category.forward.to.cart", "category.forward.to.cart",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.category.show.special.items",
			"category.show.special.items", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.credit.card.types", "credit.card.types",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.currency.id", "currency.id",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.from.address", "email.from.address",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.from.name", "email.from.name",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.confirmation.enabled",
			"email.order.confirmation.enabled", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.confirmation.subject",
			"email.order.confirmation.subject", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.confirmation.body",
			"email.order.confirmation.body", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.shipping.enabled",
			"email.order.shipping.enabled", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.shipping.subject",
			"email.order.shipping.subject", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.email.order.shipping.body", "email.order.shipping.body",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.insurance", "insurance", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.insurance.formula", "insurance.formula",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.item.show.availability", "item.show.availability",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.min.order", "min.order", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.order.comments.enabled", "order.comments.enabled",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.paypal.email.address", "paypal.email.address",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.shipping", "shipping", "com.liferay.shopping.service"
		},
		new String[] {
			"shopping.shipping.formula", "shipping.formula",
			"com.liferay.shopping.service"
		},
		new String[] {
			"shopping.tax.rate", "tax.rate", "com.liferay.shopping.service"
		},

		// Scripting

		new String[] {
			"scripting.forbidden.classes", "forbidden.classes",
			"com.liferay.portal.scripting.javascript"
		},
		new String[] {
			"scripting.jruby.load.paths", "load.paths",
			"com.liferay.portal.scripting.ruby"
		},

		// Search

		new String[] {
			"search.facet.configuration", "facet.configuration",
			"com.liferay.search.web"
		},

		// Site Map

		new String[] {
			"sitemap.display.templates.config", "display.templates.config",
			"com.liferay.site.navigation.site.map.web"
		},

		// Staging

		new String[] {
			"staging.draft.export.import.configuration.check.interval",
			"draft.export.import.configuration.check.interval",
			"com.liferay.exportimport.web"
		},
		new String[] {
			"staging.draft.export.import.configuration.clean.up.count",
			"draft.export.import.configuration.clean.up.count",
			"com.liferay.exportimport.web"
		},

		// Social Activity

		new String[] {
			"social.activity.contribution.increments",
			"contribution.increments", "com.liferay.social.activity"
		},
		new String[] {
			"social.activity.contribution.limit.values",
			"contribution.limit.values", "com.liferay.social.activity"
		},
		new String[] {
			"social.activity.participation.increments",
			"participation.increments", "com.liferay.social.activity"
		},
		new String[] {
			"social.activity.participation.limit.values",
			"participation.limit.values", "com.liferay.social.activity"
		},

		// Tags Compiler

		new String[] {
			"tags.compiler.enabled", "enabled",
			"com.liferay.asset.tags.compiler.web"
		},

		// Translator

		new String[] {
			"translator.default.languages", "translation.id",
			"com.liferay.translator.web"
		},
		new String[] {
			"translator.languages", "language.ids", "com.liferay.translator.web"
		},

		// Velocity Engine

		new String[] {
			"velocity.engine.directive.if.to.string.null.check",
			"directive.if.to.string.null.check",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.resource.parsers", "resource.parsers",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.resource.modification.check.interval",
			"resource.modification.check.interval",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.classes", "restricted.classes",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.packages", "restricted.packages",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.restricted.variables", "restricted.variables",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.velocimacro.library", "macro.library",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.logger", "logger",
			"com.liferay.portal.template.velocity"
		},
		new String[] {
			"velocity.engine.logger.category", "logger.category",
			"com.liferay.portal.template.velocity"
		},

		// XSL Content

		new String[] {
			"xsl.content.valid.url.prefixes", "valid.url.prefixes",
			"com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xml.doctype.declaration.allowed",
			"xml.doctype.declaration.allowed", "com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xml.external.general.entities.allowed",
			"xml.external.general.entities.allowed",
			"com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xml.external.parameter.entities.allowed",
			"xml.external.parameter.entities.allowed",
			"com.liferay.xsl.content.web"
		},
		new String[] {
			"xsl.content.xsl.secure.processing.enabled",
			"xsl.secure.processing.enabled", "com.liferay.xsl.content.web"
		},

		// XSL Engine

		new String[] {
			"xsl.template.secure.processing.enabled",
			"secure.processing.enabled", "com.liferay.portal.template.xsl"
		}
	};

	private static final String[][] _MODULARIZED_SYSTEM_KEYS = {

		// Calendar

		new String[] {
			"ical4j.compatibility.outlook", "ical4j.compatibility.outlook",
			"com.liferay.calendar.service"
		},
		new String[] {
			"ical4j.parsing.relaxed", "ical4j.parsing.relaxed",
			"com.liferay.calendar.service"
		},
		new String[] {
			"ical4j.unfolding.relaxed", "ical4j.unfolding.relaxed",
			"com.liferay.calendar.service"
		},
		new String[] {
			"ical4j.validation.relaxed", "ical4j.validation.relaxed",
			"com.liferay.calendar.service"
		}
	};

	private static final String[] _OBSOLETE_PORTAL_KEYS = new String[] {
		"aim.login", "aim.login", "amazon.access.key.id",
		"amazon.associate.tag", "amazon.secret.access.key",
		"asset.entry.increment.view.counter.enabled", "asset.entry.validator",
		"asset.publisher.asset.entry.query.processors",
		"asset.publisher.filter.unlistable.entries",
		"asset.publisher.query.form.configuration",
		"asset.tag.permissions.enabled", "asset.tag.properties.default",
		"asset.tag.properties.enabled", "asset.tag.suggestions.enabled",
		"auth.max.failures.limit", "blogs.image.small.max.size",
		"breadcrumb.display.style.options",
		"buffered.increment.parallel.queue.size",
		"buffered.increment.serial.queue.size", "captcha.max.challenges",
		"captcha.check.portal.create_account",
		"captcha.check.portal.send_password",
		"captcha.check.portlet.message_boards.edit_category",
		"captcha.check.portlet.message_boards.edit_message",
		"captcha.engine.impl", "captcha.engine.recaptcha.key.private",
		"captcha.engine.recaptcha.key.public",
		"captcha.engine.recaptcha.url.script",
		"captcha.engine.recaptcha.url.noscript",
		"captcha.engine.recaptcha.url.verify",
		"captcha.engine.simplecaptcha.height",
		"captcha.engine.simplecaptcha.width",
		"captcha.engine.simplecaptcha.background.producers",
		"captcha.engine.simplecaptcha.gimpy.renderers",
		"captcha.engine.simplecaptcha.noise.producers",
		"captcha.engine.simplecaptcha.text.producers",
		"captcha.engine.simplecaptcha.word.renderers", "cas.validate.url",
		"cluster.executor.heartbeat.interval",
		"com.liferay.filters.doubleclick.DoubleClickFilter",
		"com.liferay.portal.servlet.filters.audit.AuditFilter",
		"com.liferay.portal.servlet.filters.doubleclick.DoubleClickFilter",
		"com.liferay.portal.servlet.filters.charbufferpool." +
			"CharBufferPoolFilter",
		"com.liferay.portal.servlet.filters.monitoring.MonitoringFilter",
		"com.liferay.portal.servlet.filters.secure.SecureFilter",
		"com.liferay.portal.servlet.filters.validhtml.ValidHtmlFilter",
		"com.liferay.portal.upload.UploadServletRequestImpl.max.size",
		"com.liferay.portal.upload.UploadServletRequestImpl.temp.dir",
		"commons.pool.enabled", "company.settings.form.configuration",
		"company.settings.form.identification",
		"company.settings.form.miscellaneous", "company.settings.form.social",
		"control.panel.home.portlet.id", "convert.processes",
		"default.guest.public.layout.wap.color.scheme.id",
		"default.guest.public.layout.wap.theme.id",
		"default.user.private.layout.wap.color.scheme.id",
		"default.user.private.layout.wap.theme.id",
		"default.user.public.layout.wap.color.scheme.id",
		"default.user.public.layout.wap.theme.id",
		"default.wap.color.scheme.id", "default.wap.theme.id",
		"discussion.thread.view",
		"dl.file.entry.image.exif.metadata.rotation.enabled",
		"dl.file.entry.read.count.enabled", "dl.folder.menu.visible",
		"dockbar.add.portlets", "dockbar.administrative.links.show.in.pop.up",
		"dynamic.data.lists.record.set.force.autogenerate.key",
		"dynamic.data.lists.template.language.parser[ftl]",
		"dynamic.data.lists.template.language.parser[vm]",
		"dynamic.data.lists.template.language.parser[xsl]",
		"dynamic.data.mapping.structure.index.with.thread",
		"dynamic.data.mapping.structure.private.field.names",
		"dynamic.data.mapping.structure.private.field.datatype[_fieldsDisplay]",
		"dynamic.data.mapping.structure.private.field.repeatable[" +
			"_fieldsDisplay]",
		"dynamic.data.mapping.template.language.types",
		"editor.ckeditor.version", "editor.inline.editing.enabled",
		"editor.wysiwyg.portal-web.docroot.html.portlet.asset_publisher." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.blogs.configuration." +
			"jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.bookmarks." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.document_library." +
			"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
				"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.login.configuration." +
			"jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
			"configuration.jsp",
		"editor.wysiwyg.portal-web.docroot.html.portlet.portal_settings." +
			"email_notifications.jsp",
		"ehcache.bootstrap.cache.loader.factory",
		"ehcache.cache.event.listener.factory",
		"ehcache.cache.manager.peer.listener.factory",
		"ehcache.cache.manager.peer.provider.factory",
		"ehcache.cache.manager.statistics.thread.pool.size",
		"ehcache.multi.vm.config.location.peerProviderProperties",
		"ehcache.statistics.enabled",
		"hot.deploy.hook.custom.jsp.verification.enabled",
		"hibernate.cache.region.factory_class",
		"hibernate.cache.use_minimal_puts", "hibernate.cache.use_query_cache",
		"hibernate.cache.use_second_level_cache",
		"hibernate.cache.use_structured_entries", "icq.jar", "icq.login",
		"icq.password", "index.filter.search.limit",
		"index.portal.field.analyzer.enabled", "index.read.only",
		"invitation.email.max.recipients", "invitation.email.message.body",
		"invitation.email.message.subject", "javax.persistence.validation.mode",
		"jbi.workflow.url", "json.deserializer.strict.mode",
		"journal.article.form.add", "journal.article.form.default.values",
		"journal.article.form.update", "journal.article.form.translate",
		"journal.article.types", "journal.articles.page.delta.values",
		"journal.browse.by.structures.sorted.by.name",
		"journal.image.extensions", "journal.image.small.max.size",
		"journal.template.language.parser[css]",
		"journal.template.language.parser[ftl]",
		"journal.template.language.parser[vm]",
		"journal.template.language.parser[xsl]",
		"journal.template.language.types", "jpa.configs",
		"jpa.database.platform", "jpa.database.type", "jpa.load.time.weaver",
		"jpa.provider", "jpa.provider.property.eclipselink.allow-zero-id",
		"jpa.provider.property.eclipselink.logging.level",
		"jpa.provider.property.eclipselink.logging.timestamp",
		"language.display.style.options",
		"layout.configuration.action.update[link_to_layout]",
		"layout.configuration.action.delete[link_to_layout]",
		"layout.edit.page[control_panel]", "layout.edit.page[link_to_layout]",
		"layout.first.pageable[control_panel]",
		"layout.first.pageable[link_to_layout]", "layout.form.add",
		"layout.form.update", "layout.parentable[control_panel]",
		"layout.parentable[link_to_layout]", "layout.reset.portlet.ids",
		"layout.set.form.update", "layout.sitemapable[link_to_layout]",
		"layout.types", "layout.url[control_panel]",
		"layout.url[link_to_layout]", "layout.url.friendliable[control_panel]",
		"layout.url.friendliable[link_to_layout]",
		"layout.view.page[control_panel]", "layout.view.page[link_to_layout]",
		"library.download.url.resin.jar", "library.download.url.script-10.jar",
		"lucene.analyzer", "lucene.cluster.index.loading.sync.timeout",
		"lucene.file.extractor", "lucene.file.extractor.regexp.strip",
		"lucene.replicate.write", "lucene.store.jdbc.auto.clean.up",
		"lucene.store.jdbc.auto.clean.up.enabled",
		"lucene.store.jdbc.auto.clean.up.interval",
		"lucene.store.jdbc.dialect.db2", "lucene.store.jdbc.dialect.derby",
		"lucene.store.jdbc.dialect.hsqldb", "lucene.store.jdbc.dialect.jtds",
		"lucene.store.jdbc.dialect.microsoft",
		"lucene.store.jdbc.dialect.mysql", "lucene.store.jdbc.dialect.oracle",
		"lucene.store.jdbc.dialect.postgresql", "mail.hook.cyrus.add.user",
		"mail.hook.cyrus.delete.user", "mail.hook.cyrus.home",
		"mail.hook.fusemail.account.type", "mail.hook.fusemail.group.parent",
		"mail.hook.fusemail.password", "mail.hook.fusemail.url",
		"mail.hook.fusemail.username",
		"memory.cluster.scheduler.lock.cache.enabled",
		"message.boards.email.message.added.signature",
		"message.boards.email.message.updated.signature",
		"message.boards.thread.locking.enabled",
		"message.boards.thread.previous.and.next.navigation.enabled",
		"message.boards.thread.views", "message.boards.thread.views.default",
		"microsoft.translator.client.id", "microsoft.translator.client.secret",
		"mobile.device.styling.wap.enabled", "module.framework.initial.bundles",
		"msn.login", "msn.password", "multicast.group.address[\"hibernate\"]",
		"multicast.group.port[\"hibernate\"]",
		"net.sf.ehcache.configurationResourceName",
		"net.sf.ehcache.configurationResourceName.peerProviderProperties",
		"openoffice.server.enabled", "openoffice.server.host",
		"openoffice.server.port", "openoffice.cache.enabled",
		"organizations.form.add.identification", "organizations.form.add.main",
		"organizations.form.add.miscellaneous",
		"organizations.form.update.identification",
		"organizations.form.update.main",
		"organizations.form.update.miscellaneous",
		"organizations.indexer.enabled", "portal.cache.manager.type.multi.vm",
		"portal.cache.manager.type.single.vm", "portal.ctx",
		"portal.security.manager.enable", "permissions.list.filter",
		"permissions.thread.local.cache.max.size",
		"permissions.user.check.algorithm", "persistence.provider",
		"ratings.max.score", "ratings.min.score", "sandbox.deploy.dir",
		"sandbox.deploy.enabled", "sandbox.deploy.interval",
		"sandbox.deploy.listeners", "sc.image.max.size",
		"sc.image.thumbnail.max.height", "sc.image.thumbnail.max.width",
		"sc.product.comments.enabled", "scheduler.classes",
		"schema.run.minimal", "search.container.page.iterator.page.values",
		"service.builder.service.read.only.prefixes", "shard.available.names",
		"shard.default.name", "shard.selector", "shopping.image.extensions",
		"shopping.image.large.max.size", "shopping.image.medium.max.size",
		"shopping.image.small.max.size", "siteminder.auth.enabled",
		"siteminder.import.from.ldap", "siteminder.user.header",
		"sites.form.add.advanced", "sites.form.add.main",
		"sites.form.add.miscellaneous", "sites.form.add.seo",
		"sites.form.update.advanced", "sites.form.update.main",
		"sites.form.update.miscellaneous", "sites.form.update.seo",
		"staging.lock.enabled", "social.activity.sets.bundling.enabled",
		"table.mapper.cache.mapping.table.names", "tck.url",
		"user.groups.indexer.enabled", "users.form.add.identification",
		"users.indexer.enabled", "users.form.add.main",
		"users.form.add.miscellaneous", "users.form.my.account.identification",
		"users.form.my.account.main", "users.form.my.account.miscellaneous",
		"users.form.update.identification", "users.form.update.main",
		"users.form.update.miscellaneous", "users.image.check.token",
		"users.image.default.use.initials", "users.image.max.height",
		"users.image.max.size", "users.image.max.width",
		"vaadin.resources.path", "vaadin.theme", "vaadin.widgetset",
		"webdav.storage.class", "webdav.storage.show.edit.url",
		"webdav.storage.show.view.url", "webdav.storage.tokens",
		"wiki.email.page.added.signature", "wiki.email.page.updated.signature",
		"xss.allow", "ym.login", "ym.password"
	};

	private static final String[] _OBSOLETE_SYSTEM_KEYS = new String[] {
		"com.liferay.util.Http.proxy.host", "com.liferay.util.Http.proxy.port",
		"com.liferay.util.XSSUtil.regexp.pattern"
	};

	private static final String[][] _RENAMED_PORTAL_KEYS = new String[][] {
		new String[] {
			"amazon.license.0", "amazon.access.key.id"
		},
		new String[] {"amazon.license.1", "amazon.access.key.id"},
		new String[] {"amazon.license.2", "amazon.access.key.id"},
		new String[] {"amazon.license.3", "amazon.access.key.id"},
		new String[] {"cdn.host", "cdn.host.http"},
		new String[] {
			"cluster.executor.debug.enabled", "cluster.link.debug.enabled"
		},
		new String[] {
			"com.liferay.portal.servlet.filters.compression.CompressionFilter",
			"com.liferay.portal.servlet.filters.gzip.GZipFilter"
		},
		new String[] {
			"default.guest.friendly.url",
			"default.guest.public.layout.friendly.url"
		},
		new String[] {
			"default.guest.layout.column", "default.guest.public.layout.column"
		},
		new String[] {
			"default.guest.layout.name", "default.guest.public.layout.name"
		},
		new String[] {
			"default.guest.layout.template.id",
			"default.guest.public.layout.template.id"
		},
		new String[] {
			"default.user.layout.column", "default.user.public.layout.column"
		},
		new String[] {
			"default.user.layout.name", "default.user.public.layout.name"
		},
		new String[] {
			"default.user.layout.template.id",
			"default.user.public.layout.template.id"
		},
		new String[] {
			"default.user.private.layout.lar",
			"default.user.private.layouts.lar"
		},
		new String[] {
			"default.user.public.layout.lar", "default.user.public.layouts.lar"
		},
		new String[] {
			"dl.hook.cmis.credentials.password",
			"dl.store.cmis.credentials.password"
		},
		new String[] {
			"dl.hook.cmis.credentials.username",
			"dl.store.cmis.credentials.username"
		},
		new String[] {
			"dl.hook.cmis.repository.url", "dl.store.cmis.repository.url"
		},
		new String[] {
			"dl.hook.cmis.system.root.dir", "dl.store.cmis.system.root.dir"
		},
		new String[] {
			"dl.hook.file.system.root.dir", "dl.store.file.system.root.dir"
		},
		new String[] {"dl.hook.impl", "dl.store.impl"},
		new String[] {"dl.hook.jcr.fetch.delay", "dl.store.jcr.fetch.delay"},
		new String[] {
			"dl.hook.jcr.fetch.max.failures", "dl.store.jcr.fetch.max.failures"
		},
		new String[] {
			"dl.hook.jcr.move.version.labels",
			"dl.store.jcr.move.version.labels"
		},
		new String[] {"dl.hook.s3.access.key", "dl.store.s3.access.key"},
		new String[] {"dl.hook.s3.bucket.name", "dl.store.s3.bucket.name"},
		new String[] {"dl.hook.s3.secret.key", "dl.store.s3.secret.key"},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.calendar." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.invitation." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.journal." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.message_boards." +
				"configuration.jsp"
		},
		new String[] {
			"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
				"edit_configuration.jsp",
			"editor.wysiwyg.portal-web.docroot.html.portlet.shopping." +
				"configuration.jsp"
		},
		new String[] {
			"field.editable.com.liferay.portal.kernel.model.User.emailAddress",
			"field.editable.user.types"
		},
		new String[] {
			"field.editable.com.liferay.portal.kernel.model.User.screenName",
			"field.editable.user.types"
		},
		new String[] {"icon.menu.max.display.items", "menu.max.display.items"},
		new String[] {
			"journal.error.template.freemarker", "journal.error.template[ftl]"
		},
		new String[] {
			"journal.error.template.velocity", "journal.error.template[vm]"
		},
		new String[] {
			"journal.error.template.xsl", "journal.error.template[xsl]"
		},
		new String[] {
			"journal.template.velocity.restricted.variables",
			"velocity.engine.restricted.variables"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.lowercase",
			"passwords.passwordpolicytoolkit.validator.charset.lowercase"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.numbers",
			"passwords.passwordpolicytoolkit.validator.charset.numbers"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.symbols",
			"passwords.passwordpolicytoolkit.validator.charset.symbols"
		},
		new String[] {
			"passwords.passwordpolicytoolkit.charset.uppercase",
			"passwords.passwordpolicytoolkit.validator.charset.uppercase"
		},
		new String[] {
			"permissions.inline.sql.resource.block.query.threshhold",
			"permissions.inline.sql.resource.block.query.threshold"
		},
		new String[] {
			"portal.instance.http.port", "portal.instance.http.socket.address"
		},
		new String[] {
			"portal.instance.https.port", "portal.instance.http.socket.address"
		},
		new String[] {
			"referer.url.domains.allowed", "redirect.url.domains.allowed"
		},
		new String[] {"referer.url.ips.allowed", "redirect.url.ips.allowed"},
		new String[] {
			"referer.url.security.mode", "redirect.url.security.mode"
		},
		new String[] {
			"tags.asset.increment.view.counter.enabled",
			"asset.entry.increment.view.counter.enabled"
		}
	};

	private static final String[][] _RENAMED_SYSTEM_KEYS = new String[][] {
		new String[] {
			"com.liferay.portal.kernel.util.StringBundler.unsafe.create." +
				"threshold",
			"com.liferay.portal.kernel.util.StringBundler.threadlocal.buffer." +
				"limit"
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyProperties.class);

}