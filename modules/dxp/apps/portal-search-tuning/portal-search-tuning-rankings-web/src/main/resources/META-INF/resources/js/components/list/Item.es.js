/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React, {PureComponent} from 'react';
import {DragSource as dragSource, DropTarget as dropTarget} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';
import {findDOMNode} from 'react-dom';

import {KEY_CODES} from '../../utils/constants.es';
import DRAG_TYPES from '../../utils/drag-types.es';
import {sub} from '../../utils/language.es';
import {isNil} from '../../utils/util.es';
import ItemDropdown from './ItemDropdown.es';

const DEFAULT_ICON = 'web-content';

const DOCUMENT_CSS_CLASS_COLOR_MAP = {
	'document-code': 'file-icon-color-7',
	'document-compressed': 'file-icon-color-0',
	'document-default': 'file-icon-color-0',
	'document-image': 'file-icon-color-5',
	'document-multimedia': 'file-icon-color-5',
	'document-pdf': 'file-icon-color-3',
	'document-presentation': 'file-icon-color-4',
	'document-table': 'file-icon-color-2',
	'document-text': 'file-icon-color-6'
};

const HOVER_TYPES = {
	BOTTOM: 'bottom',
	TOP: 'top'
};

const ROOT_CLASS = 'list-item-root';

/**
 * Component used for displaying a pin icon when an item is pinned. This should
 * be identical to the pin quick action button so that when hovered over, the
 * icons should align exactly on top of each other. This icon should not be
 * focusable or clickable.
 */
const ResultPinIconDisplay = () => (
	<div className="quick-action-menu result-pin-icon-display">
		<ClayButton
			className="btn-outline-borderless component-action quick-action-item"
			displayType="secondary"
			monospaced
			tabIndex="-1"
			title={Liferay.Language.get('pinned-result')}
		>
			<ClayIcon symbol="pin" />
		</ClayButton>
	</div>
);

/**
 * Passes the required values to the drop target and drag preview.
 * This method must be called `beginDrag`.
 * @param {Object} props Component's current props
 * @returns {Object} The props to be passed to the drop target and drag preview.
 */
function beginDrag({
	author,
	clicks,
	date,
	description,
	hidden,
	id,
	index,
	onBlur,
	pinned,
	selected,
	title,
	type
}) {
	onBlur();

	return {
		author,
		clicks,
		date,
		description,
		hidden,
		id,
		index,
		pinned,
		selected,
		title,
		type
	};
}

/**
 * Prevents dropping from the same position.
 * This method must be called `canDrop`.
 * @param {Object} props Component's current props.
 * @param {DropTargetMonitor} monitor
 * @returns {boolean} True if the target should accept the item.
 */
function canDrop(props, monitor) {
	const {index: dropIndex, pinned} = props;

	const {index: dragIndex} = monitor.getItem();

	return pinned && dragIndex !== dropIndex;
}

/**
 * Passes necessary values for the `endDrag` method.
 *
 * hoveringBelow: For determining if the index should be +1 if below.
 * index: For the position of where the move the dragged item.
 *
 * @param {Object} props The current props of the component being dropped.
 * @param {DropTargetMonitor} monitor
 * @param {DragDropContainer} component The drop target component.
 */
function drop({index}, monitor, component) {
	const decoratedComponent = component.getDecoratedComponentInstance();

	return {
		hoverPosition: decoratedComponent.state.hoverPosition,
		index
	};
}

/**
 * Implements the behavior of what will occur when an item stops being dragged.
 * Moves the dragged item to the dropped index.
 * This method must be called `endDrag`.
 * @param {Object} props The current props of the component being dragged.
 * @param {DropTargetMonitor} monitor
 */
function endDrag(props, monitor) {
	const {onFocus, onMove} = props;

	const {index: dragIndex} = monitor.getItem();

	if (monitor.didDrop()) {
		const {hoverPosition, index: dropIndex} = monitor.getDropResult();

		const destIndex =
			hoverPosition === HOVER_TYPES.TOP ? dropIndex : dropIndex + 1;

		const focusIndex = dragIndex < destIndex ? destIndex - 1 : destIndex;

		if (hoverPosition !== null) {
			onMove(dragIndex, destIndex);
		}

		onFocus(hoverPosition !== null ? focusIndex : dragIndex);
	}
	else {
		onFocus(dragIndex);
	}
}

/**
 * Updates the hover indicator line. Performs logic to hide the hover indicator
 * for hovering over a different item, but moving to the same index. This is
 * done here instead of ideally in `canDrop` so the state doesn't need to be
 * lifted to a parent component. This also means though that everywhere we
 * depend on `canDrop` we have to also have to check `hoverPosition !== null`.
 *
 * @param {Object} props The component's current props.
 * @param {DropTargetMonitor} monitor
 * @param {DragDropContainer} component The component being hovered over.
 */
