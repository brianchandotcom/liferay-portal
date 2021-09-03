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

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal from '@clayui/modal';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

const emptyArray = [];
const noop = () => {};

const TranslationAdminContent = ({
	ariaLabels = {
		default: Liferay.Language.get('default'),
		manageTranslations: Liferay.Language.get('manage-translations'),
		managementToolbar: Liferay.Language.get('management-toolbar'),
		translated: Liferay.Language.get('translated'),
		notTranslated: Liferay.Language.get('not-translated'),
	},
	activeLanguageIds: initialActiveLanguageIds = emptyArray,
	availableLocales: initialAvailableLocales = emptyArray,
	defaultLanguageId,
	lastDeletedLocaleId,
	onAddLocale = noop,
	onClearRestoreLocale = noop,
	onRemoveLocale = noop,
	onRestoreLocale = noop,
	translations = {},
}) => {
	const [creationMenuActive, setCreationMenuActive] = useState(false);
	const [searchValue, setSearchValue] = useState('');

	const activeLanguageIds = useMemo(() => {
		return initialAvailableLocales.filter((availableLocale) => {
			const regExp = new RegExp(searchValue, 'i');

			return (
				initialActiveLanguageIds.includes(availableLocale.id) &&
				(availableLocale.label.match(regExp) ||
					availableLocale.displayName.match(regExp))
			);
		});
	}, [initialAvailableLocales, initialActiveLanguageIds, searchValue]);

	const availableLocales = useMemo(() => {
		return initialAvailableLocales.filter(
			(availableLocale) =>
				!initialActiveLanguageIds.includes(availableLocale.id)
		);
	}, [initialAvailableLocales, initialActiveLanguageIds]);

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('manage-translations')}
			</ClayModal.Header>

			<ClayModal.Body scrollable>
				<ClayManagementToolbar
					aria-label={ariaLabels.managementToolbar}
				>
					<ClayManagementToolbar.Search showMobile={true}>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									insetAfter={true}
									onChange={(event) => {
										const {value} = event.target;

										setSearchValue(value);
									}}
									placeholder={Liferay.Language.get('search')}
									value={searchValue}
								/>

								<ClayInput.GroupInsetItem after tag="span">
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'search'
										)}
										displayType="unstyled"
										onClick={() => {
											setSearchValue('');
										}}
										symbol={
											searchValue ? 'times' : 'search'
										}
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>

					<ClayManagementToolbar.ItemList>
						<ClayDropDown
							active={
								creationMenuActive &&
								availableLocales.length > 0
							}
							hasLeftSymbols
							onActiveChange={setCreationMenuActive}
							trigger={
								<ClayButtonWithIcon
									disabled={availableLocales.length === 0}
									symbol="plus"
								/>
							}
						>
							<ClayDropDown.ItemList>
								{availableLocales.map((availableLocale) => {
									return (
										<ClayDropDown.Item
											key={availableLocale.label}
											onClick={() => {
												onAddLocale(availableLocale.id);
											}}
											symbolLeft={availableLocale.symbol}
										>
											{availableLocale.label}
										</ClayDropDown.Item>
									);
								})}
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</ClayManagementToolbar.ItemList>
				</ClayManagementToolbar>

				{lastDeletedLocaleId && (
					<ClayAlert
						displayType="success"
						onClose={onClearRestoreLocale}
						title={Liferay.Language.get('success')}
					>
						{Liferay.Util.sub(
							Liferay.Language.get('translation-was-deleted'),
							availableLocales.find(
								(availableLocale) =>
									availableLocale.id === lastDeletedLocaleId
							).label
						)}
						<ClayLink onClick={onRestoreLocale}>
							{Liferay.Language.get('undo')}
						</ClayLink>
					</ClayAlert>
				)}

				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								{Liferay.Language.get('code')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{Liferay.Language.get('language')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{Liferay.Language.get('status')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell />
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{activeLanguageIds.map((activeLocale) => {
							const label = activeLocale.label;

							const isDefaultLocale =
								activeLocale.id === defaultLanguageId;
							const localeValue = translations[label];

							return (
								<ClayTable.Row key={label}>
									<ClayTable.Cell>
										<>
											<ClayIcon
												className="inline-item inline-item-before"
												symbol={activeLocale.symbol}
											/>
											<strong>{label}</strong>
										</>
									</ClayTable.Cell>

									<ClayTable.Cell expanded>
										{activeLocale.displayName}
									</ClayTable.Cell>

									<ClayTable.Cell>
										<ClayLabel
											displayType={
												isDefaultLocale
													? 'info'
													: localeValue
													? 'success'
													: 'warning'
											}
										>
											{isDefaultLocale
												? ariaLabels.default
												: localeValue
												? ariaLabels.translated
												: ariaLabels.notTranslated}
										</ClayLabel>
									</ClayTable.Cell>

									<ClayTable.Cell>
										{!isDefaultLocale && (
											<ClayIcon
												className="inline-item"
												onClick={() =>
													onRemoveLocale(
														activeLocale.id
													)
												}
												symbol="trash"
											/>
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</ClayModal.Body>
		</>
	);
};

TranslationAdminContent.propTypes = {
	activeLanguageIds: PropTypes.arrayOf(PropTypes.string),
	ariaLabels: PropTypes.shape({
		default: PropTypes.string,
		manageTranslations: PropTypes.string,
		managementToolbar: PropTypes.string,
		tranlated: PropTypes.string,
		notTranslated: PropTypes.string,
	}),
	availableLocales: PropTypes.arrayOf(PropTypes.object).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	lastDeletedLocale: PropTypes.object,
	translations: PropTypes.object,
};

export default TranslationAdminContent;
