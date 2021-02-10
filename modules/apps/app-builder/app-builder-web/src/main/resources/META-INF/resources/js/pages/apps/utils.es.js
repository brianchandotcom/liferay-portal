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

import {ADMINISTRATION_SCOPE_VALUE} from './constants.es';

/**
 * @description Verifiy if appDeployments contains type productMenu
 * if productMenu is selected, check if contains at least one scope
 * @param {*} app state
 */

export function isProductMenuValid({appDeployments}) {
	const productMenuDeployment = appDeployments.find(
		(deployment) => deployment.type === 'productMenu'
	);

	if (productMenuDeployment) {
		const {
			settings: {scope, siteIds = []},
		} = productMenuDeployment;

		if (scope.includes(ADMINISTRATION_SCOPE_VALUE)) {
			return siteIds.length > 0;
		}
	}

	return true;
}
