/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Before;

/**
 * @author Albert Gomes Cabral
 */
public abstract class BaseTemplateUpgradeProcessTestCase {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	protected void addDDMTemplate(String filePath) throws Exception {
		long classPK = ParamUtil.getLong(_serviceContext, "classPK");

		_ddmTemplate = _ddmTemplateService.addTemplate(
			_group.getGroupId(), _portal.getClassNameId(DDMTemplate.class),
			classPK, _portal.getClassNameId(JournalArticle.class),
			HashMapBuilder.put(
				LocaleUtil.US, "DDMTemplate Name"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.US, "DDMTemplate Description"
			).build(),
			DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, StringPool.BLANK,
			TemplateConstants.LANG_TYPE_FTL, read(filePath), _serviceContext);
	}

	protected void addFragmentEntry(String filePath) throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection", StringPool.BLANK,
				_serviceContext);

		_fragmentEntry = _fragmentEntryService.addFragmentEntry(
			_group.getGroupId(), fragmentCollection.getFragmentCollectionId(),
			null, "FragmentEntry Name", null, read(filePath), null, false, null,
			null, 0, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	protected DDMTemplate getDDMTemplate() throws Exception {
		return _ddmTemplateService.getTemplate(_ddmTemplate.getTemplateId());
	}

	protected FragmentEntry getFragmentEntry() throws Exception {
		return _fragmentEntryService.fetchFragmentEntry(
			_fragmentEntry.getFragmentEntryId());
	}

	protected abstract String getUpgradeStepClassName() throws Exception;

	protected String read(String filePath) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/dynamic/data/mapping/dependencies/upgrade" + filePath);
	}

	protected void runTemplateUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				getUpgradeStepClassName(), LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, getUpgradeStepClassName());

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}
	}

	@Inject(
		filter = "(&(component.name=com.liferay.dynamic.data.mapping.internal.upgrade.registry.DDMServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private DDMTemplate _ddmTemplate;

	@Inject
	private DDMTemplateService _ddmTemplateService;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryService _fragmentEntryService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}