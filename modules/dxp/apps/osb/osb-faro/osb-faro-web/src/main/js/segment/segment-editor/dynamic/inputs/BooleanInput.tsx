import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import React from 'react';
import {BOOLEAN_OPTIONS} from '../utils/constants';
import {ClaySelectWithOption} from '@clayui/select';
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
	handleChange(event) {
		this.props.onChange({value: event.target.value});
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
						<ClaySelectWithOption
							className='criterion-input'
							onChange={this.handleChange}
							options={BOOLEAN_OPTIONS}
							value={value}
						/>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
