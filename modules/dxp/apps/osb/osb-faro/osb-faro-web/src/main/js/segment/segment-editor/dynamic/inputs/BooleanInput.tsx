import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import React from 'react';
import {BOOLEAN_OPTIONS} from '../utils/constants';
import {Option, Picker} from '@clayui/core';
import {Property} from 'shared/util/records';

interface IBooleanInputProps {
	displayValue: string;
	id?: string;
	onChange: (object) => void;
	operatorRenderer: React.ElementType;
	property: Property;
	value: string;
}
export default class BooleanInput extends React.Component<IBooleanInputProps> {
	componentDidMount() {
		const {
			id,
			property: {entityName, type}
		} = this.props;

		if (!id) {
			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}

	@autobind
	handleChange(value) {
		this.props.onChange({value});
	}

	render() {
		const {
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			value
		} = this.props;

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{displayValue}
					</Form.GroupItem>

					<OperatorDropdown />

					<Form.GroupItem shrink>
						<Picker
							className='criterion-input'
							items={BOOLEAN_OPTIONS}
							onSelectionChange={this.handleChange}
							selectedKey={value}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
