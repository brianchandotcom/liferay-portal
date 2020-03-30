/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';
import {DndProvider, createDndContext} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import LanguageListItem from './LanguageListItem.es';
import LanguageListItemEditable from './LanguageListItemEditable.es';

const noop = () => {};

const LanguagesList = ({
	defaultLocaleId,
	locales,
	isEditable = false,
	onMakeDefault = noop,
	onEditBtnClick = noop,
	onItemDrop = noop,
	moveItem = noop,
}) => {
	const manager = useRef(createDndContext(HTML5Backend));

	return (
		<ClayTable borderless headVerticalAlignment="middle" hover={false}>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell headingTitle>
						{Liferay.Language.get('active-language')}
					</ClayTable.Cell>

					{isEditable && (
						<ClayTable.Cell align="center">
							<ClayButton
								displayType="secondary"
								onClick={onEditBtnClick}
								small
							>
								{Liferay.Language.get('edit')}
							</ClayButton>
						</ClayTable.Cell>
					)}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				<DndProvider manager={manager.current.dragDropManager}>
					{locales.map((locale, index) => {
						const baseProps = {
							...locale,
							index,
							isDefault: defaultLocaleId === locale.localeId,
							key: locale.localeId,
						};

						return isEditable ? (
							<LanguageListItemEditable
								{...baseProps}
								isFirst={index === 0}
								isLast={index === locales.length - 1}
								onItemDrop={onItemDrop}
								onMakeDefault={onMakeDefault}
								onMoveDown={() => moveItem(index, index + 1)}
								onMoveUp={() => moveItem(index, index - 1)}
							/>
						) : (
							<LanguageListItem {...baseProps} />
						);
					})}
				</DndProvider>
			</ClayTable.Body>
		</ClayTable>
	);
};

LanguagesList.prototypes = {
	isEditable: PropTypes.bool,
	moveItem: PropTypes.func,
	onEditBtnClick: PropTypes.func,
	onItemDrop: PropTypes.func,
	onMakeDefault: PropTypes.func,
};

export default LanguagesList;
