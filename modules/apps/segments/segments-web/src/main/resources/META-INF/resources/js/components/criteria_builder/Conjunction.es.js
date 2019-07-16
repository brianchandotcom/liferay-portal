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

import ClayButton from '@clayui/button';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import {conjunctionShape} from '../../utils/types.es';

class Conjunction extends React.Component {
	static propTypes = {
		className: PropTypes.string,
		conjunctionName: PropTypes.string.isRequired,
		editing: PropTypes.bool.isRequired,
		onClick: PropTypes.func,
		supportedConjunctions: PropTypes.arrayOf(conjunctionShape)
	};

	_getConjunctionLabel(conjunctionName, conjunctions) {
		const conjunction = conjunctions.find(
			({name}) => name === conjunctionName
		);

		return conjunction ? conjunction.label : undefined;
	}

	render() {
		const {
			className,
			conjunctionName,
			editing,
			onClick,
			supportedConjunctions
		} = this.props;

		const classnames = getCN(
			{
				'btn-sm conjunction-button': editing,
				'conjunction-label': !editing
			},
			className
		);

		return editing ? (
			<ClayButton
				className={classnames}
				displayType="secondary"
				onClick={onClick}
			>
				{this._getConjunctionLabel(
					conjunctionName,
					supportedConjunctions
				)}
			</ClayButton>
		) : (
			<div className={classnames}>
				{this._getConjunctionLabel(
					conjunctionName,
					supportedConjunctions
				)}
			</div>
		);
	}
}

export default Conjunction;
