import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class Radio extends React.Component {
	static defaultProps = {
		displayInline: false
	};

	static propTypes = {
		checked: PropTypes.bool,
		displayInline: PropTypes.bool,
		label: PropTypes.node
	};

	render() {
		const {
			checked,
			className,
			displayInline,
			label,
			...otherProps
		} = this.props;

		const classes = getCN('custom-control', 'custom-radio', className, {
			['custom-control-inline']: displayInline
		});

		return (
			<div className={classes}>
				<label>
					<input
						{...omitDefinedProps(otherProps, Radio.propTypes)}
						checked={checked}
						className='custom-control-input'
						type='radio'
					/>

					<span className='custom-control-label'>
						{label && (
							<span className='custom-control-label-text'>
								{label}
							</span>
						)}
					</span>
				</label>
			</div>
		);
	}
}
