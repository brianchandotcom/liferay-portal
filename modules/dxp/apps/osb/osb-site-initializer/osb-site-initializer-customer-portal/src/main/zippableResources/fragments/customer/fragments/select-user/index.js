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



const userApplicationIdKey = 'customer-portal-user-application';
const userApplication = sessionStorage.getItem(userApplicationIdKey);

if (userApplication) {
    var user = JSON.parse(userApplication);

    fragmentElement.querySelector('#select-user-application').textContent = user.name;

    if (user.image) {
        fragmentElement.querySelector('#user-icon-application').src = user.image;
    }
} 