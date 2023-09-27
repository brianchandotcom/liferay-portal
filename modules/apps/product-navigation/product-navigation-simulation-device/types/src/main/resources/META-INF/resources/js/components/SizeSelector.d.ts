/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';
import {Size} from '../constants/sizes';
interface ISizeSelectorProps {
	activeSize: Size;
	namespace: string;
	open: boolean;
	previewRef: React.RefObject<HTMLDivElement>;
	setActiveSize: Function;
}
declare function SizeSelector({
	activeSize,
	namespace,
	open,
	previewRef,
	setActiveSize,
}: ISizeSelectorProps): JSX.Element;
declare namespace SizeSelector {
	var propTypes: {
		activeSize: PropTypes.Validator<object>;
		namespace: PropTypes.Validator<string>;
		previewRef: PropTypes.Validator<object>;
		setActiveSize: PropTypes.Validator<(...args: any[]) => any>;
	};
}
export default SizeSelector;
