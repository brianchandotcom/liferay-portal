/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import PropTypes from 'prop-types';
import {ViewportSize} from '../config/constants/viewportSizes';
interface Props {
	onSizeSelected: (sizeId: ViewportSize) => void;
	selectedSize: ViewportSize;
}
declare function ViewportSizeSelector({
	onSizeSelected,
	selectedSize,
}: Props): JSX.Element;
declare namespace ViewportSizeSelector {
	var propTypes: {
		onSizeSelected: PropTypes.Requireable<(...args: any[]) => any>;
		selectedSize: PropTypes.Requireable<string>;
	};
}
export default ViewportSizeSelector;
