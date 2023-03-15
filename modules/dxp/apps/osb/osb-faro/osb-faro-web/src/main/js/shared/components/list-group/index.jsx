import getCN from 'classnames';
import Item from './Item';
import ItemField from './ItemField';
import ItemText from './ItemText';
import ItemTitle from './ItemTitle';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class ListGroup extends React.Component {
	static defaultProps = {
		noBorder: false,
		showQuickActionsOnHover: true
	};

	static propTypes = {
		noBorder: PropTypes.bool,
		showQuickActionsOnHover: PropTypes.bool
	};

	render() {
		const {
			children,
			className,
			noBorder,
			showQuickActionsOnHover,
			...otherProps
		} = this.props;

		const classes = getCN('list-group', 'list-group-root', className, {
			'no-border': noBorder,
			'show-quick-actions-on-hover': showQuickActionsOnHover
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
ListGroup.ItemText = ItemText;
ListGroup.ItemTitle = ItemTitle;
