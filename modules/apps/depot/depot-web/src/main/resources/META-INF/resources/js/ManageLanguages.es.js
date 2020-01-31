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
import {ClayCheckbox} from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayModal from '@clayui/modal';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const noop = () => {};

const ManageLanguages = ({
	availableLocales,
	customDefaultLocaleId,
	customLocales,
	observer,
	onModalClose = noop,
	onModalSave = noop
}) => {
	const [selectedLocales, setSelectedLocales] = useState(customLocales);

	const [selectedLocalesIds, setSelectedLocalesIds] = useState(
		selectedLocales.map(({localeId}) => localeId)
	);

	const onChangeLocale = (checked, displayName, selectedLocaleId) => {
		if (checked) {
			setSelectedLocales(
				selectedLocales.concat({
					displayName,
					localeId: selectedLocaleId
				})
			);
		} else {
			setSelectedLocales(
				selectedLocales.filter(
					({localeId}) => localeId != selectedLocaleId
				)
			);
		}
	};

	useEffect(() => {
		setSelectedLocalesIds(selectedLocales.map(({localeId}) => localeId));
	}, [selectedLocales]);

	const Language = ({displayName, isDefault, localeId}) => {
		const checked = selectedLocalesIds.indexOf(localeId) != -1;

		return (
			<ClayTable.Row>
				<ClayTable.Cell>
					<ClayCheckbox
						checked={checked}
						disabled={isDefault}
						onChange={() => {
							onChangeLocale(!checked, displayName, localeId);
						}}
					/>
				</ClayTable.Cell>

				<ClayTable.Cell expanded>
					{displayName}

					{isDefault && (
						<ClayLabel className="ml-3" displayType="info">
							{Liferay.Language.get('default')}
						</ClayLabel>
					)}
				</ClayTable.Cell>
			</ClayTable.Row>
		);
	};

	return (
		<ClayModal observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('language-selection')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayTable borderless headVerticalAlignment="middle">
					<ClayTable.Body>
						{availableLocales.map(locale => {
							return (
								<Language
									{...locale}
									isDefault={
										customDefaultLocaleId ===
										locale.localeId
									}
									key={locale.localeId}
								/>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={onModalClose}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							onClick={() => onModalSave(selectedLocales)}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};

ManageLanguages.propTypes = {
	availableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	customDefaultLocaleId: PropTypes.string.isRequired,
	customLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	handleSaveBtn: PropTypes.func,
	observer: PropTypes.object.isRequired,
	onModalClose: PropTypes.func
};

export default function(props) {
	return <ManageLanguages {...props} />;
}
