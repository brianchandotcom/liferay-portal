/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyCompanyConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Yurena Cabrera
 */
@Component(
	property = "model.class.name=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyCompanyConfiguration",
	service = ConfigurationModelListener.class
)
public class SegmentsContextVocabularyCompanyConfigurationModelListener
	extends BaseConfigurationModelListener {

	@Override
	protected Class<?> getConfigurationClass() {
		return SegmentsContextVocabularyCompanyConfiguration.class;
	}

}