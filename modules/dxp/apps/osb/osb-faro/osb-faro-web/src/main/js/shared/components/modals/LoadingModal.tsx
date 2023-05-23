import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import Modal from 'shared/components/modal';
import React from 'react';

interface ILoadingModalProps extends React.HTMLAttributes<HTMLDivElement> {
	icon?: string;
	message?: string;
	title?: string;
}

const LoadingModal: React.FC<ILoadingModalProps> = ({
	className,
	icon,
	message = Liferay.Language.get('loading'),
	title
}) => (
	<Modal className={getCN('loading-modal-root', className)} size='sm'>
		{title && <h1 className='title'>{title}</h1>}

		<div className='icon-container'>
			{icon ? (
				<ClayIcon className='icon-root icon-size-xl' symbol={icon} />
			) : (
				<ClayLoadingIndicator className='spinner-root' />
			)}
		</div>

		{message && <p className='message'>{message}</p>}
	</Modal>
);

export default LoadingModal;
