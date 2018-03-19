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

package com.liferay.talend.runtime;

import com.liferay.talend.runtime.writer.LiferayWriteOperation;
import com.liferay.talend.tliferayoutput.TLiferayOutputProperties;

import org.talend.components.api.component.runtime.Sink;
import org.talend.components.api.component.runtime.WriteOperation;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.exception.ComponentException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;

/**
 * @author Zoltán Takács
 */
public class LiferaySink extends LiferaySourceOrSink implements Sink {

	@Override
	public WriteOperation<?> createWriteOperation() {
		if (liferayConnectionPropertiesProvider instanceof
				TLiferayOutputProperties) {

			return new LiferayWriteOperation(
				this,
				(TLiferayOutputProperties)liferayConnectionPropertiesProvider);
		}

		Class<?> propertiesClass =
			liferayConnectionPropertiesProvider.getClass();

		throw new ComponentException(
			new RuntimeException(
				i18nMessages.getMessage(
					"error.validation.properties",
					propertiesClass.getCanonicalName())));
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		ValidationResult validate = super.validate(runtimeContainer);

		if (validate.getStatus() != Result.ERROR) {
			Class<?> propertiesClass =
				liferayConnectionPropertiesProvider.getClass();

			if (!(liferayConnectionPropertiesProvider instanceof
					TLiferayOutputProperties)) {

				return new ValidationResult(
					Result.ERROR,
					i18nMessages.getMessage(
						"error.validation.properties",
						propertiesClass.getCanonicalName()));
			}
		}

		return validate;
	}

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(LiferaySink.class);

}