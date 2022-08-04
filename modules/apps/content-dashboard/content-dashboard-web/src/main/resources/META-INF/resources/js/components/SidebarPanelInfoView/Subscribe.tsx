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

import {ClayButtonWithIcon} from '@clayui/button';
import {fetch, openToast} from 'frontend-js-web';
import React, {useContext} from 'react';

const {SidebarContext} = require('../Sidebar');

const Subscribe = ({icon, label, url}: IProps) => {
	const {fetchData} = useContext(SidebarContext);

	const handleSubscribe = async (): Promise<void> => {
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
			borderless
			className="mr-2"
			data-tooltip-align="bottom"
			displayType="secondary"
			onClick={handleSubscribe}
			symbol={icon}
			title={label}
		/>
	);
};

interface IProps {
	children?: React.ReactNode;
	icon: string;
	label: string;
	url: RequestInfo;
}

export default Subscribe;
