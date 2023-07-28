/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.engine.CommerceInventoryEngine;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseLocalService;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.context.TestCommerceContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import org.frutilla.FrutillaRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CommerceInventoryWarehouseItemLocalServiceTest {

    @ClassRule
    @Rule
    public static final AggregateTestRule aggregateTestRule =
            new AggregateTestRule(
                    new LiferayIntegrationTestRule(),
                    PermissionCheckerMethodTestRule.INSTANCE);

    @Before
    public void setUp() throws Exception {
        _group = GroupTestUtil.addGroup();

        _user = UserTestUtil.addUser();

        _serviceContext = ServiceContextTestUtil.getServiceContext(
                _group.getCompanyId(), _group.getGroupId(), _user.getUserId());

        _cpInstance = CommerceInventoryTestUtil.addRandomCPInstanceSku(
                _group.getGroupId());

        _cpInstanceUnitOfMeasure =
            _cpInstanceUnitOfMeasureLocalService.addCPInstanceUnitOfMeasure(
                _user.getUserId(), _cpInstance.getCPInstanceId(), true, null,
                "KEY",
                HashMapBuilder.put(
                        LocaleUtil.getDefault(), "NOME"
                ).build(),
                2, true, 0.0, BigDecimal.ONE, _cpInstance.getSku());

    }

    @After
    public void tearDown() throws Exception {
        List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
            _commerceInventoryWarehouseLocalService.
                getCommerceInventoryWarehouses(_group.getCompanyId());

        for (CommerceInventoryWarehouse commerceInventoryWarehouse :
                commerceInventoryWarehouses) {

            _commerceInventoryWarehouseLocalService.
                deleteCommerceInventoryWarehouse(commerceInventoryWarehouse);
        }
    }

    public void testWarehouseItemsWithRightUOM() throws Exception {
        frutillaRule.scenario(
                "It should be possible to add items with UOM of the SKU"
        ).given(
                "1 active warehouse"
        ).when(
                "The item is add with UOM of the SKU"
        ).then(
                "The Item is correctly created"
        );

        CommerceInventoryWarehouse commerceInventoryWarehouseActive =
                CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
                        true, _serviceContext);

        Assert.assertNotNull(_commerceInventoryWarehouseItemLocalService.
            addCommerceInventoryWarehouseItem(
                StringPool.BLANK, _user.getUserId(),
                commerceInventoryWarehouseActive.
                    getCommerceInventoryWarehouseId(),
                BigDecimal.ONE, _cpInstance.getSku(),
                    _cpInstanceUnitOfMeasure.getKey()));
    }

    @Rule
    public FrutillaRule frutillaRule = new FrutillaRule();

    private static User _user;

    @Inject
    private CommerceInventoryWarehouseItemLocalService
            _commerceInventoryWarehouseItemLocalService;

    @Inject
    private CommerceInventoryWarehouseLocalService
            _commerceInventoryWarehouseLocalService;

    private CPInstance _cpInstance;

    private CPInstanceUnitOfMeasure _cpInstanceUnitOfMeasure;

    @Inject
    private CPInstanceLocalService _cpInstanceLocalService;

    @Inject
    private CPInstanceUnitOfMeasureLocalService _cpInstanceUnitOfMeasureLocalService;

    private Group _group;
    private ServiceContext _serviceContext;
}
