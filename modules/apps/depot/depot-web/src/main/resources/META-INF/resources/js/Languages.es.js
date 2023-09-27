/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import LanguagesList from './LanguagesList.es';
import ManageLanguages from './ManageLanguages.es';

import '../css/Languages.scss';

const getLocalesInputValue = (array) =>
	array.map(({localeId}) => localeId).join(',');

function move(array, from, to) {
	const clonedArr = [...array];
	const removedItem = clonedArr.splice(from, 1)[0];
	clonedArr.splice(to, 0, removedItem);

	return clonedArr;
}

const Languages = ({
	availableLocales,
	defaultLocaleId,
	inheritLocales = true,
	portletNamespace,
	siteAvailableLocales,
	siteDefaultLocaleId,
	translatedLanguages,
}) => {
	const [currentInheritLocales, setCurrentInheritLocales] = useState(
		inheritLocales
	);

	const [customDefaultLocaleId, setCustomDefaultLocaleId] = useState(
		siteDefaultLocaleId
	);

	const [customLocales, setCustomLocales] = useState(siteAvailableLocales);

	const [localesInputValue, setLocalesInputValue] = useState(
		getLocalesInputValue(siteAvailableLocales)
	);

	const [languageWarning, setLanguageWarning] = useState(false);
	const [
		languageTranslationWarning,
		setLanguageTranslationWarning,
	] = useState(false);

	const [showModal, setShowModal] = useState(false);

	const handleOnModalClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnModalClose,
	});

	const handleOnModalDone = (selectedLocales) => {
		setCustomLocales(selectedLocales);
		onClose();
	};

	const handleOnModalOpen = () => {
		setShowModal(true);
	};

	const handleOnMakeDefault = ({localeId}) => {
		setCustomDefaultLocaleId(localeId);
		setLanguageWarning(true);
		setLanguageTranslationWarning(
			translatedLanguages && !translatedLanguages[localeId]
		);
	};

	const moveItem = (currentIndex, newIndex) => {
		setCustomLocales((languages) =>
			move(languages, currentIndex, newIndex)
		);
	};

	const handleOnItemDrop = (currentIndex, newIndex) => {
		moveItem(currentIndex, newIndex);
	};

	useEffect(() => {
		if (!currentInheritLocales) {
			setLocalesInputValue(getLocalesInputValue(customLocales));
		}
	}, [customLocales, currentInheritLocales]);

	return (
		<div className="mt-5">
			<ClayRadioGroup
				name={`${portletNamespace}TypeSettingsProperties--inheritLocales--`}
				onChange={setCurrentInheritLocales}
				value={currentInheritLocales}
			>
				<ClayRadio
					label={Liferay.Language.get(
						'use-the-default-language-options'
					)}
					value={true}
				/>

				<ClayRadio
					label={Liferay.Language.get(
						'define-a-custom-default-language-and-additional-active-languages-for-this-asset-library'
					)}
					value={false}
				/>
			</ClayRadioGroup>

			{currentInheritLocales ? (
				<LanguagesList
					defaultLocaleId={defaultLocaleId}
					locales={availableLocales}
				/>
			) : (
				<>
					<input
						name={`${portletNamespace}TypeSettingsProperties--languageId--`}
						type="hidden"
						value={customDefaultLocaleId}
					/>

					<input
						name={`${portletNamespace}TypeSettingsProperties--locales--`}
						type="hidden"
						value={localesInputValue}
					/>

					<LanguagesList
						defaultLocaleId={customDefaultLocaleId}
						isEditable
						locales={customLocales}
						moveItem={moveItem}
						onEditBtnClick={handleOnModalOpen}
						onItemDrop={handleOnItemDrop}
						onMakeDefault={handleOnMakeDefault}
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
					onModalDone={handleOnModalDone}
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
						'asset-library-name-will-display-a-generic-text-until-a-translation-is-added'
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
			localeId: PropTypes.string,
		})
	).isRequired,
	defaultLocaleId: PropTypes.string.isRequired,
	inheritLocales: PropTypes.bool,
	portletNamespace: PropTypes.string.isRequired,
	siteAvailableLocales: PropTypes.arrayOf(
		PropTypes.shape({
			displayName: PropTypes.string,
			localeId: PropTypes.string,
		})
	).isRequired,
	siteDefaultLocaleId: PropTypes.string.isRequired,
	translatedLanguages: PropTypes.object,
};

export default Languages;
