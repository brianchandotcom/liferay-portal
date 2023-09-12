/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';
import {Size} from '../constants/sizes';
interface IPreviewProps {
	activeSize: Size;
	open: boolean;
	previewRef: React.RefObject<HTMLDivElement>;
}
declare function Preview({
	activeSize,
	open,
	previewRef,
}: IPreviewProps): JSX.Element | null;
declare namespace Preview {
	var propTypes: {
		activeSize: PropTypes.Validator<object>;
		previewRef: PropTypes.Validator<object>;
	};
}
export default Preview;
