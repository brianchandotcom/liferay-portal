import ClayIcon from 'components/shared/ClayIcon.es';
import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class ClayButton extends Component {
	static propTypes = {
		borderless: PropTypes.bool,
		className: PropTypes.string,
		displayStyle: PropTypes.oneOf(
			[
				'danger',
				'dark',
				'info',
				'light',
				'link',
				'primary',
				'secondary',
				'success',
				'unstyled',
				'warning'
			]
		),
		href: PropTypes.string,
		iconName: PropTypes.string,
		label: PropTypes.string,
		monospaced: PropTypes.bool,
		size: PropTypes.oneOf(['sm']),
		type: PropTypes.string
	};

	static defaultProps = {
		borderless: false,
		displayStyle: 'secondary',
		monospaced: false,
		type: 'button'
	};

	render() {
		const {
			borderless,
			className,
			displayStyle,
			href,
			iconName,
			label,
			monospaced,
			size,
			type,
			...otherProps
		} = this.props;

		const stylePrefix = borderless ? 'btn-outline-' : 'btn-';

		const classes = getCN(
			'btn',
			`${stylePrefix}${displayStyle}`,
			{
				[`btn-${size}`]: size,
				'btn-monospaced': monospaced,
				'btn-outline-borderless': borderless
			},
			className
		);

		return href ? (
			<a className={classes} href={href} {...otherProps}>
				{label}
			</a>
		) : (
			<button className={classes} type={type} {...otherProps}>
				{label}

				{iconName && (
					<ClayIcon
						className={
							label ? 'inline-item inline-item-before' : 'icon'
						}
						iconName={iconName}
					/>
				)}
			</button>
		);
	}
}

export default ClayButton;