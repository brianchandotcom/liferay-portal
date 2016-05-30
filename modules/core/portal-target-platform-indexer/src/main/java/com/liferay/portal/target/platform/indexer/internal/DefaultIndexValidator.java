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
import aQute.bnd.osgi.repository.XMLResourceParser;
import aQute.bnd.osgi.resource.ResourceBuilder;

import biz.aQute.resolve.ResolverValidator;
import biz.aQute.resolve.ResolverValidator.Resolution;

import com.liferay.portal.target.platform.indexer.IndexValidator;

import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.osgi.resource.Resource;

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

		for (URI uri : indexURIs) {
			URL url = uri.toURL();

			try (InputStream inputStream = url.openStream()) {
				String identity = _getRepositoryIdentity(
					uri.getPath(), inputStream);

				identities.add(identity);
			}
		}

		try (ResolverValidator resolverValidator = new ResolverValidator()) {
			ResourceBuilder resourceBuilder = new ResourceBuilder();

			resourceBuilder.addEE(EE.JavaSE_1_7);
			resourceBuilder.addManifest(OSGI_CORE.R6_0_0.getManifest());

			_includeTargetPlatform(resourceBuilder, identities);

			resolverValidator.setSystem(resourceBuilder.build());

			for (URI indexURI : indexURIs) {
				resolverValidator.addRepository(indexURI);
			}

			List<String> messages = new ArrayList<>();

			List<Resolution> resolutions = resolverValidator.validate();

			for (Resolution resolution : resolutions) {
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

				if ((eventType == XMLStreamConstants.START_ELEMENT) &&
					xmlStreamReader.getLocalName().equals("repository")) {

					return xmlStreamReader.getAttributeValue(null, "name");
				}
			}

			return path;
		}
		finally {
			xmlStreamReader.close();
		}
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

			try (XMLResourceParser xmlResourceParser =
					new XMLResourceParser(uri)) {

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

	private final List<URI> _targetPlatformIndexURIs;

}