import getCN from 'classnames';
import InfoPopover from 'shared/components/InfoPopover';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class Label extends React.Component {
	static defaultProps = {
		required: false
	};

	static propTypes = {
		className: PropTypes.string,
		popover: PropTypes.object,
		required: PropTypes.bool
	};

	render() {
		const {
			children,
			className,
			popover,
			required,
			...otherProps
		} = this.props;

		return (
			<label
				{...omitDefinedProps(otherProps, Label.propTypes)}
				className={getCN(
					'form-control-label',
					'label-root',
					className,
					{
						required
					}
				)}
			>
				<span className='content-container'>{children}</span>

				{popover && (
					<InfoPopover
						content={popover.content}
						title={popover.title}
					/>
				)}
			</label>
		);
	}
}
