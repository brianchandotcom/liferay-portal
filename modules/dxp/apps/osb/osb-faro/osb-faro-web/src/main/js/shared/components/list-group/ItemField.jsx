import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class ItemField extends React.Component {
	static defaultProps = {
		expand: false
	};

	static propTypes = {
		expand: PropTypes.bool
	};

	render() {
		const {children, className, expand, ...otherProps} = this.props;

		const classes = getCN('autofit-col', className, {
			'autofit-col-expand': expand
		});

		return (
			<div
				{...omitDefinedProps(otherProps, ItemField.propTypes)}
				className={classes}
			>
				{children}
			</div>
		);
	}
}
