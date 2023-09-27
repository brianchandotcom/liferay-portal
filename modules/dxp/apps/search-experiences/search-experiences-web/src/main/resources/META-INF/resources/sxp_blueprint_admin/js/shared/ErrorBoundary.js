/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import {openToast} from 'frontend-js-web';
import React, {Component} from 'react';

import {DEFAULT_ERROR} from '../utils/errorMessages';

class ErrorBoundary extends Component {
	state = {
		hasError: false,
	};

	static getDerivedStateFromError() {
		return {hasError: true};
	}

	/**
	 * Displays a notification toast when the component is unable to load.
	 */
	componentDidCatch() {
		if (this.props.toast) {
			openToast({
				message: DEFAULT_ERROR,
				type: 'danger',
			});
		}
	}

	render() {
		return this.state.hasError
			? !this.props.toast && (
					<ClayEmptyState
						description={DEFAULT_ERROR}
						imgSrc="/o/admin-theme/images/states/empty_state.gif"
						title={Liferay.Language.get('unable-to-load-content')}
					/>
			  )
			: this.props.children;
	}
}

export default ErrorBoundary;
