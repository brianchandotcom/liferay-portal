/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import PropTypes from 'prop-types';
import '../../css/main.scss';
interface IProps {
	portletNamespace: string;
}
declare function App({portletNamespace: namespace}: IProps): JSX.Element | null;
declare namespace App {
	var propTypes: {
		portletNamespace: PropTypes.Validator<string>;
	};
}
export default App;
