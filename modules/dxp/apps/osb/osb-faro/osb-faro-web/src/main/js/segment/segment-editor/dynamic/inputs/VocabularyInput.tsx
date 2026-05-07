import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React, {useEffect, useState} from 'react';
import SelectCategoryFromModal, {
	CategoryItem
} from './components/SelectCategoryFromModal';
import {
	APPLICATION_ID_ASSET_TYPE_MAP,
	ASSET_TYPE_APPLICATION_ID_MAP,
	ASSET_TYPE_COMPATIBLE_EVENTS_MAP,
	EVENT_ID_EVENT_TYPE_MAP,
	EVENT_TYPE_EVENT_ID_MAP,
	RelationalOperators,
	TimeSpans
} from '../utils/constants';
import {
	createCustomValueMap,
	getFilterCriterionIMap,
	getIndexFromPropertyName
} from '../utils/custom-inputs';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {CustomValue} from 'shared/util/records';
import {Icon, Option, Picker} from '@clayui/core';
import {isValid} from '../utils/utils';

const isValidOccurrenceCount = (count: number | string) =>
	isValid(count) && Number(count) >= 0;

export const EVENT_TYPE_OPTIONS = [
	{label: Liferay.Language.get('all-events'), value: 'all'},
	{label: Liferay.Language.get('view'), value: 'view'},
	{label: Liferay.Language.get('download'), value: 'download'},
	{label: Liferay.Language.get('impression'), value: 'impression'},
	{label: Liferay.Language.get('submit'), value: 'submit'},
	{label: Liferay.Language.get('comment'), value: 'comment'}
];

export const OCCURRENCE_OPTIONS = [
	{
		label: Liferay.Language.get('at-least-fragment'),
		value: RelationalOperators.GE
	},
	{
		label: Liferay.Language.get('at-most-fragment'),
		value: RelationalOperators.LE
	}
];

export const ASSET_TYPE_OPTIONS = [
	{label: Liferay.Language.get('any'), value: 'any'},
	{label: Liferay.Language.get('blogs'), value: 'blogs'},
	{label: Liferay.Language.get('forms'), value: 'forms'},
	{
		label: Liferay.Language.get('documents-and-media'),
		value: 'documents-and-media'
	},
	{label: Liferay.Language.get('web-content'), value: 'web-content'},
	{
		label: Liferay.Language.get('basic-web-content'),
		value: 'basic-web-content'
	},
	{label: Liferay.Language.get('basic-document'), value: 'basic-document'},
	{label: Liferay.Language.get('knowledge-base'), value: 'knowledge-base'}
];

const DEFAULT_CONJUNCTION_CRITERION = {
	operatorName: RelationalOperators.GT as any,
	propertyName: 'day',
	touched: false,
	valid: true,
	value: TimeSpans.Last24Hours
} as Criterion & {touched: boolean; valid: boolean};

function buildValue(
	eventType: React.Key,
	assetType: React.Key,
	occurrenceOperator: React.Key,
	occurrenceCount: number | string,
	conjunctionCriterion: Criterion & {touched: boolean; valid: boolean},
	vocabularyId: string,
	categories: CategoryItem[]
): CustomValue {
	const applicationId =
		assetType !== 'any'
			? ASSET_TYPE_APPLICATION_ID_MAP[assetType as string]
			: undefined;

	const eventId =
		eventType !== 'all' && assetType !== 'any'
			? EVENT_TYPE_EVENT_ID_MAP[eventType as string]?.[assetType as string]
			: undefined;

	const activityKeyValue = applicationId
		? eventId
			? `${applicationId}#${eventId}`
			: applicationId
		: undefined;

	const criterionItems: (Criterion & {touched: boolean; valid: boolean})[] = [
		{
			operatorName: RelationalOperators.EQ as any,
			propertyName: 'vocabularies/id',
			touched: false,
			valid: true,
			value: vocabularyId
		} as Criterion & {touched: boolean; valid: boolean}
	];

	if (activityKeyValue) {
		criterionItems.push({
			operatorName: RelationalOperators.EQ as any,
			propertyName: 'activityKey',
			touched: false,
			valid: true,
			value: activityKeyValue
		} as Criterion & {touched: boolean; valid: boolean});
	}

	if (categories.length > 0) {
		criterionItems.push({
			operatorName: RelationalOperators.In as any,
			propertyName: 'categories/id',
			touched: false,
			valid: true,
			value: categories.map(c => c.id)
		} as Criterion & {touched: boolean; valid: boolean});
	}

	criterionItems.push(conjunctionCriterion);

	return createCustomValueMap([
		{key: 'operator', value: occurrenceOperator},
		{key: 'value', value: occurrenceCount},
		{key: 'criterionGroup', value: criterionItems}
	]);
}

