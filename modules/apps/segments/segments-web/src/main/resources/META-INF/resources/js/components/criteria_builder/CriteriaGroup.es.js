import ClayIcon from '../shared/ClayIcon.es';
import Conjunction from './Conjunction.es';
import CriteriaRow from './CriteriaRow.es';
import DropZone from './DropZone.es';
import EmptyDropZone from './EmptyDropZone.es';
import getCN from 'classnames';
import React, {Component, Fragment} from 'react';
import {CONJUNCTIONS} from '../../utils/constants.es';
import {DragSource as dragSource} from 'react-dnd';
import {DragTypes} from '../../utils/drag-types.es';
import {
	generateGroupId,
	getChildGroupIds,
	getSupportedOperatorsFromType,
	insertAtIndex,
	replaceAtIndex
} from '../../utils/utils.es';
import {PropTypes} from 'prop-types';

/**
 * Passes the required values to the drop target.
 * This method must be called `beginDrag`.
 * @param {Object} props Component's current props
 * @returns {Object} The props to be passed to the drop target.
 */
function beginDrag({criteria, index, parentGroupId}) {
	const childGroupIds = getChildGroupIds(criteria);

	return {
		childGroupIds,
		criterion: criteria,
		groupId: parentGroupId,
		index
	};
}

/**
 * A function that decorates the passed in component with the drag source HOC.
 * This was separated out since this function needed to be called again for the
 * nested groups.
 * @param {React.Component} component The component to decorate.
 */
const withDragSource = dragSource(
	DragTypes.CRITERIA_GROUP,
	{
		beginDrag
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging()
	})
);

class CriteriaGroup extends Component {
	static propTypes = {
		connectDragPreview: PropTypes.func,
		connectDragSource: PropTypes.func,
		criteria: PropTypes.object,
		dragging: PropTypes.bool,
		editing: PropTypes.bool,
		emptyContributors: PropTypes.bool,
		entityName: PropTypes.string,
		groupId: PropTypes.string,
		index: PropTypes.number,
		modelLabel: PropTypes.string,
		onChange: PropTypes.func,
		onMove: PropTypes.func,
		parentGroupId: PropTypes.string,
		propertyKey: PropTypes.string.isRequired,
		root: PropTypes.bool,
		supportedConjunctions: PropTypes.array,
		supportedOperators: PropTypes.array,
		supportedProperties: PropTypes.array,
		supportedPropertyTypes: PropTypes.object
	};

	static defaultProps = {
		root: false
	};

	constructor(props) {
		super(props);

		this.NestedCriteriaGroupWithDrag = withDragSource(CriteriaGroup);
	}

	_handleConjunctionClick = event => {
		event.preventDefault();

		const {criteria, onChange, supportedConjunctions} = this.props;

		const index = supportedConjunctions.findIndex(
			item => item.name === criteria.conjunctionName
		);

		const conjunctionSelected = index === supportedConjunctions.length - 1 ?
			supportedConjunctions[0].name :
			supportedConjunctions[index + 1].name;

		onChange(
			{
				...criteria,
				conjunctionName: conjunctionSelected
			}
		);
	}

	/**
	 * Adds a new criterion in a group at the specified index. If the criteria
	 * was previously empty and is being added to the root group, a new group
	 * will be added as well.
	 * @param {number} index The position the criterion will be inserted in.
	 * @param {object} criterion The criterion that will be added.
	 * @memberof CriteriaGroup
	 */
	_handleCriterionAdd = (index, criterion) => {
		const {
			criteria,
			onChange,
			root,
			supportedOperators,
			supportedPropertyTypes
		} = this.props;

		const {
			defaultValue,
			operatorName,
			propertyName,
			type,
			value
		} = criterion;

		const criterionValue = value || defaultValue;

		const operators = getSupportedOperatorsFromType(
			supportedOperators,
			supportedPropertyTypes,
			type
		);

		const newCriterion = {
			operatorName: operatorName ?
				operatorName :
				operators[0].name,
			propertyName,
			type,
			value: criterionValue
		};

		if (root && !criteria) {
			onChange(
				{
					conjunctionName: CONJUNCTIONS.AND,
					groupId: generateGroupId(),
					items: [newCriterion]
				}
			);
		}
		else {
			onChange(
				{
					...criteria,
					items: insertAtIndex(
						newCriterion,
						criteria.items,
						index
					)
				}
			);
		}
	}

	_handleCriterionChange = index => newCriterion => {
		const {criteria, onChange} = this.props;

		onChange(
			{
				...criteria,
				items: replaceAtIndex(newCriterion, criteria.items, index)
			}
		);
	}

