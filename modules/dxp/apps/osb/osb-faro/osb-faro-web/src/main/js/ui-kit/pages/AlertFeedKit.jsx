import autobind from 'autobind-decorator';
import Button from 'shared/components/Button';
import React from 'react';
import Row from '../components/Row';
import {addAlert, updateAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {connect} from 'react-redux';
import {PropTypes} from 'prop-types';

const TIMEOUT = 2000;

@connect(null, {addAlert, updateAlert})
export default class AlertFeedKit extends React.Component {
	static propTypes = {
		addAlert: PropTypes.func.isRequired,
		updateAlert: PropTypes.func.isRequired
	};

	@autobind
	handleInfo() {
		this.props.addAlert({
			alertType: Alert.Types.Default,
			message: 'You are using the offline Mode.'
		});
	}

	@autobind
	handleError() {
		this.props.addAlert({
			alertType: Alert.Types.Error,
			message: 'Upload Failed! Check your internet connection.'
		});
	}

	@autobind
	handlePending() {
		const pendingAlert = this.props.addAlert({
			alertType: Alert.Types.Pending,
			message: 'Updating your profile picture...'
		});

		const completedAlert = () => {
			this.props.updateAlert({
				alertType: Alert.Types.Success,
				id: pendingAlert.payload.id,
				message: 'Your profile picture has been updated',
				timeout: TIMEOUT
			});
		};

		setTimeout(completedAlert, TIMEOUT);
	}

	@autobind
	handleSuccess() {
		this.props.addAlert({
			alertType: Alert.Types.Success,
			message: 'Congratulations! You are now logged in.'
		});
	}

	@autobind
	handleWarning() {
		this.props.addAlert({
			alertType: Alert.Types.Warning,
			message: `This is a warning. This alert will not go away unless you
			 dismiss it manually.`,
			timeout: false
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Button display='primary' onClick={this.handleInfo}>
						{'Create Info'}
					</Button>
				</Row>

				<Row>
					<Button display='primary' onClick={this.handleError}>
						{'Create Error'}
					</Button>
				</Row>

				<Row>
					<Button display='primary' onClick={this.handlePending}>
						{'Create Pending'}
					</Button>
				</Row>

				<Row>
					<Button display='primary' onClick={this.handleSuccess}>
						{'Create Success'}
					</Button>
				</Row>

				<Row>
					<Button display='primary' onClick={this.handleWarning}>
						{'Create Warning'}
					</Button>
				</Row>
			</div>
		);
	}
}
