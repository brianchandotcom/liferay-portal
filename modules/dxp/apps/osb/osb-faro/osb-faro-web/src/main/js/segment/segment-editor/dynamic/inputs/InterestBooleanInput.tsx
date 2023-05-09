import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import React from 'react';
import {getPropertyValue, setPropertyValue} from '../utils/custom-inputs';
import {INTEREST_BOOLEAN_OPTIONS} from '../utils/constants';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {Option, Picker} from '@clayui/core';

export default class InterestBooleanInput extends React.Component<ISegmentEditorCustomInputBase> {
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
	handleChange(newValue) {
		const {onChange, value} = this.props;

		onChange({
			value: setPropertyValue(value, 'value', 1, newValue)
		});
	}

	render() {
		const {
			property: {entityName},
			value
		} = this.props;

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<Picker
							className='criterion-input'
							items={INTEREST_BOOLEAN_OPTIONS}
							onSelectionChange={this.handleChange}
							selectedKey={getPropertyValue(value, 'value', 1)}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>

					<Form.GroupItem className='operator' label shrink>
						{Liferay.Language.get('interested-in-fragment')}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{getPropertyValue(value, 'value', 0)}
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
