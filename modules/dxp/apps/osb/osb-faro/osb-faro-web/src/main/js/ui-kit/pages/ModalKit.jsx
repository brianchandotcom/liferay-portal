import React from 'react';
import TestModal from 'shared/components/modals/TestModal';

class ModalKit extends React.Component {
	render() {
		return (
			<div className='modal-container'>
				<TestModal size='sm' title='Small Modal' />

				<TestModal title='Default Modal' />

				<TestModal size='lg' title='Large Modal' />
			</div>
		);
	}
}

export default ModalKit;
