/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import {FeatureIndicator} from 'frontend-js-components-web';
import React from 'react';

const KEY_LABEL = Liferay.Browser?.isMac() ? '⌘' : 'Ctrl';
const OPTION_KEY_LABEL = Liferay.Browser?.isMac() ? '⌥' : 'Alt';

export default function ShortcutModal({onCloseModal}) {
	const {observer} = useModal({onClose: () => onCloseModal()});

	return (
		<ClayModal
			containerProps={{className: 'cadmin'}}
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{Liferay.Language.get('keyboard-shortcuts')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('fragments')}
				</p>

				<KeyboardShortcut
					description={Liferay.Language.get('duplicate-fragment')}
					keyCombinations={[KEY_LABEL, OPTION_KEY_LABEL, 'D']}
				/>

				<KeyboardShortcut
					description={Liferay.Language.get('delete-fragment')}
					keyCombinations={['⌫']}
				/>

				<KeyboardShortcut
					description={Liferay.Language.get(
						'save-composition-for-containers-and-grids'
					)}
					keyCombinations={[KEY_LABEL, 'S']}
				/>

				<KeyboardShortcut
					description={Liferay.Language.get('show-hide-fragment')}
					keyCombinations={[KEY_LABEL, OPTION_KEY_LABEL, 'H']}
				/>

				<KeyboardShortcut
					description={Liferay.Language.get('rename')}
					keyCombinations={[KEY_LABEL, OPTION_KEY_LABEL, 'R']}
				/>

				{Liferay.FeatureFlags['LPD-18221'] ? (
					<KeyboardShortcut
						betaFeatureIndicator
						description={Liferay.Language.get('cut')}
						keyCombinations={[KEY_LABEL, 'X']}
					/>
				) : null}

				{Liferay.FeatureFlags['LPD-18221'] ? (
					<KeyboardShortcut
						betaFeatureIndicator
						description={Liferay.Language.get('copy')}
						keyCombinations={[KEY_LABEL, 'C']}
					/>
				) : null}

				{Liferay.FeatureFlags['LPD-18221'] ? (
					<KeyboardShortcut
						betaFeatureIndicator
						description={Liferay.Language.get('paste')}
						keyCombinations={[KEY_LABEL, 'V']}
					/>
				) : null}

				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('selection')}
				</p>

				<KeyboardShortcut
					description={Liferay.Language.get('select-parent')}
					keyCombinations={['⇧', 'Enter']}
				/>

				{Liferay.FeatureFlags['LPD-18221'] ? (
					<>
						<KeyboardShortcut
							betaFeatureIndicator
							description={Liferay.Language.get(
								'range-selection'
							)}
							keyCombinations={['⇧', 'Arrows']}
						/>
					</>
				) : null}

				{Liferay.FeatureFlags['LPD-18221'] ? (
					<KeyboardShortcut
						betaFeatureIndicator
						description={Liferay.Language.get(
							'noncontinuous-selection'
						)}
						keyCombinations={[KEY_LABEL, 'Enter']}
					/>
				) : null}

				<p className="sheet-subtitle text-secondary">
					{Liferay.Language.get('view')}
				</p>

				<KeyboardShortcut
					description={Liferay.Language.get('toggle-sidebars')}
					keyCombinations={[KEY_LABEL, '⇧', '.']}
				/>
			</ClayModal.Body>
		</ClayModal>
	);
}

function KeyboardShortcut({
	betaFeatureIndicator = false,
	description,
	keyCombinations,
}) {
	return (
		<div className="align-items-center d-flex mb-3">
			<div className="page-editor__shortcut-modal__shortcut text-right">
				<kbd className="c-kbd c-kbd-light">
					{keyCombinations.map((key, index) => (
						<React.Fragment key={index}>
							{key}

							{index < keyCombinations.length - 1 ? (
								<span className="c-kbd-separator">+</span>
							) : null}
						</React.Fragment>
					))}
				</kbd>
			</div>

			<p className="mb-0 ml-3 mr-2 page-editor__shortcut-modal__shortcut-description text-3 text-weight-semi-bold">
				{description}
			</p>

			{betaFeatureIndicator ? <FeatureIndicator type="beta" /> : null}
		</div>
	);
}
