import ModalInfoBar from 'shared/components/ModalInfoBar';
import React from 'react';

class ModalInfoBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<ModalInfoBar>{'Test test Info'}</ModalInfoBar>
			</div>
		);
	}
}

export default ModalInfoBarKit;
