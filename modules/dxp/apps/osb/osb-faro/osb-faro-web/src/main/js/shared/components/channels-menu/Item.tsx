import Button from '../Button';
import ClayDropDown from '@clayui/drop-down';
import getCN from 'classnames';
import React from 'react';
import TextTruncate from '../TextTruncate';
import {Channel} from './index';
import {noop} from 'lodash';
import {toRoute} from 'shared/util/router';

interface ISitesDropdownItem
	extends React.ComponentProps<typeof ClayDropDown.Item> {
	active?: boolean;
	channel: Channel;
	groupId: string;
	route: string;
	onClick: typeof noop;
}

const ChannelsMenuDropdownItem: React.FC<ISitesDropdownItem> = ({
	active = false,
	channel: {id, name},
	className,
	groupId,
	onClick,
	route
}) => {
	const classes = getCN('sites-dropdown-item', className, {
		active
	});

	return (
		<ClayDropDown.Item className={classes}>
			<Button
				className='link'
				display='link'
				href={toRoute(route, {channelId: id, groupId})}
				onClick={onClick}
			>
				<TextTruncate
					className='link-content-wrapper'
					inline
					title={name}
				/>
			</Button>
		</ClayDropDown.Item>
	);
};
export default ChannelsMenuDropdownItem;
