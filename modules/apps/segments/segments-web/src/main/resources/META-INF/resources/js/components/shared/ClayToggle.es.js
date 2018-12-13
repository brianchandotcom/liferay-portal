import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import ClayIcon from './ClayIcon.es';
import getCN from 'classnames';

class ClayToggle extends Component {
	static propTypes = {
		checked: PropTypes.bool,
		className: PropTypes.string,
		iconOff: PropTypes.string,
		iconOn: PropTypes.string,
		label: PropTypes.string,
		onChange: PropTypes.func
	};

	static defaultProps = {
		label: ''
	};

	render() {
		const {
			checked,
			className,
			iconOff,
			iconOn,
			label,
			onChange,
			...otherProps
		} = this.props;

		const classes = getCN(
			'toggle-switch',
			className
		);

		return (
			<label className={classes} {...otherProps}>
				<input
					checked={checked}
					className="toggle-switch-check"
					onChange={onChange}
					type="checkbox"
				/>

				<span aria-hidden="true" className="toggle-switch-bar">
					<span
						className="toggle-switch-handle"
						data-label-off={label}
						data-label-on={label}
					>
						{iconOff &&
							<span className="button-icon button-icon-off toggle-switch-icon">
								<ClayIcon iconName={iconOff} />
							</span>
						}

						{iconOn &&
							<span className="button-icon button-icon-on toggle-switch-icon">
								<ClayIcon iconName={iconOn} />
							</span>
						}
					</span>
				</span>
			</label>
		);
	}
}

export default ClayToggle;