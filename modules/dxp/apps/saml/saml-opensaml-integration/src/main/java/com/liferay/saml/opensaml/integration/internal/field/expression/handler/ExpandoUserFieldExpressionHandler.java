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
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.ldap.LDAPSettings;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.SafeLdapContext;
import com.liferay.portal.security.ldap.SafeLdapFilter;
import com.liferay.portal.security.ldap.SafeLdapFilterConstraints;
import com.liferay.portal.security.ldap.SafeLdapFilterFactory;
import com.liferay.portal.security.ldap.SafeLdapNameFactory;
import com.liferay.portal.security.ldap.SafePortalLDAP;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;
import com.liferay.portal.security.ldap.util.LDAPUtil;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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
	public void bindProcessorContext(
		UserProcessorContext userProcessorContext) {

		for (String validFieldExpression : getValidFieldExpressions()) {
			if (Validator.isBlank(
					userProcessorContext.getValue(
						String.class, validFieldExpression))) {

				continue;
			}

			ProcessorContext.Bind<ExpandoValue> userBind =
				userProcessorContext.bind(
					user -> _getExpandoValue(user, validFieldExpression),
					_processingIndex, validFieldExpression, this::_update);

			userBind.mapString(validFieldExpression, ExpandoValue::setData);
		}
	}

	@Override
	public User getLdapUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws Exception {

		Collection<LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurationProvider.getConfigurations(companyId);

		for (LDAPServerConfiguration ldapServerConfiguration :
				ldapServerConfigurations) {

			String providerUrl = ldapServerConfiguration.baseProviderURL();

			if (Validator.isNull(providerUrl)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No provider URL defined in " +
							ldapServerConfiguration);
				}

				continue;
			}

			User user = _getLdapUser(
				ldapServerConfiguration.ldapServerId(), companyId,
				userIdentifier, userIdentifierExpression);

			if (user != null) {
				return user;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"User with the expando field ", userIdentifierExpression,
					"=", userIdentifier, " was not found in any LDAP servers"));
		}

		return null;
	}

	@Override
	public String getSectionLabel(Locale locale) {
		return ResourceBundleUtil.getString(
			ResourceBundleUtil.getBundle(
				locale, DefaultUserFieldExpressionHandler.class),
			"user-custom-fields");
	}

	@Override
	public User getUser(
			long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws PortalException {

		if (userIdentifier == null) {
			return null;
		}

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

	@Override
	public boolean isSupportedForUserMatching(String userIdentifier) {
		return true;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_processingIndex = GetterUtil.getInteger(
			properties.get("processing.index"));
	}

	@Reference(
		target = "(factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration)",
		unbind = "-"
	)
	protected void setLDAPServerConfigurationProvider(
		ConfigurationProvider<LDAPServerConfiguration>
			ldapServerConfigurationProvider) {

		_ldapServerConfigurationProvider = ldapServerConfigurationProvider;
	}

	private ExpandoValue _getExpandoValue(
		User user, String validUserFieldExpression) {

		ExpandoValue expandoValue = _expandoValueLocalService.getValue(
			user.getCompanyId(), User.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, validUserFieldExpression,
			user.getUserId());

		if (expandoValue == null) {
			ExpandoTable table = null;

			try {
				table = _expandoTableLocalService.getTable(
					user.getCompanyId(), User.class.getName(),
					ExpandoTableConstants.DEFAULT_TABLE_NAME);
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}

			ExpandoColumn column = _expandoColumnLocalService.getColumn(
				table.getTableId(), validUserFieldExpression);

			expandoValue = _expandoValueLocalService.createExpandoValue(0);

			expandoValue.setCompanyId(user.getCompanyId());
			expandoValue.setClassName(User.class.getName());
			expandoValue.setColumnId(column.getColumnId());
			expandoValue.setClassPK(user.getUserId());
		}

		return expandoValue;
	}

	private User _getLdapUser(
			long ldapServerId, long companyId, String userIdentifier,
			String userIdentifierExpression)
		throws Exception {

		Properties userExpandoMappings = _ldapSettings.getUserExpandoMappings(
			ldapServerId, companyId);

		String attributeName = GetterUtil.getString(
			userExpandoMappings.getProperty(userIdentifierExpression));

		if (Validator.isBlank(attributeName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"User expando field ", userIdentifierExpression,
						" is not mapped for LDAP server ", ldapServerId));
			}

			return null;
		}

		SafeLdapContext safeLdapContext = null;

		NamingEnumeration<SearchResult> enumeration = null;

		try {
			LDAPServerConfiguration ldapServerConfiguration =
				_ldapServerConfigurationProvider.getConfiguration(
					companyId, ldapServerId);

			safeLdapContext = _safePortalLDAP.getSafeLdapContext(
				ldapServerId, companyId);

			if (safeLdapContext == null) {
				_log.error("Unable to bind to the LDAP server");

				return null;
			}

			if (ldapServerConfiguration.ldapServerId() != ldapServerId) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"LDAP server ID ", ldapServerId,
							" is no longer valid, company ", companyId,
							" now uses ",
							ldapServerConfiguration.ldapServerId()));
				}

				return null;
			}

			SafeLdapFilter safeLdapFilter = null;

			try {
				safeLdapFilter = SafeLdapFilterFactory.fromUnsafeFilter(
					ldapServerConfiguration.userSearchFilter(),
					_ldapFilterValidator);
			}
			catch (LDAPFilterException ldapFilterException) {
				throw new LDAPFilterException(
					"Invalid user search filter: ", ldapFilterException);
			}

			safeLdapFilter = safeLdapFilter.and(
				SafeLdapFilterConstraints.eq(attributeName, userIdentifier));

			Properties userMappings = _ldapSettings.getUserMappings(
				ldapServerId, companyId);

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName"));

			userMappingsScreenName = StringUtil.toLowerCase(
				userMappingsScreenName);

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0,
				new String[] {userMappingsScreenName}, false, false);

			enumeration = safeLdapContext.search(
				LDAPUtil.getBaseDNSafeLdapName(ldapServerConfiguration),
				safeLdapFilter, searchControls);

			if (enumeration.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				Binding binding = enumeration.nextElement();

				Attributes attributes = _safePortalLDAP.getUserAttributes(
					ldapServerId, companyId, safeLdapContext,
					SafeLdapNameFactory.from(binding));

				return _ldapUserImporter.importUser(
					ldapServerId, companyId, safeLdapContext, attributes, null);
			}

			return null;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Problem accessing LDAP server " + exception.getMessage());
			}

			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			throw new SystemException(
				"Problem accessing LDAP server " + exception.getMessage());
		}
		finally {
			if (enumeration != null) {
				enumeration.close();
			}

			if (safeLdapContext != null) {
				safeLdapContext.close();
			}
		}
	}

	private ExpandoValue _update(
			ExpandoValue currentExpandoValue, ExpandoValue newExpandoValue,
			ServiceContext serviceContext)
		throws PortalException {

		if (!newExpandoValue.isNew()) {
			return _expandoValueLocalService.updateExpandoValue(
				newExpandoValue);
		}

		ExpandoColumn column = _expandoColumnLocalService.getColumn(
			newExpandoValue.getColumnId());

		return _expandoValueLocalService.addValue(
			newExpandoValue.getCompanyId(), User.class.getName(),
			ExpandoTableConstants.DEFAULT_TABLE_NAME, column.getName(),
			newExpandoValue.getClassPK(), newExpandoValue.getData());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoUserFieldExpressionHandler.class);

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private LDAPFilterValidator _ldapFilterValidator;

	private ConfigurationProvider<LDAPServerConfiguration>
		_ldapServerConfigurationProvider;

	@Reference
	private LDAPSettings _ldapSettings;

	@Reference
	private LDAPUserImporter _ldapUserImporter;

	private int _processingIndex;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SafePortalLDAP _safePortalLDAP;

	@Reference
	private UserLocalService _userLocalService;

}