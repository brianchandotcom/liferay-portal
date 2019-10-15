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

import Icon from '../../shared/components/Icon.es';
import React from 'react';

export default class AlertMessage extends React.Component {
	render() {
		const {children, className, iconName, type = 'danger'} = this.props;
		let typeText = Liferay.Language.get('warning');

		if (type === 'danger') {
			typeText = Liferay.Language.get('error');
		}

		return (
			<div className="container-fluid-1280">
				<div
					className={`alert alert-dismissible alert-${type} ${className}`}
					role="alert"
				>
					<span className="alert-indicator">
						<Icon iconName={iconName} />
					</span>

					<strong className="lead">{typeText}</strong>

					{children}

					<button
						aria-label="Close"
						className="close"
						data-dismiss="alert"
						type="button"
					>
						<Icon iconName="times" />
					</button>
				</div>
			</div>
		);
	}
}
