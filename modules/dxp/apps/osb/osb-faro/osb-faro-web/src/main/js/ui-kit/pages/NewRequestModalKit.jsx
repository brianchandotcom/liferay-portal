import NewRequestModal from 'shared/components/modals/NewRequestModal';
import React from 'react';

const handleClose = () => alert('close!');
const handleSubmit = () => alert('submit!');

const NewRequestModalKit = () => (
	<div className='modal-container'>
		<NewRequestModal
			groupId='123123'
			onClose={handleClose}
			onSubmit={handleSubmit}
		/>
	</div>
);

export default NewRequestModalKit;
