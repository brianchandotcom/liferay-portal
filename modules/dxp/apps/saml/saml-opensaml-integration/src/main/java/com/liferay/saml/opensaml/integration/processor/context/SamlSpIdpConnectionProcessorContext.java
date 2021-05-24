/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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