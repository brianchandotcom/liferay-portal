import autobind from 'autobind-decorator';
import RadioGroup from 'shared/components/RadioGroup';
import React from 'react';
import Row from '../components/Row';

class RadioGroupKit extends React.Component {
	state = {
		value: 1
	};

	@autobind
	handleChange(value) {
		this.setState({
			value
		});
	}

	render() {
		return (
			<div>
				<Row>
					<RadioGroup name='foo'>
						<RadioGroup.Option value={1} />
					</RadioGroup>
				</Row>

				<Row>
					<RadioGroup
						checked={this.state.value}
						name='bar'
						onChange={this.handleChange}
					>
						<RadioGroup.Option label='Option 1' value={1} />
						<RadioGroup.Option label='Option 2' value={2} />
						<RadioGroup.Option label='Option 3' value={3} />
					</RadioGroup>
				</Row>

				<Row>
					<RadioGroup
						disabled
						name='baz'
						onChange={this.handleChange}
					>
						<RadioGroup.Option label='disabled' value={1} />
					</RadioGroup>
				</Row>
			</div>
		);
	}
}

export default RadioGroupKit;
