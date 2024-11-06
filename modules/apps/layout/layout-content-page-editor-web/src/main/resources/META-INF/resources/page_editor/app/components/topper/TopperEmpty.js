/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {FeatureIndicator} from 'frontend-js-components-web';
import React, {useRef} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {ITEM_ACTIVATION_ORIGINS} from '../../config/constants/itemActivationOrigins';
import {useClipboard} from '../../contexts/ClipboardContext';
import {
	useActiveItemIds,
	useHoverItem,
	useIsActive,
	useIsHovered,
	useSelectItem,
	useSelectMultipleItems,
} from '../../contexts/ControlsContext';
import {
	useMovementTarget,
	useMovementTargetPosition,
} from '../../contexts/KeyboardMovementContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import {useGetWidgets} from '../../contexts/WidgetsContext';
import {useLayoutKeyboardNavigation} from '../../hooks/app_hooks/useLayoutKeyboardNavigation';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectLayoutDataItemLabel from '../../selectors/selectLayoutDataItemLabel';
import pasteItems from '../../thunks/pasteItems';
import {TARGET_POSITIONS} from '../../utils/drag_and_drop/constants/targetPositions';
import {useDropTarget} from '../../utils/drag_and_drop/useDragAndDrop';
import {isMovementValid} from '../../utils/isMovementValid';
import toMovementItem from '../../utils/toMovementItem';
import useDropContainerId from '../../utils/useDropContainerId';
import {TopperLabel} from './TopperLabel';

export default function ({activable = true, children, ...props}) {
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);

	if (!canUpdatePageStructure) {
		return children;
	}

	if (Liferay.FeatureFlags['LPD-18221'] && activable) {
		return (
			<ActivableTopperEmptyWrapper {...props}>
				{children}
			</ActivableTopperEmptyWrapper>
		);
	}

	return <TopperEmpty {...props}>{children}</TopperEmpty>;
}

const ActivableTopperEmptyWrapper = ({children, item, ...props}) => {
	const isHovered = useIsHovered();
	const isActive = useIsActive();

	return (
		<MemoizedActivableTopperEmpty
			{...props}
			isActive={isActive(item.itemId)}
			isHovered={isHovered(item.itemId)}
			item={item}
		>
			{children}
		</MemoizedActivableTopperEmpty>
	);
};

const TopperEmpty = ({children, className, item}) => {
	const containerRef = useRef(null);

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(item);
	const {itemId: movementTargetItemId} = useMovementTarget();
	const movementTargetPosition = useMovementTargetPosition();

	const dropTargetPosition = targetPosition || movementTargetPosition;

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const dropContainerId = useDropContainerId();

	const isValidDrop = isOverTarget || movementTargetItemId === item.itemId;

	return React.Children.map(realChildren, (child) => {
		if (!child) {
			return child;
		}

		return (
			<>
				{React.cloneElement(child, {
					...child.props,
					className: classNames(
						child.props.className,
						className,
						'page-editor__topper',
						{
							'drag-over-bottom':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.BOTTOM,
							'drag-over-middle':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.MIDDLE,
							'drag-over-top':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.TOP,
							'drop-container': dropContainerId === item.itemId,
						}
					),
					ref: (node) => {
						containerRef.current = node;
						targetRef(node);

						// Call the original ref, if any.

						if (typeof child.ref === 'function') {
							child.ref(node);
						}
						else if (child.ref && 'current' in child.ref) {
							child.ref.current = node;
						}
					},
				})}
			</>
		);
	});
};

