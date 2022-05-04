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

package com.liferay.vldap.server.internal;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactory;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageService;
import com.liferay.portal.kernel.service.ImageServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.util.PortletPropsKeys;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import org.mockito.Mockito;

/**
 * @author William Newbury
 */
public abstract class BaseVLDAPTestCase {

	@Before
	public void setUp() throws Exception {
		_setUpPortal();
		_setUpConfiguration();
		_setUpCompany();
		_setUpORM();
		setUpPropsUtil();
		_setUpSearchBase();
	}

	@After
	public void tearDown() {
		for (Class<?> serviceUtilClass : serviceUtilClasses) {
			ReflectionTestUtil.setFieldValue(
				serviceUtilClass, "_service", null);
		}
	}

	protected <T> T getMockPortalService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		serviceUtilClasses.add(serviceUtilClass);

		T serviceMock = Mockito.mock(serviceClass);

		ReflectionTestUtil.setFieldValue(
			serviceUtilClass, "_service", serviceMock);

		return serviceMock;
	}

	protected void setUpPasswordPolicy(PasswordPolicy passwordPolicy) {
		Mockito.when(
			passwordPolicy.getGraceLimit()
		).thenReturn(
			GRACE_LIMIT
		);

		Mockito.when(
			passwordPolicy.getHistoryCount()
		).thenReturn(
			HISTORY_COUNT
		);

		Mockito.when(
			passwordPolicy.getLockoutDuration()
		).thenReturn(
			LOCKOUT_DURATION
		);

		Mockito.when(
			passwordPolicy.getMaxAge()
		).thenReturn(
			MAX_AGE
		);

		Mockito.when(
			passwordPolicy.getMinAge()
		).thenReturn(
			MIN_AGE
		);

		Mockito.when(
			passwordPolicy.getResetFailureCount()
		).thenReturn(
			RESET_FAILURE_COUNT
		);

		Mockito.when(
			passwordPolicy.isExpireable()
		).thenReturn(
			false
		);

		Mockito.when(
			passwordPolicy.isLockout()
		).thenReturn(
			true
		);

		Mockito.when(
			passwordPolicy.isRequireUnlock()
		).thenReturn(
			true
		);
	}

	protected void setUpPortalUtil() {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getClassNameId(Mockito.any(Class.class))
		).thenReturn(
			PRIMARY_KEY
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(portal);
	}

	protected void setUpPropsUtil() {
		props = Mockito.mock(Props.class);

		PropsUtil.setProps(props);

		Mockito.when(
			props.get(PortletPropsKeys.SEARCH_MAX_SIZE)
		).thenReturn(
			"42"
		);
	}

	protected static final int GRACE_LIMIT = 7200000;

	protected static final int HISTORY_COUNT = 3600000;

	protected static final long LOCKOUT_DURATION = 7200000;

	protected static final long MAX_AGE = 14400000;

	protected static final long MIN_AGE = 3600000;

	protected static final long PRIMARY_KEY = 42;

	protected static final long RESET_FAILURE_COUNT = 3600000;

	protected final List<Company> companies = new ArrayList<>();
	protected Company company;
	protected GroupLocalService groupLocalService;
	protected ImageService imageService;
	protected OrganizationLocalService organizationLocalService;
	protected Props props;
	protected RoleLocalService roleLocalService;
	protected SearchBase searchBase;
	protected final List<Class<?>> serviceUtilClasses = new ArrayList<>();
	protected UserGroupLocalService userGroupLocalService;
	protected UserLocalService userLocalService;

	private void _setUpCompany() throws Exception {
		company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			company.getWebId()
		).thenReturn(
			"liferay.com"
		);

		companies.add(company);

		CompanyLocalService companyLocalService = getMockPortalService(
			CompanyLocalServiceUtil.class, CompanyLocalService.class);

		Mockito.when(
			companyLocalService.getCompanies()
		).thenReturn(
			companies
		);

		Mockito.when(
			companyLocalService.getCompanies(Mockito.anyBoolean())
		).thenReturn(
			companies
		);

		Mockito.when(
			companyLocalService.getCompanyByWebId(Mockito.eq("liferay.com"))
		).thenReturn(
			company
		);
	}

	private void _setUpConfiguration() {
		PortletClassLoaderUtil.setServletContextName("vldap-server");

		Thread currentThread = Thread.currentThread();

		ClassLoaderPool.register(
			"vldap-server", currentThread.getContextClassLoader());

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getArray(PortletPropsKeys.SAMBA_DOMAIN_NAMES)
		).thenReturn(
			new String[] {"testDomainName"}
		);

		Mockito.when(
			configuration.getArray(PortletPropsKeys.SAMBA_HOSTS_ALLOWED)
		).thenReturn(
			new String[0]
		);

		ConfigurationFactory configurationFactory = Mockito.mock(
			ConfigurationFactory.class);

		Mockito.when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("portlet"))
		).thenReturn(
			configuration
		);

		Mockito.when(
			configurationFactory.getConfiguration(
				Mockito.any(ClassLoader.class), Mockito.eq("service"))
		).thenReturn(
			configuration
		);

		ConfigurationFactoryUtil.setConfigurationFactory(configurationFactory);
	}

	private void _setUpORM() {
		Criterion criterion = Mockito.mock(Criterion.class);

		DynamicQuery dynamicQuery = Mockito.mock(DynamicQuery.class);

		DynamicQueryFactory dynamicQueryFactory = Mockito.mock(
			DynamicQueryFactory.class);

		Mockito.when(
			dynamicQueryFactory.forClass(
				Mockito.any(Class.class), Mockito.any(ClassLoader.class))
		).thenReturn(
			dynamicQuery
		);

		DynamicQueryFactoryUtil dynamicQueryFactoryUtil =
			new DynamicQueryFactoryUtil();

		dynamicQueryFactoryUtil.setDynamicQueryFactory(dynamicQueryFactory);

		RestrictionsFactory restrictionsFactory = Mockito.mock(
			RestrictionsFactory.class);

		Mockito.when(
			restrictionsFactory.eq(
				Mockito.anyString(), Mockito.any(Object.class))
		).thenReturn(
			criterion
		);

		Mockito.when(
			restrictionsFactory.ilike(
				Mockito.anyString(), Mockito.any(Object.class))
		).thenReturn(
			criterion
		);

		RestrictionsFactoryUtil restrictionsFactoryUtil =
			new RestrictionsFactoryUtil();

		restrictionsFactoryUtil.setRestrictionsFactory(restrictionsFactory);
	}

	private void _setUpPortal() {
		groupLocalService = getMockPortalService(
			GroupLocalServiceUtil.class, GroupLocalService.class);
		imageService = getMockPortalService(
			ImageServiceUtil.class, ImageService.class);
		organizationLocalService = getMockPortalService(
			OrganizationLocalServiceUtil.class, OrganizationLocalService.class);
		roleLocalService = getMockPortalService(
			RoleLocalServiceUtil.class, RoleLocalService.class);
		userGroupLocalService = getMockPortalService(
			UserGroupLocalServiceUtil.class, UserGroupLocalService.class);
		userLocalService = getMockPortalService(
			UserLocalServiceUtil.class, UserLocalService.class);
	}

	private void _setUpSearchBase() {
		searchBase = Mockito.mock(SearchBase.class);

		Mockito.when(
			searchBase.getCompanies()
		).thenReturn(
			companies
		);

		Mockito.when(
			searchBase.getSizeLimit()
		).thenReturn(
			PRIMARY_KEY
		);

		Mockito.when(
			searchBase.getTop()
		).thenReturn(
			"Liferay"
		);
	}

}