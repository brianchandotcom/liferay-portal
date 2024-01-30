/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DeleteLocaleModal from './components/DeleteLocaleModal';
import LocaleSelector from './components/LocaleSelector';
import LocalesContainer from './components/LocalesContainer';
import LocalesList from './components/LocalesList';
import useRegistry from './hooks/useRegistry';

const TranslationManager = ({
	availableLocales: initialAvailableLocales,
	changeableDefaultLanguage,
	componentId,
	cssClass,
	defaultLanguageId: initialDefaultLanguageId,
	id,
	locales,
	readOnly,
}) => {
	const compId = componentId ? componentId : id;

	const [availableLocales, setAvailableLocales] = useState(
		initialAvailableLocales
	);
	const [defaultLocale, setDefaultLocale] = useState(
		initialDefaultLanguageId
	);
	const [editingLocale, setEditingLocale] = useState(
		initialDefaultLanguageId
	);

	const [visibleModal, setVisibleModal] = useState(false);

	useRegistry({
		componentId: compId,
		states: {
			availableLocales,
			defaultLocale,
			editingLocale,
		},
	});

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	const localeToBeRemovedRef = React.useRef(null);

	const removeLocale = (locale) => {
		if (defaultLocale === locale.id) {
			setDefaultLocale(editingLocale);
		}

		if (editingLocale === locale.id) {
			setEditingLocale(defaultLocale);
		}

		const newAvailableLocales = new Map(availableLocales);

		newAvailableLocales.delete(locale.id);

		setAvailableLocales(newAvailableLocales);
	};

	return (
		<>
			{visibleModal && (
				<DeleteLocaleModal
					observer={observer}
					onCancel={() => {
						localeToBeRemovedRef.current = null;
						onClose();
					}}
					onConfirm={() => {
						removeLocale(localeToBeRemovedRef.current);
						onClose();
					}}
				/>
			)}

			<div className="autofit-row">
				<div className="autofit-col autofit-col-expand">
					<LocalesContainer
						className={cssClass}
						id={id}
						readOnly={readOnly}
					>
						<LocalesList
							availableLocales={availableLocales}
							changeableDefaultLanguage={
								changeableDefaultLanguage
							}
							defaultLocale={defaultLocale}
							editingLocale={editingLocale}
							onLocaleClicked={(locale) => {
								if (changeableDefaultLanguage) {
									setDefaultLocale(locale.id);
								}
								setEditingLocale(locale.id);
							}}
							onLocaleRemoved={(locale) => {
								localeToBeRemovedRef.current = locale;
								setVisibleModal(true);
							}}
						/>

						<div className="autofit-col">
							<LocaleSelector
								locales={locales}
								onItemClick={(locale) => {
									setAvailableLocales(
										new Map(availableLocales).set(
											locale.id,
											locale
										)
									);

									setEditingLocale(locale.id);
								}}
							/>
						</div>
					</LocalesContainer>
				</div>
			</div>
		</>
	);
};

TranslationManager.propTypes = {
	availableLocales: PropTypes.instanceOf(Map),
	changeableDefaultLanguage: PropTypes.bool,
	defaultLanguageId: PropTypes.string,
	defaultLocale: PropTypes.string,
	editingLocale: PropTypes.string,
	locales: PropTypes.array,
};

export default function (props) {
	const availableLocales = new Map(
		props.availableLocales.map((locale) => [locale.id, locale])
	);

	return (
		<TranslationManager {...props} availableLocales={availableLocales} />
	);
}