function hover(props, monitor, component) {
	const {index: dropIndex} = props;

	const {index: dragIndex} = monitor.getItem();

	const hoverAbove = isHoverAbove(monitor, component);

	const destIndex = hoverAbove ? dropIndex - 1 : dropIndex + 1;

	if (dragIndex === destIndex) {
		component.setState({hoverPosition: null});
	}
	else if (hoverAbove) {
		component.setState({hoverPosition: HOVER_TYPES.TOP});
	}
	else {
		component.setState({hoverPosition: HOVER_TYPES.BOTTOM});
	}
}

/**
 * A helper method for drag and drop methods.
 *
 * Checks if the mouse is hovering over an item's top-half by:
 * 1) hoverMiddleY: Get vertical middle.
 * 2) clientOffset: Determine mouse position.
 * 3) hoverClientY: Get pixels to the top.
 *
 * @param {DropTargetMonitor} monitor
 * @param {DragDropContainer} component The component being hovered over.
 */
function isHoverAbove(monitor, component) {
	const hoverBoundingRect = findDOMNode(component).getBoundingClientRect();

	const hoverMiddleY = (hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

	const clientOffset = monitor.getClientOffset();

	const hoverClientY = clientOffset.y - hoverBoundingRect.top;

	return hoverClientY < hoverMiddleY;
}

const DND_PROPS = {
	canDrop: PropTypes.bool,
	connectDragPreview: PropTypes.func,
	connectDragSource: PropTypes.func,
	connectDropTarget: PropTypes.func,
	dragging: PropTypes.bool
};

class Item extends PureComponent {
	static propTypes = {
		...DND_PROPS,
		addedResult: PropTypes.bool,
		author: PropTypes.string,
		clicks: PropTypes.number,
		date: PropTypes.string,
		description: PropTypes.string,
		focus: PropTypes.bool,
		hidden: PropTypes.bool,
		icon: PropTypes.string,
		id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
		index: PropTypes.number,
		initialPinned: PropTypes.number,
		onBlur: PropTypes.func,
		onClickHide: PropTypes.func,
		onClickPin: PropTypes.func,
		onFocus: PropTypes.func,
		onMove: PropTypes.func,
		onRemoveSelect: PropTypes.func,
		onSelect: PropTypes.func,
		pinned: PropTypes.bool,
		reorder: PropTypes.bool,
		searchQuery: PropTypes.string,
		selected: PropTypes.bool,
		title: PropTypes.string,
		type: PropTypes.string,
		url: PropTypes.string
	};

	static defaultProps = {
		author: '',
		connectDragPreview: val => val,
		connectDragSource: val => val,
		connectDropTarget: val => val,
		date: '',
		onBlur: () => {},
		onFocus: () => {},
		onMove: () => {},
		onRemoveSelect: () => {},
		onSelect: () => {},
		title: '-',
		type: ''
	};

	rootRef = React.createRef();

	state = {
		hoverPosition: null
	};

	/**
	 * Use empty image as a drag preview so browsers don't draw it and
	 * we can draw what we want on the custom drag layer instead.
	 *
	 * captureDraggingState: true for IE fallback. This specifies that we'd
	 * rather screenshot the node when it already knows it's being dragged so we
	 * can hide it with CSS.
	 */
	componentDidMount() {
		const {connectDragPreview} = this.props;

		if (connectDragPreview) {
			connectDragPreview(getEmptyImage(), {
				captureDraggingState: true
			});
		}
	}

	/**
	 * Use HTMLElement focus method so that pressing tab will focus starting
	 * from the currently focused item. This is needed when using arrow keys to
	 * change focus between items.
	 * @param {Object} prevProps The previous props before updating.
	 */
	componentDidUpdate(prevProps) {
		const {focus} = this.props;

		if (prevProps.focus !== focus && focus) {
			this.rootRef.current.focus();
		}
	}

	_handleBlur = () => {
		this.props.onBlur();
	};

	_handleFocus = event => {
		if (event.target.classList.contains(ROOT_CLASS)) {
			const {index, onFocus} = this.props;

			onFocus(index);
		}
	};

	_handleHide = () => {
		const {hidden, id, onBlur, onClickHide, onRemoveSelect} = this.props;

		onRemoveSelect([id]);

		onClickHide([id], !hidden);

		if (onBlur) {
			onBlur();
		}
	};

	_handleKeyDown = event => {
		const {focus} = this.props;

		if (focus) {
			if (event.key === KEY_CODES.S) {
				this._handleSelect();
			}
			else if (event.key === KEY_CODES.P) {
				this._handlePin();
			}
			else if (event.key === KEY_CODES.H) {
				this._handleHide();
			}
		}
	};

	_handlePin = () => {
		const {
			addedResult,
			id,
			onClickPin,
			onRemoveSelect,
			pinned
		} = this.props;

		if (addedResult) {
			onRemoveSelect([id]);
		}

		onClickPin([id], !pinned);
	};

	_handleSelect = () => {
		const {id, onSelect} = this.props;

		onSelect(id);
	};

	render() {
		const {
			author,
			canDrop,
			clicks,
			connectDragSource,
			connectDropTarget,
			date,
			description,
			dragging,
			focus,
			hidden,
			icon,
			id,
			onClickHide,
			onClickPin,
			over,
			pinned,
			reorder,
			selected,
			style,
			title,
			type,
			url
		} = this.props;

		const {hoverPosition} = this.state;

		const classSticker = getCN(
			DOCUMENT_CSS_CLASS_COLOR_MAP[icon] ||
				DOCUMENT_CSS_CLASS_COLOR_MAP['document-default'],
			'result-icon'
		);

		const listClasses = getCN(
			ROOT_CLASS,
			'list-group-item',
			'list-group-item-flex',
			{
				'item-drop-indicator-above':
					over && canDrop && hoverPosition === HOVER_TYPES.TOP,
				'item-drop-indicator-below':
					over && canDrop && hoverPosition === HOVER_TYPES.BOTTOM,
				'list-item-dragging': dragging,
				'list-item-has-clicks': !isNil(clicks),
				'result-ranking-item-focus': focus,
				'result-ranking-item-hidden': hidden,
				'result-ranking-item-pinned': pinned,
				'result-ranking-item-reorder': reorder
			}
		);

		return connectDropTarget(
			<li
				className={listClasses}
				data-testid={id}
				onBlur={this._handleBlur}
				onFocus={this._handleFocus}
				onKeyDown={this._handleKeyDown}
				ref={this.rootRef}
				style={style}
				tabIndex={0}
			>
				<div
					className="autofit-col result-drag"
					data-testid="DRAG_ICON"
					style={{visibility: pinned ? 'visible' : 'hidden'}}
				>
					{connectDragSource(
						<span className="result-drag-sticker sticker sticker-secondary">
							<ClayIcon symbol="drag" />
						</span>
					)}
				</div>

				<div className="autofit-col">
					<ClayCheckbox
						aria-label={Liferay.Language.get('select')}
						checked={selected}
						onChange={this._handleSelect}
					/>
				</div>

				<div className="autofit-col">
					<ClaySticker className={classSticker} displayType="light">
						<ClayIcon symbol={icon ? icon : DEFAULT_ICON} />
					</ClaySticker>
				</div>

				<div className="autofit-col autofit-col-expand">
					<section className="autofit-section">
						<div className="list-group-title">
							<span className="text-truncate-inline">
								{url ? <a href={url}>{title}</a> : title}
							</span>
						</div>

						{(author || date) && (
							<p className="list-group-subtext">
								{author && (
									<span className="author">{author}</span>
								)}

								{date && <span className="date">{date}</span>}
							</p>
						)}

						{type && (
							<p className="list-group-subtext">{`[${type}]`}</p>
						)}

						{description && (
							<p className="list-group-text list-item-description">
								{description}
							</p>
						)}
					</section>
				</div>

				<div className="autofit-col">
					{pinned && <ResultPinIconDisplay />}

					<div className="quick-action-menu">
						{onClickHide && (
							<ClayButton
								className="btn-outline-borderless component-action quick-action-item"
								displayType="secondary"
								monospaced
								onClick={this._handleHide}
								title={
									hidden
										? Liferay.Language.get('show-result')
										: Liferay.Language.get('hide-result')
								}
							>
								<ClayIcon symbol={hidden ? 'view' : 'hidden'} />
							</ClayButton>
						)}

						{onClickPin && (
							<ClayButton
								className="btn-outline-borderless component-action quick-action-item"
								displayType="secondary"
								monospaced
								onClick={this._handlePin}
								title={
									pinned
										? Liferay.Language.get('unpin-result')
										: Liferay.Language.get('pin-result')
								}
							>
								{pinned ? (
									<ClayIcon key="UNPIN" symbol="unpin" />
								) : (
									<ClayIcon key="PIN" symbol="pin" />
								)}
							</ClayButton>
						)}
					</div>

					{(onClickPin || onClickHide) && (
						<ItemDropdown
							hidden={hidden}
							onClickHide={this._handleHide}
							onClickPin={this._handlePin}
							pinned={pinned}
						/>
					)}
				</div>

				{!isNil(clicks) && (
					<div className="click-count list-group-text sticker-bottom-right">
						{sub(
							Liferay.Language.get('clicks-x'),
							[<b key="CLICK_COUNT">{clicks}</b>],
							false
						)}
					</div>
				)}
			</li>
		);
	}
}

const ItemWithDrag = dragSource(
	DRAG_TYPES.LIST_ITEM,
	{
		beginDrag,
		endDrag
	},
	(connect, monitor) => ({
		connectDragPreview: connect.dragPreview(),
		connectDragSource: connect.dragSource(),
		dragging: monitor.isDragging()
	})
)(Item);

export default dropTarget(
	DRAG_TYPES.LIST_ITEM,
	{
		canDrop,
		drop,
		hover
	},
	(connect, monitor) => ({
		canDrop: monitor.canDrop(),
		connectDropTarget: connect.dropTarget(),
		over: monitor.isOver()
	})
)(ItemWithDrag);
