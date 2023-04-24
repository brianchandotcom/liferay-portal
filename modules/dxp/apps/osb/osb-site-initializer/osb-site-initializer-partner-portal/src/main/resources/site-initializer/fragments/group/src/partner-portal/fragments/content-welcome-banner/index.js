/* eslint-disable no-undef */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const predissmissBanner = fragmentElement.querySelector('.pre-dismiss');
const posDissmissBanner = fragmentElement.querySelector('.pos-dismiss');
const dissmissButtonBanner = fragmentElement.querySelector('.on-click-button');
const isBannerClosed = sessionStorage.getItem(fragmentEntryLinkNamespace);

const toggleBanner = () => {
	predissmissBanner.classList.toggle('d-lg-flex');
	predissmissBanner.classList.toggle('d-none');

	posDissmissBanner.classList.toggle('d-none');
	posDissmissBanner.classList.toggle('d-lg-flex');

	dissmissButtonBanner.classList.toggle('d-none');
	dissmissButtonBanner.classList.toggle('d-flex');
};

fragmentElement.querySelector('.dismiss-button').onclick = () => {
	toggleBanner();
	sessionStorage.setItem(fragmentEntryLinkNamespace, true);
};

if (!isBannerClosed) {
	toggleBanner();
}
