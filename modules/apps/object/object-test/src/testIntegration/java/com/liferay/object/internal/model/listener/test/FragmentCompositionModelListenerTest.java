/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class FragmentCompositionModelListenerTest
	extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeCreate() throws Exception {
		super.testOnBeforeCreate();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, companyAdminUser.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.addFragmentComposition(
				null, companyAdminUser.getUserId(), group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				StringUtil.randomId(), RandomTestUtil.randomString(),
				StringPool.BLANK,
				_getFragmentCompositionData(objectDefinition1), 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		JSONAssert.assertEquals(
			fragmentComposition.getData(),
			_getFragmentCompositionData(objectDefinition2), true);
	}

	private String _getFragmentCompositionData(
		ObjectDefinition objectDefinition) {

		return JSONUtil.put(
			"pageElements",
			JSONUtil.putAll(
				JSONUtil.put(
					"definition",
					JSONUtil.put(
						"formConfig",
						JSONUtil.put(
							"formReference",
							JSONUtil.put(
								"className",
								objectDefinition.getClassName())))),
				JSONUtil.put(
					"definition",
					JSONUtil.put(
						"widgetInstance",
						JSONUtil.put(
							"widgetName", objectDefinition.getPortletId()))))
		).toString();
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

}