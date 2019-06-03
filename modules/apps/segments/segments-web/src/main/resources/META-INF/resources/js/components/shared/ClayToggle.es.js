import ClayIcon from './ClayIcon.es';
import getCN from 'classnames';
import React, {Component} from 'react';
import {PropTypes} from 'prop-types';

class ClayToggle extends Component {
	static propTypes = {
		checked: PropTypes.bool,
		className: PropTypes.string,
		iconOff: PropTypes.string,
		iconOn: PropTypes.string,
		label: PropTypes.string,
		labelLeft: PropTypes.string,
		labelOff: PropTypes.string,
		labelOn: PropTypes.string,
		labelRight: PropTypes.string,
		name: PropTypes.string,
		onChange: PropTypes.func
	};

	static defaultProps = {
		checked: false,
		label: '',
		labelLeft: '',
		labelOff: '',
		labelOn: '',
		labelRight: ''
	};

	render() {
		const {
			checked,
			className,
			disabled,
			iconOff,
			iconOn,
			label,
			labelLeft,
			labelOff,
			labelOn,
			labelRight,
			name,
			onChange,
			...otherProps
		} = this.props;

		const classes = getCN('toggle-switch', className);

		return (
			<label className={classes} {...otherProps}>
				<input
					checked={checked}
					className='toggle-switch-check'
					disabled={disabled}
					name={name}
					onChange={onChange}
					type='checkbox'
				/>

				{label && <span className='toggle-switch-label'>{label}</span>}

				{labelLeft && (
					<span className='toggle-switch-text toggle-switch-text-left'>
						{labelLeft}
					</span>
				)}

				{labelRight && (
					<span className='toggle-switch-text toggle-switch-text-right'>
						{labelRight}
					</span>
				)}

				<span aria-hidden='true' className='toggle-switch-bar'>
					<span
						className='toggle-switch-handle'
						data-label-off={labelOff}
						data-label-on={labelOn}
					>
						{iconOff && (
							<span className='button-icon button-icon-off toggle-switch-icon'>
								<ClayIcon iconName={iconOff} />
							</span>
						)}

						{iconOn && (
							<span className='button-icon button-icon-on toggle-switch-icon'>
								<ClayIcon iconName={iconOn} />
							</span>
						)}
					</span>
				</span>
			</label>
		);
	}
}

export default ClayToggle;
