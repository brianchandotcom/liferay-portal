//API Calls to get product and product informations

export function getProduct(productId) {
    return fetch(`/o/headless-commerce-admin-catalog/v1.0/products/${productId}?p_auth=${window.Liferay.authToken}`)
        .then((response) => response.json());
}

export function getSpecificationsProduct(productId) {
    return fetch(`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productSpecifications?p_auth=${window.Liferay.authToken}`)
        .then((response) => response.json());
}
export function getSkuProduct(productId) {
    return fetch(`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/skus?p_auth=${window.Liferay.authToken}`)
        .then(response => response.json());
}
