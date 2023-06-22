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

import PropTypes from 'prop-types';
declare type Setting = {
	className: string;
	defaultValue: boolean;
	key: string;
	label: string;
	sessionClicksValue: boolean;
};
declare type Props = {
	settings: Array<Setting>;
};
export declare const accessibilityMenuAtom: {
	readonly 'default': readonly {
		readonly className: string;
		readonly key: string;
		readonly label: string;
		readonly updating?: boolean | undefined;
		readonly value: boolean;
	}[];
	readonly 'key': string;
	readonly 'Liferay.State.ATOM': true;
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
