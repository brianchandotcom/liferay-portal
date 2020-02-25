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
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import LanguagesList from './LanguagesList.es';
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
	const [currentInheritLocales, setCurrentInheritLocales] = useState(
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

	const handleOnModalClose = () => {
		setShowModal(false);
	};

	const {observer, onClose} = useModal({
		onClose: handleOnModalClose
	});

	const handleOnModalDone = selectedLocales => {
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
	const customLocalesInputRef = useRef();

	useEffect(() => {
		if (!currentInheritLocales) {
			const localesIds = customLocales.map(({localeId}) => localeId);

			customLocalesInputRef.current.value = localesIds.join(',');
		}
	}, [customLocales, currentInheritLocales]);

	return (
		<div className="mt-5">
			<ClayRadioGroup
				name={`_${portletNamespace}_TypeSettingsProperties--inheritLocales--`}
				onSelectedValueChange={setCurrentInheritLocales}
				selectedValue={currentInheritLocales}
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
						onMakeDefault={handleOnMakeDefault}
						onOpenManageModal={handleOnModalOpen}
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
