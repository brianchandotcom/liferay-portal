import AttributeConjunctionInput from './components/attribute-conjunction-input';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import EventPropertiesQuery, {
	EventPropertiesData,
	EventPropertiesVariables
} from '../queries/EventPropertiesQuery';
import Form from 'shared/components/form';
import OccurenceConjunctionInput from './components/OccurenceConjunctionInput';
import React from 'react';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {CustomValue} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {FunctionalOperators, RelationalOperators} from '../utils/constants';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName
} from '../utils/custom-inputs';
import {isBoolean, isNil} from 'lodash';
import {NAME} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';
import {SafeResults} from 'shared/hoc/util';
import {useQuery} from '@apollo/react-hooks';

type Touched = {
	attribute: boolean;
	attributeValue: string;
	dateFilter: boolean;
	occurenceCount: boolean;
};

type Valid = {
	attribute: boolean;
	attributeValue: string;
	dateFilter: boolean;
	occurenceCount: boolean;
};

interface IEventInputProps extends ISegmentEditorCustomInputBase {
	touched: Touched;
	valid: Valid;
}

const EventInput: React.FC<IEventInputProps> = ({
	displayValue,
	onChange,
	operatorRenderer: OperatorDropdown,
	property,
	touched,
	valid,
	value: valueIMap
}) => {
	const {id: eventId, options} = property;

	const getConjunctionDateFilterIMap = value => {
		const conjunctionDateFilterIndex = getIndexFromPropertyName(
			value,
			'day'
		);

		if (conjunctionDateFilterIndex >= 0) {
			return getFilterCriterionIMap(value, conjunctionDateFilterIndex);
		}
	};

	const handleAttributeConjunctionChange = ({
		criterion,
		touched: conjunctionTouched,
		valid: conjunctionValid
	}) => {
		onChange({
			touched: {...touched, ...conjunctionTouched},
			valid: {...valid, ...conjunctionValid},
			value: valueIMap.mergeIn(
				['criterionGroup', 'items', 1],
				fromJS(criterion)
			)
		});
	};

	const handleDateFilterConjunctionChange = criterion => {
		onChange({
			touched: {...touched, dateFilter: criterion && criterion.touched},
			valid: {...valid, dateFilter: isNil(criterion) || criterion.valid},
			value: isNil(criterion)
				? valueIMap.deleteIn(['criterionGroup', 'items', 2])
				: valueIMap.mergeIn(
						['criterionGroup', 'items', 2],
						fromJS(criterion)
				  )
		});
	};

	const handleOccurenceConjunctionChange = ({
		criterion,
		touched: occurenceCountTouched,
		valid: occurenceCountValid
	}: {
		criterion?: Criterion;
		touched?: boolean;
		valid?: boolean;
	}) => {
		let params: {touched?: Touched; valid?: Valid; value?: CustomValue} = {
			touched,
			valid
		};

		if (criterion?.operatorName) {
			params = {
				...params,
				value: valueIMap.mergeIn(
					['operator'],
					criterion.operatorName
				) as CustomValue
			};
		} else if (!isNil(criterion?.value)) {
			params = {
				...params,
				value: valueIMap.mergeIn(
					['value'],
					criterion.value
				) as CustomValue
			};
		}

		if (isBoolean(occurenceCountTouched)) {
			params = {
				...params,
				touched: {...touched, occurenceCount: occurenceCountTouched}
			};
		}

		if (isBoolean(occurenceCountValid)) {
			params = {
				...params,
				valid: {...valid, occurenceCount: occurenceCountValid}
			};
		}

		onChange(params);
	};

	const dateFilterConjunctionCriterion = (
		getConjunctionDateFilterIMap(valueIMap) || Map({propertyName: 'day'})
	).toJS();

	if (
		options.length &&
		options.some(option => option.label === 'hidden' && option.value)
	) {
		return (
			<div className='criteria-statement'>
				<b className='non-existent-property-message'>
					{Liferay.Language.get('custom-event-no-longer-exists')}
				</b>
			</div>
		);
	}

	const result = useQuery<EventPropertiesData, EventPropertiesVariables>(
		EventPropertiesQuery,
		{
			variables: {
				eventId,
				keyword: '',
				page: 0,
				size: 25,
				sort: {
					column: NAME,
					type: OrderByDirections.Ascending
				}
			}
		}
	);

	return (
		<div className='criteria-statement'>
			<SafeResults {...result} page={false} pageDisplay={false}>
				{data => {
					const attributes =
						data?.eventProperties?.eventProperties || [];

					return (
						<>
							<Form.Group autoFit>
								<Form.GroupItem
									className='font-weight-semibold text-secondary'
									label
									shrink
								>
									{Liferay.Language.get('individual')}
								</Form.GroupItem>

								<OperatorDropdown />

								<Form.GroupItem
									className='entity-name'
									label
									shrink
								>
									{Liferay.Language.get('performed-fragment')}
								</Form.GroupItem>

								<Form.GroupItem
									className='display-value'
									label
									shrink
								>
									<b>{displayValue}</b>
								</Form.GroupItem>

								<OccurenceConjunctionInput
									onChange={handleOccurenceConjunctionChange}
									operatorName={
										valueIMap.get(
											'operator'
										) as FunctionalOperators &
											RelationalOperators
									}
									touched={touched.occurenceCount}
									valid={valid.occurenceCount}
									value={valueIMap.get('value')}
								/>

								<DateFilterConjunctionInput
									conjunctionCriterion={
										dateFilterConjunctionCriterion
									}
									onChange={handleDateFilterConjunctionChange}
								/>
							</Form.Group>

							{!!attributes.length && (
								<Form.Group autoFit>
									<Form.GroupItem
										className='conjunction'
										label
										shrink
									>
										{Liferay.Language.get(
											'where-attribute-fragment'
										)}
									</Form.GroupItem>

									<AttributeConjunctionInput
										attributes={attributes}
										conjunctionCriterion={getFilterCriterionIMap(
											valueIMap,
											1
										).toJS()}
										onChange={
											handleAttributeConjunctionChange
										}
										touched={{
											attribute: touched.attribute,
											attributeValue:
												touched.attributeValue
										}}
										valid={{
											attribute: valid.attribute,
											attributeValue: valid.attributeValue
										}}
									/>
								</Form.Group>
							)}
						</>
					);
				}}
			</SafeResults>
		</div>
	);
};

export default EventInput;
