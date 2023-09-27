/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect} from 'react';

import {AppContext} from '../../../components/AppContext.es';
import HeaderBackButton from './HeaderBackButton.es';
import HeaderReindexStatus from './HeaderReindexStatus.es';
import HeaderTitle from './HeaderTitle.es';

const HeaderController = ({basePath}) => {
	const {portletNamespace, title} = useContext(AppContext);
	const header = document.getElementById(`${portletNamespace}controlMenu`);

	const container = header
		? {
				button: header.querySelector(
					'.sites-control-group .control-menu-nav'
				),
				status: header.querySelector(
					'.user-control-group li.control-menu-nav-item'
				),
				title: header.querySelector(
					'.tools-control-group .control-menu-level-1-heading'
				),
		  }
		: {};

	useEffect(() => {
		const legacyElement = document.querySelector(
			'[data-qa-id="headerOptions"]'
		);

		if (legacyElement) {
			legacyElement.innerHTML = '';
		}
	}, []);

	return (
		<>
			<HeaderBackButton
				basePath={basePath}
				container={container.button}
			/>

			<HeaderReindexStatus container={container.status} />

			<HeaderTitle container={container.title} title={title} />
		</>
	);
};

export default HeaderController;
