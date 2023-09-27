/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

function getLanguage(id) {
	const text = id.replaceAll('_', '-');
	const icon = text.toLowerCase();

	return {
		icon,
		text,
	};
}

function LanguageSelector({languageIds, onChange, selectedLanguageId}) {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const selectedLanguage = getLanguage(selectedLanguageId);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			onActiveChange={setIsDropdownOpen}
			trigger={
				<ClayButton displayType="secondary" monospaced>
					<span className="inline-item">
						<ClayIcon symbol={selectedLanguage.icon} />
					</span>

					<span className="btn-section">{selectedLanguage.text}</span>
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{languageIds.map((id) => {
					const {icon, text} = getLanguage(id);

					return (
						<ClayDropDown.Item
							active={id === selectedLanguageId}
							key={id}
							onClick={() => {
								onChange(id);
								setIsDropdownOpen(false);
							}}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={icon} />
							</span>

							{text}
						</ClayDropDown.Item>
					);
				})}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

LanguageSelector.propTypes = {
	languageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	onChange: PropTypes.func.isRequired,
	selectedLanguageId: PropTypes.string.isRequired,
};

function DataEngineLanguageSelector({
	ddmStructureIds,
	portletNamespace,
	selectedLanguageId: initialSelectedLanguageId,
	translatedLanguageIds: initialTranslatedLanguageIds,
	...restProps
}) {
	const [selectedLanguageId, setSelectedLanguageId] = useState(
		initialSelectedLanguageId
	);
	const [translatedLanguageIds, setTranslatedLanguageIds] = useState([
		...new Set([
			...initialTranslatedLanguageIds,
			initialSelectedLanguageId,
		]),
	]);

	const handleLocaleChange = (localeId) => {
		setSelectedLanguageId(localeId);

		if (!translatedLanguageIds.includes(localeId)) {
			setTranslatedLanguageIds([...translatedLanguageIds, localeId]);
		}
	};

	useEffect(() => {
		ddmStructureIds.forEach((ddmStructureId) => {
			Liferay.componentReady(
				`${portletNamespace}dataEngineLayoutRenderer${ddmStructureId}`
			).then((dataEngineLayoutRenderer) => {
				const {
					reactComponentRef: {current},
				} = dataEngineLayoutRenderer;

				if (current) {
					current.updateEditingLanguageId({
						editingLanguageId: selectedLanguageId,
					});
				}
			});
		});
	}, [portletNamespace, selectedLanguageId, ddmStructureIds]);

	return (
		<>
			<input
				name={`${portletNamespace}availableLocales`}
				type="hidden"
				value={translatedLanguageIds.join(',')}
			/>

			<LanguageSelector
				{...restProps}
				onChange={handleLocaleChange}
				selectedLanguageId={selectedLanguageId}
			/>
		</>
	);
}

DataEngineLanguageSelector.propTypes = {
	ddmStructureIds: PropTypes.arrayOf(PropTypes.string).isRequired,
	portletNamespace: PropTypes.string.isRequired,
	selectedLanguageId: PropTypes.string.isRequired,
	translatedLanguageIds: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default DataEngineLanguageSelector;
