/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {LinkOrButton} from '@clayui/shared';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {ACTION_ITEM_TARGETS} from '../../utils/actionItems/constants';
import {triggerAction} from '../../utils/actionItems/triggerAction';
import {ICreationActionItem} from '../../utils/types';

const DropDown = ({
	inEmptyState,
	primaryItems,
}: {
	inEmptyState: boolean;
	primaryItems: Array<ICreationActionItem>;
}) => {
	const frontendDataSetContext = useContext(FrontendDataSetContext);

	const {loadData} = frontendDataSetContext;

	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					className={!inEmptyState ? 'nav-btn' : undefined}
					data-testid="fdsCreationActionButton"
					displayType={inEmptyState ? 'secondary' : undefined}
				>
					{Liferay.Language.get('new')}

					<span className="d-inline-flex inline-item-after">
						<ClayIcon symbol="caret-bottom" />
					</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{primaryItems.map((item, i) => (
					<ClayDropDown.Item
						key={i}
						onClick={(event) => {
							event.preventDefault();

							setActive(false);

							item.onClick?.({
								loadData,
							});

							if (item.href || item.target) {
								triggerAction(item, frontendDataSetContext);
							}
						}}
					>
						{item.icon && (
							<span className="pr-2">
								<ClayIcon symbol={item.icon} />
							</span>
						)}

						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

function CreationButton({
	firstItem,
	inEmptyState,
}: {
	firstItem: ICreationActionItem;
	inEmptyState: boolean;
}) {
	const frontendDataSetContext = useContext(FrontendDataSetContext);

	const {loadData} = frontendDataSetContext;

	const opensInNewTab = firstItem.target === ACTION_ITEM_TARGETS.BLANK;

	return (
		<LinkOrButton
			aria-label={!inEmptyState ? firstItem.label : undefined}
			className={
				inEmptyState ? 'btn btn-secondary' : 'btn btn-primary nav-btn'
			}
			data-testid="fdsCreationActionButton"
			data-tooltip-align="top"
			href={opensInNewTab ? firstItem.href : undefined}
			onClick={() => {
				if (opensInNewTab) {
					return;
				}

				firstItem.onClick?.({
					loadData,
				});

				if (firstItem.href || firstItem.target) {
					triggerAction(firstItem, frontendDataSetContext);
				}
			}}
			target={opensInNewTab ? '_blank' : undefined}
			title={!inEmptyState ? firstItem.label : undefined}
		>
			{inEmptyState ? firstItem.label : Liferay.Language.get('new')}

			{opensInNewTab && (
				<span
					className={classNames(
						'inline-item-after',
						inEmptyState ? 'inline-item' : 'd-inline-flex'
					)}
				>
					<ClayIcon symbol="shortcut" />
				</span>
			)}
		</LinkOrButton>
	);
}

function CreationMenu({
	inEmptyState,
	primaryItems,
}: {
	inEmptyState: boolean;
	primaryItems: Array<ICreationActionItem>;
}) {
	if (primaryItems?.length === 0) {
		return null;
	}

	return (
		<ul
			className={classNames('navbar-nav', {
				'd-inline-flex': inEmptyState,
			})}
		>
			<li className="nav-item">
				{primaryItems.length > 1 ? (
					<DropDown
						inEmptyState={inEmptyState}
						primaryItems={primaryItems}
					/>
				) : (
					<CreationButton
						firstItem={primaryItems[0]}
						inEmptyState={inEmptyState}
					/>
				)}
			</li>
		</ul>
	);
}

export default CreationMenu;
