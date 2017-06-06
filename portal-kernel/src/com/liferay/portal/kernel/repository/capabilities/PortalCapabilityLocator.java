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

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.repository.DocumentRepository;

/**
 * @author Adolfo Pérez
 */
public interface PortalCapabilityLocator {

	public BulkOperationCapability getBulkOperationCapability(
		DocumentRepository documentRepository);

	public CommentCapability getCommentCapability(
		DocumentRepository documentRepository);

	public ConfigurationCapability getConfigurationCapability(
		DocumentRepository documentRepository);

	public ProcessorCapability getProcessorCapability(
		DocumentRepository documentRepository,
		ProcessorCapability.ResourceGenerationStrategy
			resourceGenerationStrategy);

	public RelatedModelCapability getRelatedModelCapability(
		DocumentRepository documentRepository);

	public SyncCapability getSyncCapability(
		DocumentRepository documentRepository);

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