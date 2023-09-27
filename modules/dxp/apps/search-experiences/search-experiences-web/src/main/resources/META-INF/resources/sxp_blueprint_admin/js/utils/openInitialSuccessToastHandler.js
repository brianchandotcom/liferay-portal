/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openInitialSuccessToast} from './toasts';

/**
 * Handler function for jsp files to show the a success toast when the page
 * loads.
 *
 * Example usage:
 * <liferay-frontend:component
 *   module="sxp_blueprint_admin/js/utils/openInitialSuccessToastHandler"
 * />
 */
export default function () {
	openInitialSuccessToast();
}
