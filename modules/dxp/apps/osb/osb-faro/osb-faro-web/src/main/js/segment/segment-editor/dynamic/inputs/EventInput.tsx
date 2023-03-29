import AttributeConjunctionInput from './components/attribute-conjunction-input';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import EventAttributeDefinitionsQuery, {
	EventAttributeDefinitionsData,
	EventAttributeDefinitionsVariables
} from 'event-analysis/queries/EventAttributeDefinitionsQuery';
import Form from 'shared/components/form';
import OccurenceConjunctionInput from './components/OccurenceConjunctionInput';
import React, {useEffect} from 'react';
import {AttributeTypes} from 'event-analysis/utils/types';
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
	id,
	onChange,
	operatorRenderer: OperatorDropdown,
	property: {entityName, id: eventDefinitionId, type},
	touched,
	valid,
	value: valueIMap
}) => {
	let _completedAnalytics = false;

	useEffect(() => {
		const {attributeValue, dateFilter, occurenceCount} = valid;

		const inputsValid =
			(isNil(attributeValue) || attributeValue) &&
			(isNil(dateFilter) || dateFilter) &&
			(isNil(occurenceCount) || occurenceCount);

		if (!id && inputsValid && !_completedAnalytics) {
			_completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}, [valid]);

	const result = useQuery<
		EventAttributeDefinitionsData,
		EventAttributeDefinitionsVariables
	>(EventAttributeDefinitionsQuery, {
		variables: {
			eventDefinitionId,
			keyword: '',
			page: 0,
			size: 25,
			sort: {
				column: NAME,
				type: OrderByDirections.Ascending
			},
			type: AttributeTypes.Global
		}
	});

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

	return (
		<div className='criteria-statement'>
			<SafeResults {...result} page={false} pageDisplay={false}>
				{data => {
					const attributes =
						data?.eventAttributeDefinitions
							?.eventAttributeDefinitions || [];

					return (
						<>
							<Form.Group autoFit>
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

							<Form.Group autoFit>
								<Form.GroupItem
									className='conjunction'
									label
									shrink
								>
									{Liferay.Language.get('where-fragment')}
								</Form.GroupItem>

								<AttributeConjunctionInput
									attributes={attributes}
									conjunctionCriterion={getFilterCriterionIMap(
										valueIMap,
										1
									).toJS()}
									onChange={handleAttributeConjunctionChange}
									touched={{
										attribute: touched.attribute,
										attributeValue: touched.attributeValue
									}}
									valid={{
										attribute: valid.attribute,
										attributeValue: valid.attributeValue
									}}
								/>
							</Form.Group>
						</>
					);
				}}
			</SafeResults>
		</div>
	);
};

export default EventInput;
