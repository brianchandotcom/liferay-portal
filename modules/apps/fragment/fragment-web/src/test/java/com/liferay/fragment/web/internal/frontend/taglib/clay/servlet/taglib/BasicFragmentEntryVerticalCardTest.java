/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Diego Hu
 */
public class BasicFragmentEntryVerticalCardTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpLanguageUtil();
		_setUpPortalUtil();
	}

	@Test
	public void testGetLabelsForApprovedFragmentEntry() {
		Mockito.when(
			_fragmentEntry.isApproved()
		).thenReturn(
			true
		);

		Mockito.when(
			_fragmentEntry.fetchDraftFragmentEntry()
		).thenReturn(
			Mockito.mock(FragmentEntry.class)
		);

		Assert.assertEquals(2, _getLabelsSize());

		Mockito.when(
			_fragmentEntry.isCacheable()
		).thenReturn(
			true
		);

		Assert.assertEquals(3, _getLabelsSize());
	}

	@Test
	public void testGetLabelsWithoutWarnings() {
		Mockito.when(
			_fragmentEntry.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Mockito.when(
			_httpServletRequest.getAttribute(
				FragmentEntryProcessorRegistry.class.getName())
		).thenReturn(
			Mockito.mock(FragmentEntryProcessorRegistry.class)
		);

		Mockito.when(
			_httpServletRequest.getAttribute(
				FragmentEntryValidator.class.getName())
		).thenReturn(
			Mockito.mock(FragmentEntryValidator.class)
		);

		Assert.assertEquals(1, _getLabelsSize());

		Mockito.when(
			_fragmentEntry.isCacheable()
		).thenReturn(
			true
		);

		Assert.assertEquals(2, _getLabelsSize());
	}

	@Test
	public void testGetLabelsWithWarnings() {
		Mockito.when(
			_fragmentEntry.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Assert.assertEquals(2, _getLabelsSize());

		Mockito.when(
			_fragmentEntry.isCacheable()
		).thenReturn(
			true
		);

		Assert.assertEquals(3, _getLabelsSize());
	}

	private int _getLabelsSize() {
		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.when(
			PortalUtil.getHttpServletRequest(renderRequest)
		).thenReturn(
			_httpServletRequest
		);

		BasicFragmentEntryVerticalCard basicFragmentEntryVerticalCard =
			new BasicFragmentEntryVerticalCard(
				_fragmentEntry, renderRequest,
				Mockito.mock(RenderResponse.class),
				Mockito.mock(RowChecker.class));

		List<LabelItem> labels = basicFragmentEntryVerticalCard.getLabels();

		return labels.size();
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(Mockito.mock(Language.class));
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private final FragmentEntry _fragmentEntry = Mockito.mock(
		FragmentEntry.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final Portal _portal = Mockito.mock(Portal.class);

}