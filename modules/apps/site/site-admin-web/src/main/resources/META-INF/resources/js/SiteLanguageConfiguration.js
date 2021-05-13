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
import {
	ClayDualListBox,
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
	ClaySelectWithOption,
} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useMemo, useState} from 'react';

export default function SiteLanguageConfiguration({
	availableLanguages: initialAvailableLanguages,
	currentLanguages: initialCurrentLanguages,
	defaultLanguageId: initialDefaultLanguageId,
	inheritLocales: initialInheritLocales,
	liveGroupIsGuest,
	liveGroupIsOrganization,
	portletNamespace,
}) {
	const [
		showRemoveDefaultLanguageWarning,
		setShowRemoveDefaultLanguageWarning,
	] = useState(false);

	const [
		showDefaultLanguageSiteNameWarning,
		setShowDefaultLanguageSiteNameWarning,
	] = useState(false);

	const [availableLanguages, setAvailableLanguages] = useState(
		initialAvailableLanguages
	);

	const [currentLanguages, setCurrentLanguages] = useState(
		initialCurrentLanguages
	);

	const [defaultLanguageId, setDefaultLanguageId] = useState(
		initialDefaultLanguageId
	);

	const [inheritLocales, setInheritLocales] = useState(
		initialInheritLocales ? 'true' : 'false'
	);

	const defaultLanguageLabel = useMemo(
		() =>
			currentLanguages.find(({value}) => value === defaultLanguageId)
				?.label || '',
		[currentLanguages, defaultLanguageId]
	);

	const handleItemsChange = (items) => {
		const [nextCurrentLanguages, nextAvailableLanguages] = items;

		const removingDefaultLanguage = nextAvailableLanguages.some(
			(language) => language.value === defaultLanguageId
		);

		if (removingDefaultLanguage) {
			setShowRemoveDefaultLanguageWarning(true);
		}
		else {
			setAvailableLanguages(nextAvailableLanguages);
			setCurrentLanguages(nextCurrentLanguages);
			setShowRemoveDefaultLanguageWarning(false);
		}
	};

	useEffect(() => {
		const nameInput = Liferay.component(`${portletNamespace}name`);

		if (
			nameInput &&
			!nameInput.getValue(defaultLanguageId) &&
			!liveGroupIsGuest &&
			!liveGroupIsOrganization
		) {
			setShowDefaultLanguageSiteNameWarning(true);

			nameInput.selectFlag(defaultLanguageId, false);
			nameInput.updateInput(Liferay.Language.get('unnamed-site'));
		}
		else {
			setShowDefaultLanguageSiteNameWarning(false);
		}
	}, [
		defaultLanguageId,
		portletNamespace,
		liveGroupIsGuest,
		liveGroupIsOrganization,
	]);

	return (
		<>
			<ClayRadioGroup
				name={`${portletNamespace}TypeSettingsProperties--inheritLocales--`}
				onSelectedValueChange={setInheritLocales}
				selectedValue={inheritLocales}
			>
				<ClayRadio
					label={Liferay.Language.get(
						'use-the-default-language-options'
					)}
					value="true"
				/>

				<ClayRadio
					label={Liferay.Language.get(
						'define-a-custom-default-language-and-additional-available-languages-for-this-site'
					)}
					value="false"
				/>
			</ClayRadioGroup>

			{inheritLocales === 'true' ? (
				<div>
					<h5 className="h4">
						{Liferay.Language.get('default-language')}
					</h5>
					<p>{defaultLanguageLabel}</p>

					<h5 className="h4">
						{Liferay.Language.get('available-languages')}
					</h5>

					{initialCurrentLanguages
						.map((language) => language.label)
						.join(', ')}
				</div>
			) : (
				<fieldset>
					<h5 className="h4">
						{Liferay.Language.get('default-language')}
					</h5>

					<ClaySelectWithOption
						name={`${portletNamespace}TypeSettingsProperties--languageId--`}
						onChange={(event) =>
							setDefaultLanguageId(event.target.value)
						}
						options={currentLanguages}
						value={defaultLanguageId}
					/>

					{initialDefaultLanguageId !== defaultLanguageId && (
						<ClayAlert className="mt-3" displayType="warning">
							{Liferay.Language.get(
								'this-change-will-only-affect-the-newly-created-localized-content'
							)}
						</ClayAlert>
					)}

					{showDefaultLanguageSiteNameWarning && (
						<ClayAlert className="mt-3" displayType="warning">
							{Liferay.Language.get(
								'site-name-will-display-a-generic-text-until-a-translation-is-added'
							)}
						</ClayAlert>
					)}

					<h5 className="h4 mt-4 sheet-subtitle">
						{Liferay.Language.get('available-languages')}
					</h5>

					{showRemoveDefaultLanguageWarning && (
						<ClayAlert
							autoClose
							className="mt-3"
							displayType="danger"
							onClose={() => {
								setShowRemoveDefaultLanguageWarning(false);
							}}
							title={Liferay.Language.get('error')}
						>
							{Liferay.Language.get(
								'you-cannot-remove-a-language-that-is-the-current-default-language'
							)}
						</ClayAlert>
					)}

					<ClayInput
						name={`${portletNamespace}TypeSettingsProperties--locales--`}
						type="hidden"
						value={currentLanguages
							.map((language) => language.value)
							.join(',')}
					/>

					<ClayDualListBox
						items={[currentLanguages, availableLanguages]}
						left={{
							label: Liferay.Language.get('current'),
						}}
						onItemsChange={handleItemsChange}
						right={{
							label: Liferay.Language.get('available'),
						}}
					/>
				</fieldset>
			)}
		</>
	);
}

SiteLanguageConfiguration.propTypes = {
	availableLanguages: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			value: PropTypes.string.isRequired,
		})
	).isRequired,
	currentLanguages: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string.isRequired,
			value: PropTypes.string.isRequired,
		})
	).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	inheritLocales: PropTypes.bool.isRequired,
	liveGroupIsGuest: PropTypes.bool.isRequired,
	liveGroupIsOrganization: PropTypes.bool.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};
