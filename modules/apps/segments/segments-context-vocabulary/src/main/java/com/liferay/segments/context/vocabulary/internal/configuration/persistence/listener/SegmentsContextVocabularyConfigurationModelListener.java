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

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	property = "model.class.name=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration",
	service = ConfigurationModelListener.class
)
public class SegmentsContextVocabularyConfigurationModelListener
	extends BaseConfigurationModelListener {

	@Override
	protected Class<?> getConfigurationClass() {
		return SegmentsContextVocabularyConfiguration.class;
	}

}