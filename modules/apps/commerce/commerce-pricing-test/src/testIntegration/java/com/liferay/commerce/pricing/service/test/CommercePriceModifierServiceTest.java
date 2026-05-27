/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalService;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.test.util.price.list.CommercePriceListTestUtil;
import com.liferay.commerce.test.util.pricing.CommercePriceModifierTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.math.BigDecimal;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class CommercePriceModifierServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			group.getCompanyId());

		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		_user = UserTestUtil.addUser();

		_userLocalService.addRoleUser(_role.getRoleId(), _user);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getCompanyId(), group.getGroupId(),
			TestPropsValues.getUserId());

		_commerceCatalog = _commerceCatalogLocalService.addCommerceCatalog(
			null, RandomTestUtil.randomString(), _commerceCurrency.getCode(),
			LocaleUtil.US.getDisplayLanguage(), _serviceContext);

		_commercePriceList = CommercePriceListTestUtil.addCommercePriceList(
			_commerceCatalog.getGroupId(), TestPropsValues.getUser(),
			_commerceCurrency, 0.0, _serviceContext);
	}

	@Test
	public void testAddCommercePriceModifier() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addCommercePriceModifier();

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addCommercePriceModifier();
		}
	}

	@Test
	public void testAddOrUpdateCommercePriceModifier() throws Exception {
		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), TestPropsValues.getUser(),
				_commerceCurrency, 0.0, _serviceContext);

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				_commerceCatalog.getGroupId(), TestPropsValues.getUser(),
				commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addOrUpdateCommercePriceModifier(
				RandomTestUtil.randomString(), _commercePriceList,
				commercePriceModifier.getCommercePriceModifierId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addOrUpdateCommercePriceModifier(
				RandomTestUtil.randomString(), _commercePriceList,
				commercePriceModifier.getCommercePriceModifierId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_addOrUpdateCommercePriceModifier(
				RandomTestUtil.randomString(), _commercePriceList,
				commercePriceModifier.getCommercePriceModifierId());
		}
	}

	@Test
	public void testDeleteCommercePriceModifier() throws Exception {
		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				_commerceCatalog.getGroupId(), _user,
				_commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.deleteCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.deleteCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());
		}
	}

	@Test
	public void testFetchCommercePriceModifier() throws Exception {
		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertNull(
				_commercePriceModifierService.fetchCommercePriceModifier(0L));
		}

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				_commerceCatalog.getGroupId(), _user,
				_commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.fetchCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.fetchCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());
		}
	}

	@Test
	public void testFetchCommercePriceModifierByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = RandomTestUtil.randomString();

		_addOrUpdateCommercePriceModifier(
			externalReferenceCode, _commercePriceList, 0);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.
				fetchCommercePriceModifierByExternalReferenceCode(
					externalReferenceCode, _commercePriceList.getCompanyId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.
				fetchCommercePriceModifierByExternalReferenceCode(
					externalReferenceCode, _commercePriceList.getCompanyId());
		}
	}

	@Test
	public void testGetCommercePriceModifier() throws Exception {
		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				_commerceCatalog.getGroupId(), _user,
				_commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifier(
				commercePriceModifier.getCommercePriceModifierId());
		}
	}

	@Test
	public void testGetCommercePriceModifiers() throws Exception {
		CommercePriceModifierTestUtil.addCommercePriceModifier(
			_commerceCatalog.getGroupId(), _user,
			_commercePriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
			_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifiers(
				_commercePriceList.getCommercePriceListId(), 0, 10, null);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifiers(
				_commercePriceList.getCommercePriceListId(), 0, 10, null);
		}
	}

	@Test
	public void testGetCommercePriceModifiersCount() throws Exception {
		CommercePriceModifierTestUtil.addCommercePriceModifier(
			_commerceCatalog.getGroupId(), _user,
			_commercePriceList.getCommercePriceListId(),
			BigDecimal.valueOf(RandomTestUtil.randomDouble()),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
			_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifiersCount(
				_commercePriceList.getCommercePriceListId());

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.VIEW, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.VIEW);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_commercePriceModifierService.getCommercePriceModifiersCount(
				_commercePriceList.getCommercePriceListId());
		}
	}

	@Test
	public void testUpdateCommercePriceModifier() throws Exception {
		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				_commerceCatalog.getGroupId(), TestPropsValues.getUser(),
				_commerceCurrency, 0.0, _serviceContext);

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierTestUtil.addCommercePriceModifier(
				_commerceCatalog.getGroupId(), TestPropsValues.getUser(),
				commercePriceList.getCommercePriceListId(),
				BigDecimal.valueOf(RandomTestUtil.randomDouble()),
				CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, true,
				_serviceContext);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_updateCommercePriceModifier(
				_commercePriceList, commercePriceModifier);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_updateCommercePriceModifier(
				_commercePriceList, commercePriceModifier);

			Assert.fail();
		}
		catch (Exception exception) {
			_assertMessage(
				ActionKeys.UPDATE, exception.getMessage(), _user.getUserId());
		}

		_setResourcePermissions(_commercePriceList, ActionKeys.UPDATE);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			_updateCommercePriceModifier(
				_commercePriceList, commercePriceModifier);
		}
	}

	private void _addCommercePriceModifier() throws PortalException {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		_commercePriceModifierService.addCommercePriceModifier(
			RandomTestUtil.randomString(), _commercePriceList.getGroupId(),
			_commercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomString(),
			CommercePriceModifierConstants.TARGET_CATALOG,
			BigDecimal.valueOf(RandomTestUtil.randomDouble()),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, 0.0, true,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, _serviceContext);
	}

	private void _addOrUpdateCommercePriceModifier(
			String externalReferenceCode, CommercePriceList commercePriceList,
			long commercePriceModifier)
		throws PortalException {

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		_commercePriceModifierService.addOrUpdateCommercePriceModifier(
			externalReferenceCode, commercePriceModifier,
			commercePriceList.getGroupId(),
			commercePriceList.getCommercePriceListId(),
			RandomTestUtil.randomString(),
			CommercePriceModifierConstants.TARGET_CATALOG,
			BigDecimal.valueOf(RandomTestUtil.randomDouble()),
			CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE, 0.0, true,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, _serviceContext);
	}

	private void _assertMessage(String actionKey, String message, long userId) {
		Assert.assertTrue(
			message.contains(
				StringBundler.concat(
					"User ", userId, " must have ", actionKey,
					" permission for")));
	}

	private void _setResourcePermissions(
			CommercePriceList commercePriceList, String actionId)
		throws Exception {

		_resourcePermissionLocalService.setResourcePermissions(
			commercePriceList.getCompanyId(), CommercePriceList.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(commercePriceList.getCommercePriceListId()),
			_role.getRoleId(), new String[] {actionId});
	}

	private void _updateCommercePriceModifier(
			CommercePriceList commercePriceList,
			CommercePriceModifier commercePriceModifier)
		throws PortalException {

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		_commercePriceModifierService.updateCommercePriceModifier(
			commercePriceModifier.getCommercePriceModifierId(),
			commercePriceList.getGroupId(),
			commercePriceList.getCommercePriceListId(),
			commercePriceModifier.getTitle(), commercePriceModifier.getTarget(),
			commercePriceModifier.getModifierAmount(),
			commercePriceModifier.getModifierType(),
			commercePriceModifier.getPriority(),
			commercePriceModifier.isActive(), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), true, _serviceContext);
	}

	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	private CommerceCurrency _commerceCurrency;
	private CommercePriceList _commercePriceList;

	@Inject
	private CommercePriceModifierLocalService
		_commercePriceModifierLocalService;

	@Inject
	private CommercePriceModifierService _commercePriceModifierService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private Role _role;
	private ServiceContext _serviceContext;
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}