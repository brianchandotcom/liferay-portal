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

import {Link, withRouter} from 'react-router-dom';
import React, {Fragment} from 'react';
import Icon from '../Icon';
import pathToRegexp from 'path-to-regexp';

/**
 * @class
 * @memberof shared/components
 */
class PageItem extends React.Component {
	render() {
		const {
			disabled,
			highlighted,
			location: {search},
			match,
			page,
			type
		} = this.props;
		const classNames = ['page-item'];

		if (disabled) {
			classNames.push('disabled');
		}
		if (highlighted) {
			classNames.push('active');
		}

		const renderLink = () => {
			const pathname = pathToRegexp.compile(match.path)(
				Object.assign({}, match.params, {page})
			);

			if (type) {
				const isNext = type === 'next';

				const iconType = isNext ? 'angle-right' : 'angle-left';
				const displayType = isNext ? 'Next' : 'Previous';

				const renderLink = () => {
					const children = () => (
						<>
							<Icon iconName={iconType} />
							<span className="sr-only">{displayType}</span>
						</>
					);

					if (disabled) {
						return (
							<a className="page-link" href="javascript:;">
								{children()}
							</a>
						);
					}

					return (
						<Link
							className="page-link"
							to={{
								pathname,
								search
							}}
						>
							{children()}
						</Link>
					);
				};

				return renderLink();
			}

			return (
				<Link
					className="page-link"
					to={{
						pathname,
						search
					}}
				>
					{page}
				</Link>
			);
		};

		return <li className={classNames.join(' ')}>{renderLink()}</li>;
	}
}

export default withRouter(PageItem);
