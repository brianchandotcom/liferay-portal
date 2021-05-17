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

package com.liferay.saml.web.internal.opensaml.integration.field.expression.handler;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.web.internal.UserAttributeMappingException;
import com.liferay.saml.web.internal.UserIdentifierExpressionException;

import java.io.StringWriter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {"prefix=attribute", "processing.index:Integer=100"},
	service = SamlSpIdpConnectionFieldExpressionHandler.class
)
public class AttributeMappingSamlSpIdpConnectionFieldExpressionHandler
	implements SamlSpIdpConnectionFieldExpressionHandler {

	@Override
	public void bindProcessorContext(
		SamlSpIdpConnectionProcessorContext processorContext) {

		SamlSpIdpConnectionProcessorContext.SamlSpIdpConnectionBind
			<SamlSpIdpConnection> fieldExpressionMapper = processorContext.bind(
				_processingIndex, this::_update);

		fieldExpressionMapper.mapUnsafeString(
			"userAttributeMappingsPrefixes",
			(samlSpIdpConnection, prefixes) -> {
				Properties userAttributeMappingsProperties = new Properties();

				List<String> split = StringUtil.split(prefixes);

				split.add(StringPool.BLANK);

				for (String prefix : split) {
					List<String> indexesString = StringUtil.split(
						processorContext.getValue(
							String.class,
							prefix + ":userAttributeMappingsIndexes"
						));

					Set<String> userFieldExpressions = new HashSet<>();

					for (String index : indexesString.toArray(new String[0])) {
						String fieldExpression = GetterUtil.getString(
							processorContext.getValue(
								String.class, prefix +
											  ":userAttributeMappingFieldExpression-" +
											  index
							));

						String attributeName = GetterUtil.getString(
							processorContext.getValue(
								String.class,
								prefix + ":userAttributeMappingSamlAttribute-" +
								index
							));

						if (!userFieldExpressions.add(fieldExpression)) {
							throw new UserAttributeMappingException(
								prefix, fieldExpression, attributeName,
								UserAttributeMappingException.ErrorType.
									DUPLICATE_FIELD_EXPRESSION);
						}

						if (fieldExpression.equals(StringPool.BLANK) &&
							attributeName.equals(StringPool.BLANK)) {

							continue;
						}

						if (fieldExpression.equals(StringPool.BLANK) ||
							attributeName.equals(StringPool.BLANK)) {

							throw new UserAttributeMappingException(
								prefix, fieldExpression, attributeName,
								UserAttributeMappingException.ErrorType.
									INVALID_MAPPING);
						}

						if (!Validator.isBlank(prefix)) {
							fieldExpression = prefix + ":" + fieldExpression;
						}

						userAttributeMappingsProperties.put(
							attributeName, fieldExpression);

						String prefix2 = processorContext.getValue(
							String.class, "userIdentifierExpressionPrefix");

						String index2 = processorContext.getValue(
							String.class, "userIdentifierExpressionIndex");

						if (Objects.equals(index, index2) &&
							Objects.equals(prefix, prefix2)) {

							samlSpIdpConnection.setUserIdentifierExpression(
								"attribute:" + fieldExpression);
						}
					}
				}

				try (StringWriter stringWriter = new StringWriter()) {
					userAttributeMappingsProperties.store(stringWriter, null);

					samlSpIdpConnection.setUserAttributeMappings(
						stringWriter.toString());
				}
			});
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_processingIndex = GetterUtil.getInteger(
			properties.get("processing.index"));
	}

	private SamlSpIdpConnection _update(
			SamlSpIdpConnection currentUser, SamlSpIdpConnection newUser,
			ServiceContext serviceContext)
		throws PortalException {

		if (Objects.equals(
				newUser.getUserIdentifierExpression(), "attribute")) {

			throw new UserIdentifierExpressionException(
				"No attribute mapping selected for user matching");
		}

		return newUser;
	}

	private int _processingIndex;

}