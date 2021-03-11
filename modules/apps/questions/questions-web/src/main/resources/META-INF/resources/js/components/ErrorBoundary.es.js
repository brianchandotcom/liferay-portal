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
