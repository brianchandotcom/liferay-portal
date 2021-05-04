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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {updateLanguageId} from '../actions/index';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/editableFragmentEntryProcessor';
import {TRANSLATION_STATUS_TYPE} from '../config/constants/translationStatusType';
import {useSelector} from '../contexts/StoreContext';
import getLanguages from '../utils/getLanguages';

const getEditableValues = (fragmentEntryLinks) =>
	Object.values(fragmentEntryLinks)
		.filter(
			(fragmentEntryLink) =>
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues
		)
		.map((fragmentEntryLink) => [
			...Object.values(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				] || {}
			),
			...Object.values(
				fragmentEntryLink.editableValues[
					BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
				] || {}
			),
		])
		.reduce(
			(editableValuesA, editableValuesB) => [
				...editableValuesA,
				...editableValuesB,
			],
			[]
		);

const isTranslated = (editableValue, languageId) => editableValue[languageId];

const getTranslationStatus = ({
	editableValuesLength,
	isDefault,
	translatedValuesLength,
}) => {
	if (isDefault) {
		return TRANSLATION_STATUS_TYPE.default;
	}
	else if (translatedValuesLength === 0) {
		return TRANSLATION_STATUS_TYPE.untranslated;
	}
	else if (translatedValuesLength < editableValuesLength) {
		return TRANSLATION_STATUS_TYPE.translating;
	}
	else if (translatedValuesLength === editableValuesLength) {
		return TRANSLATION_STATUS_TYPE.translated;
	}
};

const TRANSLATION_STATUS_LANGUAGE = {
	[TRANSLATION_STATUS_TYPE.default]: Liferay.Language.get('default'),
	[TRANSLATION_STATUS_TYPE.translated]: Liferay.Language.get('translated'),
	[TRANSLATION_STATUS_TYPE.translating]: Liferay.Language.get('translating'),
	[TRANSLATION_STATUS_TYPE.untranslated]: Liferay.Language.get(
		'not-translated'
	),
};

const TranslationItem = ({
	editableValuesLength,
	isDefault,
	language,
	languageIcon,
	languageId,
	languageLabel,
	onClick,
	translatedValuesLength,
}) => {
	const status = getTranslationStatus({
		editableValuesLength,
		isDefault,
		translatedValuesLength,
	});

	return (
		<ClayDropDown.Item onClick={onClick} symbolLeft={languageIcon}>
			{languageId === language.languageId ? (
				<strong>{languageLabel}</strong>
			) : (
				<span>{languageLabel}</span>
			)}
			<span className="dropdown-item-indicator-end page-editor__translation__label-wrapper">
				<div
					className={classNames(
						'page-editor__translation__label label',
						status
					)}
				>
					{TRANSLATION_STATUS_LANGUAGE[status]}
					{TRANSLATION_STATUS_TYPE[status] ===
						TRANSLATION_STATUS_TYPE.translating &&
						` ${translatedValuesLength}/${editableValuesLength}`}
				</div>
			</span>
		</ClayDropDown.Item>
	);
};

export default function Translation({
	availableLanguages,
	defaultLanguageId,
	dispatch,
	fragmentEntryLinks,
	languageId,
	segmentsExperienceId,
}) {
	const [active, setActive] = useState(false);
	const editableValues = useMemo(
		() => getEditableValues(fragmentEntryLinks),
		[fragmentEntryLinks]
	);

	const availableSegmentsExperiences = useSelector(
		(state) => state.availableSegmentsExperiences
	);

	const languages = getLanguages(
		availableLanguages,
		availableSegmentsExperiences,
		segmentsExperienceId
	);

	const languageValues = useMemo(() => {
		const availableLanguagesMut = {...languages};

		const defaultLanguage = languages[defaultLanguageId];

		delete availableLanguagesMut[defaultLanguageId];

		return Object.keys({
			[defaultLanguageId]: defaultLanguage,
			...availableLanguagesMut,
		}).map((languageId) => ({
			languageId,
			values: editableValues.filter((editableValue) =>
				isTranslated(editableValue, languageId)
			),
		}));
	}, [defaultLanguageId, editableValues, languages]);

	const selectedExperienceLanguage = (
		defaultLanguageId,
		languageId,
		languages
	) => {
		if (!languages[languageId]) {
			dispatch(
				updateLanguageId({
					languageId: defaultLanguageId,
				})
			);

			return languages[defaultLanguageId];
		}

		return languages[languageId];
	};

	const {
		languageIcon,
		w3cLanguageId: languageLabel,
	} = selectedExperienceLanguage(defaultLanguageId, languageId, languages);

	return (
		<ClayDropDown
			active={active}
			hasLeftSymbols
			hasRightSymbols
			menuElementAttrs={{
				className: 'page-editor__translation',
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					aria-pressed={active}
					className="btn-monospaced"
					displayType="secondary"
					small
				>
					<ClayIcon symbol={languageIcon} />
					<span className="sr-only">{languageLabel}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{languageValues.map((language) => (
					<TranslationItem
						editableValuesLength={editableValues.length}
						isDefault={language.languageId === defaultLanguageId}
						key={language.languageId}
						language={language}
						languageIcon={
							languages[language.languageId].languageIcon
						}
						languageId={languageId}
						languageLabel={
							languages[language.languageId].w3cLanguageId
						}
						onClick={() => {
							dispatch(
								updateLanguageId({
									languageId: language.languageId,
								})
							);
							setActive(false);
						}}
						translatedValuesLength={language.values.length}
					/>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

Translation.propTypes = {
	availableLanguages: PropTypes.objectOf(
		PropTypes.shape({
			default: PropTypes.bool,
			displayName: PropTypes.string,
			languageIcon: PropTypes.string.isRequired,
			languageId: PropTypes.string,
			w3cLanguageId: PropTypes.string.isRequired,
		})
	).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	dispatch: PropTypes.func.isRequired,
	fragmentEntryLinks: PropTypes.object.isRequired,
	languageId: PropTypes.string.isRequired,
	segmentsExperienceId: PropTypes.string,
};
