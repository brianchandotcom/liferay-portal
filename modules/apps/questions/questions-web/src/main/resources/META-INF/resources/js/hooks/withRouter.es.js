/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useLocation, useNavigate, useParams} from 'react-router';

export function withRouter(Component) {
	return (props) => {
		const location = useLocation();
		const params = useParams();
		const navigate = useNavigate();

		const history = {
			goBack: () => navigate(-1),
			push: (to) => navigate(to, {replace: false}),
			replace: (to) => navigate(to, {replace: true}),
		};

		return (
			<Component
				{...props}
				history={history}
				location={location}
				navigate={navigate}
				params={params}
			/>
		);
	};
}
