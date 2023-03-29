import autobind from 'autobind-decorator';
import Button from 'shared/components/Button';
import Popover from 'shared/components/Popover';
import React from 'react';
import ReactDOM from 'react-dom';
import Row from '../components/Row';

export default class PopoverKit extends React.Component {
	state = {
		show: false,
		tooltipTarget: {}
	};

	constructor() {
		super();
	}

	@autobind
	handleMouseOut() {
		this.setState({
			show: false
		});
	}

	@autobind
	handleMouseOver(event) {
		this.setState({
			show: true,
			tooltipTarget: event.target
		});
	}

	render() {
		return (
			<div>
				<Row>
					<h3>{'Popover'}</h3>

					<div>
						<Button
							onBlur={this.handleMouseOver}
							onFocus={this.handleMouseOut}
							onMouseOut={this.handleMouseOut}
							onMouseOver={this.handleMouseOver}
							ref={this._triggerRef}
						>
							{'Mouse over me!'}
						</Button>

						{ReactDOM.createPortal(
							<Popover
								alignElement={this.state.tooltipTarget}
								title='Popover content'
								visible={this.state.show}
							/>,
							document.querySelector('body.dxp')
						)}
					</div>
				</Row>
			</div>
		);
	}
}
