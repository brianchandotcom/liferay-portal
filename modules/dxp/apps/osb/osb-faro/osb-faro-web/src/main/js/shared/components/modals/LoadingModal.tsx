import getCN from 'classnames';
import Icon from 'shared/components/Icon';
import Modal from 'shared/components/modal';
import React from 'react';
import Spinner from 'shared/components/Spinner';

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
			{icon ? <Icon size='xl' symbol={icon} /> : <Spinner />}
		</div>

		{message && <p className='message'>{message}</p>}
	</Modal>
);

export default LoadingModal;
