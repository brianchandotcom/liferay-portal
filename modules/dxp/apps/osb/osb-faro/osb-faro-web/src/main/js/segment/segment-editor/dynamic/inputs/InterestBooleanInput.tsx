import autobind from 'autobind-decorator';
import Form from 'shared/components/form';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
import {getPropertyValue, setPropertyValue} from '../utils/custom-inputs';
import {INTEREST_BOOLEAN_OPTIONS} from '../utils/constants';
import {ISegmentEditorCustomInputBase} from '../utils/types';

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
	handleChange(event) {
		const {onChange, value} = this.props;

		onChange({
			value: setPropertyValue(value, 'value', 1, event.target.value)
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
						<ClaySelectWithOption
							className='criterion-input'
							onChange={this.handleChange}
							options={INTEREST_BOOLEAN_OPTIONS}
							value={getPropertyValue(value, 'value', 1)}
						/>
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
