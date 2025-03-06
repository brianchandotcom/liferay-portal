/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;

import java.util.function.BiFunction;

/**
 * @author Shuyang Zhou
 */
public class DDMTemplateModelListener extends BaseModelListener<DDMTemplate> {

	public DDMTemplateModelListener(
		BiFunction<String, String, String> textReplacerBiFunction) {

		_textReplacerBiFunction = textReplacerBiFunction;
	}

	@Override
	public void onBeforeCreate(DDMTemplate ddmTemplate)
		throws ModelListenerException {

		_jakartaTransformScript(ddmTemplate);
	}

	@Override
	public void onBeforeUpdate(
			DDMTemplate originalDDMTemplate, DDMTemplate ddmTemplate)
		throws ModelListenerException {

		_jakartaTransformScript(ddmTemplate);
	}

	private void _jakartaTransformScript(DDMTemplate ddmTemplate) {
		ddmTemplate.setScript(
			_textReplacerBiFunction.apply(
				"DDMTemplate#Script#" + ddmTemplate.getTemplateId(),
				ddmTemplate.getScript()));
	}

	private final BiFunction<String, String, String> _textReplacerBiFunction;

}