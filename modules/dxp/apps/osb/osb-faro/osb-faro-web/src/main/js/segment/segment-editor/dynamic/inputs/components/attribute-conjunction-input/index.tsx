import Form from 'shared/components/form';
import OperatorSelect from './OperatorSelect';
import React, {useEffect} from 'react';
import ValueInput from './ValueInput';
import {
	AddEntity,
	EntityType,
	ReferencedEntities,
	withReferencedObjectsConsumer
} from '../../../context/referencedObjects';
import {Attribute} from 'event-analysis/utils/types';
import {Criterion} from '../../../utils/types';
import {
	getDefaultAttributeOperator,
	getDefaultAttributeValue,
	validateAttributeValue
} from './utils';
import {Map} from 'immutable';
import {Option, Picker} from '@clayui/core';

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
	referencedEntities,
	touched,
	valid
}) => {
	useEffect(() => {
		if (!getAttributeId()) {
			const defaultAttribute = attributes[0];

			setAttribute(defaultAttribute);
		}
	}, []);

	const getAttributeFromContext = () => {
		const attributeId = getAttributeId();

		return referencedEntities
			.getIn([EntityType.Attributes, attributeId], Map({}))
			.toJS();
	};

	const getAttributeId = (): string => {
		const [, id] = conjunctionCriterion.propertyName.split('/');

		return id;
	};

	const handleAttributeChange = value => {
		const attribute = attributes.find(({id}) => id === value);

		setAttribute(attribute);
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

	const {dataType} = getAttributeFromContext();
	const {operatorName, value} = conjunctionCriterion;

	return (
		<>
			<Form.GroupItem shrink>
				<Picker
					className='attribute-input'
					items={attributes.map(({displayName, id, name}) => ({
						label: displayName || name,
						value: id
					}))}
					onSelectionChange={handleAttributeChange}
					selectedKey={getAttributeId()}
				>
					{({label, value}) => <Option key={value}>{label}</Option>}
				</Picker>
			</Form.GroupItem>

			<OperatorSelect
				dataType={dataType}
				onChange={onChange}
				operatorName={operatorName}
			/>

			<ValueInput
				dataType={dataType}
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
