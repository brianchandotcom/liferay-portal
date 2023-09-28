/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OptionHTMLAttributes} from 'react';

/**
 * Utility function to allow passing readonly arrays (declared `as const`) to
 * ClaySelect when it requires a non-readonly OptionHTMLAttributes element.
 *
 * We know that Clay is not going to mutate the array, so we can safely mark
 * it as mutable.
 */
export declare function getSelectOptions(
	options: Readonly<Array<OptionHTMLAttributes<HTMLOptionElement>>>
): OptionHTMLAttributes<HTMLOptionElement>[];
