/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.fragment.util.FragmentEntryTestUtil;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				FragmentPersistenceConstants.BUNDLE_SYMBOLIC_NAME));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testAddFragmentEntryLink() throws Exception {
		String css = "div {\\ncolor: red;\\n}";
		String html = "<div>test</div>";
		String js = "alert(\"test\");";
		String configuration = "{fieldSets: []}";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				_group.getGroupId(), 0, _fragmentEntry.getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class), _layout.getPlid(), css,
				html, js, configuration, StringPool.BLANK, StringPool.BLANK, 0,
				null, serviceContext);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkPersistence.findByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(fragmentEntryLink, persistedFragmentEntryLink);
		Assert.assertEquals(css, persistedFragmentEntryLink.getCss());
		Assert.assertEquals(html, persistedFragmentEntryLink.getHtml());
		Assert.assertEquals(js, persistedFragmentEntryLink.getJs());
		Assert.assertEquals(
			configuration, persistedFragmentEntryLink.getConfiguration());
	}

	@Test
	public void testDeleteFragmentEntryLink() throws Exception {
		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		_fragmentEntryLinkService.deleteFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertNull(
			_fragmentEntryLinkPersistence.fetchByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId()));
	}

	@Test
	public void testUpdateFragmentEntryLink() throws Exception {
		String editableValues = _createEditableValues();

		FragmentEntryLink fragmentEntryLink =
			FragmentTestUtil.addFragmentEntryLink(
				_fragmentEntry, PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		_fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId(), editableValues);

		FragmentEntryLink persistedFragmentEntryLink =
			_fragmentEntryLinkPersistence.findByPrimaryKey(
				fragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(fragmentEntryLink, persistedFragmentEntryLink);
		Assert.assertEquals(
			editableValues, persistedFragmentEntryLink.getEditableValues());
	}

	@Test
	public void testUpdateFragmentEntryLinks() throws Exception {
		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			_fragmentCollection.getFragmentCollectionId());

		FragmentTestUtil.addFragmentEntryLink(
			fragmentEntry, PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid());

		long[] fragmentEntryIds = {
			_fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentEntryId()
		};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkPersistence.findByG_C_C(
				_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		_fragmentEntryLinkService.updateFragmentEntryLinks(
			_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
			_layout.getPlid(), fragmentEntryIds, _createEditableValues(),
			serviceContext);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkPersistence.findByG_C_C(
				_group.getGroupId(), PortalUtil.getClassNameId(Layout.class),
				_layout.getPlid());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + 1,
			actualFragmentEntryLinks.size());
	}

	private String _createEditableValues() {
		JSONObject jsonObject = JSONUtil.put(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		return jsonObject.toString();
	}

	private FragmentCollection _fragmentCollection;
	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkPersistence _fragmentEntryLinkPersistence;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

}