/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Julio Camarero
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class LayoutSetPrototypePropagationTest
	extends BasePrototypePropagationTestCase {

	@Test
	public void testAddGroup() throws Exception {
		Assert.assertEquals(_initialPrototypeLayoutCount, _initialLayoutCount);
	}

	@Test
	public void testIsLayoutDeleteable() throws Exception {
		Assert.assertFalse(SitesUtil.isLayoutDeleteable(_layout));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutDeleteable(_layout));
	}

	@Test
	public void testIsLayoutSortable() throws Exception {
		Assert.assertFalse(SitesUtil.isLayoutSortable(_layout));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutSortable(_layout));
	}

	@Test
	public void testIsLayoutUpdateable() throws Exception {
		doTestIsLayoutUpdateable();
	}

	@Test
	public void testLayoutPropagationWithLayoutPrototypeLinkDisabled()
		throws Exception {

		doTestLayoutPropagationWithLayoutPrototype(false);
	}

	@Test
	public void testLayoutPropagationWithLayoutPrototypeLinkEnabled()
		throws Exception {

		doTestLayoutPropagationWithLayoutPrototype(true);
	}

	@Test
	public void testLayoutPropagationWithLinkDisabled() throws Exception {
		doTestLayoutPropagation(false);
	}

	@Test
	public void testLayoutPropagationWithLinkEnabled() throws Exception {
		doTestLayoutPropagation(true);
	}

	@Test
	public void testPortletDataPropagationWithLinkDisabled() throws Exception {
		doTestPortletDataPropagation(false);
	}

	@Test
	public void testPortletDataPropagationWithLinkEnabled() throws Exception {
		doTestPortletDataPropagation(true);
	}

	@Test
	public void testPortletPreferencesPropagationWithGlobalScopeLinkDisabled()
		throws Exception {

		doTestPortletPreferencesPropagation(false, true);
	}

	@Test
	public void testPortletPreferencesPropagationWithGlobalScopeLinkEnabled()
		throws Exception {

		doTestPortletPreferencesPropagation(true, true);
	}

	@Test
	public void testReset() throws Exception {
		SitesUtil.resetPrototype(_layout);
		SitesUtil.resetPrototype(_layout2);

		propagateChanges(_group);

		_layout = LayoutTestUtil.updateLayoutTemplateId(_layout, "1_column");

		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));
		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(_layout2));

		_layout2 = LayoutTestUtil.updateLayoutTemplateId(_layout2, "1_column");

		SitesUtil.resetPrototype(_layout);

		_layout = propagateChanges(_layout);

		Assert.assertFalse(SitesUtil.isLayoutModifiedSinceLastMerge(_layout));
		Assert.assertEquals(
			_initialLayoutTemplateId,
			LayoutTestUtil.getLayoutTemplateId(_layout));
		Assert.assertTrue(SitesUtil.isLayoutModifiedSinceLastMerge(_layout2));
		Assert.assertEquals(
			"1_column", LayoutTestUtil.getLayoutTemplateId(_layout2));
	}

	protected void doSetUp() throws Exception {

		// Layout set prototype

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			ServiceTestUtil.randomString());

		_layoutSetPrototypeGroup = _layoutSetPrototype.getGroup();

		_prototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);

		LayoutTestUtil.updateLayoutTemplateId(
			_prototypeLayout, _initialLayoutTemplateId);

		_layoutSetPrototypeJournalArticle = JournalTestUtil.addArticle(
			_layoutSetPrototypeGroup.getGroupId(), "Test Article",
			"Test Content");

		_journalContentPortletId =
			addJournalContentPortletToLayout(
				TestPropsValues.getUserId(), _prototypeLayout,
				_layoutSetPrototypeJournalArticle, "column-1");

		_prototypeLayout2 = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);

		LayoutTestUtil.updateLayoutTemplateId(
				_prototypeLayout2, _initialLayoutTemplateId);

		_initialPrototypeLayoutCount =
			LayoutLocalServiceUtil.getLayoutsCount(
				_layoutSetPrototypeGroup, true);

		// Group

		setLinkEnabled(true);

		propagateChanges(_group);

		_layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false, _prototypeLayout.getFriendlyURL());

		_layout2 = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false, _prototypeLayout2.getFriendlyURL());

		_initialLayoutCount = getGroupLayoutCount();

		_journalArticle =
			JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				_group.getGroupId(),
				_layoutSetPrototypeJournalArticle.getUrlTitle());
	}

	protected void doTestIsLayoutUpdateable() throws Exception {
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout2));

		setLayoutUpdateable(_prototypeLayout, false);

		Assert.assertFalse(SitesUtil.isLayoutUpdateable(_layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout2));

		setLayoutsUpdateable(false);

		Assert.assertFalse(SitesUtil.isLayoutUpdateable(_layout));
		Assert.assertFalse(SitesUtil.isLayoutUpdateable(_layout2));

		setLinkEnabled(false);

		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout));
		Assert.assertTrue(SitesUtil.isLayoutUpdateable(_layout2));
	}

	protected void doTestLayoutPropagation(boolean linkEnabled)
		throws Exception {

		setLinkEnabled(linkEnabled);

		Layout layout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true);

		Assert.assertEquals(
			_initialPrototypeLayoutCount, getGroupLayoutCount());

		propagateChanges(_group);

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutCount, getGroupLayoutCount());
		}

		LayoutLocalServiceUtil.deleteLayout(
			layout, true, ServiceTestUtil.getServiceContext());

		if (linkEnabled) {
			Assert.assertEquals(
				_initialPrototypeLayoutCount + 1, getGroupLayoutCount());
		}
		else {
			Assert.assertEquals(
				_initialPrototypeLayoutCount, getGroupLayoutCount());
		}

		propagateChanges(_group);

		Assert.assertEquals(
				_initialPrototypeLayoutCount, getGroupLayoutCount());
	}

	protected void doTestLayoutPropagationWithLayoutPrototype(
			boolean layoutSetLayoutLinkEnabled)
		throws Exception {

		Layout layoutSetPrototypeLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototypeGroup.getGroupId(),
			ServiceTestUtil.randomString(), true, _layoutPrototype,
			layoutSetLayoutLinkEnabled);

		layoutSetPrototypeLayout = propagateChanges(layoutSetPrototypeLayout);

		propagateChanges(_group);

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			_group.getGroupId(), false,
			layoutSetPrototypeLayout.getFriendlyURL());

		LayoutTestUtil.updateLayoutTemplateId(
				_layoutPrototypeLayout, "1_column");

		if (layoutSetLayoutLinkEnabled) {
			Assert.assertEquals(
				_initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(layout));
		}

		layout = propagateChanges(layout);

		propagateChanges(_group);

		if (layoutSetLayoutLinkEnabled) {
			Assert.assertEquals(
				"1_column", LayoutTestUtil.getLayoutTemplateId(layout));
		}
		else {
			Assert.assertEquals(
				_initialLayoutTemplateId,
				LayoutTestUtil.getLayoutTemplateId(layout));
		}
	}

	protected void doTestPortletDataPropagation(boolean linkEnabled)
		throws Exception {

		setLinkEnabled(linkEnabled);

		String content = _layoutSetPrototypeJournalArticle.getContent();

		Assert.assertEquals(content, _journalArticle.getContent());

		JournalTestUtil.updateArticle(
			_layoutSetPrototypeJournalArticle, "New Test Title",
			"New Test Content");

		propagateChanges(_group);

		// Portlet data is no longer propagated once the group has been created

		Assert.assertEquals(content, _journalArticle.getContent());
	}

	@Override
	protected void doTestPortletPreferencesPropagation(boolean linkEnabled)
		throws Exception {

		doTestPortletPreferencesPropagation(linkEnabled, false);
	}

	protected int getGroupLayoutCount() throws Exception {
		return LayoutLocalServiceUtil.getLayoutsCount(_group, false);
	}

	protected void propagateChanges(Group group) throws Exception {
		LayoutLocalServiceUtil.getLayouts(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Thread.sleep(2000);
	}

	protected void setLayoutsUpdateable(boolean layoutsUpdateable)
		throws Exception {

		_layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
				_layoutSetPrototype.getLayoutSetPrototypeId(),
				_layoutSetPrototype.getNameMap(),
				_layoutSetPrototype.getDescription(),
				_layoutSetPrototype.getActive(), layoutsUpdateable,
				ServiceTestUtil.getServiceContext());
	}

	protected Layout setLayoutUpdateable(
			Layout layout, boolean layoutUpdateable)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.put(
			Sites.LAYOUT_UPDATEABLE, String.valueOf(layoutUpdateable));

		layout.setTypeSettingsProperties(typeSettingsProperties);

		return LayoutLocalServiceUtil.updateLayout(layout);
	}

	protected void setLinkEnabled(boolean linkEnabled) throws Exception {
		SitesUtil.updateLayoutSetPrototypesLinks(
			_group, _layoutSetPrototype.getLayoutSetPrototypeId(), 0,
			linkEnabled, linkEnabled);

		if ((_layout != null) && (_layout2 != null)) {
			_layout = LayoutLocalServiceUtil.getLayout(_layout.getPlid());
			_layout2 = LayoutLocalServiceUtil.getLayout(_layout2.getPlid());
		}
	}

	private int _initialLayoutCount;
	private int _initialPrototypeLayoutCount;
	private Layout _layout2;
	private LayoutSetPrototype _layoutSetPrototype;
	private Group _layoutSetPrototypeGroup;
	private JournalArticle _layoutSetPrototypeJournalArticle;
	private Layout _prototypeLayout2;

}