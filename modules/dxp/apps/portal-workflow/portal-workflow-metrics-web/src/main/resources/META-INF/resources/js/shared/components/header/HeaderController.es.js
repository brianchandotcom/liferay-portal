/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useContext, useMemo} from 'react';

import {AppContext} from '../../../components/AppContext.es';
import HeaderBackButton from './HeaderBackButton.es';
import HeaderTitle from './HeaderTitle.es';

const HeaderController = ({basePath}) => {
	const {portletNamespace, title} = useContext(AppContext);

	const container = useMemo(() => {
		const header = document.getElementById(
			`_${portletNamespace}_controlMenu`
		);

		if (!header) {
			return {};
		}

		return {
			button: header.querySelector(
				'.sites-control-group .control-menu-nav'
			),
			title: header.querySelector(
				'.tools-control-group .control-menu-level-1-heading'
			)
		};
	}, [portletNamespace]);

	return (
		<>
			<HeaderBackButton
				basePath={basePath}
				container={container.button}
			/>

			<HeaderTitle container={container.title} title={title} />
		</>
	);
};

export default HeaderController;
