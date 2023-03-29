import FieldPreviewModal from 'shared/components/modals/FieldPreviewModal';
import Promise from 'metal-promise';
import React from 'react';

class FieldPreviewModalKit extends React.Component {
	dataSourceFn() {
		return Promise.resolve({
			emailAddress: [
				'test@liferay.com',
				'test2@liferay.com',
				'test3@liferay.com'
			]
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<FieldPreviewModal
					dataSourceFn={this.dataSourceFn}
					fieldName='emailAddress'
					sourceName='test'
				/>
			</div>
		);
	}
}

export default FieldPreviewModalKit;
