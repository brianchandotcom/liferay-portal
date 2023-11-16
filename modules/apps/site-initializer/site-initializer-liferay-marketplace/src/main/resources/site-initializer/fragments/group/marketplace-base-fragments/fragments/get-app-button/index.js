/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


const baseURL = Liferay.ThemeDisplay.getPortalURL();
const scopeGroupId = themeDisplay.getScopeGroupId();
const isLogged = themeDisplay.isSignedIn();

const productID = fragmentElement
	.querySelector('.product-id')
	.innerText.replace(/[\n\r]+|[\s]{2,}/g, ' ')
	.trim();

const linkButton = fragmentElement.querySelector('.get-app-button');
const tooltip = document.createElement("div");
tooltip.className = "tooltiptext";
tooltip.style.visibility = "hidden";

const getAppButton = document.querySelector(".get-app-button");
getAppButton.parentNode.appendChild(tooltip);

const fetcher = async (url, {method = 'GET', ...options} = {}) => {
	const response = await fetch(`${baseURL}${url}`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method,
		...options,
	});

	if (response.ok) {
		if (method === 'DELETE' || response.status === 204) {
			return;
		}

		return response.json();
	}

	console.error('Failed to fetch user data:', response.status);

	throw new Error(response.json());
};

const redirectPage = () => {

	if (layoutMode !== 'edit') {
		linkButton.onclick = () => {
			window.location.href = `${getSiteURL()}/get-app?productId=${productID}`;
		};
	}

}

const mouseOver= () =>  getAppButton.addEventListener("mouseover", () => {
   tooltip.style.visibility = "visible";
   tooltip.style.opacity = "1";
});

const mouseOut = () => getAppButton.addEventListener("mouseout", () => {
	tooltip.style.visibility = "hidden";
	tooltip.style.opacity = "0";
});

const getChannel = async (siteId) => {
	const channel = await fetcher(
		`/o/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${siteId}'`
	);

	return channel?.items ?? [];

}

const getSkus = async (chanelId, productId) => {
	const skus = await fetcher(
		`/o/headless-commerce-delivery-catalog/v1.0/channels/${chanelId}/products/${productId}/skus?accountId=-1`
	);

	return skus?.items ?? [];
};

const getAccounts = async () => {
	const response = await fetcher(
		`/o/headless-commerce-admin-account/v1.0/accounts`,
	
	);

	return await response.items ?? [];
}

const getCatalogPerProduct = async (productId) => {
	return await fetcher(
		`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/catalog`,
	
	);

}

const getaccountAddress = async (accountId) => {
	return await fetcher(
		`/o/headless-commerce-admin-account/v1.0/accounts/${accountId}/accountAddresses`,
	
	);

}

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const openModal = () => {

	Liferay.Util.openModal({
		bodyHTML:`<div class="body-modal-container">
			<div class="body-content">
				<div class="body-modal-header d-flex" style="align-items: center; gap: 8px;">
					<div class="d-flex body-modal-logo" style="width:24px;">
					</div>
					<div class="body-modal-account" style="color: #282934; font-size: 20px; font-weight: 600;"></div>
				</div>
		
				<div class="body-modal-address pt-2">
					<div>
						<span class="street"></span>&nbsp;<span class="city"></span>
					</div>
					<div>
						<span class="zip"></span>
						<span class="countryISOCode"></span>
					</div>
				</div>
		
				<div class="body-modal-email d-flex pt-2"> 
					<span class="email"></span>
				</div>   
		
				<div class="body-modal-site-account d-flex pt-2"> 
					<a href=""></a>
				</div>   
			</div>  
		</div>`,
		buttons: [
			{
				displayType: 'secondary',
				label: 'Close',
				type: 'cancel',
			},
			
		],
		center: true,
		headerHTML: `Publisher Contact Info`,
		size: 'md',
	});		
	
	
};


const setItemsInModal = (accountData, accountAdress) => {

	const accountImage = accountData?.logoURL;
	const accountName = accountData?.name;
	const accountEmail = accountData?.customFields['Contact Email'];
	const homePageUrlAccount = `${getSiteURL()}/publisher-dashboard#/${accountData.id}`
	const accountAddresses = accountAdress;

	const addressStreet = document.querySelector('.body-modal-container .body-content .body-modal-address .street');
	const addressCity = document.querySelector('.body-modal-container .body-content .body-modal-address .city');
	const addressZip = document.querySelector('.body-modal-container .body-content .body-modal-address .zip');
	const addressCountry = document.querySelector('.body-modal-container .body-content .body-modal-address .countryISOCode');
	
	if(accountAddresses.length >= 1){

		for (const address of accountAddresses) {

			addressStreet.textContent = `${address?.street1},`;
			addressCity.textContent = `${address?.city},`;
			addressZip.textContent = `${address?.zip},`;
			addressCountry.textContent = address?.countryISOCode;
			break;
		}
	}
	const containerImage = document.querySelector('.body-modal-container .body-content .body-modal-header .body-modal-logo');
	const image = document.createElement('img');
	image.setAttribute('alt', '');
	image.setAttribute('src', accountImage);
	image.setAttribute('class', 'logo');
	image.style.width = '100%';
	image.style.height = '24px';
	image.style.borderRadius = '72px';
	image.style.objectFit = 'cover';
	containerImage.appendChild(image);


	// const image = document.querySelector('.body-modal-container .body-content .body-modal-header .body-modal-logo .logo');
	// image.setAttribute('src', accountImage);

	const account = document.querySelector('.body-modal-container .body-content .body-modal-header .body-modal-account');
	account.textContent = accountName;

	const email = document.querySelector('.body-modal-container .body-content .body-modal-email .email');
	email.textContent = accountEmail;

	const urlAccount = document.querySelector('.body-modal-container .body-content .body-modal-site-account a');
	const nameSplit = accountName.split(' ');
	const firstName = nameSplit[0].toLowerCase();
	const siteAccountName = firstName + ".com";
	urlAccount.innerHTML = siteAccountName
	urlAccount.setAttribute('href', homePageUrlAccount);
}


const openContactPublisherModal = async () => {
    openModal();

    const productCatalog = await getCatalogPerProduct(productID);
    const accounts = await getAccounts();
    const accountData = accounts.find(account => account?.customFields?.CatalogId === productCatalog.id.toString());
    const accountAddress = await getaccountAddress(accountData?.id);

    if (accountData) {
        setTimeout(() => {
            setItemsInModal(accountData, accountAddress?.items);
        }, 500);
    }
};

async function unavailableApp() {
    linkButton.innerText = "Contact Publisher";
    tooltip.textContent = "This app is not available for purchase in the Marketplace. It is either no longer available or supported. Please click to get further information on how to contact the publisher.";
    mouseOver();
    mouseOut();

    if (!isLogged) {
        linkButton.onclick = async () => {
			const url = Liferay.ThemeDisplay.getLayoutRelativeURL();
			const path = url.split('/').pop();
			localStorage.setItem("productName", path);
			location.href = `${getSiteURL()}/sign-in`;
        };
    } else {
		if(localStorage.getItem("productName") && isLogged){
			openContactPublisherModal();
			localStorage.removeItem("productName");
		}

    }
}


const main = async () => {
    let channelId = "";
    const channels = await getChannel(scopeGroupId);

    if (channels) {
        for (const channel of channels) {
            channelId = channel.id;
            break;
        }
    }

    if (channelId) {
		const skus = await getSkus(channelId, productID);
		const skuPublished = skus.some(sku => sku.purchasable);
	
		if (!skuPublished) {
			unavailableApp();
		} else {
			redirectPage();
		}
	}
};

main();
