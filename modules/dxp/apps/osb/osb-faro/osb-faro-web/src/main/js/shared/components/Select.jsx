import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

class SelectItem extends React.Component {
	render() {
		const {children, className, ...otherProps} = this.props;

		return (
			<option className={className} {...otherProps}>
				{children}
			</option>
		);
	}
}

class Select extends React.Component {
	static defaultProps = {
		showBlankOption: false
	};

	static propTypes = {
		showBlankOption: PropTypes.bool
	};

	render() {
		const {
			children,
			className,
			showBlankOption,
			...otherProps
		} = this.props;

		return (
			<select
				className={getCN('form-control select-root', className)}
				{...omitDefinedProps(otherProps, Select.propTypes)}
			>
				{showBlankOption && <SelectItem />}

				{children}
			</select>
		);
	}
}

Select.Item = SelectItem;
export default Select;
