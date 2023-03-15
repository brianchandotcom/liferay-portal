import Form from 'shared/components/form';
import getCN from 'classnames';
import Promise from 'metal-promise';
import React, {useContext, useEffect} from 'react';
import SelectEntityFromModal from '../components/SelectEntityFromModal';
import {Columns} from 'shared/types';
import {
	EntityType,
	ReferencedObjectsContext
} from '../../context/referencedObjects';
import {getFormattedTitle} from 'shared/components/NoResultsDisplay';
import {Map, OrderedMap} from 'immutable';
import {OrderParams} from 'shared/util/records';

interface ISelectEntityInputProps {
	columns: Columns;
	dataSourceFn?: () => typeof Promise;
	delta?: number;
	entityLabel: string;
	entityType: EntityType;
	graphqlProps?: {[key: string]: any};
	groupId?: string;
	initialOrderIOMap?: OrderedMap<string, OrderParams>;
	onItemsChange: (items: OrderedMap<string, any>) => void;
	onValidChange: ({
		touched,
		valid
	}: {
		touched: boolean;
		valid: boolean;
	}) => void;
	page?: number;
	query?: string;
	touched: boolean;
	valid: boolean;
	value: string;
	[key: string]: any;
}

const SelectEntityInput: React.FC<ISelectEntityInputProps> = ({
	className,
	columns,
	dataSourceFn,
	displayValue,
	entityLabel,
	entityType,
	initialOrderIOMap,
	onItemsChange,
	onValidChange,
	operatorRenderer: OperatorDropdown,
	property,
	touched,
	valid,
	value,
	...otherProps
}) => {
	const {addEntities, addEntity, referencedEntities} = useContext(
		ReferencedObjectsContext
	);

	const reference = referencedEntities.getIn([entityType, value]);

	useEffect(() => {
		if (value && !reference && valid) {
			onValidChange({touched: true, valid: false});
		}
	});

	const handleEntitySelect = (items: OrderedMap<string, any>) => {
		const entity = items.first();

		if (items.size === 1) {
			addEntity({
				entityType,
				payload: Map(entity)
			});

			onItemsChange(items);
		} else {
			addEntities({
				entityType,
				payload: items.map(Map).valueSeq().toArray()
			});

			onItemsChange(items);
		}
	};

	return (
		<div
			className={getCN(
				className,
				'criteria-statement',
				'select-entity-input-root'
			)}
		>
			<Form.Group autoFit>
				<Form.GroupItem className='entity-name' label shrink>
					{property.entityName}
				</Form.GroupItem>

				{property.entityName !== displayValue && (
					<Form.GroupItem className='display-value' label shrink>
						<b>{displayValue}</b>
					</Form.GroupItem>
				)}

				<OperatorDropdown />

				<SelectEntityFromModal
					{...otherProps}
					columns={columns}
					dataSourceFn={dataSourceFn}
					entity={reference && reference.toJS()}
					error={touched && !valid}
					initialOrderIOMap={initialOrderIOMap}
					noResultsProps={{
						spacer: true,
						title: getFormattedTitle(entityLabel)
					}}
					onSubmit={handleEntitySelect}
					renderEntity={entity => {
						if (entity && entity?.name) {
							const {dataSourceName, name} = entity;

							return (
								<div
									className='asset-display-root'
									title={dataSourceName}
								>
									<div className='asset-name text-truncate'>
										{name}
									</div>

									{!!dataSourceName && (
										<div
											data-tooltip
											data-tooltip-align='top'
											title={dataSourceName}
										>
											<div className='asset-url text-secondary text-truncate'>
												{dataSourceName}
											</div>
										</div>
									)}
								</div>
							);
						}
					}}
					title={property.label}
				/>
			</Form.Group>
		</div>
	);
};

export default SelectEntityInput;
