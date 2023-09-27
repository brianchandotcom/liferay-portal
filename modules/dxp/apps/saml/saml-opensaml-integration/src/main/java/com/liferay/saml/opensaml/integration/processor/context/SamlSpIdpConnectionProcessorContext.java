/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor.context;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import java.util.function.Function;

/**
 * @author Stian Sigvartsen
 */
public interface SamlSpIdpConnectionProcessorContext
	extends ProcessorContext<SamlSpIdpConnection> {

	@Override
	public <T extends BaseModel<T>> SamlSpIdpConnectionBind<T> bind(
		Function<SamlSpIdpConnection, T> modelGetterFunction,
		int processingIndex, String publicIdentifier,
		UpdateFunction<T> updateFunction);

	@Override
	public SamlSpIdpConnectionBind<SamlSpIdpConnection> bind(
		int processingIndex,
		UpdateFunction<SamlSpIdpConnection> updateFunction);

	public FileItem[] getFileItemArray(String fieldExpression);

	public FileItem getFileItemValue(String fieldExpression);

	public interface SamlSpIdpConnectionBind<T extends BaseModel<T>>
		extends Bind<T> {

		public void handleFileItemArray(
			String fieldExpression,
			UnsafeBiConsumer<T, FileItem[], ?> unsafeBiConsumer);

	}

}