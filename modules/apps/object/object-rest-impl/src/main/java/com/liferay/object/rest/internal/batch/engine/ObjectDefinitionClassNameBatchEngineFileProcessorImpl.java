/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.batch.engine;

import com.liferay.batch.engine.BatchEngineFileProcessor;
import com.liferay.object.definition.processor.ObjectDefinitionClassNameProcessor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = BatchEngineFileProcessor.class)
public class ObjectDefinitionClassNameBatchEngineFileProcessorImpl
	implements BatchEngineFileProcessor {

	@Override
	public String process(String content) {
		return _objectDefinitionClassNameProcessor.
			updateObjectDefinitionReferences(content);
	}

	@Reference
	private ObjectDefinitionClassNameProcessor
		_objectDefinitionClassNameProcessor;

}