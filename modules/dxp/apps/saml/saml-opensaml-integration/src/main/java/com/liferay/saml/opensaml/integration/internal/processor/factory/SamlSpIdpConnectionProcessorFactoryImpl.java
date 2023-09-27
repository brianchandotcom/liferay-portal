/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.processor.factory;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.internal.processor.BaseProcessorImpl;
import com.liferay.saml.opensaml.integration.processor.SamlSpIdpConnectionProcessor;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.opensaml.integration.processor.factory.SamlSpIdpConnectionProcessorFactory;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(service = SamlSpIdpConnectionProcessorFactory.class)
public class SamlSpIdpConnectionProcessorFactoryImpl
	implements SamlSpIdpConnectionProcessorFactory {

	@Override
	public SamlSpIdpConnectionProcessor create(
		SamlSpIdpConnection samlSpIdpConnection,
		SamlSpIdpConnectionFieldExpressionHandlerRegistry
			samlSpIdpConnectionFieldExpressionHandlerRegistry) {

		return new SamlSpIdpConnectionProcessorImpl(
			samlSpIdpConnection,
			samlSpIdpConnectionFieldExpressionHandlerRegistry);
	}

	public static class SamlSpIdpConnectionProcessorImpl
		extends BaseProcessorImpl
			<SamlSpIdpConnection, SamlSpIdpConnectionProcessorContext,
			 SamlSpIdpConnectionFieldExpressionHandler,
			 SamlSpIdpConnectionFieldExpressionHandlerRegistry>
		implements SamlSpIdpConnectionProcessor {

		public SamlSpIdpConnectionProcessorImpl(
			SamlSpIdpConnection samlSpIdpConnection,
			SamlSpIdpConnectionFieldExpressionHandlerRegistry
				fieldExpressionHandlerRegistry) {

			super(samlSpIdpConnection, fieldExpressionHandlerRegistry);
		}

		@Override
		public void setFileItemArray(
			String fieldExpression, FileItem[] fileItems) {

			setValueArray(FileItem.class, fieldExpression, fileItems);
		}

		public class SamlSpIdpConnectionBindImpl<T extends BaseModel<T>>
			extends BindImpl<T>
			implements SamlSpIdpConnectionProcessorContext.
						   SamlSpIdpConnectionBind<T> {

			public SamlSpIdpConnectionBindImpl(
				Function<SamlSpIdpConnection, T> modelGetterFunction,
				int processingIndex, ProcessorContext processorContext,
				String publicIdentifier,
				ProcessorContext.UpdateFunction<T> updateFunction) {

				super(
					modelGetterFunction, processingIndex, processorContext,
					publicIdentifier, updateFunction);
			}

			@Override
			public void handleFileItemArray(
				String fieldExpression,
				UnsafeBiConsumer<T, FileItem[], ?> unsafeBiConsumer) {

				handleUnsafeObjectArray(
					fieldExpression, FileItem.class, unsafeBiConsumer);
			}

		}

		public class SamlSpIdpConnectionProcessorContextImpl
			extends ProcessorContextImpl
			implements SamlSpIdpConnectionProcessorContext {

			public SamlSpIdpConnectionProcessorContextImpl(String prefix) {
				super(prefix);
			}

			@Override
			public <T extends BaseModel<T>> SamlSpIdpConnectionBind<T> bind(
				Function<SamlSpIdpConnection, T> modelGetterFunction,
				int processingIndex, String publicIdentifier,
				UpdateFunction<T> updateFunction) {

				return new SamlSpIdpConnectionBindImpl<>(
					modelGetterFunction, processingIndex, this,
					publicIdentifier, updateFunction);
			}

			@Override
			public SamlSpIdpConnectionBind<SamlSpIdpConnection> bind(
				int processingIndex,
				UpdateFunction<SamlSpIdpConnection> updateFunction) {

				return new SamlSpIdpConnectionBindImpl<>(
					Function.identity(), processingIndex, this, null,
					updateFunction);
			}

			@Override
			public FileItem[] getFileItemArray(String fieldExpression) {
				return getValueArray(FileItem.class, fieldExpression);
			}

			@Override
			public FileItem getFileItemValue(String fieldExpression) {
				return getValue(FileItem.class, fieldExpression);
			}

		}

		@Override
		protected SamlSpIdpConnectionProcessorContext getProcessorContext(
			String prefix) {

			return new SamlSpIdpConnectionProcessorContextImpl(prefix);
		}

		private static final Log _log = LogFactoryUtil.getLog(
			SamlSpIdpConnectionProcessorImpl.class);

	}

}