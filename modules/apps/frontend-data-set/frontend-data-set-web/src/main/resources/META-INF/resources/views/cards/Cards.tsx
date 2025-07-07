/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithInfo} from '@clayui/card';
import classNames from 'classnames';
import React, {forwardRef, useContext, useRef} from 'react';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../../FrontendDataSetContext';
import FDSDndProvider from '../../dnd/FDSDndProvider';
import useFDSDrop from '../../dnd/useFDSDrop';
import filterItemActions from '../../utils/actionItems/filterItemActions';
import formatActionURL from '../../utils/actionItems/formatActionURL';
import handleActionClick from '../../utils/actionItems/handleActionClick';
import {getLocalizedValue} from '../../utils/getLocalizedValue';
import getRandomId from '../../utils/getRandomId';
import isLink from '../../utils/isLink';
import {
	DisplayType,
	ESelectionTrigger,
	ICardLabelSchema,
	ICardSchema,
	IItemsActions,
} from '../../utils/types';
import imagePropsTransformer from '../utils/imagePropsTransformer';

const Card = forwardRef<HTMLDivElement, any>(
	(
		{
			item,
			onItemSelectionChange,
			schema,
		}: {item: any; onItemSelectionChange: Function; schema: ICardSchema},
		ref
	) => {
		const {
			executeAsyncItemAction,
			highlightItems,
			infoPanelOpen,
			itemsActions,
			loadData,
			onActionDropdownItemClick,
			onInfoPanelToggleButtonClick,
			openModal,
			openSidePanel,
			selectable,
			selectedItemsKey,
			selectedItemsValue,
			selectionType,
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
			(filterItemActions({
				actions: actionsRef.current,
				infoPanelOpen,
				itemData: item,
				selectedItemsKey,
				selectedItemsValue,
			}) as any);

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

		const getSelectionTrigger = (event: any): string | boolean => {
			if (
				event.nativeEvent?.target['classList'].contains(
					'card-item-first'
				) ||
				(event.nativeEvent?.target['classList'].contains(
					'lexicon-icon'
				) &&
					!event.nativeEvent?.target['classList'].contains(
						'lexicon-icon-ellipsis-v'
					)) ||
				(event.nativeEvent?.target.nodeName === 'use' &&
					!event.nativeEvent?.target.parentNode['classList'].contains(
						'lexicon-icon-ellipsis-v'
					))
			) {
				return ESelectionTrigger.CONTAINER;
			}
			else if (
				event.nativeEvent?.target['classList'].contains(
					'custom-control-input'
				)
			) {
				return ESelectionTrigger.INPUT;
			}

			return false;
		};

		return (
			<div ref={ref}>
				<ClayCardWithInfo
					actions={formattedActions?.map((action: IItemsActions) => {
						const actionItemProps = {
							disabled: action.disabled,
							label: action.label,
							symbolLeft: action.icon,
						};

						return {
							...actionItemProps,
							href: isLink(action.target, null)
								? formatActionURL(
										action.href,
										item,
										action.target
									)
								: null,
							onClick: (event: Event) => {
								handleActionClick({
									action,
									event,
									executeAsyncItemAction,
									highlightItems,
									infoPanelOpen,
									itemData: item,
									itemId: selectedItemKey,
									loadData,
									onActionDropdownItemClick,
									onInfoPanelToggleButtonClick,
									onItemSelectionChange,
									openModal,
									openSidePanel,
									toggleItemInlineEdit,
								});
							},
						};
					})}
					description={localizedDescription}
					href={(schema.link && item[schema.link]) || null}
					imgProps={imageProps}
					labels={getLabels(item)}
					onClick={
						selectable
							? (event: any) => {
									const target = getSelectionTrigger(event);

									if (target) {
										onItemSelectionChange?.({
											item,
											trigger: target,
										});

										event.preventDefault();
									}
								}
							: undefined
					}
					onSelectChange={() => undefined}
					selectableType={
						selectionType === 'single' ? 'radio' : 'checkbox'
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
	onItemSelectionChange,
	schema,
}: React.ComponentProps<typeof Card>) {
	const cardRef = useRef<HTMLDivElement>(null);

	// ClayCardWithInfo does not take a ref, so we must query the target
	// element to highlight just the part we want

	useFDSDrop({
		item,
		targetDropRef: cardRef,
		targetDropRefQuerySelector: '.card',
	});

	return (
		<div className="col-md-3">
			<Card
				item={item}
				onItemSelectionChange={onItemSelectionChange}
				ref={cardRef}
				schema={schema}
			/>
		</div>
	);
}

const Cards = ({
	items,
	onItemSelectionChange,
	schema,
}: {
	items: Array<any>;
	onItemSelectionChange: Function;
	schema: ICardSchema;
}) => {
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
								onItemSelectionChange={onItemSelectionChange}
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
