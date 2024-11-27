import Modal from 'shared/components/modal';
import React from 'react';

interface IBaseScreenProps extends React.HTMLAttributes<HTMLElement> {
	onClose: () => void;
	title?: string;
}

const BaseScreen: React.FC<IBaseScreenProps> = ({children, onClose, title}) => (
	<>
		<Modal.Header onClose={onClose} title={title} />

		{children}
	</>
);

export default BaseScreen;
