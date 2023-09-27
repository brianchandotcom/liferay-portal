/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.field.expression.handler;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;

/**
 * @author Stian Sigvartsen
 */
@ProviderType
public interface FieldExpressionHandler
	<M extends BaseModel<M>, PC extends ProcessorContext<M>> {

	public void bindProcessorContext(PC processorContext);

}