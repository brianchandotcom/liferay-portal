import Button from 'shared/components/Button';
import ClayDropDown from '@clayui/drop-down';
import getCN from 'classnames';
import React from 'react';
import {MenuItem} from './types';

const UserMenuDropdownItem: React.FC<
	React.ComponentProps<typeof ClayDropDown.Item> & MenuItem
> = ({
	active,
	className,
	externalLink,
	icon,
	iconAlignment,
	label,
	onClick,
	url
}) => (
	<ClayDropDown.Item className={getCN(className, {active})}>
		<Button
			block
			display='unstyled'
			externalLink={externalLink}
			href={url}
			icon={icon}
			iconAlignment={iconAlignment}
			onClick={onClick}
		>
			{label}
		</Button>
	</ClayDropDown.Item>
);
export default UserMenuDropdownItem;
