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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import React, {useState, useMemo} from 'react';

import {updateLanguageId} from '../actions/index';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/editableFragmentEntryProcessor';
import {TRANSLATION_STATUS_TYPE} from '../config/constants/translationStatusType';
import selectPrefixedSegmentsExperienceId from '../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../store/index';

const getEditableValues = fragmentEntryLinks =>
	Object.values(fragmentEntryLinks)
		.filter(
			fragmentEntryLink =>
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues
		)
		.map(fragmentEntryLink => [
			...Object.values(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				] || {}
			),
			...Object.values(
				fragmentEntryLink.editableValues[
					BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
				] || {}
			)
		])
		.reduce(
			(editableValuesA, editableValuesB) => [
				...editableValuesA,
				...editableValuesB
			],
			[]
		);

const isTranslated = (editableValue, languageId, segmentExperienceId) =>
	editableValue[languageId] ||
	(segmentExperienceId in editableValue &&
		editableValue[segmentExperienceId][languageId]);

const getTranslationStatus = ({
	editableValuesLength,
	isDefault,
	translatedValuesLength
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
	)
};

const TranslationItem = ({
	editableValuesLength,
	isDefault,
	language,
	languageIcon,
	languageId,
	languageLabel,
	onClick,
	translatedValuesLength
}) => {
	const status = getTranslationStatus({
		editableValuesLength,
		isDefault,
		translatedValuesLength
	});

	return (
		<ClayDropDown.Item onClick={onClick} symbolLeft={languageIcon}>
			{languageId === language.languageId ? (
				<strong>{languageLabel}</strong>
			) : (
				<span>{languageLabel}</span>
			)}
			<span className="dropdown-item-indicator-end">
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
	languageId
}) {
	const [active, setActive] = useState(false);
	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);
	const editableValues = useMemo(
		() => getEditableValues(fragmentEntryLinks),
		[fragmentEntryLinks]
	);
	const languageValues = useMemo(() => {
		const availableLanguagesMut = {...availableLanguages};

		const defaultLanguage = availableLanguages[defaultLanguageId];

		delete availableLanguagesMut[defaultLanguageId];

		return Object.keys({
			[defaultLanguageId]: defaultLanguage,
			...availableLanguagesMut
		}).map(languageId => ({
			languageId,
			values: editableValues.filter(editableValue =>
				isTranslated(editableValue, languageId, segmentsExperienceId)
			)
		}));
	}, [
		availableLanguages,
		defaultLanguageId,
		editableValues,
		segmentsExperienceId
	]);

	const {languageIcon} = availableLanguages[languageId];

	return (
		<ClayDropDown
			active={active}
			hasLeftSymbols
			hasRightSymbols
			menuElementAttrs={{
				className: 'page-editor__translation'
			}}
			onActiveChange={setActive}
			trigger={
				<ClayButtonWithIcon
					displayType="secondary"
					small
					symbol={languageIcon}
				/>
			}
		>
			<ClayDropDown.ItemList>
				{languageValues.map(language => (
					<TranslationItem
						editableValuesLength={editableValues.length}
						isDefault={language.languageId === defaultLanguageId}
						key={language.languageId}
						language={language}
						languageIcon={
							availableLanguages[language.languageId].languageIcon
						}
						languageId={languageId}
						languageLabel={
							availableLanguages[language.languageId]
								.languageLabel
						}
						onClick={() => {
							dispatch(
								updateLanguageId({
									languageId: language.languageId
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
