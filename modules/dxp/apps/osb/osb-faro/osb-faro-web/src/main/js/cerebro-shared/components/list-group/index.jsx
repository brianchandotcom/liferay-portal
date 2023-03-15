import getCN from 'classnames';
import Item from './Item';
import ItemField from './ItemField';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

/**
 * List Group
 * @class
 */
class ListGroup extends React.Component {
	static defaultProps = {
		noBorder: false
	};

	static propTypes = {
		noBorder: PropTypes.bool
	};

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {children, className, noBorder, ...otherProps} = this.props;

		const classes = getCN('list-group', 'list-group-root', className, {
			'no-border': noBorder
		});

		return (
			<ul
				{...omitDefinedProps(otherProps, ListGroup.propTypes)}
				className={classes}
			>
				{children}
			</ul>
		);
	}
}

ListGroup.Item = Item;
ListGroup.ItemField = ItemField;
export default ListGroup;
