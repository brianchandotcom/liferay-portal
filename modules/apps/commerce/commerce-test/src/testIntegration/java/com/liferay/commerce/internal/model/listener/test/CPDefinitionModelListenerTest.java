package com.liferay.commerce.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.subscription.service.SubscriptionLocalServiceUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CPDefinitionModelListenerTest {

    @ClassRule
    @Rule
    public static final AggregateTestRule aggregateTestRule =
            new AggregateTestRule(
                    new LiferayIntegrationTestRule(),
                    PermissionCheckerMethodTestRule.INSTANCE);

    @BeforeClass
    public static void setUpClass() throws Exception {
        _company = CompanyTestUtil.addCompany();

        _user = UserTestUtil.addUser(_company);
    }

    @Before
    public void setUp() throws Exception {
        _commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
                null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
                LocaleUtil.US.getDisplayLanguage(),
                ServiceContextTestUtil.getServiceContext(_company.getGroupId()));
    }

    @Test
    public void testAddCPDefinitionInventory() throws Exception {

        frutillaRule.scenario(
                "Add product definition"
        ).given(
                "The product definition is created"
        ).then(
                "product definition inventory should be ADDED"
        );

        CPDefinition cpDefinition = CPTestUtil.addCPDefinitionFromCatalog(
                _commerceCatalog.getGroupId(), SimpleCPTypeConstants.NAME, true,
                false);

        CPDefinitionInventory cpDefinitionInventory =
                _cpDefinitionInventoryService.
                        fetchCPDefinitionInventoryByCPDefinitionId(cpDefinition.getCPDefinitionId());


        Assert.assertNotNull(cpDefinitionInventory);
    }

    @Rule
    public FrutillaRule frutillaRule = new FrutillaRule();

    private CommerceCatalog _commerceCatalog;

    @Inject
    CPDefinitionInventoryService _cpDefinitionInventoryService;

    private static Company _company;
    private static User _user;
}
