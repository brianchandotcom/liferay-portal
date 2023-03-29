import BaseDropdown from '../base-dropdown';
import BreakdownOptions from './options';
import EventAttributeDefinitionQuery, {
	UPDATE_EVENT_ATTRIBUTE_DEFINITION
} from 'event-analysis/queries/EventAttributeDefinitionQuery';
import EventAttributeDefinitionsQuery, {
	EventAttributeDefinitionsData,
	EventAttributeDefinitionsVariables
} from 'event-analysis/queries/EventAttributeDefinitionsQuery';
import React, {useState} from 'react';
import {AddBreakdown, EditBreakdown} from '../../context/attributes';
import {Align} from '@clayui/drop-down';
import {
	Attribute,
	AttributeOwnerTypes,
	AttributeTypes,
	Breakdown,
	DataTypes
} from 'event-analysis/utils/types';
import {BREAKDOWN_FNS_MAP} from 'event-analysis/utils/utils';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, ConnectedProps} from 'react-redux';
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import {DISPLAY_NAME} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';
import {SafeResults} from 'shared/hoc/util';
import {useQuery} from '@apollo/react-hooks';

const connector = connect(null, {close, open});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IAttributeBreakdownDropdownProps extends PropsFromRedux {
	alignmentPosition?: typeof Align[keyof typeof Align];
	attribute?: Attribute;
	breakdown?: Breakdown;
	disabledIds: string[];
	eventId: string;
	onAttributeSelect: AddBreakdown | EditBreakdown;
	trigger: React.ReactElement;
	uneditableIds: string[];
}

const AttributeBreakdownDropdown: React.FC<IAttributeBreakdownDropdownProps> = ({
	alignmentPosition = Align.RightTop,
	attribute,
	breakdown,
	close,
	disabledIds,
	eventId,
	onAttributeSelect,
	open,
	trigger,
	uneditableIds
}) => {
	const [
		attributeOwnerType,
		setAttributeOwnerType
	] = useState<AttributeOwnerTypes>(AttributeOwnerTypes.Event);
	const [query, setQuery] = useState('');
	const [selectedAttribute, setSelectedAttribute] = useState<Attribute>(
		breakdown ? attribute : null
	);

	const result = useQuery<
		EventAttributeDefinitionsData,
		EventAttributeDefinitionsVariables
	>(EventAttributeDefinitionsQuery, {
		fetchPolicy: 'network-only',
		variables: {
			eventDefinitionId: eventId,
			keyword: '',
			page: 0,
			size: 200,
			sort: {
				column: DISPLAY_NAME,
				type: OrderByDirections.Ascending
			},
			type: AttributeTypes.All
		}
	});

	const attributeId = attribute ? attribute.id : null;
	const breakdownId = breakdown ? breakdown.id : null;

	const hasOptions = (attribute: Attribute) =>
		[DataTypes.Date, DataTypes.Duration, DataTypes.Number].includes(
			attribute.dataType
		);

	const showOptions = selectedAttribute && hasOptions(selectedAttribute);

	const onClose = (save: boolean) => {
		if (save) {
			result.refetch();
		}

		close();
	};

	return (
		<BaseDropdown
			alignmentPosition={alignmentPosition}
			className='event-analysis-editor-attribute-dropdown-root'
			onActiveChange={active => {
				if (!active) {
					setAttributeOwnerType(AttributeOwnerTypes.Event);
					setQuery('');
					setSelectedAttribute(breakdown ? attribute : null);
				}
			}}
			trigger={trigger}
		>
			{({setActive}) => (
				<TransitionGroup className='transition-carousel-group'>
					{(!selectedAttribute || !showOptions) && (
						<CSSTransition
							classNames='transition-attribute-carousel-right'
							timeout={250}
						>
							<div className='d-flex flex-column'>
								<BaseDropdown.Header
									activeTabId={attributeOwnerType}
									tabs={[
										{
											onClick: () =>
												setAttributeOwnerType(
													AttributeOwnerTypes.Event
												),
											tabId: AttributeOwnerTypes.Event,
											title: Liferay.Language.get('event')
										}
									]}
									title={Liferay.Language.get('attributes')}
								/>

								<SafeResults
									page={false}
									pageDisplay={false}
									{...result}
								>
									{({
										eventAttributeDefinitions: {
											eventAttributeDefinitions
										}
									}: {
										eventAttributeDefinitions: {
											eventAttributeDefinitions: Attribute[];
										};
									}) => {
										const modifieldEventAttributeDefinitions = attribute
											? eventAttributeDefinitions.map(
													eventAttributeDefinition => {
														if (
															attribute.id ===
															eventAttributeDefinition.id
														) {
															return attribute;
														}

														return eventAttributeDefinition;
													}
											  )
											: eventAttributeDefinitions;

										return (
											<BaseDropdown.SearchableList
												activeId={attributeId}
												disabledIds={disabledIds}
												items={
													modifieldEventAttributeDefinitions
												}
												onEditClick={(
													attribute: Attribute
												) => {
													open(
														modalTypes.EDIT_ATTRIBUTE_EVENT_MODAL,
														{
															id: attribute.id,
															mutation: UPDATE_EVENT_ATTRIBUTE_DEFINITION,
															onClose,
															query: EventAttributeDefinitionQuery,
															showTypecast: true
														}
													);

													setActive(false);
												}}
												onItemClick={(
													attribute: Attribute
												) => {
													const {
														dataType,
														description,
														displayName,
														id: newAttributeId
													} = attribute;

													const breakdownFn =
														BREAKDOWN_FNS_MAP[
															dataType
														];

													onAttributeSelect({
														attribute,
														breakdown: breakdownFn({
															attributeId: newAttributeId,
															attributeType: attributeOwnerType,
															description,
															displayName
														}),
														id: breakdownId
													});

													setActive(false);
												}}
												onItemOptionsClick={
													setSelectedAttribute
												}
												onQueryChange={setQuery}
												query={query}
												showOptionsCondition={
													hasOptions
												}
												uneditableIds={uneditableIds}
											/>
										);
									}}
								</SafeResults>
							</div>
						</CSSTransition>
					)}

					{showOptions && (
						<CSSTransition
							classNames='transition-attribute-carousel-left'
							timeout={250}
						>
							<div className='w-100'>
								<BreakdownOptions
									attribute={selectedAttribute}
									attributeOwnerType={attributeOwnerType}
									breakdownId={breakdownId}
									onActiveChange={setActive}
									onAttributeChange={params => {
										setSelectedAttribute(params);
									}}
									onEditClick={
										uneditableIds &&
										uneditableIds.some(
											uneditableAttributeId =>
												uneditableAttributeId ===
												selectedAttribute.id
										)
											? null
											: () => {
													open(
														modalTypes.EDIT_ATTRIBUTE_EVENT_MODAL,
														{
															id:
																selectedAttribute.id,
															mutation: UPDATE_EVENT_ATTRIBUTE_DEFINITION,
															onClose,
															query: EventAttributeDefinitionQuery,
															showTypecast: true
														}
													);

													setActive(false);
											  }
									}
								/>
							</div>
						</CSSTransition>
					)}
				</TransitionGroup>
			)}
		</BaseDropdown>
	);
};

export default connector(AttributeBreakdownDropdown);
