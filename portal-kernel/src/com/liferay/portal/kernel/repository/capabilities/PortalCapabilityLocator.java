/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface PortalCapabilityLocator {

	public BulkOperationCapability getBulkOperationCapability(
		DocumentRepository documentRepository);

	public CommentCapability getCommentCapability(
		DocumentRepository documentRepository);

	public ConfigurationCapability getConfigurationCapability(
		DocumentRepository documentRepository);

	public DynamicCapability getDynamicCapability(
		DocumentRepository documentRepository, String repositoryClassName);

	public FileEntryTypeCapability getFileEntryTypeCapability();

	public ProcessorCapability getProcessorCapability(
		DocumentRepository documentRepository,
		ProcessorCapability.ResourceGenerationStrategy
			resourceGenerationStrategy);

	public RelatedModelCapability getRelatedModelCapability(
		DocumentRepository documentRepository);

	public RepositoryEventTriggerCapability getRepositoryEventTriggerCapability(
		DocumentRepository documentRepository,
		RepositoryEventTrigger repositoryEventTrigger);

	public TemporaryFileEntriesCapability getTemporaryFileEntriesCapability(
		DocumentRepository documentRepository);

	public ThumbnailCapability getThumbnailCapability(
		DocumentRepository documentRepository);

	public TrashCapability getTrashCapability(
		DocumentRepository documentRepository);

	public WorkflowCapability getWorkflowCapability(
		DocumentRepository documentRepository,
		WorkflowCapability.OperationMode operationMode);

}