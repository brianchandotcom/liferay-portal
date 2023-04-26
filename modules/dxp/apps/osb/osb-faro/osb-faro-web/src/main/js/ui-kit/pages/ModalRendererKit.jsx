import * as modalActions from 'shared/actions/modals';
import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import mockStore from 'test/mock-store';
import ModalRenderer from 'shared/components/ModalRenderer';
import React from 'react';
import Row from '../components/Row';
import {Provider} from 'react-redux';
import {times} from 'lodash';

class ModalRendererKit extends React.Component {
	constructor(props) {
		super(props);

		this._store = mockStore();
	}

	@autobind
	handleOpen() {
		this._store.dispatch(
			modalActions.open(modalActions.modalTypes.TEST, {
				onClose: this.handleClose
			})
		);
	}

	@autobind
	handleOpenThree() {
		times(3, i =>
			this._store.dispatch(
				modalActions.open(modalActions.modalTypes.TEST, {
					onClose: this.handleClose,
					title: `Modal Number #${i + 1}`
				})
			)
		);
	}

	@autobind
	handleClose() {
		this._store.dispatch(modalActions.close());
	}

	render() {
		return (
			<div>
				<Provider store={this._store}>
					<ModalRenderer />

					<Row>
						<ClayButton
							className='button-root'
							displayType='primary'
							onClick={this.handleOpen}
						>
							{'Open Modal'}
						</ClayButton>
					</Row>

					<Row>
						<ClayButton
							className='button-root'
							displayType='primary'
							onClick={this.handleOpenThree}
						>
							{'Open Three Modals'}
						</ClayButton>
					</Row>
				</Provider>
			</div>
		);
	}
}

export default ModalRendererKit;
