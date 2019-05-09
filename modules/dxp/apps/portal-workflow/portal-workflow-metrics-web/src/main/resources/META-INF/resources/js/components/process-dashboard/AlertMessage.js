import Icon from '../../shared/components/Icon';
import React from 'react';

export default class AlertMessage extends React.Component {
	render() {
		const { children, iconName, type = 'danger' } = this.props;
		let typeText = Liferay.Language.get('warning');

		if (type !== 'danger') {
			typeText = Liferay.Language.get('error');
		}

		return (
			<div className="container-fluid-1280" style={{ paddingTop: '24px' }}>
				<div className={`alert alert-dismissible alert-${type}`} role="alert">
					<span className="alert-indicator">
						<Icon iconName={iconName} />
					</span>

					<strong className="lead">{typeText}</strong>

					{children}

					<button
						aria-label="Close"
						className="close"
						data-dismiss="alert"
						type="button"
					>
						<Icon iconName="times" />
					</button>
				</div>
			</div>
		);
	}
}