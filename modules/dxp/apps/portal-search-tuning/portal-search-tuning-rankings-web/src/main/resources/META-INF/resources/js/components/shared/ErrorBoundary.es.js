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

import {openToast} from 'frontend-js-web';
import {PropTypes} from 'prop-types';
import React, {Component} from 'react';

import {sub} from '../../utils/language.es';
import ClayEmptyState, {DISPLAY_STATES} from './ClayEmptyState.es';

class ErrorBoundary extends Component {
	static propTypes = {
		component: PropTypes.string
	};

	state = {
		hasError: false,
		message: this.props.component
			? sub(
					Liferay.Language.get(
						'an-error-has-occurred-and-we-were-unable-to-load-x'
					),
					[this.props.component]
			  )
			: Liferay.Language.get(
					'an-error-has-occurred-and-we-were-unable-to-load-the-results'
			  )
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
				message: this.state.message,
				title: Liferay.Language.get('error'),
				type: 'danger'
			});
		}
	}

	render() {
		return this.state.hasError
			? !this.props.toast && (
					<ClayEmptyState
						description={this.state.message}
						displayState={DISPLAY_STATES.EMPTY}
						title={Liferay.Language.get('unable-to-load-content')}
					/>
			  )
			: this.props.children;
	}
}

export default ErrorBoundary;
