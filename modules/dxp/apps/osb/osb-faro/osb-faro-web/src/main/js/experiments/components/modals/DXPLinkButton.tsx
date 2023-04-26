import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';
import {ClayLinkContext} from '@clayui/link';

interface IDXPLinkButtonProps
	extends React.AnchorHTMLAttributes<React.ElementType> {
	disabled?: boolean;
}
const DXPLinkButton: React.FC<IDXPLinkButtonProps> = ({disabled, href}) => (
	<ClayLinkContext.Provider
		value={({children, ...otherProps}) => <a {...otherProps}>{children}</a>}
	>
		<ClayLink
			className={getCN('button-root', {
				disabled
			})}
			displayType='secondary'
			href={href}
			small
			target='_blank'
		>
			{Liferay.Language.get('view-variant-in-dxp')}
			<ClayIcon className='icon-root ml-2' symbol='shortcut' />
		</ClayLink>
	</ClayLinkContext.Provider>
);

export default DXPLinkButton;
