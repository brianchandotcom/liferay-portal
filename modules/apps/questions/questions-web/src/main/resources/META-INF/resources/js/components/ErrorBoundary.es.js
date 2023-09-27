/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

export class ErrorBoundary extends React.Component {
	constructor(props) {
		super(props);
		this.state = {hasError: false};
	}

	static getDerivedStateFromError(error) {
		return {error, hasError: true};
	}

	componentDidCatch(_error, _errorInfo) {}

	render() {
		if (this.state.hasError) {
			if (process.env.NODE_ENV === 'development') {
				console.error(this.state.error);
			}

			return (
				<>
					<ClayAlert
						autoClose={5000}
						displayType="danger"
						title="Error:"
					>
						Could not load the page
					</ClayAlert>
				</>
			);
		}

		return this.props.children;
	}
}
