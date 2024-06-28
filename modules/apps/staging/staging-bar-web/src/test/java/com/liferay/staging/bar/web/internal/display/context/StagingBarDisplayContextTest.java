/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.bar.web.internal.display.context;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * @author Lourdes Fernández Besada
 */
public class StagingBarDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_layoutRevisionLocalServiceUtilMockedStatic.when(
			() -> LayoutRevisionLocalServiceUtil.updateLayoutRevision(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyString(), Mockito.eq(null), Mockito.eq(null),
				Mockito.eq(null), Mockito.eq(null), Mockito.eq(null),
				Mockito.anyBoolean(), Mockito.anyLong(), Mockito.eq(null),
				Mockito.eq(null), Mockito.eq(null),
				Mockito.any(ServiceContext.class))
		).thenAnswer(
			(Answer<LayoutRevision>)invocationOnMock -> {
				LayoutRevision layoutRevision = Mockito.mock(
					LayoutRevision.class);

				Mockito.when(
					layoutRevision.getLayoutRevisionId()
				).thenReturn(
					invocationOnMock.getArgument(1, Long.class)
				);

				Mockito.when(
					layoutRevision.getName()
				).thenReturn(
					invocationOnMock.getArgument(3, String.class) + "-Updated"
				);

				return layoutRevision;
			}
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_layoutRevisionLocalServiceUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_layout = Mockito.mock(Layout.class);

		_stagingBarDisplayContext = new StagingBarDisplayContext(
			_getLiferayPortletRequest(),
			Mockito.mock(LiferayPortletResponse.class), _layout);
	}

	@Test
	public void testUpdateLayoutRevision() {
		Mockito.when(
			_layout.isTypeContent()
		).thenReturn(
			true
		);

		LayoutRevision layoutRevision = _getLayoutRevision();

		LayoutRevision updatedLayoutRevision =
			_stagingBarDisplayContext.updateLayoutRevision(layoutRevision);

		Assert.assertNotEquals(layoutRevision, updatedLayoutRevision);
		Assert.assertEquals(
			layoutRevision.getLayoutRevisionId(),
			updatedLayoutRevision.getLayoutRevisionId());
		Assert.assertEquals(
			layoutRevision.getName() + "-Updated",
			updatedLayoutRevision.getName());
	}

	@Test
	public void testUpdateLayoutRevisionWithApprovedLayoutRevision() {
		LayoutRevision layoutRevision = Mockito.mock(LayoutRevision.class);

		Mockito.when(
			layoutRevision.isApproved()
		).thenReturn(
			true
		);

		Assert.assertEquals(
			layoutRevision,
			_stagingBarDisplayContext.updateLayoutRevision(layoutRevision));
	}

	@Test
	public void testUpdateLayoutRevisionWithDraftLayout() {
		Mockito.when(
			_layout.isDraftLayout()
		).thenReturn(
			true
		);

		Mockito.when(
			_layout.isTypeContent()
		).thenReturn(
			true
		);

		LayoutRevision layoutRevision = Mockito.mock(LayoutRevision.class);

		Assert.assertEquals(
			layoutRevision,
			_stagingBarDisplayContext.updateLayoutRevision(layoutRevision));
	}

	@Test
	public void testUpdateLayoutRevisionWithNoTypeContentLayout() {
		Mockito.when(
			_layout.isTypeContent()
		).thenReturn(
			false
		);

		LayoutRevision layoutRevision = Mockito.mock(LayoutRevision.class);

		Assert.assertEquals(
			layoutRevision,
			_stagingBarDisplayContext.updateLayoutRevision(layoutRevision));
	}

	@Test
	public void testUpdateLayoutRevisionWithNullLayoutRevision() {
		Assert.assertNull(_stagingBarDisplayContext.updateLayoutRevision(null));
	}

	private LayoutRevision _getLayoutRevision() {
		LayoutRevision layoutRevision = Mockito.mock(LayoutRevision.class);

		Mockito.when(
			layoutRevision.getLayoutRevisionId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			layoutRevision.getName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		return layoutRevision;
	}

	private LiferayPortletRequest _getLiferayPortletRequest() {
		LiferayPortletRequest liferayPortletRequest = Mockito.mock(
			LiferayPortletRequest.class);

		Mockito.when(
			liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			Mockito.mock(ThemeDisplay.class)
		);

		return liferayPortletRequest;
	}

	private static final MockedStatic<LayoutRevisionLocalServiceUtil>
		_layoutRevisionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			LayoutRevisionLocalServiceUtil.class);

	private Layout _layout;
	private StagingBarDisplayContext _stagingBarDisplayContext;

}