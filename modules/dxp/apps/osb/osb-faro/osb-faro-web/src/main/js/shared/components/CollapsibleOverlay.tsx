import Button from 'shared/components/Button';
import getCN from 'classnames';
import React from 'react';

interface ICollapsibleOverlayProps extends React.HTMLFactory<HTMLElement> {
	onClose: (event: React.MouseEvent<HTMLButtonElement>) => void;
	title: string;
	visible: boolean;
}

const CollapsibleOverlay: React.FC<ICollapsibleOverlayProps> = ({
	children,
	onClose,
	title = '',
	visible = false
}) => (
	<div
		className={getCN('collapsible-overlay-root', {
			hidden: !visible
		})}
		hidden={!visible}
	>
		<div className='content-wrapper'>
			<div className='header'>
				<h3>{title}</h3>

				<Button
					aria-label={Liferay.Language.get('close')}
					display='unstyled'
					icon='times'
					iconAlignment='left'
					onClick={onClose}
				></Button>
			</div>

			{children}
		</div>
	</div>
);

export default CollapsibleOverlay;
