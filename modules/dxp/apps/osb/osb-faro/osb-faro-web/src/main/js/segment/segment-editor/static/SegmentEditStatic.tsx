import * as API from 'shared/api';
import EntityRowActions from './EntityRowActions';
import getCN from 'classnames';
import Promise from 'metal-promise';
import React, {useEffect, useState} from 'react';
import SearchableTableWithAdded from './SearchableTableWithAdded';
import ToolbarActionsRenderer from './ToolbarActionsRenderer';
import {
	ActionTypes,
	SelectionProvider,
	useSelectionContext,
	withSelectionProvider
} from 'shared/context/selection';
import {Changeset} from 'shared/util/records';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {individualsListColumns} from 'shared/util/table-columns';
import {Map, OrderedMap} from 'immutable';
import {RootState} from 'shared/store';
import {sub} from 'shared/util/lang';
import {useRequest, useStatefulPagination} from 'shared/hooks';

const ORDER_BY_OPTIONS = [
	{
		label: Liferay.Language.get('name'),
		value: 'name'
	},
	{
		label: Liferay.Language.get('first-seen'),
		value: 'dateCreated'
	}
];

const connector = connect(
	(store: RootState, {groupId}: {groupId: string}) => ({
		timeZoneId: store.getIn([
			'projects',
			groupId,
			'data',
			'timeZone',
			'timeZoneId'
		])
	}),
	{close, open}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISegmentEditStaticProps extends PropsFromRedux {
	changeset: typeof Changeset;
	channelId: string;
	className: string;
	entityLabel: string;
	groupId: string;
	id: string;
	membershipCount: number;
	onChange: (changeset: typeof Changeset) => void;
}

const SegmentEditStatic: React.FC<ISegmentEditStaticProps> = ({
	changeset,
	channelId,
	className,
	close,
	entityLabel,
	groupId,
	id,
	membershipCount,
	onChange,
	open,
	timeZoneId,
	...otherProps
}) => {
	const {selectedItems, selectionDispatch} = useSelectionContext();

	const {
		delta,
		onDeltaChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query
	} = useStatefulPagination(null, {
		initialOrderIOMap: createOrderIOMap(NAME)
	});

	const [showAdded, setShowAdded] = useState<boolean>(false);

	useEffect(() => {
		if (changeset.added.isEmpty() && showAdded) {
			setShowAdded(false);
		}
	}, [changeset]);

	const getColumns = () => [
		individualsListColumns.name,
		individualsListColumns.getDateCreated(timeZoneId)
	];

	const getIndividuals = ({delta, orderIOMap, page, query}) => {
		const params = {
			channelId,
			delta,
			groupId,
			orderIOMap,
			page,
			query
		};

		return API.individuals.search(
			id ? {notIndividualSegmentId: id, ...params} : params
		);
	};

	const {data, error, loading} = useRequest({
		dataSourceFn: id
			? API.individuals.fetchMembership
			: () => Promise.resolve({items: [], total: 0}),
		variables: {
			delta,
			groupId,
			individualSegmentId: id,
			orderIOMap,
			page,
			query
		}
	});

	const handleAddEntitiesModal = () => {
		if (!id) {
			analytics.track('Static Segment Creation - Clicked Add Members');
		}

		open(modalTypes.SEARCHABLE_TABLE_MODAL, {
			checkDisabled: isCurrentMember,
			columns: getColumns(),
			dataSourceFn: getIndividuals,
			entityLabel,
			groupId,
			initialOrderIOMap: createOrderIOMap(NAME),
			instruction: sub(
				Liferay.Language.get('select-x-to-add-to-your-static-segment'),
				[entityLabel]
			),
			onClose: close,
			onSubmit: itemsIOMap => {
				handleStageEntityAdditions(itemsIOMap);

				if (!id) {
					analytics.track('Static Segment Creation - Added Members');
				}

				close();
			},
			orderByOptions: ORDER_BY_OPTIONS,
			submitMessage: Liferay.Language.get('add'),
			title: Liferay.Language.get('add-members')
		});
	};

	const handleStageEntityAdditions = itemsIOMap => {
		onChange(changeset.update('added', added => added.merge(itemsIOMap)));
	};

	const handleStageEntityRemoval = itemsIOMap => {
		onChange(
			changeset.update('removed', removed => removed.merge(itemsIOMap))
		);
	};

	const handleToggleShowAdded = () => {
		setShowAdded(!showAdded);

		selectionDispatch({
			type: ActionTypes.ClearAll
		});
	};

	const handleUndoChanges = itemsIOMap => {
		onChange(
			new Changeset({
				added: changeset.added.filter(item => !itemsIOMap.has(item.id)),
				removed: changeset.removed.filter(
					item => !itemsIOMap.has(item.id)
				)
			})
		);

		selectionDispatch({type: ActionTypes.ClearAll});
	};

	const isCurrentMember = ({id}) => changeset.added.has(id);

	const renderNav = ({
		selectedItemsIOMap
	}: {
		selectedItemsIOMap: OrderedMap<string, any>;
	}) => (
		<ToolbarActionsRenderer
			buttonDisplay={id ? 'secondary' : 'primary'}
			onClick={handleAddEntitiesModal}
			onSelectedClick={handleStageEntityRemoval}
			onUndoChanges={handleUndoChanges}
			selectedItemsIOMap={selectedItemsIOMap}
			showAdded={showAdded}
		/>
	);

	const renderInlineRowActions = ({data, items, itemsSelected}) => (
		/* eslint-disable react/jsx-handler-names */
		<EntityRowActions
			addIdsISet={changeset.added.keySeq().toSet()}
			data={data}
			itemsIMap={Map(items.map(item => [item.id, item]))}
			itemsSelected={itemsSelected}
			onRowDelete={() => {
				onChange(
					changeset.update('removed', removed =>
						removed.set(data.id, data)
					)
				);
			}}
			onUndoChanges={([id]) => {
				const undoAdd = changeset.added.has(id);

				onChange(
					new Changeset({
						added: changeset.added.delete(id),
						removed: undoAdd
							? changeset.removed
							: changeset.removed.delete(id)
					})
				);

				// undoAdd && this._tableRef.current.reload(); // CHange to refetch
			}}
			removeIdsISet={changeset.removed.keySeq().toSet()}
			showAdded={showAdded}
		/>
		/* eslint-enable react/jsx-handler-names */
	);

	const allChecked =
		!selectedItems.isEmpty() &&
		data?.items.every(item => selectedItems.has(item.id));

	return (
		<div
			className={getCN(
				'segment-edit-static-root d-flex flex-column flex-grow-1',
				className
			)}
		>
			<div className='select-items-header'>
				<h3>
					{!!membershipCount &&
						sub(Liferay.Language.get('manage-x-members'), [
							membershipCount
						])}
				</h3>
			</div>

			<SelectionProvider>
				<SearchableTableWithAdded
					{...otherProps}
					addedItemsIOMap={changeset.added.toOrderedMap()}
					className='d-flex flex-column flex-grow-1'
					columns={getColumns()}
					delta={delta}
					entityLabel={entityLabel}
					error={error}
					items={data?.items}
					loading={loading}
					onDeltaChange={onDeltaChange}
					onOrderIOMapChange={onOrderIOMapChange}
					onPageChange={onPageChange}
					onQueryChange={onQueryChange}
					onSelectEntirePage={(items: any[]) => (
						checked: boolean
					) => {
						selectionDispatch({
							payload: {
								items
							},
							type: checked ? ActionTypes.Add : ActionTypes.Remove
						});
					}}
					onSelectItemsChange={item =>
						selectionDispatch({
							payload: {item},
							type: ActionTypes.Toggle
						})
					}
					onShowStagedToggle={handleToggleShowAdded}
					orderByOptions={ORDER_BY_OPTIONS}
					orderIOMap={orderIOMap}
					page={page}
					query={query}
					renderInlineRowActions={renderInlineRowActions}
					renderNav={renderNav}
					rowIdentifier='id'
					selectedItemsIOMap={selectedItems}
					selectEntirePage={allChecked}
					selectEntirePageIndeterminate={
						!allChecked && !selectedItems.isEmpty()
					}
					showCheckbox
					showStaged={showAdded}
					total={data?.total}
				/>
			</SelectionProvider>
		</div>
	);
};

export default compose(connector, withSelectionProvider)(SegmentEditStatic);
