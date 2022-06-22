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

import {Liferay} from '../../services/liferay';
import {ROUTE_TYPES} from './routeTypes';

const siteURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

const LiferayURL = `${Liferay.ThemeDisplay.getPortalURL()}${siteURL.substring(
	0,
	siteURL.lastIndexOf('/')
)}`;

export const PAGE_ROUTER_TYPES = {
	onboarding: (externalReferenceCode) =>
		`${LiferayURL}/${ROUTE_TYPES.onboarding}/#/${externalReferenceCode}`,
	project: (externalReferenceCode) =>
		`${LiferayURL}/${ROUTE_TYPES.project}/#/${externalReferenceCode}`,
};
