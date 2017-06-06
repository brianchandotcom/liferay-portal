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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.CommentCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.util.CacheResourceBundleLoader;
import com.liferay.portal.kernel.util.ClassResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.chemistry.opencmis.client.api.Document;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return ResourceBundleUtil.getString(
			resourceBundle, _MODEL_RESOURCE_NAME_PREFIX + getClassName());
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		DocumentRepository documentRepository = capabilityRegistry.getTarget();

		PortalCapabilityLocator portalCapabilityLocator =
			getPortalCapabilityLocator();

		capabilityRegistry.addExportedCapability(
			CommentCapability.class,
			portalCapabilityLocator.getCommentCapability(documentRepository));
		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class,
			new RefreshingProcessorCapability(
				portalCapabilityLocator.getProcessorCapability(
					documentRepository)));
	}

	protected PortalCapabilityLocator getPortalCapabilityLocator() {
		return _portalCapabilityLocator;
	}

	protected ResourceBundleLoader getResourceBundleLoader() {
		return _resourceBundleLoader;
	}

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	@Reference
	private PortalCapabilityLocator _portalCapabilityLocator;

	private final ResourceBundleLoader _resourceBundleLoader =
		new CacheResourceBundleLoader(
			new ClassResourceBundleLoader(
				"content.Language", BaseCMISRepositoryDefiner.class));

	private static class RefreshingProcessorCapability
		implements ProcessorCapability {

		public RefreshingProcessorCapability(
			ProcessorCapability processorCapability) {

			_processorCapability = processorCapability;
		}

		@Override
		public void cleanUp(FileEntry fileEntry) throws PortalException {
			_refresh(fileEntry);

			_processorCapability.cleanUp(fileEntry);
		}

		@Override
		public void cleanUp(FileVersion fileVersion) throws PortalException {
			_refresh(fileVersion);

			_processorCapability.cleanUp(fileVersion);
		}

		@Override
		public void copy(FileEntry fileEntry, FileVersion fileVersion)
			throws PortalException {

			_refresh(fileEntry);
			_refresh(fileVersion);

			_processorCapability.copy(fileEntry, fileVersion);
		}

		@Override
		public void generateNew(FileEntry fileEntry) throws PortalException {
			_refresh(fileEntry);

			_processorCapability.generateNew(fileEntry);
		}

		private void _refresh(FileEntry fileEntry) {
			Document document = (Document)fileEntry.getModel();

			document.refresh();
		}

		private void _refresh(FileVersion fileVersion) throws PortalException {
			Document document = (Document)fileVersion.getModel();

			document.refresh();

			_refresh(fileVersion.getFileEntry());
		}

		private final ProcessorCapability _processorCapability;

	}

}