const ActivableTopperEmpty = ({
	children,
	className,
	isActive,
	isHovered,
	item,
	itemElement,
}) => {
	const containerRef = useRef(null);

	const {isOverTarget, targetPosition, targetRef} = useDropTarget(item);
	const {itemId: movementTargetItemId} = useMovementTarget();
	const movementTargetPosition = useMovementTargetPosition();

	const dropTargetPosition = targetPosition || movementTargetPosition;

	const isFragment = children.type === React.Fragment;
	const realChildren = isFragment ? children.props.children : children;

	const dropContainerId = useDropContainerId();

	const isValidDrop = isOverTarget || movementTargetItemId === item.itemId;

	const hoverItem = useHoverItem();
	const selectItem = useSelectItem();

	const {elementRef, isFocusable} = useLayoutKeyboardNavigation(item);

	return React.Children.map(realChildren, (child) => {
		if (!child) {
			return child;
		}

		return (
			<>
				{React.cloneElement(child, {
					...child.props,
					className: classNames(
						child.props.className,
						className,
						'page-editor__topper',
						{
							'active': isActive,
							'drag-over-bottom':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.BOTTOM,
							'drag-over-middle':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.MIDDLE,
							'drag-over-top':
								isValidDrop &&
								dropTargetPosition === TARGET_POSITIONS.TOP,
							'drop-container': dropContainerId === item.itemId,
							'hovered': isHovered,
						}
					),
					onClick: (event) => {
						event.stopPropagation();

						selectItem(item.itemId, {
							origin: ITEM_ACTIVATION_ORIGINS.layout,
						});
					},
					onMouseLeave: (event) => {
						event.stopPropagation();

						if (isHovered) {
							hoverItem(null, {
								origin: ITEM_ACTIVATION_ORIGINS.layout,
							});
						}
					},
					onMouseOver: (event) => {
						event.stopPropagation();

						hoverItem(item.itemId, {
							origin: ITEM_ACTIVATION_ORIGINS.layout,
						});
					},
					ref: (node) => {
						containerRef.current = node;

						// False positive - react-compiler/react-compiler
						// eslint-disable-next-line react-compiler/react-compiler
						elementRef.current = node;

						targetRef(node);

						// Call the original ref, if any.

						if (typeof child.ref === 'function') {
							child.ref(node);
						}
						else if (child.ref && 'current' in child.ref) {
							child.ref.current = node;
						}
					},
					tabIndex: isFocusable ? 0 : -1,
				})}

				{isActive ||
				(isHovered && Liferay.FeatureFlags['LPD-32075']) ? (
					<TopperEmptyLabel
						isActive={isActive}
						isHovered={isHovered && !isActive}
						item={item}
						itemElement={itemElement}
					/>
				) : null}
			</>
		);
	});
};

const TopperEmptyLabel = ({isActive, isHovered, item, itemElement}) => {
	const clipboard = useClipboard();
	const activeItemIds = useActiveItemIds();

	const selectItems = useSelectMultipleItems();

	const dispatch = useDispatch();

	const layoutData = useSelector((state) => state.layoutData);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const getWidgets = useGetWidgets();

	const name = useSelectorCallback(
		(state) => selectLayoutDataItemLabel(state, item),
		[item]
	);

	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);

	return (
		<TopperLabel
			isHovered={isHovered}
			item={item}
			itemElement={itemElement}
		>
			<ul className="tbar-nav">
				<li className="d-inline-block mx-2 page-editor__topper__item page-editor__topper__title tbar-item tbar-item-expand">
					{name}
				</li>

				{canUpdatePageStructure && isActive ? (
					<li className="page-editor__topper__item tbar-item">
						<ClayDropDown
							alignmentPosition={Align.BottomRight}
							hasLeftSymbols
							menuElementAttrs={{
								containerProps: {
									className: 'cadmin',
								},
							}}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get('options')}
									disabled={activeItemIds.length > 1}
									displayType="unstyled"
									onClick={(event) => event.stopPropagation()}
									size="sm"
									title={Liferay.Language.get('options')}
								>
									<ClayIcon
										className="page-editor__topper__icon"
										symbol="ellipsis-v"
									/>
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item
									disabled={!clipboard?.length}
									onClick={(event) => {
										event.stopPropagation();

										if (
											isMovementValid({
												fragmentEntryLinks,
												getWidgets,
												layoutData,
												sources: clipboard.map((id) =>
													toMovementItem(
														id,
														layoutData,
														fragmentEntryLinks
													)
												),
												targetId: item.itemId,
											})
										) {
											dispatch(
												pasteItems({
													clipboard,
													parentItemId: item.itemId,
													selectItems,
												})
											);
										}
									}}
									symbolLeft="paste"
								>
									{Liferay.Language.get('paste')}

									<span className="ml-2">
										<FeatureIndicator type="beta" />
									</span>
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</li>
				) : null}
			</ul>
		</TopperLabel>
	);
};

const MemoizedActivableTopperEmpty = React.memo(ActivableTopperEmpty);

TopperEmpty.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};
