export function createOrder(channelId, accountId, productId, skuId) {
    return fetch(`/o/headless-commerce-delivery-cart/v1.0/channels/${channelId}/carts?p_auth=${window.Liferay.authToken}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "accountId": accountId,
            "cartItems": [
                {
                    "price": {
                        "currency": "US Dollar",
                        "finalPrice": 12123,
                        "price": 12313,
                    },
                    "productId": productId,
                    "quantity": 1,
                    "skuId": skuId
                }
            ],
            "summary": {
                "currency": "US Dollar",
                "itemsQuantity": 1,
                "subtotal": 10,
                "total": 20
            },
            "currencyCode": "USD"
        })
    }).then(response => response.json());
}

export function checkoutOrder(cartId) {
    return fetch(`/o/headless-commerce-delivery-cart/v1.0/carts/${cartId}/checkout?p_auth=${window.Liferay.authToken}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    }).then(response => response.json());
}

export function insertOrderObject(orderId, productId, investmentAmount) {
    return fetch(`/o/c/crowseinvestmentobjects?p_auth=${window.Liferay.authToken}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            "investimentCommitment": +investmentAmount,
            "phoneNumber": "12312312",
            "orderId": `${orderId}`,
            "productId": productId
        })
    });
}