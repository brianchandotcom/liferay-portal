package com.liferay.commerce.data.integration.apio.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.data.integration.apio.identifiers.AccountIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.AddressIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.OrderIdentifier;
import com.liferay.commerce.data.integration.apio.internal.form.OrderUpdaterForm;
import com.liferay.commerce.data.integration.apio.internal.security.permission.OrderPermissionChecker;
import com.liferay.commerce.data.integration.apio.internal.util.OrderHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ServiceContextHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.ServerErrorException;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true)
public class OrderNestedCollectionResource
    implements
        NestedCollectionResource<CommerceOrder, Long,
            OrderIdentifier, Long, AccountIdentifier> {

    @Override
    public NestedCollectionRoutes<CommerceOrder, Long, Long> collectionRoutes(
            NestedCollectionRoutes.Builder<CommerceOrder, Long, Long> builder) {
        return builder.addGetter(
            this::_getPageItems
        ).build();
    }

    @Override
    public String getName() {
        return "orders";
    }

    @Override
    public ItemRoutes<CommerceOrder, Long> itemRoutes(ItemRoutes.Builder<CommerceOrder, Long> builder) {
        return builder.addGetter(
            this::_getCommerceOrder
        ).addUpdater(
            this::_updateCommerceOrder,
            _orderPermissionChecker.forUpdating()::apply,
            OrderUpdaterForm::buildForm
        ).build();
    }

    @Override
    public Representor<CommerceOrder> representor(Representor.Builder<CommerceOrder, Long> builder) {

        return builder.types(
            "Order"
        ).identifier(
            CommerceOrder::getCommerceOrderId
        ).addBidirectionalModel(
            "account", "orders", AccountIdentifier.class,
            CommerceOrder::getOrderOrganizationId
        ).addLinkedModel(
            "account", AccountIdentifier.class,
            CommerceOrder::getOrderOrganizationId
        ).addString(
            "purchaseOrderNumber",
            CommerceOrder::getPurchaseOrderNumber
        ).addNumber(
            "shippingPrice",
            CommerceOrder::getShippingPrice
        ).addNumber(
            "total",
            CommerceOrder::getTotal
        ).addString(
            "orderStatus",
            (order) -> CommerceOrderConstants.getOrderStatusLabel(order.getOrderStatus())
        ).addString(
            "paymentStatus",
            (order) -> CommerceOrderConstants.getPaymentStatusLabel(order.getPaymentStatus())
        ).addDate(
            "dateCreated",
            CommerceOrder::getCreateDate
        ).addDate(
            "dateModified",
            CommerceOrder::getModifiedDate
        ).addLinkedModel(
            "author", PersonIdentifier.class,
            CommerceOrder::getUserId
        ).addLinkedModel(
            "shippingAddress", AddressIdentifier.class,
            CommerceOrder::getShippingAddressId
        ).addLinkedModel(
            "billingAddress", AddressIdentifier.class,
            CommerceOrder::getBillingAddressId
        ).build();
    }

    private PageItems<CommerceOrder> _getPageItems(
            Pagination pagination, Long organizationId) {
        try {

            Organization account = _commerceOrganizationService.getOrganization(organizationId);
            Group group = account.getGroup();

            List<CommerceOrder> orders = _commerceOrderService.getCommerceOrdersByGroupId(group.getGroupId(), pagination.getStartPosition(),
                    pagination.getEndPosition(), null);

            int total = _commerceOrderService.getCommerceOrdersCountByGroupId(group.getGroupId());

            return new PageItems<>(orders, total);
        }
        catch (PortalException pe) {
            throw new ServerErrorException(500, pe);
        }
    }

    private CommerceOrder _getCommerceOrder(Long commerceOrderId) {
        try {
            return _commerceOrderService.getCommerceOrder(commerceOrderId);
        }
        catch (PortalException pe) {
            throw new ServerErrorException(500, pe);
        }
    }

    private CommerceOrder _updateCommerceOrder(
            Long commerceOrderId, OrderUpdaterForm orderForm)
            throws PortalException {

        return _orderHelper.updateCommerceOrder(commerceOrderId, orderForm.getOrderStatus(), orderForm.getPaymentStatus());
    }

    @Reference
    private CommerceOrderService _commerceOrderService;

    @Reference
    private CommerceOrganizationService _commerceOrganizationService;

    @Reference
    private OrderPermissionChecker _orderPermissionChecker;

    @Reference
    private OrderHelper _orderHelper;


}