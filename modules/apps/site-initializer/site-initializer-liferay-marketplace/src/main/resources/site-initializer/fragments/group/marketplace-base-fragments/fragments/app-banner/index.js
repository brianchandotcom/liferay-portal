/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

Liferay.on("copy-link", () => {
  const url = window.location.href;
  navigator.clipboard.writeText(url)
    Liferay.Util.openToast({message:"Copied link to the clipboard",type: "success"})

})

Liferay.on("contact-publisher", () => {
  // eslint-disable-next-line no-undef
  const emailAddress = `${publisherEmail}`;
  const mailtoLink = `mailto: ${emailAddress}`;

  window.location.href = mailtoLink;

})

Liferay.on("start-trial", () => {
// eslint-disable-next-line no-undef
const finalURL = `purchase-product-form?productId=${configuration.productID}`
window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/${finalURL}`;
})

const getSiteURL = () => {
const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

if (layoutRelativeURL.includes('web')) {
  return layoutRelativeURL.split('/').slice(0, 3).join('/');
}

return '';
};
