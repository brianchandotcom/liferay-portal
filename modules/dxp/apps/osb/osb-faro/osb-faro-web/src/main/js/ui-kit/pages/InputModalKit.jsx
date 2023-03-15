import InputModal from 'shared/components/modals/InputModal';
import React from 'react';

class InputModalKit extends React.Component {
	handleSubmit(val) {
		alert(val);
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<InputModal
					onSubmit={this.handleSubmit}
					placeholder='Put some text in here'
					title='Input Modal Preview'
				/>
			</div>
		);
	}
}

export default InputModalKit;
