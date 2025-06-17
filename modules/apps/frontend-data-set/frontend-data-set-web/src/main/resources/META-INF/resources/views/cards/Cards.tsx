/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithInfo} from '@clayui/card';
import classNames from 'classnames';
import React, {
	MutableRefObject,
	forwardRef,
	useCallback,
	useContext,
	useEffect,
	useRef,
} from 'react';
import {type DropTargetMonitor, useDrop} from 'react-dnd';
import {NativeTypes} from 'react-dnd-html5-backend';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../../FrontendDataSetContext';
import FDSDndProvider from '../../drop/FDSDndProvider';
import filterItemActions from '../../utils/actionItems/filterItemActions';
import formatActionURL from '../../utils/actionItems/formatActionURL';
import handleActionClick from '../../utils/actionItems/handleActionClick';
import {getLocalizedValue} from '../../utils/getLocalizedValue';
import getRandomId from '../../utils/getRandomId';
import isFileDropEnabled from '../../utils/isFileDropEnabled';
import isLink from '../../utils/isLink';
import {
	DisplayType,
	ICardLabelSchema,
	ICardSchema,
	IItemsActions,
} from '../../utils/types';
import imagePropsTransformer from '../utils/imagePropsTransformer';

const Card = forwardRef<HTMLDivElement, any>(
	({item, schema}: {item: any; schema: ICardSchema}, ref) => {
		const {
			executeAsyncItemAction,
			highlightItems,
			itemsActions,
			loadData,
			onActionDropdownItemClick,
			onInfoPanelToggleButtonClick,
			openModal,
			openSidePanel,
			selectItems,
			selectable,
			selectedItemsKey,
			selectedItemsValue,
			toggleItemInlineEdit,
		}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

		const actionsRef = useRef(
			(itemsActions?.length && itemsActions) || item.actionDropdownItems
		);

		const cardSelected =
			selectable &&
			!!selectedItemsValue?.find(
				(element) =>
					selectedItemsKey && element === item[selectedItemsKey]
			);
		const imageProps =
			schema.image &&
			imagePropsTransformer(getLocalizedValue(item, schema.image)?.value);
		const localizedDescription = getLocalizedValue(
			item,
			schema.description
		)?.value;
		const localizedTitle =
			getLocalizedValue(item, schema.title)?.value || '';
		const selectedItemKey = selectedItemsKey && item[selectedItemsKey];
		const formattedActions =
			actionsRef.current &&
			(filterItemActions(actionsRef.current, item) as any);

		const getLabels = (
			item: any
		): Array<{
			displayType: DisplayType;
			value: string;
		}> => {
			if (!schema.labels) {
				return [];
			}

			return schema.labels.flatMap((label: ICardLabelSchema) => {
				const {displayTypeKey, displayTypeValues} = label;
				let {displayType} = label;

				if (!displayType && displayTypeValues && displayTypeKey) {
					const keyValue = getLocalizedValue(
						item,
						displayTypeKey
					)?.value;

					displayType = displayTypeValues[keyValue!];
				}

				const value = getLocalizedValue(item, label.value)?.value;

				if (!value) {
					return [];
				}

				return [
					{
						displayType: displayType || DisplayType.UNSTYLED,
						value,
					},
				];
			});
		};

		return (
			<div ref={ref}>
				<ClayCardWithInfo
					actions={formattedActions?.map((action: IItemsActions) => ({
						...action,
						href: isLink(action.target, null)
							? formatActionURL(action.href, item, action.target)
							: null,
						onClick: (event: Event) => {
							handleActionClick({
								action,
								event,
								executeAsyncItemAction,
								highlightItems,
								itemData: item,
								itemId: selectedItemKey,
								loadData,
								onActionDropdownItemClick,
								onInfoPanelToggleButtonClick,
								openModal,
								openSidePanel,
								toggleItemInlineEdit,
							});
						},
					}))}
					description={localizedDescription}
					href={(schema.link && item[schema.link]) || null}
					imgProps={imageProps}
					labels={getLabels(item)}
					onSelectChange={
						selectable
							? () => {
									selectItems(selectedItemKey);
								}
							: undefined
					}
					selected={cardSelected}
					stickerProps={
						(schema.sticker && item[schema.sticker]) || null
					}
					symbol={schema.symbol && item[schema.symbol]}
					title={localizedTitle}
				/>
			</div>
		);
	}
);

function ClayCardOptionalDropTarget({
	item,
	schema,
}: React.ComponentProps<typeof Card>) {
	const {fileDropSettings, handleFileDrop} = useContext(
		FrontendDataSetContext
	);

	const canDrop = useCallback(
		(item: any): boolean =>
			fileDropSettings?.canReceiveDrop
				? fileDropSettings.canReceiveDrop({item})
				: true,
		[fileDropSettings]
	);

	const cardRef = useRef<HTMLDivElement>(null);

	// ClayCardWithInfo does not take a ref, so we must query the target
	// element to highlight just the part we want

	const cardElementRef: MutableRefObject<HTMLElement | null | undefined> =
		useRef<HTMLElement>(null);

	const [{isOverCurrent}, dropRef] = useDrop({
		accept: isFileDropEnabled(fileDropSettings) ? [NativeTypes.FILE] : [],
		canDrop() {
			return isFileDropEnabled(fileDropSettings) && canDrop(item);
		},
		collect: (monitor: DropTargetMonitor) => {
			return {
				isOverCurrent:
					isFileDropEnabled(fileDropSettings) &&
					canDrop(item) &&
					monitor.isOver({shallow: true}),
			};
		},
		drop(fileItem: any, monitor) {
			if (monitor.isOver({shallow: true})) {
				cardElementRef?.current?.classList.remove('card-drop-target');

				handleFileDrop(fileItem, item);
			}
		},
	});

	useEffect(() => {
		if (!isFileDropEnabled(fileDropSettings) || !canDrop(item)) {
			return;
		}

		dropRef(cardRef);

		cardElementRef.current = cardRef?.current?.querySelector('.card');
	}, [canDrop, cardRef, dropRef, fileDropSettings, item]);

	useEffect(() => {
		if (!isFileDropEnabled(fileDropSettings)) {
			return;
		}

		if (isOverCurrent) {
			cardElementRef?.current?.classList.add('card-drop-target');
		}
		else {
			cardElementRef?.current?.classList.remove('card-drop-target');
		}
	}, [isOverCurrent, fileDropSettings]);

	return (
		<div className="col-md-3">
			<Card item={item} ref={cardRef} schema={schema} />
		</div>
	);
}

const Cards = ({items, schema}: {items: Array<any>; schema: ICardSchema}) => {
	const {selectedItemsKey, style}: IFrontendDataSetContext = useContext(
		FrontendDataSetContext
	);

	if (!items?.length) {
		return null;
	}

	return (
		<div
			className={classNames(
				'cards-container mb-n4',
				style === 'default' && 'px-3 pt-4'
			)}
		>
			<FDSDndProvider>
				<div className="row">
					{items.map((item) => {
						return (
							<ClayCardOptionalDropTarget
								item={item}
								key={
									selectedItemsKey
										? item[selectedItemsKey]
										: getRandomId()
								}
								schema={schema}
							/>
						);
					})}
				</div>
			</FDSDndProvider>
		</div>
	);
};

export {Card};
export default Cards;
