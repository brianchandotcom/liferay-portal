import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import Form from 'shared/components/form';
import OperatorSelect from './OperatorSelect';
import React, {useEffect, useState} from 'react';
import Sticker from 'shared/components/Sticker';
import ValueInput from './ValueInput';
import {
	AddEntity,
	EntityType,
	ReferencedEntities,
	withReferencedObjectsConsumer
} from '../../../context/referencedObjects';
import {Attribute} from 'event-analysis/utils/types';
import {Criterion} from '../../../utils/types';
import {DATA_TYPE_ICONS_MAP} from 'event-analysis/utils/utils';
import {
	getDefaultAttributeOperator,
	getDefaultAttributeValue,
	validateAttributeValue
} from './utils';
import {Map} from 'immutable';

interface IAttributeFilterConjunctionInputProps {
	addEntity: AddEntity;
	attributes: Attribute[];
	conjunctionCriterion: Criterion;
	onChange: (params: {
		criterion: Criterion;
		touched: {
			attribute: boolean;
			attributeValue: boolean;
		};
		valid: {
			attribute: boolean;
			attributeValue: boolean;
		};
	}) => void;
	referencedEntities: ReferencedEntities;
	touched: {
		attribute: boolean;
		attributeValue: boolean;
	};
	valid: {
		attribute: boolean;
		attributeValue: boolean;
	};
}

const AttributeFilterConjunctionInput: React.FC<IAttributeFilterConjunctionInputProps> = ({
	addEntity,
	attributes,
	conjunctionCriterion,
	onChange,
	touched,
	valid
}) => {
	useEffect(() => {
		if (!getAttributeId()) {
			const defaultAttribute = attributes[0];

			setAttribute(defaultAttribute);
		}
	}, []);

	const [attributesDisplayed, setAttributesDisplayed] = useState<Attribute[]>(
		attributes
	);
	const [searchValue, setSearchValue] = useState<string>('');

	const getAttributeFromContext = (): Attribute => {
		const attributeId = getAttributeId();

		return (
			attributes.find(attribute => attribute?.id === attributeId) ||
			attributes[0]
		);
	};

	const getAttributeId = (): string => {
		const [, id] = conjunctionCriterion.propertyName.split('/');

		return id;
	};

	const handleAttributeChange = value => {
		const attribute = attributes.find(({id}) => id === value);

		setAttribute(attribute);
	};

	const getAttributes = query => {
		if (!query) return attributes;

		return attributes.filter(
			({displayName, name}) =>
				displayName.toLowerCase().includes(query.toLowerCase()) ||
				name.toLowerCase().includes(query.toLowerCase())
		);
	};

	const setAttribute = (attribute: Attribute) => {
		addEntity({
			entityType: EntityType.Attributes,
			payload: Map(attribute)
		});

		const defaultAttributeValue = getDefaultAttributeValue(
			attribute.dataType,
			conjunctionCriterion.operatorName
		);

		const defaultAttributeOperator = getDefaultAttributeOperator(
			attribute.dataType
		);

		onChange({
			criterion: {
				operatorName: defaultAttributeOperator as Criterion['operatorName'],
				propertyName: `attribute/${attribute.id}`,
				value: defaultAttributeValue
			},
			touched: {...touched, attribute: true, attributeValue: false},
			valid: {
				...valid,
				attribute: true,
				attributeValue: validateAttributeValue(
					defaultAttributeValue,
					attribute.dataType,
					defaultAttributeOperator
				)
			}
		});
	};

	const attribute = getAttributeFromContext();
	const {operatorName, value} = conjunctionCriterion;

	return (
		<>
			<Form.GroupItem shrink>
				<ClayDropDown
					closeOnClick
					trigger={
						<ClayButton
							className='form-control form-control-select form-control-select-secondary'
							displayType='secondary'
						>
							{attribute.displayName || attribute.name}
						</ClayButton>
					}
				>
					<ClayDropDown.Search
						className='py-2 px-2'
						onChange={(query: string) => {
							setSearchValue(query);
							setAttributesDisplayed(getAttributes(query));
						}}
						placeholder={Liferay.Language.get('search')}
						value={searchValue}
					/>

					<ClayDropDown.ItemList items={attributesDisplayed}>
						{({dataType, displayName, id, name}: Attribute) => (
							<ClayDropDown.Item
								active={id === attribute.id}
								key={name}
								onClick={() => handleAttributeChange(id)}
								roleItem='option'
							>
								<Sticker className='mr-3' display='secondary'>
									<ClayIcon
										symbol={DATA_TYPE_ICONS_MAP[dataType]}
									/>
								</Sticker>

								{displayName || name}
							</ClayDropDown.Item>
						)}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</Form.GroupItem>

			<OperatorSelect
				dataType={attribute.dataType}
				onChange={onChange}
				operatorName={operatorName}
			/>

			<ValueInput
				dataType={attribute.dataType}
				onChange={onChange}
				operatorName={operatorName}
				touched={touched.attributeValue}
				valid={valid.attributeValue}
				value={value}
			/>
		</>
	);
};

export default withReferencedObjectsConsumer(AttributeFilterConjunctionInput);
