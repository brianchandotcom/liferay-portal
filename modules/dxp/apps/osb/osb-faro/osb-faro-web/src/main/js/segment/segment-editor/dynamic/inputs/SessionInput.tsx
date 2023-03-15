import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import CustomNumberInput from './CustomNumberInput';
import CustomStringInput from './CustomStringInput';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import Form from 'shared/components/form';
import React from 'react';
import {fromJS} from 'immutable';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getPropertyValue
} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {isNull} from 'lodash';
import {PropertyTypes} from '../utils/constants';

interface ISessionInputProps extends ISegmentEditorCustomInputBase {
	touched: {
		customInput: boolean;
		dateFilter: boolean;
	};
	valid: {
		customInput: boolean;
		dateFilter: boolean;
	};
}

export default class SessionInput extends React.Component<ISessionInputProps> {
	@autobind
	fieldValuesDataSourceFn() {
		const {
			channelId,
			groupId,
			property: {name},
			value: valueIMap
		} = this.props;

		return API.session
			.fetchFieldValues({
				channelId,
				fieldName: name,
				groupId,
				query: getPropertyValue(valueIMap, 'value', 0)
			})
			.then(({items}) => items);
	}

	getConjunctionDateFilterIMap() {
		const {value} = this.props;

		const conjunctionDateFilterIndex = getIndexFromPropertyName(
			value,
			'completeDate'
		);

		if (conjunctionDateFilterIndex >= 0) {
			return getFilterCriterionIMap(
				value,
				conjunctionDateFilterIndex
			).toJS();
		}

		return {propertyName: 'completeDate'};
	}

	@autobind
	handleConjunctionChange(criterion) {
		const {onChange, touched, valid, value} = this.props;

		onChange({
			touched: {...touched, dateFilter: criterion && criterion.touched},
			valid: {...valid, dateFilter: isNull(criterion) || criterion.valid},
			value: isNull(criterion)
				? value.deleteIn(['criterionGroup', 'items', 1])
				: value.setIn(['criterionGroup', 'items', 1], fromJS(criterion))
		});
	}

	@autobind
	handleCustomInputChange(criterion) {
		const {onChange, touched, valid} = this.props;

		onChange({
			touched: {...touched, customInput: true},
			valid: {...valid, customInput: criterion.valid},
			value: criterion.value
		});
	}

	renderCustomInput() {
		const {property, touched, valid} = this.props;

		if (property.type === PropertyTypes.SessionNumber) {
			return (
				<CustomNumberInput
					{...this.props}
					onChange={this.handleCustomInputChange}
					touched={touched.customInput}
					valid={valid.customInput}
				/>
			);
		}

		return (
			<CustomStringInput
				{...this.props}
				fieldValuesDataSourceFn={this.fieldValuesDataSourceFn}
				onChange={this.handleCustomInputChange}
				touched={touched.customInput}
				valid={valid.customInput}
			/>
		);
	}

	render() {
		const conjunctionCriterion = this.getConjunctionDateFilterIMap();

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>{this.renderCustomInput()}</Form.Group>

				<Form.Group autoFit>
					<DateFilterConjunctionInput
						conjunctionCriterion={conjunctionCriterion}
						onChange={this.handleConjunctionChange}
					/>
				</Form.Group>
			</div>
		);
	}
}
