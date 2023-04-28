import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';

interface ISidebarItemProps {
	active?: boolean;
	className?: string;
	href?: string;
	icon?: string;
	label?: string;
	onClick?: () => void;
}

const SidebarItem: React.FC<ISidebarItemProps> = ({
	active,
	className,
	href,
	icon,
	label = '',
	onClick,
	...otherProps
}) => (
	<li
		{...otherProps}
		className={getCN('sidebar-item-root', className, {
			active
		})}
	>
		<ClayLink
			button
			className='link'
			displayType='unstyled'
			href={href}
			onClick={onClick}
		>
			<span className='link-content-wrapper'>
				<span className='icon-wrapper'>
					<ClayIcon className='icon-root' symbol={icon} />
				</span>

				<span className='item-label'>{label}</span>
			</span>
		</ClayLink>
	</li>
);

export default SidebarItem;