function getAssetTypeFromValue(value: CustomValue | undefined): React.Key {
	if (!value) return 'any';

	const activityKeyIndex = getIndexFromPropertyName(value, 'activityKey');

	if (activityKeyIndex < 0) return 'any';

	const activityKey = value.getIn([
		'criterionGroup',
		'items',
		activityKeyIndex,
		'value'
	]) as string;

	const [applicationId] = activityKey.split('#');

	return APPLICATION_ID_ASSET_TYPE_MAP[applicationId] ?? 'any';
}

function getEventTypeFromValue(value: CustomValue | undefined): React.Key {
	if (!value) return 'all';

	const activityKeyIndex = getIndexFromPropertyName(value, 'activityKey');

	if (activityKeyIndex < 0) return 'all';

	const activityKey = value.getIn([
		'criterionGroup',
		'items',
		activityKeyIndex,
		'value'
	]) as string;

	const [, eventId] = activityKey.split('#');

	if (!eventId) return 'all';

	return EVENT_ID_EVENT_TYPE_MAP[eventId] ?? 'all';
}

function getConjunctionCriterionFromValue(
	value: CustomValue | undefined
): Criterion & {touched: boolean; valid: boolean} {
	if (!value) return DEFAULT_CONJUNCTION_CRITERION;

	const dayIndex = getIndexFromPropertyName(value, 'day');

	if (dayIndex < 0) return DEFAULT_CONJUNCTION_CRITERION;

	return (
		(getFilterCriterionIMap(value, dayIndex)?.toJS() as Criterion & {
			touched: boolean;
			valid: boolean;
		}) ?? DEFAULT_CONJUNCTION_CRITERION
	);
}

