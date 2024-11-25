/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

export function LocalizationSelect({
	defaultLanguageId,
	editMode,
	hideLanguageLabel,
	locales,
	size,
}) {
	const [active, setActive] = useState(false);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);

	const onSelectedLocaleChange = (localeId) => {
		setSelectedLocaleId(localeId);
		setActive(false);
	};

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
					<LanguageItem locale={item} />
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

const LanguageItem = ({locale}) => {
	return (
		<ClayLayout.ContentRow containerElement="span">
			<ClayLayout.ContentCol containerElement="span" expand>
				<ClayLayout.ContentSection>
					<ClayIcon
						className="inline-item inline-item-before"
						symbol={locale.symbol}
					/>

					<span>{locale.label}</span>
				</ClayLayout.ContentSection>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
};
