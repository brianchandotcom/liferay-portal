package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.search.admin.web.internal.display.context.builder.IndexActionsDisplayContextBuilder;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.cluster.StatsInformation;
import com.liferay.portal.search.cluster.StatsInformationFactory;
import com.liferay.portal.search.configuration.ReindexConfiguration;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockRenderRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;

public class IndexActionsDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		setUpIndexInformation();
		_setUpLanguage();
		setUpPortalUtil();
	}

	@Test
	public void testGetStatsInformation() {
		IndexActionsDisplayContextBuilder indexActionsDisplayContextBuilder =
			new IndexActionsDisplayContextBuilder(
				_language, _portal, _reindexConfiguration,
				new MockRenderRequest(), _searchCapabilities);

		indexActionsDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(100.0, 50.0, 80.0));

		IndexActionsDisplayContext indexActionsDisplayContext =
			indexActionsDisplayContextBuilder.build();

		double availableDiskSpace =
			indexActionsDisplayContext.getAvailableDiskSpace();

		double currentDiskSpaceUsed =
			indexActionsDisplayContext.getCurrentDiskSpaceUsed();

		Assert.assertEquals(100.0, availableDiskSpace, 0);

		Assert.assertEquals(80.0, currentDiskSpaceUsed, 0);

		indexActionsDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(16.0, 10.0, 10.0));

		indexActionsDisplayContext = indexActionsDisplayContextBuilder.build();

		Assert.assertFalse(indexActionsDisplayContext.isLowOnDiskSpace());

		indexActionsDisplayContextBuilder.setStatsInformationFactory(
			getStatsInformationFactory(14.0, 10.0, 10.0));

		indexActionsDisplayContext = indexActionsDisplayContextBuilder.build();

		Assert.assertTrue(indexActionsDisplayContext.isLowOnDiskSpace());
	}

	protected StatsInformationFactory getStatsInformationFactory(
		double available, double largest, double used) {

		StatsInformationFactory statsInformationFactory = Mockito.mock(
			StatsInformationFactory.class);

		StatsInformation statsInformation = Mockito.mock(
			StatsInformation.class);

		Mockito.when(
			statsInformation.getAvailableDiskSpace()
		).thenReturn(
			available
		);

		Mockito.when(
			statsInformation.getSizeOfLargestIndex()
		).thenReturn(
			largest
		);

		Mockito.when(
			statsInformation.getUsedDiskSpace()
		).thenReturn(
			used
		);

		Mockito.when(
			statsInformationFactory.getStatsInformation()
		).thenReturn(
			statsInformation
		);

		return statsInformationFactory;
	}

	protected void setUpIndexInformation() {
		indexInformation = Mockito.mock(IndexInformation.class);

		Mockito.when(
			indexInformation.getIndexNames()
		).thenReturn(
			new String[] {"index1", "index2"}
		);

		Mockito.when(
			indexInformation.getCompanyIndexName(Mockito.anyLong())
		).thenAnswer(
			invocation -> "index" + invocation.getArguments()[0]
		);
	}

	protected void setUpPortalUtil() {
		_portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgument(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected IndexInformation indexInformation;

	private void _setUpLanguage() {
		_language = new LanguageImpl();
	}

	private Language _language;
	private SearchCapabilities _searchCapabilities;
	private ReindexConfiguration _reindexConfiguration;
	private Portal _portal;

}
