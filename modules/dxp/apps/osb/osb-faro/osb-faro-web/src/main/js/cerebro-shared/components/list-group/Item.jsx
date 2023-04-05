import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

/**
 * Item
 * @class
 */
class Item extends React.Component {
	static defaultProps = {
		flex: false,
		header: false
	};

	static propTypes = {
		accentColor: PropTypes.string,
		flex: PropTypes.bool,
		header: PropTypes.bool
	};

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {accentColor, children, className, flex, header, ...otherProps} =
			this.props;

		const classes = getCN(className, {
			'list-group-header': header,
			'list-group-item': !header,
			'list-group-item-flex': flex
		});

		const style = {
			borderLeft: `4px solid ${accentColor}`
		};

		return (
			<li
				{...omitDefinedProps(otherProps, Item.propTypes)}
				className={classes}
				style={accentColor ? style : undefined}
			>
				{children}
			</li>
		);
	}
}

export default Item;
