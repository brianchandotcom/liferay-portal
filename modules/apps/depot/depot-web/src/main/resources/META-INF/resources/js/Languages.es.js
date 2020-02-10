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
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import ManageLanguages from './ManageLanguages.es';

const Languages = ({
	availableLocales,
	defaultLocaleId,
	inheritLocales = true,
	portletNamespace,
	siteAvailableLocales,
	siteDefaultLocaleId,
	translatedLanguages
}) => {
	const [selectedRadioGroupValue, setSelectedRadioGroupValue] = useState(
		inheritLocales
	);

	const [customDefaultLocaleId, setCustomDefaultLocaleId] = useState(
		siteDefaultLocaleId
	);

	const [customLocales, setCustomLocales] = useState(siteAvailableLocales);

	const [languageWarning, setLanguageWarning] = useState(false);
	const [
		languageTranslationWarning,
		setLanguageTranslationWarning
	] = useState(false);

	const [showModal, setShowModal] = useState(false);

	const handleOnCloseModal = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnCloseModal
	});

	const handleOnSaveModal = selectedLocales => {
		setCustomLocales(selectedLocales);
		onClose();
	};

	const customLocalesInputRef = useRef();

	useEffect(() => {
		if (!selectedRadioGroupValue) {
			const localesIds = customLocales.map(({localeId}) => localeId);

			customLocalesInputRef.current.value = localesIds.join(',');
		}
	}, [customLocales, selectedRadioGroupValue]);

	const Language = ({displayName, isDefault, localeId, showActions}) => {
		const [active, setActive] = useState(false);

		const makeDefault = event => {
			setActive(false);
			setCustomDefaultLocaleId(localeId);
			setLanguageWarning(true);
			setLanguageTranslationWarning(
				translatedLanguages && !translatedLanguages[localeId]
			);

			Liferay.fire('inputLocalized:defaultLocaleChanged', {
				item: event.currentTarget
			});
		};

		return (
			<ClayTable.Row>
				<ClayTable.Cell expanded>
					{displayName}
					{isDefault && (
						<ClayLabel className="ml-3" displayType="info">
							{Liferay.Language.get('default')}
						</ClayLabel>
					)}
				</ClayTable.Cell>
				{showActions && (
					<ClayTable.Cell align="center">
						<ClayDropDown
							active={active}
							onActiveChange={setActive}
							trigger={
								<ClayButton
									displayType="unstyled"
									monospaced
									small
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item
									data-value={localeId}
									key={localeId}
									onClick={event => makeDefault(event)}
								>
									{Liferay.Language.get('make-default')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</ClayTable.Cell>
				)}
			</ClayTable.Row>
		);
	};

	const LanguagesList = ({defaultLocaleId, locales, showActions = false}) => {
		return (
			<ClayTable
				borderless
				headVerticalAlignment="middle"
				hover={showActions}
			>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expanded headingCell headingTitle>
							{Liferay.Language.get('language')}
						</ClayTable.Cell>

						{showActions && (
							<ClayTable.Cell align="center">
								<ClayButton
									displayType="secondary"
									onClick={() => {
										setShowModal(true);
									}}
									small
								>
									{Liferay.Language.get('manage')}
								</ClayButton>
							</ClayTable.Cell>
						)}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{locales.map(locale => {
						return (
							<Language
								{...locale}
								isDefault={defaultLocaleId === locale.localeId}
								key={locale.localeId}
								showActions={showActions}
							/>
						);
					})}
				</ClayTable.Body>
			</ClayTable>
		);
	};

	return (
		<div className="mt-5">
			<ClayRadioGroup
				name={`_${portletNamespace}_TypeSettingsProperties--inheritLocales--`}
				onSelectedValueChange={setSelectedRadioGroupValue}
				selectedValue={selectedRadioGroupValue}
			>
				<ClayRadio
					label={Liferay.Language.get(
						'use-the-default-language-options'
					)}
					value={true}
				/>

				<ClayRadio
					label={Liferay.Language.get(
						'define-a-custom-default-language-and-additional-available-languages-for-this-repository'
					)}
					value={false}
				/>
			</ClayRadioGroup>

			{selectedRadioGroupValue && (
				<LanguagesList
					defaultLocaleId={defaultLocaleId}
					locales={availableLocales}
				/>
			)}

			{!selectedRadioGroupValue && (
				<>
					<input
						name={`_${portletNamespace}_TypeSettingsProperties--languageId--`}
						type="hidden"
						value={customDefaultLocaleId}
					/>

					<input
						name={`_${portletNamespace}_TypeSettingsProperties--locales--`}
						ref={customLocalesInputRef}
						type="hidden"
					/>

					<LanguagesList
						defaultLocaleId={customDefaultLocaleId}
						locales={customLocales}
						showActions
					/>
				</>
			)}

			{showModal && (
				<ManageLanguages
					availableLocales={availableLocales}
					customDefaultLocaleId={customDefaultLocaleId}
					customLocales={customLocales}
					observer={observer}
					onModalClose={onClose}
					onModalSave={handleOnSaveModal}
				/>
			)}

			{languageWarning && (
				<ClayAlert
					displayType="warning"
					onClose={() => setLanguageWarning(false)}
					title={Liferay.Language.get('warning')}
				>
					{Liferay.Language.get(
						'this-change-will-only-affect-the-newly-created-localized-content'
					)}
				</ClayAlert>
			)}

			{languageTranslationWarning && (
				<ClayAlert
					displayType="warning"
					onClose={() => setLanguageTranslationWarning(false)}
					title={Liferay.Language.get('warning')}
				>
					{Liferay.Language.get(
						'repository-name-will-display-a-generic-text-until-a-translation-is-added'
					)}
				</ClayAlert>
			)}
		</div>
	);
};

Languages.propTypes = {
	availableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	inheritLocales: PropTypes.bool,
	portletNamespace: PropTypes.string.isRequired,
	siteAvailableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string
		})
	).isRequired,
	siteDefaultLocaleId: PropTypes.string.isRequired,
	translatedLanguages: PropTypes.object
};

export default function(props) {
	return <Languages {...props} />;
}
