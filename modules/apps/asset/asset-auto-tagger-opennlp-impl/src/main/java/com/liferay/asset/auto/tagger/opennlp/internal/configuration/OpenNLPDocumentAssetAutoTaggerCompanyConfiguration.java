/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.opennlp.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Cristina González
 */
@ExtendedObjectClassDefinition(
	category = "assets", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "opennlp-auto-tag-configuration-description",
	id = "com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration",
	localization = "content/Language",
	name = "opennlp-auto-tag-configuration-name"
)
public interface OpenNLPDocumentAssetAutoTaggerCompanyConfiguration {

	/**
	 * Sets the confidence threshold for the returned tags.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = "0.1", description = "confidence-threshold-description",
		name = "confidence-threshold", required = false
	)
	public float confidenceThreshold();

	/**
	 * Sets the class names to enable auto tagging of documents using a
	 * pre-trained opennlp model.
	 *
	 * @review
	 */
	@Meta.AD(
		deflt = "", name = "enabled-class-names[opennlp]", required = false
	)
	public String[] enabledClassNames();

}