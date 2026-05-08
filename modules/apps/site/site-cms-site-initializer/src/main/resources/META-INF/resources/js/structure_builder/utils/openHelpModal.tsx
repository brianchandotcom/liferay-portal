/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayVerticalNav} from '@clayui/nav';
import {openModal} from 'frontend-js-components-web';
import React, {useState} from 'react';

type Shortcut = {
	keys: string[];
	label: string;
};

type Section = {
	id: string;
	label: string;
	shortcuts: Shortcut[];
};

const IS_MAC = Liferay.Browser?.isMac();

const META = IS_MAC ? '⌘' : 'Ctrl';
const SHIFT = IS_MAC ? '⇧' : 'Shift';
const ALT = IS_MAC ? '⌥' : 'Alt';
const BACKSPACE = '⌫';
const ENTER = '↵';
const ARROWS = '←↑↓→';

const SECTIONS: Section[] = [
	{
		id: 'actions',
		label: Liferay.Language.get('actions'),
		shortcuts: [
			{
				keys: [META, 'D'],
				label: Liferay.Language.get('duplicate-item'),
			},
			{
				keys: [BACKSPACE],
				label: Liferay.Language.get('delete-selected-item'),
			},
			{
				keys: [SHIFT, ENTER],
				label: Liferay.Language.get('add-referenced-structure'),
			},
			{
				keys: [META, 'G'],
				label: Liferay.Language.get('create-repeatable-group'),
			},
			{
				keys: [SHIFT, META, 'G'],
				label: Liferay.Language.get('ungroup'),
			},
			{
				keys: [ALT, META, 'R'],
				label: Liferay.Language.get('rename'),
			},
			{
				keys: [META, 'C'],
				label: Liferay.Language.get('copy'),
			},
			{
				keys: [META, 'V'],
				label: Liferay.Language.get('paste'),
			},
			{
				keys: [META, 'S'],
				label: Liferay.Language.get('save'),
			},
			{
				keys: [META, 'P'],
				label: Liferay.Language.get('publish'),
			},
		],
	},
	{
		id: 'selection',
		label: Liferay.Language.get('selection'),
		shortcuts: [
			{
				keys: [SHIFT, ARROWS],
				label: Liferay.Language.get('range-selection'),
			},
			{
				keys: [META, ENTER],
				label: Liferay.Language.get('noncontinuous-selection'),
			},
		],
	},
	{
		id: 'help',
		label: Liferay.Language.get('help'),
		shortcuts: [
			{
				keys: [SHIFT, '?'],
				label: Liferay.Language.get('help-and-shortcuts'),
			},
		],
	},
];

export default function openHelpModal() {
	openModal({
		bodyComponent: HelpModalBody,
		center: true,
		height: '700px',
		size: 'lg',
		title: Liferay.Language.get('help-and-shortcuts'),
	});
}

function HelpModalBody() {
	const [activeId, setActiveId] = useState(SECTIONS[0].id);

	const activeSection =
		SECTIONS.find((section) => section.id === activeId) ?? SECTIONS[0];

	const navItems = SECTIONS.map((section) => ({
		active: section.id === activeId,
		id: section.id,
		label: section.label,
		onClick: () => setActiveId(section.id),
	}));

	return (
		<div className="d-flex">
			<div className="flex-shrink-0 mr-3 structure-builder__help-modal-nav">
				<ClayVerticalNav items={navItems} />
			</div>

			<div className="flex-grow-1 pl-3 pr-5">
				{activeSection.shortcuts.map((shortcut, index) => (
					<div
						className={`align-items-center d-flex py-3 ${
							index < activeSection.shortcuts.length - 1
								? 'border-bottom'
								: ''
						}`}
						key={shortcut.label}
					>
						<span className="flex-grow-1">{shortcut.label}</span>

						<div className="flex-shrink-0 structure-builder__help-modal-shortcut">
							<kbd className="c-kbd c-kbd-light">
								{shortcut.keys.map((key, keyIndex) => (
									<React.Fragment key={keyIndex}>
										{key}

										{keyIndex < shortcut.keys.length - 1 ? (
											<span className="c-kbd-separator">
												+
											</span>
										) : null}
									</React.Fragment>
								))}
							</kbd>
						</div>
					</div>
				))}
			</div>
		</div>
	);
}
