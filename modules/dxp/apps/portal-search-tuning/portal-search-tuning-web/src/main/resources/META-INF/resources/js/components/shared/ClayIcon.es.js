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

import getCN from 'classnames';
import React, {Component} from 'react';
import ThemeContext from '../../ThemeContext.es';
import {PropTypes} from 'prop-types';

class ClayIcon extends Component {
	static contextType = ThemeContext;

	static propTypes = {
		className: PropTypes.string,
		iconName: PropTypes.string.isRequired
	};

	render() {
		const {className, iconName} = this.props;

		const classes = getCN('lexicon-icon', `lexicon-icon-${iconName}`, {
			[className]: className
		});

		return (
			<svg aria-hidden='true' className={classes} viewBox='0 0 512 512'>
				<use xlinkHref={`${this.context.spritemap}#${iconName}`} />
			</svg>
		);
	}
}

export default ClayIcon;
