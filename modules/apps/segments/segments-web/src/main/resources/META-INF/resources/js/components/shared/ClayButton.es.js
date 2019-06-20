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

import ClayIcon from './ClayIcon.es';
import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class ClayButton extends Component {
	static propTypes = {
		borderless: PropTypes.bool,
		className: PropTypes.string,
		disabled: PropTypes.bool,
		href: PropTypes.string,
		iconName: PropTypes.string,
		label: PropTypes.string,
		monospaced: PropTypes.bool,
		size: PropTypes.oneOf(['sm']),
		style: PropTypes.oneOf([
			'primary',
			'secondary',
			'info',
			'success',
			'warning',
			'danger',
			'dark',
			'light',
			'unstyled',
			'outline-danger'
		]),
		type: PropTypes.string
	};

	static defaultProps = {
		borderless: false,
		monospaced: false,
		style: 'secondary',
		type: 'button'
	};

	render() {
		const {
			borderless,
			className,
			disabled,
			href,
			iconName,
			label,
			monospaced,
			size,
			style,
			type,
			...otherProps
		} = this.props;

		const stylePrefix = borderless ? 'btn-outline-' : 'btn-';

		const classes = getCN(
			'btn',
			`${stylePrefix}${style}`,
			{
				[`btn-${size}`]: size,
				'btn-monospaced': monospaced,
				'btn-outline-borderless': borderless,
				disabled: href && disabled
			},
			className
		);

		return href ? (
			<a className={classes} href={href} {...otherProps}>
				{label}
			</a>
		) : (
			<button
				className={classes}
				disabled={disabled}
				type={type}
				{...otherProps}
			>
				{iconName && (
					<ClayIcon
						className={
							label ? 'inline-item inline-item-before' : 'icon'
						}
						iconName={iconName}
					/>
				)}

				{label}
			</button>
		);
	}
}

export default ClayButton;
