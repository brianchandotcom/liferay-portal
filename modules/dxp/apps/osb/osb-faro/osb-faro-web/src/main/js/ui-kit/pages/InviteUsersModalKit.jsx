import InviteUsersModal from 'shared/components/modals/InviteUsersModal';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import React from 'react';
import {Provider} from 'react-redux';

const store = mockStore();

const handleClose = () => alert('close!');
const handleSubmit = val => alert(val.join(','));

class InviteUsersModalKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Provider store={store}>
					<InviteUsersModal
						message='this is the message'
						onClose={handleClose}
						onSubmit={handleSubmit}
					/>

					<ModalRenderer />
				</Provider>
			</div>
		);
	}
}

export default InviteUsersModalKit;
