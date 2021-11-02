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

import ClayEmptyState from '@clayui/empty-state';
import {openToast} from 'frontend-js-web';
import React, {Component} from 'react';

import {DEFAULT_ERROR} from '../utils/constants';

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
