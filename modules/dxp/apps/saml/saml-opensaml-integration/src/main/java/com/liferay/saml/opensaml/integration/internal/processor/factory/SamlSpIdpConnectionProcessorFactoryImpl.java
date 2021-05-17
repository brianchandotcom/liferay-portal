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

package com.liferay.saml.opensaml.integration.internal.processor.factory;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.SamlSpIdpConnectionFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.internal.processor.ProcessorImpl;
import com.liferay.saml.opensaml.integration.processor.SamlSpIdpConnectionProcessor;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.opensaml.integration.processor.factory.SamlSpIdpConnectionProcessorFactory;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;

import java.util.Map;
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
		extends ProcessorImpl
			<SamlSpIdpConnection, SamlSpIdpConnectionProcessorContext,
			 SamlSpIdpConnectionFieldExpressionHandler,
			 SamlSpIdpConnectionFieldExpressionHandlerRegistry>
		implements SamlSpIdpConnectionProcessor {

		public SamlSpIdpConnectionProcessorImpl(
			SamlSpIdpConnection model,
			SamlSpIdpConnectionFieldExpressionHandlerRegistry
				fieldExpressionHandlerRegistry) {

			super(model, fieldExpressionHandlerRegistry);
		}

		@Override
		public void setFileItemArray(
			String fieldExpression, FileItem[] values) {

			setValueArray(fieldExpression, FileItem.class, values);
		}

		public class SamlSpIdpConnectionBindImpl<T extends BaseModel<T>>
			extends BindImpl<T>
			implements SamlSpIdpConnectionProcessorContext.
						   SamlSpIdpConnectionBind<T> {

			public SamlSpIdpConnectionBindImpl(
				ProcessorContext processorContext, String publicIdentifier,
				Function<SamlSpIdpConnection, T> modelGetter,
				int processingIndex,
				ProcessorContext.UpdateFunction<T> updateFunction) {

				super(
					processorContext, publicIdentifier, modelGetter,
					processingIndex, updateFunction);
			}

			@Override
			public void handleFileItemArray(
				String fieldExpression,
				UnsafeBiConsumer<T, FileItem[], ?> consumer) {

				handleUnsafeObjectArray(
					fieldExpression, FileItem.class, consumer);
			}

			private Map<String, UnsafeConsumer<T, ?>> _bufferedSetters;

		}

		public class SamlSpIdpConnectionProcessorContextImpl
			extends ProcessorContextImpl
			implements SamlSpIdpConnectionProcessorContext {

			public SamlSpIdpConnectionProcessorContextImpl(String prefix) {
				super(prefix);
			}

			@Override
			public SamlSpIdpConnectionBind<SamlSpIdpConnection> bind(
				int processingIndex,
				UpdateFunction<SamlSpIdpConnection> updateFunction) {

				return new SamlSpIdpConnectionBindImpl<>(
					this, null, Function.identity(), processingIndex,
					updateFunction);
			}

			@Override
			public <T extends BaseModel<T>> SamlSpIdpConnectionBind<T> bind(
				String publicIdentifier,
				Function<SamlSpIdpConnection, T> modelGetter,
				int processingIndex, UpdateFunction<T> updateFunction) {

				return new SamlSpIdpConnectionBindImpl<>(
					this, publicIdentifier, modelGetter, processingIndex,
					updateFunction);
			}

			@Override
			public FileItem[] getFileItemArray(String fieldExpression) {
				return getValueArray(fieldExpression, FileItem.class);
			}

			@Override
			public FileItem getFileItemValue(String fieldExpression) {
				return getValue(fieldExpression, FileItem.class);
			}

		}

		@Override
		protected SamlSpIdpConnectionProcessorContext getProcessorContext(
			String prefix) {

			return new SamlSpIdpConnectionProcessorContextImpl(prefix);
		}

		private static final Log _log = LogFactoryUtil.getLog(
			ProcessorImpl.class);

	}

}