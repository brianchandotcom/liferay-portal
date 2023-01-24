function getParams() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    return {
        productIdParam: urlParams.get('productId'),
        accountIdParam: urlParams.get('accountId'),
        channelIdParam: urlParams.get('channelId'),
        investmentParam: urlParams.get('investment')
    }
}

export default getParams;