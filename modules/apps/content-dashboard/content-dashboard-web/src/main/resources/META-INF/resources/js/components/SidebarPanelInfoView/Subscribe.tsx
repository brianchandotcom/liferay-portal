/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {fetch, openToast} from 'frontend-js-web';
import React, {useContext} from 'react';

const {SidebarContext} = require('../Sidebar');

const Subscribe = ({disabled, icon, label, url}: IProps) => {
	const {fetchData} = useContext(SidebarContext);

	const handleSubscribe = async (): Promise<void> => {
		if (disabled) {
			return;
		}

		try {
			const {ok}: Response = await fetch(url);

			if (!ok) {
				throw new Error(`Failed to fetch ${url}`);
			}

			await fetchData();

			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
		}
		catch (error: unknown) {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		}
	};

	return (
		<ClayButtonWithIcon
			aria-label={label}
			className="component-action mr-2"
			data-tooltip-align="bottom"
			disabled={disabled}
			displayType="unstyled"
			onClick={handleSubscribe}
			symbol={icon}
			title={label}
		/>
	);
};

interface IProps {
	children?: React.ReactNode;
	disabled: boolean;
	icon: string;
	label: string;
	url: RequestInfo;
}

export default Subscribe;