	_handleCriterionDelete = index => {
		const {criteria, onChange} = this.props;

		onChange(
			{
				...criteria,
				items: criteria.items.filter(
					(fItem, fIndex) => fIndex !== index
				)
			}
		);
	}

	_isCriteriaEmpty = () => {
		const {criteria} = this.props;

		return criteria ? !criteria.items.length : true;
	}

	_renderConjunction = index => {
		const {
			criteria,
			editing,
			groupId,
			onMove,
			propertyKey,
			supportedConjunctions
		} = this.props;

		return (
			<Fragment>
				<DropZone
					dropIndex={index}
					groupId={groupId}
					onCriterionAdd={this._handleCriterionAdd}
					onMove={onMove}
					propertyKey={propertyKey}
				/>

				<Conjunction
					conjunctionName={criteria.conjunctionName}
					editing={editing}
					onClick={this._handleConjunctionClick}
					supportedConjunctions={supportedConjunctions}
				/>

				<DropZone
					before
					dropIndex={index}
					groupId={groupId}
					onCriterionAdd={this._handleCriterionAdd}
					onMove={onMove}
					propertyKey={propertyKey}
				/>
			</Fragment>
		);
	}

	_renderCriterion = (criterion, index) => {
		const {
			editing,
			entityName,
			groupId,
			modelLabel,
			onMove,
			propertyKey,
			root,
			supportedConjunctions,
			supportedOperators,
			supportedProperties,
			supportedPropertyTypes
		} = this.props;

		const classes = getCN(
			'criterion',
			{
				'criterion-group': criterion.items
			}
		);

		return (
			<div className={classes}>
				{criterion.items ? (
					<this.NestedCriteriaGroupWithDrag
						criteria={criterion}
						editing={editing}
						entityName={entityName}
						groupId={criterion.groupId}
						index={index}
						modelLabel={modelLabel}
						onChange={this._handleCriterionChange(index)}
						onMove={onMove}
						parentGroupId={groupId}
						propertyKey={propertyKey}
						supportedConjunctions={supportedConjunctions}
						supportedOperators={supportedOperators}
						supportedProperties={supportedProperties}
						supportedPropertyTypes={supportedPropertyTypes}
					/>
				) : (
					<CriteriaRow
						criterion={criterion}
						editing={editing}
						entityName={entityName}
						groupId={groupId}
						index={index}
						modelLabel={modelLabel}
						onAdd={this._handleCriterionAdd}
						onChange={this._handleCriterionChange(index)}
						onDelete={this._handleCriterionDelete}
						onMove={onMove}
						propertyKey={propertyKey}
						root={root}
						supportedOperators={supportedOperators}
						supportedProperties={supportedProperties}
						supportedPropertyTypes={supportedPropertyTypes}
					/>
				)}

				<DropZone
					dropIndex={index + 1}
					groupId={groupId}
					onCriterionAdd={this._handleCriterionAdd}
					onMove={onMove}
					propertyKey={propertyKey}
				/>
			</div>
		);
	}

	render() {
		const {
			connectDragPreview,
			connectDragSource,
			criteria,
			dragging,
			editing,
			emptyContributors,
			groupId,
			onMove,
			propertyKey,
			root
		} = this.props;

		const classes = getCN(
			{
				'criteria-group-root': criteria
			},
			`criteria-group-item${root ? '-root' : ''}`,
			`color--${propertyKey}`,
			{
				'dnd-drag': dragging
			}
		);

		return connectDragPreview(
			<div
				className={classes}
			>
				{this._isCriteriaEmpty() ?
					<EmptyDropZone
						emptyContributors={emptyContributors}
						onCriterionAdd={this._handleCriterionAdd}
						propertyKey={propertyKey}
					/> :
					<Fragment>
						<DropZone
							before
							dropIndex={0}
							groupId={groupId}
							onCriterionAdd={this._handleCriterionAdd}
							onMove={onMove}
							propertyKey={propertyKey}
						/>

						{editing && !root && connectDragSource(
							<div className="criteria-group-drag-icon drag-icon">
								<ClayIcon iconName="drag" />
							</div>
						)}

						{criteria.items && criteria.items.map(
							(criterion, index) => {
								return (
									<Fragment key={index}>
										{index !== 0 &&
											this._renderConjunction(index)}

										{this._renderCriterion(
											criterion,
											index
										)}
									</Fragment>
								);
							}
						)}
					</Fragment>
				}
			</div>
		);
	}
}

export default withDragSource(CriteriaGroup);