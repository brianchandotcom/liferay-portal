/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.configuration.ReindexConfiguration",
	localization = "content/Language", name = "reindex-configuration-name"
)
@ProviderType
public interface ReindexConfiguration {

	@Meta.AD(
		deflt = "full", description = "default-reindex-execution-mode-help",
		name = "default-reindex-execution-mode", required = false
	)
	public String defaultReindexExecutionMode();

	@Meta.AD(
		deflt = "com.liferay.document.library.kernel.model.DLFileEntry=500",
		description = "indexing-batch-sizes-help",
		name = "indexing-batch-sizes", required = false
	)
	public String[] indexingBatchSizes();

	@ExtendedAttributeDefinition(featureFlagKey = "LPS-183672")
	@Meta.AD(
		deflt = "false",
		description = "index-actions-in-all-virtual-instances-enabled-help",
		name = "index-actions-in-all-virtual-instances-enabled",
		required = false
	)
	public boolean indexActionsInAllVirtualInstancesEnabled();

	@ExtendedAttributeDefinition(featureFlagKey = "LPS-183672")
	@Meta.AD(
		deflt = "",
		description = "enable-index-actions-in-a-virtual-instance-help",
		name = "enable-index-actions-in-a-virtual-instance", required = false
	)
	public String[] indexActionsVirtualInstance();

}