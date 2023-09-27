/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {CONSTANTS} from '@liferay/accessibility-settings-state-web';
import PropTypes from 'prop-types';
declare type KEYS = keyof typeof CONSTANTS;
declare type Setting = {
	className: string;
	defaultValue: boolean;
	description: string;
	key: KEYS;
	label: string;
	sessionClicksValue: boolean;
};
declare type Props = {
	settings: Array<Setting>;
};
declare const AccessibilityMenu: {
	(props: Props): JSX.Element;
	propTypes: {
		settings: PropTypes.Validator<
			(PropTypes.InferProps<{
				className: PropTypes.Requireable<string>;
				defaultValue: PropTypes.Requireable<boolean>;
				key: PropTypes.Requireable<string>;
				label: PropTypes.Requireable<string>;
				sessionClicksValue: PropTypes.Requireable<boolean>;
			}> | null)[]
		>;
	};
};
export default AccessibilityMenu;
