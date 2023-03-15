import LoadingModal from 'shared/components/modals/LoadingModal';
import React from 'react';
import Row from '../components/Row';

export default class LoadingModalKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<LoadingModal message='MyMessage' />
				</Row>

				<Row>
					<LoadingModal title='MyTitle' />
				</Row>

				<Row>
					<LoadingModal icon='embed' />
				</Row>

				<Row>
					<LoadingModal
						icon='embed'
						message='MyMessage'
						title='MyTitle'
					/>
				</Row>
			</div>
		);
	}
}
