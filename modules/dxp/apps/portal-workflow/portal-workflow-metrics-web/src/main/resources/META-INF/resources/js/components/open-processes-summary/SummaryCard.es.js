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

import React from 'react';

/**
 * @class
 * @memberof open-processes-summary
 */
export default class SummaryCard extends React.Component {
	render() {
		const {description, total} = this.props;

		return (
			<div
				className="summary-card border col-2"
				style={{marginLeft: '16px'}}
			>
				<span className="regular-text semi-bold text-secondary">
					{description}
				</span>

				<div className="">
					<span
						className="font-weight-normal"
						style={{fontSize: '2.5rem'}}
					>
						{total}
					</span>

					<span className="regular-text text-secondary">
						{Liferay.Language.get('items')}
					</span>
				</div>
			</div>
		);
	}
}
