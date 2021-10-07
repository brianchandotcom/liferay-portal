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

const applicationIdKey = 'raylife-application-id';

const check = fragmentElement.querySelector("#hidden-images #check");
const uncheck = fragmentElement.querySelector("#hidden-images #uncheck");

const data = {
    productName: "Business Owners Policy",
    policyTerm: "10/1/2019 - 10/1/2020",
    price: "1,225",
    infoOne: {
        checked: true,
        value: "1,000,000"
    },
    infoTwo: {
        checked: true,
        value: "2,000,000"
    },
    infoThree: {
        checked: true,
        value: "50,000"
    },
    infoFour: {
        checked: true,
        value: "100,000"
    },
    infoFive: {
        checked: true,
        value: "500,000"
    },
};

const productName = document.getElementById(
    'congrats-information-title'
);
productName.innerHTML = data.productName;

const newPolicyNumber = localStorage.getItem(applicationIdKey);
if (newPolicyNumber) {
    document.getElementById('congrats-information-policy').textContent =
        'Policy: #' + newPolicyNumber;
}

const policyTerm = document.getElementById(
    'congrats-information-date'
);
policyTerm.innerHTML = data.policyTerm;


const policyAmount = document.getElementById(
    'congrats-information-price-first'
);
policyAmount.innerHTML = "$" + data.price;

const imageOne = document.getElementById(
    'congrats-table-info-check-one'
);
const priceOneChecked = document.getElementById(
    'price-one'
);
const priceOneUnchecked = document.getElementById(
    'list-info'
);
if (data.infoOne.checked) {
    imageOne.src = check.currentSrc;
    priceOneChecked.innerHTML = "$" + data.infoOne.value;
} else {
    priceOne.innerHTML = "&nbsp;";
    imageOne.src = uncheck.currentSrc;
    priceOneUnchecked.style.color = "#A0A0A4";
}

const imageTwo = document.getElementById(
    'congrats-table-info-check-two'
);
const priceTwoChecked = document.getElementById(
    'price-two'
);
const priceTwoUnchecked = document.getElementById(
    'list-info'
);
if (data.infoTwo.checked) {
    imageTwo.src = check.currentSrc;
    priceTwoChecked.innerHTML = "$" + data.infoTwo.value;
} else {
    priceTwo.innerHTML = "&nbsp;";
    imageTwo.src = uncheck.currentSrc;
    priceTwoUnchecked.style.color = "#A0A0A4";
}

const imageThree = document.getElementById(
    'congrats-table-info-check-three'
);
const priceThreeChecked = document.getElementById(
    'price-three'
);
const priceThreeUnchecked = document.getElementById(
    'list-info'
);
if (data.infoThree.checked) {
    imageThree.src = check.currentSrc;
    priceThreeChecked.innerHTML = "$" + data.infoThree.value;
} else {
    priceThree.innerHTML = "&nbsp;";
    imageThree.src = uncheck.currentSrc;
    priceThreeUnchecked.style.color = "#A0A0A4";
}

const imageFour = document.getElementById(
    'congrats-table-info-check-four'
);
const priceFourChecked = document.getElementById(
    'price-four'
);
const priceFourUnchecked = document.getElementById(
    'list-info'
);
if (data.infoFour.checked) {
    imageFour.src = check.currentSrc;
    priceFourChecked.innerHTML = "$" + data.infoFour.value;
} else {
    priceFour.innerHTML = "&nbsp;";
    imageFour.src = uncheck.currentSrc;
    priceFourUnchecked.style.color = "#A0A0A4";
}

const imageFive = document.getElementById(
    'congrats-table-info-check-five'
);
const priceFiveChecked = document.getElementById(
    'price-five'
);
const priceFiveUnchecked = document.getElementById(
    'list-info'
);
if (data.infoFive.checked) {
    imageFive.src = check.currentSrc;
    priceFiveChecked.innerHTML = "$" + data.infoFive.value;
} else {
    priceFive.innerHTML = "&nbsp;";
    imageFive.src = uncheck.currentSrc;
    priceFiveUnchecked.style.color = "#A0A0A4";
}