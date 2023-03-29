import ConfirmationModal from 'shared/components/modals/ConfirmationModal';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import Promise from 'metal-promise';
import React from 'react';
import {Provider} from 'react-redux';

const store = mockStore();

const handleClose = () => alert('close!');
const handleSubmitBase = () => alert('submit!');
const handleSubmitPromiseReject = () =>
	new Promise((res, rej) => setTimeout(() => rej(''), 2000)).then(
		handleSubmitBase
	);
const handleSubmitPromiseResolve = () =>
	new Promise(res => setTimeout(() => res(''), 2000)).then(handleSubmitBase);

class ConfirmationModalKit extends React.Component {
	render() {
		return (
			<div className='modal-container'>
				<Provider store={store}>
					<ConfirmationModal
						message='SYNC: This is the message field.'
						onClose={handleClose}
						onSubmit={handleSubmitBase}
					/>

					<ConfirmationModal
						message='ASYNC: Resolved promise should submit and close.'
						onClose={handleClose}
						onSubmit={handleSubmitPromiseResolve}
					/>

					<ConfirmationModal
						message="ASYNC: Rejected promise shouldn't submit or close."
						onClose={handleClose}
						onSubmit={handleSubmitPromiseReject}
					/>

					<ModalRenderer />
				</Provider>
			</div>
		);
	}
}

export default ConfirmationModalKit;
