/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCardWithInfo} from '@clayui/card';
import classNames from 'classnames';
import {getObjectValueFromPath} from 'frontend-js-web';
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
	IView,
} from '../../utils/types';
import ViewsContext from '../ViewsContext';
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
			onSelect,
			openModal,
			openSidePanel,
			selectable,
			selectedItemsKey,
			selectedItemsValue,
			selectionType,
			toggleItemInlineEdit,
		}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

		const [viewsContext] = useContext(ViewsContext);

		const activeView: IView = viewsContext.activeView;

		const actions =
			(itemsActions?.length && itemsActions) || item.actionDropdownItems;

		const formattedActions =
			actions &&
			(filterItemActions({
				actions,
				infoPanelOpen,
				itemData: item,
				selectable,
				selectedItemsKey,
				selectedItemsValue,
			}) as any);

		const selectedItemKey =
			selectedItemsKey &&
			getObjectValueFromPath({object: item, path: selectedItemsKey});

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
			const target = event.nativeEvent?.target;

			if (target.classList.contains('custom-control-input')) {
				return ESelectionTrigger.INPUT;
			}

			if (target.closest('.dropdown-toggle')) {
				return false;
			}

			return ESelectionTrigger.CONTAINER;
		};

		const props = {
			actions: formattedActions?.map((action: IItemsActions) => ({
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
				symbolLeft: action.icon,
			})),
			description: getLocalizedValue(item, schema.description)?.value,
			href: (schema.link && item[schema.link]) || null,
			imgProps:
				schema.image &&
				imagePropsTransformer(
					getLocalizedValue(item, schema.image)?.value
				),
			labels: getLabels(item),
			onClick: selectable
				? (event: any) => {
						const target = getSelectionTrigger(event);

						if (target) {
							onItemSelectionChange?.({
								item,
								trigger: target,
							});

							onSelect?.({selectedItems: [item]});

							if (event.target.tagName !== 'A') {
								event.preventDefault();
							}
						}
					}
				: undefined,
			onSelectChange: selectable ? () => undefined : undefined,
			selectableType: selectionType === 'single' ? 'radio' : 'checkbox',
			selected:
				selectable &&
				!!selectedItemsValue?.find(
					(element) =>
						selectedItemsKey &&
						element ===
							getObjectValueFromPath({
								object: item,
								path: selectedItemsKey,
							})
				),
			stickerProps: (schema.sticker && item[schema.sticker]) || null,
			symbol: schema.symbol && item[schema.symbol],
			title: getLocalizedValue(item, schema.title)?.value || '',
		};

		return (
			<div ref={ref}>
				<ClayCardWithInfo
					{...{
						...props,
						...(activeView.setItemComponentProps?.({item, props}) ??
							{}),
					}}
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
										? getObjectValueFromPath({
												object: item,
												path: selectedItemsKey,
											})
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
