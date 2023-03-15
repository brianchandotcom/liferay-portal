import React from 'react';
import Row from '../components/Row';
import ToggleSwitch from 'shared/components/ToggleSwitch';

export default class ToggleSwitchKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<h3>{'No options'}</h3>

				<Row>
					<ToggleSwitch name='name1' />
				</Row>

				<h3>{'Checked'}</h3>

				<Row>
					<ToggleSwitch checked name='name2' />
				</Row>

				<h3>{'With label'}</h3>

				<Row>
					<ToggleSwitch label='Label' name='name3' />

					<ToggleSwitch
						label='Some too long label that may not fit inside the whole page but lets try anyway because this is fun'
						name='name4'
					/>
				</Row>
			</div>
		);
	}
}