export default function VocabularyInput({
	channelId = '',
	displayValue,
	groupId = '',
	onChange,
	operatorRenderer: OperatorDropdown,
	property,
	value
}: ISegmentEditorCustomInputBase) {
	const rawOperator = value?.get('operator');

	const [eventType, setEventType] = useState<React.Key>(
		getEventTypeFromValue(value)
	);
	const [occurrenceOperator, setOccurrenceOperator] = useState<React.Key>(
		rawOperator ?? RelationalOperators.GE
	);
	const [occurrenceCount, setOccurrenceCount] = useState<number | string>(
		value?.get('value') ?? 1
	);
	const [occurrenceTouched, setOccurrenceTouched] = useState(false);
	const [occurrenceValid, setOccurrenceValid] = useState(true);
	const [assetType, setAssetType] = useState<React.Key>(
		getAssetTypeFromValue(value)
	);
	const [conjunctionCriterion, setConjunctionCriterion] = useState<
		Criterion & {touched: boolean; valid: boolean}
	>(getConjunctionCriterionFromValue(value));
	const [categories, setCategories] = useState<CategoryItem[]>([]);

	useEffect(() => {
		if (!value?.get('criterionGroup')) {
			onChange({
				touched: false,
				valid: true,
				value: buildValue(
					eventType,
					assetType,
					occurrenceOperator,
					occurrenceCount,
					conjunctionCriterion,
					property.name,
					categories
				)
			});
		}
	}, []);

	const isOccurrenceValid = (
		operator: React.Key,
		count: number | string
	): boolean => isValidOccurrenceCount(count);

	const handleCategoriesChange = (newCategories: CategoryItem[]) => {
		setCategories(newCategories);

		onChange({
			touched: occurrenceTouched,
			valid: isOccurrenceValid(occurrenceOperator, occurrenceCount),
			value: buildValue(
				eventType,
				assetType,
				occurrenceOperator,
				occurrenceCount,
				conjunctionCriterion,
				property.name,
				newCategories
			)
		});
	};

	const handleDateFilterChange = (criterion: Criterion | null) => {
		const newCriterion = criterion
			? (criterion as Criterion & {touched: boolean; valid: boolean})
			: DEFAULT_CONJUNCTION_CRITERION;

		setConjunctionCriterion(newCriterion);

		onChange({
			touched: occurrenceTouched,
			valid: isOccurrenceValid(occurrenceOperator, occurrenceCount),
			value: buildValue(
				eventType,
				assetType,
				occurrenceOperator,
				occurrenceCount,
				newCriterion,
				property.name,
				categories
			)
		});
	};

	return (
		<div className='criteria-statement'>
			<Form.Group autoFit>
				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('individual')}
				</Form.GroupItem>

				<OperatorDropdown />

				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('triggered')}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className='criterion-input'
						items={EVENT_TYPE_OPTIONS.filter(({value}) =>
							(
								ASSET_TYPE_COMPATIBLE_EVENTS_MAP[
									assetType as string
								] ?? ['all']
							).includes(value)
						)}
						onSelectionChange={(newEventType: React.Key) => {
							setEventType(newEventType);

							onChange({
								touched: occurrenceTouched,
								valid: isOccurrenceValid(
									occurrenceOperator,
									occurrenceCount
								),
								value: buildValue(
									newEventType,
									assetType,
									occurrenceOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									categories
								)
							});
						}}
						searchable
						selectedKey={eventType}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('on-the-vocabulary')}
				</Form.GroupItem>

				<Form.GroupItem className='display-value' label shrink>
					<b>{displayValue}</b>
				</Form.GroupItem>
			</Form.Group>

			<Form.Group autoFit>
				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('on-the-categories')}
				</Form.GroupItem>

				<SelectCategoryFromModal
					channelId={channelId}
					groupId={groupId}
					onCategoriesChange={handleCategoriesChange}
					selectedCategories={categories}
					vocabularyId={property.name}
				/>
			</Form.Group>

			<Form.Group autoFit>
				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('for-fragment')}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className='criterion-input'
						items={ASSET_TYPE_OPTIONS}
						onSelectionChange={(newAssetType: React.Key) => {
							setAssetType(newAssetType);

							const compatibleEvents =
								ASSET_TYPE_COMPATIBLE_EVENTS_MAP[
									newAssetType as string
								] ?? ['all'];

							const newEventType = compatibleEvents.includes(
								eventType as string
							)
								? eventType
								: 'all';

							if (newEventType !== eventType) {
								setEventType(newEventType);
							}

							onChange({
								touched: occurrenceTouched,
								valid: isOccurrenceValid(
									occurrenceOperator,
									occurrenceCount
								),
								value: buildValue(
									newEventType,
									newAssetType,
									occurrenceOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									categories
								)
							});
						}}
						selectedKey={assetType}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem className='entity-name' label shrink>
					{Liferay.Language.get('asset-type-fragment')}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className='operator-input'
						items={OCCURRENCE_OPTIONS}
						onSelectionChange={(newOperator: React.Key) => {
							setOccurrenceOperator(newOperator);

							const valid = isOccurrenceValid(
								newOperator,
								occurrenceCount
							);

							onChange({
								touched: occurrenceTouched,
								valid,
								value: buildValue(
									eventType,
									assetType,
									newOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									categories
								)
							});
						}}
						selectedKey={occurrenceOperator}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem
					className={getCN({
						'has-error': !occurrenceValid && occurrenceTouched
					})}
					shrink
				>
					<Input
						className='number-input'
						min='0'
						onBlur={({
							target: {value: inputVal}
						}: React.FocusEvent<HTMLInputElement>) => {
							const valid = isValidOccurrenceCount(inputVal);

							setOccurrenceTouched(true);
							setOccurrenceValid(valid);

							onChange({
								touched: true,
								valid,
								value: buildValue(
									eventType,
									assetType,
									occurrenceOperator,
									inputVal,
									conjunctionCriterion,
									property.name,
									categories
								)
							});
						}}
						onChange={({
							target: {value: inputVal}
						}: React.ChangeEvent<HTMLInputElement>) => {
							let numberVal: string | number = '';

							if (isValid(inputVal)) {
								numberVal = parseInt(inputVal);
							}

							const valid = isValidOccurrenceCount(numberVal);

							setOccurrenceCount(numberVal);
							setOccurrenceTouched(true);
							setOccurrenceValid(valid);

							onChange({
								touched: true,
								valid,
								value: buildValue(
									eventType,
									assetType,
									occurrenceOperator,
									numberVal,
									conjunctionCriterion,
									property.name,
									categories
								)
							});
						}}
						type='number'
						value={occurrenceCount}
					/>

					{occurrenceTouched && occurrenceCount === '' && (
						<div className='form-feedback-group'>
							<div className='form-feedback-item'>
								<Icon
									className='mr-1'
									symbol='exclamation-full'
								/>
								{Liferay.Language.get('required')}
							</div>
						</div>
					)}
				</Form.GroupItem>

				<Form.GroupItem className='unit' label shrink>
					{Liferay.Language.get('times')}
				</Form.GroupItem>

				<DateFilterConjunctionInput
					conjunctionCriterion={conjunctionCriterion}
					onChange={handleDateFilterChange}
				/>
			</Form.Group>
		</div>
	);
}
