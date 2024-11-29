/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {TranslationAdminItem} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

const EVENT_TRANSLATION_STATUS = 'localizationSelect:updateTranslationStatus';

export function LocalizationSelect({
	defaultLanguageId,
	editMode,
	hideLanguageLabel,
	locales,
	size,
}) {
	const [active, setActive] = useState(false);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [translatedInputs, setTranslatedInputs] = useState({});

	const localizableInputs = useMemo(
		() =>
			document.querySelectorAll('label[data-localizable="true"]').length,
		[]
	);

	const onSelectedLocaleChange = (localeId) => {
		setSelectedLocaleId(localeId);
		setActive(false);
	};

	useEffect(() => {
		Liferay.on(EVENT_TRANSLATION_STATUS, ({languageId}) => {
			const translatedInputs = Array.from(
				document.querySelectorAll(
					`[type="hidden"][name*="_${languageId}"]`
				)
			).filter((input) => input.getAttribute('value') !== null);

			setTranslatedInputs((previousState) => ({
				...previousState,
				[languageId]: translatedInputs.length,
			}));
		});

		return () => {
			Liferay.detach(EVENT_TRANSLATION_STATUS);
		};
	}, []);

	return (
		<Picker
			active={active}
			as={TriggerButton}
			hideLanguageLabel={hideLanguageLabel}
			items={locales}
			onActiveChange={(active) => {
				if (!editMode) {
					setActive(active);
				}
			}}
			onSelectionChange={(id) => {
				onSelectedLocaleChange(id);

				Liferay.fire('localizationSelect:localeChanged', {
					languageId: id,
				});
			}}
			selectedKey={selectedLocaleId}
			selectedLocale={locales.find(
				(locale) => locale.id === selectedLocaleId
			)}
			small={size === 'small'}
		>
			{(item) => (
				<Option key={item.id} textValue={item.label}>
					<TranslationAdminItem
						defaultLanguageId={defaultLanguageId}
						item={item}
						translationProgress={
							localizableInputs
								? {
										totalItems: localizableInputs,
										translatedItems: translatedInputs,
									}
								: null
						}
					/>
				</Option>
			)}
		</Picker>
	);
}

const TriggerButton = React.forwardRef(
	({hideLanguageLabel, selectedLocale, small, ...props}, ref) => {
		const ariaLabelButton = sub(
			Liferay.Language.get('select-a-language.-current-language-x'),
			selectedLocale.displayName
		);

		return (
			<ClayButton
				{...props}
				aria-label={ariaLabelButton}
				className="btn-block form-control-select"
				displayType="secondary"
				ref={ref}
				size={small ? 'sm' : undefined}
			>
				<span className="inline-item-before">
					<ClayIcon symbol={selectedLocale.symbol} />
				</span>

				{!hideLanguageLabel ? (
					<span className="font-weight-normal mr-2">
						{selectedLocale.label}
					</span>
				) : null}
			</ClayButton>
		);
	}
);
