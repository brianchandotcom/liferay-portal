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
import com.liferay.portal.kernel.util.OrderedProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.opensaml.integration.field.expression.handler.SamlSpIdpConnectionFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.SamlSpIdpConnectionProcessorContext;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.web.internal.exception.UserAttributeMappingException;
import com.liferay.saml.web.internal.exception.UserIdentifierExpressionException;

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
		SamlSpIdpConnectionProcessorContext
			samlSpIdpConnectionProcessorContext) {

		SamlSpIdpConnectionProcessorContext.SamlSpIdpConnectionBind
			<SamlSpIdpConnection> fieldExpressionMapper =
				samlSpIdpConnectionProcessorContext.bind(
					_processingIndex, this::_update);

		fieldExpressionMapper.mapUnsafeString(
			"userAttributeMappingsPrefixes",
			(samlSpIdpConnection, prefixes) -> {
				Properties userAttributeMappingsProperties =
					new OrderedProperties();

				for (String prefix : StringUtil.split(prefixes)) {
					_populate(
						prefix, samlSpIdpConnection,
						samlSpIdpConnectionProcessorContext,
						userAttributeMappingsProperties);
				}

				_populate(
					StringPool.BLANK, samlSpIdpConnection,
					samlSpIdpConnectionProcessorContext,
					userAttributeMappingsProperties);

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

	private void _populate(
			String prefix, SamlSpIdpConnection samlSpIdpConnection,
			SamlSpIdpConnectionProcessorContext
				samlSpIdpConnectionProcessorContext,
			Properties userAttributeMappingsProperties)
		throws PortalException {

		List<String> indexes = StringUtil.split(
			samlSpIdpConnectionProcessorContext.getValue(
				String.class, prefix + ":userAttributeMappingsIndexes"));

		Set<String> userFieldExpressions = new HashSet<>();

		for (String index : indexes) {
			String fieldExpression = GetterUtil.getString(
				samlSpIdpConnectionProcessorContext.getValue(
					String.class,
					prefix + ":userAttributeMappingFieldExpression-" + index));

			String attributeName = GetterUtil.getString(
				samlSpIdpConnectionProcessorContext.getValue(
					String.class,
					prefix + ":userAttributeMappingSamlAttribute-" + index));

			if (!userFieldExpressions.add(fieldExpression)) {
				throw new UserAttributeMappingException(
					prefix, fieldExpression, attributeName,
					UserAttributeMappingException.ErrorType.
						DUPLICATE_FIELD_EXPRESSION);
			}

			if (Validator.isBlank(attributeName) &&
				Validator.isBlank(fieldExpression)) {

				continue;
			}

			if (Validator.isBlank(attributeName) ||
				Validator.isBlank(fieldExpression)) {

				throw new UserAttributeMappingException(
					prefix, fieldExpression, attributeName,
					UserAttributeMappingException.ErrorType.INVALID_MAPPING);
			}

			if (!Validator.isBlank(prefix)) {
				fieldExpression = prefix + ":" + fieldExpression;
			}

			if (Objects.equals(
					index,
					samlSpIdpConnectionProcessorContext.getValue(
						String.class, "userIdentifierExpressionIndex")) &&
				Objects.equals(
					prefix,
					samlSpIdpConnectionProcessorContext.getValue(
						String.class, "userIdentifierExpressionPrefix"))) {

				samlSpIdpConnection.setUserIdentifierExpression(
					"attribute:" + fieldExpression);
			}

			userAttributeMappingsProperties.put(attributeName, fieldExpression);
		}
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