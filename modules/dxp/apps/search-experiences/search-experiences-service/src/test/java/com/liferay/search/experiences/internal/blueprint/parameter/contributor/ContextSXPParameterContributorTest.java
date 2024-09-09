/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.petra.function.UnsafePredicate;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;

import java.beans.ExceptionListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class ContextSXPParameterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_mockLanguage();
	}

	@Test
	public void testCommerceAccountGroupIds() throws Exception {
		long[] commerceAccountGroupIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		_searchContext.setAttribute(
			"commerceAccountGroupIds", commerceAccountGroupIds);

		_testSXPParameter(
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Long[])value), commerceAccountGroupIds),
			"commerceAccountGroupIds");
	}

	@Test
	public void testCommerceChannelGroupId() throws Exception {
		long commerceChannelGroupId = RandomTestUtil.randomLong();

		_searchContext.setAttribute(
			"commerceChannelGroupId", commerceChannelGroupId);

		_testSXPParameter(
			value -> (long)value == commerceChannelGroupId,
			"commerceChannelGroupId");
	}

	@Test
	public void testContextCompanyId() throws Exception {
		long companyId = RandomTestUtil.randomLong();

		_searchContext.setCompanyId(companyId);

		_testSXPParameter(
			value -> (long)value == companyId, "context.company_id");
	}

	@Test
	public void testContextIsStagingGroup() throws Exception {
		_searchContext.setAttribute(
			"search.experiences.scope.group.id", RandomTestUtil.randomLong());

		_testSXPParameter(
			value -> (boolean)value == _group.isStagingGroup(),
			"context.is_staging_group");
	}

	@Test
	public void testContextLanguage() throws Exception {
		_searchContext.setLocale(_locale);

		_testSXPParameter(
			value -> value.equals(_locale.getLanguage()), "context.language");
	}

	@Test
	public void testContextLanguageId() throws Exception {
		_testSXPParameter(
			value -> value.equals(_language.getLanguageId(_locale)),
			"context.language_id");
	}

	@Test
	public void testContextLayoutNameLocalized() throws Exception {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			layout
		).getName(
			Mockito.any(Locale.class), Mockito.anyBoolean()
		);

		_searchContext.setLayout(layout);

		_testSXPParameter(
			value -> value.equals(layout.getName(LocaleUtil.US, true)),
			"context.layout-name-localized");
	}

	@Test
	public void testContextPublicationId() throws Exception {
		CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
			RandomTestUtil.randomLong());

		_testSXPParameter(
			value -> (long)value == CTCollectionThreadLocal.getCTCollectionId(),
			"context.publication_id");
	}

	@Test
	public void testContextScopeGroupExternalReferenceCode() throws Exception {
		_searchContext.setAttribute(
			"search.experiences.scope.group.id", RandomTestUtil.randomLong());

		_testSXPParameter(
			value -> value.equals(_group.getExternalReferenceCode()),
			"context.scope_group_external_reference_code");
	}

	@Test
	public void testContextScopeGroupId() throws Exception {
		long scopeGroupId = RandomTestUtil.randomLong();

		_searchContext.setAttribute(
			"search.experiences.scope.group.id", scopeGroupId);

		_testSXPParameter(
			value -> (long)value == scopeGroupId, "context.scope_group_id");
	}

	@Test
	public void testPlid() throws Exception {
		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			RandomTestUtil.randomLong()
		).when(
			layout
		).getPlid();

		_searchContext.setLayout(layout);

		_testSXPParameter(value -> (long)value == layout.getPlid(), "plid");
	}

	private static void _mockLanguage() {
		Mockito.doReturn(
			_locale.toString()
		).when(
			_language
		).getLanguageId(
			Mockito.any(Locale.class)
		);
	}

	private boolean _exists(
			String name, UnsafePredicate<Object, Exception> unsafePredicate)
		throws Exception {

		for (SXPParameter sxpParameter : _sxpParameters) {
			if (name.equals(sxpParameter.getName()) &&
				unsafePredicate.test(sxpParameter.getValue())) {

				return true;
			}
		}

		return false;
	}

	private GroupLocalService _mockGroupLocalService() throws Exception {
		_group = Mockito.mock(Group.class);

		Mockito.doReturn(
			RandomTestUtil.randomBoolean()
		).when(
			_group
		).isStagingGroup();

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			_group
		).getExternalReferenceCode();

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Mockito.doReturn(
			_group
		).when(
			groupLocalService
		).getGroup(
			Mockito.anyLong()
		);

		return groupLocalService;
	}

	private void _testSXPParameter(
			UnsafePredicate<Object, Exception> unsafePredicate,
			String sxpParameterName)
		throws Exception {

		ContextSXPParameterContributor contextSXPParameterContributor =
			new ContextSXPParameterContributor(
				_mockGroupLocalService(), _language);

		contextSXPParameterContributor.contribute(
			Mockito.mock(ExceptionListener.class), _searchContext,
			_sxpParameters);

		Assert.assertTrue(_exists(sxpParameterName, unsafePredicate));
	}

	private static final Language _language = Mockito.mock(Language.class);
	private static final Locale _locale = LocaleUtil.US;

	private Group _group;
	private final SearchContext _searchContext = new SearchContext();
	private final Set<SXPParameter> _sxpParameters = new HashSet<>();

}