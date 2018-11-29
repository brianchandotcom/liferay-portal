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

package com.liferay.portal.target.platform.indexer.internal;

import aQute.bnd.build.model.EE;
import aQute.bnd.build.model.OSGI_CORE;
import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Domain;
import aQute.bnd.osgi.Processor;
import aQute.bnd.osgi.repository.XMLResourceParser;
import aQute.bnd.osgi.resource.CapReqBuilder;
import aQute.bnd.osgi.resource.ResourceBuilder;
import aQute.bnd.osgi.resource.ResourceUtils;

import biz.aQute.resolve.ResolverValidator;

import com.liferay.portal.target.platform.indexer.IndexValidator;

import java.io.InputStream;

import java.lang.reflect.Field;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.osgi.framework.Constants;
import org.osgi.framework.namespace.BundleNamespace;
import org.osgi.framework.namespace.HostNamespace;
import org.osgi.framework.namespace.IdentityNamespace;
import org.osgi.resource.Resource;
import org.osgi.util.promise.PromiseFactory;

/**
 * @author Raymond Augé
 */
public class DefaultIndexValidator implements IndexValidator {

	public DefaultIndexValidator(List<URI> targetPlatformIndexURIs) {
		_targetPlatformIndexURIs = targetPlatformIndexURIs;
	}

	@Override
	public List<String> validate(List<URI> indexURIs) throws Exception {
		Set<String> identities = new HashSet<>();
		Set<Resource> resources = new HashSet<>();

		for (URI uri : indexURIs) {
			URL url = uri.toURL();

			try (InputStream inputStream = url.openStream()) {
				String identity = _getRepositoryIdentity(
					uri.getPath(), inputStream);

				identities.add(identity);
			}

			resources.addAll(XMLResourceParser.getResources(uri));
		}

		try (ResolverValidator resolverValidator = new ResolverValidator()) {
			ResourceBuilder resourceBuilder = new ResourceBuilder();

			resourceBuilder.addEE(EE.JavaSE_1_8);

			Domain domain = OSGI_CORE.R7_0_0.getManifest();

			resourceBuilder.addManifest(domain);

			_includeSystemBundleAlias(resourceBuilder, domain);

			_includeTargetPlatform(resourceBuilder, identities);

			resolverValidator.setSystem(resourceBuilder.build());

			List<ResolverValidator.Resolution> resolutions =
				resolverValidator.validate(resources);

			List<String> messages = new ArrayList<>();

			for (ResolverValidator.Resolution resolution : resolutions) {
				if (resolution.succeeded) {
					continue;
				}

				String message = resolution.message;

				if (message == null) {
					continue;
				}

				if (message.startsWith(_MESSAGE_PREFIX)) {
					message = message.substring(_MESSAGE_PREFIX.length());
				}

				messages.add(message);
			}

			return messages;
		}
	}

	private String _getRepositoryIdentity(String path, InputStream inputStream)
		throws XMLStreamException {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		xmlInputFactory.setProperty(
			XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
		xmlInputFactory.setProperty(
			XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);

		XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(
			inputStream);

		try {
			while (xmlStreamReader.hasNext()) {
				int eventType = xmlStreamReader.next();

				if (eventType == XMLStreamConstants.START_ELEMENT) {
					String localName = xmlStreamReader.getLocalName();

					if (localName.equals("repository")) {
						return xmlStreamReader.getAttributeValue(null, "name");
					}
				}
			}

			return path;
		}
		finally {
			xmlStreamReader.close();
		}
	}

	private void _includeSystemBundleAlias(
			ResourceBuilder resourceBuilder, Domain domain)
		throws Exception {

		CapReqBuilder capability = new CapReqBuilder(
			BundleNamespace.BUNDLE_NAMESPACE);

		capability.addAttribute(
			capability.getNamespace(), Constants.SYSTEM_BUNDLE_SYMBOLICNAME);

		Parameters parameters = domain.getExportPackage();

		Attrs attrs = parameters.get("org.osgi.framework");

		String version = attrs.get(Constants.VERSION_ATTRIBUTE);

		capability.addAttribute(
			ResourceUtils.getVersionAttributeForNamespace(
				capability.getNamespace()),
			version);

		resourceBuilder.addCapability(capability);

		capability = new CapReqBuilder(HostNamespace.HOST_NAMESPACE);

		capability.addAttribute(
			capability.getNamespace(), Constants.SYSTEM_BUNDLE_SYMBOLICNAME);
		capability.addAttribute(
			ResourceUtils.getVersionAttributeForNamespace(
				capability.getNamespace()),
			version);

		resourceBuilder.addCapability(capability);

		capability = new CapReqBuilder(IdentityNamespace.IDENTITY_NAMESPACE);

		capability.addAttribute(
			capability.getNamespace(), Constants.SYSTEM_BUNDLE_SYMBOLICNAME);
		capability.addAttribute(
			ResourceUtils.getVersionAttributeForNamespace(
				capability.getNamespace()),
			version);
		capability.addAttribute(
			IdentityNamespace.CAPABILITY_TYPE_ATTRIBUTE,
			IdentityNamespace.TYPE_BUNDLE);

		resourceBuilder.addCapability(capability);
	}

	private void _includeTargetPlatform(
			ResourceBuilder resourceBuilder, Set<String> identities)
		throws Exception {

		for (URI uri : _targetPlatformIndexURIs) {
			URL url = uri.toURL();

			try (InputStream inputStream = url.openStream()) {
				String identity = _getRepositoryIdentity(
					uri.getPath(), inputStream);

				if (identities.contains(identity)) {
					continue;
				}

				identities.add(identity);
			}

			try (XMLResourceParser xmlResourceParser = new XMLResourceParser(
					uri)) {

				List<Resource> resources = xmlResourceParser.parse();

				for (Resource resource : resources) {
					resourceBuilder.addCapabilities(
						resource.getCapabilities(null));
				}
			}
		}
	}

	private static final String _MESSAGE_PREFIX =
		"Unable to resolve <<INITIAL>> version=null: ";

	private static final Field _field;

	static {
		try {
			_field = Processor.class.getDeclaredField("promiseFactory");

			_field.setAccessible(true);

			_field.set(null, new PromiseFactory(null));

			ThreadPoolExecutor threadPoolExecutor =
				(ThreadPoolExecutor)Processor.getExecutor();

			threadPoolExecutor.setMaximumPoolSize(1);

			threadPoolExecutor.setThreadFactory(
				runnable -> {
					Thread thread = new Thread(
						runnable, "bnd-Processor-Thread");

					thread.setDaemon(true);

					return thread;
				});
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

	private final List<URI> _targetPlatformIndexURIs;

}