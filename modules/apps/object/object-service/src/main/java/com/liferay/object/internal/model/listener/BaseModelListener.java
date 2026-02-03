/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener;

import com.liferay.object.definition.processor.ObjectDefinitionClassNameProcessor;
import com.liferay.portal.kernel.model.BaseModel;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
public abstract class BaseModelListener<T extends BaseModel<T>>
	extends com.liferay.portal.kernel.model.BaseModelListener<T> {

	protected long updateObjectDefinitionClassNameId(long classNameId) {
		return objectDefinitionClassNameProcessor.
			updateObjectDefinitionClassNameId(classNameId);
	}

	protected String updateObjectDefinitionReferences(String value) {
		return objectDefinitionClassNameProcessor.
			updateObjectDefinitionReferences(value);
	}

	@Reference
	protected ObjectDefinitionClassNameProcessor
		objectDefinitionClassNameProcessor;

}