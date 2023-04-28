import autobind from 'autobind-decorator';
import ClayIcon from '@clayui/icon';
import Conjunction from './Conjunction';
import CriteriaRow from './CriteriaRow';
import DropZone from './DropZone';
import EmptyDropZone from './EmptyDropZone';
import getCN from 'classnames';
import React, {Fragment} from 'react';
import {Conjunctions, SUPPORTED_CONJUNCTION_OPTIONS} from '../utils/constants';
import {
	ConnectDragPreview,
	ConnectDragSource,
	DragSource as dragSource
} from 'react-dnd';
import {Criterion, CriterionGroup, OnMove} from '../utils/types';
import {DragTypes} from '../utils/drag-types';
import {
	generateGroupId,
	generateRowId,
	getChildGroupIds,
	getSupportedOperatorsFromType,
	isCriterionGroup,
	isValid
} from '../utils/utils';
import {
	insertAtIndex,
	replaceAtIndex,
	replaceWithMultipleAtIndex
} from 'shared/util/array';
import {isArray} from 'lodash';

/**
 * Passes the required values to the drop target.
 * This method must be called `beginDrag`.
 */
const beginDrag = ({
	criteria,
	index,
	parentGroupId
}: {
	criteria: CriterionGroup;
	index: number;
	parentGroupId: string;
}): object => {
	const childGroupIds = getChildGroupIds(criteria);

	return {
		childGroupIds,
		criteriaGroupId: parentGroupId,
		criterion: criteria,
		index
	};
};

/**
 * A function that decorates the passed in component with the drag source HOC.
 * This was separated out since this function needed to be called again for the
 * nested groups.
 * @param {React.Component} component The component to decorate.
 */
const withDragSource = dragSource(
	DragTypes.CriteriaGroup,
	{
		beginDrag
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging()
	})
);

interface ICriteriaGroupProps {
	channelId: string;
	connectDragPreview: ConnectDragPreview;
	connectDragSource: ConnectDragSource;
	criteria: CriterionGroup;
	criteriaGroupId: string;
	dragging?: boolean;
	groupId: string;
	id?: string;
	index?: number;
	onChange: (newCriterionGroup: CriterionGroup) => void;
	onMove: OnMove;
	parentGroupId?: string;
	root?: boolean;
}

class CriteriaGroup extends React.Component<ICriteriaGroupProps> {
	static defaultProps = {
		root: false
	};

	private NestedCriteriaGroupWithDrag;

	constructor(props) {
		super(props);

		this.NestedCriteriaGroupWithDrag = withDragSource(CriteriaGroup);
	}

	@autobind
	handleConjunctionClick(event) {
		event.preventDefault();

		const {criteria, onChange} = this.props;

		const index = SUPPORTED_CONJUNCTION_OPTIONS.findIndex(
			({name}) => name === criteria.conjunctionName
		);

		const conjunctionSelected =
			index === SUPPORTED_CONJUNCTION_OPTIONS.length - 1
				? SUPPORTED_CONJUNCTION_OPTIONS[0].name
				: SUPPORTED_CONJUNCTION_OPTIONS[index + 1].name;

		onChange({
			...criteria,
			conjunctionName: conjunctionSelected
		});
	}

	/**
	 * Adds a new criterion in a group at the specified index. If the criteria
	 * was previously empty and is being added to the root group, a new group
	 * will be added as well. If the criteria is being duplicated, it will
	 * use the previous values as the default values.
	 * @param {number} index The position the criterion will be inserted in.
	 * @param {object} criterion The criterion that will be added.
	 * @memberof CriteriaGroup
	 */
	@autobind
	handleCriterionAdd(index, criterion) {
		const {criteria, onChange, root} = this.props;

		const {
			defaultValue,
			operatorName,
			propertyName,
			touched,
			type,
			valid,
			value
		} = criterion;

		const operators = getSupportedOperatorsFromType(type);

		const newCriterion = {
			operatorName: operatorName || operators[0].name,
			propertyName,
			rowId: generateRowId(),
			touched,
			type,
			valid,
			value: isValid(value) ? value : defaultValue
		};

		if (root && !criteria) {
			onChange({
				conjunctionName: Conjunctions.And,
				criteriaGroupId: generateGroupId(),
				items: [newCriterion]
			} as CriterionGroup);
		} else {
			onChange({
				...criteria,
				items: insertAtIndex(criteria.items, index, newCriterion)
			});
		}
	}

