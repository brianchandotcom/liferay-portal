/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.petra.function.UnsafePredicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.segments.SegmentsEntryRetriever;

import java.beans.ExceptionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class UserSXPParameterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_mockLanguage();
		_mockPortal();
		_mockUser();

		_searchContext.setAttribute(
			"search.experiences.scope.group.id", RandomTestUtil.randomLong());
		_searchContext.setKeywords(StringPool.BLANK);
		_searchContext.setLocale(_locale);
		_searchContext.setUserId(_user.getUserId());
	}

	@Test
	public void testActiveSegmentEntryIds() throws Exception {
		long[] segmentsEntryIds = _randomLongArray(2);

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(segmentsEntryIds),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.active_segment_entry_ids",
				value -> Arrays.equals(
					ArrayUtils.toPrimitive((Long[])value), segmentsEntryIds)));
	}

	@Test
	public void testAge() throws Exception {
		_testSXPParameter(value -> (int)value == 0, "user.age");
	}

	@Test
	public void testAssetCategoryIds() throws Exception {
		long[] assetCategoryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		AssetCategory assetCategory1 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			assetCategoryIds[0]
		).when(
			assetCategory1
		).getCategoryId();

		AssetCategory assetCategory2 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			assetCategoryIds[1]
		).when(
			assetCategory2
		).getCategoryId();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(
				Arrays.asList(assetCategory1, assetCategory2)),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.asset_category_ids",
				value -> Arrays.equals(
					ArrayUtils.toPrimitive((Long[])value), assetCategoryIds)));
	}

	@Test
	public void testAssetTagNames() throws Exception {
		String[] assetTagNames = {
			RandomTestUtil.randomString(5), RandomTestUtil.randomString(5)
		};

		AssetTag assetTag1 = Mockito.mock(AssetTag.class);

		Mockito.doReturn(
			assetTagNames[0]
		).when(
			assetTag1
		).getName();

		AssetTag assetTag2 = Mockito.mock(AssetTag.class);

		Mockito.doReturn(
			assetTagNames[1]
		).when(
			assetTag2
		).getName();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Arrays.asList(assetTag1, assetTag2)),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.asset_tag_names",
				value -> Arrays.equals((String[])value, assetTagNames)));
	}

	@Test
	public void testBirthday() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getBirthday()), "user.birthday");
	}

	@Test
	public void testBooleanArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new boolean[] {true, false}
		).when(
			expandoValue
		).getBooleanArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Boolean[])value),
				expandoValue.getBooleanArray()),
			ExpandoColumnConstants.BOOLEAN_ARRAY);
	}

	@Test
	public void testBooleanExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			true
		).when(
			expandoValue
		).getBoolean();

		_testExpandoSXPParameter(
			expandoValue, value -> (Boolean)value == expandoValue.getBoolean(),
			ExpandoColumnConstants.BOOLEAN);
	}

	@Test
	public void testCreateDate() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getCreateDate()), "user.create_date");
	}

	@Test
	public void testCurrentSiteRoleIds() throws Exception {
		long[] roleIds = _randomLongArray(2);

		UserGroupRole userGroupRole1 = Mockito.mock(UserGroupRole.class);

		Mockito.doReturn(
			roleIds[0]
		).when(
			userGroupRole1
		).getRoleId();

		UserGroupRole userGroupRole2 = Mockito.mock(UserGroupRole.class);

		Mockito.doReturn(
			roleIds[1]
		).when(
			userGroupRole2
		).getRoleId();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(
				Arrays.asList(userGroupRole1, userGroupRole2)),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.current_site_role_ids",
				value -> Arrays.equals(
					ArrayUtils.toPrimitive((Long[])value), roleIds)));
	}

	@Test
	public void testDateExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new Date()
		).when(
			expandoValue
		).getDate();

		_testExpandoSXPParameter(
			expandoValue, value -> value.equals(expandoValue.getDate()),
			ExpandoColumnConstants.DATE);
	}

	@Test
	public void testDoubleArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new double[] {1.0D, 2.0D}
		).when(
			expandoValue
		).getDoubleArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Double[])value),
				expandoValue.getDoubleArray()),
			ExpandoColumnConstants.DOUBLE_ARRAY);
	}

	@Test
	public void testDoubleExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			1.0D
		).when(
			expandoValue
		).getDouble();

		_testExpandoSXPParameter(
			expandoValue, value -> (double)value == expandoValue.getDouble(),
			ExpandoColumnConstants.DOUBLE);
	}

	@Test
	public void testEmailDomain() throws Exception {
		_testSXPParameter(
			value -> {
				String[] parts = StringUtil.split(_user.getEmailAddress(), "@");

				return value.equals(parts[1]);
			},
			"user.email_domain");
	}

	@Test
	public void testFirstName() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getFirstName()), "user.first_name");
	}

	@Test
	public void testFloatArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new float[] {1.0F, 2.0F}
		).when(
			expandoValue
		).getFloatArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Float[])value),
				expandoValue.getFloatArray()),
			ExpandoColumnConstants.FLOAT_ARRAY);
	}

	@Test
	public void testFloatExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			1.0F
		).when(
			expandoValue
		).getFloat();

		_testExpandoSXPParameter(
			expandoValue, value -> (float)value == expandoValue.getFloat(),
			ExpandoColumnConstants.FLOAT);
	}

	@Test
	public void testFullName() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getFullName()), "user.full_name");
	}

	@Test
	public void testGeolocationExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		JSONObject jsonObject = JSONUtil.put(
			"latitude", 60.182330546009915D
		).put(
			"longitude", 24.932147747881896D
		);

		Mockito.doReturn(
			jsonObject
		).when(
			expandoValue
		).getGeolocationJSONObject();

		_testExpandoSXPParameter(
			expandoValue, "expandotest",
			value -> (double)value == jsonObject.getDouble("latitude"),
			"user.custom.field.expandotest.latitude",
			ExpandoColumnConstants.GEOLOCATION);

		_testExpandoSXPParameter(
			expandoValue, "expandotest",
			value -> (double)value == jsonObject.getDouble("longitude"),
			"user.custom.field.expandotest.longitude",
			ExpandoColumnConstants.GEOLOCATION);
	}

	@Test
	public void testGroupIds() throws Exception {
		_testSXPParameter(
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Long[])value), _user.getGroupIds()),
			"user.group_ids");
	}

	@Test
	public void testId() throws Exception {
		_testSXPParameter(value -> (long)value == _user.getUserId(), "user.id");
	}

	@Test
	public void testIntegerArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new int[] {1, 2}
		).when(
			expandoValue
		).getIntegerArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Integer[])value),
				expandoValue.getIntegerArray()),
			ExpandoColumnConstants.INTEGER_ARRAY);
	}

	@Test
	public void testIntegerExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			1
		).when(
			expandoValue
		).getInteger();

		_testExpandoSXPParameter(
			expandoValue, value -> (int)value == expandoValue.getInteger(),
			ExpandoColumnConstants.INTEGER);
	}

	@Test
	public void testIsFemale() throws Exception {
		_testSXPParameter(
			value -> (boolean)value == _user.isFemale(), "user.is_female");
	}

	@Test
	public void testIsGenderX() throws Exception {
		_testSXPParameter(
			value -> (boolean)value == (!_user.isFemale() && !_user.isMale()),
			"user.is_gender_x");
	}

	@Test
	public void testIsMale() throws Exception {
		_testSXPParameter(
			value -> (boolean)value == _user.isMale(), "user.is_male");
	}

	@Test
	public void testIsOmniadmin() throws Exception {
		_testSXPParameter(
			value -> _portal.isOmniadmin(_user.getUserId()),
			"user.is_omniadmin");
	}

	@Test
	public void testIsSignedIn() throws Exception {
		_testSXPParameter(value -> !_user.isGuestUser(), "user.is_signed_in");
	}

	@Test
	public void testJobTitle() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getJobTitle()), "user.job_title");
	}

	@Test
	public void testLanguageId() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getLanguageId()), "user.language_id");
	}

	@Test
	public void testLastName() throws Exception {
		_testSXPParameter(
			value -> value.equals(_user.getLastName()), "user.last_name");
	}

	@Test
	public void testLongArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			_randomLongArray(2)
		).when(
			expandoValue
		).getLongArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Long[])value),
				expandoValue.getLongArray()),
			ExpandoColumnConstants.LONG_ARRAY);
	}

	@Test
	public void testLongExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			1L
		).when(
			expandoValue
		).getLong();

		_testExpandoSXPParameter(
			expandoValue, value -> (long)value == expandoValue.getLong(),
			ExpandoColumnConstants.LONG);
	}

	@Test
	public void testNumberArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			"1,2"
		).when(
			expandoValue
		).getData();

		_testExpandoSXPParameter(
			expandoValue,
			value -> {
				String s = StringUtil.merge((String[])value, ",");

				return s.equals(expandoValue.getData());
			},
			ExpandoColumnConstants.NUMBER_ARRAY);
	}

	@Test
	public void testNumberExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			"1"
		).when(
			expandoValue
		).getData();

		_testExpandoSXPParameter(
			expandoValue, value -> value.equals(expandoValue.getData()),
			ExpandoColumnConstants.NUMBER);
	}

	@Test
	public void testParentAssetCategoryIds() throws Exception {
		long[] parentAssetCategoryIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		AssetCategory assetCategory1 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			parentAssetCategoryIds[0]
		).when(
			assetCategory1
		).getParentCategoryId();

		AssetCategory parentAssetCategory1 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			parentAssetCategory1
		).when(
			assetCategory1
		).getParentCategory();

		Mockito.doReturn(
			parentAssetCategoryIds[0]
		).when(
			parentAssetCategory1
		).getCategoryId();

		Mockito.doReturn(
			0L
		).when(
			parentAssetCategory1
		).getParentCategoryId();

		AssetCategory assetCategory2 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			parentAssetCategoryIds[1]
		).when(
			assetCategory2
		).getParentCategoryId();

		AssetCategory parentAssetCategory2 = Mockito.mock(AssetCategory.class);

		Mockito.doReturn(
			parentAssetCategory2
		).when(
			assetCategory2
		).getParentCategory();

		Mockito.doReturn(
			parentAssetCategoryIds[1]
		).when(
			parentAssetCategory2
		).getCategoryId();

		Mockito.doReturn(
			0L
		).when(
			parentAssetCategory1
		).getParentCategoryId();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(
				Arrays.asList(assetCategory1, assetCategory2)),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.parent_asset_category_ids",
				value -> Arrays.equals(
					ArrayUtils.toPrimitive((Long[])value),
					parentAssetCategoryIds)));
	}

	@Test
	public void testRegularRoleIds() throws Exception {
		_testSXPParameter(
			value -> Arrays.equals(
				ArrayUtils.toPrimitive((Long[])value), _user.getRoleIds()),
			"user.regular_role_ids");
	}

	@Test
	public void testShortArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new short[] {1, 2}
		).when(
			expandoValue
		).getShortArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> {
				Integer[] array = (Integer[])value;

				short[] shortArray = new short[array.length];

				for (int i = 0; i < array.length; i++) {
					shortArray[i] = array[i].shortValue();
				}

				return Arrays.equals(shortArray, expandoValue.getShortArray());
			},
			ExpandoColumnConstants.SHORT_ARRAY);
	}

	@Test
	public void testShortExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			(short)0
		).when(
			expandoValue
		).getShort();

		_testExpandoSXPParameter(
			expandoValue, value -> (int)value == expandoValue.getShort(),
			ExpandoColumnConstants.SHORT);
	}

	@Test
	public void testStringArrayExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new String[] {"one", "two"}
		).when(
			expandoValue
		).getStringArray();

		_testExpandoSXPParameter(
			expandoValue,
			value -> Arrays.equals(
				(String[])value, expandoValue.getStringArray()),
			ExpandoColumnConstants.STRING_ARRAY);
	}

	@Test
	public void testStringArrayLocalizedExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			new String[] {"one", "two"}
		).when(
			expandoValue
		).getStringArray(
			Mockito.any(Locale.class)
		);

		_testExpandoSXPParameter(
			expandoValue, "expandotest",
			value -> Arrays.equals(
				(String[])value, expandoValue.getStringArray(_locale)),
			"user.custom.field.expandotest_" + _locale.toString(),
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);
	}

	@Test
	public void testStringExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			"one"
		).when(
			expandoValue
		).getString();

		_testExpandoSXPParameter(
			expandoValue, value -> value.equals(expandoValue.getString()),
			ExpandoColumnConstants.STRING);
	}

	@Test
	public void testStringLocalizedExpandoAttribute() throws Exception {
		ExpandoValue expandoValue = Mockito.mock(ExpandoValue.class);

		Mockito.doReturn(
			"string localized"
		).when(
			expandoValue
		).getString(
			Mockito.any(Locale.class)
		);

		_testExpandoSXPParameter(
			expandoValue, "expandotest",
			value -> value.equals(expandoValue.getString(_locale)),
			"user.custom.field.expandotest_" + _locale.toString(),
			ExpandoColumnConstants.STRING_LOCALIZED);
	}

	@Test
	public void testUserGroupIds() throws Exception {
		long[] groupIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		UserGroup userGroup1 = Mockito.mock(UserGroup.class);

		Mockito.doReturn(
			groupIds[0]
		).when(
			userGroup1
		).getUserGroupId();

		UserGroup userGroup2 = Mockito.mock(UserGroup.class);

		Mockito.doReturn(
			groupIds[1]
		).when(
			userGroup2
		).getUserGroupId();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Arrays.asList(userGroup1, userGroup2)),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(
			_exists(
				"user.user_group_ids",
				value -> Arrays.equals(
					ArrayUtils.toPrimitive((Long[])value), groupIds)));
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

	private AssetCategoryLocalService _mockAssetCategoryLocalService(
		List<AssetCategory> assetCategories) {

		AssetCategoryLocalService assetCategoryLocalService = Mockito.mock(
			AssetCategoryLocalService.class);

		Mockito.doReturn(
			assetCategories
		).when(
			assetCategoryLocalService
		).getCategories(
			Mockito.anyString(), Mockito.anyLong()
		);

		return assetCategoryLocalService;
	}

	private AssetTagLocalService _mockAssetTagLocalService(
		List<AssetTag> assetTags) {

		AssetTagLocalService assetTagLocalService = Mockito.mock(
			AssetTagLocalService.class);

		Mockito.doReturn(
			assetTags
		).when(
			assetTagLocalService
		).getTags(
			Mockito.anyString(), Mockito.anyLong()
		);

		return assetTagLocalService;
	}

	private ExpandoColumn _mockExpandoColumn(
		long columnId, String name, int type) {

		ExpandoColumn expandoColumn = Mockito.mock(ExpandoColumn.class);

		Mockito.doReturn(
			columnId
		).when(
			expandoColumn
		).getColumnId();

		Mockito.doReturn(
			name
		).when(
			expandoColumn
		).getName();

		Mockito.doReturn(
			type
		).when(
			expandoColumn
		).getType();

		return expandoColumn;
	}

	private ExpandoColumnLocalService _mockExpandoColumnLocalService(
		List<ExpandoColumn> expandoColumns) {

		ExpandoColumnLocalService expandoColumnLocalService = Mockito.mock(
			ExpandoColumnLocalService.class);

		Mockito.doReturn(
			expandoColumns
		).when(
			expandoColumnLocalService
		).getDefaultTableColumns(
			Mockito.anyLong(), Mockito.anyString()
		);

		return expandoColumnLocalService;
	}

	private ExpandoValueLocalService _mockExpandoValueLocalService(
		List<ExpandoValue> expandoValues) {

		ExpandoValueLocalService expandoValueLocalService = Mockito.mock(
			ExpandoValueLocalService.class);

		Mockito.doReturn(
			expandoValues
		).when(
			expandoValueLocalService
		).getRowValues(
			Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
			Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt()
		);

		return expandoValueLocalService;
	}

	private GroupLocalService _mockGroupLocalService(long[] roleIds) {
		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Mockito.doReturn(
			roleIds
		).when(
			groupLocalService
		).getRolePrimaryKeys(
			Mockito.anyLong()
		);

		return groupLocalService;
	}

	private void _mockLanguage() {
		Mockito.doReturn(
			_locale.toString()
		).when(
			_language
		).getLanguageId(
			Mockito.any(Locale.class)
		);
	}

	private void _mockPortal() {
		Mockito.doReturn(
			true
		).when(
			_portal
		).isOmniadmin(
			Mockito.anyLong()
		);
	}

	private SegmentsEntryRetriever _mockSegmentsEntryRetriever(
		long[] segmentsEntryIds) {

		SegmentsEntryRetriever segmentsEntryRetriever = Mockito.mock(
			SegmentsEntryRetriever.class);

		Mockito.doReturn(
			segmentsEntryIds
		).when(
			segmentsEntryRetriever
		).getSegmentsEntryIds(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.any()
		);

		return segmentsEntryRetriever;
	}

	private void _mockUser() throws Exception {
		Date date = new Date();
		long userId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			date
		).when(
			_user
		).getBirthday();

		Mockito.doReturn(
			date
		).when(
			_user
		).getCreateDate();

		Mockito.doReturn(
			"john.doe@liferay.com"
		).when(
			_user
		).getEmailAddress();

		Mockito.doReturn(
			"John"
		).when(
			_user
		).getFirstName();

		Mockito.doReturn(
			"John Doe"
		).when(
			_user
		).getFullName();

		Mockito.doReturn(
			"Engineer"
		).when(
			_user
		).getJobTitle();

		Mockito.doReturn(
			_locale.toString()
		).when(
			_user
		).getLanguageId();

		Mockito.doReturn(
			"Doe"
		).when(
			_user
		).getLastName();

		Mockito.doReturn(
			User.class.getName()
		).when(
			_user
		).getModelClassName();

		Mockito.doReturn(
			false
		).when(
			_user
		).isGuestUser();

		Mockito.doReturn(
			true
		).when(
			_user
		).isFemale();

		Mockito.doReturn(
			false
		).when(
			_user
		).isMale();

		Mockito.doReturn(
			_user.getUserId()
		).when(
			_user
		).getPrimaryKey();

		Mockito.doReturn(
			_randomLongArray(2)
		).when(
			_user
		).getGroupIds();

		Mockito.doReturn(
			_randomLongArray(2)
		).when(
			_user
		).getRoleIds();

		Mockito.doReturn(
			userId
		).when(
			_user
		).getUserId();
	}

	private UserGroupGroupRoleLocalService _mockUserGroupGroupRoleLocalService(
		List<UserGroupGroupRole> userGroupGroupRoles) {

		UserGroupGroupRoleLocalService userGroupGroupRoleLocalService =
			Mockito.mock(UserGroupGroupRoleLocalService.class);

		Mockito.doReturn(
			userGroupGroupRoles
		).when(
			userGroupGroupRoleLocalService
		).getUserGroupGroupRolesByUser(
			Mockito.anyLong(), Mockito.anyLong()
		);

		return userGroupGroupRoleLocalService;
	}

	private UserGroupLocalService _mockUserGroupLocalService(
		List<UserGroup> userGroups) {

		UserGroupLocalService userGroupLocalService = Mockito.mock(
			UserGroupLocalService.class);

		Mockito.doReturn(
			userGroups
		).when(
			userGroupLocalService
		).getUserUserGroups(
			Mockito.anyLong()
		);

		return userGroupLocalService;
	}

	private UserGroupRoleLocalService _mockUserGroupRoleLocalService(
		List<UserGroupRole> userGroupRoles) {

		UserGroupRoleLocalService userGroupRoleLocalService = Mockito.mock(
			UserGroupRoleLocalService.class);

		Mockito.doReturn(
			userGroupRoles
		).when(
			userGroupRoleLocalService
		).getUserGroupRoles(
			Mockito.anyLong()
		);

		return userGroupRoleLocalService;
	}

	private UserLocalService _mockUserLocalService() {
		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.doReturn(
			_user
		).when(
			userLocalService
		).fetchUserById(
			Mockito.anyLong()
		);

		return userLocalService;
	}

	private long[] _randomLongArray(int length) {
		long[] array = new long[length];

		for (int i = 0; i < length; i++) {
			array[i] = RandomTestUtil.randomLong();
		}

		return array;
	}

	private void _testExpandoSXPParameter(
			ExpandoValue expandoValue, String expandoAttributeName,
			UnsafePredicate<Object, Exception> unsafePredicate,
			String sxpParameterName, int type)
		throws Exception {

		long columnId = RandomTestUtil.randomLong();

		Mockito.doReturn(
			columnId
		).when(
			expandoValue
		).getColumnId();

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(
				new ArrayList<ExpandoColumn>() {
					{
						add(
							_mockExpandoColumn(
								columnId, expandoAttributeName, type));
					}
				}),
			_mockExpandoValueLocalService(
				new ArrayList<ExpandoValue>() {
					{
						add(expandoValue);
					}
				}),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(_exists(sxpParameterName, unsafePredicate));
	}

	private void _testExpandoSXPParameter(
			ExpandoValue expandoValue,
			UnsafePredicate<Object, Exception> unsafePredicate, int type)
		throws Exception {

		_testExpandoSXPParameter(
			expandoValue, "expandotest", unsafePredicate,
			"user.custom.field.expandotest", type);
	}

	private void _testSXPParameter(
			UnsafePredicate<Object, Exception> unsafePredicate,
			String sxpParameterName)
		throws Exception {

		_userSXPParameterContributor = new UserSXPParameterContributor(
			_mockAssetCategoryLocalService(Collections.emptyList()),
			_mockAssetTagLocalService(Collections.emptyList()),
			_mockExpandoColumnLocalService(Collections.emptyList()),
			_mockExpandoValueLocalService(Collections.emptyList()),
			_mockGroupLocalService(new long[0]), _language, _portal,
			_mockSegmentsEntryRetriever(new long[0]),
			_mockUserGroupGroupRoleLocalService(Collections.emptyList()),
			_mockUserGroupLocalService(Collections.emptyList()),
			_mockUserGroupRoleLocalService(Collections.emptyList()),
			_mockUserLocalService());

		_userSXPParameterContributor.contribute(
			_exceptionListener, _searchContext, null, _sxpParameters);

		Assert.assertTrue(_exists(sxpParameterName, unsafePredicate));
	}

	private final ExceptionListener _exceptionListener = Mockito.mock(
		ExceptionListener.class);
	private final Language _language = Mockito.mock(Language.class);
	private final Locale _locale = LocaleUtil.US;
	private final Portal _portal = Mockito.mock(Portal.class);
	private final SearchContext _searchContext = new SearchContext();
	private final Set<SXPParameter> _sxpParameters = new HashSet<>();
	private final User _user = Mockito.mock(User.class);
	private UserSXPParameterContributor _userSXPParameterContributor;

}