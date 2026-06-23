/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getNamespace() {
		return "layout_set_prototypes";
	}

	@Override
	public String getPortletId() {
		return LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Test
	public void testExportImportLayoutSetPrototype() throws Exception {
		exportImportLayoutSetPrototype(false);
	}

	@Test
	@TestInfo("LPD-83275")
	public void testExportImportLayoutSetPrototypeWithCleanImport()
		throws Exception {

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototypes();

		LayoutSetPrototype exportedLayoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group exportedLayoutSetPrototypeGroup =
			exportedLayoutSetPrototype.getGroup();

		LayoutTestUtil.addTypePortletLayout(
			exportedLayoutSetPrototypeGroup, true);

		long companyId = exportedLayoutSetPrototype.getCompanyId();
		int privateLayoutsPageCount =
			exportedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount();
		String uuid = exportedLayoutSetPrototype.getUuid();

		User user = TestPropsValues.getUser();

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_PORTLET_LOCAL,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportPortletSettingsMap(
							user, layout.getPlid(), layout.getGroupId(),
							getPortletId(), getExportParameterMap(),
							StringPool.BLANK));

		File larFile = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
			exportImportConfiguration);

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			exportedLayoutSetPrototype);

		importedLayout = LayoutTestUtil.addTypePortletLayout(importedGroup);

		exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				updateExportImportConfiguration(
					user.getUserId(),
					exportImportConfiguration.getExportImportConfigurationId(),
					StringPool.BLANK, StringPool.BLANK,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportPortletSettingsMap(
							user, importedLayout.getPlid(),
							importedGroup.getGroupId(), getPortletId(),
							getImportParameterMap()),
					new ServiceContext());

		ExportImportLocalServiceUtil.importPortletInfo(
			exportImportConfiguration, larFile);

		LayoutSetPrototype importedLayoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(uuid, companyId);

		Group importedLayoutSetPrototypeGroup =
			importedLayoutSetPrototype.getGroup();

		Assert.assertEquals(
			privateLayoutsPageCount,
			importedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount());

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			importedLayoutSetPrototype);
	}

	@Test
	public void testExportImportLayoutSetPrototypeWithLayoutPrototype()
		throws Exception {

		exportImportLayoutSetPrototype(true);
	}

	@Test
	@TestInfo("LPD-95511")
	public void testImportLayoutSetPrototypeWithLayout() throws Exception {
		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group group = layoutSetPrototype.getGroup();

		long layoutSetPrototypeId =
			layoutSetPrototype.getLayoutSetPrototypeId();

		boolean layoutImportInProcess =
			ExportImportThreadLocal.isLayoutImportInProcess();

		ExportImportThreadLocal.setLayoutImportInProcess(true);

		try {
			TransactionInvokerUtil.invoke(
				_requiredTransactionConfig,
				() -> {

					// Lock the layout set prototype row in the outer
					// transaction, as importing its staged model does

					LayoutSetPrototype lockedLayoutSetPrototype =
						LayoutSetPrototypeLocalServiceUtil.
							getLayoutSetPrototype(layoutSetPrototypeId);

					Date modifiedDate =
						lockedLayoutSetPrototype.getModifiedDate();

					long time = modifiedDate.getTime();

					lockedLayoutSetPrototype.setModifiedDate(
						new Date(time - Time.DAY));

					LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
						lockedLayoutSetPrototype);

					// Import a content page in a nested transaction, as the
					// batch engine does for each page

					try {
						TransactionInvokerUtil.invoke(
							_requiresNewTransactionConfig,
							() -> LayoutTestUtil.addTypeContentLayout(
								group, true, false));
					}
					catch (Throwable throwable) {
						throw new SystemException(throwable);
					}

					return null;
				});
		}
		catch (Throwable throwable) {
			throw new AssertionError(
				"Importing a content page into a layout set prototype " +
					"deadlocked",
				throwable);
		}
		finally {
			ExportImportThreadLocal.setLayoutImportInProcess(
				layoutImportInProcess);

			LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
				layoutSetPrototypeId);
		}
	}

	protected void exportImportLayoutSetPrototype(boolean layoutPrototype)
		throws Exception {

		// Exclude default site templates

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototypes();

		LayoutSetPrototype exportedLayoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(RandomTestUtil.randomString());

		Group exportedLayoutSetPrototypeGroup =
			exportedLayoutSetPrototype.getGroup();

		if (layoutPrototype) {
			LayoutPrototype exportedLayoutPrototype =
				LayoutTestUtil.addLayoutPrototype(
					RandomTestUtil.randomString());

			LayoutTestUtil.addTypePortletLayout(
				exportedLayoutSetPrototypeGroup, true, exportedLayoutPrototype,
				true);
		}
		else {
			LayoutTestUtil.addTypePortletLayout(
				exportedLayoutSetPrototypeGroup, true);
		}

		exportImportPortlet(LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE);

		LayoutSetPrototype importedLayoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				getLayoutSetPrototypeByUuidAndCompanyId(
					exportedLayoutSetPrototype.getUuid(),
					exportedLayoutSetPrototype.getCompanyId());

		Group importedLayoutSetPrototypeGroup =
			importedLayoutSetPrototype.getGroup();

		Assert.assertEquals(
			exportedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount(),
			importedLayoutSetPrototypeGroup.getPrivateLayoutsPageCount());

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			exportedLayoutSetPrototype);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "page-templates", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "page-templates", true);

		return parameterMap;
	}

	@Override
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

	private static final TransactionConfig _requiredTransactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});
	private static final TransactionConfig _requiresNewTransactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

}