	handleCriterionChange(index) {
		return (newCriterion: Criterion | Criterion[]) => {
			const {
				criteria: {conjunctionName, criteriaGroupId, items},
				onChange
			} = this.props;

			onChange({
				conjunctionName,
				criteriaGroupId,
				items: isArray(newCriterion)
					? replaceWithMultipleAtIndex(
							newCriterion,
							[...items],
							index
					  )
					: replaceAtIndex([...items], index, newCriterion)
			});
		};
	}

	@autobind
	handleCriterionDelete(index) {
		const {criteria, onChange} = this.props;

		onChange({
			...criteria,
			items: criteria.items.filter((fItem, fIndex) => fIndex !== index)
		});
	}

	isCriteriaEmpty() {
		const {criteria} = this.props;

		return criteria ? !criteria.items.length : true;
	}

	renderConjunction(index) {
		const {criteria, criteriaGroupId, id, onMove} = this.props;

		return (
			<>
				<DropZone
					before
					criteriaGroupId={criteriaGroupId}
					dropIndex={index}
					id={id}
					onCriterionAdd={this.handleCriterionAdd}
					onMove={onMove}
				/>

				<Conjunction
					conjunctionName={criteria.conjunctionName}
					onClick={this.handleConjunctionClick}
				/>

				<DropZone
					criteriaGroupId={criteriaGroupId}
					dropIndex={index}
					id={id}
					onCriterionAdd={this.handleCriterionAdd}
					onMove={onMove}
				/>
			</>
		);
	}

	renderCriterion(criterion, index) {
		const {channelId, criteriaGroupId, groupId, id, onMove} = this.props;

		const criterionGroup = isCriterionGroup(criterion);

		const classes = getCN('criterion', {
			'criterion-group': criterionGroup
		});

		const NestedCriteriaGroupWithDrag = this.NestedCriteriaGroupWithDrag;

		return (
			<div className={classes}>
				{criterionGroup ? (
					<NestedCriteriaGroupWithDrag
						channelId={channelId}
						criteria={criterion}
						criteriaGroupId={criterion.criteriaGroupId}
						groupId={groupId}
						id={id}
						index={index}
						onChange={this.handleCriterionChange(index)}
						onMove={onMove}
						parentGroupId={criteriaGroupId}
					/>
				) : (
					<CriteriaRow
						channelId={channelId}
						criteriaGroupId={criteriaGroupId}
						criterion={criterion}
						groupId={groupId}
						id={id}
						index={index}
						onAdd={this.handleCriterionAdd}
						onChange={this.handleCriterionChange(index)}
						onDelete={this.handleCriterionDelete}
						onMove={onMove}
					/>
				)}

				<DropZone
					criteriaGroupId={criteriaGroupId}
					dropIndex={index + 1}
					id={id}
					onCriterionAdd={this.handleCriterionAdd}
					onMove={onMove}
				/>
			</div>
		);
	}

	render() {
		const {
			connectDragPreview,
			connectDragSource,
			criteria,
			criteriaGroupId,
			dragging,
			id,
			onMove,
			root
		} = this.props;

		const classes = getCN(
			{
				'criteria-group-root': criteria
			},
			`criteria-group-item${root ? '-root' : ''}`,
			{
				'dnd-drag': dragging
			}
		);
		const singleRow =
			criteria && criteria.items && criteria.items.length === 1;

		return connectDragPreview(
			<div className={classes}>
				{this.isCriteriaEmpty() ? (
					<EmptyDropZone
						id={id}
						onCriterionAdd={this.handleCriterionAdd}
					/>
				) : (
					<>
						<DropZone
							criteriaGroupId={criteriaGroupId}
							dropIndex={0}
							id={id}
							onCriterionAdd={this.handleCriterionAdd}
							onMove={onMove}
						/>

						{singleRow &&
							!root &&
							connectDragSource(
								<div className='criteria-group-drag-icon drag-icon'>
									<ClayIcon
										className='icon-root'
										symbol='drag'
									/>
								</div>
							)}

						{isCriterionGroup(criteria) &&
							criteria.items.map((criterion, index) => (
								<Fragment
									key={`${criteriaGroupId}-${
										isCriterionGroup(criterion)
											? criterion.criteriaGroupId
											: criterion.rowId
									}`}
								>
									{index !== 0 &&
										this.renderConjunction(index)}

									{this.renderCriterion(criterion, index)}
								</Fragment>
							))}
					</>
				)}
			</div>
		);
	}
}

export default withDragSource(CriteriaGroup);
