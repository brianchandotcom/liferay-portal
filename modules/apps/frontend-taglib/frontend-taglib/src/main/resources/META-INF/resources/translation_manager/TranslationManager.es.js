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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState, useEffect, useRef} from 'react';

const LocalesContainer = ({children, ...otherProps}) => {
	return (
		<div {...otherProps} className="lfr-translationmanager">
			<ul className="nav nav-tabs nav-tabs-default">{children}</ul>
		</div>
	);
};

// In JSX is not possible just return myMap.forEach(value, key) => someJSX :/
const mapIterator = (map, callback) => {
	const result = [];

	// How can I avoid this for..of here?
	// eslint-disable-next-line
	for (const [key, value] of map) {
		result.push(callback(value, key));
	}

	return result;
};

const AvailableLocales = ({
	availableLocales,
	changeableDefaultLanguage,
	defaultLocale,
	editingLocale,
	onLocaleClicked,
	onLocaleRemoved
}) => {
	// Remove this line! It's just used to cast `availableLocales` to a Map structure.
	const availableLocalesMap = new Map(
		availableLocales.map(locale => [locale.id, locale])
	);

	return mapIterator(availableLocalesMap, (locale, localeId) => (
		<Locale
			editingLocale={editingLocale}
			key={localeId}
			locale={locale}
			onLocaleClicked={() => onLocaleClicked && onLocaleClicked(locale)}
		>
			{(changeableDefaultLanguage && defaultLocale === localeId) ||
				(localeId !== defaultLocale && (
					<ClayButtonWithIcon
						className="pl-2"
						displayType="unstyled"
						onClick={() =>
							onLocaleRemoved && onLocaleRemoved(locale)
						}
						symbol="times"
					/>
				))}
		</Locale>
	));
};

const Locale = ({children, editingLocale, locale, onLocaleClicked}) => (
	<li className={editingLocale === locale.id ? 'active' : undefined}>
		<a href="javascript:;">
			<span onClick={() => onLocaleClicked && onLocaleClicked(locale)}>
				<span className="icon-monospaced pr-2">
					<ClayIcon symbol={locale.icon} />
				</span>
				<span>{locale.label}</span>
			</span>

			{children}
		</a>
	</li>
);

const DropDownWithState = ({children}) => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			alignmentPosition={Align.BottomCenter}
			onActiveChange={isActive => setIsDropdownOpen(isActive)}
			trigger={
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					symbol="plus"
				/>
			}
		>
			{children}
		</ClayDropDown>
	);
};

const LocalesDropdown = ({locales, onItemClick}) => (
	<DropDownWithState>
		<ClayDropDown.ItemList>
			{locales.map(locale => (
				<ClayDropDown.Item
					id={locale.id}
					key={locale.id}
					onClick={() => onItemClick && onItemClick(locale)}
					symbolRight={locale.icon}
				>
					{locale.label}
				</ClayDropDown.Item>
			))}
		</ClayDropDown.ItemList>
	</DropDownWithState>
);

const useRegistry = ({componentId, states}) => {
	const currentState = useRef({...states});
	const eventsRef = useRef([]);
	const previousState = useRef({...states});

	const detach = (stateName, callback) => {
		if (eventsRef.current) {
			const refIndex = eventsRef.current.findIndex(
				event =>
					stateName === event.stateName && callback === event.callback
			);
			if (refIndex !== -1) {
				delete eventsRef.current[refIndex];
			}
		}
	};

	const get = stateName => {
		const stateValue = currentState.current[stateName];

		if (stateValue) {
			return stateValue;
		}
	};

	const on = (stateName, callback) => {
		eventsRef.current.push({callback, stateName});
	};

	if (!Liferay.component(componentId)) {
		Liferay.component(
			componentId,
			{
				detach,
				get,
				on
			},
			{
				destroyOnNavigate: true
			}
		);
	}

	useEffect(() => {
		currentState.current = {...states};
	}, [states]);

	useEffect(() => {
		const stateChanged = [];

		Object.entries(states).forEach(([key, value]) => {
			if (value !== previousState.current[key]) {
				stateChanged.push(key);
			}
		});

		eventsRef.current.forEach(({callback, stateName}) => {
			if (stateChanged.includes(stateName)) {
				callback({
					newValue: states[stateName],
					previousValue: previousState.current[stateName]
				});
			}
		});

		previousState.current = {...states};
	}, [states]);
};

const TranslationManager = ({
	availableLocales: initialAvailableLocales,
	changeableDefaultLanguage,
	componentId,
	cssClass,
	defaultLanguageId: initialDefaultLanguageId,
	id,
	locales,
	readOnly
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
			editingLocale
		}
	});

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false)
	});

	const localeToBeRemoved = React.useRef(null);

	const removeLocale = locale => {
		if (defaultLocale === locale.id) {
			setDefaultLocale(editingLocale);
		}

		if (editingLocale === locale.id) {
			setEditingLocale(defaultLocale);
		}

		setAvailableLocales(
			availableLocales.filter(
				({id}) => id !== locale.id || id === defaultLocale
			)
		);
	};

	return (
		<>
			{visibleModal && (
				<ClayModal observer={observer} size="sm">
					<ClayModal.Body>
						<h4>
							{Liferay.Language.get(
								'are-you-sure-you-want-to-deactivate-this-language'
							)}
						</h4>
					</ClayModal.Body>
					<ClayModal.Footer
						last={
							<ClayButton.Group spaced>
								<ClayButton
									displayType="secondary"
									onClick={() => {
										localeToBeRemoved.current = null;
										onClose();
									}}
								>
									{Liferay.Language.get('dismiss')}
								</ClayButton>
								<ClayButton
									onClick={() => {
										removeLocale(localeToBeRemoved.current);
										onClose();
									}}
								>
									{Liferay.Language.get('delete')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayModal>
			)}

			<LocalesContainer className={cssClass} id={id} readOnly={readOnly}>
				<AvailableLocales
					availableLocales={availableLocales}
					changeableDefaultLanguage={changeableDefaultLanguage}
					defaultLocale={defaultLocale}
					editingLocale={editingLocale}
					onLocaleClicked={locale => {
						if (changeableDefaultLanguage) {
							setDefaultLocale(locale.id);
						}
						setEditingLocale(locale.id);
					}}
					onLocaleRemoved={locale => {
						localeToBeRemoved.current = locale;
						setVisibleModal(true);
					}}
				/>

				<li>
					<LocalesDropdown
						locales={locales}
						onItemClick={locale => {
							setEditingLocale(locale.id);
							if (availableLocales.indexOf(locale) === -1) {
								setAvailableLocales([
									...availableLocales,
									locale
								]);
							}
						}}
					/>
				</li>
			</LocalesContainer>
		</>
	);
};

TranslationManager.propTypes = {
	availableLocales: PropTypes.instanceOf(Map),
	changeableDefaultLanguage: PropTypes.bool,
	defaultLanguageId: PropTypes.string,
	defaultLocale: PropTypes.string,
	editingLocale: PropTypes.string,
	locales: PropTypes.array
};

export default TranslationManager;
