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
import React from 'react';

import LanguageListItem from './LanguageListItem.es';

const noop = () => {};

const LanguagesList = ({
	defaultLocaleId,
	locales,
	showActions = false,
	onMakeDefault = noop,
	onOpenManageModal = noop
}) => {
	return (
		<ClayTable
			borderless
			headVerticalAlignment="middle"
			hover={showActions}
		>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell expanded headingCell headingTitle>
						{Liferay.Language.get('active-language')}
					</ClayTable.Cell>

					{showActions && (
						<ClayTable.Cell align="center">
							<ClayButton
								displayType="secondary"
								onClick={onOpenManageModal}
								small
							>
								{Liferay.Language.get('edit')}
							</ClayButton>
						</ClayTable.Cell>
					)}
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				{locales.map(locale => {
					return (
						<LanguageListItem
							{...locale}
							isDefault={defaultLocaleId === locale.localeId}
							key={locale.localeId}
							onMakeDefault={onMakeDefault}
							showActions={showActions}
						/>
					);
				})}
			</ClayTable.Body>
		</ClayTable>
	);
};

export default LanguagesList;
