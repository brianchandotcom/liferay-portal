import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class ItemText extends React.Component {
	static defaultProps = {
		subtext: false
	};

	static propTypes = {
		subtext: PropTypes.bool
	};

	render() {
		const {children, className, subtext, ...otherProps} = this.props;

		const classes = getCN(className, {
			'list-group-subtext': subtext,
			'list-group-text': !subtext
		});

		return (
			<p
				{...omitDefinedProps(otherProps, ItemText.propTypes)}
				className={classes}
			>
				{children}
			</p>
		);
	}
}
