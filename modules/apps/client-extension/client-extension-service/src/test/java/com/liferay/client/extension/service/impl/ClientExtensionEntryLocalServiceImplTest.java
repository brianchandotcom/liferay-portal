/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.service.impl;

import com.liferay.client.extension.internal.configuration.ClientExtensionConfiguration;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.model.impl.ClientExtensionEntryImpl;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.persistence.ClientExtensionEntryPersistence;
import com.liferay.client.extension.type.factory.CETFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterableInvokerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * @author Iván Zaera Avellón
 */
public class ClientExtensionEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		if (_clusterableInvokerUtilMockedStatic != null) {
			_clusterableInvokerUtilMockedStatic.close();
		}

		if (_localizationUtilMockedStatic != null) {
			_localizationUtilMockedStatic.close();
		}

		if (_workflowHandlerRegistryUtilMockedStatic != null) {
			_workflowHandlerRegistryUtilMockedStatic.close();
		}
	}

	@Test
	public void testUpdateClientExtensionEntry() throws Exception {

		// Prepare clientExtensionEntryLocalServiceImpl for test

		_clusterableInvokerUtilMockedStatic = Mockito.mockStatic(
			ClusterableInvokerUtil.class);

		_workflowHandlerRegistryUtilMockedStatic = Mockito.mockStatic(
			WorkflowHandlerRegistryUtil.class);

		_mockLocalizationUtil();

		ClientExtensionEntryLocalServiceImpl
			clientExtensionEntryLocalServiceImpl =
				new ClientExtensionEntryLocalServiceImpl();

		ClientExtensionEntryPersistence clientExtensionEntryPersistence =
			_mockClientExtensionEntryPersistence();

		ReflectionTestUtil.setFieldValue(
			clientExtensionEntryLocalServiceImpl,
			"clientExtensionEntryPersistence", clientExtensionEntryPersistence);

		ReflectionTestUtil.setFieldValue(
			clientExtensionEntryLocalServiceImpl, "_cetFactory",
			Mockito.mock(CETFactory.class));

		ReflectionTestUtil.setFieldValue(
			clientExtensionEntryLocalServiceImpl,
			"_clientExtensionConfiguration",
			_mockClientExtensionConfiguration());

		ReflectionTestUtil.setFieldValue(
			clientExtensionEntryLocalServiceImpl, "_companyLocalService",
			_mockCompanyLocalService());

		// Perform test

		clientExtensionEntryLocalServiceImpl.updateClientExtensionEntry(
			1, 2, "description", new HashMap<>(), StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK);

		Class<ClientExtensionEntryLocalService>
			clientExtensionEntryLocalServiceClass =
				ClientExtensionEntryLocalService.class;

		_clusterableInvokerUtilMockedStatic.verify(
			() -> ClusterableInvokerUtil.synchronousInvokeOnCluster(
				Mockito.eq(ClusterInvokeAcceptor.class), Mockito.any(),
				Mockito.eq(
					clientExtensionEntryLocalServiceClass.getMethod(
						"undeployClientExtensionEntry",
						ClientExtensionEntry.class)),
				Mockito.any(), Mockito.anyLong(), Mockito.any(TimeUnit.class)),
			Mockito.times(1));

		Mockito.verify(
			clientExtensionEntryPersistence, Mockito.times(1)
		).update(
			Mockito.any(ClientExtensionEntry.class)
		);

		_workflowHandlerRegistryUtilMockedStatic.verify(
			() -> WorkflowHandlerRegistryUtil.startWorkflowInstance(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.eq(ClientExtensionEntry.class.getName()),
				Mockito.anyLong(), Mockito.any(ClientExtensionEntry.class),
				Mockito.any(ServiceContext.class), Mockito.any(Map.class)),
			Mockito.times(1));
	}

	private ClientExtensionConfiguration _mockClientExtensionConfiguration() {
		ClientExtensionConfiguration clientExtensionConfiguration =
			Mockito.mock(ClientExtensionConfiguration.class);

		Mockito.when(
			clientExtensionConfiguration.clusterTimeout()
		).thenReturn(
			10000L
		);

		return clientExtensionConfiguration;
	}

	private ClientExtensionEntryPersistence
			_mockClientExtensionEntryPersistence()
		throws Exception {

		ClientExtensionEntryPersistence clientExtensionEntryPersistence =
			Mockito.mock(ClientExtensionEntryPersistence.class);

		Mockito.when(
			clientExtensionEntryPersistence.findByPrimaryKey(Mockito.anyLong())
		).thenReturn(
			new ClientExtensionEntryImpl()
		);

		Mockito.when(
			clientExtensionEntryPersistence.update(
				Mockito.any(ClientExtensionEntry.class))
		).thenAnswer(
			(Answer<ClientExtensionEntry>)
				invocationOnMock -> invocationOnMock.getArgument(0)
		);

		return clientExtensionEntryPersistence;
	}

	private CompanyLocalService _mockCompanyLocalService() throws Exception {
		CompanyLocalService companyLocalService = Mockito.mock(
			CompanyLocalService.class);

		Mockito.when(
			companyLocalService.getCompany(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(Company.class)
		);

		return companyLocalService;
	}

	private void _mockLocalizationUtil() {
		_localizationUtilMockedStatic = Mockito.mockStatic(
			LocalizationUtil.class);

		_localizationUtilMockedStatic.when(
			() -> LocalizationUtil.getLocalization(
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			StringPool.BLANK
		);
	}

	private MockedStatic<ClusterableInvokerUtil>
		_clusterableInvokerUtilMockedStatic;
	private MockedStatic<LocalizationUtil> _localizationUtilMockedStatic;
	private MockedStatic<WorkflowHandlerRegistryUtil>
		_workflowHandlerRegistryUtilMockedStatic;

}