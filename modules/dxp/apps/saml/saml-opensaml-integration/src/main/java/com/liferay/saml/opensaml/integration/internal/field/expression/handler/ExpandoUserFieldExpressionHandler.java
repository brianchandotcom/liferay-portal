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

package com.liferay.saml.opensaml.integration.internal.field.expression.handler;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.exportimport.UserImporter;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"display.index:Integer=100", "prefix=expando",
		"processing.index:Integer=100"
	},
	service = UserFieldExpressionHandler.class
)
public class ExpandoUserFieldExpressionHandler
	implements UserFieldExpressionHandler {

	@Override
	public void bindProcessorContext(UserProcessorContext processorContext) {
		for (String validFieldExpression : getValidFieldExpressions()) {
			ProcessorContext.Bind<ExpandoValue> userBind =
				processorContext.bind(
					validFieldExpression,
					user -> _getExpandoValue(user, validFieldExpression),
					_processingIndex, this::_update);

			userBind.mapString(validFieldExpression, ExpandoValue::setData);
		}
	}

	@Override
	public User getLdapUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws Exception {

		return _userImporter.importUserByExpando(
			companyId, userIdentifierExpression, userIdentifier);
	}

	@Override
	public String getSectionLabel(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, DefaultUserFieldExpressionHandler.class),
			"expando-fields");
	}

	@Override
	public User getUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws PortalException {

		List<ExpandoValue> expandoValues =
			_expandoValueLocalService.getColumnValues(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				userIdentifierExpression, userIdentifier, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		if (expandoValues.size() > 1) {
			expandoValues.forEach(StringBundler::concat);

			List<Long> userIds = new ArrayList<>();

			expandoValues.forEach(
				expandoValue -> userIds.add(expandoValue.getClassPK()));

			throw new PortalException(
				StringBundler.concat(
					"User expando column \"", userIdentifierExpression,
					"\" and value \"", userIdentifier,
					"\" must match only 1 user, but it matched ", userIds));
		}

		Stream<ExpandoValue> stream = expandoValues.stream();

		return stream.map(
			ExpandoValue::getClassPK
		).map(
			_userLocalService::fetchUserById
		).findFirst(
		).orElse(
			null
		);
	}

	@Override
	public List<String> getValidFieldExpressions() {
		List<String> validExpressions = new ArrayList<>();
		long companyId = CompanyThreadLocal.getCompanyId();

		for (ExpandoColumn column :
				_expandoColumnLocalService.getDefaultTableColumns(
					companyId, User.class.getName())) {

			validExpressions.add(column.getName());
		}

		return Collections.unmodifiableList(validExpressions);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_processingIndex = GetterUtil.getInteger(
			properties.get("processing.index"));
	}

	private ExpandoValue _getExpandoValue(
		User user, String validUserFieldExpression) {

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, validUserFieldExpression,
			user.getUserId());

		if (expandoValue != null) {
			return expandoValue;
		}

		try {
			return _expandoValueLocalService.addValue(
				user.getCompanyId(), User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				validUserFieldExpression, user.getUserId(), StringPool.BLANK);
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private ExpandoValue _update(
			ExpandoValue currentExpandoValue, ExpandoValue newExpandoValue,
			ServiceContext serviceContext)
		throws PortalException {

		return _expandoValueLocalService.updateExpandoValue(newExpandoValue);
	}

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	private int _processingIndex;

	@Reference
	private UserImporter _userImporter;

	@Reference
	private UserLocalService _userLocalService;

}