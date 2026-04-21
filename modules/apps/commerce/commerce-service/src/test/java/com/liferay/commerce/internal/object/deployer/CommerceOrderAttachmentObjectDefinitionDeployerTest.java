/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.deployer;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentObjectDefinitionDeployerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_commerceOrderAttachmentObjectDefinitionDeployer =
			new CommerceOrderAttachmentObjectDefinitionDeployer();

		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentObjectDefinitionDeployer, "_bundleContext",
			_bundleContext);
		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentObjectDefinitionDeployer,
			"_commerceOrderLocalService", _commerceOrderLocalService);
		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentObjectDefinitionDeployer,
			"_commerceOrderModelResourcePermission",
			_commerceOrderModelResourcePermission);
		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentObjectDefinitionDeployer,
			"_objectEntryLocalService", _objectEntryLocalService);
	}

	@After
	public void tearDown() {
		_modelResourcePermissionRegistryUtilMockedStatic.close();
	}

	@Test
	public void testDeploy() {
		Mockito.when(
			_objectDefinition.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		List<ServiceRegistration<?>> serviceRegistrations =
			_commerceOrderAttachmentObjectDefinitionDeployer.deploy(
				_objectDefinition);

		Assert.assertTrue(serviceRegistrations.isEmpty());

		String className = RandomTestUtil.randomString();

		Mockito.when(
			_objectDefinition.getExternalReferenceCode()
		).thenReturn(
			"L_COMMERCE_ORDER_ATTACHMENT"
		);

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			className
		);

		_modelResourcePermissionRegistryUtilMockedStatic.when(
			() ->
				ModelResourcePermissionRegistryUtil.getModelResourcePermission(
					className)
		).thenReturn(
			null
		);

		serviceRegistrations =
			_commerceOrderAttachmentObjectDefinitionDeployer.deploy(
				_objectDefinition);

		Assert.assertTrue(serviceRegistrations.isEmpty());

		_modelResourcePermissionRegistryUtilMockedStatic.when(
			() ->
				ModelResourcePermissionRegistryUtil.getModelResourcePermission(
					className)
		).thenReturn(
			_modelResourcePermission
		);

		Mockito.doReturn(
			_serviceRegistration
		).when(
			_bundleContext
		).registerService(
			Mockito.eq(ModelResourcePermission.class),
			Mockito.any(ModelResourcePermission.class),
			Mockito.<Dictionary<String, Object>>any()
		);

		serviceRegistrations =
			_commerceOrderAttachmentObjectDefinitionDeployer.deploy(
				_objectDefinition);

		Assert.assertEquals(
			serviceRegistrations.toString(), 1, serviceRegistrations.size());
		Assert.assertSame(_serviceRegistration, serviceRegistrations.get(0));
	}

	@Mock
	private BundleContext _bundleContext;

	private CommerceOrderAttachmentObjectDefinitionDeployer
		_commerceOrderAttachmentObjectDefinitionDeployer;

	@Mock
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Mock
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Mock
	private ModelResourcePermission<ObjectEntry> _modelResourcePermission;

	private final MockedStatic<ModelResourcePermissionRegistryUtil>
		_modelResourcePermissionRegistryUtilMockedStatic = Mockito.mockStatic(
			ModelResourcePermissionRegistryUtil.class);

	@Mock
	private ObjectDefinition _objectDefinition;

	@Mock
	private ObjectEntryLocalService _objectEntryLocalService;

	@Mock
	private ServiceRegistration<?> _serviceRegistration;

}