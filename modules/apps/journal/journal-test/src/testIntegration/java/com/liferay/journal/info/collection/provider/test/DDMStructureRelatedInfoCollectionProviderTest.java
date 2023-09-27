/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@Ignore
@RunWith(Arquillian.class)
public class DDMStructureRelatedInfoCollectionProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRegisterDDMStructureRelatedInfoCollectionProvider()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					_getKey(ddmStructure));

		Assert.assertNotNull(relatedInfoItemCollectionProvider);
	}

	@Test
	public void testUnregisterDDMStructureRelatedInfoCollectionProvider()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_ddmStructureLocalService.deleteStructure(ddmStructure);

		RelatedInfoItemCollectionProvider<?, ?>
			relatedInfoItemCollectionProvider =
				_infoItemServiceRegistry.getFirstInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					_getKey(ddmStructure));

		Assert.assertNull(relatedInfoItemCollectionProvider);
	}

	private String _getKey(DDMStructure ddmStructure) {
		StringBundler sb = new StringBundler(5);

		sb.append(RelatedInfoItemCollectionProvider.class.getName());
		sb.append(StringPool.DASH);
		sb.append(JournalArticle.class.getName());
		sb.append(StringPool.DASH);
		sb.append(ddmStructure.getStructureId());

		return sb.toString();
	}

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}