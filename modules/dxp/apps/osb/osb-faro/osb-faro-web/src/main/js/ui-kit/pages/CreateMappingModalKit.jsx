import CreateMappingModal from 'shared/components/modals/CreateMappingModal';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import React from 'react';
import {Provider} from 'react-redux';

const store = mockStore();

class CreateMappingModalKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Provider store={store}>
					<CreateMappingModal {...this.props} />

					<ModalRenderer />
				</Provider>
			</div>
		);
	}
}

export default CreateMappingModalKit;
