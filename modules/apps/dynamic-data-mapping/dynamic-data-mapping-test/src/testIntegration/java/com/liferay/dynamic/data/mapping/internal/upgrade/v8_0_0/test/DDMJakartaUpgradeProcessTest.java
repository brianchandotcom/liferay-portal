/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v8_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateVersionLocalService;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributeUtil;
import com.liferay.dynamic.data.mapping.service.test.BaseDDMServiceTestCase;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DDMJakartaUpgradeProcessTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		_siteAdminUser = UserTestUtil.addGroupAdminUser(group);

		PrincipalThreadLocal.setName(_siteAdminUser.getUserId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_siteAdminUser));

		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<?> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.dynamic.data.mapping.internal." +
								"upgrade.v8_0_0.DDMJakartaUpgradeProcess")) {

						_upgradeProcess = (UpgradeProcess)upgradeStep;
					}
				}
			});
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeDDMFieldAttribute() throws Throwable {
		TransactionInvokerUtil.invoke(
			TransactionConfig.Factory.create(
				Propagation.REQUIRED, new Class<?>[] {Exception.class}),
			() -> {
				DDMFieldAttribute ddmFieldAttribute = null;

				DDMFieldAttributePersistence ddmFieldAttributePersistence =
					DDMFieldAttributeUtil.getPersistence();

				try {
					ddmFieldAttribute = ddmFieldAttributePersistence.create(
						RandomTestUtil.nextLong());

					ddmFieldAttribute.setLargeAttributeValue(_JAVAX_SCRIPT);

					ddmFieldAttribute = ddmFieldAttributePersistence.update(
						ddmFieldAttribute);

					Session session =
						ddmFieldAttributePersistence.getCurrentSession();

					session.evict(ddmFieldAttribute);

					_upgradeProcess.upgrade();

					_entityCache.clearCache();
					_finderCache.clearCache();

					DDMFieldAttribute updatedDDMFieldAttribute =
						ddmFieldAttributePersistence.findByPrimaryKey(
							ddmFieldAttribute.getPrimaryKey());

					Assert.assertNotNull(updatedDDMFieldAttribute);

					Assert.assertEquals(
						_JAKARTA_SCRIPT,
						updatedDDMFieldAttribute.getLargeAttributeValue());

					return null;
				}
				finally {
					if (ddmFieldAttribute != null) {
						ddmFieldAttributePersistence.remove(ddmFieldAttribute);
					}
				}
			});
	}

	@Test
	@TestInfo("LPD-52638")
	public void testUpgradeDDMTemplate() throws Exception {
		DDMTemplate ddmTemplate = null;

		try {
			String languageId = UpgradeProcessUtil.getDefaultLanguageId(
				TestPropsValues.getCompanyId());

			ddmTemplate = _ddmTemplateLocalService.addTemplate(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				PortalUtil.getClassNameId(DDL_RECORD_CLASS_NAME), 0,
				PortalUtil.getClassNameId(DDL_RECORD_SET_CLASS_NAME),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.fromLanguageId(languageId),
					RandomTestUtil.randomString()),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_VM, _JAVAX_SCRIPT, false, false,
				null, null, ServiceContextTestUtil.getServiceContext());

			ddmTemplate = _ddmTemplateLocalService.updateTemplate(
				ddmTemplate.getUserId(), ddmTemplate.getTemplateId(),
				ddmTemplate.getClassPK(), ddmTemplate.getNameMap(),
				ddmTemplate.getDescriptionMap(), ddmTemplate.getType(),
				ddmTemplate.getMode(), ddmTemplate.getLanguage(),
				ddmTemplate.getScript(), ddmTemplate.isCacheable(),
				ServiceContextTestUtil.getServiceContext());

			_upgradeProcess.upgrade();

			_multiVMPool.clear();

			DDMTemplate updatedDDMTemplate =
				_ddmTemplateLocalService.getTemplate(
					ddmTemplate.getTemplateId());

			Assert.assertNotNull(updatedDDMTemplate);

			Assert.assertEquals(
				_JAKARTA_SCRIPT, updatedDDMTemplate.getScript());

			List<DDMTemplateVersion> ddmTemplateVersions =
				_ddmTemplateVersionLocalService.getTemplateVersions(
					updatedDDMTemplate.getTemplateId());

			Assert.assertFalse(ddmTemplateVersions.isEmpty());

			for (DDMTemplateVersion ddmTemplateVersion : ddmTemplateVersions) {
				Assert.assertEquals(
					_JAKARTA_SCRIPT, ddmTemplateVersion.getScript());
			}
		}
		finally {
			if (ddmTemplate != null) {
				_ddmTemplateLocalService.deleteTemplate(
					ddmTemplate.getTemplateId());
			}
		}
	}

	private static final String _JAKARTA_SCRIPT =
		"import jakarta.servlet.test." +
			"DDMJakartaUpgradeProcessTestConfiguration;";

	private static final String _JAVAX_SCRIPT =
		"import javax.servlet.test.DDMJakartaUpgradeProcessTestConfiguration;";

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private DDMTemplateVersionLocalService _ddmTemplateVersionLocalService;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FinderCache _finderCache;

	@Inject
	private MultiVMPool _multiVMPool;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _siteAdminUser;

	private UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}